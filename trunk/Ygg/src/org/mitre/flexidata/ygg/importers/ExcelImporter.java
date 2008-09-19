package org.mitre.flexidata.ygg.importers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;



import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;

/**
 * 
 * ExcelConverter is a poor man's importer. It imports a relational definition
 * stored in an excel sheet The format of the schema is [table name][column
 * name] [documentation] A row with only table name (with no column name)
 * contains name and documentation for the table.
 * 
 * @author HAOLI
 * 
 */

public class ExcelImporter extends Importer {
	protected HSSFWorkbook _excelWorkbook;
	private URI _excelURI;
	private HashMap<String, Entity> _entities;
	private HashMap<String, Attribute> _attributes;
	private static Domain D_ANY = new Domain(nextId(), ANY, null, 0);
	
	
	// get rid of characters
	protected String cleanup(String s) {
		s = s.trim().replaceAll("'", "\'").replaceAll("\"", "\\\"");  //.replaceAll(" ", "_");
		return s;
	}

	protected void generate() {
		int numSheets = _excelWorkbook.getNumberOfSheets();
		
		// iterate and load individual work sheets
		for (int s = 0; s < numSheets; s++) {
			HSSFSheet sheet = _excelWorkbook.getSheetAt(s);

			// iterate through rows and create table/attribute nodes
			for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
				readRow(sheet.getRow(i));
			}
		}
	}

	protected void readRow(HSSFRow row) {
		
		String tblName = cleanup(row.getCell(0).getRichStringCellValue().toString());
		String attName = cleanup(row.getCell(1).getRichStringCellValue().toString());
		String documentation = cleanup(row.getCell(2).getRichStringCellValue().toString());
		Entity tblEntity;
		Attribute attribute;

		if (tblName.length() == 0 && attName.length() == 0)
			return;

		// create an entity for a table
		if (!_entities.containsKey(tblName)) {
			tblEntity = new Entity(nextId(), tblName, "", 0);
			_entities.put(tblName, tblEntity);
		} else
			tblEntity = _entities.get(tblName);

		// create an attribute
		if (attName.length() > 0) {
			attribute = new Attribute(nextId(), attName, documentation, tblEntity.getId(), D_ANY
					.getId(), null, null, 0);
			_attributes.put(attribute.getName(), attribute);
		}
	}

	/** Returns the importer name */
	public String getName()
		{ return "Excel Importer (xls)"; }
	
	/** Returns the importer description */
	public String getDescription()
		{ return "Imports Excel formatted schema into the schema store."; }

	/** Returns the importer URI type */
	public Integer getURIType()
		{ return FILE; }
	
	/** Returns the importer URI file types */
	public ArrayList<String> getFileTypes()
	{
		ArrayList<String> filetypes = new ArrayList<String>(3);
		filetypes.add("xls");
		filetypes.add("csv");
		filetypes.add("xlsx");
		return filetypes;
	}

	protected void initialize() throws ImporterException
	{
		try {
			InputStream excelStream;
			_entities = new HashMap<String, Entity>();
			_attributes = new HashMap<String, Attribute>();

			// Do nothing if the excel sheet has been cached.
			if (_excelURI != null && _excelURI.equals(uri))
				return;

			_excelURI = uri;
			excelStream = _excelURI.toURL().openStream();
			_excelWorkbook = new HSSFWorkbook(excelStream);
			excelStream.close();
		} catch (IOException e) {
			throw new ImporterException(ImporterException.PARSE_FAILURE, e.getMessage());
		} 
	}

	public static void main(String[] args) throws IOException, URISyntaxException,
			ImporterException {
		File excel = new File("Example.xls");
		ExcelImporter tester = new ExcelImporter();
		tester.importSchema(excel.getName(), "", "", excel.toURI());
	}

	protected ArrayList<SchemaElement> generateSchemaElementList() {
		ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();

		for (Entity e : _entities.values())
			schemaElements.add(e);
		for (Attribute a : _attributes.values())
			schemaElements.add(a);
		return schemaElements;
	}

	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> getExtendedSchemaIDs() throws ImporterException
		{ return new ArrayList<Integer>(); }
	
	/** Generate the schema elements */
	public ArrayList<SchemaElement> getSchemaElements() throws ImporterException
	{
		generate();
		return generateSchemaElementList();
	}
}
