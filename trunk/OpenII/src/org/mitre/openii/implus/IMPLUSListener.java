package org.mitre.openii.implus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import org.mitre.implus.Metadata;
import org.mitre.implus.PLUSException;
import org.mitre.implus.PLUSUtils;
import org.mitre.implus.plusobject.PLUSActivity;
import org.mitre.implus.plusobject.PLUSEdge;
import org.mitre.implus.plusobject.PLUSInvocation;
import org.mitre.implus.plusobject.PLUSObject;
import org.mitre.implus.plusobject.PLUSString;
import org.mitre.implus.plusobject.PLUSWorkflow;
import org.mitre.implus.surrogate.SurrogateProvidedException;
import org.mitre.implus.user.User;
import org.mitre.openii.model.OpenIIListener;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.Tag;

/**
 * <p>This class is an OpenIIListener, that attaches itself to the OpenIIManager and logs provenance
 * or lineage of actions taken within OpenII.  For example, it will log the existence of various
 * schemas and mappings, as well as any modifications that happen, and when they happened.
 * <p>The result is a provenance graph of changes within the OpenII system that we hope will be
 * useful for some CCoD demonstrations and use cases.
 * <p>This class isn't likely to make much sense unless you're familiar with the IM-PLUS MSR.
 * @author DMALLEN
 */
public class IMPLUSListener implements OpenIIListener {
	public static final String TAG = "tag";
	public static final String SCHEMA = "schema";
	public static final String MAPPING = "mapping";
	public static final String DATASOURCE = "dataSource";
	
	/** Which user to use when loading and writing lineage records.  By default, it's the admin. */
	public static org.mitre.implus.user.User lineageUser = User.DEFAULT_USER_GOD; 
	
	/** Metadata tag we use in IMPLUS objects to indicate an OpenII integer identifier. */
	public static final String OPEN_II_ID = "openii:id";
	/** Metadata tag we use in IMPLUS objects to indicate an OpenII type (schema, mapping, etc). */
	public static final String OPEN_II_TYPE = "openii:type";
	/** Metadata tag we use in IMPLUS objects to indicate an OpenII repository URI. */
	public static final String OPEN_II_REPOSITORY = "openii:repository"; 
	
	/** OpenIIManager's list of OpenIIListeners is weak references only.  This means that if you 
	 * create a new instance of this object and feed it to addListener() it quickly dies for lack
	 * of a strong reference.  This object exists so that there is always a strong reference to at
	 * least one instance of this class.  That way, OpenII can use it without requiring that OpenII
	 * have a strong reference to it.
	 */
	public static IMPLUSListener primary = null;
	
	static { primary = new IMPLUSListener(); }
	
	/**
	 * Ye olde constructor.
	 */
	public IMPLUSListener() { 
		//System.out.println("IM-PLUS Listener created"); 
	}

	public PLUSObject getObjectByOID(String oid) {
		try { 
			try { return PLUSObject.load(oid, lineageUser); } 
			catch(SurrogateProvidedException exc) { return exc.getProvidedSurrogate(); }
		} catch(Exception e) { 
			e.printStackTrace(); 
			return null;
		}
	} // End getObjectByOID
	
	/**
	 * Used for generating context-specific custom processes.
	 * @param processName name of the process
	 * @param inputs what its inputs are
	 * @return the process node.  Caller should link intended output to this.
	 * @throws Exception
	 */
	public PLUSInvocation customProcess(String processName, PLUSObject [] inputs) throws Exception {
		if(processName == null || "".equals(processName)) processName = "UPDATE";
		
		PLUSInvocation proc = new PLUSInvocation(processName);
		proc.setWorkflowId(PLUSWorkflow.DEFAULT_WORKFLOW_OID);
		proc.setActivityId(PLUSActivity.UNKNOWN_ACTIVITY_OID);
				
		PLUSEdge [] edges = new PLUSEdge[inputs.length];
		for(int x=0; x<inputs.length; x++) {
			if(inputs[x] == null) continue; 
			proc.addInput("input"+(x+1), inputs[x].getId());
			System.out.println("INPUT to " + processName + ": " + inputs[x].getId()); 
		} // End for
		
		proc.writeToDB();
		
		for(int x=0; x<inputs.length; x++) { 
			if(inputs[x] == null) continue; 
			edges[x] = new PLUSEdge(inputs[x].getId(), proc.getId(), 
					PLUSWorkflow.DEFAULT_WORKFLOW_OID, PLUSEdge.EDGE_TYPE_INPUT_TO);
			edges[x].writeToDB(); 
		}

		return proc;
	} // End customProcess
	
	/**
	 * Build a simple list of what was added/deleted between two lists.  
	 * @param list1 the first list
	 * @param list2 the result list
	 * @return a difference between them.
	 */
	protected SimpleDiff diff(ArrayList <PLUSObject> list1, ArrayList <PLUSObject> list2) {
		SimpleDiff d = new SimpleDiff();
		HashMap<String,Integer> m = new HashMap<String,Integer>();
		for(PLUSObject o : list1) { 
			m.put(o.getId(), 1);
		}
		
		for(PLUSObject o : list2) { 
			if(m.containsKey(o.getId())) {
				m.put(o.getId(), 2); 
			} else { d.added(o.getId()); } 
		}
		
		for(String key : m.keySet()) { 
			if(m.get(key) == 1) d.deleted(key); 
		}
		
		System.out.println("DIFF: " + d.getAdded().size() + " added, " + d.getDeleted().size() + " deleted."); 
		return d; 
	} // End diff
	
	protected ArrayList <PLUSObject> extractAssociatedSchemasFromTag(PLUSObject tagObj) { 
		ArrayList <PLUSObject> results = new ArrayList<PLUSObject>();
		
		int x = 1;
		try { 
			Metadata m = tagObj.getMetadata(); 
			while(m.containsKey("schema-"+x)) {
				String oid = ""+m.get("schema-"+x);
				PLUSObject o = getObjectByOID(oid); 
				results.add(o); 
				x++; 
			} // End while
		} catch(Exception e) { 
			System.err.println("IM-PLUS: failed to extract associated schemas from tag: " + e);
			e.printStackTrace(); 
		}
		
		return results;
	} // End extractAssociatedSchemasFromTag
	
	public void associateSchemasToTag(PLUSObject tagObj, Integer tagID) {
		try { 
			ArrayList <PLUSObject> taggedSchemas = getTaggedSchemas(tagID); 			
			for(int x=0; x<taggedSchemas.size(); x++) 
				tagObj.getMetadata().put("schema-"+(x+1), taggedSchemas.get(x).getId());
		} catch(Exception e) { 
			System.err.println("IM-PLUS: Failed associate schemas to tag: " + e);
			e.printStackTrace(); 
		} // End catch
	} // End associateSchemasToTag
	
	public ArrayList <Integer> getTagSchemaIDs(Integer tagID) throws RemoteException { 
		SchemaStoreClient client = RepositoryManager.getClient();
		return client.getTagSchemas(tagID); 
	} // End getTagSchemaIDs
	
	public ArrayList <PLUSObject> getTaggedSchemas(Integer tagID) throws Exception { 
		ArrayList <PLUSObject> schemas = new ArrayList<PLUSObject>();
		ArrayList <Integer> schemaIDs = getTagSchemaIDs(tagID);
		for(Integer id : schemaIDs) { 
			PLUSObject o = loadOpenIIPLUSObject(SCHEMA, id); 
			if(o != null) schemas.add(o); 
		}
		
		return schemas; 
	} // End getTaggedSchemas
	
	/**
	 * Quick convenience function to return a String that is unique to the repository that
	 * we're connected to at the moment.
	 * @return repository URI.  Hopefully this is enough to uniquely identify all repositories.
	 */
	public String getRepositoryURI() { 
		Repository repo = RepositoryManager.getSelectedRepository();
		if(repo == null) return "none";
		return repo.getURI().toString();
	} // End getRepositoryURI
	
	/**
	 * Try to guess at an appropriate PLUSObject name for something in schemastore.
	 * @param objectType
	 * @param id
	 * @return a plausible name to use.
	 */
	public String getDefaultName(String objectType, Integer id) {  
		if(objectType.equals(SCHEMA)) { 
			Schema s = OpenIIManager.getSchema(id); 
			return "Schema " + (s == null ? id : s.getName()); 
		} else if(objectType.equals("project")) {
			Project p = OpenIIManager.getProject(id); 
			return "Project " + (p == null ? id : p.getName()); 
		} else if(objectType.equals(TAG)) { 
			Tag t = OpenIIManager.getTag(id);
			return "Tag " + (t == null ? id : t.getName()); 
		} else if(objectType.equals(DATASOURCE)) { 
			return "DataSource " + id; 
		}
		
		return objectType + " " + id; 
	} // End name
	
	/**
	 * Creates a new IMPLUS data object for a given object in schemastore.  Attempt to populate
	 * it with reasonable metadata based on what kind of schemastore object it is.
	 * @param objectType the type of object, e.g. SCHEMA, TAG, MAPPING, etc.
	 * @param id the ID that identifies it in schemastore.
	 * @return a PLUSObject equivalent.
	 * @throws PLUSException
	 */
	public PLUSObject createPLUSDataObject(String objectType, Integer id) throws PLUSException {
		PLUSString s = new PLUSString("Name", objectType); 
		s.getMetadata().put(OPEN_II_ID, objectType + "-" + id); 
		s.getMetadata().put(OPEN_II_TYPE, objectType); 
		s.getMetadata().put(OPEN_II_REPOSITORY, getRepositoryURI()); 		
		
		boolean renameDefault = false; 
		
		if(objectType.equals(SCHEMA)) { 
			Schema sch = OpenIIManager.getSchema(id);
			if(sch != null) { 
				s.setName("Schema " + sch.getName());
				s.getMetadata().put("locked", ""+sch.getLocked());
				s.getMetadata().put("class", ""+sch.getClass().getName());
				s.getMetadata().put("author", ""+sch.getAuthor()); 
				s.getMetadata().put("description", ""+sch.getDescription());
				s.getMetadata().put("source", ""+sch.getSource());
			} else renameDefault = true; 
		} else if(objectType.equals("project")) {
			Project p = OpenIIManager.getProject(id);
			if(p != null) { 
				s.setName("Project " + p.getName());
				s.getMetadata().put("author", ""+p.getAuthor()); 
				s.getMetadata().put("description", ""+p.getDescription()); 			
			} else renameDefault = true; 
		} else if(objectType.equals(TAG)) { 
			Tag t = OpenIIManager.getTag(id);
			if(t != null) { 
				s.setName("Tag " + t.getName()); 
				s.getMetadata().put("parentID", ""+t.getParentId());
				associateSchemasToTag(s, id); 
			} else renameDefault = true; 
		} else if(objectType.equals(DATASOURCE)) { 
			s.setName("DataSource " + id); 
		} else if(objectType.equals(MAPPING)) {
			Mapping m = OpenIIManager.getMapping(id);
			if(m != null) { 
				s.setName("Mapping " + m.getId());
				s.getMetadata().put("source", ""+m.getSourceId()); 
				s.getMetadata().put("target", ""+m.getTargetId()); 			 						
			} else renameDefault = true; 
		} // End if 
		
		if(renameDefault) { 
			s.setName(getDefaultName(objectType, id)); 
			System.err.println("IM-PLUS: Couldn't get " + objectType + " " + id + " from schemastore!"); 
		} // End if
		
		return s; 
	} // End createPLUSDataDummyObject
	
	/**
	 * Given an object type and an ID, returns the PLUSObject for that ID, or null if none exists.
	 * @param objectType examples are SCHEMA, MAPPING, TAG, etc.
	 * @param id the ID used by the schemastore database.
	 * @return a PLUSObject or null if none exists.
	 */
	public PLUSObject loadOpenIIPLUSObject(String objectType, Integer id) { 
		Metadata m = new Metadata(); 
		String value = objectType + "-" + id; 
		m.put(OPEN_II_ID, value);
		m.put(OPEN_II_REPOSITORY, getRepositoryURI()); 
		
		try { 
			ArrayList <PLUSObject> r = PLUSObject.loadByMetadata(lineageUser, m);			
			if(r.size() <= 0) {
				// We're referring to something that's real, but hasn't ever been explicitly
				// created by us. Create a stand-in object for this data item in its present
				// state, and return that instead.
				// This takes care of the problem of objects that exist in schemastore BEFORE
				// lineage logging starts.  The first time they're ever referenced, they get 
				// picked up and logged.
				PLUSObject obj = createPLUSDataObject(objectType, id);  
				obj.writeToDB(); 
				return obj;				
			} // End if
			
			// Sort most recent first.  This way, if modifications have been made, we're up to date.
			Collections.sort(r, new Comparator <PLUSObject> () {
				public int compare(PLUSObject a, PLUSObject b) {
					Long aCreated = new Long(a.getCreated());
					Long bCreated = new Long(b.getCreated());
					return aCreated.compareTo(bCreated);
				}
			}); 

			// Return only most recent.
			return r.get(0); 
		} catch(Exception e) {
			System.err.println("IMPLUS: " + e); 
			e.printStackTrace();
			return null;
		} // End catch		
	} // End loadOpenIIPLUSObject
	
	/**
	 * "Modify" operations in OpenII are generic; we don't get information about who modified, or
	 * exactly what they did.  This adds a linkage to a "USER MODIFIED" invocation, and clones
	 * the original object into a new one, to indicate that the old thing changed in some way.
	 * @param obj the object that was modified by the user.
	 * @return the new object, after modification.
	 */
	public PLUSObject markAsModified(PLUSObject obj) {
		if(obj == null) { 
			System.out.println("IM-PLUS: nothing to mark as modified.");
			return null;
		}
		
		try { 
			PLUSObject newVersion = obj.clone();
			newVersion.setId(PLUSUtils.generateID());
			PLUSInvocation mod = new PLUSInvocation("USER MODIFIED"); 		
			mod.addInput("input-"+obj.getMetadata().get(OPEN_II_TYPE), obj.getId());
			mod.setWorkflowId(PLUSWorkflow.DEFAULT_WORKFLOW_OID);
			mod.setActivityId(PLUSActivity.UNKNOWN_ACTIVITY_OID);
			mod.writeToDB();
			PLUSEdge e = new PLUSEdge(obj.getId(), mod.getId(), PLUSWorkflow.DEFAULT_WORKFLOW_OID,
					PLUSEdge.EDGE_TYPE_INPUT_TO); 
			e.writeToDB(); 
			
			newVersion.getMetadata().put("modified", (new Date()).toString()); 
			newVersion.writeToDB(); 
			
			PLUSEdge e2 = new PLUSEdge(mod.getId(), newVersion.getId(), PLUSWorkflow.DEFAULT_WORKFLOW_OID,
					PLUSEdge.EDGE_TYPE_GENERATED);
			e2.writeToDB(); 
			
			return newVersion;
		} catch(Exception e) { 
			System.err.println("IM-PLUS: " + e); 
			e.printStackTrace(); 
			return null;
		} // End catch
	} // End markAsModified
	
	/**
	 * Often when things are created in OpenII, they have inputs.  (E.g. a project organizes a 
	 * set of schemas).  This writes a simple "CREATE" invocation, and links in the provided inputs.
	 * @param created what was created
	 * @param inputs the things used to create that object.
	 */
	public void createWithInputs(PLUSObject created, PLUSObject [] inputs) {
		try { 
			PLUSInvocation create = new PLUSInvocation("CREATE");
			PLUSEdge [] e = new PLUSEdge [inputs.length];
			create.setActivityId(PLUSActivity.UNKNOWN_ACTIVITY_OID); 
			create.setWorkflowId(PLUSWorkflow.DEFAULT_WORKFLOW_OID);
	
			for(int x=0; x<inputs.length; x++) { 
				create.addInput(inputs[x].getName(), inputs[x].getId());
				e[x] = new PLUSEdge(inputs[x].getId(), create.getId(), 
						PLUSWorkflow.DEFAULT_WORKFLOW_OID,
						PLUSEdge.EDGE_TYPE_INPUT_TO); 
			} // End for
			
			create.writeToDB();
			for(int x=0; x<inputs.length; x++) { e[x].writeToDB(); } 
			
			PLUSEdge output = new PLUSEdge(create.getId(), created.getId(), 
					PLUSWorkflow.DEFAULT_WORKFLOW_OID, PLUSEdge.EDGE_TYPE_GENERATED);
			output.writeToDB(); 
		} catch(Exception e) { 
			System.err.println("IM-PLUS: " + e);
			e.printStackTrace();
		} // End catch		
	} // End createWithInputs
	
	/**
	 * Deletion is the end of the line.  Pipe this object to an invocation marked "DELETE", and
	 * it's dead.  "Delete" in this sense means OpenII deletion.  The actual PLUSObject will
	 * continue to live on in the IM-PLUS database.
	 * @param obj the object being deleted.
	 */
	public void markAsDeleted(PLUSObject obj) {
		if(obj == null) {
			System.out.println("markAsDeleted: no object to mark."); 
			return; 
		}
				
		try { 
			PLUSInvocation inv = new PLUSInvocation("DELETE by user");
			inv.addInput("object", obj.getId()); 
			inv.setActivityId(PLUSActivity.UNKNOWN_ACTIVITY_OID);
			inv.setWorkflowId(PLUSWorkflow.DEFAULT_WORKFLOW_OID); 
			inv.writeToDB();
	
			PLUSEdge e = new PLUSEdge(obj.getId(), inv.getId(), PLUSWorkflow.DEFAULT_WORKFLOW_OID, 
									  PLUSEdge.EDGE_TYPE_INPUT_TO); 
			e.writeToDB();
		} catch(Exception e) { 
			System.err.println("IM-PLUS: " + e); 
			e.printStackTrace(); 
		} // End catch
	} // End markAsDeleted
	
	/**
	 * @see OpenIIListener#dataSourceAdded(Integer)
	 */
	public void dataSourceAdded(Integer dataSourceID) {
		System.out.println("IMPLUS: dataSourceAdded");
		
		try { 
			PLUSObject obj = createPLUSDataObject(DATASOURCE, dataSourceID);		
			obj.writeToDB(); 
		} catch(Exception e) { 
			System.err.println("IM-PLUS: " + e); 
			e.printStackTrace(); 
		} // End catch	
	} // End dataSourceAdded
	
	/**
	 * @see OpenIIListener#dataSourceDeleted(Integer dataSourceID)
	 */
	public void dataSourceDeleted(Integer dataSourceID) {
		System.out.println("IMPLUS: dataSourceDeleted");
		markAsDeleted(loadOpenIIPLUSObject(DATASOURCE, dataSourceID)); 
	}

	/**
	 * @see OpenIIListener#mappingAdded(Integer)
	 */
	public void mappingAdded(Integer mappingID) {
		System.out.println("IMPLUS: mappingAdded");
		Mapping m = OpenIIManager.getMapping(mappingID); 
		
		try { 
			PLUSObject obj = createPLUSDataObject(MAPPING, mappingID);		
			obj.writeToDB();
		
			PLUSObject proj = loadOpenIIPLUSObject("project", m.getProjectId()); 
			PLUSObject src  = loadOpenIIPLUSObject(SCHEMA, m.getSourceId()); 
			PLUSObject dst  = loadOpenIIPLUSObject(SCHEMA, m.getTargetId()); 
						
			createWithInputs(obj, new PLUSObject [] { proj, src, dst });			
		} catch(Exception e) { 
			System.err.println("IM-PLUS: " + e); 
			e.printStackTrace(); 
		} // End catch			
	} // End mappingAdded

	/**
	 * @see OpenIIListener#mappingDeleted(Integer)
	 */
	public void mappingDeleted(Integer mappingID) {
		System.out.println("IMPLUS: mappingDeleted");
		markAsDeleted(loadOpenIIPLUSObject(MAPPING, mappingID)); 
	}

	/**
	 * @see OpenIIListener#mappingModified(Integer)
	 */
	public void mappingModified(Integer mappingID) {
		System.out.println("IMPLUS: mappingModified");
		PLUSObject nv = markAsModified(loadOpenIIPLUSObject(MAPPING, mappingID)); 
	}

	/**
	 * @see OpenIIListener#projectsMerged(ArrayList, Integer)
	 */
	public void projectsMerged(ArrayList<Integer> mergedProjectIDs, Integer projectID) {
		try {
			PLUSInvocation merge = new PLUSInvocation("MERGE"); 
			merge.setActivityId(PLUSActivity.UNKNOWN_ACTIVITY_OID);
			merge.setWorkflowId(PLUSWorkflow.DEFAULT_WORKFLOW_OID);
			
			PLUSObject [] inputs = new PLUSObject [mergedProjectIDs.size()+1];
			PLUSEdge [] edges = new PLUSEdge [mergedProjectIDs.size()+1];
			
			for(int x=0; x<mergedProjectIDs.size(); x++) { 
				PLUSObject p = loadOpenIIPLUSObject("project", mergedProjectIDs.get(x));
				merge.addInput("project"+x, p.getId());
				edges[x] = new PLUSEdge(p.getId(), merge.getId(), 
						PLUSWorkflow.DEFAULT_WORKFLOW_OID,
						PLUSEdge.EDGE_TYPE_INPUT_TO); 
			} // End for
			
			PLUSObject newp = loadOpenIIPLUSObject("project", projectID);
			merge.addInput("project"+mergedProjectIDs.size(), newp.getId());
			edges[mergedProjectIDs.size()] = new PLUSEdge(newp.getId(), merge.getId(),
					PLUSWorkflow.DEFAULT_WORKFLOW_OID,
					PLUSEdge.EDGE_TYPE_INPUT_TO); 
	
			merge.writeToDB();
			for(int x=0; x<edges.length; x++) edges[x].writeToDB(); 
			
			PLUSObject merged = newp.clone();
			merged.setId(PLUSUtils.generateID());
			merged.writeToDB();
			
			PLUSEdge out = new PLUSEdge(merge.getId(), merged.getId(), 
					PLUSWorkflow.DEFAULT_WORKFLOW_OID,
					PLUSEdge.EDGE_TYPE_INPUT_TO); 
			out.writeToDB(); 
		} catch(Exception e) { 
			System.err.println("IM-PLUS: " + e); 
			e.printStackTrace(); 
		}
	} // End projectsMerged
	
	/**
	 * @see OpenIIListener#projectAdded(Integer)
	 */	
	public void projectAdded(Integer projectID) {
		System.out.println("IMPLUS: projectAdded");
		
		Project proj = OpenIIManager.getProject(projectID);
				
		try { 
			PLUSObject obj = createPLUSDataObject("project", projectID);  
			obj.writeToDB();
			
			Integer [] sids = proj.getSchemaIDs();
			
			PLUSObject [] inputSchemas = new PLUSObject [sids.length];
			for(int x=0; x<sids.length; x++) { 
				inputSchemas[x] = loadOpenIIPLUSObject(SCHEMA, sids[x]); 				
			}
			
			createWithInputs(obj, inputSchemas); 
		} catch(Exception e) { 
			System.err.println("IM-PLUS: " + e); 
			e.printStackTrace(); 
		} // End catch		
	} // End projectAdded

	/**
	 * @see OpenIIListener#projectDeleted(Integer)
	 */	
	public void projectDeleted(Integer projectID) {
		System.out.println("IMPLUS: projectDeleted");
		markAsDeleted(loadOpenIIPLUSObject("project", projectID)); 
	}

	/**
	 * @see OpenIIListener#projectModified(Integer)
	 */	
	public void projectModified(Integer projectID) {
		System.out.println("IMPLUS: projectModified"); 
		PLUSObject nv = markAsModified(loadOpenIIPLUSObject("project", projectID));
	}

	/**
	 * @see OpenIIListener#repositoryReset()
	 */
	public void repositoryReset() {
		System.out.println("IMPLUS: repositoryReset");		
	}

	/**
	 * @see OpenIIListener#schemaAdded(Integer)
	 */	
	public void schemaAdded(Integer schemaID) {
		System.out.println("IMPLUS: schemaAdded");
		Repository repo = RepositoryManager.getSelectedRepository();
		Schema sch = OpenIIManager.getSchema(schemaID); 
		
		try { 
			PLUSObject obj = createPLUSDataObject(SCHEMA, schemaID); 
			obj.writeToDB(); 
		} catch(Exception e) { 
			System.err.println("IM-PLUS: " + e); 
			e.printStackTrace(); 
		}
	} // End schemaAdded

	/**
	 * @see OpenIIListener#schemaDeleted(Integer)
	 */	
	public void schemaDeleted(Integer schemaID) {
		System.out.println("IMPLUS: schemaDeleted");
		markAsDeleted(loadOpenIIPLUSObject(SCHEMA, schemaID)); 
	}

	/**
	 * @see OpenIIListener#schemaModified(Integer)
	 */
	public void schemaModified(Integer schemaID) {
		System.out.println("IMPLUS: schemaModified"); 		
		PLUSObject nv = markAsModified(loadOpenIIPLUSObject(SCHEMA, schemaID));
	}

	/**
	 * @see OpenIIListener#tagAdded(Integer)
	 */	
	public void tagAdded(Integer tagID) {		
		System.out.println("IMPLUS: tagAdded"); 
		
		try { 			
			ArrayList <PLUSObject> schemas = getTaggedSchemas(tagID);
			PLUSInvocation create = customProcess("CREATE TAG", schemas.toArray(new PLUSObject []{}));
			PLUSObject obj = createPLUSDataObject(TAG, tagID);  			
			obj.writeToDB();
			PLUSEdge e = new PLUSEdge(create.getId(), obj.getId(), PLUSWorkflow.DEFAULT_WORKFLOW_OID,
					PLUSEdge.EDGE_TYPE_GENERATED);
			e.writeToDB();
		} catch(Exception e) { 
			System.err.println("IM-PLUS: " + e); 
			e.printStackTrace(); 
		} // End catch		
	} // End tagAdded

	/**
	 * @see OpenIIListener#tagDeleted(Integer)
	 */
	public void tagDeleted(Integer tagID) {
		System.out.println("IMPLUS: tagDeleted"); 
		markAsDeleted(loadOpenIIPLUSObject(TAG, tagID)); 		
	}

	/**
	 * @see OpenIIListener#tagModified(Integer)
	 */	
	public void tagModified(Integer tagID) {
		System.out.println("IMPLUS: tagModified");
		try { 
			Tag tag = OpenIIManager.getTag(tagID);
			PLUSObject oldTagObj = loadOpenIIPLUSObject(TAG, tagID); 
			ArrayList <PLUSObject> oldSchemaSet = extractAssociatedSchemasFromTag(oldTagObj);
			 
			PLUSObject newTagObj = createPLUSDataObject(TAG, tagID); 
			ArrayList <PLUSObject> newSchemaSet = extractAssociatedSchemasFromTag(newTagObj);
			newTagObj.writeToDB(); 
						
			System.out.println("Diffing old " + oldTagObj.getId() + " and new " + newTagObj.getId()); 
			
			SimpleDiff diff = diff(oldSchemaSet, newSchemaSet); 
			
			boolean oldObjInputToAdded = false;
			PLUSInvocation added = null;
			if(diff.getAdded().size() > 0) { 
				PLUSObject [] inputs = new PLUSObject [diff.getAdded().size()+1];
				inputs[0] = oldTagObj; 
				oldObjInputToAdded = true; 
				for(int x=1; x<diff.getAdded().size(); x++) 
					inputs[x] = getObjectByOID(diff.getAdded().get(x));
				
				System.out.println("Writing ADD SCHEMAS"); 
				added = customProcess("ADD SCHEMAS", inputs);
			} // End if
			
			PLUSInvocation deleted = null;
			if(diff.getDeleted().size() > 0) { 
				PLUSObject [] inputs = new PLUSObject [diff.getDeleted().size() + 2];
				if(!oldObjInputToAdded) inputs[0] = oldTagObj;
				inputs[1] = added; 
				
				for(int x=0; x<diff.getDeleted().size(); x++)  
					inputs[x+2] = getObjectByOID(diff.getDeleted().get(x));
				
				System.out.println("Writing REMOVE SCHEMAS"); 
				deleted = customProcess("REMOVE SCHEMAS", inputs); 
			} // End if
			
			if(added == null && deleted == null) { 
				markAsModified(oldTagObj); 
			} else if(deleted != null) { 
				System.out.println("Writing edge deleted " + deleted.getId() + " -> new " + 
						newTagObj.getId()); 
				PLUSEdge output = new PLUSEdge(deleted.getId(), newTagObj.getId(), 
						PLUSWorkflow.DEFAULT_WORKFLOW_OID, PLUSEdge.EDGE_TYPE_GENERATED);
				output.writeToDB(); 
			} else {
				System.out.println("Writing edge added " + added.getId() + " -> new " + 
						newTagObj.getId()); 
  
				PLUSEdge outEdge = new PLUSEdge(added.getId(), newTagObj.getId(), 
						PLUSWorkflow.DEFAULT_WORKFLOW_OID, PLUSEdge.EDGE_TYPE_GENERATED);
				outEdge.writeToDB(); 				
			} // End else			
		} catch(Exception e) { 
			System.err.println("IM-PLUS: failed to modify tag: " + e);
			e.printStackTrace();
		} // End catch 		
	} // End tag
	
	private class SimpleDiff { 
		protected ArrayList <String> added;
		protected ArrayList <String> deleted; 
		public SimpleDiff() { 
			added = new ArrayList <String> ();
			deleted = new ArrayList <String> (); 
		}
		
		public void added(String oid) { added.add(oid); } 
		public void deleted(String oid) { deleted.add(oid); } 
		public ArrayList <String> getAdded() { return added; } 
		public ArrayList <String> getDeleted() { return deleted; } 
	} // End SimpleDiff
} // End IMPLUSListener

