package wwe.handler;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import wwe.util.SimulationScopeHandler;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

public class SaveHandler extends AbstractHandler implements IHandler {

	Shell s;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		int index = SimulationScopeHandler.INSTANCE.getCurrentIndex();
		
		ISimulationScope scope = SimulationScopeHandler.INSTANCE
				.getScope(index);
		s = new Shell();
		FileDialog fileDialog = new FileDialog(s, SWT.SAVE);
		fileDialog.setText("Save");
		fileDialog.setFilterPath("C:/");
		String[] filterExt = { "*.xml", };
		fileDialog.setFilterExtensions(filterExt);

		String fileName = fileDialog.open();
		if (fileName != null) {
			try {
				scope.save(fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
