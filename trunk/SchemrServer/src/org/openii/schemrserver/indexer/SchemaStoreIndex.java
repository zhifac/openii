package org.openii.schemrserver.indexer;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.LockObtainFailedException;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.Graph;
import org.openii.schemrserver.search.Query;

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
		SchemaStoreClient ssclient;
		try {
			ssclient = new SchemaStoreClient(ssUrl);
			String testIndexDir = args[1];
			buildIndex(ssclient, testIndexDir);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

	public static final String TITLE = "title";
	public static final String SUMMARY = "summary";
	public static final String UID = "uid";
	// TODO is this the right set?
//	private static final String [] TYPES = new String [] {TITLE, SUMMARY, "entity", "attribute", "domain", "domainvalue"};
	private static final String [] TYPES = new String [] {TITLE, SUMMARY, "entity", "attribute", "containment", "domainvalue"};
	public static final HashSet<String> TYPES_SET = new HashSet<String>();
	static {
		TYPES_SET.addAll(Arrays.asList(TYPES));
	}

	public static void createIndex(IndexWriter iwriter, SchemaStoreClient client)
			throws RemoteException, CorruptIndexException, IOException {
		for (Schema schema : client.getSchemas()) {

			// For schemas, check the name and description
			Document schemaDoc = new Document();
			schemaDoc.add(new Field(TITLE, schema.getName(), Field.Store.YES, Field.Index.TOKENIZED));
			schemaDoc.add(new Field(SUMMARY, schema.getDescription(), Field.Store.YES, Field.Index.TOKENIZED));
			schemaDoc.add(new Field(UID, schema.getId().toString(), Field.Store.YES, Field.Index.NO));
		
			// For schema elements, check the name and description
			Graph g = client.getGraph(schema.getId());
			ArrayList<SchemaElement> elements = g.getElements(null);
			
			
			for (SchemaElement schemaElement : elements) {
				String schemaType = schemaElement.getClass().getSimpleName().toLowerCase();
				schemaDoc.add(new Field(schemaType, schemaElement.getName(), Field.Store.YES, Field.Index.TOKENIZED));
				schemaDoc.add(new Field(schemaType, schemaElement.getDescription(), Field.Store.YES, Field.Index.TOKENIZED));
			}

//			List<Field> fields = schemaDoc.getFields();
//			for (Field f : fields) {
//				System.out.println("\t" + f.name() + " | " + f.stringValue());
//			}
			System.out.println(schema.getName() + ": " + schemaDoc.getFields().size() + " fields added to index.");
			iwriter.addDocument(schemaDoc);
		}
	}
	
	public static CandidateSchema [] searchIndex(String filePath, Query schemrQuery) {
		
		File indexDir = new File(filePath);
		boolean askBuildIndex = true;
		if (indexDir.exists()) {
			for (String filename : indexDir.list()) {
				if (filename.startsWith("segments")) {
					askBuildIndex = false; break;
				}
			}			
		}
		if (askBuildIndex) {
			// TODO: should not build every time!
			buildIndex(SchemaUtility.getCLIENT(), filePath);
		}
		
		CandidateSchema [] candidateSchemas = null; 
		IndexReader reader;
		try {
						
			reader = IndexReader.open(filePath);
			IndexSearcher isearcher = new IndexSearcher(reader);
			
			org.apache.lucene.search.Query luceneQuery = translateQuery(schemrQuery);
			
			Hits hits = isearcher.search(luceneQuery);
			
			candidateSchemas = new CandidateSchema [hits.length()];
			System.out.println(hits.length() + " hits");
			for (int i = 0; i < hits.length(); i++) {
				Document hitDoc = hits.doc(i);								
				String title = hitDoc.get(TITLE);
				String summary = hitDoc.get(SUMMARY);
				String schemaIDstr = hitDoc.get(UID);
				Integer schemaID = Integer.parseInt(schemaIDstr);
				candidateSchemas[i] = new CandidateSchema(title, summary, schemaID);				
				System.out.println(candidateSchemas[i]);
			}
	
			isearcher.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return candidateSchemas;
	}

	private static org.apache.lucene.search.Query translateQuery(Query schemrQuery) throws ParseException {
		Analyzer analyzer = new StandardAnalyzer();
        QueryParser qp = new MultiFieldQueryParser(SchemaStoreIndex.TYPES, analyzer);
        String queryString = schemrQuery.toKeywordSearchString();
        System.out.println(queryString);
        return qp.parse(queryString);
	}
	
	public static class CandidateSchema {
		public String name;
		public String summary;
		public Integer uid;		
		public CandidateSchema(String name, String summary, Integer uid) {
			super();
			this.name = name;
			this.summary = summary;
			this.uid = uid;
		}
		@Override
		public String toString() {
			return "("+uid+") "+name+": "+summary;
		}
	}
	
	
}
