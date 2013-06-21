package fhv.eclipse2013.wwe;

import fhv.eclipse2013.wwe.contract.toolbox.IToolCategory;
import fhv.eclipse2013.wwe.impl.toolbox.Toolbox;

public class ToolboxFacade {
	public static IToolCategory load(String folderName) {
		return Toolbox.load(folderName);
	}
}
