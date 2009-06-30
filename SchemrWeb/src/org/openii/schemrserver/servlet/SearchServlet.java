package org.openii.schemrserver.servlet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
import org.openii.schemrserver.SchemaUtility;
import org.openii.schemrserver.search.MatchSummary;
import org.openii.schemrserver.search.SchemaSearch;

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String DESC = "desc";
	private static final String SCORE = "score";
	private static final String MATCHES = "matches";
	private static final String ENTITIES_ATTRIBUTES = "ea";

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
		
		MatchSummary [] msa = SchemaSearch.performSearch(searchTerms, schemaFile, schemaFragmentType, matchersOn);

		Element root = new Element("schemas");
		boolean first = true;
		if (msa!=null) {
			for (MatchSummary matchSummary : msa) {
				Schema schema = matchSummary.getSchema();			
				if (schema == null) throw new IllegalArgumentException("Schema must not be null");		
				Element resultElement = new Element("result");
				if (first){
					first = false;
					resultElement.setAttribute("results", Integer.toString(SchemaSearch.numResults));
				} else resultElement.setAttribute("results", "0");
				
				resultElement.setAttribute(ID, schema.getId().toString());
				resultElement.setAttribute(NAME, schema.getName().trim());
				resultElement.setAttribute(DESC, schema.getDescription().trim());

				Double s = matchSummary.getScore();
				String score = Double.toString(s);
				resultElement.setAttribute(SCORE, score.length() < 5 ? score : score.substring(0,5));			

				int matches = matchSummary.getNumMatches();
				resultElement.setAttribute(MATCHES, Integer.toString(matches));
				
				int entities = 0, attributes=0;
				for (SchemaElement se : SchemaUtility.getCLIENT().getGraph(schema.getId()).getElements(null)) {
					if (se instanceof Entity || se instanceof Containment) entities++;
					if (se instanceof Attribute) attributes++;
				}
				resultElement.setAttribute(ENTITIES_ATTRIBUTES, "" + entities + "/" + attributes);

				root.addContent(resultElement);
			}
		}
		Document doc = new Document(root);
		XMLOutputter serializer = new XMLOutputter();
		serializer.output(doc, response.getWriter());
		if (!Boolean.parseBoolean(matchers)){
			//Perform a second pass search if matchers are turned off to fully populate msa 
			msa = SchemaSearch.performSearch(searchTerms, schemaFile, schemaFragmentType, true);
		}
		for (MatchSummary matchSummary : msa) {
			userState.idToMatchSummary.put(matchSummary.getSchema().getId(), matchSummary);
		}
	}
}
