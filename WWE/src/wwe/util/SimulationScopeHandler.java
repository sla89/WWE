package wwe.util;

import fhv.eclipse2013.wwe.WireWorldFactory;
import fhv.eclipse2013.wwe.contract.ISimulationFactory;
import fhv.eclipse2013.wwe.contract.SimulationState;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

public class SimulationScopeHandler {
	private static final int WORLD_SIZE = 100;
	private static SimulationScopeHandler INSTANCE;

	private ISimulationScope currentScope;

	private ISimulationFactory factory = new WireWorldFactory();

	private SimulationScopeHandler() {
		currentScope = factory.createScope(WORLD_SIZE, WORLD_SIZE, "CREATED");
	}

	public static SimulationScopeHandler getInstance() {
		if (INSTANCE == null)
			INSTANCE = new SimulationScopeHandler();

		return INSTANCE;
	}

	public SimulationState getCurrentState() {
		return currentScope.getSimulationState();
	}

	public ISimulationScope getCurrentScope() {
		return currentScope;
	}

	public void setCurrentScope(ISimulationScope scope) {
		this.currentScope = scope;
	}

	public ISimulationScope loadScope(String filename) {
		return factory.loadScope(filename);
	}
}
