package fhv.eclipse2013.wwe.impl.scope;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.scope.IFieldAddedEventListener;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.scope.IStateChangedEventListener;
import fhv.eclipse2013.wwe.contract.scope.IStepChangedEventListener;
import fhv.eclipse2013.wwe.contract.scope.IStepChangedEventListener.Type;
import fhv.eclipse2013.wwe.contract.state.SimulationState;
import fhv.eclipse2013.wwe.impl.scope.event.FieldAddedSupport;
import fhv.eclipse2013.wwe.impl.scope.event.StateChangedSupport;
import fhv.eclipse2013.wwe.impl.scope.event.StepChangedSupport;

public abstract class AbstractScopeEvents implements ISimulationScope {
	private PropertyChangeSupport propertyChanged = new PropertyChangeSupport(
			this);
	private StateChangedSupport stateChanged = new StateChangedSupport();
	private StepChangedSupport stepChanged = new StepChangedSupport();
	private FieldAddedSupport fieldAdded = new FieldAddedSupport();
	private boolean lock;

	protected boolean getLock() {
		return lock;
	}

	protected void onPropertyChanged(String propertyName, Object oldValue,
			Object newValue) {
		this.propertyChanged.firePropertyChange(propertyName, oldValue,
				newValue);
	}

	protected void onStateChanged() {
		SimulationState state = getSimulationState();
		this.lock = (state.equals(SimulationState.started) || state
				.equals(SimulationState.paused));
		stateChanged.fireStateChanged(state, lock);
	}

	protected void removeAllStateListener() {
		stateChanged.removeAll();
	}


	protected void onStepChanged(Type t) {
		stepChanged.fireStepChanged(t);
	}

	protected void removeAllStepListener() {
		stepChanged.removeAll();
	}

	protected void onFieldAdded(int x, int y, IField f) {
		fieldAdded.fireStepChanged(x, y, f);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		this.propertyChanged.addPropertyChangeListener(l);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
		this.propertyChanged.removePropertyChangeListener(l);
	}

	public void addStepListener(IStepChangedEventListener l) {
		this.stepChanged.addStepListener(l);
	}

	public void removeStepListener(IStepChangedEventListener l) {
		this.stepChanged.removeStepListener(l);
	}

	public void addStateChangedListener(IStateChangedEventListener l) {
		this.stateChanged.addStateChangedListener(l);
	}

	public void removeStateChangedListener(IStateChangedEventListener l) {
		this.stateChanged.removeStateChangedListener(l);
	}

	public void addFieldAddedListener(IFieldAddedEventListener l) {
		this.fieldAdded.addStepListener(l);
	}

	public void removeFieldAddedListener(IFieldAddedEventListener l) {
		this.fieldAdded.removeStepListener(l);
	}
}
