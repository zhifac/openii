package org.mitre.openii.views.manager.mappings.importer;

import java.net.URI;
import java.util.ArrayList;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.Schema;

/**
 * Class for displaying the Import Mapping page
 */
public class MappingSchemasPage extends WizardPage
{
	/** Private class for displaying the selection pane */
	private class SelectionPane implements ISelectionChangedListener, KeyListener
	{
		/** Stores the schema list */
		private ComboViewer schemaList = null;
		
		/** Stores the mapping schema */
		private MappingSchema mappingSchema = null;
		
		/** Constructs the selection pane */
		private SelectionPane(Composite parent, MappingSchema mappingSchema)
		{
			this.mappingSchema = mappingSchema;
			
			// Display the mapping schema name
			BasicWidgets.createLabel(parent, mappingSchema.getName());
			
			// Display the list of schemas which might align with the mapping schema
			schemaList = new ComboViewer(parent, SWT.NONE);
			for(Schema currSchema : OpenIIManager.getSchemas())
				schemaList.add(currSchema);

			// Tries to select the proper schema if possible
			for(int i=0; i<schemaList.getCombo().getItemCount(); i++)
			{
				Schema schema = (Schema)schemaList.getElementAt(i);
				if(schema.getName().equals(mappingSchema.getName()))
					schemaList.getCombo().select(i);
				schemaList.addSelectionChangedListener(this);
				schemaList.getCombo().addKeyListener(this);
			}
		}

		/** Returns the mapping schema */
		private MappingSchema getMappingSchema()
		{
			Integer index = schemaList.getCombo().getSelectionIndex();
			if(index>=0)
			{
				mappingSchema.setId(((Schema)schemaList.getElementAt(index)).getId());
				return mappingSchema;
			}
			return null;
		}
		
		/** Monitors changes to the selected schema */
		public void selectionChanged(SelectionChangedEvent e)
			{ updatePageCompleteStatus(); }

		/** Monitors changes to the selected schema */
		public void keyPressed(KeyEvent e)
			{ updatePageCompleteStatus(); }

		// Unused listener events
		public void keyReleased(KeyEvent e) {}
	}
	
	/** Stores the current URI being referenced */
	private URI currentURI = null;
	
	/** Stores a list of all selection panes */
	private ArrayList<SelectionPane> selectionPanes = new ArrayList<SelectionPane>();
	
	/** Stores the scrolled pane */
	private ScrolledComposite scrolledPane = null;
	
	/** Constructor for the Import Mappings pane */
	public MappingSchemasPage()
	{
		super("SchemasPage");
		setTitle("Select Schemas");
		setDescription("Select the schemas associated with the mapping");
	}
	
	/** Constructs the Mappings Schema page */
	public void createControl(Composite parent)
	{
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.NONE);
		pane.setLayout(new GridLayout(1,false));
		pane.setLayoutData(new GridData(GridData.FILL_BOTH));
		setControl(pane);

		// Construct the group pane for showing the schemas to map
		Group group = new Group(pane, SWT.NONE);
		group.setText("Schemas");
		group.setLayout(new GridLayout(1,false));
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
	
		// Construct the scrolling pane for showing the schemas to map
		scrolledPane = new ScrolledComposite(group, SWT.V_SCROLL);
		scrolledPane.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	/** Initializes the Mapping Schema page as needed */
	public void setVisible(boolean visible)
	{
		// Updates the page if the current URI has changed
		MappingPropertiesPage propertiesPage = ((ImportMappingWizard)getWizard()).getPropertiesPage();
		if(!propertiesPage.getURI().equals(currentURI))
		{
			// Update the current URI
			currentURI = propertiesPage.getURI();

			// Replace the displayed schemas to reflect the new URI
			Composite schemaPane = new Composite(scrolledPane, SWT.NONE);
			schemaPane.setLayout(new GridLayout(2,false));
			try {
				propertiesPage.getImporter().initialize(currentURI);
				for(MappingSchema schema : propertiesPage.getImporter().getSchemas())
					selectionPanes.add(new SelectionPane(schemaPane,schema));
			} catch(Exception e) { System.out.println("(E) MappingSchemasPage.setVisible - " + e.getMessage()); }
			
			// Refresh the scroll pane to display the updated schemas
			scrolledPane.setContent(schemaPane);
			scrolledPane.setExpandVertical(true);
			scrolledPane.setExpandHorizontal(true);
			scrolledPane.setMinSize(schemaPane.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			
			// Check page completion
			updatePageCompleteStatus();
		}
			
		// Make visible
		super.setVisible(visible);
	}
	
	/** Returns the generated list of aligned schema IDs */
	ArrayList<Integer> getSchemaIDs()
	{
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		for(SelectionPane selectionPane : selectionPanes)
			schemaIDs.add(selectionPane.getMappingSchema().getId());
		return schemaIDs;
	}
	
	/** Checks completion of page */
	private void updatePageCompleteStatus()
	{
		boolean completed = true;
		for(SelectionPane selectionPane : selectionPanes)
			if(selectionPane.getMappingSchema()==null) completed = false;
		setPageComplete(completed);	
	}
}