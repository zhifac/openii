package org.mitre.openii.editors.vocabulary;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.editors.schemas.schema.SchemaView;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.schemaTree.SchemaTree;
import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.Vocabulary;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/** Constructs the Manifest View */
public class ManifestView extends OpenIIEditor implements IDoubleClickListener
{	
	/** Provides unique ID values to the schema */
	private int counter = -100;
	
	/** Generate the manifest */
	private SchemaInfo generateManifest(Integer projectID)
	{
		// Fetch the vocabulary
		Project project = OpenIIManager.getProject(projectID);
		Vocabulary vocabulary = OpenIIManager.getVocabulary(projectID);
		
		// Retrieve all schema elements which might be referenced
		HashMap<Integer,SchemaElement> elements = new HashMap<Integer,SchemaElement>();
		for(Integer schemaID : vocabulary.getSchemaIDs())
		{
			SchemaInfo info = OpenIIManager.getSchemaInfo(schemaID);
			for(SchemaElement element : info.getElements(null))
				elements.put(element.getId(), element);
		}
			
		// Generate the manifest
		Schema schema = new Schema(0,project.getName(),project.getAuthor(),null,"Vocabulary",project.getDescription(),false);
		SchemaInfo manifest = new SchemaInfo(schema,new ArrayList<Integer>(),new ArrayList<SchemaElement>());
		
		// Create the domain to tie to all attributes
		Integer domainID = counter--;
		manifest.addElement(new Domain(domainID,"String","String",null));
		
		// Build a schema based on all items in the vocabulary
		for(Term term : vocabulary.getTerms())
		{
			// Add in the vocabulary term
			Entity entity = new Entity(counter--,term.getName(),term.getDescription(),null);
			manifest.addElement(entity);
			
			// Attach all associated elements
			for(AssociatedElement element : term.getElements())
			{
				String name = element.getName();
				String description = elements.get(element.getElementID()).getDescription();
				manifest.addElement(new Relationship(counter--,name,description,entity.getId(),null,null,domainID,null,null,element.getSchemaID()));
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
		schemaTree.addDoubleClickListener(this);
	}

	/** Handles the double clicking of an element */
	public void doubleClick(DoubleClickEvent e)
	{
		Relationship relationship = (Relationship)((TreeSelection)e.getSelection()).getFirstElement();
		Schema schema = OpenIIManager.getSchema(relationship.getBase());
		SchemaView.launchEditor(schema, relationship.getName().replaceAll(" \\(.*", ""));
	}
	
	// Default instantiations of editor functions
	public boolean isDirty() { return false; }
	public boolean isSaveAsAllowed() { return false; }	
	public void doSave(IProgressMonitor arg0) {}
	public void doSaveAs() {}
	public void setFocus() {}
}