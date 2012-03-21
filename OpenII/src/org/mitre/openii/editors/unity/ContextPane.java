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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.terms.AssociatedElement;
import org.mitre.schemastore.model.terms.Term;


public class ContextPane  {

    private UnityCanvas unityCanvas;
    private Image DomainValueImage = OpenIIActivator.getImage("DomainValue.jpg");
    private Image AttributeImage = OpenIIActivator.getImage("Attribute.jpg");
    private Image ContainmentImage = OpenIIActivator.getImage("Containment.jpg");
    private Image RelationshipImage = OpenIIActivator.getImage("Relationship.jpg");
    private Image SchemaElementImage = OpenIIActivator.getImage("SchemaElement.jpg");
	private Color white;
	private Font LargeBoldFont = new Font(Display.getDefault(), new FontData("Arial", 14, SWT.NONE));
    private ScrolledComposite contextView;
    private GridData contextViewGridData;
    private GridLayout contextViewLayout;
    private Label synsetLabel3;

    public Integer contextSID = new Integer(-1);


    public Integer closeMatchSID = new Integer(-1);
    

	public ContextPane(UnityCanvas unity) {
		unityCanvas = unity;
		white = unityCanvas.getDisplay().getSystemColor(SWT.COLOR_WHITE);

	}
	
	public void createContextPane(Composite parent) {
		GridLayout viewlayout = new GridLayout(2, false);
		viewlayout.marginHeight = 0;
		viewlayout.marginWidth = 0;
		viewlayout.verticalSpacing = 0;
		viewlayout.horizontalSpacing = 0;
		viewlayout.marginBottom = 0;
		parent.setLayout(viewlayout);
		GridData viewGridData = new GridData();
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
		
		synsetLabel3 = new Label(synsetLabelC, SWT.NONE);
		synsetLabel3.setText("");
		synsetLabel3.setFont(LargeBoldFont);
		
		Composite buttons = new Composite(parent, SWT.NONE);
		RowLayout tableButtonlayout = new RowLayout();
		//tableButtonlayout.center = true;
		buttons.setLayout(tableButtonlayout);
		buttons.setLayoutData(new GridData (SWT.END, SWT.CENTER, false, false));
/*		
		colorsettingsE = new Button(buttons, SWT.PUSH);
		colorsettingsE.setImage(OpenIIActivator.getImage("color_settings.png"));
		colorsettingsE.setToolTipText("Color Settings");
		colorsettingsE.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				colorsettingsE.setSelection(false);
			}
		});
*/
		contextView = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		contextViewGridData = new GridData();
		contextViewGridData.horizontalSpan = 2;
		contextViewGridData.minimumHeight = 0;
		contextViewGridData.verticalIndent = 0;
		contextViewGridData.horizontalAlignment = GridData.FILL;
		contextViewGridData.verticalAlignment = GridData.BEGINNING;
		contextViewGridData.grabExcessHorizontalSpace = true;
		contextViewGridData.grabExcessVerticalSpace = true;
		contextView.setLayoutData(contextViewGridData);

		contextViewLayout = new GridLayout(2, false);
		contextViewLayout.marginHeight = 4;
		contextViewLayout.marginWidth = 4;
		contextView.setLayout(contextViewLayout);	
		new Composite(contextView, SWT.NONE); //dummy component
		contextView.setExpandVertical(true);
		contextView.setExpandHorizontal(true);

		addListeners();
	}

	private void addListeners() {
		
	}

	public void updateContext(Integer synsetID, Integer schemaID, Integer elementID){
//		System.err.println("updating detail pane");
		Composite tempComp = (Composite)contextView.getChildren()[0];
		if(tempComp != null && !tempComp.isDisposed())
		{
			tempComp.dispose();
		}
		
		tempComp = new Composite(contextView, SWT.NONE);

		tempComp.setLayout(new GridLayout(1, false));
		tempComp.setLayoutData(contextViewGridData);

		Term term = unityCanvas.getVocabulary().getTerm(synsetID);
		synsetLabel3.setText(term.getName());
		contextSID = synsetID;

		AssociatedElement[][] allElements = new AssociatedElement[unityCanvas.getSchemaIDs().length][];
		int elementCount = 0;
		Composite showMeR = null;
		                  
		//loop through once to get all terms
		for(int j = 0; j < unityCanvas.getSchemaIDs().length; j++){			
			allElements[j] = term.getAssociatedElements(unityCanvas.getSchemas()[j].getId()); 
			elementCount += allElements[j].length;
		}

		GridData gridData = new GridData();
		gridData.horizontalSpan = 1;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;

		ArrayList<SchemaElement> parents = null;
		//for each schema
		for(int j = 0; j < unityCanvas.getSchemaIDs().length; j++){
		
					
			for(int i = 0; i < allElements[j].length; i++) {			
			//for each term
				Composite termDetails = new Composite(tempComp, SWT.NONE);
				termDetails.setLayoutData(gridData);
				termDetails.setLayout(new GridLayout(1,true));
				termDetails.setBackground(white);
				HierarchicalSchemaInfo hsi = new HierarchicalSchemaInfo(unityCanvas.getSchemaInfos()[j], unityCanvas.getSchemaModels()[j]);
				Label name = new Label(termDetails, SWT.NONE);
				name.setText("  " + allElements[j][i].getName());
				name.setBackground(white);

				Label schemaname = new Label(termDetails, SWT.NONE);
				schemaname.setText("  Schema - " + unityCanvas.getSchemas()[j].getName());
				schemaname.setBackground(white);

				Composite contextC = new Composite(termDetails,SWT.NONE);
				contextC.setBackground(white);
				contextC.setLayout(new GridLayout(3,false));
				Label context = new Label(contextC, SWT.NONE);
				context.setText("Context - ");
				context.setBackground(white);
				
				parents = hsi.getAncestorElements(allElements[j][i].getElementID());
				int k = 0;
				String text = "";
				for(; k < parents.size(); k++) {
					text = "";
					for(int l = 0; l < k; l++) {
						text += "     ";
					}
					if(k > 0) {
						text += "                  ";
						contextC = new Composite(termDetails,SWT.NONE);
						contextC.setBackground(white);
						contextC.setLayout(new GridLayout(3,false));
						Label spacerC = new Label(contextC, SWT.NONE);
						spacerC.setBackground(white);
						spacerC.setText(text);
					}
					Label iLabel = new Label(contextC, SWT.NONE);
					Label pLabel = new Label(contextC, SWT.NONE);
					pLabel.setBackground(white);
					SchemaElement parentElement = parents.get(k);
					Image img =  SchemaElementImage;
					if(parentElement instanceof DomainValue) img =  DomainValueImage;
					else if(parentElement instanceof Attribute) img =  AttributeImage;
					else if(parentElement instanceof Containment) img =  ContainmentImage;
					else if(parentElement instanceof Relationship) img =  RelationshipImage;
					iLabel.setImage(img);
					pLabel.setText(parentElement.getName());
				}
				text = "";
				for(int l = 0; l < k; l++) {
					text += "     ";
				}
				if(k > 0) {
					text += "                  ";
					contextC = new Composite(termDetails,SWT.NONE);
					contextC.setBackground(white);
					contextC.setLayout(new GridLayout(3,false));
					Label spacerC = new Label(contextC, SWT.NONE);
					spacerC.setText(text);
					spacerC.setBackground(white);
				}
				Label iLabel = new Label(contextC, SWT.NONE);
				Label pLabel = new Label(contextC, SWT.NONE);
				SchemaElement selfElement = hsi.getType(hsi, allElements[j][i].getElementID());
				pLabel.setText(allElements[j][i].getName());
				pLabel.setAlignment(SWT.BEGINNING);
				pLabel.setBackground(white);
				Image img =  SchemaElementImage;
				if(selfElement instanceof DomainValue) img =  DomainValueImage;
				else if(selfElement instanceof Attribute) img =  AttributeImage;
				else if(selfElement instanceof Containment) img =  ContainmentImage;
				else if(selfElement instanceof Relationship) img =  RelationshipImage;
				iLabel.setImage(img);
				
				Label descLabel = new Label(termDetails, SWT.NONE);
				descLabel.setText("  Description - " + allElements[j][i].getDescription());
				descLabel.setBackground(white);

				if(showMeR == null || (allElements[j][i].getSchemaID().equals(schemaID) && allElements[j][i].getElementID().equals(elementID))) {
					showMeR = termDetails;
				}
			}
		}

		//show item
		contextView.setContent(tempComp);
		contextView.getParent().layout(true);
		contextView.setMinSize(contextView.computeSize(SWT.DEFAULT, SWT.DEFAULT));		
	}
}