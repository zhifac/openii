// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.rmap.view;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableModel;

import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.*;
import java.net.URI;
import java.util.*;

import org.mitre.harmony.model.project.ProjectListener;
import org.mitre.harmony.view.mappingPane.MappingPane;

import org.mitre.rmap.model.Dependency;
import org.mitre.rmap.model.SQLGenerator;

@SuppressWarnings("serial")
public class RMapGUI extends JPanel implements ProjectListener {
	/** type of database generated */
	public static String _targetDB;
	public static final String POSTGRES_TYPE = "Postgres";
	public static final String DERBY_TYPE = "Derby";
	
	/** Dependencies to be stored */
	private ArrayList<Dependency> _dependsOrdered;
	
	/** Stores the Harmony model */
	private RMapHarmonyModel _harmonyModel;
	private MappingPane _mappingPane;
	
    private Integer _dependDisplayedIndex;   
    private JTextPane _scriptViewArea;
    private JTable _dependencyTable;
    
    /** Indicates that a schema has been added to the mapping */
	public void schemaAdded(Integer schemaID){}
	
	/** Indicates that a schema has been removed from the mapping */
	public void schemaRemoved(Integer schemaID){}
	
	/** Indicates that the mapping has been modified */
	public void mappingModified(){}
    
	public RMapGUI(SchemaStoreClient client, Integer elementID) {
		super();

		// TODO: set the targetDB type
		_targetDB = DERBY_TYPE;
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		this.setLayout(layout);
	
		/** Generate dependencies */
		_dependsOrdered = Dependency.generate(elementID, client);
		_dependDisplayedIndex = -1;
		
		/** Initialize mapping pane data structures */
		_harmonyModel = new RMapHarmonyModel(null);
        _mappingPane = new MappingPane(this, _harmonyModel);
        _harmonyModel.getProjectManager().addListener(this);
		_harmonyModel.getSchemaManager().initSchemas(_dependsOrdered);
		
		/** Create the Dependency Table and put in a scroll pane */
		Object[][] dependTableData = new Object[_dependsOrdered.size()][2];
		for (int i=0; i<_dependsOrdered.size(); i++){
			dependTableData[i][0] = new String(i + " -- [" 
					+ _dependsOrdered.get(i).getSourceLogRel().getMappingSchemaEntitySet().get(0).getName() + "][" 
					+ _dependsOrdered.get(i).getTargetLogRel().getMappingSchemaEntitySet().get(0).getName() +"]");
			dependTableData[i][1] = new Boolean(false);
		}
		
		DependTableModel _dependTableModel = new DependTableModel(dependTableData);
		_dependencyTable = new JTable(_dependTableModel);       
		SelectionListener selListener = new SelectionListener(_dependencyTable);
		_dependencyTable.getSelectionModel().addListSelectionListener(selListener);
		_dependencyTable.getColumnModel().getSelectionModel().addListSelectionListener(selListener);
		_dependencyTable.setCellSelectionEnabled(true);
		_dependencyTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane dependencyListScrollPane = new JScrollPane(_dependencyTable);
     
       
        /** create checkbox */
        JRadioButton derbyButton = new JRadioButton(RMapGUI.DERBY_TYPE,true);
        derbyButton.setName(RMapGUI.DERBY_TYPE);
        derbyButton.setSelected(true);
        JRadioButton postgresButton = new JRadioButton(RMapGUI.POSTGRES_TYPE,true);
        postgresButton.setName(RMapGUI.POSTGRES_TYPE);

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(derbyButton);
        group.add(postgresButton);
  
        //Register a listener for the radio buttons.
        derbyButton.addActionListener(new SelectDBListener(derbyButton));
        postgresButton.addActionListener(new SelectDBListener(postgresButton));
        
        JPanel selectDbPanel = new JPanel();
        GroupLayout selectDbPanelLayout = new GroupLayout(selectDbPanel);
        selectDbPanelLayout.setAutoCreateGaps(true);
        selectDbPanel.setLayout(selectDbPanelLayout);
        selectDbPanelLayout.setHorizontalGroup(
        		selectDbPanelLayout.createSequentialGroup()
        		.addGroup(selectDbPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER,true)
        				.addComponent(derbyButton,110,110,110)
        				.addComponent(postgresButton,110,110,110)
           ));
            
        selectDbPanelLayout.setVerticalGroup(
        		selectDbPanelLayout.createSequentialGroup()
        			.addComponent(derbyButton,20,20,20)
        			.addComponent(postgresButton,20,20,20)
            );
       
        
        /** create the "Generate SQL" button */
        JPanel buttonPane = new JPanel();
        JButton generateSQLButton = new JButton("Generate SQL");
		generateSQLButton.setName("Generate SQL");
        generateSQLButton.addActionListener(new ButtonListener(generateSQLButton));
        generateSQLButton.setEnabled(true);
 
        /** create Buttons in button pane and set layout*/
	    GroupLayout buttonPaneLayout = new GroupLayout(buttonPane);
	    buttonPaneLayout.setAutoCreateGaps(true);
        buttonPaneLayout.setAutoCreateContainerGaps(true);
        buttonPane.setLayout(buttonPaneLayout);
        buttonPaneLayout.setHorizontalGroup(
        	buttonPaneLayout.createSequentialGroup()
            	.addComponent(generateSQLButton,100,100,100)
            	.addComponent(selectDbPanel,100,100,100)
            );
            
        buttonPaneLayout.setVerticalGroup(
        	buttonPaneLayout.createSequentialGroup()
        		.addGroup(buttonPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE,true)
        			.addComponent(generateSQLButton,30,30,30)
        			.addComponent(selectDbPanel,50,50,50)
            ));
       
        
        
        
        _scriptViewArea = new JTextPane();
        _scriptViewArea.setText("Select the set of correct dependencies");
        JScrollPane textPane = new JScrollPane(_scriptViewArea);
           
        JPanel controlPanel = new JPanel();
        GroupLayout controlPanelLayout = new GroupLayout(controlPanel);
        controlPanelLayout.setAutoCreateGaps(true);
        controlPanelLayout.setAutoCreateContainerGaps(true);
		controlPanel.setLayout(controlPanelLayout);
		
		controlPanelLayout.setHorizontalGroup(
		    controlPanelLayout.createSequentialGroup()
	        	// control panel (dependency list, buttons, code screen)
	        	.addGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
	        	    .addComponent(dependencyListScrollPane,200,300,300)
	        		.addComponent(buttonPane,200,300,300)
	        		.addComponent(textPane,200,300,300))
	        );
	        
	    controlPanelLayout.setVerticalGroup(
	        controlPanelLayout.createSequentialGroup()
	        	.addComponent(dependencyListScrollPane,200,300,2048)     
	        	.addComponent(buttonPane,54,54,54)
	        	.addComponent(textPane,284,384,2048)
	        );

		
	   
        
        /** set layout for top-level RMap panel */
        layout.setHorizontalGroup(
        	layout.createSequentialGroup()
        		.addComponent(controlPanel,212,212,312)
        		.addComponent(_mappingPane,412,512,2048)
        );
        
        layout.setVerticalGroup(
        	layout.createSequentialGroup()
        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE,true)
        		    .addComponent(controlPanel,568,768,2048)
        		    .addComponent(_mappingPane,568,768,2048))
        );
        
        setVisible(true); 
    }
	
	
	
	class SelectDBListener implements ActionListener {
		private JRadioButton button;

		public SelectDBListener(JRadioButton button) {
			this.button = button;
		}
		public void actionPerformed(ActionEvent e) {
			if (button.getName().equals(RMapGUI.DERBY_TYPE)) {
				_targetDB = RMapGUI.DERBY_TYPE;
			} else if (button.getName().equals(RMapGUI.POSTGRES_TYPE)) {
				_targetDB = RMapGUI.POSTGRES_TYPE;
			}
		}
	}
	
	class ButtonListener implements ActionListener {
		private boolean alreadyEnabled = false;
		private JButton button;

		public ButtonListener(JButton button) {
			this.button = button;
			enableButton();
		}
		
		//Required by ActionListener.
		public void actionPerformed(ActionEvent e) {
    	   
	    	// save current work (for visible Dependency)
	   		Dependency dependDisplayed = null;
	   		if (_dependDisplayedIndex >= 0){
	   			ArrayList<MappingCell> mappingCells = _harmonyModel.getMappingManager().getMappingCells();
	   			ArrayList<MappingCell> updatedCells = new ArrayList<MappingCell>();
	   			dependDisplayed = _dependsOrdered.get(_dependDisplayedIndex);
	   			for (MappingCell cell : mappingCells){
	   				if (cell.getScore() > -1.0){
	   					MappingCell cellCopy = cell.copy();
	   					updatedCells.add(cellCopy);
	   				}
	   			}
	   			dependDisplayed.updateCorrespondences(updatedCells);
	   			_harmonyModel.getSchemaManager().setMappingCells(dependDisplayed, updatedCells);
	   		}
    	   
	   		_scriptViewArea.setText("");
	   		ArrayList<String> finalScript = new ArrayList<String>();
	   		ArrayList<Integer> selectedIndices = new ArrayList<Integer>();
    	   
   			Object[][] data = ((DependTableModel)_dependencyTable.getModel()).getData(); 
   			for (int i = 0; i< data.length; i++)
   				if ((Boolean)data[i][1]) selectedIndices.add(i);
   		
	   		
	   		if (selectedIndices.size() > 0){ 
	   			ArrayList<Dependency> selectedDepends = new ArrayList<Dependency>();
	   			for (Integer index : selectedIndices)
	   				selectedDepends.add(_dependsOrdered.get(index));
	   			
	   			finalScript = new ArrayList<String>(); // SQLGenerator.performErrorChecking(_dependsOrdered, selectedIndices);
	   			if (finalScript.size() == 0)
	   				finalScript = SQLGenerator.generateFinalSQLScript(_harmonyModel.getProjectManager().getProject(), selectedDepends, _targetDB);
	   			
	   			String output = new String();
	   			for (String str : finalScript) output += str + "\n";
	   			_scriptViewArea.setText(output);
	   			_scriptViewArea.setCaretPosition(0);

	   		}
	   		else
	   			_scriptViewArea.setText("No Dependencies are selected!");
	   		
		}
		
		private void enableButton() {
			if (!alreadyEnabled) button.setEnabled(true);    
		}
       
     
   } // end class GenerateButtonListener

   class SelectionListener implements ListSelectionListener {
	   JTable table;
	   
       // It is necessary to keep the table since it is not possible
       // to determine the table from the event's source
	   SelectionListener(JTable table) {
           this.table = table;
       }
	
       public void valueChanged(ListSelectionEvent e) {
		
    	   if (e.getValueIsAdjusting()) {
               // The mouse button has not yet been released
    		   return;
           }

			Dependency dependDisplayed = null;
			if (_dependDisplayedIndex >= 0){
				ArrayList<MappingCell> mappingCells = _harmonyModel.getMappingManager().getMappingCells();
				ArrayList<MappingCell> updatedCells = new ArrayList<MappingCell>();
				dependDisplayed = _dependsOrdered.get(_dependDisplayedIndex);
				for (MappingCell cell : mappingCells){
					if (cell.getScore() > -1.0){
						MappingCell cellCopy = cell.copy();
						updatedCells.add(cellCopy);
					}
				}
				dependDisplayed.updateCorrespondences(updatedCells);
				_harmonyModel.getSchemaManager().setMappingCells(dependDisplayed, updatedCells);
			}
		
			// update what LogicalRelation "schemas" which are displayed
		    int rowIndex = table.getSelectedRow();
		    int colIndex = table.getSelectedColumn();
		    
			if (colIndex == 1 )
				((TableModel)table.getModel()).setValueAt( !((Boolean)((TableModel)table.getModel()).getValueAt(rowIndex,colIndex)), rowIndex, colIndex);
			
			else if (_dependsOrdered != null && colIndex == 0){        
				_dependDisplayedIndex = rowIndex;
				dependDisplayed = _dependsOrdered.get(_dependDisplayedIndex);
		        if (_harmonyModel.getSchemaManager()._mappingID_by_Dependency != null) {
		        	Integer mappingIDdisplayed = _harmonyModel.getSchemaManager()._mappingID_by_Dependency.get(dependDisplayed);
		        	if (mappingIDdisplayed != null){
		        		// TODO: do something here to load the new schemas
		        	} 
		        } 	
			} 
		}
   	} // end DependTableListener class

   	public static void main(String[] args) {
   		javax.swing.SwingUtilities.invokeLater(new Runnable() {
   			public void run() {
	           	/** SET SchemaStoreClient location, Source and Target schemas **/
	       		try { 
	       			Repository serverLocation = new Repository(Repository.POSTGRES,new URI("ygg.mitre.org"),"schemastore","postgres","postgres"); 
	       			SchemaStoreClient client = new SchemaStoreClient(serverLocation);      			
	       			JFrame frame = new JFrame("SQL Mapping Generator");
	       	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	       	        //Create and set up the content pane.
	       	        JComponent newContentPane = new RMapGUI(client, 422130);
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

   	public void schemaModified(Integer schemaID) {
	   // TODO Auto-generated method stub
   	}

	public void projectModified() {
		// TODO Auto-generated method stub
		
	}

	public void schemaModelModified(Integer schemaID) {
		// TODO Auto-generated method stub
		
	}
}
