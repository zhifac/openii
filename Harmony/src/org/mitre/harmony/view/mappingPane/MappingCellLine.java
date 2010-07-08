// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.mappingPane;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.harmony.model.HarmonyConsts;

/**
 * Stores the info on a single line associated with a schema tree
 * @author CWOLF
 */
class MappingCellLine extends Line2D.Double
{
	/** Indicates if the nodes are currently hidden under the parent node */
	private boolean visible;

	/** Initialize the line */
	MappingCellLine(MappingPane mappingPane, DefaultMutableTreeNode sNode, DefaultMutableTreeNode tNode)
	{		
		// Retrieve the source and target schema trees
		SchemaTreeImp source = mappingPane.getTree(HarmonyConsts.LEFT);
		SchemaTreeImp target = mappingPane.getTree(HarmonyConsts.RIGHT);
		
		// Initialize local parameters
		visible = source.isVisible(sNode) && target.isVisible(tNode);

		// Calculate start and end point for line and set line
		Component functionPane = mappingPane.getFunctionPane();
		Rectangle sRect = source.getBufferedRowBounds(source.getNodeRow(sNode));
		Rectangle tRect = target.getBufferedRowBounds(target.getNodeRow(tNode));
		Point sourcePt = new Point(functionPane.getX(), (int)sRect.getCenterY());
		Point targetPt = new Point(functionPane.getX()+functionPane.getWidth(),(int)tRect.getCenterY());  		

		// Define the mapping cell line
		setLine(sourcePt,targetPt);
	}
	
	/** for line segment in function mapping cell line */
	MappingCellLine(Point sourcePt, Point targetPt)
	{		
		// Define the mapping cell line
		setLine(sourcePt,targetPt);
	}
	
	/** Returns if the mapping cell line is visible */
	boolean isVisible() { return visible; }
	
	
}
