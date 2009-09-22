// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.mappingPane;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.schemastore.model.MappingSchema;

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
		SchemaTreeImp source = mappingPane.getTree(MappingSchema.LEFT);
		SchemaTreeImp target = mappingPane.getTree(MappingSchema.RIGHT);
		
		// Initialize local parameters
		visible = source.isVisible(sNode) && target.isVisible(tNode);

		// Calculate start and end point for line and set line
		Rectangle sRect = source.getBufferedRowBounds(source.getNodeRow(sNode));
		Point sourcePt = new Point((int)sRect.getMaxX(),(int)sRect.getCenterY());
		Rectangle tRect = target.getBufferedRowBounds(target.getNodeRow(tNode));
		Point targetPt = new Point((int)tRect.getMinX()-2,(int)tRect.getCenterY());
		if(sourcePt.x>mappingPane.getBounds().getCenterX())
			sourcePt.x=(int)mappingPane.getBounds().getCenterX();
		setLine(sourcePt,targetPt);
	}
	
	/** Returns if the mapping cell line is visible */
	boolean isVisible() { return visible; }
}