package org.mitre.schemastore.graph;

import java.util.*;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.*;

public class GraphBuilder{
	
	
	public static void main(){
		
		
		SchemaStoreClient clientLocal = new SchemaStoreClient("http://localhost:8080/SchemaStore/services/SchemaStore");
////	Integer schemaID = 54131; // uno
		    Integer schemaID = 54029; // PO1
////	Integer schemaID = 54006; // ejournal
////	Integer schemaID = 53901; // CoT
////	Integer schemaID = 52510; // EntityProduct
		    
//			Schema s = clientLocal.getSchema(schemaID);
//			ArrayList<SchemaElement> elementList =  clientLocal.getSchemaElements(schemaID);
			
	//		build(elementList, schemaID);
	
	
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
					GraphEntity parent = (GraphEntity)graphHash.get(((Containment)schemaElement).getParentID());
					SchemaElement child = graphHash.get(((Containment)schemaElement).getChildID());
					// add entity <--> entity connections
					if (child instanceof GraphEntity){
						parent.addChildEnititiesContained((GraphEntity)child);
						((GraphEntity)child).addParentEnitiesContained(parent);
					}
					else if (child instanceof GraphDomain){
						parent.addChildDomains((GraphDomain)child);
						((GraphDomain)child).addParentEntities(parent);
					}
				} // end if(schemaElement instanceof Containment){
				
				else if(schemaElement instanceof Relationship){ 
					Relationship rel = (Relationship) schemaElement;
					GraphEntity leftEntity  = (GraphEntity)graphHash.get(rel.getLeftID());
					GraphEntity rightEntity = (GraphEntity)graphHash.get(rel.getRightID());
		
					leftEntity.addRightEntitiesRelated(rightEntity);
					rightEntity.addLeftEntitiesRelated(leftEntity);
				}
				
				else if(schemaElement instanceof Subtype){ 
					Subtype subtype = (Subtype) schemaElement;
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