package wwe.control;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import fhv.eclipse2013.wwe.control.ScopeControl;

import wwe.WireworldView;
import wwe.util.SimulationScopeHandler;

public class ObserverJPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private int index;
	private WireworldView view;

	public ObserverJPanel(int index, WireworldView view) {
		SimulationScopeHandler.INSTANCE.addObserver(this);
		this.index = index;
		this.view = view;
		init();
		this.repaint();
	}

	public void init() {
		this.add(new ScopeControl(SimulationScopeHandler.INSTANCE
				.getScope(index)));
		
		// TODO SET View Title
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if ((int) arg1 == index) {
			this.removeAll();
			init();
		}
	}

}
