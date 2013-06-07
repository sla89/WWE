package wwe.util;

import fhv.eclipse2013.wwe.contract.SimulationState;
import fhv.eclipse2013.wwe.impl.scope.SimulationScope;

public class SimulationScopeHandler {
	private static final int WORLD_SIZE = 100;
	private static SimulationScopeHandler INSTANCE;

	private SimulationScope currentScope;

	private SimulationScopeHandler() {
		currentScope = new SimulationScope(WORLD_SIZE, WORLD_SIZE);
	}

	public static SimulationScopeHandler getInstance() {
		if (INSTANCE == null)
			INSTANCE = new SimulationScopeHandler();

		return INSTANCE;
	}

	public SimulationState getCurrentState() {
		return currentScope.getSimulationState();
	}

	public SimulationScope getCurrentScope() {
		return currentScope;
	}
	
	public void setCurrentScope(SimulationScope scope) {
		this.currentScope=scope;
	}
}
