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

package org.mitre.affinity.view.venn_diagram.view.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSetsMatrix;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets.MatchedSchemaElements;
import org.mitre.affinity.view.venn_diagram.view.event.VennDiagramEvent;
import org.mitre.affinity.view.venn_diagram.view.event.VennDiagramListener;
import org.mitre.affinity.view.venn_diagram.view.swing.VennDiagramKNearestPane.VennDiagramKNearestView;
import org.mitre.harmony.matchers.MatchGenerator;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;

/**
 * A grid of Venn Diagrams for one schema vs. all others
 * the one schema is selected in the Affinity view
 * 
 * @author CBONACETO
 * @author BETHYOST
 * 
 */
public class VennDiagramKNearestsMatrixPane extends JPanel implements MinMaxSliderListener, MouseListener, ActionListener {
	private static final long serialVersionUID = 1L;
	
	private VennDiagramSetsMatrix matrix;
	private List<VennDiagramKNearestView> vennDiagrams;
	
	/** Currently selected venn diagram matrix cell */
	private VennDiagramKNearestView selectedVennDiagram;
	
	/** Listeners that have registered to receive venn diagram events from this component */
	private List<VennDiagramListener> listeners;
	 
	JComboBox radioBox;
	 
	private Border cellBorder = BorderFactory.createLineBorder(Color.BLACK);
	private Border selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 3);
	
	JList uniqueAList, matchedABList, uniqueBList;
	DefaultListModel uniqueAlistmodel, matchedABlistmodel, uniqueBlistmodel;
	JLabel labelA, labelB;
	
	String schemaAName;
	
	Set<SchemaElement> prev_seA;
	
	/** Whether or not schema names are labeled on row/column headings */
	//private boolean labelRowsAndColumns = true;
	
	/** Whether or not schema names are labeled in individual venn diagram cells */
	//private boolean labelCells = true;
	
	public VennDiagramKNearestsMatrixPane(List<FilteredSchemaInfo> schemaInfos,	double minMatchScoreThreshold, 
			double maxMatchScoreThreshold, MatchGenerator matchScoreComputer, boolean showMinMaxSlider) {
		this(new VennDiagramSetsMatrix(schemaInfos, minMatchScoreThreshold, 
				maxMatchScoreThreshold, matchScoreComputer), true);
	}
	
	public VennDiagramKNearestsMatrixPane(final VennDiagramSetsMatrix matrix, boolean showMinMaxSlider) {
		super();
        
		this.prev_seA = null;
		this.matrix = matrix;
		//sortRowByJaccards();
		this.sortRowByUniqueA();
		
		//calculate the maxSchema2Size
		//this value is used to ensure the 1 (in the 1xn schemas) is always the same size across Venn Diagrams
		int tempSize = 0;
		int maxSchema2Size = 0;
		for(ArrayList<VennDiagramSets> vdSet : matrix) {
			for(VennDiagramSets vd : vdSet){
				tempSize = vd.getSchema2AllElements().size();
				if(tempSize > maxSchema2Size){
					maxSchema2Size = tempSize;
				}
			}
		}
				//int colIndex = 0;
				//if(rowIndex < numSchemas) {
					//for(VennDiagramSets col : row) {
		
						
		this.vennDiagrams = new LinkedList<VennDiagramKNearestView>();
		this.listeners = new LinkedList<VennDiagramListener>();
		addMouseListener(this);
		//setBackground(Color.LIGHT_GRAY);
		
		setLayout(new BorderLayout());
		
		
		JPanel  toolbarPane = new JPanel();
		GridLayout g = new GridLayout(2, 0);
		toolbarPane.setLayout(g);
		
        String[] options = {"Bars", "Venn Diagram"};
        this.radioBox = new JComboBox(options);
        radioBox.setSelectedIndex(1);
        radioBox.addActionListener(this);
        toolbarPane.add(radioBox);
        
        
		//this.setBackground(Color.BLACK);
		//labeling the view
		String textForViewLabel = "Schema1 (versus Schema2)";
		if(matrix != null){
			textForViewLabel = this.matrix.getMatrixEntry(0, 0).getSchema1().getName() + " versus... ";
		}
		JLabel labelForView = new JLabel(textForViewLabel);	
		labelForView.setForeground(Color.blue);
        labelForView.setFont(new Font(getFont().getName(), Font.BOLD, 18));
        toolbarPane.add(labelForView);        
        add(toolbarPane, BorderLayout.NORTH);        
		
		if(showMinMaxSlider) {
			MinMaxSliderPane slider = new MinMaxSliderPane(JSlider.VERTICAL, 
					matrix.getMinMatchScoreThreshold(), matrix.getMaxMatchScoreThreshold(), false);
			slider.addMinMaxSliderListener(this);
			slider.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			add(slider, BorderLayout.EAST);			
		}		
		
		JPanel schemaPane = new JPanel();
		schemaPane.setLayout(new GridLayout(2, 0));
		add(schemaPane, BorderLayout.CENTER);		        
		
		JPanel matrixPane = new JPanel();
		//int numSchemas = matrix.getNumSchemas();
//		GridLayout gl = new GridLayout(0, numSchemas-1);
		GridLayout gl = new GridLayout(0, 3);
		gl.setHgap(2);
		gl.setVgap(2);		
		matrixPane.setLayout(gl);		
		
		int fontSize = 12;
		/*if(matrix.getNumSchemas() > 3){
			fontSize = 10;
		}else{
			fontSize = 14;
		}*/
		Font font = new Font(getFont().getName(), Font.PLAIN, fontSize);	
		//int rowIndex = 0;
		int countPos = 1;
		//for(ArrayList<VennDiagramSets> row : matrix) {
		for(ArrayList<VennDiagramSets> vdSet : matrix) {

			//int colIndex = 0;
			//if(rowIndex < numSchemas) {
				//for(VennDiagramSets col : row) {
				for(VennDiagramSets vd : vdSet){
					schemaAName = vd.getSchema1().getName();
					//schemaAName = col.getSchema1().getName();
					JPanel cell = null;
					//if(colIndex > rowIndex) {
						//VennDiagramView vennDiagram = new VennDiagramView(col, true, VennDiagramView.MouseOverSetsPosition.BOTTOM);
						//VennDiagramKNearestView vennDiagram = new VennDiagramKNearestView(vd, true, VennDiagramKNearestView.MouseOverSetsPosition.BOTTOM);
						VennDiagramKNearestView vennDiagram = new VennDiagramKNearestView(vd, false, VennDiagramKNearestView.MouseOverSetsPosition.BOTTOM, countPos, maxSchema2Size);
						vennDiagram.setFont(font);		
						
						vennDiagrams.add(vennDiagram);					
						cell = vennDiagram;		
						cell.setBackground(Color.WHITE);		
						cell.setBorder(cellBorder);
						cell.addMouseListener(this);

					//}
					
					if(cell != null) {
						//cell.setBackground(Color.WHITE);		
						//cell.setBorder(cellBorder);
						matrixPane.add(cell);
					}
				//	colIndex++;
					countPos++;
				}	
			//}
			
			//note that there's actually only 1 row in this view
			//rowIndex++;
		}
		

			
			
		matrixPane.setBorder(BorderFactory.createCompoundBorder(cellBorder, BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		//add(matrixPane, BorderLayout.CENTER);
		
		
		JScrollPane scrollpaneVennDiagrams = new JScrollPane(matrixPane);   
		//schemaPane.add(matrixPane);
		schemaPane.add(scrollpaneVennDiagrams);
		
		
		
		
		
		
		//adding in the details pane down the bottom
		JPanel detailsPane = new JPanel();
		GridLayout g3 = new GridLayout(0, 3);
		g3.setHgap(8);
		g3.setVgap(0);
		detailsPane.setLayout(g3);

		Font listFont = new Font(getFont().getName(), Font.PLAIN, 12);
		
			//CREATING UNIQUE TO A PANE
			JPanel uniqueToA_Pane = new JPanel();
	        uniqueToA_Pane.setLayout(new BoxLayout(uniqueToA_Pane, BoxLayout.PAGE_AXIS));
	    
	        this.uniqueAlistmodel = new DefaultListModel();
	        SchemaElement fakeTempElement = new SchemaElement();
	        fakeTempElement.setName("<Select Venn Diagram>");
	        this.uniqueAlistmodel.addElement(fakeTempElement);
	        
			this.uniqueAList = new JList(this.uniqueAlistmodel);
			this.uniqueAList.setFont(listFont);
			this.uniqueAList.addMouseListener(this);
			
	        labelA = new JLabel("<Schema A> Unique Elements");
	        JScrollPane scrollpaneA = new JScrollPane(uniqueAList);	      
	        uniqueToA_Pane.add(labelA);
	        uniqueToA_Pane.add(scrollpaneA);
	        uniqueToA_Pane.addMouseListener(this);
			detailsPane.add(uniqueToA_Pane);
	        
	        

			//CREATING MATCHED AB-A PANE
			JPanel matchedAB_APane = new JPanel();
	        matchedAB_APane.setLayout(new BoxLayout(matchedAB_APane, BoxLayout.PAGE_AXIS));
	    
			this.matchedABlistmodel = new DefaultListModel();
			this.matchedABList = new JList(this.matchedABlistmodel);
			this.matchedABList.setFont(listFont);
			this.matchedABList.setCellRenderer(new MatchedCellRenderer()); 
			this.matchedABList.addMouseListener(this);
			
	        JLabel labelAB = new JLabel("Matched Elements");			
	        JScrollPane scrollpaneAB = new JScrollPane(matchedABList);	      
	        matchedAB_APane.add(labelAB);
	        matchedAB_APane.add(scrollpaneAB);
	        matchedAB_APane.addMouseListener(this);
			detailsPane.add(matchedAB_APane);

			
			
			//CREATING UNIQUE TO B PANE
			JPanel uniqueToB_Pane = new JPanel();
	        uniqueToB_Pane.setLayout(new BoxLayout(uniqueToB_Pane, BoxLayout.PAGE_AXIS));
	    
			this.uniqueBlistmodel = new DefaultListModel();
			this.uniqueBlistmodel.addElement(fakeTempElement);
			this.uniqueBList = new JList(this.uniqueBlistmodel);
			this.uniqueBList.setFont(listFont);
			this.uniqueBList.addMouseListener(this);
			
	        labelB = new JLabel("<Schema B> Unique Elements");
	        JScrollPane scrollpaneB = new JScrollPane(uniqueBList);	      
	        uniqueToB_Pane.add(labelB);
	        uniqueToB_Pane.add(scrollpaneB);
	        uniqueToB_Pane.addMouseListener(this);
			detailsPane.add(uniqueToB_Pane);
			
			
			schemaPane.add(detailsPane);
	}
	
	//sorts row of venn diagrams in the matrix using jaccard's distance
	//basically orders the VD's 
	protected void sortRowByJaccards(){
		Comparator<VennDiagramSets> c = new Comparator<VennDiagramSets>(){
			//returns negative integer if vd1 > vd2
			//returns zero if vd1 = vd2
			//returns positive integer if vd1 < vd2
			public int compare(VennDiagramSets vd1, VennDiagramSets vd2) {
				double j1=	vd1.getJaccards(matrix.getMinMatchScoreThreshold());
				double j2 = vd2.getJaccards(matrix.getMinMatchScoreThreshold());
				if(j1 < j2){
					return 1;
				}else if(j1 == j2){
					return 0;
				}else{
					return -1;
				}
			}
			
		};
		matrix.sortMatrixBySets(c);
	}

	//sorts row by the number of unique elements in A
	//basically orders the VD's 
	protected void sortRowByUniqueA(){
		Comparator<VennDiagramSets> c = new Comparator<VennDiagramSets>(){
			//returns negative integer if vd1 > vd2
			//returns zero if vd1 = vd2
			//returns positive integer if vd1 < vd2
			public int compare(VennDiagramSets vd1, VennDiagramSets vd2) {				
				double s1=	vd1.getSchema1UniqueElements().size();
				double s2 = vd2.getSchema1UniqueElements().size();
				if(s1 > s2){
					return 1;
				}else if(s1 == s2){
					return 0;
				}else{
					return -1;
				}
			}
			
		};
		matrix.sortMatrixBySets(c);
	}

	public void addVennDiagramListener(VennDiagramListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeVennDiagramListener(VennDiagramListener listener) {
		this.listeners.remove(listener);
	}
	
	public VennDiagramSetsMatrix getMatrix() {
		return matrix;
	}
	
	protected void fireVennDiagramEvent(VennDiagramSets sets, int mouseX, int mouseY, int mouseButton, boolean controlDown) {
		if(!listeners.isEmpty()) {
			VennDiagramEvent event = new VennDiagramEvent(sets, mouseX, mouseY, mouseButton, controlDown);
			for(VennDiagramListener listener : listeners) {
				listener.vennDiagramSelected(event);
			}
		}
	}
	
	public void minMaxSliderMoved(double minValue, double maxValue) {
		VennDiagramKNearestView.closeMouseOverDialog();		
		//Update min/max thresholds in each Venn Diagram
		for(VennDiagramKNearestView vennDiagram : vennDiagrams){
			vennDiagram.getSets().setMatchScoreThresholdRange(minValue, maxValue);
		}
		if(selectedVennDiagram != null){
			updateList(selectedVennDiagram);		
		}
		repaint();
	}	

	
	//MouseListener methods
	public void mousePressed(MouseEvent ev) {
		Component clickedComponent = ev.getComponent();
		//System.out.println("component that was clicked: " + ev.toString());
		if(clickedComponent instanceof VennDiagramKNearestView) {
			//If a cell was clicked, highlight it and un-highlight any
			//previously highlighted cell
			if(selectedVennDiagram != null){
				selectedVennDiagram.setBorder(cellBorder);
				selectedVennDiagram.setBackground(Color.WHITE);
			}
			selectedVennDiagram = (VennDiagramKNearestView)clickedComponent;
			selectedVennDiagram.setBorder(selectedBorder);
			selectedVennDiagram.setBackground(new Color(255, 255, 153));
			
			labelA.setText(selectedVennDiagram.getSets().getSchema1().getName() + " Unique Elements");
			labelA.setToolTipText(selectedVennDiagram.getSets().getSchema1().getDescription());
			labelB.setText(selectedVennDiagram.getSets().getSchema2().getName() +" Unique Elements");
			labelB.setToolTipText(selectedVennDiagram.getSets().getSchema2().getDescription());
			//labelA.repaint();
			//labelB.repaint();
			updateList(selectedVennDiagram);

			//Notify any listeners that a venn diagram was selected
			if(!listeners.isEmpty()) {				
				fireVennDiagramEvent(selectedVennDiagram.getSets(), ev.getX(), ev.getY(), ev.getButton(),
						ev.isControlDown());
			}
		} else if(clickedComponent instanceof JList){
			//System.out.println("Clicked a JList");
			JTextPane jtp = new JTextPane();
			StyledDocument sd = jtp.getStyledDocument();
			
			//add styles
			Style reg = sd.addStyle("normal", StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE));
			Style bold = sd.addStyle("bold", reg);
			StyleConstants.setBold(bold, true);
			
			
			if(clickedComponent == this.uniqueAList || clickedComponent == this.uniqueBList){
				JDialog dlg = new JDialog();
				dlg.setAlwaysOnTop(true);
				dlg.setResizable(true);
				dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				
				int index;
				SchemaElement se;
				String forName;
				String forLabel;
				
				if(clickedComponent == this.uniqueAList){
					index = this.uniqueAList.locationToIndex(ev.getPoint());
					se = (SchemaElement)this.uniqueAlistmodel.getElementAt(index);
					forName = se.getName();
					forLabel = se.getDescription();
					//dlg.setLocationRelativeTo(uniqueAList);
				}else{
					//clickedComponent == this.uniqueBList
					index = this.uniqueBList.locationToIndex(ev.getPoint());
					se = (SchemaElement)this.uniqueBlistmodel.getElementAt(index);
					forName = se.getName();
					forLabel = se.getDescription();
					//dlg.setLocationRelativeTo(uniqueBList);
				}
				//JLabel label = new JLabel(" " + forLabel + " ");
				
				//trying to make sure it all fits nicely in the dialog box
				/*Graphics g = this.getGraphics();
				FontMetrics fontMetrics = g.getFontMetrics();
				int fontHeight = fontMetrics.getAscent();
				Rectangle2D textSize = fontMetrics.getStringBounds(" " + forLabel + " ", g);
				if(textSize.getWidth() > dlg.getWidth()){
					
				}*/
				
				//label.setOpaque(true);
				//label.setBackground(Color.WHITE);
				
				try {
					sd.insertString(sd.getLength(), forName + ": ", bold);
					sd.insertString(sd.getLength(), forLabel, reg);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
	
				//dlg.setContentPane(label);
				JScrollPane jsp = new JScrollPane(jtp);
				dlg.setContentPane(jsp);
				
				dlg.setTitle(forName);
				dlg.setLocation(ev.getXOnScreen(), ev.getYOnScreen());
				
				dlg.pack();
				dlg.toFront();
				dlg.setVisible(true);				
			} else if(clickedComponent == this.matchedABList){
				JDialog dlg = new JDialog();
				dlg.setAlwaysOnTop(true);
				dlg.setResizable(true);
				dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				
				int index = this.matchedABList.locationToIndex(ev.getPoint());
				MatchedSchemaElements mses = (MatchedSchemaElements)this.matchedABlistmodel.getElementAt(index);

				Integer s1 = selectedVennDiagram.getSets().getSchema1().getId();
				Integer s2 = selectedVennDiagram.getSets().getSchema2().getId();
				
				/*JLabel label1 =	new JLabel(" " + mses.getMatchedElement(s1).getName() + ": " +
					mses.getMatchedElement(s1).getDescription() + " ");
				label1.setOpaque(true);
				label1.setBackground(Color.WHITE);
				
				JLabel label2 = new JLabel(" " + mses.getMatchedElement(s2).getName() + ": " +
					mses.getMatchedElement(s2).getDescription() + " ");
				label2.setOpaque(true);
				label2.setBackground(Color.WHITE);
				
				JPanel pane = new JPanel();
				pane.setLayout(new GridLayout(2, 0));
				pane.add(label1);
				pane.add(label2);
				
				String forName = mses.getMatchedElement(s1).getName() + " - " + mses.getMatchedElement(s2).getName();
			
				dlg.setContentPane(pane);
				dlg.setTitle(forName);
				*/
					try {
						sd.insertString(sd.getLength(), mses.getMatchedElement(s1).getName() + ": ", bold);
						sd.insertString(sd.getLength(), mses.getMatchedElement(s1).getDescription() + " \n", reg);
						sd.insertString(sd.getLength(), mses.getMatchedElement(s2).getName() + ": ", bold);
						sd.insertString(sd.getLength(), mses.getMatchedElement(s2).getDescription(), reg);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}

				
				/*jtp.setText(mses.getMatchedElement(s1).getName() + ": " +
							mses.getMatchedElement(s1).getDescription() + " \n \n" +
							mses.getMatchedElement(s2).getName() + ": " +
							mses.getMatchedElement(s2).getDescription()
							);
				*/
					
				//dlg.setContentPane(jtp);
				JScrollPane jsp = new JScrollPane(jtp);
				dlg.setContentPane(jsp);
				
				dlg.setLocation(ev.getXOnScreen(), ev.getYOnScreen());
		
				//dlg.setLocationRelativeTo(matchedABList);
				//dlg.setPreferredSize(new Dimension(100, 50));
				dlg.pack();
				dlg.toFront();
				dlg.setVisible(true);
				
			}	
		}
	}
	
	private void updateList(VennDiagramKNearestView selectedVennDiagram) {		
		//update list for SchemaA Unique elements
		Set<SchemaElement> curr_seA = selectedVennDiagram.getSets().getSchema1UniqueElements();
		
		this.uniqueAlistmodel.removeAllElements();
		
		for(SchemaElement e : curr_seA){
			this.uniqueAlistmodel.addElement(e);
		}
		uniqueAList.repaint();		
		
		//update list for Matching Elements
		List<MatchedSchemaElements> seAB = selectedVennDiagram.getSets().getIntersectElements();
		this.matchedABlistmodel.removeAllElements();
		
		//Integer schema1ID = selectedVennDiagram.getSets().getSchema1().getId();
		//Integer schema2ID = selectedVennDiagram.getSets().getSchema2().getId();
		for(MatchedSchemaElements e: seAB){
			this.matchedABlistmodel.addElement(e);
		}
		matchedABList.repaint();		
		
		//update list for Schema B Unique Elements
		Set<SchemaElement> seB = selectedVennDiagram.getSets().getSchema2UniqueElements();
		this.uniqueBlistmodel.removeAllElements();
		for(SchemaElement e : seB){
			this.uniqueBlistmodel.addElement(e);
		}
		uniqueBList.repaint();
	}
	
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}	
	
	public static class VennDiagramMatrixCell {
		public VennDiagramKNearestView vennDiagram;
		public boolean isSelected;		
	}

	public void actionPerformed(ActionEvent e) {
		String selectedItem = (String) this.radioBox.getSelectedItem();
		//System.out.println("selected: " + this.radioBox.getSelectedItem());
	
		//Update min/max thresholds in each Venn Diagram
		for(VennDiagramKNearestView vennDiagram : vennDiagrams){
			vennDiagram.setViewType(selectedItem);
			vennDiagram.repaint();
		}			
	}	
	
	class MatchedCellRenderer extends JLabel implements ListCellRenderer {
		
		private static final long serialVersionUID = 1L;

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        MatchedSchemaElements mses = (MatchedSchemaElements)value;
			Integer s1 = selectedVennDiagram.getSets().getSchema1().getId();
			Integer s2 = selectedVennDiagram.getSets().getSchema2().getId();
			String combo = 
				mses.getMatchedElement(s1).getName() + " - " +
				mses.getMatchedElement(s2).getName();
	        setText(combo);     
	        
		   	setFont(list.getFont());
		   	
	        setOpaque(true);
	        
	      
		   	if(isSelected){
		   		setBackground(list.getSelectionBackground());
		   		setForeground(list.getSelectionForeground());
		   	}else{
		   		setBackground(list.getBackground());
		   		setForeground(list.getForeground());
		   	}

	        return this;
	    }	    
	}
}