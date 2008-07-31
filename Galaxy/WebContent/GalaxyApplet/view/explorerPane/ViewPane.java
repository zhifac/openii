// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.Schemas;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;

/** Class for viewing information about a schema object */
class ViewPane extends JPanel
{
	// Stores various objects used by the View Pane
	private JLabel title = new JLabel();
	private JEditorPane info = new JEditorPane();
	
	/** Constructs the View Pane */
	ViewPane()
	{
		// Initialize the info
		info.setBorder(new EmptyBorder(1,2,1,2));
		info.setContentType("text/html; charset=EUC-JP");
		info.setEditable(false);
		
		// Construct the title pane
		JPanel titlePane = new JPanel();
		titlePane.setBackground(new Color((float)0.80,(float)0.88,(float)1.0));
		titlePane.setBorder(new EmptyBorder(1,2,1,2));
		titlePane.setLayout(new BorderLayout());
		titlePane.add(title,BorderLayout.WEST);
		
		// Create the view scroll pane
		JScrollPane viewPane = new JScrollPane();
		viewPane.setBorder(null);
		viewPane.setPreferredSize(new Dimension(0,100));
		viewPane.setViewportView(info);
		viewPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		viewPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	
		// Construct the view pane
		setBackground(Color.white);
		setBorder(new CompoundBorder(new EmptyBorder(7,0,0,1),new LineBorder(Color.gray)));
		setLayout(new BorderLayout());
		add(titlePane,BorderLayout.NORTH);
		add(viewPane,BorderLayout.CENTER);
		
		// Initializes the pane to not show any information
		clearInfo();
	}
	
	/** Clears any schema object being displayed in the view pane */
	void clearInfo()
	{
		// Update the title pane
		title.setText("Information");
		
		// Update the view pane
		StringBuffer text = new StringBuffer("");
		text.append("<html>");
		text.append("  <div style='font-family:ialog; font-size:10px'>");
		text.append("    <br>");
		text.append("    <table width=100% cellPadding=0 cellSpacing=0>");
		text.append("      <tr align=center><td nowrap>To view information on</td></tr>");
		text.append("      <tr align=center><td nowrap>a schema object, drag the mouse</td></tr>");
		text.append("      <tr align=center><td nowrap>on top of the item</td></tr>");
		text.append("    </table>");
		text.append("  </div>");
		text.append("</html>");
		info.setText(text.toString());
		info.setCaretPosition(0);
		
		// Refresh the pane to display changes
		validate(); repaint();		
	}
	
	/** Displays the specified schema element in the view pane */
	void displayInfo(SchemaElement schemaElement, Integer schemaID)
	{
		// Update the title pane
		title.setText("Information on " + Schemas.getSchemaElementName(schemaID, schemaElement.getId()));
		
		// Update the view pane
		StringBuffer text = new StringBuffer("");
		text.append("<html>");
		text.append("  <div style='font-family:ialog; font-size:10px'>");
		
		// Display the schema name		
		text.append("    <b>Originating Schema: </b>"+Schemas.getSchema(schemaElement.getBase()).getName());

		// Display the schema description
		if(!schemaElement.getDescription().equals(""))
		{
			text.append("    <table cellPadding=0 cellSpacing=0>");
			text.append("      <tr>");
			text.append("        <td nowrap valign=top><b>Description: </b></td>");
			text.append("        <td>"+schemaElement.getDescription()+"</td>");
			text.append("      </tr>");
			text.append("    </table>");
		}		
		
		// Display domain values for attributes
		if(schemaElement instanceof Attribute)
		{
			Attribute attribute = (Attribute)schemaElement;
			Domain domain = (Domain)Schemas.getSchemaElement(attribute.getDomainID());
			boolean newDomain = schemaID.equals(domain.getBase());
			ArrayList<DomainValue> domainValues = Schemas.getDomainValues(schemaID,attribute.getDomainID());
			text.append("    <table cellPadding=0 cellSpacing=0>");
			text.append("      <tr>");
			text.append("        <td nowrap valign=top><b>Domain: </b></td>");
			text.append("        <td>"+(newDomain?"<b>":"")+Schemas.getSchemaElementName(schemaID,attribute.getDomainID())+(newDomain?"</b>":""));
			if(!domainValues.isEmpty())
			{
				text.append(" (");
				for(DomainValue domainValue : domainValues)
				{
					boolean newValue = schemaID.equals(domainValue.getBase());
					text.append((newValue?"<b>":"")+Schemas.getSchemaElementName(schemaID,domainValue.getId())+(newValue?"</b>":"")+", ");
				}
				text.delete(text.length()-2,text.length());
				text.append(")");
			}
			text.append("        </td>");
			text.append("      </tr>");
			text.append("    </table>");				
		}
		
		// Display domain values and users
		if(schemaElement instanceof Domain)
		{
			// Display domain values
			Domain domain = (Domain)schemaElement;
			ArrayList<DomainValue> domainValues = Schemas.getDomainValues(schemaID,domain.getId());
			text.append("    <table cellPadding=0 cellSpacing=0>");
			text.append("      <tr>");
			text.append("        <td nowrap valign=top><b>Domain Values: </b></td>");
			if(!domainValues.isEmpty())
			{
				for(DomainValue domainValue : domainValues)
				{
					boolean newValue = schemaID.equals(domainValue.getBase());
					text.append((newValue?"<b>":"")+Schemas.getSchemaElementName(schemaID, domainValue.getId())+(newValue?"</b>":"")+", ");
				}
				text.delete(text.length()-2,text.length());
			}
			text.append("        </td>");
			text.append("      </tr>");
			text.append("    </table>");
			
			// Display domain users
			ArrayList<Attribute> attributes = Schemas.getAttributesFromDomain(schemaID,domain.getId());
			if(attributes.size()>0)
			{
				text.append("    <table cellPadding=0 cellSpacing=0>");
				text.append("      <tr>");
				text.append("        <td nowrap valign=top><b>Used In: </b></td>");
				if(!attributes.isEmpty())
				{
					for(Attribute attribute : attributes)
						text.append(Schemas.getSchemaElementName(schemaID, attribute.getId())+", ");
					text.delete(text.length()-2,text.length());
				}
				text.append("        </td>");
				text.append("      </tr>");
				text.append("    </table>");		
			}
		}
		
		// Display left and right entities for relationships
		if(schemaElement instanceof Relationship)
		{
			Relationship relationship = (Relationship)schemaElement;
			text.append("    <table cellPadding=0 cellSpacing=0>");
			text.append("      <tr>");
			text.append("        <td nowrap valign=top><b>Left Entity: </b></td>");
			text.append("        <td>"+getRelationshipEntityInfo(schemaID,relationship.getLeftID(),relationship.getLeftMin(),relationship.getLeftMax())+"</td>");
			text.append("      </tr>");
			text.append("    </table>");				
			text.append("    <table cellPadding=0 cellSpacing=0>");
			text.append("      <tr>");
			text.append("        <td nowrap valign=top><b>Right Entity: </b></td>");
			text.append("        <td>"+getRelationshipEntityInfo(schemaID,relationship.getRightID(),relationship.getRightMin(),relationship.getRightMax())+"</td>");
			text.append("      </tr>");
			text.append("    </table>");			
		}
		
		text.append("  </div>");
		text.append("</html>");
		info.setText(text.toString());
		info.setCaretPosition(0);
		
		// Refresh the pane to display changes
		validate(); repaint();
	}

	/** Display relationship entity info */
	private String getRelationshipEntityInfo(Integer schemaID, int entityID, Integer min, Integer max)
	{
		String info = Schemas.getSchemaElementName(schemaID, entityID);
		if(min!=null || max!=null)
			info += " ("+(min!=null?"Min="+min:"")+(min!=null&&max!=null?", ":"")+(max!=null?"Max="+max:"")+")";
		return info;
	}
}
