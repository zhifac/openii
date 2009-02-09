package org.mitre.harmony.view.dialogs.importer;

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

import org.mitre.harmony.model.preferences.Preferences;
import org.mitre.schemastore.importers.Importer;

/** File parameter class */
public class FileParameter extends JPanel implements ActionListener
{
	/** File filter class associated with this file parameter */
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
	
	/** Stores the file field */
	private JTextField fileField = new JTextField();
	
	/** Stores the specified importer */
	private Importer importer = null;
	
	/** Constructs the file parameter */
	public FileParameter()
	{
		// Initialize the file field
		fileField.setColumns(20);
		
		// Constructs the file button
		JButton fileButton = new JButton("Browse...");
		fileButton.setBorder(new CompoundBorder(new LineBorder(Color.gray),new EmptyBorder(1,3,1,3)));
		fileButton.setFont(new Font("Default",Font.BOLD,10));
		fileButton.addActionListener(this);
		
		// Constructs the file parameter
		setBorder(new EmptyBorder(1,0,0,0));
		setLayout(new BorderLayout());
		add(fileField,BorderLayout.CENTER);
		add(fileButton,BorderLayout.EAST);
	}

	/** Sets the background the specified color */
	public void setBackground(Color color)
		{ if(fileField!=null) fileField.setBackground(color); }
	
	/** Set the selected importer */
	public void setImporter(Importer importer)
	{
		this.importer = importer;
		fileField.setText("");
	}
	
	/** Returns the parameter value */
	public URI getValue()
	{
		String value = fileField.getText();
		return value==null || value.length()==0 ? null : new File(value).toURI();
	}
	
	/** Handles the pressing of the file button */
	public void actionPerformed(ActionEvent e)
	{
		// Identify the dialog of which this pane is part of
		Component dialog = getParent();
		while(!(dialog instanceof JDialog))
			dialog = dialog.getParent();
		
		// Create the file filter for use
		ParameterFileFilter filter = new ParameterFileFilter();
		
		// Ask the user to specify a file
		JFileChooser chooser = new JFileChooser(Preferences.getImportDir());
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.addChoosableFileFilter(filter);
		if(chooser.showDialog(dialog,"Select")==JFileChooser.APPROVE_OPTION)
		{
			Preferences.setImportDir(chooser.getSelectedFile().getParentFile());
			String path = chooser.getSelectedFile().getPath();
			if(!filter.accept(new File(path))) path += importer.getFileTypes().get(0);
			fileField.setText(path);
		}
	}

	/** Adds a listener to the file field */
	public void addListener(CaretListener listener)
		{ fileField.addCaretListener(listener); }

	/** Removes a listener to the file field */
	public void removeListener(CaretListener listener)
		{ fileField.removeCaretListener(listener); }
}