package org.openii.schemrserver.importer;
import java.io.File;
import java.net.URISyntaxException;
import org.mitre.schemastore.porters.ImporterException;

public class LoadCSVDirectly extends LoadSchemaFile {

	static String csvFile = "data/schemas-subset.csv";
	
	public static void main(String[] args) {
		//setClient();
	    File f = new File(csvFile);
			System.out.println(f.getAbsolutePath());
	        
			try {
				int i=0;
				while(true){
					loadSchema(f, "schema"  + i, "akannan", "CSV File");
					i++;
				}
			} catch (ImporterException e) {
				System.out.println(e.getMessage());
			} catch (URISyntaxException e) {
				System.err.println("ERROR: " + e.getMessage());
				System.exit(-1);
			}
	        
	    }
	}
