package org.mitre.harmony.matchers.mergers;

import java.util.Iterator;
import java.util.Set;

import org.mitre.harmony.matchers.ElementPair;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

public class TypedVoteMerger extends VoteMerger {
	
	/** Returns the name associated with this match merger */
	public String getName()
		{ return "Typed Vote Merger"; }

	/** Create a new VoterScores object with only same-type (e.g. attribute to attribute) matches */
	public void addVoterScores(VoterScores voterScores) {

		VoterScores newVoterScores = new VoterScores(voterScores.getScoreCeiling());
	
		Set<ElementPair> elementPairs = voterScores.getElementPairs();	
		Iterator i = elementPairs.iterator();
		while (i.hasNext()) {
			ElementPair pair = (ElementPair) i.next();
			Integer element1 = pair.getElement1();
			Integer element2 = pair.getElement2();
			
			SchemaElement e1 = schema1.getElement(element1);
			SchemaElement e2 = schema2.getElement(element2);
System.out.println("comparing " + e1 + " to " + e2);
			if (getType(e1) == getType(e2)) {
				newVoterScores.setScore(element1, element2, voterScores.getScore(element1, element2));
			}
		}
		
		super.addVoterScores(newVoterScores);
	}
	
	private String getType(SchemaElement schemaElement) {
		String type = null;
//		if(schemaElement instanceof Entity) type = "Entity";
//		else if(schemaElement instanceof Attribute) type = "Attribute";
//		else if(schemaElement instanceof Domain) type = "Domain";
//		else if(schemaElement instanceof DomainValue) type = "DomainValue";
//		else if(schemaElement instanceof Relationship) type = "Relationship";
//		else if(schemaElement instanceof Containment) type = "Containment";
//		else if(schemaElement instanceof Subtype) type = "Subtype";
//		else if(schemaElement instanceof Alias) type = "Alias";
//		else System.out.println("can't type " + schemaElement.getName());
//		
//		return type;
		try {
			System.out.println("element " + schemaElement.getName() + " : " + schemaElement.getClass().getName());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
			return schemaElement.getClass().getName();
	}
}