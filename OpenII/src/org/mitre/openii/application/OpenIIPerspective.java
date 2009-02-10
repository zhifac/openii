package org.mitre.openii.application;

import org.eclipse.ui.*;

/** Lays out the OpenII perspective */
public class OpenIIPerspective implements IPerspectiveFactory
{
	/** Initializes the OpenII Layout */
	public void createInitialLayout(IPageLayout layout)
	{
		// Hides the editor pane
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);
		
		// Creates the main pane
		IFolderLayout mainPane = layout.createFolder("main", IPageLayout.RIGHT, 0.75f, layout.getEditorArea());
		mainPane.addView("org.mitre.openii.plugin.HarmonyView");
		mainPane.addView("org.mitre.openii.plugin.GalaxyView");

		// Creates the left pane
		IFolderLayout leftPane = layout.createFolder("left", IPageLayout.LEFT, 0.25f, "main");
		leftPane.addView("org.mitre.openii.plugin.YggView");
		leftPane.addView("org.mitre.openii.plugin.SearchView");
	}
}