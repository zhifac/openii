// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import org.mitre.harmony.matchers.TypeMappings;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;

/** MatchVoter Interface - A match voter scores source-target linkages based on a specific algorithm */	
public abstract class MatchVoter
{
	// Stores the match merger schema information
	protected FilteredSchemaInfo schema1, schema2;

	/** Stores the match merger type mapping information */
	private TypeMappings typeMappings;
	
	// Stores the completed and total number of comparisons that need to be performed
	protected int completedComparisons=0, totalComparisons=1;
	
	/** Return the name of the match voter */
	abstract public String getName();
	
	/** Indicates if a default voter */
	public boolean isDefault() { return false; }
	
	/** Initializes the match voter */
	public void initialize(FilteredSchemaInfo schema1, FilteredSchemaInfo schema2)
		{ this.schema1 = schema1; this.schema2 = schema2; this.typeMappings = null; }
	
	/** Initializes the match voter */
	public void initialize(FilteredSchemaInfo schema1, FilteredSchemaInfo schema2, TypeMappings typeMappings)
		{ this.schema1 = schema1; this.schema2 = schema2; this.typeMappings = typeMappings; }
	
	/** Generates scores for the specified graphs */
	abstract public VoterScores match();
	
	/** Indicates if the specified elements can validly be mapped together */
	protected boolean isAllowableMatch(SchemaElement element1, SchemaElement element2)
		{ return typeMappings==null || typeMappings.isMapped(element1, element2); }
	
	/** Indicates the completion percentage of the matcher */
	public double getPercentComplete()
		{ return 1.0 * completedComparisons / totalComparisons; }
}