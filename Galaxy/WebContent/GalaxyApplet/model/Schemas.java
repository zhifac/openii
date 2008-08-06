// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

import model.listeners.SchemasListener;
import model.server.ServletConnection;

/**
 * Class for managing the schemas in the schema repository
 * @author CWOLF
 */
public class Schemas
{
	/** Caches schema information */
	static private HashMap<Integer,Schema> schemas = null;
	
	/** Caches schema element count information */
	static private HashMap<Integer,Integer> schemaElementCounts = new HashMap<Integer,Integer>();
	
	/** Caches schema element information */
	static private HashMap<Integer,SchemaElement> schemaElements = new HashMap<Integer,SchemaElement>();
	
	/** Caches schema elements associated with schemas */
	static private HashMap<Integer,ArrayList<SchemaElement>> schemaSchemaElements = new HashMap<Integer,ArrayList<SchemaElement>>();
	
	/** List of listeners monitoring schema events */
	static private ArrayList<SchemasListener> listeners = new ArrayList<SchemasListener>();
		
	//----------------
	// Schema Actions
	//----------------
	
	/** Initializes the schema list */
	static private void initSchemas()
	{
		schemas = new HashMap<Integer,Schema>();
		for(Schema schema : ServletConnection.getSchemas())
			if(ServletConnection.getSchemaElementCount(schema.getId())<10000)
			{
				schemas.put(schema.getId(),schema);
				for(SchemasListener listener : listeners)
					listener.schemaAdded(schema);
			}
	}
	
	/** Returns a list of all schemas */
	static public ArrayList<Schema> getSchemas()
	{
		if(schemas==null) initSchemas();
		return new ArrayList<Schema>(schemas.values());
	}
	
	/** Returns the specified schema */
	static public Schema getSchema(Integer schemaID)
	{
		if(schemas==null) initSchemas(); 
		return schemas.get(schemaID);
	}
	
	/** Locks the specified schema */
	static public void lockSchema(Integer schemaID)
	{
		if(ServletConnection.lockSchema(schemaID))
		{
			Schema schema = schemas.get(schemaID);
			Schema newSchema = new Schema(schema.getId(),schema.getName(),schema.getAuthor(),schema.getSource(),schema.getType(),schema.getDescription(),true);
			schemas.put(schemaID, newSchema);
			for(SchemasListener listener : listeners)
				listener.schemaLocked(newSchema);
		}
	}
	
	/** Extends the specified schema */
	static public void extendSchema(Integer schemaID)
	{
		Schema extendedSchema = ServletConnection.extendSchema(schemaID);
		if(extendedSchema!=null)
		{
			// Update Galaxy to contain extended schema
			schemas.put(extendedSchema.getId(), extendedSchema);
			for(SchemasListener listener : listeners)
				listener.schemaAdded(extendedSchema);
			SelectedObjects.setSelectedSchema(extendedSchema.getId());

			// Set schema groups to match base schema
			Groups.setSchemaGroups(extendedSchema.getId(), Groups.getSchemaGroups(schemaID));
		}
	}
	
	/** Updates the specified schema */
	static public void updateSchema(Schema schema)
	{
		if(ServletConnection.updateSchema(schema))
		{
			schemas.put(schema.getId(), schema);
			for(SchemasListener listener : listeners)
				listener.schemaUpdated(schema);
		}
	}
	
	/** Delete the specified schema */
	static public void deleteSchema(Schema schema)
	{
		// Delete all data sources associated with the schema
		for(DataSource dataSource : DataSources.getDataSources(schema.getId()))
			DataSources.deleteDataSource(DataSources.getDataSource(dataSource.getId()));

		// Delete all schema group associations
		Groups.setSchemaGroups(schema.getId(),new ArrayList<Integer>());

		// Identify the parent schema ID
		Integer parentSchemaID = null;
		ArrayList<Integer> parentSchemas = getParentSchemas(schema.getId());
		if(parentSchemas.size()>0)
			parentSchemaID = parentSchemas.get(0);

		// Delete the schema
		if(ServletConnection.deleteSchema(schema.getId()))
		{
			schemas.remove(schema.getId());
			for(SchemasListener listener : listeners)
				listener.schemaRemoved(schema);
			
			// Modify the selected schema
			if(parentSchemaID!=null) SelectedObjects.setSelectedSchema(parentSchemaID);
			else if(schemas.size()>0) SelectedObjects.setSelectedSchema(schemas.get(0).getId());
		}
	}
	
	/** Filter the list of schemas to only include schemas existent in Galaxy (<=1000) */
	static public ArrayList<Integer> filter(ArrayList<Integer> schemas)
	{
		ArrayList<Integer> filteredSchemas = new ArrayList<Integer>();
		for(Integer schemaID : schemas)
			if(getSchema(schemaID)!=null) filteredSchemas.add(schemaID);
		return filteredSchemas;
	}
	
	/** Sorts the list of schemas */
	static public ArrayList<Schema> sort(ArrayList<Schema> schemas)
	{
		final class SchemaComparator implements Comparator<Schema>
		{
			public int compare(Schema schema1, Schema schema2)
				{ return schema1.getName().compareTo(schema2.getName()); }
		}
		Collections.sort(schemas,new SchemaComparator());
		return schemas;
	}

	/** Sorts the list of schemas by ID */
	static public ArrayList<Integer> sortByID(ArrayList<Integer> schemas)
	{
		final class SchemaComparator implements Comparator<Integer>
		{
			public int compare(Integer schema1, Integer schema2)
				{ return getSchema(schema1).getName().compareTo(getSchema(schema2).getName()); }
		}
		Collections.sort(schemas,new SchemaComparator());
		return schemas;
	}
	
	//-----------------------------
	// Schema Relationship Actions
	//-----------------------------
	
	/** Returns the parent schemas */
	static public ArrayList<Integer> getParentSchemas(Integer schemaID)
		{ return ServletConnection.getParentSchemas(schemaID); }

	/** Returns the child schemas */
	static public ArrayList<Integer> getChildSchemas(Integer schemaID)
		{ return ServletConnection.getChildSchemas(schemaID); }

	/** Returns the descendant schemas of the specified schema */
	static public ArrayList<Integer> getDescendantSchemas(Integer schemaID)
		{ return ServletConnection.getDescendantSchemas(schemaID); }

	/** Returns the schemas associated with the specified schema */
	static public ArrayList<Integer> getAssociatedSchemas(Integer schemaID)
		{ return ServletConnection.getAssociatedSchemas(schemaID); }

	/** Returns the root for the two specified schemas */
	static public Integer getRootSchema(Integer schema1ID, Integer schema2ID)
		{ return ServletConnection.getRootSchema(schema1ID, schema2ID); }

	/** Returns the schema path between the specified root and schema */
	static public ArrayList<Integer> getSchemaPath(Integer rootID, Integer schemaID)
		{ return ServletConnection.getSchemaPath(rootID, schemaID); }

	/** Updates the parent schemas for the specified schema */
	static public void setParentSchemas(Schema schema, ArrayList<Integer> parentIDs)
	{
		if(ServletConnection.setParentSchemas(schema.getId(), parentIDs))
		{
			schemaSchemaElements.remove(schema.getId());
			for(SchemasListener listener : listeners)
				listener.schemaParentsUpdated(schema);		
		}
	}
	
	//------------------------
	// Schema Element Actions
	//------------------------

	/** Returns the schema element count */
	static public int getSchemaElementCount(Integer schemaID)
	{
		// First, check schema element array to calculate size
		ArrayList<SchemaElement> schemaElements = schemaSchemaElements.get(schemaID);
		if(schemaElements!=null) return schemaElements.size();
		
		// If no schema elements exist, get schema element size from database
		Integer schemaElementCount = schemaElementCounts.get(schemaID);
		if(schemaElementCount==null)
		{
			schemaElementCount=ServletConnection.getSchemaElementCount(schemaID);
			schemaElementCounts.put(schemaID, schemaElementCount);
		}
		return schemaElementCount;
	}
		
	/** Retrieves the schema elements for the specified schema and type from the web service */
	static public ArrayList<SchemaElement> getSchemaElements(Integer schemaID, Class type)
	{
		// Fill cache if needed
		if(!schemaSchemaElements.containsKey(schemaID))
		{
			ArrayList<SchemaElement> currSchemaElements = ServletConnection.getSchemaElements(schemaID);
			for(SchemaElement schemaElement : currSchemaElements)
				schemaElements.put(schemaElement.getId(), schemaElement);
			schemaSchemaElements.put(schemaID, currSchemaElements);
		}
			
		// Filter on the specified type
		ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();
		for(SchemaElement schemaElement : schemaSchemaElements.get(schemaID))
			if(type==null || type.isInstance(schemaElement))
				schemaElements.add(schemaElement);
		return schemaElements;
	}	
	
	/** Gets the specified schema element */
	static public SchemaElement getSchemaElement(Integer schemaElementID)
	{
		if(!schemaElements.containsKey(schemaElementID))
			schemaElements.put(schemaElementID, ServletConnection.getSchemaElement(schemaElementID));
		return schemaElements.get(schemaElementID);
	}

	/** Returns the name for the specified schema element */
	static public String getSchemaElementName(Integer schemaID, Integer schemaElementID)
	{
		for(SchemaElement schemaElement : Schemas.getSchemaElements(schemaID, Alias.class)){
			if(((Alias)schemaElement).getElementID().equals(schemaElementID))
				return schemaElement.getName();
		}
		SchemaElement se = getSchemaElement(schemaElementID);
		if (se == null){
			Schema s = getSchema(schemaElementID);
			if (s != null)
				return 	s.getName();
			else
				return new String("") ;
		} else{
			return se.getName();
		}
		
	}
	
	/** Adds the specified schema element */
	static public Integer addSchemaElement(SchemaElement schemaElement)
	{
		Integer schemaElementID = ServletConnection.addSchemaElement(schemaElement);
		if(schemaElementID!=null)
		{
			schemaElement.setId(schemaElementID);
			schemaElements.put(schemaElement.getId(), schemaElement);
			schemaSchemaElements.get(schemaElement.getBase()).add(schemaElement);
			for(SchemasListener listener : listeners)
				listener.schemaElementAdded(schemaElement);
			return schemaElementID;
		}
		return schemaElementID;
	}
	

	
	/** Updates the specified schema element */
	static public Boolean updateSchemaElement(SchemaElement schemaElement)
	{
		if(ServletConnection.updateSchemaElement(schemaElement))
		{
			schemaElements.put(schemaElement.getId(), schemaElement);
			schemaSchemaElements.get(schemaElement.getBase()).remove(schemaElement);
			schemaSchemaElements.get(schemaElement.getBase()).add(schemaElement);
			for(SchemasListener listener : listeners)
			{
				listener.schemaElementRemoved(schemaElement);
				listener.schemaElementAdded(schemaElement);
			}
			return true;
		}
		return false;
	}
	
	/** Deletes the specified schema element */
	static public Boolean deleteSchemaElement(SchemaElement schemaElement)
	{
		if(ServletConnection.deleteSchemaElement(schemaElement.getId()))
		{
			schemaElements.remove(schemaElement.getId());
			schemaSchemaElements.get(schemaElement.getBase()).remove(schemaElement);
			for(SchemasListener listener : listeners)
				listener.schemaElementRemoved(schemaElement);
			return true;
		}
		return false;
	}
	
	/** Retrieves all base schema elements */
	public ArrayList<SchemaElement> getBaseSchemaElements(Integer schemaID)
	{
		ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>();
		for(SchemaElement schemaElement : getSchemaElements(schemaID,null))
			if(schemaElement.getBase().equals(schemaID))
				elements.add(schemaElement);
		return elements;
	}

	/** Indicates if the schema contains the specified schema element */
	static public boolean containsSchemaElement(Integer schemaID, Integer schemaElementID)
	{
		for(SchemaElement schemaElement : getSchemaElements(schemaID,null))
			if(schemaElement.getId().equals(schemaElementID)) return true;
		return false;
	}
	
	/** Retrieves the specified entity's attributes */
	static public ArrayList<Attribute> getAttributesFromEntity(Integer schemaID, Integer entityID)
	{
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		for(SchemaElement schemaElement : getSchemaElements(schemaID, Attribute.class))
		{
			Attribute attribute = (Attribute)schemaElement;
			if(attribute.getEntityID().equals(entityID)) attributes.add(attribute);
		}
		return attributes;		
	}
	
	/** Retrieves the specified entity's attributes from the domain value */
	static public ArrayList<Attribute> getAttributesFromDomain(Integer schemaID, Integer domainID)
	{
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		for(SchemaElement schemaElement : getSchemaElements(schemaID, Attribute.class))
		{
			Attribute attribute = (Attribute)schemaElement;
			if(attribute.getDomainID().equals(domainID)) attributes.add(attribute);
		}
		return attributes;		
	}
	
	/** Retrieves the specified domain's domain values */
	static public ArrayList<DomainValue> getDomainValues(Integer schemaID, Integer domainID)
	{
		ArrayList<DomainValue> domainValues = new ArrayList<DomainValue>();
		for(SchemaElement schemaElement : getSchemaElements(schemaID, DomainValue.class))
		{
			DomainValue domainValue = (DomainValue)schemaElement;
			if(domainValue.getDomainID().equals(domainID)) domainValues.add(domainValue);
		}
		return domainValues;
	}

	//------------------
	// Schema Listeners
	//------------------
	
	/** Adds a listener monitoring schema events */
	static public void addSchemasListener(SchemasListener listener)
		{ listeners.add(listener); }
	
	/** Removes a listener monitoring schema events */
	static public void removeSchemasListener(SchemasListener listener)
		{ listeners.remove(listener); }
	
	/** Gets the current schema listeners */
	static public ArrayList<SchemasListener> getSchemasListeners()
		{ return new ArrayList<SchemasListener>(listeners); }
}
