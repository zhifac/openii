<%@page import="model.CachedData"%><%@page import="utils.SearchUtils"%><%@page import="model.MatchedTerms"%><%@page contentType="text/xml;" %><%@page import="org.mitre.schemastore.client.Repository"%><%@page import="java.net.URI"%><%@page import="org.mitre.schemastore.client.SchemaStoreClient"%><%@page import="org.mitre.schemastore.model.Schema"%><%@page import="model.ClientManager"%><%@page import="java.util.ArrayList"%><%@page import="org.mitre.schemastore.model.SchemaElement"%><%@page import="com.sun.syndication.feed.synd.SyndFeed"%><%@page import="com.sun.syndication.feed.synd.SyndFeedImpl"%><%@page import="com.sun.syndication.feed.synd.SyndEntry"%><%@page import="java.awt.List"%><%@page import="java.util.HashSet"%><%@page import="com.sun.syndication.feed.synd.SyndEntryImpl"%><%@page import="java.util.Date"%><%@page import="com.sun.syndication.feed.synd.SyndContent"%><%@page import="com.sun.syndication.feed.synd.SyndContentImpl"%><%@page import="com.sun.syndication.io.SyndFeedOutput"%><%@page import="org.mitre.schemastore.model.schemaInfo.SchemaInfo"%><%

	// Refresh the data if needed
	if(request.getParameter("refresh")!=null)
		CachedData.refresh();

	// Generate the rss entries
	ArrayList<SyndEntry> entries = new ArrayList<SyndEntry>();
  	String item = request.getParameter("item");
	MatchedTerms matchedTerms = SearchUtils.getItems(item);
 	
	// Output the found terms
	for(Integer supplierID : matchedTerms.getSuppliers())
	{
		Schema schema = CachedData.getSchema(supplierID);
		
		// Create the entry
		SyndEntry entry = new SyndEntryImpl();
		entry.setTitle(schema.getName());
		entry.setLink(request.getRequestURL().toString().replace("suppliesFeed.jsp","displaySupplier.jsp?id=" + schema.getId()));		
		entry.setAuthor(schema.getName()); 

		// Store the entry description
		SyndContent content = new SyndContentImpl();
		content.setType("text/html");
		String descr = "";
		for(Integer elementID : matchedTerms.getItems(supplierID))
		{ 
			SchemaElement element = CachedData.getSchemaElement(elementID);
			descr += "<p>" + element.getName().replaceAll("(?i)(" + item + ")","<b>$1</b>") +
					 " (" + element.getDescription() + ")</p>";
		}
		content.setValue(descr); 
		entry.setDescription(content);
	
		// Store the entry
		entries.add(entry);
	}
	
	// Generate the feed
	SyndFeed feed = new SyndFeedImpl();
	feed.setTitle("Relief Supplies Search");
	feed.setDescription("Feed of Relief Supplies (" + (item==null?"":item) + ")");
	feed.setLanguage("en-us" );
	feed.setPublishedDate(new Date(System.currentTimeMillis()));
	feed.setLink(request.getRequestURL().toString()); 
	feed.setUri(request.getRequestURL().toString()); 
	feed.setCopyright("(C) MITRE Corporation 2010"); 
	feed.setFeedType("rss_2.0");
	feed.setEntries(entries);

	/** Output the feed */
	SyndFeedOutput output = new SyndFeedOutput();
	out.write(output.outputString(feed)); 
%>