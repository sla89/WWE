package fhv.eclipse2013.wwe.contract.toolbox;

import org.eclipse.core.databinding.observable.list.IObservableList;

public interface IToolCategory extends ITool {
	IObservableList getChildren();
}
