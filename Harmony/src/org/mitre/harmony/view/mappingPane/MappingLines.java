// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.mappingPane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JViewport;

import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.filters.FilterManager;
import org.mitre.harmony.model.filters.FiltersListener;
import org.mitre.harmony.model.project.MappingListener;
import org.mitre.harmony.model.project.ProjectMapping;
import org.mitre.harmony.model.selectedInfo.SelectedInfoListener;
import org.mitre.harmony.view.schemaTree.SchemaTree;
import org.mitre.harmony.view.schemaTree.SchemaTreeListener;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/**
 * Stores all lines currently associated with the mapping
 * @author CWOLF
 */
public class MappingLines implements MappingListener, FiltersListener, SchemaTreeListener, SelectedInfoListener
{	
	/** Stores the mapping pane */
	private MappingPane mappingPane;
	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Stores a list of the mapping cell lines */
	private Hashtable<Integer, MappingCellLines> lines = null;
	
	/** Stores schema tree mapping cell listeners */
	private Vector<LinesListener> listeners = new Vector<LinesListener>();

	/** Initializes the schema tree lines */
	MappingLines(MappingPane mappingPane, HarmonyModel harmonyModel)
	{	
		this.mappingPane = mappingPane;
		this.harmonyModel = harmonyModel;
		
		// Listen for changes which affect the schema tree links
		mappingPane.getTree(HarmonyConsts.LEFT).addSchemaTreeListener(this);
		mappingPane.getTree(HarmonyConsts.RIGHT).addSchemaTreeListener(this);
		harmonyModel.getFilters().addListener(this);
		harmonyModel.getSelectedInfo().addListener(this);
		harmonyModel.getMappingManager().addListener(this);
	}

	/** Initialize the lines within the schema tree */
	private Hashtable<Integer, MappingCellLines> getLines()
	{
		if(lines == null)
		{
			lines = new Hashtable<Integer, MappingCellLines>();
			for(ProjectMapping mapping : harmonyModel.getMappingManager().getMappings())
				for(MappingCell mappingCell : mapping.getMappingCells())
					lines.put(mappingCell.getId(), new MappingCellLines(mappingPane,mappingCell.getId(),harmonyModel));
		}
		return lines;
	}
	
	/** Updates if the mapping cell lines are hidden */
	private void updateHidden()
	{
		Enumeration mappingCells = getLines().elements();
		while(mappingCells.hasMoreElements())
			((MappingCellLines)mappingCells.nextElement()).updateHidden();
		fireLinesModified();
	}
	
	/** Updates the mapping cell lines */
	public void updateLines()
	{
		Enumeration mappingCells = getLines().elements();
		while(mappingCells.hasMoreElements())
			((MappingCellLines)mappingCells.nextElement()).updateLines();
		fireLinesModified();
	}
	
	/** Determine which mapping cell resides closest to the provided point */
	public Integer getClosestMappingCellToPoint(Point point)
	{
		Integer bestMappingCell = null;
		double shortestDist = Double.MAX_VALUE;

		// Cycle through all mapping cells
		Enumeration mappingCells = getLines().elements();
		while(mappingCells.hasMoreElements())
		{
			// Examine all non-hidden mapping cells
			MappingCellLines mappingCellLines = (MappingCellLines)mappingCells.nextElement();
			
			//For regular mapping cell lines
			if(!mappingCellLines.getHidden())
			{
				// Cycle through all mapping cell lines
				for(MappingCellLine line : mappingCellLines.getLines())
				{
					// Examines all visible lines
					if(line.isVisible())
					{
						// If shortest distance between point/line, mark as current closest
						double dist = line.ptSegDistSq(point.x,point.y);
						if(dist<shortestDist && dist<101) {
							bestMappingCell = mappingCellLines.getMappingCellID();
							shortestDist = dist;
						}
					}
				}			
			}
			
			//For functional mapping cell lines
			if(!mappingCellLines.getHidden()&& mappingCellLines.getHasFunction())
			{
				// Cycle through all mapping cell lines
				for(MappingCellLine line : mappingCellLines.getLines())
				{
					// Examines all visible lines
					//if(line.isVisible())
					if(true)
					{
						// If shortest distance between point/line, mark as current closest
						double dist = line.ptSegDistSq(point.x,point.y);
						if(dist<shortestDist && dist<101) {
							bestMappingCell = mappingCellLines.getMappingCellID();
							shortestDist = dist;
						}
					}
					
				}
			}
		}
		
		// Return the mapping cell which was determined to be closest to the point
		return bestMappingCell;
	}
	
	/** Determine which mapping cells reside within the specified region */
	public ArrayList<Integer> getMappingCellsInRegion(Rectangle region)
	{
		// List for storing all mapping cells in region
		ArrayList<Integer> selMappingCells = new ArrayList<Integer>();
		
		// Cycle through all mapping cells
		Enumeration mappingCells = getLines().elements();
		while(mappingCells.hasMoreElements())
		{
			// Examine all non-hidden mapping cells
			MappingCellLines mappingCell = (MappingCellLines)mappingCells.nextElement();
			if(!mappingCell.getHidden())
			{
				// Cycle through all mapping cell lines
				for(MappingCellLine line : mappingCell.getLines())
				{
					// Examines all visible lines
					if(line.isVisible())
					{
						// Add mapping cell to list if intersects region
						if(region==null || line.intersects(region))
						{
							selMappingCells.add(mappingCell.getMappingCellID());
							break;
						}
					}
				}
			}
			//For function mapping cells
			if(!mappingCell.getHidden()&& mappingCell.getHasFunction())
			{
				// Cycle through all mapping cell lines
				for(MappingCellLine line : mappingCell.getLines())
				{
					// Examines all visible lines
					//if(line.isVisible())
					{
						// Add mapping cell to list if intersects region
						if(region==null || line.intersects(region))
						{
							selMappingCells.add(mappingCell.getMappingCellID());
							break;
						}
					}
				}
			}
		}
		
		// Return array of all selected mapping cells
		return selMappingCells;
	}

	/** Collects all elements hierarchically influenced by the specified element */
	private ArrayList<Integer> getHierarchicallyAffectedElementIDs(HashSet<Integer> schemaIDs, Integer elementID)
	{
		// Find all affected element IDs
		ArrayList<Integer> affectedElementIDs = new ArrayList<Integer>();
		for(Integer schemaID : schemaIDs)
		{
			// Retrieve the schema information
			HierarchicalSchemaInfo schemaInfo = harmonyModel.getSchemaManager().getSchemaInfo(schemaID);

			// Identify all affected elements
			if(schemaInfo.containsElement(elementID))
				for(SchemaElement element : schemaInfo.getDescendantElements(elementID))
					affectedElementIDs.add(element.getId());
		}
		return affectedElementIDs;
	}
	
	/** Updates the hierarchical filter as needed */
	private void updateHierarchicalFilterAsNeeded(List<MappingCell> mappingCells)
	{
		if(harmonyModel.getFilters().getFilter(FilterManager.HIERARCHY_FILTER))
		{
			// Identify the visible schemas
			HashSet<Integer> schemaIDs = new HashSet<Integer>();
			for(ProjectMapping mapping : harmonyModel.getMappingManager().getMappings())
				if(mapping.isVisible())
					{ schemaIDs.add(mapping.getSourceId()); schemaIDs.add(mapping.getTargetId()); }
			
			// Build up list of elements affected by changed mapping cell
			HashSet<Integer> affectedElementIDs = new HashSet<Integer>();
			for(MappingCell mappingCell : mappingCells)
			{
				// Generate list of elements still in need of checking
				ArrayList<Integer> elementIDs = new ArrayList<Integer>();
				if(!affectedElementIDs.contains(mappingCell.getOutput())) elementIDs.add(mappingCell.getOutput());
				for(Integer elementID : mappingCell.getInput())
					if(!affectedElementIDs.contains(elementID)) elementIDs.add(mappingCell.getOutput());

				// Gather up hierarchically affected element IDs
				for(Integer elementID : elementIDs)
					affectedElementIDs.addAll(getHierarchicallyAffectedElementIDs(schemaIDs, elementID));
			}
			
			// Translate list of affected elements into list of mapping cells
			HashSet<Integer> mappingCellIDs = new HashSet<Integer>();
			for(Integer elementID : affectedElementIDs)
				mappingCellIDs.addAll(harmonyModel.getMappingManager().getMappingCellsByElement(elementID));
			
			// Update the lines for the affected mappings cells
			for(Integer mappingCellID : mappingCellIDs)
				getLines().get(mappingCellID).updateLines();
		}
	}
	
	/** Handles the addition of a mapping cell */
	public void mappingCellsAdded(Integer mappingID, List<MappingCell> mappingCells)
	{
		for(MappingCell mappingCell : mappingCells){
			
				MappingCellLines ml = new MappingCellLines(mappingPane,mappingCell.getId(),harmonyModel);
				
				//need to change this when the database is redesigned for this 
				if((mappingCell.getFunctionID().toString()).equals(("450"))){
					ml.setHasFunction(false);
				}
				else{
					ml.setHasFunction(true);
				}
				getLines().put(mappingCell.getId(), ml);		
		}
		updateHierarchicalFilterAsNeeded(mappingCells);
		fireLinesModified();
	}

	/** Handles the modification of a mapping cell */
	public void mappingCellsModified(Integer mappingID, List<MappingCell> oldMappingCells, List<MappingCell> newMappingCells)
	{
		for(MappingCell newMappingCell : newMappingCells)
			getLines().get(newMappingCell.getId()).updateHidden();
		updateHierarchicalFilterAsNeeded(newMappingCells);
		fireLinesModified();
	}
	
	/** Handles the removal of a mapping cell */
	public void mappingCellsRemoved(Integer mappingID, List<MappingCell> mappingCells)
	{
		for(MappingCell mappingCell : mappingCells)
			getLines().remove(mappingCell.getId());
		updateHierarchicalFilterAsNeeded(mappingCells);
		fireLinesModified();
	}

	/** Handles the modification of a schema node */
	public void mappingVisibilityChanged(Integer mappingID)
		{ fireLinesModified(); }	
	
	/** Updates mapping cell lines when assertion filters changed */
	public void filterChanged(Integer filter)
	{
		if(filter.equals(FilterManager.HIERARCHY_FILTER)) updateLines();
		else updateHidden();
	}
	
	/** Updates mapping cell lines when confidence filters changed */
	public void confidenceChanged()
		{ updateHidden(); }
	
	/** Updates mapping cell lines when a focus is added */
	public void focusModified(Integer side)
		{ updateLines(); }
	
	/** Updates mapping cell lines when depth filters changed */
	public void depthChanged(Integer side)
		{ updateLines(); }

	/** Handles changes to the selected mapping cells */
	public void selectedMappingCellsModified()
		{ updateHidden(); }
	
	/** Updates mapping cell lines when a node's max confidence changed */
	public void maxConfidenceChanged(Integer schemaObjectID)
	{
		for(Integer mappingCellID : harmonyModel.getMappingManager().getMappingCellsByElement(schemaObjectID))
			if(getLines().get(mappingCellID)!=null) getLines().get(mappingCellID).updateHidden();
		fireLinesModified();
	}
	
	/** Updates mapping cell lines when the schema display is modified */
	public void schemaDisplayModified(SchemaTree tree)
		{ updateLines(); }
	
	/** Updates the mapping lines if the schema structure changed */
	public void schemaStructureModified(SchemaTree tree)
		{ lines = null; }
	
	// Unused listener events
	public void mappingAdded(Integer mappingID) {}
	public void mappingRemoved(Integer mappingID) {}
	public void elementsMarkedAsFinished(Integer count) {}
	public void elementsMarkedAsUnfinished(Integer count) {}
	public void selectedElementsModified(Integer role) {}
	public void displayedElementModified(Integer role) {}
	
	/** Draws all lines linking the source and target schema trees */
	void paint(Graphics g)
	{
		// Collects a listing of all visible and hidden lines
		Stroke defaultStroke = ((Graphics2D)g).getStroke();
		ArrayList<String> visibleLines = new ArrayList<String>();
		HashSet<String> hiddenLines = new HashSet<String>();

		// Modifies clip bounds to not include area covered by scroll bars
		JViewport viewport = mappingPane.getTreeViewport(HarmonyConsts.LEFT);
		g.setClip(0,0,mappingPane.getWidth(),viewport.getHeight());
		
		// Cycle through all non-hidden mapping cells to draw all visible lines
		Enumeration mappingCells = getLines().elements();
		while(mappingCells.hasMoreElements())
		{
			MappingCellLines mappingCell = (MappingCellLines)mappingCells.nextElement();
			if(!mappingCell.getHidden())
			{
				// Set the link color and collect all mapping cell lines
				Color color = mappingCell.getColor();
				for(MappingCellLine line : mappingCell.getLines()) {
					if(line.intersects(g.getClipBounds()))
					{
						// Store line information
						if(line.isVisible()||mappingCell.getHasFunction()){
							visibleLines.add(color.getRGB()+","+(int)line.x1+","+(int)line.y1+","+(int)line.x2+","+(int)line.y2);
						}
						else{
							hiddenLines.add((int)line.x1+","+(int)line.y1+","+(int)line.x2+","+(int)line.y2);
						}
					}
				}
			}
		}

		// Draw all hidden mapping cell lines
		((Graphics2D)g).setStroke(new BasicStroke((float)1.0,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL,(float)0.0,new float[]{5,5},(float)2.0));
		g.setColor(new Color(0.87f,0.87f,0.87f));
		for(String hiddenLine : hiddenLines)
		{
			String info[] = hiddenLine.split(",");
			g.drawLine(Integer.parseInt(info[0]),Integer.parseInt(info[1]),
					Integer.parseInt(info[2]),Integer.parseInt(info[3]));
		}
		
		// Draw all visible mapping cell lines
		((Graphics2D)g).setStroke(defaultStroke);
		for(String visibleLine : visibleLines)
		{
			//Draw the lines
			String info[] = visibleLine.split(",");
			g.setColor(new Color(Integer.parseInt(info[0])));
			g.drawLine(Integer.parseInt(info[1]),Integer.parseInt(info[2]),
					Integer.parseInt(info[3]),Integer.parseInt(info[4]));
			
			//Draw circles to the edge of the lines
			int circleSize = 2;
			

			Component functionPane = mappingPane.getFunctionPane();
			String minX = functionPane.getWidth() + "";
			String midX = functionPane.getWidth() + functionPane.getWidth()/2 + "";
			String maxX = functionPane.getWidth()*2 + "";
			
			//lines with Function
			if(info[1].equals(midX) && info[3].equals(maxX)){
				//Ending line
				g.fillOval(Integer.parseInt(info[3])-6,Integer.parseInt(info[4])-5, 4*circleSize, 4*circleSize);
				//adding function graph here
				BufferedImage functionImage = null;
				try{
					functionImage = ImageIO.read(new File("src\\org\\mitre\\harmony\\view\\graphics\\funcLogo2.png")); 
				} catch (IOException ex) { 
				
				}
				int width = 20;
				int height = 23;
				int x= Integer.parseInt(info[1])-10;
				int y = Integer.parseInt(info[2])-10;
		        g.drawImage(functionImage, x, y, width, height, null);  
			}
			else if(info[1].equals(minX) && info[3].equals(midX)){
				//beginning line
				g.fillOval(Integer.parseInt(info[1])-1,Integer.parseInt(info[2])-4, 4*circleSize, 4*circleSize);			
			}
			//line without a function
			else{  
				g.fillOval(Integer.parseInt(info[1])-1,Integer.parseInt(info[2])-4, 4*circleSize, 4*circleSize);			
				g.fillOval(Integer.parseInt(info[3])-6,Integer.parseInt(info[4])-5, 4*circleSize, 4*circleSize);
			}
			
			//draw highlight dash extended lines
		}
	}		
	
	/** Adds a line listener */
	public void addLinesListener(LinesListener obj)
		{ listeners.add(obj); }
	
	/** Removes a line listener */
	void removeLinesListener(LinesListener obj)
		{ listeners.remove(obj); }

	/** Indicates that the lines have been modified */
	private void fireLinesModified()
		{ for(LinesListener listener : listeners) listener.linesModified(); }
}