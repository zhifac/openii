// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.graph;

import java.util.*;

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
	
	}

	public GraphBuilder(ArrayList<SchemaElement> elements, Integer id){
		this.schemaID = id;
		
		addElements(elements,id);
		printGraph();
		if (getSchemaElement(id) == null){
			System.out.println("[E] GraphBuilder:GraphBuilder -- graph does not contain element for schema (id " + id + ")");
		}
	}
	
	public ArrayList<SchemaElement> getSchemaElements(Class type){
		
		// Filter on the specified type
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		for (SchemaElement se : getSchemaElements()){
			if(type==null || type.isInstance(se))
				retVal.add(se);
		}
		return retVal;
		
	}
	
	public ArrayList<SchemaElement> getSchemaElements(){
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		for (SchemaElement se : graphHash.values()){
			if (se.getId().equals(this.schemaID) == false){
				retVal.add(se);
			}
		}
		return retVal;
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
		queue.add(graphHash.get(this.schemaID));
		visitedElements.add(this.schemaID);
		
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
	
	public void printGraph(){
		
		System.err.println("****************************");
		System.err.println("schemaID: " + this.schemaID);
		for (SchemaElement se : graphHash.values()){
			System.err.println(se.getId() + " , " + se.getName() + " , " + se.getClass());
		}
		System.err.println("****************************");
	
	} // end method enumerateGraph()
	
	public SchemaElement getSchemaElement(Integer id){
		return graphHash.get(id);
	}
	
	/***
	 * deleteSchemaElement(): Removes a schema element from the graph; 
	 * @param schemaID ID of element being deleted
	 * @return TRUE if element successfully deleted; FALSE otherwise
	 *
	 */
	public Boolean deleteSchemaElement(Integer schemaID){
		
		SchemaElement element = graphHash.get(schemaID);
		
		if (element == null){
			System.out.println("[E] GraphBuilder:deleteSchemaElement -- schema ID " + schemaID + 
					" does not exist ");
			return false;
		}
		
		if ((element instanceof GraphSchemaElement) == false){
			System.out.println("[E] GraphBuilder:deleteSchemaElement -- schema ID " + schemaID + 
					" is unexpected type " + element.getClass().toString());
			graphHash.remove(schemaID);
			return true;
		}
		
		////////////////////// CASE: Domain ///////////////////////////////////
		if (element instanceof Domain){
			if (((GraphSchemaElement)element).getParents().size() == 0){
				
				// find containments refering to domain
				//    remove enity --> domain containment 
				for (SchemaElement containment : ((GraphDomain)element).getChildContainments()){
					GraphEntity entity = (GraphEntity)graphHash.get(((GraphContainment)containment).getParentID());  
					if (entity == null){
						System.out.println("[E] GraphBuilder:deleteSchemaElement -- entity ID " + schemaID + " is invalid ");
						return false;
					} else {
						entity.removeChildDomain(element.getId());
					}
					// remove the containment itself in graph
					graphHash.remove(containment.getId());
				}
				// remove all domainValues for deleted domain
				for (SchemaElement dv : ((GraphSchemaElement)element).getChildren()){
					graphHash.remove(dv.getId());
				}
				// remove the domain
				graphHash.remove(element.getId());
			} else {
				System.out.println("[E] GraphBuilder:deleteSchemaElement -- schema ID " + schemaID + 
						" is a domain used by attributes or entities");
				return false;
			}
		}
		
		///////////////////// CASE: DomainValue ///////////////////////////////
		else if (element instanceof DomainValue){
			GraphDomain domain = (GraphDomain)graphHash.get(((DomainValue)element).getDomainID());
			domain.removeChildDomainValue(element.getId());
		}
		
		////////////////////// CASE: Attribute ////////////////////////////////
		else if (element instanceof Attribute){
			// remove attribute from domainList
			GraphDomain domain = (GraphDomain)graphHash.get(((GraphAttribute)element).getDomainID());
			if (domain == null){
				System.out.println("[E] GraphBuilder:deleteSchemaElement -- Attribute with ID " + schemaID + 
						" refers to a non-existant domain (ID " + ((GraphAttribute)element).getDomainID() + ")");
				return false;
			}
			else{
				domain.removeParentAttribute(element.getId());
				// if deleted last attribute referring to domain, delete domain
				if (domain.getParents().size() == 0){
					deleteSchemaElement(domain.getId());
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
				entity.removeChildAttribute(element.getId());
			}
			graphHash.remove(element.getId());
		}
	
		///////////////////////////// CASE: Entity ////////////////////////////
		else if (element instanceof Entity){
			
			// cannot delete entity with child Entities OR subtypes
			if (((GraphEntity)element).getChildEnititiesContained().size() > 0 || ((GraphEntity)element).getSubtypeEntity().size() > 0) {
				System.out.println("[E] GraphBuilder:deleteSchemaElement -- Entity with ID " + element.getId() + 
						" has child Entities and cannot be deleted");
				return false;
			}
			
			// delete all attributes for entity
			
			for (GraphAttribute attribute : ((GraphEntity)element).getChildAttributes()){
				// remove attribute from domainList
				GraphDomain domain = (GraphDomain)graphHash.get(attribute.getDomainID());
				if (domain == null){
					System.out.println("[E] GraphBuilder:deleteSchemaElement -- Attribute with ID " + schemaID + 
							" refers to a non-existant domain (ID " + ((GraphAttribute)element).getDomainID() + ")");
					return false;
				}
				else{
					domain.removeParentAttribute(attribute.getId());
					// if deleted last attribute referring to domain, delete domain
					if (domain.getParents().size() == 0){
						deleteSchemaElement(domain.getId());
					}
				}
			}
			
			// delete all containments where entity is child
			for (GraphContainment se : ((GraphEntity)element).getChildContainments()){
				
				// delete containment reference in child 
				GraphEntity parent = (GraphEntity) graphHash.get(se.getParentID());
				parent.removeChildEntityContained(element.getId());
				parent.removeChildContainment(se.getId());
				// delete containment in graph
				graphHash.remove(se.getId());
			}
			for (Containment se : ((GraphEntity)element).getParentContainments()){
				SchemaElement child = (GraphEntity) graphHash.get(se.getChildID());
				if (child instanceof GraphDomain){
					((GraphDomain)child).removeParentEntity(element.getId()); 
					((GraphDomain)child).removeChildContainment(se.getId()); 	
				} 
				else if (child instanceof GraphEntity){
					((GraphEntity)child).removeParentEnitityContained(element.getId()); 
					((GraphEntity)child).removeChildContainment(se.getId()); 
				}
				// delete containment in graph
				graphHash.remove(se.getId());
			}
			
			// delete all relationships where entity left / right related
			for (GraphRelationship rel : ((GraphEntity)element).getRelationships()){
				GraphEntity other = null;
				if (rel.getLeftID().equals(element.getId())) other = (GraphEntity)graphHash.get(rel.getRightID()); 
				else other = (GraphEntity)graphHash.get(rel.getLeftID()); 
				
				if (other == null){
					System.out.println("[E] GraphBuilder:deleteSchemaElement -- Entity with ID " + element.getId() + 
						" has relationship that refers to non-existant entity ");
					return false;
				}
				// 1) remove relationship 2) remove leftRelated 3) remove rightRelated
				other.removeLeftEntityRelated(element.getId());
				other.removeRightEntityRelated(element.getId());
				graphHash.remove(rel.getId());
			}
			
			// delete all supertypes / subtypes 
			for (GraphSubtype subType : ((GraphEntity)element).getSubtypes()){
				GraphEntity subtypeEntity = (GraphEntity)graphHash.get(subType.getChildID());
				if (subtypeEntity == null){
					System.out.println("[E] GraphBuilder:deleteSchemaElement -- Entity with ID " + element.getId() + 
						" has relationship that refers to non-existant entity ");
					return false;
				}
				subtypeEntity.removeSupertype(subType.getId());
				subtypeEntity.removeSupertypeEntity(element.getId());
				graphHash.remove(subType.getId());
			}
			for (GraphSubtype superType : ((GraphEntity)element).getSupertypes()){
				GraphEntity supertypeEntity = (GraphEntity)graphHash.get(superType.getChildID());
				if (supertypeEntity == null){
					System.out.println("[E] GraphBuilder:deleteSchemaElement -- Entity with ID " + element.getId() + 
						" has relationship that refers to non-existant entity ");
					return false;
				}
				supertypeEntity.removeSubtype(superType.getId());
				supertypeEntity.removeSubtypeEntity(element.getId());
				graphHash.remove(superType.getId());
			}
			graphHash.remove(element.getId());
		}
		
		// process aliases
		ArrayList<SchemaElement> elems = new ArrayList<SchemaElement>(graphHash.values());
		for (int i=0; i<graphHash.keySet().size(); i++){
			if (elems.get(i) instanceof GraphAlias && ((GraphAlias)elems.get(i)).getElementID().equals(element.getId()))  {
				graphHash.remove(elems.get(i).getId());
			}
		}
		return true;
	}

	
	/**
	 * addElements(): Adds a set of schema elements to the graph. NOTE: assumes the
	 * elements are a CONSISTENT set (e.g., if a new node is added, all proper edges 
	 * are being added as well)
	 * 
	 * @param schemaElements set of schema elements to add to graph
	 * @param schemaID the id of the schema
	 * @return
	 */
	public ArrayList<SchemaElement> addElements(ArrayList<SchemaElement> schemaElements, Integer passedSchemaID){
		
		if (graphHash == null){
			graphHash = new HashMap<Integer, SchemaElement>();
			graphHash.put(passedSchemaID, new GraphEntity(passedSchemaID, new String("The Schema"),new String("The Schema"),passedSchemaID));
			
		} 
		// Update The Schema (if schema changes)
		else if (passedSchemaID.equals(schemaID) == false){
				
			for (SchemaElement se : getSchemaElements(GraphContainment.class)){
				GraphContainment c = (GraphContainment)se;
				if (c.getParentID().equals(this.schemaID)){
					c.setParentID(passedSchemaID);
				}
			}
			graphHash.remove(this.schemaID);
			graphHash.put(passedSchemaID, new GraphEntity(passedSchemaID, new String("The Schema"),new String("The Schema"),passedSchemaID));
			this.schemaID = passedSchemaID;
					
		}
		
		ArrayList<SchemaElement> edges = new ArrayList<SchemaElement>();
		ArrayList<SchemaElement> aliases = new ArrayList<SchemaElement>();
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
				else if (schemaElement instanceof Alias){
					aliases.add(schemaElement);
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
					if (domain == null){
						System.out.println("[E] GraphBuilder:build -- NAME: " + schemaElement.getName() + " [" + schemaElement.getId() + "]" + " is attribute referencing non-existent domain with id " + attribute.getDomainID());
						throw new NullPointerException();
					}
					attribute.setDomainType(domain);
					domain.addParentAttribute(attribute);
					GraphEntity entity = (GraphEntity)graphHash.get(attribute.getEntityID());
					if (entity == null){
						System.out.println("[E] GraphBuilder:build -- NAME: " + schemaElement.getName() +" " + " "+" [" + schemaElement.getId() + "]" + " is attribute referencing non-existent entity with id " +  attribute.getEntityID());
						throw new NullPointerException();
					}
					attribute.setParentEntity(entity);
					entity.addChildAttribute(attribute);
				}
			
				else if(schemaElement instanceof DomainValue){ //node
					GraphDomainValue domainValue = (GraphDomainValue)graphHash.get(schemaElement.getId());
					GraphDomain domain = (GraphDomain)graphHash.get(domainValue.getDomainID());
					if (domain == null){
						System.out.println("[E] GraphBuilder:build -- NAME: " + schemaElement.getName() + " [" + schemaElement.getId() + "]" + " is domain value referencing non-existent domain with id " + domainValue.getDomainID());
						throw new NullPointerException();
					}
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
					// TODO: This is a patch to fix Galaxy bug
					if (parent == null){
						graphElement.setParentID(passedSchemaID);
						parent = (GraphEntity)graphHash.get(passedSchemaID);
						//System.out.println("[E] GraphBuilder:build -- NAME: " + schemaElement.getName() + " [" + schemaElement.getId() + "]" +  " is containment that references non-existent parent with id " +  ((Containment)schemaElement).getParentID());
					}
					if (child == null)
						System.out.println("[E] GraphBuilder:build -- NAME: " + schemaElement.getName() + " [" + schemaElement.getId() + "]" +  " is containment that references non-existent child with id " +  ((Containment)schemaElement).getChildID());
					if (parent == null || child == null) throw new NullPointerException();
					
					graphElement.setParent(parent);
					graphElement.setChild(child);
					
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
					if (leftEntity == null) System.out.println("[E] GraphBuilder:build --  " + schemaElement.getId() + " is relationship referencing non-existent left entity with id " + rel.getLeftID());
					if (rightEntity == null) System.out.println("[E] GraphBuilder:build --  " + schemaElement.getId() + " is relationship referencing non-existent right entity with id " + rel.getRightID());  
					if (leftEntity == null || rightEntity == null) throw new NullPointerException();
					
					rel.setParent(leftEntity);
					rel.setChild(rightEntity);
					leftEntity.addRelationship(rel);
					rightEntity.addRelationship(rel);
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
					if (parentEntity == null) System.out.println("[E] GraphBuilder:build --  " + schemaElement.getId() + " is subtype referencing non-existent parent element with id " + subtype.getParentID());
					if (childEntity == null) System.out.println("[E] GraphBuilder:build --  " + schemaElement.getId() + " is subtype referencing non-existent child element with id " + subtype.getChildID());
					if (parentEntity == null || childEntity == null) throw new NullPointerException();
					
					subtype.setParent(parentEntity);
					subtype.setChild(childEntity);	
					parentEntity.addSubtypeEntity(childEntity);
					parentEntity.addSubtype(subtype);
					childEntity.addSupertypeEntity(parentEntity);
					childEntity.addSupertype(subtype);
				}
				
				else {
					System.out.println("[E] GraphBuilder:build -- SchemaElement " + schemaElement.getId() + " has unexpected type " + schemaElement.getClass().toString());
					graphHash.put(schemaElement.getId(), schemaElement);
				}
				
			} // end for(SchemaElement schemaElement: edges){
			
			// Add the alias information to the graph
			for(SchemaElement schemaElement : aliases){
				GraphAlias alias = new GraphAlias((Alias) schemaElement);
				GraphSchemaElement parent = (GraphSchemaElement)graphHash.get(alias.getElementID());
				if (parent != null) parent.setAlias(alias);
				else {
					System.out.println("[E] GraphBuilder:build -- SchemaElement " + alias.getId() + " is alias referrning to non-existent element id " + alias.getElementID());
					throw new Exception();
				}
				graphHash.put(alias.getId(), alias);
			}
			
			// Calculate the pathNames for all "non-named" entities
			for (SchemaElement entity : graphHash.values()){
				if (entity instanceof GraphEntity && (entity.getName() == null || entity.getName().length() == 0)){
					// calculate path to root
					String pathString = "/";
					GraphEntity current = (GraphEntity)entity;
					boolean done = false;
					while (done == false){
						// get set of containments where current entity is a child
						ArrayList<GraphContainment> containments = current.getChildContainments();	
						if (containments.size() == 0){
							System.out.println("[E] GraphBuilder:build -- SchemaElement " + current.getId() + "has no containments in which they are children" );
							done = true;
						}
						else {
							pathString = new String("/" + containments.get(0).getName() + pathString);
							GraphEntity parent = containments.get(0).getParent();
							if ((parent.getName().length() > 0 && parent.getName().contains("/") == false)  || parent.getId().equals(this.schemaID)){ 
								
								if (parent.getId().equals(this.schemaID) == false){
									// add parent 
									pathString = new String(parent.getName() + pathString);
								}
								done = true; 
							}
							else {
								current = parent;
							}
						}
					}
					((GraphEntity)entity).setPath(pathString);
				}
			}
			
		} catch(Exception e){
			System.out.println("[E] GraphBuilder:build -- Error assigning implicit connections for schemaElements");		
			e.printStackTrace();
		}
		
		// return the graph elements (both NODES and EDGES)
		return getSchemaElements();
		
	} // end method build
	
}