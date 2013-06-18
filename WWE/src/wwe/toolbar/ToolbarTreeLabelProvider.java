package wwe.toolbar;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import fhv.eclipse2013.wwe.contract.toolbox.ITool;
import fhv.eclipse2013.wwe.contract.toolbox.IToolElement;

public class ToolbarTreeLabelProvider extends LabelProvider {
	@Override
	public String getText(Object element) {
		if (element instanceof ITool) {
			return ((ITool) element).getName();
		}
		return "ERROR";
	}

	public Image getImage(Object obj) {
		if (obj instanceof IToolElement) {
			String img = ((IToolElement) obj).getImage();
			if (!img.equals("")) {
				try {
					ImageDescriptor image = ImageDescriptor.createFromFile(
							null, img);
					image = ImageDescriptor.createFromImageData(image
							.getImageData().scaledTo(25, 25));
					return image.createImage();
				} catch (Exception ex) {
				}
			}
		}
		Image img = PlatformUI.getWorkbench().getSharedImages()
				.getImage(ISharedImages.IMG_OBJ_FOLDER);
		ImageDescriptor image = ImageDescriptor.createFromImageData(img
				.getImageData().scaledTo(25, 25));
		return image.createImage();
	}
}
