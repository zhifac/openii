package org.mitre.schemastore.exporters.sql;

public class ReferenceValue {
	private String _value;
	private ReferenceTable _domain;

	public ReferenceValue(ReferenceTable referenceTable, String value) {
		_domain = referenceTable;
		_value = value;
	}
	
	public String getValue() {
		return _value;
	}

	public ReferenceTable getDomain() {
		return _domain;
	}
}
