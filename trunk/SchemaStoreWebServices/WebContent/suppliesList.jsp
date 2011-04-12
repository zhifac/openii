<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="model.CachedData"%>
<%@page import="org.mitre.schemastore.model.Term"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="model.ClientManager"%>
<%@page import="org.mitre.schemastore.model.Vocabulary"%>
<%@page import="org.mitre.schemastore.client.SchemaStoreClient"%>
<%
	out.println("<terms>");

	// Retrieve the list of vocabulary terms
	for(Term term : CachedData.getTerms())
		out.println("<term>" + term.getName() + "</term>");

	out.println("</terms>");
%>