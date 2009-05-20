// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.heatmap;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.schemastore.model.Schema;

/**
 * Displays the heat map for Harmony
 * @author MDMORSE
 */
public class HeatMapPane extends JPanel
{
	/** Stores a reference to the heat map */
	static private HeatMap heatMap = null;
	
	/** Initializes About dialog */
	public HeatMapPane(JComponent parent,HarmonyModel harmonyModel)
	{
		// Retrieves the schemas to display in the heat map
		ArrayList<Integer> schemas = harmonyModel.getMappingManager().getSchemas();
		Schema schema1 = harmonyModel.getSchemaManager().getSchema(schemas.get(0));
		Schema schema2 = harmonyModel.getSchemaManager().getSchema(schemas.get(1));
		
		// Initialize the heat map
		heatMap = new HeatMap(harmonyModel);
		heatMap.setUp(schema1, schema2);
		heatMap.setFocusable(true);
		heatMap.addMouseListener(heatMap);
		heatMap.addMouseMotionListener(heatMap);
	
		// Initialize the heat map pane
		setBorder(new EmptyBorder(10,10,10,10));
		setLayout(new BorderLayout());
		add(heatMap);
				
		// Register heat map keyboard actions
		int keyCodes[] = new int[] {KeyEvent.VK_T, KeyEvent.VK_I, KeyEvent.VK_G, KeyEvent.VK_U, KeyEvent.VK_Y, KeyEvent.VK_Y, KeyEvent.VK_SPACE, KeyEvent.VK_Q};
		for(int keyCode : keyCodes)
		{
			KeyStroke keyStroke = KeyStroke.getKeyStroke((char)keyCode);
			parent.getInputMap(JPanel.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(keyStroke, keyCode);
			parent.getActionMap().put(keyCode, new HeatMapKeyAction(keyCode));				
		}
		
  	}

	/** Class used for handling heat map key actions */
	static private class HeatMapKeyAction extends AbstractAction
	{
		/** Stores the key to which the action is associated */
		private Integer keyCode = null;
		
		/** Constructs the heat map key action */
		private HeatMapKeyAction(Integer keyCode)
			{ this.keyCode = keyCode; }
		
		/** Handles the heat map key action */
		public void actionPerformed(ActionEvent arg0)
		{
			switch(keyCode)
			{
				case KeyEvent.VK_T: heatMap.reset(); break;
		        case KeyEvent.VK_I: heatMap.toggleInfoBox(); break;
		        case KeyEvent.VK_G: heatMap.setUpdate(); break;
		        case KeyEvent.VK_U: heatMap.visualSummary(++heatMap.currentLevelX, heatMap.currentLevelY); break;
		        case KeyEvent.VK_Y: heatMap.visualSummary(heatMap.currentLevelX, ++heatMap.currentLevelY); break;
		        case KeyEvent.VK_Q: heatMap.visualSummary(heatMap.currentLevelX, heatMap.currentLevelY); break;
		        case KeyEvent.VK_SPACE: heatMap.toggleLabels(); break;
			}
		}
	}
}
