package org.mitre.flexidata.ygg.importers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.read.biff.BiffException;

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
	protected void initialize(URI uri) throws IOException, URISyntaxException, BiffException {
		super.initialize(uri);
		_domains = new HashMap<String, Domain>();
		_domainValues = new HashMap<String, DomainValue>();

	}

	protected void generate() {
		Sheet[] sheets = _excelWorkbook.getSheets();

		// iterate and load individual work sheets
		for (int s = 0; s < sheets.length; s++) {
			Sheet sheet = sheets[s];

			// add sheet name as a domain
			String domainName = sheet.getName();
			addDomain(domainName);

			int numRows = sheet.getRows();
			// iterate through rows and create table/attribute nodes
			for (int i = 0; i < numRows; i++) {
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

	protected void readRow(Cell[] cells) {
		if (cells.length < 1) return;
		
		String domainName = despace(cells[0].getContents());
		String domainValueStr = despace(cells[1].getContents());
		String documentation = (cells.length > 2) ? cells[2].getContents().trim() : "";
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
//			 System.err.println("adding new domain value " + domain.getName() + " : "
//					 + domainValueStr);
		}
	}
}
