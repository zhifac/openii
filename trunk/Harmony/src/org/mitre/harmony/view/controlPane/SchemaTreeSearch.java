// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.controlPane;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

import org.mitre.harmony.model.HarmonyModel;

/**
 * Handles all keyword searching done of schema tree
 * @author CWOLF
 */
public class SchemaTreeSearch extends JPanel implements KeyListener
{
	/** Indicates the side of the Harmony to which this search is tied */
	private Integer side;

	/** Field for storing the search string */
	private JTextField searchField;
	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Constructs the schema tree search field */
	public SchemaTreeSearch(Integer side, HarmonyModel harmonyModel)
	{
		this.side = side;
		this.harmonyModel = harmonyModel;
		searchField = new JTextField();
		
		// Lay out search box
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		add(new JLabel(" Search: "));
		add(searchField);

		// Add listeners
		searchField.addKeyListener(this);
	}

	/** Update matches every time a new search keyword is entered */
	public void keyTyped(KeyEvent e)
	{
		if(e.getKeyChar() == KeyEvent.VK_ENTER)
			harmonyModel.getSearchManager().runQuery(side, searchField.getText());
	}
	
	/** Unused listener actions */
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}
