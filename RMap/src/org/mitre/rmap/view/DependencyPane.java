package org.mitre.rmap.view;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.selectedInfo.SelectedInfoListener;
import org.mitre.harmony.view.mappingPane.MappingPane;
import org.mitre.rmap.generator.Dependency;
import org.mitre.rmap.model.RMapHarmonyModel;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.ProjectSchema;

@SuppressWarnings("serial")
public class DependencyPane extends JPanel implements SelectedInfoListener {

	/** Stores the Harmony model */
	private RMapHarmonyModel harmonyModel;
	private MappingPane mappingPane;
	
	/** Dependencies to be stored */
	private ArrayList<Dependency> dependenciesOrdered;
	
	/** Dependency Table Data */
	private Object[][] dependencyTableData;
	private Integer dependencyDisplayedIndex = null;

	public void setDependencyTableData(Object[][] dependencyTableData) {
		this.dependencyTableData = dependencyTableData;
	}

	public Object[][] getDependencyTableData() {
		return dependencyTableData;
	}

	/** Initializes the dependency pane */
	public DependencyPane(RMapHarmonyModel harmonyModel, MappingPane mappingPane, ArrayList<Dependency> dependenciesOrdered) {
		this.harmonyModel = harmonyModel;
		this.mappingPane = mappingPane;
		this.dependenciesOrdered = dependenciesOrdered; 

		// build the dependency table
        // create the Dependency Table and put in a scroll pane
        setDependencyTableData(new Object[dependenciesOrdered.size()][2]);
        for (int i = 0; i < dependenciesOrdered.size(); i++) {
            getDependencyTableData()[i][0] = new Boolean(false);
            getDependencyTableData()[i][1] = new String(
                    i +
                    " -- [" + 
                            dependenciesOrdered.get(i).getSourceLogicalRelation().getMappingSchemaEntitySet().get(0).getName() +
                    "][" + 
                            dependenciesOrdered.get(i).getTargetLogicalRelation().getMappingSchemaEntitySet().get(0).getName() +
                    "]"
            );
        }
        
        DependencyTableModel dependencyTableModel = new DependencyTableModel(getDependencyTableData());
        JTable dependencyTable = new JTable(dependencyTableModel);

        // add a selection listener (defined below) to handle selecting or checking things
        SelectionListener selListener = new SelectionListener(dependencyTable);
        dependencyTable.getSelectionModel().addListSelectionListener(selListener);
        dependencyTable.getColumnModel().getSelectionModel().addListSelectionListener(selListener);

        // only allow selecting one thing at a time
        dependencyTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // specify some column widths, this is for the checkbox column
        dependencyTable.getColumnModel().getColumn(0).setMaxWidth(20);
        
        // make it so that if the list of dependencies gets long we can scroll to see them all
        JScrollPane dependencyListScrollPane = new JScrollPane(dependencyTable);

		setLayout(new BorderLayout());
		add(dependencyListScrollPane,BorderLayout.CENTER);
		harmonyModel.getSelectedInfo().addListener(this);
	}

	private class SelectionListener implements ListSelectionListener {
        JTable table;

        // It is necessary to keep the table since it is not possible
        // to determine the table from the event's source
        private SelectionListener(JTable table) {
        	this.table = table;
        }

        public void valueChanged(ListSelectionEvent e) {
        	if (e.getValueIsAdjusting()) {
        		// The mouse button has not yet been released
                return;
        	}

        	// save current work for visible dependency
        	Dependency dependencyDisplayed = null;
        	if (dependencyDisplayedIndex != null) {
        		ArrayList<MappingCell> mappingCells = harmonyModel.getMappingManager().getMappingCells();
        		ArrayList<MappingCell> updatedCells = new ArrayList<MappingCell>();
        		dependencyDisplayed = dependenciesOrdered.get(dependencyDisplayedIndex);
        		for (MappingCell cell : mappingCells) {
        			if (cell.getScore() > -1.0) {
        				MappingCell cellCopy = cell.copy();
        				updatedCells.add(cellCopy);
        			}
        		}

        		dependencyDisplayed.updateCorrespondences(updatedCells);
        		harmonyModel.getSchemaManager().setMappingCells(dependencyDisplayed, updatedCells);
        	}

    		// clicked on the dependency, so show it on the schema list
        	if (table.getSelectedColumn() == 1 && dependenciesOrdered != null) {
    			dependencyDisplayedIndex = table.getSelectedRow(); // save the value of the currently displayed dependency
        		dependencyDisplayed = dependenciesOrdered.get(dependencyDisplayedIndex);
    			Integer displayedMappingID = harmonyModel.getSchemaManager().getMappingID(dependencyDisplayed);

    			if (displayedMappingID != null) {
    				// get the new schemas
    				ArrayList<ProjectSchema> projectSchemas = new ArrayList<ProjectSchema>();
    				ProjectSchema sourceProjectSchema = harmonyModel.getSchemaManager().getSourceSchema(displayedMappingID);
    				ProjectSchema targetProjectSchema = harmonyModel.getSchemaManager().getTargetSchema(displayedMappingID);
    				projectSchemas.add(sourceProjectSchema);
    				projectSchemas.add(targetProjectSchema);

    				// retrieve old/new mapping and mapping cell information
    				Mapping newMapping = harmonyModel.getSchemaManager().getMapping(displayedMappingID);
    				ArrayList<MappingCell> newMappingCells = harmonyModel.getSchemaManager().getMappingCells(displayedMappingID);

    				// delete the old mapping cell information
    				harmonyModel.getMappingManager().removeAllMappings();

    				// set the new schema and mapping information
    				harmonyModel.getProjectManager().setSchemas(projectSchemas);
    				harmonyModel.getMappingManager().addMapping(newMapping);
    				harmonyModel.getMappingManager().setMappingCells(newMappingCells);

    				// show the mappings and expand the tree to display everything (including all fields in each schema)
    				harmonyModel.getMappingManager().getMapping(displayedMappingID).setVisibility(true);
    				
    				mappingPane.getTree(HarmonyConsts.LEFT).expandNode(mappingPane.getTree(HarmonyConsts.LEFT).root);
    				mappingPane.getTree(HarmonyConsts.RIGHT).expandNode(mappingPane.getTree(HarmonyConsts.RIGHT).root);

    				harmonyModel.getProjectManager().setModified(false);
    			}
        	}
        }
	} // end SelectionListener class

	public void displayedElementModified(Integer role) {
		// TODO Auto-generated method stub
	}

	public void selectedElementsModified(Integer role) {
		// TODO Auto-generated method stub
	}

	public void selectedMappingCellsModified() {
		// TODO Auto-generated method stub
	}
}
