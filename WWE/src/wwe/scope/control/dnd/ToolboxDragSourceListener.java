package wwe.scope.control.dnd;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

import fhv.eclipse2013.wwe.contract.toolbox.IToolElement;

public class ToolboxDragSourceListener implements DragSourceListener {

	private TableViewer viewer;

	public ToolboxDragSourceListener(TableViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void dragStart(DragSourceEvent event) {
	}

	@Override
	public void dragSetData(DragSourceEvent event) {
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		IToolElement firstElement = (IToolElement) selection.getFirstElement();

		if (ObjectTransfer.elementTransfer.isSupportedType(event.dataType)) {
			event.data = firstElement;
		}
	}

	@Override
	public void dragFinished(DragSourceEvent event) {
	}

}
