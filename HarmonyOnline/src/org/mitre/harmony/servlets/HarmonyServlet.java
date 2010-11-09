// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.harmony.servlets;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mitre.harmony.view.GenericExporter;
import org.mitre.harmony.view.GenericImporter;
import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.Exporter;
import org.mitre.schemastore.porters.Importer;
import org.mitre.schemastore.porters.Porter;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.PorterType;
import org.mitre.schemastore.porters.URIType;
import org.mitre.schemastore.porters.mappingExporters.MappingExporter;
import org.mitre.schemastore.porters.projectExporters.ProjectExporter;
import org.mitre.schemastore.porters.projectImporters.M3ProjectImporter;
import org.mitre.schemastore.porters.schemaExporters.SchemaExporter;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;

/**
 * Servlet for returning the information from the database
 * @author CWOLF
 */
public class HarmonyServlet extends HttpServlet
{
	static private SchemaStoreClient client = null;
	
	/** Returns the schemas existent in the database */ @SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
	{
		Object output = null;

		// Performs the specified action
		try {
			// Initialize the schema store client
			if(client==null)
			{
				// Retrieve the repository information
				String typeParm = req.getSession().getServletContext().getInitParameter("connectionType");
				String locationParm = req.getSession().getServletContext().getInitParameter("connectionLocation");
				String databaseParm = req.getSession().getServletContext().getInitParameter("connectionDatabase");
				String usernameParm = req.getSession().getServletContext().getInitParameter("connectionUsername");
				String passwordParm = req.getSession().getServletContext().getInitParameter("connectionPassword");
				
				// Create a repository connection
				Integer type = typeParm.equals("service")?Repository.SERVICE:typeParm.equals("postgres")?Repository.POSTGRES:Repository.DERBY;
				URI uri = null;	try { uri = new URI(locationParm); } catch(Exception e) {}
				Repository repository = new Repository(type,uri,databaseParm,usernameParm,passwordParm);
				
				// Connects to the client
				client = new SchemaStoreClient(repository);
			}

			// Get the function name
			ObjectInputStream in = new ObjectInputStream(req.getInputStream());
			String functionName = (String)in.readObject();

			// Get the list of function arguments
			Object args[] = new Object[(Integer)in.readObject()];
			for(int i=0; i<args.length; i++)
				args[i] = in.readObject();

			// Retrieves the requested porters
			if(functionName.equals("getPorters"))
				output = getPorters((PorterType)args[0]);

			// Retrieves the requested URI list from the specified porter
			else if(functionName.equals("getImporterURIList"))
			{
				GenericImporter importer = ((GenericImporter)args[0]);
				for(Object porter : new PorterManager(client).getPorters(importer.getType()))
				{
					Importer currImporter = (Importer)porter;
					if(importer.getName().equals(currImporter.getName()))
						if(importer.getDescription().equals(currImporter.getDescription()))
							{ output = currImporter.getList(); break; }
				}
			}
		
			// Exports the data through the specified exporter
			else if(functionName.equals("exportData"))
			{
				output = exportData((PorterType)args[0],(String)args[1],(ArrayList<Object>)args[2]);
				if(output==null) throw new Exception("Failed to export data");			
			}
				
			// Handles all normal calls to the schema store client
			else
			{
				// Create an array of types
				Class<?> types[] = new Class[args.length];
				for(int i=0; i<args.length; i++)
					types[i] = (args[i]==null) ? Integer.class : args[i].getClass();
					
				// Calls the SchemaStore method
				try {
					Method method = client.getClass().getDeclaredMethod(functionName, types);
					output = method.invoke(client, args);
				} catch(Exception e) { System.out.println("(E) HarmonyServlet: Unable to call method " + functionName); }
			}
				
			in.close();
		} catch(Exception e) { System.out.println("(E) HarmonyServlet.doPost - " + e.getMessage()); }
		
		// Returns the output
		try {
			ObjectOutputStream out = new ObjectOutputStream(res.getOutputStream());
			out.writeObject(output);
			out.close();
		} catch(IOException e) { System.out.println("(E) HarmonyServlet.doPost - " + e.getMessage()); }
	}
	
	/** Returns the requested list of exporter names */
	private ArrayList<Porter> getPorters(PorterType type)
	{
		ArrayList<Porter> porters = new ArrayList<Porter>();
		
		// Retrieve the porters
		for(Object porter : new PorterManager(client).getPorters(type))
		{
			// For schema importers, filter out unavailable importers
			if(type.equals(PorterType.SCHEMA_IMPORTERS))
			{
				URIType uriType = ((SchemaImporter)porter).getURIType();
				if(uriType.equals(URIType.SCHEMA)) continue;
			}
		
			// For project importers, only allow the M3 Project Importer
			if(type.equals(PorterType.PROJECT_IMPORTERS) && !(porter instanceof M3ProjectImporter))
				continue;
			
			// Add the porter to the list
			if(porter instanceof Importer) porters.add(new GenericImporter(type,(Importer)porter));
			if(porter instanceof Exporter) porters.add(new GenericExporter(type,(Exporter)porter));
		}

		// Sort the porters
		class PorterComparator implements Comparator<Porter>
		{
			public int compare(Porter porter1, Porter porter2)
				{ return porter1.getName().compareTo(porter2.getName()); }			
		}
		Collections.sort(porters,new PorterComparator());
		
		// Return the porters
		return porters;
	}
	
	/** Returns a temporary file with the provided name */
	private File getFile(String filename) throws IOException
		{ return File.createTempFile("Temp", "__" + filename.replaceAll("[^\\w\\.]+","")); }
	
	/** Handles the exporting of data */ @SuppressWarnings("unchecked")
	private String exportData(PorterType type, String exporterName, ArrayList<Object> data)
	{
		// Retrieve the exporter
		Exporter exporter = null;
		ArrayList<Exporter> exporters = new PorterManager(client).getPorters(type);
		for(Exporter currExporter : exporters)
			if(currExporter.getName().equals(exporterName))
				{ exporter = currExporter; break; }
		if(exporter==null) return null;
		
		// Export the data
		File file = null;
		try {

			// Run the schema exporter
			if(exporter instanceof SchemaExporter)
			{
				Schema schema = (Schema)data.get(0);
				ArrayList<SchemaElement> elements = (ArrayList<SchemaElement>)data.get(1);
				file = getFile(schema.getName() + exporter.getFileType());
				((SchemaExporter)exporter).exportSchema(schema, elements, file);
			}
				
			// Run the project exporter
			if(exporter instanceof ProjectExporter)
			{
				// Retrieve data
				Project project = (Project)data.get(0);
				ArrayList<Mapping> mappings = (ArrayList<Mapping>)data.get(1);
				ArrayList<ArrayList<MappingCell>> mappingCells = (ArrayList<ArrayList<MappingCell>>)data.get(2);
				
				// Assemble the hash map
				HashMap<Mapping,ArrayList<MappingCell>> mappingHash = new HashMap<Mapping,ArrayList<MappingCell>>();
				for(int i=0; i<mappings.size(); i++)
					mappingHash.put(mappings.get(i), mappingCells.get(i));
				
				// Export the project
				file = getFile(project.getName() + exporter.getFileType());
				((ProjectExporter)exporter).exportProject(project, mappingHash, file);
			}
			
			// Run the mapping exporter
			if(exporter instanceof MappingExporter)
			{
				Mapping mapping = (Mapping)data.get(0);
				ArrayList<MappingCell> mappingCells = (ArrayList<MappingCell>)data.get(1);
				String schema1 = client.getSchema(mapping.getSourceId()).getName();
				String schema2 = client.getSchema(mapping.getTargetId()).getName();
				file = getFile(schema1 + " to " + schema2 + exporter.getFileType());
				((MappingExporter)exporter).exportMapping(mapping, mappingCells, file);
			}
			
			file.deleteOnExit();
			
		} catch(Exception e) { file.delete(); file=null; }
		return file.getName();
	}
}