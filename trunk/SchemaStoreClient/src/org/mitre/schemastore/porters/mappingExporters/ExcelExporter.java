// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.schemastore.porters.mappingExporters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/**
 * Class for exporting a data dictionary from the project
 * @author pmork
 */
public class ExcelExporter extends MappingExporter
{
	/** Stores the mapping being exported. */
	private Mapping mapping;
	
	private PrintWriter out; 
	
	/** Maintains the information for ALL of the schemata referenced by the mapping. */
	ArrayList<SchemaInfo> schemataInfo;
	
	/** Maps the "containers" to the mappings that reference something in the container. */
	HashMap<SchemaElement, ArrayList<MappingCell>> container2mappingCells;
	
	/** Maps the "containers" to the elements for that container. It is used to track which containers/elements have NOT been matched. */
	private HashMap<SchemaElement, ArrayListHelper<SchemaElement>> container2contents;

	/**
	 * This helper class keeps track of whether or not an element has ever been removed from the array list.
	 * It also contains a boolean that is set whenever it is determined that a container (or one of its elements) is used as a target.
	 * @param <E> The type being maintained in the array list.
	 */
	private static class ArrayListHelper<E> extends ArrayList<E> {
		private boolean removeWasCalled = false;
		public boolean isSource = true;
		public boolean remove(Object o) {
			removeWasCalled = true;
			return super.remove(o);
		}
		public void reset() {
			removeWasCalled = false;
		}
	}
	
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
	public void exportMapping(Mapping mapping, ArrayList<MappingCell> mappingCells, File file) throws IOException
	{
		this.mapping = mapping;
		out = new PrintWriter(new FileWriter(file));
		initDataStructures();
		initHashMaps(mappingCells);
		out.println("Source Type,Source Property,Source Documentation,Target Type,Target Property,Target Documentation,Score,Comment");
		exportMatchedElements();
		exportUnmatchedElements();
    	out.close();
	}

	/**
	 * Initializes all of the internal data structures.
	 */
	private void initDataStructures() {
		container2mappingCells = new HashMap<SchemaElement, ArrayList<MappingCell>>();
		container2contents = new HashMap<SchemaElement,ArrayListHelper<SchemaElement>>();		
		schemataInfo = new ArrayList<SchemaInfo>();
	}
	
	/**
	 * Populates the hash maps that map containers to a) their contents and b) their mapping cells.
	 */
	private void initHashMaps(ArrayList<MappingCell> cells) throws RemoteException {
		for (Integer schemaID : mapping.getSchemaIDs()) {
			SchemaInfo info = client.getSchemaInfo(schemaID);
			schemataInfo.add(info);
			initContainersFor(info);
		}
		for (MappingCell cell : cells) {
			container2mappingCells.get(getContainerForElement(cell.getOutput())).add(cell);
		}
	}
	
	/**
	 * Populates the container hash map for a single schema.
	 * @param info A schema from which entity, domain and relationship containers will be read.
	 */
	private void initContainersFor(SchemaInfo info) {
		initEntityContainers(info);
		initDomainContainers(info);
		initRelationshipContainers(info);
	}

	/**
	 * Populates the container hash map with entities whose children are attributes and properties.
	 */
	private void initEntityContainers(SchemaInfo info) {
		ArrayList<SchemaElement> entities = info.getElements(Entity.class);
		for (SchemaElement entity : entities) {
			container2mappingCells.put(entity, new ArrayList<MappingCell>());
			ArrayListHelper<SchemaElement> children = new ArrayListHelper<SchemaElement>();
			container2contents.put(entity, children);
			children.add(entity);
		}
		ArrayList<SchemaElement> attributes = info.getElements(Attribute.class);
		for (SchemaElement attribute : attributes) {
			SchemaElement entity = info.getEntity(attribute.getId());
			container2contents.get(entity).add(attribute);
		}
		ArrayList<SchemaElement> containments = info.getElements(Containment.class);
		for (SchemaElement containment : containments) {
			SchemaElement parent = info.getElement(((Containment)containment).getParentID());
			// Top-level containments will not be found.
			if (container2contents.get(parent) != null) {
				container2contents.get(parent).add(containment);
			}
		}
	}
	
	/**
	 * Populates the container hash map with domains whose children are domain values.
	 */
	private void initDomainContainers(SchemaInfo info) {
		ArrayList<SchemaElement> domains = info.getElements(Domain.class);
		for (SchemaElement domain : domains) {
			container2mappingCells.put(domain, new ArrayList<MappingCell>());
			ArrayListHelper<SchemaElement> children = new ArrayListHelper<SchemaElement>();
			container2contents.put(domain, children);
			children.add(domain);
			for (SchemaElement domainvalue : info.getDomainValuesForDomain(domain.getId())) {
				children.add(domainvalue);
			}
		}
	}

	/**
	 * Populates the container hash map with relationships, which have no children.
	 */
	private void initRelationshipContainers(SchemaInfo info) {
		ArrayList<SchemaElement> relationships = info.getElements(Relationship.class);
		for (SchemaElement relationship : relationships) {
			container2mappingCells.put(relationship, new ArrayList<MappingCell>());
			ArrayListHelper<SchemaElement> children = new ArrayListHelper<SchemaElement>();
			container2contents.put(relationship, children);
		}
	}

	/**
	 * Exports all of the pairs of matched elements.
	 * @throws RemoteException
	 */
	private void exportMatchedElements() throws RemoteException {
		// Iterate over all "containers" and export all of the mappings for which elements in that container appear as a target.
		for (SchemaElement base : container2mappingCells.keySet()) {
			for (MappingCell cell : container2mappingCells.get(base)) {
				// Because the container appears as a target, mark it.
				container2contents.get(base).isSource = false;
				SchemaElement tgt = findElementByID(cell.getOutput());
				// The mapping cell might be associated with multiple source schema elements.
				for (Integer srcID : cell.getInput()) {
					SchemaElement src = findElementByID(srcID);
					SchemaElement srcBase = getContainerForElement(srcID);
					// The source and target elements are being exported, so remove them from the list of unprocessed elements.
					container2contents.get(base).remove(tgt);
					container2contents.get(srcBase).remove(src);
					// Convert to human readable names.
					String srcBaseName = getDisplayNameByID(srcBase.getId());
					String srcName = (srcBase == src) ? "-" : getDisplayNameByID(src.getId());
					String tgtBaseName = getDisplayNameByID(base.getId());
					String tgtName = (base == tgt) ? "-" : getDisplayNameByID(tgt.getId());
					// Export the results.
					out.println(srcBaseName + "," + srcName + ",\"" + src.getDescription() + "\"," +
								tgtBaseName + "," + tgtName + ",\"" + tgt.getDescription() + "\"," +
								cell.getScore() + ",\"" + cell.getNotes() + "\"");
				}
			}
		}
	}
	
	/**
	 * Exports all unmatched elements, provided the container has at least one exported match.
	 * @throws RemoteException
	 */
	private void exportUnmatchedElements() throws RemoteException {
		for (SchemaElement base : container2mappingCells.keySet()) {
			// If remove was invoked, then at least one match was exported.  Do NOT export containers with no matches.
			if (container2contents.get(base).removeWasCalled) {
				// If the container was used as a target it will be exported in the target columns, otherwise it will be exported to the source columns.
				boolean baseIsSource = container2contents.get(base).isSource;
				for (SchemaElement child : container2contents.get(base)) {
					String srcBaseName = (baseIsSource) ? getDisplayNameByID(base.getId()) : "";
					String srcName = (baseIsSource) ? (base == child) ? "-" : getDisplayNameByID(child.getId()) : "";
					String tgtBaseName = (baseIsSource) ? "" : getDisplayNameByID(base.getId());
					String tgtName = (baseIsSource) ? "" : (base == child) ? "-" : getDisplayNameByID(child.getId());
					out.println(srcBaseName + "," + srcName + ",\"" + ((baseIsSource) ? child.getDescription() : "") + "\"," +
							tgtBaseName + "," + tgtName + ",\"" + ((baseIsSource) ? "" : child.getDescription()) + "\",");
				}
			}
		}
	}

	/**
	 * Determines if a given ID is found in the indicated schema.
	 */
	static private boolean contains(Integer id, SchemaInfo info) {
		return (info.getElement(id) != null);
	}
	
	/**
	 * Finds a given schema element by iterating through all available schemata.
	 */
	private SchemaElement findElementByID(Integer id) throws RemoteException {
		for (SchemaInfo info : schemataInfo) {
			if (contains(id, info)) {
				return info.getElement(id);
			}
		}
		return null;
	}

	/**
	 * Finds the display name (which accounts for anonymous elements) by iterating through all available schemata.
	 */
	private String getDisplayNameByID(Integer id) throws RemoteException {
		for (SchemaInfo info : schemataInfo) {
			if (contains(id, info)) {
				return info.getDisplayName(id);
			}
		}
		return null;
	}
	
	/**
	 * Finds the container for a given element: attributes and containments are stored with the parent entity, domain values are
	 * stored with their domains, all other elements are stored in themselves (entities, relationships, and domains).
	 */
	private SchemaElement getContainerForElement(Integer id) throws RemoteException {
		SchemaElement base = findElementByID(id);
		if (base instanceof DomainValue) {
			return findElementByID(((DomainValue)base).getDomainID());
		}
		if (base instanceof Attribute) {
			return findElementByID(((Attribute)base).getEntityID());
		}
		if (base instanceof Containment) {
			return findElementByID (((Containment)base).getParentID());
		}
		return base;
	}

}