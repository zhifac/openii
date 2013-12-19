package org.mitre.schemastore.porters.schemaImporters.protobuf.parser.objects;

import java.util.HashMap;

public class BasicScope {
	protected String namespace;
	protected String name;
	protected Scope parent;
	protected String description;
	protected boolean canNest = false;
	protected RootScope root;

	
	protected BasicScope() {
		this(null, null, null);
	}
	protected BasicScope(Scope parent, String name, String description)
	{
		this.parent = parent;
		if (parent != null) {

			root = parent.root;
			this.namespace = ((parent.namespace==null)?"":parent.namespace + ".") + parent.name==null?"":parent.name;
		}

		this.name = name;
		this.description = description;
	}
	protected boolean isLeaf()
	{
		return true;
	}
	public String getNamespace() {
		return namespace;
	}
	public String getName() {
		return name;
	}
	public Scope getParent() {
		return parent;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String setDescription) {
		this.description = description;
	}
	public boolean canNest() {
		return canNest;
	}
	public RootScope getRoot() {
		return root;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((namespace == null) ? 0 : namespace.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicScope other = (BasicScope) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (namespace == null) {
			if (other.namespace != null)
				return false;
		} else if (!namespace.equals(other.namespace))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}
	protected boolean hasUndefined()
	{
		return false;
	}
	public String toString()
	{
		return "BasicScope: [ namespace = " + namespace + " name = " + name + " description = " + description +  " parent = " + parent.toString()+ " ] ";
	}
	BasicScope findType(String typeName)
	{
		return null;
	}
	

}
