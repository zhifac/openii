// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane.editPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Schema;

import view.sharedComponents.EditPaneInterface;

import model.Schemas;

/** Class for editing a schema domain */
class EditDomainPane extends EditPaneInterface implements MouseListener, MouseMotionListener
{	
	/** Stores the schema to which a domain is associated */
	private Schema schema;

	/** Stores the domain id if one exists */
	private Integer domainID;
	
	/** Stores the edit pane for switching to secondary pane */
	private ExplorerEditPane editPane;
	
	/** Stores the domain values attached to this domain */
	private ArrayList<DomainValue> domainValues = new ArrayList<DomainValue>();

	/** Stores the domain values deleted from this domain */
	private ArrayList<DomainValue> deletedDomainValues = new ArrayList<DomainValue>();
	
	// Stores various pane objects
	private JTextField name = new JTextField();
	private JTextArea description = new JTextArea();
	private JPanel infoPane = new JPanel();
	private JLabel editLink = new JLabel("(Edit)");
	
	/** Get the domain */
	private Domain getDomain()
		{ return new Domain(domainID,name.getText(),description.getText(),schema.getId()); }
	
	/** Constructs the Edit Domain pane */
	EditDomainPane(Schema schema, Domain domain, ExplorerEditPane editPane)
	{
		this.schema = schema;
		this.domainID = domain==null ? null : domain.getId();
		this.editPane = editPane;
		
		// Populate the various elements
		if(domain!=null)
		{
			name.setText(domain.getName());
			description.setText(domain.getDescription());
			domainValues = Schemas.getDomainValues(schema.getId(),domain.getId());
		}
		
		// Initialize the edit link
		editLink.addMouseListener(this);
		editLink.addMouseMotionListener(this);
		
		// Constructs the name pane
		NamePane namePane = new NamePane(name);
		namePane.setBorder(new EmptyBorder(2,6,2,2));
		
		// Construct the info pane
		infoPane.setOpaque(false);
		infoPane.setLayout(new GridLayout(2,1));
		infoPane.add(new TextPane("Details",description));
		infoPane.add(new DomainValuePane(domainValues,editLink));
		
		// Construct the Edit Domain pane
		setLayout(new BorderLayout());
		setOpaque(false);
		add(namePane,BorderLayout.NORTH);
		add(infoPane,BorderLayout.CENTER);
	}
	
	/** Retrieves information from the secondary pane */
	public void saveSecondaryInfo(EditPaneInterface secondaryPane, boolean deleted)
	{
		EditDomainValuesPane domainValuesPane = (EditDomainValuesPane)secondaryPane;
		domainValues = domainValuesPane.getDomainValues();
		deletedDomainValues = domainValuesPane.getDeletedDomainValues();
		infoPane.remove(1);
		infoPane.add(new DomainValuePane(domainValues,editLink));
	}
	
	/** Handles the validating of the domain */
	public boolean validatePane()
	{
		// Checks to make sure that name has been entered
		name.setBackground(Color.white);
		if(name.getText().length()==0)
			{ name.setBackground(Color.yellow); return false; }
	
		return true;
	}
	
	/** Handles the saving of the edited domain */
	public void save()
	{
		// Retrieve the domain
		Domain domain = getDomain();
		if(domainID==null || !Schemas.getSchemaElement(domainID).getName().equals(domain.getName()))
			domain.setName(getUniqueName(domain.getName(),new ArrayList<Object>(Schemas.getSchemaElements(schema.getId(),Domain.class))));				
		
		// Handle the addition of a domain
		if(domainID==null)
		{
			Schemas.addSchemaElement(domain);
			domainID = domain.getId();
			for(DomainValue domainValue : domainValues) domainValue.setDomainID(domainID);
		}
			
		// Handles the editing of a domain
		else Schemas.updateSchemaElement(domain);

		// Saves the domain values
		EditDomainValuesPane secondaryPane = new EditDomainValuesPane(schema,domain);
		secondaryPane.setDomainValues(new ArrayList<DomainValue>(domainValues));
		secondaryPane.setDeletedDomainValues(new ArrayList<DomainValue>(deletedDomainValues));
		secondaryPane.save();
	}
	
	/** Handles the deletion of a domain */
	public boolean delete()
	{
		for(DomainValue domainValue : Schemas.getDomainValues(schema.getId(),domainID))
			Schemas.deleteSchemaElement(domainValue);
		return Schemas.deleteSchemaElement(getDomain());
	}
	
	/** Switch to a secondary pane for editing domain values if the edit link is selected */
	public void mouseClicked(MouseEvent e)
	{
		EditDomainValuesPane secondaryPane = new EditDomainValuesPane(schema,getDomain());
		secondaryPane.setDomainValues(new ArrayList<DomainValue>(domainValues));
		secondaryPane.setDeletedDomainValues(new ArrayList<DomainValue>(deletedDomainValues));
		editPane.displaySecondaryPane(secondaryPane,false);
		setCursor(Cursor.getDefaultCursor());
	}
	
	/** Displays a hand cursor when the mouse is over the edit link */
	public void mouseEntered(MouseEvent e)
		{ setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
	
	/** Resets the mouse cursor if moved off of the edit link */
	public void mouseExited(MouseEvent e)
		{ setCursor(Cursor.getDefaultCursor()); }

	// Unused listener event
	public void mouseMoved(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
}