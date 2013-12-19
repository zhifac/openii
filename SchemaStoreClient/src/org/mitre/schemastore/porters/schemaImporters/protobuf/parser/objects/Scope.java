package org.mitre.schemastore.porters.schemaImporters.protobuf.parser.objects;

import java.util.HashMap;

public class Scope extends BasicScope{

	protected HashMap<String, BasicScope> children = null;




	protected Scope() {
		this(null, null, null);
		
	}
	protected Scope(Scope parent, String name, String description){
		super(parent, name, description);
		canNest = true;
	}
	protected BasicScope getChildWithName(String name) {
		if (children == null) {
			return null;
		}
		return children.get(name);
	}
	protected BasicScope addChildWithName(String name, String description) {
		if (children == null) {
			children = new HashMap<String, BasicScope>();
		}
		BasicScope newChild = getChildWithName(name);
		if (newChild == null) {
			newChild = new Scope(this, name, description);
			children.put(name,  newChild);
		}
		return newChild;
	}
	private void addChildScope(BasicScope scope) {
		if (children == null)
		{
			children = new HashMap<String, BasicScope>();
		}
		children.put(scope.name, scope);
	}
	public MessageScope addMessage(String name, String description){
		MessageScope scope = new MessageScope(this, name, description);
		root.addMessage(scope);
		addChildScope(scope);
		return scope;
	}
	public PBEnumeration addEnumeration(String name, String description) {
		PBEnumeration enumeration = new PBEnumeration(this, name, description);
		addChildScope(enumeration);
		return enumeration;
	}
	protected boolean isLeaf()
	{
		return children==null || children.isEmpty();
	}
	BasicScope findType(String type){
		if (type.startsWith(".")){
			Scope curParent = this;
			while (curParent.parent != null) {
				curParent = curParent.parent;
			}
			return searchDown(curParent, type.substring(1));
			
		}
		else if (type.contains(".")){
			String firstPart = type.split("[.]")[0];
			Scope currentScope = searchUp(this, firstPart);
			if (currentScope == null){
				return null;
			}
			
			return searchDown(currentScope, type);
		}
		else {
			Scope currentScope = searchUp(this, type);
			if (currentScope == null){
				return null;
			}
			return currentScope.children.get(type);
		}
	}
	private Scope searchUp(Scope scope, String name){
		while (!scope.children.containsKey(name)){
			scope = scope.getParent();
			if (scope == null)
			{
				return null;
			}
		}
		return scope;
	}
	private BasicScope searchDown(Scope scope, String typeString){
		BasicScope current = scope;
		String[] parts = typeString.split("[.]");
		for (String part : parts){
			if (current instanceof Scope)
			{
				current = ((Scope)current).getChildWithName(part);
			}
			else{
				return null;
			}
		   
		}
		return current;
	}


	

}
