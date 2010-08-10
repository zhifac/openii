package org.mitre.openii.application;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

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
		layout.addStandaloneView("RepositoryView", true, IPageLayout.TOP, 0.20f, "left");
		leftPane.addView("ManagerView");
	}
}