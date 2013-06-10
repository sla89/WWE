package wwe.scope.control;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.FieldState;

public class FieldCanvas extends Canvas implements PropertyChangeListener {

	Display d;

	private Point coord;
	private IField field;
	private int bg;
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

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (field == null) {
					initField();
				}
				field.click();
			}
		});

		this.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				// System.out.println("Redraw: [" + coord.x + "," + coord.y +
				// "]");
				e.gc.setBackground(e.display.getSystemColor(bg));
				e.gc.fillRectangle(0, 0, e.width, e.height);
				e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_WHITE));
				e.gc.drawRectangle(0, 0, e.width, e.height);
			}
		});
	}

	private void initField() {
		this.field = this.scope.getField((int) this.coord.getX(),
				(int) this.coord.getY());
		this.field.addPropertyChangeListener(this);
		this.setColor(this.field.getState());
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
		if (state.equals(FieldState.none)) {
			this.bg = SWT.COLOR_BLACK;
		} else if (state.equals(FieldState.conductor)) {
			this.bg = SWT.COLOR_YELLOW;
		} else if (state.equals(FieldState.head)) {
			this.bg = SWT.COLOR_BLUE;
		} else if (state.equals(FieldState.tail)) {
			this.bg = SWT.COLOR_RED;
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
		initialSize.x = 20;
		initialSize.y = 20;
		return initialSize;
	}
}
