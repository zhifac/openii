// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data;

import java.util.ArrayList;

import org.mitre.schemastore.data.database.FunctionDataCalls;
import org.mitre.schemastore.model.DataType;
import org.mitre.schemastore.model.Function;
import org.mitre.schemastore.model.FunctionImp;

/** Class for managing the current list of functions in the schema repository */
public class FunctionCache extends DataCache
{
	/** Stores reference to the function data calls */
	private FunctionDataCalls dataCalls = null;
	
	/** Constructs the functions cache */
	FunctionCache(DataManager manager, FunctionDataCalls dataCalls)
		{ super(manager); this.dataCalls=dataCalls; }
	
	/** Returns a listing of all data types */
	public ArrayList<DataType> getDataTypes()
		{ return dataCalls.getDataTypes(); }
	
	/** Returns a listing of all functions */
	public ArrayList<Function> getFunctions()
		{ return dataCalls.getFunctions(); }
	
	/** Returns the specified function */
	public Function getFunction(Integer functionID)
		{ return dataCalls.getFunction(functionID); }

	/** Adds the specified function */
	public Integer addFunction(Function function)
		{ return dataCalls.addFunction(function); }
	
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