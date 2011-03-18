<%@page import="model.MatchedTerms"%>
<%@page import="utils.SearchUtils"%>
<%@page import="org.mitre.schemastore.data.DataCache"%>
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
	// Refresh the data if needed
	if(request.getParameter("refresh")!=null)
		CachedData.refresh();
	
	// Retrieve the matched terms
	String item = request.getParameter("item");
	MatchedTerms matchedTerms = SearchUtils.getItems(item);
	
	// Output the found terms
	for(Integer supplierID : matchedTerms.getSuppliers())
		for(Integer elementID : matchedTerms.getItems(supplierID))
		{ 
			SchemaElement element = CachedData.getSchemaElement(elementID);
		    %>
		      <supplier>
			    <name><%= CachedData.getSchema(supplierID).getName() %></name>
			    <supply><%= element.getName() %></supply>
			    <amount><%= element.getDescription() %></amount>  
		      </supplier>
			<%
		}
%>