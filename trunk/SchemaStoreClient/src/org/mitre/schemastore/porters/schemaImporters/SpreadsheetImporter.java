/**
 *  Copyright 2008 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mitre.schemastore.porters.schemaImporters;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.ImporterException.ImporterExceptionType;

/**
 *
 * SpreadsheetImporter is a poor man's importer. It imports the schema of a spreadsheet. This is
 * very simplistic - the following are the assumptions:
 * <ul>
 *     <li>Multiple worksheets are imported as separate tables - no effort is made to link them through analysis of the formulas</li>
 *     <li>No blank lines above or to the left of the data</li>
 *     <li>The schema attribute names are in the first row</li>
 *     <li>No breaks in the data listing (i.e., no blank rows until after all the data is listed)</li>
 * </ul>
 *
 * @author Jeffrey Hoyt
 *
 */
public class SpreadsheetImporter extends SchemaImporter {
	protected HSSFWorkbook _excelWorkbook;
	private URI _excelURI;
	private HashMap<String, Entity> _entities;
	private HashMap<String, Attribute> _attributes;
	protected int[] cellTypes;
	protected String[] attributeNames;
	protected ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();
	private HashMap<String, Domain> domainList = new HashMap<String, Domain>();

	public SpreadsheetImporter() {
		loadDomains();
	}

	// get rid of characters
	protected String cleanup(String s) {
		s = s.trim().replaceAll("'", "''").replaceAll("\"", "\\\"");

		return s;
	}

	protected String getCellValStr(HSSFCell cell) {
		if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			return Boolean.toString(cell.getBooleanCellValue());
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			return Double.toString(cell.getNumericCellValue());
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			return cleanup(cell.getRichStringCellValue().toString());
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
			return "";
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
			return cell.getCellFormula();
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_ERROR) {
			return String.valueOf(cell.getErrorCellValue());
		} else if (cell.getCellType() == 1024) {
			return String.valueOf(cell.getDateCellValue());
		} else {
			return "";
		}
	}

	/**
	 * Returns the int of the cell type. The int returned will correspond to the value of the field
	 * types in HSSFCell.  Since the underlying platform doesn't return dates, we use 1024 to attempt to
	 * represent a date
	 */
	protected int getCellDataType(HSSFCell cell) {
		if (cell == null) return HSSFCell.CELL_TYPE_BLANK;
	    try
	    {
	        java.util.Date isItDate= cell.getDateCellValue();
            if( isItDate != null && !cell.getCellStyle().getDataFormatString().contains("#") &&
                !cell.getCellStyle().getDataFormatString().equalsIgnoreCase("General") )
            {
                return 1024;
            }
        }
        catch (RuntimeException e)
        {
            //it's a String...moving on
        }
		return cell.getCellType();
	}

	protected String documentation = "";

	/**
	 * Derive the schema from the contents of an Excel workbook
	 */
	protected void generate() {
		int numSheets = _excelWorkbook.getNumberOfSheets();

		// iterate and load individual work sheets
		for (int s = 0; s < numSheets; s++) {
			HSSFSheet sheet = _excelWorkbook.getSheetAt(s);
			HSSFRow topRow = sheet.getRow(0); //first logical row

			if (topRow == null || topRow.getCell(0) == null) {
				continue;
			}

			String sheetName = _excelWorkbook.getSheetName(s);
			Entity tblEntity = new Entity(nextId(), sheetName, "", 0);
			_entities.put(sheetName, tblEntity);

			//			String topLeftCell = getCellValStr(topRow.getCell(0));
			cellTypes = new int[topRow.getLastCellNum()];
			Arrays.fill(cellTypes, -1);

			//get attribute names
			HSSFRow row = sheet.getRow(0);
			attributeNames = new String[row.getPhysicalNumberOfCells()];
			for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
				attributeNames[j] = getCellValStr(row.getCell(j));
			}
			determineColumnTypes(sheet, cellTypes.length);
			Attribute attribute;
			for (int i = 0; i < cellTypes.length; i++) {
				if (attributeNames[i].length() > 0) {
					Domain domain = domainList.get(translateType(cellTypes[i]));
					attribute = new Attribute(nextId(), attributeNames[i], documentation,
							tblEntity.getId(), domain.getId(), null, null, false, 0);
					_attributes.put(attribute.getName(), attribute);
				}
			}
		}
	}

	/**
	 * sets the cellTypes array. The rule is: once it's a String, it stays a String
	 */
	protected void determineColumnTypes(HSSFSheet sheet, int colCount) {
		// iterate through rows and create table/attribute nodes
		// the first row has the attribute names in them
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null) continue;

			int currentCellType;
			for (int j = 0; j < colCount; j++) {
				if (cellTypes[j] == HSSFCell.CELL_TYPE_STRING) {
					continue;
				}
				//System.out.print(getCellValStr(row.getCell(j)) + " is a ");
				currentCellType = getCellDataType(row.getCell(j));
				//System.out.println( translateType(currentCellType ) + " and the date format is " + row.getCell(j).getCellStyle().getDataFormatString() );
				if (cellTypes[j] == -1
						|| (cellTypes[j] == HSSFCell.CELL_TYPE_BLANK && cellTypes[j] != currentCellType)) {
					cellTypes[j] = currentCellType;
				} else if (cellTypes[j] != currentCellType
						&& currentCellType != HSSFCell.CELL_TYPE_BLANK) {
					//they don't match to default to String
					cellTypes[j] = HSSFCell.CELL_TYPE_STRING;
				}
			}
		}
	}

	public String translateType(int hssfType) {
		switch (hssfType) {
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return BOOLEAN;
		case HSSFCell.CELL_TYPE_NUMERIC:
			return REAL;
		case HSSFCell.CELL_TYPE_BLANK:
		case HSSFCell.CELL_TYPE_STRING:
			return STRING;
		case HSSFCell.CELL_TYPE_ERROR:
			throw new RuntimeException(
					"There appears to be an error in the formulas in the spreadsheet");
		case HSSFCell.CELL_TYPE_FORMULA:
			throw new RuntimeException(
					"Formulas are not valid return types.  The spreadsheet was processed incorrectly");
        case 1024:
			return DATETIME;
		}
		return STRING;
	}

	/** Returns the importer name */
	public String getName() {
		return "Spreadsheet Importer";
	}

	/** Returns the importer description */
	public String getDescription() {
		return "Imports Excel formatted schema into the schema store.";
	}

	/** Returns the importer URI type */
	public URIType getURIType() {
		return URIType.FILE;
	}

	/** Returns the importer URI file types */
	public ArrayList<String> getFileTypes() {
		ArrayList<String> filetypes = new ArrayList<String>(3);
		filetypes.add("xls");
		filetypes.add("csv");

		// filetypes.add("xlsx");
		return filetypes;
	}

	protected void initialize() throws ImporterException {
		try {
			InputStream excelStream;
			_entities = new HashMap<String, Entity>();
			_attributes = new HashMap<String, Attribute>();

			// Do nothing if the excel sheet has been cached.
			if ((_excelURI != null) && _excelURI.equals(uri)) { return; }

			_excelURI = uri;
			excelStream = _excelURI.toURL().openStream();
			_excelWorkbook = new HSSFWorkbook(excelStream);
			excelStream.close();
		} catch (IOException e) {
			throw new ImporterException(ImporterExceptionType.PARSE_FAILURE, e.getMessage());
		}
	}

	protected ArrayList<SchemaElement> generateSchemaElementList() {

		for (Entity e : _entities.values())
			schemaElements.add(e);

		for (Attribute a : _attributes.values())
			schemaElements.add(a);

		return schemaElements;
	}

	/** Generate the schema elements */
	public ArrayList<SchemaElement> generateSchemaElements() throws ImporterException
	{
		generate();
		return generateSchemaElementList();
	}

	/**
	 * Function for loading the preset domains into the Schema and into a list for use during
	 * Attribute creation
	 */
	private void loadDomains() {
		Domain domain = new Domain(SchemaImporter.nextId(), INTEGER, "The Integer domain", 0);
		schemaElements.add(domain);
		domainList.put(INTEGER, domain);
		domain = new Domain(SchemaImporter.nextId(), REAL, "The Real domain", 0);
		schemaElements.add(domain);
		domainList.put(REAL, domain);
		domain = new Domain(SchemaImporter.nextId(), STRING, "The String domain", 0);
		schemaElements.add(domain);
		domainList.put(STRING, domain);
		domain = new Domain(SchemaImporter.nextId(), DATETIME, "The DateTime domain", 0);
		schemaElements.add(domain);
		domainList.put(DATETIME, domain);
		domain = new Domain(SchemaImporter.nextId(), BOOLEAN, "The Boolean domain", 0);
		schemaElements.add(domain);
		domainList.put(BOOLEAN, domain);
	}

	public static void main(String[] args) throws IOException, URISyntaxException, ImporterException {
		File excel = new File(args[0]);
		SpreadsheetImporter tester = new SpreadsheetImporter();
		Repository repository = new Repository(Repository.DERBY,new URI("."),"schemastore","postgres","postgres");
		SchemaStoreClient client = new SchemaStoreClient(repository);
		tester.setClient(client);
		tester.importSchema(excel.getName(), "", "", excel.toURI());
	}
}
