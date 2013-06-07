package wwe.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import wwe.util.SimulationScopeHandler;
import fhv.eclipse2013.wwe.contract.SimulationState;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

public class NextStepHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISimulationScope scope = SimulationScopeHandler.getInstance()
				.getCurrentScope();
		scope.nextStep();
		return null;
	}

	@Override
	public boolean isEnabled() {
		// just enable during pause state
		if (SimulationScopeHandler.getInstance().getCurrentState() == SimulationState.paused)
			return true;
		else
			return false;
	}
}
