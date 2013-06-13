package fhv.eclipse2013.wwe.impl.field;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.FieldState;

public class FieldNeighbours {
	private class FieldChanged implements PropertyChangeListener {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals("state")) {
				FieldNeighbours.this.removeOne((FieldState) evt.getOldValue());
				FieldNeighbours.this.addOne((FieldState) evt.getNewValue());
			}
		}
	};

	protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

	private ISimulationScope scope;
	private Point coordinate;

	private HashMap<FieldState, Integer> states = new HashMap<FieldState, Integer>();

	private IField[] fields;

	private IField field;

	public FieldNeighbours(ISimulationScope scope, IField field,
			Point coordinate) {
		this.scope = scope;
		this.coordinate = coordinate;
		this.field = field;

		this.field.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0.getPropertyName().equals("state")) {
					FieldState state = (FieldState) arg0.getNewValue();
					if (!state.equals(FieldState.none)
							&& (FieldNeighbours.this.fields == null)) {
						FieldNeighbours.this.init();
					}
				}
			}
		});
	}

	private void initField(int dx, int dy, int i) {
		int x = (int) (this.coordinate.getX() - dx);
		int y = (int) (this.coordinate.getY() - dy);
		IField f = this.scope.getField(x, y);
		if (f != null) {
			this.fields[i] = f;
			this.addOne(f.getState());
			f.addPropertyChangeListener(new FieldChanged());
		}
	}

	public void init() {
		this.fields = new IField[8];
		int i = 0;
		// Above left
		this.initField(-1, -1, i++);

		// Above
		this.initField(0, -1, i++);

		// Above right
		this.initField(+1, -1, i++);

		// Left
		this.initField(-1, 0, i++);

		// Right
		this.initField(+1, 0, i++);

		// Below left
		this.initField(-1, +1, i++);

		// Below
		this.initField(0, +1, i++);

		// Below right
		this.initField(+1, +1, i++);
	}

	protected void addOne(FieldState state) {
		int actual = 0;
		if (this.states.containsKey(state)) {
			actual = this.states.get(state);
		}
		FieldNeighbours.this.states.put(state, actual + 1);

		this.changes.firePropertyChange(state.name(), actual, actual + 1);
	}

	protected void removeOne(FieldState state) {
		int actual = 0;
		int next = 0;
		if (this.states.containsKey(state)) {
			actual = this.states.get(state);
			next = actual - 1;
		}
		FieldNeighbours.this.states.put(state, next);

		this.changes.firePropertyChange(state.name(), actual, next);
	}

	public Integer getState(FieldState state) {
		if (this.states.containsKey(state)) {
			return this.states.get(state);
		} else {
			return 0;
		}
	}

	public Point getCoordinate() {
		return coordinate;
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		this.changes.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		this.changes.removePropertyChangeListener(l);
	}
}
