package org.mitre.openii.views.manager.schemas;

import java.rmi.RemoteException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.warehouse.client.DeleteDatabaseClient;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;

/** Constructs the Delete Data Source Dialog class */
public class DeleteDataSourceDialog
{
	static public void delete(Shell shell, DataSource dataSource)
	{
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm Delete", "Are you sure you want to delete data source '" + dataSource.getName() + "'?");
		if(confirmed)
		{
			boolean success = OpenIIManager.deleteDataSource(dataSource.getId());
			if(!success) MessageDialog.openError(shell, "Deletion Failure", "The data source failed to be properly deleted");
			
			Repository selectedRepository = RepositoryManager.getSelectedRepository();
			try
			{
				DeleteDatabaseClient d = new DeleteDatabaseClient(dataSource.getName(), selectedRepository);
				boolean deleteDatabaseSuccess = d.deleteInstanceDatabase();
				if(!deleteDatabaseSuccess) MessageDialog.openError(shell, "Deletion Failure", "The data source failed to be properly deleted");
			}
			catch(NoDataFoundException e)
			{	System.out.println("----Application Terminated----");
				System.out.println(e.getMessage());	
			}
			catch(RemoteException e)
			{	System.out.println("----Application Terminated----");
				System.out.println(e.getMessage());	
			}
		}
	}
}