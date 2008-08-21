// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.sharedComponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

import model.Schemas;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

/** Constructs an abstract class around the various edit pane components */
abstract public class EditPaneInterface extends JPanel
{
	/** Protected class for displaying an entity combo box */
	protected class EntityComboBox extends JComboBox
	{
		/** Constructs the entity combo box */
		public EntityComboBox(Schema schema)
		{
			setBackground(Color.white);
			setBorder(new EmptyBorder(0,0,0,1));
			setFocusable(false);
			for(SchemaElement entity : Schemas.getGraph(schema.getId()).getElements(Entity.class))
				addItem((Entity)entity);
		}
		
		/** Returns the id of the selected entity */
		public Integer getID()
			{ return ((SchemaElement)getSelectedItem()).getId(); }
		
		/** Sets the id of the selected entity */
		public void setID(Integer id)
		{
			for(int i=0; i<getItemCount(); i++)
				if(((Entity)getItemAt(i)).getId().equals(id))
					{ setSelectedIndex(i); return; }
		}
	}

	/** Protected class for displaying an domain combo box */
	protected class DomainComboBox extends JComboBox
	{
		/** Constructs the domain combo box */
		public DomainComboBox(Schema schema)
		{
			setBackground(Color.white);
			setBorder(new EmptyBorder(0,0,0,1));
			setFocusable(false);

			// Add in default domains
			for(SchemaElement domain : Schemas.getGraph(schema.getId()).getElements(Domain.class))
				if(domain.getId()<0) addItem(domain);
			
			// Add in defined domains
			for(SchemaElement domain : Schemas.getGraph(schema.getId()).getElements(Domain.class))
				if(domain.getId()>=0) addItem(domain);
		}
		
		/** Returns the id of the selected domain */
		public Integer getID()
			{ return ((SchemaElement)getSelectedItem()).getId(); }
		
		/** Sets the id of the selected domain */
		public void setID(Integer id)
		{
			for(int i=0; i<getItemCount(); i++)
				if(((Domain)getItemAt(i)).getId().equals(id))
					{ setSelectedIndex(i); return; }
		}
	}
	
	/** Protected class for displaying a number field */
	protected class NumberField extends JTextField
	{
		/** Private class for the number field document */
		private class NumberDocument extends DefaultStyledDocument
		{
			public void insertString(int offset, String text, AttributeSet attributes) throws BadLocationException
				{ super.insertString(offset, text.replaceAll("\\D",""),attributes); }
		}
		
		/** Constructs the number field */
		public NumberField()
		{
			setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			setMaximumSize(new Dimension(30,16));
			setDocument(new NumberDocument());
		}
		
		/** Returns the value */
		public Integer getValue()
			{ return getText().length()==0 ? null : new Integer(getText()); }
		
		/** Sets the value */
		public void setValue(Integer value)
			{ if(value!=null) setText(value.toString()); }
	}
	
	/** Protected class for displaying the name pane */
	protected class NamePane extends JPanel
	{
		/** Constructs the name pane */
		public NamePane(JTextField name)
		{
			// Initialize the name box
			name.setBackground(Color.white);
			name.setBorder(new CompoundBorder(new LineBorder(Color.gray), new EmptyBorder(0,1,0,1)));
			
			// Create the name pane
			setLayout(new BorderLayout());
			setOpaque(false);
			add(new JLabel("Name: "),BorderLayout.WEST);
			add(name,BorderLayout.CENTER);
		}
	}
	
	/** Protected class for displaying the text pane */
	protected class TextPane extends JPanel
	{
		/** Constructs the text pane */
		public TextPane(String label, JTextArea text)
		{
			// Initialize the text box
			text.setBorder(new EmptyBorder(0,1,0,1));
			text.setLineWrap(true);
			text.setWrapStyleWord(true);
			text.getCaret().setDot(0);
			
			// Create the scroll pane
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBackground(Color.white);
			scrollPane.setBorder(new CompoundBorder(new EmptyBorder(3,3,3,3),new LineBorder(Color.gray)));
			scrollPane.setViewportView(text);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			// Create the label pane
			JPanel labelPane = new JPanel();
			labelPane.setBorder(new EmptyBorder(4,0,0,0));
			labelPane.setLayout(new BorderLayout());
			labelPane.setOpaque(false);
			labelPane.add(new JLabel(label+":"),BorderLayout.NORTH);
			labelPane.add(new JLabel(" "),BorderLayout.SOUTH);
			
			// Construct the text pane
			setBorder(new EmptyBorder(2,0,0,0));
			setLayout(new BorderLayout());
			setOpaque(false);
			add(labelPane,BorderLayout.WEST);
			add(scrollPane,BorderLayout.CENTER);
		}
	}
	
	/** Protected class for displaying a link pane */
	public class LinkPane extends JPanel
	{		
		/** Protected class for storing an link */
		public class Link extends JLabel
		{
			/** Stores the object associated with this pane */
			private Object object;

			/** Constructs a link */
			Link(Object object)
			{
				super(" (Edit/Delete)");
				this.object = object;
			}
			
			/** Returns the object associated with this link */
			public Object getObject()
				{ return object; }
		}
		
		/** Stores the link associated with this pane */
		private Link link;
		
		/** Constructs the link pane */
		public LinkPane(Object object)
		{			
			// Initialize the label
			JLabel label = new JLabel(object.toString());
			label.setFont(new Font(null,Font.PLAIN,12));
			
			// Initialize the link
			link = new Link(object);
			link.setForeground(Color.blue);
			link.setFont(new Font(null,Font.BOLD,10));
			
			// Create the link pane
			setAlignmentX(0);
			setOpaque(false);
			setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
			add(label);
			add(link);
		}
		
		/** Returns the link associated with this pane */
		public JLabel getLink()
			{ return link; }
	}
	
	/** Protected class for displaying a link list pane */
	protected class LinkListPane extends JPanel
	{
		/** Constructs the link list pane */
		public LinkListPane(String label, ArrayList<LinkPane> links, JLabel addLink)
		{
			// Initialize the link
			addLink.setForeground(Color.blue);
			addLink.setFont(new Font(null,Font.BOLD,10));
			addLink.setHorizontalAlignment(JLabel.CENTER);
			addLink.setVerticalAlignment(JLabel.NORTH);
						
			// Create the label pane
			JPanel labelPane = new JPanel();
			labelPane.setBorder(new EmptyBorder(4,0,0,0));
			labelPane.setLayout(new BorderLayout());
			labelPane.setOpaque(false);
			labelPane.add(new JLabel(label),BorderLayout.NORTH);
			labelPane.add(addLink,BorderLayout.CENTER);

			// Create the label buffer pane
			JPanel labelBufferPane = new JPanel();
			labelBufferPane.setLayout(new BorderLayout());
			labelBufferPane.setOpaque(false);
			labelBufferPane.add(labelPane,BorderLayout.NORTH);
			labelBufferPane.add(new JLabel(""));
			
			// Create the link list pane
			JPanel linkListPane = new JPanel();
			linkListPane.setBackground(Color.white);
			linkListPane.setBorder(new EmptyBorder(0,2,0,1));
			linkListPane.setLayout(new BoxLayout(linkListPane,BoxLayout.Y_AXIS));
			for(LinkPane link : links) linkListPane.add(link);
				
			// Create the scroll pane
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBackground(Color.white);
			scrollPane.setBorder(new CompoundBorder(new EmptyBorder(3,3,3,3),new LineBorder(Color.gray)));
			scrollPane.setPreferredSize(new Dimension(100,30));
			scrollPane.setViewportView(linkListPane);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			// Construct the link list pane
			setBorder(new EmptyBorder(2,0,0,0));
			setLayout(new BorderLayout());
			setOpaque(false);
			add(labelBufferPane,BorderLayout.WEST);
			add(scrollPane,BorderLayout.CENTER);
		}
	}	
	
	/** Protected class for displaying the domain pane */
	protected class DomainPane extends JPanel
	{
		/** Constructs the domain pane */
		public DomainPane(DomainComboBox domain)
		{
			// Construct the description pane
			setBorder(new EmptyBorder(2,0,0,0));
			setLayout(new BorderLayout());
			setOpaque(false);
			add(new JLabel("Domain: "),BorderLayout.WEST);
			add(domain,BorderLayout.CENTER);
		}
	}
	
	/** Protected class for displaying a relationship entity pane */
	protected class RelationshipEntityPane extends JPanel
	{
		/** Constructs an entity constraint pane */
		private JPanel getEntityConstraintPane(String label, NumberField numberField)
		{
			// Constant for font use in displaying relationship entity constraints
			final Font constraintFont = new Font("Default",Font.PLAIN,10);
			
			// Initialize the label pane
			JLabel labelPane = new JLabel(label);
			labelPane.setFont(constraintFont);
			
			// Initialize the number field
			numberField.setFont(constraintFont);
			
			// Create the entity constraint pane
			JPanel pane = new JPanel();
			pane.setOpaque(false);
			pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS));
			pane.add(labelPane);
			pane.add(numberField);
			return pane;
		}
		
		/** Constructs the relationship entity pane */
		public RelationshipEntityPane(String label, EntityComboBox entity, NumberField min, NumberField max)
		{
			// Create the label pane
			JPanel labelPane = new JPanel();
			labelPane.setBorder(new EmptyBorder(4,0,0,0));
			labelPane.setLayout(new BorderLayout());
			labelPane.setOpaque(false);
			labelPane.add(new JLabel(label+" Entity: "),BorderLayout.NORTH);
			labelPane.add(new JLabel(" "),BorderLayout.SOUTH);
			
			// Create the min/max pane
			JPanel minMaxPane = new JPanel();
			minMaxPane.setBorder(new EmptyBorder(2,0,0,0));
			minMaxPane.setOpaque(false);
			minMaxPane.setLayout(new BoxLayout(minMaxPane,BoxLayout.X_AXIS));
			minMaxPane.add(getEntityConstraintPane("Min: ",min));
			minMaxPane.add(getEntityConstraintPane("   Max: ",max));
			
			// Create the entity pane
			JPanel entityPane = new JPanel();
			entityPane.setLayout(new BorderLayout());
			entityPane.setOpaque(false);
			entityPane.add(entity,BorderLayout.NORTH);
			entityPane.add(minMaxPane,BorderLayout.SOUTH);
			
			// Create the entity pane
			setLayout(new BorderLayout());
			setOpaque(false);
			add(labelPane,BorderLayout.WEST);
			add(entityPane,BorderLayout.CENTER);
		}
	}
	
	/** Protected class for displaying the domain value pane */
	protected class DomainValuePane extends JPanel
	{
		/** Constructs the domain value pane */
		public DomainValuePane(ArrayList<DomainValue> domainValues, JLabel editLink)
		{
			// Initialize the link
			editLink.setForeground(Color.blue);
			editLink.setFont(new Font(null,Font.BOLD,10));
			editLink.setHorizontalAlignment(JLabel.CENTER);
			editLink.setVerticalAlignment(JLabel.NORTH);
			
			// Generate the list of domain values
			StringBuffer text = new StringBuffer("");
			for(DomainValue domainValue : domainValues)
				text.append(domainValue.getName()+", ");
			if(text.length()>1) text.delete(text.length()-2,text.length());
			
			// Create the label pane
			JPanel labelPane = new JPanel();
			labelPane.setBorder(new EmptyBorder(4,0,0,0));
			labelPane.setLayout(new BorderLayout());
			labelPane.setOpaque(false);
			labelPane.add(new JLabel("Values:"),BorderLayout.NORTH);
			labelPane.add(editLink,BorderLayout.CENTER);

			// Create the label buffer pane
			JPanel labelBufferPane = new JPanel();
			labelBufferPane.setLayout(new BorderLayout());
			labelBufferPane.setOpaque(false);
			labelBufferPane.add(labelPane,BorderLayout.NORTH);
			labelBufferPane.add(new JLabel(""));
			
			// Create the domain value pane
			JTextArea domainValuePane = new JTextArea();
			domainValuePane.setBorder(new EmptyBorder(0,1,0,1));
			domainValuePane.setEditable(false);
			domainValuePane.setLineWrap(true);
			domainValuePane.setWrapStyleWord(true);
			domainValuePane.getCaret().setDot(0);
			domainValuePane.setText(text.toString());

			// Create the scroll pane
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBackground(Color.white);
			scrollPane.setBorder(new CompoundBorder(new EmptyBorder(3,3,3,3),new LineBorder(Color.gray)));
			scrollPane.setPreferredSize(new Dimension(100,30));
			scrollPane.setViewportView(domainValuePane);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			// Construct the domain value pane
			setBorder(new EmptyBorder(2,0,0,0));
			setLayout(new BorderLayout());
			setOpaque(false);
			add(labelBufferPane,BorderLayout.WEST);
			add(scrollPane,BorderLayout.CENTER);
		}
	}	
	
	/** Protected class for displaying the domain value list */
	protected class DomainValueList extends JScrollPane
	{
		/** Stores the schema associated with this domain value list */
		private Schema schema = null;
		
		/** Private class used for rendering the value list */
		private class ValueListRenderer extends DefaultListCellRenderer
		{
			/** Constructs the value list component */
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
			{
				// Determine if the domain value should be made bold
				boolean bold = true;
				if(value instanceof DomainValue)
					if(!schema.getId().equals(((DomainValue)value).getBase())) bold = false;
				
				// Generate the domain value component
				Component cell = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				cell.setFont(new Font("Default",bold?Font.BOLD:Font.PLAIN,12));
				return cell;
			}
		}
		
		/** Constructs the domain value list pane */
		public DomainValueList(JList domainValueList, Schema schema)
		{
			// Initialize the domain value list
			this.schema = schema;
			domainValueList.setCellRenderer(new ValueListRenderer());
			
			// Generate the domain values scrollable list
			setBackground(Color.white);
			setPreferredSize(new Dimension(100,125));
			setViewportView(domainValueList);
			setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}
	}
	
	/** Returns a unique name based off of the specified name and list of possible conflicts */
	protected String getUniqueName(String name, ArrayList<Object> conflicts)
	{
		// Find the next available id
		int availableID = 0;
		for(Object conflict : conflicts)
			if(conflict.toString().equals(name))
				{ if(availableID==0) availableID=1; }
			else if(conflict.toString().matches(name+"\\(\\d+\\)"))
			{
				String idString = conflict.toString().replaceAll(".*\\(","").replaceAll("\\)","");
				int id = Integer.parseInt(idString);
				if(id>=availableID) availableID = id+1;
			}

		// Return the unique name
		return name + ((availableID==0) ? "" : "("+availableID+")"); 
	}
	
	/** Adds the secondary pane information to the current object being edited */
	public void saveSecondaryInfo(EditPaneInterface secondaryPane, boolean deleted) {}
	
	/** Closes the secondary pane */
	public void closeSecondaryPane() {}
	
	/** Validates the current object being edited */
	public boolean validatePane() { return true; }
	
	/** Saves the current object being edited */
	public void save() {} 
	
	/** Deletes the current object being edited */
	public boolean delete() { return true; }
}
