package wwe;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import wwe.util.SimulationScopeHandler;
import fhv.eclipse2013.wwe.control.ScopeControl;

public class WireworldView extends ViewPart {

	public static final String ID = "WWE.wireworldView";

	public WireworldView() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		ScopeControl control = new ScopeControl(SimulationScopeHandler
				.getInstance().getCurrentScope());

		// Frame f = SWT_AWT.new_Frame(parent);
		// f.add(control);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
	}
}
