package fhv.eclipse2013.wwe.impl.scope.event;

import java.util.ArrayList;
import java.util.List;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.scope.IFieldAddedEventListener;

public class FieldAddedSupport {

	private List<IFieldAddedEventListener> fieldAddedListener = new ArrayList<>();

	public void fireFieldAddedChanged(int x, int y, IField field) {
		for (IFieldAddedEventListener event : this.fieldAddedListener) {
			event.handleFieldAdded(x, y, field);
		}
	}

	public void fireFieldDeletedChanged(int x, int y, IField field) {
		for (IFieldAddedEventListener event : this.fieldAddedListener) {
			event.handleFieldAdded(x, y, field);
		}
	}
	

	public void addStepListener(IFieldAddedEventListener l) {
		this.fieldAddedListener.add(l);
	}

	public void removeStepListener(IFieldAddedEventListener l) {
		this.fieldAddedListener.remove(l);
	}
}
