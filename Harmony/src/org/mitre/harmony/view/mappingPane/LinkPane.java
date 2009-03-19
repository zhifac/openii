// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.mappingPane;

import java.awt.Graphics;

import javax.swing.JPanel;

import org.mitre.harmony.model.HarmonyModel;

/**
 * Holds link pane which manages display of all links between schemas
 * @author CWOLF
 */
public class LinkPane extends JPanel
{
	/** Initializes the link pane */
	public LinkPane(SchemaTreeImp leftTree, SchemaTreeImp rightTree, HarmonyModel harmonyModel)
	{
		new MappingLines(leftTree,rightTree, harmonyModel);		
		setOpaque(false);
	}

	/** Paints all links */
	public void paint(Graphics g)
		{ super.paint(g); MappingLines.mappingLines.paint(g); }
}
