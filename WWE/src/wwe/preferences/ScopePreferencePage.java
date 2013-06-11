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

	public static final String ID = "WWE.preferences.scope";

	public ScopePreferencePage(String title) {
		super(title, GRID);
		setTitle("Scope");

		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Scope Preferences");
	}

	@Override
	protected void createFieldEditors() {
		addField(new ColorFieldEditor("color.none", "&None Field",
				getFieldEditorParent()));

		addField(new ColorFieldEditor("color.conductor", "&Conductor Field",
				getFieldEditorParent()));

		addField(new ColorFieldEditor("color.head", "&Head Field",
				getFieldEditorParent()));

		addField(new ColorFieldEditor("color.tail", "&Tail Field",
				getFieldEditorParent()));

		addField(new BooleanFieldEditor("showCoordinate", "&Show Coordinate",
				getFieldEditorParent()));

		addField(new IntegerFieldEditor("block.width", "Block &width",
				getFieldEditorParent()));

		addField(new IntegerFieldEditor("block.height", "Block hei&ght",
				getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
	}
}
