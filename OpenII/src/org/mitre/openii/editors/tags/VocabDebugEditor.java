	package org.mitre.openii.editors.tags;

	import java.util.ArrayList;
	import java.util.Collection;
	import java.util.Iterator;

	import org.eclipse.swt.SWT;
	import org.eclipse.swt.layout.FillLayout;
	import org.eclipse.swt.widgets.Composite;
	import org.eclipse.swt.widgets.Menu;
	import org.eclipse.ui.IEditorInput;
	import org.mitre.affinity.view.venn_diagram.VennDiagramUtils;
	import org.mitre.affinity.view.venn_diagram.model.CachedFilteredSchemaInfo;
	import org.mitre.affinity.view.vocab_debug_view.model.VocabDebugViewSets;
	import org.mitre.affinity.view.vocab_debug_view.view.swt.SWTVocabDebugView;
	import org.mitre.openii.editors.OpenIIEditor;
	import org.mitre.openii.model.EditorInput;
	import org.mitre.openii.model.OpenIIManager;
	import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
	import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;


public class VocabDebugEditor extends OpenIIEditor{
		private Menu menu;
		
		/** Displays the VocabDebugView */ @SuppressWarnings("unchecked")
		public void createPartControl(final Composite parent)
		{
			//Construct the VocabDebugView pane
			parent.setLayout(new FillLayout());

			VocabDebugViewSets vdvSets = null;
			
			//Get the sets passed in as the editor input
			IEditorInput editorInput = getEditorInput();
			if(editorInput != null && editorInput instanceof EditorInput) {
				Object element = ((EditorInput)editorInput).getElement();
				if(element != null) {
					if(element instanceof VocabDebugViewSets)
						vdvSets = (VocabDebugViewSets)element;
					}
			}
			
		if(vdvSets == null)
			{
				// Retrieve the schema IDs from which to generate the vocab debug view
				ArrayList<Integer> schemaIDs = null;
				if(elementID!=null)
				{
					schemaIDs = OpenIIManager.getTagSchemas(elementID);
					schemaIDs.addAll(OpenIIManager.getChildTagSchemas(elementID));				
				}
				else schemaIDs = new ArrayList<Integer>((Collection)((EditorInput)editorInput).getElement());
				
				//If given a tag, get all the schemas with the tag and create a new VocabDebugView
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
					vdvSets = new VocabDebugViewSets(schemaInfos, 0.6, 1.0, AffinityEditor.matchScoreComputer);
			}
				setPartName(elementID!=null ? OpenIIManager.getTag(elementID).getName() : "All Schemas");
			}else{ 
				setPartName("New Tag");	
			}
		
			if(vdvSets != null){
							new SWTVocabDebugView(parent, SWT.NONE, vdvSets);
			}
		}

}


