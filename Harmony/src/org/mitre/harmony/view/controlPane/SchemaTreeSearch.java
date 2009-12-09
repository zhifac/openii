// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.controlPane;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.filters.ElementPath;
import org.mitre.harmony.model.search.SearchResult;
import org.mitre.harmony.view.mappingPane.MappingPane;
import org.mitre.harmony.view.schemaTree.SchemaTree;

/**
 * Handles all keyword searching done of schema tree
 * @author CWOLF
 */
public class SchemaTreeSearch extends JPanel implements KeyListener, MouseListener
{
	/** Indicates the SchemaTree to which this search is tied */
	private SchemaTree tree;

	/** Field for storing the search string */
	private JTextField searchField = new JTextField();
	
	/** Button for setting focus */
	private JLabel setFocusButton = new JLabel("<html><font color='#0000ff' size=2>Set Focus</font></html>");
	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Constructs the schema tree search field */
	public SchemaTreeSearch(SchemaTree tree, HarmonyModel harmonyModel)
	{
		this.tree = tree;
		this.harmonyModel = harmonyModel;
		
		// Create the label pane
		JPanel labelPane = new JPanel();
		labelPane.setBorder(new EmptyBorder(0,0,0,3));
		labelPane.setLayout(new BorderLayout());
		labelPane.add(new JLabel(" Search:"),BorderLayout.NORTH);
		labelPane.add(setFocusButton,BorderLayout.SOUTH);
		
		// Lay out search box
		setLayout(new BorderLayout());
		add(labelPane, BorderLayout.WEST);
		add(searchField, BorderLayout.CENTER);

		// Add listeners
		searchField.addKeyListener(this);
		setFocusButton.addMouseListener(this);
	}
	
	/** Update matches every time a new search keyword is entered */
	public void keyTyped(KeyEvent e)
	{
		if(e.getKeyChar() == KeyEvent.VK_ENTER)
			harmonyModel.getSearchManager().runQuery(tree.getSide(), searchField.getText());
	}
	
	/** Handles the setting of focus of all all search results */
	public void mouseClicked(MouseEvent e)
	{
		// Identify all matches
		HashMap<Integer,SearchResult> matches = harmonyModel.getSearchManager().getMatches(tree.getSide());
		if(matches.keySet().size()>0)
		{
			// Set focus on all identified matches
			for(Integer match : matches.keySet())
			{
				for(DefaultMutableTreeNode node : tree.getSchemaElementNodes(match))
				{
					Integer schemaID = SchemaTree.getSchema(node);
					ElementPath elementPath = SchemaTree.getElementPath(node);
					harmonyModel.getFilters().addFocus(tree.getSide(), schemaID, elementPath);
				}
			}
		}
	}

	/** Display a hand when hovering over the "Set Focus" button */
	public void mouseEntered(MouseEvent e)
	{
		Container pane = this;
		while(!(pane instanceof MappingPane)) pane = pane.getParent();
		pane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	/** Always changes the mouse back to default when it exits the "Set Focus" button */
	public void mouseExited(MouseEvent e)
	{
		Container pane = this;
		while(!(pane instanceof MappingPane)) pane = pane.getParent();
		pane.setCursor(Cursor.getDefaultCursor());
	}
	
	/** Unused listener actions */
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
