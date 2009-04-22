// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.schemastore.porters.mappingExporters;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.Porter;

/** Export Class - An exporter enables the exporting of projects */
public abstract class MappingExporter extends Porter
{
	/** Return the file type available for use with this exporter */
	abstract public String getFileType();

	/** Exports the project to the specified file */
	abstract public void exportMapping(Mapping mapping, ArrayList<MappingCell> mappingCells, File file) throws IOException;
	
	/** Generates a hash map of all schema elements contained within the mapping */
	protected HashMap<Integer,SchemaElement> getSchemaElements(List<Integer> schemaIDs) throws RemoteException
	{
		HashMap<Integer,SchemaElement> elements = new HashMap<Integer,SchemaElement>();
		for(Integer schemaID : schemaIDs)
			for(SchemaElement element : client.getGraph(schemaID).getElements(null))
				elements.put(element.getId(),element);
		return elements;
	}
	
	/** Returns the list of mapping cells which contain the specified element */
	protected ArrayList<MappingCell> getMappingCellsByElement(Integer elementID, ArrayList<MappingCell> mappingCells)
	{
		ArrayList<MappingCell> mappingCellsWithElement = new ArrayList<MappingCell>();
		for(MappingCell mappingCell : mappingCells)
			if(mappingCell.getElement1().equals(elementID) || mappingCell.getElement2().equals(elementID))
				mappingCellsWithElement.add(mappingCell);
		return mappingCellsWithElement;
	}
}