// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.harmony.view.dialogs.importer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.Collections;
import java.util.Comparator;
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
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.harmony.view.dialogs.AbstractButtonPane;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;

/** Class for displaying the importer view */
public class ImporterDialog extends JDialog implements ActionListener,CaretListener
{		
	/** Stores the imported schema ID */
	private Integer schemaID = null;
	
	// Stores the panes associated with this dialog
	private JComboBox selectionList = null;
	private JTextField nameField = new JTextField();
	private JTextField authorField = new JTextField();
	private JTextArea descriptionField = new JTextArea();
	private UriParameter uriField;	
	
	/** Private class for sorting importers */
	private class ImporterComparator implements Comparator<SchemaImporter>
	{
		public int compare(SchemaImporter importer1, SchemaImporter importer2)
			{ return importer1.getName().compareTo(importer2.getName()); }
	}
	
	/** Private class for defining the button pane */
	private class ButtonPane extends AbstractButtonPane
	{
		/** Constructs the button pane */
		public ButtonPane()
			{ super("OK", "Cancel"); }

		/** Handles selection of okay button */
		protected void button1Action()
			{ if(importSchema()) dispose();	}

		/** Handles selection of cancel button */
		protected void button2Action()
			{ dispose(); }
	}
	
	/** Constructs the selection pane */
	private JPanel getSelectionPane()
	{
		// Generate the list of importers which are available
		Vector<SchemaImporter> importers = new Vector<SchemaImporter>();
		for(SchemaImporter importer : SchemaStoreManager.getSchemaImporters())
		{
			Integer uriType = importer.getURIType();
			if(uriType.equals(SchemaImporter.FILE) || uriType.equals(SchemaImporter.M3MODEL) || uriType.equals(SchemaImporter.URI))
				importers.add(importer);
		}
		Collections.sort(importers, new ImporterComparator());
				
		// Initializes the label
		JLabel selectionLabel = new JLabel("Importers: ");
		selectionLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		// Generate the selection list
		selectionList = new JComboBox(importers);
		selectionList.setBackground(Color.white);
		selectionList.setFocusable(false);
		selectionList.addActionListener(this);
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
	public ImporterDialog(JDialog parent, HarmonyModel harmonyModel)
	{
		super(parent);
		
		// Initialize the uri field
		uriField = new UriParameter(harmonyModel);
		uriField.addListener(this);
		
		// Initialize the main pane
		JPanel mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5,10,5,10));
		mainPane.setLayout(new BorderLayout());
		mainPane.add(getSelectionPane(),BorderLayout.NORTH);
		mainPane.add(getInformationPane(),BorderLayout.CENTER);
		mainPane.add(new ButtonPane(), BorderLayout.SOUTH);
		
		// Initialize the dialog pane
		setTitle("Import Schema");
		setModal(true);
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(mainPane);
		pack();
		setLocationRelativeTo(parent);
		setVisible(true);
	}
	
	/** Returns the schema ID of the imported schema */
	public Integer getSchemaID()
		{ return schemaID; }
	
	/** Attempts to import the schema */
	private boolean importSchema()
	{
		// Get the current importer being used
		SchemaImporter importer = (SchemaImporter)selectionList.getSelectedItem();
		
		// Retrieve the information from the various fields
		String name = nameField.getText();
		String author = authorField.getText();
		String description = descriptionField.getText();
		URI uri = uriField.getValue();
		
		// Update highlighting
		nameField.setBackground(name.length()>0 ? Color.white : Color.yellow);
		authorField.setBackground(author.length()>0 ? Color.white : Color.yellow);
		descriptionField.setBackground(description.length()>0 ? Color.white : Color.yellow);
		uriField.setBackground(uri!=null ? Color.white : Color.yellow);
		
		// If completed, run importer
		if(name.length()>0 && author.length()>0 && description.length()>0 && uri!=null)
		{
			// Run the importer
			try {
				schemaID = importer.importSchema(name, author, description, uri);
				return true;
			}
			catch(Exception e2) { JOptionPane.showMessageDialog(null,e2.getMessage(),"Import Error",JOptionPane.ERROR_MESSAGE); }
		}
		else JOptionPane.showMessageDialog(null,"All fields must be completed before import!","Missing Fields",JOptionPane.ERROR_MESSAGE);
		return false;
	}
	
	/** Handles the selection of the importer */
	public void actionPerformed(ActionEvent e)
	{
		// Initialize the importer pane
		SchemaImporter importer = (SchemaImporter)selectionList.getSelectedItem();
		boolean isArchiveImporter = importer.getURIType()==SchemaImporter.M3MODEL;
		uriField.setImporter(importer);

		// Lock down the name and description fields for archive importers
		nameField.setEditable(!isArchiveImporter);
		descriptionField.setEditable(!isArchiveImporter);
		descriptionField.setBackground(isArchiveImporter ? new Color(0xeeeeee) : Color.white);
	}
	
	/** Handles changes to the uri */
	public void caretUpdate(CaretEvent e)
	{
		SchemaImporter importer = (SchemaImporter)selectionList.getSelectedItem();
		if(uriField.getValue()!=null)
			try {
				Schema schema = importer.getSchema(uriField.getValue());
				if(schema!=null)
				{
					nameField.setText(schema.getName());
					authorField.setText(schema.getAuthor());
					descriptionField.setText(schema.getDescription());
				}
			} catch(Exception e2) { System.out.println("(E) ImporterDialog.caretUpdate - " + e2.getMessage()); }
	}
}