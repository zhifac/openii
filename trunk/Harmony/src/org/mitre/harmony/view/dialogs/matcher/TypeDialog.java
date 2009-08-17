// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import java.awt.*;



import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.matchers.mergers.TypedVoteMerger.TypeMappings;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaManager;
import org.mitre.harmony.model.mapping.MappingManager;
import org.mitre.harmony.view.dialogs.AbstractButtonPane;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/**
 * Class used for selecting which type(s) to match to which type(s)
 * (e.g. Entities to entities and attributes to attributes.) 
 * @author JKORB
 */
class TypeDialog extends JDialog
{		
	private HarmonyModel harmonyModel;
	
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
//			setBackground(Color.white);
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
		// Generate the source and target labels
		HashSet<SchemaElement> leftSchemaElements = harmonyModel.getMappingManager().getSchemaElements(MappingSchema.LEFT);
//			harmonyModel.getSelectedInfo().getSelectedElements(MappingSchema.LEFT);
		HashSet<SchemaElement> rightSchemaElements = harmonyModel.getMappingManager().getSchemaElements(MappingSchema.RIGHT);

		SchemaManager sm = harmonyModel.getSchemaManager();
//		String [] type1Array = { "Entity", "Attribute", "Domain", "DomainValue", "Relationship", "Containment"};
		
//		Class[] types = new Class[] { Entity.class, Attribute.class, Domain.class, DomainValue.class, Relationship.class, Containment.class };		
		
		HashMap<Class, Integer> sourceTypes = new HashMap<Class, Integer>();
		for (SchemaElement e : leftSchemaElements) {
			sourceTypes.put(e.getClass(), 1);
		}
		HashMap<Class, Integer> targetTypes = new HashMap<Class, Integer>();
		for (SchemaElement e: rightSchemaElements) {
			targetTypes.put(e.getClass(), 1);
		}
		
		
		
		// Create pane for storing all checkbox items
		JPanel checkboxPane = new JPanel();
//		checkboxPane.setBackground(Color.white);
		checkboxPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Generate the source label
		JLabel sourceLabel = new JLabel("Source", JLabel.CENTER);
		sourceLabel.setUI(new VerticalLabelUI(false));
		sourceLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		c.gridx = 0;
		c.ipadx = 20;
		c.fill = GridBagConstraints.VERTICAL;
		c.gridheight = sourceTypes.size();
		c.gridy = 2;
		checkboxPane.add(sourceLabel, c);

		c.ipadx = 0;
		c.gridheight = 1;
		
		
		// Generate the target label
		JLabel targetLabel = new JLabel("Target", JLabel.CENTER);
		targetLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		c.ipady = 20; 
		c.gridx = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = targetTypes.size();
		c.gridy = 0;
		checkboxPane.add(targetLabel, c);
		
		c.gridwidth = 1;
		c.ipady = 0; 
		
		int x = 2;
		int y = 2;
		for (Class type : sourceTypes.keySet()) {
			JLabel sLabel = new JLabel(type.getSimpleName());
//			sLabel.setFont(new Font("Arial", Font.PLAIN, 14));
			c.gridx = 1;
			c.gridy = y++;
			checkboxPane.add(sLabel, c);
		}
		for (Class type : targetTypes.keySet()) {
			JLabel tLabel = new JLabel(type.getSimpleName());
//			tLabel.setFont(new Font("Arial", Font.PLAIN, 14));
			tLabel.setUI(new VerticalLabelUI(false));
			c.gridx = x++;
			c.gridy = 1;
			checkboxPane.add(tLabel, c);		
		}
		
		// Generate the type grid
		x = 2;
		y = 2;
		
		for(Class sourceType : sourceTypes.keySet()) {
			for(Class targetType : targetTypes.keySet())
			{
				TypeCheckBox checkbox = new TypeCheckBox(sourceType, targetType);
				checkboxes.add(checkbox);
				if(sourceType.equals(targetType)) checkbox.setSelected(true);
				c.gridx = x++;
				c.gridy = y;
				checkboxPane.add(checkbox, c);
			}
			x = 2;
			y++;
		}
		
//		JPanel checkboxPane = new JPanel(checkboxPane);
//		checkboxPane.setPreferredSize(new Dimension(250, 300));
	    
		// Place list of roots in center of project pane
		JPanel pane = new JPanel();
//		pane.setBorder(new EmptyBorder(20,20,20,20));
		pane.setLayout(new BorderLayout());
		

		
		pane.add(checkboxPane,BorderLayout.CENTER);
		pane.add(new ButtonPane(),BorderLayout.SOUTH);
		return pane;
	}

	/** Constructs the type match dialog */
	TypeDialog(HarmonyModel harmonyModel)
	{
		super(harmonyModel.getBaseFrame());
		
		this.harmonyModel = harmonyModel;
		
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