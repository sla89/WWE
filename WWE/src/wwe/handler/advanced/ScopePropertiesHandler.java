package wwe.handler.advanced;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.NotBoundException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.EditorReference;
import org.eclipse.ui.part.WorkbenchPart;

import wwe.dialog.CreateScopeDialog;
import wwe.scope.ScopeEditor;
import wwe.util.EditorHandler;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.SimulationState;

public class ScopePropertiesHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			ScopeEditor editor = EditorHandler.getCurrentEditor();
			IWorkbenchPart part = (IWorkbenchPart) editor;
			ISimulationScope scope = editor.getScope();

			Shell s = new Shell();
			CreateScopeDialog dialog = new CreateScopeDialog(s, Messages.ScopePropertiesHandler_0,
					scope.getWidth(), scope.getHeight(), scope.getName());
			// User pressed cancel
			if (dialog.open() != Window.OK) {
				return false;
			}

			scope.setName(dialog.getName());
			try {
				scope.setSize(dialog.getWidth(), dialog.getHeight());
			} catch (Exception ex) {
				MessageDialog dlg = new MessageDialog(new Shell(),
						Messages.ScopePropertiesHandler_1, null, Messages.ScopePropertiesHandler_2,
						MessageDialog.ERROR, new String[] { Messages.ScopePropertiesHandler_3, Messages.ScopePropertiesHandler_4 }, 0);
				if (dlg.open() == 0) {
					try {
						scope.setSize(dialog.getWidth(), dialog.getHeight(),
								false);
					} catch (Exception e) {
					}
				}
			}
			editor.getCanvas().pack();
			Method method = WorkbenchPart.class.getDeclaredMethod(
					Messages.ScopePropertiesHandler_5, String.class);
			method.setAccessible(true);
			method.invoke(part, scope.getName());
		} catch (NotBoundException ex) {

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		try {
			ScopeEditor editor = EditorHandler.getCurrentEditor();
			ISimulationScope scope = editor.getScope();
			return scope.getSimulationState().equals(SimulationState.stopped);
		} catch (NotBoundException ex) {
			return false;
		}
	}
}
