package org.mitre.openii.widgets.matchers;

import java.util.ArrayList;

import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.MatcherOption;
import org.mitre.harmony.matchers.matchers.Matcher;

/** Constructs a pane displaying all of the matchers */
public class MatchersPane
{
	/** Displays the matcher parameters */
	public class MatcherParametersDisplay extends ToolTip
	{
		/** Stores the matcher who's parameters are being displayed */
		private Matcher matcher;
		
		/** Constructs the parameters display */
		public MatcherParametersDisplay(Control parent, Matcher matcher)
			{ super(parent); this.matcher = matcher; setShift(new Point(10,10)); }

		/** Displays the parameters */
		protected Composite createToolTipContentArea(Event e, Composite parent)
		{
			Composite pane = new Composite(parent, SWT.NONE);
			pane.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			pane.setLayout(new GridLayout(1,false));
			
			// Display the header
			Label header = new Label(pane, SWT.NONE);
			header.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			header.setFont(new Font(Display.getCurrent(),"Arial",10,SWT.BOLD));
			header.setText("Parameters");
			
			// Display the matcher parameters
			for(MatcherOption option : matcher.getOptions())
			{
				Label parameter = new Label(pane, SWT.NONE);
				parameter.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				parameter.setText(option.getName() + ": " + option.getValue());
			}
			
			return pane;
		} 
	}
	
	/** Stores a matcher check box */
	private class MatcherCheckBox implements MouseListener
	{
		/** Stores the matcher associated with the check box */
		private Matcher matcher;
		
		/** Stores the check box */
		private Button checkbox;

		/** Initializes the matcher check box */
		private MatcherCheckBox(Composite parent, Matcher matcher, SelectionListener listener)
		{
			this.matcher = matcher;

			// Generate the pane for holding the matcher check box
			Composite pane = new Composite(parent, SWT.NONE);
			GridLayout layout = new GridLayout(2,false);
			layout.marginHeight=0; layout.marginLeft=0;
			layout.horizontalSpacing=1;
			pane.setLayout(layout);
			
			// Display the check box
			checkbox = new Button(pane, SWT.CHECK);
			checkbox.setText(matcher.getName());
			checkbox.setSelection(matcher.isDefault());
			checkbox.addSelectionListener(listener);

			// Allow settings to be modified if exist
			if(matcher.getOptions().size()>0)
			{
				// Display the settings pane
				Label label = new Label(pane, SWT.NONE);
				label.setFont(new Font(Display.getCurrent(),"Arial",7,SWT.NONE));
				label.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
				label.setText("(Settings)");
				label.setCursor(new Cursor(Display.getCurrent(), SWT.CURSOR_HAND));
				label.addMouseListener(this);
				
				// Set the matcher tool tip
				new MatcherParametersDisplay(label, matcher);
			}
		}
		
		/** Indicates if the check box is selected */
		private boolean isSelected()
			{ return checkbox.getSelection(); }
		
		/** Returns the matcher associated with this check box */
		private Matcher getMatcher()
			{ return matcher; }

		/** Launches a pane to edit matcher parameters */
		public void mouseUp(MouseEvent e)
			{ new EditMatcherParametersDialog(checkbox.getShell(),matcher).open(); }

		// Unused listener events
		public void mouseDown(MouseEvent e) {}
		public void mouseDoubleClick(MouseEvent e) {}
	}
	
	/** Stores the list of matcher checkboxes */
	private ArrayList<MatcherCheckBox> checkboxes = new ArrayList<MatcherCheckBox>();
	
	/** Constructs the Matchers Pane */
	public MatchersPane(Composite parent, SelectionListener listener)
	{
		// Create a group panel for displaying the matchers
		Group group = new Group(parent, SWT.NONE);
		group.setText("Matchers");
		group.setLayout(new RowLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Construct a list of all matchers that can be selected
		for(Matcher matcher : MatcherManager.getVisibleMatchers())
			checkboxes.add(new MatcherCheckBox(group,matcher,listener));
	}

	/** Return the list of selected matchers */
	public ArrayList<Matcher> getMatchers()
	{
		ArrayList<Matcher> matchers = new ArrayList<Matcher>();
		for(MatcherCheckBox checkbox : checkboxes)
			if(checkbox.isSelected()) matchers.add(checkbox.getMatcher());
		return matchers;
	}
}