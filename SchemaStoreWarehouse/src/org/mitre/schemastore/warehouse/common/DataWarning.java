package org.mitre.schemastore.warehouse.common;

public class DataWarning extends RuntimeException 
{
	private static final long serialVersionUID = 1L;

	public DataWarning(String message) 
	{
		// Constructor.  Create a DataWarning object containing
	    // the given message as its error message.
		super(message);
	}
}
