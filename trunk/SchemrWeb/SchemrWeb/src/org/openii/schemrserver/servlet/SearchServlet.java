package org.openii.schemrserver.servlet;

import java.io.IOException;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.openii.schemrserver.indexer.SchemaUtility;
import org.openii.schemrserver.matcher.SimilarityMatrix.ScoreEvidence;
import org.openii.schemrserver.search.MatchSummary;
import org.openii.schemrserver.search.QueryFragment;
import org.openii.schemrserver.search.SchemaSearch;

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        response.getWriter().print("SearchServlet GET is not supported, use POST.");
        response.setStatus(200);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	    String searchTerms = request.getParameter("keywords");
    
		HttpSession session = request.getSession();
		SessionState userState = (SessionState) session.getAttribute("sessionState");
		if (userState == null) {
			userState = new SessionState(session.getId());
			session.setAttribute("sessionState", userState);
		}

		MatchSummary [] ms = SchemaSearch.performSearch(searchTerms, null);
		userState.matchSummaries = ms;
		
		String xml = generateSearchResultsListing(ms, userState);
        response.getWriter().print(xml);
        response.setStatus(200);
	}

	private String generateSearchResultsListing(MatchSummary[] msa, SessionState state) {
		StringWriter listingXML = new StringWriter();
		listingXML.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><schemas>");

		constructGraphs(msa, listingXML, state);
		
		listingXML.write("</schemas>");
		return listingXML.toString();
	}
	
	private void constructGraphs(MatchSummary[] msa, StringWriter listingXML, SessionState state) {
		
		state.results = new String [msa.length];
		for (int i = 0; i < msa.length; i++) {
			StringWriter schemaXML = new StringWriter();
			generateSearchResultXML(schemaXML);
			constructGraph(i, msa[i], listingXML, schemaXML);
			schemaXML.write("</graph>\n</graphml>");
			state.results[i] = schemaXML.toString();
		}
		
	}
	
	private static void generateSearchResultXML(StringWriter f){
		f.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		f.write("\n<graphml>\n<graph edgedefault=\"undirected\">\n\n");
		for (String[] kv : keys) 
			f.write("<key id=\"" + kv[0] + "\" for=\"node\" attr.name=\"" + kv[0] + "\" attr.type=\"" + kv[1] + "\"/>\n");
		f.write("\n\n");
	}
	
	private void constructGraph(int schemaIndex, MatchSummary matchSummary,
			StringWriter listingXML, StringWriter schemaXML) {

		Schema schema = matchSummary.getSchema();
		ArrayList<SchemaElement> schemaElements = matchSummary.getSchemaElements();
		HashMap<QueryFragment, ScoreEvidence> queryFragmentScoreEvidenceMap 
			= matchSummary.getQueryFragmentToScoreEvidencePairMap();
		
		HashMap<Integer, ScoreEvidence> idScoreEvidenceMap = new HashMap<Integer, ScoreEvidence>();
		HashMap<Integer, QueryFragment> idQueryFragmentMap = new HashMap<Integer, QueryFragment>();
		
		for (QueryFragment q : queryFragmentScoreEvidenceMap.keySet()) {
			ScoreEvidence se = idScoreEvidenceMap.get(q);
			SchemaElement e = (SchemaElement) se.getObj();
			idScoreEvidenceMap.put(e.getId(), se);
			idQueryFragmentMap.put(e.getId(), q);
		}
		
		if (schema == null) // || matchSummary.ge == null)
			throw new IllegalArgumentException("Schema and elements must not be null");
		
		boolean matched = idScoreEvidenceMap.keySet().contains(schema.getId());
		double score = 1;
		String matchedObj = "";
		if (matched) { 
			score += idScoreEvidenceMap.get(schema.getId()).getScore() * 3;
			matchedObj = idQueryFragmentMap.get(schema.getId()).getName().trim();
		}
		addNode(schema.getName().trim(), schema.getDescription().trim(),
				"Schema", schema.getId(), matched, score, matchedObj,
				listingXML, schemaXML, schemaIndex);

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
				addNode(name, e.getDescription().trim(), "Entity", e.getId(),
						matched, score, matchedObj, listingXML, schemaXML, schemaIndex);

				// TODO: assume all entities connect with schema
				addEdge("Relationship", schema.getId(), e.getId(), schemaXML);
				
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
					addNode(se.getName().trim(), se.getDescription().trim(),
							"Attribute", se.getId(), matched, score,
							matchedObj, listingXML, schemaXML, schemaIndex);
					addEdge("Relationship", e.getId(), se.getId(), schemaXML);
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
				addNode(c.getName().trim(), c.getDescription().trim(), "Containment", c.getId(), matched, score, matchedObj, listingXML, schemaXML, schemaIndex);
				System.out.println("Containment"+": "+c.getName().trim()+" "+c.getId());			
				int pid = c.getParentID();
				addEdge("Relationship", pid , c.getId(), schemaXML);
				System.out.println("\tedge: "+pid+" <-> "+c.getId());
			}
		}
	}
	
	private static final String[][] keys = {{"name", "string"}, {"desc", "string"}, {"type", "string"},
		{"id", "int"}, {"matched", "boolean"}, {"score", "double"}, {"matched_obj", "string"}};

	private static void addNode(String name, String desc, String type, int id,
			boolean matched, double score, String matchedObj,
			StringWriter schemas, StringWriter f, int schemaIndex) {
		try {
			f.write("<node id=\"" + id + "\">\n");
			if (type.equals("Schema")) {
				schemas.write("<node " + "index=\""
						+ String.format("%03d", Integer.valueOf(schemaIndex))
						+ "\" name=\"" + name + "\" desc=\"" + desc
						+ "\" score=\"" + score + "\" id=\"" + id + "\""
						+ ">"
						+ String.format("%03d", Integer.valueOf(schemaIndex))
						+ "</node>\n");
			}
				//schemas.write("<node>" + String.format("%03d", Integer.valueOf(schemaIndex))  + "</node>\n");
			data("name", name, f);
			data("desc", desc, f);
			data("type", type, f);
			data("id", "" + id, f);
			data("matched", matched?"true":"false", f);
			data("score", "" + score, f);
			data("matched_obj", matchedObj, f);
			f.write("</node>\n");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
//		System.out.println("Adding node\t"+name+"\t"+type+"\t"+id+"\t"+matched+"\t"+score);
	}
	private static void data(String key, String value, StringWriter schemaXML) throws IOException{
		schemaXML.write("\t<data key=\"" + key + "\">" + value + "</data>\n");
	}
	
	private static void addEdge(String type, int sid, int did, StringWriter f) {
		f.write("\n<edge source=\"" + sid + "\" target=\"" + did + "\"></edge>\n");
	}
}
