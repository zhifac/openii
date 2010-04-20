package org.mitre.schemastore.porters.xml;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** Class for converting SchemaStore objects to XML */
public class ConvertToXML
{
	/** Stores a reference to the XML document */
	static private Document document;
	
	/** Adds a new child element to the specified element */
	static private Element addChildElement(Element element, String name, Object object)
	{
		Element childElement = document.createElement(name);
		String text = object==null ? "" : object.toString().replaceAll("[^\\p{ASCII}]","#");
		childElement.appendChild(document.createTextNode(text));
		element.appendChild(childElement);
		return childElement;
	}
	
	/** Generates the XML for the specified schema */
	static public Element generate(Schema schema, ArrayList<Integer> parentIDs, Document documentIn)
	{
		document = documentIn;
		
		// Generate the schema XML element
		Element element = document.createElement("Schema");			
		addChildElement(element,"SchemaId",schema.getId());
		addChildElement(element,"SchemaName", schema.getName());
		addChildElement(element,"SchemaAuthor", schema.getAuthor());
		addChildElement(element,"SchemaSource", schema.getSource());
		addChildElement(element,"SchemaType", schema.getType());
		addChildElement(element,"SchemaDescription", schema.getDescription());
		addChildElement(element,"SchemaLocked", new Boolean(schema.getLocked()));
		
		// Add parent schemas to the schema XML element
		if(parentIDs != null)
		{
			int x = 0;
			for(Integer parentID : parentIDs)
			{
				String name = "SchemaParentId"+new Integer(x++).toString();
				addChildElement(element,name,parentID);				
			}
		}
		
		return element;
	}
	
	/** Generate XML for the specified schema element */
	static public Element generate(SchemaElement schemaElement, Document documentIn)
	{
		document = documentIn;

		// Generate XML for the generic schema element information
		Element element = document.createElement(schemaElement.getClass().getName());
		addChildElement(element,"ElementId",schemaElement.getId());
		addChildElement(element,"ElementName",schemaElement.getName());
		addChildElement(element,"ElementDescription",schemaElement.getDescription());
		addChildElement(element,"ElementBase",schemaElement.getBase());
		
		// Generate XML for alias schema elements
		if(schemaElement instanceof Alias)
			addChildElement(element,"AliasIdElement",((Alias)schemaElement).getElementID());

		// Generate XML for attribute schema elements
		if(schemaElement instanceof Attribute)
		{
			Attribute attribute = (Attribute)schemaElement;
			addChildElement(element,"AttributeEntityId", attribute.getEntityID());
			addChildElement(element,"AttributeDomainId", attribute.getDomainID());
			addChildElement(element,"AttributeMin", attribute.getMin());
			addChildElement(element,"AttributeMax", attribute.getMax());
			addChildElement(element,"AttributeKey", new Boolean(attribute.isKey()));
		}
		
		// Generate XML for containment schema elements
		if(schemaElement instanceof Containment)
		{
			Containment containment = (Containment)schemaElement;
			addChildElement(element,"ContainmentParentId",containment.getParentID()==null?"NULL":containment.getParentID());
			addChildElement(element,"ContainmentChildId",containment.getChildID());
			addChildElement(element,"ContainmentMin",containment.getMin());
			addChildElement(element,"ContainmentMax",containment.getMax());
		}
		
		// Generate XML for domain value schema elements
		if(schemaElement instanceof DomainValue)
			addChildElement(element,"DomainValueDomainId",((DomainValue)schemaElement).getDomainID());

		// Generate XML for relationship schema elements
		if(schemaElement instanceof Relationship)
		{
			Relationship relationship = (Relationship)schemaElement;
			addChildElement(element,"RelationshipLeftId",relationship.getLeftID());
			addChildElement(element,"RelationshipLeftMin",relationship.getLeftMin());
			addChildElement(element,"RelationshipLeftMax",relationship.getLeftMax());
			addChildElement(element,"RelationshipRightId",relationship.getRightID());
			addChildElement(element,"RelationshipRightMin",relationship.getRightMin());
			addChildElement(element,"RelationshipRightMax",relationship.getRightMax());
		}
		
		// Generate XML for subtype schema elements
		if(schemaElement instanceof Subtype)
		{
			Subtype subtype = (Subtype)schemaElement;
			addChildElement(element,"SubTypeParentId",subtype.getParentID());
			addChildElement(element,"SubTypeChildId",subtype.getChildID());
		}
		
		return element;
	}	
	
	/** Generates the XML for the specified project */
	static public Element generate(Project project, HashMap<Integer,SchemaInfo> schemaInfoList, Document documentIn)
	{
		document = documentIn;
		
		// Generate the project XML elements
		Element element = document.createElement("Project");			
		addChildElement(element,"ProjectId",project.getId());
		addChildElement(element,"ProjectName", project.getName());
		addChildElement(element,"ProjectAuthor", project.getAuthor());
		addChildElement(element,"ProjectDescription", project.getDescription());
		
		// Generate the project schema XML elements
		for(ProjectSchema projectSchema : project.getSchemas())
		{
			// Retrieve the associated schema
			SchemaInfo schemaInfo = schemaInfoList.get(projectSchema.getId());
			
			// Stores the mapping schema
			Element schemaElement = addChildElement(element,"ProjectSchema",null);
			addChildElement(schemaElement,"SchemaId",projectSchema.getId());
			addChildElement(schemaElement,"SchemaName",schemaInfo.getSchema().getName());
			addChildElement(schemaElement,"SchemaModel",projectSchema.getModel());
		}
		
		return element;
	}

	/** Retrieves the element path for the given element ID */
	static private String getElementPath(Integer elementID, HierarchicalSchemaInfo schemaInfo)
	{
		ArrayList<ArrayList<SchemaElement>> paths = schemaInfo.getPaths(elementID);
		if(paths==null || paths.size()==0) return null;
		StringBuffer path = new StringBuffer("/" + schemaInfo.getSchema().getName().replaceAll("/","&#47;"));
		for(SchemaElement element : paths.get(0))
			path.append("/" + schemaInfo.getDisplayName(element.getId()).replaceAll("/","&#47;"));
		return path.toString();
	}	
	
	/** Generates the XML for the specified mapping */
	static public Element generate(Mapping mapping, ArrayList<MappingCell> mappingCells, HierarchicalSchemaInfo sourceInfo, HierarchicalSchemaInfo targetInfo, Document documentIn)
	{
		document = documentIn;

		// Generate the mapping XML elements
		Element element = document.createElement("Mapping");
		addChildElement(element,"MappingId",mapping.getId());
		addChildElement(element,"MappingProjectId",mapping.getProjectId());
		addChildElement(element,"MappingSourceId",mapping.getSourceId());
		addChildElement(element,"MappingSourceName",sourceInfo.getSchema().getName());
		addChildElement(element,"MappingSourceModel",sourceInfo.getModel());
		addChildElement(element,"MappingTargetId",mapping.getTargetId());
		addChildElement(element,"MappingTargetName",targetInfo.getSchema().getName());
		addChildElement(element,"MappingTargetModel",targetInfo.getModel());
		
		// Generate the mapping cell XML elements
		for(MappingCell mappingCell : mappingCells)
		{
			// Retrieve the input and output paths
			ArrayList<String> inputPaths = new ArrayList<String>();
			for(Integer input : mappingCell.getInput())
			{
				String inputPath = getElementPath(input,sourceInfo);
				if(inputPath==null) continue;
				inputPaths.add(inputPath);
			}
			String outputPath = getElementPath(mappingCell.getOutput(),targetInfo);
			if(outputPath==null) continue;
			
			// Stores the mapping cell information
			Element mappingCellElement = addChildElement(element,"MappingCell",null);
			addChildElement(mappingCellElement,"MappingCellId",mappingCell.getId());
			addChildElement(mappingCellElement,"MappingCellAuthor",mappingCell.getAuthor());
			addChildElement(mappingCellElement,"MappingCellDate",DateFormat.getDateInstance(DateFormat.MEDIUM).format(mappingCell.getModificationDate()));
			addChildElement(mappingCellElement,"MappingCellInputCount",mappingCell.getInput().length);
			for(int i=0; i<mappingCell.getInput().length; i++)
			{
				addChildElement(mappingCellElement,"MappingCellInput"+i+"Id",mappingCell.getInput()[i]);			
				addChildElement(mappingCellElement,"MappingCellInput"+i+"Path",inputPaths.get(i));
			}
			addChildElement(mappingCellElement,"MappingCellOutputId",mappingCell.getOutput());
			addChildElement(mappingCellElement,"MappingCellOutputPath",outputPath);
			addChildElement(mappingCellElement,"MappingCellScore",mappingCell.getScore());
			addChildElement(mappingCellElement,"MappingCellFunction",mappingCell.getFunctionID());
			addChildElement(mappingCellElement,"MappingCellNotes",mappingCell.getNotes());
		}
		
		return element;
	}
}