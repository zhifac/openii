// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.mitre.schemastore.graph.GraphBuilder;
import org.mitre.schemastore.graph.GraphEntity;
import org.mitre.schemastore.graph.GraphContainment;
import org.mitre.schemastore.graph.*;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.graph.*;
import java.util.*;
import view.sharedComponents.LinkedTree;

import model.AliasedSchemaElement;
import model.Schemas;
import model.SelectedObjects;

/** Class for displaying the schema tree for the specified schema */
class SchemaTree extends LinkedTree implements MouseListener, MouseMotionListener, TreeExpansionListener
{
	/** Stores the parent of this schema tree */
	private ExplorerPane parent;
	
	/** Stores the schema associated with this tree */
	private Integer schemaID;
	
	// Keeps track of various tree objects
	private SchemaTreeNode entitiesNode;
	private SchemaTreeNode relationshipsNode;
	private SchemaTreeNode domainsNode;
	private SchemaTreeNode parentsNode;
	private GraphBuilder graph;

	
	
	
	/** Generates a schema tree node */
//	private SchemaTreeNode getSchemaTreeNode(Object object)
//	{
//		if(object instanceof Integer && Schemas.getSchemaElement((Integer)object)!=null)
//			object = new AliasedSchemaElement(schemaID, (Integer)object);
//		return new SchemaTreeNode(Schemas.getSchema(schemaID),object,parent);
//	}
	
	private SchemaTreeNode getSchemaTreeNode(Object object)
	{	
		if(object instanceof Integer && Schemas.getSchemaElement((Integer)object)!=null){
			if ( graph.getSchemaElement((Integer)object) == null){
				// add the schemaElement to the graph
				
				// TODO: hook the NEW object into the graph
				// -- for attribute
				
				SchemaElement se = Schemas.getSchemaElement((Integer)object);
				ArrayList<SchemaElement> elementsToAdd = new ArrayList<SchemaElement>();
				if (se instanceof Entity){
					// add containment to [root, Entity]
					GraphEntity newEntity = new GraphEntity((Entity)se);
					Containment c = new Containment(0,"","",schemaID,se.getId(),0,1,schemaID);
					Integer assignedID = Schemas.addSchemaElement(c);
					GraphContainment newContainment = new GraphContainment((Containment)Schemas.getSchemaElement(assignedID));
					elementsToAdd.add(newEntity);
					elementsToAdd.add(newContainment);
					
					
				} else if (se instanceof Attribute){
					GraphAttribute newAttribute = new GraphAttribute((Attribute)se);
					elementsToAdd.add(newAttribute);
					//System.err.println("graphAttr: " + "NAME: " + graphAttr.getName() + " ID " + graphAttr.getId() + " ENTITY-ID " + graphAttr.getEntityID());
					
				} else if (se instanceof Domain){
					// add containment to [root, Domain]
					
					GraphDomain newDomain = new GraphDomain((Domain)se);
					elementsToAdd.add(newDomain);
		
					//System.err.println("graphDomain: " + "NAME: " + newDomain.getName() + " ID " + newDomain.getId() );
				
				} else if (se instanceof DomainValue){
					GraphDomainValue newDomainValue = new GraphDomainValue((DomainValue)se);
					elementsToAdd.add(newDomainValue);
					//System.err.println("graphDomainValue: " + "NAME: " + graphDomainValue.getName() + " ID " + graphDomainValue.getId() + " DOMAIN-ID " + graphDomainValue.getDomainID());
				
				} else if (se instanceof Relationship) {
					GraphRelationship newRelationship = new GraphRelationship((Relationship)se);
					elementsToAdd.add(newRelationship);
				
				}
				graph.addElements(elementsToAdd, schemaID);
			}
			
			object = new AliasedSchemaElement(graph.getSchemaElement((Integer)object));
		}
		return new SchemaTreeNode(Schemas.getSchema(schemaID),object,parent);
	}
	
	/** Expands the specified schema tree */
	private void expandSchema(SchemaTreeNode root)
	{
		// Retrieve the root schema
		Integer schemaID = (Integer)root.getUserObject();
		
		// Only display entities and attributes for the selected node
		if(root==getModel().getRoot())
		{
	
			// Display all root Entities in schema
			GraphEntity schemaRootElement = (GraphEntity)graph.getSchemaElement(schemaID);
			ArrayList<GraphEntity> rootEntities = schemaRootElement.getChildEnititiesContained();
		
			
			
		//	if(rootEntities.size()>0){
				entitiesNode = getSchemaTreeNode("Entities");
				for(SchemaElement element : rootEntities){
					GraphEntity entity = (GraphEntity)element;
					SchemaTreeNode entityNode = addEntity(entity);
				//	basenodeByID.put(entity.getId(), entityNode);
					entitiesNode.add(entityNode);
				}
				root.add(entitiesNode);
		//	}
			
			// Display the schema relationships (if any exist)
			ArrayList<SchemaElement> relationships = Schemas.getSchemaElements(schemaID,Relationship.class);
		//	if(relationships.size()>0){
				relationshipsNode = getSchemaTreeNode("Relationships");
				for(SchemaElement relationship : relationships)
					relationshipsNode.add(getSchemaTreeNode(relationship.getId()));
				root.add(relationshipsNode);
		//	}
			
			// Display the schema domains (if any exist)
			ArrayList<SchemaElement> domains = graph.getSchemaElements(Domain.class);	
			int domainCount = 0;
			for(SchemaElement domain : domains) if(domain.getId()>0) domainCount++;
	//		if(domainCount>0){
				domainsNode = getSchemaTreeNode("Domains");
				for(SchemaElement domain : domains)
					if(domain.getId()>=0){ 
						SchemaTreeNode domainNode = getSchemaTreeNode(domain.getId());
						
						ArrayList<GraphDomainValue> domainValues  = ((GraphDomain)domain).getChildDomainValues();
						if(domainValues.size()>0){
							for(DomainValue domainValue: domainValues)
								domainNode.add(getSchemaTreeNode(domainValue.getId()));
						}
						else domainNode.add(getSchemaTreeNode("<No DomainValues Specified>"));
						
						domainsNode.add(getSchemaTreeNode(domain.getId()));
					}
				root.add(domainsNode);
	//		}
		}
		
		// Display the schema parent schemas (if any exist)
		ArrayList<Integer> parentSchemaIDs = Schemas.getParentSchemas(schemaID);
		if(parentSchemaIDs!=null && parentSchemaIDs.size()>0)
		{
			parentsNode = getSchemaTreeNode("Parents");
			for(Integer parentSchemaID : parentSchemaIDs)
				parentsNode.add(getSchemaTreeNode(parentSchemaID));
			root.add(parentsNode);
		}
			
		// Display the schema child schemas (if any exist)
		ArrayList<Integer> childSchemaIDs = Schemas.getChildSchemas(schemaID);
		if(childSchemaIDs!=null && childSchemaIDs.size()>0)
		{
			SchemaTreeNode childrenNode = getSchemaTreeNode("Children");
			for(Integer childSchemaID : childSchemaIDs)
				childrenNode.add(getSchemaTreeNode(childSchemaID));
			root.add(childrenNode);
		}
	}
	
	SchemaTreeNode addEntity(GraphEntity passedRoot){
		
		SchemaTreeNode rootNode = getSchemaTreeNode(passedRoot.getId()); 
		
		// add attributes for the current entity
		ArrayList<GraphAttribute> attributes = passedRoot.getChildAttributes();
		if(attributes.size()>0){
			for(Attribute attribute : attributes){
				SchemaTreeNode attributeNode = getSchemaTreeNode(attribute.getId());
			//	basenodeByID.put(attribute.getId(), attributeNode);
				rootNode.add(getSchemaTreeNode(attribute.getId()));
			}
		}
		else rootNode.add(getSchemaTreeNode("<No Attributes>"));
		
		
		// Display the entity's containments
		ArrayList<GraphContainment> containments = passedRoot.getParentContainments();
		
		if(containments.size()>0){
			for(GraphContainment containment : containments){
				SchemaTreeNode containmentNode = getSchemaTreeNode(containment.getId());
				//basenodeByID.put()
				if (containment.getChild() instanceof GraphEntity){
					containmentNode.add(addEntity((GraphEntity)containment.getChild()));
				}
				else{ 
					containmentNode.add(getSchemaTreeNode(containment.getChild().getId()));
				}
				rootNode.add(containmentNode);
				
			}
		}
		//else rootNode.add(getSchemaTreeNode("<No Containments>"));	
		return rootNode;
	}
	
	
	
	/** Expand the specified node from the specified root */
	private SchemaTreeNode expandNode(SchemaTreeNode schemaNode, Object object)
	{
		schemaNode = (SchemaTreeNode)schemaNode.getChildNode(object);
		if(schemaNode!=null) expandPath(new TreePath(schemaNode.getPath()));
		return schemaNode;
	}


	//TODO: need to re-write this!
	/** Returns the base node to which the specified schema object attaches */
	private SchemaTreeNode getBaseNode(SchemaElement schemaElement)
	{
		if(schemaElement instanceof Entity){ 
			// find parent containment 
			//Integer entity 
			return entitiesNode;
		}
		else if(schemaElement instanceof Attribute){
			// find parent entity
			
			Integer entityID = ((Attribute)schemaElement).getEntityID();
			return (SchemaTreeNode)entitiesNode.getChildNode(entityID);
		}
			
		else if(schemaElement instanceof Domain){
			return domainsNode;
		}
		
		else if(schemaElement instanceof Relationship){
			return relationshipsNode;	
		}
		return null;
	}
	
	
	
	/** Constructs the base schema tree */
	SchemaTree(Integer schemaID, ExplorerPane parent)
	{
		this.parent = parent;
		this.schemaID = schemaID;

		graph = new GraphBuilder(Schemas.getSchemaElements(schemaID, null), schemaID);
		
		// Place the schema into the schema tree
		SchemaTreeNode root = new SchemaTreeNode(Schemas.getSchema(schemaID),schemaID,parent);
		setBorder(new EmptyBorder(0,3,0,0));
		setModel(new DefaultTreeModel(root));
		setCellRenderer(new SchemaTreeCellRenderer());

		// Listens for tree actions
		addMouseListener(this);
		addMouseMotionListener(this);
		addTreeExpansionListener(this);
		
		// Expand out the first level of the schema tree
		expandSchema(root);
		for(int i=0; i<root.getChildCount(); i++)
			expandPath(new TreePath(((SchemaTreeNode)root.getChildAt(i)).getPath()));
	}

	/** Indicates if the schema associated with this tree is committed */
	public boolean isCommitted()
		{ return Schemas.getSchema(schemaID).getLocked(); }
	
	/** Highlight the compared schema */
	public void selectedComparisonSchemaChanged()
	{
		Integer selSchema = SelectedObjects.getSelectedSchema();
		Integer compSchema = SelectedObjects.getSelectedComparisonSchema();
		if(compSchema!=null && Schemas.getSchema(schemaID).getLocked())
		{
			// Identify the expansion path that needs to be displayed
			Integer rootID = Schemas.getRootSchema(selSchema, compSchema);
			ArrayList<Integer> selSchemaPath = Schemas.getSchemaPath(rootID, selSchema);
			ArrayList<Integer> compSchemaPath = Schemas.getSchemaPath(rootID, compSchema);
			
			// Expand out the nodes from the selected schema to the shared root schema
			SchemaTreeNode schemaNode = (SchemaTreeNode)getModel().getRoot();			
			for(int i=selSchemaPath.size()-2; i>=0; i--)
			{
				Schema schema = Schemas.getSchema(selSchemaPath.get(i));
				schemaNode = expandNode(schemaNode,new String("Parents"));
				schemaNode = expandNode(schemaNode,schema.getId());
			}
			
			// Expand out the nodes from the shared root schema to the comparison schema
			for(int i=1; i<compSchemaPath.size(); i++)
			{
				Schema schema = Schemas.getSchema(compSchemaPath.get(i));
				schemaNode = expandNode(schemaNode,new String("Children"));
				schemaNode = expandNode(schemaNode,schema.getId());
			}
		}
		repaint();
	}
	
	/** Updates the schema name */
	public void schemaUpdated(Schema schema)
		{ ((DefaultTreeModel)getModel()).nodeChanged((SchemaTreeNode)getModel().getRoot()); }
	
	/** Handles the addition of a schema object */
	public void schemaElementAdded(SchemaElement schemaElement)
	{
		SchemaTreeNode baseNode = getBaseNode(schemaElement);
		if(baseNode!=null)
		{
			// TODO: Move add element code down to here
			
			SchemaTreeNode node = getSchemaTreeNode(schemaElement.getId());
			
			if(schemaElement instanceof Entity){ 
				node.add(getSchemaTreeNode("<No Attributes>"));
			}
			if(schemaElement instanceof Attribute && baseNode.getChildCount()==1)
				if(((DefaultMutableTreeNode)baseNode.getChildAt(0)).getUserObject() instanceof String)
					baseNode.removeAllChildren();
			baseNode.insert(node);
			((DefaultTreeModel)getModel()).nodeStructureChanged(baseNode);
		}
	}
	  
	/** Handles the removal of a schema object */
	public void schemaElementRemoved(SchemaElement schemaElement)
	{
		SchemaTreeNode baseNode = getBaseNode(schemaElement);

		System.err.println("***** " + "removing schemaElement id " + schemaElement.getId() + " name " +  schemaElement.getName());
		graph.printGraph();
		graph.deleteSchemaElement(schemaElement.getId());
		
		System.err.println("***** " + "removed schemaElement id " + schemaElement.getId() + " name " +  schemaElement.getName());
		graph.printGraph();
		if(baseNode!=null)
		{
			baseNode.delete(schemaElement.getId());
			if(schemaElement instanceof Attribute)
				if(baseNode.getChildCount()==0) baseNode.add(getSchemaTreeNode("<No Attributes>"));
			((DefaultTreeModel)getModel()).nodeStructureChanged(baseNode);
		}
	}
	
	/** Generate schema nodes as tree is expanded outward */
	public void treeExpanded(TreeExpansionEvent e)
	{
		// Only examine expanded nodes containing child or parent schemas
		SchemaTreeNode node = (SchemaTreeNode)e.getPath().getLastPathComponent();
		if(node.getUserObject().equals("Parents") || node.getUserObject().equals("Children"))
		{
			// Expand all schemas which have yet to be expanded
			for(int i=0; i<node.getChildCount(); i++)
			{
				SchemaTreeNode schemaNode = (SchemaTreeNode)node.getChildAt(i);
				if(isCommitted() && schemaNode.getChildCount()==0)
					expandSchema(schemaNode);
			}
		}
	}
	
	/**Displays info on whatever schema object the mouse is over */
	public void mouseMoved(MouseEvent e)
	{
		// Inform extended class of mouse motion
		super.mouseMoved(e);
		
		// Identify the path over which the mouse is hovering
		TreePath path = getPathForLocation(e.getX(),e.getY());
		
		// Display information about the specified schema object
		SchemaElement schemaElement = null;
		if(path!=null)
		{
			Object userObject = ((SchemaTreeNode)path.getLastPathComponent()).getUserObject();
			if(userObject instanceof AliasedSchemaElement)
				schemaElement = ((AliasedSchemaElement)userObject).getElement();
		}
		if(schemaElement==null) parent.clearInfo();
		else parent.displayInfo(graph,schemaElement,schemaID);
	}
	
	/** Clears displayed info if mouse moves off of schema tree pane */
	public void mouseExited(MouseEvent e)
		{ super.mouseExited(e); parent.clearInfo(); }

	// Unused listener event
	public void treeCollapsed(TreeExpansionEvent e) {}
}