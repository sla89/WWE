package wwe.handler.scope;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.preference.IPreferenceStore;

import wwe.Activator;

public class ZoomOutHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		int width = store.getInt("block.width");
		int height = store.getInt("block.height");

		if (height / 2 >= 5) {
			height = height / 2;
		}
		if (width / 2 >= 5) {
			width = width / 2;
		}

		store.setValue("block.width", width);
		store.setValue("block.height", height);

		return null;
	}

}
