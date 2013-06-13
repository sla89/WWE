package fhv.eclipse2013.wwe.impl.field;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.IFieldNeighbours;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.FieldState;

public abstract class AbstractNeighbours implements IFieldNeighbours {
	private class FieldChanged implements PropertyChangeListener {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals("state")) {
				removeOne((FieldState) evt.getOldValue());
				addOne((FieldState) evt.getNewValue());
			}
		}
	};

	protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

	private ISimulationScope scope;
	private Point coordinate;

	private HashMap<FieldState, Integer> states = new HashMap<FieldState, Integer>();

	protected IField[] fields;

	private IField field;

	public AbstractNeighbours(ISimulationScope scope, IField field,
			Point coordinate) {
		this.scope = scope;
		this.coordinate = coordinate;
		this.field = field;

		this.field.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0.getPropertyName().equals("state")) {
					FieldState state = (FieldState) arg0.getNewValue();
					if (!state.equals(FieldState.none) && (fields == null)) {
						init();
					}
				}
			}
		});
	}

	protected void initField(int dx, int dy, int i) {
		int x = (int) (this.coordinate.getX() - dx);
		int y = (int) (this.coordinate.getY() - dy);
		IField f = this.scope.getField(x, y);
		if (f != null) {
			this.fields[i] = f;
			this.addOne(f.getState());
			f.addPropertyChangeListener(new FieldChanged());
		}
	}

	@Override
	public abstract void init();

	protected void addOne(FieldState state) {
		int actual = 0;
		if (this.states.containsKey(state)) {
			actual = this.states.get(state);
		}
		this.states.put(state, actual + 1);

		this.changes.firePropertyChange(state.name(), actual, actual + 1);
	}

	protected void removeOne(FieldState state) {
		int actual = 0;
		int next = 0;
		if (this.states.containsKey(state)) {
			actual = this.states.get(state);
			next = actual - 1;
		}
		this.states.put(state, next);

		this.changes.firePropertyChange(state.name(), actual, next);
	}

	@Override
	public Integer getState(FieldState state) {
		if (this.states.containsKey(state)) {
			return this.states.get(state);
		} else {
			return 0;
		}
	}

	@Override
	public Point getCoordinate() {
		return coordinate;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		this.changes.addPropertyChangeListener(l);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
		this.changes.removePropertyChangeListener(l);
	}
}
