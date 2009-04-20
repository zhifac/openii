package org.mitre.openii.application;

import org.eclipse.ui.*;

/** Lays out the OpenII perspective */
public class OpenIIPerspective implements IPerspectiveFactory
{
	/** Initializes the OpenII Layout */
	public void createInitialLayout(IPageLayout layout)
	{
		// Hides the editor pane
		layout.setFixed(true);

		// Creates the left pane
		IFolderLayout leftPane = layout.createFolder("left", IPageLayout.LEFT, 0.25f, layout.getEditorArea());
		leftPane.addView("org.mitre.openii.views.manager.ManagerView");
		leftPane.addView("org.mitre.openii.views.SearchView");
		
		// Creates the repositories pane
		layout.addStandaloneView("org.mitre.openii.views.repositories.RepositoryView", true, IPageLayout.TOP, 0.20f, "left");
	}
}