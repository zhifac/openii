package org.mitre.harmony.matchers;

public class MatcherOption
{
	/** Enumeration of the various types of options */
	public static enum OptionType { CHECKBOX }
	
	// Stores the matcher option variables
	private OptionType type;
	private String name;
	private String value;

	/** Constructs the matcher option */
	public MatcherOption(OptionType type, String name,String value)
		{ this.type = type; this.name = name; this.value = value; }

	// Getters for this class
	public OptionType getType() { return type; }
	public String getName() { return name; }
	public String getValue() { return value; }

	/** Sets the matcher option value */
	public void setValue(String value)
		{ this.value = value; }
	
	/** Indicates if the option is selected */
	public Boolean isSelected()
		{ return value.equals("true"); }

	/** Marks the option as selected */
	public void setSelected(Boolean selected)
		{ value = selected ? "true" : "false"; }
}