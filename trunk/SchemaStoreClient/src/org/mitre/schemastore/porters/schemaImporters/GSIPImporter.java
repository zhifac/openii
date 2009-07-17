// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.schemaImporters;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Containment;
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
	Connection conn = null; 
            
//	/** Private class for creating a GSIP file filter */
//	private static class GSIPFileFilter extends FileFilter
//	{
//
//		/** Indicates if the file should be accepted */
//		public boolean accept(File file)
//		{
//			if(file.isDirectory()) return true;
//			if(file.toString().endsWith(".mdb")) return true;
//			return false;
//		}
//
//		/** Provides the exporter file description */
//		public String getDescription()
//			{ return "Microsoft Access Database (mdb)"; }
//	};
//
//	
//	
	
	/** Returns the importer name */
	public String getName() { return "GSIP Importer"; }

	/** Returns the importer description */
    public String getDescription()  { return "This imports schemas from the NAS-specific Access MDB"; }

	/** Returns the importer URI type */
	public Integer getURIType() { return FILE; }
	
	/** Returns the importer URI file types */
	public ArrayList<String> getFileTypes()
	{
		ArrayList<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".mdb");
		return fileTypes;
	}
	
	/** Initializes the importer for the specified URI */
	protected void initializeSchemaStructures() throws ImporterException {
		try {
			//  connect to MS Access database
//	        conn = DriverManager.getConnection("jdbc:odbc:"+uri,"Admin",null); 

			//file method
			// Initialize the file chooser
//			JFileChooser chooser = new JFileChooser();
//			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
//			chooser.setAcceptAllFileFilterUsed(false);
//			chooser.addChoosableFileFilter(new GSIPFileFilter());
//			
//			File file = chooser.getSelectedFile();
			
			
			
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            // set this to a MS Access DB you have on your machine
//            String filename = file.getPath();//"c:/hsip/test.mdb";

			//uri comes in as file:/foo, needs to be foo
			String filename = uri.toString().substring(6);
			
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
		}
		catch(Exception e) { 
			// If anything goes wrong, alert on the command line.
		    System.err.println ("Cannot connect to database server!"); 
		    e.printStackTrace(); 
			//throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); 
	    }		
	}

	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> generateExtendedSchemaIDs() throws ImporterException { 
		return new ArrayList<Integer>(); 
    }

	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> generateSchemaElements() throws ImporterException {	
		generate();
		return generateSchemaElementList();
	}
	
	protected void generate() {
		// Mapping of gdbTable_PK to SchemaElement id
		HashMap<String, Integer> EntityKey = new HashMap<String, Integer>();
      try {
    	Entity tblEntity = null;;      
    	ResultSet entities = null;  
    	Statement selectEntities = null;
		
    	selectEntities = conn.createStatement();
		
		// Create nodes for abstract classes in T_AbstractTable -- added by Joel
		String entitySQL = "SELECT name AS tableName, definition, description, "
			+ " note AS entityNote, gdbTable_PK AS [key] FROM T_AbstractTable;";
		entities=selectEntities.executeQuery(entitySQL); 

		while(entities.next()) {
        	tblEntity = createEntity(entities, EntityKey);  			
			_entities.add(tblEntity);
		}
		
		
        // Set of Features, with Package associations (2 levels deep). -- original, by Sarah, Joel added "key"
        entitySQL = "SELECT t.tableName AS tableName, g.point as t_P, "
        	+ " g.curve as t_C, t.gdbTable_PK AS FTID, g.surface as t_S, ts.note AS entityNote, "
            + " ts.definition AS definition, ts.description AS description, "
            + " ts.itemIdentifier_PK AS itemIdentifier_PK, t.gdbTable_PK AS key "
            + " FROM T_Table t, T_TableSpecification ts, T_FeatureGeometry g "
            + " WHERE t.itemIdentifier_FK=ts.itemIdentifier_PK AND g.itemIdentifier_FK=ts.itemIdentifier_PK "
            + " ORDER BY 1 ";
        entities=selectEntities.executeQuery(entitySQL); 

        while(entities.next()) {
        	tblEntity = createEntity(entities, EntityKey);  			
  			_entities.add(tblEntity);
        	String FTID = entities.getString("FTID");  // key to get attributes.
    		
    		Statement selectAttributes = null;
            selectAttributes = conn.createStatement();
            // Find all attributes belonging to the feature type
            String attributeSQL = "SELECT f.name AS attName, fs.definition AS definition, "
            	+ " fs.description AS description, fs.datatypeNsgAlphaCode AS datatypeNsgAlphaCode, "
                + " fs.note AS attNote, fs.structureSpecification AS structureSpecification, "
                + " fs.rangeMinimum AS rMin, fs.rangeMaximum AS rMax, fs.generalDatatype as generalDatatype, "
                + " f.gdbField_PK as DTID, f.infoAttID_FK as uniqueAttribute, "
                + " fs.measure AS measure,  fs.length AS attLength, fs.lexical AS lexical, "
                + " f.qualifiedPathName AS qualifiedPathName, fs.validRange AS validRange "
                + " FROM T_Field f, T_FieldSpecification fs WHERE f.gdbTable_FK='"+ FTID + "'"
                + " AND f.itemIdentifier_FK=fs.itemIdentifier_PK ORDER BY f.name ";
            ResultSet attributes=null; 
            attributes=selectAttributes.executeQuery(attributeSQL); 
            Attribute attribute;            
            int a=0;
            Hashtable<String, Integer> datatypeIDs = new Hashtable<String, Integer>();
            while(attributes.next()) {              	
            	String attName= attributes.getString("attName");           	
            	String qualifiedPathName = attributes.getString("qualifiedPathName");
            	if (!qualifiedPathName.isEmpty()) {
            		attName= concatNonNullFields(qualifiedPathName, attName, " : ");
            	}
            	String attDefinition = attributes.getString("definition");
            	attDefinition = concatNonNullFields(attDefinition, attributes.getString("description"), " [desc] ");
            	attDefinition = concatNonNullFields(attDefinition, attributes.getString("attNote"), " [note] ");
            	String generalDatatype = attributes.getString("generalDatatype");
            	attDefinition = concatNonNullFields(attDefinition, generalDatatype, " [type] ");
            	String lexical = attributes.getString("lexical");
                if (lexical.equals("1")) { lexical ="Lexical"; }
                else { lexical="Non-lexical"; }
                                               
                String attLength = attributes.getString("attLength");
                if ((attLength==null || attLength.equals("null") || attLength.equals(""))) { 
                	attLength="Unlimited";
                }                
                if (!(generalDatatype.equals("Integer") || generalDatatype.equals("Boolean") || generalDatatype.equals("Real"))) {
                	attDefinition = concatNonNullFields(attDefinition, lexical, " [lex] ");
                	attDefinition = concatNonNullFields(attDefinition, attLength, " [len] ");
                }
                attDefinition = concatNonNullFields(attDefinition, attributes.getString("structureSpecification"), " [struc] ");
                String measure = attributes.getString("measure");
            	
            	String validRange = attributes.getString("validRange");
            	String rMin = attributes.getString("rMin");
                String rMax = attributes.getString("rMax");
            	
            	if (!generalDatatype.equals("Text")) {
            		attDefinition = concatNonNullFields(attDefinition, measure, " [UoM] ");
            		attDefinition = concatNonNullFields(attDefinition, validRange, " [range] ");
            		attDefinition = concatNonNullFields(attDefinition, rMin, " [min] ");
            		attDefinition = concatNonNullFields(attDefinition, rMax, " [max] ");    
            	}
                
                String dtID = attributes.getString("DTID"); // used to key on enumerants
                String datatype = attributes.getString("datatypeNsgAlphaCode");
                String uniqueAttribute = attributes.getString("uniqueAttribute");
                

                String domainType = datatype;
                String ignoreEnumerants="no";
                
                if (generalDatatype.equals("Enumeration")) {
                	if (datatype.equals("Boolean")) { 
                		ignoreEnumerants="yes";
                		uniqueAttribute=datatype;
                	}
                }
                else if (generalDatatype.equals("Integer")||generalDatatype.equals("Real")) {
                	domainType = concatNonNullFields(domainType, measure, " : ");
                	domainType = concatNonNullFields(domainType, validRange, " ");
                	if (!((concatNonNullFields("",rMin,"")+concatNonNullFields("",rMax,"")).equals(""))) {
                		domainType += " <";
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
                	if (!((generalDatatype.equals("Text")||generalDatatype.equals("Complex")))){
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
       				attribute = new Attribute(nextId(), attName, attDefinition, tblEntity.getId(), 
       					datatypeIDs.get(uniqueAttribute), null, null, false, 0);
       				_attributes.add(attribute);
        		}
        		
        		if (ignoreEnumerants.equals("no")) { 
        			Statement selectEnumerants = null;
        			selectEnumerants = conn.createStatement();
        			// Find any enumerants to this attribute (most will have none).
        			String enumerantSQL = "SELECT cs.name AS enumName, cs.definition AS definition, "
        				+ " cs.description AS description, cs.note AS enumNote "
        				+ " FROM T_CodedValue c, T_CodedValueSpecification cs  WHERE " 
        				+ " c.itemIdentifier_FK=cs.itemIdentifier_PK AND c.gdbField_FK='" + dtID + "'" 
        				+ " ORDER BY cs.name";
        			ResultSet enumerants=null; 
        			enumerants=selectEnumerants.executeQuery(enumerantSQL); 
        			while(enumerants.next()) {                	
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
        } // while Entities
		selectEntities.close();
		
		addContainments(EntityKey);
		
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
		//System.out.println(i+ " elements.");
		return schemaElements;
	}
	
	//given a ResultSet (iterated elsewhere) with fields "tableName, definition, description, entityNote", return new Entity
	private Entity createEntity(ResultSet entities, HashMap<String, Integer> EntityKey){
		String tblName = null;
		String definition = null;
		String key = null;
		
		try {
			tblName = entities.getString("tableName").toLowerCase(); // note these match the columns in the  
			tblName = firstLetterCapitalize(tblName);   //Make nicer looking with 1st-cap.        	
			definition = entities.getString("definition");
			definition = concatNonNullFields(definition, entities.getString("description"), " [desc] ");
			definition = concatNonNullFields(definition, entities.getString("entityNote"), " [note] ");
			key = entities.getString("key");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		int id = nextId();
		EntityKey.put(key, id);

		return new Entity(id, tblName, definition, 0);
	}
	
	private void addContainments(HashMap<String, Integer> EntityKey) {
		try {
			Statement s = conn.createStatement();
			
			// Create nodes for abstract classes in T_AbstractTable -- added by Joel
			String sql = "SELECT gdbTable_FK, parentGdbTable_FK FROM T_Containment;";
			ResultSet rs = s.executeQuery(sql); 
	
			while(rs.next()) {
				Integer childID = EntityKey.get(rs.getString("gdbTable_FK"));
				Integer parentID = EntityKey.get(rs.getString("parentGdbTable_FK"));
				Containment c = new Containment(nextId(), "Containment", null, parentID, childID, 0, 0, 0);
System.out.println("Adding " + c.getName());
				_containments.add(c);
			}
		}
		catch (Exception e) {
			System.out.println("Error adding containments.");
			e.printStackTrace();
		}
	}
}