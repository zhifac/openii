// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.exporters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.project.ProjectMapping;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.porters.Exporter;
import org.mitre.schemastore.porters.PorterManager.PorterType;
import org.mitre.schemastore.porters.projectExporters.ProjectExporter;

/**
 * Dialog for exporting the current project
 * @author CWOLF
 */
public class ExportProjectDialog extends AbstractExportDialog
{
	/** Declares the export type */
	protected PorterType getExporterType() { return PorterType.PROJECT_EXPORTERS; }
	
	/** Handles the export to the specified file */
	protected void export(HarmonyModel harmonyModel, Exporter exporter, File file) throws IOException
	{
		// Gather up the project to export
		Project project = harmonyModel.getProjectManager().getProject();
		HashMap<Mapping,ArrayList<MappingCell>> mappings = new HashMap<Mapping,ArrayList<MappingCell>>();
		for(ProjectMapping mapping : harmonyModel.getMappingManager().getMappings())
			mappings.put(mapping, mapping.getMappingCells());

		// Export the project to the specified file
		((ProjectExporter)exporter).exportProject(project,mappings, file);
	}
}
