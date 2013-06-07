package fhv.eclipse2013.wwe.contract.scope;

import java.beans.PropertyChangeListener;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.SimulationState;
import fhv.eclipse2013.wwe.impl.scope.ScopeSize;

public interface ISimulationScope {
	void start();

	void stop();

	void backStep();

	void click(int x, int y);

	SimulationState getSimulationState();

	ScopeSize getSize();

	IField getField(int x, int y);

	void addPropertyChangeListener(PropertyChangeListener l);

	void removePropertyChangeListener(PropertyChangeListener l);

	void addStateChangedListener(IStateChangedEventListener l);

	void removeStateChangedListener(IStateChangedEventListener l);

	void addStepListener(IStepEventListener l);

	void removeStepListener(IStepEventListener l);

}
