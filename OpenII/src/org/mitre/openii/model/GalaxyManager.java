package org.mitre.openii.model;

import java.util.ArrayList;

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
	/** Private class for managing changes to OpenII */
	static private class OpenIIMonitor implements OpenIIListener
	{
		// Reset schema information as needed
		public void schemaAdded(Integer schemaID) { Schemas.resetSchema(schemaID); }
		public void schemaDeleted(Integer schemaID) { Schemas.resetSchema(schemaID); }
		public void schemaModified(Integer schemaID) { Schemas.resetSchema(schemaID); }

		// Unused listener events
		public void groupAdded(Integer groupID) {}
		public void groupDeleted(Integer groupID) {}
		public void groupModified(Integer groupID) {}
		public void mappingAdded(Integer mappingID) {}
		public void mappingDeleted(Integer mappingID) {}
		public void mappingModified(Integer mappingID) {}		
	}
	static private OpenIIMonitor monitor = new OpenIIMonitor();
	
	/** Constructs the Galaxy Manager */
	static
	{
		// Initialize the connection to SchemaStore
		SchemaStoreManager.setClient(OpenIIManager.getConnection());
		ImageManager.init(null);

		// Set the selected schema
		ArrayList<Schema> schemas = Schemas.getSchemas();
		if(schemas.size()>0)
			SelectedObjects.setSelectedSchema(schemas.get(0).getId());
		
		// Monitors changes in OpenII
		OpenIIManager.addListener(monitor);
	}

	/** Returns the Galaxy Extension Pane */
	static public ExtensionsPane getExtensionPane(Integer schemaID)
	{
		ExtensionsPane extensionsPane = new ExtensionsPane();
		extensionsPane.setSchema(schemaID);
		return extensionsPane;
	}
	
	/** Returns the Galaxy Schema Pane */
	static public SchemaPane getSchemaPane(Integer schemaID)
		{ return new SchemaPane(schemaID); }
	
	/** Returns the Galaxy Search Pane */
	static public SearchPane getSearchPane()
		{ return new SearchPane(); }
}
