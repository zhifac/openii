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
import java.util.ArrayList;

import org.mitre.schemastore.porters.Importer;
import org.mitre.schemastore.porters.PorterType;
import org.mitre.schemastore.porters.URIType;

/** Generic Importer class */
public class GenericImporter extends Importer implements Serializable
{
	/** Stores the importer type */
	private PorterType type = null;
	
	/** Stores the name */
	private String name = null;
	
	/** Stores the description */
	private String description = null;
	
	/** Stores the URIType */
	private URIType uriType = null;
	
	/** Stores the URI file types */
	private ArrayList<String> fileTypes = null;
	
	/** Constructs the generic importer */
	public GenericImporter(PorterType type, Importer importer)
	{
		this.type = type;
		name = importer.getName();
		description = importer.getDescription();
		uriType = importer.getURIType();
		fileTypes = importer.getFileTypes();
	}
	
	/** Returns the importer type */
	public PorterType getType() { return type; }
	
	/** Returns the importer name */
	public String getName()	{ return name; }

	/** Returns the importer description */
	public String getDescription() { return description; }
	
	/** Returns the importer URI type */
	public URIType getURIType()	{ return uriType; }
	
	/** Returns the importer URI file types (only needed when URI type is FILE) */
	public ArrayList<String> getFileTypes()	{ return fileTypes; }
}