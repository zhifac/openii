package org.mitre.harmony.matchers;

public class MatcherOption {
	private String id;
	private String type;
	private String name;
	private Boolean selected; // using the object Boolean so that it can be set to "null" if needed

	public MatcherOption(String id, String type, String name, Boolean selected) {
		this.id = id;
		this.type = type.toLowerCase();
		this.name = name;
		this.selected = selected;
	}

	public String getId() {
		return this.id;
	}

	public String getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	/** Get and set the value of this option.
	 *  More of these could be added if additional option values are needed. 
	 */
	public Boolean getSelected() {
		return this.selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
}
