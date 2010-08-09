package org.mitre.openii.editors.schemas.schema;

import java.util.ArrayList;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.widgets.schemaList.SchemaLabelProvider;

/** Displays an extension list */
public class ExtensionList implements ControlListener, MouseListener, MouseMoveListener
{
	/** Stores reference to the table */
	Table table = null;
	
	/** Constructs the extension list */
	public ExtensionList(Composite parent, String header, ArrayList<Integer> schemaIDs)
	{
		// Generate the extension list
		TableViewer list = new TableViewer(parent);
		
		// Generate the underlying table
		table = list.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		table.addControlListener(this);
		table.addMouseListener(this);
		table.addMouseMoveListener(this);
		
		// Add the list header
		TableViewerColumn column = new TableViewerColumn(list, SWT.NONE);
		column.getColumn().setText(header);
		
		// Display the list of elements
		list.setLabelProvider(new SchemaLabelProvider());
		for(Integer schemaID : schemaIDs)
			list.add(OpenIIManager.getSchema(schemaID));
	}

	/** Resize the column size when the pane is resized */
	public void controlResized(ControlEvent e)
		{ table.getColumns()[0].setWidth(table.getSize().x-10); }
	
	/** Change mouse to hand when hovering above selectable object */
	public void mouseMove(MouseEvent e)
	{
		TableItem item = table.getItem(new Point(e.x,e.y));
		int cursorType = item!=null ? SWT.CURSOR_HAND : SWT.CURSOR_ARROW;
		table.setCursor(new Cursor(table.getDisplay(), cursorType));
	}
	
	/** Handles the double clicking of a schema */
	public void mouseDoubleClick(MouseEvent e)
	{
		TableItem items[] = table.getSelection();
		if(items!=null && items.length>0) EditorManager.launchDefaultEditor(items[0].getData());
	}
	
	// Unused event listeners
	public void controlMoved(ControlEvent e) {}
	public void mouseDown(MouseEvent e) {}
	public void mouseUp(MouseEvent e) {}
}