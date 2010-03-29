// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.schemastore.porters.mappingExporters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/**
 * Class for exporting a mapping formatted in csv
 * @author cwolf
 */
public class ExcelExporter extends MappingExporter
{
	/** Private class used to flag an array when items are deleted */ @SuppressWarnings("serial")
	private class FlaggedArrayList extends ArrayList<SchemaElement>
	{
		boolean flag = false;
		private FlaggedArrayList() { super(); }
		private FlaggedArrayList(SchemaElement element) { super(); add(element); }
		public boolean remove(Object o) { flag = true; 	return super.remove(o); }
		private boolean isFlagged() { return flag; }
	}
	
	/** Stores the mapping being exported. */
	private Mapping mapping;
	
	/** Stores the source schema used in the mapping */
	private SchemaInfo sourceInfo;
	
	/** Stores the target schema used in the mapping */
	private SchemaInfo targetInfo;
	
	/** Maps the target "containers" to the mapping cells that reference the container */
	HashMap<SchemaElement, ArrayList<MappingCell>> mappingCellHash;
	
	/** Maps the "containers" to the elements in the container that have yet to be matched */
	private HashMap<Integer, FlaggedArrayList> containerHash;
	
	/** Returns the exporter name */
	public String getName()
		{ return "Excel Exporter"; }
	
	/** Returns the exporter description */
	public String getDescription()
		{ return "This exporter generates an Excel file with one set of columns for the source and another set for the target.  Selected mismatches are also exported."; }
	
	/** Returns the file types associated with this converter */
	public String getFileType()
		{ return ".csv"; }
	
	/** Generates a data dictionary for this project */
	public void exportMapping(Project project, Mapping mapping, ArrayList<MappingCell> mappingCells, File file) throws IOException
	{
		// Initialize the hashes used in exporting the mapping
		this.mapping = mapping;
		initHashMaps(mappingCells);

		// Export the mapping
		PrintWriter out = new PrintWriter(new FileWriter(file));
		out.println("Source Type,Source Property,Source Documentation,Target Type,Target Property,Target Documentation,Score,Comment");
		exportMatchedElements(out);
		exportUnmatchedElements(out);
    	out.close();
	}
	
	/** Populates the hash maps that map containers to a) their contents and b) their mapping cells */
	private void initHashMaps(ArrayList<MappingCell> cells) throws RemoteException
	{
		// Initialize the source and target schemas
		sourceInfo = client.getSchemaInfo(mapping.getSourceId());
		targetInfo = client.getSchemaInfo(mapping.getTargetId());

		// Initialize the containers
		containerHash = new HashMap<Integer,FlaggedArrayList>();		
		for(SchemaInfo schemaInfo : new SchemaInfo[]{sourceInfo,targetInfo})
			initializeContainers(schemaInfo);
		
		// Initialize the mapping cells
		mappingCellHash = new HashMap<SchemaElement, ArrayList<MappingCell>>();
		for(MappingCell cell : cells)
		{
			SchemaElement containingElement = getContainingElement(cell.getOutput());
			ArrayList<MappingCell> mappingCells = mappingCellHash.get(containingElement);
			if(mappingCells==null) mappingCellHash.put(containingElement, mappingCells = new ArrayList<MappingCell>());
			mappingCells.add(cell);
		}
	}

	/** Initializes the containers with their associated schema elements */
	private void initializeContainers(SchemaInfo schemaInfo)
	{
		// Stores entities in their own containers
		for (SchemaElement entity : schemaInfo.getElements(Entity.class))
			containerHash.put(entity.getId(), new FlaggedArrayList(entity));
		containerHash.put(null, new FlaggedArrayList(null));
		
		// Store attributes in the associated entity container
		for (SchemaElement attribute : schemaInfo.getElements(Attribute.class))
			containerHash.get(((Attribute)attribute).getEntityID()).add(attribute);

		// Stores containments in the associated entity container
		for (SchemaElement containment : schemaInfo.getElements(Containment.class))
			containerHash.get(((Containment)containment).getParentID()).add(containment);
		
		// Stores domains and their associated values in domain containers
		for (SchemaElement domain : schemaInfo.getElements(Domain.class))
		{
			FlaggedArrayList children = new FlaggedArrayList();
			children.add(domain);
			children.addAll(schemaInfo.getDomainValuesForDomain(domain.getId()));
			containerHash.put(domain.getId(), children);
		}
		
		// Stores relationships in their own containers
		for (SchemaElement relationship : schemaInfo.getElements(Relationship.class))
			containerHash.put(relationship.getId(), new FlaggedArrayList());
	}

	/** Exports all of the pairs of matched elements */
	private void exportMatchedElements(PrintWriter out) throws RemoteException
	{
		// Iterate over each mapping cell pairing (grouped by element container)
		for(SchemaElement targetBase : mappingCellHash.keySet())
			for(MappingCell mappingCell : mappingCellHash.get(targetBase))
				for(Integer sourceID : mappingCell.getInput())
				{
					// Retrieve the source and target element
					SchemaElement sourceElement = findElementByID(sourceID);
					SchemaElement sourceBase = getContainingElement(sourceID);
					SchemaElement targetElement = findElementByID(mappingCell.getOutput());

					// Output the results
					out.println(getElementString(sourceBase, sourceElement) + "," +
								getElementString(targetBase, targetElement) + "," +							
								mappingCell.getScore() + ",\"" + mappingCell.getNotes() + "\"");

					// Remove the source and target elements from the container hash
					containerHash.get(targetBase.getId()).remove(targetElement);
					containerHash.get(sourceBase.getId()).remove(sourceElement);
				}
	}
	
	/** Exports all unmatched elements, provided the container has at least one exported match */
	private void exportUnmatchedElements(PrintWriter out) throws RemoteException
	{
		// Identify all target containers
		HashSet<Integer> targetContainers = new HashSet<Integer>();
		for(SchemaElement targetContainer : mappingCellHash.keySet())
			targetContainers.add(targetContainer.getId());
		
		// Cycle through all containers to identify unmatched elements
		for(Integer baseID : containerHash.keySet())
		{
			// Make sure that container was associated with at least one match
			FlaggedArrayList elements = containerHash.get(baseID);
			if(!elements.isFlagged()) continue;

			// Determine if the container is part of the source or target schema	
			boolean isSource = !targetContainers.contains(baseID);
			SchemaElement base = isSource ? sourceInfo.getElement(baseID) : targetInfo.getElement(baseID);

			// Display the unmatched elements which make up the container
			for(SchemaElement child : containerHash.get(baseID))
			{
				String sourceString = isSource ? getElementString(base, child) : ",,";
				String targetString = isSource ? ",," : getElementString(base, child);
				out.println(sourceString + "," + targetString);
			}
		}
	}
	
	/** Generates a string for the specified base and element */
	private String getElementString(SchemaElement base, SchemaElement element)
		{ return "\""+getDisplayName(base) + "\",\"" + getDisplayName(base,element) + "\",\"" + element.getDescription() + "\""; }
	
	/** Finds a given schema element by iterating through all available schemata */
	private SchemaElement findElementByID(Integer elementID) throws RemoteException
	{
		if(elementID == null) return null;
		if(sourceInfo.containsElement(elementID)) return sourceInfo.getElement(elementID);
		if(targetInfo.containsElement(elementID)) return targetInfo.getElement(elementID);
		return null;
	}

	/** Finds the display name (which accounts for anonymous elements) by iterating through all available schemas */
	private String getDisplayName(SchemaElement root, SchemaElement element)
		{ if(element.equals(root)) return "-"; return getDisplayName(element); }
	
	/** Finds the display name (which accounts for anonymous elements) by iterating through all available schemas */
	private String getDisplayName(SchemaElement element)
	{
		if(element == null) return "[root]";
		if(sourceInfo.containsElement(element.getId())) return sourceInfo.getDisplayName(element.getId());
		if(targetInfo.containsElement(element.getId())) return targetInfo.getDisplayName(element.getId());
		return null;
	}
	
	/**
	 * Finds the container for a given element: attributes and containments are stored with the parent entity, domain values are
	 * stored with their domains, all other elements are stored in themselves (entities, relationships, and domains).
	 */
	private SchemaElement getContainingElement(Integer elementID) throws RemoteException
	{
		if(elementID == null) return null;
		SchemaElement base = findElementByID(elementID);
		if(base instanceof DomainValue)
			return findElementByID(((DomainValue)base).getDomainID());
		if(base instanceof Attribute)
			return findElementByID(((Attribute)base).getEntityID());
		if(base instanceof Containment)
			return findElementByID(((Containment)base).getParentID());
		return base;
	}
}