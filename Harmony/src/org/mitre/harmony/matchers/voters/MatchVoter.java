// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.mitre.harmony.matchers.MatchTypeMappings;
import org.mitre.harmony.matchers.MatcherOption;
import org.mitre.harmony.matchers.MatcherScores;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;

/** MatchVoter Interface - A match voter scores source-target linkages based on a specific algorithm */	
public abstract class MatchVoter
{
	// Stores the match merger schema information
	protected FilteredSchemaInfo schema1, schema2;

	/** Stores the match merger type mapping information */
	private MatchTypeMappings types;

	/** Stores the options specified for this matcher */
	protected LinkedHashMap<String, MatcherOption> options = new LinkedHashMap<String,MatcherOption>();

	/** Stores if this is a default voter */
	private boolean isDefault = false;

	/** Stores if this is a hidden voter */
	private boolean isHidden = false;

	// Stores the completed and total number of comparisons that need to be performed
	protected int completedComparisons = 0, totalComparisons = 1;

	/** Constructs the match voter */
	public MatchVoter()
	{
		for(MatcherOption option : getMatcherOptions())
			options.put(option.getName(), option);
	}
	
	/** Return the name of the match voter */
	abstract public String getName();

	/** Returns the list of options associated with the match voter */
	protected ArrayList<MatcherOption> getMatcherOptions() { return new ArrayList<MatcherOption>(); }
	
	// Match voter getters
	final public boolean isDefault() { return isDefault; }
	final public boolean isHidden() { return isHidden; }

	// Match voter setters
	final public void setDefault(boolean isDefault) { this.isDefault = isDefault; }
	final public void setHidden(boolean isHidden) { this.isHidden = isHidden; }

	/** Initializes the match voter */
	final public void initialize(FilteredSchemaInfo schema1, FilteredSchemaInfo schema2)
		{ this.schema1 = schema1; this.schema2 = schema2; this.types = null; }

	/** Initializes the match voter */
	final public void initialize(FilteredSchemaInfo schema1, FilteredSchemaInfo schema2, MatchTypeMappings types)
		{ this.schema1 = schema1; this.schema2 = schema2; this.types = types; }

	/** Gets the list of options */
	final public ArrayList<MatcherOption> getOptions()
		{ return new ArrayList<MatcherOption>(options.values()); }
	
	/**Gets the specified matcher option */
	final public String getOption(String name)
	{
		MatcherOption option = options.get(name);
		return option!=null ? option.getValue() : null;
	}
	
	/** Sets the specified option */
	final public void setOption(String name, String value)
	{
		MatcherOption option = options.get(name);
		if(option!=null) option.setValue(value);
	}
	
	/** Generates scores for the specified graphs */
	abstract public MatcherScores match();

	/** Indicates if the specified elements can validly be mapped together */
	final protected boolean isAllowableMatch(SchemaElement element1, SchemaElement element2)
		{ return types == null || types.isMapped(element1, element2); }

	/** Indicates the completion percentage of the matcher */
	final public double getPercentComplete()
		{ return 1.0 * completedComparisons / totalComparisons; }
}