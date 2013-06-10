package wwe.handler.scope;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import fhv.eclipse2013.wwe.contract.state.SimulationState;

import wwe.scope.ScopeEditor;
import wwe.util.EditorHandler;

public class PlayHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ScopeEditor editor = EditorHandler.getCurrentEditor(event);
		if (editor != null) {
			editor.getScope().start();
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		ScopeEditor editor = EditorHandler.getCurrentEditor();
		if (editor != null) {
			return !editor.getScope().getSimulationState()
					.equals(SimulationState.started);
		}
		return false;
	}
}
