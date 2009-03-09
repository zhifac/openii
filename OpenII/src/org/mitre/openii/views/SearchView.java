package org.mitre.openii.views;

import java.awt.Frame;

import javax.swing.JApplet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.ui.part.ViewPart;
import org.mitre.galaxy.view.searchPane.SearchPane;
import org.mitre.galaxy.view.searchPane.SearchPaneListener;
import org.mitre.openii.model.GalaxyManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.ygg.menu.ElementInput;
import org.mitre.schemastore.model.Schema;

/** Constructs the Search View */
public class SearchView extends ViewPart implements SearchPaneListener
{
	/** Stores the search pane used within this editor */
	private SearchPane searchPane = null;
	
	/** Displays the Search View */
	public void createPartControl(Composite parent)
	{				
		// Constructs the extensions pane
		searchPane = GalaxyManager.getSearchPane();
		searchPane.addSearchPaneListener(this);

		// Construct the applet pane to contain the Galaxy Search frame
		JApplet appletPane = new JApplet();
		appletPane.add(searchPane);
		
		// Embed the applet pane into the Eclipse view
		Composite swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
		Frame frame = SWT_AWT.new_Frame( swtAwtComponent );
		frame.add(appletPane);
	}

	/** Disposes the editor */
	public void dispose()
		{ searchPane.removeSearchPaneListener(this); super.dispose(); }

	/** Handles the selection of a schema within the search pane */
	public void schemaSelected(Integer schemaID)
	{
		final Schema schema = OpenIIManager.getSchema(schemaID); 
		Display display = getSite().getWorkbenchWindow().getShell().getDisplay();
		display.syncExec( new Runnable()
		{
			public void run()
			{
				try {
					ElementInput input = new ElementInput(schema);
					String editorID = "org.mitre.openii.editors.SchemaView";
					getSite().getPage().openEditor(input,editorID);
				} catch(Exception e) { System.out.println(e); }
			}
		});
	}
	
	// Sets the focus in this view
	public void setFocus() {}
}