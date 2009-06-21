package org.openii.schemrserver.servlet;

import java.util.HashMap;
import java.util.Map;

import org.openii.schemrserver.search.MatchSummary;
import org.openii.schemrserver.search.Query;


public class SessionState {

	public final String sessionId;

	public Query query;
	
//	public MatchSummary[] matchSummaries;
	public Map<Integer, MatchSummary> idToMatchSummary;

	public String[] results;

	public SessionState(String id) {
		this.sessionId = id;
		this.idToMatchSummary = new HashMap<Integer, MatchSummary>();
	}


}
