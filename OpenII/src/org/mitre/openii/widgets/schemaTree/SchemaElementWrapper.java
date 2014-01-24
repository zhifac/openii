package org.mitre.openii.widgets.schemaTree;

import java.util.ArrayList;
import java.util.HashSet;

import org.mitre.schemastore.model.SchemaElement;

public class SchemaElementWrapper {
	private SchemaElement element;
	private ArrayList<Integer> pathList;
	private Integer parentId = null;
	
	public SchemaElementWrapper(SchemaElement elem) {
		this(elem, new ArrayList<Integer>());

	}
	private SchemaElementWrapper(SchemaElement elem, ArrayList<Integer> pathList) {
		this.element = elem;
		if (pathList.size()>0) {
			parentId = pathList.get(pathList.size()-1);
		}
		this.pathList = pathList;
		pathList.add(elem.getId());
	}
	public SchemaElementWrapper createChildWrapper(SchemaElement elem){
		ArrayList<Integer> paths = (ArrayList<Integer>) pathList.clone();
		return new SchemaElementWrapper(elem, paths);
	}
	public SchemaElement getSchemaElement() {
		return element;
	}
	public HashSet<Integer> getPathIds() {
		HashSet<Integer> pathIds = new HashSet<Integer>();
		pathIds.addAll(pathList);
		return pathIds;
	}
	public Integer getParentId(){
		return parentId;
	}
	public void insertTypeId(Integer id) {
		pathList.add(0, id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((element == null) ? 0 : element.hashCode());
		result = prime * result + ((pathList == null) ? 0 : pathList.hashCode());
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
		if (pathList == null) {
			if (other.pathList != null) {
				return false;
			}
		} else if (!pathList.equals(other.pathList)) {
			return false;
		}
		return true;
	}

}
