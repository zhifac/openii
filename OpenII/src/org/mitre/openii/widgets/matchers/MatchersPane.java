package org.mitre.openii.widgets.matchers;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.matchers.Matcher;

/** Constructs a pane displaying all of the matchers */
public class MatchersPane
{
	/** Stores a matcher check box */
	private class MatcherCheckBox
	{
		/** Stores the matcher associated with the check box */
		private Matcher matcher;
		
		/** Stores the check box */
		private Button checkbox;

		/** Initializes the matcher check box */
		private MatcherCheckBox(Composite parent, Matcher matcher, SelectionListener listener)
		{
			this.matcher = matcher;
			checkbox = new Button(parent, SWT.CHECK);
			checkbox.setText(matcher.getName());
			checkbox.setSelection(matcher.isDefault());
			checkbox.addSelectionListener(listener);
		}
		
		/** Indicates if the check box is selected */
		private boolean isSelected()
			{ return checkbox.getSelection(); }
		
		/** Returns the matcher associated with this check box */
		private Matcher getMatcher()
			{ return matcher; }
	}
	
	/** Stores the list of matcher checkboxes */
	private ArrayList<MatcherCheckBox> checkboxes = new ArrayList<MatcherCheckBox>();
	
	/** Constructs the Matchers Pane */
	public MatchersPane(Composite parent, SelectionListener listener)
	{
		Group group = new Group(parent, SWT.NONE);
		group.setText("Matchers");
		group.setLayout(new GridLayout(2, false));
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