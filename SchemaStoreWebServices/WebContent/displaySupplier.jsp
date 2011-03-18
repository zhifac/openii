<%@page import="org.mitre.schemastore.model.SchemaElement"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.mitre.schemastore.model.Schema"%>
<%@page import="model.ClientManager"%>
<%@page import="org.mitre.schemastore.client.SchemaStoreClient"%>
<html>
  <head>
    <style type="text/css">
    
      .title
      {
        font-size: 1.3em;
  		font-weight: normal;
        padding-bottom: 10px;
      }
    
      .spreadsheet table
      {
        border: 1px solid #bbddbb;
  		border-spacing: 1px;
	  }

	  .spreadsheet th
	  {
  		background-color: #D0E0DA;	
  		color: #444488;
  		font-size: 1.2em;
 		font-weight: normal;
  		padding: 1px 0px;
	  }

	  .spreadsheet td
	  {
   		padding-left: 3px;
  		padding-right: 10px;
	  }

	  .spreadsheet-type1
	  {
  		background-color: #e6e6f0;	
	  }

	  .spreadsheet-type2
	  {
 		background-color: #fcfcfc;	
	  }
    </style>
  </head>
  <body>
    <%
      // Get the referenced supplier
	  SchemaStoreClient client = ClientManager.getClient();
      Schema schema = null;
      ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>();
  	  try {  
	  	Integer schemaID = Integer.parseInt(request.getParameter("id"));
		schema = client.getSchema(schemaID);
		elements = client.getSchemaInfo(schema.getId()).getElements(null);
  	  } catch(Exception e) {}
    %>
    
    <!-- Display the supplier name -->
    <div class=title>Supplier: <%= schema==null ? "" : schema.getName() %></div>
    
    <!-- Display the spreadsheet of supplied goods -->
 	<div class=spreadsheet style="width:100%">
      <table>
		
		<!-- Display the headers -->
		<tr>
          <th>Item</th>
          <th>Amount</th>
        </tr>
		
		<!-- Display the list of supplied items -->
	    <%
	      int count=0;
		  for(SchemaElement element : elements)
		  { %>
		 
		    <tr class=spreadsheet-type<%= count++%2==0 ? "1" : "2" %>>
    	   	  <td><%= element.getName() %></td>
    	   	  <td><%= element.getDescription() %></td>
    	   	</tr>
		    
		  <% }
		%>

	  </table>
	</div>    
  </body>
</html>