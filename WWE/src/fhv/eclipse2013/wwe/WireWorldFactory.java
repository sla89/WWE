package fhv.eclipse2013.wwe;

import java.awt.Point;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.IFieldNeighbours;
import fhv.eclipse2013.wwe.contract.factory.IFieldFactory;
import fhv.eclipse2013.wwe.contract.factory.ISimulationFactory;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.FieldState;
import fhv.eclipse2013.wwe.impl.field.MooreNeighbours;
import fhv.eclipse2013.wwe.impl.field.WireWorldField;
import fhv.eclipse2013.wwe.impl.scope.SimulationScope;

public class WireWorldFactory implements ISimulationFactory {

	private class WireWorldFieldFactory implements IFieldFactory {

		@Override
		public IField createField(ISimulationScope scope, Point coord) {
			return new WireWorldField(scope, coord, this);
		}

		@Override
		public IField createField(ISimulationScope scope, FieldState state,
				Point coord) {
			return new WireWorldField(scope, state, coord, this);
		}

		@Override
		public IFieldNeighbours createFieldNeighbour(ISimulationScope scope,
				IField field, Point c) {
			return new MooreNeighbours(scope, field, c);
		}

	}

	public WireWorldFactory() {

	}

	@Override
	public ISimulationScope createScope(int width, int height, String name) {
		return new SimulationScope(width, height, name,
				new WireWorldFieldFactory());
	}

	@Override
	public ISimulationScope loadScope(String filename) {
		return SimulationScope.load(filename, new WireWorldFieldFactory());
	}

}
