package org.openii.schemr.viz;

import static org.openii.schemr.viz.Visualizer.ATTRIBUTE;
import static org.openii.schemr.viz.Visualizer.DESC;
import static org.openii.schemr.viz.Visualizer.ENTITY;
import static org.openii.schemr.viz.Visualizer.ID;
import static org.openii.schemr.viz.Visualizer.NAME;
import static org.openii.schemr.viz.Visualizer.RELATIONSHIP;
import static org.openii.schemr.viz.Visualizer.SCHEMA;
import static org.openii.schemr.viz.Visualizer.TYPE;
import static prefuse.data.Graph.DEFAULT_SOURCE_KEY;
import static prefuse.data.Graph.DEFAULT_TARGET_KEY;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.mitre.schemastore.graph.GraphAttribute;
import org.mitre.schemastore.graph.GraphEntity;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

import prefuse.data.Graph;
import prefuse.data.Table;

public class SchemaGraphReader {
	
	private static final Logger logger = Logger.getLogger(SchemaGraphReader.class.getName());
	
	private Table nodesTable;
	private Table edgesTable;
	
	private int nodeNameColIdx;
	private int nodeDescColIdx;
	private int nodeTypeColIdx;
	private int nodeIdColIdx;

	private int edgeTypeColIdx;
	private int edgeSrcIdColIdx;
	private int edgeDstIdColIdx;
	
	public SchemaGraphReader() {
		nodesTable = new Table();
		
		nodesTable.addColumn(NAME, String.class);
		nodesTable.addColumn(DESC, String.class);
		nodesTable.addColumn(TYPE, String.class);
		nodesTable.addColumn(ID, int.class);

		nodeNameColIdx = nodesTable.getColumnNumber(NAME);
		nodeDescColIdx = nodesTable.getColumnNumber(DESC);
		nodeTypeColIdx = nodesTable.getColumnNumber(TYPE);
		nodeIdColIdx = nodesTable.getColumnNumber(ID);
		
		edgesTable = new Table();
		edgesTable.addColumn(TYPE, String.class);
		edgesTable.addColumn(DEFAULT_SOURCE_KEY, int.class);
		edgesTable.addColumn(DEFAULT_TARGET_KEY, int.class);
		
		edgeTypeColIdx = edgesTable.getColumnNumber(TYPE);
		edgeSrcIdColIdx = edgesTable.getColumnNumber(DEFAULT_SOURCE_KEY);
		edgeDstIdColIdx = edgesTable.getColumnNumber(DEFAULT_TARGET_KEY);
	}
	
	public Graph readGraph(Schema schema, ArrayList<SchemaElement> schemaElements) {
		if (schema == null || schemaElements == null) throw new IllegalArgumentException("Schema and elements must not be null");

		addNode(schema.getName().trim(), schema.getDescription().trim(), SCHEMA, schema.getId());
	
		// first pass
		for (SchemaElement schemaElement : schemaElements) {
			if (schemaElement instanceof Entity) {
				GraphEntity e = (GraphEntity) schemaElement;
				if (!"".equals(e.getName().trim())) {
					addNode(e.getName().trim(), e.getDescription().trim(), ENTITY, e.getId());
				}

				for (GraphEntity ce : e.getChildEnititiesContained()) {
					// only add edges, because child entities will be covered
					addEdge(RELATIONSHIP, e.getId(), ce.getId());					
				}
				
				for (GraphAttribute ca : e.getChildAttributes()) {
					addNode(ca.getName().trim(), ca.getDescription().trim(), ATTRIBUTE, ca.getId());
					addEdge(RELATIONSHIP, e.getId(), ca.getId());		
				}
			}
		}

		boolean directed = false;
		Graph g = new Graph(nodesTable, edgesTable, directed, ID, DEFAULT_SOURCE_KEY, DEFAULT_TARGET_KEY);
		return g;
	}
	
	private void addNode(String name, String desc, String type, int id) {
		int ridx = nodesTable.addRow(); 
		nodesTable.setString(ridx, nodeNameColIdx, name);
		nodesTable.setString(ridx, nodeDescColIdx, desc);
		nodesTable.setString(ridx, nodeTypeColIdx, type);
		nodesTable.setInt(ridx, nodeIdColIdx, id);
		
		System.out.println("Adding node\n\t"+name);
		System.out.println("\t"+desc);
		System.out.println("\t"+type);
		System.out.println("\t"+id);
	}
	
	private void addEdge(String type, int sid, int did) {
		int ridx = edgesTable.addRow(); 
		edgesTable.setString(ridx, edgeTypeColIdx, type);
		edgesTable.setInt(ridx, edgeSrcIdColIdx, sid);
		edgesTable.setInt(ridx, edgeDstIdColIdx, did);
		
		System.out.println("Adding edge\n\t"+type);
		System.out.println("\t"+sid);
		System.out.println("\t"+did);
	}

}
