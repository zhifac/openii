package org.openii.schemrserver.servlet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.openii.schemrserver.SchemaUtility;
import org.openii.schemrserver.search.MatchSummary;
import org.openii.schemrserver.search.SchemaSearch;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String DESC = "desc";
	private static final String SCORE = "score";

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
	    String schemaFragmentType = request.getParameter("schemaFragmentType");
	    String schemaFragmentContent = request.getParameter("schemaFragmentContent");
	    String matchers = request.getParameter("matchers");
	    if (matchers==null) matchers = "true";
	    boolean matchersOn = Boolean.parseBoolean(matchers);
		HttpSession session = request.getSession();
		SessionState userState = (SessionState) session.getAttribute("sessionState");
		if (userState == null) {
			userState = new SessionState(session.getId());
			session.setAttribute("sessionState", userState);
		}

		File schemaFile = null;
		if (schemaFragmentType != null && !schemaFragmentType.trim().equals("") && schemaFragmentContent != null && !schemaFragmentContent.trim().equals("")) {
			schemaFile = File.createTempFile("schemafragment", "."+schemaFragmentType);
		    FileWriter fw = new FileWriter( schemaFile );
		    try {
		      fw.write( schemaFragmentContent );
		    }
		    finally {
		      fw.close();
		    }
		    System.out.println( "temp file: " + schemaFile.getAbsolutePath() );
		}
		
		MatchSummary [] matchSummaryArray = SchemaSearch.performSearch(searchTerms, schemaFile, schemaFragmentType, matchersOn);

		Element root = new Element("schemas");
		if (matchSummaryArray != null && matchSummaryArray.length > 0) {	
			for (MatchSummary matchSummary : matchSummaryArray) {
				Schema schema = matchSummary.getSchema();			
				if (schema == null) throw new IllegalArgumentException("Schema must not be null");		
				Element resultElement = new Element("result");
				resultElement.setAttribute(ID, schema.getId().toString());
				resultElement.setAttribute(NAME, schema.getName().trim());
				resultElement.setAttribute(DESC, schema.getDescription().trim());
				Double s = matchSummary.getScore();
				if (matchersOn && !searchTerms.contains(":")){ //adjust scores
					for (String q : searchTerms.split("\\s")){
						if (schema.getName().toLowerCase().contains(q)) s *= 2.0; //reward for schema name
						ArrayList<SchemaElement> se = SchemaUtility.getCLIENT().getGraph(schema.getId()).getElements(Entity.class);
						for (SchemaElement e : se){
							if (e.getName().toLowerCase().contains(q)) s *= 1+ 3.0/(3.75 + se.size()/4.0);  //reward for entity name
						}
					}
				}
				String score = s > 1.0 ? "1.0" : Double.toString(s);
				resultElement.setAttribute(SCORE, score.length() < 5 ? score : score.substring(0,5));			
				root.addContent(resultElement);
			}
			
			for (MatchSummary matchSummary : matchSummaryArray) {
				userState.idToMatchSummary.put(matchSummary.getSchema().getId(), matchSummary);
			}
		}

		Document doc = new Document(root);
		XMLOutputter serializer = new XMLOutputter();
		serializer.output(doc, response.getWriter());

		// WTF IS THIS?! comment your code.
//		if (!Boolean.parseBoolean(matchers)){
//			msa = SchemaSearch.performSearch(searchTerms, schemaFile);
//		}
		
	}
}
