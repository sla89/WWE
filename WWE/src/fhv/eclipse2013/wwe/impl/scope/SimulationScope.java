package fhv.eclipse2013.wwe.impl.scope;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Rectangle;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.ISimulationFactory;
import fhv.eclipse2013.wwe.contract.state.FieldState;
import fhv.eclipse2013.wwe.contract.toolbox.IToolElement;
import fhv.eclipse2013.wwe.impl.field.WireWorldField;

public class SimulationScope extends AbstractScope {

	public static SimulationScope load(String filename,
			ISimulationFactory factory) {

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(filename);

		try {
			Document document = builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			String name = rootNode.getAttributeValue("name");
			int width = Integer.parseInt(rootNode.getChild("width").getValue());
			int height = Integer.parseInt(rootNode.getChild("height")
					.getValue());

			IField[][] fields = new IField[width][];
			List<IField> field_list = new ArrayList<>();
			SimulationScope scope = new SimulationScope(width, height, name,
					factory, false);
			rootNode.getChild("fields");
			for (Element field : rootNode.getChild("fields").getChildren(
					"field")) {
				int x = Integer.parseInt(field.getAttributeValue("x"));
				int y = Integer.parseInt(field.getAttributeValue("y"));
				FieldState state = FieldState.valueOf(field
						.getAttributeValue("state"));
				if (fields[y] == null) {
					fields[y] = new IField[width];
				}
				fields[y][x] = new WireWorldField(scope, state, new Point(x, y));
				field_list.add(fields[y][x]);
			}
			scope.setFields(fields, field_list);

			return scope;
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}

		return new SimulationScope(1, 1, "NEW", factory);
	}

	public SimulationScope(int w, int h, String name, ISimulationFactory factory) {
		super(w, h, name, true, factory);
	}

	private SimulationScope(int w, int h, String name,
			ISimulationFactory factory, boolean init) {
		super(w, h, name, init, factory);
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(0, 0, getWidth(), getHeight());
	}

	@Override
	public void save(String filename) throws IOException {
		Element scope = new Element("scope");
		Document doc = new Document(scope);

		scope.setAttribute(new Attribute("name", this.getName()));
		scope.addContent(new Element("width").setText((int) this.getWidth()
				+ ""));
		scope.addContent(new Element("height").setText((int) this.getHeight()
				+ ""));

		Element fields = new Element("fields");
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				if (this.fieldExists(x, y)) {
					IField f = this.getField(x, y);
					if (!f.getState().equals(FieldState.none)) {
						Element e = new Element("field");
						e.setAttribute(new Attribute("y", y + ""));
						e.setAttribute(new Attribute("x", x + ""));
						fields.addContent(f.getElement(e));
					}
				}
			}
		}
		scope.addContent(fields);

		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		xmlOutput.output(doc, new FileWriter(filename));
	}

	@Override
	public void saveAsToolElement(String filename, String imageFileName)
			throws IOException {
		Element scope = new Element("scope");
		Document doc = new Document(scope);

		Rectangle max = getMinRectangle();

		scope.setAttribute(new Attribute("name", this.getName()));
		scope.addContent(new Element("width").setText((max.width) + ""));
		scope.addContent(new Element("height").setText((max.height) + ""));
		scope.addContent(new Element("image").setText(imageFileName));

		Element fields = new Element("fields");
		for (int x = max.x; x < this.getWidth(); x++) {
			for (int y = max.y; y < this.getHeight(); y++) {
				FieldState f = this.getField(x, y).getState();
				if (f != null && !f.equals(FieldState.none)) {
					Element e = new Element("field");
					String sx = String.format("%d", x - max.x);
					String sy = String.format("%d", y - max.y);
					e.setAttribute(new Attribute("y", sy));
					e.setAttribute(new Attribute("x", sx));
					e.setAttribute(new Attribute("state", f.name()));
					fields.addContent(e);
				}
			}
		}
		scope.addContent(fields);

		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		xmlOutput.output(doc, new FileWriter(filename));
	}

	@Override
	public void place(IToolElement element, Point coord) {
		if (!this.getLock()) {
			for (int x = 0; x < element.getWidth(); x++) {
				for (int y = 0; y < element.getHeight(); y++) {
					FieldState state = element.getField(x, y);
					if (state != null) {
						getField(coord.x + x, coord.y + y).setState(state);
					}
				}
			}
		}
	}
}
