package wwe.scope;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.control.ScopeControl;

public class ScopeEditor extends EditorPart {
	public static final String ID = "WWE.scope.ScopeEditor";
	private ISimulationScope scope;
	private ScopeEditorInput input;

	// Will be called before createPartControl
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (!(input instanceof ScopeEditorInput)) {
			throw new RuntimeException("Wrong input");
		}

		this.input = (ScopeEditorInput) input;
		setSite(site);
		setInput(input);
		scope = this.input.getScope();
		setPartName(this.input.getName());
	}
	
	public ISimulationScope getScope() {
		return scope;
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
		java.awt.Frame frame = SWT_AWT.new_Frame(swtAwtComponent);

		JScrollPane pane = new JScrollPane(new ScopeControl(this.scope),
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		frame.add(pane);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {
	}

}
