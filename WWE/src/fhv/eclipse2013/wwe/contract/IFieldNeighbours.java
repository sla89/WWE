package fhv.eclipse2013.wwe.contract;

import java.awt.Point;
import java.beans.PropertyChangeListener;

import fhv.eclipse2013.wwe.contract.state.FieldState;

public interface IFieldNeighbours {

	public abstract Integer getState(FieldState state);

	public abstract void addPropertyChangeListener(PropertyChangeListener l);

	public abstract void removePropertyChangeListener(PropertyChangeListener l);

	public abstract Point getCoordinate();

	public abstract void init();

}