// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.searchPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.regex.Matcher;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.mitre.galaxy.model.Schemas;
import org.mitre.galaxy.model.SelectedObjects;
import org.mitre.galaxy.model.listeners.SelectedObjectsListener;
import org.mitre.galaxy.model.search.Keyword;
import org.mitre.galaxy.model.search.PrimaryMatches;
import org.mitre.galaxy.model.search.SearchManager;
import org.mitre.galaxy.model.search.SearchResult;
import org.mitre.galaxy.model.server.ImageManager;
import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;


/** Class for displaying a schema's search results */
class ResultPane extends JPanel implements MouseListener, SelectedObjectsListener
{
	/** Stores the search result being displayed in this pane */
	private SearchResult searchResult = null;
	
	/** Stores the schema being displayed in this pane */
	private Schema schema = null;
	
	/** Stores information about the primary matches being displayed in this pane */
	private PrimaryMatches primaryMatches = null;
	
	/** Private class for managing a primary match pane */
	private class PrimaryMatchPane extends JPanel implements MouseListener
	{
		/** Stores the list of subsumed matches */
		ArrayList<SchemaElement> subsumedMatches;
		
		/** Tracks if the subsumed matches are currently expanded or collapsed */
		private boolean expanded = false;
		
		// Stores objects used within the primary match pane
		private JLabel expansionLabel = new JLabel();
		private JPanel primaryPane = new JPanel();
		private JPanel subsumedPane = new JPanel();
				
		/** Constructs a match pane */
		private JPanel getMatchPane(SchemaElement match)
		{
			// Creates the match label
			JLabel matchLabel = new JLabel(markupText(match.getName()));
			matchLabel.setFont(new Font(null,Font.PLAIN,12));
			
			// Create the domain label
			JLabel domainLabel = new JLabel();
			if(match instanceof Attribute)
			{
				Domain domain = (Domain)(Schemas.getSchemaElement(((Attribute)match).getDomainID()));
				domainLabel.setText(markupText(" (Domain: "+domain.getName()+" - "+domain.getDescription()+")"));
			}
			domainLabel.setFont(new Font(null,Font.PLAIN,10));
			domainLabel.setVerticalTextPosition(SwingConstants.CENTER);
			
			// Create the description label
			String desc = "";
			if(match instanceof Entity) desc = "Entity - " + match.getDescription();
			if(match instanceof Attribute)
			{
				SchemaElement entity = Schemas.getSchemaElement(((Attribute)match).getEntityID());
				desc = "Attribute of " + entity.getName() + " - " + match.getDescription();
			}
			if(match instanceof DomainValue)
			{
				Domain domain = (Domain)(Schemas.getSchemaElement(((DomainValue)match).getDomainID()));
				desc = "Domain value of " + domain.getName() + " - " + match.getDescription();
			}
			if(match instanceof Relationship)
			{
				SchemaElement left = Schemas.getSchemaElement(((Relationship)match).getLeftID());
				SchemaElement right = Schemas.getSchemaElement(((Relationship)match).getRightID());
				desc = "Relationship between " + left.getName() + " and " + right.getName();
			}
			if(match instanceof Alias)
			{
				SchemaElement element = Schemas.getSchemaElement(((Alias)match).getElementID());
				desc = "Alias of " + element.getName() + " - " + match.getDescription();
			}
			JLabel descLabel = new JLabel(markupText(desc));
			descLabel.setFont(new Font(null,Font.PLAIN,10));
			
			// Create the match pane
			JPanel matchPane = new JPanel();
			matchPane.setLayout(new BorderLayout());
			matchPane.setOpaque(false);
			matchPane.add(matchLabel,BorderLayout.WEST);
			matchPane.add(domainLabel,BorderLayout.CENTER);
			
			// Create the text pane
			JPanel textPane = new JPanel();
			textPane.setBorder(new EmptyBorder(0,3,0,0));
			textPane.setLayout(new BorderLayout());
			textPane.setOpaque(false);
			textPane.add(matchPane,BorderLayout.NORTH);
			textPane.add(descLabel,BorderLayout.SOUTH);
			
			// Creates the match pane
			JPanel pane = new JPanel();
			pane.setBorder(new EmptyBorder(2,0,2,0));
			pane.setLayout(new BorderLayout());
			pane.setOpaque(false);
			pane.add(new JLabel(new ImageIcon(ImageManager.getImage("Document"))),BorderLayout.WEST);
			pane.add(textPane,BorderLayout.CENTER);
			return pane;
		}
		
		/** Constructs the primary match pane */
		PrimaryMatchPane(SchemaElement match)
		{
			// Get a list of subsumed matches
			subsumedMatches = primaryMatches.getSubsumedMatches(match);
			
			// Creates the expansion label
			ImageIcon icon = new ImageIcon(ImageManager.getImage("Expand"));
			expansionLabel.setPreferredSize(new Dimension(icon.getIconWidth()+5,icon.getIconHeight()));
			expansionLabel.setBorder(new EmptyBorder(0,0,0,5));
			if(!subsumedMatches.isEmpty())
				{ expansionLabel.setIcon(icon); expansionLabel.addMouseListener(this); }
			
			// Creates the primary pane
			primaryPane.setLayout(new BorderLayout());
			primaryPane.setOpaque(false);
			primaryPane.add(expansionLabel,BorderLayout.WEST);
			primaryPane.add(getMatchPane(match),BorderLayout.CENTER);
			
			// Creates subsumed match pane
			subsumedPane.setBorder(new EmptyBorder(0,40,0,0));
			subsumedPane.setLayout(new BoxLayout(subsumedPane,BoxLayout.Y_AXIS));
			subsumedPane.setOpaque(false);
			for(SchemaElement subsumedMatch : subsumedMatches)
				subsumedPane.add(getMatchPane(subsumedMatch));
			subsumedPane.setVisible(false);
			
			// Creates the primary match pane
			setLayout(new BorderLayout());
			setOpaque(false);
			add(primaryPane,BorderLayout.NORTH);
			add(subsumedPane,BorderLayout.CENTER);
		}

		/** Paints the primary match pane */
		public void paint(Graphics g)
		{
			super.paint(g);
		
			// If expanded, draw in tree lines
			if(expanded)
			{
				// Calculate out the line measurements
				int matchCount = subsumedMatches.size();
				double matchHeight = subsumedPane.getHeight()/matchCount;
				int minY = primaryPane.getHeight()-4;
				int maxY = primaryPane.getHeight()+(int)(matchHeight*(matchCount-0.5));
				
				// Draw the tree lines
				g.setColor(Color.darkGray);
				g.drawLine(30,minY,30,maxY);
				for(int i=0; i<matchCount; i++)
				{
					int height = minY+(int)(matchHeight*(i+0.5)+4);
					g.drawLine(30,height,35,height);
				}
			}
		}
		
		/** Handles the expansion/collapse of the subsumed matches */
		public void mouseClicked(MouseEvent e)
		{
			expanded = !expanded;
			expansionLabel.setIcon(new ImageIcon(ImageManager.getImage(expanded?"Collapse":"Expand")));
			subsumedPane.setVisible(expanded);
		}
		
		// Unused event listeners
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}		
	
	/** Marks up the text */
	private String markupText(String text)
	{
		text = text.replaceAll(" ","&nbsp;");
		for(Keyword keyword : SearchManager.getKeywords())
		{
			int offset = 0;
			Matcher matcher = keyword.getPattern().matcher(text.toLowerCase());
			while(matcher.find())
			{
				text = text.substring(0,matcher.start()+offset) + "</td><td style='background-color:#EEEE99'>" + text.substring(matcher.start()+offset,matcher.end()+offset) + "</td><td>" + text.substring(matcher.end()+offset,text.length());
				offset += 51;
			}
		}
		return "<html><table cellpadding=0 cellspacing=0><tr><td>"+text+"</td></tr></table></html>";
	}
	
	/** Updates the background color */
	private void updateColor()
	{
		final Color normalColor = Color.white;
		final Color highlightColor = new Color((float)1.0,(float)1.0,(float)0.85);
		setBackground(SelectedObjects.getSelectedSchema().equals(searchResult.getSchema().getId()) ? highlightColor : normalColor);	
	}
	
	/** Constructs the results pane */
	ResultPane(SearchResult searchResult)
	{
		// Store the search result and primary matches
		this.searchResult = searchResult;
		this.schema = searchResult.getSchema();
		primaryMatches = searchResult.getPrimaryMatches();
		
		// Create the title pane
		JLabel schemaLabel = new JLabel(markupText(schema.getName()+" - "+schema.getDescription()));
		schemaLabel.setBorder(new EmptyBorder(0,5,0,0));
		JPanel titlePane = new JPanel();
		titlePane.setLayout(new BoxLayout(titlePane,BoxLayout.X_AXIS));
		titlePane.setOpaque(false);
		titlePane.add(new JLabel(new ImageIcon(ImageManager.getImage("Schema"))));
		titlePane.add(schemaLabel);
		
		// Create the matches pane
		JPanel matchesPane = new JPanel();
		matchesPane.setLayout(new BoxLayout(matchesPane,BoxLayout.Y_AXIS));
		matchesPane.setOpaque(false);
		for(SchemaElement match : primaryMatches.getPrimaryMatches())
			matchesPane.add(new PrimaryMatchPane(match));
		
		// Generate the search result pane
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.setOpaque(false);
		pane.add(titlePane,BorderLayout.NORTH);
		pane.add(matchesPane,BorderLayout.CENTER);
		
		setBorder(new EmptyBorder(4,4,4,4));
		setLayout(new BorderLayout());
		add(pane,BorderLayout.NORTH);
		updateColor();
		
		// Add listeners
		addMouseListener(this);
		SelectedObjects.addSelectedObjectsListener(this);
		
	}
	
	/** Fills in the paint details for the results pane */
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.gray);
		g.drawLine(0,getHeight()-1,getWidth()-1,getHeight()-1);
	}
	
	/** Selects the schema associated with this search result */
	public void mouseClicked(MouseEvent e)
	{
		if(SelectedObjects.inSelectedGroups(schema.getId()))
			SelectedObjects.setSelectedSchema(schema.getId());
	}
	
	/** Handles the selection of a new schema */
	public void selectedSchemaChanged()
		{ updateColor(); }
	
	// Unused listener events
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void selectedComparisonSchemaChanged() {}
	public void selectedGroupsChanged() {}
}
