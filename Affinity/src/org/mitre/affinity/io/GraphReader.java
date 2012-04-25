package org.mitre.affinity.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;

import org.gephi.graph.api.GraphController;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.mitre.affinity.algorithms.IProgressMonitor;
import org.openide.util.Lookup;

import communityFinder.Edge;
import communityFinder.Node;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * Uses Gephi importers to read a graph from a file.
 * 
 * @author CBONACETO
 *
 */
public class GraphReader {
	/** The project controller */
	protected static ProjectController pc;
	
	/** The graph controller */
	protected static GraphController gc;
	
	/**
	 * Open a graph from the file system. A temporary workspace is created and discarded.
	 * 
	 * @param file
	 * @param progressMonitor
	 * @return
	 * @throws FileNotFoundException
	 */
	public static org.gephi.graph.api.Graph openGraphFile(File file, IProgressMonitor progressMonitor) throws FileNotFoundException {	
		//Create a new temporary workspace where the graph data will be imported to
		if(pc == null) {
			//Initialize project controller
			pc = Lookup.getDefault().lookup(ProjectController.class);
			pc.newProject();
			gc = Lookup.getDefault().lookup(GraphController.class);
		}
		
		//Create the temporary workspace
		Workspace workspace = pc.newWorkspace(pc.getCurrentProject());
		pc.openWorkspace(workspace);
		
		//Open the graph
		org.gephi.graph.api.Graph graph = openGraphFile(file, workspace, progressMonitor);		
		
		//Dispose of the temporary workspace and return the graph
		/*GraphModel clonedModel = null;
		if(graph != null) {
			clonedModel = gc.getModel(workspace).copy();
		}*/
		pc.deleteWorkspace(workspace);
		
		
		return graph;
	}
	
	/**
	 * Open a graph from the file system and import it into the given workspace.
	 * 
	 * @param file
	 * @param workspace the workspace to import the graph into. Any previous graph data will be cleared.
	 * @param progressMonitor a progress monitor to track the load progress
	 * @return
	 */
	public static org.gephi.graph.api.Graph openGraphFile(File file, Workspace workspace, IProgressMonitor progressMonitor) throws FileNotFoundException {
		//Clear the workspace
		GraphController gc = Lookup.getDefault().lookup(GraphController.class);
		gc.getModel(workspace).clear();

		//Open graph file
		ImportController importController = Lookup.getDefault().lookup(ImportController.class);		
		Container container= importController.importFile(file);
		container.getLoader().setEdgeDefault(EdgeDefault.UNDIRECTED); //Make graph undirected
		
		//Append imported data to GraphAPI
		importController.process(container, new DefaultProcessor(), workspace);	

		return gc.getModel(workspace).getGraph();
	}

	/**
	 * Converts a Gephi graph to the Jung graph format.
	 * 
	 * @param graph the Gephi graph to convert
	 * @return the Jung graph format
	 */
	public static UndirectedSparseGraph<Node, Edge> convertGephiGraph(org.gephi.graph.api.Graph graph) {
		UndirectedSparseGraph<Node, Edge> g = new UndirectedSparseGraph<Node, Edge>();
		HashMap<String, Node> nodes = new HashMap<String, Node>();
		HashSet<Edge> edges = new HashSet<Edge>();		
		
		if(graph != null) {
			if(graph.getNodeCount() > 0) {
				int nodeIndex = 0;
				for(org.gephi.graph.api.Node node : graph.getNodes().toArray()) {
					Node n = new Node("n", node.getNodeData().getLabel(), nodeIndex);
					//System.out.println("gephi node: " + node.getNodeData().getId() + ", " + node.getNodeData().getLabel());
					n.setId(node.getNodeData().getId());
					nodes.put(n.getId(), n);
					nodeIndex++;
				}
			}
			if(graph.getEdgeCount() > 0) {
				for(org.gephi.graph.api.Edge edge : graph.getEdges().toArray()) {
					Node source = nodes.get(edge.getSource().getNodeData().getId());
					Node dest = nodes.get(edge.getTarget().getNodeData().getId());
					if(source != null && dest != null) {
						Edge e = new Edge(source, dest);
						e.setId(edge.getEdgeData().getId());
						edges.add(e);
					}
				}
			}
		}
		
		for (Node n: nodes.values()){
			g.addVertex(n);
		}
		for (Edge e:edges) {
			g.addEdge(e, e.getSourceNode(), e.getDestNode());
		}
		
		return g;
	}
	
	/** Test main */
	public static void main(String[] args) {
		try {
			//org.gephi.graph.api.Graph graph = openGraphFile(new File("data/graphs/voting.gexf"), null);
			org.gephi.graph.api.Graph graph = openGraphFile(new File("data/graphs/voting.graphml"), null);
			UndirectedSparseGraph<Node, Edge> convertedGraph =  convertGephiGraph(graph);
			System.out.println(convertedGraph);
			//System.out.println(graph.getNode(convertedGraph.getVertices().iterator().next().getValue()));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}