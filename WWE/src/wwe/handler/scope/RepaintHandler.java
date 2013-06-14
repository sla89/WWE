package wwe.handler.scope;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import wwe.util.EditorHandler;

public class RepaintHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		EditorHandler.getCurrentEditor(event).getCanvas().redraw();
		return null;
	}

}
