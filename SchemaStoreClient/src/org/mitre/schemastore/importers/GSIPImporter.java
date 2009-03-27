// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.importers;

import java.util.*;

import antlr.*;
import java.io.*;
import java.sql.*;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;

/**
 * DOCUMENT ME!
 *
 * @author piekut
 * @version $Revision: 1.0 $
 */
public class GSIPImporter extends Importer {
	private ArrayList<Entity> _entities;
	private ArrayList<Attribute> _attributes;
	private ArrayList<Domain> _domains;
	private ArrayList<DomainValue> _enumerants;
	Connection conn = null; 
    
    
    
	/** Returns the importer name */
	public String getName() { return "GSIP Importer"; }

	/** Returns the importer description */
    public String getDescription()  { return "This imports schemas from the NAS-specific Access MDB"; }

	/** Returns the importer URI type */
	public Integer getURIType() { return URI; }

	/** Initializes the importer for the specified URI */
	protected void initialize() throws ImporterException {
		try {
			//  connect to MS Access database
	        conn = DriverManager.getConnection("jdbc:odbc:"+uri,"Admin",null); 
	        System.out.println ("Database connection established"); 
	        _entities = new ArrayList<Entity>();
			_attributes = new ArrayList<Attribute>();
			_domains = new ArrayList<Domain>();
			_enumerants = new ArrayList<DomainValue>();
		}
		catch(Exception e) { 
			// If anything goes wrong, alert on the command line.
		    System.err.println ("Cannot connect to database server!"); 
		    e.printStackTrace(); 
			//throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); 
	    }		
	}

	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> getExtendedSchemaIDs() throws ImporterException { 
		return new ArrayList<Integer>(); 
    }

	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> getSchemaElements() throws ImporterException {	
		generate();
		return generateSchemaElementList();
	}
	
	protected void generate() {
      try {
    	Statement selectEntities = null;
		selectEntities = conn.createStatement();
        // Set of Features, with Package associations (2 levels deep).
        String entitySQL = "SELECT t.tableName AS tableName, g.point as t_P, "
        	+ " g.curve as t_C, t.gdbTable_PK AS FTID, g.surface as t_S, ts.note AS entityNote, "
            + " ts.definition AS definition, ts.description AS description, "
            + " ts.itemIdentifier_PK AS itemIdentifier_PK "
            + " FROM T_Table t, T_TableSpecification ts, T_FeatureGeometry g "
            + " WHERE t.itemIdentifier_FK=ts.itemIdentifier_PK AND g.itemIdentifier_FK=ts.itemIdentifier_PK "
            + " ORDER BY 1 ";
        ResultSet entities=selectEntities.executeQuery(entitySQL); 

        Entity tblEntity; String lastTableName="";       
        while(entities.next()) {
        	String tblName = entities.getString("tableName").toLowerCase(); // note these match the columns in the  
        	tblName = firstLetterCapitalize(tblName);   //Make nicer looking with 1st-cap.        	
            String definition = entities.getString("definition");
            definition = concatDescription(definition, entities.getString("description"));
            definition = concatDescription(definition, entities.getString("entityNote"));

            int P = entities.getInt("t_P");  // Does feature have Point geometry?
            int S = entities.getInt("t_S");  // Does feature have Surface geometry?
            int C = entities.getInt("t_C");  // Does feature have Curve geometry?
            //if (tblName.equals(lastTableName)) { continue; }
            
            int itemID = entities.getInt("itemIdentifier_PK");
            String FTID = entities.getString("FTID");  // key to get attributes.
    		// create an entity for a table
            tblEntity = new Entity(nextId(), tblName, definition, 0);   			
  			_entities.add(tblEntity);

    		Statement selectAttributes = null;
            selectAttributes = conn.createStatement();
            // Find all attributes belonging to the feature type
            String attributeSQL = "SELECT f.name AS attName, fs.definition AS definition, "
            	+ " fs.description AS description, fs.datatypeNsgAlphaCode AS datatypeNsgAlphaCode, "
                + " fs.note AS attNote, fs.structureSpecification AS structureSpecification, "
                + " fs.rangeMinimum AS rMin, fs.rangeMaximum AS rMax, "
                + " f.gdbField_PK as DTID, "
                + " fs.measure AS measure,  fs.length AS attLength, fs.lexical AS lexical, "
                + " f.qualifiedPathName AS qualifiedPathName, fs.validRange AS validRange "
                + " FROM T_Field f, T_FieldSpecification fs WHERE f.gdbTable_FK='"+ FTID + "'"
                + " AND f.itemIdentifier_FK=fs.itemIdentifier_PK ORDER BY f.name ";
            ResultSet attributes=null; 
            attributes=selectAttributes.executeQuery(attributeSQL); 
            Attribute attribute;            
            int a=0;
            Hashtable <String, Integer> datatypeIDs = new Hashtable<String, Integer>();
            while(attributes.next()) {              	
            	String attName= attributes.getString("attName");           	
            	String qualifiedPathName = attributes.getString("qualifiedPathName");
            	if (!(qualifiedPathName==null || qualifiedPathName.equals("null") || qualifiedPathName.equals(""))) { 
            		attName= qualifiedPathName + " : " + attName;
                }
            	String attDefinition = attributes.getString("definition");
            	attDefinition = concatDescription(attDefinition, attributes.getString("description"));
            	attDefinition = concatDescription(attDefinition, attributes.getString("attNote"));
            	attDefinition = concatDescription(attDefinition, attributes.getString("measure"));
            	attDefinition = concatDescription(attDefinition, attributes.getString("validRange"));
                attDefinition = concatDescription(attDefinition, attributes.getString("rMin"));
                attDefinition = concatDescription(attDefinition, attributes.getString("rMax"));
                attDefinition = concatDescription(attDefinition, attributes.getString("attLength"));
                String lexical = attributes.getString("lexical");
                if (lexical.equals("1")) { lexical = "True"; }
                else { lexical=null; }
                attDefinition = concatDescription(attDefinition, lexical);
                attDefinition = concatDescription(attDefinition, attributes.getString("structureSpecification"));
                
                String dtID = attributes.getString("DTID"); // used to key on enumerants
                String datatype = attributes.getString("datatypeNsgAlphaCode");

                String domainType = ANY;
                String booleanClause = "";  // This is an empty string, only to be non-null when datatype=Boolean
            	if (datatype.equals("Real")) { domainType = "Double"; }
            	else if (datatype.equals("Integer")) { domainType="Integer"; }
            	else if (datatype.equals("Text")) { domainType="String"; }
            	else if (datatype.equals("Boolean")) { domainType="Boolean"; booleanClause = " AND 1=2 "; }
                
                if (! datatypeIDs.containsKey(dtID)) {
                	Domain domain = new Domain(nextId(), domainType, null, 0);
        			_domains.add(domain);
                	datatypeIDs.put(dtID, domain.getId());
                }                 
            	
            	// create an attribute
        		if (attName.length() > 0 ) {   
      				a++;
       				attribute = new Attribute(nextId(), attName, attDefinition, tblEntity.getId(), 
       					datatypeIDs.get(dtID), null, null, false, 0);
       				_attributes.add(attribute);
        		}
        		
        		Statement selectEnumerants = null;
        		selectEnumerants = conn.createStatement();
                // Find any enumerants to this attribute (most will have none).
                String enumerantSQL = "SELECT cs.name AS enumName, cs.definition AS definition, "
                + " cs.description AS description, cs.note AS enumNote "
                + " FROM T_CodedValue c, T_CodedValueSpecification cs " 
                + " WHERE c.itemIdentifier_FK=cs.itemIdentifier_PK AND c.gdbField_FK='" + dtID + "'" + booleanClause
                + " ORDER BY cs.name";
                ResultSet enumerants=null; 
                enumerants=selectEnumerants.executeQuery(enumerantSQL); 
                while(enumerants.next()) {                	
                	String enumName= enumerants.getString("enumName");
                    String enumDefinition = enumerants.getString("definition");
                    enumDefinition = concatDescription(enumDefinition, enumerants.getString("description"));
                    enumDefinition = concatDescription(enumDefinition, enumerants.getString("enumNote"));                    
                    
                    DomainValue enumerant = new DomainValue(nextId(), enumName, enumDefinition,
                    		datatypeIDs.get(dtID), 0);
                    _enumerants.add(enumerant);
                }
                selectEnumerants.close();        		
            } // while attributes
            selectAttributes.close();
        } // while Entities
		selectEntities.close();
    	if (conn != null) { 
		    try { 
		        conn.close (); 
		        System.out.println ("Database connection terminated"); 
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
	
	public String concatDescription(String description, String field) {
		if (!(field==null || field.equals("null") || field.equals(""))) { 
			description += " " + field;
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
		//System.out.println(i+ " elements.");
		return schemaElements;
	}
}