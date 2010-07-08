/**
 * 
 */
package org.mitre.harmony.view.mappingPane;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.harmony.model.HarmonyConsts;


/**
 * @author KZHENG
 *
 */
public class FunctionMappingCellLines{

	/**
	 * 
	 */
	public FunctionMappingCellLines() {
		// TODO Auto-generated constructor stub
	}
	
	/** Indicates if the nodes are currently hidden under the parent node */
	private boolean visible;
	
	/** for storing all line segments */
	private LinkedList<Line2D.Double> lines = new LinkedList<Line2D.Double>(); 
	
	public LinkedList<Line2D.Double> getLines(){
		return lines;
	}

	/** Initialize the line */
	//FunctionMappingCellLines(MappingPane mappingPane, DefaultMutableTreeNode[] sNode, DefaultMutableTreeNode tNode)
	FunctionMappingCellLines(MappingPane mappingPane, Integer[] sNodeId, Integer tNodeId)
	{		
		// Retrieve the source and target schema trees
		SchemaTreeImp leftTree = mappingPane.getTree(HarmonyConsts.LEFT);
		SchemaTreeImp rightTree = mappingPane.getTree(HarmonyConsts.RIGHT);
		
		LinkedList nodeArrayList = new LinkedList();
	    
		for(int i=0; i< sNodeId.length; i++){
			if(leftTree!=null){
				ArrayList<DefaultMutableTreeNode> leftN = leftTree.getSchemaElementNodes(sNodeId[i]);
				nodeArrayList.add(leftN);
				
			}
		}
		
		ArrayList<DefaultMutableTreeNode> rightNodes = null;
		rightNodes = rightTree.getSchemaElementNodes(tNodeId);
		
		// get average Y and X for sources 
		
		int midX=0, midY=0;
		Rectangle sRectangle;
		Component functionPane = mappingPane.getFunctionPane();
		
		//end point
		Rectangle tRect = rightTree.getBufferedRowBounds(rightTree.getNodeRow(rightNodes.get(0)));
		Point tPt = new Point(functionPane.getX()+functionPane.getWidth(),(int)tRect.getCenterY());

		
		for(int i=0; i<sNodeId.length; i++){
			sRectangle = leftTree.getBufferedRowBounds(leftTree.getNodeRow(((ArrayList<DefaultMutableTreeNode>)nodeArrayList.get(i)).get(0)));
			midY = midY + (int)sRectangle.getCenterY();
			midX = functionPane.getWidth() + functionPane.getWidth()/2;
		}
		//get the middle Y point
		midY =(midY/sNodeId.length + (int)tRect.getCenterY())/2;
		
		//for input lines
		for(int i=0; i<sNodeId.length; i++){
			visible = leftTree.isVisible(((ArrayList<DefaultMutableTreeNode>)nodeArrayList.get(0)).get(0)) && rightTree.isVisible(rightNodes.get(0));
			
			// Calculate start and end point for line and set line
			Rectangle sRect = leftTree.getBufferedRowBounds(leftTree.getNodeRow(((ArrayList<DefaultMutableTreeNode>)nodeArrayList.get(i)).get(0)));
			
			Point sourcePt = new Point(functionPane.getX(), (int)sRect.getCenterY());
			Point targetPt = new Point(midX, midY);  		
	
			// Define the mapping cell line
			Line2D.Double linesSegment = new Line2D.Double();
			linesSegment.setLine(sourcePt,targetPt);
			lines.add(linesSegment);
		}
		
		//for output line

		Line2D.Double sLinesSegment = new Line2D.Double();
		sLinesSegment.setLine(new Point(midX, midY),tPt);
		lines.add(sLinesSegment);
	}
	
	/** Returns if the mapping cell line is visible */
	boolean isVisible() { return visible; }

}
