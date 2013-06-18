package wwe.toolbar;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import fhv.eclipse2013.wwe.contract.toolbox.ITool;
import fhv.eclipse2013.wwe.contract.toolbox.IToolCategory;

public class ToolbarTreeContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof IToolCategory) {
			IToolCategory element = (IToolCategory) inputElement;
			return element.getChildren().toArray();
		}
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IToolCategory) {
			IToolCategory element = (IToolCategory) parentElement;
			return element.getChildren().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof ITool) {
			ITool e = (ITool) element;
			return e.getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof ITool) {
			ITool e = (ITool) element;
			return e.hasChildren();
		}
		return false;
	}

}
