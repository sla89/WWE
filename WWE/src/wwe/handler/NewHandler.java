package wwe.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import wwe.control.CreateScopeDialog;
import wwe.util.EditorHandler;
import wwe.util.SimulationScopeHandler;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

public class NewHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell s = new Shell();
		CreateScopeDialog dialog = new CreateScopeDialog(s);
		dialog.setTitle("Which operating system are you using");
		// User pressed cancel
		if (dialog.open() != Window.OK) {
			return false;
		}

		ISimulationScope scope = SimulationScopeHandler.INSTANCE.getFactory()
				.createScope(dialog.getWidth(), dialog.getHeight(),
						dialog.getName());

		EditorHandler.newEditor(event, scope);
		return null;
	}
}
