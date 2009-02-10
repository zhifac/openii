package org.mitre.harmony.matchers.affinity;

import org.mitre.schemastore.model.graph.FilteredGraph;
import org.mitre.harmony.matchers.AffinityScore;

/**
 * Displays the affinity interface 
 * @author MDMORSE
 */
public interface AffinityInterface
{
	public AffinityScore getAffinity(FilteredGraph schema1);
}
	