/*
 *  Copyright 2008 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
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

package org.mitre.harmony.view;

import java.io.Serializable;

import org.mitre.schemastore.porters.Exporter;
import org.mitre.schemastore.porters.PorterType;

/** Abstract Exporter class */
public class GenericExporter extends Exporter implements Serializable
{
	/** Stores the exporter type */
	private PorterType type = null;

	/** Stores the name */
	private String name = null;
	
	/** Stores the description */
	private String description = null;
	
	/** Stores the file type */
	private String fileType = null;
	
	/** Indicates if the file can be overwritten */
	private Boolean overwritten = null;
	
	/** Constructs the generic exporter */
	public GenericExporter(PorterType type, Exporter exporter)
	{
		this.type = type;
		name = exporter.getName();
		description = exporter.getDescription();
		fileType = exporter.getFileType();
		overwritten = exporter.isFileOverwritten();
	}
	
	/** Returns the exporter type */
	public PorterType getType() { return type; }

	/** Returns the importer name */
	public String getName() { return name; }

	/** Returns the importer description */
	public String getDescription() { return description; }
	
	/** Return the file type available for use with this exporter */
	public String getFileType() { return fileType; }
	
	/** Indicates if the file is overwritten */
	public boolean isFileOverwritten() { return overwritten; }
}