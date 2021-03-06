package wwe.util;

import java.rmi.NotBoundException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.Workbench;

import wwe.scope.ScopeEditor;
import wwe.scope.ScopeEditorInput;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

@SuppressWarnings("restriction")
public class EditorHandler {
	public static void newEditor(ExecutionEvent event, ISimulationScope scope) {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		ScopeEditorInput input = new ScopeEditorInput(scope);
		try {
			page.openEditor(input, ScopeEditor.ID);

		} catch (PartInitException e) {
			throw new RuntimeException(e);
		}
	}

	public static ScopeEditor getCurrentEditor(ExecutionEvent event) {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		IEditorPart editor = page.getActiveEditor();
		if (editor instanceof ScopeEditor) {
			return (ScopeEditor) editor;
		} else {
			return null;
		}
	}

	public static ScopeEditor getCurrentEditor() throws NotBoundException {
		IEditorPart editor = Workbench.getInstance().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor();
		if (editor != null) {
			if (editor instanceof ScopeEditor) {
				return (ScopeEditor) editor;
			} else {
				return null;
			}
		} else {
			throw new NotBoundException(Messages.EditorHandler_0);
		}
	}

	public static void killAll() {
		try {

			IEditorReference[] editors = Workbench.getInstance()
					.getActiveWorkbenchWindow().getActivePage()
					.getEditorReferences();

			if (editors != null) {
				for (int i = 0; i < editors.length; i++) {
					IEditorPart editor = editors[i].getEditor(false);

					if (editor != null && editor instanceof ScopeEditor) {
						ScopeEditor scopeEditor = (ScopeEditor) editors[i];
						scopeEditor.getScope().stop();
					}
				}
			}
		} catch (Exception e) {

		}

	}
}
