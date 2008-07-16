package org.openii.schemr;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.mitre.schemastore.graph.GraphAttribute;
import org.mitre.schemastore.graph.GraphEntity;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Query Fragment is a single tree
 */
public class QueryFragment {

	private static final Logger logger = Logger.getLogger("org.openii.schemr.QueryFragment");

	private ArrayList<QueryFragment> m_queryFragments = null;
	private String m_name = null;
	private String m_type = null;
		
	public QueryFragment(String name, String type) {
		if (name == null || name.equals("")) throw new IllegalArgumentException("Name must be specified");
		this.m_queryFragments = null;
		this.m_name = name;
		this.m_type = type;
	}

	public QueryFragment(Schema s, ArrayList<SchemaElement> se) {
		if (s == null || se == null) throw new IllegalArgumentException("Schema and SchemaElements must be specified");
		this.m_name = SchemaUtility.SCHEMA;
		this.m_type = s.getName();
		
		this.m_queryFragments = new ArrayList<QueryFragment>();
		for (SchemaElement schemaElement : se) {
			// find top level entities
			if (schemaElement instanceof Entity) {
				GraphEntity ge = (GraphEntity) schemaElement;
				if (ge.getParentEnitiesContained().isEmpty() && !ge.getName().equals("")) {
					m_queryFragments.add(new QueryFragment(schemaElement));
				}
			}
		}
	}

	public QueryFragment(SchemaElement schemaElement) { //, HashSet<SchemaElement> visited) {
		this.m_name = SchemaUtility.getType(schemaElement);
		this.m_type = schemaElement.getName();
		
		this.m_queryFragments = new ArrayList<QueryFragment>();
		if (schemaElement instanceof GraphEntity) {
			GraphEntity ge = (GraphEntity) schemaElement;
			ArrayList<GraphEntity> containedEntities = ge.getChildEnititiesContained();
			for (GraphEntity containedEntity : containedEntities) {
				m_queryFragments.add(new QueryFragment(containedEntity));
			}
			ArrayList<GraphAttribute> graphAttributes = ge.getChildAttributes();
			for (GraphAttribute graphAttribute : graphAttributes) {
				m_queryFragments.add(new QueryFragment(graphAttribute));
			}
		} else if (schemaElement instanceof GraphAttribute) {
			GraphAttribute ga = (GraphAttribute) schemaElement;
			m_queryFragments.add(new QueryFragment(ga.getDomainType()));
		}
	}

	public ArrayList<QueryFragment> getQueryFragments() {
		return m_queryFragments;
	}
	public void setQueryFragments(ArrayList<QueryFragment> queryFragments) {
		this.m_queryFragments = queryFragments;
	}
	public String getName() {
		return m_name;
	}
	public void setName(String name) {
		this.m_name = name;
	}
	public String getType() {
		return m_type;
	}
	public void setType(String type) {
		this.m_type = type;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("QueryFragment:\n");
		return toString(sb, "\t");
	}
	/*
	 * helper to print a tree of query graph
	 */
	private String toString(StringBuffer sb, String prefix) {
		sb.append(prefix+getType()+":"+getName()+"\n");
		if (m_queryFragments != null && !m_queryFragments.isEmpty()) {
			for (QueryFragment f : m_queryFragments) {
				f.toString(sb, prefix+"\t");
			}
		}
		return sb.toString();
	}		
}