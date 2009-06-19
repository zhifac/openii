package org.mitre.harmony.view.dialogs.properties;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/** Displays a schema display row (two check box columns and schema) */
class SchemaDisplayRow extends JPanel
{
	/** Creates a pane to center the provided component */
	private JComponent getCenteredPane(JComponent component)
	{
		if(component instanceof JLabel)
			((JLabel)component).setHorizontalAlignment(SwingConstants.CENTER);
		if(component instanceof JCheckBox)
			((JCheckBox)component).setHorizontalAlignment(SwingConstants.CENTER);
		return component;
	}
	
	/** Constructs the schema row */
	SchemaDisplayRow(JComponent deleteOption, JComponent checkbox1, JComponent checkbox2, JComponent schema)
	{
		// Create the pane merging the check box items
		JPanel checkboxPane = new JPanel();
		checkboxPane.setBorder(new EmptyBorder(0,0,0,5));
		checkboxPane.setPreferredSize(new Dimension(70,20));
		checkboxPane.setOpaque(false);
		checkboxPane.setLayout(new GridLayout(1,2));
		checkboxPane.add(getCenteredPane(checkbox1));
		checkboxPane.add(getCenteredPane(checkbox2));

		// Create the left pane
		JPanel leftPane = new JPanel();
		leftPane.setOpaque(false);
		leftPane.setPreferredSize(new Dimension(85,20));
		leftPane.setLayout(new BorderLayout());
		leftPane.add(deleteOption,BorderLayout.WEST);
		leftPane.add(checkboxPane,BorderLayout.EAST);
		
		// Create the row pane
		setOpaque(false);
		setLayout(new BorderLayout());
		add(leftPane,BorderLayout.WEST);
		add(schema,BorderLayout.CENTER);
	}
}
