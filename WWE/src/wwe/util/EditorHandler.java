package wwe.util;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.Workbench;

import wwe.scope.ScopeEditor;
import wwe.scope.ScopeEditorInput;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

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

	public static ScopeEditor getCurrentEditor() {
		IEditorPart editor = Workbench.getInstance().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor();
		if (editor instanceof ScopeEditor) {
			return (ScopeEditor) editor;
		} else {
			return null;
		}
	}
}
