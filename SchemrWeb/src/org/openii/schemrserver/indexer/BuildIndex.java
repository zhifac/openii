package org.openii.schemrserver.indexer;

import java.io.IOException;
import java.rmi.RemoteException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;

import org.openii.schemrserver.indexer.SchemaStoreIndex;
import org.openii.schemrserver.indexer.SchemaUtility;

public class BuildIndex {
	public static String TEST_INDEX_DIR = SchemaUtility.LOCAL_INDEX_DIR;
	
	public static void main(String[] args) throws CorruptIndexException, IOException{
		System.out.println("Building index in "+SchemaUtility.LOCAL_INDEX_DIR);
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriter iwriter;
		try {
			iwriter = new IndexWriter(SchemaUtility.LOCAL_INDEX_DIR, analyzer, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} 
		try {
			SchemaStoreIndex.createIndex(iwriter, SchemaUtility.getCLIENT());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iwriter.optimize();
		iwriter.close();
		
		
	}
}
