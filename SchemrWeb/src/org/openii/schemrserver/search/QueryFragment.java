package org.openii.schemrserver.search;

import java.util.ArrayList;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.Graph;
import org.mitre.schemastore.model.graph.HierarchicalGraph;
import org.mitre.schemastore.porters.schemaImporters.SchemaProperties;
import org.openii.schemrserver.SchemaUtility;

/**
 * Query Fragment is a single tree
 */
public class QueryFragment {

//	private static final Logger logger = Logger.getLogger(QueryFragment.class.getName());

	private ArrayList<QueryFragment> queryFragments = null;
	private String name = null;
	private String type = null;
		
	public QueryFragment(String name, String type) {
		if (name == null || name.equals("")) throw new IllegalArgumentException("Name must be specified");
		this.queryFragments = new ArrayList<QueryFragment>();
		this.name = name;
		this.type = type;
	}

	public QueryFragment(String schemaName, ArrayList<SchemaElement> se, HierarchicalGraph hg) {

		if (hg == null) throw new IllegalArgumentException("HierarchicalGraph must be specified");

		this.name = schemaName;
		this.type = SchemaUtility.SCHEMA;
		this.queryFragments = new ArrayList<QueryFragment>();

		ArrayList<SchemaElement> rootElements = hg.getRootElements();
		System.out.println("Found "+rootElements.size()+" root elements");
		
		for (SchemaElement re : rootElements) {
			this.queryFragments.add(new QueryFragment(re, hg));
		}
	}

//	public QueryFragment(String schemaName, ArrayList<SchemaElement> se) {
////		if (se == null) throw new IllegalArgumentException("SchemaElements must be specified");
////		this.name = schemaName;
////		this.type = SchemaUtility.SCHEMA;
//		
//		this.queryFragments = new ArrayList<QueryFragment>();
//		for (SchemaElement schemaElement : se) {
//			// find top level entities
//			if (schemaElement instanceof Entity) {
//				Entity ge = (Entity) schemaElement;
//				
//				this.queryFragments.add(new QueryFragment(schemaElement));
//
//				//FIXME: Reimplement these checks				
////				try {
////					if (SchemaUtility.getCLIENT().getGraph(s.getId()).getReferencingElements(ge.getId()).isEmpty()
////							&& ge.getName().equals("")){
//						//if (ge.getParentEnitiesContained().isEmpty() && !ge.getName().equals("")) {
////						this.queryFragments.add(new QueryFragment(schemaElement));
////					}
////				} catch (RemoteException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
//			}
//			if (schemaElement instanceof Containment) {
//				Containment c = (Containment) schemaElement;
//				// if the parent is valid and name is not null
//				if (c.getParentID() > 0 && !c.getName().equals("")) {
//					this.queryFragments.add(new QueryFragment(c));
//				}
//			}
//		}
//	}

	public QueryFragment(SchemaElement schemaElement, HierarchicalGraph hg) {
		this.name = schemaElement.getName();
		this.type = SchemaUtility.getType(schemaElement);
		this.queryFragments = new ArrayList<QueryFragment>();
		
		ArrayList<SchemaElement> childElements = hg.getChildElements(schemaElement.getId());
		System.out.println("\t and "+childElements.size()+" child elements");		
		for (SchemaElement ce : childElements) {			
			if (ce instanceof Entity || ce instanceof Containment || ce instanceof Attribute) {
				this.queryFragments.add(new QueryFragment(ce, hg));				
			} else {
				System.out.println("QueryFragment: ignoring a "+ce);
			}
		}
	}
//		if (schemaElement instanceof Entity) {
//			
//			// get all child entities or attributes
//			
//			/*GraphEntity ge = (GraphEntity) schemaElement;
//			ArrayList<GraphEntity> containedEntities = ge.getChildEnititiesContained();
//			for (GraphEntity containedEntity : containedEntities) {*/
//				
//				// FIXME this is an infinite loop
//				this.queryFragments.add(new QueryFragment(schemaElement));
				
			/*
			ArrayList<GraphAttribute> graphAttributes = ge.getChildAttributes();
			for (GraphAttribute graphAttribute : graphAttributes) {
				this.queryFragments.add(new QueryFragment(graphAttribute));
			}*/
				//FIXME: Make this functional
//		} else if (schemaElement instanceof GraphAttribute) {
//			GraphAttribute ga = (GraphAttribute) schemaElement;
//			this.queryFragments.add(new QueryFragment(ga.getDomainType()));
//		}
//	}


	public ArrayList<QueryFragment> getQueryFragments() {
		return this.queryFragments;
	}
	public void setQueryFragments(ArrayList<QueryFragment> queryFragments) {
		this.queryFragments = queryFragments;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return toString(new StringBuffer(), "\t");
	}
	/*
	 * helper to print a tree of query graph
	 */
	private String toString(StringBuffer sb, String prefix) {
		sb.append(prefix+getType()+":"+getName()+"\n");
		if (this.queryFragments != null && !this.queryFragments.isEmpty()) {
			for (QueryFragment f : this.queryFragments) {
				f.toString(sb, prefix+"\t");
			}
		}
		return sb.toString();
	}

	public static void flatten(QueryFragment root, ArrayList<QueryFragment> result) {
		if (root == null || result == null) {
			throw new IllegalArgumentException("QueryFragmentRoot and result container must not be null");
		}
		result.add(root);
		ArrayList<QueryFragment> qfs = root.getQueryFragments();
		for (QueryFragment queryFragment : qfs) {
			flatten(queryFragment, result);
		}
	}		
}