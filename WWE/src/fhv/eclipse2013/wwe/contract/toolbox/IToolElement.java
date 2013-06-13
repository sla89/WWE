package fhv.eclipse2013.wwe.contract.toolbox;

import java.awt.Dimension;

import fhv.eclipse2013.wwe.contract.state.FieldState;

public interface IToolElement {
	String getName();

	Dimension getSize();

	String getImage();

	String getXml();

	int getHeight();

	int getWidth();

	FieldState getField(int x, int y);
}
