// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.schema.edit;

import java.util.ArrayList;

import javax.swing.JButton;

import org.mitre.flexidata.ygg.view.GenericView;
import org.mitre.schemastore.model.Schema;

/** Class for displaying the edit schema view */
public class EditSchemaView extends GenericView
{
	/** Stores the schema being edited */
	private Schema schema;
	
	/** Constructs the edit schema view */
	public EditSchemaView(Schema schema)
		{ this.schema = schema; }
	
	/** Return the title */
	public String getTitle()
		{ return "Edit " + schema.getName(); }
	
	/** Return the buttons used by this view */
	public ArrayList<JButton> getButtons()
		{ return new ArrayList<JButton>(); }
}