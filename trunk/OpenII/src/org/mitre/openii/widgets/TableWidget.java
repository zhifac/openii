package org.mitre.openii.widgets;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/** Class for constructing a table widgets*/
public class TableWidget
{
	/** Class for sorting table columns */
	static class ColumnListener implements Listener
	{
		public void handleEvent(Event e)
		{
			TableColumn column = (TableColumn)e.widget;
			Table table = column.getParent();

			// Find the column location
			int loc = 0;
			for(loc=0; loc<table.getColumnCount(); loc++)
				if(table.getColumn(loc).equals(column)) break;
			
			// Sort out list of table items
			TableItem[] items = table.getItems();
	        for(int origRow=1; origRow<items.length; origRow++)
	        {
	        	String value1 = items[origRow].getText(loc);
	        	for(int row=0; row<origRow; row++)
	        	{
	        		// Look for row where item should be sorted to
		            if(value1.compareTo(items[row].getText(loc)) < 0)
		            {
		            	// Retrieve the values out of the various columns
		            	ArrayList<String> values = new ArrayList<String>();
		            	for(int i=0; i<table.getColumnCount(); i++)
		            		values.add(items[origRow].getText(i));
		            	
		            	// Move the row to the new position
		            	items[origRow].dispose();
						TableItem item = new TableItem(table, SWT.NONE, row);
						item.setText(values.toArray(new String[0]));
						items = table.getItems();
						break;
		            }
	        	}
	        }
		}			
	}
	
	/** Function to create a table */
	static public Table createTable(Composite parent, String fields[], ArrayList<Object[]> rows)
	{		
		// Create the table
		Table table = new Table(parent, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		table.setLayoutData(data);
		for(String field : fields)
		{
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(field);
			column.addListener(SWT.Selection, new ColumnListener());
		}
		
		// Populate the table
		for(Object[] row : rows)
		{
			TableItem item = new TableItem(table, SWT.NONE);
			for(int i=0; i<row.length; i++)
				item.setText(i,row[i]==null ? "" : row[i].toString());
		}
		for(int i=0; i<table.getColumnCount(); i++) table.getColumn(i).pack();
		
		// Returns the create table
		return table;
	}
}