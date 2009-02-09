// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.filters;

import java.util.Hashtable;

import org.mitre.harmony.model.MappingCellListener;
import org.mitre.harmony.model.MappingCellManager;
import org.mitre.schemastore.model.MappingCell;

/**
 * Keeps an updated listing of the maximum confidence associated with each element
 * @author CWOLF
 */
class ElementConfHashTable extends Hashtable<Integer,Double> implements MappingCellListener
{
	/** Constructor which initializes the hash table */
	ElementConfHashTable()
		{ MappingCellManager.addListener(this); }
	
	/** Retrieves the specified element's confidence from the hash table */
	public synchronized Double get(Integer elementID)
	{
		// Retrieves the cached node confidence
		Double confidence = super.get(elementID);
		if(confidence!=null) return confidence;
		
		// Calculates the node confidence
		confidence = Double.MIN_VALUE;
		for(Integer mappingCellID : MappingCellManager.getMappingCellsByElement(elementID))
		{
			Double mappingCellConf = MappingCellManager.getMappingCell(mappingCellID).getScore();
			if(mappingCellConf>confidence) confidence = mappingCellConf;
		}
		put(elementID,confidence);
		return confidence;
	}
	
	/** Updates confidences when a new mapping cell is added to the model */
	public void mappingCellAdded(MappingCell mappingCell)
	{
		// Retrieve mapping cell info
		Integer element1 = mappingCell.getElement1();
		Integer element2 = mappingCell.getElement2();
		Double conf = mappingCell.getScore();
		
		// Updates the first node's confidence score if needed
		Double element1Conf = super.get(element1);	
		if(element1Conf!=null && conf>element1Conf)
			{ put(element1,conf); Filters.fireMaxConfidenceChanged(element1); }
			
		// Updates the second node's confidence score if needed
		Double element2Conf = super.get(element2);
		if(element2Conf!=null && conf>element2Conf)
			{ put(element2,conf); Filters.fireMaxConfidenceChanged(element2); }
	}

	/** Updates confidences when a mapping cell is modified in the model */
	public void mappingCellModified(MappingCell oldMappingCell, MappingCell newMappingCell)
	{
		// Retrieve mapping cell info
		Integer element1 = newMappingCell.getElement1();
		Integer element2 = newMappingCell.getElement2();
		Double oldConf = oldMappingCell.getScore();
		Double newConf = newMappingCell.getScore();
		
		// Only update max confidence if the mapping cell's confidence changed
		if(oldConf!=newConf)
		{			
			// Modify the first node's confidence score as needed
			Double element1Conf = super.get(element1);
			if(element1Conf!=null)
			{
				boolean changed = false;
				if(newConf>element1Conf) { put(element1,newConf); changed = true; }
				else if(oldConf.equals(element1Conf)) { remove(element1); changed = true; }
				if(changed) Filters.fireMaxConfidenceChanged(element1);
			}
			
			// Modify the second node's confidence score as needed
			Double element2Conf = super.get(element2);
			if(element2Conf!=null)
			{
				boolean changed = false;
				if(newConf>element2Conf) { put(element2,newConf); changed = true; }
				else if(oldConf.equals(element2Conf))  { remove(element2); changed = true; }
				if(changed) Filters.fireMaxConfidenceChanged(element2);
			}
		}
	}
		
	/** Updates confidences when a mapping cell is removed from the model */
	public void mappingCellRemoved(MappingCell mappingCell)
	{
		// Retrieve mapping cell info
		Integer element1 = mappingCell.getElement1();
		Integer element2 = mappingCell.getElement2();
		Double conf = mappingCell.getScore();

		// Modify the first node's confidence score as needed
		Double element1Conf = super.get(element1);
		if(element1Conf!=null && conf.equals(element1Conf))
			{ remove(element1); Filters.fireMaxConfidenceChanged(element1); }
			
		// Modify the second node's confidence score as needed
		Double element2Conf = super.get(element2);
		if(element2Conf!=null && conf.equals(element2Conf)) 
			{ remove(element2); Filters.fireMaxConfidenceChanged(element2); }
	}
}