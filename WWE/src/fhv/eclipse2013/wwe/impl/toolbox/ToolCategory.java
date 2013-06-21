package fhv.eclipse2013.wwe.impl.toolbox;

import org.eclipse.core.databinding.observable.list.IObservableList;

import fhv.eclipse2013.wwe.contract.toolbox.ITool;
import fhv.eclipse2013.wwe.contract.toolbox.IToolCategory;

public class ToolCategory extends AbstractTool implements IToolCategory {

	private IObservableList elements;

	public ToolCategory(ITool parent, String name) {
		super(parent, name);
	}

	public void setElements(IObservableList elements) {
		this.elements = elements;
	}

	@Override
	public IObservableList getChildren() {
		return elements;
	}

	@Override
	public boolean hasChildren() {
		return elements.size() > 0;
	}

}
