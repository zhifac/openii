package org.openii.schemr;

import org.openii.schemr.SchemaStoreIndex.CandidateSchema;
import org.openii.schemr.client.model.Query;

public class SchemrServer {

	public static CandidateSchema [] getCandidateSchemas(Query query) {
//		ArrayList<Schema> candidateSchemas = null;
//		try {
//			candidateSchemas = new ArrayList<Schema>();	
//			ArrayList<Schema> all = SchemaUtility.getCLIENT().getSchemas();
//			for (int i=0;i<30;i++) {
//				candidateSchemas.add(all.get(i));
//			}
//			candidateSchemas.add(SchemaUtility.getCLIENT().getSchema(52384));			
//			candidateSchemas.add(SchemaUtility.getCLIENT().getSchema(17));
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}			
		CandidateSchema [] candidateSchemas = SchemaStoreIndex.searchIndex(SchemaUtility.LOCAL_INDEX_DIR, query);			
		System.out.println("Found "+candidateSchemas.length+" candidate schemas");
		return candidateSchemas;
	}
	
	public static void main(String[] args) {

		// filter for candidate schemas
		CandidateSchema [] candidateSchemas = getCandidateSchemas(null);
		
		System.out.println("Found "+candidateSchemas.length+" candidate schemas");

	}
	
}
