package wwe.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import wwe.control.CreateScopeDialog;
import wwe.util.SimulationScopeHandler;

public class NewHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		int index = SimulationScopeHandler.INSTANCE.getCurrentIndex();
		// TODO perhaps new Tab

		Shell s = new Shell();
		CreateScopeDialog dialog = new CreateScopeDialog(s);
		dialog.setTitle("Which operating system are you using");
		// User pressed cancel
		if (dialog.open() != Window.OK) {
			return false;
		}
		SimulationScopeHandler.INSTANCE.createNew(index, dialog.getWidth(),
				dialog.getHeight(), dialog.getName());
		return null;
	}
}
