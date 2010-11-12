package org.mitre.harmony.view.dialogs.importers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileFilter;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.schemastore.porters.Importer;
import org.mitre.schemastore.porters.URIType;

/** URI parameter class */
public class URIParm extends JPanel implements ActionListener
{
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Stores the specified importer */
	private Importer importer = null;

	// Stores components used in the panel
	private JTextField fileField = new JTextField();
	private JButton fileButton = new JButton("Browse...");

	/** File filter associated with this URI parameter */
	private class ParameterFileFilter extends FileFilter
	{
		/** Indicates if the file is acceptable */
		public boolean accept(File file)
		{
			if(file.isDirectory()) return true;
			for(String uriFileType : importer.getFileTypes())
				if(file.getName().endsWith(uriFileType)) return true;
			return false;
		}
			
		/** Provides a description of what constitutes an acceptable file */
		public String getDescription()
		{
			String description = importer.getName() + "(";
			for(String uriFileType : importer.getFileTypes())
				description += "*" + uriFileType + ",";
			return description.replaceAll(",$",")");
		}
	}
	
	/** Constructs the file parameter */
	public URIParm(HarmonyModel harmonyModel)
	{
		this.harmonyModel = harmonyModel;
		
		// Initialize the file field
		fileField.setColumns(20);
		fileField.setBorder(new LineBorder(Color.gray));
				
		// Initializes the file button
		fileButton.setBorder(new CompoundBorder(new LineBorder(Color.gray),new EmptyBorder(1,3,1,3)));
		fileButton.setFont(new Font("Default",Font.BOLD,10));
		fileButton.addActionListener(this);

		// Initializes a pane for holding the file field
		JPanel fileFieldPane = new JPanel();
		fileFieldPane.setLayout(new BorderLayout());
		fileFieldPane.setBorder(new EmptyBorder(0,0,0,3));
		fileFieldPane.add(fileField, BorderLayout.CENTER);
		
		// Constructs the file parameter
		setBorder(new EmptyBorder(1,0,0,0));
		setLayout(new BorderLayout());
		add(fileFieldPane,BorderLayout.CENTER);
		add(fileButton,BorderLayout.EAST);
	}

	/** Sets the background the specified color */
	public void setBackground(Color color)
		{ if(fileField!=null) fileField.setBackground(color); }
	
	/** Enables/disables the URI parameter */
	public void setEnabled(boolean enable)
		{ super.setEnabled(enable); fileField.setEditable(enable); fileButton.setEnabled(enable); }
	
	/** Set the selected importer */
	public void setImporter(Importer importer)
	{
		this.importer = importer;
		fileField.setText("");
		fileButton.setVisible(importer.getURIType()!=URIType.URI);
	}
	
	/** Returns the parameter URI */
	public URI getURI()
	{
		String value = fileField.getText();
		if(value==null || value.length()==0) return null;
		if(importer.getURIType()==URIType.FILE || importer.getURIType()==URIType.M3MODEL)
			return new File(value).toURI();
		else try { return new URI(value); } catch(Exception e) { return null; }
	}
	
	/** Handles the pressing of the file button */
	public void actionPerformed(ActionEvent e)
	{
		// Identify the dialog of which this pane is part of
		Component dialog = getParent();
		while(!(dialog instanceof JDialog))
			dialog = dialog.getParent();
		
		// Handles the selection of an item from a list for importing
		if(importer.getURIType()==URIType.LIST)
		{
			URIListDialog listDialog = new URIListDialog((JDialog)dialog,SchemaStoreManager.getImporterURIList(importer));
			while(listDialog.isVisible()) try { Thread.sleep(500); } catch(Exception e2) {}
			fileField.setText(listDialog.getURI().toString());
		}
		
		// Handles the retrieval of a file for importing
		else
		{
			// Create the file filter for use
			ParameterFileFilter filter = new ParameterFileFilter();
			
			// Ask the user to specify a file
			JFileChooser chooser = new JFileChooser(harmonyModel.getPreferences().getImportDir());
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.addChoosableFileFilter(filter);
			if(chooser.showDialog(dialog,"Select")==JFileChooser.APPROVE_OPTION)
			{
				harmonyModel.getPreferences().setImportDir(chooser.getSelectedFile().getParentFile());
				String path = chooser.getSelectedFile().getPath();
				if(!filter.accept(new File(path))) path += importer.getFileTypes().get(0);
				fileField.setText(path);
			}
		}
	}
	
	/** Adds a listener to the file field */
	public void addListener(CaretListener listener)
		{ fileField.addCaretListener(listener); }

	/** Removes a listener to the file field */
	public void removeListener(CaretListener listener)
		{ fileField.removeCaretListener(listener); }
}