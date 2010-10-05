// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.harmony.view;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.harmony.view.harmonyPane.HarmonyFrame;

/** Class for displaying the Harmony Applet */
public class HarmonyApplet extends Applet
{
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;

	/** Constructs the applet */
	public void init()
	{
		// Retrieve the applet frame
		Container parent = getParent();
		while(!(parent instanceof Frame))
			parent = parent.getParent();
		Frame frame = (Frame)parent;
		
		// Generate the Harmony pane
		harmonyModel = new HarmonyModel(frame);
		JPanel harmonyPane = new JPanel();
		harmonyPane.setBorder(new CompoundBorder(new EmptyBorder(2,2,2,2),new LineBorder(Color.gray)));
		harmonyPane.setLayout(new BorderLayout());
		harmonyPane.add(new HarmonyFrame(harmonyModel),BorderLayout.CENTER);

		// Display Harmony in the frame
		setLayout(new BorderLayout());
		add(harmonyPane,BorderLayout.CENTER);

		// Connect to the remote schema store client
		SchemaStoreManager.init(this);
	}
}