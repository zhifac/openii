package org.mitre.openii.editors.mappings.quickAlign;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.mappingInfo.MappingInfo;

/** Constructs the Quick Alignment Mapping Editor */
public class QuickAlignEditor extends OpenIIEditor
{	
	/** Stores reference to the filter pane */
	private QuickAlignFilterPane filterPane = null;
	
	/** Stores reference to the matches pane */
	private QuickAlignMatchesPane matchesPane = null;
	
	/** Private class for handling the calculation of matches */
	class CalculateMatches implements SelectionListener
	{
		/** Handles the selection of the calculate button */
		public void widgetSelected(SelectionEvent e)
			{ matchesPane.recalculateMatches(); }
		
		// Unused listener event
		public void widgetDefaultSelected(SelectionEvent e) {}
	}

	/** Private class for handling the saving of matches */
	class SaveMatches implements SelectionListener
	{
		/** Handles the selection of the save button */
		public void widgetSelected(SelectionEvent e)
			{ matchesPane.saveMatches(); }
		
		// Unused listener event
		public void widgetDefaultSelected(SelectionEvent e) {}
	}
	
	/** Displays the Quick Alignment Editor */
	public void createPartControl(Composite parent)
	{		
		// Retrieves the mapping
		Integer mappingID = getElementID();
		MappingInfo mapping = new MappingInfo(OpenIIManager.getMapping(mappingID),OpenIIManager.getMappingCells(mappingID));
		
		// Constructs the editor pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		
		// Constructs the filter and matches pane
		filterPane = new QuickAlignFilterPane(pane,mapping.getMapping(),new CalculateMatches(), new SaveMatches());
		matchesPane = new QuickAlignMatchesPane(pane,mapping,filterPane);
	}
}