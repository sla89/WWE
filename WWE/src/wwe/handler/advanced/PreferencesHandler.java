package wwe.handler.advanced;

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

import wwe.preferences.ScopePreferencePage;
import wwe.preferences.ToolbarPreferencePage;
import wwe.preferences.WireWorldPreferencePage;

public class PreferencesHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IPreferencePage page = new ToolbarPreferencePage(Messages.PreferencesHandler_0);
		IPreferencePage scope = new ScopePreferencePage(Messages.PreferencesHandler_1);
		IPreferencePage wireworld = new WireWorldPreferencePage("WireWorld");
		
		PreferenceManager mgr = new PreferenceManager();
		IPreferenceNode node1 = new PreferenceNode(ToolbarPreferencePage.ID,
				page);
		IPreferenceNode node2 = new PreferenceNode(ScopePreferencePage.ID,
				scope);
		node2.add(new PreferenceNode(WireWorldPreferencePage.ID, wireworld));
		
		mgr.addToRoot(node1);
		mgr.addToRoot(node2);
		
		PreferenceDialog dialog = new PreferenceDialog(new Shell(), mgr);
		dialog.create();
		dialog.setMessage(Messages.PreferencesHandler_2);
		dialog.open();
		return null;
	}

}
