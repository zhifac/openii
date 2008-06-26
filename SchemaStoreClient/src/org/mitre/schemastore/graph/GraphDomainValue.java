package org.mitre.schemastore.graph;

/**
 * Class GraphDomainValue
 * @author MDMORSE
 */

import java.util.ArrayList;
import org.mitre.schemastore.model.*;

public class GraphDomainValue extends DomainValue{
	
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
		
}