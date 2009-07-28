// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.mappingPane;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;

/**
 * Stores the info on mapping cell's lines
 * @author CWOLF
 */
class MappingCellLines
{
	// Defines line colors used in displaying the mapping cells
	private static Color BLACK = Color.black;
	private static Color GRAY = Color.lightGray;
	private static Color BLUE = Color.blue;
	private static Color RED = new Color(0.8f,0.0f,0.0f);
	private static Color GREEN = new Color(0.0f,0.8f,0.0f);
	
	/** Stores the mapping pane to which these lines are associated */
	private MappingPane mappingPane;
	
	/** Stores the mapping cell ID */
	private Integer mappingCellID;
	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Indicates if the mapping cell is hidden from display */
	private Boolean hidden;
	
	/** List of lines associated with the mapping cell */
	private ArrayList<MappingCellLine> lines;
	
	/** Calculates all of the lines connecting the specified left and right nodes */
	private void getLines(Integer leftID, Integer rightID)
	{
		// Retrieve the left and right schema trees
		SchemaTreeImp left = mappingPane.getTree(MappingSchema.LEFT);
		SchemaTreeImp right = mappingPane.getTree(MappingSchema.RIGHT);
		
		// Cycle through all combination of source and target nodes
		for(DefaultMutableTreeNode leftNode : left.getComponentNodes(leftID))
			for(DefaultMutableTreeNode rightNode : right.getComponentNodes(rightID))
			{
				// Only create lines if they are within the specified depths
				if(!harmonyModel.getFilters().isVisibleNode(MappingSchema.LEFT,leftNode)) continue;
				if(!harmonyModel.getFilters().isVisibleNode(MappingSchema.RIGHT,rightNode)) continue;
				
				// Only create lines if they are visible on the screen (saves processing time)
				Integer leftRow = left.getNodeRow(leftNode);
				Integer rightRow = right.getNodeRow(rightNode);
				if(leftRow<left.firstVisibleRow && rightRow<right.firstVisibleRow) continue;
				if(leftRow>left.lastVisibleRow && rightRow>right.lastVisibleRow) continue;
				
				// Add line linking the left and right nodes
				lines.add(new MappingCellLine(mappingPane,leftNode,rightNode));
			}
	}
	
	/** Initializes the mapping cell lines */
	MappingCellLines(MappingPane mappingPane, Integer mappingCellID, HarmonyModel harmonyModel)
	{
		this.mappingPane = mappingPane;
		this.mappingCellID = mappingCellID;
		this.harmonyModel = harmonyModel;
	}
	
	/** Returns the mapping cell ID */
	Integer getMappingCellID()
		{ return mappingCellID; }
	
	/** Returns the list of lines associated with this mapping cell */
	synchronized ArrayList<MappingCellLine> getLines()
	{
		if(!getHidden() && lines==null)
		{
			lines = new ArrayList<MappingCellLine>();
			MappingCell mappingCell = harmonyModel.getMappingCellManager().getMappingCell(mappingCellID);
			getLines(mappingCell.getElement1(),mappingCell.getElement2());
			getLines(mappingCell.getElement2(),mappingCell.getElement1());
		}
		return lines==null ? new ArrayList<MappingCellLine>() : new ArrayList<MappingCellLine>(lines);
	}
	
	/** Returns indication of if the mapping cell's lines are currently hidden */
	synchronized boolean getHidden()
	{
		if(hidden==null)
		{
			hidden = !harmonyModel.getFilters().isVisibleMappingCell(mappingCellID) && !harmonyModel.getSelectedInfo().isMappingCellSelected(mappingCellID);
			if(hidden) lines = null;
		}
		return hidden;
	}

	/** Returns the color associated with this mapping cell */
	Color getColor()
	{
		MappingCell mappingCell = harmonyModel.getMappingCellManager().getMappingCell(mappingCellID);
		double conf = mappingCell.getScore();
    	if(harmonyModel.getSelectedInfo().isMappingCellSelected(mappingCellID)) return BLUE;
    	else if(mappingCell.getValidated())
    		{ if(conf>0) return harmonyModel.getMappingCellManager().isMappingCellFinished(mappingCellID) ? GRAY : BLACK; return RED; }
    	else if(conf>=1) return GREEN;
    	else if(conf<-0.333) return RED;
    	else if(conf<0.333) return new Color(0.8f,0.8f-1.2f*(0.333f-(float)conf),0.0f);
	    else return new Color(0.8f-1.2f*((float)conf-0.333f),0.8f,0.0f);
	}
	
	/** Calculates all of the lines associated with this mapping cell */
	void updateLines()
		{ lines = null; }
	
	/** Unset hidden variable to force recalculation */
	void updateHidden()
		{ hidden = null; }
}