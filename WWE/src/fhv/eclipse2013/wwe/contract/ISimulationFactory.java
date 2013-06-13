package fhv.eclipse2013.wwe.contract;

import java.awt.Point;

import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.FieldState;
import fhv.eclipse2013.wwe.contract.toolbox.IToolElement;

public interface ISimulationFactory {
	ISimulationScope createScope(int width, int height, String name);

	ISimulationScope loadScope(String filename);

	IField createField(ISimulationScope scope, Point coord);

	IToolElement[] readToolboxFolder(String foldername);

	IField createField(ISimulationScope scope, FieldState state, Point point);
}
