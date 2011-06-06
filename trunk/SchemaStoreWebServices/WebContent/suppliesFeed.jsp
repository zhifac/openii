<%@page import="org.mitre.schemastore.model.AssociatedElement"%><%@page import="org.mitre.schemastore.model.Term"%><%@page import="java.util.Arrays"%><%@page import="org.jdom.Element"%><%@page import="utils.ServletUtils"%><%@page import="model.CachedData"%><%@page import="utils.SearchUtils"%><%@page import="model.MatchedTerms"%><%@page contentType="text/xml;" %><%@page import="org.mitre.schemastore.client.Repository"%><%@page import="java.net.URI"%><%@page import="org.mitre.schemastore.client.SchemaStoreClient"%><%@page import="org.mitre.schemastore.model.Schema"%><%@page import="model.ClientManager"%><%@page import="java.util.ArrayList"%><%@page import="org.mitre.schemastore.model.SchemaElement"%><%@page import="com.sun.syndication.feed.synd.SyndFeed"%><%@page import="com.sun.syndication.feed.synd.SyndFeedImpl"%><%@page import="com.sun.syndication.feed.synd.SyndEntry"%><%@page import="java.awt.List"%><%@page import="java.util.HashSet"%><%@page import="com.sun.syndication.feed.synd.SyndEntryImpl"%><%@page import="java.util.Date"%><%@page import="com.sun.syndication.feed.synd.SyndContent"%><%@page import="com.sun.syndication.feed.synd.SyndContentImpl"%><%@page import="com.sun.syndication.io.SyndFeedOutput"%><%@page import="org.mitre.schemastore.model.schemaInfo.SchemaInfo"%><%

	// Refresh the data if needed
	if(request.getParameter("refresh")!=null)
		CachedData.refresh();

	// Generate the rss entries
	ArrayList<SyndEntry> entries = new ArrayList<SyndEntry>();
  	String item = request.getParameter("item");
  	
  	// Find the associated term(s)
	ArrayList<Term> terms = new ArrayList<Term>();
  	for(Term term : CachedData.getTerms())
  		if(term.getName().equals(item)) terms.add(term);

	// Defines the entry type
	Element entryType = new Element("type","rem","http://simplec2.mitre.org");
	entryType.setText("Supplies");
  	
  	// Display the matched items
  	for(Term term : terms)
  		for(AssociatedElement associatedElement : term.getElements())
  		{
			// Retrieve the schema and element
  			Schema schema = CachedData.getSchema(associatedElement.getSchemaID());
			SchemaElement element = CachedData.getSchemaElement(associatedElement.getElementID());
 
			// Defines the entry location
			Element entryLoc = new Element("point","georss","http://www.georss.org/georss");
			entryLoc.setText(schema.getDescription().replaceAll(".*:","").trim());		
			
 			// Create the entry
  			SyndEntry entry = new SyndEntryImpl();
  			entry.setTitle(element.getName() + " (" + element.getDescription() + ")");
  			entry.setLink(ServletUtils.getSuppliesURL(request,schema.getId(),element.getId()));		
  			entry.setAuthor(schema.getName()); 
  			entry.setForeignMarkup(Arrays.asList(entryType,entryLoc));

  			// Set the description
  			SyndContent content = new SyndContentImpl();
  			content.setType("text/html");
  			content.setValue("<p>Supplier: " + schema.getName() + "<p>Amount: " + element.getDescription());
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
	feed.setLink(ServletUtils.getMainPageURL(request)); 
	feed.setUri(ServletUtils.getMainPageURL(request)); 
	feed.setCopyright("(C) MITRE Corporation 2010"); 
	feed.setFeedType("rss_2.0");
	feed.setEntries(entries);

	/** Output the feed */
	SyndFeedOutput output = new SyndFeedOutput();
	out.write(output.outputString(feed)); 
%>