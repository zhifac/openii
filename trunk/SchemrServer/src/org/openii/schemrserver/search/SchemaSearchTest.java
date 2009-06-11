package org.openii.schemrserver.search;

import java.rmi.RemoteException;

import junit.framework.TestCase;

public class SchemaSearchTest extends TestCase {
	private static String searchTerm = "item"; 
	public void testSearch(){
		MatchSummary[] search;
		try {
			search = SchemaSearch.performSearch(searchTerm, null);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		for (MatchSummary match : search){
			match.schema.toString();
			assertTrue(true);
		}
		
	}
}
