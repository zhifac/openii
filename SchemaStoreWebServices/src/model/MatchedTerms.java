package model;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Term;

/** Sorts the list of matched terms */
public class MatchedTerms
{
	/** Stores the list of matched terms */
	private ArrayList<Term> matchedTerms = new ArrayList<Term>();

	/** Stores the list of suppliers and matched supplies */
	private HashMap<Integer,ArrayList<Integer>> supplierHash = null;
	
	/** Generate supplier list */
	private void generateSupplierList()
	{
		supplierHash = new HashMap<Integer,ArrayList<Integer>>();
		for(Term term : matchedTerms)
			for(AssociatedElement element : term.getElements())
			{
				ArrayList<Integer> elements = supplierHash.get(element.getSchemaID());
				if(elements==null)
					supplierHash.put(element.getSchemaID(), elements = new ArrayList<Integer>());
				if(!elements.contains(element.getElementID()))
					elements.add(element.getElementID());
			}
	}
	
	/** Adds a term to the list */
	public void add(Term term)
	{
		matchedTerms.add(term);
		supplierHash = null;
	}
	
	/** Returns the list of suppliers */
	public ArrayList<Integer> getSuppliers()
	{
		if(supplierHash==null) generateSupplierList();
		return new ArrayList<Integer>(supplierHash.keySet());
	}
	
	/** Returns the list of supplier items */
	public ArrayList<Integer> getItems(Integer supplierID)
	{
		if(supplierHash==null) generateSupplierList();
		ArrayList<Integer> elementIDs = supplierHash.get(supplierID);
		return elementIDs==null ? new ArrayList<Integer>() : elementIDs;
	}
	
	/** Returns the size of the list */
	public int size()
		{ return matchedTerms.size(); }
}