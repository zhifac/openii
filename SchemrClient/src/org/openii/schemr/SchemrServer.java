package org.openii.schemr;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.mitre.schemastore.model.Schema;
import org.openii.schemr.client.model.Query;

public class SchemrServer {

	public static ArrayList<Schema> getCandidateSchemas(Query query) {
		ArrayList<Schema> candidateSchemas = null;
		try {
			candidateSchemas = new ArrayList<Schema>();
			
			ArrayList<Schema> all = SchemaUtility.getCLIENT().getSchemas();
			for (int i=0;i<30;i++) {
				candidateSchemas.add(all.get(i));
			}
//			candidateSchemas.add(SchemaUtility.getCLIENT().getSchema(52384));			
//			candidateSchemas.add(SchemaUtility.getCLIENT().getSchema(17));			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("Found "+candidateSchemas.size()+" candidate schemas");
		return candidateSchemas;
	}
	
	public static void main(String[] args) {

		// filter for candidate schemas
		ArrayList<Schema> candidateSchemas = getCandidateSchemas(null);
		
		System.out.println("Found "+candidateSchemas.size()+" candidate schemas");

	}
	
}
