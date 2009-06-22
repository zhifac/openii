package org.openii.schemrserver.servlet;

import java.io.IOException;
import java.rmi.RemoteException;
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
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
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
						matched, score, matchedObj, graph);

				// TODO: assume all entities connect with schema
				addEdge("Relationship", schema.getId(), e.getId(), graph);
				
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
							matchedObj, graph);
					addEdge("Relationship", e.getId(), se.getId(), graph);
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
				addNode(c.getName().trim(), c.getDescription().trim(), "Containment", c.getId(), matched, score, matchedObj, graph);
				System.out.println("Containment"+": "+c.getName().trim()+" "+c.getId());			
				int pid = c.getParentID();
				addEdge("Relationship", pid , c.getId(), graph);
				System.out.println("\tedge: "+pid+" <-> "+c.getId());
			}
		}
		
//		schemaXMLWriter.write("</graph>\n</graphml>");
		Document doc = new Document(root);
		XMLOutputter serializer = new XMLOutputter();
		serializer.output(doc, response.getWriter());

		serializer.output(doc, System.out);
	}

	
	private static void addNode(String name, String desc, String type, int id,
			boolean matched, double score, String matchedObj, Element graph) {
//		try {
//			f.write("<node id=\"" + id + "\">\n");
//			data("name", name, f);
//			data("desc", desc, f);
//			data("type", type, f);
//			data("id", "" + id, f);
//			data("matched", matched?"true":"false", f);
//			data("score", "" + score, f);
//			data("matched_obj", matchedObj, f);
//			f.write("</node>\n");
			
			Element node = new Element("node");
			node.setAttribute("name",name);
			node.setAttribute("desc",desc);
			node.setAttribute("type",type);
			node.setAttribute("id",Integer.toString(id));
			node.setAttribute("matched",Boolean.toString(matched));
			node.setAttribute("score",Double.toString(score));
			node.setAttribute("matched_obj",matchedObj);
			graph.addContent(node);
			
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
	System.out.println("Adding node\t"+name+"\t"+type+"\t"+id+"\t"+matched+"\t"+score);
	}
//	private static void data(String key, String value, StringWriter schemaXML) throws IOException{
//		schemaXML.write("\t<data key=\"" + key + "\">" + value + "</data>\n");
//	}
	
	private static void addEdge(String type, int sid, int did, Element graph) {
//		f.write("\n<edge source=\"" + sid + "\" target=\"" + did + "\"></edge>\n");
		Element edge = new Element("edge");
		edge.setAttribute("source",Integer.toString(sid));
		edge.setAttribute("target",Integer.toString(did));
		graph.addContent(edge);
	}
}
