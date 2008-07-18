// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.schema.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import org.mitre.flexidata.ygg.Ygg;
import org.mitre.flexidata.ygg.model.SchemaManager;
import org.mitre.flexidata.ygg.view.Consts;
import org.mitre.flexidata.ygg.view.GenericView;
import org.mitre.flexidata.ygg.view.schema.edit.EditSchemaView;
import org.mitre.flexidata.ygg.view.schema.porters.ExporterView;
import org.mitre.flexidata.ygg.view.shared.ColoredButton;
import org.mitre.flexidata.ygg.view.shared.parameters.ParameterPane;
import org.mitre.flexidata.ygg.view.shared.parameters.TextParameter;
import org.mitre.schemastore.model.Schema;

/** Class for displaying the schema view */
public class SchemaView extends GenericView implements ActionListener
{	
	/** Stores the schema associated with this view */
	private Schema schema;
	
	/** Stores the schema elements associated with this view */
	//private ArrayList<>
	
	// Stores the various objects associated with the schema view
	private ParameterPane generalInfoPane = new ParameterPane();
	private ColoredButton editButton = new ColoredButton("Edit");
	private ColoredButton exportButton = new ColoredButton("Export");
	private ColoredButton deleteButton = new ColoredButton("Delete");
	
	/** Constructs the schema view */
	public SchemaView(Schema schema)
	{
		// Stores the schema associated with this view
		this.schema = schema;

		// Generate the general info pane
		generalInfoPane.addParameter(new TextParameter("Description",schema.getDescription()));
		generalInfoPane.addParameter(new TextParameter("Author","Unknown"));
		generalInfoPane.addParameter(new TextParameter("Source","Unknown"));
		generalInfoPane.setBorder(new EmptyBorder(0,10,2,10));
		
		// Generate the schema view
		setOpaque(false);
		setLayout(new BorderLayout());
		add(generalInfoPane,BorderLayout.NORTH);
		add(new SchemaElementPane(schema.getId()),BorderLayout.CENTER);
		
		// Add button listeners
		editButton.addActionListener(this);
		exportButton.addActionListener(this);
		deleteButton.addActionListener(this);
	}
	
	/** Return the title */
	public String getTitle()
		{ return schema.getName() + " Schema"; }
	
	/** Return the buttons used by this pane */
	public ArrayList<JButton> getButtons()
	{
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		buttons.add(editButton); buttons.add(exportButton); buttons.add(deleteButton); 
		return buttons;
	}

	/** Handles the pressing of various buttons */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==editButton) Ygg.setView(new EditSchemaView(schema));
		if(e.getSource()==exportButton) Ygg.setView(new ExporterView(schema));
		if(e.getSource()==deleteButton)
		{
			int response = JOptionPane.showConfirmDialog(Ygg.ygg,"Delete the '"+schema.getName()+"' schema?","Delete Schema",JOptionPane.YES_NO_OPTION);
			if(response==0) SchemaManager.deleteSchema(schema);
		}
	}
	
	/** Handles the painting of the schema view */
	public void paint(Graphics g)
	{
		// Paints the menu pane
		super.paint(g);
		
		// Paints a line under the header pane
		g.setColor(Consts.ORANGE);
		g.drawLine(0, generalInfoPane.getHeight()-1, generalInfoPane.getWidth(), generalInfoPane.getHeight()-1);		
	}
}