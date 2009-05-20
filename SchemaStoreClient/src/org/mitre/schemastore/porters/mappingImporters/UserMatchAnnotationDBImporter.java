// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.mappingImporters;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Calendar;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;
import org.mitre.schemastore.porters.ImporterException;



public class UserMatchAnnotationDBImporter extends MappingImporter {
	Connection conn = null; 
	Schema schema1;
	Schema schema2;
	
	/** Returns the importer URI file types */
	public ArrayList<String> getFileTypes() {
		ArrayList<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".mdb");
		return fileTypes;
	}
	
	/** Initializes the importer */
	protected void initialize() throws ImporterException {
		String dataSourceName = "mdbTEST";
		try {
			schema1 = new Schema(); //client.getSchema(1);
			schema1.setName("HSIP");
			schema2 = new Schema(); //client.getSchema(2);
			schema2.setName("IPT");
		}
		catch(Exception e) { 
		    System.err.println ("Error getting schemas" + dataSourceName); 
		    e.printStackTrace(); 
		}		

		try {
			//  connect to MS Access database
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String dbURL = "jdbc:odbc:" + dataSourceName;
	        conn = DriverManager.getConnection(dbURL, "","");
	        System.out.println ("Database connection established."); 
		}
	//fixme: Should I be throwing this exception instead?  Ask Chris.
		catch(Exception e) { 
		    System.err.println ("Error with database" + dataSourceName); 
		    e.printStackTrace(); 
		}
	}
	
	/** Returns the importer URI type */
	public Integer getURIType() {
		//fixme: Chris?
		return URI;
	}
	
	/** Returns the schemas from the specified URI */
	protected ArrayList<Schema> getSchemas() throws ImporterException {
		ArrayList<Schema> schemas = new ArrayList<Schema>();
		//Fixme: hardcoded
		try {
			
			//Fixme: delete
			for (Schema s: client.getSchemas()) {
//				System.out.println("s: " + s.getId());
			}
			
			schemas.add(schema1);
			schemas.add(schema2);
		}
		
		//fixme: Should I be throwing this exception instead?  Ask Chris.
		catch (IOException e) {
		    System.err.println ("Error adding schema"); 
		    e.printStackTrace(); 
			
		}
		return schemas;
	}
	
	/** Returns the mapping cells from the specified URI */
	protected ArrayList<MappingCell> getMappingCells() throws ImporterException {
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		HierarchicalGraph hg1 = null;
		HierarchicalGraph hg2 = null;
		
		try {
			//schemaIDs contains schemaIDs of the correct schemas in the correct order
			hg1 = new HierarchicalGraph(client.getGraph(schemaIDs.get(0)), null);
			hg2 = new HierarchicalGraph(client.getGraph(schemaIDs.get(1)), null);
		}
		catch (Exception e) {
		    System.err.println ("Error getting graph"); 
		    e.printStackTrace(); 			
		}
		
		//fixme: throw exception?
		try {
			Statement s = conn.createStatement();
			s.execute("select * from HSIPMIPT");
			
			ResultSet rs = s.getResultSet();

			
			while(rs.next()) {
				String notes = rs.getString("MPNotes");
				String transform = rs.getString("Transform");
				
				
				// Path is stored in DB as "#parent#child#child"
				// Chop the first #
				String nodePath1 = rs.getString("HSIP-Path").substring(1);
				String nodePath2 = rs.getString("IPT-Path").substring(1);
				
				ArrayList<String> path1 = new ArrayList<String>(Arrays.asList(nodePath1.split("#")));
				ArrayList<String> path2 = new ArrayList<String>(Arrays.asList(nodePath2.split("#")));

				ArrayList<Integer> pathIds1 = hg1.getPathIDs(path1);
				ArrayList<Integer> pathIds2 = hg2.getPathIDs(path2);
				// path array lists have only one item since paths are unique in our schemas
				int element1 = pathIds1.get(0);
				int element2 = pathIds2.get(0);
				
				//fixme: delete
				//notes = "TESTING JOEL";
//System.out.println("Node1: " + node1);
			
				MappingCell cell = new MappingCell(null, 
						null,
						element1,
						element2,
						1.0,
						"",
						Calendar.getInstance().getTime(),
						transform,
						notes,
						true);
//fixme
//				cell.setElement1(1);
//				cell.setElement2(1);
//				cell.setNotes(notes);
//				cell.setTransform(transform);
//				// We aren't importing the date but OpenII requires one
//				cell.setModificationDate(Calendar.getInstance().getTime());
				mappingCells.add(cell);
			}

			
			s.close();
			
		}
		catch (SQLException e) {
		    System.err.println ("Error reading DB"); 
		    e.printStackTrace(); 			
		}

		return mappingCells;
	}


	/** Returns the importer description */
	public String getDescription(){
		return "Custom mapping importer from DB for HSIP project";
	}	
            
	/** Returns the importer name */
	public String getName() { return "User Match Annotation DB Importer"; }

//	/** Returns the importer description */
//    public String getDescription()  { return "This imports schemas from User Match Annotation DB"; }
//
//	/** Returns the importer URI type */
//	public Integer getURIType() { return URI; }
//
//	/** Initializes the importer for the specified URI */
//	protected void initialize() throws ImporterException {
//		try {
//			//  connect to MS Access database
//	        conn = DriverManager.getConnection("jdbc:odbc:"+uri,"Admin",null); 
//	        System.out.println ("Database connection established."); 
//	        _entities = new ArrayList<Entity>();
//			_attributes = new ArrayList<Attribute>();
//			_domains = new ArrayList<Domain>();
//			_enumerants = new ArrayList<DomainValue>();
//		}
//		catch(Exception e) { 
//			// If anything goes wrong, alert on the command line.
//		    System.err.println ("Cannot connect to database server!"); 
//		    e.printStackTrace(); 
//			//throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); 
//	    }		
//	}
//
//	/** Returns the list of schemas which this schema extends */
//	protected ArrayList<Integer> getExtendedSchemaIDs() throws ImporterException { 
//		return new ArrayList<Integer>(); 
//    }
//
//	/** Returns the schema elements from the specified URI */
//	public ArrayList<SchemaElement> getSchemaElements() throws ImporterException {	
//		generate();
//		return generateSchemaElementList();
//	}
//	
//	protected void generate() {
//      try {
//    	Statement selectEntities = null;
//		selectEntities = conn.createStatement();
//        // Set of Features, with Package associations (2 levels deep).
//        String entitySQL = "SELECT t.tableName AS tableName, g.point as t_P, "
//        	+ " g.curve as t_C, t.gdbTable_PK AS FTID, g.surface as t_S, ts.note AS entityNote, "
//            + " ts.definition AS definition, ts.description AS description, "
//            + " ts.itemIdentifier_PK AS itemIdentifier_PK "
//            + " FROM T_Table t, T_TableSpecification ts, T_FeatureGeometry g "
//            + " WHERE t.itemIdentifier_FK=ts.itemIdentifier_PK AND g.itemIdentifier_FK=ts.itemIdentifier_PK "
//            + " ORDER BY 1 ";
//        ResultSet entities=selectEntities.executeQuery(entitySQL); 
//
//        Entity tblEntity;      
//        while(entities.next()) {
//        	String tblName = entities.getString("tableName").toLowerCase(); // note these match the columns in the  
//        	tblName = firstLetterCapitalize(tblName);   //Make nicer looking with 1st-cap.        	
//            String definition = entities.getString("definition");
//            definition = concatNonNullFields(definition, entities.getString("description"), " [desc] ");
//            definition = concatNonNullFields(definition, entities.getString("entityNote"), " [note] ");
//            
//            String FTID = entities.getString("FTID");  // key to get attributes.
//    		// create an entity for a table
//            tblEntity = new Entity(nextId(), tblName, definition, 0);   			
//  			_entities.add(tblEntity);
//
//    		Statement selectAttributes = null;
//            selectAttributes = conn.createStatement();
//            // Find all attributes belonging to the feature type
//            String attributeSQL = "SELECT f.name AS attName, fs.definition AS definition, "
//            	+ " fs.description AS description, fs.datatypeNsgAlphaCode AS datatypeNsgAlphaCode, "
//                + " fs.note AS attNote, fs.structureSpecification AS structureSpecification, "
//                + " fs.rangeMinimum AS rMin, fs.rangeMaximum AS rMax, fs.generalDatatype as generalDatatype, "
//                + " f.gdbField_PK as DTID, f.infoAttID_FK as uniqueAttribute, "
//                + " fs.measure AS measure,  fs.length AS attLength, fs.lexical AS lexical, "
//                + " f.qualifiedPathName AS qualifiedPathName, fs.validRange AS validRange "
//                + " FROM T_Field f, T_FieldSpecification fs WHERE f.gdbTable_FK='"+ FTID + "'"
//                + " AND f.itemIdentifier_FK=fs.itemIdentifier_PK ORDER BY f.name ";
//            ResultSet attributes=null; 
//            attributes=selectAttributes.executeQuery(attributeSQL); 
//            Attribute attribute;            
//            int a=0;
//            Hashtable<String, Integer> datatypeIDs = new Hashtable<String, Integer>();
//            while(attributes.next()) {              	
//            	String attName= attributes.getString("attName");           	
//            	String qualifiedPathName = attributes.getString("qualifiedPathName");
//            	attName= concatNonNullFields(attName, qualifiedPathName, " : ");
//            	String attDefinition = attributes.getString("definition");
//            	attDefinition = concatNonNullFields(attDefinition, attributes.getString("description"), " [desc] ");
//            	attDefinition = concatNonNullFields(attDefinition, attributes.getString("attNote"), " [note] ");
//            	String generalDatatype = attributes.getString("generalDatatype");
//            	attDefinition = concatNonNullFields(attDefinition, generalDatatype, " [type] ");
//            	String lexical = attributes.getString("lexical");
//                if (lexical.equals("1")) { lexical ="Lexical"; }
//                else { lexical="Non-lexical"; }
//                                               
//                String attLength = attributes.getString("attLength");
//                if ((attLength==null || attLength.equals("null") || attLength.equals(""))) { 
//                	attLength="Unlimited";
//                }                
//                if (!(generalDatatype.equals("Integer") || generalDatatype.equals("Boolean") || generalDatatype.equals("Real"))) {
//                	attDefinition = concatNonNullFields(attDefinition, lexical, " [lex] ");
//                	attDefinition = concatNonNullFields(attDefinition, attLength, " [len] ");
//                }
//                attDefinition = concatNonNullFields(attDefinition, attributes.getString("structureSpecification"), " [struc] ");
//                String measure = attributes.getString("measure");
//            	
//            	String validRange = attributes.getString("validRange");
//            	String rMin = attributes.getString("rMin");
//                String rMax = attributes.getString("rMax");
//            	
//            	if (!generalDatatype.equals("Text")) {
//            		attDefinition = concatNonNullFields(attDefinition, measure, " [UoM] ");
//            		attDefinition = concatNonNullFields(attDefinition, validRange, " [range] ");
//            		attDefinition = concatNonNullFields(attDefinition, rMin, " [min] ");
//            		attDefinition = concatNonNullFields(attDefinition, rMax, " [max] ");    
//            	}
//                
//                String dtID = attributes.getString("DTID"); // used to key on enumerants
//                String datatype = attributes.getString("datatypeNsgAlphaCode");
//                String uniqueAttribute = attributes.getString("uniqueAttribute");
//                
//
//                String domainType = datatype;
//                String ignoreEnumerants="no";
//                
//                if (generalDatatype.equals("Enumeration")) {
//                	if (datatype.equals("Boolean")) { 
//                		ignoreEnumerants="yes";
//                		uniqueAttribute=datatype;
//                	}
//                }
//                else if (generalDatatype.equals("Integer")||generalDatatype.equals("Real")) {
//                	domainType = concatNonNullFields(domainType, measure, " : ");
//                	domainType = concatNonNullFields(domainType, validRange, " ");
//                	if (!((concatNonNullFields("",rMin,"")+concatNonNullFields("",rMax,"")).equals(""))) {
//                		domainType += " <";
//                		domainType = concatNonNullFields(domainType, rMin, "");
//                		if (!((concatNonNullFields("",rMin,"")).equals(""))) {
//                			domainType = concatNonNullFields(domainType, rMax, ", ");
//                		}
//                		else { domainType = concatNonNullFields(domainType, rMax, ""); }
//                		domainType += ">";
//                	}
//                }
//                else {
//                	domainType+= " : " +lexical;
//                	
//                	domainType+= " : " +attLength;
//                	if (!((generalDatatype.equals("Text")||generalDatatype.equals("Complex")))){
//                		domainType += " : ERROR";
//                	}
//                }                        	
//            	
//                if (! datatypeIDs.containsKey(uniqueAttribute)) {
//                	Domain domain = new Domain(nextId(), domainType, null, 0);
//        			_domains.add(domain);
//                	datatypeIDs.put(uniqueAttribute, domain.getId());
//                }         
//                else { ignoreEnumerants="yes"; }
//            	
//            	// create an attribute
//        		if (attName.length() > 0 ) {   
//      				a++;
//       				attribute = new Attribute(nextId(), attName, attDefinition, tblEntity.getId(), 
//       					datatypeIDs.get(uniqueAttribute), null, null, false, 0);
//       				_attributes.add(attribute);
//        		}
//        		
//        		if (ignoreEnumerants.equals("no")) { 
//        			Statement selectEnumerants = null;
//        			selectEnumerants = conn.createStatement();
//        			// Find any enumerants to this attribute (most will have none).
//        			String enumerantSQL = "SELECT cs.name AS enumName, cs.definition AS definition, "
//        				+ " cs.description AS description, cs.note AS enumNote "
//        				+ " FROM T_CodedValue c, T_CodedValueSpecification cs  WHERE " 
//        				+ " c.itemIdentifier_FK=cs.itemIdentifier_PK AND c.gdbField_FK='" + dtID + "'" 
//        				+ " ORDER BY cs.name";
//        			ResultSet enumerants=null; 
//        			enumerants=selectEnumerants.executeQuery(enumerantSQL); 
//        			while(enumerants.next()) {                	
//        				String enumName= enumerants.getString("enumName");
//        				String enumDefinition = enumerants.getString("definition");
//        				enumDefinition = concatNonNullFields(enumDefinition, enumerants.getString("description"), " [desc] ");
//        				enumDefinition = concatNonNullFields(enumDefinition, enumerants.getString("enumNote"), " [note] ");                    
//                    
//        				DomainValue enumerant = new DomainValue(nextId(), enumName, enumDefinition,
//                    		datatypeIDs.get(uniqueAttribute), 0);
//        				_enumerants.add(enumerant);
//        			}
//        			selectEnumerants.close();        		
//        		} 
//            } // while attributes
//            selectAttributes.close();
//        } // while Entities
//		selectEntities.close();
//    	if (conn != null) { 
//		    try { 
//		        conn.close (); 
//		        System.out.println ("Schema Elements generated.  Database connection closed."); 
//		    } 
//		    catch (Exception e) { /* ignore close errors */ } 
//		}
//      }
//      catch (SQLException e) { e.printStackTrace(); }
//	}
//
//	
//	//  Simple function to make all caps data easier on the eyes.
//	public static String firstLetterCapitalize(String line){
//	        char[] charArray = line.toCharArray();
//	        int linLen = line.length();
//	        charArray[0] = Character.toUpperCase(charArray[0]);
//	        for (int i=1;i<linLen;i++) {
//	            if (line.substring(i-1,i).equals("_") ||line.substring(i-1,i).equals(" ")) { charArray[i] = Character.toUpperCase(charArray[i]); }
//	        }
//		return new String(charArray);
//	} 
//	
//	public String concatNonNullFields(String description, String field, String joinText) {
//		if (!(field==null || field.equals("null") || field.equals(""))) { 
//			description += joinText + field;
//        }
//		return description;
//	}
//	
//	protected ArrayList<SchemaElement> generateSchemaElementList() {
//		ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();
//		int i = 0; // just counting elements here.
//		for (int j=0; j<_entities.size(); j++) { schemaElements.add(_entities.get(j)); i++;	}
//		for (int j=0; j<_domains.size(); j++) { schemaElements.add(_domains.get(j)); i++; }
//		for (int j=0; j<_attributes.size(); j++) { schemaElements.add(_attributes.get(j)); i++; }
//		for (int j=0; j<_enumerants.size(); j++) { schemaElements.add(_enumerants.get(j)); i++;	}
//		//System.out.println(i+ " elements.");
//		return schemaElements;
//	}
}