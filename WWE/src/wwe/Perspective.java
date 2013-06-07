package wwe;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);

		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.15f, layout.getEditorArea());
		IFolderLayout right = layout.createFolder("right", IPageLayout.RIGHT, 0.85f, layout.getEditorArea());

		left.addView(ToolbarView.ID);
		right.addView(WireworldView.ID);
	}

}
