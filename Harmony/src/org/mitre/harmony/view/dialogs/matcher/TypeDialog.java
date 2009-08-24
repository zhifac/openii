// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.matcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.matchers.mergers.TypedVoteMerger.TypeMappings;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.AbstractButtonPane;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Class used for selecting which type(s) to match to which type(s)
 * (e.g. Entities to entities and attributes to attributes.) 
 * @author JKORB
 */
class TypeDialog extends JDialog
{		
	// Stores the list of source and target types
	private HashSet<Class> sourceTypes = new HashSet<Class>();
	private HashSet<Class> targetTypes = new HashSet<Class>();
	
	// List of selected match voters
	private TypeMappings typeMappings = new TypeMappings();
	
	// Set of checkboxes containing the possible type pairings
	private JPanel checkboxes = new JPanel();
	
	/** Returns the text width */
	private int getTextWidth(String text, Graphics g)
		{ return (int)g.getFontMetrics().getStringBounds(text, g).getWidth(); }
	
	/** Display the checkbox labels */
	private class CheckboxLabels extends JPanel
	{
		public void paint(Graphics g)
		{
			// Paints the background
			g.setColor(new Color(0xd0,0xd0,0xd0));
			g.fillPolygon(new int[]{5,5,getWidth()-10}, new int[]{10,getHeight()-5,getHeight()-5},3);
			g.fillPolygon(new int[]{10,getWidth()-5,getWidth()-5}, new int[]{5,5,getHeight()-10},3);

			// Paints the labels
			g.setColor(Color.black);
			g.drawString("Left",(getWidth()-getTextWidth("Left",g))/3,2*getHeight()/3+5);
			g.drawString("Right",2*(getWidth()-getTextWidth("Left",g))/3,getHeight()/3+5);
		}
	}
	
	/** Displays the specified type label */
	private class TypeLabel extends JPanel
	{
		String label;
		boolean vertical;

		/** Constructs the label class */
		private TypeLabel(String label, boolean vertical)
		{
			this.label=label; this.vertical=vertical;
			setPreferredSize(vertical ? new Dimension(20,90) : new Dimension(90,20));
		}
		
		/** Draws the label */
		public void paint(Graphics g)
		{
		    Graphics2D g2 = (Graphics2D)g;
		    if(vertical) { g2.rotate(-Math.PI/2.0); g2.translate(-getHeight()+5, 0); }
		    else g2.translate(getWidth()-getTextWidth(label, g)-5, 0);
		    g2.drawString(label, 0, 15);
		}
	}
	
	/** Class for storing a type checkbox */
	private class TypeCheckBox extends JCheckBox
	{
		// Stores the source and target types
		private Class<SchemaElement> sourceType;
		private Class<SchemaElement> targetType;
		
		/** Initializes the type check box */
		private TypeCheckBox(Class<SchemaElement> sourceType, Class<SchemaElement> targetType, boolean selected)
		{
			this.sourceType = sourceType;
			this.targetType = targetType;
			setText("");
			setFocusable(false);
			setSelected(selected);
		}
	}
	
	/** Private class for defining the checkbox pane */
	private class CheckboxPane extends JPanel
	{
		/** Constructs the checkbox pane */
		private CheckboxPane()
		{
			setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10),new CompoundBorder(new LineBorder(Color.lightGray),new EmptyBorder(5,5,5,5))));
			setLayout(new GridBagLayout());
		}
		
		/** Adds a component to the checkbox pane */
		private void add(JPanel pane, int xLoc, int yLoc)
		{
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = xLoc;
			c.gridy = yLoc;
			c.fill = GridBagConstraints.BOTH;
			add(pane, c);
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
			for(Component component : checkboxes.getComponents())
			{
				TypeCheckBox checkbox = (TypeCheckBox)component;
				if(checkbox.isSelected())
				{
					ArrayList<Class<SchemaElement>> typeMapping = typeMappings.get(checkbox.sourceType);
					if(typeMapping==null) typeMappings.put(checkbox.sourceType, typeMapping = new ArrayList<Class<SchemaElement>>());
					typeMapping.add(checkbox.targetType);
				}
			}
			dispose();
		}

		/** Handles selection of cancel button */
		protected void button2Action()
			{ dispose(); }
	}

	/** Generates the checkbox pane */
	private JPanel getCheckboxPane()
	{		
		// Create pane for storing all checkbox items
		CheckboxPane checkboxPane = new CheckboxPane();

		// Set aside space for the checkbox labels
		checkboxPane.add(new CheckboxLabels(),0,0);
		
		// Generate the source labels
		JPanel sourceLabels = new JPanel();
		sourceLabels.setLayout(new GridLayout(sourceTypes.size(),1));
		for(Class<SchemaElement> sourceType : sourceTypes)
			sourceLabels.add(new TypeLabel(sourceType.getSimpleName(),false));
		checkboxPane.add(sourceLabels,0,1);
		
		// Generate the target labels
		JPanel targetLabels = new JPanel();
		targetLabels.setLayout(new GridLayout(1,targetTypes.size()));
		for(Class<SchemaElement> targetType : targetTypes)
			targetLabels.add(new TypeLabel(targetType.getSimpleName(),true));
		checkboxPane.add(targetLabels,1,0);
		
		// Generate the checkbox grid
		checkboxes.setLayout(new GridLayout(sourceTypes.size(),targetTypes.size()));
		for(Class<SchemaElement> sourceType : sourceTypes)
			for(Class<SchemaElement> targetType : targetTypes)
				checkboxes.add(new TypeCheckBox(sourceType, targetType,sourceType.equals(targetType)));
		checkboxPane.add(checkboxes,1,1);

		return checkboxPane;
	}

	/** Constructs the type match dialog */
	TypeDialog(HarmonyModel harmonyModel)
	{
		super(harmonyModel.getBaseFrame());
		
		// Determine the source and target types to be displayed
		for(SchemaElement sourceElement : harmonyModel.getMappingManager().getSchemaElements(MappingSchema.LEFT))
			sourceTypes.add(sourceElement.getClass());
		for(SchemaElement targetElement : harmonyModel.getMappingManager().getSchemaElements(MappingSchema.RIGHT))
			targetTypes.add(targetElement.getClass());
		
		// Generate the main pane
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.add(getCheckboxPane(),BorderLayout.CENTER);
		pane.add(new ButtonPane(),BorderLayout.SOUTH);
		
		// Lay out the dialog
		setTitle("Types To Match?");
    	setResizable(false);
		setModal(true);
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    	setContentPane(pane);
		pack();
		setLocationRelativeTo(harmonyModel.getBaseFrame());
		setVisible(true);
	}

	/** Returns the type mappings */
	TypeMappings getTypeMappings()
		{ return typeMappings; }
}