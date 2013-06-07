package fhv.eclipse2013.wwe.contract;

import java.awt.Point;

import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

public interface ISimulationFactory {
	ISimulationScope createScope(int width, int height, String name);

	ISimulationScope loadScope(String filename);
	
	IField createField(ISimulationScope scope, Point coord);
	
}
