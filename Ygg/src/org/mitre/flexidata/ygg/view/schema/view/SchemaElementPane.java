// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.schema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.mitre.flexidata.ygg.model.SchemaManager;
import org.mitre.flexidata.ygg.view.Consts;
import org.mitre.flexidata.ygg.view.shared.RoundedPane;
import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;

/** Class for displaying the schema element information */
class SchemaElementPane extends JPanel
{	
	/** Stores the schema associated with this pane */
	private Integer schemaID = null;
	
	/** Stores the schema elements to be displayed */
	private ArrayList<SchemaElement> schemaElements = null;
	
	/** Stores the data sources to be displayed */
	private ArrayList<DataSource> dataSources = null;
	
	/** List of schema elements */
	private JList elementList = new JList();
	
	/** Stores the current type */
	private Class<?> currentType = null;
	
	// Stores various components used by the schema element pane
	private JLabel listLabel = new JLabel();
	private JPanel listPane = new JPanel();
	private JPanel displayPane = new JPanel();

	/** Class for displaying the view pane */
	private class ViewPane extends JPanel
	{
		/** Constructs the view pane */
		private ViewPane()
		{
			// Initialize the list label
			listLabel.setFont(Consts.MENU_FONT);
			listLabel.setForeground(Consts.BLACK);
			listLabel.setHorizontalAlignment(SwingConstants.CENTER);
			listLabel.setBorder(new EmptyBorder(0,0,12,0));
			
			// Generate the scroll pane for showing the items being managed
			JScrollPane listScrollPane = new JScrollPane();
			listScrollPane.setBorder(new EmptyBorder(0,0,0,0));
			listScrollPane.setViewportView(elementList);
			listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			// Construct the list pane
			listPane.setBorder(new EmptyBorder(3,3,3,7));
			listPane.setPreferredSize(new Dimension(175,0));
			listPane.setOpaque(false);
			listPane.setLayout(new BorderLayout());
			listPane.add(listLabel,BorderLayout.NORTH);
			listPane.add(listScrollPane,BorderLayout.CENTER);
			
			// Construct the display pane
			displayPane.setOpaque(false);
			
			// Construct the view pane
			setOpaque(false);
			setLayout(new BorderLayout());
			add(listPane,BorderLayout.WEST);
			add(displayPane,BorderLayout.CENTER);			
		}

		/** Paints the border and decorative lines within the schema element pane */
		public void paint(Graphics g)
		{
			super.paint(g);
			
			// Draws the border and decorative lines within the schema element view
			g.setColor(Consts.BLACK);
			g.drawLine(listPane.getX()+listPane.getWidth(), listPane.getY()+4, listPane.getX()+listPane.getWidth(), listPane.getY()+listPane.getHeight()-4);
			g.drawLine(listPane.getX()+4,listLabel.getY()+listLabel.getHeight()-6,listPane.getX()+listPane.getWidth()-8,listLabel.getY()+listLabel.getHeight()-6);
		}
	}
	
	/** Class for displaying the options pane */
	private class OptionsPane extends JPanel
	{
		// Construct the option pane
		private OptionsPane()
		{
			JPanel optionMenu = new JPanel();
			optionMenu.setOpaque(false);
			optionMenu.setLayout(new BoxLayout(optionMenu,BoxLayout.X_AXIS));
			optionMenu.add(new LinkedText("Entities",Entity.class,null));
			optionMenu.add(new JLabel(" | "));
			optionMenu.add(new LinkedText("Domains",Domain.class,null));
			optionMenu.add(new JLabel(" | "));
			optionMenu.add(new LinkedText("Relationships",Relationship.class,null));
			optionMenu.add(new JLabel(" | "));
			optionMenu.add(new LinkedText("Alias",Alias.class,null));
			optionMenu.add(new JLabel(" | "));
			optionMenu.add(new LinkedText("Data Sources",DataSource.class,null));
			
			// Construct the options pane
			setBorder(new EmptyBorder(5,0,5,0));
			setOpaque(false);
			add(optionMenu);
		}
	}
	
	/** Constructs the schema element pane */
	SchemaElementPane(Integer schemaID)
	{
		this.schemaID = schemaID;
		
		// Put the view pane into a rounded pane
		RoundedPane roundedPane = new RoundedPane();
		roundedPane.setBorder(new EmptyBorder(10,10,0,10));
		roundedPane.setView(new ViewPane());
		
		// Construct the schema element pane
		setOpaque(false);
		setLayout(new BorderLayout());
		add(roundedPane,BorderLayout.CENTER);
		add(new OptionsPane(),BorderLayout.SOUTH);

		// Initialize the schema element view
		updateSchemaElementList(Entity.class,null);
	}
	
	/** Update schema element list */
	void updateSchemaElementList(Class<?> type, Integer selectedElement)
	{
		// Declares comparator between objects
		class ObjectComparator implements Comparator<Object>
		{
			public int compare(Object object1, Object object2)
				{ return object1.toString().compareTo(object2.toString()); }			
		}
		
		// Generate the list based on the specified type
		if(type!=currentType)
		{
			// Retrieve the list data
			ArrayList<Object> listData = new ArrayList<Object>();
			if(type==DataSource.class)
			{
				if(dataSources==null) dataSources = SchemaManager.getDataSources(schemaID);
				listData.addAll(dataSources);
			}
			else
			{
				if(schemaElements==null) schemaElements = SchemaManager.getSchemaElements(schemaID);
				for(SchemaElement schemaElement : schemaElements)
					if(schemaElement.getClass()==type) listData.add(schemaElement);
			}
			Collections.sort(listData, new ObjectComparator());
			
			// Update the JList with the new list data
			listLabel.setText(type==Entity.class ? "Entities" : type==Domain.class ? "Domains" : type==Relationship.class ? "Relations" : type==Alias.class ? "Aliases" : "Data Sources");
			elementList.setListData(listData.toArray(new Object[0]));
			currentType = type;
		}
		
		// Select the indicated element
		if(selectedElement==null) elementList.setSelectedIndex(0);
		else
		{
			ListModel listData = elementList.getModel();
			for(int i=0; i<listData.getSize(); i++)
			{
				Object object = listData.getElementAt(i);
				if(selectedElement.equals(object instanceof DataSource ? ((DataSource)object).getId() : ((SchemaElement)object).getId()))
					elementList.setSelectedValue(object, true);
			}
		}
	}
}