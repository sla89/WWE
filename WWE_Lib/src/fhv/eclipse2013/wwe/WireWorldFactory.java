package fhv.eclipse2013.wwe;

import java.awt.Point;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.ISimulationFactory;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.impl.field.WireWorldField;
import fhv.eclipse2013.wwe.impl.scope.SimulationScope;

public class WireWorldFactory implements ISimulationFactory {

	public WireWorldFactory() {

	}

	@Override
	public ISimulationScope createScope(int width, int height, String name) {
		return new SimulationScope(width, height, name, this);
	}

	@Override
	public ISimulationScope loadScope(String filename) {
		return SimulationScope.load(filename, this);
	}

	@Override
	public IField createField(ISimulationScope scope, Point coord) {
		return new WireWorldField(scope, coord);
	}

}
