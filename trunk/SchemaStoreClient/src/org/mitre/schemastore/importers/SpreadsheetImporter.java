package org.mitre.schemastore.importers;

import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * SpreadsheetImporter is a poor man's importer. It imports the schema of a spreadsheet.  This is very simplistic -
 * the following are the assumptions:
 * <ul>
 *     <li>Multiple worksheets are imported as separate tables - no effort is made to link them through analysis of the formulas</li>
 *     <li>No blank lines above or to the left of the data</li>
 *     <li>The schema attribute names are in the first row</li>
 *     <li></li>
 *     <li></li>
 * </ul>
 *
 * @author Jeffrey Hoyt
 *
 */
public class SpreadsheetImporter extends Importer {
    protected HSSFWorkbook _excelWorkbook;
    private URI _excelURI;
    private HashMap<String, Entity> _entities;
    private HashMap<String, Attribute> _attributes;
    protected int[] cellTypes;
    protected String[] attributeNames;
    protected ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();
    private HashMap<String, Domain> domainList = new HashMap<String, Domain>();

    public SpreadsheetImporter()
    {
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
        } else {
            return "";
        }
    }

    /**
     *  Returns the int of the cell type.  The int returned will correspond to the value of the field types in HSSFCell
     */
    protected int getCellDataType(HSSFCell cell) {
        if( cell == null )
        {
            return HSSFCell.CELL_TYPE_BLANK;
        }
        if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
            return cell.getCachedFormulaResultType();
        } else {
            return cell.getCellType();
        }
    }


    protected String documentation = "";

    /**
     *   Derive the schema from the contents of an Excel workbook
     */
    protected void generate() {
        int numSheets = _excelWorkbook.getNumberOfSheets();

        // iterate and load individual work sheets
        for (int s = 0; s < numSheets; s++) {
            HSSFSheet sheet = _excelWorkbook.getSheetAt(s);
            HSSFRow topRow = sheet.getRow(0); //first logical row

            if (topRow == null|| topRow.getCell(0)==null) {
                System.out.println("Sheet " + s + " is blank.");
                continue;
            }

            String sheetName = _excelWorkbook.getSheetName(s);
            //System.out.println( "***** " + sheetName + " *****" );
            Entity tblEntity = new Entity(nextId(), sheetName, "", 0);
             _entities.put(sheetName, tblEntity);

            String topLeftCell = getCellValStr(topRow.getCell(0));
            cellTypes = new int[topRow.getLastCellNum()];
            Arrays.fill(cellTypes, -1);


            //get attribute names
            HSSFRow row = sheet.getRow(0);
            attributeNames = new String[row.getPhysicalNumberOfCells()];
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                attributeNames[j] = getCellValStr(row.getCell(j));
            }
            determineColumnTypes( sheet, cellTypes.length );
            Attribute attribute;
            for(int i = 0; i < cellTypes.length; i++)
            {
                //System.out.println( attributeNames[i] + ": " + cellTypes[i] );
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
     * sets the cellTypes array.  The rule is:  once it's a String, it stays a String
     */
    protected void determineColumnTypes( HSSFSheet sheet, int colCount )
    {
        // iterate through rows and create table/attribute nodes
        // the first row has the attribute names in them
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            if( row==null ) continue;

            int currentCellType;
            for (int j = 0; j < colCount; j++) {
                //System.out.println( "( " + i + ", " + j + " )" );
                if( cellTypes[j]==HSSFCell.CELL_TYPE_STRING )
                {
                    continue;
                }
                currentCellType = getCellDataType(row.getCell(j));
                if ( cellTypes[j] == -1 ||
                     ( cellTypes[j] ==  HSSFCell.CELL_TYPE_BLANK && cellTypes[j] != currentCellType ) )
                {
                    cellTypes[j] = currentCellType;
                }
                else if( cellTypes[j] != currentCellType && currentCellType != HSSFCell.CELL_TYPE_BLANK )
                {
                    //they don't match to default to String
                    cellTypes[j] = HSSFCell.CELL_TYPE_STRING ;
                }
            }
        }
    }

    /* protected void readRow(HSSFRow row) {
        String tblName = getCellValStr(row.getCell(0));
        String attName = getCellValStr(row.getCell(0));
        String documentation = getCellValStr(row.getCell(0));
        Entity tblEntity;
        Attribute attribute;

        if ((tblName.length() == 0) && (attName.length() == 0)) {
            return;
        }

        // create an entity for a table
        if (!_entities.containsKey(tblName)) {
            tblEntity = new Entity(nextId(), tblName, "", 0);
            _entities.put(tblName, tblEntity);
        } else {
            tblEntity = _entities.get(tblName);
        }

        // create an attribute
        if (attName.length() > 0) {
            attribute = new Attribute(nextId(), attName, documentation,
                    tblEntity.getId(), D_ANY.getId(), null, null, false, 0);
            _attributes.put(attribute.getName(), attribute);
        }
    } */


    public String translateType(int hssfType)
    {
        switch( hssfType )
        {
            case HSSFCell.CELL_TYPE_BOOLEAN: return BOOLEAN;
            case HSSFCell.CELL_TYPE_NUMERIC: return DOUBLE;
            case HSSFCell.CELL_TYPE_BLANK:
            case HSSFCell.CELL_TYPE_STRING: return STRING;
            case HSSFCell.CELL_TYPE_ERROR: throw new RuntimeException( "There appears to be an error in the formulas in the spreadsheet");
            case HSSFCell.CELL_TYPE_FORMULA: throw new RuntimeException("Formulas are not valid return types.  The spreadsheet was processed incorrectly");
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
    public Integer getURIType() {
        return FILE;
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
            if ((_excelURI != null) && _excelURI.equals(uri)) {
                return;
            }

            _excelURI = uri;
            excelStream = _excelURI.toURL().openStream();
            _excelWorkbook = new HSSFWorkbook(excelStream);
            excelStream.close();
        } catch (IOException e) {
            throw new ImporterException(ImporterException.PARSE_FAILURE,
                e.getMessage());
        }
    }

    public static void main(String[] args)
        throws IOException, URISyntaxException, ImporterException {
        File excel = new File(args[0]);
        SpreadsheetImporter tester = new SpreadsheetImporter();
        tester.setClient( new org.mitre.schemastore.client.SchemaStoreClient("../SchemaStore/SchemaStore.jar" ) );
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
    protected ArrayList<Integer> getExtendedSchemaIDs()
        throws ImporterException {
        return new ArrayList<Integer>();
    }

    /** Generate the schema elements */
    public ArrayList<SchemaElement> getSchemaElements()
        throws ImporterException {
        generate();

        return generateSchemaElementList();
    }


    /**
	 * Function for loading the preset domains into the Schema and into a
	 * list for use during Attribute creation
	 */
	private void loadDomains() {
		Domain domain = new Domain(Importer.nextId(), INTEGER, "The Integer domain", 0);
		schemaElements.add(domain);
		domainList.put(INTEGER, domain);
		domain = new Domain(Importer.nextId(), DOUBLE, "The Double domain", 0);
		schemaElements.add(domain);
		domainList.put(DOUBLE, domain);
		domain = new Domain(Importer.nextId(), STRING, "The String domain", 0);
		schemaElements.add(domain);
		domainList.put(STRING, domain);
		domain = new Domain(Importer.nextId(), DATETIME, "The DateTime domain", 0);
		schemaElements.add(domain);
		domainList.put(DATETIME, domain);
		domain = new Domain(Importer.nextId(), BOOLEAN, "The Boolean domain", 0);
		schemaElements.add(domain);
		domainList.put(BOOLEAN, domain);
	}
}

