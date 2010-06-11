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

	/** Generate the schema elements */
	protected ArrayList<SchemaElement> generateSchemaElements() throws ImporterException {
		int numSheets = excelWorkbook.getNumberOfSheets();
		_schemaElements = new ArrayList<SchemaElement>();
		_schemaElements.add(D_ANY);

		// iterate and load individual work sheets
		for (int s = 0; s < numSheets; s++) {
			HSSFSheet sheet = excelWorkbook.getSheetAt(s);

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
				if (tblName.length() == 0) break;
				tblEntity = _entities.get(tblName);
				if (tblEntity == null) {
					tblEntity = new Entity(nextId(), tblName, "", 0);
					_entities.put(tblName, tblEntity);
					_schemaElements.add(tblEntity);
				}

				// Create an attribute for each attribute name
				if (attName.length() > 0) {
					attribute = _attributes.get(attName);
					if (attribute == null) {
						attribute = new Attribute(nextId(), attName, documentation, tblEntity.getId(), D_ANY.getId(), null, null, false, 0);
						_attributes.put(attName, attribute);
						_schemaElements.add(attribute);
					}
				} else if (documentation.length() > 0) tblEntity.setDescription(documentation);

				// Create a subtype
				if (parent.length() > 0) {
					parentEntity = _entities.get(parent);
					if (parentEntity == null) {
						parentEntity = new Entity(nextId(), parent, "", 0);
						_entities.put(parent, parentEntity);
						_schemaElements.add(parentEntity);
					}

					if (_subtypes.get(parentEntity.getName() + "." + tblEntity.getName()) == null) {
						Subtype subtype = new Subtype(nextId(), parentEntity.getId(), tblEntity.getId(), 0);
						_subtypes.put(parentEntity.getName() + "." + tblEntity.getName(), subtype);
						_schemaElements.add(subtype);
					}
				}
			} // End for loop
		}

		return _schemaElements;
	}

	@Override
	public String getDescription() {
		return "Imports Excel formatted schema with hierarchy column. ";
	}

	@Override
	public String getName() {
		return "Hierarchical Excel Importer";
	}

}
