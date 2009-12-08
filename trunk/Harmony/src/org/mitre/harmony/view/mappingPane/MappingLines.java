// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.mappingPane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JViewport;
import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.filters.FilterManager;
import org.mitre.harmony.model.filters.FiltersListener;
import org.mitre.harmony.model.mapping.MappingCellListener;
import org.mitre.harmony.model.selectedInfo.SelectedInfoListener;
import org.mitre.harmony.view.schemaTree.SchemaTree;
import org.mitre.harmony.view.schemaTree.SchemaTreeListener;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/**
 * Stores all lines currently associated with the mapping
 * @author CWOLF
 */
public class MappingLines implements MappingCellListener, FiltersListener, SchemaTreeListener, SelectedInfoListener
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
		mappingPane.getTree(MappingSchema.LEFT).addSchemaTreeListener(this);
		mappingPane.getTree(MappingSchema.RIGHT).addSchemaTreeListener(this);
		harmonyModel.getFilters().addListener(this);
		harmonyModel.getSelectedInfo().addListener(this);
		harmonyModel.getMappingCellManager().addListener(this);
	}

	/** Initialize the lines within the schema tree */
	private Hashtable<Integer, MappingCellLines> getLines()
	{
		if(lines == null)
		{
			lines = new Hashtable<Integer, MappingCellLines>();
			for(MappingCell mappingCell : harmonyModel.getMappingCellManager().getMappingCells())
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
	Integer getClosestMappingCellToPoint(Point point)
	{
		Integer bestMappingCell = null;
		double shortestDist = Double.MAX_VALUE;

		// Cycle through all mapping cells
		Enumeration mappingCells = getLines().elements();
		while(mappingCells.hasMoreElements())
		{
			// Examine all non-hidden mapping cells
			MappingCellLines mappingCellLines = (MappingCellLines)mappingCells.nextElement();
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
		}
		
		// Return array of all selected mapping cells
		return selMappingCells;
	}

	/** Collects all elements hierarchically influenced by the specified element */
	private ArrayList<Integer> getHierarchicallyAffectedNodes(Integer side, Integer elementID)
	{
		ArrayList<Integer> affectedNodeIDs = new ArrayList<Integer>();
		for(DefaultMutableTreeNode leftNode : mappingPane.getTree(side).getSchemaElementNodes(elementID))
			for(int i=0; i<leftNode.getChildCount(); i++)
			{
				// Retrieve information about the child node
				DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)leftNode.getChildAt(i);
				Integer schemaID = SchemaTree.getSchema(childNode);
				Integer childID = SchemaTree.getElement(childNode);

				// Store any mapping cells possibly affected
				HierarchicalSchemaInfo schemaInfo = harmonyModel.getSchemaManager().getSchemaInfo(schemaID);
				if(schemaInfo.getParentElements(childID).size()>1)
				{
					affectedNodeIDs.add(childID);
					for(SchemaElement element : schemaInfo.getDescendantElements(childID))
						affectedNodeIDs.add(element.getId());
				}
			}
		return affectedNodeIDs;
	}
	
	/** Updates the hierarchical filter as needed */
	private void updateHierarchicalFilterAsNeeded(List<MappingCell> mappingCells)
	{
		if(harmonyModel.getFilters().getFilter(FilterManager.HIERARCHY_FILTER))
		{
			// Build up list of elements affected by changed mapping cell
			HashSet<Integer> elementIDs = new HashSet<Integer>();
			for(MappingCell mappingCell : mappingCells)
			{
				for(Integer inputID : mappingCell.getInput())
					elementIDs.addAll(getHierarchicallyAffectedNodes(MappingSchema.LEFT, inputID));
				elementIDs.addAll(getHierarchicallyAffectedNodes(MappingSchema.RIGHT, mappingCell.getOutput()));
			}
			
			// Translate list of affected elements into list of mapping cells
			HashSet<Integer> mappingCellIDs = new HashSet<Integer>();
			for(Integer elementID : elementIDs)
				mappingCellIDs.addAll(harmonyModel.getMappingCellManager().getMappingCellsByElement(elementID));
			
			// Update the lines for the affected mappings cells
			for(Integer mappingCellID : mappingCellIDs)
				getLines().get(mappingCellID).updateLines();
		}
	}
	
	/** Handles the addition of a mapping cell */
	public void mappingCellsAdded(List<MappingCell> mappingCells)
	{
		for(MappingCell mappingCell : mappingCells)
			getLines().put(mappingCell.getId(),new MappingCellLines(mappingPane,mappingCell.getId(),harmonyModel));
		updateHierarchicalFilterAsNeeded(mappingCells);
		fireLinesModified();
	}

	/** Handles the modification of a mapping cell */
	public void mappingCellsModified(List<MappingCell> oldMappingCells, List<MappingCell> newMappingCells)
	{
		for(MappingCell newMappingCell : newMappingCells)
		{
			getLines().get(newMappingCell.getId()).updateHidden();
			updateHierarchicalFilterAsNeeded(Arrays.asList(new MappingCell[] {newMappingCell}));
		}
		fireLinesModified();
	}
	
	/** Handles the removal of a mapping cell */
	public void mappingCellsRemoved(List<MappingCell> mappingCells)
	{
		for(MappingCell mappingCell : mappingCells)
			getLines().remove(mappingCell.getId());
		updateHierarchicalFilterAsNeeded(mappingCells);
		fireLinesModified();
	}

	/** Handles the modification of a schema node */
	public void schemaModified(Integer schemaID)
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
		for(Integer mappingCellID : harmonyModel.getMappingCellManager().getMappingCellsByElement(schemaObjectID))
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
		JViewport viewport = mappingPane.getTreeViewport(MappingSchema.LEFT);
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
						if(line.isVisible())
							visibleLines.add(color.getRGB()+","+(int)line.x1+","+(int)line.y1+","+(int)line.x2+","+(int)line.y2);
						else hiddenLines.add((int)line.x1+","+(int)line.y1+","+(int)line.x2+","+(int)line.y2);
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
			String info[] = visibleLine.split(",");
			g.setColor(new Color(Integer.parseInt(info[0])));
			g.drawLine(Integer.parseInt(info[1]),Integer.parseInt(info[2]),
					Integer.parseInt(info[3]),Integer.parseInt(info[4]));
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