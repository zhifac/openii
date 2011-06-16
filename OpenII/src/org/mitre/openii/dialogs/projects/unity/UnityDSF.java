package org.mitre.openii.dialogs.projects.unity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import org.mitre.openii.dialogs.projects.unity.DisjointSetForest.ContainerMethod;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingCellInput;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;
import org.mitre.schemastore.model.terms.AssociatedElement;
import org.mitre.schemastore.model.terms.Term;
import org.mitre.schemastore.model.terms.VocabularyTerms;
import org.mitre.schemastore.porters.vocabularyExporters.CompleteVocabExporter;

public class UnityDSF extends Thread
{
	private Project project;
	private VocabularyTerms terms;
	private ArrayList<Integer> rankedSchemas;

	private ArrayList<ProgressListener> listeners = new ArrayList<ProgressListener>();
	private ArrayList<Mapping> mappings = new ArrayList<Mapping>();
	private int progressCount = 0;
	private int totalProgress = 1;
	private boolean isStopped = false;

	public UnityDSF(Project project) {
		this.project = project;
		this.terms = OpenIIManager.getVocabularyTerms(project.getId());
		rankedSchemas = new ArrayList<Integer>();
		for (Integer i : project.getSchemaIDs())
			rankedSchemas.add(i);
		setDaemon(true); 
	}

	public UnityDSF(Project project, ArrayList<Mapping> mappings, ArrayList<Integer> rankedSchemas) {
		this.project = project;
		this.rankedSchemas = rankedSchemas;
		this.mappings = mappings;
		setDaemon(true); 
	}

	public VocabularyTerms getVocabularyTerms() {
		return terms;
	}

	public double getProgress() {
		return (double) progressCount / (double) totalProgress;
	}

	public void run() {
		System.out.println("Start running unity thread...");
		compileVocabulary();
	}

	public void stopUnity() {
		isStopped = true;
	}

	public boolean isStopped() {
		return isStopped;
	}

	/**
	 * Use DisjointSetForest to generate synsets from pair wise matches
	 * 
	 * @param inputMappings
	 * @return
	 */
	private void compileVocabulary() {
		long start = System.currentTimeMillis();
		notify("Preparing to generate vocabulary... ");

		// Prepare mappingCellHash
		final HashMap<Mapping, ArrayList<MappingCell>> mappingCellHash = new HashMap<Mapping, ArrayList<MappingCell>>();
		ArrayList<MappingCell> tmpList;
		totalProgress = 0;
		for (Mapping m : this.mappings) {
			tmpList = OpenIIManager.getMappingCells(m.getId());
			mappingCellHash.put(m, tmpList);
			totalProgress += tmpList.size();
		}
		if (totalProgress == 0)
			totalProgress = 1;

		// Create synsetTerm for each schema element
		final HashMap<String, SynsetElement> synsetElements = new HashMap<String, SynsetElement>();
		for (Integer sID : rankedSchemas) {
			SchemaModel schemaModel = project.getSchemaModel(sID);
			HierarchicalSchemaInfo schemaInfo = new HierarchicalSchemaInfo(OpenIIManager.getSchemaInfo(sID), schemaModel);
			for (SchemaElement ele : schemaInfo.getHierarchicalElements()) {
				SynsetElement newSynsetTerm = new SynsetElement(sID, ele.getId(), ele.getName(), ele.getDescription());
				synsetElements.put(new String(sID + "-" + ele.getId()), newSynsetTerm);
			}
		}

		// The containers are the schemas
		ContainerMethod<SynsetElement> method = new ContainerMethod<SynsetElement>() {
			public int getContainerFor(SynsetElement element) {
				return element.getSchemaID();
			}
		};

		notify("Generating vocabulary... ");
		// Disjoint Set Forest
		DisjointSetForest<SynsetElement> dsf = new DisjointSetForest<SynsetElement>(synsetElements.values().toArray(new SynsetElement[0]), method, rankedSchemas.size());

		for (Mapping mapping : this.mappings)
		{
			Integer inputSchema = mapping.getSourceId();
			Integer outputSchema = mapping.getTargetId();

			// Get the list of all matches above the threshold of 0.1
			ArrayList<MappingCell> mcList = mappingCellHash.get(mapping);
			for(int i=0; i<mcList.size(); i++)
				if(mcList.get(i).getScore()<0.1) mcList.remove(i--);
			
			// First sort the mapping cells from high score to low
			MappingCell[] mcArray = mcList.toArray(new MappingCell[0]);
			Arrays.sort(mcArray, new Comparator<MappingCell>() {
				public int compare(MappingCell mc1, MappingCell mc2) {
					if (mc1.isValidated() && mc2.isValidated())
						return -(mc1.getScore().compareTo(mc2.getScore()));
					else if (mc1.isValidated())
						return 1;
					else if (mc2.isValidated())
						return -1;
					else
						return -(mc1.getScore().compareTo(mc2.getScore()));
				}
			});

			// Then call DisjointSetForest merge on the mapping cells.
			for (MappingCell mc : mcArray) {
				for (MappingCellInput input : mc.getInputs())
					dsf.merge(synsetElements.get(new String(inputSchema + "-" + input.getElementID())), synsetElements.get(new String(outputSchema + "-" + mc.getOutput())), mc.isValidated());

				notify(++progressCount);
			}
		}

		// Now that DSF is done, reorganize the data into synsets
		HashMap<Integer, Synset> synsets = new HashMap<Integer, Synset>();
		for (SynsetElement term : synsetElements.values()) {
			int root = dsf.find(term);
			Synset termSet = synsets.get(new Integer(root));
			if (termSet == null) {
				termSet = new Synset(term);
				synsets.put(new Integer(root), termSet);
			} else
				termSet.add(term);
		}

		long endDSF = System.currentTimeMillis();
		Double dsfTime = (endDSF - start) / new Double(1000) / new Double(60);
		System.out.println("DSF time " + dsfTime.toString() + " minutes");

		// Generate connonical terms for each synset
		terms = new VocabularyTerms(project.getId(), generateVocabTerms(new ArrayList<Synset>(synsets.values())));
		notify("Vocabulary is completed. Total time: " + dsfTime.toString() + " minutes");

		System.out.println("Done running unity.");
		stopUnity(); 
	}

	private void notify(int progressCount) {
		for (ProgressListener l : listeners) {
			Double progress = new Double((double) progressCount / (double) totalProgress * 100.0);
			l.updateProgress(progress.intValue());
			l.updateProgressMessage("Progress... " + progress.intValue() + "%");
		}
	}

	private void notify(String message) {
		for (ProgressListener l : listeners) {
			l.updateProgressMessage(message);
		}
	}

	private Term[] generateVocabTerms(ArrayList<Synset> list) {
		Term[] termArray = new Term[list.size()];

		// Create a canonical term for each synset
		for (int t = 0; t < list.size(); t++)
			termArray[t] = generateConnicalTerm(list.get(t));
		return termArray;
	}

	/**
	 * Use one default vocabulary exporter to export the vocabulary to file
	 * 
	 * @param vocabulary
	 * @param file
	 * @throws IOException
	 */
	public static void exportVocabulary(VocabularyTerms terms, File file) throws IOException {
		CompleteVocabExporter exporter = new CompleteVocabExporter();
		exporter.setClient(RepositoryManager.getClient());
		exporter.exportVocabulary(terms, file);
	}

	public ArrayList<Integer> getSchemaRanking() {
		return this.rankedSchemas;
	}

	/** Generates a canonical term from the synset */
	protected Term generateConnicalTerm(Synset synset)
	{
		// First, try to generate name based on common terms
		String name = null;

		// Get all unique words used in each synset term
		ArrayList<String> words = new ArrayList<String>();
		if(synset.getElements().size()>=2)
		{
			for(SynsetElement element : synset.getElements())
			{
				// Generate the list of words found in the elements
				LinkedHashSet<String> elementWords = new LinkedHashSet<String>();
				elementWords.addAll(Arrays.asList(element.getName().split("\\s+")));
				for(String elementWord : elementWords) words.add(elementWord.replaceAll("\\W","").toLowerCase());
			}
			
			// Load the words into a hash table
			LinkedHashMap<String,Integer> wordHash = new LinkedHashMap<String,Integer>();
			Integer maxCount = 0;
			for(String word : words)
			{
				Integer count = wordHash.get(word);
				count = count==null ? 1 : count+1;
				if(count>maxCount) maxCount=count;
				wordHash.put(word, count);
			}
				
			// If multiple items share words, generate name
			if(maxCount>1)
			{
				name = "";
				for(String word : wordHash.keySet())
					if(wordHash.get(word).equals(maxCount)) name += " " + word;
				name = name.trim();
			}
		}
		
		// Otherwise, use highest ranked schema to determine vocabulary term
		if(name==null)
			for(Integer authoritySchema : rankedSchemas)
			{
				SynsetElement element = synset.getElementBySchemaID(authoritySchema);
				if(element!=null && element.getName().length() > 0)
					{ name = element.getName(); break; }
			}

		// Create and return term object from Synset
		AssociatedElement[] elements = new ArrayList<AssociatedElement>(synset.getElements()).toArray(new AssociatedElement[0]);
		return new Term(null, name, "", elements);
	}

	public void exportVocabulary(File file) throws IOException {
		UnityDSF.exportVocabulary(this.terms, file);
	}

	public void setSchemaRanking(ArrayList<Integer> schemas) {
		this.rankedSchemas = schemas;
	}

	public void addListener(ProgressListener listener) {
		listeners.add(listener);
	}
}
