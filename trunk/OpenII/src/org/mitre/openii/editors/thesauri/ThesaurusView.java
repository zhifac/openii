package org.mitre.openii.editors.thesauri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.TableWidget;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Synonym;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/** Constructs the Schema View */
public class ThesaurusView extends OpenIIEditor
{	
	/** Returns the list of schema elements for the specified element type */
	private ArrayList<SchemaElement> getElements(SchemaInfo schemaInfo)
	{
		// Class for sorting schema elements
		class ElementComparator implements Comparator<SchemaElement>
		{
			public int compare(SchemaElement element1, SchemaElement element2)
				{ return element1.getName().compareTo(element2.getName()); }			
		}
		
		// Returns the list of schema elements
		ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>();
		for(SchemaElement element : schemaInfo.getElements(null))
			if(schemaInfo.getSynonyms(element.getId()).size()>0)
				elements.add(element);
		Collections.sort(elements,new ElementComparator());
		return elements;
	}
	
	/** Displays the Schema View */
	public void createPartControl(Composite parent)
	{		
		// Add a margin around the pane
		GridLayout layout = new GridLayout();
		layout.marginHeight = 5; layout.marginWidth = 5;
		parent.setLayout(layout);
		
		// Get the schema info
		SchemaInfo schemaInfo = OpenIIManager.getSchemaInfo(getElementID());

		// Fetch the field and row information
		String[] fields = new String[]{"Term","Synonyms"};
		ArrayList<Object[]> rows = new ArrayList<Object[]>();
		for(SchemaElement element : getElements(schemaInfo))
		{
			// Gather up the synonyms
			StringBuffer synonyms = new StringBuffer();
			for(Synonym synonym : schemaInfo.getSynonyms(element.getId()))
				synonyms.append(synonym + ", ");
				
			// Create the row
			rows.add(new Object[]{element.getName(),synonyms.substring(0, synonyms.length()-2)});
		}

		// Create the table
		TableWidget.createTable(parent, fields, rows);
	}
}