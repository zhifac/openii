package org.mitre.openii.widgets.schemaList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.openii.widgets.ListWithButtonBar;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.model.Schema;

/** Constructs a Schema List */
public class SchemaList extends ListWithButtonBar implements SelectionListener
{
	/** Stores schema list listeners */
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	
	// Stores the various dialog fields
	private TableViewer list = null;
	private Button addButton = null;
	private Button searchButton = null;
	private Button removeButton = null;
	
	/** Constructs the dialog */
	public SchemaList(Composite parent, String heading, ArrayList<Integer> initialSchemas)
	{
		super(parent, heading, "Schema");
		addButton = addButton("Add...",this);
		searchButton = addButton("Search...",this);
		removeButton = addButton("Remove",this);
		list = getList();
		list.setLabelProvider(new BasicWidgets.SchemaLabelProvider());
	}	

	public void setSchemas(ArrayList<Integer> schemaIDs)
	{
		// Set the schemas displayed in the list
		ArrayList<Schema> schemas = new ArrayList<Schema>();
		for(Integer schemaID : schemaIDs)
			schemas.add(OpenIIManager.getSchema(schemaID));
		for(Schema schema : WidgetUtilities.sortList(schemas))
			list.add(schema);
		
		// Inform listeners of the change to displayed list items
		for(ActionListener listener : listeners)
			listener.actionPerformed(new ActionEvent(this,0,null));
	}
		
	/** Returns the list of schemas */
	public ArrayList<Integer> getSchemas()
	{
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		for(int i=0; i<list.getTable().getItemCount(); i++)
			schemaIDs.add(((Schema)list.getElementAt(i)).getId());
		return schemaIDs;
	}

	/** Adds a schema list listener */
	public void addListener(ActionListener listener)
		{ listeners.add(listener); }
	
	/** Handles the pressing of list buttons */
	public void widgetSelected(SelectionEvent e)
	{
		// Handles the addition of schemas
		if(e.getSource().equals(addButton))
		{
			AddSchemasToListDialog dialog = new AddSchemasToListDialog(getShell(),getSchemas());
			if(dialog.open() == Window.OK)
				list.add(dialog.getResult());
		}
			
		// Handles the search for schemas
		if(e.getSource().equals(searchButton))
			new QuerySchemasForListDialog(getShell()).open();

		// Handles the removal of schemas
		if(e.getSource().equals(removeButton))
			list.getTable().remove(list.getTable().getSelectionIndices());
		
		// Inform listeners of the change to displayed list items
		for(ActionListener listener : listeners)
			listener.actionPerformed(new ActionEvent(this,0,null));
	}

	// Unused listener event
	public void widgetDefaultSelected(SelectionEvent arg0) {}
}