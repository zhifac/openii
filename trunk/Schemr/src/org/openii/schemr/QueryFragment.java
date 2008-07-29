package org.openii.schemr;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.mitre.schemastore.graph.GraphAttribute;
import org.mitre.schemastore.graph.GraphContainment;
import org.mitre.schemastore.graph.GraphEntity;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Query Fragment is a single tree
 */
public class QueryFragment {

	private static final Logger logger = Logger.getLogger(QueryFragment.class.getName());

	private ArrayList<QueryFragment> queryFragments = null;
	private String name = null;
	private String type = null;
		
	public QueryFragment(String name, String type) {
		if (name == null || name.equals("")) throw new IllegalArgumentException("Name must be specified");
		this.queryFragments = new ArrayList<QueryFragment>();
		this.name = name;
		this.type = type;
	}

	public QueryFragment(Schema s, ArrayList<SchemaElement> se) {
		if (s == null || se == null) throw new IllegalArgumentException("Schema and SchemaElements must be specified");
		this.name = s.getName();
		this.type = SchemaUtility.SCHEMA;
		
		this.queryFragments = new ArrayList<QueryFragment>();
		for (SchemaElement schemaElement : se) {
			// find top level entities
			if (schemaElement instanceof Entity) {
				GraphEntity ge = (GraphEntity) schemaElement;
				if (ge.getParentEnitiesContained().isEmpty() && !ge.getName().equals("")) {
					this.queryFragments.add(new QueryFragment(schemaElement));
				}
			}
			if (schemaElement instanceof Containment) {
				Containment c = (Containment) schemaElement;
				if (c.getParentID() < 0 && !c.getName().equals("")) {
					this.queryFragments.add(new QueryFragment(c));
				}
			}
		}
	}

	public QueryFragment(SchemaElement schemaElement) { //, HashSet<SchemaElement> visited) {
		this.name = schemaElement.getName();
		this.type = SchemaUtility.getType(schemaElement);
		
		this.queryFragments = new ArrayList<QueryFragment>();
		if (schemaElement instanceof GraphEntity) {
			GraphEntity ge = (GraphEntity) schemaElement;
			ArrayList<GraphEntity> containedEntities = ge.getChildEnititiesContained();
			for (GraphEntity containedEntity : containedEntities) {
				this.queryFragments.add(new QueryFragment(containedEntity));
			}
			ArrayList<GraphAttribute> graphAttributes = ge.getChildAttributes();
			for (GraphAttribute graphAttribute : graphAttributes) {
				this.queryFragments.add(new QueryFragment(graphAttribute));
			}
//		} else if (schemaElement instanceof GraphAttribute) {
//			GraphAttribute ga = (GraphAttribute) schemaElement;
//			this.queryFragments.add(new QueryFragment(ga.getDomainType()));
		}
	}

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