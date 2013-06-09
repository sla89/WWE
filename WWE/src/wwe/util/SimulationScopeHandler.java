package wwe.util;

import java.util.Observable;

import fhv.eclipse2013.wwe.WireWorldFactory;
import fhv.eclipse2013.wwe.contract.ISimulationFactory;

public class SimulationScopeHandler extends Observable {
	public static final int WORLD_SIZE = 100;
	public final static SimulationScopeHandler INSTANCE = new SimulationScopeHandler();

	private ISimulationFactory factory = new WireWorldFactory();

	private SimulationScopeHandler() {
	}
	
	public ISimulationFactory getFactory() {
		return factory;
	}
}
