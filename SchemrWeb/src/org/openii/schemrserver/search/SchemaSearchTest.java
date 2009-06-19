package org.openii.schemrserver.search;

import java.rmi.RemoteException;

import junit.framework.TestCase;

public class SchemaSearchTest extends TestCase {
	private static String searchTerm = "Score"; 
	public void testSearch(){
		MatchSummary[] search;
		try {
			search = SchemaSearch.performSearch(searchTerm, null);
		} catch (RemoteException e) {
			assertFalse(true); //should not be triggered
			e.printStackTrace();
			return;
		}
		for (MatchSummary match : search){
			match.schema.toString();
			assertTrue(search.length > 0);
		}
		
	}
}
