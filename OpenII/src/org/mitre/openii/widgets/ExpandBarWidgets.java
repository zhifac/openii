package org.mitre.openii.widgets;

import java.util.ArrayList;
import java.util.jar.Attributes;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;

/** Class for constructing widgets within an expand bar */
public class ExpandBarWidgets
{
	/** Generates an expand item */
	static public ExpandItem createExpandItem(ExpandBar bar, String label, Composite item)
	{
		// Determine which height to use for sizing the expand item
		Integer height = item.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		if(height>250) height = 250;
		
		// Generate the expand item
		ExpandItem expandItem = new ExpandItem(bar, SWT.NONE);
		expandItem.setText(label);
		expandItem.setHeight(height);
		expandItem.setControl(item);
		expandItem.setImage(OpenIIActivator.getImage("SchemaElement.jpg"));
		expandItem.setExpanded(true);
		return expandItem;
	}
	
	/** Creates a properties pane */
	static public void createPropertiesPane(ExpandBar bar, String title, Attributes attributes)
	{
		// Construct the pane for showing the info for the schema properties
		Composite pane = new Composite(bar, SWT.NONE);
		pane.setLayout(new GridLayout(2,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		pane.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		
		// Generate the property fields to be displayed by the info pane
		for(Object key : attributes.keySet())
		{
			Text field = BasicWidgets.createTextField(pane,key.toString());		
			field.setText(attributes.get(key).toString());
			field.setEditable(false);
			field.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));		
		}
		
		// Create the Expand Item containing the properties
		createExpandItem(bar,title,pane);
	}
	
	/** Creates a table pane */
	static public void createTablePane(ExpandBar bar, String title, String[] fields, ArrayList<Object[]> rows)
	{
		Table table = BasicWidgets.createTable(bar, fields, rows);
		ExpandBarWidgets.createExpandItem(bar,title,table);
	}
}