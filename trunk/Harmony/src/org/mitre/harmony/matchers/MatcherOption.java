package org.mitre.harmony.matchers;

public class MatcherOption {
	private String id;
	private String type;
	private String name;
	private Boolean selected;

	public MatcherOption(String id, String type, String name, Boolean selected) {
		this.id = id;
		this.type = type;
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

	public Boolean getSelected() {
		return this.selected;
	}
}
