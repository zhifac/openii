package GUI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.mitre.harmony.model.selectedInfo.SelectedInfoListener;
import org.mitre.harmony.model.selectedInfo.SelectedInfoManager;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;

public class RMapSelectedInfoManager extends SelectedInfoManager {
	
	/** Caches schema information for Logical Relation "schemas" by Dependency */
	private RMapHarmonyModel _harmonyModel;
	
	public RMapSelectedInfoManager(RMapHarmonyModel harmonyModel){
		super(null);
		_harmonyModel = harmonyModel;
	}
	/** Returns the Harmony model */
	protected RMapHarmonyModel getModel()
		{ return _harmonyModel; }
	

	/** Toggles the selected mapping cells */
	public void setMappingCells(List<Integer> mappingCells, boolean append)
	{
		// Determine if the specified mapping cells should be treated as selected
		boolean selected = true;		
		for(Integer mappingCell : mappingCells)
			if(!selectedMappingCells.contains(mappingCell)) { selected=false; break; }
		if(!append) selected &= mappingCells.size()==selectedMappingCells.size();
		
		// Handles the case where the specified mapping cells should replace the old selected mapping cells
		if(!append) setSelectedMappingCells(selected ? new ArrayList<Integer>() : mappingCells, REPLACE);
		else setSelectedMappingCells(mappingCells,selected?REMOVE:ADD);
	}
	
	/** Sets the selected mapping cells */
	private void setSelectedMappingCells(List<Integer> mappingCells, Integer mode)
	{
		// Set the selected mapping cells
		if(!updateSet(selectedMappingCells,mappingCells,mode)) return;
		for(SelectedInfoListener listener : getListeners()) listener.selectedMappingCellsModified();

		// Identify changes to the elements required by the newly selected mapping cells
		ArrayList<Integer> selectedLeftElements = new ArrayList<Integer>();
		ArrayList<Integer> selectedRightElements = new ArrayList<Integer>();		
		HashSet<Integer> leftElements = getModel().getMappingManager().getSchemaElementIDs(MappingSchema.LEFT);
		HashSet<Integer> rightElements = getModel().getMappingManager().getSchemaElementIDs(MappingSchema.RIGHT);
		for(Integer mappingCellID : getSelectedMappingCells())
		{
			// Identify the elements for the mapping cell
			MappingCell mappingCell = getModel().getMappingCellManager().getMappingCell(mappingCellID);
			Integer[] element11 = mappingCell.getInput(); 
			Integer element2 = mappingCell.getOutput();
			
			boolean leftElementsContainInput = true;
			for (Integer input: element11)
				leftElementsContainInput &= leftElements.contains(input);
			
			boolean rightElementsContainInput = true;
			for (Integer input: element11)
				rightElementsContainInput &= rightElements.contains(input);
			
			// Mark selected elements
			if(leftElementsContainInput && rightElements.contains(element2)){ 
				for (Integer source : element11)
					selectedLeftElements.add(source); 
				selectedRightElements.add(element2); 
			}
			if(leftElements.contains(element2) && rightElementsContainInput){ 
				selectedLeftElements.add(element2); 
				for (Integer source : element11)
					selectedLeftElements.add(source); 
				
			}
		}

		// Update the elements
		Integer sides[] = { MappingSchema.LEFT, MappingSchema.RIGHT };
		for(Integer side : sides)
		{
			ArrayList<Integer> selectedElements = (side.equals(MappingSchema.LEFT)?selectedLeftElements:selectedRightElements);
			if(updateSet(getSelectedElementSet(side),selectedElements,REPLACE))
				for(SelectedInfoListener listener : getListeners())
					listener.selectedElementsModified(side);		
		}
			
		// Update the displayed elements
		updateDisplayedElements();
	}

	
}