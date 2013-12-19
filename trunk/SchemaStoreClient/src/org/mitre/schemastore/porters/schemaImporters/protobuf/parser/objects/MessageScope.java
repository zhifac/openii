package org.mitre.schemastore.porters.schemaImporters.protobuf.parser.objects;

import java.util.ArrayList;

import org.mitre.schemastore.porters.schemaImporters.protobuf.parser.ProtobufParser.KeywordEnum;

import de.susebox.jtopas.Token;

public class MessageScope extends Scope{

	private ArrayList<Element> elements;
	private boolean elementsProcessed = false;
	public MessageScope(Scope currentScope, String name, String description ) {
		super(currentScope, name, description);
	}
	public void addElement(Token frequency, Token type, Token name, Token comment )
	{
		
		if (elements == null){
			elements = new ArrayList<Element>();
		}
		elements.add(new Element(frequency, type, name, comment));
		elementsProcessed = false;
	}
	
	public void processElements() throws ProtoParseException
	{
		if (elementsProcessed)
			return;
		for (Element elem : elements){
			elem.processElement(this);
		}
		elementsProcessed = true;
	}


}
