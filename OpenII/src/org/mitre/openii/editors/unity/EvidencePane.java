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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
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
import org.eclipse.swt.widgets.TableItem;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.terms.AssociatedElement;
import org.mitre.schemastore.model.terms.Term;


public class EvidencePane  {

    private UnityCanvas unityCanvas;
	private Color red;
	private Color yellow;
	private Color green;
	private Font LargeBoldFont = new Font(Display.getDefault(), new FontData("Arial", 14, SWT.NONE));

	private Label synsetLabel1;
    private Button colorsettingsE;
    private Composite EvidenceView;
    private GridData EvidenceViewGridData;
    private GridLayout EvidenceViewLayout;

    
    public Integer evidenceSID = new Integer(-1);
    


	public EvidencePane(UnityCanvas unity) {
		unityCanvas = unity;
		red =  unityCanvas.getDisplay().getSystemColor(SWT.COLOR_RED);
		yellow =  unityCanvas.getDisplay().getSystemColor(SWT.COLOR_YELLOW);
		green =  unityCanvas.getDisplay().getSystemColor(SWT.COLOR_GREEN);

	}
	
	public void createEvidencePane(Composite parent) {
		
		GridLayout viewlayout = new GridLayout(2, false);
		viewlayout.marginHeight = 0;
		viewlayout.marginWidth = 0;
		viewlayout.verticalSpacing = 0;
		viewlayout.horizontalSpacing = 0;
		viewlayout.marginBottom = 0;
		parent.setLayout(viewlayout);
		GridData viewGridData = new GridData();
		viewGridData.horizontalSpan = 2;
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
		synsetLabelC.setLayoutData(new GridData (SWT.BEGINNING, SWT.CENTER, true, false));
		
		synsetLabel1 = new Label(synsetLabelC, SWT.NONE);
		synsetLabel1.setText("");
		synsetLabel1.setFont(LargeBoldFont);
		
		Composite buttons = new Composite(parent, SWT.NONE);
		RowLayout tableButtonlayout = new RowLayout();
		//tableButtonlayout.center = true;
		buttons.setLayout(tableButtonlayout);
		buttons.setLayoutData(new GridData (SWT.END, SWT.CENTER, false, false));
		
		colorsettingsE = new Button(buttons, SWT.PUSH);
		colorsettingsE.setImage(OpenIIActivator.getImage("color_settings.png"));
		colorsettingsE.setToolTipText("Color Settings");
//		colorsettingsE.setEnabled(false);
		colorsettingsE.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				colorsettingsE.setSelection(false);
			}
		});

		EvidenceView = new Composite(parent, SWT.NONE);
		EvidenceViewGridData = new GridData();
		EvidenceViewGridData.horizontalSpan = 2;
		EvidenceViewGridData.minimumHeight = 0;
		EvidenceViewGridData.verticalIndent = 0;
		EvidenceViewGridData.horizontalAlignment = GridData.FILL;
		EvidenceViewGridData.verticalAlignment = GridData.BEGINNING;
		EvidenceViewGridData.grabExcessHorizontalSpace = true;
		EvidenceViewGridData.grabExcessVerticalSpace = true;
//		EvidenceView.setLayoutData(gridData);

		EvidenceViewLayout = new GridLayout(1, false);
		EvidenceViewLayout.marginHeight = 0;
		EvidenceViewLayout.marginWidth = 0;
		EvidenceViewLayout.verticalSpacing = 0;
		EvidenceViewLayout.horizontalSpacing = 0;
		EvidenceViewLayout.marginBottom = 0;
//		EvidenceView.setLayout(EVlayout);	
				
		addListeners();
	}

	private void addListeners() {
		
	}

	public void updateEvidence(Integer synsetID, Integer schemaID, Integer elementID){
//		System.err.println("updating detail pane");
		EvidenceView.dispose();
		EvidenceView = new Composite(synsetLabel1.getParent().getParent(), SWT.NONE);
		EvidenceView.setLayoutData(EvidenceViewGridData);
		EvidenceView.setLayout(EvidenceViewLayout);	

		evidenceSID = synsetID;
		Term term = unityCanvas.getVocabulary().getTerm(synsetID);
		synsetLabel1.setText(term.getName());

		AssociatedElement[][] allElements = new AssociatedElement[unityCanvas.getSchemaIDs().length][];
		ArrayList<AssociatedElement> assElements = new ArrayList<AssociatedElement>();
//		int elementCount = 0;
		TableItem showMeR = null;
		TableColumn showMeC = null;
		//int first = -1;
		                  
		//loop through once to get all terms
		for(int j = 0; j < unityCanvas.getSchemaIDs().length; j++){			
			allElements[j] = term.getAssociatedElements(unityCanvas.getSchemas()[j].getId()); 
			for(int i = 0; i < allElements[j].length; i++) {
				assElements.add(allElements[j][i]);				
			}
//			elementCount += allElements[j].length;
		//	if(allElements[j].length > 0 && first < 0) { first = j;}
		}
		//if(elementCount < 2) {return;}

		Table EvidenceTable = new Table(EvidenceView, SWT.BORDER | SWT.HIDE_SELECTION | SWT.NO_FOCUS | SWT.NONE);
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
		
		ArrayList<MappingCell> mappingCellList = OpenIIManager.getAssociatedMappingCells(unityCanvas.getVocabulary().getProjectID(),assElements);
		//for each schema
		for(int j = 0; j < unityCanvas.getSchemaIDs().length; j++){
			
//			Label schemaLabel = new Label(EvidenceView, SWT.NONE);
//			schemaLabel.setText(schemas[j].getName());
//			schemaLabel.setToolTipText(schemas[j].getDescription());
			
					
			for(int i = 0; i < allElements[j].length; i++) {			
			//for each term
				TableItem newItem = new TableItem(EvidenceTable, SWT.NONE, EvidenceTable.getItemCount());
				newItem.setText(0, allElements[j][i].getName());
				if(showMeR == null || (allElements[j][i].getSchemaID().equals(schemaID) && allElements[j][i].getElementID().equals(elementID))) {
					showMeR = newItem;
				}

				
				
				//add matching info here
				// for each mapping, we need to find any cells that map to the
				// current element
/*				ArrayList<MappingCell> mappingCellList = new ArrayList<MappingCell>();
				for (Mapping m : mappings.values()) {
					MappingInfoExt mix = new MappingInfoExt(m, OpenIIManager.getMappingCells(m.getId()));
					AssociatedElementHash aeh = mix.getMappingCells();
					for ( MappingCell mc : aeh.get(allElements[j][i].getElementID()) )
						if ( !mappingCellList.contains(mc))
							mappingCellList.add(mc); 
				}	*/
				int count = 0;
				for (int l = 0; l < allElements.length; l++) {
					for (int n = 0; n < allElements[l].length; n++) {
						count++;
						for (int k = 0; k < mappingCellList.size(); k++) {
							MappingCell mc = mappingCellList.get(k); 
							Mapping m = unityCanvas.getMappings().get(mc.getMappingId());
							Integer outputId = mc.getOutput();
							Integer otherSchemaId = (allElements[j][i].getSchemaID().equals(m.getSourceId())) ? m.getTargetId() : m.getSourceId();
							if(otherSchemaId.equals(allElements[j][i].getSchemaID())){
								break;
							}
							int otherSIndex = 0;
							for(; otherSIndex < unityCanvas.getSchemaIDs().length; otherSIndex++){
								if(unityCanvas.getSchemaIDs()[otherSIndex].equals(otherSchemaId)) {
									break;
								}
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
			    tooltipText += "\nSchema - " + unityCanvas.getSchemas()[j].getName();
			    tooltipText += "\n";//Description: ";
			    tooltipText +=  WordUtils.wrap(allElements[j][i].getDescription(),60,"\n",true);
				newItem.setData("popup",tooltipText);
			}
		}
				
		if(showMeR != null) EvidenceTable.showItem(showMeR);
		if(showMeC != null) EvidenceTable.showColumn(showMeC);
		if(showMeR != null) EvidenceTable.getColumn(0).setWidth((new GC(EvidenceTable)).textExtent(showMeR.getText()).x + 10);
		
		EvidenceTable.addListener(SWT.MouseHover, new Listener() {
			public void handleEvent(Event e) {
				unityCanvas.activeTable = (Table)(e.widget);
		        	// Identify the selected row
					TableItem item = ((Table)(e.widget)).getItem(new Point(e.x,e.y));
				    if (unityCanvas.activeTable.getColumn(0).getWidth() < e.x || item == null)
				    {
				    	unityCanvas.activeTable.setToolTipText(null);
				    } else if(item.getData("popup") != null) {
				    	unityCanvas.activeTable.setToolTipText((String)item.getData("popup"));
				    }
		        }

		});
		
		EvidenceTable.addListener(SWT.Resize, new Listener() {	
			public void handleEvent(Event e) {
				Table table = ((Table)(e.widget));
				if(table.getColumnCount() > 1) {
					int size = 1+(table.getBounds().width - table.getColumn(0).getWidth())/(table.getColumnCount()-1);
					for(int i = 1; i < table.getColumnCount(); i++)
					{
						table.getColumn(i).setWidth(size);
					}
				}
			}
		});

		
/* These workarounds don't work
		EvidenceTable.addListener(SWT.EraseItem, new Listener() {
			public void handleEvent(Event event) {
				event.detail &= ~SWT.HOT;	
				if((event.detail & SWT.SELECTED) != 0) {
					event.detail &= ~SWT.SELECTED;					
				}						
				int value = 0;
				Table theTable = (Table)(event.widget);
				theTable.deselectAll();
				for(int i = 1; i < theTable.getColumnCount(); i++)
				{
					value = Integer.parseInt(theTable.getSelection()[0].getText(i));
					if(value >= .85) {
						theTable.getSelection()[0].setBackground(i,green);
					} else if(value >= .7) {
						theTable.getSelection()[0].setBackground(i,green);
					} else if(value >= .4) {
						theTable.getSelection()[0].setBackground(i,yellow);
					} else if(value >= .2) {
						theTable.getSelection()[0].setBackground(i,red);
					} else {
						theTable.getSelection()[0].setBackground(i,red);
					}
				}
			}
		});		

		EvidenceTable.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent event) {
				int value = 0;
				Table theTable = (Table)(event.widget);
				theTable.deselectAll();
				for(int i = 1; i < theTable.getColumnCount(); i++)
				{
					value = Integer.parseInt(theTable.getSelection()[0].getText(i));
					if(value >= .85) {
						theTable.getSelection()[0].setBackground(i,green);
					} else if(value >= .7) {
						theTable.getSelection()[0].setBackground(i,green);
					} else if(value >= .4) {
						theTable.getSelection()[0].setBackground(i,yellow);
					} else if(value >= .2) {
						theTable.getSelection()[0].setBackground(i,red);
					} else {
						theTable.getSelection()[0].setBackground(i,red);
					}
				}
            }

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				int value = 0;
				Table theTable = (Table)(event.widget);
				theTable.deselectAll();
				for(int i = 1; i < theTable.getColumnCount(); i++)
				{
					value = Integer.parseInt(theTable.getSelection()[0].getText(i));
					if(value >= .85) {
						theTable.getSelection()[0].setBackground(i,green);
					} else if(value >= .7) {
						theTable.getSelection()[0].setBackground(i,green);
					} else if(value >= .4) {
						theTable.getSelection()[0].setBackground(i,yellow);
					} else if(value >= .2) {
						theTable.getSelection()[0].setBackground(i,red);
					} else {
						theTable.getSelection()[0].setBackground(i,red);
					}
				}
			}

		});
	*/	
		
		synsetLabel1.getParent().getParent().layout(true);
	
	}
}