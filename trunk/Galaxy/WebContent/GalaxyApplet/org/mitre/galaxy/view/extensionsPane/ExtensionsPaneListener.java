// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.extensionsPane;

/** Listener class for the extension pane */
public interface ExtensionsPaneListener
{
	/** Indicates that a schema has been selected */
	public void schemaSelected(Integer schemaID);

	/** Indicates that a comparison schema has been selected */
	public void comparisonSchemaSelected(Integer schemaID);
}
