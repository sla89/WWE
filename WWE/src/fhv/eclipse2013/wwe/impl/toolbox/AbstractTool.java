package fhv.eclipse2013.wwe.impl.toolbox;

import fhv.eclipse2013.wwe.contract.toolbox.ITool;

public class AbstractTool implements ITool {

	private ITool parent;
	private String name;

	public AbstractTool(ITool parent, String name) {
		this.name = name;
		this.parent = parent;
	}

	public AbstractTool(String name) {
		this(null, name);
	}

	@Override
	public ITool getParent() {
		return this.parent;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	@Override
	public String getPath() {
		if (parent == null) {
			return "";
		} else {
			String ret = getParent().getPath();
			return ret + "/" + getName();
		}
	}

}
