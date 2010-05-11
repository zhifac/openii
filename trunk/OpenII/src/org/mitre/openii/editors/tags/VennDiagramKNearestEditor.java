package org.mitre.openii.editors.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorInput;
import org.mitre.affinity.view.venn_diagram.VennDiagramUtils;
import org.mitre.affinity.view.venn_diagram.model.CachedFilteredSchemaInfo;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSetsMatrix;
import org.mitre.affinity.view.venn_diagram.view.event.VennDiagramEvent;
import org.mitre.affinity.view.venn_diagram.view.event.VennDiagramListener;
import org.mitre.affinity.view.venn_diagram.view.swt.SWTVennDiagram;
import org.mitre.affinity.view.venn_diagram.view.swt.SWTVennDiagramMatrix;
import org.mitre.affinity.view.venn_diagram.view.swt.SWTVennDiagramMatrix_KNeighbors;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.EditorInput;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/**
 * Constructs a Venn Diagram View
 * 
 * @author CBONACETO
 *
 */
public class VennDiagramKNearestEditor extends OpenIIEditor implements VennDiagramListener
{	
	private SWTVennDiagramMatrix_KNeighbors vennKNeighborsDiagramMatrix;
	private VennDiagramEvent currSelectionEvent;
	private Menu menu;
	
	/** Displays the Venn Diagram View */ @SuppressWarnings("unchecked")
	public void createPartControl(final Composite parent)
	{
		//Construct the Venn Diagram pane
		parent.setLayout(new FillLayout());

		VennDiagramSets sets = null;
		VennDiagramSetsMatrix matrix = null;	
		
		//Get the sets or matrix object passed in as the element of the editor input
		IEditorInput editorInput = getEditorInput();
		if(editorInput != null && editorInput instanceof EditorInput) {
			Object element = ((EditorInput)editorInput).getElement();
			if(element != null) {
				if(element instanceof VennDiagramSets) 
					sets = (VennDiagramSets)element;
				else if(element instanceof VennDiagramSetsMatrix) 
					matrix = (VennDiagramSetsMatrix)element;				
			}
		}
		
		if(sets == null && matrix == null)
		{
			// Retrieve the schema IDs from which to generate the Venn diagram chart
			ArrayList<Integer> schemaIDs = null;
			if(elementID!=null)
			{
				schemaIDs = OpenIIManager.getTagSchemas(elementID);
				schemaIDs.addAll(OpenIIManager.getChildTagSchemas(elementID));				
			}
			else schemaIDs = new ArrayList<Integer>((Collection)((EditorInput)editorInput).getElement());
			
			//If given a tag, get all the schemas with the tag and create a Venn Diagram
			if(schemaIDs != null && schemaIDs.size() > 1) {
				Iterator<Integer> iter = schemaIDs.iterator();
				ArrayList<FilteredSchemaInfo> schemaInfos = new ArrayList<FilteredSchemaInfo>();
				while(iter.hasNext()) {
					CachedFilteredSchemaInfo schemaInfo =
						new CachedFilteredSchemaInfo(new HierarchicalSchemaInfo(OpenIIManager.getSchemaInfo(iter.next()), null));
					VennDiagramUtils.deleteUnnamedElements(schemaInfo);					
					VennDiagramUtils.sortFilteredElements(schemaInfo);
					schemaInfos.add(schemaInfo);
				}
				if(schemaInfos.size() == 2)
					sets = new VennDiagramSets(schemaInfos.get(0), schemaInfos.get(1), 0.6, 1.0, AffinityEditor.matchScoreComputer, 2);
				else
					matrix = new VennDiagramSetsMatrix(schemaInfos, AffinityEditor.matchScoreComputer, 2);
			}
			setPartName(elementID!=null ? OpenIIManager.getTag(elementID).getName() : "All Schemas");
		}
		else
			setPartName("New Tag");	
		
		if(sets != null) {
			new SWTVennDiagram(parent, SWT.NONE, sets, true);
		}
		else if(matrix != null) {
			this.vennKNeighborsDiagramMatrix = new SWTVennDiagramMatrix_KNeighbors(parent, SWT.NONE, matrix, true);
			vennKNeighborsDiagramMatrix.getVennDiagramMatrix().addVennDiagramListener(this);
			//Construct the right-click menu
			this.menu = createMenu(vennKNeighborsDiagramMatrix);
		}
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
		Menu menu = new Menu(vennKNeighborsDiagramMatrix);
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
		Project project = new Project(null,pair,"Mapping frpm " + pair,"",schemas);
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
