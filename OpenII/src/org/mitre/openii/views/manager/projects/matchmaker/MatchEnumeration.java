package org.mitre.openii.views.manager.projects.matchmaker;

import java.rmi.RemoteException;

import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

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
	 * Take a schema and return a filtering down to only domains.
	 * 
	 * @param g
	 *            the input schema info
	 * @return a FilteredSchemaInfo containing only domains.
	 */
	public static FilteredSchemaInfo getDomainsOnly(HierarchicalSchemaInfo g) {
		FilteredSchemaInfo f = new FilteredSchemaInfo(g);
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
			FilteredSchemaInfo fSchemaInfoA = getSchemaInfo((Integer) pair.a);
			FilteredSchemaInfo fSchemaInfoB = getSchemaInfo((Integer) pair.b);

			matcher = new APIMatch(fSchemaInfoA, fSchemaInfoB);
			// run match
			matcher.runMatch();

			// save match
			MappingSchema[] schemas = new MappingSchema[2]; 
			schemas[0] = getMappingSchema((Integer)pair.a);
			schemas[1] = getMappingSchema((Integer)pair.b); 
			matcher.saveMatch( schemas);

			fSchemaInfoA = null;
			fSchemaInfoB = null;
			System.gc();
		} catch (RemoteException e1) {
			Informant.status("Unable to access remote database for schemas to match. ", true);
			return null;
		}

		return matcher;
	}// End runNextMatch

	protected FilteredSchemaInfo getSchemaInfo(Integer schemaID) throws RemoteException {

		HierarchicalSchemaInfo schemaInfo = new HierarchicalSchemaInfo(OpenIIManager.getSchemaInfo(schemaID), null);
		FilteredSchemaInfo fSchemaInfo = new FilteredSchemaInfo(schemaInfo);
		return fSchemaInfo;
	}
} // End MatchEnumeration
