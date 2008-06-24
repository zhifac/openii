package org.openii.schemr;

import junit.framework.TestCase;
import junit.framework.TestSuite;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
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
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainTest extends TestCase {

  
  static HashMap SEARCH_TERMS = new HashMap();
  
  static {
    SEARCH_TERMS.put("stuff", "value");
  }
  
  static final File INDEX_DIR = new File("/usr/local/google/workspace/lucene/schemr/index");

  public static junit.framework.Test suite() {
    return new TestSuite(MainTest.class);
  }

  /**
   * 
   */
  public void testSanity() {
    Analyzer analyzer = new StandardAnalyzer();
    Directory directory = new RAMDirectory();
    IndexWriter iwriter;
    try {
      iwriter = new IndexWriter(directory, analyzer, true);
      iwriter.setMaxFieldLength(25000);
      Document doc = new Document();
      String text = "Index me banana";
      doc.add(new Field("fieldname", text, Field.Store.YES, Field.Index.TOKENIZED));

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

  /**
   * 
   */
  public void testAllSchemas() {

    if (INDEX_DIR.exists()) {
      INDEX_DIR.delete();
    }
//    assertFalse("Cannot save index to '" +INDEX_DIR+ "' directory, please delete it first", INDEX_DIR.exists());

    Date start = new Date();
    
    Analyzer analyzer = new StandardAnalyzer();
    IndexWriter iwriter;
    try {
      iwriter = new IndexWriter(INDEX_DIR, analyzer, true);
      iwriter.setMaxFieldLength(25000);

      SchemaStoreClient client = new SchemaStoreClient("http://localhost:8080/SchemaStore/services/SchemaStore");
      
      for(Schema schema : client.getSchemas()) {

        // For schemas, check the name and description
        Document schemaDoc = new Document();
        schemaDoc.add(new Field("title", schema.getName(), Field.Store.YES, Field.Index.TOKENIZED));
        schemaDoc.add(new Field("summary", schema.getDescription(), Field.Store.YES, Field.Index.TOKENIZED));        
        schemaDoc.add(new Field("uid", schema.getId().toString(), Field.Store.YES, Field.Index.NO));    

//        schemaDoc.add(new Field("contents", schema.getName()+" "+schema.getDescription(), Field.Store.YES, Field.Index.TOKENIZED));
//        // For schema elements, check the name and description
//        for(SchemaElement schemaElement : client.getSchemaElements(schema.getId())) {
//          schemaDoc.add(new Field("contents", schemaElement.getName(), Field.Store.YES, Field.Index.TOKENIZED));
//          schemaDoc.add(new Field("contents", schemaElement.getDescription(), Field.Store.YES, Field.Index.TOKENIZED));          
//        }

        // For schema elements, check the name and description
        for(SchemaElement schemaElement : client.getSchemaElements(schema.getId())) {
          String schemaType = schemaElement.getClass().getSimpleName().toLowerCase();
          schemaDoc.add(new Field(schemaType, schemaElement.getName(), Field.Store.YES, Field.Index.TOKENIZED));
          schemaDoc.add(new Field(schemaType, schemaElement.getDescription(), Field.Store.NO, Field.Index.TOKENIZED));          
        }

        List<Field> fields = schemaDoc.getFields();
        for (Field f : fields) {
          System.out.println("\t" + f.name() + " | " + f.stringValue());          
        }
        System.out.println(schema.getName()+": " + schemaDoc.getFields().size() + " fields added to index.");
        iwriter.addDocument(schemaDoc);
      }
      iwriter.optimize();
      iwriter.close();
      
      IndexReader reader = IndexReader.open(INDEX_DIR);
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
    }

    Date end = new Date();
    
    System.out.println("Elapse time: "+ (end.getTime() - start.getTime())/1000 + " s");
  
  }

}
