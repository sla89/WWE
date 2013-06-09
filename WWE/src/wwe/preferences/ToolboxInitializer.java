package wwe.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import wwe.Activator;

public class ToolboxInitializer extends AbstractPreferenceInitializer {

	public ToolboxInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault("PATH", "C:\\Users\\Administrator\\git\\WWE\\WWE\\toolbar");
	}
}
