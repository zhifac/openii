package org.mitre.openii.widgets.mappingList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.mitre.openii.widgets.ListWithButtonBar;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

/** Constructs a Mapping List */
public class MappingList extends ListWithButtonBar implements SelectionListener, ISelectionChangedListener
{
	/** Stores schemas from which mappings can be created */
	private ArrayList<Schema> schemas = new ArrayList<Schema>();
	
	/** Stores schema list listeners */
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	
	// Stores the various dialog fields
	private TableViewer list = null;
	private Button addButton = null;
	private Button editButton = null;
	private Button removeButton = null;

	// Stores the projectID so we can get mappings out of this
	private Integer projectId;

	/** Constructs the dialog */
	public MappingList(Composite parent, String heading, Integer projectId) {
		super(parent, heading, "Mapping");

		// record our project id
		this.projectId = projectId;

		// create buttons down the right
		addButton = addButton("Add...", this);
		editButton = addButton("Edit...", this);
		removeButton = addButton("Remove", this);

		// the edit button should be invalid until something is selected
		editButton.setEnabled(false);

		// the remove button should be invalid until something is selected
		removeButton.setEnabled(false);

		list = getList();
		list.setLabelProvider(new MappingLabelProvider());

		// Listens for changes to the selected schemas
		list.addSelectionChangedListener(this);
	}

	/** Sets the schemas from which mappings can be generated */
	public void setSchemas(ArrayList<Schema> schemas) {
		this.schemas = schemas;
	}
	
	/** Sets the mappings currently in this list */
	public void setMappings(ArrayList<Mapping> mappings) {
		ArrayList<MappingReference> mappingRefs = new ArrayList<MappingReference>();
		for(Mapping mapping : mappings) {
			mappingRefs.add(new MappingReference(mapping,schemas));
		}
		list.add(WidgetUtilities.sortList(mappingRefs).toArray());
		
		// Inform listeners of the change to displayed list items
		for (ActionListener listener : listeners) {
			listener.actionPerformed(new ActionEvent(this,0,null));
		}
	}
		
	/** Returns the list of mappings */
	public ArrayList<Mapping> getMappings() {
		ArrayList<Mapping> mappings = new ArrayList<Mapping>();
		for (int i=0; i<list.getTable().getItemCount(); i++) {
			mappings.add(((Mapping)list.getElementAt(i)).copy());
		}
		return mappings;
	}
	
	/** Adds a mapping list listener */
	public void addListener(ActionListener listener) {
		listeners.add(listener);
	}

	public void selectionChanged(SelectionChangedEvent event) {
		// how many are selected
		int selected = list.getTable().getSelectionCount();

		// do not enable the edit button if some number other than one schemas are selected
		if (editButton != null) {
			if (selected == 1) {
				editButton.setEnabled(true);
			} else {
				editButton.setEnabled(false);
			}
		}

		// do not enable the remove button if zero schemas are selected
		if (removeButton != null) {
			if (selected > 0) {
				removeButton.setEnabled(true);
			} else {
				removeButton.setEnabled(false);
			}
		}
	}
	
	/** Handles the pressing of list buttons */
	public void widgetSelected(SelectionEvent e) {
		// Handles the addition of mappings
		if (e.getSource().equals(addButton)) {
			//AddMappingsToListDialog dialog = new AddMappingsToListDialog(getShell(), schemas, getMappings());
			MappingSelectionDialog dialog = new MappingSelectionDialog(getShell(), schemas, getMappings(), null);
			if (dialog.open() == Window.OK) {
				list.add(dialog.getMapping());
			}
		}

		// Handles the editing of a mapping
		if (e.getSource().equals(editButton)) {
			MappingSelectionDialog dialog = new MappingSelectionDialog(getShell(), schemas, getMappings(), (Mapping)list.getTable().getSelection()[0].getData());
			if (dialog.open() == Window.OK) {
				// TODO
			}
		}

		// Handles the removal of mappings
		if (e.getSource().equals(removeButton)) {
			list.getTable().remove(list.getTable().getSelectionIndices());
		}
		
		// Inform listeners of the change to displayed list items
		for (ActionListener listener : listeners) {
			listener.actionPerformed(new ActionEvent(this,0,null));
		}
	}

	// Unused listener event
	public void widgetDefaultSelected(SelectionEvent arg0) {}
}