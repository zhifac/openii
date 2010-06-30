package org.mitre.openii.widgets.schemaList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.ListWithButtonBar;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.model.Schema;

/** Constructs a Schema List */
public class SchemaList extends ListWithButtonBar implements SelectionListener, ISelectionChangedListener
{
	/** Stores the locked schemas (prohibited from being removed) */
	private HashSet<Integer> lockedIDs = new HashSet<Integer>();
	
	/** Stores schema list listeners */
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	
	// Stores the various dialog fields
	private TableViewer list = null;
	private Button addButton = null;
	private Button searchButton = null;
	private Button removeButton = null;
	
	/** Constructs the dialog */
	public SchemaList(Composite parent, String heading)
	{
		super(parent, heading, "Schema");
		addButton = addButton("Add...",this);
		searchButton = addButton("Find...",this);
		removeButton = addButton("Remove",this);
		list = getList();
		list.setLabelProvider(new SchemaLabelProvider());
		
		// Listens for changes to the selected schemas
		list.addSelectionChangedListener(this);
	}	

	/** Sets the schemas to be displayed in the list */
	public void setSchemas(List<Integer> schemaIDs)
	{
		// Set the schemas displayed in the list
		ArrayList<Schema> schemas = new ArrayList<Schema>();
		for(Integer schemaID : schemaIDs)
			schemas.add(OpenIIManager.getSchema(schemaID));
		list.add(WidgetUtilities.sortList(schemas).toArray());
		
		// Inform listeners of the change to displayed list items
		for(ActionListener listener : listeners)
			listener.actionPerformed(new ActionEvent(this,0,null));
	}
		
	/** Sets the locked schemas */
	public void setLockedSchemas(HashSet<Integer> lockedIDs)
		{ this.lockedIDs = lockedIDs; }
	
	/** Returns the list of schemas */
	public ArrayList<Schema> getSchemas()
	{
		ArrayList<Schema> schemas = new ArrayList<Schema>();
		for(int i=0; i<list.getTable().getItemCount(); i++)
			schemas.add((Schema)list.getElementAt(i));
		return schemas;
	}
	
	/** Returns the list of schema IDs */
	public ArrayList<Integer> getSchemaIDs()
	{
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		for(Schema schema : getSchemas()) schemaIDs.add(schema.getId());
		return schemaIDs;
	}
	
	/** Adds a schema list listener */
	public void addListener(ActionListener listener)
		{ listeners.add(listener); }
	
	/** Disable the "Delete" button if locked schemas are selected */
	public void selectionChanged(SelectionChangedEvent e)
	{
		boolean enabled = true;
		for(TableItem item : list.getTable().getSelection())
			enabled &= !lockedIDs.contains(((Schema)item.getData()).getId());
		removeButton.setEnabled(enabled);
	}
	
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