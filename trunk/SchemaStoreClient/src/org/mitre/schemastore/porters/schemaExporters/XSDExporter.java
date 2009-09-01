// Copyright (C) The MITRE Corporation 2008
// ALL RIGHTS RESERVED

package org.mitre.schemastore.porters.schemaExporters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;


/**
 * Class for converting SchemaStore format to XSD files
 * NOTE: m-to-n relationships are converted to 1-to-n relationships
 *  
 * @author DBURDICK
 */
public class XSDExporter extends SchemaExporter
{	
	// store Entities by ID
	HashMap<Integer, Entity> entitySet = new HashMap<Integer, Entity>();
	
	// store attributes by entityID
	HashMap<Integer, ArrayList<Attribute>> attrSet = new HashMap<Integer, ArrayList<Attribute>>();
	
	// stores domains by domain ID
	HashMap<Integer, Domain> domainSet = new HashMap<Integer, Domain>();
	
	// stores domainValues by domainID
	HashMap<Integer, ArrayList<DomainValue>> domainValueSet = new HashMap<Integer, ArrayList<DomainValue>>();
	
	// stores containments by parentID
	HashMap<Integer, ArrayList<Containment>> containmentSet = new HashMap<Integer, ArrayList<Containment>>();
	
	// stores top-level containments 
	ArrayList<Containment> topLevelContainments = new ArrayList<Containment>();
	
	// stores 1-to-m relationships by ID for entity with 1 
	// assumes left-right m-to-n relationships converted to 1-to-n relationships
	HashMap<Integer, ArrayList<Relationship>> relationshipSet = new HashMap<Integer, ArrayList<Relationship>>();
	
	// stores Subsets by ID
	HashMap<Integer, Subtype> subtypeSet = new HashMap<Integer, Subtype>();
	
	// indenting for "pretty printing"
	public static final String INDENT2  = "   ";
	public static final String INDENT4  = "      ";
	public static final String INDENT6  = "         ";
	public static final String INDENT8  = "            ";
	public static final String INDENT10 = "               ";
	
	/**
	 * XSDExporter(): Constructor. Sets the common domains.
	 *
	 */
	public XSDExporter(){	
		domainSet.put(-1, new Domain(-1,"integer","The Integer Domain",0));
		domainSet.put(-2, new Domain(-2,"double","The Double Domain",0));
		domainSet.put(-3, new Domain(-3,"string","The String Domain",0));
		domainSet.put(-4, new Domain(-4,"Timestamp","The Timestamp Domain",0));
		domainSet.put(-5, new Domain(-5,"boolean","The Boolean Domain",0));
		domainSet.put(-6, new Domain(-6,"Any","The Any Domain",0));
	}

	/** Returns the exporter name */
	public String getName()
		{ return "XSD Exporter"; }
	
	/** Returns the exporter description */
	public String getDescription()
		{ return "This method exports the schema in XSD format"; }
	
	/** Returns the exporter file type */
	public String getFileType()
		{ return ".xsd"; }

	
	/**
	 * export(): Exports the given schema from schemaStore to XSD 
	 * @param elementList elements for the schema to be exported
	 * @param schemaID ID for the schema being exported
	 * @return ArrayList of strings representing the XSD
	 */
	public void exportSchema(Schema schema, ArrayList<SchemaElement> elementList, File file) throws IOException 
	{		
		// Scan schemaElements to initialize hashtables
		for (SchemaElement elem : elementList){
			
			if (elem instanceof Entity)
				entitySet.put(elem.getId(), (Entity)elem);
				
			else if (elem instanceof Domain){
				//TODO: translate the Domain name to upper-case 
				elem.setName(toUpper(elem.getName()));
				domainSet.put(elem.getId(), (Domain)elem);
				
			} else if (elem instanceof Attribute){
				ArrayList<Attribute> attrs = attrSet.get(((Attribute)elem).getEntityID());
				if (attrs == null)
					attrs = new ArrayList<Attribute>();
				attrs.add((Attribute)elem);
				attrSet.put(((Attribute)elem).getEntityID(), attrs);
				
			} else if (elem instanceof DomainValue){
				ArrayList<DomainValue> domVals = domainValueSet.get(((DomainValue)elem).getDomainID());
				if (domVals == null)
					domVals = new ArrayList<DomainValue>();
				domVals.add((DomainValue)elem);
				domainValueSet.put(((DomainValue)elem).getDomainID(), domVals);
				
			} else if (elem instanceof Containment){
				if (((Containment)elem).getParentID() == null)
					topLevelContainments.add((Containment)elem);
				else {
					ArrayList<Containment> containVals = containmentSet.get(((Containment)elem).getParentID()); 
					if (containVals == null)
						containVals = new ArrayList<Containment>();
					containVals.add((Containment)elem);
					containmentSet.put(((Containment)elem).getParentID(), containVals);
				}
			} else if (elem instanceof Relationship){
				
				// store 1-to-m, m-to-1 relationships by left or right ID respectively
				// store m-to-n relationships by leftID (as 1-to-n)
				Relationship rel = (Relationship)elem;
				Integer leftCard = rel.getLeftMax();
				Integer rightCard = rel.getRightMax(); 
				Integer keyID = null; 
				if (leftCard == 1)
					keyID = rel.getLeftID();
				else if (rightCard == 1)
					keyID = rel.getRightID();
				else {
					rel.setLeftMax(1);
					keyID = rel.getLeftID();
				}
				
				ArrayList<Relationship> relationVals = relationshipSet.get(keyID); 
				if (relationVals == null)
					relationVals = new ArrayList<Relationship>();
				relationVals.add((Relationship)elem);
				relationshipSet.put(keyID, relationVals);
				
			} else if (elem instanceof Subtype){
				subtypeSet.put(((Subtype)elem).getChildID(), (Subtype)elem);
			}
		} // end for (SchemaElement elem : elementList){

		// add header for XML file
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		out.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		out.append("<!-- Created with OpenII XSDExporter -->\n");
		out.append("<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n");
		
		// Generate XML for each remaining NON-ANNONYMOUS entity
		Collection<Entity> entities = entitySet.values();
		for (Entity entity : entities)
			if (entity.getName().equals("") == false)
				out.append(outputEntity(entity, new String("")) + "\n");
		
		// Generate XML for each top-level entity (represented as containment with parentID == null)
		out.append(outputContainments(topLevelContainments,true));
		
		
	   // Generate XML for simple types
	   Collection<Domain> domains = domainSet.values();
	   HashSet<String> outputNames = new HashSet<String>();
	   
	   // Generate XML for each domain.  
	   // Need to insure that common types are NOT added twice
	   for (Domain d : domains)
	   {
		  // Make sure that each output domain is unique
		 // if(!outputNames.contains(d.getName()))
		  //{
			  outputNames.add(d.getName());
		  
			  out.append("<xs:simpleType name=\"" + d.getName() + "\">\n");
			  if (d.getDescription().length() > 0)
				  out.append(INDENT2 + "<xs:annotation> <xs:documentation> " + d.getDescription() + " </xs:documentation> </xs:annotation>\n");

			  // TODO: Set the restriction base here -- need to add restrictionBase to domain
			  String restrictionBaseType = new String("xs:string");
			  out.append(INDENT2 + "<xs:restriction base=\"" + restrictionBaseType + "\">\n");
			  ArrayList<DomainValue> domVals = domainValueSet.get(d.getId());
			  if(domVals != null)
				  for (DomainValue dv : domVals)
					  out.append(INDENT4 + "<xs:enumeration value=\""+ dv.getName() +"\"/>\n");  
			  out.append(INDENT2 + "</xs:restriction>\n");
			  out.append("</xs:simpleType>\n");
		 // }
	   }
	   
	   // add closing schema tag
	   out.append("</xs:schema>\n");
	   out.close();
	} // end method
	
	/**
	 * outputComplexTypeEntity(): Generates the XML for a entity
	 * @param entity Entity to generate XML for
	 * @return ArrayList of strings containing XML for entity
	 */
	private StringBuffer outputEntity (Entity entity, String indentBase)
	{	
		StringBuffer output = new StringBuffer();
		
		// put all elements together
		ArrayList<Relationship> relationships = relationshipSet.get(entity.getId());
		ArrayList<Containment> containments = containmentSet.get(entity.getId());
		ArrayList<Attribute> attributes = attrSet.get(entity.getId());
		
		// handle unnamed types
		if (entity.getName().equals("") == false)
			output.append(indentBase + "<xs:complexType name=\"" + entity.getName() + "\">\n");
		else
			output.append(indentBase + "<xs:complexType>\n");
		
		// Check if entity is a subtype and add appropriate tags
		if (subtypeSet.get(entity.getId()) != null)
		{
			// get ID of parent type 
			int parentTypeID = ((Subtype)subtypeSet.get(entity.getId())).getParentID();
			String parentTypeName = ((Entity)entitySet.get(parentTypeID)).getName();
			output.append(indentBase + INDENT2 + "<xs:complexContent>\n");
			output.append(indentBase + INDENT2 + "<xs:extension base=\"" + parentTypeName + "\">\n");
		} 
		
		// add sequence of elements for complex type (by processing 
		// containments and relationships referencing this entity)
		if ((containments != null) || (relationships != null))
		{
			output.append(indentBase + INDENT4 + "<xs:sequence>\n");
			
			if (relationships != null){
				for (Relationship relationship : relationships){
				
					Entity childEntity;
					String minOccursString, maxOccursString;
					// find which side of relationship is (left or right)
					if (relationship.getLeftID().equals(entity.getId())){
						childEntity = entitySet.get(relationship.getRightID());		
						if ((relationship.getRightMin() == null) || (relationship.getRightMin() < 0)) minOccursString = "0";
						else minOccursString = relationship.getRightMin().toString();
						if (relationship.getRightMax() == null || relationship.getRightMax() < 0 || relationship.getRightMax() > 1) 
							maxOccursString = "unbounded";
						else maxOccursString = relationship.getRightMax().toString();
					
					} else {
						childEntity = entitySet.get(relationship.getLeftID());		
						if (relationship.getLeftMin() == null || relationship.getLeftMin() < 0) minOccursString = "0";
						else minOccursString = relationship.getLeftMin().toString();
						if (relationship.getLeftMax() == null || relationship.getLeftMax() < 0 || relationship.getLeftMax() > 1) 
							maxOccursString = "unbounded";
						else maxOccursString = relationship.getRightMax().toString();
					}
					
					output.append(indentBase + INDENT6 + "<xs:element name=\"" +  
							relationship.getName() + "\" " + " type= \"" + childEntity.getName()+ 
							"\"" + " minOccurs=\"" + minOccursString + "\"" + " maxOccurs=\"" 
							+ maxOccursString +  "\">\n");	
					if (childEntity.getDescription().length() > 0){
						output.append(indentBase + INDENT8 + "<xs:annotation><xs:documentation>" + 
								childEntity.getDescription() + "</xs:documentation></xs:annotation>\n");
					}
					output.append(indentBase + INDENT6 + "</xs:element>\n");
				} // end for (Relationship relationship : relationships){ 
			} // end if (relationship != null) {
		
			if (containments != null)
				output.append(outputContainments(containments,false));
			
		    output.append(indentBase + INDENT4 + "</xs:sequence>\n");
		}
	
		// put all attributes together at end
		if (attributes != null)
			for (Attribute a : attributes)
			{
				Domain dom = domainSet.get(a.getDomainID());
				String domName = new String("");
				if (dom != null)
					domName = dom.getName(); 
				output.append(indentBase + INDENT4 + "<xs:attribute name=\"" + a.getName() + "\" type=\"" + domName + "\"/>\n");	
			}
	
		// Close off tags if entity is a subtype
		if (subtypeSet.get(entity.getId()) != null)
		{
			output.append(indentBase + INDENT2 + "</xs:extension>\n");
			output.append(indentBase + INDENT2 + "</xs:complexContent>\n");
		} 
		output.append(indentBase + "</xs:complexType>\n");
		
		return output;
	} // end method outputComplexTypeEntity
	
	/** Outputs the list of containments */
	private StringBuffer outputContainments(ArrayList<Containment> containments, boolean topLevel)
	{
		// Cycle through the list of containments
		StringBuffer output = new StringBuffer();
		for (Containment containment : containments)
		{	
			// Get the min occur and max occur strings
			String minOccurString = containment.getMin()<0 ? "0" : containment.getMin().toString();
			String maxOccurString = containment.getMax()<0 ? "unbounded" : containment.getMax().toString();
			
			// check to see containment is actually an element
			if(!containment.getName().equals(""))
			{	
				// check to see if element is an entity or domain 
				SchemaElement schemaElement = entitySet.get(containment.getChildID());
				if (schemaElement == null) 
					schemaElement = domainSet.get(containment.getChildID());

				// Output the containment element
				output.append(INDENT6 + "<xs:element name=\"" 
						+ containment.getName() + "\" ");
				if (topLevel == false){
					output.append("minOccurs=\"" + minOccurString + "\"" 
							+ " maxOccurs=\"" + maxOccurString + "\" ");    
				}
				if (schemaElement.getName().length() > 0)
					output.append("type=\"" + schemaElement.getName() + "\"");
				output.append(">\n");	
				
				if (schemaElement.getDescription().length() > 0)
					output.append(INDENT8 + "<xs:annotation><xs:documentation>" + schemaElement.getDescription() + "</xs:documentation></xs:annotation>\n");		
				if (schemaElement instanceof Entity && schemaElement.getName().equals(""))
					output.append(outputEntity((Entity)schemaElement,INDENT10) + "\n");
				output.append(INDENT6 + "</xs:element>\n");
			}
		}				
		return output;
	}
	
	private String toUpper(String s){
		String s1 = new String(s.substring(0,1));
		String s2 = new String(s.substring(1,s.length()));
		s1 = s1.toUpperCase();
		return new String(s1 + s2);
	}
	
} // end class