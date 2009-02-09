// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony;

import java.awt.Toolkit;

import javax.swing.JFrame;

import org.mitre.harmony.model.ConfigManager;
import org.mitre.harmony.model.MappingListener;
import org.mitre.harmony.model.MappingManager;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.harmony.view.harmonyPane.HarmonyFrame;

/** Main Harmony class */
public class Harmony extends JFrame implements MappingListener
{
	// Constants indicating the schema roles
	static public final Integer LEFT = 0;
	static public final Integer RIGHT = 1;

	/** Constructs the Harmony frame */
    public Harmony()
    {
    	// Place title on application
    	super();
		String mappingName = MappingManager.getMapping().getName();
		setTitle("Harmony Schema Matcher" + (mappingName!=null ? " - " + MappingManager.getMapping().getName() : ""));
    	
    	// Set dialog pane settings
	   	setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/mitre/harmony/view/graphics/SSM.jpg")));
	   	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setContentPane(new HarmonyFrame(this));
	   	setSize(600,500);
		setExtendedState(MAXIMIZED_BOTH);
	   	setLocationRelativeTo(null);
 	   	setVisible(true);

	   	// Add in application listeners
	   	MappingManager.addListener(this);
    }

    /** Adjusts Harmony title to indicate currently opened project file */
	public void mappingModified()
	{
		String mappingName = MappingManager.getMapping().getName();
		setTitle("Harmony Schema Matcher" + (mappingName!=null ? " - " + MappingManager.getMapping().getName() : ""));
	}

	// Unused event listeners
	public void schemaAdded(Integer schemaID) {}
	public void schemaRemoved(Integer schemaID) {}

	/** Launches Harmony */
	static public void main(String args[])
	{
		String serviceAddress = ConfigManager.getParm("schemastore");
		if(serviceAddress==null) serviceAddress = "../SchemaStore/SchemaStore.jar";
		if(SchemaStoreManager.setConnection(serviceAddress))
			new Harmony();
		else System.out.println("(E) Failed to connect to SchemaStore");
	}
}