package org.mitre.openii.widgets;

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
	
	/** Stores the button pane */
	private Composite buttonPane = null;
	
	/** Constructs the dialog list */
	public ListWithButtonBar(Composite parent, String heading,  String listHeader)
	{
		super(parent, SWT.NONE);
		setLayout(new GridLayout(1,false));
	
		// Create the list heading
		Label label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		label.setText(" "+heading+": ");

		// Create the list pane
		Composite listPane = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout(2,false);
		layout.marginHeight = 0; layout.marginWidth = 0;
		listPane.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 200;
		listPane.setLayoutData(gridData);
		
		// Create the dialog list
		list = new TableViewer(listPane);
		Table table = list.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(GridData.FILL_VERTICAL));

		// Add the list header
		TableViewerColumn column = new TableViewerColumn(list, SWT.NONE);
		column.getColumn().setText(listHeader);
		column.getColumn().setWidth(200);

		// Construct the button pane
		buttonPane = new Composite(listPane, SWT.NONE);
		layout = new GridLayout(1,false);
		layout.marginHeight = 0; layout.marginWidth = 0;
		buttonPane.setLayout(layout);
		buttonPane.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
	}
	
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