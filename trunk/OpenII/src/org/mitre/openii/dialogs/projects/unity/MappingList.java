package org.mitre.openii.dialogs.projects.unity;

import java.util.ArrayList;

import org.mitre.schemastore.model.ProjectSchema;

public class MappingList {
	private ArrayList<Pair<ProjectSchema>> mappingList = new ArrayList<Pair<ProjectSchema>>();
	
	private int index  = 0; 
	
	public MappingList(){
	}
	
	public MappingList(ArrayList<Pair<ProjectSchema>> mappingList) {
		this.mappingList = mappingList; 
	}
	
	public void add(Pair<ProjectSchema> mapping) {
		mappingList.add(mapping); 
	}
	
	public void remove(Pair<ProjectSchema> mapping) {
		mappingList.remove(mapping); 
	}
	
	public int size() {
		return mappingList.size();
	}
	
	public Pair<ProjectSchema> next() {
		if ( hasMore() ){
			return mappingList.get(index++); 
		} else 
			return null; 
	}

	public void reset(){ index = 0; }
	
	public boolean hasMore() {
		return index <= size()-1; 
	}
	
	public boolean contains(Pair<ProjectSchema> m) {
		return mappingList.contains(m); 
	}
	
	public ArrayList<Pair<ProjectSchema>> getList(){
		return mappingList; 
	}
	
	public void addAll(ArrayList<Pair<ProjectSchema>> other) {
		for (Pair<ProjectSchema> pair : other )
			mappingList.add(pair); 
	}
	
	public void addAll(MappingList other) {
		mappingList.addAll(other.getList()); 
	}
	
}
