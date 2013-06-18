package wwe.preferences;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import wwe.Activator;

public class WireWorldPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	
	public static final String ID = "WWE.preferences.scope";

	public WireWorldPreferencePage(String title) {
		super(title, GRID);
		setTitle("WireWorld");

		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("WireWorld description");
	}
	
	@Override
	protected void createFieldEditors() {
		addField(new ColorFieldEditor(
				"color.none", Messages.ScopePreferencePage_0, //$NON-NLS-1$
				getFieldEditorParent()));

		addField(new ColorFieldEditor(
				"color.conductor", Messages.ScopePreferencePage_6, //$NON-NLS-1$
				getFieldEditorParent()));

		addField(new ColorFieldEditor(
				"color.head", Messages.ScopePreferencePage_8, //$NON-NLS-1$
				getFieldEditorParent()));

		addField(new ColorFieldEditor(
				"color.tail", Messages.ScopePreferencePage_10, //$NON-NLS-1$
				getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
	}

}
