package org.mitre.openii.model;

import org.mitre.schemastore.client.Repository;

/** Interface used by objects listening to the Repository manager */
public interface RepositoryListener
{
	/** Informs the listener that the specified repository has been added */
	public void repositoryAdded(Repository repository);
	
	/** Informs the listener that the specified repository has been deleted */
	public void repositoryDeleted(Repository repository);
}
