package org.mitre.flexidata.ygg.view.shared.parameters;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.border.EmptyBorder;

import org.mitre.flexidata.ygg.model.SchemaManager;
import org.mitre.flexidata.ygg.view.Consts;
import org.mitre.schemastore.model.Schema;

/** Schema parameter class */
public class SchemaParameter extends AbstractParameter
{
	/** Stores the schema list */
	private JComboBox schemaList = new JComboBox();		
	
	/** Constructs the schema parameter */
	public SchemaParameter(String name)
	{
		super(name);
		setBorder(new EmptyBorder(2,0,2,0));

		// Constructs the drop-down box
		for(Schema schema : SchemaManager.getSchemas()) schemaList.addItem(schema);
		schemaList.setBackground(Color.white);
		schemaList.setFocusable(false);
		add(schemaList);
	}
	
	/** Returns the parameter value */
	public String getValue()
	{
		String value = ((Schema)schemaList.getSelectedItem()).getId().toString();
		return value==null || value.length()==0 ? null : value;
	}		
	
	/** Highlights the parameter */
	protected void setHighlight(boolean highlight)
		{ schemaList.setBackground(highlight ? Consts.YELLOW : Consts.WHITE); }
}
