package org.mitre.openii.widgets.schemaList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;

/** Constructs a Schema List */
public class SchemaList extends ListWithButtonBar implements SelectionListener, ISelectionChangedListener
{	
	/** Stores the schema models */
	private HashMap<Integer,SchemaModel> models = null;
	
	/** Stores the locked schemas (prohibited from being removed) */
	private HashSet<Integer> lockedIDs = new HashSet<Integer>();
	
	/** Stores schema list listeners */
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	
	// Stores the various dialog fields
	private Button addButton = null;
	private Button editButton = null;
	private Button removeButton = null;
	
	/** Returns the headers for the schema list */
	static private ArrayList<String> getHeaders(boolean displayModel)
	{
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("Schema");
		if(displayModel) headers.add("Model");
		return headers;
	}
	
	/** Constructs the dialog */
	public SchemaList(Composite parent, String heading)
		{ this(parent,heading,null); }
	
	/** Constructs the dialog */
	public SchemaList(Composite parent, String heading, HashMap<Integer,SchemaModel> models)
	{
		super(parent, heading, getHeaders(models!=null));
		this.models = models;

		// Generate the buttons associated with the list
		addButton = addButton("Add...",this);
		if(models!=null) editButton = addButton("Edit...",this);
		removeButton = addButton("Remove",this);

		// Generate the schema list
		getColumn(0).setLabelProvider(new SchemaLabelProvider());
		if(models!=null) getColumn(1).setLabelProvider(new ModelLabelProvider(models));
		getList().addSelectionChangedListener(this);
	}	

	/** Sets the schemas to be displayed in the list */
	public void setSchemas(List<Integer> schemaIDs)
	{
		// Set the schemas displayed in the list
		ArrayList<Schema> schemas = new ArrayList<Schema>();
		for(Integer schemaID : schemaIDs)
			schemas.add(OpenIIManager.getSchema(schemaID));
		getList().add(WidgetUtilities.sortList(schemas).toArray());
		
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
		for(int i=0; i<getList().getTable().getItemCount(); i++)
			schemas.add((Schema)getList().getElementAt(i));
		return schemas;
	}
	
	/** Returns the list of schema IDs */
	public ArrayList<Integer> getSchemaIDs()
	{
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		for(Schema schema : getSchemas()) schemaIDs.add(schema.getId());
		return schemaIDs;
	}
	
	/** Returns the schema models */
	public HashMap<Integer,SchemaModel> getSchemaModels()
		{ return models; }
	
	/** Adds a schema list listener */
	public void addListener(ActionListener listener)
		{ listeners.add(listener); }
	
	/** Disable the "Delete" button if locked schemas are selected */
	public void selectionChanged(SelectionChangedEvent e)
	{
		boolean enabled = true;
		for(TableItem item : getList().getTable().getSelection())
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
				getList().add(dialog.getResult());
		}
			
		// Handles the editing of a schema
		if(e.getSource().equals(editButton))
		{
			// Get the selected schemas
			ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
			for(int i : getList().getTable().getSelectionIndices())
				schemaIDs.add(((Schema)getList().getElementAt(i)).getId());
			if(schemaIDs.size()==0) return;
				
			// Determine the current model for the selected schemas
			SchemaModel model = models.get(schemaIDs.get(0));
			for(int i=1; i<schemaIDs.size(); i++)
				if(model==null || !model.equals(models.get(schemaIDs.get(i)))) model=null;

			// Launch the model selection dialog
			EditProjectSchemaDialog dialog = new EditProjectSchemaDialog(getShell(),model);
			if(dialog.open() == Window.OK)
			{
				// Update the selected schema models
				model = dialog.getSchemaModel();
				for(Integer schemaID : schemaIDs)
					models.put(schemaID, model);
				for(int i : getList().getTable().getSelectionIndices())
					getList().replace(getList().getElementAt(i), i);
			}
		}
		
		// Handles the removal of schemas
		if(e.getSource().equals(removeButton))
			getList().getTable().remove(getList().getTable().getSelectionIndices());
		
		// Inform listeners of the change to displayed list items
		for(ActionListener listener : listeners)
			listener.actionPerformed(new ActionEvent(this,0,null));
	}

	// Unused listener event
	public void widgetDefaultSelected(SelectionEvent arg0) {}
}