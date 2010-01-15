// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.schemastore.porters.mappingExporters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.porters.Porter;

/** Export Class - An exporter enables the exporting of a mapping */
public abstract class MappingExporter extends Porter
{
	/** Return the file type available for use with this exporter */
	abstract public String getFileType();

	/** Exports the mapping to the specified file */
	abstract public void exportMapping(Project project, Mapping mapping, ArrayList<MappingCell> mappingCells, File file) throws IOException;
}