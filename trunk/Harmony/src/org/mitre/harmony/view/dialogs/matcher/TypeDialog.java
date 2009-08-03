// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.matcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.matchers.mergers.TypedVoteMerger.TypeMappings;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.AbstractButtonPane;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Class used for selecting which type(s) to match to which type(s)
 * (e.g. Entities to entities and attributes to attributes.) 
 * @author JKORB
 */
class TypeDialog extends JDialog
{		
	// List of selected match voters
	private TypeMappings typeMappings = new TypeMappings();
	
	// Set of checkboxes containing the possible type pairings
	private ArrayList<TypeCheckBox> checkboxes = new ArrayList<TypeCheckBox>();
	
	/** Class for storing a type checkbox */
	private class TypeCheckBox extends JCheckBox
	{
		// Stores the source and target types
		private Class<SchemaElement> sourceType;
		private Class<SchemaElement> targetType;
		
		/** Initializes the type check box */
		private TypeCheckBox(Class<SchemaElement> sourceType, Class<SchemaElement> targetType)
		{
			this.sourceType = sourceType;
			this.targetType = targetType;
			setText("");
			setBackground(Color.white);
			setFocusable(false);
		}
	}
	
	/** Private class for defining the button pane */
	private class ButtonPane extends AbstractButtonPane
	{
		/** Constructs the button pane */
		public ButtonPane()
			{ super("OK", "Cancel"); }

		/** Handles selection of okay button */
		protected void button1Action()
		{
			// Retrieves all selected match voters from the dialog
			for(TypeCheckBox checkbox : checkboxes)
				if(checkbox.isSelected())
				{
					ArrayList<Class<SchemaElement>> typeMapping = typeMappings.get(checkbox.sourceType);
					if(typeMapping==null) typeMappings.put(checkbox.sourceType, typeMapping = new ArrayList<Class<SchemaElement>>());
					typeMapping.add(checkbox.targetType);
				}
			dispose();
		}

		/** Handles selection of cancel button */
		protected void button2Action()
			{ dispose(); }
	}
	
	/** Generates the type dialog */ @SuppressWarnings("unchecked")
	private JPanel mainPane()
	{
		// Create pane for storing all checkbox items
		JPanel checkboxPane = new JPanel();
		checkboxPane.setBackground(Color.white);
		checkboxPane.setLayout(new GridLayout(6, 6));
		
		// Generate the type grid
		Class[] types = new Class[] { Entity.class, Attribute.class, Domain.class, DomainValue.class, Relationship.class, Containment.class };		
		for(Class sourceType : types)
			for(Class targetType : types)
			{
				TypeCheckBox checkbox = new TypeCheckBox(sourceType, targetType);
				checkboxes.add(checkbox);
				if(sourceType.equals(targetType)) checkbox.setSelected(true);
				checkboxPane.add(checkbox);
			}
		
		JScrollPane checkboxScrollPane = new JScrollPane(checkboxPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		checkboxScrollPane.setPreferredSize(new Dimension(250, 300));
	    
		// Place list of roots in center of project pane
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(20,20,20,20));
		pane.setLayout(new BorderLayout());
		pane.add(new JLabel("                          Entity  Attribute Domain DomainValue Relationship Containment     "),BorderLayout.NORTH);
		
		JLabel longLabel = new JLabel();
		longLabel.setText("<html><body>Entity<p><p>" +
				"Attribute<p><p>" +
				"Domain<p><p>" +
				"DomainValue<p><p><p>" +
				"Relationship <p><p>" +
				"Containment<p><p>" +
				"</body></html>");

		pane.add(longLabel, BorderLayout.WEST);
		
		pane.add(checkboxScrollPane,BorderLayout.CENTER);
		pane.add(new ButtonPane(),BorderLayout.SOUTH);
		return pane;
	}

	/** Constructs the type match dialog */
	TypeDialog(HarmonyModel harmonyModel)
	{
		super(harmonyModel.getBaseFrame());
		setTitle("Which Types Would You Like To Match?");
		setModal(true);
    	setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setContentPane(mainPane());
		pack();
		setLocationRelativeTo(harmonyModel.getBaseFrame());
		setVisible(true);
	}

	/** Returns the type mappings */
	TypeMappings getTypeMappings()
		{ return typeMappings; }
}