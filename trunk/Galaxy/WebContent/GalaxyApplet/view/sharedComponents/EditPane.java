// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.sharedComponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/** Class for editing information */
abstract public class EditPane extends JPanel implements ActionListener
{	
	/** Stores the parent of this editing pane */
	private EditPaneParent parent;
	
	/** Stores the main pane when a secondary pane is being displayed */
	private EditPaneInterface mainPane;
	
	// Tracks various check box settings
	private boolean visible = true;
	private boolean enabled = true;
	private boolean selected = false;
	
	// Stores various objects used by the Edit Pane
	private JLabel title = new JLabel();
	private JCheckBox deleteCheckbox = new JCheckBox("Delete");
	private JPanel editPane = new JPanel();
	public JButton saveButton = new JButton("Save");
	public JButton cancelButton = new JButton("Cancel");
	
	/** Constructs a button pane */
	private JPanel getButtonPane(JButton button)
	{
		// Initialize the button
		button.setBorder(new CompoundBorder(new LineBorder(Color.gray),new EmptyBorder(1,1,1,1)));
		button.setFocusable(false);
		button.addActionListener(this);
		
		// Initialize the button pane
		JPanel pane = new JPanel();
		pane.setOpaque(false);
		pane.setBorder(new EmptyBorder(1,1,1,1));
		pane.setLayout(new BorderLayout());
		pane.add(button,BorderLayout.CENTER);
		return pane;
	}
	
	/** Constructs the Edit Pane */
	public EditPane(EditPaneParent parent)
	{
		this.parent = parent;
		
		// Initialize the delete check box
		deleteCheckbox.setBorder(new EmptyBorder(0,0,0,0));
		deleteCheckbox.setForeground(new Color(150,0,0));
		deleteCheckbox.setHorizontalTextPosition(SwingConstants.LEFT);
		deleteCheckbox.setOpaque(false);
		deleteCheckbox.setFocusable(false);
		deleteCheckbox.setVisible(false);
		
		// Construct the title pane
		JPanel titlePane = new JPanel();
		titlePane.setBackground(new Color((float)0.80,(float)0.88,(float)1.0));
		titlePane.setBorder(new EmptyBorder(1,2,1,2));
		titlePane.setLayout(new BorderLayout());
		titlePane.add(title,BorderLayout.WEST);
		titlePane.add(deleteCheckbox,BorderLayout.EAST);
		
		// Initializes the info pane
		editPane.setBorder(new EmptyBorder(2,2,2,2));
		editPane.setLayout(new BorderLayout());
		editPane.setOpaque(false);
		editPane.setPreferredSize(new Dimension(0,150));
		
		// Constructs the button pane
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Color.white);
		buttonPane.setBorder(new EmptyBorder(1,1,1,1));
		buttonPane.setLayout(new GridLayout(1,2));
		buttonPane.add(getButtonPane(saveButton));
		buttonPane.add(getButtonPane(cancelButton));
		
		// Construct the edit pane
		setBackground(Color.white);
		setBorder(new CompoundBorder(new EmptyBorder(7,0,0,0),new LineBorder(Color.gray)));
		setLayout(new BorderLayout());
		add(titlePane,BorderLayout.NORTH);
		add(editPane,BorderLayout.CENTER);
		add(buttonPane,BorderLayout.SOUTH);
	}
	
	/** Displays the pane for the item in need of editing */
	public void editInfo(String titleText, EditPaneInterface pane, Boolean deleteVisible, Boolean deleteEnabled)
	{
		// Remove old edit pane
		editPane.removeAll();
		
		// Set up the title and info pane
		title.setText(titleText);
		editPane.add(pane);

		// Configure the delete check box
		deleteCheckbox.setSelected(selected = false);
		deleteCheckbox.setVisible(visible = deleteVisible);
		deleteCheckbox.setEnabled(enabled = deleteEnabled);
		deleteCheckbox.addActionListener(this);
		
		// Reset the labeling on the save button
		saveButton.setText("Save");
	}
	
	/** Displays a secondary pane */
	public void displaySecondaryPane(EditPaneInterface secondaryPane, boolean deletable)
	{
		// Alter settings
		deleteCheckbox.setEnabled(true);
		deleteCheckbox.setSelected(false);
		deleteCheckbox.setVisible(deletable);
		saveButton.setText("Back");
		
		// Display the secondary pane
		mainPane = (EditPaneInterface)editPane.getComponent(0);
		editPane.removeAll();
		editPane.add(secondaryPane);
		validate();
	}
	
	/** Handles the saving or canceling of edited information */
	public void actionPerformed(ActionEvent e)
	{
		// If delete check box is changed, modify labeling of button
		if(e.getSource()==deleteCheckbox)
		{	
			if(mainPane==null)
			{
				selected = deleteCheckbox.isSelected();
				saveButton.setText(deleteCheckbox.isSelected()?"Delete":"Save");
			}
		}
		
		// Process information on main pane
		else if(mainPane==null)
		{			
			if(e.getSource()==saveButton)
			{
				EditPaneInterface pane = (EditPaneInterface)editPane.getComponent(0);
				if(!deleteCheckbox.isSelected()) { if(!pane.validatePane()) return; pane.save(); }
				else if(!pane.delete()) return;
			}
			
			// Closes the editor
			parent.doneEditingInfo();
		}
		
		// Process information on secondary pane
		else
		{
			// Transfers information back to the main pane
			if(e.getSource()==saveButton)
			{
				EditPaneInterface pane = (EditPaneInterface)editPane.getComponent(0);
				if(!pane.validatePane()) return;
				mainPane.saveSecondaryInfo(pane,deleteCheckbox.isSelected());
			}
				
			// Alter settings
			deleteCheckbox.setEnabled(enabled);
			deleteCheckbox.setSelected(selected);
			deleteCheckbox.setVisible(visible);
			saveButton.setText("Save");
			mainPane.closeSecondaryPane();
			
			// Display the main pane
			editPane.removeAll();
			editPane.add(mainPane);
			mainPane = null;
			validate(); repaint();
		}
	}
}
