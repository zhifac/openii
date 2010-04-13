// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.rmap.view;

import java.util.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.MappingCell;

import org.mitre.harmony.model.preferences.PreferencesListener;
import org.mitre.harmony.view.harmonyPane.TitledPane;
import org.mitre.harmony.view.mappingPane.MappingPane;

import org.mitre.rmap.generator.Dependency;
import org.mitre.rmap.model.RMapHarmonyModel;

@SuppressWarnings("serial")
public class RMapFrame extends JInternalFrame implements PreferencesListener {
	/** Stores a reference to the view pane */
	private JPanel viewPane = new JPanel();

	/** Dependencies to be stored */
	private ArrayList<Dependency> dependenciesOrdered;

	/** Stores the Harmony model */
	private RMapHarmonyModel harmonyModel;
	private MappingPane mappingPane;
	private DependencyPane dependencyPane;
	private Integer dependencyDisplayedIndex;

	/** Returns the view pane
	 *  the view pane contains the three column mapping dimensions */
	private JPanel getViewPane() {
		// Clear out the action map
		getActionMap().clear();
		
		// generate a mapping pane
		mappingPane = new MappingPane(this, harmonyModel);

		// Generate the new view
		return new TitledPane(null, (JComponent)mappingPane);
	}

	/** Generates the main pane
	 *  the main pane contains in it both the view pane (see above)
	 *  and the dependencies pane, which is generated in this method */
	private JPanel getMainPane() {
        // THIS PANEL BASICALLY DEFINES THE ENTIRE WIDTH OF THE RIGHT COLUMN (in this case, it is 225)
		Integer sidePaneWidth = 225;
		Integer sidePaneDependencyHeight = 300;
        Dimension sidePaneSize = new Dimension(sidePaneWidth, 40);

		// layout the view pane of RMap
		// the function call to getViewPane() has a side effect of creating the instance of mappingPane
		// i hate functions that have side-effects, but it seems necessary in this case
        // as a result, this MUST go at the top of this method so that we can send an initialized mappingPane to DependencyPane
		viewPane.setLayout(new BorderLayout());
		viewPane.add(getViewPane(), BorderLayout.CENTER);

		// initialize the panels, we will need to work with them
		dependencyPane = new DependencyPane(harmonyModel, mappingPane, dependenciesOrdered);
		TitledPane dependencyTitledPane = new TitledPane("Dependencies", dependencyPane);
        dependencyTitledPane.setPreferredSize(new Dimension(sidePaneWidth, sidePaneDependencyHeight));

		// initialize the button that generates SQL
        JButton generateSQLButton = new JButton("Generate SQL");
        generateSQLButton.setName("Generate SQL");
        generateSQLButton.addActionListener(new GenerateSQLButtonListener(generateSQLButton));
        generateSQLButton.setEnabled(true);

        // create a panel with padding to put the button in
        JPanel generateSQLButtonPanel = new JPanel();
        generateSQLButtonPanel.add(generateSQLButton, JPanel.CENTER_ALIGNMENT);

		// layout the side pane of RMap
		JPanel sidePane = new JPanel();
		sidePane.setLayout(new BorderLayout());
		sidePane.add(dependencyTitledPane, BorderLayout.CENTER);
		sidePane.add(generateSQLButtonPanel, BorderLayout.SOUTH);
		sidePane.setPreferredSize(sidePaneSize);
		sidePane.setMaximumSize(sidePaneSize);

		// generate the main pane of RMap
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout());
		mainPane.add(viewPane, BorderLayout.CENTER);
		mainPane.add(sidePane, BorderLayout.EAST);
		return mainPane;
	}

    class GenerateSQLButtonListener implements ActionListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public GenerateSQLButtonListener(JButton button) {
            this.button = button;
            enableButton();
        }

        public void actionPerformed(ActionEvent e) {
        	Dependency dependenciesDisplayed = null;
        	if (dependencyDisplayedIndex >= 0){
        		ArrayList<MappingCell> mappingCells = harmonyModel.getMappingManager().getMappingCells();
        		ArrayList<MappingCell> updatedCells = new ArrayList<MappingCell>();
        		dependenciesDisplayed = dependenciesOrdered.get(dependencyDisplayedIndex);
        		for (MappingCell cell : mappingCells){
        			if (cell.getScore() > -1.0) {
        				MappingCell cellCopy = cell.copy();
        				updatedCells.add(cellCopy);
        			}
        		}
        		dependenciesDisplayed.updateCorrespondences(updatedCells);
        		harmonyModel.getSchemaManager().setMappingCells(dependenciesDisplayed, updatedCells);
        	}

        	// store what has been checked
        	ArrayList<Integer> selectedIndices = new ArrayList<Integer>();
        	for (int i = 0; i < dependencyPane.getDependencyTableData().length; i++) {
        		if ((Boolean)dependencyPane.getDependencyTableData()[i][0]) { selectedIndices.add(i); }
        	}

        	// if things have been checked, add them to the list of dependencies for our SQL generator
        	if (selectedIndices.size() > 0) {
        		ArrayList<Dependency> selectedDependencies = new ArrayList<Dependency>();
        		for (Integer index : selectedIndices) {
        			selectedDependencies.add(dependenciesOrdered.get(index));
        		}

        		// TODO: add this to the list of the final script
        		// TODO: generate the final script? perhaps ask for what type of output they'd like
        		// TODO: whether that be postgresql or derby or whatever
        	} else {
        		// nothing has been selected
        	}
        }
        
        private void enableButton() {
            if (!alreadyEnabled) { button.setEnabled(true); }    
        }
    } // end class GenerateSQLButtonListener

	public RMapFrame(RMapHarmonyModel harmonyModel, SchemaStoreClient client, Integer elementID) {
		super();
		this.harmonyModel = harmonyModel;

		// Place title on application
		String mappingName = harmonyModel.getProjectManager().getProject().getName();
		setTitle("RMap SQL Generator" + (mappingName != null ? " - " + harmonyModel.getProjectManager().getProject().getName() : ""));

		// Generate dependencies
		dependenciesOrdered = Dependency.generate(client, elementID);

		// initialize the schema list
		harmonyModel.getSchemaManager().initSchemas(dependenciesOrdered);

		// build a layout for the display
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		this.setLayout(layout);

		// Set dialog pane settings
		((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).setNorthPane(null);
		setBorder(new EmptyBorder(0, 0, 0, 0));
		try { setMaximum(true); } catch (Exception e) {}
		setContentPane(getMainPane());
        setVisible(true);

		// Add a listener to monitor for the closing of the parent frame
		harmonyModel.getPreferences().addListener(this);
	}

	public void displayedViewChanged() {
		viewPane.removeAll();
		viewPane.add(getViewPane(), BorderLayout.CENTER);
		viewPane.revalidate();
		viewPane.repaint();
	}

	public void elementsMarkedAsFinished(Integer schemaID, HashSet<Integer> elementIDs) {}
	public void elementsMarkedAsUnfinished(Integer schemaID, HashSet<Integer> elementIDs) {}
	public void showSchemaTypesChanged() {}
	public void alphabetizedChanged() {}
}
