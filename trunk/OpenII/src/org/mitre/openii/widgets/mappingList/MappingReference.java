package org.mitre.openii.widgets.mappingList;

import java.util.List;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;
import org.w3c.dom.Document;

/**
 * The entire goal of this class is to provide a readable toString method
 * for the ComboList view to show something useful to the user. However, I
 * have added more functionality to show if the mapping has changed.
 * 
 * @author PLOCKABY
 *
 */

@SuppressWarnings("serial")
public class MappingReference extends Mapping {
	/** Stores the mapping label */
	private String label;
	
	/** stores whether we are modified or new */
	private Boolean modified = false;

	/** Stores the old mapping id */
	private Integer oldMappingId = null;

	/** Stores the old source schema */
	private Integer oldSourceId = null;

	/** Stores the old target schema */
	private Integer oldTargetId = null;

	/** Stores the exported document containing this mapping */
	private Document document = null;

	/** Constructs the mapping */
	MappingReference(Mapping mapping, List<Schema> schemas) {
		super(mapping.getId(), mapping.getProjectId(), mapping.getSourceId(), mapping.getTargetId());
		
		// Generate the label
		String source = null;
		String target = null;
		for (Schema schema : schemas) {
			if (schema.getId().equals(getSourceId())) { source = schema.getName(); }
			if (schema.getId().equals(getTargetId())) { target = schema.getName(); }
		}

		label = "'" + source + "' to '" + target + "'";
	}

	/** Outputs the mapping reference */
	public String toString() {
		return label;
	}

	public Boolean isModified() {
		return this.modified;
	}

	public void setOldSourceId(Integer oldSourceId) {
		this.modified = true;
		this.oldSourceId = oldSourceId;
	}
	
	public void setOldTargetId(Integer oldTargetId) {
		this.modified = true;
		this.oldTargetId = oldTargetId;
	}

	public void setOldMappingid(Integer oldMappingId) {
		this.modified = true;
		this.oldMappingId = oldMappingId;
	}

	public void setExportDocument(Document document) {
		this.document = document;
	}

	public Integer getOldSourceId() {
		return this.oldSourceId;
	}

	public Integer getOldTargetId() {
		return this.oldTargetId;
	}

	public Integer getOldMappingId() {
		return this.oldMappingId;
	}

	public Document getExportDocument() {
		return this.document;
	}
}