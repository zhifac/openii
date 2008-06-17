// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane.editPane;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Schema;

import view.sharedComponents.EditPaneInterface;
import view.sharedComponents.EditPaneInterface.LinkPane.Link;

import model.DataSources;
import model.Schemas;

/** Class for editing a schema sources */
class EditSourcesPane extends EditPaneInterface implements MouseListener, MouseMotionListener
{	
	/** Stores the schema to which the entity is associated */
	private Schema schema;
	
	/** Stores the edit pane for switching to secondary pane */
	private ExplorerEditPane editPane;
	
	/** Stores the sources attached to this entity */
	private ArrayList<DataSource> sources = new ArrayList<DataSource>();

	/** Stores the sources deleted from this entity */
	private ArrayList<DataSource> deletedSources = new ArrayList<DataSource>();
	
	/** Tracks the current source being edited */
	private DataSource currDataSource = null;
	
	// Stores various pane objects
	private JLabel addLink = new JLabel("(Add)");
	
	/** Returns the source list */
	private JPanel getSourceList()
	{
		ArrayList<LinkPane> sourceList = new ArrayList<LinkPane>();
		for(DataSource source : sources)
		{
			LinkPane pane = new LinkPane(source);
			pane.getLink().addMouseListener(this);
			pane.getLink().addMouseMotionListener(this);
			sourceList.add(pane);
		}
		return new LinkListPane("Sources:",sourceList,addLink);
	}
	
	/** Constructs the Edit Source pane */
	EditSourcesPane(Schema schema, ExplorerEditPane editPane)
	{
		this.schema = schema;
		this.editPane = editPane;
		
		// Populate the various elements
		for(DataSource dataSource : DataSources.getDataSources(schema.getId()))
			sources.add(DataSources.getDataSource(dataSource.getId()));
		
		// Initialize the add link
		addLink.addMouseListener(this);
		addLink.addMouseMotionListener(this);
		
		// Construct the Edit Domain pane
		setLayout(new BorderLayout());
		setOpaque(false);
		add(getSourceList(),BorderLayout.CENTER);
	}
	
	/** Retrieves information from the secondary pane */
	public void saveSecondaryInfo(EditPaneInterface secondaryPane, boolean deleted)
	{
		// Get the source
		EditSourcePane sourcePane = (EditSourcePane)secondaryPane;
		DataSource source = sourcePane.getDataSource();
		
		// Delete the old sources
		if(currDataSource!=null)
			for(int i=0; i<sources.size(); i++)
				if(sources.get(i).getName().equals(currDataSource.getName()))
					{ sources.remove(i); break; }
		
		// Add new source if not being deleted
		if(!deleted && source.getName().length()>0)
		{
			String uniqueName = getUniqueName(source.getName(), new ArrayList<Object>(sources));
			if(!uniqueName.equals(source.getName()))
				source = new DataSource(source.getId(),uniqueName,source.getSchemaID(),source.getUrl());
			sources.add(source);
		}
		else if(source.getId()!=null) deletedSources.add(source);

		// Redisplay the new list of sources
		remove(0);
		add(getSourceList());
	}
	
	/** Handles the saving of the schema sources */
	public void save()
	{
		// Deletes the sources that were removed
		for(DataSource source : deletedSources)
		{
			EditSourcePane secondaryPane = new EditSourcePane(schema,source);
			secondaryPane.delete();			
		}

		// Saves the sources that were created
		for(DataSource source : sources)
		{
			EditSourcePane secondaryPane = new EditSourcePane(schema,source);
			secondaryPane.save();		
		}
	}
	
	/** Handles the deletion of a schema */
	public boolean delete()
		{ Schemas.deleteSchema(schema); return true; }
	
	/** Switch to a secondary pane for adding a source if the link is selected */
	public void mouseClicked(MouseEvent e)
	{
		// Handles the addition of a link
		if(e.getSource()==addLink)
		{
			currDataSource = null;
			EditSourcePane secondaryPane = new EditSourcePane(schema,null);
			editPane.displaySecondaryPane(secondaryPane,false);
		}
		
		// Handles the editing of a link
		else
		{
			currDataSource = (DataSource)((Link)e.getSource()).getObject();
			EditSourcePane secondaryPane = new EditSourcePane(schema,currDataSource);
			editPane.displaySecondaryPane(secondaryPane,true);
		}

		// Set the mouse cursor back to an arrow
		setCursor(Cursor.getDefaultCursor());
	}
	
	/** Displays a hand cursor when the mouse is over the edit link */
	public void mouseEntered(MouseEvent e)
		{ setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
	
	/** Resets the mouse cursor if moved off of the edit link */
	public void mouseExited(MouseEvent e)
		{ setCursor(Cursor.getDefaultCursor()); }

	// Unused listener event
	public void mouseMoved(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
}