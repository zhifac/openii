package org.mitre.flexidata.ygg.view.shared.parameters;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.mitre.flexidata.ygg.view.Consts;

/** Edit field parameter class */
public class EditFieldParameter extends AbstractParameter
{
	/** Stores the text field */
	private JTextField textField = new JTextField();
	
	/** Constructs the edit field parameter */
	public EditFieldParameter(String name, String value, boolean editable)
	{
		super(name);
		setBorder(new EmptyBorder(2,0,2,0));

		// Constructs the text field
		textField.setEditable(editable);
		textField.setText(value);
		textField.setFont(Consts.PARAMETER_EDIT_FONT);
		add(textField);
	}

	/** Returns the parameter value */
	public String getValue()
	{
		String value = textField.getText();
		return value==null || value.length()==0 ? null : value;
	}
	
	/** Sets the parameter value */
	public void setValue(String value)
		{ textField.setText(value); }
	
	/** Highlights the parameter */
	public void setHighlight(boolean highlight)
		{ textField.setBackground(highlight ? Consts.YELLOW : Consts.WHITE); }
}