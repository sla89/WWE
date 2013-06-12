package fhv.eclipse2013.wwe.impl.scope.event;

import java.util.ArrayList;
import java.util.List;

import fhv.eclipse2013.wwe.contract.scope.IStepChangedEventListener;
import fhv.eclipse2013.wwe.contract.scope.IStepChangedEventListener.Type;

public class StepChangedSupport {

	private List<IStepChangedEventListener> stepEventListener = new ArrayList<>();

	public void fireStepChanged(Type t) {
		for (IStepChangedEventListener event : this.stepEventListener) {
			if (t.equals(Type.prepare)) {
				event.handlePrepareNextState();
			} else if (t.equals(Type.next)) {
				event.handleNextStep();
			} else if (t.equals(Type.back)) {
				event.handleBackStep();
			} else if (t.equals(Type.reset)) {
				event.handleReset();
			}
		}
	}

	public void removeAll() {
		this.stepEventListener.clear();
	}

	public void addStepListener(IStepChangedEventListener l) {
		this.stepEventListener.add(l);
	}

	public void removeStepListener(IStepChangedEventListener l) {
		this.stepEventListener.remove(l);
	}
}
