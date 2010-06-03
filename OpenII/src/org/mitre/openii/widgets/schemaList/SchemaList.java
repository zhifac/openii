package org.mitre.openii.widgets.schemaList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.widgets.ListWithButtonBar;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.model.Function;
import org.mitre.schemastore.model.FunctionImp;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.PorterManager.PorterType;
import org.mitre.schemastore.porters.mappingExporters.MappingExporter;

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
	private Button replaceButton = null;

	// Stores the projectID so we can get mappings out of this
	private Integer projectID;

	/** Constructs the dialog */
	public SchemaList(Composite parent, String heading, Integer projectID) {
		super(parent, heading, "Schema");

		// record our project id
		this.projectID = projectID;

		// create buttons down the right
		addButton     = addButton("Add...", this);
		searchButton  = addButton("Find...", this);
		removeButton  = addButton("Remove", this);
		
		// the remove button should be disabled by default, until the user selects something
		removeButton.setEnabled(false);

		// if there is no projectID, we can't replace a schema
		// also, the replace button should be disabled by default
		if (projectID != null) {
			replaceButton = addButton("Replace...", this);
			replaceButton.setEnabled(false);
		}

		list = getList();
		list.setLabelProvider(new SchemaLabelProvider());

		// Listens for changes to the selected schemas
		list.addSelectionChangedListener(this);
	}	

	/** Sets the schemas to be displayed in the list */
	public void setSchemas(List<Integer> schemaIDs) {
		// Set the schemas displayed in the list
		ArrayList<Schema> schemas = new ArrayList<Schema>();
		for (Integer schemaID : schemaIDs) {
			schemas.add(OpenIIManager.getSchema(schemaID));
		}
		list.add(WidgetUtilities.sortList(schemas).toArray());
		
		// Inform listeners of the change to displayed list items
		for (ActionListener listener : listeners) {
			listener.actionPerformed(new ActionEvent(this,0,null));
		}
	}
		
	/** Sets the locked schemas */
	public void setLockedSchemas(HashSet<Integer> lockedIDs) {
		this.lockedIDs = lockedIDs;
	}
	
	/** Returns the list of schemas */
	public ArrayList<Schema> getSchemas() {
		ArrayList<Schema> schemas = new ArrayList<Schema>();
		for (int i=0; i<list.getTable().getItemCount(); i++) {
			schemas.add((Schema)list.getElementAt(i));
		}
		return schemas;
	}
	
	/** Returns the list of schema IDs */
	public ArrayList<Integer> getSchemaIDs() {
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		for (Schema schema : getSchemas()) {
			schemaIDs.add(schema.getId());
		}
		return schemaIDs;
	}
	
	/** Adds a schema list listener */
	public void addListener(ActionListener listener) {
		listeners.add(listener);
	}
	
	/** Disable the "Remove" and "Replace" button if locked schemas are selected */
	public void selectionChanged(SelectionChangedEvent e) {
		int selected = 0;
		boolean enabled = true;
		for (TableItem item : list.getTable().getSelection()) {
			enabled &= !lockedIDs.contains(((Schema)item.getData()).getId());
			selected++;
		}

		// do not show the remove button if zero schemas are selected
		if (selected > 0) {
			removeButton.setEnabled(enabled);
		} else {
			removeButton.setEnabled(false);
		}

		// do not show the replace button if zero or more than one schema is selected
		if (replaceButton != null) {
			if (selected == 1) {
				replaceButton.setEnabled(true);
			} else {
				replaceButton.setEnabled(false);
			}
		}
	}
	
	/** Handles the pressing of list buttons */
	public void widgetSelected(SelectionEvent e) {
		// Handles the addition of schemas
		if (e.getSource().equals(addButton)) {
			AddSchemasToListDialog dialog = new AddSchemasToListDialog(getShell(), getSchemas());
			if (dialog.open() == Window.OK) {
				list.add(dialog.getResult());
			}
		}
			
		// Handles the search for schemas
		if (e.getSource().equals(searchButton)) {
			new QuerySchemasForListDialog(getShell()).open();
		}

		// Handles the removal of schemas
		if (e.getSource().equals(removeButton)) {
			list.getTable().remove(list.getTable().getSelectionIndices());
		}

		// Handles the replacement of a schema
		if (e.getSource().equals(replaceButton)) {
			ReplaceSchemasOnListDialog dialog = new ReplaceSchemasOnListDialog(getShell(), getSchemas());
			if (dialog.open() == Window.OK) {
				// check to see if this schema is currently in a mapping
				boolean unlocked = true;
				for (TableItem item : list.getTable().getSelection()) {
					unlocked &= !lockedIDs.contains(((Schema)item.getData()).getId());
				}

				if (unlocked) {
					// if the schema is NOT in a mapping, just remove one and insert the other
					list.getTable().remove(list.getTable().getSelectionIndices());
					list.add(dialog.getResult());
				} else {
					// if the schema we replaced is in a mapping, change the mappings to the new schema
					// and tell the user what mappings are being dropped

					// 1. figure out what we are replacing and with what
					Schema original = ((Schema)list.getTable().getSelection()[0].getData());
					Schema replacement = ((Schema)dialog.getResult()[0]);
					
					// 2. find all affected mappings and remember them
					ArrayList<Integer> mappings = new ArrayList<Integer>();
					for (Mapping mapping : OpenIIManager.getMappings(projectID)) {
						Integer mappingID = mapping.getId();

						// don't bother putting this mapping into the list if it is already there
						if (!mappings.contains(mappingID)) {
							if (mapping.getSourceId().equals(original.getId())) {
								mappings.add(mappingID);
							}
							if (mapping.getTargetId().equals(original.getId())) {
								mappings.add(mappingID);
							}
						}
					}

					// 3. save the details for each mapping that is going to be affected
					for (Integer mappingID : mappings) {
						Mapping mapping = OpenIIManager.getMapping(mappingID);
						Integer sourceID = mapping.getSourceId();	// the source schema for this mapping
						Integer targetID = mapping.getTargetId();	// the target schema for this mapping

					}

					// 3. get the diff between the two schemas
					// 4. ask the user to confirm the diff
					// 5. save the mappings to the existing schema
					// 6. erase the existing schema
					// 7. add the new schema
					// 8. re-apply the mappings to the new schema
					// TODO: finish this
				}
			}
		}
		
		// Inform listeners of the change to displayed list items
		for (ActionListener listener : listeners) {
			listener.actionPerformed(new ActionEvent(this,0,null));
		}
	}

	// Unused listener event
	public void widgetDefaultSelected(SelectionEvent arg0) {}
}