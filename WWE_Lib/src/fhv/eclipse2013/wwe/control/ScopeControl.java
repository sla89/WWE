package fhv.eclipse2013.wwe.control;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import fhv.eclipse2013.wwe.impl.scope.SimulationScope;

public class ScopeControl extends JPanel {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private SimulationScope scope;

	private JPanel fieldPanel;

	public ScopeControl(SimulationScope scope) {
		this.scope = scope;
		this.setLayout(new BorderLayout());

		this.fieldPanel = new JPanel();
		this.fieldPanel.setLayout(new GridLayout(scope.getWidth(), scope
				.getHeight()));

		for (int x = 0; x < scope.getWidth(); x++) {
			for (int y = 0; y < scope.getWidth(); y++) {
				this.fieldPanel.add(new FieldControl(x, y, this.scope));
			}
		}

		this.add(this.fieldPanel, BorderLayout.CENTER);
	}
}
