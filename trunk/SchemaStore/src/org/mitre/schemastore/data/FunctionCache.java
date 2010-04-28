// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.data.database.FunctionDataCalls;
import org.mitre.schemastore.model.DataType;
import org.mitre.schemastore.model.Function;
import org.mitre.schemastore.model.FunctionImp;

/** Class for managing the current list of functions in the schema repository */
public class FunctionCache extends DataCache
{
	/** Defines the expression for finding the next function in the expression */
	private static final String functionExp = ".*?((\\w+)\\(\\$\\d+(,\\$\\d+)*\\)).*";
	
	/** Stores reference to the function data calls */
	private FunctionDataCalls dataCalls = null;

	/** Stores the validation number */
	private Integer validationNumber = 0;
	
	/** Stores a mapping of function names to functions */
	private HashMap<String,Function> functionRefs = new HashMap<String,Function>();
	
	/** Stores a listing of function dependencies */
	private HashMap<Integer,ArrayList<Function>> functionDependencies = new HashMap<Integer,ArrayList<Function>>();
	
	/** Constructs the functions cache */
	FunctionCache(DataManager manager, FunctionDataCalls dataCalls)
		{ super(manager); this.dataCalls=dataCalls; }
	
	/** Refreshes the function dependencies*/
	private void recacheAsNeeded()
	{
		// Check to see if the schema tags have changed any
		Integer newValidationNumber = dataCalls.getFunctionValidationNumber();
		if(!newValidationNumber.equals(validationNumber))
		{
			validationNumber = newValidationNumber;
			
			// Clears the cached function hashes
			functionRefs.clear();
			functionDependencies.clear();
			
			// Caches the function names
			ArrayList<Function> functions = dataCalls.getFunctions();
			for(Function function : functions)
				functionRefs.put(function.getName(), function);

			// Caches the function dependencies
			for(Function function : functions)
			{
				ArrayList<Function> dependentFunctions = new ArrayList<Function>();
				for(String name : function.getExpression().split("[\\(\\),]+"))
					dependentFunctions.add(functionRefs.get(name));
				functionDependencies.put(function.getId(), dependentFunctions);
			}
		}
	}
	
	/** Returns a listing of all data types */
	public ArrayList<DataType> getDataTypes()
		{ return dataCalls.getDataTypes(); }
	
	/** Returns a listing of all functions */
	public ArrayList<Function> getFunctions()
		{ recacheAsNeeded(); return new ArrayList<Function>(functionRefs.values()); }
	
	/** Returns the specified function */
	public Function getFunction(Integer functionID)
		{ return dataCalls.getFunction(functionID); }

	/** Returns the dependent functions of the specified function */
	public ArrayList<Function> getDependentFunctions(Integer functionID)
	{
		recacheAsNeeded();
		ArrayList<Function> dependentFunctions = new ArrayList<Function>(functionDependencies.get(functionID));
		for(int i=0; i<dependentFunctions.size(); i++)
			for(Function dependentFunction : functionDependencies.get(dependentFunctions.get(i).getId()))
				if(!dependentFunctions.contains(dependentFunction))
					dependentFunctions.add(dependentFunction);
		return dependentFunctions;
	}
	
	/** Adds the specified function */
	public Integer addFunction(Function function)
	{
		recacheAsNeeded();

		// Validate the syntax of the expression
		String expression = function.getExpression();
		while(expression.matches(functionExp))
		{
			String dependentFunction = expression.replaceFirst(functionExp, "$1");
			if(!functionRefs.containsKey(dependentFunction.replaceFirst(functionExp, "$2"))) return 0;
			expression = expression.replace(dependentFunction, "$0");
		}
		if(!expression.equals("$0")) return 0;
		
		// Add the function
		return dataCalls.addFunction(function);
	}
	
	/** Returns the list of deletable functions */
	public ArrayList<Integer> getDeletableFunctions()
		{ return dataCalls.getDeletableFunctions(); }
	
	/** Removes the specified function */
	public boolean deleteFunction(Integer functionID)
		{ return dataCalls.deleteFunction(functionID); }
	
	/** Returns the implementations for all functions */
	public ArrayList<FunctionImp> getFunctionImps()
		{ return dataCalls.getFunctionImps(); }
	
	/** Sets the specified function implementation */
	public boolean setFunctionImp(FunctionImp functionImp)
		{ return dataCalls.setFunctionImp(functionImp); }
	
	/** Deletes the specified function implementation */
	public boolean deleteFunctionImp(FunctionImp functionImp)
		{ return dataCalls.deleteFunctionImp(functionImp); }
}