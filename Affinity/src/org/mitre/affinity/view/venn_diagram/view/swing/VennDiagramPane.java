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

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets.MatchedSchemaElements;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/**
 * Venn diagram pane contains a Venn diagram view of the set relationship between matched elements in two schemas
 * and an optional min-max slider to change the min-max match score used to compute the venn diagram sets.
 * 
 * @author CBONACETO
 *
 */
public class VennDiagramPane extends JPanel implements MinMaxSliderListener {
	private static final long serialVersionUID = 1L;
	
	private VennDiagramView vennDiagram;
		
	public VennDiagramPane(VennDiagramSets sets) { 
		this(sets, false);
	}
	
	public VennDiagramPane(VennDiagramSets sets, boolean showMinMaxSlider) { 
		super();
		setLayout(new BorderLayout());
		if(showMinMaxSlider) {
			MinMaxSliderPane slider = new MinMaxSliderPane(JSlider.VERTICAL, 
					sets.getMinMatchScoreThreshold(), sets.getMaxMatchScoreThreshold(), false);
			slider.addMinMaxSliderListener(this);
			slider.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
			add(slider, BorderLayout.EAST);			
		}

		
		this.vennDiagram = new VennDiagramView(sets);
		vennDiagram.setFont(new Font(getFont().getName(), Font.BOLD, 12));
		vennDiagram.setMouseOverSetsEnabled(true);
		vennDiagram.setBackground(Color.WHITE);		
		add(vennDiagram, BorderLayout.CENTER);		
	}
	
	public void minMaxSliderMoved(double minValue, double maxValue) {
		//System.out.println("new slider values: " + minValue + ", " + maxValue);
		VennDiagramView.closeMouseOverDialog();
		vennDiagram.sets.setMatchScoreThresholdRange(minValue, maxValue);
		vennDiagram.repaint();
	}
	

	
	
	/**
	 * Venn diagram view of the set relationship between matched elements in two schemas.
	 * 
	 * @author CBONACETO
	 *
	 */
	public static class VennDiagramView extends JPanel implements MouseMotionListener, MouseListener {
		private static final long serialVersionUID = 1L;
		
		public static enum MouseOverSetsPosition {TOP, CENTER, BOTTOM};

		private VennDiagramSets sets;

		private Circle circle1;
		private Circle circle2;

		private Color[] lineColors;
		private Color[] fillColors;
		
		private int combinedWidth;

		/** Indicates which set mouse is currently over (1 = schema1, 2 = schema2, 3 = intersection) */
		private int mouseOverSet = -1;
		
		/** Whether or not the pop-up showing set elements is displayed as the user mouses over sets
		 * (default is false) */
		private boolean mouseOverSetsEnabled;
		
		/** Position to display the pop-up dialog when the user mouses over a set, 
		 * one of TOP, CENTER, or BOTTOM (default is CENTER) */
		private MouseOverSetsPosition mouseOverSetsPosition;

		//if set to 1 then this is a venn diagram of elements, if 2 then it shows entities
		private int entityOrElement;
		
		public VennDiagramView(VennDiagramSets sets) {	
			this(sets, false, MouseOverSetsPosition.CENTER);
		}
			
		public VennDiagramView(VennDiagramSets sets, boolean enableMouseOverSets, MouseOverSetsPosition mouseOverSetsPosition) {
			super(true);	
			this.entityOrElement = sets.getEntityOrElementVal();
			this.sets = sets;
			this.mouseOverSetsEnabled = enableMouseOverSets;
			this.mouseOverSetsPosition = mouseOverSetsPosition;
			this.circle1 = new Circle(0, 0, 1);
			this.circle2 = new Circle(0, 0, 1);
			this.lineColors = new Color[2];		
			lineColors[0] = Color.BLUE;
			lineColors[1] = Color.RED;
			this.fillColors = new Color[2];
			fillColors[0] = lineColors[0];
			fillColors[1] = lineColors[1];				

			this.addAncestorListener(new AncestorListener() {
				public void ancestorAdded(AncestorEvent arg0) {}
				public void ancestorMoved(AncestorEvent arg0) {}
				public void ancestorRemoved(AncestorEvent arg0) {
					if(dialog != null)
						dialog.dispose();
				}				
			});
			
			//Add mouse listeners
			this.addMouseMotionListener(this);
			this.addMouseListener(this);
		}

		public VennDiagramSets getSets() {
			return sets;
		}

		public void setSets(VennDiagramSets sets) {
			this.sets = sets;
		}

		public boolean isMouseOverSetsEnabled() {
			return mouseOverSetsEnabled;
		}

		public void setMouseOverSetsEnabled(boolean mouseOverSetsEnabled) {
			this.mouseOverSetsEnabled = mouseOverSetsEnabled;
			if(dialog != null) 
				dialog.dispose();
		}
		
		public static void closeMouseOverDialog() {
			if(dialog != null) 
				dialog.dispose();
		}

		public Color getLineColor(int circleNum) {
			return lineColors[circleNum];
		}

		public void setLineColor(Color lineColor, int circleNum) {
			lineColors[circleNum] = lineColor;
		}

		public Color getFillColor(int circleNum) {
			return fillColors[circleNum];
		}

		public void setFillColor(Color fillColor, int circleNum) {
			fillColors[circleNum] = fillColor;
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;

			//Render the background
			super.paintComponent(g2d);
			Insets insets = this.getInsets();
			Rectangle bounds = new Rectangle(insets.left + 1, insets.top + 1, getWidth() - insets.left - insets.right - 2,
					getHeight() - insets.top - insets.bottom - 2);

			g2d.setFont(getFont());
			FontMetrics fontMetrics = g2d.getFontMetrics();
			int fontHeight = fontMetrics.getAscent();

			//Compute diameter of circles representing schemas 1 and 2 such that the areas
			//are proportional to the number of elements in each schema
			int numElements1 = sets.getSchema1AllElements().size();
			int numElements2 = sets.getSchema2AllElements().size();
			double ratio = Math.sqrt((double)numElements1/numElements2);
			int diameter2 = (int)(bounds.width/(ratio+1));
			int diameter1 = (int)(ratio * diameter2);
			if(diameter1 >= diameter2) {
				if(diameter1 > bounds.height - fontHeight*2) {
					diameter1 = bounds.height - fontHeight*2;
					diameter2 = (int)(diameter1/ratio);
				}
			}
			else {
				if(diameter2 > bounds.height - fontHeight*2) {
					diameter2 = bounds.height - fontHeight*2;
					diameter1 = (int)(ratio * diameter2);
				}
			}
			circle1.diameter = diameter1;
			circle2.diameter = diameter2;

			//Position and render the circles
			int yPos = bounds.height/2;
			circle1.y = yPos;
			circle2.y = yPos;
			if(sets.getSchema1UniqueElements().isEmpty() || sets.getSchema2UniqueElements().isEmpty()) {
				//one circle is completely contained in the other (schemas are identical or have subset/superset relationship)
				//center both circles
				int xPos = bounds.width/2 + bounds.x;
				circle1.x = xPos;
				circle2.x = xPos;
				if(circle1.diameter > circle2.diameter)
					this.combinedWidth = circle1.diameter;
				else
					this.combinedWidth = circle2.diameter;
			}
			else if(!sets.getIntersectElements().isEmpty()) {
				//the circles overlap (schemas share some common elements)
				//Get the size of the intersect set and compute degree of overlap
				int numIntersectElements = sets.getNumIntersectElements();			
				int overlap = 0;
				if(numElements1 > numElements2) 
					//Overlap circle2 over circle1
					overlap = (int)((float)numIntersectElements/numElements2 * diameter2);				
				else 
					//Overlap circle1 over circle2
					overlap = (int)((float)numIntersectElements/numElements1 * diameter1);								
				this.combinedWidth = diameter1 + diameter2 - overlap;
				int remainder = (bounds.width - combinedWidth)/2;
				circle1.x = remainder + circle1.diameter/2 + bounds.x;				
				circle2.x = bounds.width - remainder - circle2.diameter/2;
			}
			else {		
				//the circles do not overlap (schemas share no common elements)
				this.combinedWidth = circle1.diameter + circle2.diameter;
				int remainder = (bounds.width - combinedWidth)/2;
				circle1.x = remainder + circle1.diameter/2 + bounds.x;
				circle2.x = circle1.x + circle1.diameter/2 + circle2.diameter/2 + 1;
			}

			//Draw venn circles
			g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f)); 

			g2d.setColor(fillColors[0]);
			//g2d.setBackground(fillColors[0]);
			//g2d.setColor(lineColors[0]);
			g2d.fillOval(circle1.getUpperLeftX(), circle1.getUpperLeftY(), circle1.diameter, circle1.diameter);
			g2d.drawOval(circle1.getUpperLeftX(), circle1.getUpperLeftY(), circle1.diameter, circle1.diameter);

			g2d.setColor(fillColors[1]);
			//g2d.setBackground(fillColors[1]);				
			//g2d.setColor(lineColors[1]);
			g2d.fillOval(circle2.getUpperLeftX(), circle2.getUpperLeftY(), circle2.diameter, circle2.diameter);
			g2d.drawOval(circle2.getUpperLeftX(), circle2.getUpperLeftY(), circle2.diameter, circle2.diameter);		

			//Label circles
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.f)); 
				

			String name = sets.getSchema1().getName();
			if(name != null && !name.isEmpty()) {
				g2d.setColor(lineColors[0]);	
				Rectangle2D strSize = fontMetrics.getStringBounds(name, g2d);	
				float xPos =  (float)(circle1.x - strSize.getWidth()/2);
				float overAmt = (float)((xPos + strSize.getWidth()) - (bounds.x + bounds.width));
				if(overAmt > 0)	xPos -= overAmt;
				if(xPos < bounds.x) xPos = bounds.x;
				g2d.drawString(name, xPos, 
						(float)(circle1.y - circle1.diameter/2 + strSize.getHeight()/2 - 1));
			}
			
			name = sets.getSchema2().getName();
			if(name != null && !name.isEmpty()) {
				g2d.setColor(lineColors[1]);
				Rectangle2D strSize = fontMetrics.getStringBounds(name, g2d);
				float xPos =  (float)((circle2.x - strSize.getWidth()/2));
				float overAmt = (float)((xPos + strSize.getWidth()) - (bounds.x + bounds.width));
				if(overAmt > 0)	xPos -= overAmt;	
				if(xPos < bounds.x) xPos = bounds.x;
				g2d.drawString(name, xPos, 
						(float)(circle2.y + circle2.diameter/2 + strSize.getHeight()/2 - 3));			
			}			
		}

		private static JDialog dialog = null;

		boolean inHoverThread = false;
		Thread hoverThread;
		long hoverStartTime;
		MouseEvent currMouseEvent;

		//MouseMotionListener methods
		public void mouseDragged(MouseEvent e) {}	
		public void mouseMoved(MouseEvent e) {
			if(!mouseOverSetsEnabled) return;
			
			//if(dialog != null && dialog.isVisible()) {
			//	hoverStartTime = System.currentTimeMillis();
			//	mouseHovered(e);
			//}
			//else {
				if(!inHoverThread) {
					inHoverThread = true;
					currMouseEvent = e;
					Thread hoverThread = new Thread() {
						public void run() {
							hoverStartTime = System.currentTimeMillis();
							while((System.currentTimeMillis() - hoverStartTime) < 750) {
								//System.out.println("in thread: " + System.currentTimeMillis());
								try {
									Thread.sleep(50);
								} catch(InterruptedException ex) {}	
							}
							inHoverThread = false;
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {													
									mouseHovered(currMouseEvent);
								}						
							});					
						}				
					};
					hoverThread.setDaemon(true);
					hoverThread.setPriority(Thread.MIN_PRIORITY);
					hoverThread.start();
				}	
				else {
					hoverStartTime = System.currentTimeMillis();
					currMouseEvent = e;
				}
			//}
		}

		/** Called when the mouse has been hovering over a spot for a time >= hoverTimeThreshold */
		protected void mouseHovered(MouseEvent e) {			
			int overSet = -1;
			int x = e.getX(); int y = e.getY();
			if(circle1.contains(x, y)) {
				if(circle2.contains(x, y))
					overSet = 3;
				else
					overSet = 1;
			}
			else if(circle2.contains(x, y))
				overSet = 2;

			if(overSet != this.mouseOverSet || (dialog == null || !dialog.isVisible())) {			
				this.mouseOverSet = overSet;

				/*
				//This closes the dialog when the user mouses off a set
				if(dialog != null && dialog.isVisible()) {
					if(!dialog.getBounds().contains(x, y))
						dialog.dispose();
					return;
				}*/

				if(mouseOverSet != -1) {	
					if(dialog != null)
						dialog.dispose();
					dialog = new JDialog();
					dialog.setAlwaysOnTop(true);
					dialog.setResizable(false);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					/*
					dialog.addMouseListener(new MouseAdapter() {						
						public void mouseExited(MouseEvent arg0) {
							dialog.dispose();
						}						
					});*/	
					//dialog = new JInternalFrame("", false, true, false, false);	
					//dialog.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));				
				}
				else
					return;

				//Show appropriate dialog depending on which set the mouse is over
				switch(mouseOverSet) {
				case(1):
				//mouse is over set 1		
				String schemaName = sets.getSchema1().getName();
				
				JPanel pane;
				int sizeOfSet;
				if(entityOrElement == 1){
					sizeOfSet = sets.getSchema1UniqueElements().size();
					pane = new ElementsPane(sizeOfSet + " " + schemaName + " Unmatched Elements", fillColors[0], sets.getSchema1UniqueElements(),
						new Dimension(circle1.diameter, circle1.diameter/4 + (getHeight()-circle1.diameter)/2), entityOrElement, sets.getSchemaInfo1());
				}else{
					sizeOfSet = sets.getSchema1UniqueElements().size();
					//pane = new ElementsPane(sizeOfSet + " " + schemaName + " Unmatched Entities", fillColors[0], sets.getSchema1UniqueEntities(),
					pane = new ElementsPane(sizeOfSet + " " + schemaName + " Unmatched Entities", fillColors[0], sets.getSchema1UniqueElements(),
						new Dimension(circle1.diameter, circle1.diameter/4 + (getHeight()-circle1.diameter)/2), entityOrElement, sets.getSchemaInfo1());
				}
				//dialog.setTitle(schemaName + " Unmatched Elements");
				dialog.getContentPane().add(pane);						
				dialog.pack();				
				int xPos = circle1.x - dialog.getWidth()/2;
				//if(xPos + dialog.getWidth() > getWidth())
				//xPos = getWidth() - dialog.getWidth();
				int yPos = 0;
				//if(yPos + dialog.getHeight() > getHeight()) 
				//yPos = getHeight() - dialog.getHeight();
				if(mouseOverSetsPosition == MouseOverSetsPosition.CENTER)				
					yPos = circle1.y + circle1.diameter/4;
				else if(mouseOverSetsPosition == MouseOverSetsPosition.BOTTOM)
					yPos = getHeight() - 5;				
				Point p = new Point(xPos, yPos);
				SwingUtilities.convertPointToScreen(p, this);
				dialog.setLocation(p);
				break;
				case(2):
				//mouse is over set 2
				schemaName = sets.getSchema2().getName();

				if(entityOrElement == 1){
					sizeOfSet = sets.getSchema2UniqueElements().size();
					pane = new ElementsPane(sizeOfSet + " " + schemaName + " Unmatched Elements", fillColors[1], sets.getSchema2UniqueElements(),
						new Dimension(circle2.diameter, circle2.diameter/4 + (getHeight()-circle2.diameter)/2), entityOrElement, sets.getSchemaInfo2());
				}else{
					sizeOfSet = sets.getSchema2UniqueElements().size();
					pane = new ElementsPane(sizeOfSet + " " + schemaName + " Unmatched Entities", fillColors[1], sets.getSchema2UniqueElements(),
							new Dimension(circle2.diameter, circle2.diameter/4 + (getHeight()-circle2.diameter)/2), entityOrElement, sets.getSchemaInfo2());
				}
				//dialog.setTitle(schemaName + " Unmatched Elements");
				dialog.getContentPane().add(pane);						
				dialog.pack();
				xPos = circle2.x - dialog.getWidth()/2;
				yPos = 0;
				if(mouseOverSetsPosition == MouseOverSetsPosition.CENTER)				
					yPos = circle2.y + circle2.diameter/4;
				else if(mouseOverSetsPosition == MouseOverSetsPosition.BOTTOM)
					yPos = getHeight() - 5;
				p = new Point(xPos, yPos);
				SwingUtilities.convertPointToScreen(p, this);
				dialog.setLocation(p);			
				break;
				case(3):
					//mouse is over intersection				
					pane = new MatchedElementsPane(sets.getSchema1(), sets.getSchema2(), sets.getIntersectElements(),
							new Dimension(getWidth(), getHeight() - circle1.y), sets.getSchemaInfo1(), sets.getSchemaInfo2());				
				dialog.getContentPane().add(pane);			
				dialog.pack();
				xPos = (getWidth() - combinedWidth)/2 + combinedWidth/2 - dialog.getWidth()/2;
				yPos = 0;
				if(mouseOverSetsPosition == MouseOverSetsPosition.CENTER)				
					yPos = circle1.y + 3;
				else if(mouseOverSetsPosition == MouseOverSetsPosition.BOTTOM)
					yPos = getHeight() - 5;
				p = new Point(xPos, yPos);
				SwingUtilities.convertPointToScreen(p, this);
				dialog.setLocation(p);
				break;
				}			
				//add(dialog);
				dialog.pack();
				dialog.toFront();
				dialog.setVisible(true);
			}
		}
		
		//MouseListener methods
		public void mouseClicked(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {
			if(dialog != null) {				
				Point mousePoint = arg0.getPoint();
				SwingUtilities.convertPointToScreen(mousePoint, this);				
				Rectangle bounds = dialog.getBounds();
				if(mousePoint.x < bounds.x || mousePoint.x > bounds.x + bounds.width
						|| mousePoint.y < bounds.y - 30 || mousePoint.y > bounds.y + bounds.height) {
					dialog.dispose();
				}
			}
			/*if(dialog != null) {
				if(!dialog.getBounds().contains(SwingUtilities.convertPoint(this, arg0.getX(), arg0.getY(), dialog)));
					dialog.dispose();
			}*/
			/*if(dialog != null && dialog.isVisible()) 
				dialog.dispose();*/
		}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {
		}

		public static class Circle {
			/** x coordinate of the center of the circle */
			public int x;

			/** y coordinate of the center of the circle */
			public int y;

			/** diameter of the circle */
			public int diameter;

			public Circle(int x, int y, int diameter) {
				this.x = x;
				this.y = y;
				this.diameter = diameter;
			}

			/** 
			 * @param x
			 * @param y
			 * @return true if the circle contains the point (x,y)
			 */
			public boolean contains(int x, int y) {			
				if (computeDistance(x, y, this.x, this.y) <= diameter/2)
					return true;
				return false;
			}

			/** Computes the Euclidean distance between two points */
			public static double computeDistance(float x1, float y1, float x2, float y2) {
				return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
			}

			public int getX() {
				return x;
			}

			public int getUpperLeftX() {
				return x - diameter/2;
			}

			public void setX(int x) {
				this.x = x;
			}

			public int getY() {
				return y;
			}

			public int getUpperLeftY() {
				return y - diameter/2;
			}

			public void setY(int y) {
				this.y = y;
			}

			public int getDiameter() {
				return diameter;
			}

			public void setDiameter(int diameter) {
				this.diameter = diameter;
			}
		}

		public static class ElementsPane extends JPanel {
			private static final long serialVersionUID = 1L;

			public ElementsPane(String title, Color titleColor, Collection<SchemaElement> schemaElements, Dimension maxSize, int entityOrElement, SchemaInfo schemasInfo) {
				super();			
				createPane(title, titleColor, schemaElements, maxSize, entityOrElement, schemasInfo);
			}	

			private void createPane(String title, Color titleColor, Collection<SchemaElement> schemaElements, Dimension maxSize, int entityOrElement, SchemaInfo schemasInfo) {	
				Box box = Box.createVerticalBox();
				JLabel titleLabel = new JLabel(title);		
				titleLabel.setForeground(titleColor);
				box.add(titleLabel);

				String[][] rowData = new String[schemaElements.size()][2];
				int row = 0;
				
				if(entityOrElement == 1){
					//showing elements
					for(SchemaElement element : schemaElements) {
						rowData[row][0] = element.getName();				
						rowData[row][1] = element.getClass().getSimpleName();
						row++;
					}
				}else{
					//showing entities
					for(SchemaElement element : schemaElements) {
						//rowData[row][0] = element.getName();			
						rowData[row][0] = schemasInfo.getDisplayName(element.getId());
						rowData[row][1] = element.getClass().getSimpleName();
						row++;
					}
					
				}
				JTable elementsTable = new JTable(rowData, new String[] {"Name", "Type"});			
				elementsTable.setShowGrid(true);
				for (int i = 0; i < elementsTable.getColumnCount(); i++) 
					elementsTable.getColumnModel().getColumn(i).sizeWidthToFit();
				elementsTable.doLayout();
				JScrollPane scrollPane = new JScrollPane(elementsTable);
				elementsTable.setFillsViewportHeight(false);

				int width = elementsTable.getPreferredSize().width;
				//if(width > maxSize.width) width = maxSize.width;
				int height = elementsTable.getPreferredSize().height;
				//if(height > maxSize.height) height = maxSize.height;	
				if(height > maxSize.height) height = (int)(height/1.4);
				//scrollPane.setPreferredSize(new Dimension(width, height));
				scrollPane.setPreferredSize(new Dimension(width, height + 20));
				
				box.add(scrollPane);	
				this.add(box);			
			}
		}

		public class MatchedElementsPane extends JPanel {
			private static final long serialVersionUID = 1L;

			public MatchedElementsPane(Schema s1, Schema s2, Collection<MatchedSchemaElements> intersectElements,
					Dimension prefferedSize, SchemaInfo s1Info, SchemaInfo s2Info) {
				super();
				createPane(s1, s2, intersectElements, prefferedSize, s1Info, s2Info);
			}	

			private void createPane(Schema s1, Schema s2, Collection<MatchedSchemaElements> intersectElements,
					Dimension prefferedSize, SchemaInfo s1Info, SchemaInfo s2Info) {
				Box box = Box.createVerticalBox();

				JLabel titleLabel;
				
				//total number of elements
				//minus the total number of unique elements
				int sizeA = sets.getSchema1AllElements().size();
				int sizeB = sets.getSchema2AllElements().size();
				int AplusBsize = sizeA + sizeB;
				int numNonMatchingElementsA = sets.getSchema1UniqueElements().size();
				int numNonMatchingElementsB = sets.getSchema2UniqueElements().size();
				int UniqueAplusUniqueB = numNonMatchingElementsA + numNonMatchingElementsB;
				int numMatches= AplusBsize-UniqueAplusUniqueB;
				
				if(entityOrElement == 1){
					//titleLabel = new JLabel(intersectElements.size() + " Matched Elements");
					titleLabel = new JLabel(numMatches + " Matched Elements");
				}else{
					//titleLabel = new JLabel(intersectElements.size() + " Matched Entities");
					titleLabel = new JLabel(numMatches + " Matched Entities");
				}
				box.add(titleLabel);

				String[][] rowData = new String[intersectElements.size()][5];
				int row = 0;
				for(MatchedSchemaElements matchedElements : intersectElements) {
					SchemaElement element1 = matchedElements.getMatchedElement(s1.getId());
					SchemaElement element2 = matchedElements.getMatchedElement(s2.getId());

					if(entityOrElement == 1){
						rowData[row][0] = element1.getName();
						rowData[row][1] = element1.getClass().getSimpleName();
						rowData[row][2] = element2.getName();
						rowData[row][3] = element2.getClass().getSimpleName();
						rowData[row][4] = Double.toString(matchedElements.getMatchScore());				
					}else{
						//System.out.println("Print entity: " + schemaInfo2.getDisplayName(temp.getId()));
						//rowData[row][0] = element1.getName();
						rowData[row][0] = s1Info.getDisplayName(element1.getId());
						rowData[row][1] = element1.getClass().getSimpleName();
						//rowData[row][2] = element2.getName();
						rowData[row][2] = s2Info.getDisplayName(element2.getId());
						rowData[row][3] = element2.getClass().getSimpleName();
						rowData[row][4] = Double.toString(matchedElements.getMatchScore());	
					}
					row++;
				}						
				
				JTable elementsTable;
				if(entityOrElement == 1){
					elementsTable = new JTable(rowData, 
						new String[] {s1.getName() + " Element", "Type", s2.getName() + " Element", "Type", "Match Score"});			
				}else{
					elementsTable = new JTable(rowData, 
							new String[] {s1.getName() + " Entity", "Type", s2.getName() + " Entity", "Type", "Match Score"});			
				}
				elementsTable.setShowGrid(true);
				for (int i = 0; i < elementsTable.getColumnCount(); i++) 
					elementsTable.getColumnModel().getColumn(i).sizeWidthToFit();
				elementsTable.doLayout();
				JScrollPane scrollPane = new JScrollPane(elementsTable);
				elementsTable.setFillsViewportHeight(false);

				int width = elementsTable.getPreferredSize().width;
				//if(width > prefferedSize.width) width = prefferedSize.width;
				int height = elementsTable.getPreferredSize().height + elementsTable.getRowHeight() + 4;
				//if(height > prefferedSize.height) height = prefferedSize.height;			
				if(height > prefferedSize.height) height = (int)(height/1.4);
				scrollPane.setPreferredSize(new Dimension(width, height));

				box.add(scrollPane);	
				this.add(box);			
				/*
			setLayout(new GridLayout(1, true));
			Label titleLabel = new Label(this, SWT.CENTER);
			titleLabel.setText("Matched Elements");
			titleLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			Table elementsTable = new Table(this, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
			elementsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			elementsTable.setLinesVisible(true);
			elementsTable.setHeaderVisible(true);
			TableColumn column = new TableColumn (elementsTable, SWT.CENTER);
			column.setText(s1.getName() + " Element");
			column = new TableColumn (elementsTable, SWT.CENTER);
			column.setText("Type");
			column = new TableColumn (elementsTable, SWT.CENTER);
			column.setText(s2.getName() + " Element");
			column = new TableColumn (elementsTable, SWT.CENTER);
			column.setText("Type");
			column = new TableColumn (elementsTable, SWT.CENTER);
			column.setText("Match Score");			
			for(MatchedSchemaElements matchedElements : intersectElements) {
				TableItem item = new TableItem (elementsTable, SWT.NONE);
				SchemaElement element1 = matchedElements.getMatchedElement(s1.getId());
				SchemaElement element2 = matchedElements.getMatchedElement(s2.getId());
				item.setText(0, element1.getName());
				item.setText(1, element1.getClass().getSimpleName());
				item.setText(2, element2.getName());
				item.setText(3, element2.getClass().getSimpleName());
				item.setText(4, Double.toString(matchedElements.getMatchScore()));
			}
			for(int i=0; i<elementsTable.getColumnCount(); i++) {
				elementsTable.getColumn(i).pack();
			}
				 */		
			}	
		}
	}
}
