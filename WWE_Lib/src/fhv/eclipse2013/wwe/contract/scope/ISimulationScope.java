package fhv.eclipse2013.wwe.contract.scope;

import java.awt.Dimension;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.state.SimulationState;

public interface ISimulationScope {

	void setName(String name);

	String getName();

	int getWidth();

	int getHeight();

	void start();

	void stop();

	void nextStep();

	void backStep();

	void click(int x, int y);

	SimulationState getSimulationState();

	Dimension getSize();

	IField getField(int x, int y);

	void addPropertyChangeListener(PropertyChangeListener l);

	void removePropertyChangeListener(PropertyChangeListener l);

	void addStateChangedListener(IStateChangedEventListener l);

	void removeStateChangedListener(IStateChangedEventListener l);

	void addStepListener(IStepChangedEventListener l);

	void removeStepListener(IStepChangedEventListener l);

	boolean fieldExists(int x, int y);

	void save(String filename) throws IOException;

}
