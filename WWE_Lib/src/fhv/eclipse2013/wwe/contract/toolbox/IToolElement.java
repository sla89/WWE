package fhv.eclipse2013.wwe.contract.toolbox;

import java.awt.Dimension;

import fhv.eclipse2013.wwe.contract.state.FieldState;

public interface IToolElement {
	String getName();

	Dimension getSize();

	FieldState[][] getFields();

	String getImage();
}
