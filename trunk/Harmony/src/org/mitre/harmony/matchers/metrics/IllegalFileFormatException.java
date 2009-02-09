package org.mitre.harmony.matchers.metrics;

public class IllegalFileFormatException extends Exception 
{
	/**
	 * Exception thrown when the given Ground Truths file is not in the required format
	 * Required format of he XML file should have the following tags:
	 * <results>
	 * 		<mapping>
	 * 			<sourceSchema schema1="Schema_name_1"/>
	 * 			<targetSchema schema2="Schema_name_2"/>
	 * 			<link>
	 * 				<sourceNode>Warehouses-CurrentNumBooks</sourceNode>
	 * 				<targetNode>Warehouse-CurCapacity</targetNode>
	 * 				...
	 * 				...
	 * 			</link>
	 * 			<link>
	 * 				<sourceNode>Availability-Quantity</sourceNode>
	 * 				<targetNode>Availability-Amount</targetNode>
	 * 				...
	 * 				...
	 * 			</link>
	 * 		</mapping>
	 * </results>
	 * @author STANDON
	 */
	public IllegalFileFormatException(String message) 
    {
		// Constructor.  Create a IllegalFileFormatException object containing
        // the given message as its error message.
		super(message);
    }
}
