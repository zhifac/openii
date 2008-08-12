package org.openii.schemr;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.LockObtainFailedException;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

public class SchemaStoreIndex {

	public static void main(String[] args) {

		System.out.println(args.length + " args:");
		for (String s : args) {
			System.out.println("\t" + s);
		}

		if (args.length < 2) {
			System.out
					.println("Usage: java SchemStoreIndex <SchemaStore client URL> <index directory>");
			return;
		}

		String ssUrl = args[0];
		SchemaStoreClient ssclient = new SchemaStoreClient(ssUrl);
		String testIndexDir = args[1];

		buildIndex(ssclient, testIndexDir);
	}

	public static void buildIndex(SchemaStoreClient ssclient, String testIndexDir) {
		Date start = new Date();

		Analyzer analyzer = new StandardAnalyzer();
		IndexWriter iwriter;
		try {
			iwriter = new IndexWriter(testIndexDir, analyzer, true);
			iwriter.setMaxFieldLength(25000);

			SchemaStoreIndex.createIndex(iwriter, ssclient);

			iwriter.optimize();
			iwriter.close();

		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Date end = new Date();
		System.out.println("Elapse time: " + (end.getTime() - start.getTime()) / 1000 + " s");
	}

	public static void createIndex(IndexWriter iwriter, SchemaStoreClient client)
			throws RemoteException, CorruptIndexException, IOException {
		for (Schema schema : client.getSchemas()) {

			// For schemas, check the name and description
			Document schemaDoc = new Document();
			schemaDoc.add(new Field("title", schema.getName(), Field.Store.YES,
					Field.Index.TOKENIZED));
			schemaDoc.add(new Field("summary", schema.getDescription(), Field.Store.YES,
					Field.Index.TOKENIZED));
			schemaDoc.add(new Field("uid", schema.getId().toString(), Field.Store.YES,
					Field.Index.NO));

			// For schema elements, check the name and description
			for (SchemaElement schemaElement : client.getSchemaElements(schema.getId())) {
				String schemaType = schemaElement.getClass().getSimpleName().toLowerCase();
				schemaDoc.add(new Field(schemaType, schemaElement.getName(), Field.Store.YES,
						Field.Index.TOKENIZED));
				schemaDoc.add(new Field(schemaType, schemaElement.getDescription(),
						Field.Store.YES, Field.Index.TOKENIZED));
			}

			List<Field> fields = schemaDoc.getFields();
			for (Field f : fields) {
				System.out.println("\t" + f.name() + " | " + f.stringValue());
			}
			System.out.println(schema.getName() + ": " + schemaDoc.getFields().size()
					+ " fields added to index.");
			iwriter.addDocument(schemaDoc);
		}
	}
	
	public static ArrayList<Schema> searchIndex(File indexDir, Query query) {
		ArrayList<Schema> schemas = new ArrayList<Schema>();
		IndexReader reader;
		try {
			reader = IndexReader.open(indexDir);
			IndexSearcher isearcher = new IndexSearcher(reader);
			Hits hits = isearcher.search(query);
	
			for (int i = 0; i < hits.length(); i++) {
				Document hitDoc = hits.doc(i);
				
				//TODO get schema ID and instantiate schema
				
				schemas.add(schema dude)
				
			}
	
			isearcher.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return schemas;
	}
}
