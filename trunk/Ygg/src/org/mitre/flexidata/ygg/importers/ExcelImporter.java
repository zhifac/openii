package org.mitre.flexidata.ygg.importers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

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
	private Workbook _excelWorkbook;
	private URI _excelURI;
	private HashMap<String, Entity> _entities;
	private HashMap<String, Attribute> _attributes;
	private static Domain D_ANY  = new Domain(nextId(), ANY, null, 0 ); 

	// get rid of characters
	protected String despace(String s) {
		s = s.trim().replaceAll(" ", "_");
		s = s.replaceAll("'", "\'");
		return s;
	}

	protected void generate() {
		Sheet[] sheets = _excelWorkbook.getSheets();

		// iterate and load individual work sheets
		for (int s = 0; s < sheets.length; s++) {
			Sheet sheet = sheets[s];
			int numRows = sheet.getRows();

			// iterate through rows and create table/attribute nodes
			for (int i = 0; i < numRows; i++) {
				readRow(sheet.getRow(i));
			}
		}
	}

	protected void readRow(Cell[] cells) {
		String tblName = despace(cells[0].getContents());
		String attName = despace(cells[1].getContents());
		String documentation = cells[2].getContents().trim();
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
			attribute = new Attribute(nextId(), attName, documentation, tblEntity.getId(),
					D_ANY.getId(), null, null, 0);
			_attributes.put(attribute.getName(), attribute);
		} else {
			// doc describes table if attribute doesn't exist
			tblEntity.setDescription(documentation);
		}
	}

	public ArrayList<String> getFileTypes() {
		ArrayList<String> filetypes = new ArrayList<String>(2);
		filetypes.add("xls");
		filetypes.add("csv");
		return filetypes;
	}

	protected void initialize(URI uri) throws IOException, URISyntaxException, BiffException {
		InputStream excelStream;
		_entities = new HashMap<String, Entity>();
		_attributes = new HashMap<String, Attribute>();

		// Do nothing if the excel sheet has been cached.
		if (_excelURI != null && _excelURI.equals(uri))
			return;

		_excelURI = uri;
		excelStream = _excelURI.toURL().openStream();
		_excelWorkbook = Workbook.getWorkbook(excelStream);
		excelStream.close();
	}

	public static void main(String[] args) throws IOException, URISyntaxException,
			ImporterException {
		File excel = new File("Example.xls");
		ExcelImporter tester = new ExcelImporter();
		tester.importSchema(excel.getName(), "", "", excel.toURI());
	}

	@Override
	public String getDescription() {
		return "Imports Excel formatted schema into the schema store.";
	}

	@Override
	public String getName() {
		return "Excel Importer (xls)";
	}

	protected ArrayList<SchemaElement> generateSchemaElementList() {
		ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();

		for (Entity e : _entities.values())
			schemaElements.add(e);
		for (Attribute a : _attributes.values())
			schemaElements.add(a);
		return schemaElements;
	}

	@Override
	// TODO why schemaID in the parameter list?
	protected ArrayList<SchemaElement> getSchemaElements(Integer schemaID, URI uri)
			throws ImporterException {
		try {
			initialize(uri);
			generate();
		} catch (URISyntaxException e) {
			throw new ImporterException(ImporterException.INVALID_URI, e.getMessage());
		} catch (IOException e) {
			throw new ImporterException(ImporterException.PARSE_FAILURE, e.getMessage());
		} catch (BiffException b) {
			throw new ImporterException(ImporterException.IMPORT_FAILURE, b.getMessage());
		}
		return generateSchemaElementList();
	}

	@Override
	public Integer getURIType() {
		return FILE;
	}

}
