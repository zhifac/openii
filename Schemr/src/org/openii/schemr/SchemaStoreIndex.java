package org.openii.schemr;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.LockObtainFailedException;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

public class SchemaStoreIndex {

	public static String DEFAULT_INDEX_DIR = SchemaUtility.DEFAULT_ROOT + "/index";;

	public static void createIndex(IndexWriter iwriter, SchemaStoreClient client) throws RemoteException, CorruptIndexException, IOException {
		for (Schema schema : client.getSchemas()) {

			// For schemas, check the name and description
			Document schemaDoc = new Document();
			schemaDoc.add(new Field("title", schema.getName(), Field.Store.YES,
					Field.Index.TOKENIZED));
			schemaDoc.add(new Field("summary", schema.getDescription(),
					Field.Store.YES, Field.Index.TOKENIZED));
			schemaDoc.add(new Field("uid", schema.getId().toString(),
					Field.Store.YES, Field.Index.NO));

			// For schema elements, check the name and description
			for (SchemaElement schemaElement : client.getSchemaElements(schema
					.getId())) {
				String schemaType = schemaElement.getClass().getSimpleName()
						.toLowerCase();
				schemaDoc.add(new Field(schemaType, schemaElement.getName(),
						Field.Store.YES, Field.Index.TOKENIZED));
				schemaDoc.add(new Field(schemaType, schemaElement
						.getDescription(), Field.Store.YES,
						Field.Index.TOKENIZED));
			}

			List<Field> fields = schemaDoc.getFields();
			for (Field f : fields) {
				System.out.println("\t" + f.name() + " | " + f.stringValue());
			}
			System.out.println(schema.getName() + ": "
					+ schemaDoc.getFields().size() + " fields added to index.");
			iwriter.addDocument(schemaDoc);
		}
	}

	
	public static void main( String [] args ) {

		System.out.println(args.length+" args:");
		for (String s : args) {
			System.out.println("\t"+s);
		}
		
		if (args.length < 2) {
			System.out.println("Usage: java SchemStoreIndex <SchemaStore client URL> <index directory>");
			return;
		}
		
		String ssUrl = args[0];
		SchemaStoreClient ssclient = new SchemaStoreClient(ssUrl);
		String testIndexDir = args[1];

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
}
