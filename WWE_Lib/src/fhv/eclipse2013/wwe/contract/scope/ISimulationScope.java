package fhv.eclipse2013.wwe.contract.scope;

import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import org.eclipse.swt.graphics.Rectangle;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.state.SimulationState;
import fhv.eclipse2013.wwe.contract.toolbox.IToolElement;

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

	void addFieldAddedListener(IFieldAddedEventListener l);

	void removeFieldAddedListener(IFieldAddedEventListener l);

	boolean fieldExists(int x, int y);

	void place(IToolElement element, Point coord);

	void save(String filename) throws IOException;

	void saveAsToolElement(String filename, String imageFileName) throws IOException;

	Rectangle getMinRectangle();

	Rectangle getRect();

}
