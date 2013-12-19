package org.mitre.schemastore.porters.schemaImporters.protobuf.parser.objects;

import org.mitre.schemastore.porters.schemaImporters.protobuf.parser.ProtobufParser;
import org.mitre.schemastore.porters.schemaImporters.protobuf.parser.ProtobufParser.KeywordEnum;
import de.susebox.jtopas.Token;

public class Element {

	private KeywordEnum frequency;
	private Object type;
	private String name, description;
	private Scope parent;
	
	
    Element(Token frequency, Token typeToken,
			Token name, Token comment) {
		this.name = name.getImage();
		this.description = comment.getImage().substring(2).trim();
		if (typeToken.getCompanion() instanceof KeywordEnum)
		{
			type = typeToken.getCompanion();
		}
		else
		{
			this.type = typeToken.getImage();
		}
		this.frequency = (KeywordEnum) frequency.getCompanion();
	}
	protected void processElement(Scope currentScope) throws ProtoParseException {
		if (type instanceof KeywordEnum || type instanceof BasicScope){
			return;
		}
		else if (type instanceof String)
		{

			BasicScope temp = currentScope.findType((String)type);
			if (temp == null){
				throw new ProtoParseException("Failed to find type " + type.toString());
			}
			type = temp;
		}
		else{
			throw new ProtoParseException("Invalid type passed to element " + name);
		}
	}
	public ProtobufParser.KeywordEnum getFrequency() {
		return frequency;
	}
	public void setFrequency(ProtobufParser.KeywordEnum frequency) {
		this.frequency = frequency;
	}
	public Object getType()
	{
		return type;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getMinOccurs() {
		return frequency==KeywordEnum.REQUIRED?1:0;
	}
	public int getMaxOccurs() {
		return frequency==KeywordEnum.REPEATED?-1:1;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = (name==null) ? 0 : name.hashCode();
		result = prime * result
				+ ((frequency == null) ? 0 : frequency.hashCode());

		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Element other = (Element) obj;
		if (frequency != other.frequency)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Element [frequency=" + frequency + ", type=" + type + ", name="
				+ name + ", description=" + description + ", parent=" + parent
				+ "]";
	}

	

}
