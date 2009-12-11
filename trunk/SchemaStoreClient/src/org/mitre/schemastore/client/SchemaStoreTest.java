package org.mitre.schemastore.client;

import java.io.File;
import java.net.URI;

import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.model.schemaInfo.model.DomainSchemaModel;
import org.mitre.schemastore.model.schemaInfo.model.RelationalSchemaModel;
import org.mitre.schemastore.porters.schemaImporters.DDLImporter;
import org.mitre.schemastore.porters.schemaImporters.HierarchicalDomainValueImporter2;

public class SchemaStoreTest {
	static int size(String value) {
		return value == null ? 0 : value.length();
	}

	static public void main(String args[]) {
		// Display the schemas found within the repository
		try {
			Repository repository;

			repository = new Repository("Local Repository", Repository.DERBY, new URI("schemastore"), "schemastore", "postgres", "postgres");

//			repository = new Repository(Repository.POSTGRES, new URI("implus.mitre.org"), "d3", "postgres", "implus$"); 

			// repository = new Repository(Repository.POSTGRES, new
			// URI("ygg.mitre.org"), "goldstandard", "postgres", "postgres");

			// repository = new Repository(Repository.POSTGRES, new
			// URI("ygg.mitre.org"), "schemastore", "postgres", "postgres");

			SchemaStoreClient client = new SchemaStoreClient(repository);

			// test connection
			// for (Schema schema : client.getSchemas())
			// System.out.println(schema.getId() + ": " + schema.getName());

			// test ExcelImporter
			// File excel = new File("TED_Final.xls");
			// ExcelImporter tester = new ExcelImporter();
			// tester.setClient(client);
			// tester.importSchema(excel.getName(), "", "", excel.toURI());

			// test HierarchicalDomainValueImporter
			File hexcel = new File("pmork3.xls");
			HierarchicalDomainValueImporter2 htester = new HierarchicalDomainValueImporter2();
			htester.setClient(client);
			int schemaID = htester.importSchema("Pmork Hierarchical domain values", "mhl", "", hexcel.toURI());

			// File excel = new File("DAF.xls");
			// HierarchicalExcelImporter importer = new
			// HierarchicalExcelImporter();
			// importer.setClient(client);
			//			
			// int schemaID = importer.importSchema(excel.getName(), "mhl",
			// "testHierarchicalExcelImporter", excel.toURI());
			// Schema s = client.getSchema(schemaID);

//			File sql = new File("fixed-DCGS31TED.sql");
//			DDLImporter importer = new DDLImporter();
//			importer.setClient(client);
//			int schemaID = importer.importSchema(sql.getName(), "mhl", "test ddl importer", sql.toURI());
	
			System.out.println(schemaID + " success ");

			DomainSchemaModel model = new DomainSchemaModel(); 
			SchemaInfo g = new SchemaInfo(client.getSchemaInfo(schemaID));
			
			
			for (SchemaElement se : g.getElements(null)) {
				System.out.println( se.getClass().getName() + " = " + se.getName());
				
				// Subtype subtype = (Subtype) se;
				// Integer parent = subtype.getParentID();
				// Integer child = subtype.getChildID();
				//
				// System.out.println(g.getElement(parent).getName() + " --- " +
				// g.getElement(child).getName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
