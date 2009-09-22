package org.mitre.harmony.view.dialogs.matcher;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.matchers.mergers.MatchMerger.TypeMappings;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.matcher.wizard.WizardPanel;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.SchemaElement;

/** Constructs the type pane for the matcher wizard */
public class TypePane extends WizardPanel implements ActionListener
{
	// Defines the identifier for the match voter pane
	static public final String IDENTIFIER = "TYPE_PANEL";
	
	// Stores the list of source and target types
	private HashSet<Class> sourceTypes = new HashSet<Class>();
	private HashSet<Class> targetTypes = new HashSet<Class>();
	
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

	/** Returns the grid bag constraints */
	private GridBagConstraints getGridBagConstraints(int xLoc, int yLoc)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = xLoc;
		c.gridy = yLoc;
		c.fill = GridBagConstraints.BOTH;
		return c;
	}
    
	/** Constructs the type pane */
    public TypePane(HarmonyModel harmonyModel)
    {
		// Determine the source and target types to be displayed
		for(SchemaElement sourceElement : harmonyModel.getMappingManager().getSchemaElements(MappingSchema.LEFT))
			sourceTypes.add(sourceElement.getClass());
		for(SchemaElement targetElement : harmonyModel.getMappingManager().getSchemaElements(MappingSchema.RIGHT))
			targetTypes.add(targetElement.getClass());

		// Initialize the layout of the type pane
		JPanel pane = getPanel();
		pane.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10),new CompoundBorder(new LineBorder(Color.lightGray),new EmptyBorder(5,5,5,5))));
		pane.setLayout(new GridBagLayout());
		
		// Set aside space for the checkbox labels
		pane.add(new CheckboxLabels(),getGridBagConstraints(0,0));
		
		// Generate the source labels
		JPanel sourceLabels = new JPanel();
		sourceLabels.setLayout(new GridLayout(sourceTypes.size(),1));
		for(Class<SchemaElement> sourceType : sourceTypes)
			sourceLabels.add(new TypeLabel(sourceType.getSimpleName(),false));
		pane.add(sourceLabels,getGridBagConstraints(0,1));
		
		// Generate the target labels
		JPanel targetLabels = new JPanel();
		targetLabels.setLayout(new GridLayout(1,targetTypes.size()));
		for(Class<SchemaElement> targetType : targetTypes)
			targetLabels.add(new TypeLabel(targetType.getSimpleName(),true));
		pane.add(targetLabels,getGridBagConstraints(1,0));
		
		// Generate the checkbox grid
		checkboxes.setLayout(new GridLayout(sourceTypes.size(),targetTypes.size()));
		for(Class<SchemaElement> sourceType : sourceTypes)
			for(Class<SchemaElement> targetType : targetTypes)
			{
				TypeCheckBox checkbox = new TypeCheckBox(sourceType,targetType,true);
				checkbox.addActionListener(this);
				checkboxes.add(checkbox);
			}
		pane.add(checkboxes,getGridBagConstraints(1,1));
    }
       
    /** Describes the next pane to display */
    public String getNextPanelDescriptor()
    	{ return MatchPane.IDENTIFIER; }
    
    /** Describes the previous pane that was displayed */
    public String getBackPanelDescriptor()
    	{ return MatchVoterPane.IDENTIFIER; }

	/** Returns the type mappings */
	public TypeMappings getTypeMappings()
	{
		// Retrieves all selected match voters from the dialog
		TypeMappings typeMappings = new TypeMappings();
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
		return typeMappings;
	}

	/** Updates the 'next' button whenever the checkboxes are changed */
	public void actionPerformed(ActionEvent e)
		{ getWizard().setNextFinishButtonEnabled(getTypeMappings().size()>0); }
}