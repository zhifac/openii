/*
 *  Copyright (C) 2007 The MITRE Corporation.  All rights reserved.
 */
package org.mitre.schemastore.porters.schemaImporters.ddl;

import java.util.*;

import org.mitre.schemastore.model.*;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;


/**
 * This class helps with the packaging of SchemaElements for transport to Yggdrasil.  It is written to be used in
 * conjunction with a event-based parser such as the one created for the DDLImporter
 *
 * @author $author$
 * @version $Revision: 1.5 $
 */
public class SchemaBuilder
{
    int currentEntityId;
    int currentAttributeId;
    Schema schema = new Schema( SchemaImporter.nextId(), "name", "","","","desc", false );
    ArrayList<SchemaElement> schemaObjects = new ArrayList<SchemaElement>();
    private HashMap<String, Domain> domainList = new HashMap<String, Domain>();


    /**
     *  Constructor - only loads in the Domain items.
     */
    public SchemaBuilder()
    {
        loadDomains();
    }


    /**
     *  Adds a new Element to the base schema and sets the currentEntityId to this new Entity's ID
     */
    public void addEntity(String name)
    {
        Entity newEntity = new Entity( SchemaImporter.nextId(), name, "", schema.getId() );
        currentEntityId = newEntity.getId();
        schemaObjects.add( newEntity );
        //System.out.println( this.getClass().getName() + " ( addEntity ) added entity number: " + currentEntityId );
    }


    /**
     *  Add a new Attribute attached to the SchemaElement identified by currentEntityId
     */
    public void addAttributeToLastEntity( String name )
    {
        System.out.println( "Adding " + name );
        Attribute newAttribute = new Attribute( SchemaImporter.nextId(), name, "", currentEntityId, domainList.get("String").getId(), 1, 1, false, schema.getId() );
        currentAttributeId = newAttribute.getId();
        schemaObjects.add( newAttribute );
    }


    public void setDomainOfLastAttribute( String domain )
    {
        Attribute a = (Attribute) getSchemaObject( currentAttributeId );
        // System.out.println( this.getClass().getName() + " ( setDomainOfLastAttribute ): " + domain);
        a.setDomainID( domainList.get( domain ).getId() );
    }


    /**
     *  adds a reference between the current entity and the entity with the given nameheck,
     */
    public void addReferenceTo(String tablename)
    {
        System.out.println(  );
        int id = SchemaImporter.nextId();
        String name = getSchemaObject( currentEntityId ).getName() + ":" + tablename;
        int leftMin =0;
        int leftMax=-1;
        int relatedId = getSchemaObject(tablename).getId();
        int rightID = relatedId;
        int rightMin = 1;
        int rightMax = 1;
        int base = schema.getId();
        Relationship r = new Relationship(id, name, currentEntityId, leftMin, leftMax, rightID, rightMin, rightMax, base );
        schemaObjects.add( r );
    }


    /**
	 * Function for loading the preset domains into the Schema and into a
	 * list for use during Attribute creation
	 */
	private void loadDomains() {
		Domain domain = new Domain(SchemaImporter.nextId(), "Integer", "The Integer domain", 0);
		schemaObjects.add(domain);
		domainList.put("Integer", domain);
		domain = new Domain(SchemaImporter.nextId(), "Double", "The Double domain", 0);
		schemaObjects.add(domain);
		domainList.put("Double", domain);
		domain = new Domain(SchemaImporter.nextId(), "String", "The String domain", 0);
		schemaObjects.add(domain);
		domainList.put("String", domain);
		domain = new Domain(SchemaImporter.nextId(), "DateTime", "The DateTime domain", 0);
		schemaObjects.add(domain);
		domainList.put("DateTime", domain);
		domain = new Domain(SchemaImporter.nextId(), "Boolean", "The Boolean domain", 0);
		schemaObjects.add(domain);
		domainList.put("Boolean", domain);
		domain = new Domain(SchemaImporter.nextId(), "Any", "The Any domain", 0);
		schemaObjects.add(domain);
		domainList.put("Any", domain);

	}



    /**
     *  Set the currentEntityId member to the SchemaElement ID with the given name
     */
    public void setEntityTo( String name )
    {
        currentEntityId = getSchemaObject( name ).getId();
    }


    /**
     *  Retrieve an ArrayList of all the SchemaElements
     */
    public ArrayList<SchemaElement> getSchemaObjects()
    {
        // System.out.println( this.getClass().getName() + " ( getSchemaObjects ) returning List with " + schemaObjects.size() + "  objects" );
        return schemaObjects;
    }

    /**
     *  Given an ID, return the SechmeElement that has that ID
     */
    private SchemaElement getSchemaObject( int id )
    {
        for(SchemaElement o : schemaObjects )
        {
            if( o.getId() == id )
            {
                return o;
            }
        }
        return null;
    }


    /**
     *  Given a name, return the SchemaElement that has that name
     */
    private SchemaElement getSchemaObject( String name )
    {
        for(SchemaElement o : schemaObjects )
        {
            if( o.getName().equals(name) )
            {
                return o;
            }
        }
        return null;
    }

}

