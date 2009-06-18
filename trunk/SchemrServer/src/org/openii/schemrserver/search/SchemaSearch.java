package org.openii.schemrserver.search;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.schemaImporters.DDLImporter;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;
import org.mitre.schemastore.porters.schemaImporters.XSDImporter;
import org.openii.schemrserver.search.QueryFragment;
import org.openii.schemrserver.matcher.SimilarityMatrix.ScoreEvidence;
import org.openii.schemrserver.indexer.SchemaStoreIndex;
import org.openii.schemrserver.indexer.SchemaUtility;
import org.openii.schemrserver.indexer.SchemaStoreIndex.CandidateSchema;
import org.openii.schemrserver.search.Query;
import org.openii.schemrserver.search.MatchSummary;

public class SchemaSearch {
	private static int  RESULT_PAGE_SIZE = 30;
	private static final String[][] keys = {{"name", "string"}, {"desc", "string"}, {"type", "string"},
		{"id", "int"}, {"matched", "boolean"}, {"score", "double"}, {"matched_obj", "string"}};
	private static FileWriter f = null; //GraphML Files
	private static int schemaIndex = 1;
	private static FileWriter schemas; //Schemas XML File
	private static double trueScore; 
	private static void newFile(){
		schemaIndex++;
		try {
			if (f != null){
				f.write("</graph>\n</graphml>\n");
				f.close();
			}
			f = new FileWriter("results/out" + String.format("%03d", Integer.valueOf(schemaIndex)) + ".xml");
			f.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			f.write("\n<graphml>\n<graph edgedefault=\"undirected\">\n\n");
			for (String[] kv : keys) 
				f.write("<key id=\"" + kv[0] + "\" for=\"node\" attr.name=\"" + kv[0] + "\" attr.type=\"" + kv[1] + "\"/>\n");
			f.write("\n\n");
			
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
	}
	
	public static MatchSummary [] performSearch(String keywordString, File schemaFile) throws RemoteException {
		Query q = null;
		try {
			schemas = new FileWriter("schemas.xml");
			schemas.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<schemas>\n");
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		HashMap<String,String> queryKeywords = getKeywordMap(keywordString);
		//ArrayList<SchemaProperty> schema = null;
		ArrayList<SchemaElement> schemaElements = null;
		if (schemaFile != null) {
			SchemaImporter importer = null;
			String s = "file:///" + schemaFile.getAbsolutePath().replace("\\", "/").replace(" ", "%20");
			// pick a loader
			if (schemaFile.getName().endsWith("xsd")) {
				importer = new XSDImporter();
			} else if (schemaFile.getName().endsWith("sql")) {
				importer = new DDLImporter();
			} else {
				System.err.println("Not a schema file: "+schemaFile.getName());
			}

			// TODO: set this as preference
			String author = System.getProperty("user.name");
			try {
				//schema = importer.getSchemaProperties( new URI(s));
				schemaElements = importer.getSchemaElements(new URI(s));
			} catch (ImporterException e) {
				System.err.println("Error importing "+s+": "+e.getMessage());
			} catch (URISyntaxException e) {
				System.err.println("Error accessing "+s+": "+e.getMessage());
			}
		}

		if (queryKeywords.size() < 1 && schemaElements == null) {
			System.err.println("The query must have either keywords or a valid schema!");
			return new MatchSummary [0];
		}

		//if (schema != null && schemaElements != null) {
		//	q = new Query(schema, schemaElements, queryKeywords);			
		//} else {
			q = new Query(queryKeywords);			
		//}

		// filter for candidate schemas
		CandidateSchema [] candidateSchemas = getCandidateSchemas(q);

		int page = Math.min(RESULT_PAGE_SIZE, candidateSchemas.length);
		
		CandidateSchema [] filteredSchemas = new CandidateSchema [page];
		for (int i = 0; i < page; i++) {
			filteredSchemas[i] = candidateSchemas[i];
			//constructGraph()
			
		}
		
		// process query against candidate schemas
		MatchSummary [] msarray = q.processQuery(filteredSchemas);
		
		System.out.println("Ranked results");
		MatchSummary [] topResultsArray = new MatchSummary [page];
		for (int i = 0; i < page; i++) {
			topResultsArray[i] = msarray[i];
			trueScore = topResultsArray[i].getScore();
			System.out.println(i+"\tschema: "+topResultsArray[i].getSchema().getName() + "\t\t\tscore: "+trueScore);
			
			
			constructGraph(topResultsArray[i].getSchema(), 
					SchemaUtility.getCLIENT().getGraph(topResultsArray[i].getSchema().getId()).getElements(null),
					new HashMap<QueryFragment, ScoreEvidence>());
		}
		try {
			f.write("</graph>\n</graphml>");
			f.close();
			schemas.write("\n</schemas>");
			schemas.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return topResultsArray;
	}
	
	
	//HELPERS::
	
	public static void constructGraph(Schema schema,
			ArrayList<SchemaElement> schemaElements,
			HashMap<QueryFragment, ScoreEvidence> queryFragmentScoreEvidenceMap) {
		
		HashMap<Integer, ScoreEvidence> idScoreEvidenceMap = new HashMap<Integer, ScoreEvidence>();
		HashMap<Integer, QueryFragment> idQueryFragmentMap = new HashMap<Integer, QueryFragment>();
		
		for (QueryFragment q : queryFragmentScoreEvidenceMap.keySet()) {
			ScoreEvidence se = queryFragmentScoreEvidenceMap.get(q);
			SchemaElement e = (SchemaElement) se.getObj();
			idScoreEvidenceMap.put(e.getId(), se);
			idQueryFragmentMap.put(e.getId(), q);
		}
		
		if (schema == null || schemaElements == null)
			throw new IllegalArgumentException("Schema and elements must not be null");
		
		boolean matched = idScoreEvidenceMap.keySet().contains(schema.getId());
		double score = 1;
		String matchedObj = "";
		if (matched) { 
			score += idScoreEvidenceMap.get(schema.getId()).getScore() * 3;
			matchedObj = idQueryFragmentMap.get(schema.getId()).getName().trim();
		}
		addNode(schema.getName().trim(), schema.getDescription().trim(), "Schema", schema.getId(), matched, score, matchedObj);

		for (SchemaElement schemaElement : schemaElements) {
			if (schemaElement instanceof Entity) {
				Entity e = (Entity) schemaElement;
				
				String name = e.getName().trim();
				if ("".equals(name)) {
					name = "-";
				}
				
				matched = idScoreEvidenceMap.keySet().contains(e.getId());
				score = 1;
				matchedObj = "";
				if (matched) { 
					score += idScoreEvidenceMap.get(e.getId()).getScore() * 3 + 1;
					matchedObj = idQueryFragmentMap.get(e.getId()).getName().trim();
				}
				addNode(name, e.getDescription().trim(), "Entity" , e.getId(), matched, score, matchedObj);

				// TODO: assume all entities connect with schema
				addEdge("Relationship", schema.getId(), e.getId());
				
				// FIXME: add child attributes
				for (Integer i : e.getReferencedIDs()){
					System.out.println(i);
					SchemaElement se;
					try {
						se = SchemaUtility.getCLIENT().getSchemaElement(i);
					} catch (RemoteException e1) {
						e1.printStackTrace();
						return;
					}
					addNode(se.getName().trim(), se.getDescription().trim(), "Attribute", se.getId(), matched, score, matchedObj);
					addEdge("Relationship", e.getId(), se.getId());
				}

			} else if (schemaElement instanceof Containment) {
				Containment c = (Containment) schemaElement;
				String name = c.getName().trim();
				if ("".equals(name)) {
					name = "-";
				}
				
				matched = idScoreEvidenceMap.keySet().contains(c.getId());
				score = 1;
				matchedObj = "";
				if (matched) { 
					score += idScoreEvidenceMap.get(c.getId()).getScore() * 3 + 1;
					matchedObj = idQueryFragmentMap.get(c.getId()).getName().trim();
				}
				addNode(c.getName().trim(), c.getDescription().trim(), "Containment", c.getId(), matched, score, matchedObj);
				System.out.println("Containment"+": "+c.getName().trim()+" "+c.getId());			
				int pid = c.getParentID();
				addEdge("Relationship", pid , c.getId());
				System.out.println("\tedge: "+pid+" <-> "+c.getId());
			}
		}
	}
	
	private static void addNode(String name, String desc, String type, int id, boolean matched, double score, String matchedObj) {
		if (f==null || type.equals("Schema")) newFile();
		try {
			f.write("<node id=\"" + id + "\">\n");
			if (type.equals("Schema")) 
				schemas.write("<node " + "index=\"" + String.format("%03d", Integer.valueOf(schemaIndex)) + "\" name=\"" + name + "\" desc=\"" + desc + "\" score=\"" + trueScore + "\" id=\"" +id +"\"" + ">" + String.format("%03d", Integer.valueOf(schemaIndex)) + "</node>\n");
				//schemas.write("<node>" + String.format("%03d", Integer.valueOf(schemaIndex))  + "</node>\n");
			data("name", name);
			data("desc", desc);
			data("type", type);
			data("id", "" + id);
			data("matched", matched?"true":"false");
			data("score", "" + score);
			data("matched_obj", matchedObj);
			f.write("</node>\n");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
//		System.out.println("Adding node\t"+name+"\t"+type+"\t"+id+"\t"+matched+"\t"+score);
	}
	private static void data(String key, String value) throws IOException{
			f.write("\t<data key=\"" + key + "\">" + value + "</data>\n");
	}
	
	private static void addEdge(String type, int sid, int did) {
		/*
		int ridx = edgesTable.addRow(); 
		edgesTable.setString(ridx, edgeTypeColIdx, type);
		edgesTable.setInt(ridx, edgeSrcIdColIdx, sid);
		edgesTable.setInt(ridx, edgeDstIdColIdx, did);
		*/
		try {
			f.write("\n<edge source=\"" + sid + "\" target=\"" + did + "\"></edge>\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println("Adding edge\n\t"+type);
//		System.out.println("\t"+sid);
//		System.out.println("\t"+did);
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
	private static CandidateSchema [] getCandidateSchemas(Query query) {		
		CandidateSchema [] candidateSchemas = SchemaStoreIndex.searchIndex(SchemaUtility.LOCAL_INDEX_DIR, query);
		
		System.out.println("Found " + candidateSchemas.length + " candidate schemas");
		return candidateSchemas;
	}
}
