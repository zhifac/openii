package org.mitre.schemastore.exporters.sql;

import java.util.ArrayList;

public class ReferenceTable extends Table {

	private ArrayList<ReferenceValue> _values = new ArrayList<ReferenceValue>();

	public ReferenceTable(Rdb rdb, String name) {
		super(rdb, name);
	}

	public void addValue(String value) {
		ReferenceValue rv = new ReferenceValue(this, value);
		if (!_values.contains(rv))
			_values.add(rv);
	}

	public ArrayList<ReferenceValue> getReferenceValues() {
		return _values;
	}

}
