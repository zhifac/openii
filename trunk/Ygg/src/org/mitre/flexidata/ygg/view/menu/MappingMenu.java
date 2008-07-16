// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.menu;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.mitre.flexidata.ygg.model.MappingManager;
import org.mitre.schemastore.model.Mapping;

/** Class for displaying the mapping menu pane */
public class MappingMenu extends JPanel
{	
	/** Stores the mapping list */
	private JList mappingList = null;
	
	/** Comparator for sorting mappings */
	private class MappingComparator implements Comparator<Mapping>
	{
		public int compare(Mapping mapping1, Mapping mapping2)
			{ return mapping1.getName().compareTo(mapping2.getName()); }		
	}

	/** Constructs the mapping menu pane */
	public MappingMenu()
	{	
		// Generate the list of items to be managed
		ArrayList<Mapping> mappings = MappingManager.getMappings();
		Collections.sort(mappings,new MappingComparator());
		mappingList = new JList(mappings.toArray(new Mapping[0]));

		// Generate the scroll pane for showing the items being managed
		JScrollPane mappingListPane = new JScrollPane();
		mappingListPane.setViewportView(mappingList);
		mappingListPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mappingListPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		// Generate the mapping menu pane
		setBorder(new EmptyBorder(0,0,10,0));
		setOpaque(false);
		setLayout(new BorderLayout());
		add(mappingListPane,BorderLayout.CENTER);
	}

	/** Deletes the specified mapping */
	public void deleteMapping(Mapping mapping)
	{
		int response = JOptionPane.showConfirmDialog(null,"Delete the '"+mapping.getName()+"' mapping?","Delete Mapping",JOptionPane.YES_NO_OPTION);
		if(response==0 && MappingManager.deleteMapping(mapping.getId()))
			((DefaultListModel)mappingList.getModel()).removeElement(mapping);
	}
}