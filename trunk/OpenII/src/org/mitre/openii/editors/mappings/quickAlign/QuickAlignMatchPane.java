package org.mitre.openii.editors.mappings.quickAlign;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.mitre.openii.widgets.BasicWidgets;
import org.mitre.schemastore.model.SchemaElement;

/** Constructs the Quick Alignment Mapping Editor */
class QuickAlignMatchPane extends Composite implements MouseListener, SelectionListener
{
	// Stores constants for highlighting the pane
	static private Color DEFAULT = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
	static private Color HIGHLIGHT = new Color(Display.getCurrent(), 255, 255, 180);
	
	/** Stores the source element */
	private SchemaElement sourceElement;
	
	// Stores various components used in the match pane */
	private Label label = null;
	private ComboViewer viewer = null;
	
	/** Constructs the match pane */
	QuickAlignMatchPane(Composite parent, SchemaElement sourceElement)
	{
		// Constructs the match pane
		super(parent, SWT.BORDER);
		this.sourceElement = sourceElement;
		setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		setLayout(new GridLayout(3,false));
		setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Display the source element label
		label = BasicWidgets.createLabel(this, sourceElement.getName());
		
		// Create the selection combo viewer
		viewer = new ComboViewer(this,SWT.READ_ONLY);
		viewer.getCombo().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Create the selection button
		BasicWidgets.createButton(this, "Edit", this);

		// Listens for selection of pane
		addMouseListener(this);
		label.addMouseListener(this);
		viewer.getCombo().addMouseListener(this);
	}

	/** Sets the label width */
	void setLabelWidth(int width)
	{
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gridData.widthHint = width;
		label.setLayoutData(gridData);
	}

	/** Handles the selection of this match pane */
	void setSelected(boolean select)
		{ setBackground(select ? HIGHLIGHT : DEFAULT); }
	
	/** Adds an element to the selection pane */
	void add(Object object)
		{ viewer.add(object); }

	/** Returns the source element */
	SchemaElement getSourceElement()
		{ return sourceElement; }

	/** Returns the target element */
	SchemaElement getTargetElement()
	{
		Object selection = ((StructuredSelection)viewer.getSelection()).getFirstElement();
		return selection instanceof SchemaElement ? (SchemaElement)selection : null;
	}

	/** Handles the selection of the pane */
	public void mouseDown(MouseEvent e)
	{
		Composite parent = getParent();
		while(!(parent instanceof QuickAlignMatchesPane))
			parent = parent.getParent();
		((QuickAlignMatchesPane)parent).setSelectedPane(this);
	}

	// Unused event listeners
	public void widgetDefaultSelected(SelectionEvent e) {}
	public void widgetSelected(SelectionEvent e) {}
	public void mouseDoubleClick(MouseEvent e) {}
	public void mouseUp(MouseEvent e) {}
}