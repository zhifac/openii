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

package org.mitre.affinity.view.venn_diagram.view.swt;

import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets.MatchedSchemaElements;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Venn diagram view of the set relationship between matched elements in two schemas.
 * 
 * @author CBONACETO
 *
 */
public class VennDiagramPane_Old extends Canvas implements MouseMoveListener {	
	private VennDiagramSets sets;
	
	private Circle circle1;
	private Circle circle2;
	
	private Color[] lineColors;
	private Color[] fillColors;
	
	/** Indicates which set mouse is currently over (1 = schema1, 2 = schema2, 3 = intersection) */
	private int mouseOverSet = -1;
	
	//private int textHeight;
	
	public VennDiagramPane_Old(Composite parent, int style, VennDiagramSets sets) {
		super(parent, style | SWT.DOUBLE_BUFFERED);	
		this.sets = sets;
		this.circle1 = new Circle(0, 0, 1);
		this.circle2 = new Circle(0, 0, 1);
		this.lineColors = new Color[2];
		lineColors[0] = getDisplay().getSystemColor(SWT.COLOR_BLUE);
		lineColors[1] = getDisplay().getSystemColor(SWT.COLOR_RED);
		this.fillColors = new Color[2];
		fillColors[0] = lineColors[0];
		fillColors[1] = lineColors[1];		
		setFont(new Font(Display.getDefault(), new FontData("Arial", 9, SWT.BOLD)));
		
		//Add mouse move listener
		this.addMouseMoveListener(this);
		
		//Add repaint listener
		this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) { render(e.gc); }
		});
	}
	
	public VennDiagramSets getSets() {
		return sets;
	}
	
	public void setSets(VennDiagramSets sets) {
		this.sets = sets;
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
	
	public void render(GC gc) {
		//Render the background
		Rectangle bounds = this.getBounds();
		this.drawBackground(gc, bounds.x, bounds.y, bounds.width, bounds.height);
		
		//Compute diameter of circles representing schemas 1 and 2 such that the areas
		//are proportional to the number of elements in each schema
		int numElements1 = sets.getSchema1AllElements().size();
		int numElements2 = sets.getSchema2AllElements().size();
		double ratio = Math.sqrt((double)numElements1/numElements2);
		int diameter2 = (int)(bounds.width/(ratio+1));
		int diameter1 = (int)(ratio * diameter2);
		if(diameter1 >= diameter2) {
			if(diameter1 > bounds.height) {
				diameter1 = bounds.height;
				diameter2 = (int)(diameter1/ratio);
			}
		}
		else {
			if(diameter2 > bounds.height) {
				diameter2 = bounds.height;
				diameter1 = (int)(ratio * diameter2);
			}
		}
		circle1.diameter = diameter1;
		circle2.diameter = diameter2;
		
		//Position and render the circles
		int yPos = bounds.y + bounds.height/2;
		circle1.y = yPos;
		circle2.y = yPos;
		if(sets.getSchema1UniqueElements().isEmpty() || sets.getSchema2UniqueElements().isEmpty()) {
			//one circle is completely contained in the other (schemas are identical or have subset/superset relationship)
			//center both circles
			int xPos = bounds.x + bounds.width/2;
			circle1.x = xPos;
			circle2.x = xPos;
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
			int combinedWidth = diameter1 + diameter2 - overlap;
			int remainder = (bounds.width - combinedWidth)/2;
			circle1.x = bounds.x + remainder + circle1.diameter/2;
			circle2.x = bounds.x + bounds.width - remainder - circle2.diameter/2;
		}
		else {		
			//the circles do not overlap (schemas share no common elements)
			int remainder = (bounds.width - circle1.diameter - circle2.diameter)/2;
			circle1.x = bounds.x + remainder + circle1.diameter/2;
			circle2.x = circle1.x + circle1.diameter/2 + circle2.diameter/2;
		}
		
		//Draw venn circles
		gc.setAntialias(SWT.ON);
		gc.setAlpha(100);
		gc.setBackground(fillColors[0]);
		gc.fillOval(circle1.getUpperLeftX(), circle1.getUpperLeftY(), circle1.diameter, circle1.diameter);
		gc.setBackground(fillColors[1]);
		gc.fillOval(circle2.getUpperLeftX(), circle2.getUpperLeftY(), circle2.diameter, circle2.diameter);
		//gc.setAlpha(255);
		gc.setForeground(lineColors[0]);
		gc.drawOval(circle1.getUpperLeftX(), circle1.getUpperLeftY(), circle1.diameter, circle1.diameter);
		gc.setForeground(lineColors[1]);
		gc.drawOval(circle2.getUpperLeftX(), circle2.getUpperLeftY(), circle2.diameter, circle2.diameter);
		
		//Label circles
		gc.setAlpha(255);
		gc.setForeground(lineColors[0]);
		String name = sets.getSchema1().getName();
		if(name != null && !name.isEmpty()) {
			Point strSize = gc.stringExtent(name);
			//this.textHeight = strSize.y;
			gc.drawString(name, circle1.x - strSize.x/2, circle1.y - circle1.diameter/2 - strSize.y + 1, true);
			//gc.drawString(name, circle1.x - strSize.x/2, circle1.y - circle1.diameter/2 +  strSize.y, true);			
		}
		gc.setForeground(lineColors[1]);
		name = sets.getSchema2().getName();
		if(name != null && !name.isEmpty()) {
			Point strSize = gc.stringExtent(name);			
			//gc.drawString(name, circle2.x - strSize.x/2, circle2.y - circle2.diameter/2 +  strSize.y, true);
			gc.drawString(name, circle2.x - strSize.x/2, circle2.y + circle2.diameter/2 - 1, true);
			
			/*
			if(circle1.x == circle2.x) 
				//Offset the text a bit since the circles completely overlap
				gc.drawString(name, circle2.x - strSize.x/2, circle2.y - strSize.y - strSize.y/2, true);			
			else
				gc.drawString(name, circle2.x - strSize.x/2, circle2.y - strSize.y/2, true);
				*/
		}
	}
	
	private Shell dlgShell = null;
	
	boolean inHoverThread = false;
	Thread hoverThread;
	long hoverStartTime;
	MouseEvent currMouseEvent;
	
	public void mouseMove(MouseEvent e) {
		if(dlgShell != null && !dlgShell.isDisposed()) {
			hoverStartTime = System.currentTimeMillis();
			mouseHovered(e);
		}
		else {
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
						getDisplay().asyncExec(new Runnable() {
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
		}
	}
	
	/** Called when the mouse has been hovering over a spot for a time >= hoverTimeThreshold */
	protected void mouseHovered(MouseEvent e) {
		int overSet = -1;
		if(circle1.contains(e.x, e.y)) {
			if(circle2.contains(e.x, e.y))
				overSet = 3;
			else
				overSet = 1;
		}
		else if(circle2.contains(e.x, e.y))
			overSet = 2;
		
		if(overSet != this.mouseOverSet || (dlgShell == null || dlgShell.isDisposed())) {
			//if(dlgShell != null && !dlgShell.isDisposed() && dlgShell.getBounds().contains(e.x, e.y))
				//return;
			this.mouseOverSet = overSet;
			
			if(dlgShell != null && !dlgShell.isDisposed()) {
				if(!dlgShell.getBounds().contains(e.x, e.y))
					dlgShell.dispose();
				return;
			}
			
			//this.mouseOverSet = overSet;			
			
			if(mouseOverSet != -1) {
				dlgShell = new Shell(getShell(), SWT.BORDER | SWT.CLOSE);
				dlgShell.setLayout(new FillLayout());
			}
			else
				return;
			
			//Show appropriate dialog depending on which set the mouse is over
			Composite pane = null;
			switch(mouseOverSet) {
			case(1):
				//mouse is over set 1		
				String schemaName = sets.getSchema1().getName();
				pane = new ElementsPane(dlgShell, SWT.NONE, schemaName + " Unmatched Elements", sets.getSchema1UniqueElements());
				//dlgShell.setText(schemaName + " Unmatched Elements");
				Point size = pane.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				if(size.y > getSize().y/2)								
					dlgShell.setSize(size.x + 10, (int)(size.y/1.4 + 10));
				else
					dlgShell.pack();				
				//Display dialog on left side of pane
				dlgShell.setLocation(toDisplay(circle1.x - dlgShell.getSize().x/2, circle1.y + circle1.diameter/4));
				//dlgShell.setLocation(toDisplay(circle1.x - dlgShell.getSize().x/2, circle1.y - circle1.diameter/2 + textHeight * 3));
				//dlgShell.setLocation(toDisplay(getLocation().x - dlgShell.getSize().x, 
					//	getLocation().y + (getSize().y - dlgShell.getSize().y)/2));
				break;
			case(2):
				//mouse is over set 2
				schemaName = sets.getSchema2().getName();
				pane = new ElementsPane(dlgShell, SWT.NONE, schemaName + " Unmatched Elements", sets.getSchema2UniqueElements());
				size = pane.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				if(size.y > getSize().y/2)								
					dlgShell.setSize(size.x + 10, (int)(size.y/1.4 + 10));
				else
					dlgShell.pack();				
				//Display dialog on right side of pane
				dlgShell.setLocation(toDisplay(circle2.x - dlgShell.getSize().x/2,  circle2.y + circle2.diameter/4));
				//dlgShell.setLocation(toDisplay(circle2.x - dlgShell.getSize().x/2, circle2.y - circle2.diameter/2 + textHeight * 3));
				break;
			case(3):
				//mouse is over intersection
				pane = new MatchedElementsPane(dlgShell, SWT.NONE, sets.getSchema1(), sets.getSchema2(), sets.getIntersectElements());
				size = pane.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				if(size.y > getSize().y/2)								
					dlgShell.setSize(size.x + 10, (int)(size.y/1.4 + 10));
				else
					dlgShell.pack();
				
				//Display dialog below pane				
				//Rectangle bounds = getBounds();
				if(circle1.diameter > circle2.diameter)				
					//dlgShell.setLocation(toDisplay(bounds.x + (bounds.width - dlgShell.getSize().x)/2, 
					//	circle2.y - circle2.diameter/2 + textHeight * 3));
					dlgShell.setLocation(toDisplay(circle1.x + (circle2.x - circle1.x)/2 - dlgShell.getSize().x/2, 
							circle2.y + 3)); // + circle2.diameter/4));
				else
					//dlgShell.setLocation(toDisplay((circle2.x - circle1.x)/2 + circle1.x - dlgShell.getSize().x/2, 
					//		circle1.y - circle1.diameter/2 + textHeight * 3));				
					dlgShell.setLocation(toDisplay(circle1.x + (circle2.x - circle1.x)/2 - dlgShell.getSize().x/2, 
							circle1.y + 3)); //+ circle1.diameter/4));
				break;
			}			
			dlgShell.open();	
		}
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
	
	public static class ElementsPane extends Composite {
		//private String schemaName;
		//private Collection<SchemaElement> schemaElements;		
		//private Label schemaNameLabel;		
		
		public ElementsPane(Composite parent, int style, 
				String title, Collection<SchemaElement> schemaElements) {
			super(parent, style);
			//this.schemaName = schemaName;
			//this.schemaElements = schemaElements;
			createPane(title, schemaElements);
		}	
		
		private void createPane(String title, Collection<SchemaElement> schemaElements) {
			setLayout(new GridLayout(1, true));
			Label titleLabel = new Label(this, SWT.CENTER);
			titleLabel.setText(title);
			titleLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			Table elementsTable = new Table(this, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
			elementsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			elementsTable.setLinesVisible(true);
			elementsTable.setHeaderVisible(true);
			TableColumn column = new TableColumn (elementsTable, SWT.CENTER);
			column.setText("Name");
			column = new TableColumn (elementsTable, SWT.CENTER);
			column.setText("Type");			
			for(SchemaElement element : schemaElements) {
				TableItem item = new TableItem (elementsTable, SWT.NONE);
				item.setText (0, element.getName());
				item.setText (1, element.getClass().getSimpleName());

			}
			for(int i=0; i<elementsTable.getColumnCount(); i++) {
				elementsTable.getColumn(i).pack();
			}			
		}
	}
	
	public static class MatchedElementsPane extends Composite {
		public MatchedElementsPane(Composite parent, int style, 
				Schema s1, Schema s2, Collection<MatchedSchemaElements> intersectElements) {
			super(parent, style);
			createPane(s1, s2, intersectElements);
		}	
		
		private void createPane(Schema s1, Schema s2, Collection<MatchedSchemaElements> intersectElements) {
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
		}
	}
}
