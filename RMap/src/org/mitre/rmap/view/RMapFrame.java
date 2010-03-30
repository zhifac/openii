// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.rmap.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.MappingCell;

import java.net.URI;
import java.util.*;

import org.mitre.harmony.model.preferences.PreferencesListener;
import org.mitre.harmony.view.harmonyPane.TitledPane;
import org.mitre.harmony.view.mappingPane.MappingPane;

import org.mitre.rmap.model.Dependency;
import org.mitre.rmap.model.SQLGenerator;

@SuppressWarnings("serial")
public class RMapFrame extends JInternalFrame implements PreferencesListener {
	/** type of database generated */
	public static String _targetDB;
	public static final String POSTGRES_TYPE = "Postgres";
	public static final String DERBY_TYPE = "Derby";

	/** Stores a reference to the view pane */
	private JPanel viewPane = new JPanel();

	/** Dependencies to be stored */
	private ArrayList<Dependency> dependenciesOrdered;

	/** Stores the Harmony model */
	private RMapHarmonyModel harmonyModel;
	
	private DependencyPane dependencyPane;
	private CorrectionPane correctionPane;
	private Integer dependencyDisplayedIndex;
	
	public RMapFrame(SchemaStoreClient client, Integer elementID) {
		super();

		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		this.setLayout(layout);

		// Generate dependencies
		dependenciesOrdered = Dependency.generate(elementID, client);

		// create the rmap version of the harmony model and initialize the schema list
		harmonyModel = new RMapHarmonyModel(null);
		harmonyModel.getSchemaManager().initSchemas(dependenciesOrdered);

		// Place title on application
		String mappingName = harmonyModel.getProjectManager().getProject().getName();
		setTitle("RMap SQL Generator" + (mappingName != null ? " - " + harmonyModel.getProjectManager().getProject().getName() : ""));

		// Set dialog pane settings
		((javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).setNorthPane(null);
		setBorder(new EmptyBorder(0, 0, 0, 0));
		try { setMaximum(true); } catch (Exception e) {}
		setContentPane(getMainPane());
        setVisible(true);

		// Add a listener to monitor for the closing of the parent frame
		harmonyModel.getPreferences().addListener(this);
	}

	/** Returns the view pane */
	private JPanel getViewPane() {
		// Clear out the action map
		getActionMap().clear();
		
		// Generate the new view
		return new TitledPane(null, (JComponent)new MappingPane(this, harmonyModel));
	}

	/** Generates the main pane */
	private JPanel getMainPane() {
		// initialize the panels, we will need to work with them
		dependencyPane = new DependencyPane(harmonyModel, dependenciesOrdered);
		correctionPane = new CorrectionPane(harmonyModel);
		
		// initialize the various panes shown in the main Harmony pane
		TitledPane dependencyTitledPane = new TitledPane("Dependencies", dependencyPane);
		TitledPane correctionTitledPane = new TitledPane("Corrections", correctionPane);
		
		// initialize the button that generates SQL
        JButton generateSQLButton = new JButton("Generate SQL");
        generateSQLButton.setName("Generate SQL");
        generateSQLButton.addActionListener(new GenerateSQLButtonListener(generateSQLButton));
        generateSQLButton.setEnabled(true);
        
        // create a panel with padding to put the button in
        JPanel generateSQLButtonPanel = new JPanel();
        generateSQLButtonPanel.add(generateSQLButton, JPanel.CENTER_ALIGNMENT);

		// layout the view pane of RMap
		viewPane.setLayout(new BorderLayout());
		viewPane.add(getViewPane(), BorderLayout.CENTER);
		
        // THIS PANEL BASICALLY DEFINES THE ENTIRE WIDTH OF THE RIGHT COLUMN (in this case, it is 250)
		Integer sidePaneMaxWidth = 250;
		Integer sidePaneDependencyMaxHeight = 300;
        Dimension sidePaneSize = new Dimension(sidePaneMaxWidth, 40);
        Dimension sidePaneDependencySize = new Dimension(sidePaneMaxWidth, sidePaneDependencyMaxHeight);
        
        // specify the maximum height of the dependencies frame
        dependencyTitledPane.setPreferredSize(sidePaneDependencySize);
        dependencyTitledPane.setMaximumSize(sidePaneDependencySize);

		// layout the side pane of RMap
		JPanel sidePane = new JPanel();
		sidePane.setLayout(new BorderLayout());
		sidePane.add(dependencyTitledPane, BorderLayout.NORTH);
		sidePane.add(correctionTitledPane, BorderLayout.CENTER);
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
        	// save current work (for visible Dependency)
            Dependency dependDisplayed = null;
            if (dependencyDisplayedIndex >= 0) {
                ArrayList<MappingCell> mappingCells = harmonyModel.getMappingManager().getMappingCells();
                ArrayList<MappingCell> updatedCells = new ArrayList<MappingCell>();
                dependDisplayed = dependenciesOrdered.get(dependencyDisplayedIndex);
                for (MappingCell cell : mappingCells){
                    if (cell.getScore() > -1.0){
                        MappingCell cellCopy = cell.copy();
                        updatedCells.add(cellCopy);
                    }
                }
                dependDisplayed.updateCorrespondences(updatedCells);
                harmonyModel.getSchemaManager().setMappingCells(dependDisplayed, updatedCells);
            }

            correctionPane.getCorrections().setText("");
            ArrayList<String> finalScript = new ArrayList<String>();
            ArrayList<Integer> selectedIndices = new ArrayList<Integer>();

            Object[][] data = dependencyPane.getDependencyTableData(); 
            for (int i = 0; i < data.length; i++) {
            	if ((Boolean)data[i][1]) { selectedIndices.add(i); }
            }

            if (selectedIndices.size() > 0) { 
                ArrayList<Dependency> selectedDepends = new ArrayList<Dependency>();
                for (Integer index : selectedIndices) {
                    selectedDepends.add(dependenciesOrdered.get(index));
                }

                finalScript = new ArrayList<String>(); // SQLGenerator.performErrorChecking(_dependsOrdered, selectedIndices);
                if (finalScript.size() == 0) {
                    finalScript = SQLGenerator.generateFinalSQLScript(harmonyModel.getProjectManager().getProject(), selectedDepends, _targetDB);
                }

                String output = new String();
                for (String str : finalScript) { output += str + "\n"; }
                correctionPane.getCorrections().setText(output);
                correctionPane.getCorrections().setCaretPosition(0);
            } else {
                correctionPane.getCorrections().setText("No Dependencies are selected!");
            }
        }
        
        private void enableButton() {
            if (!alreadyEnabled) { button.setEnabled(true); }    
        }
    } // end class GenerateSQLButtonListener

   	public static void main(String[] args) {
   		javax.swing.SwingUtilities.invokeLater(new Runnable() {
   			public void run() {
	           	/** SET SchemaStoreClient location, Source and Target schemas **/
	       		try { 
	       			Repository serverLocation = new Repository(Repository.POSTGRES, new URI("ygg.mitre.org"),"schemastore","postgres","postgres"); 
	       			SchemaStoreClient client = new SchemaStoreClient(serverLocation);      			
	       			JFrame frame = new JFrame("SQL Mapping Generator");
	       	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	       	        //Create and set up the content pane.
	       	        JComponent newContentPane = new RMapFrame(client, 422130);
	       	        //JComponent newContentPane = new SQLGeneratorGUI(client, 446405);
	       	        newContentPane.setOpaque(true); //content panes must be opaque
	       	        frame.setContentPane(newContentPane);
	
	       	        //Display the window.
	       	        frame.setVisible(true);
	       		} catch (Exception e){
	       			e.printStackTrace();
	       		}
   			} 
       });
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
