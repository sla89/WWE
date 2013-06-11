package fhv.eclipse2013.wwe.impl.scope;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;
import java.util.TimerTask;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.ISimulationFactory;
import fhv.eclipse2013.wwe.contract.scope.IStepChangedEventListener.Type;
import fhv.eclipse2013.wwe.contract.state.SimulationState;

public abstract class AbstractScope extends AbstractScopeEvents {
	private class Task extends TimerTask {
		@Override
		public void run() {
			onNextStep();
		}
	}

	private ScopeTimer timer = new ScopeTimer();
	private ISimulationFactory factory;

	private Dimension size;
	private String name;

	@Override
	public Dimension getSize() {
		return size;
	}

	@Override
	public int getWidth() {
		return (int) this.getSize().getWidth();
	}

	@Override
	public int getHeight() {
		return (int) this.getSize().getHeight();
	}

	protected void setSize(Dimension size) {
		Dimension old = this.size;
		onPropertyChanged("size", old, this.size = size);
		onPropertyChanged("width", (old == null) ? null : old.getWidth(),
				getWidth());
		onPropertyChanged("height", (old == null) ? null : old.getHeight(),
				getHeight());
	}

	private IField[][] fields;

	public IField[][] getFields() {
		return fields;
	}

	protected void setFields(IField[][] fields, List<IField> field_list) {
		this.fields = fields;
		for (IField item : field_list) {
			item.reinitiateNeighbours();
		}
	}

	private SimulationState simulationState = SimulationState.stopped;

	public SimulationState getSimulationState() {
		return simulationState;
	}

	protected void setSimulationState(SimulationState simulationState) {
		this.onPropertyChanged("simulationState", this.simulationState,
				this.simulationState = simulationState);
		this.onStateChanged();
	}

	public AbstractScope(int w, int h, String name, boolean init,
			ISimulationFactory factory) {
		this.setSize(new Dimension(w, h));
		this.name = name;
		this.factory = factory;
		if (init) {
			this.init();
		}
	}

	protected void init() {
		this.fields = new IField[this.getHeight()][];
	}

	@Override
	public boolean fieldExists(int x, int y) {
		if ((x >= 0) && (x < this.getWidth()) && (y >= 0)
				&& (y < this.getHeight())) {
			if (this.fields[y] == null) {
				return false;
			} else if (this.fields[y][x] != null) {
				return true;
			}
		}
		return false;
	}

	protected boolean rowExists(int y) {
		if ((y >= 0) && (y < this.getHeight())) {
			if (this.fields[y] != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public IField getField(int x, int y) {
		if ((x >= 0) && (x < this.getWidth()) && (y >= 0)
				&& (y < this.getHeight())) {
			if (this.fields[y] == null) {
				this.fields[y] = new IField[this.getWidth()];
			}
			if (this.fields[y][x] == null) {
				this.fields[y][x] = factory.createField(this, new Point(x, y));
				this.fields[y][x].setLock(this.getLock());
				this.onFieldAdded(y, x, this.fields[y][x]);
			}
			return this.fields[y][x];
		} else {
			return null;
		}
	}

	@Override
	public void setName(String name) {
		this.onPropertyChanged("name", this.name, this.name = name);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void nextStep() {
		if (this.getSimulationState().equals(SimulationState.paused)) {
			this.onNextStep();
		}
	}

	private void onNextStep() {
		this.onStepChanged(Type.prepare);
		this.onStepChanged(Type.next);
	}

	@Override
	public void backStep() {
		if (this.getSimulationState().equals(SimulationState.paused)) {
			this.onStepChanged(Type.back);
		}
	}

	@Override
	public void click(int x, int y) {
		this.getField(x, y).click();
	}

	@Override
	public void start() {
		if (!this.getSimulationState().equals(SimulationState.started)) {
			this.setSimulationState(SimulationState.started);
		}
		if (this.timer.startTimer(new Task())) {
			this.setSimulationState(SimulationState.paused);
		}
	}

	@Override
	public void stop() {
		if (!this.getSimulationState().equals(SimulationState.stopped)) {
			this.timer.stopTimer();
			this.setSimulationState(SimulationState.stopped);
			this.onStepChanged(Type.reset);
		}
	}

}
