// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.schema.porters;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.mitre.flexidata.ygg.Ygg;
import org.mitre.flexidata.ygg.exporters.Exporter;
import org.mitre.flexidata.ygg.model.ConfigManager;
import org.mitre.flexidata.ygg.view.Consts;
import org.mitre.flexidata.ygg.view.GenericView;
import org.mitre.flexidata.ygg.view.schema.view.SchemaView;
import org.mitre.flexidata.ygg.view.shared.ColoredButton;
import org.mitre.flexidata.ygg.view.shared.DescriptionPane;
import org.mitre.flexidata.ygg.view.shared.RoundedPane;
import org.mitre.flexidata.ygg.view.shared.SelectionPane;
import org.mitre.flexidata.ygg.view.shared.parameters.FileParameter;
import org.mitre.flexidata.ygg.view.shared.parameters.ParameterPane;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

/** Class for displaying the exporter view */
public class ExporterView extends GenericView implements ActionListener
{
	/** Stores the schema and schema elements being exported */
	private Schema schema;
	private ArrayList<SchemaElement> schemaElements;

	/** Stores the text to be outputted */
	private StringBuffer output = null;
	
	// Stores various objects used by the schema view
	private JTextArea previewArea = null;
	private ParameterPane parameterPane = null;
	private ExporterSelectionPane selectionPane;
	private InformationPane informationPane;
	private DescriptionPane descriptionPane;

	/** Stores the preview and save buttons used to run the exporter */
	private ColoredButton backButton = new ColoredButton("Back");
	private ColoredButton previewButton = new ColoredButton("Preview");
	private ColoredButton saveButton = new ColoredButton("Save");

	/** Class for displaying the exporter selection pane */
	private class ExporterSelectionPane extends SelectionPane<Exporter> implements ActionListener
	{
		/** Constructs the exporter selection pane */
		private ExporterSelectionPane()
		{
			super("Method",ConfigManager.getExporters());
			addActionListener(this);
		}

		/** Handles the selection of an exporter */
		public void actionPerformed(ActionEvent e)
		{
			Exporter exporter = getSelection();
			informationPane.displayExporter(exporter);
			descriptionPane.setText(exporter.getDescription(),false);
		}
	}
	
	/** Class for displaying the information pane */
	private class InformationPane extends RoundedPane
	{
		/** Constructs the information pane */
		private InformationPane()
		{			
			setBorder(new EmptyBorder(10,10,0,10));
			setBackground(Consts.LIGHT_TAN);
			displayExporter(selectionPane.getSelection());
		}
		
		/** Sets the type of URI needed for the currently selected exporter */
		private void displayExporter(Exporter exporter)
		{
			// Generate the preview label
			JLabel previewLabel = new JLabel("Preview");
			previewLabel.setFont(Consts.PARAMETER_FONT);
			
			// Generate a preview area
			previewArea = new JTextArea();
			JScrollPane previewScrollPane = new JScrollPane();
			previewScrollPane.setViewportView(previewArea);
			previewScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			previewScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

			// Generate the preview pane
			JPanel previewPane = new JPanel();
			previewPane.setBorder(new EmptyBorder(2,5,5,5));
			previewPane.setOpaque(false);
			previewPane.setLayout(new BorderLayout());
			previewPane.add(previewLabel,BorderLayout.NORTH);
			previewPane.add(previewScrollPane,BorderLayout.CENTER);
			
			// Generate the parameter pane
			parameterPane = new ParameterPane();
			parameterPane.setBorder(new EmptyBorder(5,5,5,5));
			ArrayList<String> fileTypes = new ArrayList<String>();
			fileTypes.add(exporter.getFileType());
			parameterPane.addParameter(new FileParameter("File",fileTypes));
			
			// Generate the information pane
			JPanel infoPane = new JPanel();
			infoPane.setOpaque(false);
			infoPane.setLayout(new BorderLayout());
			infoPane.add(previewPane,BorderLayout.CENTER);
			infoPane.add(parameterPane,BorderLayout.SOUTH);
			
			// Reset the view with the new parameter pane
			setView(infoPane);
		}
	}
	
	/** Retrieves the text to be outputted */
	private StringBuffer getOutput()
	{
		if(output==null)
		{
			Exporter exporter = selectionPane.getSelection();
			output = exporter.exportSchema(schema.getId(), schemaElements);
		}
		return output;
	}
	
	/** Constructs the importer view */
	public ExporterView(Schema schema, ArrayList<SchemaElement> schemaElements)
	{
		this.schema = schema;
		this.schemaElements = schemaElements;
		
		// Generate the schema view
		setOpaque(false);
		setLayout(new BorderLayout());
		add(selectionPane = new ExporterSelectionPane(),BorderLayout.NORTH);
		add(informationPane = new InformationPane(),BorderLayout.CENTER);
		add(descriptionPane = new DescriptionPane(selectionPane.getSelection().getDescription()),BorderLayout.SOUTH);
		
		// Add button listener
		backButton.addActionListener(this);
		previewButton.addActionListener(this);
		saveButton.addActionListener(this);
	}
	
	/** Return the title */
	public String getTitle()
		{ return "Export " + schema.getName(); }
	
	/** Return the buttons used by this view */
	public ArrayList<JButton> getButtons()
	{
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		buttons.add(backButton); buttons.add(previewButton); buttons.add(saveButton);
		return buttons;
	}
	
	/** Handles the running of the importer */
	public void actionPerformed(ActionEvent e)
	{
		// Switches back to the schema view
		if(e.getSource()==backButton)
			Ygg.setView(new SchemaView(schema));
			
		// Displays a preview of the schema export file
		if(e.getSource()==previewButton)
		{
			previewArea.setText(getOutput().toString());
			previewArea.setCaretPosition(0);
		}
			
		// Exports the schema to the specified file
		if(e.getSource()==saveButton)
		{
			try {
				// Export the schema to file
				File file = new File(parameterPane.getParameter("File").getValue());
				BufferedWriter out = new BufferedWriter(new FileWriter(file));
				out.write(getOutput().toString());
				out.close();

				// Switch back to schema view
				Ygg.setView(new SchemaView(schema));
			}
			catch(Exception e2)
				{ descriptionPane.setText("Unable to export to specified file!",true); }
		}
	}
}