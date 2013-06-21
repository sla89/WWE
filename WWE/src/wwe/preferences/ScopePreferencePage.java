package wwe.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import wwe.Activator;

public class ScopePreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public static final String ID = "WWE.preferences.scope";

	public ScopePreferencePage(String title) {
		super(title, GRID);
		setTitle(Messages.ScopePreferencePage_1);

		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.ScopePreferencePage_2);
	}

	@Override
	protected void createFieldEditors() {
		addField(new IntegerFieldEditor("block.width", Messages.ScopePreferencePage_3, //$NON-NLS-1$
				getFieldEditorParent()));

		addField(new IntegerFieldEditor("block.height", Messages.ScopePreferencePage_16, //$NON-NLS-1$
				getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
	}
}
