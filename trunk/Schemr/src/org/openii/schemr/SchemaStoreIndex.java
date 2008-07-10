package org.openii.schemr;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
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

}
