package org.openii.schemrserver.importer;
import java.io.File;
import java.io.FileFilter;
import java.net.URISyntaxException;
import org.mitre.schemastore.porters.ImporterException;

public class LoadThalia extends LoadSchemaFile {

	static String ROOT = "data/thalia/";
	
	public static void main(String[] args) {
		setClient();
		
	    FileFilter filter = new FileFilter () {
	      public boolean accept(File pathname) {
	        return pathname.getName().endsWith("xsd");
	      }    
	    };

	    File schemasDir = new File(ROOT);
		File [] schemaList = schemasDir.listFiles(filter);
		for (File f : schemaList) {
			System.out.println(f.getAbsolutePath());
	        try {
				loadSchema(f, f.getName().substring(0, f.getName().length()-4), "kuangc", "Thalia");
			} catch (ImporterException e) {
				System.err.println("WARNING: " + e.getMessage());
			} catch (URISyntaxException e) {
				System.err.println("ERROR: " + e.getMessage());
				System.exit(-1);
			}
	    }
	}	
	}
