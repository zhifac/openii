package org.openii.schemr.client;


import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.openii.schemr.client.view.ResultVizView;
import org.openii.schemr.client.view.SearchBoxView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		
		layout.addStandaloneView(SearchBoxView.ID,  true, IPageLayout.LEFT, 0.25f, editorArea);

		layout.addStandaloneView(ResultVizView.ID,  true, IPageLayout.RIGHT, 0.70f, editorArea);
		

		// TODO: make selection an "action" and use folder view!
//		IFolderLayout folder = layout.createFolder("results", IPageLayout.RIGHT, 065f, editorArea);
//		folder.addPlaceholder(ResultVizView.ID + ":*");
//		folder.addView(ResultVizView.ID);
		
		layout.getViewLayout(SearchBoxView.ID).setCloseable(false);
		layout.getViewLayout(ResultVizView.ID).setCloseable(false);
	}
}
