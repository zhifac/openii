package org.mitre.openii.widgets;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/** Class for constructing a table widgets*/
public class TableWidget
{
	/** Class for handling copy key events */
	static private class KeyMonitor implements KeyListener
	{
		/** Stores the table being monitored */
		private Table table = null;

		/** Constructs the key monitor */
		private KeyMonitor(Table table)
			{ this.table = table; table.addKeyListener(this); }

		/** Checks to see if a selected item should be copied */
		public void keyPressed(KeyEvent e)
		{
			// Copy the text if requested
			if(e.stateMask==SWT.CONTROL && (e.keyCode==SWT.INSERT || e.character=='c'))
			{
				// Gets the text to copy to the clipboard
				StringBuffer text = new StringBuffer();
				for(TableItem item : table.getSelection())
					for(int i=0; i<table.getColumnCount(); i++)
						text.append(item.getText(i) + (i<table.getColumnCount()-1?", ":"\n"));
					
				// Copy the text to the clipboard
				Clipboard clipboard = new Clipboard(Display.getCurrent());
				TextTransfer textTransfer = TextTransfer.getInstance();
				clipboard.setContents(new Object[]{text.substring(0,text.length()-1)}, new Transfer[]{textTransfer});
				clipboard.dispose();
			}
		}

		// Unused event listeners
		public void keyReleased(KeyEvent e) {}
	}
	
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
		Table table = new Table(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
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
		new KeyMonitor(table);
		return table;
	}
}