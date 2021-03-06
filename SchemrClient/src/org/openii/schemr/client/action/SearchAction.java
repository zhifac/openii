package org.openii.schemr.client.action;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.widgets.Shell;
import org.mitre.flexidata.ygg.importers.DDLImporter;
import org.mitre.flexidata.ygg.importers.Importer;
import org.mitre.flexidata.ygg.importers.ImporterException;
import org.mitre.flexidata.ygg.importers.XSDImporter;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.openii.schemr.SchemrServer;
import org.openii.schemr.SchemaStoreIndex.CandidateSchema;
import org.openii.schemr.client.model.MatchSummary;
import org.openii.schemr.client.model.Query;
import org.openii.schemr.client.view.MessageDialogFactory;

public class SearchAction 
//extends Action { TODO: not a PDE action yet!
{	
	// TODO put this in prefs
	private static int  RESULT_PAGE_SIZE = 30;

//    private final IWorkbenchWindow window;
//
//    public SearchAction(IWorkbenchWindow window, String text) {
//        super(text);
//        this.window = window;
//        // The id is used to refer to the action in a menu or toolbar
//        setId(ICommandIds.CMD_SEARCH);
//        // Associate the action with a pre-defined command, to allow key bindings.
//        setActionDefinitionId(ICommandIds.CMD_SEARCH);
//        setImageDescriptor(Activator.getImageDescriptor("/icons/xmag16.png"));
//    }
//
//	public void run() {
//		performSearch()
//    }

	public static MatchSummary [] performSearch(String keywordString, File schemaFile, Shell shell) throws RemoteException {
		Query q = null;
		
		HashMap<String,String> queryKeywords = getKeywordMap(keywordString);
		Schema schema = null;
		ArrayList<SchemaElement> schemaElements = null;
		
		if (schemaFile != null) {
			Importer importer = null;
			String s = "file:///" + schemaFile.getAbsolutePath().replace("\\", "/").replace(" ", "%20");
			// pick a loader
			if (schemaFile.getName().endsWith("xsd")) {
				importer = new XSDImporter();
			} else if (schemaFile.getName().endsWith("sql")) {
				importer = new DDLImporter();
			} else {
				MessageDialogFactory.displayError(shell, "Error" , "Not a schema file: "+schemaFile.getName());
			}

			// TODO: set this as preference
			String author = System.getProperty("user.name");
			try {
				schema = Importer.buildSchema(schemaFile.getName(), author, "", new URI(s));
				schemaElements = importer.buildSchemaElements(schema, new URI(s));
			} catch (ImporterException e) {
				MessageDialogFactory.displayError(shell, "Error", "Error importing "+s+": "+e.getMessage());
			} catch (URISyntaxException e) {
				MessageDialogFactory.displayError(shell, "Error", "Error accessing "+s+": "+e.getMessage());
			}
		}

		if (queryKeywords.size() < 1 && schemaElements == null) {
			MessageDialogFactory.displayError(shell, "Warning", "The query must have either keywords or a valid schema!");
			return new MatchSummary [0];
		}

		if (schema != null && schemaElements != null) {
			q = new Query(schema, schemaElements, queryKeywords);			
		} else {
			q = new Query(queryKeywords);			
		}

		// filter for candidate schemas
		CandidateSchema [] candidateSchemas = SchemrServer.getCandidateSchemas(q);

		int page = Math.min(RESULT_PAGE_SIZE, candidateSchemas.length);
		
		CandidateSchema [] filteredSchemas = new CandidateSchema [page];
		for (int i = 0; i < page; i++) {
			filteredSchemas[i] = candidateSchemas[i];
		}
		
		// process query against candidate schemas
		MatchSummary [] msarray = q.processQuery(filteredSchemas);

		System.out.println("Ranked results");
		MatchSummary [] topResultsArray = new MatchSummary [page];
		for (int i = 0; i < page; i++) {
			topResultsArray[i] = msarray[i];
			System.out.println(i+"\tschema: "+topResultsArray[i].getSchema().getName() + "\t\t\tscore: "+topResultsArray[i].getScore());
		}
		
		return topResultsArray;
	}

	private static HashMap<String, String> getKeywordMap(String keywordString) {
		HashMap<String,String> queryKeywords = new HashMap<String,String>();
		if (keywordString != null && !keywordString.equals("")) {
			for(String keyword : keywordString.split("\\s+")) {
				String [] ka = keyword.split(":");
				if (ka.length == 1) {
					queryKeywords.put(ka[0], "");
				} else if (ka.length > 1) {
					queryKeywords.put(ka[1], ka[0]);
				} else {
					continue;
				}
			}			
		}
		return queryKeywords;
	}

}
