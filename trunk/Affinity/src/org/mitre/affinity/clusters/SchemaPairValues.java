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

import java.util.ArrayList;
import java.util.HashMap;

/** Class for storing schema pair values */
public class SchemaPairValues<T>
{	
	/** Interface used to define how to transform an element */
	public interface Transformation<T>
		{ public T transform(T value); }
	
	// Stores the schema pair values
	protected HashMap<String,SchemaPairValue<T>> values = new HashMap<String,SchemaPairValue<T>>();
	
	// Generates the key to retrieve the grid value
	private String getKey(Integer schema1ID, Integer schema2ID)
	{
		if(schema1ID>schema2ID) { int tempSchemaID=schema1ID; schema1ID=schema2ID; schema2ID=tempSchemaID; }
		return schema1ID + " " + schema2ID;
	}
	
	/** Sets a schema pair value */
	public void set(Integer schema1ID, Integer schema2ID, T value)
		{ values.put(getKey(schema1ID,schema2ID),new SchemaPairValue<T>(schema1ID,schema2ID,value)); }

	/** Returns a value for the schema pair */
	public T get(Integer schema1ID, Integer schema2ID)
		{ return values.get(getKey(schema1ID,schema2ID)).getValue(); }

	/** Returns all schema pair values */
	public ArrayList<SchemaPairValue<T>> getValues()
		{ return new ArrayList<SchemaPairValue<T>>(values.values()); }
	
	/** Transforms the distance grid */
	public void transform(Transformation<T> transformation)
	{
		for(SchemaPairValue<T> value : values.values())
			value.setValue(transformation.transform(value.getValue()));
	}
	
	/** Displays the schema pair values */
	public String toString()
	{
		StringBuffer out = new StringBuffer();
		for(SchemaPairValue<T> value : values.values())
			out.append(value + "\n");
		return out.toString();
	}
}