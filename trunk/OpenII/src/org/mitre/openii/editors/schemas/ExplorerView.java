package org.mitre.openii.editors.schemas;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.editors.schemas.explorer.SchemaExplorer;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

/**
 * ExplorerView for OpenII: Generate and display HTML documentation tailored to each individual element in 
 * schemastore.
 * <p>This class creates a browser window, and controls how HTML references are generated and loaded into it.  See
 * SchemaExplorer for the implementation of the documentation.
 * @see org.mitre.openii.editors.schemas.explorer.SchemaExplorer
 * @author DMALLEN
 */
public class ExplorerView extends OpenIIEditor {
	protected Browser browser;

	/** Displays the Schema Explorer */
	public void createPartControl(Composite parent)
	{			
		try { 			
			parent.setLayout(new GridLayout());
			Composite compTools = new Composite(parent, SWT.NONE);
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			compTools.setLayoutData(data);
			compTools.setLayout(new GridLayout(2, false));
			ToolBar navBar = new ToolBar(compTools, SWT.NONE);
			navBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_END));
			final ToolItem back = new ToolItem(navBar, SWT.PUSH);
			back.setText("Back");
			back.setEnabled(false);
			final ToolItem forward = new ToolItem(navBar, SWT.PUSH);
			forward.setText("Forward");
			forward.setEnabled(false);
			
			Composite comp = new Composite(parent, SWT.NONE);
			data = new GridData(GridData.FILL_BOTH);
			comp.setLayoutData(data);
			comp.setLayout(new FillLayout());
			final SashForm form = new SashForm(comp, SWT.HORIZONTAL);
			form.setLayout(new FillLayout());	
			//browser = new Browser(parent, SWT.EMBEDDED);
			//Frame frame = SWT_AWT.new_Frame( browser );
			browser = new Browser(form, SWT.NONE); 
			
			back.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) { browser.back(); }
			});
			forward.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) { browser.forward(); }
			});
			
			//final ExplorerView me = this; 
			final SchemaExplorer explorer = new SchemaExplorer();
			
			// Whenever the user clicks on a link, figure out where they are going,
			// and generate the page just before they get there.  :)
			LocationListener locationListener = new LocationListener() {
			      public void changed(LocationEvent event) { 
						Browser browser = (Browser)event.widget;
						back.setEnabled(browser.isBackEnabled());
						forward.setEnabled(browser.isForwardEnabled());
			      }
			      public void changing(LocationEvent event) {
			    	  	Browser browser = (Browser)event.widget;
			    	  	String location = event.location;
			    	  	System.err.println("Browser changing location: " + location);
			    	  	
			    	  	if(location.indexOf("file:") != -1) return; 
			    	  	explorer.generatePage(SchemaExplorer.elementIDFromURL(location));
			    	  	
			    	  	// For some reason, if this isn't called then the browser has funky display issues.
			    	  	browser.redraw(); 
			         } // End changing
			      };
			
			browser.addLocationListener(locationListener);						
			String u = explorer.urlForElement(elementID); 
			System.err.println("Navigating to " + u);  
			browser.setUrl(u);			
		} catch (SWTError e) {	          
	         e.printStackTrace();
	         System.err.println("Browser cannot be initialized.");
	    } // End catch		
	} // End createPartControl
} // End ExplorerView
