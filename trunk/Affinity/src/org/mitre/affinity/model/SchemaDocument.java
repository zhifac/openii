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

package org.mitre.affinity.model;

//import javax.swing.text.Document;

import org.mitre.schemastore.model.Schema;

/**
 *
 * A schema class that contains a document object.
 * 
 * @author CBONACETO
 *
 */
public class SchemaDocument extends Schema {
	private static final long serialVersionUID = 1L;
	
	private String document;

	public SchemaDocument() {
		super();
	}
	
	public SchemaDocument(String doc) {
		super();
		this.document = doc;
	}
	
	public SchemaDocument(Integer id, String name, String author, String source, String type, String description, boolean locked)
	{
		super(id, name, author, source, type, description, locked);
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}
}
