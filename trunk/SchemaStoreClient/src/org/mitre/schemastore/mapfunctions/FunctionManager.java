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

 
package org.mitre.schemastore.mapfunctions;

import java.util.*;
import org.mitre.schemastore.client.SchemaStoreClient;


/** Class for managing the various map functions */
public class FunctionManager
{
	/** Stores listings of the map functions */
	private List<AbstractMappingFunction> functions = new ArrayList<AbstractMappingFunction>();
    
    private SchemaStoreClient client = null;
	
	/** 
     * Constructs the porter manager class
     */
	public FunctionManager(SchemaStoreClient client)
	{
        this.client = client;
        // get a list of all implementations of AbstractMappingFunction
        //http://snippets.dzone.com/posts/show/4831
        //or better yet http://www.palbrattberg.com/2009/05/11/find-java-classes-in-the-classpath/
	}
	
	/** 
     * Returns a list of names of map functions.  To get an instance of the function, use {@link #getFunction() getFunction}.
     */
	public List<String> getAvailableFunctions()
	{ 
        ArrayList<String> names = new ArrayList<String>();  
        for( AbstractMappingFunction f : functions )
        {
            try
            {
                names.add(f.getDisplayName());
            }
            catch (NotImplementedException e)
            {
                //do nothing...dont' include this function in the list of what's available
            }
        }
        return names;
    }
    
    
    public AbstractMappingFunction getFunction(String niceName) throws FunctionNotFoundException
    {
        for( AbstractMappingFunction f : functions )
        {
            try
            {
                if( niceName.equals( f.getDisplayName() ) )
                {
                   // return f.clone();
                   return null;
                }
            }
            catch (NotImplementedException e)
            {
                throw new FunctionNotFoundException( niceName + " was not found as an available function" );
            }
        }
        throw new FunctionNotFoundException( niceName + " was not found as an available function" );
    }
    
	
}