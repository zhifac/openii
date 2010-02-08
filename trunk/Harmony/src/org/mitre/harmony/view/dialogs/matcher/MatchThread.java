package org.mitre.harmony.view.dialogs.matcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.mitre.harmony.matchers.MatchScore;
import org.mitre.harmony.matchers.MatchScores;
import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.TypeMappings;
import org.mitre.harmony.matchers.mergers.MatchMerger;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.filters.FilterManager;
import org.mitre.harmony.model.filters.Focus;
import org.mitre.harmony.model.project.MappingManager;
import org.mitre.harmony.model.project.ProjectMapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/** Constructs the match pane for the matcher wizard */
public class MatchThread extends Thread
{
	/** Stores the harmony model */
	private HarmonyModel harmonyModel;
	
	/** Stores the voters to be used in matching */
	private ArrayList<MatchVoter> voters;
	
	/** Stores the merger to be used in matching */
	private MatchMerger merger;
	
	/** Stores the type mappings to be used in matching */
	private TypeMappings typeMappings;
	
	/** Used to halt the running of the matchers */
	private boolean stop = false;
	
	/** Stores the list of match listeners */
	private ArrayList<MatchListener> listeners = new ArrayList<MatchListener>();
	
	/** Class for monitoring match progress */
	private class ProgressThread extends Thread
	{
		/** Stores the total number of mappings being matched */
		private Integer currentMapping=0, totalMappings=0;
		
		/** Stores the current mapping being matched */
		private String leftSchema, rightSchema;
		
		/** Stores the current voter being used */
		private MatchVoter voter = null;
		
		/** Indicates that the thread should be stopped */
		private boolean stop=false;
		
		/** Sets the total number of mappings */
		void setTotalMappings(Integer totalMappings)
			{ this.currentMapping=-1; this.totalMappings=totalMappings; }
		
		/** Sets the current schema pair being matched */
		void setCurrentSchemaPair(String leftSchema, String rightSchema)
			{ this.leftSchema=leftSchema; this.rightSchema=rightSchema; currentMapping++; voter=null; }
		
		/** Sets the current voter being used */
		void setCurrentVoter(MatchVoter voter)
			{ this.voter=voter; }
		
		/** Runs the thread */
		public void run()
		{
			while(!stop)
			{
				// Update progress information
				if(voter==null) voter = voters.get(0);

				// Update the schema progress
				Double voterProgress = 100.0*voter.getPercentComplete();
				Double overallProgress = 0.0;
				try {
					Double votersProgress = (100*voters.indexOf(voter)+voterProgress)/voters.size();
					overallProgress = new Double((100*currentMapping+votersProgress)/totalMappings);
				} catch(Exception e) {}
				String status = "Matching " + leftSchema + " to " + rightSchema + " with " + voter.getName();
				for(MatchListener listener : listeners)
				{
					listener.updateVoterProgress(voterProgress, status);
					listener.updateOverallProgress(overallProgress);
				}
					
				// Wait a second before updating progress again
				try { Thread.sleep(1000); } catch(Exception e) {}
			}
		}
	
		/** Stops the progress thread */
		public void stopThread()
			{ stop=true; }		
	}
	
	/** Constructs the match thread */
	MatchThread(HarmonyModel harmonyModel, ArrayList<MatchVoter> voters, MatchMerger merger, TypeMappings typeMappings)
		{ this.harmonyModel=harmonyModel; this.voters=voters; this.merger=merger; this.typeMappings=typeMappings; }	
	
	/** Returns the generated matcher name */
	private String getMatcherName()
	{
		// Handles the case where all voters were used
		if(voters.size() == MatcherManager.getVoters().size())
			return merger.getName() + "(All Voters)";
		
		// Handles the case where a single voter was used
		if(voters.size() == 1) return voters.get(0).getName();

		// Handles the case where a subset of voters was used
		String matcherName = merger.getName() + "(";
		for (MatchVoter voter : voters) 
		{
			if (matcherName.length() + voter.getName().length() > 45)
				{ matcherName += "..., "; break; }
			matcherName += voter.getName() + ", ";
		}
		matcherName = matcherName.substring(0, matcherName.length() - 2) + ")";
		return matcherName;
	}

	/** Returns the list of filtered schema info for the specified side */
	private FilteredSchemaInfo getFilteredSchemaInfo(Integer schemaID, Integer side)
	{
		// Check to make sure that the schema is currently in focus
		FilterManager filters = harmonyModel.getFilters();
		if(!filters.inFocus(side, schemaID)) return null;

		// Create the filtered schema info object
		HierarchicalSchemaInfo schemaInfo = harmonyModel.getSchemaManager().getSchemaInfo(schemaID);
		FilteredSchemaInfo filteredSchemaInfo = new FilteredSchemaInfo(schemaInfo);

		// Set the filter roots
		Focus focus = filters.getFocus(side,schemaID);
		if(focus!=null && !focus.contains(schemaID))
			filteredSchemaInfo.setFilteredRoots(focus.getFocusedIDs());
			
		// Filter by minimum and maximum depth
		filteredSchemaInfo.setMinDepth(filters.getMinDepth(side));
		filteredSchemaInfo.setMaxDepth(filters.getMaxDepth(side));
				
		// Set the hidden elements
		ArrayList<Integer> hiddenElements = new ArrayList<Integer>();
		hiddenElements.addAll(harmonyModel.getPreferences().getFinishedElements(schemaID));
		if(focus!=null) hiddenElements.addAll(focus.getHiddenElements());
		filteredSchemaInfo.setHiddenElements(hiddenElements);

		// Return the filtered schema info object
		return filteredSchemaInfo;
	}
    
	/** Runs the matchers on the specified schemas */
	private void runMatch(Integer mappingID, FilteredSchemaInfo leftSchemaInfo, FilteredSchemaInfo rightSchemaInfo, String matcherName, ProgressThread progressThread)
	{
		// Generate the match scores for the left and right roots
		merger.initialize(leftSchemaInfo, rightSchemaInfo, typeMappings);
		for(MatchVoter voter : voters)
		{
			progressThread.setCurrentVoter(voter);
			voter.initialize(leftSchemaInfo, rightSchemaInfo, typeMappings);
			merger.addVoterScores(voter.match());
		}
		MatchScores matchScores =  merger.getMatchScores();
		
		// Store the generated mapping cells
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		MappingManager manager = harmonyModel.getMappingManager();
		for (MatchScore matchScore : matchScores.getScores())
		{	
			// Don't proceed if process has been stopped
			if(stop) return;
			
			// Don't store mapping cells which were already validated
			Integer mappingCellID = manager.getMappingCellID(matchScore.getElement1(), matchScore.getElement2());
			if(mappingCellID!=null && manager.getMappingCell(mappingCellID).getValidated()) continue;
			
			// Get the mapping cell properties
			Integer id = manager.getMappingCellID(matchScore.getElement1(), matchScore.getElement2());
			Integer input = matchScore.getElement1();
			Integer output = matchScore.getElement2();
			Double score = matchScore.getScore();
			Date date = Calendar.getInstance().getTime();
			
			// Generate the mapping cell
			mappingCells.add(MappingCell.createProposedMappingCell(id, mappingID, input, output, score, matcherName, date, null));
		}
		manager.getMapping(mappingID).setMappingCells(mappingCells);
	}
	
	/** Runs the thread */
	public void run()
	{		
		// Generate the matcher name
		String matcherName = getMatcherName();

		// Retrieve the visible mappings
		ArrayList<ProjectMapping> mappings = new ArrayList<ProjectMapping>();
		for(ProjectMapping mapping : harmonyModel.getMappingManager().getMappings())
			if(mapping.isVisible()) mappings.add(mapping);
		
		// Gather up the schema info
		HashMap<Integer,FilteredSchemaInfo> schemaInfoList = new HashMap<Integer,FilteredSchemaInfo>();
		for(ProjectMapping mapping : mappings)
		{
			Integer sourceID = mapping.getSourceId(), targetID = mapping.getTargetId();
			if(!schemaInfoList.containsKey(sourceID))
				schemaInfoList.put(sourceID, getFilteredSchemaInfo(sourceID,HarmonyConsts.LEFT));
			if(!schemaInfoList.containsKey(targetID))
				schemaInfoList.put(targetID, getFilteredSchemaInfo(targetID,HarmonyConsts.RIGHT));
		}

		// Create a thread for monitoring the progress of the match process
		ProgressThread progressThread = new ProgressThread();
		progressThread.start();
		progressThread.setTotalMappings(mappings.size());
		
		// Matches all left schemas to all right schemas
		for(ProjectMapping mapping : mappings)
		{
			// Don't proceed if the matching has been stopped
			if(stop) break;

			// Get schema info
			FilteredSchemaInfo leftSchemaInfo = schemaInfoList.get(mapping.getSourceId());
			FilteredSchemaInfo rightSchemaInfo = schemaInfoList.get(mapping.getTargetId());
			
			// Inform the progress thread of the current pair of schemas being matched
			String leftName = leftSchemaInfo.getSchema().getName();
			String rightName = rightSchemaInfo.getSchema().getName();
			progressThread.setCurrentSchemaPair(leftName,rightName);
				
			// Run the matcher of the current pair of schemas
			try { runMatch(mapping.getId(),leftSchemaInfo,rightSchemaInfo,matcherName,progressThread); }
			catch (Exception e) { System.out.println("(E) MatchThread.run - " + e.getMessage()); }
		}
		
		// Stops the progress thread
		progressThread.stopThread();
		
		// Inform listeners when the matching is completed
		for(MatchListener listener : listeners)
			listener.matchCompleted();
	}
	
	/** Stops the matching process */
	public void stopThread()
		{ stop=true; }
	
	/** Adds a match listener */
	public void addListener(MatchListener listener) { listeners.add(listener); }
}