package wwe.scope.control.dnd;

import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;

import fhv.eclipse2013.wwe.contract.toolbox.IToolElement;

import wwe.scope.control.FieldCanvas;

public class ToolboxTarget extends DropTargetAdapter {

	private FieldCanvas canvas;

	public ToolboxTarget(FieldCanvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void drop(DropTargetEvent event) {
		canvas.getScope().place((IToolElement) event.data, canvas.getCoord());
	}

}
