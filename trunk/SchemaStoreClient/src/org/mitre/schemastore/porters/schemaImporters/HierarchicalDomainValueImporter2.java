package org.mitre.schemastore.porters.schemaImporters;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.porters.ImporterException;

public class HierarchicalDomainValueImporter2 extends DomainValueImporter {
protected HashMap<String, Subtype> _subtypes = new HashMap<String, Subtype>();
	

	protected ArrayList<SchemaElement> generateSchemaElements() throws ImporterException {
		int numSheets = _excelWorkbook.getNumberOfSheets();
		ArrayList<SchemaElement> _schemaElements = new ArrayList<SchemaElement>();
		

		// iterate and load individual work sheets
		for (int s = 0; s < numSheets; s++) {
			HSSFSheet sheet = _excelWorkbook.getSheetAt(s);
			if (sheet == null) break;
			
			ArrayList<SchemaElement> hierarchy = new ArrayList<SchemaElement> ();
			
			// iterate through rows and create table/attribute nodes
			for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
				HSSFRow row = sheet.getRow(i);
				if (row == null || row.getPhysicalNumberOfCells() == 0) break;

				String domainName = "";
				String outlineLevelStr = "";
				String domainValueStr = "";
				String documentation = "";
				
				// get domain name, assume cell contains string value
				HSSFCell domainCell = row.getCell(0);
				HSSFCell levelCell = row.getCell(1);
				HSSFCell valueCell = row.getCell(2);
				HSSFCell descrCell = row.getCell(3);
				
				// Ignore rows without domain specified
				if ( domainCell != null ) domainName = getCellValStr(domainCell);
				// Get parent value
				if (levelCell != null) outlineLevelStr = getCellValStr(levelCell);
				// Get domain value
				if (valueCell != null ) domainValueStr = getCellValStr(valueCell);
				// Get documentation value
				if (descrCell != null) documentation = getCellValStr(descrCell);
				
				if (domainName.length() == 0) break; 
				if ( outlineLevelStr.length() == 0 ) outlineLevelStr = "1"; 
				
				int level = Integer.parseInt(outlineLevelStr.substring(0, outlineLevelStr.indexOf(".")));
				if (level == 1 ) 
					hierarchy = new ArrayList<SchemaElement>();

				Domain domain;
				DomainValue domainValue;
				DomainValue parentValue;

				// Create domain
				domain = _domains.get(domainName);
				if (domain == null ){
					domain = new Domain(nextId(), domainName, "", 0);
					_domains.put(domainName, domain);
					_schemaElements.add(domain);
					
//					System.out.println( "Add domain " + domainName); 
				} 

				// Set documentation for domain only rows
				if (valueCell == null || domainValueStr.length() == 0) {
					domain.setDescription(documentation);
					continue;
				}
				
				// Create a domain value
				String valueHashKey = domainName + "/" + domainValueStr;
				domainValue = _domainValues.get(valueHashKey);
				if (domainValue == null) {
					domainValue = new DomainValue(nextId(), domainValueStr, documentation, domain.getId(), 0);
					_domainValues.put(valueHashKey, domainValue);
					_schemaElements.add(domainValue);
					
//					System.out.println( "Add DV " + domainValueStr ); 
				}
				
				if ( level > hierarchy.size() ) { 
					hierarchy.add((level-1), domainValue );
					System.out.println( "H: " + + hierarchy.size() + " " + domainValue.getName()); 
				}

				// Create a subtype relationship for the parent
				if (level > 1  ) {
					// First get parent DomainValue
					parentValue = (DomainValue) hierarchy.get(level-2); 

					// Then create subtype
					String subtypeHash = parentValue.getId() + "/" + valueHashKey;
					if (!_subtypes.containsKey(subtypeHash)) {
						Subtype subtype = new Subtype(nextId(), parentValue.getId(), domainValue.getId(), 0);
						_subtypes.put(subtypeHash, subtype);
						_schemaElements.add(subtype);

//						System.out.println("Add subtype " + parentValue.getName() + " _ " + valueHashKey);
					}
				} // End creating subtype
			} // End iterating through all rows
		}// End iterating all sheets

		return _schemaElements;
	}

	@Override
	public String getDescription() {
		return "Imports Excel formatted domain and domain values with hierarchy information as numbered levels";
	}

	@Override
	public String getName() {
		return "Hierarchical Domain Value Importer 2";
	}
	
}
