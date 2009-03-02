package org.mitre.harmony.view.harmonyPane;

import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class HarmonyLookAndFeel extends MetalLookAndFeel  {
	
	
	protected void initSystemColorDefaults(UIDefaults table) 
    { 
        //call the super method and populate the UIDefaults table 
        super.initSystemColorDefaults(table); 

        // Set tool tip background color to light yellow
        table.put("info", new ColorUIResource(255, 255, 180)); 
    } 


}
