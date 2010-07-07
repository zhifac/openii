// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import org.mitre.harmony.matchers.MatchTypeMappings;
import org.mitre.harmony.matchers.MatcherScores;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;

/** MatchVoter Interface - A match voter scores source-target linkages based on a specific algorithm */	
public abstract class MatchVoter {
	// Stores the match merger schema information
	protected FilteredSchemaInfo schema1, schema2;

	/** Stores the match merger type mapping information */
	private MatchTypeMappings typeMappings;
	
	/** Stores if this is a default voter */
	private boolean isDefault = false;
	
	/** Stores if this is a hidden voter */
	private boolean isHidden = false;

	/** Stores if this has configurable options */
	private boolean isConfigurable = false;

	// Stores the completed and total number of comparisons that need to be performed
	protected int completedComparisons = 0, totalComparisons = 1;
	
	/** Return the name of the match voter */
	abstract public String getName();

	// Match voter getters
	final public boolean isDefault() { return isDefault; }
	final public boolean isHidden() { return isHidden; }
	final public boolean isConfigurable() { return isConfigurable; }
	
	// Match voter setters
	final public void setDefault(boolean isDefault) { this.isDefault = isDefault; }
	final public void setHidden(boolean isHidden) { this.isHidden = isHidden; }
	final public void setConfigurable(boolean isConfigurable) { this.isConfigurable = isConfigurable; }
	
	/** Initializes the match voter */
	final public void initialize(FilteredSchemaInfo schema1, FilteredSchemaInfo schema2) {
		this.schema1 = schema1;
		this.schema2 = schema2;
		this.typeMappings = null;
	}
	
	/** Initializes the match voter */
	final public void initialize(FilteredSchemaInfo schema1, FilteredSchemaInfo schema2, MatchTypeMappings typeMappings) {
		this.schema1 = schema1;
		this.schema2 = schema2;
		this.typeMappings = typeMappings;
	}

	/** Generates scores for the specified graphs */
	abstract public MatcherScores match();
	
	/** Indicates if the specified elements can validly be mapped together */
	final protected boolean isAllowableMatch(SchemaElement element1, SchemaElement element2) {
		return typeMappings == null || typeMappings.isMapped(element1, element2);
	}
	
	/** Indicates the completion percentage of the matcher */
	final public double getPercentComplete() {
		return 1.0 * completedComparisons / totalComparisons;
	}
}