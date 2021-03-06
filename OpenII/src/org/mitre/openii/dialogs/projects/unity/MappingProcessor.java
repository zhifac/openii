package org.mitre.openii.dialogs.projects.unity;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.mitre.harmony.matchers.MatchGenerator;
import org.mitre.harmony.matchers.MatchGenerator.MatchGeneratorListener;
import org.mitre.harmony.matchers.MatchScore;
import org.mitre.harmony.matchers.MatchScores;
import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.matchers.Matcher;
import org.mitre.harmony.matchers.mergers.VoteMerger;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/**
 * Help to process through the mappings associated with a project
 * 
 * @author HAOLI
 */
public class MappingProcessor extends Thread implements MatchGeneratorListener {
	/** Stores the project ID */
	private Integer projectID;

	/** Stores the permutation list of project schemas */
	private Permuter<ProjectSchema> permuter = null;

	/** new mappings **/
	private MappingList newMappings; 

	/** excluded mapping list **/
	private MappingList exclusionMappings; 
	
	/** Matchers **/
	private ArrayList<Matcher> matchers; 

	// Progress bar listener 
	private ProgressListener listener = null;

	private Double progress;

	private Double mappingProgressInterval;
	
	private Double matcherProgressInterval; 

	/** Constructs the match enumeration object */
	public MappingProcessor(Project project, Permuter<ProjectSchema> permuter, ArrayList<Matcher> voters) {
		this.projectID = project.getId();
		this.permuter = permuter;
		this.matchers = (voters == null) ? MatcherManager.getDefaultMatchers() : voters;
		progress = 0.0;
		mappingProgressInterval = 1.0 / newMappings.size() * 100.0;
		matcherProgressInterval = mappingProgressInterval / matchers.size() * 1.0;  

		setDaemon(true);
	}

	public MappingProcessor(Project project, ArrayList<Matcher> voters, MappingList newMappings ) {
		this.projectID = project.getId();
		this.matchers = (voters == null) ? MatcherManager.getDefaultMatchers() : voters;
		this.newMappings =  newMappings; 
		progress = 0.0;
		mappingProgressInterval = 1.0 / newMappings.size() * 100.0;
		matcherProgressInterval = mappingProgressInterval / matchers.size() * 1.0;  
		permuter = null;
		setDaemon(true);
	}

	/** Runs only the provided mappings **/
	public void run() {
		if (permuter != null) {
			try {
				while (hasMoreMappings())
					processNextMapping();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			int mappingIDX = 0;
			while ( newMappings.hasMore() ) {
				Pair<ProjectSchema> pair = newMappings.next(); 
				try {
					notify("Processing mapping " + (mappingIDX + 1) + " of " + newMappings.size() +": " + pair.getItem1().getName() + " to " + pair.getItem2().getName());
					processMapping(pair);
					mappingIDX++;
					progress = mappingIDX * mappingProgressInterval * 1.0;
					notify(progress.intValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void notify(Integer pct) {
		if (listener != null ) 
			listener.updateProgress(pct); 
	}

	private void notify(String msg) {
		if ( listener != null ) 
			listener.updateProgressMessage(msg); 
	}

	/** Returns the number of mappings in need of doing */
	public int size() {
		return this.permuter.size();
	}

	public void addExclusionMappingPair(Pair<ProjectSchema> m) {
		exclusionMappings.add(m);
	}

	public boolean isExcludedMappingPair(Pair<ProjectSchema> m) {
		return exclusionMappings.contains(m);
	}

	/** Indicates that there are more mappings to perform */
	public boolean hasMoreMappings() {
		return this.permuter.hasMoreElements();
	}

	/** Generate the specified filtered schema object */
	private FilteredSchemaInfo getSchemaInfo(ProjectSchema schema) throws RemoteException {
		HierarchicalSchemaInfo schemaInfo = new HierarchicalSchemaInfo(OpenIIManager.getSchemaInfo(schema.getId()), schema.geetSchemaModel());
		FilteredSchemaInfo fSchemaInfo = new FilteredSchemaInfo(schemaInfo);
		return fSchemaInfo;
	}

	/**
	 * Processes the next mapping
	 * 
	 * @throws Exception
	 */
	public void processNextMapping() throws Exception {
		// Don't proceed if no more mappings in need of creation
		if (permuter == null)
			return;

		// Gather up the schemas in need of matching
		Pair<ProjectSchema> pair = (Pair<ProjectSchema>) permuter.nextElement();
		if (pair != null && !isExcludedMappingPair(pair))
			processMapping(pair);
	}

	private void processMapping(Pair<ProjectSchema> pair) throws Exception {
		FilteredSchemaInfo schemaInfo1 = getSchemaInfo(pair.getItem1());
		FilteredSchemaInfo schemaInfo2 = getSchemaInfo(pair.getItem2());

		// Generate the mapping
		Mapping mapping = new Mapping(null, projectID, schemaInfo1.getSchema().getId(), schemaInfo2.getSchema().getId());
		Integer mappingID = OpenIIManager.addMapping(mapping);
		if (mappingID == null)
			throw new Exception("Failed to create mapping between " + pair);

		// Generate the mapping cells
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		MatchGenerator matchGenerator = new MatchGenerator(matchers, new VoteMerger()); 
		matchGenerator.addListener(this); 
		MatchScores matchScores = matchGenerator.getScores(schemaInfo1, schemaInfo2);
		
		
		String author = matchersToString(matchers);
		ArrayList<MatchScore> scores = matchScores.getScores();
		for (MatchScore score : scores) {
			mappingCells.add(MappingCell.createProposedMappingCell(null, mappingID, score.getSourceID(), score.getTargetID(), score.getScore(), author, new Date(System.currentTimeMillis()), ""));
		}

		// Save the mapping. Delete if saving fails
		if (!OpenIIManager.saveMappingCells(mappingID, mappingCells)) {
			try {
				OpenIIManager.deleteMapping(mappingID);
			} catch (Exception e2) {
				throw new Exception("Failed to delete mapping cells between " + pair);
			}
		}

		// Perform garbage collection
		mapping = null;
		mappingCells.clear();
		mappingCells = null;
		System.gc();
	}

	public static String matchersToString(ArrayList<Matcher> matcherList) {
		String author = new String("Vote Merger(");
		for (Matcher matcher : matcherList)
			author += matcher.getName() + ";";
		author += ")";
		return author;
	}

	/**
	 * Runs the automatic mapping process . voters can be null and default set
	 * will be chosen
	 * 
	 * @return number of pairwise matches
	 */
	static public int run(Project project, ArrayList<Matcher> matchers) throws Exception {
		int numMatches = 0;
		// Generate a hash of project schemas
		HashMap<Integer, ProjectSchema> schemas = new HashMap<Integer, ProjectSchema>();
		for (ProjectSchema schema : project.getSchemas())
			schemas.put(schema.getId(), schema);

		Permuter<ProjectSchema> permuter = new Permuter<ProjectSchema>(new ArrayList<ProjectSchema>(schemas.values()));
		MappingProcessor mappingProcessor = new MappingProcessor(project, permuter, matchers);

		// Generate the mapping permuter and exclude existing mappings
		for (Mapping mapping : OpenIIManager.getMappings(project.getId())) {
			ProjectSchema schema1 = schemas.get(mapping.getSourceId());
			ProjectSchema schema2 = schemas.get(mapping.getTargetId());

			// empty mapping object with mapping cell
			if (OpenIIManager.getMappingCells(mapping.getId()).size() <= 0) {
				OpenIIManager.deleteMapping(mapping.getId());
				continue;
			}

			mappingProcessor.addExclusionMappingPair(new Pair<ProjectSchema>(schema1, schema2));
			System.out.println("Generating  Matching Pair: " + schema1.getId() + " - " + schema2.getId());
		}

		// Run the mapping processor on un-run mappings
		Informant.stage("Running Harmony Matches");
		Informant.progress(0);

		int current = 0, total = mappingProcessor.size();

		while (mappingProcessor.hasMoreMappings()) {
			mappingProcessor.processNextMapping();
			Informant.progress((int) (((float) ++current / (float) total) * (float) 100));
			numMatches++;
		}
		Informant.progress(100);
		return numMatches;
	}

	public void addProgressListener(ProgressListener l) {
		this.listener = l;
	}

	public int getNumMappings() {
		return newMappings.size();
	}

	/** Updates the progress listener with matcher progress **/
	public void matcherRun(Matcher matcher) {
		progress = progress + matcher.getPercentComplete() * matcherProgressInterval.doubleValue() ;
		notify(progress.intValue()); 
	}

}
