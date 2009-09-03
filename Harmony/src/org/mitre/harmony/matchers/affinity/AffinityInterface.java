package org.mitre.harmony.matchers.affinity;

import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.harmony.matchers.AffinityScore;

/**
 * Displays the affinity interface 
 * @author MDMORSE
 */
public interface AffinityInterface
{
	public AffinityScore getAffinity(FilteredSchemaInfo schemaInfo);
}
	