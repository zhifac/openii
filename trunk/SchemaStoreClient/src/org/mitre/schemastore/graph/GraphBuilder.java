package org.mitre.schemastore.graph;

import java.util.Hashtable;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Enumeration;
import org.mitre.schemastore.model.*;

import org.mitre.schemastore.model.SchemaElement;

public class GraphBuilder{
	
	//Hashtable<Integer,GraphObject> hashGraph;
	
	public static GraphObject build(ArrayList<SchemaElement> schema){
		//Graph hash stores all of the nodes.  I make no assumptions about the order of things
		//in the list.  So, edges may appear before nodes.  hence, store nodes into the 
		//hashtable.  then, iterate through the edges and connect them.
		Hashtable<Integer, GraphObject> graphHash = new Hashtable<Integer, GraphObject>();
		Vector<SchemaElement> Edges = new Vector<SchemaElement>();
		for(SchemaElement schemaElement : schema){
			if(schemaElement instanceof Attribute){ //node
				Attribute Attr = (Attribute) schemaElement;
				GraphAttribute GAttr = new GraphAttribute(Attr.getId(), Attr.getName(), Attr.getDescription(), Attr.getBase());
				GAttr.setAttr(Attr);
				graphHash.put(Attr.getId(), GAttr);
			}
			else if(schemaElement instanceof Containment){ //edge
				Edges.add(schemaElement);
			}
			else if(schemaElement instanceof Domain){ //node
				Domain Dom = (Domain) schemaElement;
				GraphDomain GDom = new GraphDomain(Dom.getId(), Dom.getName(), Dom.getDescription(), Dom.getBase());
				graphHash.put(Dom.getId(), GDom);
			}
			else if(schemaElement instanceof DomainValue){ //node
				DomainValue DomVal = (DomainValue) schemaElement;
				GraphDomainValue GDomVal = new GraphDomainValue(DomVal.getId(), DomVal.getName(), DomVal.getDescription(), DomVal.getBase());
				GDomVal.setDomainID(DomVal.getDomainID());
				graphHash.put(DomVal.getId(), GDomVal);
			}
			else if(schemaElement instanceof Relationship){ //edge
				Edges.add(schemaElement);
			}
			else if(schemaElement instanceof Entity){ //node
				Entity Ent = (Entity) schemaElement;
				GraphEntity GEntity = new GraphEntity(Ent.getId(), Ent.getName(), Ent.getDescription(), Ent.getBase());
				graphHash.put(Ent.getId(), GEntity);
			}
			else if(schemaElement instanceof Subtype){ //edge
				Edges.add(schemaElement);
			}
		}
		
		int rootParent=0;
		
		//now, iterate through the edges, and connect.
		for(SchemaElement schemaElement: Edges){
			if(schemaElement instanceof Containment){ 
				Containment connie = (Containment) schemaElement;
				Integer parent = connie.getParentID();
				Integer child = connie.getChildID();
				//The schema node is a virtual node, meaning that the
				//schema store representation of the graph doesn't contain
				//an actual node, even though the containment from the schema to a top
				//level node in the graph exists, and references the non-existant
				//node.  Thus, we must add the node.
				if(!graphHash.containsKey(parent)){
					//we've found the missing schema node.
					GraphSchemaRootNode gsrn = new GraphSchemaRootNode(parent, "Schema Root","Scheam Root", 0);
					graphHash.put(parent,gsrn);
					rootParent = parent;
				}
				GraphObject pObject = graphHash.get(parent);
				GraphObject cObject = graphHash.get(child);
				//pObject has to be an entity.
				//GraphEntity pEntity = null;
				if(pObject instanceof GraphEntity){
					GraphEntity pEntity = (GraphEntity) pObject;
					pEntity.addChildContainment(cObject);
				}
				else if(pObject instanceof GraphSchemaRootNode){
					GraphSchemaRootNode gsrn = (GraphSchemaRootNode) pObject;
					gsrn.addChildContainment(cObject);
				}
				else{ //error.
					System.out.println("Error, Parent Object not valid");
				}
				
				if(cObject instanceof GraphEntity){
					GraphEntity cEntity = (GraphEntity) cObject;
					cEntity.addParentContainment(pObject);
				}
				else if(cObject instanceof GraphDomain){
					GraphDomain cGraphDomain = (GraphDomain) cObject;
					cGraphDomain.addParentEntity((GraphEntity)pObject);
				}
				else{ //error
					System.out.println("Error, Child Object not Valid");
				}
			}
			else if(schemaElement instanceof Relationship){ 
				Relationship rel = (Relationship) schemaElement;
				Integer left = rel.getLeftID();
				Integer right = rel.getRightID();
				GraphObject lObject = graphHash.get(left);
				GraphObject rObject = graphHash.get(right);
				//lObject and rObject have to be an entity.
				GraphEntity lEntity = (GraphEntity) lObject;
				GraphEntity rEntity = (GraphEntity) rObject;
				//should be all hooked up now.
				lEntity.addOutgoingRelationship(rEntity);
				rEntity.addOutgoingRelationship(lEntity);
			}
			else if(schemaElement instanceof Subtype){ 
				Subtype subt = (Subtype) schemaElement;
				Integer parent = subt.getParentID();
				Integer child = subt.getChildID();
				GraphObject pObject = graphHash.get(parent);
				GraphObject cObject = graphHash.get(child);
				//pObject and cObject have to be an entity.
				GraphEntity pEntity = (GraphEntity) pObject;
				GraphEntity cEntity = (GraphEntity) cObject;
				//should be all hooked up now.
				pEntity.addChildSubtype(cEntity);
				pEntity.addParentSubtype(pEntity);
			}
		}
		
		//now, iterate through the elements themselves, and connect Entity to attribute,
		//domain to domain values, etc.
		for(GraphObject gObject: graphHash.values()){
			if(gObject instanceof GraphDomainValue){
				GraphDomainValue gdv = (GraphDomainValue) gObject;
				int domainhook = gdv.domainID;
				GraphObject go = graphHash.get(domainhook);
				//graph object should be a domain.
				gdv.addDomain(((GraphDomain)go));
			}
			else if(gObject instanceof GraphAttribute){
				GraphAttribute ga = (GraphAttribute) gObject;
				//hook up domain and entity.
				int domainhook = ga.getDomainID();
				GraphObject go = graphHash.get(domainhook);
				//graph object should be a domain.
				ga.addDomain(((GraphDomain)go));
				//now, hook up entity.
				int entityhook = ga.getParentEntityID();
				GraphObject go2 = graphHash.get(entityhook);
				ga.addParentEntity(((GraphEntity)go2));
			}
		}
		return graphHash.get(rootParent);
	}
}