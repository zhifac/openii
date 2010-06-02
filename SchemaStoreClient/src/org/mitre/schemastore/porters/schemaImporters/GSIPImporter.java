// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.schemaImporters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.ImporterException;

/**
 * DOCUMENT ME!
 *
 * @author piekut
 * @version $Revision: 1.0 $
 */
public class GSIPImporter extends SchemaImporter {
	private ArrayList<Entity> _entities;
	private ArrayList<Attribute> _attributes;
	private ArrayList<Domain> _domains;
	private ArrayList<DomainValue> _enumerants;
	private ArrayList<Containment> _containments;
	private ArrayList<Relationship> _relationships;
	private Boolean IATStripIETName=false;
	private String IATStripNameSeparator="";
	private Boolean IATStripNameSpaces=false;
	private HashMap<Integer, Entity> EntityKey;
	private HashMap<Integer, Relationship> oppositeRelationship;
	Connection conn = null; 
            
	/** Returns the importer name */
	public String getName() { return "GSIP Importer"; }

	/** Returns the importer description */
    public String getDescription()  { return "This imports schemas from the NAS-specific Access MDB"; }

	/** Returns the importer URI type */
	public URIType getURIType() { return URIType.FILE; }
	
	/** Returns the importer URI file types */
	public ArrayList<String> getFileTypes()
	{
		ArrayList<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".mdb");
		return fileTypes;
	}
	
	/** Initializes the importer for the specified URI */
	protected void initialize() throws ImporterException {
		String filename = "";
		try {
			//  connect to MS Access database
		
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			//uri comes in as file:/foo, needs to be foo
			filename = uri.toString().substring(6);
			
			//uris replace spaces with %20s.  Fix that.
			filename = filename.replaceAll("%20"," ");
			
System.out.println(filename);
			String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
            database+= filename + ";DriverID=22;READONLY=true}"; // add on to the end 
            // now we can get the connection from the DriverManager
            conn = DriverManager.getConnection( database ,"",""); 
            //end file method			
			
			System.out.println ("Database connection established."); 
	        _entities = new ArrayList<Entity>();
			_attributes = new ArrayList<Attribute>();
			_domains = new ArrayList<Domain>();
			_enumerants = new ArrayList<DomainValue>();
			_containments = new ArrayList<Containment>();
			_relationships = new ArrayList<Relationship>();
			EntityKey = new HashMap<Integer, Entity>();
			oppositeRelationship = new HashMap<Integer, Relationship>();
		}
		catch(Exception e) { 
			// If anything goes wrong, alert on the command line.
		    System.err.println ("Cannot connect to database server " + filename + "!"); 
		    e.printStackTrace(); 
			//throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); 
	    }		
	}

	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> generateSchemaElements() throws ImporterException {	
		generate();
		return generateSchemaElementList();
	}
	
	protected void generate() {
		// Mapping of gdbTable_PK to SchemaElement id
      try {
    	Entity tblEntity = null;;      
    	ResultSet entities = null;  
    	Statement selectEntities = null;
		
    	selectEntities = conn.createStatement();
		
		// Create nodes for abstract classes in T_AbstractTable -- added by Joel
		String schemaSQL = "SELECT P_IATStripIETName, P_IATStripNameSeparator, P_IATStripNameSpaces, P_harmonyName "
			+" FROM T_EntityCatalogue ";
		ResultSet schema=selectEntities.executeQuery(schemaSQL); 		
		schema.next();
		IATStripIETName = schema.getBoolean("P_IATStripIETName");
		IATStripNameSeparator = schema.getString("P_IATStripNameSeparator");
       	IATStripNameSpaces = schema.getBoolean("P_IATStripNameSpaces");
       	if (IATStripIETName && IATStripNameSpaces)  { IATStripNameSeparator = " " + IATStripNameSeparator + " "; }
       	Boolean useName = true; //schema.getBoolean("P_harmonyName");
       	String nameCol = "";
       	if (useName) { nameCol="name"; }
       	else { nameCol="nsgAlphaCode"; }
				
        // Set of Entities
        String entitySQL = "SELECT t."+nameCol+" AS tableName, t.note AS entityNote, "
            + " t.definition AS definition, t.description AS description, "
            + " t.itemIdentifier_PK AS itemIdentifier_PK, t.itemIdentifier_PK AS ETID, t.dfddAlphaCode AS key "
            + " FROM T_EntityType t ORDER BY 1 ";
        entities=selectEntities.executeQuery(entitySQL); 

        while(entities.next()) { 
        	tblEntity = createEntity(entities);  			
  			_entities.add(tblEntity);
        	Integer ETID = entities.getInt("ETID");  // key to get attributes.
        	EntityKey.put(ETID, tblEntity);
        	addAttributes(ETID, ETID);  // This call does the majority of the work, including inheritance.
        } // while Entities
		selectEntities.close();		
		addContainments();
		addRelationships();
		
    	if (conn != null) { 
		    try { 
		        conn.close (); 
		        System.out.println ("Schema Elements generated.  Database connection closed."); 
		    } 
		    catch (Exception e) { /* ignore close errors */ } 
		}
      }
      catch (SQLException e) { e.printStackTrace(); }
	}

	
	//  Simple function to make all caps data easier on the eyes.
	public static String firstLetterCapitalize(String line){
	        char[] charArray = line.toCharArray();
	        int linLen = line.length();
	        charArray[0] = Character.toUpperCase(charArray[0]);
	        for (int i=1;i<linLen;i++) {
	            if (line.substring(i-1,i).equals("_") ||line.substring(i-1,i).equals(" ")) { charArray[i] = Character.toUpperCase(charArray[i]); }
	        }
		return new String(charArray);
	} 
	
	public String concatNonNullFields(String description, String field, String joinText) {
		if (!(field==null || field.equals("null") || field.equals(""))) { 
			description += joinText + field;
        }
		return description;
	}
	
	protected ArrayList<SchemaElement> generateSchemaElementList() {
		ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();
		int i = 0; // just counting elements here.
		for (int j=0; j<_entities.size(); j++) { schemaElements.add(_entities.get(j)); i++;	}
		for (int j=0; j<_domains.size(); j++) { schemaElements.add(_domains.get(j)); i++; }
		for (int j=0; j<_attributes.size(); j++) { schemaElements.add(_attributes.get(j)); i++; }
		for (int j=0; j<_enumerants.size(); j++) { schemaElements.add(_enumerants.get(j)); i++;	}
		for (int j=0; j<_containments.size(); j++) { schemaElements.add(_containments.get(j)); i++;	}
		for (int j=0; j<_relationships.size(); j++) { schemaElements.add(_relationships.get(j)); i++; }
		return schemaElements;
	}
	
	//given a ResultSet (iterated elsewhere) with fields "tableName, definition, description, entityNote", return new Entity
	private Entity createEntity(ResultSet entities){
		String tblName = null;
		String definition = null;
		try {
			tblName = entities.getString("tableName");  // note these match the columns in the  
			if (tblName.substring(0, 1).equals(tblName.substring(0, 1).toLowerCase())) {
				tblName = tblName.toLowerCase();
				tblName = firstLetterCapitalize(tblName);
			}
			definition = entities.getString("definition");
			definition = concatNonNullFields(definition, entities.getString("description"), " [desc] ");
			definition = concatNonNullFields(definition, entities.getString("entityNote"), " [note] ");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		int id = nextId();
		Entity e = new Entity(id, tblName, definition, 0);

		return e;
	}
	
	private void addAttributes(Integer ancestorID, Integer entityID) {  
		//  adds attributes for an entity, or implements inheritance of an ancestor's attributes.
		//  if ancestorID=entityID, then we're retrieving attributes for the entity itself.
		try { 
			Statement selectAttributes = null;
			selectAttributes = conn.createStatement();
			// Find all attributes belonging to the feature type
			String attributeSQL = "SELECT ia.name AS attName, ia.definition AS definition, "
				+ " ia.description AS description, ia.valueType AS datatypeNsgAlphaCode, ia.itemIdentifier_PK as IAID, "
				+ " ia.note AS attNote, ia.structureSpecification AS structureSpecification, "
				+ " ia.rangeMinimum AS rMin, ia.rangeMaximum AS rMax, ia.valuePrimitive as generalDatatype, "
				+ " ia.itemIdentifier_PK as uniqueAttribute, ia.valueMeasurementUnit AS measure, "
				+ " ia.characterLength AS attLength, ia.lexical AS lexical "
				+ " FROM T_EntityAttribute ia WHERE ia.entityType_FK="+ ancestorID ;
			ResultSet attributes=null;
			attributes=selectAttributes.executeQuery(attributeSQL); 
			Attribute attribute;            
			int a=0;
			Hashtable<String, Integer> datatypeIDs = new Hashtable<String, Integer>();
			while(attributes.next()) {    	
				String IAID = attributes.getString("IAID");
				String attName= attributes.getString("attName");   
				//String dtName = attributes.getString("dtName");  
				if (IATStripIETName && attName.indexOf(IATStripNameSeparator)>-1) { // chop off the orig entity name that prepends.					
					attName = attName.substring(attName.indexOf(IATStripNameSeparator)+IATStripNameSeparator.length());  
				}
				String attDefinition = attributes.getString("definition");
				attDefinition = concatNonNullFields(attDefinition, attributes.getString("description"), " [desc] ");
				attDefinition = concatNonNullFields(attDefinition, attributes.getString("attNote"), " [note] ");
				String generalDatatype = attributes.getString("generalDatatype");
				attDefinition = concatNonNullFields(attDefinition, generalDatatype, " [type] ");
				String lexical = attributes.getString("lexical");
				if (lexical.equals("1")) { lexical ="Lexical"; }
				else { lexical="Non-lexical"; }
				
				String datatype = attributes.getString("datatypeNsgAlphaCode");
				String attLength = attributes.getString("attLength");
				if ((attLength==null || attLength.equals("null") || attLength.equals(""))) { 
					attLength="Unlimited";
				}                
				if (!(datatype.startsWith("Integer") 
						|| datatype.equals("Boolean") 
						|| datatype.startsWith("Real"))) {
					attDefinition = concatNonNullFields(attDefinition, lexical, " [lex] ");
					attDefinition = concatNonNullFields(attDefinition, attLength, " [len] ");
				}
				attDefinition = concatNonNullFields(attDefinition, attributes.getString("structureSpecification"), " [struc] ");
				String measure = attributes.getString("measure");
            
				//String validRange = attributes.getString("validRange");
				String rMin = attributes.getString("rMin");
				String rMax = attributes.getString("rMax");
            
				if (!generalDatatype.equals("String")) {
					attDefinition = concatNonNullFields(attDefinition, measure, " [UoM] ");
					//attDefinition = concatNonNullFields(attDefinition, validRange, " [range] ");
					attDefinition = concatNonNullFields(attDefinition, rMin, " [min] ");
					attDefinition = concatNonNullFields(attDefinition, rMax, " [max] ");    
				}
        	
				//String dtID = attributes.getString("DTID"); // used to key on enumerants
				
				String uniqueAttribute = attributes.getString("uniqueAttribute") + "_"+ entityID;

				String domainType = datatype;
				String ignoreEnumerants="yes";
            
				if (datatype.equals("Boolean")) { uniqueAttribute=datatype;	}
				else if (generalDatatype.equalsIgnoreCase("Enumeration")) { ignoreEnumerants="no"; } 
				else if (datatype.startsWith("Integer")||datatype.startsWith("Real")) {
					domainType = concatNonNullFields(domainType, measure, " : ");
					//domainType = concatNonNullFields(domainType, validRange, " ");
					if (!((concatNonNullFields("",rMin,"")+concatNonNullFields("",rMax,"")).equals(""))) {
						domainType += " &lt;";
						domainType = concatNonNullFields(domainType, rMin, "");
						if (!((concatNonNullFields("",rMin,"")).equals(""))) {
							domainType = concatNonNullFields(domainType, rMax, ", ");
						}
						else { domainType = concatNonNullFields(domainType, rMax, ""); }
						domainType += ">";
					}
				}
				else {
					domainType+= " : " +lexical;
            	
					domainType+= " : " +attLength;
					if (!(generalDatatype.equals("String")||generalDatatype.equals("Unspecified"))){
						domainType += " : ERROR";
					}
				}                        	
        	
				if (! datatypeIDs.containsKey(uniqueAttribute)) {
					Domain domain = new Domain(nextId(), domainType, null, 0);
					_domains.add(domain);
					datatypeIDs.put(uniqueAttribute, domain.getId());
				}         
				else { ignoreEnumerants="yes"; }  

				// create an attribute
				if (attName.length() > 0 ) {   
					a++;
					attribute = new Attribute(nextId(), attName, attDefinition, EntityKey.get(entityID).getId(), 
							datatypeIDs.get(uniqueAttribute), null, null, false, 0);
					_attributes.add(attribute);
				}
    		
				if (ignoreEnumerants.equals("no")) { 					
					Statement selectEnumerants = null;
					selectEnumerants = conn.createStatement();
					// Find any enumerants to this attribute (most will have none).
					String enumerantSQL = "SELECT dlv.name AS enumName, dlv.definition AS definition, "
						+ " dlv.description AS description, dlv.note AS enumNote "
						+ " FROM T_ListedValue dlv  WHERE " 
						+ " dlv.entityAttribute_FK =" + IAID + " ORDER BY dlv.name";
					ResultSet enumerants=null; 
					enumerants=selectEnumerants.executeQuery(enumerantSQL); 
					int rc = 0;
					while(enumerants.next()) {	
						rc++;
						String enumName= enumerants.getString("enumName");  
						String enumDefinition = enumerants.getString("definition");
						enumDefinition = concatNonNullFields(enumDefinition, enumerants.getString("description"), " [desc] ");
						enumDefinition = concatNonNullFields(enumDefinition, enumerants.getString("enumNote"), " [note] ");                    
                
						DomainValue enumerant = new DomainValue(nextId(), enumName, enumDefinition,
								datatypeIDs.get(uniqueAttribute), 0);
						_enumerants.add(enumerant);
					}
					selectEnumerants.close();        		
				}
			} // while attributes
			selectAttributes.close();  

			//  Now that the direct attributes are taken care of, 
			//  check if there are any inherited attributes to add. (recursive)
			Statement s = conn.createStatement();
			String sql = "SELECT entitySuperType_FK as superID FROM T_InheritanceRelation WHERE entitySubType_FK="+ancestorID+";";
			ResultSet rs = s.executeQuery(sql); 
			while(rs.next()) { 
				Integer superTypeID = rs.getInt("superID");
				addAttributes(superTypeID, entityID);
			}
			s.close(); 		
		}
       	catch (SQLException e) { e.printStackTrace(); }
	}
	
	private void addRelationships() {
		try {
			Statement s = conn.createStatement();
		
			String sql = "SELECT entityType_FK AS ETID, entityAssoc_FK AS EAID, valueMultiplicity, "
				 + " name as roleName, definition, description, note AS roleNote, isUnique, isOrdered, "
				 + " roleType FROM T_AssociationRole ORDER BY entityAssoc_FK;";
			ResultSet rs = s.executeQuery(sql); 
			
			int lastET = -1;  int lastEA=-1;  String lastValueMultiplicity = ""; String lastName = "";
			String lastDefinition="";  String lastRoleType="";
			while(rs.next()) { 
				int ETID = rs.getInt("ETID"); 
				int EAID = rs.getInt("EAID"); 
				String valueMultiplicity = rs.getString("valueMultiplicity");
				String name = rs.getString("roleName");
				String definition = rs.getString("definition");
				String description = rs.getString("description");
				Boolean isOrdered = rs.getBoolean("isOrdered");
				Boolean isUnique = rs.getBoolean("isUnique");
				String note = rs.getString("roleNote");
				String roleType = rs.getString("roleType");
				String append = " : ["+ valueMultiplicity +"]";
				if (isOrdered || isUnique) {
					append += " {";
					if (isOrdered) {  
						append+="ordered";
                        if (isUnique) { append+=", "; }
                    }
                    if (isUnique) { append+="unique"; }
                    append+="}";					
				}
				if (!roleType.equals("Ordinary")) { append+= " ("+roleType+")"; }
				definition = append+") "+ definition;
				definition = concatNonNullFields(definition, description, " [desc] ");
				definition = concatNonNullFields(definition, note, " [note] ");
				if (EAID==lastEA) {
					Integer[] mult1 = getMultiplicity(valueMultiplicity);
					Integer[] mult2 = getMultiplicity(lastValueMultiplicity);
					Integer key1 = EntityKey.get(ETID).getId();
					Integer key2 = EntityKey.get(lastET).getId();
					definition = "("+EntityKey.get(lastET).getName()+definition;
					lastDefinition = "("+EntityKey.get(ETID).getName()+ lastDefinition;
					
					Relationship r1 = new Relationship(nextId(), name, definition, key1, mult1[0], mult1[1], EntityKey.get(lastET).getId(), mult2[0], mult2[1], 0);					
					if (!lastRoleType.equals("Ordinary")) {
						Containment c = new Containment(nextId(), r1.getName(), r1.getDescription(), key1, r1.getId(), 0, 1, 0);
						_containments.add(c);
					}
					_relationships.add(r1);
					
					Relationship r2 = new Relationship(nextId(), lastName, lastDefinition, key2, mult2[0], mult2[1], EntityKey.get(ETID).getId(), mult1[0], mult1[1], 0);
					if (!roleType.equals("Ordinary")) {
						Containment c = new Containment(nextId(), r2.getName(), r2.getDescription(), key2, r2.getId(), 0, 1, 0);
						_containments.add(c);
					}
					_relationships.add(r2);
					
					//  Now that the direct associations are taken care of, 
					//  check if there are any inherited associations to add. (recursive)
					oppositeRelationship.put(lastET, r1);  
					oppositeRelationship.put(ETID, r2);
					inheritedRoles(r1,ETID, ETID);
					inheritedRoles(r2,lastET, lastET);					
				}
				lastET = ETID;  lastEA=EAID; lastValueMultiplicity = valueMultiplicity;  lastName = name;
				lastDefinition = definition;  lastRoleType=roleType;
			}			
		}
		catch (Exception e) {
			System.out.println("Error adding relationships.");
			e.printStackTrace();
		}	
	}
	
	private void inheritedRoles (Relationship r, Integer entityID, Integer origETID) {
		try {
			Statement s = conn.createStatement();
			String sql = "SELECT entitySubType_FK as subID FROM T_InheritanceRelation WHERE entitySuperType_FK="+entityID+";";
			ResultSet rs = s.executeQuery(sql); 
			while(rs.next()) {
				Integer subTypeID = rs.getInt("subID");				
				Relationship rNext = 
					new Relationship(nextId(), r.getName(), r.getDescription(), EntityKey.get(subTypeID).getId(), r.getLeftMin(), 
						r.getLeftMax(), r.getRightID(), r.getRightMin(), r.getRightMax(), 0);
				Relationship rOpp = oppositeRelationship.get(origETID);
				if (rOpp.getDescription().contains("(Aggregation)") || 
						rOpp.getDescription().contains("(Composition)")) {  
					Containment c = new 
						Containment(nextId(), r.getName(), r.getDescription(), EntityKey.get(subTypeID).getId(), rNext.getId(), 0, 1, 0);
					_containments.add(c);					
				}							
				_relationships.add(rNext);
				inheritedRoles(r, subTypeID, origETID);
			}
			s.close(); 	
		}
		catch (Exception e) {
			System.out.println("Error adding inherited relationships.");
			e.printStackTrace();
		}	
	}
	
	private Integer[] getMultiplicity(String longString) {	
		String[] values = longString.split("\\.\\.");
		
		Integer[] multArray = {0,1};
		if (values.length>1) { 
			multArray[0] =new Integer(values[0]);
			multArray[1] = values[1].equals("*") ? null : new Integer(values[1]);
		}
		return multArray;
	}
	
	private void addContainments() {
		try {
			Statement s = conn.createStatement();
			
			String sql = "SELECT entitySubType_FK, entitySuperType_FK FROM T_InheritanceRelation;";
			ResultSet rs = s.executeQuery(sql); 
			
			HashMap<Integer, String> eNames = new HashMap<Integer,String>();
			for (int j=0; j<_entities.size(); j++) {
				eNames.put(_entities.get(j).getId(), _entities.get(j).getName());
			}	
			while(rs.next()) { 
				int cID = rs.getInt("entitySubType_FK");     
				int pID = rs.getInt("entitySuperType_FK"); 
				Integer childID = EntityKey.get(cID).getId(); 
				Integer parentID = EntityKey.get(pID).getId(); 
				String childName = eNames.get(childID);
				String parentName= eNames.get(parentID);

				Containment c = new Containment(nextId(), "[" + parentName + "->" + childName + "]", "", parentID, childID, 0, 1, 0);
				_containments.add(c);
			}
		}
		catch (Exception e) {
			System.out.println("Error adding containments.");
			e.printStackTrace();
		}
	}
}