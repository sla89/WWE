package wwe.handler.file;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import fhv.eclipse2013.wwe.ImageCreator;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

import wwe.Activator;
import wwe.dialog.CreateElementDialog;
import wwe.toolbar.ToolbarTreeView;
import wwe.util.EditorHandler;

public class SaveAsToolHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			ISimulationScope scope = EditorHandler.getCurrentEditor()
					.getScope();
			CreateElementDialog dialog = new CreateElementDialog(new Shell(),
					scope.getName());

			if (dialog.open() != Window.OK) {
				return false;
			}

			String folder = Activator.getDefault().getPreferenceStore()
					.getString("PATH");
			folder += "\\" + dialog.getCategory();
			new File(folder).mkdirs();
			String xmlFileName = getXmlFileName(folder, scope.getName());
			String pngFileName = getPngFileName(scope.getName());

			try {
				ImageCreator.createImage(folder, pngFileName, scope, true);
			} catch (IOException e1) {
				pngFileName = "";
				e1.printStackTrace();
			}
			try {
				scope.saveAsToolElement(xmlFileName, pngFileName);
				ToolbarTreeView.reload();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (NotBoundException ex) {

		}

		return null;
	}

	private String getXmlFileName(String folder, String name) {
		String filename = name.replace(" ", "_") + ".xml";
		filename = filename.toLowerCase();
		filename = folder + "\\" + filename;
		return filename;
	}

	private String getPngFileName(String name) {
		String filename = name.replace(" ", "_") + ".png"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		filename = filename.toLowerCase();
		return filename;
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
