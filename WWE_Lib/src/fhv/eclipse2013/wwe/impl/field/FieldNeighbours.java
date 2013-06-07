package fhv.eclipse2013.wwe.impl.field;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

import fhv.eclipse2013.wwe.contract.FieldState;
import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.SimulationState;
import fhv.eclipse2013.wwe.impl.scope.Coordinate;
import fhv.eclipse2013.wwe.impl.scope.SimulationScope;

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

	private SimulationScope scope;
	private Coordinate coordinate;

	private HashMap<FieldState, Integer> states = new HashMap<FieldState, Integer>();

	private IField[] fields = new IField[8];

	public FieldNeighbours(SimulationScope scope, Coordinate coordinate) {
		this.scope = scope;
		this.coordinate = coordinate;

		scope.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("simulationState")
						&& evt.getNewValue().equals(SimulationState.init)) {
					FieldNeighbours.this.init();
				}
			}
		});
	}

	private void initField(int dx, int dy, int i) {
		int x = this.coordinate.getX() - dx;
		int y = this.coordinate.getY() - dy;
		IField f = this.scope.getField(x, y);
		if (f != null) {
			this.fields[i] = f;
			this.addOne(f.getState());
			f.addPropertyChangeListener(new FieldChanged());
		}
	}

	private void init() {
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

	public void addPropertyChangeListener(PropertyChangeListener l) {
		this.changes.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		this.changes.removePropertyChangeListener(l);
	}
}
