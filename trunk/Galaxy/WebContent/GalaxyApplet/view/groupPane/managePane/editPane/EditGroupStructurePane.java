// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane.managePane.editPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.mitre.schemastore.model.Group;

import view.sharedComponents.EditPaneInterface;

import model.Groups;

/** Class for editing a schema attribute's domain values */
public class EditGroupStructurePane extends EditPaneInterface implements ActionListener, CaretListener, ListSelectionListener
{
	/** Stores the group whose structure is being edited */
	private Integer groupID;
	
	/** Stores the list of sub groups associated with this group */
	private ArrayList<Group> subgroups = new ArrayList<Group>();
	
	/** Stores the list of sub groups deleted from this group */
	private ArrayList<Group> deletedSubgroups = new ArrayList<Group>();
	
	// Stores various pane objects
	private JList groupList = new JList();
	private JTextField name = new JTextField();
	private JButton button = new JButton("Add Group");
	
	/** Constructs the group list pane */
	private JScrollPane getGroupListPane()
	{
		groupList.setFont(new Font(null,Font.PLAIN,12));
		
		JScrollPane pane = new JScrollPane();
		pane.setBackground(Color.white);
		pane.setPreferredSize(new Dimension(100,125));
		pane.setViewportView(groupList);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		return pane;
	}
	
	/** Constructs the edit value pane */
	private JPanel getEditValuePane()
	{
		// Constructs the name pane
		NamePane namePane = new NamePane(name);
		namePane.setBorder(new EmptyBorder(0,2,0,5));
		
		// Initialize the value button pane
		button.setBorder(new CompoundBorder(new LineBorder(Color.gray),new EmptyBorder(1,5,1,5)));
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BorderLayout());
		buttonPane.setOpaque(false);
		buttonPane.add(button,BorderLayout.CENTER);
		
		// Display the list of domain values
		JPanel pane = new JPanel();
		pane.setBorder(new CompoundBorder(new EmptyBorder(2,0,2,1),new CompoundBorder(new LineBorder(Color.lightGray),new EmptyBorder(2,2,2,2))));
		pane.setOpaque(false);
		pane.setLayout(new BorderLayout());
		pane.add(namePane,BorderLayout.CENTER);
		pane.add(buttonPane,BorderLayout.EAST);
		return pane;
	}
	
	/** Updates the sub group list */
	private void updateSubgroupList()
	{
		// Reorder the subgroups
		Groups.sort(subgroups);
		
		// Generate the sub group items
		Vector<Object> valueItems = new Vector<Object>();
		valueItems.add("<New Group>");
		valueItems.addAll(subgroups);
		
		// Generate the sub group list
		groupList.setListData(valueItems);
		groupList.setSelectedIndex(0);
		groupList.addListSelectionListener(this);
	}
	
	/** Constructs the Domain Value Pane */
	public EditGroupStructurePane(Integer groupID)
	{
		this.groupID = groupID;
		
		// Copy over groups to be edited (this is done so edits won't affect true data model)
		ArrayList<Group> tempGroups;
		if(groupID==null) tempGroups = Groups.getBaseGroups();
		else tempGroups = Groups.getSubGroups(groupID);
		for(Group tempGroup : tempGroups)
			subgroups.add(new Group(tempGroup.getId(),tempGroup.getName(),tempGroup.getParentId()));
		updateSubgroupList();

		// Constructs the label pane
		JPanel labelPane = new JPanel();
		labelPane.setBorder(new EmptyBorder(0,0,2,0));
		labelPane.setOpaque(false);
		labelPane.setLayout(new BorderLayout());
		labelPane.add(new JLabel(groupID==null ? "Base Groups" : "Group: " + Groups.getGroup(groupID).getName()),BorderLayout.CENTER);
		
		// Constructs the values pane
		JPanel valuesPane = new JPanel();
		valuesPane.setOpaque(false);
		valuesPane.setLayout(new BorderLayout());
		valuesPane.add(getGroupListPane(),BorderLayout.CENTER);
		valuesPane.add(getEditValuePane(),BorderLayout.SOUTH);
		
		// Constructs the main pane
		JPanel pane = new JPanel();
		pane.setOpaque(false);
		pane.setLayout(new BorderLayout());
		pane.add(labelPane,BorderLayout.NORTH);
		pane.add(valuesPane,BorderLayout.CENTER);
		
		// Construct the group structure pane
		setBorder(new EmptyBorder(0,3,0,0));
		setLayout(new BorderLayout());
		setOpaque(false);
		add(pane);
		
		// Add listeners to various pane items
		name.addCaretListener(this);
		button.addActionListener(this);
	}
	
	/** Handles changes to the selected group */
	public void valueChanged(ListSelectionEvent e)
	{
		// Get the selected object
		Object object = groupList.getSelectedValue();
		if(object==null) return;
		
		// Adjust the information in the name field
		if(object instanceof Group)
		{
			Group group = (Group)object;
			name.setText(group.getName());
		}
		else { name.setText(""); }
		
		// Identify what button options are available
		Group group = (object instanceof Group) ? (Group)object : null;
		boolean newValue = group==null;
		boolean enabled = newValue || (Groups.getSubGroups(group.getId()).size()==0 && Groups.getGroupSchemas(group.getId()).size()==0);

		// Adjust the button configuration		
		button.setText(newValue?"Add Group":"Delete Group");
		button.setEnabled(enabled);
		name.setEditable(true);
		validate();
	}
	
	/** Handles the pressing of the button */
	public void actionPerformed(ActionEvent e)
	{
		// Add a new value
		if(button.getText().equals("Add Group"))
		{
			// Don't proceed if name field is left empty or is duplicate
			name.setBackground(Color.white);
			if(name.getText().length()==0)
				{ name.setBackground(Color.yellow); return; }

			// Add item to list of sub groups
			Integer subgroupID = null;
			String subgroupName = getUniqueName(name.getText(),new ArrayList<Object>(subgroups));
			subgroups.add(new Group(subgroupID,subgroupName,groupID));
		}

		// Remove an old sub group
		else
		{
			Group subgroup = (Group)groupList.getSelectedValue();
			if(subgroup.getId()!=null) deletedSubgroups.add(subgroup);
			for(int i=0; i<subgroups.size(); i++)
				if(subgroups.get(i)==subgroup) { subgroups.remove(i); break; }
		}

		// Updates the sub group list
		updateSubgroupList();
	}

	/** Handles changes to the name field */
	public void caretUpdate(CaretEvent e)
	{
		Object object = groupList.getSelectedValue();
		if(object instanceof Group)
		{
			Group subgroup = (Group)groupList.getSelectedValue();
			subgroup.setName(name.getText());
			repaint();
		}
	}
	
	/** Handles the saving of the domain value */
	public void save()
	{
		// Save all sub groups that may have changed
		for(Group subgroup : subgroups)
		{
			if(subgroup.getId()==null)
				Groups.addGroup(subgroup);
			else Groups.updateGroup(subgroup);
		}
		
		// Delete all sub groups that have been removed
		for(Group deletedSubgroup : deletedSubgroups)
			Groups.deleteGroup(deletedSubgroup);
	}
}