package org.openii.schemr.viz;

import static org.openii.schemr.viz.Visualizer.ATTRIBUTE;
import static org.openii.schemr.viz.Visualizer.DESC;
import static org.openii.schemr.viz.Visualizer.ENTITY;
import static org.openii.schemr.viz.Visualizer.ID;
import static org.openii.schemr.viz.Visualizer.MATCHED;
import static org.openii.schemr.viz.Visualizer.MATCHED_OBJ;
import static org.openii.schemr.viz.Visualizer.NAME;
import static org.openii.schemr.viz.Visualizer.RELATIONSHIP;
import static org.openii.schemr.viz.Visualizer.SCHEMA;
import static org.openii.schemr.viz.Visualizer.SCORE;
import static org.openii.schemr.viz.Visualizer.TYPE;
import static prefuse.data.Graph.DEFAULT_SOURCE_KEY;
import static prefuse.data.Graph.DEFAULT_TARGET_KEY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.mitre.schemastore.graph.GraphAttribute;
import org.mitre.schemastore.graph.GraphEntity;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.openii.schemr.QueryFragment;
import org.openii.schemr.matcher.SimilarityMatrix.ScoreEvidence;

import prefuse.data.Table;
import prefuse.data.Tree;

public class SchemaGraphReader {
	
	private static final Logger logger = Logger.getLogger(SchemaGraphReader.class.getName());
	
	private Table nodesTable;
	private Table edgesTable;
	
	private int nodeNameColIdx;
	private int nodeDescColIdx;
	private int nodeTypeColIdx;
	private int nodeIdColIdx;
	private int nodeMatchedColIdx;
	private int nodeScoreColIdx;
	private int nodeMatchedObjColIdx;

	private int edgeTypeColIdx;
	private int edgeSrcIdColIdx;
	private int edgeDstIdColIdx;
	
	public SchemaGraphReader() {
		nodesTable = new Table();
		
		nodesTable.addColumn(NAME, String.class);
		nodesTable.addColumn(DESC, String.class);
		nodesTable.addColumn(TYPE, String.class);
		nodesTable.addColumn(ID, int.class);
		nodesTable.addColumn(MATCHED, boolean.class);
		nodesTable.addColumn(SCORE, double.class);
		nodesTable.addColumn(MATCHED_OBJ, String.class);

		nodeNameColIdx = nodesTable.getColumnNumber(NAME);
		nodeDescColIdx = nodesTable.getColumnNumber(DESC);
		nodeTypeColIdx = nodesTable.getColumnNumber(TYPE);
		nodeIdColIdx = nodesTable.getColumnNumber(ID);
		nodeMatchedColIdx = nodesTable.getColumnNumber(MATCHED);
		nodeScoreColIdx = nodesTable.getColumnNumber(SCORE);
		nodeMatchedObjColIdx = nodesTable.getColumnNumber(MATCHED_OBJ);
		
		edgesTable = new Table();
		edgesTable.addColumn(TYPE, String.class);
		edgesTable.addColumn(DEFAULT_SOURCE_KEY, int.class);
		edgesTable.addColumn(DEFAULT_TARGET_KEY, int.class);
		
		edgeTypeColIdx = edgesTable.getColumnNumber(TYPE);
		edgeSrcIdColIdx = edgesTable.getColumnNumber(DEFAULT_SOURCE_KEY);
		edgeDstIdColIdx = edgesTable.getColumnNumber(DEFAULT_TARGET_KEY);
	}
	
//	public Graph constructGraph(Schema schema,
	public Tree constructGraph(Schema schema,
			ArrayList<SchemaElement> schemaElements,
			HashMap<QueryFragment, ScoreEvidence> queryFragmentScoreEvidenceMap) {
		
		HashMap<Integer, ScoreEvidence> idScoreEvidenceMap = new HashMap<Integer, ScoreEvidence>();
		HashMap<Integer, QueryFragment> idQueryFragmentMap = new HashMap<Integer, QueryFragment>();
		
		for (QueryFragment q : queryFragmentScoreEvidenceMap.keySet()) {
			ScoreEvidence se = queryFragmentScoreEvidenceMap.get(q);
			SchemaElement e = (SchemaElement) se.getObj();
			idScoreEvidenceMap.put(e.getId(), se);
			idQueryFragmentMap.put(e.getId(), q);
		}
		
		if (schema == null || schemaElements == null)
			throw new IllegalArgumentException("Schema and elements must not be null");
		
		boolean matched = idScoreEvidenceMap.keySet().contains(schema.getId());
		double score = 1;
		String matchedObj = "";
		if (matched) { 
			score += idScoreEvidenceMap.get(schema.getId()).getScore() * 3;
			matchedObj = idQueryFragmentMap.get(schema.getId()).getName().trim();
		}
		addNode(schema.getName().trim(), schema.getDescription().trim(), SCHEMA, schema.getId(), matched, score, matchedObj);

		// first pass
		for (SchemaElement schemaElement : schemaElements) {
			if (schemaElement instanceof Entity) {
				GraphEntity e = (GraphEntity) schemaElement;
				
				// add entity w/ valid name
				if (!"".equals(e.getName().trim())) {
					matched = idScoreEvidenceMap.keySet().contains(e.getId());
					score = 1;
					matchedObj = "";
					if (matched) { 
						score += idScoreEvidenceMap.get(e.getId()).getScore() * 3 + 1;
						matchedObj = idQueryFragmentMap.get(e.getId()).getName().trim();
					}
					addNode(e.getName().trim(), e.getDescription().trim(), ENTITY, e.getId(), matched, score, matchedObj);

					// TODO: assum all entities connect with schema
					addEdge(RELATIONSHIP, schema.getId(), e.getId());
				}
						
				// TODO: assume no child entities, relational only
//				for (GraphEntity ce : e.getChildEnititiesContained()) {
//					// only add edges, because child entities will be covered
//					addEdge(RELATIONSHIP, e.getId(), ce.getId());
//				}

				for (GraphAttribute ca : e.getChildAttributes()) {
					matched = idScoreEvidenceMap.keySet().contains(ca.getId());
					score = 1;
					matchedObj = "";
					if (matched) { 
						score += idScoreEvidenceMap.get(ca.getId()).getScore() * 3;
						matchedObj = idQueryFragmentMap.get(ca.getId()).getName().trim();
					}
					addNode(ca.getName().trim(), ca.getDescription().trim(), ATTRIBUTE, ca.getId(), matched, score, matchedObj);
					addEdge(RELATIONSHIP, e.getId(), ca.getId());
				}
			}
		}

//		boolean directed = false;
//		Graph g = new Graph(nodesTable, edgesTable, directed, ID, DEFAULT_SOURCE_KEY, DEFAULT_TARGET_KEY);
//		return g;
		return new Tree(nodesTable, edgesTable, ID, DEFAULT_SOURCE_KEY, DEFAULT_TARGET_KEY);
	}
	
	private void addNode(String name, String desc, String type, int id, boolean matched, double score, String matchedObj) {
		int ridx = nodesTable.addRow(); 
		nodesTable.setString(ridx, nodeNameColIdx, name);
		nodesTable.setString(ridx, nodeDescColIdx, desc);
		nodesTable.setString(ridx, nodeTypeColIdx, type);
		nodesTable.setInt(ridx, nodeIdColIdx, id);
		nodesTable.setBoolean(ridx, nodeMatchedColIdx, matched);
		nodesTable.setDouble(ridx, nodeScoreColIdx, score);
		nodesTable.setString(ridx, nodeMatchedObjColIdx, matchedObj);
		
//		System.out.println("Adding node\t"+name+"\t"+type+"\t"+id+"\t"+matched+"\t"+score);
	}
	
	private void addEdge(String type, int sid, int did) {
		int ridx = edgesTable.addRow(); 
		edgesTable.setString(ridx, edgeTypeColIdx, type);
		edgesTable.setInt(ridx, edgeSrcIdColIdx, sid);
		edgesTable.setInt(ridx, edgeDstIdColIdx, did);
//		System.out.println("Adding edge\n\t"+type);
//		System.out.println("\t"+sid);
//		System.out.println("\t"+did);
	}

}
