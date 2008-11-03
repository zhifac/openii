// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

import model.Schemas;
import model.SelectedObjects;

/** Class for displaying the schema tree for the specified schema */
class SchemaTree extends JTree implements MouseMotionListener, TreeWillExpandListener
{	
	/** Stores the parent of this schema tree */
	private ExplorerPane parent;
	
	/** Stores the schema associated with this tree */
	private Integer schemaID;
	
	/** Generates a schema tree node */
	private SchemaTreeNode getSchemaTreeNode(Object object)
	{
		if(object instanceof Integer && Schemas.getSchemaElement((Integer)object)!=null)
			object = new AliasedSchemaElement(schemaID, (Integer)object);
		return new SchemaTreeNode(object);
	}
	
	/** Expands the specified schema tree */
	private void expandSchema(SchemaTreeNode root)
	{
		// Retrieve the root schema
		Integer schemaID = (Integer)root.getUserObject();
		
		// Only display entities and attributes for the selected node
		if(root==getModel().getRoot())
		{
			// Retrieve the schema graph
			HierarchicalGraph graph = Schemas.getGraph(schemaID);
			
			// Display the schema entities
			ArrayList<SchemaElement> entities = graph.getRootElements();
			if(entities==null)
				graph.getElements(Entity.class);
			if(entities.size()>0)
			{
				SchemaTreeNode entitiesNode = getSchemaTreeNode("Entities");
				for(SchemaElement entity : entities)
				{
					SchemaTreeNode entityNode = getSchemaTreeNode(entity.getId());
					for(SchemaElement childEntity : graph.getChildElements(entity.getId()))
						entityNode.add(getSchemaTreeNode(childEntity.getId()));
					entitiesNode.add(entityNode);
				}
				root.add(entitiesNode);
			}
			
			// Display the schema relationships (if any exist)
			ArrayList<SchemaElement> relationships = graph.getElements(Relationship.class);
			if(relationships.size()>0)
			{
				SchemaTreeNode relationshipsNode = getSchemaTreeNode("Relationships");
				for(SchemaElement relationship : relationships)
					relationshipsNode.add(getSchemaTreeNode(relationship.getId()));
				root.add(relationshipsNode);
			}
			
			// Display the schema domains (if any exist)
			ArrayList<SchemaElement> domains = graph.getElements(Domain.class);
			int domainCount = 0;
			for(SchemaElement domain : domains) if(domain.getId()>0) domainCount++;
			if(domainCount>0)
			{
				SchemaTreeNode domainsNode = getSchemaTreeNode("Domains");
				for(SchemaElement domain : domains)
				{
					SchemaTreeNode domainNode = getSchemaTreeNode(domain.getId());
					for(DomainValue domainValue : graph.getDomainValuesForDomain(domain.getId()))
						domainNode.add(getSchemaTreeNode(domainValue.getId()));					
					domainsNode.add(domainNode);
				}
				root.add(domainsNode);
			}
		}
		
		// Display the schema parent schemas (if any exist)
		ArrayList<Integer> parentSchemaIDs = Schemas.getParentSchemas(schemaID);
		if(parentSchemaIDs!=null && parentSchemaIDs.size()>0)
		{
			SchemaTreeNode parentsNode = getSchemaTreeNode("Parents");
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
	
	/** Constructs the base schema tree */
	SchemaTree(Integer schemaID, ExplorerPane parent)
	{
		this.parent = parent;
		this.schemaID = schemaID;

		// Place the schema into the schema tree
		SchemaTreeNode root = new SchemaTreeNode(schemaID);
		setBorder(new EmptyBorder(0,3,0,0));
		setModel(new DefaultTreeModel(root));
		setCellRenderer(new SchemaTreeCellRenderer());

		// Listens for tree actions
		addMouseMotionListener(this);
		addTreeWillExpandListener(this);
		
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

	/** Generate schema nodes as tree is expanded outward */
	public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException
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
	
	/** Displays info on whatever schema object the mouse is over */
	public void mouseMoved(MouseEvent e)
	{
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
		{ parent.clearInfo(); }

	// Unused listener event
	public void treeWillCollapse(TreeExpansionEvent e) throws ExpandVetoException {}
	public void mouseDragged(MouseEvent e) {}
}