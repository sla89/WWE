package wwe.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import wwe.Activator;

public class ScopeInitializer extends AbstractPreferenceInitializer {

	public ScopeInitializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		store.setDefault("showCoordinate", false);

		Color none = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
		PreferenceConverter.setDefault(store, "color.none", none.getRGB());

		Color conductor = Display.getDefault().getSystemColor(SWT.COLOR_YELLOW);
		PreferenceConverter.setDefault(store, "color.conductor",
				conductor.getRGB());

		Color head = Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
		PreferenceConverter.setDefault(store, "color.head", head.getRGB());

		Color tail = Display.getDefault().getSystemColor(SWT.COLOR_RED);
		PreferenceConverter.setDefault(store, "color.tail", tail.getRGB());

		store.setDefault("block.width", 20);
		store.setDefault("block.height", 20);
	}

}
