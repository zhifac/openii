package org.mitre.openii.editors.schemas.explorer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/**
 * This class generates HTML files that represent snippets of documentation about schema elements in
 * the schemastore.  It is tied to the ExplorerView.
 *
 * <p>This is not a servlet, but behaves like one.  The core function is generatePage(), which generates an
 * HTML file for a given identifier.  Later, we will have to adapt this into a bona-fide servlet that can operate 
 * through tomcat or some such.
 * 
 * @author DMALLEN
 * @see org.mitre.openii.editors.schemas.ExplorerView
 */
public class SchemaExplorer {
	/** Scores below this will not be shown to the user. */
	public static double MATCH_SCORE_CUTOFF = 0.35;	
	
	/** Scores below this threshold will be considered "weak" matches **/
	public static double LOW_SCORE_CUTOFF = 0.5;
	
	/** Scores below this threshold will be considered "medium" strength matches **/
	public static double MEDIUM_SCORE_CUTOFF = 0.67; 
		
	/** Each instance is tied to a particular editor window and needs a unique ID **/
	protected int editorID = 0; 
	
	// TODO: Too windows-centric.  Won't work on mac/linux
	public static String STORAGE_DIRECTORY = "c:\\openii-tmp";

	static { 
		String os = System.getProperty("os.name").toLowerCase();
		String user = System.getProperty("user.name");

		if(user == null || "".equals(user)) user = "anon";
		
		if(os.contains("win")) { 
			STORAGE_DIRECTORY = "c:\\openii-tmp";
		} else {
			STORAGE_DIRECTORY = "/tmp/openii-" + user; 
		}
		
		File f = new File(STORAGE_DIRECTORY);
		if(!f.exists()) f.mkdir();		
	} // End static
	
	public SchemaExplorer() { 
		editorID = new java.util.Random().nextInt() % 1000;
		if(editorID < 0) editorID *= -1; 
	} // End SchemaExplorer
		
	/**
	 * Take a schema element, and return all mappingcells that mention that schema element.
	 * <p>This is a very complicated way of doing this - maybe talk to CWOLF later about a more direct
	 * way by implementing a new schemastoreclient call.  For now, if it works, this is OK.
	 * @param e a SchemaElement
	 * @return a list of mapping cells that reference that schemaelement.
	 */
	public ArrayList <MappingCell> getMappingCells(SchemaElement e) { 
		ArrayList <MappingCell> arr = new ArrayList <MappingCell>();
		int schemaID = e.getBase(); 		
		
		// Look through all mappings in the system to see which ones talk about the schema that
		// contains the element we care about.
		
		for(Project project : OpenIIManager.getProjects())
		{
			// Don't proceed if project doesn't use schema
			if(!Arrays.asList(project.getSchemaIDs()).contains(schemaID)) continue;
			
			// Cycle through all project mappings
			for(Mapping m : OpenIIManager.getMappings(project.getId()))
			{
				// Don't proceed if mapping doesn't reference current schema
				if(schemaID != m.getSourceId() && schemaID != m.getTargetId()) continue;
			
				// We know this mapping is relevant to the schema we want.
				ArrayList <MappingCell> mcs = OpenIIManager.getMappingCells(m.getId());
				System.out.println("Got " + mcs.size() + " mapping cells in relevant mapping."); 
			
				// Check each of the mapping cells in this mapping.  Add the cell to our list if it
				// references the element we care about.
				for(MappingCell mc : mcs)
				{
					if(mc.getScore() < SchemaExplorer.MATCH_SCORE_CUTOFF) continue;
					
					if(mc.getOutput().equals(e.getId()))
					{
						System.out.println("Added mapping cell " + mc + " on output check. "); 
						arr.add(mc);
						continue; 
					}
					
					Integer [] inputs = mc.getElementInputIDs();
					for(int x=0; x<inputs.length; x++)
						if(inputs[x].equals(e.getId()))
						{
							System.out.println("Added mapping cell " + mc + " on input check."); 
							arr.add(mc);
							break;
						}
				}
			}
		}
		
		// Viola.  We have our list.
		System.out.println("Overall: found " + arr.size() + " relevant mapping cells.");
		
		Collections.sort(arr, new Comparator<MappingCell>() {
			// Sort going from high to low.
			public int compare(MappingCell arg0, MappingCell arg1) {
				double d1 = arg0.getScore();
				double d2 = arg1.getScore();
				if(d1 > d2) return -1;
				if(d1 < d2) return 1;
				return 0;
			} // End compare			
		}); 
		
		return arr; 
	} // End 
	
	/**
	 * Format an individual mapping cell as a snippet of HTML
	 * @param source one schemaelement incident to the mappingcell that is being displayed.
	 * @param cell the mapping cell to render as HTML.
	 */
	public String formatMapping(SchemaElement source, MappingCell cell) throws RemoteException {
		int other;
		
		if(cell.getElementInputIDs()[0].equals(source.getId())) other = cell.getOutput();
		else other = cell.getElementInputIDs()[0];
		
		SchemaStoreClient client = RepositoryManager.getClient();		
		SchemaElement e = client.getSchemaElement(other); 
		
		int schemaID = e.getBase(); 
		SchemaInfo schemaInfo = OpenIIManager.getSchemaInfo(schemaID);
		Schema schema = schemaInfo.getSchema();

		String assess = "strong";
		if(cell.getScore() < SchemaExplorer.LOW_SCORE_CUTOFF) assess = "weak";
		if(cell.getScore() < SchemaExplorer.MEDIUM_SCORE_CUTOFF) assess = "medium";
		
		
		
		String score = String.format("%.2f", cell.getScore()); 
		String notes = cell.getNotes();
		if(notes == null || "".equals(notes)) notes = "&nbsp;";
		
		return new String("<tr><td>" + 
				          linkThing(other, (e == null ? "Unknown" : e.getName())) + "</td><td>" +
				          score + "</td><td>" + assess + "</td><td>" +				          
				          linkThing(schema.getId(), schema.getName()) + "</td><td>" + 
				          notes + "</td>" + 
				          "</tr>"); 
	} // End formatMapping

	/**
	 * Generate an HTML link that works given the OpenII HTML scheme.
	 * @param elementID the ID of the element being linked to
	 * @param name the text of the link.
	 * @return an HTML link
	 */
	private String linkThing(Integer elementID, String name) { 
		return "<a href='" + urlForElement(elementID) + "'>" + 
		       (name == null || "".equals(name) ? "(Unknown)" : name) + 
		       "</a>";
	} // End linkThing
	
	private String buildHTMLSchemaTree(HierarchicalSchemaInfo hsi) { 
		StringBuffer b = new StringBuffer("");
		b.append("<h2>Partial Schema Tree</h2>"); 
		b.append("<div id='markup'>\n");
		ArrayList <SchemaElement> roots = hsi.getRootElements();
		
		b.append("<ul>\n");
		int x=0;
		for(SchemaElement root : roots) { 
			b.append(buildHTMLSchemaTree(hsi, root, 10, ++x)); 
		}
		b.append("</ul>\n"); 
		b.append("</div>");

		b.append("<script type='text/javascript'>");
		b.append("var tree1;\n");
		b.append("(function() {\n");
			b.append("var treeInit = function() {\n");
				b.append("tree1 = new YAHOO.widget.TreeView('markup');\n");
				b.append("tree1.render();\n");
				b.append("tree1.subscribe('dblClickEvent',tree1.onEventEditNode);\n");
			b.append("};\n"); 
		    b.append("YAHOO.util.Event.onDOMReady(treeInit);\n");
		b.append("})();\n");
		b.append("</script>\n");

		return b.toString();
	} // End buildHTMLSchemaTree
	
	private String buildHTMLSchemaTree(HierarchicalSchemaInfo hsi, 
			                           SchemaElement departurePoint, 
			                           int totalCap, int soFar) {
		if(soFar >= totalCap) return "";  // Throttle -- no more than totalCap elements.
		
		StringBuffer b = new StringBuffer("");
		
		ArrayList <SchemaElement> children = hsi.getChildElements(departurePoint.getId());
		
		if(children.size() <= 0) { 
			b.append("<li>" + linkThing(departurePoint.getId(), departurePoint.getName()) + "</li>\n");
			return b.toString();
		} // End if
		
		b.append("<li>" + linkThing(departurePoint.getId(), departurePoint.getName()));
		b.append("<ul>");
		
		soFar++; 
		for(SchemaElement child : children) { 
			b.append(buildHTMLSchemaTree(hsi, child, totalCap, ++soFar));
		} // End for
		b.append("</ul>");
		b.append("</li>\n");
		
		return b.toString();
	} // End buildHTMLSchemaTree
	
	/**
	 * Format a series of mapping cells as HTML.
	 * @param source the schemaelement being displayed to the user.
	 * @param cells a list of mapping cells that contain that schema element.
	 * @return an HTML string.
	 */
	public String formatMappings(SchemaElement source, ArrayList <MappingCell> cells) { 
		StringBuffer b = new StringBuffer("");
		
		b.append("<h2>Mappings</h2>");
		
		if(cells.size() <= 0) {
			b.append("<p>No known mappings.</p>");
			return b.toString();
		} // End if
		
		b.append("<table border='1'>");
		b.append("<tr><th>Target</th><th>Score</th><th>Match Strength</th><th>Source Schema</th><th>Notes</th></tr>");
		try { 
			for(MappingCell mc : cells) {
				b.append(formatMapping(source, mc)); 			
			} // End	 for
		} catch(Exception e) { 
			System.err.println("Error inspecting mapping cell");
			e.printStackTrace(); 
		} // End catch
		
		b.append("</table>"); 		
		return b.toString(); 
	} // End formatMappings
	
	public String formatElements(String name, ArrayList <SchemaElement> elements) { 
		StringBuffer b = new StringBuffer("");
		Hashtable <Integer,Boolean> seen = new Hashtable<Integer,Boolean>();
		
		Collections.sort(elements, new Comparator<SchemaElement>() {
			public int compare(SchemaElement arg0, SchemaElement arg1) {
				return arg0.getName().compareTo(arg1.getName()); 
			}			
		});
		
		b.append("<h2>" + name + "</h2><p>");
		
		if(elements.size() <= 0) {
			b.append("(None)</p>");
			return b.toString();
		} // End if
		
		b.append("<ul>");
		for(SchemaElement e : elements) {
			if(seen.containsKey(e.getId())) continue; 
			b.append("<li>" + linkThing(e.getId(), e.getName())); 
			//String d = e.getDescription(); 
			//if(d != null && !"".equals(d.trim())) { 
			//	b.append("&nbsp;<small>(" + d + ")</small>"); 
			//} // End if			
			b.append("</li>");
			seen.put(e.getId(), new Boolean(true));
		} // End for
		b.append("</ul></p>"); 
		return b.toString();
	} // End formatElements
	
	/**
	 * Given a particular element ID, determine where the HTML documentation should be written.
	 * @param elementID the element
	 * @return a File.  
	 */
	private File fileForElement(Integer elementID) { 
		return new File(SchemaExplorer.STORAGE_DIRECTORY +
					    File.separatorChar +
				        editorID + "-" + elementID + ".html"); 
	} // End fileForElement
	
	/**
	 * Given a particular element ID, determine the URL the browser should be pointed towards.
	 * @param elementID the element ID
	 * @return a URL string.
	 */
	public String urlForElement(Integer elementID) {
		File f = fileForElement(elementID); 
		String fullPath = f.getAbsolutePath();
		
		// Triple slash is required for local URIs.  This relates back to the
		// URI spec; the third slash indicates the root of the windows FS.
		// Oddly enough, "C:" is considered to be under this "root".
		// Some browsers are OK with a double slash, but triple slash is more
		// correct for the URL specification.
		return new String("file:///" + fullPath.replaceAll("\\\\", "\\/"));    
	} // End urlForElement
	
	/**
	 * Generate a documentation page for a given element ID
	 * @param eid the ID of either a schema element or a schema ID.
	 * @return a URL suitable for loading in a browser where the documentation can be viewed.
	 */
	public String generatePage(Integer eid) { 
		if(eid == null) { 
			System.err.println("Can't generate page for element ID null."); 
			return null;
		}
		
		File f = fileForElement(eid);  
		BufferedWriter out; 
	
		try {
		System.out.println("Generating HTML page for " + eid + "...");
		
		SchemaStoreClient client = RepositoryManager.getClient();		
		SchemaElement e = client.getSchemaElement(eid); 
		
		Integer schemaID = eid;
		// If the element is not null, then this isn't a schema.  Get the ID of the schema it belongs to.
		if(e != null) schemaID = e.getBase(); 
		
		SchemaInfo schemaInfo = OpenIIManager.getSchemaInfo(schemaID);
		Schema schema = schemaInfo.getSchema();
			
		System.out.println("Getting HSI"); 
		HierarchicalSchemaInfo hsi = new HierarchicalSchemaInfo(schemaInfo, null);
		
		ArrayList <ArrayList<SchemaElement>> paths = hsi.getPaths(eid); 
		
		StringBuffer bpath = new StringBuffer("");
		if(paths.size() > 0) { 
			for(int x=0; x<paths.get(0).size(); x++) {
				SchemaElement pe = paths.get(0).get(x);
				bpath.append(linkThing(pe.getId(), pe.getName()));
				if(x < paths.get(0).size() - 1)  bpath.append(" &gt;&gt; ");
			} // End for
		} // End if		
		
		SchemaElement domain = null;
		ArrayList <SchemaElement> domainShared = null;
		String cls = null;
		if(e != null) {
			System.out.println("ELEMENT TYPE: " + e.getClass().getSimpleName());
			domain = hsi.getDomainForElement(e.getId());
			if(domain != null) domainShared = hsi.getElementsForDomain(domain.getId());
			cls = e.getClass().getSimpleName();
		} // End if
		
		String name = "Unknown";
		if(e != null) name = e.getName(); else if(schema != null) name = schema.getName(); 
		String descr = "(None)";
		if(e != null) descr = e.getDescription(); else if(schema != null) descr = schema.getDescription();
				 
	    out = new BufferedWriter(new FileWriter(f));
	    
	    String pageTitle = ((schema == null ? "Unknown Schema" : schema.getName()) + 
	    		            (e == null ? "" : " - " + e.getName()));
	    out.write(header(pageTitle));
	    	    
	    out.write("<div id='hd'>");
	    out.write("<h1>" + 
	    		  (e == null ? "Schema" : "Element") + " " + 
	    		  name + 
	    		  (cls == null ? "" : " (" + cls + ")") + 
	    		  "</h1>");
	    if(!"".equals(bpath.toString())) out.write("<p><b>Path</b>: " + bpath.toString()); 
	    out.write("<p><b>Documentation</b>: " + 
	    		  ("".equals(descr) ? "No documentation available." : descr));
	    if(domain != null) {
	    	out.write("<p><b>Domain</b>: " + domain.getName());
	    	
	    	if(domainShared != null && domainShared.size() > 1) { 
		    	StringBuffer tbuf = new StringBuffer("");
		    	tbuf.append("&nbsp;/&nbsp;Other elements with this domain: ");
		    	for(int x=0; x<domainShared.size(); x++) {		    		
		    		SchemaElement dse = domainShared.get(x);
		    		if(dse.getId().equals(e.getId())) continue; 
		    		tbuf.append(linkThing(dse.getId(), dse.getName()));
		    		if(x < (domainShared.size()-1)) tbuf.append(", "); 
		    		if(x > 8 && x<(domainShared.size()-1)) { 
		    			tbuf.append("(" + (domainShared.size()-8) + " others...)"); 
		    			break; 
		    		} // End if 		    		
		    	} // End if
		    	
		    	out.write(tbuf.toString()); 
	    	} // End if
	    } // End if
		    		      
	    if(e != null && schema != null) out.write("<p><b>Parent model</b>: " + 
	    		                                  linkThing(schema.getId(), schema.getName()));
	    out.write("</div>"); 
	    	 
	    out.write("<div id='bd'>\n");
	    out.write("<div id='yui-main'>\n" +	                  
                  "<div class='yui-b'>\n"); 
	   		    		    
    	if(e != null) {    		
    		out.write(formatMappings(e, getMappingCells(e)));
    	} else { 
    		out.write(buildHTMLSchemaTree(hsi)); 
    	}	        
	   	out.write("</div> <!-- End yui-b -->\n</div> <!-- End yui-main -->\n");
		    
	   	out.write("<div class='yui-b'>\n");    
	    out.write(formatElements("Parents", hsi.getParentElements(eid)));  
	    out.write(formatElements("Children", hsi.getChildElements(eid)));
	    if(e == null) out.write(formatElements("Root Elements", hsi.getRootElements()));	    	
	   	out.write("</div> <!-- End yui-b -->\n");
	    
	    out.write(footer());  
	    out.close();
		} catch(Exception exc) { 
			System.err.println("Could not write " + f + "!"); 
			exc.printStackTrace();
		} // End catch
		
		return urlForElement(eid); 
	} // End generatePage
	
	public static Integer elementIDFromURL(String url) {
		Pattern p = Pattern.compile("\\-(\\-?\\d+).html", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(url); 
		if(m.find()) return Integer.parseInt(m.group(1)); 
		else {
			System.err.println("No EID match in " + url + "!"); 
			return null;
		}
	} // End elementIDFromURL
	
	private String header(String title) {
		// The CSS references are to the Yahoo CSS Base library that is part of YUI.
		// For more information, see http://developer.yahoo.com/yui/base/
		return new String("<html><head><title>" + title + "</title></head>" + 
		 "<link rel='stylesheet' type='text/css' "+
		 "href='http://yui.yahooapis.com/combo?2.8.0r4/build/reset-fonts-grids/reset-fonts-grids.css&2.8.0r4/build/base/base-min.css&2.8.0r4/build/treeview/assets/skins/sam/treeview.css'>"+ 
		 "<script type='text/javascript' "+
		 "src='http://yui.yahooapis.com/combo?2.8.0r4/build/yahoo-dom-event/yahoo-dom-event.js&2.8.0r4/build/treeview/treeview-min.js'></script>"+ 	
         "<body>" +
         "<div id='doc3' class='yui-t5'>"); 
	} // End header
	
	private String footer() { 
		return new String("</div>" + 
				          "<div id='ft'>&nbsp;</div></div></body></html>");
	} // End footer	
} // End ExplorerPreferences
