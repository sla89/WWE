package wwe.scope.control.dnd;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

import fhv.eclipse2013.wwe.contract.toolbox.IToolElement;

public class ToolboxDragSourceListener implements DragSourceListener {

	private StructuredViewer viewer;

	public ToolboxDragSourceListener(StructuredViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void dragStart(DragSourceEvent event) {
	}

	@Override
	public void dragSetData(DragSourceEvent event) {
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();

		if (selection.getFirstElement() instanceof IToolElement) {
			if (ObjectTransfer.elementTransfer.isSupportedType(event.dataType)) {
				Object firstElement = selection.getFirstElement();
				event.data = firstElement;
			}
		}
	}

	@Override
	public void dragFinished(DragSourceEvent event) {
	}

}
