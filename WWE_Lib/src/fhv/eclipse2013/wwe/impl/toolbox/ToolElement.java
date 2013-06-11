package fhv.eclipse2013.wwe.impl.toolbox;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import fhv.eclipse2013.wwe.contract.state.FieldState;
import fhv.eclipse2013.wwe.contract.toolbox.IToolElement;

public class ToolElement implements IToolElement {

	private static IToolElement load(Document document, String absolutePath) {
		ToolElement element;

		Element rootNode = document.getRootElement();
		String name = rootNode.getAttributeValue("name");
		int width = Integer.parseInt(rootNode.getChild("width").getValue());
		int height = Integer.parseInt(rootNode.getChild("height").getValue());
		String image = rootNode.getChild("image").getValue();

		element = new ToolElement(name, absolutePath, width, height, image);

		Element fields = rootNode.getChild("fields");
		for (Element field : fields.getChildren("field")) {
			int x = Integer.parseInt(field.getAttributeValue("x"));
			int y = Integer.parseInt(field.getAttributeValue("y"));
			FieldState state = FieldState.valueOf(field
					.getAttributeValue("state"));
			element.setField(x, y, state);
		}

		return element;
	}

	public static IToolElement loadString(String doc) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new StringReader(doc));
			return load(document, "");
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
		return null;
	}

	public static IToolElement loadFile(String folder, String filename) {
		try {
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(folder + "\\" + filename);
			Document document = builder.build(xmlFile);
			return load(document, xmlFile.getAbsolutePath());
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
	private String image;

	public ToolElement(String name, String filename, int width, int height,
			String image) {
		this.name = name;
		this.filename = filename;

		if (!image.equals("")) {
			File file = new File(filename);
			String folder = file.getAbsolutePath().replace(file.getName(), "");
			image = folder + image;
			this.image = image;
		} else {
			this.image = image;
		}
		this.size = new Dimension(width, height);
		this.fields = new FieldState[width][height];
	}

	private void setField(int x, int y, FieldState state) {
		this.fields[y][x] = state;
	}

	@Override
	public FieldState getField(int x, int y) {
		return this.fields[y][x];
	}

	public String getFilename() {
		return filename;
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
	public int getHeight() {
		return (int) getSize().getHeight();
	}

	@Override
	public int getWidth() {
		return (int) getSize().getWidth();
	}

	@Override
	public String getImage() {
		return image;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	@Override
	public String getXml() {
		Element scope = new Element("scope");
		Document doc = new Document(scope);

		scope.setAttribute(new Attribute("name", this.getName()));
		scope.addContent(new Element("width").setText((int) this.getSize()
				.getWidth() + ""));
		scope.addContent(new Element("height").setText((int) this.getSize()
				.getHeight() + ""));
		scope.addContent(new Element("image").setText(this.getImage()));

		Element fields = new Element("fields");
		for (int x = 0; x < this.getSize().getHeight(); x++) {
			for (int y = 0; y < this.getSize().getWidth(); y++) {
				FieldState f = this.getField(x, y);
				if (f != null && !f.equals(FieldState.none)) {
					Element e = new Element("field");
					String sx = String.format("%d", x);
					String sy = String.format("%d", y);
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
		String string = xmlOutput.outputString(doc);
		return string;
	}
}
