package wwe;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import wwe.util.SimulationScopeHandler;
import fhv.eclipse2013.wwe.control.ScopeControl;

public class WireworldView extends ViewPart {

	public static final String ID = "WWE.wireworldView";

	public WireworldView() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
		java.awt.Frame frame = SWT_AWT.new_Frame(swtAwtComponent);
		javax.swing.JPanel panel = new ScopeControl(SimulationScopeHandler
				.getInstance().getCurrentScope());
		frame.add(panel);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
	}
}
