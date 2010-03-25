package org.mitre.openii.widgets.mappingList;

import java.util.ArrayList;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

/** Constructs the Add Mappings to List Dialog */
class AddMappingsToListDialog extends ElementListSelectionDialog
{
	/** Constructs the dialog */
	AddMappingsToListDialog(Shell shell, ArrayList<Schema> schemas, ArrayList<Mapping> selectedMappings)
	{
		super(shell,new LabelProvider());
		setMessage("Select Mappings (* = any string, ? = any char):");
		setMultipleSelection(true);
		
		// Generate list of mappings to select from
		ArrayList<MappingReference> mappings = new ArrayList<MappingReference>();
		for(Schema source : schemas)
			TARGET_LOOP: for(Schema target : schemas)
				if(!source.equals(target))
				{
					for(Mapping mapping : selectedMappings)
						if(mapping.getSourceId().equals(source.getId()) && mapping.getTargetId().equals(target.getId()))
							continue TARGET_LOOP;
					Mapping mapping = new Mapping(null,null,source.getId(),target.getId());
					mappings.add(new MappingReference(mapping,schemas));
				}
		setElements(WidgetUtilities.sortList(mappings).toArray());
	}	

	/** Configures the dialog shell */
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setImage(OpenIIActivator.getImage("Mapping.gif"));
		shell.setText("Mapping Selection");
	}
}