package fhv.eclipse2013.wwe.contract.factory;

import java.awt.Point;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.IFieldNeighbours;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.FieldState;

public interface IFieldFactory {
	IField createField(ISimulationScope scope, Point coord);

	IField createField(ISimulationScope scope, FieldState state, Point point);

	IFieldNeighbours createFieldNeighbour(ISimulationScope scope, IField field,
			Point c);
}
