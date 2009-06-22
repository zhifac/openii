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
    
		HttpSession session = request.getSession();
		SessionState userState = (SessionState) session.getAttribute("sessionState");
		if (userState == null) {
			userState = new SessionState(session.getId());
			session.setAttribute("sessionState", userState);
		}

		MatchSummary [] msa = SchemaSearch.performSearch(searchTerms, null);

		Element root = new Element("schemas");		
		for (MatchSummary matchSummary : msa) {
			Schema schema = matchSummary.getSchema();			
			if (schema == null) throw new IllegalArgumentException("Schema must not be null");		
			Element resultElement = new Element("result");
			resultElement.setAttribute(ID, schema.getId().toString());
			resultElement.setAttribute(NAME, schema.getName().trim());
			resultElement.setAttribute(DESC, schema.getDescription().trim());
			resultElement.setAttribute(SCORE, Double.toString(matchSummary.getScore()));			
			root.addContent(resultElement);
			
			userState.idToMatchSummary.put(schema.getId(), matchSummary);
		}
		
		Document doc = new Document(root);
		XMLOutputter serializer = new XMLOutputter();
		serializer.output(doc, response.getWriter());
	}
}
