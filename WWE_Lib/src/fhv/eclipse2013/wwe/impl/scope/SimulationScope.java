package fhv.eclipse2013.wwe.impl.scope;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import fhv.eclipse2013.wwe.contract.FieldState;
import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.SimulationState;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.scope.IStepEventListener.Type;
import fhv.eclipse2013.wwe.impl.field.Field;

public class SimulationScope extends SimulationScopeBase implements
		ISimulationScope {

	public static SimulationScope load(String filename) {
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(filename);

		try {
			Document document = builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			int width = Integer.parseInt(rootNode.getChild("width").getValue());
			int height = Integer.parseInt(rootNode.getChild("height")
					.getValue());

			IField[][] fields = new IField[width][];
			SimulationScope scope = new SimulationScope(width, height, false);
			rootNode.getChild("fields");
			for (Element row : rootNode.getChild("fields").getChildren("row")) {
				fields[Integer.parseInt(row.getAttributeValue("x"))] = new IField[height];
				for (Element field : row.getChildren("field")) {
					int x = Integer.parseInt(field.getAttributeValue("x"));
					int y = Integer.parseInt(field.getAttributeValue("y"));
					FieldState state = FieldState.valueOf(field
							.getAttributeValue("state"));
					fields[x][y] = new Field(scope, state, new Coordinate(x, y));
				}
			}
			scope.setFields(fields);
			scope.setSimulationState(SimulationState.init);

			return scope;
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
		return new SimulationScope(1, 1);
	}

	private class Task extends TimerTask {
		@Override
		public void run() {
			SimulationScope.this.onNextStep();
		}
	}

	Timer t;

	private void startTimer() {
		if (this.t == null) {
			this.t = new Timer();
			this.t.schedule(new Task(), 0, 100);
		} else {
			this.setSimulationState(SimulationState.paused);
			this.stopTimer();
		}
	}

	private void stopTimer() {
		if (this.t != null) {
			this.t.cancel();
			this.t = null;
		}
	}

	private String name = "TEST";

	public SimulationScope(int w, int h) {
		super(w, h, true);
	}

	private SimulationScope(int w, int h, boolean init) {
		super(w, h, init);
	}

	public void setName(String name) {
		String old = this.name;
		this.name = name;
		this.onPropertyChanged("name", old, this.name);
	}

	public String getName() {
		return this.name;
	}

	@Override
	protected IField initField(int x, int y) {
		return new Field(this, new Coordinate(x, y));
	}

	public void nextStep() {
		if (this.getSimulationState().equals(SimulationState.paused)) {
			this.onNextStep();
		}
	}

	private void onNextStep() {
		this.onStepChanged(Type.prepare);
		this.onStepChanged(Type.next);
	}

	@Override
	public void backStep() {
		if (this.getSimulationState().equals(SimulationState.paused)) {
			this.onStepChanged(Type.back);
		}
	}

	@Override
	public void click(int x, int y) {
		this.getField(x, y).click();
	}

	@Override
	public void start() {
		if (!this.getSimulationState().equals(SimulationState.started)) {
			this.setSimulationState(SimulationState.started);
		}
		this.startTimer();
	}

	@Override
	public void stop() {
		if (!this.getSimulationState().equals(SimulationState.stopped)) {
			this.stopTimer();
			this.setSimulationState(SimulationState.stopped);
			this.onStepChanged(Type.reset);
		}
	}

	public void save(String filename) {
		try {
			Element scope = new Element("scope");
			Document doc = new Document(scope);

			scope.setAttribute(new Attribute("name", this.getName()));
			scope.addContent(new Element("width").setText(this.getWidth() + ""));
			scope.addContent(new Element("height").setText(this.getHeight()
					+ ""));

			Element fields = new Element("fields");
			for (int x = 0; x < this.getWidth(); x++) {
				Element row = new Element("row");
				row.setAttribute(new Attribute("x", x + ""));
				for (int y = 0; y < this.getHeight(); y++) {
					if (this.FieldExists(x, y)) {
						Element e = new Element("field");
						e.setAttribute(new Attribute("y", y + ""));
						e.setAttribute(new Attribute("x", x + ""));
						IField f = this.getField(x, y);
						row.addContent(f.getElement(e));
					}
				}
				fields.addContent(row);
			}
			scope.addContent(fields);

			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
