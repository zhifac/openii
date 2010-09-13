package org.mitre.openii.editors.mappings.quickAlign;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/** Constructs the QuickAlign info pane */
public class QuickAlignInfoPane extends Canvas implements PaintListener
{	
	/** Stores the selected match pane */
	private QuickAlignMatchPane selectedMatchPane = null;

	// Stores the source and target element info panes
	private ElementInfoPane sourcePane, targetPane;
	
	/** Private class for displaying element information */
	private class ElementInfoPane extends Composite
	{
		/** Stores the schema info (for displaying paths) */
		private HierarchicalSchemaInfo schemaInfo = null;
		
		// Stores reference to the various displayed items
		private Label name = null;
		private Label description = null;
		private Label path = null;
		
		/** Constructs the specified field */
		private Label generateField(String label)
		{
			BasicWidgets.createLabel(this, label);
			Label field = new Label(this, SWT.NONE);
			field.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			return field;
		}
		
		/** Constructs the element info pane */
		private ElementInfoPane(Composite parent, String label, HierarchicalSchemaInfo schemaInfo)
		{
			super(parent, SWT.NONE);
			this.schemaInfo = schemaInfo;
			setLayout(new GridLayout(2,false));
			setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			// Generate the info fields
			name = generateField(label+"Name");
			description = generateField("Description");
			path = generateField("Path");
		}
		
		/** Sets the information based on the specified element */
		private void setInfo(SchemaElement element)
		{
			// Set the name and description
			name.setText(element==null ? "" : element.getName());
			description.setText(element==null ? "" : element.getDescription());
			
			// Set the path
			String pathString = "";
			if(element!=null)
			{
				ArrayList<SchemaElement> pathElements = schemaInfo.getPaths(element.getId()).get(0);
				for(SchemaElement pathElement : pathElements.subList(0, pathElements.size()-1))
					pathString += schemaInfo.getDisplayName(pathElement.getId()) + " -> ";
				if(pathString.length()>4) pathString = pathString.substring(0, pathString.length()-4);
			}
			path.setText(pathString);

		}
	}
	
	/** Constructs the QuickAlign info pane */
	QuickAlignInfoPane(Composite parent, HierarchicalSchemaInfo sourceInfo, HierarchicalSchemaInfo targetInfo)
	{
		// Constructs the info pane
		super(parent, SWT.BORDER);
		setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		setLayout(new GridLayout(1,false));
		setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Generate the sub panes for the info pane
		sourcePane = new ElementInfoPane(this,"Source",sourceInfo);
		targetPane = new ElementInfoPane(this,"Target",targetInfo);
		
		// Monitors for the painting of this panel
		addPaintListener(this);
	}

	/** Sets the selected pane */
	void setSelectedPane(QuickAlignMatchPane pane)
	{
		// Internally change the selected match pane
		if(selectedMatchPane!=null) selectedMatchPane.setSelected(false);
		selectedMatchPane = pane;
		if(selectedMatchPane!=null) selectedMatchPane.setSelected(true);
		
		// Display the newly selected information
		QuickAlignMatch match = selectedMatchPane.getTargetMatch();
		sourcePane.setInfo(selectedMatchPane.getSourceElement());
		targetPane.setInfo(match==null ? null : match.getElement());
	}
	
	/** Draws a line to divide the source and target element info */
	public void paintControl(PaintEvent e)
	{
		Rectangle rect = sourcePane.getBounds();
	    e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLACK));
		e.gc.drawLine(rect.x, rect.y+rect.height+2, rect.x+rect.width, rect.y+rect.height+2);
	}
}