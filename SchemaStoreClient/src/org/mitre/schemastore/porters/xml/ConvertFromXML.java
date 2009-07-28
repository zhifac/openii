package org.mitre.schemastore.porters.xml;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.model.graph.HierarchicalGraph;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/** Class for converting SchemaStore objects from XML */
public class ConvertFromXML
{
	/** Retrieves a value from the element */
	static private String getValue(Element element, String tag)
	{
		NodeList tagList = element.getElementsByTagName(tag);
		try {
			if(tagList!=null && tagList.getLength()>0)
			{
				Element el = (Element)tagList.item(0);
				String value = el.getFirstChild().getNodeValue();
				return value.equals("NULL") ? "" : value;
			}
		} catch(java.lang.NullPointerException e) {}
		return null;
	}
	
	/** Retrieves an integer value from the element */
	static private Integer getIntegerValue(Element element, String tag)
		{ try { return Integer.parseInt(getValue(element,tag)); } catch(Exception e) { return null; } }
	
	/** Retrieves a double value from the element */
	static private Double getDoubleValue(Element element, String tag)
		{ try { return Double.parseDouble(getValue(element,tag)); } catch(Exception e) { return null; }}

	/** Retrieves a boolean value from the element */
	static private Boolean getBooleanValue(Element element, String tag)
		{ try { return new Boolean(getValue(element,tag)); } catch(Exception e) { return null; }}

	/** Retrieves a date value from the element */
	static private Date getDateValue(Element element, String tag)
	{
		try { return DateFormat.getDateInstance(DateFormat.MEDIUM).parse(getValue(element,tag)); }
		catch(Exception e) { return null; }
	}
	
	/** Retrieve the schema from the specified XML */
	static public Schema getSchema(Element element)
	{
		Schema schema = new Schema();
		schema.setId(getIntegerValue(element,"SchemaId"));
		schema.setName(getValue(element,"SchemaName"));
		schema.setAuthor(getValue(element,"SchemaAuthor"));
		schema.setSource(getValue(element,"SchemaSource"));
		schema.setType(getValue(element,"SchemaType"));
		schema.setDescription(getValue(element,"SchemaDescription"));
		schema.setLocked(getBooleanValue(element,"SchemaLocked"));
		return schema;
	}
	
	/** Retrieve the parent schemas from the specified XML */
	static public ArrayList<Integer> getParentSchemaIDs(Element element)
	{
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		int x = 0;
		String textValue;
		while((textValue = getValue(element,"SchemaParentId"+new Integer(x++).toString())) != null)
			schemaIDs.add(Integer.parseInt(textValue));
		return schemaIDs;
	}
	
	/** Retrieves the schema element from the specified XML */
	static public SchemaElement getSchemaElement(Element element)
	{
		// Identify the schema element to be constructed
		SchemaElement schemaElement = null;
		String type = element.getTagName();
		if(type.equals(Alias.class.getName())) schemaElement = new Alias();
		if(type.equals(Attribute.class.getName())) schemaElement = new Attribute();
		if(type.equals(Containment.class.getName())) schemaElement = new Containment();
		if(type.equals(Domain.class.getName())) schemaElement = new Domain();
		if(type.equals(DomainValue.class.getName())) schemaElement = new DomainValue();
		if(type.equals(Entity.class.getName())) schemaElement = new Entity();
		if(type.equals(Relationship.class.getName())) schemaElement = new Relationship();
		if(type.equals(Subtype.class.getName())) schemaElement = new Subtype();
		if(schemaElement==null) return null;
		
		// Populate the general schema element information
		schemaElement.setId(getIntegerValue(element,"ElementId"));
		schemaElement.setName(getValue(element,"ElementName"));
		schemaElement.setDescription(getValue(element,"ElementDescription"));
		schemaElement.setBase(getIntegerValue(element,"ElementBase"));		
		
		// Populate alias schema elements
		if(schemaElement instanceof Alias)
			((Alias)schemaElement).setElementID(getIntegerValue(element,"AliasIdElement"));

		// Populate attribute schema elements
		if(schemaElement instanceof Attribute)
		{
			Attribute attribute = (Attribute)schemaElement;
			attribute.setEntityID(getIntegerValue(element,"AttributeEntityId"));
			attribute.setDomainID(getIntegerValue(element,"AttributeDomainId"));
			attribute.setMin(getIntegerValue(element,"AttributeMin"));
			attribute.setMax(getIntegerValue(element,"AttributeMax"));
			attribute.setKey(getBooleanValue(element,"AttributeKey"));
		}
		
		// Populate containment schema elements
		if(schemaElement instanceof Containment)
		{
			Containment containment = (Containment)schemaElement;
			containment.setParentID(getIntegerValue(element,"ContainmentParentId"));
			containment.setChildID(getIntegerValue(element,"ContainmentChildId"));
			containment.setMin(getIntegerValue(element,"ContainmentMin"));
			containment.setMax(getIntegerValue(element,"ContainmentMax"));			
		}
		
		// Populate domain value schema elements
		if(schemaElement instanceof DomainValue)
			((DomainValue)schemaElement).setDomainID(getIntegerValue(element,"DomainValueDomainId"));

		// Populate relationship schema elements
		if(schemaElement instanceof Relationship)
		{
			Relationship relationship = (Relationship)schemaElement;
			relationship.setLeftID(getIntegerValue(element,"RelationshipLeftId"));
			relationship.setLeftMin(getIntegerValue(element,"RelationshipLeftMin"));
			relationship.setLeftMax(getIntegerValue(element,"RelationshipLeftMax"));
			relationship.setRightID(getIntegerValue(element,"RelationshipRightId"));
			relationship.setRightMin(getIntegerValue(element,"RelationshipRightMin"));
			relationship.setRightMax(getIntegerValue(element,"RelationshipRightMax"));
		}
		
		// Populate subtype schema elements
		if(schemaElement instanceof Subtype)
		{
			Subtype subtype = (Subtype)schemaElement;
			subtype.setParentID(getIntegerValue(element,"SubTypeParentId"));
			subtype.setChildID(getIntegerValue(element,"SubTypeChildId"));
		}

		return schemaElement;
	}

	/** Retrieve the mapping from the specified XML */
	static public Mapping getMapping(Element element)
	{
		// Populate the mapping 
		Mapping mapping = new Mapping();
		mapping.setId(getIntegerValue(element,"MappingId"));
		mapping.setName(getValue(element,"MappingName"));
		mapping.setAuthor(getValue(element,"MappingAuthor"));
		mapping.setDescription(getValue(element,"MappingDescription"));

		// Populate the mapping schemas
		ArrayList<MappingSchema> schemas = new ArrayList<MappingSchema>();
		NodeList schemaList = element.getElementsByTagName("MappingSchema");
		if(schemaList!=null)
			for(int i=0; i<schemaList.getLength(); i++)
			{
				// Get the schema element
				Element schemaElement = (Element)schemaList.item(i);

				// Extract the schema information from the element
				MappingSchema schema = new MappingSchema();
				schema.setId(getIntegerValue(schemaElement,"SchemaId"));
				schema.setName(getValue(schemaElement,"SchemaName"));
				schema.setModel(getValue(schemaElement,"SchemaModel"));
				schema.setSide(getIntegerValue(schemaElement,"SchemaSide"));
				schemas.add(schema);
			}
		mapping.setSchemas(schemas.toArray(new MappingSchema[0]));
		return mapping;
	}

	/** Retrieve the elementID for the given element path */
	static private Integer getElementId(String pathString, ArrayList<HierarchicalGraph> graphs)
	{
		// Retrieve the schema and path
		String schema = pathString.substring(1).replaceAll("/.*","");
		ArrayList<String> path = new ArrayList<String>(Arrays.asList(pathString.replaceFirst("/[^/]*/","").split("/")));

		// Retrieve the element ID
		for(HierarchicalGraph graph : graphs)
			if(graph.getSchema().getName().equals(schema))
			{
				ArrayList<Integer> elementIDs = graph.getPathIDs(path);
				if(elementIDs.size()>0) return elementIDs.get(0);
			}
		return null;
	}
	
	/** Retrieve the mapping cell from the specified XML */
	static public MappingCell getMappingCell(Element element, ArrayList<HierarchicalGraph> graphs)
	{
		// Retrieve the mapping cell input and output IDs
		Integer inputCount = getIntegerValue(element,"MappingCellInputCount");
		Integer inputIDs[] = new Integer[inputCount];
		for(int i=0; i<inputCount; i++)
		{
			Integer inputID = getElementId(getValue(element,"MappingCellInput"+i+"Path"),graphs);
			if(inputID==null) return null;
			inputIDs[i] = inputID;
		}
		Integer outputID = getElementId(getValue(element,"MappingCellOutputPath"),graphs);
		if(outputID==null) return null;
		
		// Retrieve the mapping cell elements
		Integer id = getIntegerValue(element,"MappingCellId");
		String author = getValue(element,"MappingCellAuthor");
		Date date = getDateValue(element,"MappingCellDate");
		Double score = getDoubleValue(element,"MappingCellScore");
		String function = getValue(element,"MappingCellFunction");
		String notes = getValue(element,"MappingCellNotes");
		Boolean validated = getBooleanValue(element,"MappingCellValidated");
		
		// Return the generated mapping cell
		if(validated) return MappingCell.createValidatedMappingCell(id, null, inputIDs, outputID, author, date, function, notes);
		else return MappingCell.createProposedMappingCell(id, null, inputIDs[0], outputID, score, author, date, notes);
	}
}