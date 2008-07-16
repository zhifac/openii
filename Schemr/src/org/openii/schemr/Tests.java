package org.openii.schemr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.SchemaElementList;

public class Tests extends TestCase {

	public static junit.framework.Test suite() {
		return new TestSuite(Tests.class);
	}

	/**
	 * 
	 */
	public void testLucene() {
		Analyzer analyzer = new StandardAnalyzer();
		Directory directory = new RAMDirectory();
		IndexWriter iwriter;
		try {
			iwriter = new IndexWriter(directory, analyzer, true);
			iwriter.setMaxFieldLength(25000);
			Document doc = new Document();
			String text = "Index me banana";
			doc.add(new Field("fieldname", text, Field.Store.YES,
					Field.Index.TOKENIZED));

			iwriter.addDocument(doc);
			iwriter.optimize();
			iwriter.close();

			Field f = (Field) doc.getFields().get(0);
			System.out.println("\t" + f.name() + " | " + f.stringValue());

			IndexSearcher isearcher = new IndexSearcher(directory);
			QueryParser parser = new QueryParser("fieldname", analyzer);
			Query query = parser.parse("banana");
			Hits hits = isearcher.search(query);
			assertEquals(1, hits.length());

			for (int i = 0; i < hits.length(); i++) {
				Document hitDoc = hits.doc(i);
				assertEquals("Index me banana", hitDoc.get("fieldname"));
			}

			isearcher.close();
			directory.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	} // end testSanity

	static SchemaStoreClient CLIENT = new SchemaStoreClient(
			"http://localhost:8080/SchemaStore/services/SchemaStore");

	public static String TEST_INDEX_DIR = SchemaUtility.DEFAULT_ROOT + "/unitest/index";;
	
	public void testBuildIndex() {

		File f = new File(TEST_INDEX_DIR);
		f.deleteOnExit();
		assertFalse("Cannot save index to '" +TEST_INDEX_DIR+ "' directory, please delete it first", f.exists());

		Date start = new Date();

		Analyzer analyzer = new StandardAnalyzer();
		IndexWriter iwriter;
		try {
			iwriter = new IndexWriter(TEST_INDEX_DIR, analyzer, true);
			iwriter.setMaxFieldLength(25000);

			SchemaStoreIndex.createIndex(iwriter, CLIENT);

			iwriter.optimize();
			iwriter.close();

			IndexReader reader = IndexReader.open(TEST_INDEX_DIR);
			IndexSearcher isearcher = new IndexSearcher(reader);

			QueryParser parser = new QueryParser("summary", analyzer);
			Query query = parser.parse("root");
			Hits hits = isearcher.search(query);
			assertEquals(1, hits.length());

			for (int i = 0; i < hits.length(); i++) {
				Document hitDoc = hits.doc(i);
				assertEquals("Organism", hitDoc.get("title"));
			}

			isearcher.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			f.deleteOnExit();
		}

		Date end = new Date();

		System.out.println("Elapse time: " + (end.getTime() - start.getTime()) / 1000 + " s");
		
		

	}

	public void testSerializeDeserialize() {
		ArrayList<Schema> schemas = SchemaUtility.getAllSchemas(CLIENT);
		
		String filePath = SchemaUtility.DEFAULT_ROOT+"/unitest";
		int i = 0;
		for (Schema s : schemas) {
			i++;
			if (i % 10 == 0) {
				Integer schemaID = s.getId();
				String name = s.getName();
				try {
					FileOutputStream fos = new FileOutputStream(filePath+"/"+name+"_"+i);
					SchemaElementList elementList = SchemaUtility.getSchemaElementList(CLIENT, schemaID);
					SchemaUtility.serializeSchema(s, elementList, fos);
					fos.close();
					
					FileInputStream fis = new FileInputStream(filePath+"/"+name+"_"+i);
					SchemaXml result = SchemaUtility.deserializeSchema(fis);
					
					assertTrue("", s.equals(result.getSchema()));
					
					SchemaElement[] origElems = elementList.getSchemaElements();
					SchemaElement[] newElems = result.getSchemaElementList().getSchemaElements();
					assertTrue("SchemaElementList not equal length", origElems.length == newElems.length);
					
					HashSet<Integer> origIDs = new HashSet<Integer>();
					HashSet<Integer> newIDs = new HashSet<Integer>();
					for (int j = 0; j < origElems.length; j++) {
						origIDs.add(origElems[j].getId());
						newIDs.add(newElems[j].getId());
					}
					System.out.println(origIDs.toString());
					System.out.println(newIDs.toString());
					for (int k = 0; k < origElems.length; k++) {
						assertTrue("Orig elems does not contain ID: "+newElems[k].getId(), origIDs.contains(newElems[k].getId()));
					}


				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
