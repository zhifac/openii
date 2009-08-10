package org.mitre.schemastore.warehouse.common;

public class NoDataFoundException extends Exception 
{
	public NoDataFoundException(String message) 
	{
		// Constructor.  Create a NoDataFoundException object containing
        // the given message as its error message.
		super(message);
	}
}
