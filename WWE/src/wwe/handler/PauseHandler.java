package wwe.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import wwe.util.SimulationScopeHandler;
import fhv.eclipse2013.wwe.contract.SimulationState;

public class PauseHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("pause");
		return null;
	}

	@Override
	public boolean isEnabled() {
		// enable during running state
		if (SimulationScopeHandler.getInstance().getCurrentState() == SimulationState.started)
			return true;
		else
			return false;
	}
}
