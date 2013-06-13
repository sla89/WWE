package wwe.handler.file;

import java.io.IOException;
import java.rmi.NotBoundException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import fhv.eclipse2013.wwe.ImageCreator;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

import wwe.Activator;
import wwe.ToolbarView;
import wwe.util.EditorHandler;

public class SaveAsToolHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			ISimulationScope scope = EditorHandler.getCurrentEditor()
					.getScope();

			String folder = Activator.getDefault().getPreferenceStore()
					.getString("PATH"); //$NON-NLS-1$
			String xmlFileName = getXmlFileName(scope.getName());
			String pngFileName = getPngFileName(scope.getName());

			try {
				ImageCreator.createImage(folder, pngFileName, scope, true);
			} catch (IOException e1) {
				pngFileName = ""; //$NON-NLS-1$
				e1.printStackTrace();
			}
			try {
				scope.saveAsToolElement(xmlFileName, pngFileName);
				ToolbarView.reload();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (NotBoundException ex) {

		}

		return null;
	}

	private String getXmlFileName(String name) {
		String filename = name.replace(" ", "_") + ".xml"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		filename = filename.toLowerCase();
		filename = Activator.getDefault().getPreferenceStore()
				.getString("PATH") //$NON-NLS-1$
				+ "\\" + filename; //$NON-NLS-1$
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
