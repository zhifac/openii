package org.mitre.flexidata.ygg.importers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Imports domain values from an Excel sheet. The excel sheet format is [domain
 * name] [domain value] [documentation]
 * 
 * @author HAOLI
 * 
 */

public class DomainValueImporter extends ExcelImporter {
	private HashMap<String, Domain> _domains = new HashMap<String, Domain>();
	private HashMap<String, DomainValue> _domainValues = new HashMap<String, DomainValue>();

	@Override
	public String getDescription() {
		return "Imports Excel formatted domain and domain values. Domains are synonymous to "
				+ "referenced look up lists for controled vocabulary or controled data inputs.";
	}

	@Override
	public String getName() {
		return "Domain Importer (.xls)";
	}

	@Override
	protected void initialize(URI uri) throws IOException, URISyntaxException {
		super.initialize(uri);
		_domains = new HashMap<String, Domain>();
		_domainValues = new HashMap<String, DomainValue>();

	}

	protected void generate() {
		int numSheets = _excelWorkbook.getNumberOfSheets();

		// iterate and load individual work sheets
		for (int s = 0; s < numSheets; s++) {
			HSSFSheet sheet = _excelWorkbook.getSheetAt(s);

			// add sheet name as a domain
			// String domainName = _excelWorkbook.getSheetName(s);
			// addDomain(domainName);

			// iterate through rows and create table/attribute nodes
			for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
				readRow(sheet.getRow(i));
			}
		}
	}

	private Domain getDomain(String domainName) {
		Domain domain;
		if (!_domains.containsKey(domainName)) {
			domain = new Domain(nextId(), domainName, "", 0);
			System.out.println("     new domain " + domainName);
			_domains.put(domainName, domain);
		} else
			domain = _domains.get(domainName);
		return domain;
	}

	protected ArrayList<SchemaElement> generateSchemaElementList() {
		ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();

		for (Domain d : _domains.values())
			schemaElements.add(d);
		for (DomainValue v : _domainValues.values())
			schemaElements.add(v);

		System.out.println("Imported # domains " + _domains.size());
		System.out.println("Imported # domain values " + _domainValues.size());

		return schemaElements;
	}

	protected void readRow(HSSFRow row) {
		if (row == null || row.getPhysicalNumberOfCells() == 0)
			return;

		// if a row has only domain defined, the description is for the domain
		boolean domainDefOnly = false;

		// get domain name, assume cell contains string value
		HSSFCell domainCell = row.getCell(0);
		HSSFCell dvcell = row.getCell(1);
		HSSFCell descrCell = row.getCell(2);

		// ignore rows without domain specified
		if (domainCell == null) {
			return;
		}

		if (dvcell == null)
			domainDefOnly = true;

		String domainName = despace(domainCell.getRichStringCellValue().toString());
		String domainValueStr = "";

		// get domain values. this can be either string or number
		if (!domainDefOnly) {
			if (dvcell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				domainValueStr = Double.toString(dvcell.getNumericCellValue());
			else
				domainValueStr = despace(row.getCell(1).getRichStringCellValue().toString());
		}

		String documentation = "";
		if (descrCell != null)
			documentation = despace(descrCell.getRichStringCellValue().toString());

		String hashKey = domainName + "/" + domainValueStr;

		Domain domain = getDomain(domainName);
		DomainValue domainValue;

		if (domainDefOnly)
			domain.setDescription(documentation);
		else {
			// create a domain value
			domainValue = new DomainValue(nextId(), domainValueStr, documentation, domain.getId(),
					0);
			_domainValues.put(hashKey, domainValue);
		}
	}
}
