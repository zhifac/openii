<%@page import="com.sun.syndication.feed.synd.SyndEntryImpl"%><%@page import="org.mitre.schemastore.model.SchemaElement"%><%@page import="java.util.HashSet"%><%@page import="org.mitre.schemastore.model.schemaInfo.SchemaInfo"%><%@page import="org.mitre.schemastore.model.Schema"%><%@page import="model.ClientManager"%><%@page import="org.mitre.schemastore.client.SchemaStoreClient"%><%@page import="com.sun.syndication.feed.synd.SyndEntry"%><%@page import="java.util.ArrayList"%><%@page import="com.sun.syndication.feed.synd.SyndContentImpl"%><%@page import="com.sun.syndication.feed.synd.SyndContent"%><%@page import="com.sun.syndication.feed.synd.SyndFeedImpl"%><%@page import="java.util.Date"%><%@page import="com.sun.syndication.feed.synd.SyndFeed"%><%@page import="com.sun.syndication.io.SyndFeedOutput"%><%@page contentType="text/xml;" %><%

	// Generate the rss entries
	ArrayList<SyndEntry> entries = new ArrayList<SyndEntry>();

	// Retrieve matched schema elements
	SchemaStoreClient client = ClientManager.getClient();
	for(Schema schema : client.getSchemas())
	{
		if(schema.getType().equals("Rescue Supplies Importer"))
		{			
			// Create the entry
			SyndEntry entry = new SyndEntryImpl();
			entry.setTitle(schema.getName());
			entry.setLink(request.getRequestURL().toString().replace("suppliersFeed.jsp","displaySupplier.jsp?id=" + schema.getId()));		
			entry.setAuthor(schema.getName()); 
			
			// Generate the entry description
			StringBuffer description = new StringBuffer();
			ArrayList<SchemaElement> elements = client.getSchemaInfo(schema.getId()).getElements(null);
			for(int i=0; i<8 && i<elements.size(); i++)
			{
				SchemaElement element = elements.get(i);
				description.append(element.getName() + "(" + element.getDescription() + "), ");
			}
			if(elements.size()>8) description.append("...");
			else description.replace(description.length()-2,description.length(),"");
			
			// Store the entry description
			SyndContent content = new SyndContentImpl();
			content.setType("text/html");
			content.setValue(description.toString()); 
			entry.setDescription(content);
	
			// Store the entry
			entries.add(entry);
		}
	}
	
	// Generate the feed
	SyndFeed feed = new SyndFeedImpl();
	feed.setTitle("Suppliers of Relief Items");
	feed.setDescription("Feed of suppliers supporting the relief effort");
	feed.setLanguage("en-us" );
	feed.setPublishedDate(new Date(System.currentTimeMillis()));
	feed.setLink("http://implus.mitre.org:8080/PLUSv2/"); 
	feed.setUri("http://implus.mitre.org:8080/PLUSv2/services/Search.jsp"); 
	feed.setCopyright("(C) MITRE Corporation 2010"); 
	feed.setFeedType("rss_2.0");
	feed.setEntries(entries);

	/** Output the feed */
	SyndFeedOutput output = new SyndFeedOutput();
	out.write(output.outputString(feed)); 
%>