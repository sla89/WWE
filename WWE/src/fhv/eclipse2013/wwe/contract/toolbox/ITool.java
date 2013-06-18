package fhv.eclipse2013.wwe.contract.toolbox;

public interface ITool {
	ITool getParent();

	String getName();

	boolean hasChildren();

	String getPath();
}
