package org.mitre.openii.editors.groups;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.mitre.affinity.view.venn_diagram.VennDiagramUtils;
import org.mitre.affinity.view.venn_diagram.model.CachedFilteredSchemaInfo;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSetsMatrix;
import org.mitre.affinity.view.venn_diagram.view.swt.SWTVennDiagram;
import org.mitre.affinity.view.venn_diagram.view.swt.SWTVennDiagramMatrix;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.EditorInput;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/**
 * Constructs a Venn Diagram View
 * 
 * @author CBONACETO
 *
 */
public class VennDiagramEditor  extends OpenIIEditor 
{	
	/** Displays the Venn Diagram View */
	public void createPartControl(final Composite parent)
	{
		//FilteredSchemaInfo schemaInfo1, schemaInfo2, 0.4, 1.0, matchScoreComputer
		
		//Construct the Venn Diagram pane
		parent.setLayout(new FillLayout());

		VennDiagramSets sets = null;
		VennDiagramSetsMatrix matrix = null;		
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
		
		if(sets == null && matrix == null) {
			//If given a group, get all the schemas in the group and create a Venn Diagram
			ArrayList<Integer> schemaIDs = OpenIIManager.getGroupSchemas(elementID);
			schemaIDs.addAll(OpenIIManager.getChildGroupSchemas(elementID));
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
					sets = new VennDiagramSets(schemaInfos.get(0), schemaInfos.get(1), 0.4, 1.0, AffinityEditor.matchScoreComputer);
				else
					matrix = new VennDiagramSetsMatrix(schemaInfos, 0.4, 1.0, AffinityEditor.matchScoreComputer);
			}
			setPartName(OpenIIManager.getGroup(elementID).getName());
		}
		else
			setPartName("New Group");	
		
		if(sets != null) {
			new SWTVennDiagram(parent, SWT.NONE, sets, true);
		}
		else if(matrix != null) {
			new SWTVennDiagramMatrix(parent, SWT.NONE, matrix, true);
		}
	}	
}
