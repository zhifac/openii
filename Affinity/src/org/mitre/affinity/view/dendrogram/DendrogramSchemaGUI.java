/*
 *  Copyright 2010 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mitre.affinity.view.dendrogram;

import org.eclipse.swt.graphics.Rectangle;
import org.mitre.schemastore.model.Schema;

public class DendrogramSchemaGUI {
	/** The schema this DendrogramSchemaGUI is for */
	protected Schema schema;
	
	/** Bounds of the rendered schema */
	protected Rectangle bounds = null;
	
	/** Whether or not the schema is selected */
	protected boolean selected = false;
	
	public DendrogramSchemaGUI(Schema schema) {
		this.schema = schema;
	}

	public Schema getSchema() {
		return schema;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
