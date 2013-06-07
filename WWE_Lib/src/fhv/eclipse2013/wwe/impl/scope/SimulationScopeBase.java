package fhv.eclipse2013.wwe.impl.scope;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.SimulationState;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.scope.IStateChangedEventListener;
import fhv.eclipse2013.wwe.contract.scope.IStepEventListener;
import fhv.eclipse2013.wwe.contract.scope.IStepEventListener.Type;

public abstract class SimulationScopeBase implements ISimulationScope {

	private ScopeSize size;
	private IField[][] fields;
	private SimulationState simulationState = SimulationState.stopped;

	public SimulationScopeBase(int w, int h, boolean init) {
		this.setSize(new ScopeSize(w, h));
		if (init) {
			this.init();
		}
	}

	protected void init() {
		this.fields = new IField[this.getWidth()][];
		for (int x = 0; x < this.getWidth(); x++) {
			this.fields[x] = new IField[this.getHeight()];
			for (int y = 0; y < this.getHeight(); y++) {
				this.fields[x][y] = this.initField(x, y);
			}
		}
		this.setSimulationState(SimulationState.init);
	}

	protected abstract IField initField(int x, int y);

	protected void setFields(IField[][] fields) {
		this.fields = fields;
	}

	@Override
	public IField getField(int x, int y) {
		if ((x >= 0) && (x < this.getWidth()) && (y >= 0)
				&& (y < this.getHeight())) {
			return this.fields[x][y];
		} else {
			return null;
		}
	}

	private void setSize(ScopeSize size) {
		ScopeSize old = this.size;
		this.size = size;
		this.onPropertyChanged("size", old, this.size);
	}

	@Override
	public ScopeSize getSize() {
		return this.size;
	}

	public int getWidth() {
		return this.getSize().getWidth();
	}

	public int getHeight() {
		return this.getSize().getHeight();
	}

	protected void setSimulationState(SimulationState state) {
		SimulationState old = this.simulationState;
		this.simulationState = state;
		this.onPropertyChanged("simulationState", old, this.simulationState);

		if (this.simulationState.equals(SimulationState.started)
				|| this.simulationState.equals(SimulationState.paused)) {
			this.onStateChanged(true);
		} else {
			this.onStateChanged(false);
		}
	}

	@Override
	public SimulationState getSimulationState() {
		return this.simulationState;
	}

	private PropertyChangeSupport changes = new PropertyChangeSupport(this);

	private List<IStateChangedEventListener> stateChangedListener = new ArrayList<>();
	private List<IStepEventListener> stepEventListener = new ArrayList<>();

	protected void onPropertyChanged(String propertyName, Object oldValue,
			Object newValue) {
		this.changes.firePropertyChange(propertyName, oldValue, newValue);
	}

	protected void onStepChanged(Type t) {
		for (IStepEventListener event : this.stepEventListener) {
			if (t.equals(Type.prepare)) {
				event.handlePrepareNextState();
			} else if (t.equals(Type.next)) {
				event.handleNextStep();
			} else if (t.equals(Type.back)) {
				event.handleBackStep();
			} else if (t.equals(Type.reset)) {
				event.handleReset();
			}
		}
	}

	protected void onStateChanged(boolean lock) {
		for (IStateChangedEventListener event : this.stateChangedListener) {
			event.handleStateChanged(this.getSimulationState(), lock);
		}
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		this.changes.addPropertyChangeListener(l);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
		this.changes.removePropertyChangeListener(l);
	}

	@Override
	public void addStateChangedListener(IStateChangedEventListener l) {
		this.stateChangedListener.add(l);
	}

	@Override
	public void removeStateChangedListener(IStateChangedEventListener l) {
		this.stateChangedListener.remove(l);
	}

	@Override
	public void addStepListener(IStepEventListener l) {
		this.stepEventListener.add(l);
	}

	@Override
	public void removeStepListener(IStepEventListener l) {
		this.stepEventListener.remove(l);
	}

}
