package fhv.eclipse2013.wwe.contract;

import java.beans.PropertyChangeListener;

import org.jdom2.Element;

import fhv.eclipse2013.wwe.impl.field.FieldNeighbours;

public interface IField {
	FieldState getState();

	void setState(FieldState state);

	FieldNeighbours getNeighbours();

	void prepareNextState();

	void nextState();

	void addPropertyChangeListener(PropertyChangeListener l);

	void removePropertyChangeListener(PropertyChangeListener l);

	void click();

	Element getElement(Element e);
}
