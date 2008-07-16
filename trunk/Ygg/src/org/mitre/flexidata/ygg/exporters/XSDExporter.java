// Copyright (C) The MITRE Corporation 2008
// ALL RIGHTS RESERVED

package org.mitre.flexidata.ygg.exporters;

import java.util.*;

import org.mitre.schemastore.model.*;

/**
 * Class for converting SchemaStore format to XSD files
 * NOTE: m-to-n relationships are converted to 1-to-n relationships
 *  
 * @author DBURDICK
 */
public class XSDExporter extends Exporter
{	
	// store the generated XML
	ArrayList<String> retVal = new ArrayList<String>();
	
	// store Entities by ID
	HashMap<Integer, Entity> entitySet = new HashMap<Integer, Entity>();
	
	// store attributes by entityID
	HashMap<Integer, ArrayList<Attribute>> attrSet   = new HashMap<Integer, ArrayList<Attribute>>();
	
	// stores domains by domain ID
	HashMap<Integer, Domain> domainSet = new HashMap<Integer, Domain>();
	
	// stores domainValues by domainID
	HashMap<Integer, ArrayList<DomainValue>> domainValueSet = new HashMap<Integer, ArrayList<DomainValue>>();
	
	// stores containments by parentID
	HashMap<Integer, ArrayList<Containment>> containmentSet = new HashMap<Integer, ArrayList<Containment>>();
	
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
		domainSet.put(-1, new Domain(-1,"xs:integer","The Integer Domain",0));
		domainSet.put(-2, new Domain(-2,"xs:double","The Double Domain",0));
		domainSet.put(-3, new Domain(-3,"xs:string","The String Domain",0));
		domainSet.put(-4, new Domain(-4,"Timestamp","The Timestamp Domain",0));
		domainSet.put(-5, new Domain(-5,"xs:boolean","The Boolean Domain",0));
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
	public  ArrayList<String> export (ArrayList<SchemaElement> elementList, Integer schemaID) 
	{

		// Scan schemaElements to initialize the above Hashtables
		for (SchemaElement elem : elementList){
			
			//TODO: Replaces schemaStore generated names with empty string
			if (elem instanceof Entity && elem.getName().contains("/")){
				elem.setName("");
			}
			
			if (elem instanceof Entity){
				entitySet.put(elem.getId(), (Entity)elem);
				
			} else if (elem instanceof Domain){
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
				ArrayList<Containment> containVals = containmentSet.get(((Containment)elem).getParentID()); 
				if (containVals == null)
					containVals = new ArrayList<Containment>();
				containVals.add((Containment)elem);
				containmentSet.put(((Containment)elem).getParentID(), containVals);
				
			} else if (elem instanceof Relationship){
				
				// store 1-to-m, m-to-1 relationships by left ot right ID respectively
				// store m-to-n relationships by leftID (as 1-to-n)
				Relationship rel = (Relationship)elem;
				Integer leftCard = rel.getLeftMax();
				Integer rightCard = rel.getRightMax(); 
				Integer keyID = null; 
				if (leftCard == 1){
					keyID = rel.getLeftID();
				} else if (rightCard == 1){
					keyID = rel.getRightID();
				} else {
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
		retVal.add(new String("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
		retVal.add(new String("<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">"));
			
		// Generate XML for each top-level entity (represented as containment with parentID == schemaID)
		ArrayList<Containment> topLevelElements = containmentSet.get(schemaID);
		if (topLevelElements == null) topLevelElements = new ArrayList<Containment>();
		for (Containment containment : topLevelElements){
			
			// find min and max occurs strings
			String minOccursString, maxOccursString;
			if (containment.getMin() < 0) minOccursString = "0";
			else minOccursString = containment.getMin().toString();
			if (containment.getMax() < 0) maxOccursString = "unbounded";
			else maxOccursString = containment.getMax().toString();
			
			// check to see containment is actually an element
			if (containment.getName().equals("") == false){
				
				// check to see if element is an entity or domain 
				SchemaElement schemaElement = entitySet.get(containment.getChildID());
				if (schemaElement == null) 
					schemaElement = domainSet.get(containment.getChildID());
					
				// CASE 1: Element has annonymous complex type
				if (schemaElement instanceof Entity && schemaElement.getName().equals("")){
					
					retVal.add(new String(INDENT6 + "<xs:element name=\"" +  containment.getName() + "\" " 
							+ " minOccurs=\"" + minOccursString + "\"" + " maxOccurs=\"" + maxOccursString +  "\">"));
					if (schemaElement.getDescription().length() > 0){
						retVal.add(new String(INDENT8 + "<xs:annotation><xs:documentation>" + 
								schemaElement.getDescription() + "</xs:documentation></xs:annotation>"));
					}
					retVal.addAll(outputEntity((Entity)schemaElement,INDENT10));
				
				// CASE 2: Element has child that is named Entity or named Domain 
				} else{
					
					retVal.add(new String(INDENT6 + "<xs:element name=\"" +  containment.getName() + "\" " 
							+ " minOccurs=\"" + minOccursString + "\"" + " maxOccurs=\"" + maxOccursString +  "\">"));	

					if (schemaElement.getDescription().length() > 0){
						retVal.add(new String(INDENT8 + "<xs:annotation><xs:documentation>" + 
								schemaElement.getDescription() + "</xs:documentation></xs:annotation>"));
					}
				}
				retVal.add(new String(INDENT6 + "</xs:element>"));
			} // end if (containment.getName().equals("") == false){
		} // for (Containment containment : topLevelElements){
		
		// Generate XML for each remaining NON-ANNONYMOUS entity
		Collection<Entity> entities = entitySet.values();
		for (Entity entity : entities){
			if (entity.getName().equals("") == false){
				retVal.addAll(outputEntity(entity, new String("")));
			}			 
		} 

	   // Generate XML for simple types
	   Collection<Domain> domains = domainSet.values();
	   HashSet<String> outputNames = new HashSet<String>();
	   
	   // Generate XML for each domain.  
	   // Need to insure that common types are NOT added twice
	   for (Domain d : domains){
		  // make sure that each output domain is unique
		  if (outputNames.contains(d.getName()) == false){
			  outputNames.add(d.getName());  
		  
			  retVal.add(new String("<xs:simpleType name=\"" + d.getName() + "\">"));
			  if (d.getDescription().length() > 0){
				  
				  retVal.add(new String(INDENT2 + "<xs:annotation> <xs:documentation> "
						  + d.getDescription() + " </xs:documentation> </xs:annotation>"));
			  }
			  // TODO: Set the restiction base here -- need to add restrictionbase to domain
			  String restrictionBaseType = new String("xs:string");
			  retVal.add(new String(INDENT2 + "<xs:restriction base=\"" + restrictionBaseType + "\">"));
			  
			  ArrayList<DomainValue> domVals = domainValueSet.get(d.getId());
			  if (domVals != null){
				  for (DomainValue dv : domVals){
					  retVal.add(INDENT4 + "<xs:enumeration value=\""+ dv.getName() +"\"/>");  
				  }
			  }
			  retVal.add(new String(INDENT2 + "</xs:restriction>"));
			  retVal.add(new String("</xs:simpleType>"));
		  }
		   
	   } // for (Domain d: domains){
	   
	   // add closing schema tag
	   retVal.add("</xs:schema>");
	   return retVal;
	} // end method
	
	/**
	 * outputComplexTypeEntity(): Generates the XML for a entity
	 * @param entity Entity to generate XML for
	 * @return ArrayList of strings containing XML for entity
	 */
	private ArrayList<String> outputEntity (Entity entity, String indentBase){
		
		ArrayList<String> retVal = new ArrayList<String>();
		
		// put all elements together
		ArrayList<Relationship> relationships = relationshipSet.get(entity.getId());
		ArrayList<Containment> containments = containmentSet.get(entity.getId());
		ArrayList<Attribute> attributes = attrSet.get(entity.getId());
		
		// handle unnammed types
		if (entity.getName().equals("") == false){
			retVal.add(new String(indentBase + "<xs:complexType name=\"" + entity.getName() + "\">"));
		} else {
			retVal.add(new String(indentBase + "<xs:complexType>"));
		}
		
		// Check if entity is a subtype and add appropriate tags
		if (subtypeSet.get(entity.getId()) != null){
			// get ID of parent type 
			int parentTypeID = ((Subtype)subtypeSet.get(entity.getId())).getParentID();
			String parentTypeName = ((Entity)entitySet.get(parentTypeID)).getName();
			retVal.add(new String (indentBase + INDENT2 + "<xs:complexContent>"));
			retVal.add(new String(indentBase + INDENT2 + "<xs:extension base=\"" + parentTypeName + "\">"));
		} 
		
		// add sequence of elements for complex type (by processing 
		// containments and relationships referencing this entity)
		if ((containments != null) || (relationships != null)) {
			retVal.add(new String(indentBase + INDENT4 + "<xs:sequence>"));
			
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
					
					retVal.add(new String(indentBase + INDENT6 + "<xs:element name=\"" +  
							relationship.getName() + "\" " + " type= \"" + childEntity.getName()+ 
							"\"" + " minOccurs=\"" + minOccursString + "\"" + " maxOccurs=\"" 
							+ maxOccursString +  "\">"));	
					if (childEntity.getDescription().length() > 0){
						retVal.add(new String(indentBase + INDENT8 + "<xs:annotation><xs:documentation>" + 
								childEntity.getDescription() + "</xs:documentation></xs:annotation>"));
					}
					retVal.add(new String(indentBase + INDENT6 + "</xs:element>"));
				} // end for (Relationship relationship : relationships){ 
			} // end if (relationship != null) {
		
			if (containments != null) {
				for (Containment containment : containments){
				
					// find min and max occurs strings
					String minOccursString, maxOccursString;
					if (containment.getMin() < 0) minOccursString = "0";
					else minOccursString = containment.getMin().toString();
					if (containment.getMax() < 0) maxOccursString = "unbounded";
					else maxOccursString = containment.getMax().toString();
					
					// check if child is either 1) entity or 2) domain or 3) common domain
					SchemaElement schemaElement = entitySet.get(containment.getChildID());
					if (schemaElement == null) 
						schemaElement = domainSet.get(containment.getChildID());
						
					// CASE 1: Entity is Annonymous Complex Type
					if (schemaElement instanceof Entity && schemaElement.getName().equals("")){
						retVal.add(new String(indentBase + INDENT6 + "<xs:element name=\"" +  containment.getName() + "\" " 
								+ " minOccurs=\"" + minOccursString + "\"" + " maxOccurs=\"" + maxOccursString +  "\">"));			
						if (schemaElement.getDescription().length() > 0){
							retVal.add(new String(indentBase + INDENT8 + "<xs:annotation><xs:documentation>" + 
									schemaElement.getDescription() + "</xs:documentation></xs:annotation>"));
						}
						retVal.addAll(outputEntity((Entity)schemaElement, new String(indentBase + INDENT10)));
			
					// CASE 2: Named entity / domain (include just name)
					} else{
						retVal.add(new String(indentBase + INDENT6 + "<xs:element name=\"" +  containment.getName() + "\" " 
								+ " type=\"" + schemaElement.getName() + "\"" + " minOccurs=\"" + minOccursString + "\"" + " maxOccurs=\"" + maxOccursString +  "\">"));	
						if (schemaElement.getDescription().length() > 0){
							retVal.add(new String(indentBase + INDENT8 + "<xs:annotation><xs:documentation>" + 
									schemaElement.getDescription() + "</xs:documentation></xs:annotation>"));
						}
					}
					retVal.add(new String(indentBase + INDENT6 + "</xs:element>"));
				} // for (Containment containment : containments){
			} // end if (containments != null) {
			
		    retVal.add(new String(indentBase + INDENT4 + "</xs:sequence>"));
		} // end if (containments != null) || (relationships != null) {
	
		// put all attributes together at end
		if (attributes != null){
			for (Attribute a : attributes){
				Domain dom = domainSet.get(a.getDomainID());
				String domName = new String("");
				if (dom != null)
					domName = dom.getName(); 
				retVal.add(new String(indentBase + INDENT4 + "<xs:attribute name=\"" +  
						a.getName() + "\" type=\"" + domName + "\"/>"));	
			}
		} // end if (attributes != null){
	
		// Close off tags if entity is a subtype
		if (subtypeSet.get(entity.getId()) != null){
			retVal.add(new String(indentBase + INDENT2 + "</xs:extension>"));
			retVal.add(new String(indentBase + INDENT2 + "</xs:complexContent>"));
		} 
		retVal.add(new String(indentBase + "</xs:complexType>"));
		
		return retVal;
		
	} // end method outputComplexTypeEntity
	
	private String toUpper(String s){
		String s1 = new String(s.substring(0,1));
		String s2 = new String(s.substring(1,s.length()));
		s1 = s1.toUpperCase();
		return new String(s1 + s2);
	}
	
} // end class