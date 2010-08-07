package org.mitre.openii.dialogs.projects.unity;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.mitre.harmony.matchers.MatchGenerator;
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
public class MappingProcessor {
	/** Stores the project ID */
	private Integer projectID;

	/** Stores the permutation list of project schemas */
	private Permuter<ProjectSchema> permuter;

	private ArrayList<Pair<ProjectSchema>> exclusionMappings = new ArrayList<Pair<ProjectSchema>>();
	/** Matchers **/
	private ArrayList<Matcher> matchers = null;

	/** Constructs the match enumeration object */
	public MappingProcessor(Project project, Permuter<ProjectSchema> permuter,
			ArrayList<Matcher> voters) {
		this.projectID = project.getId();
		this.permuter = permuter;
		this.matchers = (voters == null) ? MatcherManager.getDefaultMatchers()
				: voters;
	}

	public MappingProcessor(Project project,
			ArrayList<Pair<ProjectSchema>> newMappings,
			ArrayList<Matcher> voters) {
		this.projectID = project.getId();
		this.matchers = (voters == null) ? MatcherManager.getDefaultMatchers()
				: voters;

		for (Pair<ProjectSchema> pair : newMappings)
			try {
				processMapping(pair);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	private FilteredSchemaInfo getSchemaInfo(ProjectSchema schema)
			throws RemoteException {
		HierarchicalSchemaInfo schemaInfo = new HierarchicalSchemaInfo(
				OpenIIManager.getSchemaInfo(schema.getId()), schema
						.geetSchemaModel());
		FilteredSchemaInfo fSchemaInfo = new FilteredSchemaInfo(schemaInfo);
		return fSchemaInfo;
	}

	/** Processes the next mapping */
	public void processNextMapping() throws Exception {
		// Don't proceed if no more mappings in need of creation
		if (!permuter.hasMoreElements())
			return;

		// Gather up the schemas in need of matching
		Pair<ProjectSchema> pair = (Pair<ProjectSchema>) permuter.nextElement();
		if (!isExcludedMappingPair(pair)) processMapping(pair);
	}

	private void processMapping(Pair<ProjectSchema> pair) throws Exception {
		Informant.status("Matching " + pair.getItem1().getName() + " to "
				+ pair.getItem2().getName());
		FilteredSchemaInfo schemaInfo1 = getSchemaInfo(pair.getItem1());
		FilteredSchemaInfo schemaInfo2 = getSchemaInfo(pair.getItem2());

		// Generate the mapping
		Mapping mapping = new Mapping(null, projectID, schemaInfo1.getSchema()
				.getId(), schemaInfo2.getSchema().getId());
		Integer mappingID = OpenIIManager.addMapping(mapping);
		if (mappingID == null)
			throw new Exception("Failed to create mapping between " + pair);

		// Prepare to generate the mapping cells
		Informant.status("Preparing to match " + pair);
		Informant
				.status("Schema A has " + schemaInfo1.getElementCount()
						+ " elements and schema B has "
						+ schemaInfo2.getElementCount());

		// Generate the mapping cells
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		Informant.status("Calling Harmony matching algorithms...");
		MatchScores matchScores = new MatchGenerator(matchers, new VoteMerger())
				.getScores(schemaInfo1, schemaInfo2);
		for (MatchScore score : matchScores.getScores())
			mappingCells.add(MappingCell.createProposedMappingCell(null,
					mappingID, score.getSourceID(), score.getTargetID(), score
							.getScore(), "MatchMaker Auto Gen", new Date(System
							.currentTimeMillis()), ""));

		// Save the mapping. Delete if saving fails
		Informant.status("Saving APIMatch...");
		if (!OpenIIManager.saveMappingCells(mappingID, mappingCells)) {
			try {
				OpenIIManager.deleteMapping(mappingID);
			} catch (Exception e2) {
			}
			throw new Exception("Failed to create mapping cells between "
					+ pair);
		}

		// Perform garbage collection
		System.gc();
	}

	/**
	 * Runs the automatic mapping process . voters can be null and default set
	 * will be chosen
	 * 
	 * @return number of pairwise matches
	 */
	static public int run(Project project, ArrayList<Matcher> matchers)
			throws Exception {
		int numMatches = 0;
		// Generate a hash of project schemas
		HashMap<Integer, ProjectSchema> schemas = new HashMap<Integer, ProjectSchema>();
		for (ProjectSchema schema : project.getSchemas())
			schemas.put(schema.getId(), schema);

		Permuter<ProjectSchema> permuter = new Permuter<ProjectSchema>(
				new ArrayList<ProjectSchema>(schemas.values()));
		MappingProcessor mappingProcessor = new MappingProcessor(project,
				permuter, matchers);

		// Generate the mapping permuter and exclude existing mappings
		for (Mapping mapping : OpenIIManager.getMappings(project.getId())) {
			ProjectSchema schema1 = schemas.get(mapping.getSourceId());
			ProjectSchema schema2 = schemas.get(mapping.getTargetId());

			// empty mapping object with mapping cell
			if (OpenIIManager.getMappingCells(mapping.getId()).size() <= 0) {
				OpenIIManager.deleteMapping(mapping.getId());
				continue;
			}

			// permuter.addExcludedPair(new Pair<ProjectSchema>(schema1,
			// schema2));
			mappingProcessor.addExclusionMappingPair(new Pair<ProjectSchema>(
					schema1, schema2));
			System.out.println("Generating  Matching Pair: " + schema1.getId()
					+ " - " + schema2.getId());
		}

		// Run the mapping processor on un-run mappings
		Informant.stage("Running Harmony Matches");
		Informant.progress(0);

		int current = 0, total = mappingProcessor.size();

		while (mappingProcessor.hasMoreMappings()) {
			mappingProcessor.processNextMapping();
			Informant
					.progress((int) (((float) ++current / (float) total) * (float) 100));
			numMatches++;
		}
		Informant.progress(100);
		return numMatches;
	}
}
