package wwe.handler.scope;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.preference.IPreferenceStore;

import wwe.Activator;

public class ZoomInHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		int width = store.getInt("block.width");
		int height = store.getInt("block.height");
		width *= 2;
		height *= 2;

		store.setValue("block.width", width);
		store.setValue("block.height", height);

		return null;
	}

}
