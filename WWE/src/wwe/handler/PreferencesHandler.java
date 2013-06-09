package wwe.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.widgets.Shell;

import wwe.preferences.ToolbarPreferencePage;

public class PreferencesHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IPreferencePage page = new ToolbarPreferencePage("Toolbar");
		PreferenceManager mgr = new PreferenceManager();
		IPreferenceNode node = new PreferenceNode(ToolbarPreferencePage.ID,
				page);
		mgr.addToRoot(node);
		PreferenceDialog dialog = new PreferenceDialog(new Shell(), mgr);
		dialog.create();
		dialog.setMessage(page.getTitle());
		dialog.open();
		return null;
	}

}
