package org.mitre.openii.editors;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.schemaTree.SchemaTree;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/** Constructs the Manifest View */
public class ManifestView extends OpenIIEditor
{	
	/** Provides unique ID values to the schema */
	private int counter = -100;
	
	/** Generate the taxonomy branch */
	private SchemaElement generateBranch(SchemaElement element, HierarchicalSchemaInfo taxonomy, SchemaInfo manifest)
	{
		// Fetch element if already exists
		SchemaElement manifestElement = manifest.getElement(element.getId());
		if(manifestElement!=null) return manifestElement;

		// Create the element
		manifestElement = new Entity(element.getId(),taxonomy.getDisplayName(element.getId()),element.getDescription(),null);
		manifest.addElement(manifestElement);
		
		// Connect element to all parent elements
		for(SchemaElement parent : taxonomy.getParentElements(element.getId()))
		{
			SchemaElement manifestParent = generateBranch(parent, taxonomy, manifest);
			manifest.addElement(new Subtype(counter--, manifestParent.getId(), manifestElement.getId(), null));
		}
		return manifestElement;
	}
	
	/** Generate the manifest */
	private SchemaInfo generateManifest(Integer taxonomyID)
	{
		// Fetch the taxonomy schema
		HierarchicalSchemaInfo taxonomy = new HierarchicalSchemaInfo(OpenIIManager.getSchemaInfo(taxonomyID));		
		
		// Generate the manifest
		SchemaInfo manifest = new SchemaInfo(taxonomy.getSchema(),new ArrayList<Integer>(),new ArrayList<SchemaElement>());
		Integer domainID = counter--;
		manifest.addElement(new Domain(domainID,"String","String",null));
		
		// Get all mappings which use this taxonomy
		ArrayList<Mapping> mappings = new ArrayList<Mapping>();
		for(Project project : OpenIIManager.getProjects())
			if(new ArrayList<Integer>(Arrays.asList(project.getSchemaIDs())).contains(taxonomyID))
				for(Mapping mapping : OpenIIManager.getMappings(project.getId()))
					if(mapping.getTargetId().equals(taxonomyID))
						mappings.add(mapping);
		
		// Build a schema based on all items aligned with the taxonomy
		for(Mapping mapping : mappings)
		{
			// Fetch the aligned schema
			SchemaInfo alignedSchema = OpenIIManager.getSchemaInfo(mapping.getSourceId());
			
			// Add attributes to the manifest for all aligned elements
			for(MappingCell mappingCell : OpenIIManager.getMappingCells(mapping.getId()))
			{
				// Generate the taxonomy branch for the aligned element
				SchemaElement taxonomyElement = taxonomy.getElement(mappingCell.getOutput());
				SchemaElement manifestElement = generateBranch(taxonomyElement,taxonomy,manifest);

				// Add each aligned elements to the manifest
				for(Integer input : mappingCell.getElementInputIDs())
				{
					SchemaElement alignedElement = alignedSchema.getElement(input);
					String name = alignedSchema.getDisplayName(input) + " (" + alignedSchema.getSchema().getName() + ")";
					String description = alignedElement.getDescription();
					Integer entityID = manifestElement.getId();
					manifest.addElement(new Relationship(counter--,name,description,entityID,null,null,domainID,null,null,null));
				}
			}
		}
		
		// Return the manifest
		return manifest;
	}
	
	/** Initializes the Search Results Viewer */
	public void init(IEditorSite site, IEditorInput input) throws PartInitException
	{
		super.init(site,input);
		setPartName("Manifest for " + getElement().toString());
	}
	
	/** Displays the Manifest View (for the specified taxonomy) */
	public void createPartControl(Composite parent)
	{
		// Construct the main pane
		Composite pane = new Composite(parent, SWT.DIALOG_TRIM);
		pane.setLayout(new GridLayout(1,false));
		pane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Layout the menu pane and tree pane
		SchemaTree schemaTree = new SchemaTree(pane, generateManifest(getElementID()));
		schemaTree.lockModel();
	}
	
	// Default instantiations of editor functions
	public boolean isDirty() { return false; }
	public boolean isSaveAsAllowed() { return false; }	
	public void doSave(IProgressMonitor arg0) {}
	public void doSaveAs() {}
	public void setFocus() {}
}