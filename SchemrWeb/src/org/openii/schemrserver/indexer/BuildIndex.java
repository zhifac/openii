package org.openii.schemrserver.indexer;

import java.io.IOException;
import java.rmi.RemoteException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.openii.schemrserver.Consts;
import org.openii.schemrserver.SchemaUtility;

public class BuildIndex {

	public static void main(String[] args) throws CorruptIndexException, IOException{
		System.out.println("Building index in "+Consts.LOCAL_INDEX_DIR);
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriter iwriter;
		try {
			iwriter = new IndexWriter(Consts.LOCAL_INDEX_DIR, analyzer, true);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} 
		try {
			SchemaStoreIndex.createIndex(iwriter, SchemaUtility.getCLIENT());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		iwriter.optimize();
		iwriter.close();
	}
}
