package org.mitre.flexidata.ygg.view.shared.parameters;

import javax.swing.JLabel;

import org.mitre.flexidata.ygg.view.Consts;

/** Text parameter class */
public class TextParameter extends AbstractParameter
{
	/** Stores the label */
	private JLabel label = new JLabel();
	
	/** Constructs the text parameter */
	public TextParameter(String name, String value)
	{
		super(name);
		label.setText(value);
		label.setFont(Consts.PARAMETER_FONT);
		add(label);
	}
	
	/** Returns the parameter value */
	public String getValue() { return label.getText(); }
	
	/** Sets the parameter value */
	public void setValue(String value)
		{ label.setText(value); }
	
	/** Highlights the parameter */
	public void setHighlight(boolean highlight) {}
}