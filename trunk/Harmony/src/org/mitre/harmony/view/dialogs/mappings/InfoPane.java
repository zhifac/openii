package org.mitre.harmony.view.dialogs.mappings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.mitre.harmony.model.SchemaManager;
import org.mitre.harmony.view.dialogs.TitledPane;
import org.mitre.schemastore.model.Mapping;

/** Private class for managing the info pane */
class InfoPane extends JPanel implements CaretListener
{	
	/** Declares the default font */
	static private Font defaultFont = new Font(null,Font.PLAIN,12);
	
	// Stores the various info fields
	private JTextField nameField = new JTextField();
	private JTextField authorField = new JTextField();
	private JTextArea descriptionField = new JTextArea();
	private JPanel schemaPane = new JPanel();
	
	/** Stores the currently displayed mapping */
	private Mapping mapping = null;
	
	/** Constructs the info pane */
	InfoPane(boolean saveMode)
	{
		// Initialize the name field
		nameField.setBackground(Color.white);
		nameField.setBorder(new LineBorder(Color.gray));
		nameField.setEditable(saveMode);
		nameField.addCaretListener(this);
		
		// Initialize the author field
		authorField.setBackground(Color.white);
		authorField.setBorder(new LineBorder(Color.gray));
		authorField.setEditable(saveMode);
		authorField.addCaretListener(this);
		
		// Initialize the description field
		descriptionField.setBackground(Color.white);
		descriptionField.setBorder(new LineBorder(Color.gray));
		descriptionField.setEditable(saveMode);
		descriptionField.addCaretListener(this);
		
		// Initializes the schema pane
		schemaPane.setBackground(Color.white);
		schemaPane.setLayout(new BoxLayout(schemaPane,BoxLayout.Y_AXIS));
		
		// Create the top info pane
		JPanel topPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane,BoxLayout.Y_AXIS));
		topPane.add(new TitledPane("Name",nameField));
		topPane.add(new TitledPane("Author",authorField));
		
		// Creates the center info pane
		JPanel centerPane = new JPanel();
		centerPane.setLayout(new GridLayout(2,1));
		centerPane.add(new TitledPane("Description",descriptionField));
		centerPane.add(new TitledPane("Schemas",new JScrollPane(schemaPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)));		
		
		// Create the info pane
		setBorder(new EmptyBorder(0,6,0,0));
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(250,250));
		add(topPane, BorderLayout.NORTH);
		add(centerPane, BorderLayout.CENTER);
	}
	
	/** Sets the info pane */
	void setInfo(Mapping mapping)
	{
		this.mapping = null;
		
		// Initialize the various fields
		nameField.setBackground(Color.white);
		authorField.setBackground(Color.white);
		descriptionField.setBackground(Color.white);
		schemaPane.removeAll();
		
		// Place information into the various fields
		nameField.setText(mapping.getName());
		authorField.setText(mapping.getAuthor());
		descriptionField.setText(mapping.getDescription());

		// Display the selected schema information
		for(Integer schemaID : mapping.getSchemas())
		{
			JLabel label = new JLabel(SchemaManager.getSchema(schemaID).getName());
			label.setFont(defaultFont);
			schemaPane.add(label);
		}
		
		this.mapping = mapping;
	}

	/** Validates the general info */
	boolean validateInfo()
	{
		// Retrieve the information from the various fields
		String name = nameField.getText();
		String author = authorField.getText();
		String description = descriptionField.getText();
		
		// Update highlighting
		nameField.setBackground(name.length()>0 ? Color.white : Color.yellow);
		authorField.setBackground(author.length()>0 ? Color.white : Color.yellow);
		descriptionField.setBackground(description.length()>0 ? Color.white : Color.yellow);

		// Indicates if the general info is completely provided
		return name.length()>0 && author.length()>0 && description.length()>0;
	}

	/** Update the mapping when the data fields are modified */
	public void caretUpdate(CaretEvent e)
	{
		if(mapping!=null)
		{
			mapping.setName(nameField.getText());
			mapping.setAuthor(authorField.getText());
			mapping.setDescription(descriptionField.getText());
		}
	}
}