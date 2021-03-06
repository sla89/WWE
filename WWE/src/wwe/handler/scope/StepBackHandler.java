package wwe.handler.scope;

import java.rmi.NotBoundException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import fhv.eclipse2013.wwe.contract.state.SimulationState;

import wwe.scope.ScopeEditor;
import wwe.util.EditorHandler;

public class StepBackHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ScopeEditor editor = EditorHandler.getCurrentEditor(event);
		if (editor != null) {
			editor.getScope().backStep();
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		try {
			ScopeEditor editor = EditorHandler.getCurrentEditor();
			if (editor != null) {
				return editor.getScope().getSimulationState()
						.equals(SimulationState.paused);
			}
		} catch (NotBoundException ex) {
		}
		return false;
	}
}
