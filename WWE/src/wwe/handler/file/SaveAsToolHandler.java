package wwe.handler.file;

import java.io.IOException;

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
		ISimulationScope scope = EditorHandler.getCurrentEditor().getScope();

		String folder = Activator.getDefault().getPreferenceStore()
				.getString("PATH");
		String xmlFileName = getXmlFileName(scope.getName());
		String pngFileName = getPngFileName(scope.getName());

		try {
			ImageCreator.createImage(folder, pngFileName, scope, true);
		} catch (IOException e1) {
			pngFileName = "";
			e1.printStackTrace();
		}
		try {
			scope.saveAsToolElement(xmlFileName, pngFileName);
			ToolbarView.reload();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String getXmlFileName(String name) {
		String filename = name.replace(" ", "_") + ".xml";
		filename = filename.toLowerCase();
		filename = Activator.getDefault().getPreferenceStore()
				.getString("PATH")
				+ "\\" + filename;
		return filename;
	}

	private String getPngFileName(String name) {
		String filename = name.replace(" ", "_") + ".png";
		filename = filename.toLowerCase();
		return filename;
	}

}
