<%@page contentType="text/xml;" %><%@page import="org.mitre.schemastore.client.Repository"%><%@page import="java.net.URI"%><%@page import="org.mitre.schemastore.client.SchemaStoreClient"%><%@page import="org.mitre.schemastore.model.Schema"%><%@page import="model.ClientManager"%><%@page import="java.util.ArrayList"%><%@page import="org.mitre.schemastore.model.SchemaElement"%><%@page import="com.sun.syndication.feed.synd.SyndFeed"%><%@page import="com.sun.syndication.feed.synd.SyndFeedImpl"%><%@page import="com.sun.syndication.feed.synd.SyndEntry"%><%@page import="java.awt.List"%><%@page import="java.util.HashSet"%><%@page import="com.sun.syndication.feed.synd.SyndEntryImpl"%><%@page import="java.util.Date"%><%@page import="com.sun.syndication.feed.synd.SyndContent"%><%@page import="com.sun.syndication.feed.synd.SyndContentImpl"%><%@page import="com.sun.syndication.io.SyndFeedOutput"%><%@page import="org.mitre.schemastore.model.schemaInfo.SchemaInfo"%><%

	// Generate the rss entries
	ArrayList<SyndEntry> entries = new ArrayList<SyndEntry>();
  	String keyword = request.getParameter("keyword");
	if(keyword!=null)
	{
		// Retrieve matched schema elements
		SchemaStoreClient client = ClientManager.getClient();
		ArrayList<SchemaElement> elements = client.getSchemaElementsForKeyword(keyword,null);
	
		// Identify the schemas associated with the matched schema elements
		HashSet<Integer> schemaIDs = new HashSet<Integer>();
		for(SchemaElement element : elements)
		{
			Integer baseID = element.getBase();
			schemaIDs.add(baseID);
			schemaIDs.addAll(client.getDescendantSchemas(baseID));
		}
	
		// Generate the feed entries
		for(Integer schemaID : schemaIDs)
		{ 
			// Retrieve the schema and matched schemas
			SchemaInfo schema = client.getSchemaInfo(schemaID);
			HashSet<String> matchedElements = new HashSet<String>();
			for(SchemaElement element : elements)
				if(schema.containsElement(element.getId()))
					matchedElements.add(element.getName() + " - " + element.getDescription());			
			
			// Create the entry
			SyndEntry entry = new SyndEntryImpl();
			entry.setTitle(schema.getSchema().getName());
			entry.setLink("http://implus.mitre.org:8080/OpenII/schemaExplorer.jsp?id=" + schema.getSchema().getId());		
			entry.setAuthor(schema.getSchema().getName()); 
			
			// Store the entry description
			SyndContent content = new SyndContentImpl();
			content.setType("text/html");
			String descr = "";
			for(String matchedElement : matchedElements)
				descr += "<p>" + matchedElement.replaceAll(keyword,"<b>"+keyword+"</b>") + "</p>";
			content.setValue(descr); 
			entry.setDescription(content);
	
			// Store the entry
			entries.add(entry);
		}
	}
	
	// Generate the feed
	SyndFeed feed = new SyndFeedImpl();
	feed.setTitle("Schema Store Search");
	feed.setDescription("Feed of schemas associated with the provided keyword");
	feed.setLanguage("en-us" );
	feed.setPublishedDate(new Date(System.currentTimeMillis()));
	feed.setLink("http://implus.mitre.org:8080/PLUSv2/"); 
	feed.setUri("http://implus.mitre.org:8080/PLUSv2/services/Search.jsp"); 
	feed.setCopyright("(C) MITRE Corporation 2010"); 
	feed.setFeedType("rss_2.0"); // set the type of your feed
	feed.setEntries( entries );

	/** Output the feed */
	SyndFeedOutput output = new SyndFeedOutput();
	out.write(output.outputString(feed)); 
%>