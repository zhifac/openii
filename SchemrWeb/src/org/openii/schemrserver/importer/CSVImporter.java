package org.openii.schemrserver.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;

public class CSVImporter extends SchemaImporter{
	/** Returns the importer name */
	public String getName()
		{ return "CSV Importer"; }

	/** Returns the importer description */
	public String getDescription()
		{ return "This importer is specifically designed to import lines from a large CSV file, in which each line represents a schema in the following format:" +
				"		number of schemas, {schema1\\, schema2\\, schema3\\...}, number of occurences"; }

	/** Returns the importer URI type */
	public Integer getURIType()
		{ return FILE; }

	/** Returns the importer URI file types */
	public ArrayList<String> getFileTypes()
	{
		ArrayList<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".csv");
		return fileTypes;
	}
	/** Initializes the importer for the specified URI */
	protected void initializeSchemaStructures() throws ImporterException {}
	
	/** Stores the URI being imported */
	protected static URI uri = null;
	protected static long start;
	
	protected static BufferedReader in = null;
	private static int i= 1;
	public static int limit = -1;
	
	protected ArrayList<Integer> generateExtendedSchemaIDs() throws ImporterException
	{ return new ArrayList<Integer>(); }
	
	public ArrayList<SchemaElement> generateSchemaElements() throws ImporterException
	{
		
		if (uri==null){
		try {
			start = System.nanoTime();
			uri = new URI("file:///C:/Users/kuangc/workspace/eclipse_workspaces/data_entry/SchemrServer/data/schemas-subset.csv");
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		}
		
		ArrayList<SchemaElement> out = new ArrayList<SchemaElement>();
		if (in==null){
		try {
			 in = new BufferedReader(new FileReader(new File(uri)));
		} catch (FileNotFoundException e) {
			throw new ImporterException(0, "CSV File not found");
		}
		}
		try {
			if (in.ready() && (limit > 0 || limit==-1)){
				String line = in.readLine();
				String[] lineParts = line.split(","); 
				//if (Integer.parseInt(lineParts[0]) < 4 || Integer.parseInt(lineParts[0]) > 14 || Integer.parseInt(lineParts[lineParts.length - 1]) <2)
					//return out; //Filter out uninteresting schemas
				int schemaID = nextId();
				out.add(new Containment(schemaID, "Schema" + i, "CSVImport", 0,0,0,0,0));
				System.out.println("new schema" + i);
				if (lineParts[1].startsWith("{")) lineParts[1] = lineParts[1].substring(1);
				for (int c=1; c < lineParts.length -1; c++){ //for each column
					lineParts[c] = lineParts[c].substring(0, lineParts[c].length() - 1);
					if (lineParts[c].endsWith("*") || lineParts[c].equals("\"\"")) continue;
					out.add(new Entity(nextId(), lineParts[c].replaceAll("\\\"|\\:|\\.|\\(|\\)|\\\\|\\/", ""), "Column", schemaID));
					System.out.println("\t" + lineParts[c].replaceAll("\\\"|\\:|\\.|\\(|\\)|\\\\|\\/", ""));	
				}
				i++;
				if (limit!=-1) limit--;
			} else{
				System.out.println("CSV Import complete: " + (System.nanoTime() - start)/1000000000 + " seconds");
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return out;
	}
	
}
