package wwe.preferences;

import java.net.URL;
import java.util.Properties;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import wwe.Activator;

public class ToolboxInitializer extends AbstractPreferenceInitializer {

	public ToolboxInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		//store.setDefault("PATH", "C:\\Users\\Administrator\\git\\WWE\\WWE\\toolbar");
		String classPath = System.getProperty("user.dir");
		
		URL url=this.getClass().getProtectionDomain().getCodeSource().getLocation();
		String s=url.getPath();
		store.setDefault("PATH", s+"/toolbar");
	}
}
