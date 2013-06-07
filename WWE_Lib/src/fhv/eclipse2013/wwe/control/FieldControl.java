package fhv.eclipse2013.wwe.control;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import fhv.eclipse2013.wwe.contract.FieldState;
import fhv.eclipse2013.wwe.contract.IField;

public class FieldControl extends JPanel {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private IField field;
	private Color bg;

	public FieldControl(IField field) {
		this.field = field;
		this.setColor(field.getState());
		field.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("state")) {
					FieldState state = (FieldState) evt.getNewValue();
					FieldControl.this.setColor(state);
				}
			}
		});
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				FieldControl.this.field.click();
			}
		});
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
		FieldControl.this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(this.bg);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		g.drawRect(0, 0, this.getWidth(), this.getHeight());
	}
}
