package org.mitre.openii.editors.mappings.quickAlign;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/** Constructs the Quick Alignment Match Pane */
class QuickAlignMatchPane extends Composite implements MouseListener, SelectionListener, ISelectionChangedListener
{
	// Stores constants for highlighting the pane
	static private Color DEFAULT = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
	static private Color HIGHLIGHT = new Color(Display.getCurrent(), 255, 255, 180);
	
	/** Stores the source element */
	private SchemaElement sourceElement;
	
	// Stores various components used in the match pane */
	private Label label = null;
	private ComboViewer viewer = null;
	
	/** Gets the Matches pane */
	private QuickAlignMatchesPane getMatchesPane()
	{
		Composite parent = getParent();
		while(!(parent instanceof QuickAlignMatchesPane))
			parent = parent.getParent();
		return (QuickAlignMatchesPane)parent;
	}
	
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
		viewer.addSelectionChangedListener(this);
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
	
	/** Updates the matches shown by this pane */
	void updateMatches(ArrayList<QuickAlignMatch> matches, HierarchicalSchemaInfo targetInfo)
	{
		// Identify the selected match
		QuickAlignMatch selectedMatch = getTargetMatch();
		if(selectedMatch==null && matches!=null)
			if(matches.size()>0 && matches.get(0).getMappingCell().isValidated())
				selectedMatch = matches.get(0);

		// Replace elements in the list
		viewer.getCombo().removeAll();
		viewer.add("");
		if(matches!=null)
			for(QuickAlignMatch match : matches)
				viewer.add(match);
		
		// Select the element as needed
		if(selectedMatch!=null)
			viewer.setSelection(new StructuredSelection(selectedMatch), true);
	}

	/** Returns the source element */
	SchemaElement getSourceElement()
		{ return sourceElement; }

	/** Returns the potential matches */
	ArrayList<QuickAlignMatch> getPotentialMatches()
	{
		ArrayList<QuickAlignMatch> matches = new ArrayList<QuickAlignMatch>();
		for(int i=0; i<viewer.getCombo().getItemCount(); i++)
		{
			Object item = viewer.getElementAt(i);
			if(item instanceof QuickAlignMatch)
				matches.add((QuickAlignMatch)item);
		}
		return matches;
	}
	
	/** Returns the target match */
	QuickAlignMatch getTargetMatch()
	{
		Object selection = ((StructuredSelection)viewer.getSelection()).getFirstElement();
		return selection instanceof QuickAlignMatch ? (QuickAlignMatch)selection : null;
	}
	
	/** Handles changes to the selected element */
	public void selectionChanged(SelectionChangedEvent e)
		{ getMatchesPane().setSelectedPane(this); }
	
	/** Handles the selection of the pane */
	public void mouseDown(MouseEvent e)
		{ getMatchesPane().setSelectedPane(this); }

	/** Allows manual selection of a target element */
	public void widgetSelected(SelectionEvent e)
	{
//		QuickAlignMatchesPane matchesPane = getMatchesPane();
//		matchesPane.g
//		
//		// Prepare the schema tree dialog
//		SchemaTreeDialog dialog = new SchemaTreeDialog(getShell(),schema,schema.getModel());
//		ArrayList<SchemaElement> rootIDs = schema.getFilteredRootElements();
//		dialog.getSchemaTree().setSelectedElement(rootIDs.size()>0 ? rootIDs.get(0).getId() : null);
//
//		// Launch the schema tree dialog
//		if(dialog.open()==Window.OK)
//		{
//			SchemaTree tree = dialog.getSchemaTree();
//			schema.setModel(tree.getSchema().getModel());
//			schema.setFilteredRoots(new ArrayList<Integer>(Arrays.asList(new Integer[]{tree.getSelectedElement()})));
//		}
	}
	
	// Unused event listeners
	public void widgetDefaultSelected(SelectionEvent e) {}
	public void mouseDoubleClick(MouseEvent e) {}
	public void mouseUp(MouseEvent e) {}
}