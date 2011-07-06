package org.mitre.harmony.matchers.options;

/** Class for storing the Matcher Checkbox Option */
public class MatcherCheckboxOption extends MatcherOption
{
	/** Constructs the matcher checkbox option */
	public MatcherCheckboxOption(String name, Boolean value)
		{ super(name,value.toString()); }
	
	/** Constructs the matcher checkbox option */
	public MatcherCheckboxOption(String name, String text, Boolean value)
		{ super(name,text,value.toString()); }
	
	/** Indicates if the option is selected */
	public Boolean isSelected()
		{ return getValue().equals("true"); }

	/** Marks the option as selected */
	public void setSelected(Boolean selected)
		{ setValue(selected.toString()); }
}