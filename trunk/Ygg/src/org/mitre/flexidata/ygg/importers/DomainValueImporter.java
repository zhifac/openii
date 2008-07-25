package org.mitre.flexidata.ygg.importers;

import java.util.ArrayList;
import java.util.HashMap;

import jxl.Cell;

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
	HashMap<String, Domain> _domains = new HashMap<String, Domain>();
	HashMap<String, DomainValue> _domainValues = new HashMap<String, DomainValue>();

	@Override
	public String getDescription() {
		return "Imports Excel formatted domain and domain values.";
	}

	@Override
	public String getName() {
		return "Domain Value Importer (xls)";
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
		String domainName = despace(cells[0].getContents());
		String domainValueStr = despace(cells[1].getContents());
		String documentation = cells[2].getContents().trim();
		Domain domain;
		DomainValue domainValue;

		if (domainName.length() == 0 && domainValueStr.length() == 0)
			return;

		// create an entity for a table
		if (_domains.containsKey(domainName))
			domain = _domains.get(domainName);
		else {
			domain = new Domain(nextId(), domainName, "", 0);
			System.err.println ("adding new domain " + domainName);
			_domains.put(domainName, domain);
		}

		// create a domain value
		if (domainValueStr.length() > 0) {
			domainValue = new DomainValue(nextId(), domainValueStr, documentation, domain.getId(),
					0);
			System.err.println ("adding new domain value " + domain.getName() + " : " + domainValueStr);
			_domainValues.put(domainValueStr, domainValue);
		} else {
			// doc describes domain if domain value is absent
			domain.setDescription(documentation);
		}
	}

}
