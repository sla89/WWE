package wwe.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import wwe.util.SimulationScopeHandler;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

public class LoadHandler extends AbstractHandler implements IHandler {

	Shell s;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISimulationScope scope = SimulationScopeHandler.getInstance()
				.getCurrentScope();
		s = new Shell();
		FileDialog fileDialog = new FileDialog(s, SWT.OPEN);
		fileDialog.setText("Load");
		fileDialog.setFilterPath("C:/");
		String[] filterExt = { "*.xml", };
		fileDialog.setFilterExtensions(filterExt);

		String fileName = fileDialog.open();
		if (fileName != null) {
			ISimulationScope sTemp = SimulationScopeHandler.getInstance().loadScope(fileName);
			SimulationScopeHandler.getInstance().setCurrentScope(sTemp);
		}
		return null;
	}

}
