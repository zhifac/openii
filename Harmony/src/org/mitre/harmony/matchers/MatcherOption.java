package org.mitre.harmony.matchers;

public class MatcherOption {
	private String id;
	private Types type;
	private String name;
	private String value = null;
	private Boolean selected = null;

	public static enum Types {
		CHECKBOX
	}

	public MatcherOption(String id, String type, String name, String value) throws Exception {
		this.id = id;
		this.name = name;

		// convert our type into the Types enum
		type = type.toLowerCase();
		if (type.equals("checkbox")) {
			this.type = Types.CHECKBOX;
		} else {
			throw new Exception("Could not process matcher type " + type + ".");
		}

		// convert our value into a boolean if it is
		if (value.toLowerCase().equals("true") || value.toLowerCase().equals("on") || value.toLowerCase().equals("enabled")) {
			this.selected = true;
		}
		if (value.toLowerCase().equals("false") || value.toLowerCase().equals("off") || value.toLowerCase().equals("disabled")) {
			this.selected = false;
		}
	}

	public String getId() {
		return this.id;
	}

	public Types getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
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
