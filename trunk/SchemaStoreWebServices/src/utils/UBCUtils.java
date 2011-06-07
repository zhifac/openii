package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class UBCUtils
{
	/* Monitors for when the UBC data is loaded */
	static private class UBCListener implements ProgressListener
	{
		private boolean loaded = false;
		boolean isLoaded() { return loaded; }
		public void changed(ProgressEvent event) {}
		public void completed(ProgressEvent event) { loaded=true; }
	}

	/** Retrieve the UBC description */
	static public String getDescription(String ubc)
	{
		// Fix the ubc to be 12 characters
		while(ubc.length()<12) ubc = "0"+ubc;
		
		// Initialize the browser
		Display display = new Display();
		Shell shell = new Shell(display);
		Browser browser = new Browser(shell, SWT.NONE);
		browser.setBounds(5,5,600,600);

		// Launch the browser
		UBCListener listener = new UBCListener();
		browser.addProgressListener(listener);
		browser.setUrl("http://www.google.com/search?q="+ubc+"&tbm=shop");    
		
		// Monitor for load of page
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch()) display.sleep();
			if(listener.isLoaded())
			{
				String description = "";
				String text = browser.getText();
				
				// Retrieve the description
				Pattern pattern = Pattern.compile("<DIV([^>]*)>");
				Matcher matcher = pattern.matcher(text);
				int phase=0;
				while(matcher.find())
				{
					if(phase==0 && matcher.group(1).equals(" class=pslimain")) { phase=1; continue; }
					if(phase==1 && matcher.group(1).equals(""))
						{ description=text.substring(matcher.end()).replaceAll("[<\r\n].*",""); break; }
				}

				// Return the description
				display.dispose();
				return description;
			}
		}
		return "";
	}
}