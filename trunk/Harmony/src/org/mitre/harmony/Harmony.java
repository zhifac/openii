// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.mitre.harmony.model.ConfigManager;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.harmony.model.mapping.MappingListener;
import org.mitre.harmony.view.dialogs.mappings.SaveMappingDialog;
import org.mitre.harmony.view.harmonyPane.HarmonyFrame;
import org.mitre.schemastore.client.Repository;

/** Main Harmony class */
public class Harmony extends JFrame implements MappingListener, WindowListener
{
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Constructs the Harmony frame */
    public Harmony()
    {
    	super();
    	harmonyModel = new HarmonyModel(this);
    	
    	// Place title on application
		String mappingName = harmonyModel.getMappingManager().getMapping().getName();
		setTitle("Harmony Schema Matcher" + (mappingName!=null ? " - " + harmonyModel.getMappingManager().getMapping().getName() : ""));
    	
    	// Set dialog pane settings
	   	setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/mitre/harmony/view/graphics/SSM.jpg")));
	   	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setContentPane(new HarmonyFrame(harmonyModel));
	   	setSize(600,500);
		setExtendedState(MAXIMIZED_BOTH);
	   	setLocationRelativeTo(null);
 	   	setVisible(true);

	   	// Add in application listeners
 	   	harmonyModel.getMappingManager().addListener(this);
		addWindowListener(this);
    }

    /** Adjusts Harmony title to indicate currently opened project file */
	public void mappingModified()
	{
		String mappingName = harmonyModel.getMappingManager().getMapping().getName();
		setTitle("Harmony Schema Matcher" + (mappingName!=null ? " - " + harmonyModel.getMappingManager().getMapping().getName() : ""));
	}

	/** Disposes of the Harmony frame */
	public void dispose()
	{
		int option = 1;
		if(harmonyModel.getMappingManager().isModified())
    		option = JOptionPane.showConfirmDialog(harmonyModel.getBaseFrame(),
    			"This mapping has been modified.  Do you want to save changes?",
				"Save Mapping", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE);
		if(option==2) return;
		if(option==0) new SaveMappingDialog(harmonyModel);
		super.dispose();
	}
	
	/** Forces graceful closing of Harmony */
	public void windowClosing(WindowEvent event) { dispose(); }

	// Unused event listeners
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
	public void schemaAdded(Integer schemaID) {}
	public void schemaModified(Integer schemaID) {}
	public void schemaRemoved(Integer schemaID) {}

	/** Launches Harmony */
	static public void main(String args[])
	{
		// Retrieve the repository information
		String typeParm = ConfigManager.getParm("repository.type");
		String locationParm = ConfigManager.getParm("repository.location");
		String databaseParm = ConfigManager.getParm("repository.database");
		String usernameParm = ConfigManager.getParm("repository.username");
		String passwordParm = ConfigManager.getParm("repository.password");
		
		// Create a repository connection
		Integer type = typeParm.equals("service")?Repository.SERVICE:typeParm.equals("postgres")?Repository.POSTGRES:Repository.DERBY;
		URI uri = null;	try { uri = new URI(locationParm); } catch(Exception e) {}
		Repository repository = new Repository(type,uri,databaseParm,usernameParm,passwordParm);

		// Launch Harmony
		if(SchemaStoreManager.setConnection(repository)) new Harmony();
		else System.out.println("(E) Failed to connect to SchemaStore");
	}
}