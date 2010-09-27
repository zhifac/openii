package org.mitre.schemastore.porters.schemaImporters.ddl.parser.schemaObjects;

public class Column extends Element {
	// there are five data types that everything must be mapped to
	public static final int COLUMN_TYPE_INTEGER = 1;
	public static final int COLUMN_TYPE_REAL = 2;
	public static final int COLUMN_TYPE_STRING = 3;
	public static final int COLUMN_TYPE_DATETIME = 4;
	public static final int COLUMN_TYPE_BOOLEAN = 5;

	private String tableName = null;
	private String description = null;
	private int type = 0;

	public Column(String name, String tableName) throws Exception {
		setTableName(tableName);
		setName(name);

		System.out.println("Generating column named '" + getName() + "' of type '" + getColumnTypeName(getType()) + "' on table '" + getTableName() + "'.");
	}

	public Column(String name, String tableName, int type) throws Exception {
		setTableName(tableName);
		setName(name);
		setType(type);

		System.out.println("Generating column named '" + getName() + "' of type '" + getColumnTypeName(getType()) + "' on table '" + getTableName() + "'.");
	}

	public Column(String name, String tableName, String type) throws Exception {
		setTableName(tableName);
		setName(name);
		setType(getColumnTypeConversion(type));

		System.out.println("Generating column named '" + getName() + "' of type '" + getColumnTypeName(getType()) + "' on table '" + getTableName() + "'.");
	}

	// setters
	public void setTableName(String tableName) throws Exception {
		if (tableName == null) { throw new Exception("Could not create primary key. No table name given."); }
		this.tableName = tableName.toUpperCase();
	}

	public void setType(String type) throws Exception {
		this.type = getColumnTypeConversion(type);
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setDescription(String description) {
		if (description != null) { description = description.trim(); }
		if (description != null && description.length() == 0) { description = null; }
		this.description = description;
	}

	// getters
	public int getType() {
		return this.type;
	}

	public String getTableName() {
		return this.tableName;
	}

	public String getDescription() {
		return this.description;
	}

	public static String getColumnTypeName(int value) throws Exception {
		switch (value) {
			case COLUMN_TYPE_INTEGER:
				return "INTEGER";
			case COLUMN_TYPE_REAL:
				return "REAL";
			case COLUMN_TYPE_STRING:
				return "STRING";
			case COLUMN_TYPE_DATETIME:
				return "DATETIME";
			case COLUMN_TYPE_BOOLEAN:
				return "BOOLEAN";
			default:
				throw new Exception("Could not determine a name for the column type ID '" + value + "'.");
		}
	}

	public static boolean isValidColumnType(String value) {
		try {
			getColumnTypeConversion(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public static int getColumnTypeConversion(String value) throws Exception {
		if (value != null) {
			value = value.toUpperCase();
		} else {
			throw new Exception("Could not get column type. No column type given.");
		}

		// convert INTEGER
		if (value.equals("BIGINT") ||
			value.equals("DEC") ||
			value.equals("DECIMAL") ||
			value.equals("INT") ||
			value.equals("INTEGER") ||
			value.equals("NUM") ||
			value.equals("NUMERIC") ||
			value.equals("SMALLINT") ||
			value.equals("NUMBER") ||
			value.equals("ROWID") ||
			value.equals("UROWID") ||
			value.equals("ROWVERSION") ||
			value.equals("TINYINT") ||
			value.equals("BIGSERIAL") ||
			value.equals("SERIAL") ||
			value.equals("SERIAL4") ||
			value.equals("SERIAL8") ||
			value.equals("INT8") ||
			value.equals("OID")) {
			return Column.COLUMN_TYPE_INTEGER;
		}

		// convert REAL
		if (value.equals("DOUBLE") ||
			value.equals("FLOAT") ||
			value.equals("REAL") ||
			value.equals("BINARY_FLOAT") ||
			value.equals("BINARY_DOUBLE") ||
			value.equals("MONEY") ||
			value.equals("SMALLMONEY") ||
			value.equals("FLOAT4") ||
			value.equals("FLOAT8")) {
			return Column.COLUMN_TYPE_REAL;
		}

		// convert STRING
		if (value.equals("BLOB") ||
			value.equals("CLOB") ||
			value.equals("DBCLOB") ||
			value.equals("IMAGE") ||
			value.equals("BINARY") ||
			value.equals("CHAR") ||
			value.equals("CHARACTER") ||
			value.equals("VARCHAR") ||
			value.equals("VARCHAR2") ||
			value.equals("VARGRAPHIC") ||
			value.equals("VARBINARY") ||
			value.equals("ENUM") ||
			value.equals("LONG") ||
			value.equals("LONGBLOB") ||
			value.equals("LONGTEXT") ||
			value.equals("MEDIUMBLOB") ||
			value.equals("MEDIUMTEXT") ||
			value.equals("NCHAR") ||
			value.equals("NVARCHAR") ||
			value.equals("NVARCHAR2") ||
			value.equals("NATIONAL") ||
			value.equals("NCLOB") ||
			value.equals("TEXT") ||
			value.equals("SET") ||
			value.equals("BFILE") ||
			value.equals("RAW") ||
			value.equals("XMLTYPE") ||
			value.equals("XML") ||
			value.equals("CURSOR") ||
			value.equals("UNIQUEID") ||
			value.equals("BOX") ||
			value.equals("BYTEA") ||
			value.equals("CIDR") ||
			value.equals("CIRCLE") ||
			value.equals("INET") ||
			value.equals("LINE") ||
			value.equals("LSEG") ||
			value.equals("MACADDR") ||
			value.equals("PATH") ||
			value.equals("POINT") ||
			value.equals("POLYGON") ||
			value.equals("GEOMETRY")) {
			return Column.COLUMN_TYPE_STRING;
		}

		// convert DATETIME
		if (value.equals("TIME") ||
			value.equals("TIMESTAMP") ||
			value.equals("DATE") ||
			value.equals("DATETIME") ||
			value.equals("INTERVAL") ||
			value.equals("TEMPORAL") ||
			value.equals("SMALLDATETIME") ||
			value.equals("TIMESPAN") ||
			value.equals("TIMETZ")) {
			return Column.COLUMN_TYPE_DATETIME;
		}

		// convert BOOLEAN
		if (value.equals("BIT") ||
			value.equals("BOOL") ||
			value.equals("BOOLEAN")) {
			return Column.COLUMN_TYPE_BOOLEAN;
		}

		throw new Exception("Could not match data type to '" + value + "'.");
	}
}
