package org.mitre.openii.editors.mappings.quickAlign;

import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/** Constructs the Quick Alignment Match */
class QuickAlignMatch
{
	/** Stores the mapping cell associated with the match */
	private MappingCell mappingCell;
	
	/** Stores the schema element associated with the match */
	private SchemaElement element;
	
	/** Constructs the match */
	QuickAlignMatch(MappingCell mappingCell, SchemaInfo schemaInfo)
	{
		this.mappingCell = mappingCell;
		element = schemaInfo.getElement(mappingCell.getOutput());
	}
	
	/** Returns the mapping cell associated with this match */
	MappingCell getMappingCell()
		{ return mappingCell; }
	
	/** Returns the element associated with this match */
	SchemaElement getElement()
		{ return element; }
	
	/** Displays the string equivalent of this class */
	public String toString()
		{ return element.toString(); }

	/** Indicates if two Quick Align Matches are equal */
	public boolean equals(Object match)
	{
		if(match instanceof QuickAlignMatch)
			return element.equals(((QuickAlignMatch)match).element);
		return false;
	}
}