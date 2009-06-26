package org.openii.schemrserver.importer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;
import org.openii.schemrserver.importer.LoadSchemaFile;

public class LoadCSV extends LoadSchemaFile{
	private static String csvFile = "data/nocheckin/schemas-subset.csv";
	/**
	 * @param args
	 */
	private static int index = 1;
	private static Domain D_STRING = new Domain(SchemaImporter.nextId(), "string", null, 0);
	public static void main(String[] args) {
		Schema schema;
		ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();
		
		setClient();
		File file = new File(csvFile);
		FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    BufferedReader br = null;
	    try {
	      fis = new FileInputStream(file);
	      // Here BufferedInputStream is added for fast reading.
	      bis = new BufferedInputStream(fis);
	      br = new BufferedReader(new InputStreamReader(bis));
	      while (br.ready()) {
	    	  String[] line = br.readLine().split(",");
	    	  int occurrences = Integer.parseInt(line[line.length - 1]);
	    	  if(occurrences > 2 && occurrences < 12 && line.length > 5){ //Filter interesting schemas
	    		  schemaElements.add(D_STRING);
	    		  Integer schemaID = SchemaImporter.nextId();
	    		  schema = new Schema(schemaID, "Schema" + index, "WebTables", "CSV File", "", "Schemas imported from the WebTables collection", false);
	    		  Integer entityID = SchemaImporter.nextId();
	    		  schemaElements.add(new Entity(entityID, "Table", "Web Table", schemaID));
	    		  index++;	    		 
	    		  for (int i=1; i< line.length -1; i++){
	    			  int end = line[i].length()-1; 
	    			  line[i] = line[i].substring((i==1)?1:0, end);
	    			  if (line[i].endsWith("*") || line[i].startsWith("\"\"")) continue;
	    			  if (line[i].endsWith(":")) line[i]=line[i].substring(0, line[i].length()-1);
	    			  System.out.println(line[i]);  //Individual schema Element
	    			  Attribute a = new Attribute(SchemaImporter.nextId(), line[i], line[i], entityID, D_STRING.getId(), null, null, false, 0);
	    			  schemaElements.add(a);
					
	    		  };
	    		  client.importSchema(schema, schemaElements);
	    		  schemaElements = new ArrayList<SchemaElement>();
	    		  System.out.println("--");
	    		  }
	      }
	      fis.close();
	      bis.close();
	    } catch (Exception e) {
	    	System.err.println("An error occured importing the CSV file");
	    	e.printStackTrace();
	    }
	   	    
	}

}
