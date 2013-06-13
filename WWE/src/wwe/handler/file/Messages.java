package wwe.handler.file;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "wwe.handler.file.messages"; //$NON-NLS-1$
	public static String LoadHandler_0;
	public static String SaveAsImageHandler_0;
	public static String SaveHandler_0;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
