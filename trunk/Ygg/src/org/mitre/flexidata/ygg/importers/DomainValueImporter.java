package org.mitre.flexidata.ygg.importers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

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
			String domainName = _excelWorkbook.getSheetName(s);
			addDomain(domainName);

			// iterate through rows and create table/attribute nodes
			for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
				readRow(sheet.getRow(i));
			}
		}
	}

	private Domain addDomain(String domainName) {
		Domain domain;
		if (!_domains.containsKey(domainName)) {
			domain = new Domain(nextId(), domainName, "", 0);
			_domains.put(domainName, domain);
			// System.err.println("adding new domain " + domainName);
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
		return schemaElements;
	}

	protected void readRow(HSSFRow row) {
		if (row.getPhysicalNumberOfCells() == 0) return;
		
		String domainName = despace(row.getCell(0).getStringCellValue());
		String domainValueStr = despace(row.getCell(1).getStringCellValue());
		String documentation = (row.getLastCellNum() >= 2) ? despace(row.getCell(2).getStringCellValue()) : "";
		String hashKey = domainName + "/" + domainValueStr;

		Domain domain;
		DomainValue domainValue;

		if (domainName.length() == 0 && domainValueStr.length() == 0)
			return;

		// get the domain if it doesn't exist
		domain = addDomain(domainName);

		// create a domain value
		if (domainValueStr.length() > 0) {
			domainValue = new DomainValue(nextId(), domainValueStr, documentation, domain.getId(),
					0);
			_domainValues.put(hashKey, domainValue);
		}
	}
}
