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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;

import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSetsMatrix;
import org.mitre.affinity.view.venn_diagram.view.event.VennDiagramEvent;
import org.mitre.affinity.view.venn_diagram.view.event.VennDiagramListener;
import org.mitre.affinity.view.venn_diagram.view.swing.VennDiagramPane.VennDiagramView;
import org.mitre.harmony.matchers.MatchGenerator;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;

/**
 * A grid of Venn Diagrams for each pair of schemas.
 * 
 * @author CBONACETO
 *
 */
public class VennDiagramMatrixPane extends JPanel implements MinMaxSliderListener, MouseListener {
	private static final long serialVersionUID = 1L;
	
	private VennDiagramSetsMatrix matrix;
	private List<VennDiagramView> vennDiagrams;
	
	/** Currently selected venn diagram matrix cell */
	private VennDiagramView selectedVennDiagram;
	
	/** Listeners that have registered to receive venn diagram events from this component */
	private List<VennDiagramListener> listeners;
	
	private Border cellBorder = BorderFactory.createLineBorder(Color.BLACK);
	private Border selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 3);
	
	/** Whether or not schema names are labeled on row/column headings */
	//private boolean labelRowsAndColumns = true;
	
	/** Whether or not schema names are labeled in individual venn diagram cells */
	//private boolean labelCells = true;
	
	public VennDiagramMatrixPane(List<FilteredSchemaInfo> schemaInfos,	double minMatchScoreThreshold, 
			double maxMatchScoreThreshold, MatchGenerator matchScoreComputer, boolean showMinMaxSlider) {
		this(new VennDiagramSetsMatrix(schemaInfos, minMatchScoreThreshold, 
				maxMatchScoreThreshold, matchScoreComputer), true);
	}
	
	public VennDiagramMatrixPane(VennDiagramSetsMatrix matrix, boolean showMinMaxSlider) {
		super();
		this.matrix = matrix;
		this.vennDiagrams = new LinkedList<VennDiagramView>();
		this.listeners = new LinkedList<VennDiagramListener>();
		addMouseListener(this);
		//setBackground(Color.LIGHT_GRAY);
		
		setLayout(new BorderLayout());
		if(showMinMaxSlider) {
			MinMaxSliderPane slider = new MinMaxSliderPane(JSlider.VERTICAL, 
					matrix.getMinMatchScoreThreshold(), matrix.getMaxMatchScoreThreshold(), false);
			slider.addMinMaxSliderListener(this);
			slider.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
			add(slider, BorderLayout.EAST);			
		}
		
		JPanel schemaPane = new JPanel();
		add(schemaPane, BorderLayout.WEST);
		
		
		JPanel matrixPane = new JPanel();
		int numSchemas = matrix.getNumSchemas();
		GridLayout gl = new GridLayout(0, numSchemas-1);
		gl.setHgap(2);
		gl.setVgap(2);		
		matrixPane.setLayout(gl);		
		
		int fontSize = 12;
		if(matrix.getNumSchemas() > 3)
			fontSize = 10;
		fontSize = 14;
		Font font = new Font(getFont().getName(), Font.PLAIN, fontSize);	
		int rowIndex = 0;
		for(ArrayList<VennDiagramSets> row : matrix) {
			int colIndex = 0;
			if(rowIndex < numSchemas - 1) {
				for(VennDiagramSets col : row) {	
					JPanel cell = null;
					if(colIndex > rowIndex) {
						VennDiagramView vennDiagram = new VennDiagramView(col, true, VennDiagramView.MouseOverSetsPosition.BOTTOM);
						vennDiagram.setFont(font);						
						vennDiagrams.add(vennDiagram);					
						cell = vennDiagram;		
						cell.setBackground(Color.WHITE);		
						cell.setBorder(cellBorder);
						cell.addMouseListener(this);
					}
					else if(colIndex < rowIndex) {
						cell = new JPanel();
						cell.setBackground(getBackground());
					}
					if(cell != null) {
						//cell.setBackground(Color.WHITE);		
						//cell.setBorder(cellBorder);
						matrixPane.add(cell);
					}
					colIndex++;
				}	
			}
			rowIndex++;
		}
		matrixPane.setBorder(BorderFactory.createCompoundBorder(cellBorder, BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		add(matrixPane, BorderLayout.CENTER);
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
		VennDiagramView.closeMouseOverDialog();		
		//Update min/max thresholds in each Venn Diagram
		for(VennDiagramView vennDiagram : vennDiagrams)
			vennDiagram.getSets().setMatchScoreThresholdRange(minValue, maxValue);	
		repaint();
	}	

	//MouseListener methods
	public void mousePressed(MouseEvent ev) {
		Component clickedComponent = ev.getComponent();
		//System.out.println("component that was clicked: " + ev.toString());
		if(clickedComponent instanceof VennDiagramView) {
			//If a cell was clicked, highlight it and un-highlight any
			//previously highlighted cell
			if(selectedVennDiagram != null)
				selectedVennDiagram.setBorder(cellBorder);
			selectedVennDiagram = (VennDiagramView)clickedComponent;
			selectedVennDiagram.setBorder(selectedBorder);
			
			//Notify any listeners that a venn diagram was selected
			if(!listeners.isEmpty()) {				
				fireVennDiagramEvent(selectedVennDiagram.getSets(), ev.getX(), ev.getY(), ev.getButton(),
						ev.isControlDown());
			}
		}
	}
	
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	
	public static class VennDiagramMatrixCell {
		public VennDiagramView vennDiagram;
		public boolean isSelected;
		
	}
}
