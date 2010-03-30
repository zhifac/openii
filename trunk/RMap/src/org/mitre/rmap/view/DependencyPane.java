package org.mitre.rmap.view;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import org.mitre.harmony.model.selectedInfo.SelectedInfoListener;
import org.mitre.rmap.model.Dependency;
import org.mitre.schemastore.model.MappingCell;

public class DependencyPane extends JPanel implements SelectedInfoListener {

	/** Stores the Harmony model */
	private RMapHarmonyModel harmonyModel;
	
	/** Dependencies to be stored */
	private ArrayList<Dependency> dependenciesOrdered;
	
	/** Dependency Table Data */
	private Object[][] dependencyTableData;
	private Integer dependencyDisplayedIndex;

	public void setDependencyTableData(Object[][] dependencyTableData) {
		this.dependencyTableData = dependencyTableData;
	}

	public Object[][] getDependencyTableData() {
		return dependencyTableData;
	}

	/** Initializes the dependency pane */
	public DependencyPane(RMapHarmonyModel harmonyModel, ArrayList<Dependency> dependenciesOrdered) {
		this.harmonyModel = harmonyModel;

		// build the dependency table
        // create the Dependency Table and put in a scroll pane
        setDependencyTableData(new Object[dependenciesOrdered.size()][2]);
        for (int i = 0; i < dependenciesOrdered.size(); i++) {
            getDependencyTableData()[i][0] = new Boolean(false);
            getDependencyTableData()[i][1] = new String(
                    i +
                    " -- [" + 
                            dependenciesOrdered.get(i).getSourceLogRel().getMappingSchemaEntitySet().get(0).getName() +
                    "][" + 
                            dependenciesOrdered.get(i).getTargetLogRel().getMappingSchemaEntitySet().get(0).getName() +
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

        // specify some column widths
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
        	if (!e.getValueIsAdjusting()) {
        		// The mouse button has not yet been released
                return;
        	}

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
	 
    		// update what LogicalRelation "schemas" which are displayed
        	int rowIndex = table.getSelectedRow();
        	int colIndex = table.getSelectedColumn();
	 
        	if (colIndex == 1) {
        		((TableModel)table.getModel()).setValueAt(!((Boolean)((TableModel)table.getModel()).getValueAt(rowIndex,colIndex)), rowIndex, colIndex);
        	} else if (dependenciesOrdered != null && colIndex == 0){        
        		dependencyDisplayedIndex = rowIndex;
        		dependenciesDisplayed = dependenciesOrdered.get(dependencyDisplayedIndex);
        		if (harmonyModel.getSchemaManager()._mappingID_by_Dependency != null) {
        			Integer mappingIDdisplayed = harmonyModel.getSchemaManager()._mappingID_by_Dependency.get(dependenciesDisplayed);
        			if (mappingIDdisplayed != null) {
        				// TODO: do something here to load the new schemas
        			}
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
