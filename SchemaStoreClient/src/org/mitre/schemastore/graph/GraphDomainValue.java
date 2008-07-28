package org.mitre.schemastore.graph;

/**
 * Class GraphDomainValue
 * @author MDMORSE, DBURDICK
 */

import java.util.ArrayList;
import org.mitre.schemastore.model.*;

public class GraphDomainValue extends DomainValue implements GraphSchemaElement{
	
	GraphDomain parentDomain;
	
	/** Constructs the graph domain value */
	public GraphDomainValue(Integer id, String name, String description, Integer domainID, Integer base)
		{ super(id,name,description,domainID,base); }
		
	public GraphDomainValue(DomainValue dv)
	{ super(dv.getId(),dv.getName(),dv.getDescription(),dv.getDomainID(),dv.getBase()); }
	
	
	public GraphDomain getDomainValue(){
		return parentDomain;
	}
	
	public void setParentDomain(GraphDomain parent){
		parentDomain = parent;
	}
	
	public ArrayList<SchemaElement> getParents(){
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		retVal.add(parentDomain);
		
		return retVal;
	}
	
	public ArrayList<SchemaElement>getChildren() {
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		return retVal;
	}
}