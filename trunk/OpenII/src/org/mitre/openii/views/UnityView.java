package org.mitre.openii.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.mitre.harmony.model.ProjectManager;
import org.mitre.cg.viz.gui.swt.Schema2DPlot;

public class UnityView extends ViewPart {

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		Composite unityComponent = new Composite(parent, SWT.EMBEDDED);
		
		Schema2DPlot schemaPlot = new Schema2DPlot(unityComponent, SWT.NONE);
	}

	@Override
	public void setFocus() {}
	
	/** Shuts down the Harmony View */
	public void dispose()
	{
		ProjectManager.save();
		super.dispose();
	}

}
