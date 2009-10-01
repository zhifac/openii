package org.mitre.openii.editors.groups;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.mitre.affinity.clusters.ClusterGroup;
import org.mitre.affinity.view.cluster_details.ClusterDetailsDlg;
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
import org.mitre.openii.views.manager.groups.EditGroupDialog;
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
	private Menu menu;
	
	/** Displays the Venn Diagram View */
	public void createPartControl(final Composite parent)
	{
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
					sets = new VennDiagramSets(schemaInfos.get(0), schemaInfos.get(1), 0.6, 1.0, AffinityEditor.matchScoreComputer);
				else
					matrix = new VennDiagramSetsMatrix(schemaInfos, 0.6, 1.0, AffinityEditor.matchScoreComputer);
			}
			setPartName(OpenIIManager.getGroup(elementID).getName());
		}
		else
			setPartName("New Group");	
		
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
				//Open selected schemas in Harmony 
				//EditorManager.launchEditor("HarmonyEditor", selectedSchemas);
			}
		};
		
		MenuItem item = new MenuItem (menu, SWT.NONE);	
		item.setText("Open schemas in Harmony");
		item.addSelectionListener(menuListener);
		
		return menu;
	}

	/** Show right-click menu with option to open the schemas corresponding to the selected venn 
	 * diagram in Harmony */
	@Override
	public void vennDiagramSelected(final VennDiagramEvent event) {
		if(event.getMouseButton() == 3 && menu != null) {	
			menu.getDisplay().asyncExec(new Runnable() {
				public void run() {			
					menu.setVisible(true);
				}
			});
		}
	}	
}
