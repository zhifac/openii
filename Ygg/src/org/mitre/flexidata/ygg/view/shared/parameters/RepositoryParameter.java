package org.mitre.flexidata.ygg.view.shared.parameters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.mitre.flexidata.ygg.model.ConfigManager;
import org.mitre.flexidata.ygg.view.Consts;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;

/** Repository parameter class */
public class RepositoryParameter extends AbstractParameter implements CaretListener
{
	/** Private thread for updating the schema list */
	private class UpdateSchemaList extends Thread
	{
		/** Runs the procedure to update the schema list */
		public void run()
		{
			// Clear out the schema list first
			schemaList.removeAllItems();
			
			// First check to make sure that the server and port information is not still changing
			int length = serverField.getText().length() + portField.getText().length();
			try { Thread.sleep(500); } catch(Exception e) {}
			if(length != serverField.getText().length() + portField.getText().length()) return;
		
			// Don't allow connection to current repository
			if(getServerString().equals(ConfigManager.getSchemaStoreLoc())) return;
			
			// Update the list of schemas
			try {
				// Retrieve the schemas from the schema store
				SchemaStoreClient client = new SchemaStoreClient(getServerString());
				ArrayList<Schema> schemas = client.getSchemas();
				
				// Constructs the drop-down box
				schemaList.removeAllItems();
				for(Schema schema : schemas)
					schemaList.addItem(schema);
			} catch(Exception e) {}
		}
	}
	
	/** Stores the various fields used in the repository parameter */
	private JTextField serverField = new JTextField();
	private JTextField portField = new JTextField();
	private JComboBox schemaList = new JComboBox();		

	/** Constructs a labeled pane */
	private JPanel getLabeledPane(String name, JTextField field)
	{
		// Initialize the label
		JLabel label = new JLabel(name+" ");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setFont(Consts.PARAMETER_FONT);
		
		// Initialize the field
		field.setFont(Consts.PARAMETER_EDIT_FONT);
		field.addCaretListener(this);
		
		// Initialize the field pane
		JPanel fieldPane = new JPanel();
		fieldPane.setOpaque(false);
		fieldPane.setBorder(new EmptyBorder(2,0,0,0));
		fieldPane.setLayout(new BorderLayout());
		fieldPane.add(field,BorderLayout.CENTER);
		
		// Create the labeled pane
		JPanel labeledPane = new JPanel();
		labeledPane.setOpaque(false);
		labeledPane.setLayout(new BorderLayout());
		labeledPane.add(label,BorderLayout.WEST);
		labeledPane.add(fieldPane,BorderLayout.CENTER);
		return labeledPane;
	}
	
	/** Constructs the repository parameter */
	public RepositoryParameter(String name)
	{
		super(name);
		setBorder(new EmptyBorder(0,0,2,0));
		setLayout(new GridLayout(2,1));
		
		// Initialize the port pane
		JPanel portPane = getLabeledPane("Port",portField);
		portField.setColumns(5);
		portPane.setBorder(new EmptyBorder(0,5,0,0));
		
		// Initialize the server pane
		JPanel serverPane = new JPanel();
		serverPane.setOpaque(false);
		serverPane.setBorder(new EmptyBorder(0,0,2,0));
		serverPane.setLayout(new BorderLayout(1,2));
		serverPane.add(getLabeledPane("Server",serverField),BorderLayout.CENTER);
		serverPane.add(portPane,BorderLayout.EAST);
		add(serverPane);
		
		// Initialize the schema list
		schemaList.setBackground(Color.white);
		schemaList.setFocusable(false);
		
		// Initialize the schema pane
		JPanel schemaPane = new JPanel();
		schemaPane.setOpaque(false);
		schemaPane.setBorder(new EmptyBorder(2,0,0,0));
		schemaPane.setLayout(new BorderLayout());
		schemaPane.add(schemaList,BorderLayout.CENTER);
		add(schemaPane);
	}
	
	/** Returns the server URI */
	private String getServerString()
		{ return "http://"+serverField.getText()+":"+portField.getText()+"/SchemaStore/services/SchemaStore"; }
	
	/** Returns the selected schema */
	public Schema getSchema()
		{ return (Schema)schemaList.getSelectedItem(); }
	
	/** Returns the parameter value */
	public String getValue()
	{
		Schema schema = ((Schema)schemaList.getSelectedItem());
		if(schema==null) return null;
		return getServerString() + "#" + schema.getId();
	}

	/** Sets the parameter value */
	public void setValue(String value)
	{
		// Set the server and port values
		serverField.setText(value.replaceAll("http://","").replaceAll(":.*",""));
		portField.setText(value.replaceAll("/SchemaStore/services/SchemaStore.*","").replaceAll(".*:",""));

		// Select the schema
		String schemaID = value.replaceAll(".*#","");
		for(int i=0; i<schemaList.getItemCount(); i++)
			if(((Schema)schemaList.getItemAt(i)).getId().equals(schemaID))
				{ schemaList.setSelectedIndex(i); break; }
	}
	
	/** Highlights the parameter */
	public void setHighlight(boolean highlight)
	{
		Color color = highlight ? Consts.YELLOW : Consts.WHITE;
		serverField.setBackground(color); portField.setBackground(color);
		schemaList.setBackground(color);
	}
	
	/** Handles the entry of server information */
	public void caretUpdate(CaretEvent arg0)
		{ new UpdateSchemaList().start(); }

	/** Adds an action listener */
	public void addActionListener(ActionListener listener)
		{ schemaList.addActionListener(listener); }

	/** Removes an action listener */
	public void removeActionListener(ActionListener listener)
		{ schemaList.removeActionListener(listener); }
}