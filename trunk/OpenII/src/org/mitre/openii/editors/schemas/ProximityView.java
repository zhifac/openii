package org.mitre.openii.editors.schemas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.mitre.affinity.clusters.DistanceGrid;
import org.mitre.affinity.clusters.distanceFunctions.JaccardDistanceFunction;
import org.mitre.affinity.model.AffinitySchemaManager;
import org.mitre.affinity.model.AffinitySchemaStoreManager;
import org.mitre.affinity.view.venn_diagram.VennDiagramUtils;
import org.mitre.affinity.view.venn_diagram.model.CachedFilteredSchemaInfo;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSetsMatrix;
import org.mitre.affinity.view.venn_diagram.view.event.VennDiagramEvent;
import org.mitre.affinity.view.venn_diagram.view.event.VennDiagramListener;
import org.mitre.affinity.view.venn_diagram.view.swt.SWTVennDiagramMatrix_KNeighbors;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.editors.tags.AffinityEditor;
import org.mitre.openii.model.EditorInput;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.views.manager.SchemaInProject;
import org.mitre.openii.views.manager.SchemaInTag;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/**
 * Constructs the Proximity View
 * @author CBONACETO
 */
public class ProximityView extends OpenIIEditor implements VennDiagramListener
{
	// Stores the proximity view
	private SWTVennDiagramMatrix_KNeighbors proximityView;

	private VennDiagramEvent currSelectionEvent;
	private Menu menu;
	
	/** Reduces the schema list to 9 comparison schemas */
	private ArrayList<Integer> reduceSchemaList(ArrayList<Integer> schemaIDs)
	{
		// Don't reduce schemas if less than 9 comparison schema present
		if(schemaIDs.size()>10)
		{
			// Run distance metrics on schemas
			JaccardDistanceFunction distanceFunction = new JaccardDistanceFunction();
			AffinitySchemaStoreManager.setConnection(RepositoryManager.getClient());
			DistanceGrid distanceGrid = distanceFunction.generateDistanceGrid(schemaIDs, new AffinitySchemaManager());
			
			// Retrieve the distances for the aligned schema
			HashMap<Integer,Double> schemaDistances = new HashMap<Integer,Double>();
			for(Integer schemaID : schemaIDs)
			{
				Double score = schemaID.equals(elementID) ? 0.0 : distanceGrid.get(elementID, schemaID);
				schemaDistances.put(schemaID,score);
			}
			
			// Find the distance threshold
			ArrayList<Double> distances = new ArrayList<Double>(schemaDistances.values());
			Collections.sort(distances);
			Double threshold = distances.get(9);
			
			// Remove all weakly associated schemas
			for(Integer schemaID : new ArrayList<Integer>(schemaIDs))
				if(schemaDistances.get(schemaID)>threshold) schemaIDs.remove(schemaID);
		}
		return schemaIDs;
	}
	
	/** Displays the Venn Diagram View */
	public void createPartControl(final Composite parent)
	{
		// Retrieve the element to be displayed in the proximity view
		Object element = ((EditorInput)getEditorInput()).getElement();

		// Generate the matrix object
		VennDiagramSetsMatrix matrix = null;	
		if(element instanceof VennDiagramSetsMatrix)
		{
			// Retrieve the matrix
			matrix = (VennDiagramSetsMatrix)element;

			// Sets the selected schema information
			Schema schema = matrix.getMatrixEntry(0, 0).getSchema1();
			elementID = schema.getId();
			setPartName(schema.getName());
		}
		
		// Extracts matrix object from schema within group
		else
		{
			// Get schema group info
			ArrayList<Integer> schemaIDs = null;
			if(element instanceof Schema)
				schemaIDs = OpenIIManager.getSchemaIDs();
			else if(element instanceof SchemaInTag)
				schemaIDs = OpenIIManager.getTagSchemas(((SchemaInTag)element).getTagID());
			else if(element instanceof SchemaInProject)
				schemaIDs = new ArrayList<Integer>(Arrays.asList(OpenIIManager.getProject(((SchemaInProject)element).getProjectID()).getSchemaIDs()));
			
			// Filter the schema list down to 9 comparison schemas
			schemaIDs = reduceSchemaList(schemaIDs);
			
			// Retrieve info for the schemas
			ArrayList<FilteredSchemaInfo> schemas = new ArrayList<FilteredSchemaInfo>();
			for(Integer schemaID : schemaIDs)
			{				
				HierarchicalSchemaInfo hierSchemaInfo = new HierarchicalSchemaInfo(OpenIIManager.getSchemaInfo(schemaID));
				CachedFilteredSchemaInfo schemaInfo = new CachedFilteredSchemaInfo(hierSchemaInfo);
				VennDiagramUtils.deleteUnnamedElements(schemaInfo);					
				VennDiagramUtils.sortFilteredElements(schemaInfo);
				schemas.add(schemaInfo);
			}			
			
			// Generate the matrix
			String schemaName = OpenIIManager.getSchema(elementID).getName();
			matrix = new VennDiagramSetsMatrix(schemaName, schemas, AffinityEditor.matchScoreComputer, 1);
		}
		
		// Launch the proximity view
		parent.setLayout(new FillLayout());
		this.proximityView = new SWTVennDiagramMatrix_KNeighbors(parent, SWT.NONE, matrix, true);
		proximityView.getVennDiagramMatrix().addVennDiagramListener(this);

		//Construct the right-click menu
		this.menu = createMenu(proximityView);
	}
	
	/** Displays a drop-down menu in the Venn Diagram editor */
	private Menu createMenu(Composite parent)
	{
		// Handles the selection of the Harmony menu item
		SelectionListener menuListener = new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				// Create a temporary project with the selected schemas
				Schema schema1 = currSelectionEvent.getSets().getSchema1();
				Schema schema2 = currSelectionEvent.getSets().getSchema2();
				Project project = createProject(schema1,schema2);

				// Launch the Harmony editor on the temporary project
				EditorManager.launchDefaultEditor(project);
			
				// Remove the temporary project
				OpenIIManager.deleteMapping(project.getId());
			}
		};
		
		// Generate the drop-down menu
		Menu menu = new Menu(proximityView);
		MenuItem item = new MenuItem (menu, SWT.NONE);	
		item.setText("Open schemas in Harmony");
		item.addSelectionListener(menuListener);		
		return menu;
	}
	
	/** Create a temporary project using the given schemas */
	private Project createProject(Schema schema1, Schema schema2)
	{
		// Generate the project
		ProjectSchema leftSchema = new ProjectSchema(schema1.getId(), schema1.getName(), null);
		ProjectSchema rightSchema = new ProjectSchema(schema2.getId(), schema2.getName(), null);
		ProjectSchema[] schemas = new ProjectSchema[]{leftSchema,rightSchema};
		String pair = schema1.getName() + " to " + schema2.getName();
		Project project = new Project(null,pair,"Mapping from " + pair,"",schemas);
		OpenIIManager.addProject(project);
		
		// Generate the mapping
		Mapping mapping = new Mapping(null,project.getId(),schema1.getId(),schema2.getId());
		OpenIIManager.addMapping(mapping);

		// Return the generated project
		return project;
	}

	/** Show right-click menu with option to open the schemas corresponding to the selected venn 
	 * diagram in Harmony */
	public void vennDiagramSelected(final VennDiagramEvent event) {
		if((event.getMouseButton() == 3 || (event.getMouseButton() == 1 && event.isControlDown())) 
				&& menu != null && event.getSets() != null) {	
			menu.getDisplay().asyncExec(new Runnable() {
				public void run() {		
					currSelectionEvent = event;
					menu.setVisible(true);
				}
			});
		}
	}	
}
