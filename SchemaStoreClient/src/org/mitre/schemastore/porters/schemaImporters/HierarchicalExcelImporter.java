package org.mitre.schemastore.porters.schemaImporters;

import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.porters.ImporterException;

/**
 * Imports a schema listed in an excel spreadsheet with the following column
 * sequence [entity][attribute][description][parent entity]
 * 
 * @author HAOLI
 * 
 */
public class HierarchicalExcelImporter extends ExcelImporter {
	/** Returns the importer name */
	public String getName() {
		return "Hierarchical Excel Importer";
	}
	
	/** Returns the importer description */
	public String getDescription() {
		return "Imports Excel formatted schema with hierarchy column. ";
	}

	/** Generate the schema elements */
	protected ArrayList<SchemaElement> generateSchemaElements() throws ImporterException {
		int numSheets = workbook.getNumberOfSheets();
		schemaElements = new ArrayList<SchemaElement>();
		schemaElements.add(D_ANY);

		// iterate and load individual work sheets
		for (int s = 0; s < numSheets; s++) {
			HSSFSheet sheet = workbook.getSheetAt(s);

			// iterate through rows and create table/attribute nodes
			for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
				HSSFRow row = sheet.getRow(i);

				String tblName = getCellValue(row.getCell(0));
				String attName = getCellValue(row.getCell(1));
				String documentation = getCellValue(row.getCell(2));
				String parent = getCellValue(row.getCell(3));

				Entity tblEntity, parentEntity;
				Attribute attribute;

				// Create an entity for table name
				if (tblName.length() == 0) { break; }
				tblEntity = entities.get(tblName);
				if (tblEntity == null) {
					tblEntity = new Entity(nextId(), tblName, "", 0);
					entities.put(tblName, tblEntity);
					schemaElements.add(tblEntity);
				}

				// Create an attribute for each attribute name
				if (attName.length() > 0) {
					attribute = attributes.get(attName);
					if (attribute == null) {
						attribute = new Attribute(nextId(), attName, documentation, tblEntity.getId(), D_ANY.getId(), null, null, false, 0);
						attributes.put(attName, attribute);
						schemaElements.add(attribute);
					}
				} else if (documentation.length() > 0) {
					tblEntity.setDescription(documentation);
				}

				// Create a subtype
				if (parent.length() > 0) {
					parentEntity = entities.get(parent);
					if (parentEntity == null) {
						parentEntity = new Entity(nextId(), parent, "", 0);
						entities.put(parent, parentEntity);
						schemaElements.add(parentEntity);
					}

					if (subtypes.get(parentEntity.getName() + "." + tblEntity.getName()) == null) {
						Subtype subtype = new Subtype(nextId(), parentEntity.getId(), tblEntity.getId(), 0);
						subtypes.put(parentEntity.getName() + "." + tblEntity.getName(), subtype);
						schemaElements.add(subtype);
					}
				}
			} // End for loop
		}

		return schemaElements;
	}
}