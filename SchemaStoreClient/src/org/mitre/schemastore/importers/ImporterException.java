// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.importers;

/** Class for throwing Importer exceptions */ @SuppressWarnings("serial")
public class ImporterException extends Exception
{
	// Stores various types of importer exceptions
	public static final int INVALID_URI = 0;
	public static final int PARSE_FAILURE = 1;
	public static final int IMPORT_FAILURE = 2;
	
	/** Stores the exception type */
	private Integer exceptionType;
	
	/** Constructs an importer exception */
	public ImporterException(Integer exceptionType, String message)
		{ super(message); this.exceptionType = exceptionType; }
	
	/** Returns the exception type */
	public Integer getExceptionType()
		{ return exceptionType; }
	
	/** Returns the error message */
	public String getMessage()
	{
		String header = "";
		switch(exceptionType)
		{
			case INVALID_URI: header = "Invalid URI"; break;
			case PARSE_FAILURE: header = "Parse Failure"; break;
			case IMPORT_FAILURE: header = "Import Failure"; break;			
		}
		return header + ": " + super.getMessage();
	}
}
