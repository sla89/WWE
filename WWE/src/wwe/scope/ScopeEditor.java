package wwe.scope;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import wwe.scope.control.FieldCanvas;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;

public class ScopeEditor extends EditorPart {
	public static final String ID = "WWE.scope.ScopeEditor";
	private ISimulationScope scope;
	private ScopeEditorInput input;

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

		ScrolledComposite scroll = new ScrolledComposite(parent, SWT.V_SCROLL
				| SWT.H_SCROLL | SWT.BORDER);
		scroll.setLayout(new GridLayout(1, true));

		Composite scrollContent = new Composite(scroll, SWT.NONE);
		scrollContent.setLayout(new GridLayout(scope.getWidth(), true));

		for (int x = 0; x < scope.getWidth(); x++) {
			for (int y = 0; y < scope.getHeight(); y++) {
				new FieldCanvas(scrollContent, 1, scope, x, y);
			}
		}

		scroll.setMinSize(scrollContent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		scroll.setContent(scrollContent);
		scroll.setExpandVertical(true);
		scroll.setExpandHorizontal(true);
		scroll.setAlwaysShowScrollBars(true);
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
