// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.mappingPane;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.filters.FilterManager;
import org.mitre.harmony.view.schemaTree.SchemaTree;
import org.mitre.schemastore.model.MappingCell;

/**
 * Stores the info on mapping cell's lines
 * @author CWOLF
 */
class MappingCellLines
{
	// Defines line colors used in displaying the mapping cells
	private static Color BLACK = Color.black;
	private static Color BLUE = Color.blue;
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

	/** Private class for storing a pair of tree nodes */
	private class TreeNodePair
	{
		// Stores the pair of tree nodes
		private DefaultMutableTreeNode leftNode, rightNode;
		
		/** Constructs the tree node pair */
		private TreeNodePair(DefaultMutableTreeNode leftNode, DefaultMutableTreeNode rightNode)
			{ this.leftNode=leftNode; this.rightNode=rightNode; }
	}
	
	/** Private class for storing pairs of tree nodes */
	private class TreeNodePairs
	{
		/** Stores the list of tree node pairs */
		private ArrayList<TreeNodePair> pairs = new ArrayList<TreeNodePair>();
		
		/** Calculates the score for the pair of nodes */
		private Double getScore(Integer direction, DefaultMutableTreeNode node1, DefaultMutableTreeNode node2)
		{
			Integer element1ID = SchemaTree.getElement(direction.equals(HarmonyConsts.LEFT) ? node1 : node2);
			Integer element2ID = SchemaTree.getElement(direction.equals(HarmonyConsts.LEFT) ? node2 : node1);
			Integer mappingID = harmonyModel.getMappingManager().getMappingCellID(element1ID, element2ID);
			return mappingID==null ? 0.0 : harmonyModel.getMappingManager().getMappingCell(mappingID).getScore();
		}
		
		/** Constructs the tree node pairs */
		private TreeNodePairs(Integer leftID, Integer rightID)
		{
			// Retrieve the left and right schema trees
			SchemaTreeImp leftTree = mappingPane.getTree(HarmonyConsts.LEFT);
			SchemaTreeImp rightTree = mappingPane.getTree(HarmonyConsts.RIGHT);
			
			// Create the list of all node pairs
			for(DefaultMutableTreeNode leftNode : leftTree.getSchemaElementNodes(leftID))
				for(DefaultMutableTreeNode rightNode : rightTree.getSchemaElementNodes(rightID))
					pairs.add(new TreeNodePair(leftNode,rightNode));
			
			// Only proceed if hierarchical filtering is on and more than one node pair exists
			if(!harmonyModel.getFilters().getFilter(FilterManager.HIERARCHY_FILTER)) return;
			if(pairs.size()<2) return;
			
			// Eliminate all node pairs where there exists a better hierarchical match
			PAIR1_LOOP: for(int loc1=0; loc1<pairs.size(); loc1++)
				PAIR2_LOOP: for(int loc2=loc1+1; loc2<pairs.size(); loc2++)
				{
					// Define the two tree nodes being compared
					TreeNodePair pair1 = pairs.get(loc1);
					TreeNodePair pair2 = pairs.get(loc2);
					
					// Identify the focal node and matched nodes
					Integer direction = null;
					DefaultMutableTreeNode focalNode=null, match1=null, match2=null;
					if(pair1.leftNode.equals(pair2.leftNode))
					{
						direction=HarmonyConsts.LEFT; focalNode=pair1.leftNode;
						match1=pair1.rightNode; match2=pair2.rightNode;
					}
					else if(pair1.rightNode.equals(pair2.rightNode))
					{
						direction=HarmonyConsts.RIGHT; focalNode=pair1.rightNode;
						match1=pair1.leftNode; match2=pair2.leftNode;
					}
					if(focalNode==null) continue;

					// Shift the matched nodes to the first unique parent node
					while(SchemaTree.getElement(match1).equals(SchemaTree.getElement(match2)))
					{
						match1 = (DefaultMutableTreeNode)match1.getParent();
						match2 = (DefaultMutableTreeNode)match2.getParent();
						if(SchemaTree.getElement(match1)==null || SchemaTree.getElement(match2)==null) break;
					}
					
					// Identify the better matched node based on context
					while(focalNode.getParent()!=null)
					{
						focalNode = (DefaultMutableTreeNode)focalNode.getParent();
						Double pair1Score = getScore(direction,focalNode,match1);
						Double pair2Score = getScore(direction,focalNode,match2);
						if(pair1Score>pair2Score+0.1) { pairs.remove(loc2--); break PAIR2_LOOP; }
						if(pair2Score>pair1Score+0.1) { pairs.remove(loc1--); break PAIR1_LOOP; }
					}
				}
		}
	}
	
	/** Calculates all of the lines connecting the specified left and right nodes */
	private void getLines(Integer leftID, Integer rightID)
	{
		// Retrieve the left and right schema trees
		SchemaTreeImp leftTree = mappingPane.getTree(HarmonyConsts.LEFT);
		SchemaTreeImp rightTree = mappingPane.getTree(HarmonyConsts.RIGHT);

		// Cycle through all combination of source and target nodes
		TreeNodePairs nodePairs = new TreeNodePairs(leftID,rightID);
		for(TreeNodePair pair : nodePairs.pairs)
		{
			// Only create lines if they are within the specified depths
			if(!harmonyModel.getFilters().isVisibleNode(HarmonyConsts.LEFT,pair.leftNode)) continue;
			if(!harmonyModel.getFilters().isVisibleNode(HarmonyConsts.RIGHT,pair.rightNode)) continue;
			
			// Only create lines if they are visible on the screen (saves processing time)
			Integer leftRow = leftTree.getNodeRow(pair.leftNode);
			Integer rightRow = rightTree.getNodeRow(pair.rightNode);
			if(leftRow<leftTree.firstVisibleRow && rightRow<rightTree.firstVisibleRow) continue;
			if(leftRow>leftTree.lastVisibleRow && rightRow>rightTree.lastVisibleRow) continue;
			
			// Add line linking the left and right nodes
			if(lines!=null) lines.add(new MappingCellLine(mappingPane,pair.leftNode,pair.rightNode));
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
			MappingCell mappingCell = harmonyModel.getMappingManager().getMappingCell(mappingCellID);
			getLines(mappingCell.getFirstInput(),mappingCell.getOutput());
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
		MappingCell mappingCell = harmonyModel.getMappingManager().getMappingCell(mappingCellID);
		double conf = mappingCell.getScore();
    	if(harmonyModel.getSelectedInfo().isMappingCellSelected(mappingCellID)) return BLUE;
    	else if(mappingCell.isValidated()) return BLACK;
    	else if(conf>=1) return GREEN;
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