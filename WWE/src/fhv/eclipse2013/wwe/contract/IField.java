package fhv.eclipse2013.wwe.contract;

import java.awt.Point;
import java.beans.PropertyChangeListener;

import org.jdom2.Element;

import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.FieldState;
import fhv.eclipse2013.wwe.impl.field.FieldNeighbours;

public interface IField {
	FieldState getState();

	void setState(FieldState state);

	void setLock(boolean lock);

	FieldNeighbours getNeighbours();

	void prepareNextState();

	void nextState();

	void addPropertyChangeListener(PropertyChangeListener l);

	void removePropertyChangeListener(PropertyChangeListener l);

	void click();

	Element getElement(Element e);

	void reinitiateNeighbours();

	Point getCoordinate();

	void init(ISimulationScope scope);
}