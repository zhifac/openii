// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane.editPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Schema;

import view.sharedComponents.EditPaneInterface;

import model.Schemas;

/** Class for editing a schema attribute's domain values */
class EditDomainValuesPane extends EditPaneInterface implements ActionListener, CaretListener, ListSelectionListener
{
	/** Stores the schema associated with these domain values */
	private Schema schema;
	
	/** Stores the id of the domain being edited */
	private Integer domainID;
	
	/** Stores the list of domain values associated with this domain */
	private ArrayList<DomainValue> domainValues;
	
	/** Stores the list of deleted domain values associated with this domain */
	private ArrayList<DomainValue> deletedDomainValues = new ArrayList<DomainValue>();
	
	// Stores various pane objects
	private JList valuesList = new JList();
	private JTextField name = new JTextField();
	private JTextArea description = new JTextArea();
	private JButton button = new JButton("Add Value");
	
	/** Constructs the edit value pane */
	private JPanel getEditValuePane()
	{
		// Constructs the name pane
		NamePane namePane = new NamePane(name);
		namePane.setBorder(new EmptyBorder(2,6,0,2));
		
		// Initialize the value button pane
		button.setBorder(new CompoundBorder(new LineBorder(Color.gray),new EmptyBorder(1,5,1,5)));
		JPanel buttonPane = new JPanel();
		buttonPane.setOpaque(false);
		buttonPane.add(button);
		
		// Display the list of domain values
		JPanel pane = new JPanel();
		pane.setBorder(new CompoundBorder(new EmptyBorder(0,2,0,2),new CompoundBorder(new LineBorder(Color.lightGray),new EmptyBorder(2,2,2,2))));
		pane.setOpaque(false);
		pane.setLayout(new BorderLayout());
		pane.add(namePane,BorderLayout.NORTH);
		pane.add(new TextPane("Details",description),BorderLayout.CENTER);
		pane.add(buttonPane,BorderLayout.SOUTH);
		return pane;
	}
	
	/** Updates the values list */
	private void updateValuesList()
	{
		// Generate the domain value items
		Vector<Object> valueItems = new Vector<Object>();
		valueItems.add("<New Value>");
		valueItems.addAll(domainValues);
		
		// Generate the domain value list
		valuesList.setListData(valueItems);
		valuesList.setSelectedIndex(0);
		valuesList.addListSelectionListener(this);
	}
	
	/** Constructs the Domain Value Pane */
	EditDomainValuesPane(Schema schema, Domain domain)
	{
		this.schema = schema;
		this.domainID = domain.getId();
		domainValues = Schemas.getGraph(schema.getId()).getDomainValues(domainID);
		updateValuesList();

		// Constructs the label pane
		JPanel labelPane = new JPanel();
		labelPane.setBorder(new EmptyBorder(0,0,2,0));
		labelPane.setOpaque(false);
		labelPane.setLayout(new BorderLayout());
		labelPane.add(new JLabel("Domain: " + domain.getName()),BorderLayout.CENTER);
		
		// Constructs the values pane
		JPanel valuesPane = new JPanel();
		valuesPane.setOpaque(false);
		valuesPane.setLayout(new BorderLayout());
		valuesPane.add(new DomainValueList(valuesList,schema),BorderLayout.WEST);
		valuesPane.add(getEditValuePane(),BorderLayout.CENTER);
		
		// Constructs the main pane
		JPanel pane = new JPanel();
		pane.setOpaque(false);
		pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
		pane.add(labelPane,BorderLayout.NORTH);
		pane.add(valuesPane,BorderLayout.CENTER);
		
		// Construct the domain value pane
		setBorder(new EmptyBorder(0,3,0,0));
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setOpaque(false);
		add(pane);
		
		// Add listeners to various pane items
		description.addCaretListener(this);
		button.addActionListener(this);
	}
	
	/** Gets the current domain values */
	ArrayList<DomainValue> getDomainValues()
		{ return new ArrayList<DomainValue>(domainValues); }
	
	/** Sets the current domain values */
	void setDomainValues(ArrayList<DomainValue> domainValues)
		{ this.domainValues = domainValues; }

	/** Gets the current deleted domain values */
	ArrayList<DomainValue> getDeletedDomainValues()
		{ return new ArrayList<DomainValue>(deletedDomainValues); }
	
	/** Sets the current deleted domain values */
	void setDeletedDomainValues(ArrayList<DomainValue> deletedDomainValues)
		{ this.deletedDomainValues = deletedDomainValues; }	
	
	/** Handles changes to the selected domain value */
	public void valueChanged(ListSelectionEvent e)
	{
		// Get the selected object
		Object object = valuesList.getSelectedValue();
		if(object==null) return;
		
		// Adjust the information in the name and description fields
		if(object instanceof DomainValue)
		{
			DomainValue domainValue = (DomainValue)object;
			name.setText(domainValue.getName());
			description.setText(domainValue.getDescription());
			description.getCaret().setDot(0);
		}
		else { name.setText(""); description.setText(""); }
		
		// Adjust the button configuration
		boolean newValue = object instanceof String;
		boolean editable = newValue || schema.getId().equals(((DomainValue)object).getBase());
		button.setText(newValue?"Add Value":"Delete Value");
		button.setEnabled(editable);
		name.setEditable(newValue);
		description.setEditable(editable);
		validate();
	}
	
	/** Handles the pressing of the button */
	public void actionPerformed(ActionEvent e)
	{
		// Add a new value
		if(button.getText().equals("Add Value"))
		{
			// Don't proceed if name field is left empty or is duplicate
			name.setBackground(Color.white);
			if(name.getText().length()==0)
				{ name.setBackground(Color.yellow); return; }

			// Add item to list of domain values
			Integer domainValueID = null;
			String domainValueName = getUniqueName(name.getText(),new ArrayList<Object>(domainValues));
			domainValues.add(new DomainValue(domainValueID,domainValueName,description.getText(),domainID,schema.getId()));
		}
		
		// Remove an old value
		else
		{
			DomainValue domainValue = (DomainValue)valuesList.getSelectedValue();
			if(domainValue.getId()!=null) deletedDomainValues.add(domainValue);
			for(int i=0; i<domainValues.size(); i++)
				if(domainValues.get(i)==domainValue) { domainValues.remove(i); break; }
		}
			
		// Updates the values list
		updateValuesList();
	}
	
	/** Handles changes to the description field */
	public void caretUpdate(CaretEvent e)
	{
		Object selectedObject = valuesList.getSelectedValue();
		if(selectedObject instanceof DomainValue)
			((DomainValue)selectedObject).setDescription(description.getText());
	}
	
	/** Handles the saving of the domain value */
	public void save()
	{
		// Save all domain values that may have changed
		for(DomainValue domainValue : domainValues)
			if(schema.getId().equals(domainValue.getBase()))
			{
				if(domainValue.getId()==null)
					Schemas.addSchemaElement(domainValue);
				else Schemas.updateSchemaElement(domainValue);
			}
		
		// Delete all domain values that have been removed
		for(DomainValue domainValue : deletedDomainValues)
			Schemas.deleteSchemaElement(domainValue);
	}
}