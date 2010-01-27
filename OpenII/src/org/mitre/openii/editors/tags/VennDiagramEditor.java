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
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.EditorInput;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/**
 * Constructs a Venn Diagram View
 * 
 * @author CBONACETO
 *
 */
public class VennDiagramEditor extends OpenIIEditor implements VennDiagramListener
{	
	private SWTVennDiagramMatrix vennDiagramMatrix;
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
					sets = new VennDiagramSets(schemaInfos.get(0), schemaInfos.get(1), 0.6, 1.0, AffinityEditor.matchScoreComputer);
				else
					matrix = new VennDiagramSetsMatrix(schemaInfos, 0.6, 1.0, AffinityEditor.matchScoreComputer);
			}
			setPartName(elementID!=null ? OpenIIManager.getTag(elementID).getName() : "All Schemas");
		}
		else
			setPartName("New Tag");	
		
		if(sets != null) {
			new SWTVennDiagram(parent, SWT.NONE, sets, true);
		}
		else if(matrix != null) {
			this.vennDiagramMatrix = new SWTVennDiagramMatrix(parent, SWT.NONE, matrix, true);
			vennDiagramMatrix.getVennDiagramMatrix().addVennDiagramListener(this);
			//Construct the right-click menu
			this.menu = createMenu(vennDiagramMatrix);
		}
	}
	
	private Menu createMenu(Composite parent) {
		Menu menu = new Menu(vennDiagramMatrix);
		SelectionListener menuListener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {				
				//Create a temporary mapping with the selected schemas and open in a new Harmony tab				
				Mapping tempMapping = createMapping(currSelectionEvent.getSets().getSchema1(),
						currSelectionEvent.getSets().getSchema2());				
				OpenIIManager.addProject(tempMapping);	
				EditorManager.launchDefaultEditor(tempMapping);				
			
				//Remove the temporary mapping
				OpenIIManager.deleteMapping(tempMapping.getId());
			}
		};
		
		MenuItem item = new MenuItem (menu, SWT.NONE);	
		item.setText("Open schemas in Harmony");
		item.addSelectionListener(menuListener);
		
		return menu;
	}
	
	/** Create a temporary mapping using the given schemas */
	private Mapping createMapping(Schema s1, Schema s2) {
		Integer mappingID = 99;
		//while(OpenIIManager.getMapping(mappingID) != null) mappingID++;		
		
		MappingSchema leftSchema = new MappingSchema(s1.getId(), s1.getName(), "", MappingSchema.LEFT);
		//leftSchema.seetSchemaModel(OpenIIManager.getSchemaInfo(s1.getId()).get);		
		MappingSchema rightSchema = new MappingSchema(s2.getId(), s1.getName(), "", MappingSchema.RIGHT);;
		Mapping mapping = new Mapping(mappingID, s1.getName() + " to " + s2.getName(), 
				"Mapping of " + s1.getName() + ", and " + s2.getName(),
				"", new MappingSchema[] {leftSchema, rightSchema});
		mapping.setLeftSchema(leftSchema);
		mapping.setRightSchema(rightSchema);
		return mapping;
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
