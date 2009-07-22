package org.mitre.openii.views.manager.mappings.matchmaker;

import java.rmi.RemoteException;

import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.graph.FilteredGraph;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/**
 * Help to manage a series of pairwise matches.
 * 
 * @author HAOLI
 */
public class MatchEnumeration {
	private Permuter permuter;
	
	private Mapping mapping;

	/**
	 * Create a new MatchEnumeration
	 * 
	 * @param permuter
	 *            the permuter object which will feed the enumeration with
	 *            various matches that need to be run.
	 */
	public MatchEnumeration(Permuter permuter, Mapping mapping) {
		this.permuter = permuter;
		this.mapping = mapping; 
	}

	/**
	 * Determine how many total pairwise matches this enumeration will provide.
	 * 
	 * @see GenericPermuter#countTotalPermutations()
	 */
	public int countTotal() {
		return permuter.countTotalPermutations();
	}

	/**
	 * Take a graph and return a filtering down to only domains.
	 * 
	 * @param g
	 *            the input graph
	 * @return a FilteredGraph containing only domains.
	 */
	public static FilteredGraph getDomainsOnly(HierarchicalGraph g) {
		FilteredGraph f = new FilteredGraph(g);
		f.setMaxDepth(1);
		f.setMinDepth(1);

		return f;
	}

	public boolean hasMoreElements() {
		return permuter.hasMoreElements();
	}

	/**
	 * Identical to runNextMatch(), but done to make the Enumeration interface
	 * happy.
	 * 
	 * @see MatchEnumeration#runNextMatch()
	 */
	public APIMatch nextElement() {
		return runNextMatch();
	}
	
	private MappingSchema getMappingSchema( Integer schemaID ) {
		for ( MappingSchema mpSchema : mapping.getSchemas() ) 
			if ( mpSchema.getId().equals(schemaID) ) return mpSchema;
		return null; 
	}

	/**
	 * @return an APIMatch object corresponding to the match of the next
	 *         permutation of schemas in the list.
	 * @throws RemoteException
	 */
	public APIMatch runNextMatch( ) {
		if (!permuter.hasMoreElements()) return null;

		APIMatch matcher = null;
		Pair pair = (Pair) permuter.nextElement();
		Informant.status("Matching " + pair.a + " to " + pair.b);

		try {
			FilteredGraph fGraphA = getGraph((Integer) pair.a);
			FilteredGraph fGraphB = getGraph((Integer) pair.b);

			matcher = new APIMatch(fGraphA, fGraphB);
			// run match
			matcher.runMatch();

			// save match
			MappingSchema[] schemas = new MappingSchema[2]; 
			schemas[0] = getMappingSchema((Integer)pair.a);
			schemas[1] = getMappingSchema((Integer)pair.b); 
			matcher.saveMatch( schemas);

			fGraphA = null;
			fGraphB = null;
			System.gc();
		} catch (RemoteException e1) {
			Informant.status("Unable to access remote database for schemas to match. ", true);
			return null;
		}

		return matcher;
	}// End runNextMatch

	protected FilteredGraph getGraph(Integer schemaID) throws RemoteException {

		HierarchicalGraph graph = new HierarchicalGraph(OpenIIManager.getGraph(schemaID), null);
		FilteredGraph fGraph = new FilteredGraph(graph);
		return fGraph;
	}
} // End MatchEnumeration
