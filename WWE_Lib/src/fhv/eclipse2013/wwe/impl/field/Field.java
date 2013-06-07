package fhv.eclipse2013.wwe.impl.field;

import org.jdom2.Attribute;
import org.jdom2.Element;

import fhv.eclipse2013.wwe.contract.FieldState;
import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.impl.scope.Coordinate;
import fhv.eclipse2013.wwe.impl.scope.SimulationScope;

public class Field extends FieldBase implements IField {

	public Field(SimulationScope scope, Coordinate c) {
		super(scope);
		this.setNeighbours(new FieldNeighbours(scope, this, c));
	}

	public Field(SimulationScope scope, FieldState state, Coordinate c) {
		super(scope);
		this.setState(state);
		this.setNeighbours(new FieldNeighbours(scope, this, c));
	}

	@Override
	public FieldState getNextState(FieldState state) {
		int heads = this.getNeighbours().getState(FieldState.head);
		if (state.equals(FieldState.head)) {
			return FieldState.tail;
		} else if (state.equals(FieldState.tail)) {
			return FieldState.conductor;
		} else if (state.equals(FieldState.conductor)
				&& ((heads == 1) || (heads == 2))) {
			return FieldState.head;
		}
		return state;
	}

	@Override
	protected FieldState onClick(FieldState state) {
		if (state.equals(FieldState.none)) {
			return FieldState.conductor;
		} else if (state.equals(FieldState.conductor)) {
			return FieldState.head;
		} else if (state.equals(FieldState.head)) {
			return FieldState.tail;
		} else {
			return FieldState.none;
		}
	}

	@Override
	public Element getElement(Element e) {
		e.setAttribute(new Attribute("state", this.getOriginal().name()));
		return e;
	}
}
