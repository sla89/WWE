package wwe;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import wwe.scope.control.dnd.ObjectTransfer;
import wwe.scope.control.dnd.ToolboxDragSourceListener;
import wwe.util.SimulationScopeHandler;
import fhv.eclipse2013.wwe.impl.toolbox.ToolElement;

public class ToolbarView extends ViewPart {
	public static final String ID = "WWE.toolbarView";

	// TODO perhaps contextmenu (right-click) delete toolboxitem
	
	public static void reload() {
		viewer.setInput(SimulationScopeHandler.INSTANCE.getFactory()
				.readToolboxFolder(
						Activator.getDefault().getPreferenceStore()
								.getString("PATH")));
	}

	private static TableViewer viewer;

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class ViewContentProvider implements IStructuredContentProvider {
		@Override
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object parent) {
			if (parent instanceof Object[]) {
				return (Object[]) parent;
			}
			return new Object[0];
		}
	}

	class ViewLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		@Override
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}

		@Override
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		@Override
		public Image getImage(Object obj) {
			ToolElement element = (ToolElement) obj;
			String img = element.getImage();
			if (!img.equals("")) {
				try {
					ImageDescriptor image = ImageDescriptor.createFromFile(
							null, img);
					image = ImageDescriptor.createFromImageData(image
							.getImageData().scaledTo(50, 50));
					return image.createImage();
				} catch (Exception ex) {
					return PlatformUI.getWorkbench().getSharedImages()
							.getImage(ISharedImages.IMG_OBJ_ELEMENT);
				}
			} else {
				return PlatformUI.getWorkbench().getSharedImages()
						.getImage(ISharedImages.IMG_OBJ_ELEMENT);
			}
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		// Provide the input to the ContentProvider
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

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}