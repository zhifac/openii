package org.mitre.schemastore.graph;

import java.util.*;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.*;

public class GraphBuilder{
	
	
	public static void main() throws Exception {
		
		
		SchemaStoreClient clientLocal = new SchemaStoreClient("http://localhost:8080/SchemaStore/services/SchemaStore");
////	Integer schemaID = 54131; // uno
		    Integer schemaID = 54029; // PO1
////	Integer schemaID = 54006; // ejournal
////	Integer schemaID = 53901; // CoT
////	Integer schemaID = 52510; // EntityProduct
		    
			Schema s = clientLocal.getSchema(schemaID);
			ArrayList<SchemaElement> elementList =  clientLocal.getSchemaElements(schemaID);
			
			ArrayList<SchemaElement> graphObject = build(elementList, schemaID);
	
			for (SchemaElement se : graphObject){
				
				if (se instanceof GraphAttribute) {
				
				} else if (se instanceof GraphContainment) {
					
				} else if (se instanceof GraphDomain) {
					
				} else if (se instanceof GraphDomainValue) {
					
				} else if (se instanceof GraphEntity) {
				
				} else if (se instanceof GraphRelationship) {
				
				} else if (se instanceof GraphSubtype) {
				
				}
				
			}
			
	
	}
	
	public static ArrayList<SchemaElement> build(ArrayList<SchemaElement> schemaElements, int schemaID){
		
		//Graph hash stores all of the nodes.  I make no assumptions about the order of things
		//in the list.  So, edges may appear before nodes.  hence, store nodes into the 
		//hashtable.  then, iterate through the edges and connect them.
		HashMap<Integer, SchemaElement> graphHash = new HashMap<Integer, SchemaElement>();
		ArrayList<SchemaElement> edges = new ArrayList<SchemaElement>();
		
		// add schema
		try {
			graphHash.put(schemaID, new GraphEntity(schemaID));
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
					// add edges
					edges.add(schemaElement);
				}
			}
		
		
			// make SECOND interation through nodes to make IMPLICIT connections without
			// a corresponding edge, specifically: 1) attributes --> entities, 
			// 2) entities --> attrs, 3) domain values --> domains, 
			// 4) domains --> attributes (NOTE ARROW DIRECTION)
			for(SchemaElement schemaElement : schemaElements){
	
				if(schemaElement instanceof GraphAttribute){ //node
					GraphAttribute attribute = (GraphAttribute)graphHash.get(schemaElement.getId());
					GraphDomain domain = (GraphDomain)graphHash.get(attribute.getDomainID());
					attribute.domainType = domain;
					domain.addParentAttributes(attribute);
					GraphEntity entity = (GraphEntity)graphHash.get(attribute.getEntityID());
					attribute.parentEntity = entity;
					entity.addChildAttributes(attribute);
				}
			
				else if(schemaElement instanceof GraphDomainValue){ //node
					GraphDomainValue domainValue = (GraphDomainValue)graphHash.get(schemaElement.getId());
					GraphDomain domain = (GraphDomain)graphHash.get(domainValue.getDomainID());
					domainValue.setParentDomain(domain);
					domain.addChildDomainValues(domainValue);
				}
			}
		} catch(Exception e){
			System.out.println("[E] GraphBuilder:build -- Error assigning implicit connections for schemaElements");
			e.printStackTrace();
		}
		
		try {
			//now, iterate through the edges, and connect.
			for(SchemaElement schemaElement : edges){
				
				if(schemaElement instanceof Containment){
					GraphContainment graphElement = new GraphContainment((Containment)schemaElement);
					
					GraphEntity parent = (GraphEntity)graphHash.get(((GraphContainment)graphElement).getParentID());
					SchemaElement child = graphHash.get(((GraphContainment)graphElement).getChildID());
					// add entity <--> entity connections
					if (child instanceof GraphEntity){
						parent.addChildEnititiesContained((GraphEntity)child);
						parent.addParentContainments(graphElement);
						((GraphEntity)child).addParentEnitiesContained(parent);
						((GraphEntity)child).addChildContainments(graphElement);
						
					}
					else if (child instanceof GraphDomain){
						parent.addChildDomains((GraphDomain)child);
						parent.addParentContainments(graphElement);
						((GraphDomain)child).addParentEntities(parent);
						((GraphDomain)child).addChildContainments(graphElement);
					}
				} // end if(schemaElement instanceof Containment){
				
				else if(schemaElement instanceof Relationship){ 
					GraphRelationship rel = new GraphRelationship((Relationship) schemaElement);
					GraphEntity leftEntity  = (GraphEntity)graphHash.get(rel.getLeftID());
					GraphEntity rightEntity = (GraphEntity)graphHash.get(rel.getRightID());
		
					leftEntity.addLeftEntitiesRelated(rightEntity);
					leftEntity.addRelationships(rel);
					rightEntity.addRightEntitiesRelated(leftEntity);
					leftEntity.addRelationships(rel);
				}
				
				else if(schemaElement instanceof Subtype){ 
					GraphSubtype subtype = new GraphSubtype((Subtype) schemaElement);
					GraphEntity parentEntity = (GraphEntity)graphHash.get(subtype.getParentID());
					GraphEntity childEntity  = (GraphEntity)graphHash.get(subtype.getChildID());
					
					//should be all hooked up now.
					parentEntity.addChildSubtypes(childEntity);
					childEntity.addParentSubtypes(parentEntity);
				}
				
			} // end for(SchemaElement schemaElement: edges){
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// return the graph elements (both NODES and EDGES)
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		retVal.addAll(graphHash.values());
		retVal.addAll(edges);
		return retVal;
		
	} // end method build
		
	
}