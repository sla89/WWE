package wwe.toolbar;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import wwe.Activator;
import wwe.scope.control.dnd.ObjectTransfer;
import wwe.scope.control.dnd.ToolboxDragSourceListener;
import fhv.eclipse2013.wwe.ToolboxFacade;

public class ToolbarTreeView extends ViewPart {
    public static final String ID = "WWE.toolbar.toolbarTreeView";

    public static void reload() {
	String folderName = Activator.getDefault().getPreferenceStore()
		.getString("PATH");

	if (!folderName.isEmpty())
	    viewer.setInput(ToolboxFacade.load(folderName));
    }

    private static TreeViewer viewer;

    public ToolbarTreeView() {
	// TODO Auto-generated constructor stub
    }

    @Override
    public void createPartControl(Composite parent) {
	viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
	viewer.setContentProvider(new ToolbarTreeContentProvider());
	viewer.setLabelProvider(new ToolbarTreeLabelProvider());

	// Expand the tree
	viewer.setAutoExpandLevel(2);
	// Provide the input to the ContentProvider

	// Load the ToolBox
	reload();

	viewer.addDragSupport(DND.DROP_COPY | DND.DROP_MOVE,
		new Transfer[] { ObjectTransfer.elementTransfer },
		new ToolboxDragSourceListener(viewer));

	Activator.getDefault().getPreferenceStore()
		.addPropertyChangeListener(new IPropertyChangeListener() {
		    @Override
		    public void propertyChange(
			    org.eclipse.jface.util.PropertyChangeEvent event) {
			if (event.getProperty() == "PATH") {
			    reload();
			}
		    }
		});
    }

    @Override
    public void setFocus() {
	// TODO Auto-generated method stub

    }

}
