package org.openii.schemrserver.importer;
import java.io.File;
import java.io.FileFilter;
import java.net.URISyntaxException;

import org.mitre.schemastore.porters.ImporterException;

public class LoadDBAnswers extends LoadSchemaFile {

	static String ROOT = "data/dbanswers/";
	
	public static void main(String[] args) {
		setClient();		
	    FileFilter filter = new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().endsWith("sql") || pathname.getName().endsWith("ddl");
			}
		};

	    File schemasDir = new File(ROOT);
		File [] schemaList = schemasDir.listFiles(filter);
		for (File f : schemaList) {
			System.out.println(f.getAbsolutePath());
	        try {
				loadSchema(f, f.getName().substring(0, f.getName().length()-4), "B. Williams", "DB Answers Co.");
			} catch (URISyntaxException e) {
				System.err.println("ERROR: " + e.getMessage());
				System.exit(-1);
			} catch (Exception e){}
	    }
	}	
	}
