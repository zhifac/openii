package org.mitre.openii.dialogs.schemas.importer;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;

/** Class for storing the files which can be selected to import */
public class FileInformationPane
{	
	/** Class for defining the labeling of the file list */
	class FileLabelProvider implements ILabelProvider
	{
		public Image getImage(Object element) { return OpenIIActivator.getImage("File.gif"); }
		public String getText(Object element) { return ((File)element).getName(); }
		public boolean isLabelProperty(Object element, String property) { return false; }
		public void addListener(ILabelProviderListener listener) {}
		public void dispose() {}
		public void removeListener(ILabelProviderListener listener) {}
		public Color getBackground(Object arg0) { return null; }
	}
	
	/** Class for defining the contents of file list */
	private class FileContentProvider implements IStructuredContentProvider
	{
		public Object[] getElements(Object inputElement) { return items.toArray(); }
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
		public void dispose() {}
	}
	
	/** Store the check box table being displayed */
	private CheckboxTableViewer checkboxTable = null;

	/** Stores the files which can be selected from */
	private ArrayList<File> items = new ArrayList<File>();
	
	/** Constructs the file selection pane */
	FileInformationPane(Composite parent)
	{
		// Construct the pane for showing the selected files
		Group group = new Group(parent, SWT.NONE);
		group.setText("File Information");
		group.setLayout(new GridLayout(1,false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Construct the list of files which can be chosen from
		checkboxTable = CheckboxTableViewer.newCheckList(group,SWT.BORDER | SWT.V_SCROLL);
		checkboxTable.setContentProvider(new FileContentProvider());
		checkboxTable.setLabelProvider(new FileLabelProvider());
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 100;
		checkboxTable.getControl().setLayoutData(gridData);
	}
	
	/** Sets the files being displayed */
	void initializeFiles(File file, SchemaImporter importer)
	{
		// Clear the file items
		items.clear();
	
		// Translate the URI into a file
		if(file.exists())
		{
			// Retrieve files from a directory
			if(file.isDirectory())
			{
				for(File childFile : file.listFiles())
					for(String fileType : importer.getFileTypes())
						if(childFile.getName().endsWith(fileType))
							{ items.add(childFile); break; }
			}
			
			// Display the specified file			
			else if(!file.getName().equals("")) items.add(file);
		}
		
		// Reset the check box table
		checkboxTable.setInput("");
		checkboxTable.setAllChecked(true);
	}
	
	/** Remove a file from the list of files */
	void removeFile(File file)
	{
		// Remove the specified file from the list
		for(int i=0; i<items.size(); i++)
			if(items.get(i).equals(file))
				{ items.remove(i); break; }

		// Update the file list
		checkboxTable.setInput("");
	}
	
	/** Indicates if any files are selected */
	ArrayList<File> getSelectedFiles()
	{
		ArrayList<File> selectedFiles = new ArrayList<File>();
		for(Object element : checkboxTable.getCheckedElements())
			selectedFiles.add((File)element);
		return selectedFiles;
	}
	
	/** Indicates if the information provided in this pane is valid */
	boolean isValid()
		{ return getSelectedFiles().size()>0; }
	
	/** Adds a listener to the selection pane */
	protected void addListener(ISelectionChangedListener listener)
		{ checkboxTable.addSelectionChangedListener(listener); }
}