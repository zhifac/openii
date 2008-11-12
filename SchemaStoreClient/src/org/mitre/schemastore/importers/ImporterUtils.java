// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.importers;


/**
 * Class for project-wide static utility functions
 * @author DBROWNING
 */
public class ImporterUtils {

	// Keep an internal representation of which id to use next for a SchemaObject.
	// These IDs are only for linking the schema objects together, once the schema
	//   representation is stored in the database, the database will assign the proper
	//   unique IDs.
	private static Integer autoIncrementedId = 0;
	public static Integer nextId() {
		return autoIncrementedId++;
	}

	public static final String ANY = "Any";
	public static final String INTEGER = "Integer";
	public static final String DOUBLE = "Double";
	public static final String STRING = "String";
	public static final String DATETIME = "DateTime";
	public static final String BOOLEAN = "Boolean";

}
