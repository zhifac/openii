package org.mitre.harmony.view.dialogs.schemas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.schemastore.model.Schema;

/** Displays a schema item with display options */
class SchemaDisplayItem extends JPanel implements ActionListener, MouseListener
{
	// Constants to define which side the schema should be displayed on
	static final int NONE = 0;
	static final int LEFT = 1;
	static final int RIGHT = 2;
	
	/** Stores the schema for this schema item */
	private Schema schema;

	// Stores the display check boxes
	private JCheckBox leftCheckBox = new JCheckBox();
	private JCheckBox rightCheckBox = new JCheckBox();
	
	/** Constructs the schema display item */
	SchemaDisplayItem(Schema schema, HarmonyModel harmonyModel)
	{			
		this.schema = schema;

		// Initialize the delete option
		URL url = getClass().getResource("/org/mitre/harmony/view/graphics/Delete.jpg");
		JLabel deleteOption = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(url)));
		deleteOption.addMouseListener(this);
		
		// Initialize the left check box
		leftCheckBox.setOpaque(false);
		leftCheckBox.setSelected(harmonyModel.getSelectedInfo().getSchemas(HarmonyConsts.LEFT).contains(schema.getId()));
		leftCheckBox.addActionListener(this);

		// Initialize the right check box
		rightCheckBox.setOpaque(false);
		rightCheckBox.setSelected(harmonyModel.getSelectedInfo().getSchemas(HarmonyConsts.RIGHT).contains(schema.getId()));
		rightCheckBox.addActionListener(this);
		
		// Constructs the schema item
		setMaximumSize(new Dimension(10000,20));
		setOpaque(false);
		setFocusable(false);
		setLayout(new BorderLayout());
		add(new SchemaDisplayRow(deleteOption,leftCheckBox,rightCheckBox,new JLabel(schema.getName())));
	}
	
	/** Returns the schema associated with this display item */
	Schema getSchema() { return schema; }

	/** Returns the side on which to display the schema */
	int getSide() { return leftCheckBox.isSelected() ? LEFT : rightCheckBox.isSelected() ? RIGHT : NONE; }
	
	/** Handles the selection of the check boxes */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==leftCheckBox)
			rightCheckBox.setSelected(false);
		else leftCheckBox.setSelected(false);
	}

	/** Unselects the clicked on schema */
	public void mouseClicked(MouseEvent e)
	{
		Component component = getParent();
		while(!(component instanceof SchemaDialog))
			component = component.getParent();
		((SchemaDialog)component).unselectSchema(schema.getId());
	}

	// Handles the changing of the mouse icon
	public void mouseEntered(MouseEvent arg0) { setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
	public void mouseExited(MouseEvent arg0) { setCursor(Cursor.getDefaultCursor()); }
	
	// Unused mouse listener events
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}