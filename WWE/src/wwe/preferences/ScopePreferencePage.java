package wwe.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import wwe.Activator;

public class ScopePreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public static final String ID = "WWE.preferences.scope"; //$NON-NLS-1$

	public ScopePreferencePage(String title) {
		super(title, GRID);
		setTitle(Messages.ScopePreferencePage_1);

		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.ScopePreferencePage_2);
	}

	@Override
	protected void createFieldEditors() {
		addField(new ColorFieldEditor("color.none", Messages.ScopePreferencePage_0, //$NON-NLS-1$
				getFieldEditorParent()));

		addField(new ColorFieldEditor("color.conductor", Messages.ScopePreferencePage_6, //$NON-NLS-1$
				getFieldEditorParent()));

		addField(new ColorFieldEditor("color.head", Messages.ScopePreferencePage_8, //$NON-NLS-1$
				getFieldEditorParent()));

		addField(new ColorFieldEditor("color.tail", Messages.ScopePreferencePage_10, //$NON-NLS-1$
				getFieldEditorParent()));

		addField(new BooleanFieldEditor("showCoordinate", Messages.ScopePreferencePage_12, //$NON-NLS-1$
				getFieldEditorParent()));

		addField(new IntegerFieldEditor("block.width", Messages.ScopePreferencePage_3, //$NON-NLS-1$
				getFieldEditorParent()));

		addField(new IntegerFieldEditor("block.height", Messages.ScopePreferencePage_16, //$NON-NLS-1$
				getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
	}
}
