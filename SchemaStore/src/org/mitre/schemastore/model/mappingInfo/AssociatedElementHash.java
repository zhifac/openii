package org.mitre.schemastore.model.mappingInfo;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.model.MappingCell;

public class AssociatedElementHash {
	/** Stores the mapping cells */
	private HashMap<Integer, ArrayList<MappingCell>> mappingCellHash = new HashMap<Integer, ArrayList<MappingCell>>();

	public AssociatedElementHash(ArrayList<MappingCell> mappingCells) {
		for (MappingCell mappingCell : mappingCells)
			set(mappingCell);
	}

	/** Sets the specified mapping cell */
	public void set(MappingCell mappingCell) {
		Integer output = mappingCell.getOutput();
		for (Integer input : mappingCell.getElementInputIDs()) {
			if (!mappingCellHash.containsKey(input))
				mappingCellHash.put(input, new ArrayList<MappingCell>());
			if (!mappingCellHash.containsKey(output))
				mappingCellHash.put(output, new ArrayList<MappingCell>());

			mappingCellHash.get(input).add(mappingCell);
			mappingCellHash.get(output).add(mappingCell);
		}
	}

	/**
	 * Returns a set of mappings cells related to the element ID regardless of
	 * whether it be an input or an output
	 **/
	public ArrayList<MappingCell> get(Integer elementID) {
		if (mappingCellHash.containsKey(elementID))
			return mappingCellHash.get(elementID);
		else
			return new ArrayList<MappingCell>(0);
	}

	/** Deletes the specified mapping cell */
	public void delete(MappingCell mappingCell) {
		for (Integer input : mappingCell.getElementInputIDs())
			if (mappingCellHash.containsKey(input))
				mappingCellHash.get(input).remove(mappingCell);

		Integer output = mappingCell.getOutput();
		if (mappingCellHash.containsKey(output))
			mappingCellHash.get(output).remove(mappingCell);
	}

	/** Returns the specified mapping cell */
	public MappingCell get(Integer inputID, Integer outputID) {
		if (mappingCellHash.containsKey(inputID)) {
			for (MappingCell cell : mappingCellHash.get(inputID))
				if (cell.getOutput().equals(outputID))
					return cell;
		}
		return null;
	}

	/** Indicates if the mapping contains the specified mapping cell */
	public boolean contains(Integer inputID, Integer outputID) {
		return contains(new Integer[] { inputID }, outputID);
	}

	/** Indicates if the mapping contains the specified mapping cell */
	public boolean contains(Integer inputIDs[], Integer outputID) {
		for (Integer input : inputIDs)
			if (get(input, outputID) != null)
				return true;
		return false;
	}

}
