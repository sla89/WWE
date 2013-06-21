package fhv.eclipse2013.wwe.impl.field;

import java.awt.Point;

import org.jdom2.Attribute;
import org.jdom2.Element;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.FieldState;

public class WireWorldField extends FieldBase implements IField {

	public WireWorldField(ISimulationScope scope, Point c) {
		super(scope);
		this.setNeighbours(new MooreNeighbours(scope, this, c));
	}

	public WireWorldField(ISimulationScope scope, FieldState state, Point c) {
		this(scope, c);
		this.setState(state);
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
