package org.mitre.openii.views.manager.mappings.matchmaker;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import org.mitre.harmony.matchers.MatchScore;
import org.mitre.harmony.matchers.MatchScores;
import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.mergers.VoteMerger;
import org.mitre.harmony.matchers.voters.BagMatcher;
import org.mitre.harmony.matchers.voters.EditDistanceMatcher;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.harmony.matchers.voters.ThesaurusMatcher;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.graph.FilteredGraph;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;

/**
 * Match a pair of filteredGraphs
 * 
 * @author HAOLI
 * 
 */
public class APIMatch {
	/** Match scores lower than this don't get returned. */
	public static double LOWER_THRESHOLD = 0.25;

	/** YggAPI used to import stuff */
	protected ArrayList<SchemaImporter> importers;

	/** Contains the result product after matching is finished. */
	private MatchScores matchScores;

	private FilteredGraph graphA;
	private FilteredGraph graphB;

	/** mapping ID for this APIMatch, not null after saving to repository **/
	private Integer APIMappingID = null;

	public APIMatch(FilteredGraph graphLeft, FilteredGraph graphRight) {
		graphA = graphLeft;
		graphB = graphRight;
	}

	/**
	 * Map two schemas and save the mappings using OpenIIManager
	 * 
	 * @param mapping
	 * @throws RemoteException
	 */
	public void runMatch() throws RemoteException {
		Informant.status("Beginning to match two schemas.");
		Informant.status("Schema A has " + graphA.size() + " elements and schema B has " + graphB.size());

		ArrayList<MatchVoter> voters = new ArrayList<MatchVoter>();

		voters.add(new BagMatcher());
		voters.add(new ThesaurusMatcher());
		voters.add(new EditDistanceMatcher());
		VoteMerger merger = new VoteMerger();

		Informant.status("Calling Harmony matching algorithms...");
		matchScores = MatcherManager.getScores(graphA, graphB, voters, null, merger);
	}

	/**
	 * Save mapping cells to a mapping in the repository
	 * 
	 * @return mapping ID
	 * @throws RemoteException
	 * @throws RemoteException
	 */
	public Integer saveMatch(MappingSchema[] schemas) throws RemoteException {
		System.out.println(" Saving APIMatch...");
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		String mappingName = "APIMatch:" + schemas[0].getId() + "-" + schemas[1].getId();
		Mapping apiMapping = new Mapping(null, mappingName, "Automated pairwise match", "Match Maker APIMatch", schemas);

		// create mappingCells from mappingScores
		for (MatchScore score : matchScores.getScores()) {
			mappingCells.add(new MappingCell(null, apiMapping.getId(), score.getElement1(), score.getElement2(), score.getScore(), "MatchMaker", new Date(System.currentTimeMillis()), null, null, false));
		}

		// save the mapping and generate an ID
		APIMappingID = OpenIIManager.saveMapping(apiMapping, mappingCells);

		System.out.println("          Saved mapping (ID) ... " + APIMappingID + "; num mappingCell= " + mappingCells.size());
		System.out.println("          Total number of mapping cell so far = " + RepositoryManager.getClient().getMappingCells(APIMappingID).size());

		System.out.println(" .... completed " + APIMappingID);
		return APIMappingID;
	}

	public Integer getMappingID() {
		return APIMappingID;
	}

} // End APIMatch
