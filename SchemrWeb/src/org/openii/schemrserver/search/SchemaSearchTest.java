package org.openii.schemrserver.search;

import java.io.File;
import java.rmi.RemoteException;

import junit.framework.TestCase;

public class SchemaSearchTest extends TestCase {
	public void testSearch(){
		String searchTerm = "Schema10"; 
		MatchSummary[] search;
		try {
			search = SchemaSearch.performSearch(searchTerm, null, null, true);
		} catch (RemoteException e) {
			assertFalse(true);
			e.printStackTrace();
			return;
		}
		for (MatchSummary match : search){
			match.schema.toString();
			assertTrue(search.length > 0);
		}
	}
	
	public void testSearchXSD(){
		MatchSummary[] search;
		try {
			search = SchemaSearch.performSearch(null, new File("data/thalia/arizona.xsd"), "xsd", true);
		} catch (RemoteException e) {
			assertFalse(true);
			e.printStackTrace();
			return;
		}
		for (MatchSummary match : search){
			match.schema.toString();
			assertTrue(search.length > 0);
		}
	}

	public void testSearchDDL(){
		MatchSummary[] search;
		try {
			search = SchemaSearch.performSearch(null, new File("data/test_blog.ddl"), "ddl", true);
		} catch (RemoteException e) {
			assertFalse(true);
			e.printStackTrace();
			return;
		}
		for (MatchSummary match : search){
			match.schema.toString();
			assertTrue(search.length > 0);
		}
	}
}
