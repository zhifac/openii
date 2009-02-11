// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.heatmap;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.model.SchemaManager;
import org.mitre.harmony.model.MappingManager;
import org.mitre.schemastore.model.Schema;

/**
 * Displays the heatmap for Harmony
 * @author MDMORSE
 */
public class HeatMapPane extends JPanel
{
	/** Initializes About dialog */
	public HeatMapPane()
	{
		//init match variables.
		//ArrayList<MappingCell> cells = MappingCellManager.getMappingCells();
		ArrayList<Integer> schemas = MappingManager.getSchemas();
		//Assume only 2 schemas being matched.
		Schema schema1 = SchemaManager.getSchema(schemas.get(0));
		Schema schema2 = SchemaManager.getSchema(schemas.get(1));
		
		HeatMap hm = new HeatMap();
		hm.setUp(schema1, schema2);
		hm.setFocusable(true);
		hm.addKeyListener(hm);
		hm.addMouseListener(hm);
		hm.addMouseMotionListener(hm);
		setBorder(new EmptyBorder(10,10,10,10));
		setLayout(new BorderLayout());
		add(hm);
   	}
}
