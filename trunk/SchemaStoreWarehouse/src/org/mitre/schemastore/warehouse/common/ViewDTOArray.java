package org.mitre.schemastore.warehouse.common;

import java.io.Serializable;

public class ViewDTOArray  implements Serializable 
{
	/**	Instance variables	*/
	private ViewDTO[] arrayOfViewDetails = null;
	
	/** No-arg constructor */
	public ViewDTOArray()	{}
	
	/** Constructor accepting all values */
	public ViewDTOArray(ViewDTO[] arrayOfViewDetails)	
	{	this.arrayOfViewDetails = arrayOfViewDetails;	}
	
	/**	Get methods	*/
	public ViewDTO[] getArrayOfViewDetails()	{	return arrayOfViewDetails;	}
	
	/**	Set methods	*/
	public void setArrayOfViewDetails(ViewDTO[] arrayOfViewDetails)	{	this.arrayOfViewDetails = arrayOfViewDetails;	}

}
