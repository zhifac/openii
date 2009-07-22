package org.mitre.openii.views.manager.mappings.matchmaker;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple class for status messages.  And other nifty stuff.  Use this instead of using System.out.println
 * and System.err.println.  It will always do the right thing, and as a nifty bonus, it's shorter to type!
 * <p>There are two vectors for running the program.  The first is to run a main() method through one of the
 * test classes, like Feeder2.  The other is to run the GUI.  This is a wrapper class for status messages 
 * so that if you use these functions for those messages, they always go to the right place.
 * <p>As an added bonus, it can do run logging.  This is really useful when you want to see what blew up.
 * @author DMALLEN
 */
public class Informant {
	public static BufferedWriter writer = null; 
		
	public static boolean startRunlog() { 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hhmm");
		String s = sdf.format(new Date()).trim();
			
		String filename = "matchmaker-" + s + ".txt";
		File f = new File("c:\\", filename); 
						
		try { 
			System.out.println("Opening runlog " + filename + " for writing...");
			f.createNewFile();
			writer = new BufferedWriter(new FileWriter(f));   				
		} catch(IOException e) { 
			System.err.println("Cannot create runlog: " + e);
			e.printStackTrace();			
			writer = null;
			return false;
		}
		
		return true; 
	}
	
	public static void stopRunlog() { 
		try { 
			if(writer != null) { 
				writer.flush();
				writer.close();
				System.out.println("Stopped runlog.");
			} // End if
		} catch(Exception e) { 
			e.printStackTrace(); 
		} // End catch
		 
		writer = null;
	} // End stopRunlog
	
	/**
	 * Log a status message.  This just calls the other method, indicating that this message isn't an error.
	 * @param statusMessage the message you want to log.
	 * @see Informant#status(String, boolean)
	 */
	public static void status(String statusMessage) { 
		Informant.status(statusMessage, false); 
	}
	
	public static void progress(int progressValue) { 
//		if(MatchMakerGUI.matchMakerPane != null) 
//			MatchMakerGUI.matchMakerPane.updateProgress(progressValue);
//			
		System.out.println("Stage Progress: " + progressValue + "%");
	} // End progress
	
	public static void stage(String stageName) { 
//		if(MatchMakerGUI.matchMakerPane != null) 
//			MatchMakerGUI.matchMakerPane.updateStage(stageName);
//		else {
			String s = "*** STAGE: " + stageName + "***";
			System.out.println(s);
			try { 
				if(writer != null) {
					writer.write(s + "\r\n"); 
					writer.flush(); 
				}
			} catch(Exception e) { e.printStackTrace(); } 
//		}
	} // End stage
	
	/**
	 * Log a status message
	 * @param statusMessage the actual message
	 * @param isError true if the message represents an error, false otherwise.
	 */
	public static void status(String statusMessage, boolean isError) { 
//		if(MatchMakerGUI.matchMakerPane != null)
//			MatchMakerGUI.matchMakerPane.updateStatus(statusMessage, isError);

		if(isError) System.err.println(statusMessage);
		else System.out.println(statusMessage);
		
		if(writer != null) { 
			try { writer.write(statusMessage + "\r\n"); writer.flush(); } 
			catch(Exception e) { e.printStackTrace(); } 
		} 		
	} // End status
} // End Informant
