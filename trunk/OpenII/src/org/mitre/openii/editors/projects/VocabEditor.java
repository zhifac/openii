package org.mitre.openii.editors.projects;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.EditorInput;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.projects.unity.Unity;
import org.mitre.openii.views.manager.projects.unity.Vocabulary;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.porters.projectExporters.matchmaker.Synset;
import org.mitre.schemastore.porters.projectExporters.matchmaker.SynsetTerm;
import org.mitre.affinity.view.vocab_debug_view.view.swt.SWTVocabDebugViewTwo;

public class VocabEditor extends OpenIIEditor {

	// uses schema IDs to create an array of schema names
	// schema names are passed to the viewer **viewer assumes that the first schemaID is the core**
	private String[] getSchemaNames(Project p) {
		Integer[] schemaIDs = p.getSchemaIDs();
		String[] schemaNamesArray = new String[schemaIDs.length];
		for (int i = 0; i < schemaIDs.length; i++) {
			schemaNamesArray[i] = OpenIIManager.getSchemaInfo(schemaIDs[i]).getSchema().getName();
		}
		return schemaNamesArray;
	}

	// transforms the Array<Synset> into the data structure that the viewer uses
	// the data structure is an ArrayList<String[num_schemas_incl_core]>
	private ArrayList<String[]> transformSynsetArray(Integer[] schemaIDs, ArrayList<Synset> synsetsOrig) {
		ArrayList<String[]> synsets = new ArrayList<String[]>(); // same order as schemaNames
		
		// transform each synset into a a String[] to be used in viewer
		for (int i = 0; i < synsetsOrig.size(); i++) {
			Synset origS = synsetsOrig.get(i);
			String[] s = new String[schemaIDs.length];
			for (int j = 0; j < schemaIDs.length; j++) {
				SynsetTerm t = origS.getTerm(schemaIDs[j]);
				s[j] = (t == null) ?  "" : t.elementName;
			}
			synsets.add(s);
		}
		return synsets;
	}

	// this function is in here for testing purposes only
	// it creates fake Synsets for a Project
	private ArrayList<Synset> createFakeSynsets(Project p) {
		ArrayList<Synset> fakeData = new ArrayList<Synset>();
		int numFakeSynsets = 10;
		Integer[] schemaIDs = p.getSchemaIDs();

		for (int i = 0; i < numFakeSynsets; i++) {
			// using first schema ID as fake core
			SynsetTerm t = new SynsetTerm(schemaIDs[0], null, "common term for fake synset " + i);
			Synset s = new Synset(t);
			for (int j = 0; j < schemaIDs.length; j++) {
				// create a term for each schema
				t = new SynsetTerm(schemaIDs[j], null, "synset " + i + " term from " + OpenIIManager.getSchemaInfo(schemaIDs[j]).getSchema().getName());
				// add term to synset - note I made this public only so I could test this
				// as soon as real data is there, no reason to call s.add(t)
				s.add(t);
			}
			fakeData.add(s);
		}
		return fakeData;
	}

	/** Displays the VocabDebugView */
	public void createPartControl(final Composite parent) {
		Object object = ((EditorInput) getEditorInput()).getElement();
		if (object instanceof Project) {
			Project proj = (Project) object;
			Unity unity = new Unity(proj);
			Vocabulary vocab;
			try {
				vocab = unity.getVocabulary(); 
				String[] schemaNames = getSchemaNames(proj);

				// ArrayList<Synset> synsets = createFakeSynsets(proj);
				ArrayList<Synset> synsets = vocab.getSynsetList();
				Integer[] schemaIDs = proj.getSchemaIDs();
				
				ArrayList<String[]> synsetsArray = transformSynsetArray(schemaIDs, synsets);

				// view results
				new SWTVocabDebugViewTwo(parent, SWT.NONE, schemaNames, synsetsArray);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
