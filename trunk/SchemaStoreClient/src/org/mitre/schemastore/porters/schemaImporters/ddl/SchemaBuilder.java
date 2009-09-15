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
    /* mapping of string domain to Object Domain.  New domains will be added. */
    private HashMap<String, Domain> domainList = new HashMap<String, Domain>();
    /* mapping of various flavors to standardized mapping names.  New names will be added. */
    private HashMap<String, String> domainMapping = new HashMap<String, String>();

    /**
     *  Constructor - only loads in the Domain items.
     */
    public SchemaBuilder()
    {
        loadDomains();
        loadStandardizedMappings();
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
        System.out.print(a.getName() + ": ");
        // System.out.println( this.getClass().getName() + " ( setDomainOfLastAttribute ): " + domain);
        a.setDomainID( convertDomain( domain ) );
    }

    /**
     *  Convert a string representation of the domain to the appropriate Domain ID.  If necessary, a new Domain and all mappings will be added
     */
    protected int convertDomain( String domain )
    {
        domain = domain.toLowerCase();
        System.out.println(domain);
        String stdName = domainMapping.get(domain);
        if( stdName == null )
        {
            //add it so all instances will use the same DomainID
            //add standardized mapping (to itself)
            domainMapping.put(domain, domain);
            System.out.println("added new domain");
            //add corresponding Domain
            Domain d = new Domain(SchemaImporter.nextId(), domain, domain, 0);
            schemaObjects.add(d);
            domainList.put(domain, d);
            System.out.println("added new Domain:  " + d.getName());
            stdName = domain;
        }
        return domainList.get( stdName ).getId();
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
		Domain domain = new Domain(SchemaImporter.nextId(), SchemaImporter.INTEGER, "The Integer domain", 0);
		schemaObjects.add(domain);
		domainList.put(SchemaImporter.INTEGER, domain);

		domain = new Domain(SchemaImporter.nextId(), SchemaImporter.REAL, "The Real domain", 0);
		schemaObjects.add(domain);
		domainList.put(SchemaImporter.REAL, domain);

		domain = new Domain(SchemaImporter.nextId(), SchemaImporter.STRING, "The String domain", 0);
		schemaObjects.add(domain);
		domainList.put(SchemaImporter.STRING, domain);

		domain = new Domain(SchemaImporter.nextId(), SchemaImporter.DATETIME, "The DateTime domain", 0);
		schemaObjects.add(domain);
		domainList.put(SchemaImporter.DATETIME, domain);

		domain = new Domain(SchemaImporter.nextId(), SchemaImporter.BOOLEAN, "The Boolean domain", 0);
		schemaObjects.add(domain);
		domainList.put(SchemaImporter.BOOLEAN, domain);
	}

	private void loadStandardizedMappings() {
	    //strings
	    domainMapping.put("varchar", SchemaImporter.STRING);
	    domainMapping.put("varchar2", SchemaImporter.STRING);
	    domainMapping.put("char", SchemaImporter.STRING);
	    domainMapping.put("text", SchemaImporter.STRING);
	    domainMapping.put("char varying", SchemaImporter.STRING);
	    domainMapping.put("character varying", SchemaImporter.STRING);
	    domainMapping.put("nchar", SchemaImporter.STRING);
	    domainMapping.put("nchar varying", SchemaImporter.STRING);
	    domainMapping.put("clob", SchemaImporter.STRING);
	    domainMapping.put("nclob", SchemaImporter.STRING);
	    domainMapping.put("uniqueidentifier", SchemaImporter.STRING);
	    //boolean
	    domainMapping.put("bit", SchemaImporter.BOOLEAN);
	    domainMapping.put("bit varying", SchemaImporter.BOOLEAN);
	    //real
	    domainMapping.put("float", SchemaImporter.REAL);
	    domainMapping.put("real", SchemaImporter.REAL);
	    domainMapping.put("double", SchemaImporter.REAL);
	    domainMapping.put("double precision", SchemaImporter.REAL);
	    domainMapping.put("numeric", SchemaImporter.REAL);
	    domainMapping.put("number", SchemaImporter.REAL);
	    domainMapping.put("decimal", SchemaImporter.REAL);
	    domainMapping.put("dec", SchemaImporter.REAL);
	    //integer
	    domainMapping.put("int", SchemaImporter.INTEGER);
	    domainMapping.put("integer", SchemaImporter.INTEGER);
	    domainMapping.put("tinyint", SchemaImporter.INTEGER);
	    domainMapping.put("smallint", SchemaImporter.INTEGER);
	    //datetime
	    domainMapping.put("date", SchemaImporter.DATETIME);
	    domainMapping.put("time", SchemaImporter.DATETIME);
	    domainMapping.put("datetime", SchemaImporter.DATETIME);
	    domainMapping.put("tiemstamp", SchemaImporter.DATETIME);
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

