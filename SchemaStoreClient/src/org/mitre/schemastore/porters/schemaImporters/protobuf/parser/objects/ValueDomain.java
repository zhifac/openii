package org.mitre.schemastore.porters.schemaImporters.protobuf.parser.objects;

public class ValueDomain {
	private String value;
	private String description;
	
	public ValueDomain(String value, String description){
		this.value = value;
		this.description = description;
	}
	public ValueDomain(String value){
		this(value, null);
	}
	public void setDescription(String description){
		this.description = description;
	}
	public String getDescription(){
		return description;
	}
	public String getValue() {
		return value;
	}
}
