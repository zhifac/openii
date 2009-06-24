package org.openii.schemrserver.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.Graph;
import org.mitre.schemastore.model.graph.HierarchicalGraph;
import org.openii.schemrserver.indexer.SchemaUtility;
import org.openii.schemrserver.matcher.SimilarityMatrix.ScoreEvidence;
import org.openii.schemrserver.search.MatchSummary;
import org.openii.schemrserver.search.QueryFragment;

/**
 * Servlet implementation class GraphVisualizationServlet
 */
public class GraphVisualizationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	private static final String[][] keys = { 
		{ "name", "string" },
		{ "desc", "string" }, 
		{ "type", "string" }, 
		{ "id", "int" },
		{ "matched", "boolean" }, 
		{ "score", "double" },
		{ "matched_obj", "string" } };

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		String raw = request.getParameter("resultId");

	    Integer id = null;
	    if (raw != null && !raw.trim().equals("")) {
	    	id = Integer.parseInt(raw);
	    }
		HttpSession session = request.getSession();
		SessionState userState = (SessionState) session.getAttribute("sessionState");
		if (userState == null || id == null) {
//			throw new IllegalStateException();
	    	response.sendError(406);
		}

		MatchSummary matchSummary = userState.idToMatchSummary.get(id);
		
		
//		schemaXMLWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//		schemaXMLWriter.write("\n<graphml>\n<graph edgedefault=\"undirected\">\n\n");
		Element root = new Element("graphml");
		Element graph = new Element("graph");
		graph.setAttribute("edgedefault", "undirected");
		root.addContent(graph);
		
//		schemaXMLWriter.write("<key id=\"" + kv[0] + "\" for=\"node\" attr.name=\"" + kv[0] + "\" attr.type=\"" + kv[1] + "\"/>\n");
		for (String[] kv : keys) {
			Element key = new Element("key");
			key.setAttribute("id", kv[0]);
			key.setAttribute("for", "node");
			key.setAttribute("attr.name", kv[0]);
			key.setAttribute("attr.type", kv[1]);
			graph.addContent(key);
		}

		Schema schema = matchSummary.getSchema();
		ArrayList<SchemaElement> schemaElements = matchSummary.getSchemaElements();
		HashMap<QueryFragment, ScoreEvidence> queryFragmentScoreEvidenceMap 
				= matchSummary.getQueryFragmentToScoreEvidencePairMap();
		
		HashMap<Integer, ScoreEvidence> idScoreEvidenceMap = new HashMap<Integer, ScoreEvidence>();
		HashMap<Integer, QueryFragment> idQueryFragmentMap = new HashMap<Integer, QueryFragment>();

		for (QueryFragment q : queryFragmentScoreEvidenceMap.keySet()) {
			ScoreEvidence se = queryFragmentScoreEvidenceMap.get(q);
			SchemaElement e = (SchemaElement) se.getObj();
			idScoreEvidenceMap.put(e.getId(), se);
			idQueryFragmentMap.put(e.getId(), q);
		}
		
		if (schema == null) throw new IllegalArgumentException("Schema must not be null");		


		boolean matched = idScoreEvidenceMap.keySet().contains(schema.getId());
		double score = 1;
		String matchedObj = "";
		if (matched) { 
			score += idScoreEvidenceMap.get(schema.getId()).getScore() * 3;
			matchedObj = idQueryFragmentMap.get(schema.getId()).getName().trim();
		}
		addNode(schema.getName().trim(), schema.getDescription().trim(),
				"Schema", schema.getId(), matched, score, matchedObj,
				graph);
		
		Graph g = SchemaUtility.getCLIENT().getGraph(schema.getId());
		HierarchicalGraph hg = new HierarchicalGraph(g, null);
		
		for (SchemaElement schemaElement : schemaElements) {
			if (schemaElement instanceof Entity || schemaElement instanceof Attribute || schemaElement instanceof Containment) {
				processElement(schemaElement, hg, idScoreEvidenceMap, idQueryFragmentMap, graph);				
			}
		}
	
		Document doc = new Document(root);
		XMLOutputter serializer = new XMLOutputter();
		serializer.output(doc, response.getWriter());
// debug
		serializer.output(doc, System.out);	
	}
	
	private void processElement(
			SchemaElement schemaElement, 
			HierarchicalGraph hg,
			HashMap<Integer, ScoreEvidence> idScoreEvidenceMap, 
			HashMap<Integer, QueryFragment> idQueryFragmentMap,
			Element parentXMLElement) {
		
		String name = schemaElement.getName().trim();
		if ("".equals(name)) {
			name = "-";
		}
		
		int id = schemaElement.getId();
		boolean matched = idScoreEvidenceMap.keySet().contains(id);

		double score = 0;
		String matchedObj = "";
		if (matched) { 
			score = idScoreEvidenceMap.get(id).getScore();
			matchedObj = idQueryFragmentMap.get(id).getName().trim();
		}
		
		String type = schemaElement.getClass().getSimpleName();

		addNode(name, schemaElement.getDescription().trim(), type, id,
						matched, score, matchedObj, parentXMLElement);

		ArrayList<SchemaElement> parents = hg.getParentElements(id);
		if (parents.size() > 0) {
			for (SchemaElement sep : parents) {
				addEdge("Relationship", sep.getId(), id, parentXMLElement);			
			}
		} else {
			addEdge("Relationship", hg.getSchema().getId(), id, parentXMLElement);
		}
	}

	private static void addNode(String name, String desc, String type, int id,
			boolean matched, double score, String matchedObj, Element parent) {
		Element node = new Element("node");
		node.setAttribute("name",name);
		node.setAttribute("desc",desc);
		node.setAttribute("type",type);
		node.setAttribute("id",Integer.toString(id));
		node.setAttribute("matched",Boolean.toString(matched));
		node.setAttribute("score",Double.toString(score));
		node.setAttribute("matched_obj",matchedObj);
		parent.addContent(node);
	}
	
	private static void addEdge(String type, int sid, int did, Element parent) {
		Element edge = new Element("edge");
		edge.setAttribute("source",Integer.toString(sid));
		edge.setAttribute("target",Integer.toString(did));
		parent.addContent(edge);
	}
}
