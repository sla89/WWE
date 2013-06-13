package wwe.scope.control;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import wwe.Activator;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.scope.IFieldAddedEventListener;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.FieldState;

public class FieldCanvas extends Canvas implements PropertyChangeListener,
		IFieldAddedEventListener, PaintListener, IHaveScope {

	Display d;

	private Point coord;
	private IField field;
	private RGB bg;
	private ISimulationScope scope;

	public FieldCanvas(Composite parent, int style, ISimulationScope scope,
			int x, int y) {
		super(parent, style);
		d = parent.getDisplay();
		this.scope = scope;
		this.setCoord(new Point(x, y));

		if (scope.fieldExists(x, y)) {
			this.initField();
		} else {
			setColor(FieldState.none);
		}

		this.addPaintListener(this);
		this.scope.addFieldAddedListener(this);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (field == null) {
					initField();
				}
				field.click();
			}
		});

		Activator.getDefault().getPreferenceStore()
				.addPropertyChangeListener(new IPropertyChangeListener() {

					@Override
					public void propertyChange(
							org.eclipse.jface.util.PropertyChangeEvent event) {
						if (event.getProperty().startsWith("color")) {
							if (field != null) {
								setColor(field.getState());
							} else {
								setColor(FieldState.none);
							}
							redraw();
						} else if (event.getProperty().equals("showCoordinate")) {
							redraw();
						} else if (event.getProperty().startsWith("block")) {
							getParent().pack();
						}
					}
				});
	}

	private void initField() {
		this.field = this.scope.getField((int) this.coord.getX(),
				(int) this.coord.getY());
		this.field.addPropertyChangeListener(this);
		this.setColor(this.field.getState());
	}

	@Override
	public ISimulationScope getScope() {
		return scope;
	}

	public void setCoord(Point coord) {
		this.coord = coord;
		if (this.field != null) {
			this.field.removePropertyChangeListener(this);
			this.field = null;
		}
		if (this.scope.fieldExists((int) coord.getX(), (int) coord.getY())) {
			this.initField();
		}
	}

	public Point getCoord() {
		return this.coord;
	}

	private void setColor(FieldState state) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		RGB none = PreferenceConverter.getColor(store, "color.none");
		RGB conductor = PreferenceConverter.getColor(store, "color.conductor");
		RGB head = PreferenceConverter.getColor(store, "color.head");
		RGB tail = PreferenceConverter.getColor(store, "color.tail");

		if (state.equals(FieldState.none)) {
			this.bg = none;
		} else if (state.equals(FieldState.conductor)) {
			this.bg = conductor;
		} else if (state.equals(FieldState.head)) {
			this.bg = head;
		} else if (state.equals(FieldState.tail)) {
			this.bg = tail;
		}
		d.asyncExec(new Runnable() {
			@Override
			public void run() {
				redraw();
			}
		});
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("state")) {
			FieldState state = (FieldState) evt.getNewValue();
			this.setColor(state);
		}
	}

	@Override
	public org.eclipse.swt.graphics.Point computeSize(int wHint, int hHint,
			boolean changed) {
		org.eclipse.swt.graphics.Point initialSize = super.computeSize(wHint,
				hHint, changed);
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		initialSize.x = store.getInt("block.width");
		initialSize.y = store.getInt("block.height");
		return initialSize;
	}

	@Override
	public void handleFieldAdded(int x, int y, IField field) {
		if (x == (int) coord.getX() && y == (int) coord.getY()) {
			initField();
		}
	}

	@Override
	public void paintControl(PaintEvent e) {
		e.gc.setBackground(new Color(e.display, bg));
		e.gc.fillRectangle(0, 0, e.width, e.height);
		e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_WHITE));
		e.gc.drawRectangle(0, 0, e.width, e.height);

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (store.getBoolean("showCoordinate")) {
			e.gc.drawString(coord.x + "," + coord.y, 2, 2);
		}
	}

	@Override
	public void handleFieldDeleted(int x, int y, IField field) {
		// TODO Auto-generated method stub
		
	}
}
