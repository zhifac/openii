package org.openii.schemrserver.servlet;

import org.openii.schemrserver.search.MatchSummary;
import org.openii.schemrserver.search.Query;


public class SessionState {

	public final String sessionId;

	public Query query;
	
	public MatchSummary[] matchSummaries;

	public String[] results;

	public SessionState(String id) {
		this.sessionId = id;
	}


}
