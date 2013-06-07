package wwe.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import wwe.util.SimulationScopeHandler;
import fhv.eclipse2013.wwe.contract.SimulationState;
import fhv.eclipse2013.wwe.impl.scope.SimulationScope;

public class PlayHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SimulationScope scope = SimulationScopeHandler.getInstance()
				.getCurrentScope();
		scope.start();
		return null;
	}

	@Override
	public boolean isEnabled() {
		// disable while running
		if (SimulationScopeHandler.getInstance().getCurrentState() != SimulationState.started)
			return true;
		else
			return false;
	}
}
