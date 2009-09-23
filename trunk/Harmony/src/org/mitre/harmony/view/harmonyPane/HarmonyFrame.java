// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.harmonyPane;

import java.awt.BorderLayout;
import java.util.HashSet;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.preferences.PreferencesListener;
import org.mitre.harmony.view.heatmap.HeatMapPane;
import org.mitre.harmony.view.mappingPane.MappingPane;

/**
 * Displays main Harmony window
 * 
 * @author CWOLF
 */
public class HarmonyFrame extends JInternalFrame implements PreferencesListener
{
	/** Stores a reference to the view pane */
	private JPanel viewPane = new JPanel();

	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Returns the view pane */
	private JPanel getViewPane()
	{
		// Clear out the action map
		getActionMap().clear();

		// Generate the new view
		JComponent view = null;
		switch (harmonyModel.getPreferences().getViewToDisplay())
		{
			case HarmonyConsts.MAPPING_VIEW: view = new MappingPane(this,harmonyModel); break;
			case HarmonyConsts.HEATMAP_VIEW: view = new HeatMapPane(this,harmonyModel); break;
		}
		return new TitledPane(null, view);
	}

	/** Generates the main pane */
	private JPanel getMainPane()
	{
		// Initialize the various panes shown in the main Harmony pane
		TitledPane confidencePane = new TitledPane("Confidence", new ConfidencePane(harmonyModel));
		TitledPane filterPane = new TitledPane("Filters", new FilterPane(harmonyModel));

		// Layout the view pane of Harmony
		viewPane.setLayout(new BorderLayout());
		viewPane.add(getViewPane(), BorderLayout.CENTER);

		// Layout the side pane of Harmony
		JPanel sidePane = new JPanel();
		sidePane.setLayout(new BorderLayout());
		sidePane.add(confidencePane, BorderLayout.CENTER);
		sidePane.add(filterPane, BorderLayout.SOUTH);

		// Generate the main pane of Harmony
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout());
		mainPane.add(viewPane, BorderLayout.CENTER);
		mainPane.add(sidePane, BorderLayout.EAST);
		return mainPane;
	}

	/** Constructs the Harmony pane */
	public HarmonyFrame(HarmonyModel harmonyModel)
	{
		super();
		this.harmonyModel = harmonyModel;
		
		// Place title on application
		String mappingName = harmonyModel.getMappingManager().getMapping().getName();
		setTitle("Harmony Schema Matcher" + (mappingName != null ? " - " + harmonyModel.getMappingManager().getMapping().getName() : ""));

		// Set dialog pane settings
		((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).setNorthPane(null);
		setBorder(new EmptyBorder(0, 0, 0, 0));
		try { setMaximum(true); } catch (Exception e) {}
		setJMenuBar(new HarmonyMenuBar(harmonyModel));
		setContentPane(getMainPane());
		setVisible(true);

		// Add a listener to monitor for the closing of the parent frame
		harmonyModel.getPreferences().addListener(this);
	}

	/** Handles the changing of the displayed view */
	public void displayedViewChanged()
	{
		viewPane.removeAll();
		viewPane.add(getViewPane(), BorderLayout.CENTER);
		viewPane.revalidate();
		viewPane.repaint();
	}

	public void elementsMarkedAsFinished(Integer schemaID, HashSet<Integer> elementIDs) {}
	public void elementsMarkedAsUnfinished(Integer schemaID, HashSet<Integer> elementIDs) {}
	public void showSchemaTypesChanged() {}
}