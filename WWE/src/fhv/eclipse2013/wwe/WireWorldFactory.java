package fhv.eclipse2013.wwe;

import java.awt.Point;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.ISimulationFactory;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.FieldState;
import fhv.eclipse2013.wwe.contract.toolbox.IToolElement;
import fhv.eclipse2013.wwe.impl.field.WireWorldField;
import fhv.eclipse2013.wwe.impl.scope.SimulationScope;
import fhv.eclipse2013.wwe.impl.toolbox.ToolElement;

public class WireWorldFactory implements ISimulationFactory {

	public WireWorldFactory() {

	}

	@Override
	public ISimulationScope createScope(int width, int height, String name) {
		return new SimulationScope(width, height, name, this);
	}

	@Override
	public ISimulationScope loadScope(String filename) {
		return SimulationScope.load(filename, this);
	}

	@Override
	public IField createField(ISimulationScope scope, Point coord) {
		return new WireWorldField(scope, coord);
	}

	@Override
	public IField createField(ISimulationScope scope, FieldState state, Point coord) {
		return new WireWorldField(scope, state, coord);
	}

	@Override
	public IToolElement[] readToolboxFolder(String foldername) {
		List<IToolElement> elements = new ArrayList<>();
		File folder = new File(foldername);
		if (folder.isDirectory()) {
			String[] files = folder.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.contains(".xml");
				}
			});
			for (String file : files) {
				IToolElement element = ToolElement.loadFile(
						folder.getAbsolutePath(), file);
				if (element != null)
					elements.add(element);
			}
		}
		if (elements.size() != 0) {
			return (IToolElement[]) elements.toArray(new IToolElement[0]);
		} else {
			return new IToolElement[0];
		}
	}
}
