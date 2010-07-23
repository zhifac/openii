package org.mitre.harmony.view.dialogs.matcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.matchers.MatcherOption;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.harmony.view.dialogs.matcher.wizard.Wizard;
import org.mitre.harmony.view.dialogs.matcher.wizard.WizardPanel;

public class MatcherOptionsPane extends WizardPanel
{
	/** Stores the matcher associated with these options */
	private MatchVoter matcher;
	
	/** Generates an option pane */
	private class OptionPane extends JPanel implements ActionListener
	{
		/** Stores the matcher option */
		private MatcherOption option;

		/** Constructs the option pane */
		public OptionPane(MatcherOption option)
		{
			this.option = option;

			// Create the option check box
			JCheckBox checkbox = new JCheckBox(option.getName().replaceAll("([a-z])([A-Z])","$1 $2"),option.isSelected());
			checkbox.setFont(new Font("Arial", Font.PLAIN, 12));
			checkbox.setAlignmentX(Component.LEFT_ALIGNMENT);
			checkbox.addActionListener(this);

			// Create the option pane
			setBorder(new EmptyBorder(2,2,2,2));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			add(checkbox);
		}

		/** Handles changes to the matcher option */
		public void actionPerformed(ActionEvent e)
			{ option.setSelected(((JCheckBox)e.getSource()).isSelected()); }
	}
	
	/** Constructs the type pane */
    public MatcherOptionsPane(Wizard wizard, MatchVoter matcher)
    {
    	super(wizard);
    	this.matcher = matcher;
    	
    	// Generate the title for this panel
		JLabel titleLabel = new JLabel("<html><u>" + matcher.getName() + " Options</u></html>");
		titleLabel.setBorder(new EmptyBorder(1,0,0,2));
		
		// Generate the options pane
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		for(MatcherOption option : matcher.getOptions())
		{
			// Handles the various types of options
			switch(option.getType())
				{ case CHECKBOX: optionsPanel.add(new OptionPane(option)); break; }
		}

    	// Generate the panel
		setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10),new CompoundBorder(new LineBorder(Color.lightGray),new EmptyBorder(5,5,5,5))));
		setLayout(new BorderLayout());
		add(titleLabel, BorderLayout.NORTH);
		add(optionsPanel, BorderLayout.CENTER);
    }
    
    /** Returns the ID for this pane */ @Override
    public String getID()
    	{ return getClass().getSimpleName() + " - " + matcher.getName(); }
    
    /** Returns the matcher associated with this option pane */
    public MatchVoter getMatcher()
    	{ return matcher; }
}