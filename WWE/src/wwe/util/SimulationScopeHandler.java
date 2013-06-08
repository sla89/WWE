package wwe.util;

import java.util.HashMap;
import java.util.Observable;

import fhv.eclipse2013.wwe.WireWorldFactory;
import fhv.eclipse2013.wwe.contract.ISimulationFactory;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.SimulationState;

public class SimulationScopeHandler extends Observable {
	private static final int WORLD_SIZE = 100;
	public final static SimulationScopeHandler INSTANCE = new SimulationScopeHandler();

	private HashMap<Integer, ISimulationScope> scopes = new HashMap<>();
	private int nextIndex = 1;

	private ISimulationFactory factory = new WireWorldFactory();

	private SimulationScopeHandler() {
	}

	public SimulationState getState(int index) {
		return getScope(index).getSimulationState();
	}

	public ISimulationScope getScope(int index) {
		if (scopes.containsKey(index)) {
			return scopes.get(index);
		} else {
			index = createNew("New Scope");
			return getScope(index);
		}
	}

	private void setScope(int index, ISimulationScope scope) {
		this.scopes.put(index, scope);
		this.setChanged();
		this.notifyObservers(index);
	}

	public void loadScope(String filename, int index) {
		setScope(index, factory.loadScope(filename));
	}

	public int createNew(int index, int width, int height, String name) {
		this.setScope(index, factory.createScope(width, height, name));
		return index;
	}

	public int createNew(String name) {
		int i = createNew(nextIndex, WORLD_SIZE, WORLD_SIZE, name);
		nextIndex++;
		return i;
	}
	
	public int getCurrentIndex(){
		// TODO Which Tab is open
		return 1;
	}
}
