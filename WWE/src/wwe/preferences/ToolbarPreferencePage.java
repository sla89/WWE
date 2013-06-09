package wwe.preferences;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import wwe.Activator;

public class ToolbarPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public static final String ID = "WWE.preferences.toolbar";

	public ToolbarPreferencePage(String title) {
		super(title, GRID);
		setTitle("Toolbar");

		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Toolbar Preferences");
	}

	@Override
	protected void createFieldEditors() {
		addField(new DirectoryFieldEditor("PATH", "&Directory preference:",
				getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}
}
