package fhv.eclipse2013.wwe.contract.scope;

import fhv.eclipse2013.wwe.contract.state.SimulationState;

public interface IStateChangedEventListener {
	void handleStateChanged(SimulationState state, boolean lock);
}
