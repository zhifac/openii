// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.schemastore.graph;

/**
 * Class GraphAttribute 
 * @author MDMORSE, DBURDICK
 */

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import java.util.*;

public class GraphSchema extends SchemaElement implements GraphSchemaElement{
	
	private GraphAlias alias;
	
	// containments where schema is PARENT
	private ArrayList<GraphContainment> parentContainments;
	
	// store child domains and entities
	private ArrayList<GraphDomain> childDomains;
	private ArrayList<GraphEntity> childEnititiesContained;

	/** Constructs the schema */
//	public GraphSchema(Integer id, String name, String description, Integer entityID, Integer domainID, Integer min, Integer max, Integer base){ 
//		// TODO: need to make author, source, type, locked PROTECTED fields
//		super(id,name,author,source,type,description,locked);
//	}
	
	
	
	public ArrayList<SchemaElement> getParents(){
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		return retVal;
	}
	
	public ArrayList<SchemaElement> getChildren(){
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		retVal.addAll(parentContainments);
		return retVal;
	}	
	public void setAlias(GraphAlias a){
		alias = a;
	}
	public GraphAlias getAlias(){
		return alias;
	}
	
	//	class getters
	public ArrayList<GraphDomain> getChildDomains() { return childDomains; }
	public ArrayList<GraphEntity> getChildEnititiesContained() { return childEnititiesContained; }
	public ArrayList<GraphContainment> getParentContainments() { return parentContainments; }

	
	// class adders
	public void addChildDomain(GraphDomain e) { childDomains.add(e); }
	public void addChildEnitityContained(GraphEntity e) { childEnititiesContained.add(e); }
	public void addParentContainment(GraphContainment c) { parentContainments.add(c); }

	
	// class removers
	public void removeChildDomain(Integer id) { 
		for (int i=0;i<childDomains.size();i++)
			if (childDomains.get(i).getId().equals(id)) childDomains.remove(i);
	}
	public void removeChildEntityContained(Integer id) {
		for (int i=0;i<childEnititiesContained.size();i++)
			if (childEnititiesContained.get(i).getId().equals(id)) childEnititiesContained.remove(i);
	}
	public void removeParentContainment(Integer id) { 
		for (int i=0;i<parentContainments.size();i++)
			if (parentContainments.get(i).getId().equals(id)) parentContainments.remove(i);	
	}

	
	/** Stores the schema id */
	private Integer id;
	
	/** Stores the schema name */
	private String name;
	
	/** Stores the schema author */
	private String author;
	
	/** Stores the schema source */
	private String source;

	/** Stores the schema type */
	private String type;
	
	/** Stores the schema description */
	private String description;	
	
	/** Indicates if the schema is locked */
	private boolean locked;
	
	/** Constructs a default schema */
	public GraphSchema() {}
	
	
	// Handles all schema getters
	public Integer getId() { return id; }
	public String getName() { return name; }
	public String getAuthor() { return author; }
	public String getSource() { return source; }
	public String getType() { return type; }
	public String getDescription() { return description; }
	public boolean getLocked() { return locked; }

	// Handles all schema setters
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setAuthor(String author) { this.author = author; }
	public void setSource(String source) { this.source = source; }
	public void setType(String type) { this.type = type; }
	public void setDescription(String description) { this.description = description; }
	public void setLocked(boolean locked) { this.locked = locked; }
	
	/** Indicates that two schemas are equals */
	public boolean equals(Object schema)
		{ return schema instanceof GraphSchema && ((GraphSchema)schema).id.equals(id); }
	
	/** String representation of the schema */
	public String toString()
		{ return name; }
}
	
	
	
	



