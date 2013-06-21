package wwe;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import wwe.toolbar.ToolbarTreeView;

public class Perspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);
		layout.setFixed(true);

		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT,
				0.15f, layout.getEditorArea());
		//left.addView(ToolbarView.ID);
		left.addView(ToolbarTreeView.ID);
	}

}
