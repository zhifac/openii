package org.mitre.schemastore.porters.schemaImporters.protobuf.parser.objects;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class RootScope extends Scope {
	
	private List<MessageScope> messages = new ArrayList<MessageScope>();
    public static RootScope getRootScope(){
    	RootScope scope = new RootScope();
    	scope.root = scope;
    	return scope;
    }
	public Scope getNamespaceWithPackageName(String packageName) throws ProtoParseException{
		if (children == null){
			children = new HashMap<String, BasicScope>();
		}
		List<String> packageParts = new ArrayList<String>();
		if (packageName== null|| packageName.isEmpty())
		{
			packageParts.add("");
		}
		else
		{
			packageParts = Arrays.asList(packageName.split("[.]"));
		}
		Scope currentNamespace = this;
		for (String current: packageParts){
			BasicScope temp = ((Scope)currentNamespace).addChildWithName(current, null);
			if (! (temp instanceof Scope)){
				throw new ProtoParseException();
			}
			else {
				currentNamespace = (Scope) temp;
			}
		}
		return currentNamespace;
		
	}
	public void addMessage(MessageScope message){
		messages.add(message);
	}
	public void processMessages() throws ProtoParseException {
		for (MessageScope scope : messages){
			scope.processElements();
		}
	}
	
	

}
