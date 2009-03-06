package org.mitre.openii.model;

import java.util.ArrayList;

import javax.swing.JPanel;

import org.mitre.galaxy.model.Schemas;
import org.mitre.galaxy.model.SelectedObjects;
import org.mitre.galaxy.model.server.ImageManager;
import org.mitre.galaxy.model.server.SchemaStoreManager;
import org.mitre.galaxy.view.extensionsPane.ExtensionsPane;
import org.mitre.galaxy.view.schemaPane.SchemaPane;
import org.mitre.galaxy.view.searchPane.SearchPane;
import org.mitre.schemastore.model.Schema;

/** Class for managing Galaxy */
public class GalaxyManager
{
	/** Initializes Galaxy */
	static
	{
		// Initialize the connection to SchemaStore
		SchemaStoreManager.setClient(OpenIIManager.getConnection());
		ImageManager.init(null);

		// Set the selected schema
		ArrayList<Schema> schemas = Schemas.getSchemas();
		if(schemas.size()>0)
			SelectedObjects.setSelectedSchema(schemas.get(0).getId());
	}

	/** Returns the Galaxy Pane */
	static public JPanel getExtensionPane()
		{ return new ExtensionsPane(); }
	
	/** Returns the Galaxy Schema Pane */
	static public JPanel getSchemaPane(Integer schemaID)
		{ return new SchemaPane(schemaID,null); }
	
	/** Returns the Galaxy Search Pane */
	static public JPanel getSearchPane()
		{ return new SearchPane(); }
}
