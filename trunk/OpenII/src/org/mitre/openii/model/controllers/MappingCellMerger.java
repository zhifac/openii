package org.mitre.openii.model.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.mitre.schemastore.model.MappingCell;

/** Handles the merging of mapping cells */
public class MappingCellMerger
{
	/** Private class for comparing mapping cells */
	static private class MappingCellComparator implements Comparator<MappingCell>
	{
		public int compare(MappingCell mappingCell1, MappingCell mappingCell2)
		{
			// First prioritize validated mapping cells over unvalidated mapping cells
			if(mappingCell1.getValidated()!=mappingCell2.getValidated())
				return mappingCell2.getValidated().compareTo(mappingCell1.getValidated());

			// Next prioritize higher scores over lower scores
			return mappingCell2.getScore().compareTo(mappingCell1.getScore());
		}
	}

	/** Merges the provided mapping cells together */
	static public ArrayList<MappingCell> merge(ArrayList<MappingCell> mappingCells)
	{
		// Group together mapping cells in need of merging
		HashMap<String,ArrayList<MappingCell>> mappingCellHash = new HashMap<String,ArrayList<MappingCell>>();
		for(MappingCell mappingCell : mappingCells)
		{
			// Generate hash key
			Integer element1 = mappingCell.getFirstInput();
			Integer element2 = mappingCell.getOutput();
			String key = element1<element2 ? element1+"-"+element2 : element2+"-"+element1;
			
			// Place mapping cell in hash map
			ArrayList<MappingCell> mappingCellList = mappingCellHash.get(key);
			if(mappingCellList==null) mappingCellHash.put(key, mappingCellList = new ArrayList<MappingCell>());
			mappingCellList.add(mappingCell);
		}
		
		// Merge mapping cells into single list
		ArrayList<MappingCell> mergedMappingCells = new ArrayList<MappingCell>();
		for(ArrayList<MappingCell> mappingCellList : mappingCellHash.values())
		{
			Collections.sort(mappingCellList, new MappingCellComparator());
			mergedMappingCells.add(mappingCellList.get(0));
		}
		return mergedMappingCells;
	}
}
