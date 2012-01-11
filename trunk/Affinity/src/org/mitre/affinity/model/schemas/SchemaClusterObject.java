package org.mitre.affinity.model.schemas;

import org.mitre.affinity.model.ClusterObjectProperties;
import org.mitre.schemastore.model.Schema;

/**
 * @author CBONACETO
 *
 */
public class SchemaClusterObject extends ClusterObjectProperties<Integer, Schema> {
	
	public SchemaClusterObject(Schema clusterObject) {
		this.clusterObject = clusterObject;
	}	

	public void setClusterObject(Schema clusterObject) {
		this.clusterObject = clusterObject;
	}

	@Override
	public String getName() {
		if(clusterObject != null) {
			return clusterObject.getName();
		}
		return null;
	}

	@Override
	public Integer getObjectId() {
		if(clusterObject != null) {
			return clusterObject.getId();
		}
		return null;
	}	
}