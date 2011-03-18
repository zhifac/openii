<%@page import="model.CachedData"%>
<%@page import="org.mitre.schemastore.model.SchemaElement"%>
<%@page import="org.mitre.schemastore.model.Schema"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.mitre.schemastore.model.AssociatedElement"%>
<%@page import="org.mitre.schemastore.model.Project"%>
<%@page import="org.mitre.schemastore.model.Term"%>
<%@page import="org.mitre.schemastore.model.Vocabulary"%>
<%@page import="model.ClientManager"%>
<%@page import="org.mitre.schemastore.client.SchemaStoreClient"%>
<%
	// Get the searched for item
	String type = request.getParameter("type");
	String item = request.getParameter("item");
	boolean refresh = request.getParameter("refresh")!=null;

	// Fetch the vocabulary
	Vocabulary vocabulary = CachedData.getVocabulary(type);
	if(vocabulary==null || refresh)
		vocabulary = CachedData.refreshVocabulary(type);
		
	// First pass only retrieves items whose terms match the specified item
	ArrayList<Term> terms = new ArrayList<Term>();
	TERM: for(Term term : vocabulary.getTerms())
	{
		if(term.getName().equalsIgnoreCase(item)) { terms.add(term); continue; }
		for(AssociatedElement element : term.getElements())
			if(element.getName().equalsIgnoreCase(item)) { terms.add(term); continue TERM; }
	}

	// Second pass finds all close matches to the specified item
	if(terms.size()==0)
	{
		item = "(?i).*\\b" + item + "\\b.*";
		TERM2: for(Term term : vocabulary.getTerms())
		{
			if(term.getName().matches(item)) { terms.add(term); continue; }
			for(AssociatedElement element : term.getElements())
				if(element.getName().matches(item)) { terms.add(term); continue TERM2; }
		}
	}
		
	// Output the found terms
	for(Term term : terms)
		for(AssociatedElement associatedElement : term.getElements())
		{ 
			SchemaElement element = CachedData.getSchemaElement(associatedElement.getElementID());
		    %>
		      <supplier>
			    <name><%= CachedData.getSchemaName(associatedElement.getSchemaID()) %></name>
			    <supply><%= element.getName() %></supply>
			    <amount><%= element.getDescription() %></amount>  
		      </supplier>
		<% }
%>