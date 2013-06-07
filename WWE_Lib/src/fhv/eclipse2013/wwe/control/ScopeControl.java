package fhv.eclipse2013.wwe.control;

import java.awt.GridLayout;

import javax.swing.JPanel;

import fhv.eclipse2013.wwe.impl.scope.SimulationScope;

public class ScopeControl extends JPanel {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private SimulationScope scope;

	public ScopeControl(SimulationScope scope) {
		this.scope = scope;

		this.setLayout(new GridLayout(scope.getSize().getWidth(), scope
				.getSize().getHeight()));

		for (int y = 0; y < scope.getSize().getHeight(); y++) {
			for (int x = 0; x < scope.getSize().getWidth(); x++) {
				this.add(new FieldControl(scope.getField(x, y)));
			}
		}
	}
}
