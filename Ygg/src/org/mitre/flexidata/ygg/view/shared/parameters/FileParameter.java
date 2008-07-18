package org.mitre.flexidata.ygg.view.shared.parameters;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
	/** Stores the file field */
	private JTextField fileField = new JTextField();
	
	/** Stores the specified file filter */
	private FileFilter fileFilter = null;
	
	/** Constructs the file parameter */
	public FileParameter(String name, FileFilter fileFilter)
	{
		super(name);
		this.fileFilter = fileFilter;
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
		return value==null || value.length()==0 ? null : (new File(value).toURI()).toString();
	}
	
	/** Highlights the parameter */
	protected void setHighlight(boolean highlight)
		{ fileField.setBackground(highlight ? Consts.YELLOW : Consts.WHITE); }
	
	/** Handles the pressing of the file button */
	public void actionPerformed(ActionEvent e)
	{
		// Ask the user to specify a file
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.addChoosableFileFilter(fileFilter);
		if(chooser.showDialog(Ygg.ygg,"Select")==JFileChooser.APPROVE_OPTION)
			fileField.setText(chooser.getSelectedFile().getPath());
	}
}