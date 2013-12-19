package org.mitre.schemastore.porters.schemaImporters.protobuf.parser.objects;

import java.util.ArrayList;

import org.mitre.schemastore.porters.schemaImporters.protobuf.parser.ProtobufParser.KeywordEnum;

public class PBEnumeration extends BasicScope{
	ArrayList<ValueDomain> values = new ArrayList<ValueDomain>();
	


	public PBEnumeration(Scope parent,
			String name, String description) {
		super(parent, name, description);
		canNest = false;
		
	}
	public ValueDomain addValue(String valueStr, String description){
		ValueDomain valDom = new ValueDomain(valueStr, description);
		values.add(valDom);
		return valDom;
	}
	public ValueDomain addValue(String valueStr) {
		return addValue(valueStr, null);
	}
	public ArrayList<ValueDomain> getValues()
	{
		return values;
	}

	

}
