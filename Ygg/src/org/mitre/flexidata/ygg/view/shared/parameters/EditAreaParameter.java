package org.mitre.flexidata.ygg.view.shared.parameters;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.flexidata.ygg.view.Consts;

/** Edit area parameter class */
public class EditAreaParameter extends AbstractParameter
{
	/** Stores the text area */
	private JTextArea textArea = new JTextArea();
	
	/** Constructs the edit area parameter */
	public EditAreaParameter(String name, String value, boolean editable)
	{
		super(name);
		setBorder(new EmptyBorder(2,0,2,0));

		// Constructs the text area
		textArea.setText(value);
		textArea.setFont(Consts.PARAMETER_EDIT_FONT);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		// Create a scroll pane in which to store the text area
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);

		// Adjust items if not editable
		if(!editable)
		{
			textArea.setEditable(false);
			textArea.setBackground(new Color(0xeeeeee));
			scrollPane.setBorder(new LineBorder(new Color(0xccccff)));
		}
	}

	/** Returns the parameter value */
	public String getValue()
	{
		String value = textArea.getText();
		return value==null || value.length()==0 ? null : value;
	}
	
	/** Sets the parameter value */
	public void setValue(String value)
		{ textArea.setText(value); }
	
	/** Highlights the parameter */
	public void setHighlight(boolean highlight)
		{ textArea.setBackground(highlight ? Consts.YELLOW : Consts.WHITE); }
}

