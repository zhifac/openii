package org.mitre.openii.editors.schemas;

import java.awt.Frame;

import javax.swing.JApplet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.awt.SWT_AWT;
import org.mitre.galaxy.model.Schemas;
import org.mitre.galaxy.view.extensionsPane.ExtensionsPane;
import org.mitre.galaxy.view.extensionsPane.ExtensionsPaneListener;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.GalaxyManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Schema;

/** Constructs the Extension graph */
public class ExtensionGraphView extends OpenIIEditor implements ExtensionsPaneListener
{
	/** Stores the extension pane used within this editor */
	private ExtensionsPane extensionsPane = null;
	
	/** Displays the Extension graph */
	public void createPartControl(Composite parent)
	{		
		// Constructs the extensions pane
		extensionsPane = GalaxyManager.getExtensionPane(elementID);
		extensionsPane.addExtensionsPaneListener(this);
		
		// Construct the applet pane to contain the Harmony frame
		JApplet appletPane = new JApplet();
		appletPane.add(extensionsPane);
		
		// Embed the applet pane into the Eclipse view
		Composite swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
		Frame frame = SWT_AWT.new_Frame( swtAwtComponent );
		frame.add(appletPane);
	}

	/** Disposes the editor */
	public void dispose()
		{ extensionsPane.removeExtensionsPaneListener(this); super.dispose(); }
	
	/** Handles the selection of a schema within the extensions pane */
	public void schemaSelected(Integer schemaID)
	{
		final Schema schema = OpenIIManager.getSchema(schemaID); 
		Display display = getSite().getWorkbenchWindow().getShell().getDisplay();
		display.syncExec( new Runnable()
		{
			public void run()
				{ EditorManager.launchDefaultEditor(schema); }
		});
	}
	
	/** Handles the addition of a schema */
	public void schemaAdded(Integer schemaID)
	{
		if(Schemas.getAssociatedSchemas(schemaID).contains(elementID))
			{ Schemas.resetSchema(schemaID); extensionsPane.addSchema(schemaID); }
		super.schemaAdded(schemaID);
	}
	
	/** Handles the removal of a schema */
	public void schemaDeleted(Integer schemaID)
	{
		Schemas.resetSchema(schemaID);
		extensionsPane.removeSchema(schemaID);
		super.schemaDeleted(schemaID);
	}
	
	// Unused listener event
	public void comparisonSchemaSelected(Integer schemaID) {}
}