package org.openii.schemrserver.importer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import org.mitre.schemastore.porters.ImporterException;

public class LoadCSV extends LoadSchemaFile{
	private static String csvFile = "data/schemas-subset.csv";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
	    		  for (int i=1; i< line.length -1; i++){
	    			  int end = line[i].length()-1; 
	    			  line[i] = line[i].substring((i==1)?1:0, end);
	    			  if (line[i].endsWith("*") || line[i].startsWith("\"\"")) continue;
	    			  if (line[i].endsWith(":")) line[i]=line[i].substring(0, line[i].length()-1);
	    			  System.out.println(line[i]);
					};
		    		  System.out.println("--");
	    		  }
	      }
	      fis.close();
	      bis.close();
	    } catch (Exception e) {
	    	System.err.println("CSV could not be read");
	    	e.printStackTrace();
	    }
	    
	}

}
