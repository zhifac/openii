/*
 *  Copyright 2011 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
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
package org.mitre.openii.editors.unity;


import java.util.ArrayList;

import org.apache.commons.lang.WordUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.schemastore.model.terms.AssociatedElement;
import org.mitre.schemastore.model.terms.Term;


public class CloseMatchPane  {

    private UnityCanvas unityCanvas;
    private Label synsetLabel2;
    private Composite closeMatchView;
    private GridData closeMatchViewGridData;
    private GridLayout closeMatchViewLayout;
	private Font LargeBoldFont = new Font(Display.getDefault(), new FontData("Arial", 14, SWT.NONE));
    private Button colorsettingsE;
    private float minMatchScore = (float)0.20;

    public Integer closeMatchSID = new Integer(-1);
    

	public CloseMatchPane(UnityCanvas unity) {
		unityCanvas = unity;

	}
	
	public void createCloseMatchPane(Composite parent) {
		
		GridLayout viewlayout = new GridLayout(3, false);
		viewlayout.marginHeight = 0;
		viewlayout.marginWidth = 0;
		viewlayout.verticalSpacing = 0;
		viewlayout.horizontalSpacing = 0;
		viewlayout.marginBottom = 0;
		parent.setLayout(viewlayout);
		GridData viewGridData = new GridData();
		viewGridData.horizontalSpan = 3;
		viewGridData.minimumHeight = 0;
		viewGridData.verticalIndent = 0;
		viewGridData.horizontalAlignment = GridData.FILL;
		viewGridData.verticalAlignment = GridData.FILL;
		viewGridData.grabExcessHorizontalSpace = true;
		viewGridData.grabExcessVerticalSpace = true;
		parent.setLayoutData(viewGridData);
		
		Composite synsetLabelC = new Composite(parent, SWT.NONE);
		RowLayout labellayout = new RowLayout();
		//labellayout.center = true;
		synsetLabelC.setLayout(labellayout);
		synsetLabelC.setLayoutData(new GridData (SWT.BEGINNING, SWT.CENTER, false, false));
		
		synsetLabel2 = new Label(synsetLabelC, SWT.NONE);
		synsetLabel2.setText("");
		synsetLabel2.setFont(LargeBoldFont);
		
		Composite matchS = new Composite(parent, SWT.NONE);
		RowLayout matchSlayout = new RowLayout();
		matchSlayout.center = true;
		matchSlayout.fill = true;
		matchSlayout.marginTop = 5;
		//tableButtonlayout.center = true;
		matchS.setLayout(matchSlayout);
		matchS.setLayoutData(new GridData (SWT.CENTER, SWT.CENTER, true, false));

		Composite buttons = new Composite(parent, SWT.NONE);
		RowLayout tableButtonlayout = new RowLayout();
		//tableButtonlayout.center = true;
		buttons.setLayout(tableButtonlayout);
		buttons.setLayoutData(new GridData (SWT.END, SWT.CENTER, false, false));
		
		
		Label scoreL = new Label(matchS, SWT.NONE);
		scoreL.setText("Match Score >= ");
		Text scoreT = new Text(matchS, SWT.BORDER);
		scoreT.setText(""+minMatchScore);
		scoreT.addListener(SWT.KeyUp, new Listener() {  
		     public void handleEvent(Event e) {  
					if(e.character==SWT.CR) {
						minMatchScore = Float.parseFloat(((Text)e.widget).getText());
						unityCanvas.updateDetailPane(unityCanvas.detailSID, unityCanvas.detailScID, unityCanvas.detailEID);
					}					
		     }});

		colorsettingsE = new Button(buttons, SWT.PUSH);
		colorsettingsE.setImage(OpenIIActivator.getImage("color_settings.png"));
		colorsettingsE.setToolTipText("Color Settings");
		colorsettingsE.setEnabled(false);
		colorsettingsE.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				colorsettingsE.setSelection(false);
			}
		});

		closeMatchView = new Composite(parent, SWT.NONE);
		closeMatchViewGridData = new GridData();
		closeMatchViewGridData.horizontalSpan = 2;
		closeMatchViewGridData.minimumHeight = 0;
		closeMatchViewGridData.verticalIndent = 0;
		closeMatchViewGridData.horizontalAlignment = GridData.FILL;
		closeMatchViewGridData.verticalAlignment = GridData.BEGINNING;
		closeMatchViewGridData.grabExcessHorizontalSpace = true;
		closeMatchViewGridData.grabExcessVerticalSpace = true;
//		closeMatchView.setLayoutData(gridData);

		closeMatchViewLayout = new GridLayout(1, false);
		closeMatchViewLayout.marginHeight = 0;
		closeMatchViewLayout.marginWidth = 0;
		closeMatchViewLayout.verticalSpacing = 0;
		closeMatchViewLayout.horizontalSpacing = 0;
		closeMatchViewLayout.marginBottom = 0;
//		closeMatchView.setLayout(EVlayout);	

		addListeners();
	}

	private void addListeners() {
		
	}

	public void updateCloseMatch(Integer synsetID, Integer schemaID, Integer elementID){
//		System.err.println("updating detail pane");
		closeMatchView.dispose();
		closeMatchView = new Composite(synsetLabel2.getParent().getParent(), SWT.NONE);
		closeMatchView.setLayoutData(closeMatchViewGridData);
		closeMatchView.setLayout(closeMatchViewLayout);	

		Label TBD = new Label(closeMatchView, SWT.NONE);
		TBD.setText("TBD");

		
		Term term = unityCanvas.getVocabulary().getTerm(synsetID);
		synsetLabel2.setText(term.getName());
		closeMatchSID = synsetID;

		AssociatedElement[][] allElements = new AssociatedElement[unityCanvas.getSchemaIDs().length][];
		ArrayList<AssociatedElement> assElements = new ArrayList<AssociatedElement>();
		int elementCount = 0;
		TableColumn showMeC = null;
		                  
		//loop through once to get all terms
		for(int j = 0; j < unityCanvas.getSchemaIDs().length; j++){			
			allElements[j] = term.getAssociatedElements(unityCanvas.getSchemas()[j].getId()); 
			for(int i = 0; i < allElements[j].length; i++) {
				assElements.add(allElements[j][i]);				
			}
			elementCount += allElements[j].length;
		//	if(allElements[j].length > 0 && first < 0) { first = j;}
		}
		//if(elementCount < 2) {return;}

		Table EvidenceTable = new Table(closeMatchView, SWT.BORDER | SWT.HIDE_SELECTION | SWT.NO_FOCUS | SWT.NONE);
		EvidenceTable.setData("name", "evidenceTable");
		EvidenceTable.setHeaderVisible(true);
		EvidenceTable.setLinesVisible(true);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 0;
		gridData.minimumHeight = 0;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		EvidenceTable.setLayoutData(gridData);
		
		// creating one column for the status and the vocabulary
		TableColumn tempC;
		tempC = new TableColumn(EvidenceTable, SWT.NONE);
//		tempC.setText("Term");
		tempC.setMoveable(false);		
		//tempC.setWidth(gc.textExtent(allElements[first][0].getName()).x + 20);
		tempC.setData("uid",new Integer(-204));
		//tempC.addListener(SWT.Selection, sortAlphabeticallyListener);
		tempC.setToolTipText("Matched Schema Term");
		// creating one column for each element
		for (int i = 0; i < allElements.length; i++) {
			for(int j = 0; j < allElements[i].length; j++) {				
				tempC = new TableColumn(EvidenceTable, SWT.NONE);
				tempC.setText(allElements[i][j].getName());
				tempC.setData("sid",allElements[i][j].getSchemaID());
				tempC.setData("eid",allElements[i][j].getElementID());
				tempC.setWidth(50);
				//tempC.setWidth((detailFolder.getBounds().width - (gc.textExtent(allElements[first][0].getName()).x + 20))/elementCount);
				tempC.setMoveable(true);
				String schema = "";
				for(int k = 0; k < unityCanvas.getSchemas().length; k++){
					if(unityCanvas.getSchemas()[k].getId().equals(allElements[i][j].getSchemaID())){
						schema = unityCanvas.getSchemas()[k].getName();
					}
				}
			      // tooltip
				  String tooltipText = allElements[i][j].getName();
				  tooltipText += "\nSchema - " + schema;
				  tooltipText += "\n";//Description: ";
				  tooltipText +=  WordUtils.wrap(allElements[i][j].getDescription(),60,"\n",true);
				tempC.setToolTipText(tooltipText);
				if(showMeC == null || (allElements[i][j].getSchemaID().equals(schemaID) && allElements[i][j].getElementID().equals(elementID))) {
					showMeC = tempC;
				}
			}
		}		
		
		
		/*
		ArrayList<MappingCell> mappingCellList = OpenIIManager.getAssociatedMappingCells(vocab.getProjectID(),assElements);
		ArrayList<String> drawnRows = new ArrayList<String>();
		//add terms  already in
		for(int i = 0; i < assElements.size(); i++){
			drawnRows.add(assElements.get(i).getElementID() + "_" + assElements.get(i).getSchemaID());
			
		}
		for(int j = 0; j < mappingCellList.size(); j++){
			MappingCell mc = mappingCellList.get(j);
			AssociatedElement workElement = null;
			Integer outputId = mc.getOutput();
			Integer otherElementId = outputId;
			for (Integer inputId : mc.getElementInputIDs()) {
				otherElementId = (drawnRows.contains(outputId) ? inputId : outputId);			
			}
			
			if(!drawnRows.contains(otherElementId + "_" + )){

			}
			drawnRows.add(workElement);				
		}
	
							for (Integer inputId : mc.getElementInputIDs()) {
								Integer otherElementId = (outputId.equals(allElements[j][i].getElementID())) ? inputId : outputId;
								if(otherElementId.equals(EvidenceTable.getColumn(count).getData("eid")) && otherSchemaId.equals(EvidenceTable.getColumn(count).getData("sid")) ) {
	//System.out.println("otherElementId = " + otherElementId + "otherSchemaId = " + otherSchemaId + " score = " + mc.getScore().toString());
									//								SchemaElement otherElement = schemaInfos[otherSIndex].getElement(otherElementId);
									newItem.setText(count, mc.getScore().toString());
									if(mc.getScore() >= .85) {
										newItem.setBackground(count,green);
									} else if(mc.getScore() >= .7) {
										newItem.setBackground(count,green);
									} else if(mc.getScore() >= .4) {
										newItem.setBackground(count,yellow);
									} else if(mc.getScore() >= .2) {
										newItem.setBackground(count,red);
									} else {
										newItem.setBackground(count,red);
									}
								}
								
							}
						
						}
					}
				}
				
				
				
			    // tooltip
			    String tooltipText = allElements[j][i].getName();
			    tooltipText += "\nSchema - " + schemas[j].getName();
			    tooltipText += "\n";//Description: ";
			    tooltipText +=  WordUtils.wrap(allElements[j][i].getDescription(),60,"\n",true);
				newItem.setData("popup",tooltipText);
			}
		}
				
		EvidenceTable.showItem(showMeR);
		EvidenceTable.showColumn(showMeC);
		EvidenceTable.getColumn(0).setWidth(gc.textExtent(showMeR.getText()).x + 20);
		
		EvidenceTable.addListener(SWT.MouseHover, new Listener() {
			public void handleEvent(Event e) {
					activeTable = (Table)(e.widget);
		        	// Identify the selected row
					TableItem item = ((Table)(e.widget)).getItem(new Point(e.x,e.y));
				    if (activeTable.getColumn(0).getWidth() < e.x || item == null)
				    {
				    	activeTable.setToolTipText(null);
				    } else if(item.getData("popup") != null) {
				    	activeTable.setToolTipText((String)item.getData("popup"));
				    }
		        }

		});
		
		EvidenceTable.addListener(SWT.Resize, new Listener() {	
			public void handleEvent(Event e) {
				Table table = ((Table)(e.widget));
				int size = 1+(table.getBounds().width - table.getColumn(0).getWidth())/(table.getColumnCount()-1);
				for(int i = 1; i < table.getColumnCount(); i++)
				{
					table.getColumn(i).setWidth(size);
				}
			}
		});

		*/
		
		synsetLabel2.getParent().getParent().layout(true);
		
	}
}