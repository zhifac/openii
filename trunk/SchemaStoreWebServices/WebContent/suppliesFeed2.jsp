<%@page import="java.util.Arrays"%><%@page import="org.jdom.Element"%><%@page import="utils.ServletUtils"%><%@page import="model.CachedData"%><%@page import="utils.SearchUtils"%><%@page import="model.MatchedTerms"%><%@page contentType="text/xml;" %><%@page import="org.mitre.schemastore.client.Repository"%><%@page import="java.net.URI"%><%@page import="org.mitre.schemastore.client.SchemaStoreClient"%><%@page import="org.mitre.schemastore.model.Schema"%><%@page import="model.ClientManager"%><%@page import="java.util.ArrayList"%><%@page import="org.mitre.schemastore.model.SchemaElement"%><%@page import="com.sun.syndication.feed.synd.SyndFeed"%><%@page import="com.sun.syndication.feed.synd.SyndFeedImpl"%><%@page import="com.sun.syndication.feed.synd.SyndEntry"%><%@page import="java.awt.List"%><%@page import="java.util.HashSet"%><%@page import="com.sun.syndication.feed.synd.SyndEntryImpl"%><%@page import="java.util.Date"%><%@page import="com.sun.syndication.feed.synd.SyndContent"%><%@page import="com.sun.syndication.feed.synd.SyndContentImpl"%><%@page import="com.sun.syndication.io.SyndFeedOutput"%><%@page import="org.mitre.schemastore.model.schemaInfo.SchemaInfo"%><%

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
		
		// Defines the entry type
		Element entryType = new Element("type","rem","http://simplec2.mitre.org");
		entryType.setText("Supplies");
		
		// Cycle through matched items
		for(Integer elementID : matchedTerms.getItems(supplierID))
		{
			// Retrieve the item
			SchemaElement element = CachedData.getSchemaElement(elementID);
			
			// Create the entry
			SyndEntry entry = new SyndEntryImpl();
			entry.setTitle(element.getName());
			entry.setLink(ServletUtils.getSupplierURL(request,schema.getId()));		
			entry.setAuthor(schema.getName()); 
			entry.setForeignMarkup(Arrays.asList(entryType));

			// Set the description
			SyndContent content = new SyndContentImpl();
			content.setType("text/html");
			content.setValue("<p>Supplier: " + schema.getName() + "<p>Amount: " + element.getDescription());
			entry.setDescription(content);

			// Store the entry
			entries.add(entry);
		}
	}
	
	// Generate the feed
	SyndFeed feed = new SyndFeedImpl();
	feed.setTitle("Relief Supplies Search");
	feed.setDescription("Feed of Relief Supplies (" + (item==null?"":item) + ")");
	feed.setLanguage("en-us" );
	feed.setPublishedDate(new Date(System.currentTimeMillis()));
	feed.setLink(ServletUtils.getMainPageURL(request)); 
	feed.setUri(ServletUtils.getMainPageURL(request)); 
	feed.setCopyright("(C) MITRE Corporation 2010"); 
	feed.setFeedType("rss_2.0");
	feed.setEntries(entries);

	/** Output the feed */
	SyndFeedOutput output = new SyndFeedOutput();
	out.write(output.outputString(feed)); 
%>