/*
 *  Copyright 2010 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mitre.affinity.view.vocab_debug_view.swing;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets.MatchedSchemaElements;
import org.mitre.affinity.view.vocab_debug_view.model.VocabDebugViewSets;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;


public class VocabDebugPane extends JPanel {
	private static final long serialVersionUID = 1L;

	public VocabDebugPane(VocabDebugViewSets sets) { 
		super();
		setLayout(new BorderLayout());

/*		vocabDebuggingView = new VocabDebugView(sets);
		vocabDebuggingView.setFont(new Font(getFont().getName(), Font.BOLD, 12));
		vocabDebuggingView.setBackground(Color.WHITE);		
		add(vocabDebuggingView, BorderLayout.CENTER);		
		
	*/	
		ArrayList<VennDiagramSets> allSets = sets.getAllSets();
		int numSets = allSets.size();
		
		//determining the max number of rows 
		ArrayList<SchemaElement> allElementsInCore = allSets.get(0).getSchema1AllElements();	
		int maxRows = allElementsInCore.size();
	
		
		
		//first get the names of all the schemas in the set
		String[] columnNames = new String[numSets+1];
		Object[][] tableData = new Object[maxRows][numSets+1];
		
		//the first schema in each set will always be the core (temporary core at the moment)
		columnNames[0] = new String("Core (" + allSets.get(0).getSchema1().getName() + ")");
		for(int j=0; j<maxRows; j++){
			tableData[j][0] = allElementsInCore.get(j).toString();
		}
		
		//getting the schemaID for the core
		int coreSchemaID = allSets.get(0).getSchema1().getId();
		
		//start at 1 because we've already populated 0
		for(int i=0; i<numSets; i++){
			VennDiagramSets currentSet = allSets.get(i);
			//setting the column name to be the same as the schema
			Schema schema2 = currentSet.getSchema2();
			columnNames[i+1] = schema2.getName();
			
			//testing
			//now putting the matching elements in the appropriate columns
			List<MatchedSchemaElements> elementsMatchingCore = currentSet.getIntersectElements();
				
			
		/*	for(MatchedSchemaElements matchedElements : elementsMatchingCore){
				SchemaElement elementInCore = matchedElements.getMatchedElement(coreSchemaID);
				SchemaElement elementInSchema2 = matchedElements.getMatchedElement(schema2.getId());
				String coreElement = elementInCore.getName();
				String matchingElement = elementInSchema2.getName();
				System.out.println("Matched: " + coreElement);
				System.out.println(" To : " + matchingElement + " in " + schema2.getName());
				
			}
			*/
					
			for(int j=0; j<maxRows; j++){
				//for each row in the core schema, see if there is a match in the intersection
				SchemaElement currentRowElement = allElementsInCore.get(j);
				//System.out.println("Begin search for elements matching " + currentRowElement);
				//boolean found = false;
				for(MatchedSchemaElements matchedElements : elementsMatchingCore){
					if(matchedElements.getMatchedElement(coreSchemaID) == currentRowElement){
						tableData[j][i+1] = matchedElements.getMatchedElement(schema2.getId());
						//System.out.println("found matching element in schema2: " + matchedElements.getMatchedElement(schema2.getId()) + " breaking");
						//found = true;
						break;
					}
				}

				if(tableData[j][i+1] == null){
					tableData[j][i+1] = new String(""); // put
				}
				
			}
		}
		
				
		

		JTable table = new JTable(tableData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		add(scrollPane, BorderLayout.CENTER);
	
	}
	
	
	
	/**
	 * View used to help debug automatic generation of common vocabulary
	 */
	public static class VocabDebugView extends JPanel {
		
		private static final long serialVersionUID = 1L;		
		
		private VocabDebugViewSets data;
		
		//private String[] columnNames;
		//private Object[][] tableData;
		//private ArrayList<VennDiagramSets> allSets;
		
		public VocabDebugView(VocabDebugViewSets dataInput) {
			super(true);	
			data = dataInput;
			//allSets = data.getAllSets();					
			/*
			this.addAncestorListener(new AncestorListener() {
				public void ancestorAdded(AncestorEvent arg0) {}
				public void ancestorMoved(AncestorEvent arg0) {}
				public void ancestorRemoved(AncestorEvent arg0) {
					if(dialog != null)
						dialog.dispose();
				}				
			}); */

		}

		public VocabDebugViewSets getSets() {
			return data;
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;

			//Render the background
			super.paintComponent(g2d);
			//Insets insets = this.getInsets();
			//Rectangle bounds = new Rectangle(insets.left + 1, insets.top + 1, getWidth() - insets.left - insets.right - 2,
			//		getHeight() - insets.top - insets.bottom - 2);

			//g2d.setFont(getFont());
			//FontMetrics fontMetrics = g2d.getFontMetrics();
			//int fontHeight = fontMetrics.getAscent();
		}
	}
}
