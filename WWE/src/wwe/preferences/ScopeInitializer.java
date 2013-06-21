package wwe.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import wwe.Activator;

public class ScopeInitializer extends AbstractPreferenceInitializer {

	public ScopeInitializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		store.setDefault("showCoordinate", false); //$NON-NLS-1$

		store.setDefault("block.width", 20); //$NON-NLS-1$
		store.setDefault("block.height", 20); //$NON-NLS-1$
	}

}
