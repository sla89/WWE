package fhv.eclipse2013.wwe.contract.factory;

import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

public interface ISimulationFactory {
	ISimulationScope createScope(int width, int height, String name);

	ISimulationScope loadScope(String filename);
}
