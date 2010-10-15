// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.exporters;

import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.HarmonyModel.InstantiationType;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.harmony.view.dialogs.widgets.RemotePorterDialog;
import org.mitre.schemastore.porters.Exporter;
import org.mitre.schemastore.porters.PorterType;

/**
 * Abstract dialog for exporting
 * @author CWOLF
 */
public abstract class AbstractExportDialog
{	
	/** Private class for creating an exporter file filter */
	private static class ExportFileFilter extends FileFilter
	{
		/** Stores the porter */
		private Exporter exporter = null;
		
		/** Constructs the project file filter */
		private ExportFileFilter(Exporter exporter)
			{ this.exporter = exporter; }

		/** Indicates if the file should be accepted */
		public boolean accept(File file)
		{
			if(file.isDirectory()) return true;
			if(file.toString().endsWith(exporter.getFileType())) return true;
			return false;
		}

		/** Provides the exporter file description */
		public String getDescription()
			{ return exporter.getName() + "(" + exporter.getFileType() + ")"; }
	}
	
	/** Abstract class for defining the type of exporters */
	abstract public PorterType getPorterType();

	/** Abstract class for retrieve the data */
	abstract public ArrayList<Object> getData(HarmonyModel harmonyModel);
	
	/** Abstract class for exporting to the specified file */
	abstract protected void export(HarmonyModel harmonyModel, Exporter exporter, File file) throws IOException;
	
	/** Returns the dialog title */
	private String getDialogTitle()
	{
		if(getPorterType()==PorterType.SCHEMA_EXPORTERS) return "Export Schema";
		if(getPorterType()==PorterType.PROJECT_EXPORTERS) return "Export Project";
		if(getPorterType()==PorterType.MAPPING_EXPORTERS) return "Export Mapping";
		return null;
	}
		
	/** Exports via local client */
	private void exportViaLocalClient(HarmonyModel harmonyModel)
	{
		// Initialize the file chooser
		JFileChooser chooser = new JFileChooser(harmonyModel.getPreferences().getExportDir());
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setAcceptAllFileFilterUsed(false);

		// Set up file filters for the various project exporters
		ArrayList<Exporter> exporters = SchemaStoreManager.getPorters(getPorterType());
		for(Exporter exporter : exporters)
			chooser.addChoosableFileFilter(new ExportFileFilter(exporter));

		// Display the dialog for selecting which exporter to use
		if(chooser.showDialog(harmonyModel.getBaseFrame(),getDialogTitle())==JFileChooser.APPROVE_OPTION)
		{
			// Retrieve the selected file and exporter from the file chooser
			File file = chooser.getSelectedFile();
			harmonyModel.getPreferences().setExportDir(file.getParentFile());
			Exporter exporter = ((ExportFileFilter)chooser.getFileFilter()).exporter;

			// Ensure that file has the proper ending
			if(!file.getName().endsWith(exporter.getFileType()))
				file = new File(file.getPath()+exporter.getFileType());
			
			// Check to see if file already exists and checks to make sure it can be overwritten
			if(file!=null && file.exists() && exporter.isFileOverwritten())
    		{
	    		int option = JOptionPane.showConfirmDialog(harmonyModel.getBaseFrame(),
	        			file + " exists.  Overwrite?",
						"Overwrite File", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);
	    		if(option==1) return;
    		}

			// Export the project to the specified file
			try { export(harmonyModel, exporter, file); }
			catch(IOException e)
			{
				JOptionPane.showMessageDialog(harmonyModel.getBaseFrame(),
						"File " + file.toString() + " failed to be exported.\n"+e.getMessage(),
						"Export Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/** Allows user to export */
	public void export(HarmonyModel harmonyModel, Window parent)
	{		
		if(harmonyModel.getInstantiationType()!=InstantiationType.WEBAPP)
			exportViaLocalClient(harmonyModel);
		else new RemotePorterDialog(this, harmonyModel, parent);
	}
}
