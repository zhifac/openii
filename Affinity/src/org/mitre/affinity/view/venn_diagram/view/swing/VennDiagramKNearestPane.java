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

import java.text.DecimalFormat;
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

import org.eclipse.swt.SWT;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets.MatchedSchemaElements;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/**
 * Venn diagram pane contains a Venn diagram view of the set relationship between matched elements in two schemas
 * and an optional min-max slider to change the min-max match score used to compute the venn diagram sets.
 * 
 * This view has been modified by Beth to match Chris Wolf's Venn Diagram view design
 * 
 * @author CBONACETO
 * @author BETHYOST
 *
 */
public class VennDiagramKNearestPane extends JPanel implements MinMaxSliderListener {
	private static final long serialVersionUID = 1L;
	private VennDiagramKNearestView vennDiagram;
		
	public VennDiagramKNearestPane(VennDiagramSets sets) { 
		this(sets, false);
	}
	
	public VennDiagramKNearestPane(VennDiagramSets sets, boolean showMinMaxSlider) { 
		super();
		setLayout(new BorderLayout());
		if(showMinMaxSlider) {
			MinMaxSliderPane slider = new MinMaxSliderPane(JSlider.VERTICAL, 
					sets.getMinMatchScoreThreshold(), sets.getMaxMatchScoreThreshold(), false);
			slider.addMinMaxSliderListener(this);
			slider.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
			add(slider, BorderLayout.EAST);			
		}

		
		this.vennDiagram = new VennDiagramKNearestView(sets);
		vennDiagram.setFont(new Font(getFont().getName(), Font.BOLD, 12));
		vennDiagram.setMouseOverSetsEnabled(true);
		vennDiagram.setBackground(Color.WHITE);		
		add(vennDiagram, BorderLayout.CENTER);		
	}
	
	public void minMaxSliderMoved(double minValue, double maxValue) {
		//System.out.println("new slider values: " + minValue + ", " + maxValue);
		VennDiagramKNearestView.closeMouseOverDialog();
		vennDiagram.sets.setMatchScoreThresholdRange(minValue, maxValue);
		vennDiagram.repaint();
	}
	

	
	
	/**
	 * Venn diagram view of the set relationship between matched elements in two schemas.
	 * 
	 * @author CBONACETO
	 * @author BETHYOST
	 *
	 */
	public static class VennDiagramKNearestView extends JPanel implements MouseMotionListener, MouseListener {
		private static final long serialVersionUID = 1L;
		
		public static enum MouseOverSetsPosition {TOP, CENTER, BOTTOM};

		private VennDiagramSets sets;

		private Circle circle1;
		private Circle circle2;

		private Color[] lineColors;
		private Color[] fillColors;
		
		private int combinedWidth;
		private int ordinalPosition;
		private int maxSizeOfAllNschemas;
		
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
		
		private String viewType;
		
		public VennDiagramKNearestView(VennDiagramSets sets) {
			this(sets, false, MouseOverSetsPosition.CENTER, -1, -1);
			System.out.println("This method has not been implemented. Please see org.mitre.affinity.view.venn_diagram.view.swing to fix.");
		}
			
		public VennDiagramKNearestView(VennDiagramSets sets, boolean enableMouseOverSets, MouseOverSetsPosition mouseOverSetsPosition, int ordinalPos, int maxSchema2Size) {
			super(true);	
			this.viewType = "Venn Diagram";
			this.maxSizeOfAllNschemas = maxSchema2Size;
			this.ordinalPosition = ordinalPos;
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

		
		public void setViewType(String type){
			this.viewType = type;
		}

		//Compute diameter of circles representing schemas 1 and 2 such that the areas
		//are proportional to the number of elements in each schema
		//assumes that the maxSizeOfAllNSchemas has been set through constructor call
		//this update fixes the K Nearest View so that the 1 in 1xn is always the same size
		public void setCircleDiameters(boolean isVennDiagram){
			Insets insets = this.getInsets();
			Rectangle bounds = new Rectangle(insets.left + 2, insets.top + 2, getWidth() - insets.left - insets.right - 4,
					getHeight() - insets.top - insets.bottom - 4);

			
			//calculate the size of schema1 for the largest schema2_MAX
			int numElements1 = sets.getSchema1AllElements().size();
			int numElements2max = this.maxSizeOfAllNschemas;
			
		
			double pixelsPerElement = (double)bounds.width/(numElements2max + numElements1);
			int diameter1 = (int)(pixelsPerElement*numElements1);
			int diameter2max = (int)(pixelsPerElement*numElements2max);

		
			if(isVennDiagram){
				//check for height constraints
				if(diameter1 >= diameter2max) {
					if(diameter1 > bounds.height) {
						diameter1 = bounds.height;
						pixelsPerElement = (double)diameter1/numElements1;
					}
				}else {
					if(diameter2max > bounds.height) {
						diameter2max = bounds.height;
						pixelsPerElement = (double)diameter2max/numElements2max;
					}
				}
			}
			
			//set schema1's diameter to that size
			circle1.diameter = (int)(pixelsPerElement*numElements1);
			if(circle1.diameter < 1){
				circle1.diameter = 1;
			}
			
			
			//now find size for the current schema2 size
			int numElements2 = sets.getSchema2AllElements().size();

			circle2.diameter = (int)(pixelsPerElement*numElements2);	
			if(circle2.diameter < 1){
				circle2.diameter = 1;
			}
			
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
			
			setCircleDiameters(this.viewType.equals("Venn Diagram"));

			
			int numElements1 = sets.getSchema1AllElements().size();
			int numElements2 = sets.getSchema2AllElements().size();

			//adding in the percentage overlap
			double uniA = sets.getSchema1UniqueElements().size();
			double uniB = sets.getSchema2UniqueElements().size();
			double totalA = sets.getSchema1AllElements().size();
			double totalB = sets.getSchema2AllElements().size();
			
			double percentUniqueA = (uniA/totalA)*100;
			double percentUniqueB = (uniB/totalB)*100;
			
			double percentIntersectA = (totalA-uniA)/totalA;
			
			
			
			//Position and render the circles
			int yPos = bounds.height/2;
			circle1.y = yPos;
			circle2.y = yPos;
			
			
			if(this.viewType.equals("Venn Diagram")){
				//draw the Venn Diagrams View		
				//this.combinedWidth = circle1.diameter + circle2.diameter;
				//int remainder = (bounds.width - combinedWidth)/2;
				//circle1.x = remainder + circle1.diameter/2;
			
				//based on the % unique elements that exist in the reference schema...
				//determine how many pixel overlap there should  be
				int overlap = (int)((float)percentIntersectA * circle1.diameter);
				this.combinedWidth = circle1.diameter + circle2.diameter - overlap;
				int remainder = (bounds.width - combinedWidth)/2;
				
				circle1.x = remainder + circle1.diameter/2;
				circle2.x = remainder + circle1.diameter + circle2.diameter/2 - overlap ;


				
				float leftSideCircleB =  (float)(circle2.x - circle2.getDiameter()/2);
				float rightSideCircleB = (float)(circle2.x + circle2.getDiameter()/2);
				float leftSideCircleA =  (float)(circle1.x - circle1.getDiameter()/2);
				float rightSideCircleA = (float)(circle1.x + circle1.getDiameter()/2);
				
				//don't let the non-ref schema go any further once it's all the way contained
				if(rightSideCircleB < rightSideCircleA){
					circle2.x = (int) (rightSideCircleA - circle2.diameter/2);
					leftSideCircleB =  (float)(circle2.x - circle2.getDiameter()/2);
					rightSideCircleB = (float)(circle2.x + circle2.getDiameter()/2);
				}

				
				//Draw VENN CIRCLES
				g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

				//draw white backgrounds behind two circles first
				//first fill it in white so they are clearer if selected
				g2d.setColor(Color.WHITE);
				g2d.fillOval(circle1.getUpperLeftX(), circle1.getUpperLeftY(), circle1.diameter, circle1.diameter);
				g2d.drawOval(circle1.getUpperLeftX(), circle1.getUpperLeftY(), circle1.diameter, circle1.diameter);
				g2d.fillOval(circle2.getUpperLeftX(), circle2.getUpperLeftY(), circle2.diameter, circle2.diameter);
				g2d.drawOval(circle2.getUpperLeftX(), circle2.getUpperLeftY(), circle2.diameter, circle2.diameter);		

				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f)); 
				g2d.setColor(fillColors[0]);
				g2d.fillOval(circle1.getUpperLeftX(), circle1.getUpperLeftY(), circle1.diameter, circle1.diameter);
				g2d.drawOval(circle1.getUpperLeftX(), circle1.getUpperLeftY(), circle1.diameter, circle1.diameter);
				g2d.setColor(fillColors[1]);
				g2d.fillOval(circle2.getUpperLeftX(), circle2.getUpperLeftY(), circle2.diameter, circle2.diameter);
				g2d.drawOval(circle2.getUpperLeftX(), circle2.getUpperLeftY(), circle2.diameter, circle2.diameter);	
				
				//DRAW THE OVERLAP %'S
				String uniqueA = (int)percentUniqueA + "%";
				String intersectA = (int)(percentIntersectA*100) + "%";
				Rectangle2D uniqueAStringSize = fontMetrics.getStringBounds(uniqueA, g2d);	
				Rectangle2D intersectBStringSize = fontMetrics.getStringBounds(intersectA, g2d);	


				
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.f)); 
				g2d.setColor(Color.BLACK);
				
				//pick the lesser of the left side of circle B and centered
				int uniqueAXpos = (int)(leftSideCircleB - uniqueAStringSize.getWidth() - 4);
				int room = (int) (leftSideCircleB-leftSideCircleA);
				int secondXposOption = (int) (leftSideCircleA + room/2 - intersectBStringSize.getWidth()/2 - 4);
				if(secondXposOption < uniqueAXpos){
					uniqueAXpos = secondXposOption;
				}
				g2d.drawString(uniqueA, uniqueAXpos, circle1.y);
				//g2d.drawString(uniqueA, (int)(rightSideCircleA - uniqueAStringSize.getWidth() - 4), circle1.y);
				g2d.setColor(Color.BLACK);
				g2d.drawString(intersectA, (int)(leftSideCircleB + 4), circle1.y);

			}else{
				//DRAW BARS
				
				int y = bounds.height/2;
				int barHeight = 20;
				int refBarBottom = y - 2 - barHeight;
				int nonrefBarBottom = y + 2;
				
				int totalCombosize = (int) (circle1.diameter + circle2.diameter);
				int remainder = (bounds.width - totalCombosize)/2;
				//int refBarLeft = remainder;
				int refBarLeft = 2;
				
				int nonOverlapA = (int)((float)(uniA/totalA) * circle1.diameter);
				int overlapA = circle1.diameter - nonOverlapA;
				
				int nonOverlapB =(int)((float)(uniB/totalB) * circle2.diameter);
				int overlapB = circle2.diameter - nonOverlapB;
				
				int nonrefBarLeft;
				if(overlapA <= overlapB){
					nonrefBarLeft = refBarLeft + circle1.diameter - overlapA;
				}else{
					nonrefBarLeft = refBarLeft + circle1.diameter - overlapB;
				}
				
				g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
				g2d.setColor(Color.WHITE);
				g2d.fillRect(refBarLeft, refBarBottom, circle1.diameter, barHeight);
				g2d.fillRect(nonrefBarLeft, nonrefBarBottom, circle2.diameter, barHeight);
				
				
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f)); 
				g2d.setColor(Color.RED);
				g2d.fillRect(refBarLeft + nonOverlapA + 1, refBarBottom + 1, overlapA -2, barHeight-2);
				g2d.setColor(Color.BLUE);
				g2d.fillRect(refBarLeft, refBarBottom, circle1.diameter, barHeight);

	
				g2d.setColor(Color.RED);
				g2d.fillRect(nonrefBarLeft, nonrefBarBottom, circle2.diameter, barHeight);
				g2d.setColor(Color.BLUE);
				g2d.fillRect(nonrefBarLeft+1, nonrefBarBottom+1, overlapB-2, barHeight-2);
				
				//drawing the values
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.f)); 
				g2d.setColor(Color.BLUE);
				//add the number of unique elements to the top left of the ref bar
				int uniAPercent = (int)((uniA/totalA)*100);
				String uniAPercentString = (int)uniA + " (" + uniAPercent + "%)";
				g2d.drawString(uniAPercentString,refBarLeft, refBarBottom - 4);
				
				//add the number of intersect elements to the top right of the ref bar
				g2d.setColor(Color.BLACK);
				int intersectAPercent = 100 - uniAPercent;
				String intersectAPercentString = (int)(totalA-uniA) + " (" + intersectAPercent + "%)";
				Rectangle2D intersectAStringSize = fontMetrics.getStringBounds(intersectAPercentString, g2d);
				int startX = (int) (refBarLeft + circle1.diameter - intersectAStringSize.getWidth());
				Rectangle2D uniAStringSize = fontMetrics.getStringBounds(uniAPercentString, g2d);
				int endLeftString = (int) (refBarLeft + uniAStringSize.getWidth());
				if(startX <= endLeftString){
					//ref bar is so short that numbers would overlap
					startX = endLeftString + 4;
				}
				g2d.drawString(intersectAPercentString, startX, refBarBottom - 4);
				
				//add the number of intersect elements to the bottom left of the nonref bar
				int uniBPercent = (int)((uniB/totalB)*100);
				int intersectBPercent = 100 - uniBPercent;
				String intersectBPercentString = (int)(totalB-uniB) + " (" + intersectBPercent + "%)";
				Rectangle2D intersectBStringSize = fontMetrics.getStringBounds(intersectBPercentString, g2d);
				//int startY = (int) (nonrefBarBottom + intersectBStringSize.getHeight());
				g2d.drawString(intersectBPercentString, nonrefBarLeft, (int) (nonrefBarBottom + barHeight + intersectBStringSize.getHeight()));
				
				//add the number of unique elements to the bottom right of the nonrefbar
				g2d.setColor(Color.RED);
				//add the number of unique elements to the top left of the ref bar
				String uniBPercentString = (int)uniB + " (" + uniBPercent + "%)";
				Rectangle2D uniqueBStringSize = fontMetrics.getStringBounds(uniBPercentString, g2d);
				int startXnonref = (int) (nonrefBarLeft + circle2.diameter - uniqueBStringSize.getWidth());
				
				int endbottomLeftString = (int) (nonrefBarLeft + intersectBStringSize.getWidth());
				if(startXnonref <= endbottomLeftString){
					//nonref bar is so short that numbers would overlap
					startXnonref = endbottomLeftString + 4;
				}
				
				g2d.drawString(uniBPercentString, startXnonref, (int) (nonrefBarBottom + barHeight + intersectBStringSize.getHeight()));
				
			}
			

			
	
			
	

			//Label circles
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.f)); 
				
			g2d.setColor(Color.BLACK);
			Font fontSchemaNames = new Font(getFont().getName(), Font.PLAIN, 12);
			g2d.setFont(fontSchemaNames);
	
			
			String name = sets.getSchema2().getName();
			Rectangle2D str2Size = fontMetrics.getStringBounds(name, g2d);	
			
			if(name != null && !name.isEmpty()) {
				g2d.setColor(Color.red);
				float xPosName;
				if(str2Size.getWidth() >= bounds.width){
					xPosName = 2;
				}else{
					xPosName = (float)(bounds.width - str2Size.getWidth());
				}
				g2d.drawString(name, xPosName, 12);
			}			
			

			double height = 0;
/*			if(ordinalPosition != -1){
				String pos = "Order: " + this.ordinalPosition;
				Rectangle2D strSize = fontMetrics.getStringBounds(pos, g2d);	
				height = strSize.getHeight();
				float xPosForPos = 0;
				float yPosForPos = (float)(bounds.height - height);
				g2d.setColor(Color.BLACK);
				g2d.setFont(new Font(getFont().getName(), Font.PLAIN, 12));
				g2d.drawString(pos, xPosForPos, yPosForPos);
			}
*/
			
	

				//show morses
				int matchesInA = (int) (totalA - uniA);
				int matchesInB = (int) (totalB - uniB);
				double morse = (matchesInA + matchesInB)/(totalA + totalB);
				
				//need to set to 2 decimals
			    DecimalFormat forLimitPrecisionTwo = new DecimalFormat("#.##");
			    //String metric = "Score: " + forLimitPrecisionTwo.format(morse);
			    String metric = forLimitPrecisionTwo.format(morse);
			    if(ordinalPosition != -1){
			    	metric = "[" + this.ordinalPosition + "] Morse: " + metric;
			    }


			Rectangle2D strSize = fontMetrics.getStringBounds(metric, g2d);	
			float xPosForJ = 0;
			float yPosForJ = (float)(bounds.height);
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font(getFont().getName(), Font.PLAIN, 12));
			g2d.drawString(metric, xPosForJ, yPosForJ);
			
		
		}

		private static JDialog dialog = null;

		boolean inHoverThread = false;
		Thread hoverThread;
		long hoverStartTime;
		MouseEvent currMouseEvent;

		//MouseMotionListener methods
		public void mouseDragged(MouseEvent e) {}	




		
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

		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}


	}
}
