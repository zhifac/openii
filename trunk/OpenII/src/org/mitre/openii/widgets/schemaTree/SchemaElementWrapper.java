package org.mitre.openii.widgets.schemaTree;

import java.util.ArrayList;
import java.util.HashSet;

import org.mitre.schemastore.model.SchemaElement;

public class SchemaElementWrapper {
	private SchemaElement element;
	private SchemaElementWrapper parent = null;
	private Integer typeId = null;
	

	public SchemaElementWrapper(SchemaElement elem) {
		this(elem, null);
	}
	private SchemaElementWrapper(SchemaElement elem, SchemaElementWrapper parent) {
		this.element = elem;

		this.parent = parent;

	}
	public SchemaElementWrapper createChildWrapper(SchemaElement elem){
		return new SchemaElementWrapper(elem, this);
	}
	public SchemaElement getSchemaElement() {
		return element;
	}
	public HashSet<Integer> getPathIds() {
		HashSet<Integer> pathIds = new HashSet<Integer>();

		pathIds.add(getSchemaElement().getId());
		SchemaElementWrapper currentWrapper = this.getParent();
		while (currentWrapper != null) {
			pathIds.add(currentWrapper.getSchemaElement().getId());
			if (currentWrapper.getTypeId() != null){
				pathIds.add(currentWrapper.getTypeId());
			}
			currentWrapper = currentWrapper.getParent();
		}
		return pathIds;
	}
	public Integer getParentId(){
		if (parent != null) {
			return parent.getSchemaElement().getId();
		}
		return null;
	}
	public void insertTypeId(Integer id) {
		typeId = id;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public SchemaElementWrapper getParent() {
		return parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((element == null) ? 0 : element.hashCode());
		result = prime * result + ((getPathIds() == null) ? 0 : getPathIds().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SchemaElementWrapper other = (SchemaElementWrapper) obj;
		if (element == null) {
			if (other.element != null) {
				return false;
			}
		} else if (!element.equals(other.element)) {
			return false;
		}
		HashSet<Integer> pathIdsA = getPathIds();
		HashSet<Integer> pathIdsB = ((SchemaElementWrapper)obj).getPathIds();
		if (pathIdsA == null) {
			if (pathIdsB != null) {
				return false;
			}
		} else if (!pathIdsA.equals(pathIdsB)) {
			return false;
		}
		return true;
	}

}
