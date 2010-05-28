package org.mitre.openii.widgets.porters;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.porters.Importer;
import org.mitre.schemastore.porters.Porter;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.Importer.URIType;
import org.mitre.schemastore.porters.PorterManager.PorterType;

/** Constructs the Importer Selector */
public class ImporterSelector extends ComboViewer implements ISelectionChangedListener
{	
	/** Stores the type of importer */
	private PorterType importerType = null;
	
	/** Constructs the Importer Selector */
	public ImporterSelector(Composite parent, PorterType importerType)
	{
		super(parent, SWT.NONE);
		this.importerType = importerType;
		addSelectionChangedListener(this);
		
		// Create a list of the available importers
		ArrayList<Porter> importers = new PorterManager(RepositoryManager.getClient()).getPorters(importerType);
		for(Porter importer : WidgetUtilities.sortList(importers))
		{
			URIType uriType = ((Importer)importer).getURIType();
			if(uriType==URIType.FILE || uriType==URIType.M3MODEL || uriType==URIType.URI)
				add(importer);
		}
		
		// Select the most recently selected importer
		getCombo().select(0);
		Class<?> importerClass = OpenIIManager.getPorterPreference(importerType);
		for(Porter importer : importers)
			if(importer.getClass().equals(importerClass))
				setSelection(new StructuredSelection(importer));
	}	

	/** Returns the selected importer */ @SuppressWarnings("unchecked")
	public <T extends Importer> T getImporter()
		{ return (T)((StructuredSelection)getSelection()).getFirstElement(); }

	/** Stores changes to the selected porter */
	public void selectionChanged(SelectionChangedEvent e)
		{ OpenIIManager.setPorterPreference(importerType, getImporter()); }
}