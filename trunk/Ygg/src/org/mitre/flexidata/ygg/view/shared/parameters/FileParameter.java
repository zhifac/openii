package org.mitre.flexidata.ygg.view.shared.parameters;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import org.mitre.flexidata.ygg.Ygg;
import org.mitre.flexidata.ygg.view.Consts;

/** File parameter class */
public class FileParameter extends AbstractParameter implements ActionListener
{
	/** File filter class associated with this file parameter */
	private class ParameterFileFilter extends FileFilter
	{
		/** Indicates if the file is acceptable */
		public boolean accept(File file)
		{
			if(file.isDirectory()) return true;
			for(String uriFileType : fileTypes)
				if(file.getName().endsWith(uriFileType)) return true;
			return false;
		}
			
		/** Provides a description of what constitutes an acceptable file */
		public String getDescription()
		{
			String description = getName() + "(";
			for(String uriFileType : fileTypes)
				description += "*" + uriFileType + ",";
			return description.replaceAll(",$",")");
		}
	}
	
	/** Stores the file field */
	private JTextField fileField = new JTextField();
	
	/** Stores the specified file types */
	private ArrayList<String> fileTypes = null;
	
	/** Constructs the file parameter */
	public FileParameter(String name, ArrayList<String> fileTypes)
	{
		super(name);
		this.fileTypes = fileTypes;
		setBorder(new EmptyBorder(2,0,2,0));

		// Constructs the file field
		fileField.setFont(Consts.PARAMETER_EDIT_FONT);
		
		// Constructs the file button
		JButton fileButton = new JButton("Browse...");
		fileButton.addActionListener(this);

		// Constructs the file button pane
		JPanel buttonPane = new JPanel();
		buttonPane.setOpaque(false);
		buttonPane.setBorder(new EmptyBorder(0,3,1,2));
		buttonPane.setLayout(new BorderLayout());
		buttonPane.add(fileButton);
		
		// Constructs the file parameter
		add(fileField,BorderLayout.CENTER);
		add(buttonPane,BorderLayout.EAST);
	}

	/** Returns the parameter value */
	public String getValue()
	{
		String value = fileField.getText();
		return value==null || value.length()==0 ? null : value;
	}
	
	/** Sets the parameter value */
	public void setValue(String value)
		{ fileField.setText(value); }
	
	/** Highlights the parameter */
	public void setHighlight(boolean highlight)
		{ fileField.setBackground(highlight ? Consts.YELLOW : Consts.WHITE); }
	
	/** Handles the pressing of the file button */
	public void actionPerformed(ActionEvent e)
	{
		// Create the file filter for use
		ParameterFileFilter filter = new ParameterFileFilter();
		
		// Ask the user to specify a file
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.addChoosableFileFilter(filter);
		if(chooser.showDialog(Ygg.ygg,"Select")==JFileChooser.APPROVE_OPTION)
		{
			String path = chooser.getSelectedFile().getPath();
			if(!filter.accept(new File(path))) path += fileTypes.get(0);
			fileField.setText(path);
		}
	}
}