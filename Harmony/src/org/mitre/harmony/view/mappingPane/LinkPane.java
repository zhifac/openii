// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.mappingPane;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Holds link pane which manages display of all links between schemas
 * @author CWOLF
 */
class LinkPane extends JPanel
{
	/** Initializes the link pane */
	LinkPane(SchemaTreeImp leftTree, SchemaTreeImp rightTree)
	{
		new MappingLines(leftTree,rightTree);		
		setOpaque(false);
	}

	/** Paints all links */
	public void paint(Graphics g)
		{ super.paint(g); MappingLines.mappingLines.paint(g); }
}
