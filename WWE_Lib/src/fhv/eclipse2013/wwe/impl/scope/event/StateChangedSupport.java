package fhv.eclipse2013.wwe.impl.scope.event;

import java.util.ArrayList;
import java.util.List;

import fhv.eclipse2013.wwe.contract.scope.IStateChangedEventListener;
import fhv.eclipse2013.wwe.contract.state.SimulationState;

public class StateChangedSupport {

	private List<IStateChangedEventListener> stateChangedListener = new ArrayList<>();

	public void fireStateChanged(SimulationState state, boolean lock) {
		for (IStateChangedEventListener event : this.stateChangedListener) {
			event.handleStateChanged(state, lock);
		}
	}
	
	public void addStateChangedListener(IStateChangedEventListener l) {
		this.stateChangedListener.add(l);
	}

	public void removeStateChangedListener(IStateChangedEventListener l) {
		this.stateChangedListener.remove(l);
	}
}
