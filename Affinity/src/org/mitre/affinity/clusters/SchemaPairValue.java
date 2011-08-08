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

package org.mitre.affinity.clusters;

/** Stores a schema pair value */
public class SchemaPairValue<T>
{
	// Stores the schema IDs
	private Integer schema1ID, schema2ID;

	/** Stores the schema pair value */
	private T value;
		
	/** Constructs the schema pair value */
	public SchemaPairValue(Integer schema1ID, Integer schema2ID, T value)
		{ this.schema1ID = schema1ID; this.schema2ID = schema2ID; this.value = value; }

	/** Returns the first schema ID */
	public Integer getSchema1ID() { return schema1ID; }

	/** Returns the second schema ID */
	public Integer getSchema2ID() { return schema2ID; }

	/** Returns the value */
	public T getValue() { return value; }

	/** Allows the value to be reset */
	public void setValue(T value) { this.value = value; }

	/** Displays the schema pair value */
	public String toString()
		{ return "[" + schema1ID + "," + schema2ID + "] -> " + value; }
}