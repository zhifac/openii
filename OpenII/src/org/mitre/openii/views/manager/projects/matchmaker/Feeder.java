package org.mitre.openii.views.manager.projects.matchmaker;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;

/**
 * FEED ME Seymore! -- Little Shop of Horrors
 * 
 * <p>
 * The feeder takes a directory of schemas as an argument, and runs APIMatch
 * with each pair of schemas as provided by the Permuter.
 * 
 * @author HAOLI
 * 
 */
public class Feeder {
	private Permuter permuter;

	/**
	 * If this is used as a "match policy" only domains will be matched.
	 * 
	 * @see Feeder2#startRepeatedMatches(int)
	 */
	public static final int MATCH_ONLY_DOMAINS = 1;

	/**
	 * If this is used as a match policy, both domains and their values will be
	 * matched
	 * 
	 * @see Feeder2#startRepeatedMatches(int)
	 */
	public static final int MATCH_DOMAINS_AND_VALUES = 2;

	/**
	 * If this is used as a match policy, only domain values (but not their
	 * names) will be matched.
	 * 
	 * @see Feeder2#startRepeatedMatches(int)
	 */
	public static final int MATCH_ONLY_DOMAIN_VALUES = 3;

	private MatchEnumeration matchEnumerator = null;

	private Mapping mapping;

	private ArrayList<Integer> pairWiseMappingIDs;

	/**
	 * This feeder knows which schemas to compare in the repository
	 * 
	 * @throws Exception
	 */

	public Feeder(Mapping mapping) throws Exception {
		pairWiseMappingIDs = new ArrayList<Integer>();
		permuter = new Permuter(new ArrayList<Integer>(Arrays.asList(mapping.getSchemaIDs())));
		this.mapping = mapping;
	}

	/**
	 * Call this to run repeated matches over all permutations of the schemas in
	 * the provided directory. Note: this could take some time to finish
	 * executing (minutes, hours, eons, etc.)
	 * 
	 * @param policy
	 *            one of the different policy types.
	 * @throws Exception
	 *             when something really surprising or bad happens.
	 */
	public MatchEnumeration startRepeatedMatches() {
		permuter.reset();
		Informant.stage("Running Harmony Matches");
		Informant.progress(0);
		matchEnumerator = new MatchEnumeration(permuter, mapping);

		int tot = matchEnumerator.countTotal();
		int x = 0;
		while (matchEnumerator.hasMoreElements()) {
			x++;
			APIMatch apiMatch = matchEnumerator.runNextMatch();
			pairWiseMappingIDs.add(apiMatch.getMappingID());

			// Right after that happens, a whole bunch of stuff goes out of
			// scope.
			System.gc();
			int pct = (int) (((float) x / (float) tot) * (float) 100);
			Informant.progress(pct);
		}

		Informant.progress(100);
		return matchEnumerator;
	}

	/** Private class for merging the mapping cells */
	private ArrayList<MappingCell> mergeMappingCells(ArrayList<MappingCell> mappingCells) {
		// Group together mapping cells in need of merging
		HashMap<String, ArrayList<MappingCell>> mappingCellHash = new HashMap<String, ArrayList<MappingCell>>();
		for (MappingCell mappingCell : mappingCells) {
			// Generate hash key
			Integer element1 = mappingCell.getElement1();
			Integer element2 = mappingCell.getElement2();
			String key = element1 < element2 ? element1 + "-" + element2 : element2 + "-" + element1;

			// Place mapping cell in hash map
			ArrayList<MappingCell> mappingCellList = mappingCellHash.get(key);
			if (mappingCellList == null) mappingCellHash.put(key, mappingCellList = new ArrayList<MappingCell>());
			mappingCellList.add(mappingCell);
		}

		// Merge mapping cells into single list
		ArrayList<MappingCell> mergedMappingCells = new ArrayList<MappingCell>();
		for (ArrayList<MappingCell> mappingCellList : mappingCellHash.values()) {
			Collections.sort(mappingCellList, new MappingCellComparator());
			mergedMappingCells.add(mappingCellList.get(0));
		}
		return mergedMappingCells;
	}

	/** Private class for comparing mapping cells */
	private class MappingCellComparator implements Comparator<MappingCell> {
		public int compare(MappingCell mappingCell1, MappingCell mappingCell2) {
			// First prioritize validated mapping cells over unvalidated mapping
			// cells
			if (mappingCell1.getValidated() != mappingCell2.getValidated()) return mappingCell2.getValidated().compareTo(mappingCell1.getValidated());

			// Next prioritize higher scores over lower scores
			return mappingCell2.getScore().compareTo(mappingCell1.getScore());
		}
	}

	public void mergeMatches() throws RemoteException {
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		
		for (Integer mappingID : pairWiseMappingIDs) {
			Mapping enumeratedMapping = OpenIIManager.getMapping( mappingID );
			ArrayList<MappingCell> cellList = OpenIIManager.getMappingCells(enumeratedMapping.getId()); 
			mappingCells.addAll(cellList);
			
			System.out.println ("merging mapping cell for ... " + mappingID + " size= " + cellList.size() ); 
			
			// delete the enumerated mapping 
			OpenIIManager.deleteMapping(mappingID); 
		}
		// Handles the creation of the mapping
		ArrayList<MappingCell> mergedMappingCells = mergeMappingCells(mappingCells);
		OpenIIManager.saveMapping(mapping, mergedMappingCells);

		Integer totalMappingCells = RepositoryManager.getClient().getMappingCells(mapping.getId()).size();
		System.out.println("Total number of mapping Cells = " + totalMappingCells);
	}

	public MatchEnumeration getMatchEnumeration() {
		return matchEnumerator;
	}


} // End Feeder
