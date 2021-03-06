package wwe.handler.file;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

import wwe.util.EditorHandler;
import wwe.util.SimulationScopeHandler;

public class LoadHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	Shell	s = new Shell();
		FileDialog fileDialog = new FileDialog(s, SWT.OPEN);
		fileDialog.setText(Messages.LoadHandler_0);
		fileDialog.setFilterPath("C:/"); //$NON-NLS-1$
		String[] filterExt = { "*.xml", }; //$NON-NLS-1$
		fileDialog.setFilterExtensions(filterExt);

		String fileName = fileDialog.open();
		if (fileName != null) {
			ISimulationScope scope = SimulationScopeHandler.INSTANCE.getFactory()
					.loadScope(fileName);
			EditorHandler.newEditor(event, scope);

		}
		return null;
	}
}
