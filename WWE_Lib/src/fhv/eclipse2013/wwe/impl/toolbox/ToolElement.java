package fhv.eclipse2013.wwe.impl.toolbox;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.state.FieldState;
import fhv.eclipse2013.wwe.contract.toolbox.IToolElement;
import fhv.eclipse2013.wwe.impl.field.WireWorldField;
import fhv.eclipse2013.wwe.impl.scope.SimulationScope;

public class ToolElement implements IToolElement {

	public static IToolElement loadFile(String folder, String filename) {
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(folder + "\\" + filename);
		xmlFile.getName();
		ToolElement element;

		try {
			Document document = builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			String name = rootNode.getAttributeValue("name");
			int width = Integer.parseInt(rootNode.getChild("width").getValue());
			int height = Integer.parseInt(rootNode.getChild("height")
					.getValue());

			element = new ToolElement(name, xmlFile.getName(), width, height);

			Element fields = rootNode.getChild("fields");
			for (Element field : fields.getChildren("field")) {
				int x = Integer.parseInt(field.getAttributeValue("x"));
				int y = Integer.parseInt(field.getAttributeValue("y"));
				FieldState state = FieldState.valueOf(field
						.getAttributeValue("state"));
				element.setField(y, x, state);
			}

			return element;
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
		return null;
	}

	private Dimension size;
	private String name;
	private FieldState[][] fields;
	private String filename;

	public ToolElement(String name, String filename, int width, int height) {
		this.name = name;
		this.filename = filename;
		this.size = new Dimension(width, height);
		this.fields = new FieldState[height][width];
	}

	private void setField(int x, int y, FieldState state) {
		this.fields[x][y] = state;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Dimension getSize() {
		return size;
	}

	@Override
	public FieldState[][] getFields() {
		return fields;
	}
}
