// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.harmony.view.dialogs.porters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.net.URI;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.widgets.AbstractButtonPane;
import org.mitre.schemastore.porters.Importer;

/** Class for displaying the importer dialog */
abstract public class ImporterDialog extends JDialog
{		
	/** Stores the harmony model */
	protected HarmonyModel harmonyModel;
	
	/** Flag indicating if import was successful */
	private boolean successful = false;
	
	// Stores the panes associated with this dialog
	protected JComboBox selectionList = null;
	protected JTextField nameField = new JTextField();
	protected JTextField authorField = new JTextField();
	protected JTextArea descriptionField = new JTextArea();
	protected UriParameter uriField;	
	
	/** Private class for defining the button pane */
	private class ButtonPane extends AbstractButtonPane
	{
		/** Constructs the button pane */
		public ButtonPane()
			{ super(new String[]{"OK", "Cancel"},1,2); }

		/** Handles selection of button */
		protected void buttonPressed(String label)
		{
			if(label.equals("OK"))
			{
				// Retrieve the information from the various fields
				String name = nameField.getText();
				String author = authorField.getText();
				String description = descriptionField.getText();
				URI uri = uriField.getURI();
				
				// Update highlighting
				nameField.setBackground(name.length()>0 ? Color.white : Color.yellow);
				authorField.setBackground(author.length()>0 ? Color.white : Color.yellow);
				uriField.setBackground(uri!=null ? Color.white : Color.yellow);
				
				// If completed, run importer
				if(name.length()>0 && author.length()>0 && uri!=null)
				{
					// Run the importer
					try { importItem(name, author, description, uri); successful=true; dispose(); }
					catch(Exception e2) { JOptionPane.showMessageDialog(null,e2.getMessage(),"Import Error",JOptionPane.ERROR_MESSAGE); }
				}
				else JOptionPane.showMessageDialog(null,"All fields must be completed before import!","Missing Fields",JOptionPane.ERROR_MESSAGE);
			}
			else dispose();
		}
	}
	
	/** Constructs the selection pane */
	private JPanel getSelectionPane()
	{
		// Initializes the label
		JLabel selectionLabel = new JLabel("Importers: ");
		selectionLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		// Generate the selection list
		selectionList = new JComboBox(new Vector<Importer>(getImporters()));
		selectionList.setBackground(Color.white);
		selectionList.setFocusable(false);
		selectionList.setSelectedIndex(0);
		
		// Generate the importer list pane
		JPanel importerPane = new JPanel();
		importerPane.setOpaque(false);
		importerPane.setLayout(new BoxLayout(importerPane,BoxLayout.X_AXIS));
		importerPane.add(selectionLabel);
		importerPane.add(selectionList);
		
		// Generate the selection pane
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(5,0,5,0));
		pane.setOpaque(false);
		pane.setLayout(new BorderLayout());
		pane.add(importerPane,BorderLayout.WEST);
		return pane;
	}
	
	/** Constructs the information pane */
	private JPanel getInformationPane()
	{
		// Initialize the name
		nameField.setBorder(new LineBorder(Color.gray));
		
		// Initialize the author
		authorField.setBorder(new LineBorder(Color.gray));
		authorField.setText(System.getProperty("user.name"));
		
		// Initialize the description
		descriptionField.setBorder(new LineBorder(Color.gray));
		descriptionField.setRows(5);
		descriptionField.setLineWrap(true);
		descriptionField.setWrapStyleWord(true);
		
		// Initialize the uri
		uriField = new UriParameter(harmonyModel);
		uriField.setImporter(getImporter());
		
		// Generates the information pane
		ParameterPane pane = new ParameterPane();
		pane.setBorder(new CompoundBorder(new LineBorder(Color.gray),new EmptyBorder(6,6,6,6)));
		pane.addParameter("Name", nameField);
		pane.addParameter("Author", authorField);
		pane.addParameter("Description", descriptionField);
		pane.addParameter("File / URI", uriField);
		return pane;
	}
	
	/** Constructs the importer dialog */
	public ImporterDialog(Component parent, HarmonyModel harmonyModel)
	{
		super(harmonyModel.getBaseFrame());
		this.harmonyModel = harmonyModel;
		
		// Initialize the main pane
		JPanel mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5,10,5,10));
		mainPane.setLayout(new BorderLayout());
		mainPane.add(getSelectionPane(),BorderLayout.NORTH);
		mainPane.add(getInformationPane(),BorderLayout.CENTER);
		mainPane.add(new ButtonPane(), BorderLayout.SOUTH);
		
		// Initialize the dialog pane
		setTitle("Import " + getImporterType());
		setModal(true);
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(mainPane);
		pack();
		setLocationRelativeTo(parent);
	}
	
	/** Returns the type of importer being run */
	abstract protected String getImporterType();
	
	/** Returns the importers from which the user can select */
	abstract protected ArrayList<Importer> getImporters();
	
	/** Imports the currently specified item */
	abstract protected void importItem(String name, String author, String description, URI uri) throws Exception;
	
	/** Returns the currently selected importer */
	public Importer getImporter()
		{ return (Importer)selectionList.getSelectedItem(); }
	
	/** Indicates if the import was successful */
	public boolean isSuccessful()
		{ return successful; }
}