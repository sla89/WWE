package wwe.scope;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

public class ScopeEditorInput implements IEditorInput {

	private final ISimulationScope scope;

	public ScopeEditorInput(ISimulationScope scope) {
		this.scope = scope;
	}

	public ISimulationScope getScope() {
		return scope;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return scope.getName();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return Messages.ScopeEditorInput_0;
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + scope.getName().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScopeEditorInput other = (ScopeEditorInput) obj;
		if (!getScope().equals(other.getScope()))
			return false;
		return true;
	}
}
