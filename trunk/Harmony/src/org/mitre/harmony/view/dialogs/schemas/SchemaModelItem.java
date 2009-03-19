package org.mitre.harmony.view.dialogs.schemas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.graph.GraphModel;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/** Displays a schema item with the model setting */
class SchemaModelItem extends JPanel
{
	// Constants to define which side the schema should be displayed on
	static final int NONE = 0;
	static final int LEFT = 1;
	static final int RIGHT = 2;
	
	/** Stores the schema for this schema item */
	private Schema schema;

	// Stores the model selection for the specific schema
	private JComboBox modelSelection = new JComboBox();
	
	/** Constructs the schema model item */
	SchemaModelItem(Schema schema, HarmonyModel harmonyModel)
	{
		this.schema = schema;
		
		// Initialize the model selection box
		modelSelection.setFocusable(false);
		modelSelection.setOpaque(false);
		modelSelection.setPreferredSize(new Dimension(80,20));
		modelSelection.addItem("<Default>");
		for(GraphModel model : HierarchicalGraph.getGraphModels())
			modelSelection.addItem(model);

		// Set the selected model
		GraphModel selectedModel = harmonyModel.getPreferences().getSchemaGraphModel(schema.getId());
		if(selectedModel!=null)
			modelSelection.setSelectedItem(selectedModel);
		
		// Constructs the schema item
		setBorder(new EmptyBorder(3,0,3,0));
		setMaximumSize(new Dimension(10000,26));
		setOpaque(false);
		setFocusable(false);
		setLayout(new BorderLayout());
		add(new SchemaModelRow(modelSelection,new JLabel(schema.getName())));
	}
	
	/** Returns the schema associated with this item */
	Schema getSchema() { return schema; }
	
	/** Returns the graph model associated with this item */
	GraphModel getModel()
	{
		Object model = modelSelection.getSelectedItem();
		return (model instanceof GraphModel) ? (GraphModel)model : null;
	}
}