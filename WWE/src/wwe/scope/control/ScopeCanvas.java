package wwe.scope.control;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

public class ScopeCanvas extends Label {

	private ISimulationScope scope;

	public ScopeCanvas(Composite parent, int style, ISimulationScope scope) {
		super(parent, style);
		this.scope = scope;

		this.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
			
			}
		});
	}

	public ISimulationScope getScope() {
		return scope;
	}
}
