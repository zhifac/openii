package org.mitre.schemastore.graph;

import java.util.*;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.*;
/**
 * Class GraphBuilder 
 * @author MDMORSE, DBURDICK
 */

public class GraphBuilder{
	
	// stores the schemaElements
	private HashMap<Integer, SchemaElement> graphHash;
	private Integer schemaID;
	
	public static void main(String[] args){
		SchemaStoreClient client = new SchemaStoreClient("http://localhost:8080/SchemaStore/services/SchemaStore");
		try {
			GraphBuilder graph = new GraphBuilder(client.getSchemaElements(2766), 2766);
			ArrayList<SchemaElement> addedElements = new ArrayList<SchemaElement>();
			Containment c = new Containment(3000,"new top","new top",2766,3001,0,1,2766); 
			Entity e = new Entity(3001,"new top","",2766);
			Containment c2 = new Containment(3002,"new top","new top",3001,3003,0,1,2766); 
			Entity e2 = new Entity(3003,"new second","",2766);
			addedElements.add(c); addedElements.add(e);
			addedElements.add(c2); addedElements.add(e2);
			graph.addElements(addedElements,2766);
			graph.enumerateGraph();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public GraphBuilder(ArrayList<SchemaElement> elements, Integer id){
		schemaID = id;
		addElements(elements,id);
	}
	
	public ArrayList<SchemaElement> getParents(Integer schemaID){
		SchemaElement element = graphHash.get(schemaID);
		if (element == null){
			System.out.println("[E] GraphBuilder:getParents -- schema ID " + schemaID + " does not exist");
			return null;
		}
		return ((GraphSchemaElement)element).getParents();
	}
	
	public ArrayList<SchemaElement> getChildren(Integer schemaID){
		SchemaElement element = graphHash.get(schemaID);
		if (element == null){
			System.out.println("[E] GraphBuilder:getChildren-- schema ID " + schemaID + " does not exist");
			return null;
		}
		return ((GraphSchemaElement)element).getChildren();	
	}
	
	/**
	 * enumerateGraph(): Performs a Breadth-first enumeration of the graph
	 * 
	 */
	public void enumerateGraph(){
		
		// stores the IDs that have been seen
		HashSet<Integer> visitedElements = new HashSet<Integer>();
		
		// stores the current "frontier" of the graph
		ArrayList<SchemaElement> queue = new ArrayList<SchemaElement>();
		queue.add(graphHash.get(schemaID));
		visitedElements.add(schemaID);
		
		while (queue.size() > 0){
			SchemaElement currentElement = queue.remove(0);
			
			// get children of currentElement (if currentElement is NOT a GraphSchemaElement 
			// THEN has no children by definition
			if ((currentElement instanceof GraphSchemaElement) == false){
				System.out.println("[E] GraphBuilder:enumerate -- schema ID " + currentElement.getId() + " is unexpected type " + currentElement.getClass().toString());	
			} else {
				System.out.println("PARENT: " + currentElement.getId() + " " + currentElement.getName() + " " + currentElement.getClass());
				for (SchemaElement se : ((GraphSchemaElement)currentElement).getChildren()){
					// process element here				
					System.out.println("     CHILD: " + se.getId() + " " + se.getName() + " " + se.getClass());
					if (visitedElements.contains(se.getId()) == false){
						queue.add(se);
						visitedElements.add(se.getId());
					}
				}
			} // end else
		} // end while
	} // end method enumerateGraph()
	
	
	public Boolean deleteSchemaElement(Integer schemaID){
		
		SchemaElement element = graphHash.get(schemaID);
		
		if ((element instanceof GraphSchemaElement) == false){
			System.out.println("[E] GraphBuilder:deleteSchemaElement -- schema ID " + schemaID + " is unexpected type " + element.getClass().toString());
			graphHash.remove(schemaID);
		}
		
		// CASE: Domain
		if (element instanceof Domain){
			if (((GraphSchemaElement)element).getParents().size() == 0){
				
				// find containments refering to domain
				//    remove enity --> domain containment 
				for (SchemaElement se : ((GraphDomain)element).getChildContainments()){
					GraphEntity entity = (GraphEntity)graphHash.get(((GraphContainment)se).getParentID());  
					if (entity == null){
						System.out.println("[E] GraphBuilder:deleteSchemaElement -- entity ID " + schemaID + " is invalid ");
						return false;
					} else {
						for (SchemaElement domain : entity.getChildDomains()){
							if (domain.getId().equals(element.getId()))
								entity.removeChildDomain(domain.getId());
						}
					}
					// remove the containment itself in graph
					graphHash.remove(se.getId());
				}
				// remove all domainValues for deleted domain
				for (SchemaElement se : ((GraphSchemaElement)element).getChildren()){
					graphHash.remove(se.getId());
				}
				// remove the domain
				graphHash.remove(element.getId());
			} else {
				System.out.println("[E] GraphBuilder:deleteSchemaElement -- schema ID " + schemaID + " is a domain used by attributes or entities");
				return false;
			}
		}
		
		// CASE: DomainValue
		else if (element instanceof DomainValue){
			GraphDomain domain = (GraphDomain)graphHash.get(((DomainValue)element).getDomainID());
			domain.removeChildDomainValue(element.getId());
		}
		
		// CASE: Attribute
		else if (element instanceof Attribute){
			// remove attribute from domainList
			GraphDomain domain = (GraphDomain)graphHash.get( ((GraphAttribute)element).getDomainID() );
			if (domain == null){
				System.out.println("[E] GraphBuilder:deleteSchemaElement -- Attribute with ID " + schemaID + 
						" refers to a non-existant domain (ID " + ((GraphAttribute)element).getDomainID() + ")");
				return false;
			}
			else{
				for (Attribute a : domain.getParentAttributes()){
					if (a.getId().equals(element.getId()) ){
						domain.removeParentAttribute(element.getId());
						
						// if deleted last attribute referring to domain, delete domain
						if (domain.getParents().size() == 0){
							deleteSchemaElement(domain.getId());
						}
						break;
					}
				}
			}
			
			// remove attr from entity
			GraphEntity entity = (GraphEntity)graphHash.get(((Attribute)element).getEntityID());
			if (entity == null){
				System.out.println("[E] GraphBuilder:deleteSchemaElement -- Attribute with ID " + element.getId() + 
						" refers to a non-existant entity (ID " + ((Attribute)element).getEntityID() + ")");
				return false;
			}
			else {
				for (SchemaElement se : entity.getChildAttributes()){
					if (se.getId().equals(element.getId())){
						entity.removeChildAttribute(element.getId());
						break;
					}
				}
			}
			graphHash.remove(element.getId());
		}
		
		// CASE: Entity
		else if (element instanceof Entity){
			// cannot delete entity with child Entities
			if (((GraphEntity)element).getChildEnititiesContained().size() > 0){
				System.out.println("[E] GraphBuilder:deleteSchemaElement -- Entity with ID " + element.getId() + 
						" has child Entities and cannot be deleted");
				return false;
			}
			
			// delete all attributes for entity
			for (SchemaElement se : ((GraphEntity)element).getChildAttributes()){
				deleteSchemaElement(se.getId());
			}
			// delete containments domains domain from gra
			for (SchemaElement se : ((GraphEntity)element).getChildDomains()){
				((GraphDomain)se).removeParentEntity(element.getId());
			}
			
			// delete all containments where entity is parent / child
			 
			// delete all relationships where entity left / right
			
		}
		
		// TODO: figure out better way to process aliases
		// For now, go through all aliases and delete
		
		return true;
	}

	public ArrayList<SchemaElement> getSchemaElements(){
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		for (SchemaElement se : graphHash.values()){
			if (se.getId().equals(schemaID) == false){
				retVal.add(se);
			}
		}
		return retVal;
	}
	
	public ArrayList<SchemaElement> addElements(ArrayList<SchemaElement> schemaElements, int schemaID){
		
		// Initialize graphHash if not already initialized
	
		if (graphHash == null){
			graphHash = new HashMap<Integer, SchemaElement>();
			graphHash.put(schemaID, new GraphEntity(schemaID));
		}
		
		ArrayList<SchemaElement> edges = new ArrayList<SchemaElement>();
		
		try {		 
		
			for(SchemaElement schemaElement : schemaElements){
				
				// process the nodes 
				if(schemaElement instanceof Attribute){ //node
					GraphAttribute graphAttribute = new GraphAttribute((Attribute)schemaElement);
					graphHash.put(graphAttribute.getId(), graphAttribute);
				}
				
				else if(schemaElement instanceof Domain){ //node
					GraphDomain graphDomain = new GraphDomain((Domain) schemaElement);
					graphHash.put(graphDomain.getId(), graphDomain);
				}
				
				else if(schemaElement instanceof DomainValue){ //node
					GraphDomainValue graphDomainValue = new GraphDomainValue((DomainValue) schemaElement);
					graphHash.put(graphDomainValue.getId(), graphDomainValue);
				}
				
				else if(schemaElement instanceof Entity){ //node
					GraphEntity graphEntity = new GraphEntity((Entity) schemaElement);
					graphHash.put(graphEntity.getId(), graphEntity);
				}
				else {
					// add edges (Containment, Relationship, Subtype, Alias)
					edges.add(schemaElement);
				}
			}
		
			// Make SECOND interation through nodes to make IMPLICIT connections without
			// a corresponding edge, specifically: 
			// 1) attributes --> entities, 
			// 2) entities --> attrs, 3) domain values --> domains, 
			// 4) domains --> attributes 5) attribute --> domains (NOTE ARROW DIRECTION)
			for(SchemaElement schemaElement : schemaElements){
	
				if(schemaElement instanceof Attribute){ //node
					GraphAttribute attribute = (GraphAttribute)graphHash.get(schemaElement.getId());
					GraphDomain domain = (GraphDomain)graphHash.get(attribute.getDomainID());
					attribute.domainType = domain;
					domain.addParentAttribute(attribute);
					GraphEntity entity = (GraphEntity)graphHash.get(attribute.getEntityID());
					attribute.parentEntity = entity;
					entity.addChildAttribute(attribute);
				}
			
				else if(schemaElement instanceof DomainValue){ //node
					GraphDomainValue domainValue = (GraphDomainValue)graphHash.get(schemaElement.getId());
					GraphDomain domain = (GraphDomain)graphHash.get(domainValue.getDomainID());
					domainValue.setParentDomain(domain);
					domain.addChildDomainValue(domainValue);
				}
			}
		} catch(Exception e){
			System.out.println("[E] GraphBuilder:build -- Error assigning implicit connections for schemaElements");
			e.printStackTrace();
		}
		
		try {
			//now, iterate through the explicit edges, and connect.
			for(SchemaElement schemaElement : edges){
				
				if(schemaElement instanceof Containment){
					GraphContainment graphElement = new GraphContainment((Containment)schemaElement);
					graphHash.put(graphElement.getId(), graphElement);
					
					GraphEntity parent = (GraphEntity)graphHash.get(((GraphContainment)graphElement).getParentID());
					SchemaElement child = graphHash.get(((GraphContainment)graphElement).getChildID());
					// add entity <--> entity connections
					if (child instanceof GraphEntity){
						parent.addChildEnitityContained((GraphEntity)child);
						parent.addParentContainment(graphElement);
						((GraphEntity)child).addParentEnitityContained(parent);
						((GraphEntity)child).addChildContainment(graphElement);
						
					}
					else if (child instanceof GraphDomain){
						parent.addChildDomain((GraphDomain)child);
						parent.addParentContainment(graphElement);
						((GraphDomain)child).addParentEntity(parent);
						((GraphDomain)child).addChildContainment(graphElement);
					}
				} // end if(schemaElement instanceof Containment){
				
				else if(schemaElement instanceof Relationship){ 
					GraphRelationship rel = new GraphRelationship((Relationship) schemaElement);
					graphHash.put(rel.getId(), rel);
					GraphEntity leftEntity  = (GraphEntity)graphHash.get(rel.getLeftID());
					GraphEntity rightEntity = (GraphEntity)graphHash.get(rel.getRightID());
		
					leftEntity.addLeftEntitiesRelated(rightEntity);
					leftEntity.addRelationship(rel);
					rightEntity.addRightEntityRelated(leftEntity);
					leftEntity.addRelationship(rel);
				}
				
				else if(schemaElement instanceof Subtype){ 
					GraphSubtype subtype = new GraphSubtype((Subtype) schemaElement);
					graphHash.put(subtype.getId(), subtype);
					GraphEntity parentEntity = (GraphEntity)graphHash.get(subtype.getParentID());
					GraphEntity childEntity  = (GraphEntity)graphHash.get(subtype.getChildID());
					
					parentEntity.addSubtype(childEntity);
					childEntity.addSupertype(parentEntity);
				}
				
				else if (schemaElement instanceof Alias){
					// add Alias top schemaElement
					GraphAlias alias = new GraphAlias((Alias) schemaElement);
					graphHash.put(alias.getId(), alias);
				}
				else {
					System.out.println("[E] GraphBuilder:build -- SchemaElement " + schemaElement.getId() + " has unexpected type " + schemaElement.getClass().toString());
					graphHash.put(schemaElement.getId(), schemaElement);
				}
				
			} // end for(SchemaElement schemaElement: edges){
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// return the graph elements (both NODES and EDGES)
		return getSchemaElements();
		
	} // end method build
		
	
}