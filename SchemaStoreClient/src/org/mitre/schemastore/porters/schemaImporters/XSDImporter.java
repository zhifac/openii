// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.schemaImporters;

import java.io.IOException;
import java.net.URI;
import java.util.*;

import org.exolab.castor.xml.schema.*;
import org.exolab.castor.xml.schema.Group;
import org.exolab.castor.xml.schema.Schema;
import org.exolab.castor.xml.schema.reader.SchemaReader;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.graph.Graph;
import org.mitre.schemastore.model.graph.HierarchicalGraph;
import org.mitre.schemastore.model.graph.model.GraphModel;
import org.mitre.schemastore.porters.ImporterException;


/**
 * XSDImporter: Class for importing XSD files into the M3 Format
 * 
 * @author DBURDICK
 */



public class XSDImporter extends SchemaImporter
{
	
	private static final String DEFAULT_NAMESPACE = "urn:mitre.org:M3";

	static public void main(String args[])
	{
		try {
			XSDImporter xsdImporter = new XSDImporter();
			URI uri = new URI("C:/3_LMP/Schemas/3LayerMessage/3LayerMessage.xsd");
			xsdImporter.getSchemaElements(uri);
		}
		catch(Exception e) { e.printStackTrace(); }
	}
	
	// Stores the M3 schema elements (entities, attributes, domain, relationships, etc.) 
	private HashMap<String, SchemaElement> schemaElementsHS = new HashMap<String, SchemaElement>();
	private HashMap<String,Domain> domainList = new HashMap<String,Domain>();
	
	// Stores the unique "Any" entity
	private Entity anyEntity;
	
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
	protected void initializeSchemaStructures() throws ImporterException
	{	
		 try {
	        String proxyHost = new String("gatekeeper.mitre.org");
	        String proxyPort = new String("80");
            System.getProperties().put( "http.proxyHost",proxyHost );
            System.getProperties().put( "http.proxyPort",proxyPort );
           
	     }catch (Exception e) {
	     
	          	String message = new String("[E] XSDImporter -- " + 
	          			"Likely a security exception - you " +
	                		"must allow modification to system properties if " +
	                		"you want to use the proxy");
	          	e.printStackTrace();
	          	throw new ImporterException(ImporterException.PARSE_FAILURE,message); 
	     }

		try {

			// reset the Importer
			schemaElementsHS = new HashMap<String, SchemaElement>();
			domainList = new HashMap<String, Domain>();
			
			// Preset domains and then process this schema
			loadDomains();
			
			// create DOM tree for main schema 
			SchemaReader xmlSchemaReader = new SchemaReader(uri.toString());
			Schema mainSchema = xmlSchemaReader.read();
			getRootElements(mainSchema);
			
			// get all imported schemas (recursively)
			ArrayList<Schema> importedSchemas = new ArrayList<Schema>();		
			findReferredSchemas(importedSchemas, mainSchema);			
			for (Schema refSchema : importedSchemas){
				getRootElements(refSchema);
			}		
		
			GraphModel xmlModel = null;
			for (GraphModel gm : HierarchicalGraph.getGraphModels()){
				if (gm.getName().equalsIgnoreCase("XML"))
					gm = xmlModel;
			}
			HierarchicalGraph hier = new HierarchicalGraph(new Graph(new org.mitre.schemastore.model.Schema(1,"test","me","","","",false),new ArrayList<SchemaElement>(schemaElementsHS.values())),xmlModel);
		
		}
		catch(Exception e) { 			
			e.printStackTrace();
			throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); 
		}
	}
	
	void findReferredSchemas (ArrayList<Schema> refSchemas, Schema mainSchema) throws IOException {
			
		Enumeration<?> enumeratedSchemas = mainSchema.getImportedSchema();
		while (enumeratedSchemas.hasMoreElements()){
			Schema importedSchema = (Schema)enumeratedSchemas.nextElement();
			
			// check if we added this schema already
			boolean alreadySeen = false;
			for (Schema refSchema : refSchemas)
				if (importedSchema.getTargetNamespace() != null && importedSchema.getTargetNamespace().equals(refSchema.getTargetNamespace()) && importedSchema.getSchemaLocation() != null && importedSchema.getSchemaLocation().equals(refSchema.getSchemaLocation()))
					alreadySeen = true;
			
			if (alreadySeen == false){
				refSchemas.add(importedSchema);
				findReferredSchemas(refSchemas,importedSchema);
			}
	
		}
	} // end method
		
	
	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> generateExtendedSchemaIDs() throws ImporterException
		{ return new ArrayList<Integer>(); }
	
	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> generateSchemaElements() throws ImporterException
		{ return new ArrayList<SchemaElement>(schemaElementsHS.values()); }


	/**
	 * getRootElements:  Processes the SimpleTypes, ComplexTypes, and Elements 
	 * defined at the "root" (Schema) level.
	 * 
	 * @param xmlSchema schema to be processed
	 */
	public void getRootElements(Schema xmlSchema) {
		
		// Each root SimpleType should be translated into a Domain
		Enumeration<?> simpleTypes = xmlSchema.getSimpleTypes();
		while (simpleTypes.hasMoreElements())
			processSimpleType((SimpleType) simpleTypes.nextElement(), null);
		
		// Each root ComplexType should be translated into an Entity
		Enumeration<?> complexTypes = xmlSchema.getComplexTypes();
		while (complexTypes.hasMoreElements())
			processComplexType((ComplexType) complexTypes.nextElement(), null);
		
		// Each root Element should be translated into a Containment (with schema as parent)
		Enumeration<?> elements = xmlSchema.getElementDecls();
		while (elements.hasMoreElements()) 
			processElement((ElementDecl) elements.nextElement(), null);
	}
	
	/**
	 * processSimpleType: creates M3 Domain for the passed SimpleType 
	 * (or finds references to existing Domain if type seen before)
	 * and adds this domain as child of passed Containment or Attribute
	 * 
	 * @param passedType XML SimpleType which needs to either be processed 
	 * or referenced if already seen
	 * @param parent M3 Containment or Attribute to which domain for passed 
	 * simpleType should be added as child
	 */
	public void processSimpleType (XMLType passedType, SchemaElement parent)
	{		
		// assign the default type of String
		String typeName = "String";
		if ((passedType != null) && (passedType.getName() != null) && (passedType.getName().length() > 0)) 
			typeName = passedType.getName();
		
		// handle "Any" type
		if (passedType instanceof AnyType)
			typeName = "Any";
		
		// handle IDREF / IDREFS -- generate relationship to "Any" entity
		if (parent instanceof Attribute && (typeName.equals("IDREF") || typeName.equals("IDREFS"))){
		
			if (this.anyEntity == null){
				this.anyEntity = new Entity(nextId(),"ANY","ANY ENTITY",0);
				schemaElementsHS.put(this.compString(DEFAULT_NAMESPACE,null,null,this.anyEntity),this.anyEntity);
			}	
			Integer rightMax = ( typeName.equals("IDREFS") ) ? null : 1;   
			Relationship rel = new Relationship(nextId(),parent.getName(),((Attribute)parent).getEntityID(),0,1,this.anyEntity.getId(),0,rightMax,0);
			schemaElementsHS.put(this.compString(DEFAULT_NAMESPACE,null,null,rel),rel);
			
			// TODO: set the domain of the parent attribute to 
			// TODO: should we remove the attribute with type Any?
			((Attribute)parent).setDomainID(domainList.get(DEFAULT_NAMESPACE + " " + "Any").getId());
			
		}
		else {
	
			// find Domain for SimpleType (generated if required)
			// Domain domain = new Domain(nextId(), typeName, this.getDocumentation(passedType), passedType.getSchema().getTargetNamespace(), 0);
			Domain domain = new Domain(nextId(), typeName, this.getDocumentation(passedType), 0);
			//TODO: fix passedType namespace
			String passedTypeNamespace = passedType == null ? DEFAULT_NAMESPACE : passedType.getSchema().getTargetNamespace();
			if (domainList.containsKey(passedTypeNamespace + " " + domain.getName()) == false) {
				domainList.put(passedTypeNamespace + " " + domain.getName(),domain);
				schemaElementsHS.put(this.compString(passedTypeNamespace,null, null, domain), domain);
				
				if (passedType instanceof SimpleType && !(passedType instanceof Union)){
					// create DomainValues (if specified for SimpleType)
					Enumeration<?> facets = ((SimpleType)passedType).getFacets("enumeration");
					while (facets.hasMoreElements()) {
						Facet facet = (Facet) facets.nextElement();
						DomainValue domainValue = new DomainValue(nextId(), facet.getValue(), facet.getValue(), domain.getId(), 0);
						schemaElementsHS.put(this.compString(passedTypeNamespace, passedType,facet,domainValue), domainValue);	
					}
				}
				
				// TODO: process Union Types
				else if (passedType instanceof Union){
					Union passedUnion = (Union)passedType;
					Enumeration<?> memberTypes = passedUnion.getMemberTypes();
					while (memberTypes.hasMoreElements()){
						SimpleType childType = (SimpleType)memberTypes.nextElement();
						// create a subtype to capture union
						//Subtype subtype = new Subtype(nextId(),domain.getId(),-1,0);
						//	schemaElementsHS.put(this.compString(passedType, childType, subtype), subtype);
						//processSimpleType(childType,subtype);
					}
				}
				
			}
			// attached Domain as child to passed Attribute / Containment / Subtype
			domain = (Domain)schemaElementsHS.get(this.compString(passedTypeNamespace,null,null,domain));
			if (parent instanceof Attribute)
				((Attribute)parent).setDomainID(domain.getId());
			else if (parent instanceof Containment)
				((Containment)parent).setChildID(domain.getId());
			else if (parent instanceof Subtype)
				((Subtype)parent).setChildID(domain.getId());
		}
	} // end method processSimpleType

		
	/**
	 * processComplexType: creates M3 Entity for the passed ComplexType 
	 * (or finds references to existing Entity if type seen before)
	 * and adds this entity as child of passed Containment or Subtype
	 * 
	 * @param passedType XML ComplexType which needs to either be processed 
	 * or referenced if already seen
	 * @param parent M3 Containment or Subtype to which entity for passed 
	 * complexType should be added as child
	 */
	public void processComplexType (ComplexType passedType, SchemaElement parent)
	{
		
		// check to see if entity has been created for passed complex type
		// create new Entity if none has been created 
		Entity entity = new Entity(nextId(), passedType.getName(), this.getDocumentation(passedType), 0);
	//	Entity entity = new Entity(nextId(), passedType.getName(), this.getDocumentation(passedType), passedType.getSchema().getTargetNamespace(),0);
		
		
		if (schemaElementsHS.containsKey(this.compString(passedType.getSchema().getTargetNamespace(),passedType,null, entity)) == false) {
			schemaElementsHS.put(this.compString(passedType.getSchema().getTargetNamespace(),passedType,null, entity), entity);
	
				
			try {
				// get Attributes for current complexType
				Enumeration<?> attrDecls = passedType.getAttributeDecls();
				while (attrDecls.hasMoreElements()){
				
					AttributeDecl attrDecl = (AttributeDecl)attrDecls.nextElement();
					while (attrDecl.isReference() == true && attrDecl.getReference() != null)
							attrDecl = attrDecl.getReference();
						
					boolean containsID = attrDecl.getSimpleType() != null && attrDecl.getSimpleType().getName() != null && attrDecl.getSimpleType().getName().equals("ID");
					Attribute attr = new Attribute(nextId(),(attrDecl.getName() == null ? "" : attrDecl.getName()),getDocumentation(attrDecl),entity.getId(),-1,(attrDecl.isRequired()? 1 : 0), 1, containsID, 0); 
					if (attr.getName().equals("qualifier"))
						System.err.println("stop");
					
					
					//Attribute attr = new Attribute(nextId(),(attrDecl.getName() == null ? "" : attrDecl.getName()),getDocumentation(attrDecl),entity.getId(),-1,(attrDecl.isRequired()? 1 : 0), 1, containsID, attrDecl.getSchema().getTargetNamespace(), 0); 
					
					schemaElementsHS.put(this.compString(attrDecl.getSchema().getTargetNamespace(),passedType, null, attr), attr);
					processSimpleType(attrDecl.getSimpleType(), attr);
				}
			} catch (IllegalStateException e){
				System.err.println("(E) XSDImporter:processComplexType -- failed reading attributes for " + parent.getName() + " of type " + passedType.getName() + " in schema location " + passedType.getSchema().getSchemaLocation() + " schema namespace " + passedType.getSchema().getTargetNamespace());
			}
			// get Elements for current complexType
			Enumeration<?> elementDecls = passedType.enumerate();
			while (elementDecls.hasMoreElements()) {
				Group group = (Group)elementDecls.nextElement();
				processGroup(group, entity);
			}
		
			// get SuperTypes for current complexType 
			XMLType baseType = null;
			if (passedType.getBaseType() != null){
				baseType = passedType.getBaseType();
				
				// process simpleType supertype here -- create a "special" Entity
				if (baseType instanceof SimpleType){
					Subtype subtype = new Subtype(nextId(),-1,entity.getId(),0);
					//Subtype subtype = new Subtype(nextId(),-1,entity.getId(),0);
					// TODO: check the targetNamespace
					schemaElementsHS.put(this.compString(passedType.getSchema().getTargetNamespace(),passedType, baseType, subtype), subtype);
					
					Entity superTypeEntity = new Entity(nextId(), (baseType.getName() == null ? "" : baseType.getName()), this.getDocumentation(baseType), 0);
					//Entity superTypeEntity = new Entity(nextId(), (baseType.getName() == null ? "" : baseType.getName()), this.getDocumentation(baseType), baseType.getSchema().getTargetNamespace(),0);
					
					// TODO: check the targetNamespace
					if (schemaElementsHS.get(this.compString(baseType.getSchema().getTargetNamespace(),baseType,null,superTypeEntity)) == null)
						schemaElementsHS.put(this.compString(baseType.getSchema().getTargetNamespace(),baseType,null,superTypeEntity), superTypeEntity);
					superTypeEntity = (Entity)schemaElementsHS.get(this.compString(baseType.getSchema().getTargetNamespace(),baseType,null,superTypeEntity));
					subtype.setParentID(superTypeEntity.getId());
				}
				else if (baseType instanceof ComplexType){
					Subtype subtype = new Subtype(nextId(),-1, entity.getId(),0);
					//Subtype subtype = new Subtype(nextId(),-1, entity.getId(),baseType.getSchema().getTargetNamespace(),0);
					// TODO: check the targetNamespace
					schemaElementsHS.put(this.compString(baseType.getSchema().getTargetNamespace(),passedType, baseType, subtype), subtype);
					processComplexType((ComplexType)baseType, subtype);
				}	
			}	
		}
		else {
			entity = (Entity)schemaElementsHS.get(this.compString(passedType.getSchema().getTargetNamespace(),passedType,null, entity));
		}
		
		// add Entity for complexType as child of passed containment or subtype 
		entity = (Entity)schemaElementsHS.get(this.compString(passedType.getSchema().getTargetNamespace(),passedType,null,entity));
		if (parent instanceof Containment && parent != null)
			((Containment)parent).setChildID(entity.getId());
		else if (parent instanceof Subtype && parent != null)
			((Subtype)parent).setParentID(entity.getId());
			
			
	} // end method	
			
	/**
	 * processGroup:  Processes a grouping of elements in a ComplexType. 
	 * The Elements in a ComplexType are contained in 1 or more Groups, 
	 * each of which is processed by this method.
	 * 
	 * @param group Element Group to be processed 
	 * @param parent Entity corresponding to complexType
	 */
	public void processGroup (Group group, Entity parent){
		
		// step through item in a group
		Enumeration<?> e = group.enumerate();
		while (e.hasMoreElements()) {
				
			Object obj = e.nextElement();
			
			// For WildCard, create containment child to "Any" domain
			if (obj instanceof Wildcard){
				Domain anyDomain = domainList.get(DEFAULT_NAMESPACE + " " + "Any");
				Containment containment = new Containment(nextId(),"Any", this.getDocumentation((Annotated)obj), parent.getId(), anyDomain.getId(), 0, 1, 0);
				//Containment containment = new Containment(nextId(),"", this.getDocumentation((Annotated)obj), parent.getId(), anyDomain.getId(), 0, 1, ((Wildcard) obj).getSchema().getTargetNamespace(),0);
				schemaElementsHS.put(this.compString(((Wildcard) obj).getSchema().getTargetNamespace(), parent, obj, containment), containment);
				
			}
			// process Group item
			else if (obj instanceof Group)
				processGroup((Group)obj, parent);	
			
			// process Element item
			else if (obj instanceof ElementDecl)  
				processElement((ElementDecl)obj, parent);
			
			else
				System.err.println("(E) XSDImporter:processGroup -- Encountered object named " + obj.toString() + " with unknown type " + obj.getClass());
							
		}
	} // end method

	/**
	 * processElement:  Creates an M3 Containment corresponding to the Element declaration in
	 * a ComplexType.  Parent of containment will be passed Entity, and the child will be either 
	 * M3 Entity for specified complexType or M3 Domain for specified simpleType.
	 * 
	 * @param elementDecl Element declaration in XSD ComplexType
	 * @param parent Entity corresponding to complexType containing elementDecl
	 */
	public void processElement(ElementDecl elementDecl, Entity parent)
	{
		// dereference xs:ref until we find actual element declarations
		while (elementDecl.isReference() && elementDecl.getReference() != null)
			elementDecl = elementDecl.getReference();
		
		// create Containment for Element  
		Containment containment = new Containment(nextId(),elementDecl.getName(),this.getDocumentation(elementDecl),((parent != null) ? parent.getId() : null),-1,elementDecl.getMinOccurs(),elementDecl.getMaxOccurs(),0);
		//Containment containment = new Containment(nextId(),elementDecl.getName(),this.getDocumentation(elementDecl),((parent != null) ? parent.getId() : null),-1,elementDecl.getMinOccurs(),elementDecl.getMaxOccurs(),elementDecl.getSchema().getTargetNamespace(),0);
		schemaElementsHS.put(this.compString(elementDecl.getSchema().getTargetNamespace(), parent, elementDecl, containment), containment);
		
		// TODO: process substitution group
//		Enumeration<?> substitutionGroup = elementDecl.getSubstitutionGroupMembers();
//		while (substitutionGroup.hasMoreElements()){
//			ElementDecl subElement = (ElementDecl)substitutionGroup.nextElement();
//			// TODO: process substitution group
//			System.err.println(elementDecl.getName() + "  has substitution element " + subElement.getName());
//		}

		// If the element type is 1) NULL, 2) SimpleType, or 3) Any type THEN process as SimpleType
		// Otherwise, process as ComplexType
		XMLType childElementType = elementDecl.getType();
				
		if ((childElementType == null) || (childElementType instanceof SimpleType) || (childElementType instanceof AnyType)) 				
			processSimpleType(childElementType, containment);
	
		else if (childElementType instanceof ComplexType)
			processComplexType((ComplexType)childElementType,containment);

		else 
			System.err.println("(E) XSDImporter:processElement -- Encountered object named " 
					+ elementDecl.getName() + " with unknown type " 
					+  ((childElementType == null)? null : childElementType.getClass()));
		
		
	} // end method



	/**
	 * getDocumentation: Get the documentation associated with specified element
	 * @param element element to get documentation about
	 * @return The documentation associated with a specific element
	 */
	private String getDocumentation(Annotated element) {
		
		StringBuffer documentation = new StringBuffer("");
		documentation.append(appendDocumentation(element));
		return documentation.toString();
	}

	/**
	 * appendDocumentation: Get the documentation associated with specified type
	 * @param type type to get documentation about
	 * @return The documentation associated with a specific element
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

		Domain domain = new Domain(nextId(), ANY, "The Any wildcard domain", 0);
		schemaElementsHS.put(this.compString(DEFAULT_NAMESPACE,null,null,domain), domain);
		domainList.put(DEFAULT_NAMESPACE + " " + ANY, domain);

		domain = new Domain(nextId(), INTEGER,"The Integer domain", 0);
		schemaElementsHS.put(this.compString(DEFAULT_NAMESPACE,null,null,domain), domain);
		domainList.put(DEFAULT_NAMESPACE + " " + INTEGER, domain);
		
		domain = new Domain(nextId(), REAL,"The Real domain", 0);
		schemaElementsHS.put(this.compString(DEFAULT_NAMESPACE,null,null,domain), domain);
		domainList.put(DEFAULT_NAMESPACE + " " + REAL, domain);
		
		domain = new Domain(nextId(), STRING,"The String domain", 0);
		schemaElementsHS.put(this.compString(DEFAULT_NAMESPACE,null,null,domain), domain);
		domainList.put(DEFAULT_NAMESPACE + " " + STRING, domain);
		
		domain = new Domain(nextId(), "string","The string domain", 0);
		schemaElementsHS.put(this.compString(DEFAULT_NAMESPACE,null,null,domain), domain);
		domainList.put(DEFAULT_NAMESPACE + " " + "string", domain);
		
		domain = new Domain(nextId(), DATETIME,"The DateTime domain", 0);
		schemaElementsHS.put(this.compString(DEFAULT_NAMESPACE,null,null,domain), domain);
		domainList.put(DEFAULT_NAMESPACE + " " + DATETIME, domain);
		
		domain = new Domain(nextId(), BOOLEAN,"The Boolean domain", 0);
		schemaElementsHS.put(this.compString(DEFAULT_NAMESPACE,null,null,domain), domain);
		domainList.put(DEFAULT_NAMESPACE + " " + BOOLEAN, domain);
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
	public String compString(String namespace, Object parent, Object child, SchemaElement o) {
		String retVal = null;

		/** Entity: NAME, DESC */
		if (o instanceof Entity) {
			if (((Entity)o).getName().equals(""))
				retVal = new String(((Entity) o).getId() + ", ENTITY ");
			 else 
				retVal = new String(((Entity) o).getName() + ", ENTITY ");
		
		/** Containment: NAME, DESC, PARENT_ID, CHILD_ID */
		} else if (o instanceof Containment) {
			retVal = new String(((Containment) o).getName() + " , "
					+ ((Containment) o).getParentID() + " , "
					+ ((Containment) o).getChildID() + ", CONTAINMENT ");
		
		/** Relationship: NAME, DESC, LEFT_ID, RIGHT_ID */ 	
		} else if (o instanceof Relationship){
			retVal = new String(((Relationship) o).getName() + " , "
					+ ((Relationship) o).getLeftID() + " , "
					+ ((Relationship) o).getRightID() + ", RELATIONSHIP ");
		
		/** Attribute: NAME, DESC, DOMAIN_ID, ENTITY_ID */
		} else if (o instanceof Attribute) {
			retVal = new String(((Attribute) o).getName() + " , "
					+ ((Attribute) o).getDomainID() + " , "
					+ ((Attribute) o).getEntityID() + ", ATTRIBUTE ");
		
		/** Domain: NAME  **/
		} else if (o instanceof Domain) {
			retVal = new String(((Domain) o).getName() + " DOMAIN ");
		
		/** DomainValue: NAME, DESC  **/
		} else if (o instanceof DomainValue) {
			retVal = new String(((DomainValue) o).getDomainID() + " , "
					+ ((DomainValue) o).getName() + ", DOMAIN VALUE ");
		
		/** Subtype: PARENT_ID, CHILD_ID  **/
		} else if (o instanceof Subtype) {
			retVal = new String(((Subtype) o).getParentID() + " , "
					+ ((Subtype) o).getChildID() + " , " + " SUBTYPE ");
		} else {
			retVal = new String();
		}
		
		// add the hashcodes associated with the parent / child objects to compString
		if (parent != null) retVal += " " + parent.hashCode();
		else retVal += " null";
		
		if (child != null) retVal += " " + child.hashCode();
		else retVal += " null";
		
		retVal += " " + namespace;
		
		return retVal;
	} // end method compString()
	
} // end class