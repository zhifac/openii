// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mitre.schemastore.model.DataSource;

import model.DataSources;
import model.SelectedObjects;
import model.listeners.DataSourceListener;
import model.listeners.SelectedObjectsListener;

/** Class for displaying the data source list for the explorer pane */
class DataSourcePane extends JPanel implements ActionListener, DataSourceListener, SelectedObjectsListener
{
	/** Stores the list of currently available data sources */
	private JComboBox dataSourceList = new JComboBox();
	
	/** Data source renderer */
	private class DataSourceRenderer extends DefaultListCellRenderer
	{
		/** Defines how items in the data source list should be rendered */
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{
			// Find the component text
			String text = value instanceof Integer ? DataSources.getDataSource((Integer)value).getName() : value.toString();
			
			// Generate a data source list item component
			JPanel pane = new JPanel();
			pane.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
			pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS));
			pane.add(new JLabel(text));
			return pane;
		}
	}
	
	/** Display data sources */
	private void updateDataSourceList()
	{
		// Gather the data sources to display
		ArrayList<DataSource> dataSources = new ArrayList<DataSource>();
		for(DataSource dataSource : DataSources.getDataSources())
			if(SelectedObjects.inSelectedGroups(dataSource.getSchemaID())) dataSources.add(dataSource);
		DataSources.sort(dataSources);
		
		// Set the data source list model
		Vector<Object> selectionIDs = new Vector<Object>();
		selectionIDs.add("<Data Source>");
		for(DataSource dataSource : dataSources) selectionIDs.add(dataSource.getId());
		dataSourceList.setModel(new DefaultComboBoxModel(selectionIDs));
	}
	
	/** Constructs the Selection Pane */
	DataSourcePane()
	{
		// Set up the selection options
		dataSourceList.setBackground(Color.white);
		dataSourceList.setFocusable(false);
		dataSourceList.setRenderer(new DataSourceRenderer());
		dataSourceList.addActionListener(this);
		
		// Creates the selection options pane
		setBackground(Color.white);
		setBorder(new EmptyBorder(5,0,0,1));
		setLayout(new BorderLayout());
		add(new JLabel("Find Schema For: "),BorderLayout.WEST);
		add(dataSourceList,BorderLayout.CENTER);

		// Add a listener to monitor for changes in the data sources
		DataSources.addDataSourceListener(this);
		SelectedObjects.addSelectedObjectsListener(this);

		// Initializes the data sources
		updateDataSourceList();
	}
	
	/** Handles the selection of a data source */
	public void actionPerformed(ActionEvent e)
	{
		Object selectedItem = dataSourceList.getSelectedItem();
		if(selectedItem instanceof Integer)
		{
			Integer schemaID = DataSources.getDataSource((Integer)selectedItem).getSchemaID();
			SelectedObjects.removeSelectedObjectsListener(this);
			SelectedObjects.setSelectedSchema(schemaID);
			SelectedObjects.addSelectedObjectsListener(this);
		}
	}
	
	/** Handles the addition of a data source */
	public void dataSourceAdded(DataSource dataSource)
	{
		DefaultComboBoxModel model = (DefaultComboBoxModel)dataSourceList.getModel();
		for(int i=1; i<model.getSize(); i++)
		{
			String name = DataSources.getDataSource((Integer)model.getElementAt(i)).getName();
			if(name.compareTo(dataSource.getName())>0)
				{ model.insertElementAt(dataSource.getId(), i); break; }
		}
	}

	/** Handles the updating of a data source */
	public void dataSourceUpdated(DataSource dataSource)
		{ repaint(); }
	
	/** Handles the removal of a data source */
	public void dataSourceRemoved(DataSource dataSource)
	{
		dataSourceList.removeItem(dataSource.getId());
		if(dataSourceList.getSelectedItem()==null && dataSourceList.getItemCount()>0)
			SelectedObjects.setSelectedSchema((Integer)dataSourceList.getItemAt(0));
	}

	/** Handles changes to the data source list */
	public void selectedGroupsChanged()
		{ updateDataSourceList(); }

	/** Handles changes to the selected schema */
	public void selectedSchemaChanged()
		{ dataSourceList.setSelectedIndex(0); }
	
	// Unused listener events
	public void selectedComparisonSchemaChanged() {}
}