package fhv.eclipse2013.wwe.control;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.FieldState;

public class FieldControl extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	private Point coord;
	private IField field;
	private Color bg;
	private final ISimulationScope scope;

	public FieldControl(int x, int y, ISimulationScope scope) {
		this.scope = scope;
		this.setCoord(new Point(x, y));

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (FieldControl.this.field == null) {
					FieldControl.this.initField();
				}
				FieldControl.this.field.click();
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
			FieldControl.this.bg = Color.BLACK;
		} else if (state.equals(FieldState.conductor)) {
			FieldControl.this.bg = Color.YELLOW;
		} else if (state.equals(FieldState.head)) {
			FieldControl.this.bg = Color.BLUE;
		} else if (state.equals(FieldState.tail)) {
			FieldControl.this.bg = Color.RED;
		}
		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(this.bg);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		g.drawRect(0, 0, this.getWidth(), this.getHeight());
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("state")) {
			FieldState state = (FieldState) evt.getNewValue();
			FieldControl.this.setColor(state);
		}
	}
}
