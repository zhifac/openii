// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.importers;

import java.util.*;

import org.exolab.castor.xml.schema.*;
import org.exolab.castor.xml.schema.Group;
import org.exolab.castor.xml.schema.reader.SchemaReader;
import org.mitre.schemastore.model.*;

/**
 * Class for converting XSD files into an Entity-Relationship format
 * 
 *  NOTES:
 *  
 *  1) The EMPTY STRING is assigned EMPTY STRING as NAME for entity created from annonymous complexType
 * 
 *  2) The containments created from Schema --> Entity have EMPTY STRING assigned as name
 *  
 
 * 
 * @author DBURDICK
 */
public class XSDImporter extends Importer
{
	// Statistics about XSD file
	public static final int UNASSIGNED_CHILD_ID = -1; 

	private org.exolab.castor.xml.schema.Schema xmlSchema; // XML Schema being parsed by the schema

	// Stores the schema objects (entities, attributes and relationships) 
	private HashMap<String, SchemaElement> schemaElementsHS = new HashMap<String, SchemaElement>();

	// Keeps track of complex elements to prevent looping
	private HashMap<String, Entity> processedComplexTypeList = new HashMap<String, Entity>();

	// Stores the preset Domains for use during Attribute creation
	private HashMap<String, Domain> domainList = new HashMap<String, Domain>();

	private HashMap<String, ArrayList<Containment>> containmentList = new HashMap<String, ArrayList<Containment>>();

	/** Returns the importer name */
	public String getName()
		{ return "XSD Importer"; }
	
	/** Returns the importer description */
	public String getDescription()
		{ return "This importer can be used to import schemas from an xsd format"; }
	
	/** Returns the importer URI type */
	public Integer getURIType()
		{ return FILE; }
	
	/** Returns the importer URI file types */
	public ArrayList<String> getFileTypes()
	{
		ArrayList<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".xsd");
		return fileTypes;
	}
	
	/** Initializes the importer for the specified URI */
	protected void initialize() throws ImporterException
	{
		try {
			// Parse the schema
			xmlSchema = new SchemaReader(uri.toString()).read();
			
			// reset the Importer
			schemaElementsHS = new HashMap<String, SchemaElement>();
			processedComplexTypeList = new HashMap<String, Entity>();
			domainList = new HashMap<String, Domain>();
			containmentList = new HashMap<String, ArrayList<Containment>>();
			
			// Preset domains and then identify entities, attributes, and relationships in this schema
			loadDomains();
			getRootElements();
		}
		catch(Exception e) { throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); }
	}
	
	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> getExtendedSchemaIDs() throws ImporterException
		{ return new ArrayList<Integer>(); }
	
	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> getSchemaElements() throws ImporterException
		{ return new ArrayList<SchemaElement>(schemaElementsHS.values()); }

	/**
	 * @return Root elements associated with the specified schema. Root elements
	 *         may be 1) SimpleTypes, 2) ComplexTypes, or 3) Elements
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<ElementDecl> getRootElements()
	{	
		//////////////////////////////////////////////
		/////// Process all simple types
		///////////////////////////////////////////////

		//// Each SimpleType should be translated into a Domain
		Enumeration simpleTypes = xmlSchema.getSimpleTypes();
		while (simpleTypes.hasMoreElements()) {
			
			// Translate each Simple Type to a domain
			processSimpleType((SimpleType) simpleTypes.nextElement(),null);
			
		} // while (simpleTypes.hasMoreElements()){
		
			
		////////////////////////////////////////////////////////
		/////// Process all complex types
		////////////////////////////////////////////////////////

		// Each complexType should be translated to an Entity
		Enumeration complexTypeNames = xmlSchema.getComplexTypes();
		Entity currentTypeEntity = null;
		
		// create list with all top-level complex types
		while (complexTypeNames.hasMoreElements()) {
			ComplexType ct = (ComplexType) complexTypeNames.nextElement();
			Entity entity = new Entity(nextId(), ct.getName().toString(), 
					this.getDocumentation(ct), 0);
			if (schemaElementsHS.containsKey(this.compString(entity)) == false) {
				schemaElementsHS.put(this.compString(entity), entity);
				processedComplexTypeList.put(entity.getName(), entity);
			}
			
			// add containment to schema
			Containment containment = new Containment(nextId(), "", "",
					null, entity.getId(), 0, 1, 0);
			if (schemaElementsHS.containsKey(this.compString(containment)) == false) {
				schemaElementsHS.put(this.compString(containment),containment);
				processContainment(containment,false);
			}	
		}

		// process complex types
		complexTypeNames = xmlSchema.getComplexTypes();
		while (complexTypeNames.hasMoreElements()){ 
			
			// get entity for current complexType
			ComplexType currComplexType = (ComplexType)complexTypeNames.nextElement();
			currentTypeEntity = processedComplexTypeList.get(currComplexType.getName().toString());
			
			// find parent type (if it exists)
			if (currComplexType.getBaseType() != null) {
				Entity parentType = processedComplexTypeList.get(currComplexType.getBaseType().getName().toString());
				if (parentType != null){
					Subtype sub = new Subtype(nextId(), parentType.getId(), currentTypeEntity.getId(), 0);
					if (schemaElementsHS.containsKey(this.compString(sub)) == false) {
						schemaElementsHS.put(this.compString(sub), sub);
					}
				}
			}
			
			// get attrs for current complexType
			Enumeration currComplexTypeAttrs = currComplexType.getAttributeDecls();
			while (currComplexTypeAttrs.hasMoreElements()) {
				AttributeDecl attr = (AttributeDecl) currComplexTypeAttrs.nextElement();		
				
				String type = "String";
				SimpleType simpleType = attr.getSimpleType();
				if ((simpleType != null) && (simpleType.getName() != null) && (simpleType.getName().length() > 0)) 
					type = simpleType.getName();
				
				// obtain the proper domain from the preset list
				Domain domain = domainList.get(type);
				if (domain == null) {
					domain = new Domain(nextId(), type, "The " + type + " domain", 0);
					if (schemaElementsHS.containsKey(this.compString(domain)) == false) {
						schemaElementsHS.put(this.compString(domain),domain);
						domainList.put(type, domain);
						System.out.println("adding " + domain.getName() + ", " + domain.getId());
					}
				} // if (domain == null) {

				Attribute attribute = new Attribute(nextId(), attr.getName(), getDocumentation(attr), currentTypeEntity.getId(),domain.getId(), 1,1,false,0);
				if (schemaElementsHS.containsKey(this.compString(attribute)) == false) {
					schemaElementsHS.put(this.compString(attribute),attribute);
				}
			} // while (currComplexTypeAttrs.hasMoreElements()) {

			//// get elements for current complexType (whose entity is currentTypeEntity)
			Enumeration currComplexTypeElements = currComplexType.enumerate();
			while (currComplexTypeElements.hasMoreElements()) {
				Group group = (Group)currComplexTypeElements.nextElement();
				Enumeration e = group.enumerate();
				while (e.hasMoreElements()) {
					
					Object obj = e.nextElement();
					//// handle special cases (WildCard)
					if (obj instanceof Wildcard){
						//// add containment to ANY domain
						Domain anyDomain = domainList.get("any");
						Containment containment = new Containment(nextId(),"", "",
								currentTypeEntity.getId(), anyDomain.getId(), 0, 1, 0);
						if (schemaElementsHS.containsKey(this.compString(containment)) == false) {
							schemaElementsHS.put(this.compString(containment),containment);
							processContainment(containment,false);
						}
						
					} else if (obj instanceof Group){
						System.out.println("currentComplexType has a Group " + ((Group)obj).getName() + " , " +  ((Group)obj).getParticleCount());
						processGroup((Group)obj, currentTypeEntity);
						
					} else  {
						ElementDecl childElement = (ElementDecl) obj;
						XMLType childElementType = childElement.getType();
						
						if (childElement.isReference()){
							// create NEW containment 
							Containment containment = new Containment(nextId(), childElement.getName(), "",
									currentTypeEntity.getId(), UNASSIGNED_CHILD_ID, childElement.getMinOccurs(), childElement.getMaxOccurs(), 0);
							processContainment(containment,true);
						
						} else {
						
							//// If the element type is 1) NULL or 2) SimpleType THEN we are at leaf
							if ((childElementType == null) || (childElementType instanceof SimpleType)) {				
								processSimpleType(childElement, currentTypeEntity.getId());
								
							} else {
								Entity childElementComplexTypeEntity = this.processedComplexTypeList.get(childElementType.getName());
								if (childElementComplexTypeEntity != null){
									//// add containment (currentTypeEntity, childElementComplexTypeEntity)
									Containment containment = new Containment(nextId(), childElement.getName(),
											"", currentTypeEntity.getId(), childElementComplexTypeEntity.getId(), childElement.getMinOccurs(), childElement.getMaxOccurs(), 0);
									if (schemaElementsHS.containsKey(this.compString(containment)) == false) {
										schemaElementsHS.put(this.compString(containment),containment);
										processContainment(containment,false);
									}		
								}
								else { 
									// add entity for complex type
									String childElementComplexTypeName = childElementType.getName();
									if ((childElementComplexTypeName == null) ||(childElementComplexTypeName.length() == 0)){
										childElementComplexTypeEntity = new Entity(nextId(), "", this.getDocumentation(childElement), 0);	
										schemaElementsHS.put(this.compString(childElementComplexTypeEntity), childElementComplexTypeEntity);
									} else {
										childElementComplexTypeEntity = new Entity(nextId(), childElementComplexTypeName, this.getDocumentation(childElement), 0);
										schemaElementsHS.put(this.compString(childElementComplexTypeEntity), childElementComplexTypeEntity);
										this.processedComplexTypeList.put(childElementComplexTypeEntity.getName(), childElementComplexTypeEntity);
									}
									Containment containment = new Containment(nextId(), childElement.getName(),
											"", currentTypeEntity.getId(), childElementComplexTypeEntity.getId(), childElement.getMinOccurs(), childElement.getMaxOccurs(), 0);
									if (schemaElementsHS.containsKey(this.compString(containment)) == false) {
										schemaElementsHS.put(this.compString(containment),containment);
										processContainment(containment,false);
									}
									processComplexTypeEntity(childElement, childElementComplexTypeEntity);
								}
							}
						} // end else if (childElement.isReference()){
					} // end else if (obj instanceof Wildcard)
				} // end while (e.hasMoreElements()) {
				 
			} // while (currComplexTypeElements.hasMoreElements()) {
		} // while (complexTypeNames.hasMoreElements()){ 

		//////////////////////////////////////////////////////////////////
		//  Process all top-level elements ///////////////////////////////
		//////////////////////////////////////////////////////////////////
		Enumeration elements = xmlSchema.getElementDecls();
		while (elements.hasMoreElements()) {
			
			ElementDecl element = (ElementDecl) elements.nextElement();
			
			if (element.isReference()){
				// create NEW containment for schema to referenced element 
				Containment containment = new Containment(nextId(), element.getName(), "",
						null, UNASSIGNED_CHILD_ID, element.getMinOccurs(), element.getMaxOccurs(), 0);
				processContainment(containment,true);
			
			} else {
		
				// process element with Simple Type OR No Type
				if ((element.getType() == null) || (element.getType() instanceof SimpleType)) {
					 processSimpleType(element,null);
				} 
				// process element with a Complex Type
				else { 
					Entity complexTypeEntity = this.processedComplexTypeList.get(((ComplexType)element.getType()).getName());
					if (complexTypeEntity != null){
						//// add containment (schema, complexTypeEntity)
						Containment containment = new Containment(nextId(), element.getName(), 
								"", null, complexTypeEntity.getId(), 0, 1, 0);
						if (schemaElementsHS.containsKey(this.compString(containment)) == false) {
							schemaElementsHS.put(this.compString(containment),containment);
							processContainment(containment,false);
						}	
					}
					else { 
						String name = ((ComplexType)element.getType()).getName();
						if ((name == null) || (name.length() == 0)){
							complexTypeEntity = new Entity(nextId(), "", this.getDocumentation(element), 0);
							this.schemaElementsHS.put(this.compString(complexTypeEntity), complexTypeEntity);
						} else {
							complexTypeEntity = new Entity(nextId(), name, this.getDocumentation(element), 0);
							this.schemaElementsHS.put(this.compString(complexTypeEntity), complexTypeEntity);
							this.processedComplexTypeList.put(complexTypeEntity.getName(), complexTypeEntity);
						}
						//// add containment (schema, complexTypeEntity)
						Containment containment = new Containment(nextId(), element.getName(), 
							"", null, complexTypeEntity.getId(), 0, 1, 0);
						if (schemaElementsHS.containsKey(this.compString(containment)) == false) {
							schemaElementsHS.put(this.compString(containment),containment);
							processContainment(containment,false);
						}	
						processComplexTypeEntity(element, complexTypeEntity);
						
					} // else if (complexTypeEntity != null){
				} // end else if ((element.getType() == null) || (element.getType() instanceof SimpleType)) {
			} // if (element.isReference()){
		}// while (elements.hasMoreElements()) {
		
		// Return the list of root elements
		return new ArrayList<ElementDecl>();
	}

	

	public void processComplexTypeEntity(ElementDecl complexTypeElement, Entity complexTypeEntity){
		
		// get the attributes for the complex type
		Enumeration<?> currElementAttrList = ((ComplexType)complexTypeElement.getType()).getAttributeDecls();		
		while (currElementAttrList.hasMoreElements()) {
			AttributeDecl attr = (AttributeDecl) currElementAttrList.nextElement();
						
			String type = "String";
			SimpleType simpleType = attr.getSimpleType();
			if ((simpleType != null) && (simpleType.getName() != null) && (simpleType.getName().length() > 0)) 
				type = simpleType.getName();
			
			// obtain the proper domain from the preset list
			Domain domain = domainList.get(type);
			if (domain == null) {
				domain = new Domain(nextId(), type, "The " + type + " domain", 0);
				if (schemaElementsHS.containsKey(this.compString(domain)) == false) {
					schemaElementsHS.put(this.compString(domain),domain);
					domainList.put(type, domain);
					System.out.println("adding " + domain.getName() + ", " + domain.getId());
				}
			} // if (domain == null) {
			
			Attribute attribute = new Attribute(nextId(), attr.getName(), 
				this.getDocumentation(attr), complexTypeEntity.getId(),domain.getId(),1,1,false, 0);
			if (schemaElementsHS.containsKey(this.compString(attribute)) == false) {
				schemaElementsHS.put(this.compString(attribute),attribute);
			}
		} // while (attrList.hasMoreElements()){
		
		
		//// get elements for current compl
		
		Enumeration<?> currComplexTypeElements = ((ComplexType)complexTypeElement.getType()).enumerate();
		while (currComplexTypeElements.hasMoreElements()) {
			
			Group group = (Group)currComplexTypeElements.nextElement();
			Enumeration<?> e = group.enumerate();
			while (e.hasMoreElements()) {
		
				Object obj = e.nextElement();
				
				//// handle special cases (WildCard / Any)
				if (obj instanceof Wildcard){
					//// add containment to ANY domain
					Domain anyDomain = domainList.get("any");
					Containment containment = new Containment(nextId(),"", "",
							null, anyDomain.getId(), 0, 1, 0);
					if (schemaElementsHS.containsKey(this.compString(containment)) == false) {
						schemaElementsHS.put(this.compString(containment),containment);
						processContainment(containment,false);
					}		
				} else if (obj instanceof Group){
					System.out.println("currentComplexType has a Group " + ((Group)obj).getName() + " , " +  ((Group)obj).getParticleCount());
					processGroup((Group)obj, complexTypeEntity);
						
				} else {
					ElementDecl childElement = (ElementDecl) obj;
					XMLType childElementType = childElement.getType();
					
					if (childElement.isReference()){
						// create NEW containment 
						Containment containment = new Containment(nextId(), childElement.getName(), "",
								complexTypeEntity.getId(), UNASSIGNED_CHILD_ID, childElement.getMinOccurs(), childElement.getMaxOccurs(), 0);
						processContainment(containment,true);
					} else {
						//// If the element type is 1) NULL or 2) SimpleType THEN we are at leaf
						if ((childElementType == null) || (childElementType instanceof SimpleType)) {
							processSimpleType(childElement, complexTypeEntity.getId());
							
						} else {
							Entity childElementComplexTypeEntity = this.processedComplexTypeList.get(((ComplexType)childElement.getType()).getName());
							if (childElementComplexTypeEntity != null){
								//// add containment (if not processed before) for child of parent
								Containment containment = new Containment(nextId(), childElement.getName(), "",
										complexTypeEntity.getId(), childElementComplexTypeEntity.getId(), childElement.getMinOccurs(), childElement.getMaxOccurs(), 0);
								if (schemaElementsHS.containsKey(this.compString(containment)) == false) {
									schemaElementsHS.put(this.compString(containment),containment);
									processContainment(containment,false);
								}
							}
							else { 
							
								// add entity for complex type
								String childElementComplexTypeName = childElementType.getName();
								if ((childElementComplexTypeName == null) ||(childElementComplexTypeName.length() == 0)){
									childElementComplexTypeEntity = new Entity(nextId(), "", this.getDocumentation(childElement), 0);
									schemaElementsHS.put(this.compString(childElementComplexTypeEntity), childElementComplexTypeEntity);
								} else {
									childElementComplexTypeEntity = new Entity(nextId(), childElementComplexTypeName, this.getDocumentation(childElement), 0);
									schemaElementsHS.put(this.compString(childElementComplexTypeEntity), childElementComplexTypeEntity);
									this.processedComplexTypeList.put(childElementComplexTypeEntity.getName(), childElementComplexTypeEntity);
								}					
								//// add containment 
								Containment containment = new Containment(nextId(), childElement.getName(), "",
										complexTypeEntity.getId(), childElementComplexTypeEntity.getId(), childElement.getMinOccurs(), childElement.getMaxOccurs(), 0);
								if (schemaElementsHS.containsKey(this.compString(containment)) == false) {
									schemaElementsHS.put(this.compString(containment),containment);
									processContainment(containment,false);
								}
	
								processComplexTypeEntity(childElement, childElementComplexTypeEntity);
							}
						} // end else [[if ((childElementType == null) || (childElementType instanceof SimpleType)) {
					} // end else if (childElement.isReference()){
				} // end else(if (obj instanceof Wildcard){
			} // end while (e.hasMoreElements()) {
			 
		} // while (currComplexTypeElements.hasMoreElements()) {
			
	} // end method processComplexTypeElement

	
	
	/**************************************************************************
	 * ProcessSimpleType: Process a given SimpleType ST by: 
	 * 1) Creating a Domain for ST
	 * 2) creating domain values for ST (if given, e.g., ST is an enumeration simple type)
	 * 3) add containment of domain to schema
	 * 
	 * @param simpleType SimpleType to be processed
	 *  
	 *************************************************************************/
	void processSimpleType (SimpleType simpleType, Integer containingID){

		// assign the default type of String
		String type = "String";
		if ((simpleType != null) && (simpleType.getName() != null) && (simpleType.getName().length() > 0)) 
			type = simpleType.getName();
		
		Domain domain = domainList.get(type);
			
		if (domain == null) {
			
			// turn simpleType into domain
			domain = new Domain(nextId(), type, "The " + type + " domain", 0);
			if (schemaElementsHS.containsKey(this.compString(domain)) == false) {
				schemaElementsHS.put(this.compString(domain), domain);
			
				// create a DomainValue for each value in the enumeration
				Enumeration<?> facets = simpleType.getFacets();
				while (facets.hasMoreElements()) {
					Facet facet = (Facet) facets.nextElement();
					DomainValue domainValue = new DomainValue(nextId(), facet.getValue(), facet.getValue(), domain.getId(), 0);
				
					if (schemaElementsHS.containsKey(this.compString(domainValue)) == false) {
						schemaElementsHS.put(this.compString(domainValue), domainValue);
					}
				}
			}
			domainList.put(type, domain);
			System.out.println("adding " + domain.getName() + ", " + domain.getId());
			
		}
	
		//// add containment to schema
		Containment containment = new Containment(nextId(), "", "",
				containingID, domain.getId(), 0, 1, 0);
		if (schemaElementsHS.containsKey(this.compString(containment)) == false) {
			schemaElementsHS.put(this.compString(containment),containment);
			processContainment(containment, false);
		}	
    } // end method processSimpleType

	/**************************************************************************
	 * ProcessSimpleType: Process a given SimpleType ST by: 
	 * 1) Creating a Domain for ST
	 * 2) creating domain values for ST (if given, e.g., ST is an enumeration simple type)
	 * 3) add containment of domain to schema
	 * 
	 * @param simpleType SimpleType to be processed
	 *  
	 *************************************************************************/
	void processSimpleType(ElementDecl simpleTypeElement, Integer containingID){

		// assign the default type of String
		String type = "String";
		SimpleType simpleType = (SimpleType)simpleTypeElement.getType();
		if ((simpleType != null) && (simpleType.getName() != null) && (simpleType.getName().length() > 0)) 
			type = simpleType.getName();
		
		Domain domain = domainList.get(type);	
		if (domain == null) {
			// turn simpleType into domain
			
			domain = new Domain(nextId(), type, "The " + type + " domain", 0);
			if (schemaElementsHS.containsKey(this.compString(domain)) == false) {
				schemaElementsHS.put(this.compString(domain), domain);
			
				// create a DomainValue for each value in the enumeration
				Enumeration<?> facets = simpleType.getFacets();
				while (facets.hasMoreElements()) {
					Facet facet = (Facet) facets.nextElement();
					DomainValue domainValue = new DomainValue(nextId(), facet.getValue(), facet.getValue(), domain.getId(), 0);
				
					if (schemaElementsHS.containsKey(this.compString(domainValue)) == false) {
						schemaElementsHS.put(this.compString(domainValue), domainValue);
					}
				}
			}
		
			domainList.put(type, domain);
			System.out.println("adding " + domain.getName() + ", " + domain.getId());
		}
	
		//// add containment to schema
		Containment containment = new Containment(nextId(), simpleTypeElement.getName(), "",
				containingID, domain.getId(), 0, 1, 0);
		if (schemaElementsHS.containsKey(this.compString(containment)) == false) {
			schemaElementsHS.put(this.compString(containment),containment);
			processContainment(containment, false);
		}
    } // end method processSimpleType
	
	
	
	///////////////////////////////////////////////////////////////////////////
	/////////////////////// UTILITY FUNCTIONS /////////////////////////////////
	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return The documentation associated with the specified element
	 */
	private String getDocumentation(Annotated element) {
		StringBuffer documentation = new StringBuffer("");
		documentation.append(appendDocumentation(element));
		if (element instanceof ElementDecl)
			documentation.append(appendDocumentation(((ElementDecl) element)
					.getType()));
		if (element instanceof AttributeDecl)
			documentation.append(appendDocumentation(((AttributeDecl) element)
					.getSimpleType()));
		return documentation.toString();
	}

	/**
	 * @return The documentation associated with the specified type
	 */
	@SuppressWarnings("unchecked")
	private StringBuffer appendDocumentation(Annotated type) {
		StringBuffer documentation = new StringBuffer();
		if (type != null) {
			Enumeration annotations = type.getAnnotations();
			while (annotations.hasMoreElements()) {
				Annotation annotation = (Annotation) annotations.nextElement();
				Enumeration docs = annotation.getDocumentation();
				while (docs.hasMoreElements()) {
					Documentation doc = (Documentation) docs.nextElement();
					if (doc.getContent() != null)
						documentation.append(doc.getContent().replaceAll("<",
								"&lt;").replaceAll(">", "&gt;").replaceAll("&",
								"&amp;"));
				}
			}
		}
		return documentation;
	}

		
	/**
	 * Function for loading the preset domains into the Schema and into a list
	 * for use during Attribute creation
	 */
	private void loadDomains() {
		Domain domain = new Domain(nextId(), ANY,
				"The Any wildcard domain", 0);
		// // schemaElements.add(domain);
		if (schemaElementsHS.containsKey(this.compString(domain)) == false) {
			schemaElementsHS.put(this.compString(domain), domain);
		}

		domainList.put(ANY, domain);

		domain = new Domain(nextId(), INTEGER,
				"The Integer domain", 0);
		// schemaElements.add(domain);

		schemaElementsHS.put(this.compString(domain), domain);

		domainList.put(INTEGER, domain);

		domain = new Domain(nextId(), DOUBLE,
				"The Double domain", 0);
		// schemaElements.add(domain);
		schemaElementsHS.put(this.compString(domain), domain);
		domainList.put(DOUBLE, domain);

		domain = new Domain(nextId(), STRING,
				"The String domain", 0);
		schemaElementsHS.put(this.compString(domain), domain);
		domainList.put(STRING, domain);

		domain = new Domain(nextId(), "string",
				"The string domain", 0);
		schemaElementsHS.put(this.compString(domain), domain);
		domainList.put("string", domain);
		
		domain = new Domain(nextId(), DATETIME,
				"The DateTime domain", 0);
		// schemaElements.add(domain);
		schemaElementsHS.put(this.compString(domain), domain);
		domainList.put(DATETIME, domain);

		domain = new Domain(nextId(), BOOLEAN,
				"The Boolean domain", 0);
		// schemaElements.add(domain);
		schemaElementsHS.put(this.compString(domain), domain);
		domainList.put(BOOLEAN, domain);
	}
	
	/**
	 * compString: Generates a unique identifier string for each SchemaType
	 * based on its contents. The contract for this function is that the unique 
	 * identifier can serve as HashKey for an element in the SchemaElement list. 
	 * Two schemaElements with the SAME compString are the SAME element
	 * 
	 * @param o  SchemaElement for which compString (hashKey) being generated
	 * @return compString generated comparison String
	 */
	public String compString(SchemaElement o) {
		String retVal = null;

		/** Entity: NAME, DESC, BASE */
		if (o instanceof Entity) {
			if (((Entity)o).getName().equals("")){
				retVal = new String(((Entity) o).getId() + " , "
						+ ((Entity) o).getBase() + ", ENTITY");
			} else {
				retVal = new String(((Entity) o).getName() + " , "
					+ ((Entity) o).getBase() + ", ENTITY");
			}
		/** Containment: NAME, DESC, BASE, PARENT_ID, CHILD_ID */
		} else if (o instanceof Containment) {
			retVal = new String(((Containment) o).getName() + " , "
					+ ((Containment) o).getBase() + " , "
					+ ((Containment) o).getParentID() + " , "
					+ ((Containment) o).getChildID() + ", CONTAINMENT");
			/** Attribute: NAME, DESC, BASE, DOMAIN_ID, ENTITY_ID */
		} else if (o instanceof Attribute) {
			retVal = new String(((Attribute) o).getName() + " , "
					+ ((Attribute) o).getBase() + " , "
					+ ((Attribute) o).getDomainID() + " , "
					+ ((Attribute) o).getEntityID() + ", ATTRIBUTE");
			/** Domain: NAME, BASE */
		} else if (o instanceof Domain) {
			retVal = new String(((Domain) o).getName() + " , "
					+ ((Domain) o).getBase()) + " , DOMAIN";
			/** DomainValue: NAME, DESC, BASE * */
		} else if (o instanceof DomainValue) {
			retVal = new String(((DomainValue) o).getDomainID() + " , "
					+ ((DomainValue) o).getName() + " , "
					+ ((DomainValue) o).getBase() + ", DOMAIN VALUE");
			/** Subtype: PARENT_ID, CHILD_ID, BASE * */
		} else if (o instanceof Subtype) {
			retVal = new String(((Subtype) o).getParentID() + " , "
					+ ((Subtype) o).getChildID() + " , "
					+ ((Subtype) o).getBase() + "SUBTYPE ");
		} else {
			retVal = new String();
		}

		return retVal;
	} // end method compString()
	
	public void processContainment(Containment containment, boolean isRef){
	
		ArrayList<Containment> contList = null;
		// add containment to schemaElement list + containmentList 
		if (isRef == false){
			if (containment.getName().length() > 0){
					
				// check if containment in containmentList
				// IF containment NOT in containment list THEN add containment
				// ELSE process + change ALL childless containments to real containments + add current containment to list
				contList = this.containmentList.get(containment.getName());
				if (contList == null){
					contList = new ArrayList<Containment>();
					contList.add(containment);
					this.containmentList.put(containment.getName(), contList);
				} else{
					for (Containment cont : contList){
						// give cont the SAME child id as current containment
						// add to schemaElement list
						if (cont.getChildID() == UNASSIGNED_CHILD_ID){
							cont.setChildID(containment.getChildID());
							if (schemaElementsHS.containsKey(this.compString(cont)) == false) {
								schemaElementsHS.put(this.compString(cont),cont);
							}	
						} else {
							System.out.println("THERE ARE 2 CONTAINMENTS WITH THE SAME NAME: " + cont.getName());
						}
					}
					contList = null;
					contList = new ArrayList<Containment>();
					contList.add(containment);
					this.containmentList.put(containment.getName(), contList);
				}
			}
			
		} else {
			// HANDLE REFERENCE
			//	 - IF not unseen element THEN create new list
			//   - ELSE if seen THEN find list
			//        - IF LIST contains complete containment 
			//			  THEN use child id from existing containment
			//                 and add ONLY to schemaElement list
			//  	 
			//        - ELSE add to existing containment list
			
			contList = this.containmentList.get(containment.getName());
			
			if (contList == null){
				contList = new ArrayList<Containment>();
				contList.add(containment);
				this.containmentList.put(containment.getName(), contList);
			} else {
				boolean containmentProcessed = false;
				for (Containment cont : contList){
					if (cont.getChildID() != UNASSIGNED_CHILD_ID){
						containment.setChildID(cont.getChildID());
						containmentProcessed = true;
						if (schemaElementsHS.containsKey(this.compString(containment)) == false) {
							schemaElementsHS.put(this.compString(containment),containment);
						}	
						break;
					}
				} // for (Containment cont : contList){
			
				if (containmentProcessed != true){
					contList.add(containment);
					this.containmentList.put(containment.getName(), contList);
				}
			} // else if (contList == null){
		} // else if (isRef == false){
		
	} // end method processContainment
	
	void processGroup (Group currentGroup, Entity parent){
		
		Enumeration<?> e2 = currentGroup.enumerate();
		while (e2.hasMoreElements()) {
			
			Object obj2 = e2.nextElement();
			// handle special cases (WildCard)
			if (obj2 instanceof Wildcard){
				// add containment to ANY domain
				Domain anyDomain = domainList.get("Any");
				Containment containment = new Containment(nextId(),"", "",
						parent.getId(), anyDomain.getId(), 0, 1, 0);
				if (schemaElementsHS.containsKey(this.compString(containment)) == false) {
					schemaElementsHS.put(this.compString(containment),containment);
					processContainment(containment,false);
				}
				
			} else if (obj2 instanceof Group){
				processGroup((Group)obj2, parent);
				
			} else  {
				ElementDecl childElement = (ElementDecl) obj2;
				XMLType childElementType = childElement.getType();
				
				if (childElement.isReference()){
					// create NEW containment 
					Containment containment = new Containment(nextId(), childElement.getName(), "",
							parent.getId(), UNASSIGNED_CHILD_ID, childElement.getMinOccurs(), childElement.getMaxOccurs(), 0);
					processContainment(containment,true);
				
				} else {
				
					// IF the element type is 1) NULL or 2) SimpleType THEN we are at leaf
					if ((childElementType == null) || (childElementType instanceof SimpleType)) {
						processSimpleType(childElement, parent.getId());
					// ELSE element is complexType
					} else {
						Entity childElementComplexTypeEntity = this.processedComplexTypeList.get(childElementType.getName());
						if (childElementComplexTypeEntity != null){
							// add containment (currentTypeEntity, childElementComplexTypeEntity)
							Containment containment = new Containment(nextId(), childElement.getName(),
									"", parent.getId(), childElementComplexTypeEntity.getId(), childElement.getMinOccurs(), childElement.getMaxOccurs(), 0);
							if (schemaElementsHS.containsKey(this.compString(containment)) == false) {
								schemaElementsHS.put(this.compString(containment),containment);
								processContainment(containment,false);
							}		
						}
						else { 
							// add entity for complex type
							String childElementComplexTypeName = childElementType.getName();
							if ((childElementComplexTypeName == null) ||(childElementComplexTypeName.length() == 0)){
								childElementComplexTypeEntity = new Entity(nextId(), "", this.getDocumentation(childElement), 0);	
								schemaElementsHS.put(this.compString(childElementComplexTypeEntity), childElementComplexTypeEntity);
							} else {
								childElementComplexTypeEntity = new Entity(nextId(), childElementComplexTypeName, this.getDocumentation(childElement), 0);
								schemaElementsHS.put(this.compString(childElementComplexTypeEntity), childElementComplexTypeEntity);
								this.processedComplexTypeList.put(childElementComplexTypeEntity.getName(), childElementComplexTypeEntity);
							}
							Containment containment = new Containment(nextId(), childElement.getName(),
									"", parent.getId(), childElementComplexTypeEntity.getId(), childElement.getMinOccurs(), childElement.getMaxOccurs(), 0);
							if (schemaElementsHS.containsKey(this.compString(containment)) == false) {
								schemaElementsHS.put(this.compString(containment),containment);
								processContainment(containment,false);
							}
							processComplexTypeEntity(childElement, childElementComplexTypeEntity);
						}
					}
				} // end else if (childElement.isReference()){
			} // end else if 
		}
	} // end method processGroup

} // end class
