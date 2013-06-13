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

		store.setDefault("showCoordinate", false); //$NON-NLS-1$

		Color none = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
		PreferenceConverter.setDefault(store, "color.none", none.getRGB()); //$NON-NLS-1$

		Color conductor = Display.getDefault().getSystemColor(SWT.COLOR_YELLOW);
		PreferenceConverter.setDefault(store, "color.conductor", //$NON-NLS-1$
				conductor.getRGB());

		Color head = Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
		PreferenceConverter.setDefault(store, "color.head", head.getRGB()); //$NON-NLS-1$

		Color tail = Display.getDefault().getSystemColor(SWT.COLOR_RED);
		PreferenceConverter.setDefault(store, "color.tail", tail.getRGB()); //$NON-NLS-1$

		store.setDefault("block.width", 20); //$NON-NLS-1$
		store.setDefault("block.height", 20); //$NON-NLS-1$
	}

}
