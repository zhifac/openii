// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.shared;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileFilter;

import org.mitre.flexidata.ygg.Ygg;
import org.mitre.flexidata.ygg.model.SchemaManager;
import org.mitre.flexidata.ygg.view.Consts;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;

/** Class for displaying a list of parameters */
public class ParameterPane extends JPanel
{
	// Colors used in the parameter pane
	static public final Color highlightColor = new Color(0xffffaa);
	
	/** Abstract parameter class */
	static abstract public class Parameter extends JPanel
	{
		/** Stores the parameter name */
		private String name;
		
		/** Constructs the parameter */
		protected Parameter(String name)
		{
			this.name = name;
			setOpaque(false);
			setLayout(new BorderLayout());
		}
		
		/** Returns the parameter name */
		public String getName() { return name; }
		
		/** Returns the parameter value */
		abstract public String getValue();
		
		/** Highlights the parameter */
		abstract protected void setHighlight(boolean highlight);
	}
	
	/** Text parameter class */
	static public class TextParameter extends Parameter
	{
		/** Stores the label */
		private JLabel label = new JLabel();
		
		/** Constructs the text parameter */
		public TextParameter(String name, String value)
		{
			super(name);
			label.setText(value);
			label.setFont(Consts.PARAMETER_FONT);
			add(label);
		}
		
		/** Returns the parameter value */
		public String getValue() { return label.getText(); }
		
		/** Highlights the parameter */
		protected void setHighlight(boolean highlight) {}
	}
	
	/** Edit field parameter class */
	static public class EditFieldParameter extends Parameter
	{
		/** Stores the text field */
		private JTextField textField = new JTextField();
		
		/** Constructs the edit field parameter */
		public EditFieldParameter(String name, String value)
		{
			super(name);
			setBorder(new EmptyBorder(2,0,2,0));

			// Constructs the text field
			textField.setText(value);
			textField.setFont(Consts.PARAMETER_EDIT_FONT);
			add(textField);
		}

		/** Returns the parameter value */
		public String getValue()
		{
			String value = textField.getText();
			return value==null || value.length()==0 ? null : value;
		}
		
		/** Highlights the parameter */
		protected void setHighlight(boolean highlight)
			{ textField.setBackground(highlight ? Consts.YELLOW : Consts.WHITE); }
	}

	/** Edit area parameter class */
	static public class EditAreaParameter extends Parameter
	{
		/** Stores the text area */
		private JTextArea textArea = new JTextArea();
		
		/** Constructs the edit area parameter */
		public EditAreaParameter(String name, String value)
		{
			super(name);
			setBorder(new EmptyBorder(2,0,2,0));

			// Constructs the text area
			textArea.setText(value);
			textArea.setFont(Consts.PARAMETER_EDIT_FONT);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			
			// Create a scroll pane in which to store the text area
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportView(textArea);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			add(scrollPane);
		}

		/** Returns the parameter value */
		public String getValue()
		{
			String value = textArea.getText();
			return value==null || value.length()==0 ? null : value;
		}
		
		/** Highlights the parameter */
		protected void setHighlight(boolean highlight)
			{ textArea.setBackground(highlight ? Consts.YELLOW : Consts.WHITE); }
	}
	
	/** File parameter class */
	static public class FileParameter extends Parameter implements ActionListener
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
	
	/** Schema parameter class */
	static public class SchemaParameter extends Parameter
	{
		/** Stores the schema list */
		private JComboBox schemaList = new JComboBox();		
		
		/** Constructs the schema parameter */
		public SchemaParameter(String name)
		{
			super(name);
			setBorder(new EmptyBorder(2,0,2,0));

			// Constructs the drop-down box
			for(Schema schema : SchemaManager.getSchemas()) schemaList.addItem(schema);
			schemaList.setBackground(Color.white);
			schemaList.setFocusable(false);
			add(schemaList);
		}
		
		/** Returns the parameter value */
		public String getValue()
		{
			String value = ((Schema)schemaList.getSelectedItem()).getId().toString();
			return value==null || value.length()==0 ? null : value;
		}		
		
		/** Highlights the parameter */
		protected void setHighlight(boolean highlight)
			{ schemaList.setBackground(highlight ? Consts.YELLOW : Consts.WHITE); }
	}
	
	/** Repository parameter class */
	static public class RepositoryParameter extends Parameter implements CaretListener
	{
		/** Stores the various fields used in the repository parameter */
		private JTextField serverField = new JTextField();
		private JTextField portField = new JTextField();
		private JComboBox schemaList = new JComboBox();		

		/** Constructs a labeled pane */
		private JPanel getLabeledPane(String name, JTextField field)
		{
			// Initialize the label
			JLabel label = new JLabel(name);
			label.setFont(Consts.PARAMETER_FONT);
			
			// Initialize the field
			field.setFont(Consts.PARAMETER_EDIT_FONT);
			field.addCaretListener(this);
			
			// Create the labeled pane
			JPanel labeledPane = new JPanel();
			labeledPane.setOpaque(false);
			labeledPane.setLayout(new BorderLayout());
			labeledPane.add(label,BorderLayout.WEST);
			labeledPane.add(field,BorderLayout.CENTER);
			return labeledPane;
		}
		
		/** Constructs the repository parameter */
		public RepositoryParameter(String name)
		{
			super(name);
			setBorder(new EmptyBorder(2,0,2,0));
			setLayout(new GridLayout(2,1));
			
			// Initialize the server pane
			JPanel serverPane = new JPanel();
			serverPane.setOpaque(false);
			serverPane.setLayout(new GridLayout(1,2));
			serverPane.add(getLabeledPane("Server",serverField));
			serverPane.add(getLabeledPane("Port",portField));
			add(serverPane);
			
			// Constructs the drop-down box
			schemaList.setBackground(Color.white);
			schemaList.setFocusable(false);
			add(schemaList);
		}
		
		/** Returns the parameter value */
		public String getValue()
		{
			Schema schema = ((Schema)schemaList.getSelectedItem());
			if(schema==null) return null;
			return "http://"+serverField.getText()+":"+portField.getText()+"/SchemaStore/services/SchemaStore#" + schema.getId();
		}

		/** Highlights the parameter */
		protected void setHighlight(boolean highlight)
		{
			Color color = highlight ? Consts.YELLOW : Consts.WHITE;
			serverField.setBackground(color); portField.setBackground(color);
			schemaList.setBackground(color);
		}
		
		/** Handles the entry of server information */
		public void caretUpdate(CaretEvent arg0)
		{
			try {
				String server = "http://"+serverField.getText()+":"+portField.getText()+"/SchemaStore/services/SchemaStore";
				SchemaStoreClient client = new SchemaStoreClient(server);
				ArrayList<Schema> schemas = client.getSchemas();
				
				// Constructs the drop-down box
				schemaList.removeAllItems();
				for(Schema schema : schemas)
					schemaList.addItem(schema);
			} catch(Exception e) {}
		}
	}
	
	/** Constructs the parameter pane */
	public ParameterPane()
	{
		setOpaque(false);
		setLayout(new GridBagLayout());		
	}
	
	/** Adds a parameter to the pane */
	public void addParameter(Parameter parameter)
	{
		// Initialize the parameter label
		JLabel parmLabel = new JLabel(parameter.getName() + ": ");
		parmLabel.setFont(Consts.PARAMETER_FONT);
			
		// Position the parameter label
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = getComponentCount()/2;
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;
		add(parmLabel,constraints);
			
		// Position the parameter component
		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
		if(parameter instanceof EditAreaParameter)
			{ constraints.fill = GridBagConstraints.BOTH; constraints.weighty = 1; }
		add(parameter,constraints);
	}
	
	/** Returns the requested parameter */
	public Parameter getParameter(Integer loc)
		{ try { return (Parameter)getComponent(2*loc+1); } catch(Exception e) { return null; } }
	
	/** Checks to make sure the parameter pane is completed */
	public boolean isCompleted()
	{
		boolean completed = true;
		for(int i=0; i<getComponentCount()/2; i++)
		{
			Parameter parameter = getParameter(i);
			boolean parameterCompleted = parameter.getValue()!=null;
			parameter.setHighlight(!parameterCompleted);
			completed &= parameterCompleted;
		}
		return completed;
	}
}