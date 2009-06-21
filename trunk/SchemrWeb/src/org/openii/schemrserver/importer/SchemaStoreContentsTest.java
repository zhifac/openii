package org.openii.schemrserver.importer;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;
import org.openii.schemrserver.indexer.SchemaUtility;

public class SchemaStoreContentsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		try {
			ArrayList<Schema> schemas = SchemaUtility.getCLIENT().getSchemas();
			System.out.println("Found "+schemas.size()+" schemas!");

			int i = 0;
			for (Schema s : schemas) {
				i++; if (i>100) break;
				System.out.println("\t"+s.getName());				
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

}
