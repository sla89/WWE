package wwe.handler;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import wwe.scope.ScopeEditor;
import wwe.util.EditorHandler;

public class SaveHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell s = new Shell();
		FileDialog fileDialog = new FileDialog(s, SWT.SAVE);
		fileDialog.setText("Save");
		fileDialog.setFilterPath("C:/");
		String[] filterExt = { "*.xml", };
		fileDialog.setFilterExtensions(filterExt);

		String fileName = fileDialog.open();
		if (fileName != null) {
			try {
				ScopeEditor editor = EditorHandler.getCurrentEditor(event);
				if (editor != null) {
					editor.getScope().save(fileName);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
