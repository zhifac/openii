package org.mitre.harmony.view.dialogs.matcher.wizard.options;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.matchers.MatcherOption;

public class CheckBox extends JPanel implements ActionListener {
	private JCheckBox element = null;
	private MatcherOption option = null;

	public CheckBox(MatcherOption option) {
		super.setBorder(new EmptyBorder(2,2,2,2));
		super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.option = option;

		element = new JCheckBox(option.getName(), option.getSelected());
		element.setFont(new Font("Arial", Font.PLAIN, 12));
		element.setAlignmentX(Component.LEFT_ALIGNMENT);
		element.addActionListener(this);
		this.add(element);
	}

	public void actionPerformed(ActionEvent event) {
		if (option == null || element == null) { return; }
		option.setSelected(element.isSelected());
	}
}
