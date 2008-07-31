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
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

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
	private SchemaTreeNode getSchemaTreeNode(Object object)
	{
		if(object instanceof Integer && Schemas.getSchemaElement((Integer)object)!=null)
			object = new AliasedSchemaElement(new GraphEntity((Integer) object,"","", (Integer) object));
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
			// Display the schema entities
			ArrayList<SchemaElement> entities = Schemas.getSchemaElements(schemaID,Entity.class);
			if(entities==null)
				Schemas.getSchemaElements(schemaID,Entity.class);
			if(entities.size()>0)
			{
				entitiesNode = getSchemaTreeNode("Entities");
				for(SchemaElement entity : entities)
				{
					// Display the entity attributes
					SchemaTreeNode entityNode = getSchemaTreeNode(entity.getId());
					ArrayList<Attribute> attributes = Schemas.getAttributesFromEntity(schemaID,entity.getId());
					if(attributes.size()>0)
						for(Attribute attribute : attributes)
							entityNode.add(getSchemaTreeNode(attribute.getId()));
					else entityNode.add(getSchemaTreeNode("<No Attributes>"));
					entitiesNode.add(entityNode);
				}
				root.add(entitiesNode);
			}
			
			// Display the schema relationships (if any exist)
			ArrayList<SchemaElement> relationships = Schemas.getSchemaElements(schemaID,Relationship.class);
			if(relationships.size()>0)
			{
				relationshipsNode = getSchemaTreeNode("Relationships");
				for(SchemaElement relationship : relationships)
					relationshipsNode.add(getSchemaTreeNode(relationship.getId()));
				root.add(relationshipsNode);
			}
			
			// Display the schema domains (if any exist)
			ArrayList<SchemaElement> domains = Schemas.getSchemaElements(schemaID,Domain.class);
			int domainCount = 0;
			for(SchemaElement domain : domains) if(domain.getId()>0) domainCount++;
			if(domainCount>0)
			{
				domainsNode = getSchemaTreeNode("Domains");
				for(SchemaElement domain : domains)
					if(domain.getId()>=0) domainsNode.add(getSchemaTreeNode(domain.getId()));
				root.add(domainsNode);
			}
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
	
	/** Expand the specified node from the specified root */
	private SchemaTreeNode expandNode(SchemaTreeNode schemaNode, Object object)
	{
		schemaNode = (SchemaTreeNode)schemaNode.getChildNode(object);
		if(schemaNode!=null) expandPath(new TreePath(schemaNode.getPath()));
		return schemaNode;
	}

	/** Returns the base node to which the specified schema object attaches */
	private SchemaTreeNode getBaseNode(SchemaElement schemaElement)
	{
		if(schemaElement instanceof Entity) return entitiesNode;
		else if(schemaElement instanceof Domain) return domainsNode;
		else if(schemaElement instanceof Relationship) return relationshipsNode;	
		else if(schemaElement instanceof Attribute)
		{
			Integer entityID = ((Attribute)schemaElement).getEntityID();
			return (SchemaTreeNode)entitiesNode.getChildNode(entityID);
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
			SchemaTreeNode node = getSchemaTreeNode(schemaElement.getId());
			if(schemaElement instanceof Entity) node.add(getSchemaTreeNode("<No Attributes>"));
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
		else parent.displayInfo(schemaElement,schemaID);
	}
	
	/** Clears displayed info if mouse moves off of schema tree pane */
	public void mouseExited(MouseEvent e)
		{ super.mouseExited(e); parent.clearInfo(); }

	// Unused listener event
	public void treeCollapsed(TreeExpansionEvent e) {}
}