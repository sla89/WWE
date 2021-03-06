package fhv.eclipse2013.wwe.impl.field;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Stack;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.IFieldNeighbours;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.scope.IStateChangedEventListener;
import fhv.eclipse2013.wwe.contract.scope.IStepChangedEventListener;
import fhv.eclipse2013.wwe.contract.state.FieldState;
import fhv.eclipse2013.wwe.contract.state.SimulationState;

public abstract class FieldBase implements IField {

	private PropertyChangeSupport changes = new PropertyChangeSupport(this);

	private IStateChangedEventListener stateListener = new IStateChangedEventListener() {
		@Override
		public void handleStateChanged(SimulationState state, boolean lock) {
			FieldBase.this.lock = lock;
		}
	};

	private IStepChangedEventListener stepListener = new IStepChangedEventListener() {
		@Override
		public void handlePrepareNextState() {
			FieldBase.this.prepareNextState();
		}

		@Override
		public void handleNextStep() {
			FieldBase.this.nextState();
		}

		@Override
		public void handleBackStep() {
			if (!FieldBase.this.stack.isEmpty()) {
				FieldBase.this.setState(FieldBase.this.stack.pop());
			}
		}

		@Override
		public void handleReset() {
			FieldBase.this.setState(FieldBase.this.original);
			FieldBase.this.stack.clear();
		}
	};

	private IFieldNeighbours neighbours;
	private FieldState original;
	private FieldState state;
	private FieldState nextState;
	private boolean lock = false;

	private Stack<FieldState> stack = new Stack<>();

	public FieldBase(ISimulationScope scope) {
		this.state = FieldState.none;
		this.original = FieldState.none;
		this.nextState = FieldState.none;
		init(scope);
	}
@Override
	public void init(ISimulationScope scope) {
		scope.addStateChangedListener(stateListener);
		scope.addStepListener(stepListener);
	}

	@Override
	public IFieldNeighbours getNeighbours() {
		return this.neighbours;
	}

	@Override
	public Point getCoordinate() {
		return getNeighbours().getCoordinate();
	}

	protected void setNeighbours(IFieldNeighbours neighbours) {
		this.neighbours = neighbours;
	}

	@Override
	public FieldState getState() {
		return this.state;
	}

	@Override
	public void setState(FieldState state) {
		FieldState old = this.state;
		this.state = state;
		this.changes.firePropertyChange("state", old, this.state);
		this.setNextState(state);
		if (!this.lock) {
			this.original = state;
		}
	}

	@Override
	public void setLock(boolean lock) {
		changes.firePropertyChange("lock", this.lock, this.lock = lock);
	}

	protected FieldState getNextState() {
		return this.nextState;
	}

	protected void setNextState(FieldState nextState) {
		this.nextState = nextState;
	}

	protected FieldState getOriginal() {
		return this.original;
	}

	@Override
	public void nextState() {
		if (this.lock) {
			this.stack.push(this.getState());
			this.setState(this.getNextState());
		}
	}

	@Override
	public void prepareNextState() {
		if (this.lock) {
			this.setNextState(this.getNextState(this.getState()));
		}
	}

	protected abstract FieldState getNextState(FieldState state);

	@Override
	public void click() {
		if (!this.lock) {
			this.setState(this.onClick(this.getState()));
		}
	}

	public void reinitiateNeighbours() {
		this.neighbours.init();
	}

	protected abstract FieldState onClick(FieldState state);

	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		this.changes.addPropertyChangeListener(l);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
		this.changes.removePropertyChangeListener(l);
	}
}
