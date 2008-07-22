package org.mitre.flexidata.ygg.view.shared.parameters;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/** Abstract parameter class */
abstract public class AbstractParameter extends JPanel
{
	/** Stores the parameter name */
	private String name;
	
	/** Constructs the parameter */
	protected AbstractParameter(String name)
	{
		this.name = name;
		setOpaque(false);
		setLayout(new BorderLayout());
	}
	
	/** Returns the parameter name */
	public String getName() { return name; }
	
	/** Returns the parameter value */
	abstract public String getValue();
	
	/** Sets the parameter value */
	abstract public void setValue(String value);
	
	/** Highlights the parameter */
	abstract public void setHighlight(boolean highlight);
}