package org.mitre.openii.editors.vocabulary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.mitre.affinity.view.vocab_debug_view.view.swt.SWTVocabDebugViewTwo;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.Vocabulary;

/** Constructs the Harmony View */
public class VocabEditor extends OpenIIEditor {
	/** Retrieve the schema names to be used as column headers */
	private String[] getSchemaNames(Integer[] schemaIDs) {
		String[] schemaNames = new String[schemaIDs.length + 1];
		schemaNames[0] = "Vocabulary";
		for (int i = 0; i < schemaIDs.length; i++)
			schemaNames[i + 1] = OpenIIManager.getSchema(schemaIDs[i])
					.getName();
		return schemaNames;
	}

	/** Translate the terms into rows to be displayed */
	private ArrayList<String[]> generateRows(Integer[] schemaIDs, Term[] terms) {
		// Print Term, associated elements, and respective description
		ArrayList<String[]> rows = new ArrayList<String[]>();
		
		// sort terms
		Arrays.sort(terms, new Comparator<Term>() {

			public int compare(Term o1, Term o2) {
				if (o1.getElements().length > o2.getElements().length)
					return -1;
				else if (o1.getElements().length < o2.getElements().length)
					return 1;
				return o1.getName().compareTo(o2.getName());
			}

		});

		for (Term term : terms) {
			String[] row = new String[schemaIDs.length + 1];
			// First column is the vocabulary term
			row[0] = term.getName();

			// The subsequent columns are source schema terms
			for (int j = 0; j < schemaIDs.length; j++) {
				AssociatedElement element = term
						.getAssociatedElement(schemaIDs[j]);
				row[j + 1] = (element == null) ? "" : element.getName();
			}
			rows.add(row);
		}
		return rows;
	}

	/** Displays the VocabView */
	public void createPartControl(final Composite parent) {
		try {
			Vocabulary vocabulary = OpenIIManager.getVocabulary(getElementID());
			Integer[] schemaIDs = vocabulary.getSchemaIDs();
			String[] schemaNames = getSchemaNames(schemaIDs);
			ArrayList<String[]> rows = generateRows(schemaIDs,
					vocabulary.getTerms());
			new SWTVocabDebugViewTwo(parent, SWT.NONE, schemaNames, rows);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
