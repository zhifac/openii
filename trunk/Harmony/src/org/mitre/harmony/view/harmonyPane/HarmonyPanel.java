// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.harmonyPane;

import java.awt.Component;

import javax.swing.JPanel;

import org.mitre.harmony.model.HarmonyModel;

/**
 * Stores the base component and model for all Harmony panels
 * @author CWOLF
 */
public class HarmonyPanel extends JPanel
{
	/** Stores the base component */
	private Component baseComponent = null;
	
	/** Stores the Harmony model */
	private HarmonyModel model = null;
	
	/** Constructs the Harmony panel */
	public HarmonyPanel(Component baseComponent, HarmonyModel model)
		{ this.baseComponent = baseComponent; this.model = model; }

	/** Constructs the Harmony panel from a parent panel */
	public HarmonyPanel(HarmonyPanel parent)
		{ baseComponent = parent.baseComponent; model = parent.model; }
	
	/** Gets the Harmony base component */
	protected Component getBaseComponent()
		{ return baseComponent; }
	
	/** Gets the Harmony model */
	protected HarmonyModel getModel()
		{ return model; }
}