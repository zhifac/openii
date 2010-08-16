package org.mitre.openii.widgets;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;

/** Constructs a list with a button bar */
public class ListWithButtonBar extends Composite
{
	/** Stores the list viewer */
	private TableViewer list = null;
	
	/** Stores the list viewer column */
	private ArrayList<TableViewerColumn> columns = new ArrayList<TableViewerColumn>();
	
	/** Stores the button pane */
	private Composite buttonPane = null;
	
	/** Constructs the dialog list */
	public ListWithButtonBar(Composite parent, String heading, String header)
		{ this(parent, heading, new ArrayList<String>(Arrays.asList(new String[]{header}))); }
	
	/** Constructs the dialog list */
	public ListWithButtonBar(Composite parent, String heading, ArrayList<String> headers)
	{
		super(parent, SWT.NONE);
		setLayout(new GridLayout(1,false));
	
		// Create the list heading
		if(heading!=null)
		{
			Label label = new Label(this, SWT.NONE);
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
			label.setText(" "+heading+": ");
		}
			
		// Create the list pane
		Composite listPane = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout(2,false);
		layout.marginHeight = 0; layout.marginWidth = 0;
		listPane.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 150;
		listPane.setLayoutData(gridData);
		
		// Create the dialog list
		list = new TableViewer(listPane, SWT.BORDER | SWT.FULL_SELECTION);
		Table table = list.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(GridData.FILL_VERTICAL));

		// Add the list headers
		for(String header : headers)
		{
			TableViewerColumn column = new TableViewerColumn(list, SWT.NONE);
			column.getColumn().setText(header);
			column.getColumn().setWidth(200);
			columns.add(column);
		}
		
		// Construct the button pane
		buttonPane = new Composite(listPane, SWT.NONE);
		layout = new GridLayout(1,false);
		layout.marginHeight = 0; layout.marginWidth = 0;
		buttonPane.setLayout(layout);
		buttonPane.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
	}
	
	/** Returns the specified column */
	public TableViewerColumn getColumn(int index)
		{ return columns.get(index); }
	
	/** Sets the width of the specified column */
	public void setWidth(int index, int width)
		{ getColumn(index).getColumn().setWidth(width); }
	
	/** Adds a button to the dialog list */
	public Button addButton(String label, SelectionListener listener)
	{
		Button button = BasicWidgets.createButton(buttonPane,label,listener);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return button;
	}
	
	/** Returns the list */
	public TableViewer getList()
		{ return list; }
}