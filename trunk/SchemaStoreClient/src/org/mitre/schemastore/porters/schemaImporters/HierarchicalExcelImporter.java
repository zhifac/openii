package org.mitre.schemastore.porters.schemaImporters;

import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.porters.ImporterException;

public class HierarchicalExcelImporter extends ExcelImporter {

	/** Generate the schema elements */
	protected ArrayList<SchemaElement> generateSchemaElements() throws ImporterException {
		int numSheets = _excelWorkbook.getNumberOfSheets();
		_schemaElements = new ArrayList<SchemaElement>();
		_schemaElements.add(D_ANY);

		// iterate and load individual work sheets
		for (int s = 0; s < numSheets; s++) {
			HSSFSheet sheet = _excelWorkbook.getSheetAt(s);

			// iterate through rows and create table/attribute nodes
			for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
				HSSFRow row = sheet.getRow(i);

				String tblName = getCellValStr(row.getCell(0));
				String attName = getCellValStr(row.getCell(1));
				String documentation = getCellValStr(row.getCell(2));
				String parent = getCellValStr(row.getCell(3));

				Entity tblEntity, parentEntity;
				Attribute attribute;

				if (tblName.length() == 0 && attName.length() == 0) break;
				tblEntity = _entities.get(tblName);

				// create an entity for table name
				if (tblEntity == null) {
					tblEntity = new Entity(nextId(), tblName, "", 0);
					_entities.put(tblName, tblEntity);
					_schemaElements.add(tblEntity);
				}

				// create an attribute for each attribute name
				if (attName.length() > 0) {
					attribute = new Attribute(nextId(), attName, documentation, tblEntity.getId(), D_ANY.getId(), null, null, false, 0);
					_attributes.put(attribute.getName(), attribute);
					_schemaElements.add(attribute);
				}

				if (parent.length() > 0 ) {
					parentEntity = _entities.get(parent); 
					if ( parentEntity == null ) {
						parentEntity = new Entity(nextId(), parent, "", 0 ); 
						_entities.put(tblName, tblEntity);
						_schemaElements.add(parentEntity); 
					}
					
					Subtype subtype = new Subtype(nextId(), parentEntity.getId(), tblEntity.getId(), 0); 
					_schemaElements.add(subtype); 
				}
			}
		}

		return _schemaElements;
	}

	@Override
	public String getDescription() {
		return "Imports Excel formatted schema. ";
	}

	@Override
	public String getName() {
		return "Hierarchical Excel Importer";
	}

}
