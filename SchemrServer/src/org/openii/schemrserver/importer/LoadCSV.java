package org.openii.schemrserver.importer;

import java.io.File;
import java.io.FileFilter;
import java.net.URISyntaxException;
import org.mitre.schemastore.porters.ImporterException;

public class LoadCSV extends LoadSchemaFile{
	private static String ROOT = "data/";
	private static String script = "makeDDL.py";
	
	private static int limit = 100;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		setClient();
		Runtime rt= Runtime.getRuntime();
		try {
			rt.exec("python " + ROOT + script + " " + ROOT + " " + limit);
			Thread.sleep(1000); // do nothing for 1000 miliseconds (1 second- needed for script to execute)
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		FileFilter filter = new FileFilter () {
		      public boolean accept(File pathname) {
		        return pathname.getName().endsWith("ddl");
		      }    
		    };
		ROOT += "ddl/";
		
	    File schemasDir = new File(ROOT);
		File [] schemaList = schemasDir.listFiles(filter);
		
		for (File f : schemaList) {
			System.out.println(f.getName().substring(0, f.getName().length()-4));
	        
	         try {
				loadSchema(f, f.getName().substring(0, f.getName().length()-4), "akannan", "CSVimport");
			} catch (ImporterException e) {
				System.err.println("WARNING: " + e.getMessage());
			} catch (URISyntaxException e) {
				System.err.println("ERROR: " + e.getMessage());
				System.exit(-1);
			}
			
	    }
		
		
		/*
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
	    		  for (int i=1; i< line.length -1; i++){
	    			  int end = line[i].length()-1; 
	    			  System.out.println(line[i].substring((i==1)?1:0, end));
					};
	    		  }
	    		  System.out.println("--");
	      }
	      fis.close();
	      bis.close();
	    } catch (Exception e) {
	    	System.err.println("CSV could not be read");
	    	e.printStackTrace();
	    }
	    */
	}

}
