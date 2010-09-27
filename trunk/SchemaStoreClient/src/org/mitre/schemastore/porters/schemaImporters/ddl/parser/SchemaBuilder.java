package org.mitre.schemastore.porters.schemaImporters.ddl.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;
import org.mitre.schemastore.porters.schemaImporters.ddl.parser.schemaObjects.Column;
import org.mitre.schemastore.porters.schemaImporters.ddl.parser.schemaObjects.ForeignKey;
import org.mitre.schemastore.porters.schemaImporters.ddl.parser.schemaObjects.PrimaryKey;
import org.mitre.schemastore.porters.schemaImporters.ddl.parser.schemaObjects.Table;

public class SchemaBuilder {
    Schema schema = new Schema(SchemaImporter.nextId(), "name", "", "", "", "desc", false);
    ArrayList<SchemaElement> schemaObjects = new ArrayList<SchemaElement>();

    // stores all of the column ids that we've generated
    private HashMap<String, Integer> tableIds = new HashMap<String, Integer>();

    // mapping of string domain to Object Domain.  New domains will be added.
    private HashMap<Integer, Domain> domainList = new HashMap<Integer, Domain>();

    /**
     *  Constructor - only loads in the Domain items.
     *  then process the table object until we've got some entities
     */
    public SchemaBuilder(Tables tableObj) {
        loadDomains();

        /*
         * NOTE:
         *  Entities = Tables
         *  Attributes = Columns
         *  Relationships = Foreign Keys
         *  
         * Relationships are between tables and NOT columns.
         */

        // process each table
        HashMap<String, Table> tables = tableObj.getTables();
        for (String tableName : tables.keySet()) {
        	// get details on this table
        	Table table = tableObj.getTables().get(tableName);
        	PrimaryKey primaryKey = tableObj.getPrimaryKey(tableName);

        	// create the table
        	int nextTableId = SchemaImporter.nextId();
        	Entity entity = new Entity(nextTableId, tableName, table.getDescription(), schema.getId());
        	schemaObjects.add(entity);

        	// make the new table in the columnIds list
        	tableIds.put(tableName, nextTableId);

            // get the table columns and primary keys and create necessary objects
        	for (String columnName : tableObj.getColumns(tableName).keySet()) {
        		Column column = tableObj.getColumns(tableName).get(columnName);

        		// see if this column is part of the key
        		boolean isPrimaryKey = false;
        		if (primaryKey != null && primaryKey.getColumns().contains(columnName)) { isPrimaryKey = true; }

        		// create the new column/attribute and add it to the list
        		Attribute attribute = new Attribute(SchemaImporter.nextId(), columnName, column.getDescription(), entity.getId(), domainList.get(column.getType()).getId(), 1, 1, isPrimaryKey, schema.getId());
        		schemaObjects.add(attribute);
        	}
        }

        // get all foreign keys and process the relationships between tables
        ArrayList<ForeignKey> foreignKeys = tableObj.getForeignKeys();
        for (int i = 0; i < foreignKeys.size(); i++) {
        	ForeignKey foreignKey = foreignKeys.get(i);
        	int sourceId = tableIds.get(foreignKey.getSourceTable());
        	int targetId = tableIds.get(foreignKey.getTargetTable());
        	Relationship relationship = new Relationship(SchemaImporter.nextId(), foreignKey.getName(), "", sourceId, 1, 1, targetId, 1, 1, schema.getId());
        	schemaObjects.add(relationship);
        }
    }

    /**
	 * Function for loading the preset domains into the Schema and into a
	 * list for use during Attribute creation
	 */
	private void loadDomains() {
		{
			Domain domain = new Domain(SchemaImporter.nextId(), SchemaImporter.INTEGER, "The Integer domain", 0);
			schemaObjects.add(domain);
			domainList.put(Column.COLUMN_TYPE_INTEGER, domain);
		}

		{
			Domain domain = new Domain(SchemaImporter.nextId(), SchemaImporter.REAL, "The Real domain", 0);
			schemaObjects.add(domain);
			domainList.put(Column.COLUMN_TYPE_REAL, domain);
		}

		{
			Domain domain = new Domain(SchemaImporter.nextId(), SchemaImporter.STRING, "The String domain", 0);
			schemaObjects.add(domain);
			domainList.put(Column.COLUMN_TYPE_STRING, domain);
		}

		{
			Domain domain = new Domain(SchemaImporter.nextId(), SchemaImporter.DATETIME, "The DateTime domain", 0);
			schemaObjects.add(domain);
			domainList.put(Column.COLUMN_TYPE_DATETIME, domain);
		}

		{
			Domain domain = new Domain(SchemaImporter.nextId(), SchemaImporter.BOOLEAN, "The Boolean domain", 0);
			schemaObjects.add(domain);
			domainList.put(Column.COLUMN_TYPE_BOOLEAN, domain);
		}
	}

    /**
     *  Retrieve an ArrayList of all the SchemaElements
     */
    public ArrayList<SchemaElement> getSchemaObjects() {
        return schemaObjects;
    }
}
