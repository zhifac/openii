package org.mitre.openii.widgets.porters;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.porters.Exporter;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.PorterType;

/** Constructs the Exporter Dialog class */
public class ExporterDialog
{
	/** Stores the exporter type */
	private PorterType exporterType = null;
	
	/** Stores the list of exporters */
	private ArrayList<Exporter> exporters = null;
	
	/** Store the file dialog */
	private FileDialog dialog = null;
	
	/** Constructs the Exporter dialog */
	public ExporterDialog(Shell shell, String filename, PorterType exporterType)
	{
		this.exporterType = exporterType;
		dialog = new FileDialog(shell, SWT.SAVE);
		
		// Generate the label
		String label = exporterType.name().replaceAll("_EXPORTERS", "").toLowerCase();
		label = label.substring(0,1).toUpperCase() + label.substring(1,label.length());
		
		// Initialize the file settings
		dialog.setText("Export " + label);
		dialog.setFileName(filename);
		dialog.setFilterPath(OpenIIManager.getActiveDir());
	
		// Get the list of exporters available for use
		PorterManager manager = new PorterManager(RepositoryManager.getClient());
		exporters = manager.getPorters(exporterType);
		
		// Set up the filter names and extensions
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> extensions = new ArrayList<String>();
		for(Exporter exporter : exporters)
		{
			names.add(exporter.getName() + " (" + exporter.getFileType() + ")");
			extensions.add("*"+exporter.getFileType());
		}
		dialog.setFilterNames(names.toArray(new String[0]));
		dialog.setFilterExtensions(extensions.toArray(new String[0]));

		// Set the preferred exporter type
		Class<?> exporterClass = OpenIIManager.getPorterPreference(exporterType);
		for(int i=0; i<exporters.size(); i++)
			if(exporters.get(i).getClass().equals(exporterClass))
				dialog.setFilterIndex(i);
	}
	
	/** Opens the dialog */
	public String open()
		{ return dialog.open(); }
	
	
	
	/** Stores the preferred exporter type */ @SuppressWarnings("unchecked")
	public <T extends Exporter> T getExporter()
	{
		Exporter exporter = exporters.get(dialog.getFilterIndex());
		OpenIIManager.setActiveDir(dialog.getFilterPath());
		OpenIIManager.setPorterPreference(exporterType, exporter);
		return (T)exporter;
	}
}