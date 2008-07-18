// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.shared.parameters;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
	public AbstractParameter getParameter(Integer loc)
		{ try { return (AbstractParameter)getComponent(2*loc+1); } catch(Exception e) { return null; } }
	
	/** Checks to make sure the parameter pane is completed */
	public boolean isCompleted()
	{
		boolean completed = true;
		for(int i=0; i<getComponentCount()/2; i++)
		{
			AbstractParameter parameter = getParameter(i);
			boolean parameterCompleted = parameter.getValue()!=null;
			parameter.setHighlight(!parameterCompleted);
			completed &= parameterCompleted;
		}
		return completed;
	}
}