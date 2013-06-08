package wwe.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import wwe.util.SimulationScopeHandler;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.SimulationState;

public class PauseHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		int index = SimulationScopeHandler.INSTANCE.getCurrentIndex();
		ISimulationScope scope = SimulationScopeHandler.INSTANCE
				.getScope(index);
		// will make the scope pause
		scope.start();
		return null;
	}

	@Override
	public boolean isEnabled() {
		int index = SimulationScopeHandler.INSTANCE.getCurrentIndex();
		// enable during running state
		if (SimulationScopeHandler.INSTANCE.getState(index) == SimulationState.started)
			return true;
		else
			return false;
	}
}
