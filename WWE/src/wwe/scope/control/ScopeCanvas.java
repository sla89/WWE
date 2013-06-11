package wwe.scope.control;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import wwe.Activator;
import wwe.scope.control.dnd.ObjectTransfer;
import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.scope.IFieldAddedEventListener;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.FieldState;
import fhv.eclipse2013.wwe.contract.toolbox.IToolElement;

public class ScopeCanvas extends Canvas implements PaintListener,
		PropertyChangeListener, IFieldAddedEventListener, MouseListener,
		IHaveScope {

	private ISimulationScope scope;

	public ScopeCanvas(Composite parent, int style, ISimulationScope scope) {
		super(parent, style);
		this.scope = scope;

		this.addPaintListener(this);
		this.scope.addFieldAddedListener(this);
		this.addMouseListener(this);

		for (int x = 0; x < scope.getWidth(); x++) {
			for (int y = 0; y < scope.getHeight(); y++) {
				if (scope.fieldExists(x, y)) {
					IField field = scope.getField(x, y);
					field.addPropertyChangeListener(this);
				}
			}
		}

		DropTarget dt = new DropTarget(this, DND.DROP_COPY | DND.DROP_MOVE);
		dt.setTransfer(new Transfer[] { ObjectTransfer.elementTransfer });
		dt.addDropListener(new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {

				Point rect = toDisplay(0, 0);
				IPreferenceStore store = Activator.getDefault()
						.getPreferenceStore();
				int wx = store.getInt("block.width");
				int wy = store.getInt("block.height");

				int x = (event.x - rect.x) / wx;
				int y = (event.y - rect.y) / wy;
				getScope().place((IToolElement) event.data,
						new java.awt.Point(x, y));
			}
		});

		Activator.getDefault().getPreferenceStore()
				.addPropertyChangeListener(new IPropertyChangeListener() {

					@Override
					public void propertyChange(
							org.eclipse.jface.util.PropertyChangeEvent event) {
						if (event.getProperty().startsWith("color")) {
							redraw();
						} else if (event.getProperty().equals("showCoordinate")) {
							redraw();
						} else if (event.getProperty().startsWith("block")) {
							getParent().pack();
						}
					}
				});
	}

	@Override
	public ISimulationScope getScope() {
		return scope;
	}

	@Override
	public void paintControl(PaintEvent e) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		int wx = store.getInt("block.width");
		int wy = store.getInt("block.height");
		if (e.x == 0 && e.y == 0 && e.width == getSize().x
				&& e.height == getSize().y) {
			paintNotInitiated(e, wx, wy);
		} else {
			paintInitiated(e, wx, wy);
		}
	}

	private void paintInitiated(PaintEvent e, int wx, int wy) {
		for (int x = e.x; x <= e.x + e.width; x = x + wx) {
			int cx = x / wx;
			for (int y = e.y; y <= e.y + e.height; y = y + wy) {
				int cy = y / wy;
				if (scope.fieldExists(cx, cy)) {
					e.gc.setBackground(new Color(e.display, getColor(scope
							.getField(cx, cy).getState())));
				} else {
					e.gc.setBackground(new Color(e.display,
							getColor(FieldState.none)));
				}
				e.gc.fillRectangle(x, y, wx, wy);
				e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_WHITE));
				e.gc.drawRectangle(x, y, wx, wy);

				IPreferenceStore store = Activator.getDefault()
						.getPreferenceStore();
				if (store.getBoolean("showCoordinate")) {
					e.gc.drawString(cx + "," + cy, 2, 2);
				}
			}
		}
	}

	private void paintNotInitiated(PaintEvent e, int wx, int wy) {
		ISimulationScope scope = getScope();
		for (int x = 0; x < scope.getWidth(); x++) {
			int cx = x * wx;
			for (int y = 0; y < scope.getHeight(); y++) {
				int cy = y * wy;

				RGB c = getColor(FieldState.none);
				if (scope.fieldExists(x, y)) {
					IField field = scope.getField(x, y);
					c = getColor(field.getState());
				}
				e.gc.setBackground(new Color(e.display, c));
				e.gc.fillRectangle(cx, cy, wx, wy);
				e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_WHITE));
				e.gc.drawRectangle(cx, cy, wx, wy);

				IPreferenceStore store = Activator.getDefault()
						.getPreferenceStore();
				if (store.getBoolean("showCoordinate")) {
					e.gc.drawString(x + "," + y, 2, 2);
				}
			}
		}
	}

	private RGB getColor(FieldState state) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		RGB none = PreferenceConverter.getColor(store, "color.none");
		RGB conductor = PreferenceConverter.getColor(store, "color.conductor");
		RGB head = PreferenceConverter.getColor(store, "color.head");
		RGB tail = PreferenceConverter.getColor(store, "color.tail");

		if (state.equals(FieldState.none)) {
			return none;
		} else if (state.equals(FieldState.conductor)) {
			return conductor;
		} else if (state.equals(FieldState.head)) {
			return head;
		} else if (state.equals(FieldState.tail)) {
			return tail;
		}
		return none;
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				IPreferenceStore store = Activator.getDefault()
						.getPreferenceStore();
				int wx = store.getInt("block.width");
				int wy = store.getInt("block.height");

				IField field = (IField) evt.getSource();

				int x = field.getCoordinate().x;
				int y = field.getCoordinate().y;
				int mx = 1;
				int my = 1;
				// if (x < 0)
				// x = 0;
				// if (y < 0)
				// y = 0;
				// if (x + 3 >= scope.getWidth())
				// mx = 2;
				// if (y + 3 >= scope.getHeight())
				// my = 2;

				int cx = x * wx;
				int cy = y * wy;
				redraw(cx, cy, wx * mx, wy * my, false);
			}
		});
	}

	@Override
	public void handleFieldAdded(int x, int y, IField field) {
		field.addPropertyChangeListener(this);
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		Point initialSize = super.computeSize(wHint, hHint, changed);
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		initialSize.x = store.getInt("block.width") * getScope().getWidth();
		initialSize.y = store.getInt("block.height") * getScope().getHeight();
		return initialSize;
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDown(MouseEvent e) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		int wx = store.getInt("block.width");
		int wy = store.getInt("block.height");

		int x = e.x / wx;
		int y = e.y / wy;

		scope.click(x, y);
	}

	@Override
	public void mouseUp(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
