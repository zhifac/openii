package org.mitre.openii.model;

public class OpenIIAdapter implements OpenIIListener {
	@Override
	public void groupAdded(Integer groupID) {}

	@Override
	public void groupDeleted(Integer groupID) {}

	@Override
	public void groupModified(Integer groupID) {}

	@Override
	public void mappingAdded(Integer mappingID) {}

	@Override
	public void mappingDeleted(Integer mappingID) {}

	@Override
	public void mappingModified(Integer mappingID) {}

	@Override
	public void schemaAdded(Integer schemaID) {}

	@Override
	public void schemaDeleted(Integer schemaID) {}

	@Override
	public void schemaModified(Integer schemaID) {}	
}
