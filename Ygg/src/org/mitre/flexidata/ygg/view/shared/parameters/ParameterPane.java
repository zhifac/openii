// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.shared.parameters;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mitre.flexidata.ygg.view.Consts;

/** Class for displaying a list of parameters */
public class ParameterPane extends JPanel
{
	// Colors used in the parameter pane
	static public final Color highlightColor = new Color(0xffffaa);		
	
	/** Constructs the parameter pane */
	public ParameterPane()
	{
		setOpaque(false);
		setLayout(new GridBagLayout());		
	}
	
	/** Adds a parameter to the pane */
	public void addParameter(AbstractParameter parameter)
	{
		// Initialize the parameter label
		JLabel parmLabel = new JLabel(parameter.getName() + ": ");
		parmLabel.setFont(Consts.PARAMETER_FONT);
		
		// Position the parameter label
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = getComponentCount()/2;
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;
		add(parmLabel,constraints);
			
		// Position the parameter component
		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
		if(parameter instanceof EditAreaParameter)
			{ constraints.fill = GridBagConstraints.BOTH; constraints.weighty = 1; }
		add(parameter,constraints);
	}
	
	/** Returns the requested parameter */
	public AbstractParameter getParameter(String name)
	{
		for(int i=0; i<getComponentCount()/2; i++)
			if(((JLabel)getComponent(2*i)).getText().equals(name+": "))
				return (AbstractParameter)getComponent(2*i+1);
		return null;
	}
	
	/** Returns the list of available parameters */
	public ArrayList<String> getParameters()
	{
		ArrayList<String> parameters = new ArrayList<String>();
		for(int i=0; i<getComponentCount()/2; i++)
			parameters.add(((JLabel)getComponent(2*i)).getText().replaceAll(": ",""));
		return parameters;
	}
}