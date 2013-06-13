package wwe.handler.file;

import java.io.IOException;
import java.rmi.NotBoundException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import fhv.eclipse2013.wwe.ImageCreator;

import wwe.scope.ScopeEditor;
import wwe.util.EditorHandler;

public class SaveAsImageHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell s = new Shell();
		FileDialog fileDialog = new FileDialog(s, SWT.SAVE);
		fileDialog.setText(Messages.SaveAsImageHandler_0);
		fileDialog.setFilterPath("C:/"); //$NON-NLS-1$
		String[] filterExt = { "*.png", }; //$NON-NLS-1$
		fileDialog.setFilterExtensions(filterExt);

		String fileName = fileDialog.open();
		if (fileName != null) {
			ScopeEditor editor = EditorHandler.getCurrentEditor(event);
			try {
				ImageCreator.createImage(fileName, editor.getScope(), 20);
				Runtime.getRuntime().exec(
						new String[] { "cmd.exe", "/C", fileName }); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		try {
			EditorHandler.getCurrentEditor();
			return true;
		} catch (NotBoundException ex) {
			return false;
		}
	}

}
