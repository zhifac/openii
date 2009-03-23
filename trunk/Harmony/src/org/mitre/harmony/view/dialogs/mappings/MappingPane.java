package org.mitre.harmony.view.dialogs.mappings;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.TitledPane;
import org.mitre.schemastore.model.Mapping;

/** Private class for managing the mapping list */
class MappingPane extends JPanel
{
	/** Comparator used to alphabetize mappings */
	private class MappingComparator implements Comparator<Object>
	{
		public int compare(Object mapping1, Object mapping2)
		{
			if(mapping1.toString()==null) return -1;
			return mapping1.toString().compareTo(mapping2.toString());
		}	
	}
	
	/** Defines how the mappings in the list should be rendered */
	class ListRenderer extends DefaultListCellRenderer
	{
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{
			if(((Mapping)value).getId()==null) value = "<New Mapping>";
			return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		}
	 }
	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Stores the mapping list */
	private JList mappingList = null;
	
	/** Constructs the mapping pane */
	MappingPane(Boolean saveMode, HarmonyModel harmonyModel)
	{
		this.harmonyModel = harmonyModel;
		
		// Retrieve the list of mappings
		Vector<Mapping> mappings = new Vector<Mapping>(harmonyModel.getSchemaManager().getAvailableMappings());
		Collections.sort(mappings, new MappingComparator());
		if(saveMode)
		{
			Mapping newMapping = harmonyModel.getMappingManager().getMapping().copy();
			newMapping.setId(null); newMapping.setName("");
			mappings.add(0,newMapping);
		}
		
		// Initializes the mapping list
		mappingList = new JList(mappings);
		mappingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mappingList.setCellRenderer(new ListRenderer());
		Mapping mapping = harmonyModel.getMappingManager().getMapping();
		if(saveMode && mapping.getId()!=null) mappingList.setSelectedValue(mapping, true);
		if(mappingList.getSelectedIndex()<0 && mappings.size()>0) mappingList.setSelectedIndex(0);
		
		// Create a scroll pane to hold the mapping list
		JScrollPane mappingScrollPane = new JScrollPane(mappingList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mappingScrollPane.setPreferredSize(new Dimension(130,200));
		JPanel mappingListPane = new TitledPane("Mappings",mappingScrollPane);
		
		// Create the mapping list pane
		setLayout(new BorderLayout());
		add(mappingListPane,BorderLayout.CENTER);
		
		// Set up the ability to delete mappings
		KeyStroke deleteKey = KeyStroke.getKeyStroke((char) KeyEvent.VK_DELETE);
		getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(deleteKey, "deleteLink");
		getActionMap().put("deleteLink", new DeleteMapping());
	}
	
	/** Returns the selected mapping */
	Mapping getMapping()
		{ return (Mapping)mappingList.getSelectedValue(); }	
	
	/** Adds a list listener */
	void addListSelectionListener(ListSelectionListener listener)
		{ mappingList.addListSelectionListener(listener); }
	
	/** Handles the deletion of a mapping */
	private class DeleteMapping extends AbstractAction
	{
		/** Deletes selected mapping */
		public void actionPerformed(ActionEvent arg0)
		{
			// Gets the selected mapping
			Mapping mapping = getMapping();
			if(mapping.getId()!=null)
			{
				// Ask user before deleting mapping
				int option = JOptionPane.showConfirmDialog(harmonyModel.getBaseFrame(),
		    		"Continue with deletion of mapping \"" + mapping.getName() + "\"?",
					"Delete Mapping", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
				if(option==2) return;
	
				// Delete the selected mapping
				if(harmonyModel.getMappingManager().deleteMapping(mapping.getId()))
				{
					// Generate a list of mappings with the specified mapping removed
					Vector<Mapping> mappings = new Vector<Mapping>();
					for(int i=0; i<mappingList.getModel().getSize(); i++)
						mappings.add((Mapping)mappingList.getModel().getElementAt(i));
					mappings.remove(mapping);
				
					// Reset the list
					mappingList.setListData(mappings);
					mappingList.setSelectedIndex(0);
				}
			}
		}
	}
}