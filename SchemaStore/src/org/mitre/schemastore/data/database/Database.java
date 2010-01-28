// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data.database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Tag;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;

/**
 * Handles access to the database
 * @author CWOLF
 */
public class Database
{
	/** Sets up a database connection */
	private DatabaseConnection connection = null;

	/** Class for returning extensions */
	public class Extension
	{
		/** Stores the schema ID */
		private Integer schemaID;

		/** Stores the extension ID */
		private Integer extensionID;

		/** Constructs the extension */
		private Extension(Integer schemaID, Integer extensionID)
			{ this.schemaID = schemaID;  this.extensionID = extensionID; }

		/** Returns the schema ID */
		public Integer getSchemaID() { return schemaID; }

		/** Returns the extension ID */
		public Integer getExtensionID() { return extensionID; }
	}

	/** Class for returning a schema tag */
	public class SchemaTag
	{
		/** Stores the schema ID */
		private Integer schemaID;

		/** Stores the tag ID */
		private Integer tagID;

		/** Constructs the schema tag */
		private SchemaTag(Integer schemaID, Integer tagID)
			{ this.schemaID = schemaID;  this.tagID = tagID; }

		/** Returns the schema ID */
		public Integer getSchemaID() { return schemaID; }

		/** Returns the tag ID */
		public Integer getTagID() { return tagID; }
	}

	/** Scrub strings to avoid database errors */
	private String scrub(String word, int length)
	{
		if(word!=null)
		{
			word = word.replace("'","''");
			if(connection.getDatabaseType()==DatabaseConnection.POSTGRES)
				word = word.replace("\\","\\\\");
			if(word.length()>length) word = word.substring(0,length);
		}
		return word;
	}

	/** Constructs the database class */
	public Database(DatabaseConnection connection)
		{ this.connection = connection; }

    /** Indicates if the database is properly connected */
 	public boolean isConnected()
 		{ try { connection.getStatement(); return true; } catch(SQLException e) { return false; } }

	//---------------------------------------
	// Handles Universal IDs in the Database
	//---------------------------------------

	/** Retrieves a universal id */
	public Integer getUniversalIDs(int count) throws SQLException
	{
		Statement stmt = connection.getStatement();
		stmt.executeUpdate("LOCK TABLE universal_id IN exclusive MODE");
		stmt.executeUpdate("UPDATE universal_id SET id = id+"+count);
		ResultSet rs = stmt.executeQuery("SELECT id FROM universal_id");
		rs.next();
		Integer universalID = rs.getInt("id")-count;
		stmt.close();
		connection.commit();
		return universalID;
	}

	//---------------------------------
	// Handles Schemas in the Database
	//---------------------------------

	/** Retrieves the list of schemas in the repository */
	public ArrayList<Schema> getSchemas()
	{
		ArrayList<Schema> schemas = new ArrayList<Schema>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id,name,author,source,\"type\",description,locked FROM \"schema\"");
			while(rs.next())
				schemas.add(new Schema(rs.getInt("id"),rs.getString("name"),rs.getString("author"),rs.getString("source"),rs.getString("type"),rs.getString("description"),rs.getString("locked").equals("t")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getSchemas: "+e.getMessage()); }
		return schemas;
	}

	/** Retrieves the specified schema from the repository */
	public Schema getSchema(Integer schemaID)
	{
		Schema schema = null;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id,name,author,source,\"type\",description,locked FROM \"schema\" WHERE id="+schemaID);
			if(rs.next())
				schema = new Schema(rs.getInt("id"),rs.getString("name"),rs.getString("author"),rs.getString("source"),rs.getString("type"),rs.getString("description"),rs.getString("locked").equals("t"));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getSchema: "+e.getMessage()); }
		return schema;
	}

	/** Extends the specified schema */
	public Schema extendSchema(Schema schema)
	{
		Schema extendedSchema = null;
		try {
			int schemaID = getUniversalIDs(1);
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("INSERT INTO \"schema\"(id,name,author,source,\"type\",description,locked) VALUES("+schemaID+",'"+scrub(schema.getName(),100)+" Extension','"+scrub(schema.getAuthor(),100)+"','"+scrub(schema.getSource(),200)+"','"+scrub(schema.getType(),100)+"','Extension of "+scrub(schema.getName(),483)+"','f')");
			stmt.executeUpdate("INSERT INTO extensions(schema_id,base_id) VALUES("+schemaID+","+schema.getId()+")");
			stmt.close();
			connection.commit();
			extendedSchema = new Schema(schemaID,schema.getName()+" Extension",schema.getAuthor(),schema.getSource(),schema.getType(),"Extension of "+schema.getName(),false);
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:extendSchema: "+e.getMessage());
		}
		return extendedSchema;
	}

	/** Adds the specified schema */
	public Integer addSchema(Schema schema)
	{
		Integer schemaID = 0;
		try {
			schemaID = getUniversalIDs(1);
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("INSERT INTO \"schema\"(id,name,author,source,\"type\",description,locked) VALUES("+schemaID+",'"+scrub(schema.getName(),100)+"','"+scrub(schema.getAuthor(),100)+"','"+scrub(schema.getSource(),200)+"','"+scrub(schema.getType(),100)+"','"+scrub(schema.getDescription(),500)+"','"+(schema.getLocked()?"t":"f")+"')");
			stmt.close();
			connection.commit();
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			schemaID = 0;
			System.out.println("(E) Database:addSchema: "+e.getMessage());
		}
		return schemaID;
	}

	/** Updates the specified schema */
	public boolean updateSchema(Schema schema)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("UPDATE \"schema\" SET name='"+scrub(schema.getName(),100)+"', author='"+scrub(schema.getAuthor(),100)+"', source='"+scrub(schema.getSource(),200)+"', \"type\"='"+scrub(schema.getType(),100)+"', description='"+scrub(schema.getDescription(),4096)+"' WHERE id="+schema.getId());
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:updateSchema: "+e.getMessage());
		}
		return success;
	}

	/** Returns the list of deletable schemas */
	public ArrayList<Integer> getDeletableSchemas()
	{
		ArrayList<Integer> schemas = new ArrayList<Integer>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id FROM \"schema\" " +
											 "EXCEPT SELECT schema_id AS id FROM data_source " +
											 "EXCEPT SELECT schema_id AS id FROM mapping_schema " +
											 "EXCEPT SELECT base_id AS id FROM extensions");
			while(rs.next())
				schemas.add(rs.getInt("id"));
			stmt.close();
		}
		catch(SQLException e) { System.out.println("(E) Database:getDeletableSchemas: "+e.getMessage()); }
		return schemas;
	}

	/** Deletes the specified schema */
	public boolean deleteSchema(int schemaID)
	{
		boolean success = false;
		try {
			// Identify which schema elements should be deleted
			Statement stmt = connection.getStatement();

			// Delete the schema elements
			stmt.executeUpdate("DELETE FROM alias WHERE schema_id="+schemaID);
			stmt.executeUpdate("DELETE FROM subtype WHERE schema_id="+schemaID);
			stmt.executeUpdate("DELETE FROM containment WHERE schema_id="+schemaID);
			stmt.executeUpdate("DELETE FROM relationship WHERE schema_id="+schemaID);
			stmt.executeUpdate("DELETE FROM attribute WHERE schema_id="+schemaID);
			stmt.executeUpdate("DELETE FROM domainvalue WHERE schema_id="+schemaID);
			stmt.executeUpdate("DELETE FROM \"domain\" WHERE schema_id="+schemaID);
			stmt.executeUpdate("DELETE FROM entity WHERE schema_id="+schemaID);

			// Delete the schema from the database
			stmt.executeUpdate("DELETE FROM schema_tag WHERE schema_id="+schemaID);
			stmt.executeUpdate("DELETE FROM extensions WHERE schema_id="+schemaID);
			stmt.executeUpdate("DELETE FROM \"schema\" WHERE id="+schemaID);
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:deleteSchema: "+e.getMessage());
		}
		return success;
	}

	/** Locks the specified schema */
	public boolean lockSchema(int schemaID, boolean locked)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("UPDATE \"schema\" SET locked='"+(locked?"t":"f")+"' WHERE id="+schemaID);
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:lockSchema: "+e.getMessage());
		}
		return success;
	}

	//---------------------------------------
	// Handles Schema Tags in the Database
	//---------------------------------------

	/** Retrieves the schema tags validation number */
	public Integer getSchemaTagValidationNumber()
	{
		Integer validationNumber = 0;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT sum((mod(schema_id,100)+1)*(mod(tag_id,100)+1)) AS validation_number FROM schema_tag");
			if(rs.next())
				validationNumber = rs.getInt("validation_number");
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getSchemaTagValidationNumber: "+e.getMessage()); }
		return validationNumber;
	}

	/** Retrieves the list of tags */
	public ArrayList<Tag> getTags()
	{
		ArrayList<Tag> tags = new ArrayList<Tag>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id,name,parent_id FROM tags");
			while(rs.next())
				tags.add(new Tag(rs.getInt("id"),rs.getString("name"),rs.getInt("parent_id")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getTags: "+e.getMessage()); }
		return tags;
	}

	/** Retrieves the specified tag from the repository */
	public Tag getTag(Integer tagID)
	{
		Tag tag = null;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id,name,parent_id FROM tags");
			if(rs.next())
				tag = new Tag(rs.getInt("id"),rs.getString("name"),rs.getInt("parent_id"));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getTag: "+e.getMessage()); }
		return tag;
	}

	/** Retrieves the list of sub-categories for the specified tag */
	public ArrayList<Tag> getSubcategories(Integer tagID)
	{
		ArrayList<Tag> tags = new ArrayList<Tag>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id,name,parent_id FROM tags WHERE parent_id " + (tagID==null ? " IS NULL" : "= "+tagID));
			while(rs.next())
				tags.add(new Tag(rs.getInt("id"),rs.getString("name"),rs.getInt("parent_id")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getSubcatagories: "+e.getMessage()); }
		return tags;
	}

	/** Adds the specified tag */
	public Integer addTag(Tag tag)
	{
		Integer tagID = 0;
		try {
			tagID = getUniversalIDs(1);
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("INSERT INTO tags(id,name,parent_id) VALUES("+tagID+",'"+scrub(tag.getName(),100)+"',"+tag.getParentId()+")");
			stmt.close();
			connection.commit();
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			tagID = 0;
			System.out.println("(E) Database:addTag: "+e.getMessage());
		}
		return tagID;
	}

	/** Updates the specified tag */
	public Boolean updateTag(Tag tag)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("UPDATE tags SET name='"+scrub(tag.getName(),100)+"' WHERE id="+tag.getId());
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:updateTags: "+e.getMessage());
		}
		return success;
	}

	/** Deletes the specified tag */
	public boolean deleteTag(int tagID)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("DELETE FROM schema_tag WHERE tag_id="+tagID);
			stmt.executeUpdate("DELETE FROM tags WHERE id="+tagID);
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:deleteTag: "+e.getMessage());
		}
		return success;
	}

	/** Retrieve the list of schema tags */
	public ArrayList<SchemaTag> getSchemaTags()
	{
		ArrayList<SchemaTag> schemaTags = new ArrayList<SchemaTag>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT schema_id, tag_id FROM schema_tag");
			while(rs.next())
				schemaTags.add(new SchemaTag(rs.getInt("schema_id"),rs.getInt("tag_id")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getSchemaTags: "+e.getMessage()); }
		return schemaTags;
	}

	/** Add the tag to the specified schema */
	public Boolean addTagToSchema(Integer schemaID, Integer tagID)
	{
		Boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT count(*) AS count FROM schema_tag WHERE schema_id="+schemaID+" AND tag_id="+tagID);
			rs.next();
			if(rs.getInt("count")==0)
				stmt.executeUpdate("INSERT INTO schema_tag(schema_id,tag_id) VALUES ("+schemaID+","+tagID+")");
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:addTagToSchema: "+e.getMessage());
		}
		return success;
	}

	/** Remove the tag to the specified schema */
	public Boolean removeTagFromSchema(Integer schemaID, Integer tagID)
	{
		Boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("DELETE FROM schema_tag WHERE schema_id="+schemaID+" AND tag_id="+tagID);
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:removeTagFromSchema: "+e.getMessage());
		}
		return success;
	}

	//----------------------------------------------
	// Handles Schema Relationships in the Database
	//----------------------------------------------

	/** Retrieves the schema extensions validation number */
	public Integer getSchemaExtensionsValidationNumber()
	{
		Integer validationNumber = 0;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT sum((mod(schema_id,100)+1)*(mod(base_id,100)+1)) AS validation_number FROM extensions");
			if(rs.next())
				validationNumber = rs.getInt("validation_number");
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getSchemaExtensionsValidationNumber: "+e.getMessage()); }
		return validationNumber;
	}

	/** Retrieves the list of schema extensions */
	public ArrayList<Extension> getSchemaExtensions()
	{
		ArrayList<Extension> extensions = new ArrayList<Extension>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT schema_id,base_id FROM extensions");
			while(rs.next())
				extensions.add(new Extension(rs.getInt("base_id"),rs.getInt("schema_id")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getSchemaExtensions: "+e.getMessage()); }
		return extensions;
	}

	/** Updates the specified schema parents */
	public boolean setSchemaParents(Integer schemaID, ArrayList<Integer> parentIDs)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("DELETE FROM extensions WHERE schema_id="+schemaID);
			for(Integer parentSchemaID : parentIDs)
				stmt.executeUpdate("INSERT INTO extensions(schema_id,base_id) VALUES("+schemaID+","+parentSchemaID+")");
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:updateSchema: "+e.getMessage());
		}
		return success;
	}

	//-----------------------------------------
	// Handles Schema Elements in the Database
	//-----------------------------------------

	/** Retrieves the default domain values from the repository */
	public ArrayList<Domain> getDefaultDomains()
	{
		ArrayList<Domain> defaultDomains = new ArrayList<Domain>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id,name,description FROM \"domain\" WHERE id<0");
			while(rs.next())
				defaultDomains.add(new Domain(rs.getInt("id"),rs.getString("name"),rs.getString("description"),null));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getDefaultDomains: "+e.getMessage()); }
		return defaultDomains;
	}

	/** Retrieves the default domain value count from the repository */
	public Integer getDefaultDomainCount()
	{
		Integer defaultDomainCount = 0;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT count(*) AS count FROM \"domain\" WHERE id<0");
			if(rs.next()) defaultDomainCount = rs.getInt("count");
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getDefaultDomainCount: "+e.getMessage()); }
		return defaultDomainCount;
	}

	/** Retrieves the list of base elements for the specified schema in the repository */
	public ArrayList<SchemaElement> getBaseElements(Integer schemaID)
	{
		ArrayList<SchemaElement> baseElements = new ArrayList<SchemaElement>();
		try {
			Statement stmt = connection.getStatement();

			// Gets the schema entities
			ResultSet rs = stmt.executeQuery("SELECT id,name,description FROM entity WHERE schema_id="+schemaID);
			while(rs.next())
				baseElements.add(new Entity(rs.getInt("id"),rs.getString("name"),rs.getString("description"),schemaID));

			// Gets the schema attributes
			rs = stmt.executeQuery("SELECT id,name,description,entity_id,domain_id,\"min\",\"max\",\"key\" FROM attribute WHERE schema_id="+schemaID);
			while(rs.next())
			{
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				Integer entityID = rs.getInt("entity_id");
				Integer domainID = rs.getInt("domain_id");
				Integer min = rs.getString("min")==null?null:rs.getInt("min");
				Integer max = rs.getString("max")==null?null:rs.getInt("max");
				boolean key = rs.getString("key").equals("t");

				baseElements.add(new Attribute(id,name,description,entityID,domainID,min,max,key,schemaID));
			}

			// Gets the schema domains
			rs = stmt.executeQuery("SELECT id,name,description FROM \"domain\" WHERE schema_id="+schemaID);
			while(rs.next())
				baseElements.add(new Domain(rs.getInt("id"),rs.getString("name"),rs.getString("description"),schemaID));

			// Gets the schema domain values
			rs = stmt.executeQuery("SELECT id,value,description,domain_id FROM domainvalue WHERE schema_id="+schemaID);
			while(rs.next())
				baseElements.add(new DomainValue(rs.getInt("id"),rs.getString("value"),rs.getString("description"),rs.getInt("domain_id"),schemaID));

			// Gets the schema relationships
			rs = stmt.executeQuery("SELECT id,name,left_id,left_min,left_max,right_id,right_min,right_max FROM relationship WHERE schema_id="+schemaID);
			while(rs.next())
			{
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				Integer leftID = rs.getInt("left_id");
				Integer leftMin = rs.getString("left_min")==null?null:rs.getInt("left_min");
				Integer leftMax = rs.getString("left_max")==null?null:rs.getInt("left_max");
				Integer rightID = rs.getInt("right_id");
				Integer rightMin = rs.getString("right_min")==null?null:rs.getInt("right_min");
				Integer rightMax = rs.getString("right_max")==null?null:rs.getInt("right_max");
				baseElements.add(new Relationship(id,name,leftID,leftMin,leftMax,rightID,rightMin,rightMax,schemaID));
			}

			// Gets the schema containment relationships
			rs = stmt.executeQuery("SELECT id,name,description,parent_id,child_id,\"min\",\"max\" FROM containment WHERE schema_id="+schemaID);
			while(rs.next())
			{
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				Integer parentID = rs.getInt("parent_id");
				Integer childID = rs.getInt("child_id");
				Integer min = rs.getString("min")==null?null:rs.getInt("min");
				Integer max = rs.getString("max")==null?null:rs.getInt("max");
				baseElements.add(new Containment(id,name,description,parentID,childID,min,max,schemaID));
			}

			// Gets the schema subset relationships
			rs = stmt.executeQuery("SELECT id,parent_id,child_id FROM subtype WHERE schema_id="+schemaID);
			while(rs.next())
			{
				Integer id = rs.getInt("id");
				Integer parentID = rs.getInt("parent_id");
				Integer childID = rs.getInt("child_id");
				baseElements.add(new Subtype(id,parentID,childID,schemaID));
			}

			// Gets the schema aliases
			rs = stmt.executeQuery("SELECT id,name,alias.element_id FROM alias WHERE schema_id="+schemaID);
			while(rs.next())
				baseElements.add(new Alias(rs.getInt("id"),rs.getString("name"),rs.getInt("element_id"),schemaID));

			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getBaseElements: "+e.getMessage()); }
		return baseElements;
	}

	/** Retrieves the requested schema element from the repository */
	public SchemaElement getSchemaElement(Integer schemaElementID)
	{
		SchemaElement schemaElement = null;
		try {
			Statement stmt = connection.getStatement();

			// Determine the base and type
			Integer base = null;
			String type = "";
			ResultSet rs = stmt.executeQuery("SELECT schema_id, \"type\" FROM schema_elements WHERE id="+schemaElementID);
			if(rs.next())
				{ base = rs.getInt("schema_id"); type = rs.getString("type").trim(); }

			// Gets the specified entity
			if(type.equals("entity"))
			{
				rs = stmt.executeQuery("SELECT id,name,description FROM entity WHERE id="+schemaElementID);
				rs.next();
				schemaElement = new Entity(rs.getInt("id"),rs.getString("name"),rs.getString("description"),base);
			}

			// Gets the specified attribute
			else if(type.equals("attribute"))
			{
				rs = stmt.executeQuery("SELECT id,name,description,entity_id,domain_id,\"min\",\"max\",\"key\" FROM attribute WHERE id="+schemaElementID);
				rs.next();
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				Integer entityID = rs.getInt("entity_id");
				Integer domainID = rs.getInt("domain_id");
				Integer min = rs.getString("min")==null?null:rs.getInt("min");
				Integer max = rs.getString("max")==null?null:rs.getInt("max");
				boolean key = rs.getString("key").equals("t");
				schemaElement = new Attribute(id,name,description,entityID,domainID,min,max,key,base);
			}

			// Gets the specified domain
			else if(type.equals("domain"))
			{
				rs = stmt.executeQuery("SELECT id,name,description FROM \"domain\" WHERE id="+schemaElementID);
				rs.next();
				schemaElement = new Domain(rs.getInt("id"),rs.getString("name"),rs.getString("description"),base);
			}

			// Gets the specified domain value
			else if(type.equals("domainvalue"))
			{
				rs = stmt.executeQuery("SELECT id,value,description,domain_id FROM domainvalue WHERE id="+schemaElementID);
				rs.next();
				schemaElement = new DomainValue(rs.getInt("id"),rs.getString("value"),rs.getString("description"),rs.getInt("domain_id"),base);
			}

			// Gets the specified relationship
			else if(type.equals("relationship"))
			{
				rs = stmt.executeQuery("SELECT id,name,left_id,left_min,left_max,right_id,right_min,right_max FROM relationship WHERE id="+schemaElementID);
				rs.next();
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				Integer leftID = rs.getInt("left_id");
				Integer leftMin = rs.getString("left_min")==null?null:rs.getInt("left_min");
				Integer leftMax = rs.getString("left_max")==null?null:rs.getInt("left_max");
				Integer rightID = rs.getInt("right_id");
				Integer rightMin = rs.getString("right_min")==null?null:rs.getInt("right_min");
				Integer rightMax = rs.getString("right_max")==null?null:rs.getInt("right_max");
				schemaElement = new Relationship(id,name,leftID,leftMin,leftMax,rightID,rightMin,rightMax,base);
			}

			// Gets the specified containment relationship
			else if(type.equals("containment"))
			{
				rs = stmt.executeQuery("SELECT id,name,description,parent_id,child_id,\"min\",\"max\" FROM containment WHERE id="+schemaElementID);
				rs.next();
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				Integer parentID = rs.getInt("parent_id");
				Integer childID = rs.getInt("child_id");
				Integer min = rs.getString("min")==null?null:rs.getInt("min");
				Integer max = rs.getString("max")==null?null:rs.getInt("max");
				schemaElement = new Containment(id,name,description,parentID,childID,min,max,base);
			}

			// Gets the specified subset relationship
			else if(type.equals("subtype"))
			{
				rs = stmt.executeQuery("SELECT id,parent_id,child_id FROM subtype WHERE id="+schemaElementID);
				rs.next();
				Integer id = rs.getInt("id");
				Integer parentID = rs.getInt("parent_id");
				Integer childID = rs.getInt("child_id");
				schemaElement = new Subtype(id,parentID,childID,base);
			}

			// Gets the specified alias
			else if(type.equals("alias"))
			{
				rs = stmt.executeQuery("SELECT id,name,element_id FROM alias WHERE id="+schemaElementID);
				rs.next();
				schemaElement = new Alias(rs.getInt("id"),rs.getString("name"),rs.getInt("element_id"),base);
			}

			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getSchemaElement: "+e.getMessage()); }
		return schemaElement;
	}

	/** Adds a schema element to the specified schema */
	private void insertSchemaElement(Statement stmt, SchemaElement schemaElement) throws SQLException
	{
		// Retrieve the schema element name, description, and base ID
		Integer id = schemaElement.getId();
		String name = scrub(schemaElement.getName(),100);
		String description = scrub(schemaElement.getDescription(),4096);
		Integer baseID = schemaElement.getBase();

		// Inserts an entity
		if(schemaElement instanceof Entity)
			stmt.addBatch("INSERT INTO entity(id,name,description,schema_id) VALUES("+id+",'"+name+"','"+description+"',"+baseID+")");

		// Inserts an attribute
		if(schemaElement instanceof Attribute)
		{
			Attribute attribute = (Attribute)schemaElement;
			stmt.addBatch("INSERT INTO attribute(id,name,description,entity_id,domain_id,\"min\",\"max\",\"key\",schema_id) VALUES("+id+",'"+name+"','"+description+"',"+attribute.getEntityID()+","+attribute.getDomainID()+","+attribute.getMin()+","+attribute.getMax()+",'"+(attribute.isKey()?"t":"f")+"',"+baseID+")");
		}

		// Inserts a domain
		if(schemaElement instanceof Domain)
			stmt.addBatch("INSERT INTO \"domain\"(id,name,description,schema_id) VALUES("+id+",'"+name+"','"+description+"',"+baseID+")");

		// Inserts a domain value
		if(schemaElement instanceof DomainValue)
		{
			DomainValue domainValue = (DomainValue)schemaElement;
			stmt.addBatch("INSERT INTO domainvalue(id,value,description,domain_id,schema_id) VALUES("+id+",'"+name+"','"+description+"',"+domainValue.getDomainID()+","+baseID+")");
		}

		// Inserts a relationship
		if(schemaElement instanceof Relationship)
		{
			Relationship relationship = (Relationship)schemaElement;
			stmt.addBatch("INSERT INTO relationship(id,name,left_id,left_min,left_max,right_id,right_min,right_max,schema_id) VALUES("+id+",'"+name+"',"+relationship.getLeftID()+","+relationship.getLeftMin()+","+relationship.getLeftMax()+","+relationship.getRightID()+","+relationship.getRightMin()+","+relationship.getRightMax()+","+baseID+")");
		}

		// Inserts a containment relationship
		if(schemaElement instanceof Containment)
		{
			Containment containment = (Containment)schemaElement;
			stmt.addBatch("INSERT INTO containment(id,name,description,parent_id,child_id,\"min\",\"max\",schema_id) VALUES("+id+",'"+name+"','"+description+"',"+containment.getParentID()+","+containment.getChildID()+","+containment.getMin()+","+containment.getMax()+","+baseID+")");
		}

		// Inserts a subset relationship
		if(schemaElement instanceof Subtype)
		{
			Subtype subtype = (Subtype)schemaElement;
			stmt.addBatch("INSERT INTO subtype(id,parent_id,child_id,schema_id) VALUES("+id+","+subtype.getParentID()+","+subtype.getChildID()+","+baseID+")");
		}

		// Inserts an alias
		if(schemaElement instanceof Alias)
		{
			Alias alias = (Alias)schemaElement;
			stmt.addBatch("INSERT INTO alias(id,name,element_id,schema_id) VALUES("+id+",'"+name+"',"+alias.getElementID()+","+baseID+")");
		}
	}

	/** Adds a schema element to the specified schema */
	public Integer addSchemaElement(SchemaElement schemaElement)
	{
		Integer schemaElementID = 0;
		try {
			Statement stmt = connection.getStatement();
			schemaElementID = getUniversalIDs(1);
			schemaElement.setId(schemaElementID);
			insertSchemaElement(stmt,schemaElement);
			stmt.executeBatch();
			stmt.close();
			connection.commit();
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			schemaElementID = 0;
			System.out.println("(E) Database:addSchemaElement: "+e.getMessage());
		}
		return schemaElementID;
	}

	/** Adds the schema elements to the specified schema */
	public boolean addSchemaElements(ArrayList<SchemaElement> schemaElements)
	{
		try {
			Statement stmt = connection.getStatement();
			for(int i=0; i<schemaElements.size(); i++)
			{
				insertSchemaElement(stmt,schemaElements.get(i));
				if(i%1000==999) stmt.executeBatch();
			}
			stmt.executeBatch();
			stmt.close();
			connection.commit();
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:addSchemaElements: "+e.getMessage());
			return false;
		}
		return true;
	}

	/** Updates the specified schema element */
	public boolean updateSchemaElement(SchemaElement schemaElement)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();

			// Retrieve the schema element name, description, and base ID
			String name = scrub(schemaElement.getName(),100);
			String description = scrub(schemaElement.getDescription(),4096);

			// Updates an entity
			if(schemaElement instanceof Entity)
			{
				Entity entity = (Entity)schemaElement;
				stmt.executeUpdate("UPDATE entity SET name='"+name+"', description='"+description+"' WHERE id="+entity.getId());
			}

			// Updates an attribute
			if(schemaElement instanceof Attribute)
			{
				Attribute attribute = (Attribute)schemaElement;
				stmt.executeUpdate("UPDATE attribute SET name='"+name+"', description='"+description+"', entity_id="+attribute.getEntityID()+", domain_id="+attribute.getDomainID()+", min="+attribute.getMin()+", max="+attribute.getMax()+" WHERE id="+attribute.getId());
			}

			// Updates a domain
			if(schemaElement instanceof Domain)
			{
				Domain domain = (Domain)schemaElement;
				stmt.executeUpdate("UPDATE \"domain\" SET name='"+name+"', description='"+description+"' WHERE id="+domain.getId());
			}

			// Updates an domain value
			if(schemaElement instanceof DomainValue)
			{
				DomainValue domainValue = (DomainValue)schemaElement;
				stmt.executeUpdate("UPDATE domainvalue SET value='"+name+"', description='"+description+"', domain_id="+domainValue.getDomainID()+" WHERE id="+domainValue.getId());
			}

			// Updates a relationship
			if(schemaElement instanceof Relationship)
			{
				Relationship relationship = (Relationship)schemaElement;
				stmt.executeUpdate("UPDATE relationship SET name='"+name+"', left_id="+relationship.getLeftID()+", left_min="+relationship.getLeftMin()+", left_max="+relationship.getLeftMax()+", right_id="+relationship.getRightID()+", right_min="+relationship.getRightMin()+", right_max="+relationship.getRightMax()+" WHERE id="+relationship.getId());
			}

			// Updates a containment relationship
			if(schemaElement instanceof Containment)
			{
				Containment containment = (Containment)schemaElement;
				stmt.executeUpdate("UPDATE containment SET name='"+name+"', description='"+description+"' parent_id="+containment.getParentID()+", child_id="+containment.getChildID()+", \"min\"="+containment.getMin()+", \"max\"="+containment.getMax()+" WHERE id="+containment.getId());
			}

			// Updates a subset relationship
			if(schemaElement instanceof Subtype)
			{
				Subtype subtype = (Subtype)schemaElement;
				stmt.executeUpdate("UPDATE subtype SET parent_id="+name+", child_id="+subtype.getChildID()+" WHERE id="+subtype.getId());
			}

			// Updates an alias
			if(schemaElement instanceof Alias)
			{
				Alias alias = (Alias)schemaElement;
				stmt.executeUpdate("UPDATE alias SET name='"+name+"', element_id='"+alias.getElementID()+"' WHERE id="+alias.getId());
			}

			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:updateSchemaElement: "+e.getMessage());
		}
		return success;
	}

	/** Deletes the specified schema element */
	public boolean deleteSchemaElement(int schemaElementID)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();

			// Determine the element type
			String type = null;
			ResultSet rs = stmt.executeQuery("SELECT \"type\" FROM schema_elements WHERE id="+schemaElementID);
			if(rs.next()) type = rs.getString("type");
			if(type.equals("domain")) type = "\"domain\"";

			// Delete the element
			stmt.executeUpdate("DELETE FROM "+type+" WHERE id="+schemaElementID);
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:deleteSchemaElement: "+e.getMessage());
			return false;
		}
		return success;
	}

	/** Retrieves the list of elements using the specified keyword in the repository */
	public ArrayList<SchemaElement> getSchemaElementsForKeyword(String keyword, ArrayList<Integer> schemaIDs)
	{
		ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>();
		try {
			Statement stmt = connection.getStatement();
			keyword = new String(' ' + keyword + ' ').replaceAll("^ \\*","").replaceAll("\\* $","").replaceAll("\\*","%");
			String nameFilter = "lower(' '||name||' ') LIKE '%"+keyword.toLowerCase()+"%'";
			String valueFilter = "lower(' '||value||' ') LIKE '%"+keyword.toLowerCase()+"%'";
			String descFilter = "lower(' '||description||' ') LIKE '%"+keyword.toLowerCase()+"%'";

			// Generate the base filter
			String baseFilter;
			if(schemaIDs.size()>0)
			{
				baseFilter = "schema_id IN (";
				for(Integer schemaID : schemaIDs)
					baseFilter += schemaID + ",";
				baseFilter = baseFilter.substring(0, baseFilter.length()-1) + ")";
			}
			else baseFilter = "1=1";

			// Gets the schema entities
			ResultSet rs = stmt.executeQuery("SELECT id,name,description,schema_id FROM entity WHERE "+baseFilter+" AND (" + nameFilter + " OR " + descFilter + ")");
			while(rs.next())
				elements.add(new Entity(rs.getInt("id"),rs.getString("name"),rs.getString("description"),rs.getInt("schema_id")));

			// Gets the schema attributes
			rs = stmt.executeQuery("SELECT id,name,description,entity_id,domain_id,\"min\",\"max\",\"key\",schema_id FROM attribute WHERE "+baseFilter+" AND (" + nameFilter + " OR " + descFilter + ")");
			while(rs.next())
			{
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				Integer entityID = rs.getInt("entity_id");
				Integer domainID = rs.getInt("domain_id");
				Integer min = rs.getString("min")==null?null:rs.getInt("min");
				Integer max = rs.getString("max")==null?null:rs.getInt("max");
				Integer schemaID = rs.getInt("schema_id");
				boolean key = rs.getString("key").equals("t");
				elements.add(new Attribute(id,name,description,entityID,domainID,min,max,key,schemaID));
			}

			// Gets the schema domains
			rs = stmt.executeQuery("SELECT id,name,description,schema_id FROM \"domain\" WHERE "+baseFilter+" AND (" + nameFilter + " OR " + descFilter + ")");
			while(rs.next())
				elements.add(new Domain(rs.getInt("id"),rs.getString("name"),rs.getString("description"),rs.getInt("schema_id")));

			// Gets the schema domain values
			rs = stmt.executeQuery("SELECT id,value,description,domain_id,schema_id FROM domainvalue WHERE "+baseFilter+" AND (" + valueFilter + " OR " + descFilter + ")");
			while(rs.next())
				elements.add(new DomainValue(rs.getInt("id"),rs.getString("value"),rs.getString("description"),rs.getInt("domain_id"),rs.getInt("schema_id")));

			// Gets the schema relationships
			rs = stmt.executeQuery("SELECT id,name,left_id,left_min,left_max,right_id,right_min,right_max,schema_id FROM relationship WHERE "+baseFilter+" AND " + nameFilter);
			while(rs.next())
			{
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				Integer leftID = rs.getInt("left_id");
				Integer leftMin = rs.getString("left_min")==null?null:rs.getInt("left_min");
				Integer leftMax = rs.getString("left_max")==null?null:rs.getInt("left_max");
				Integer rightID = rs.getInt("right_id");
				Integer rightMin = rs.getString("right_min")==null?null:rs.getInt("right_min");
				Integer rightMax = rs.getString("right_max")==null?null:rs.getInt("right_max");
				Integer schemaID = rs.getInt("schema_id");
				elements.add(new Relationship(id,name,leftID,leftMin,leftMax,rightID,rightMin,rightMax,schemaID));
			}

			// Gets the schema containment relationships
			rs = stmt.executeQuery("SELECT id,name,description,parent_id,child_id,\"min\",\"max\",schema_id FROM containment WHERE "+baseFilter+" AND (" + nameFilter + " OR " + descFilter + ")");
			while(rs.next())
			{
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				Integer parentID = rs.getInt("parent_id");
				Integer childID = rs.getInt("child_id");
				Integer min = rs.getString("min")==null?null:rs.getInt("min");
				Integer max = rs.getString("max")==null?null:rs.getInt("max");
				Integer schemaID = rs.getInt("schema_id");
				elements.add(new Containment(id,name,description,parentID,childID,min,max,schemaID));
			}

			// Gets the schema aliases
			rs = stmt.executeQuery("SELECT id,name,alias.element_id,schema_id FROM alias WHERE "+baseFilter+" AND " + nameFilter);
			while(rs.next())
				elements.add(new Alias(rs.getInt("id"),rs.getString("name"),rs.getInt("element_id"),rs.getInt("schema_id")));

			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getSchemaElementsForKeyword: "+e.getMessage()); }
		return elements;
	}

	//--------------------------------------
	// Handles Data Sources in the Database
	//--------------------------------------

	/** Retrieve a list of data sources for the specified schema (or all if no schemaID given) */
	public ArrayList<DataSource> getDataSources(Integer schemaID)
	{
		ArrayList<DataSource> dataSources = new ArrayList<DataSource>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id,name,schema_id,url FROM data_source" + (schemaID!=null?" WHERE schema_id="+schemaID:""));
			while(rs.next())
				dataSources.add(new DataSource(rs.getInt("id"),rs.getString("name"),rs.getInt("schema_id"),rs.getString("url")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getDataSources - "+e.getMessage()); }
		return dataSources;
	}

	/** Retrieve the specified data source */
	public DataSource getDataSource(Integer dataSourceID)
	{
		DataSource dataSource = null;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id,name,schema_id,url FROM data_source WHERE id="+dataSourceID);
			if(rs.next())
				dataSource = new DataSource(rs.getInt("id"),rs.getString("name"),rs.getInt("schema_id"),rs.getString("url"));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getDataSource - "+e.getMessage()); }
		return dataSource;
	}

	/** Retrieve the specified data source based on the specified URL */
	public DataSource getDataSourceByURL(String url)
	{
		DataSource dataSource = null;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id,name,schema_id,url FROM data_source WHERE url='"+url+"'");
			if(rs.next())
				dataSource = new DataSource(rs.getInt("id"),rs.getString("name"),rs.getInt("schema_id"),rs.getString("url"));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getDataSourceByURL - "+e.getMessage()); }
		return dataSource;
	}

	/** Adds a data source to the specified schema */
	public Integer addDataSource(DataSource dataSource)
	{
		Integer dataSourceID = 0;
		try {
			dataSourceID = getUniversalIDs(1);
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("INSERT INTO data_source(id,name,url,schema_id) VALUES("+dataSourceID+",'"+scrub(dataSource.getName(),100)+"','"+scrub(dataSource.getUrl(),200)+"',"+dataSource.getSchemaID()+")");
			stmt.close();
			connection.commit();
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			dataSourceID = 0;
			System.out.println("(E) Database:addDataSource - "+e.getMessage());
		}
		return dataSourceID;
	}

	/** Updates the specified dataSource */
	public boolean updateDataSource(DataSource dataSource)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("UPDATE data_source SET name='"+scrub(dataSource.getName(),100)+"', url='"+scrub(dataSource.getUrl(),200)+"' WHERE id="+dataSource.getId());
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:updateDataSource - "+e.getMessage());
		}
		return success;
	}

	/** Deletes the specified dataSource */
	public boolean deleteDataSource(int dataSourceID)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("DELETE FROM data_source WHERE id="+dataSourceID);
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:deleteDataSource - "+e.getMessage());
		}
		return success;
	}

	//----------------------------------
	// Handles Projects in the Database
	//----------------------------------

	/** Retrieves the list of projects in the repository */
	public ArrayList<Project> getProjects()
	{
		ArrayList<Project> projects = new ArrayList<Project>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id FROM project");
			while(rs.next())
				projects.add(getProject(rs.getInt("id")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getProjects: "+e.getMessage()); }
		return projects;
	}

	/** Retrieves the specified project from the repository */
	public Project getProject(Integer projectID)
	{
		Project project = null;
		try {
			Statement stmt = connection.getStatement();

			// Get the schemas associated with project
			ArrayList<ProjectSchema> schemas = new ArrayList<ProjectSchema>();
			ResultSet rs = stmt.executeQuery("SELECT schema_id,name,model FROM project_schema,\"schema\" WHERE project_id="+projectID+" AND schema_id=id");
			while(rs.next())
				schemas.add(new ProjectSchema(rs.getInt("schema_id"),rs.getString("name"),rs.getString("model")));
			
			// Get the project information
			ResultSet rs2 = stmt.executeQuery("SELECT name,description,author FROM project WHERE id="+projectID);
			if(rs2.next())
				project = new Project(projectID,rs2.getString("name"),rs2.getString("description"),rs2.getString("author"),schemas.toArray(new ProjectSchema[0]));

			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getProject: "+e.getMessage()); }
		return project;
	}

	/** Retrieves the list of projects associated with the specified schema in the repository */
	public ArrayList<Integer> getSchemaProjectIDs(Integer schemaID)
	{
		ArrayList<Integer> projectIDs = new ArrayList<Integer>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT project_id FROM project_schema WHERE schema_id="+schemaID);
			while(rs.next())
				projectIDs.add(rs.getInt("project_id"));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getSchemaProjects: "+e.getMessage()); }
		return projectIDs;
	}

	/** Adds the specified project */
	public Integer addProject(Project project)
	{
		Integer projectID = 0;
		try {
			projectID = getUniversalIDs(1);
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("INSERT INTO project(id,name,description,author) VALUES("+projectID+",'"+scrub(project.getName(),100)+"','"+scrub(project.getDescription(),4096)+"','"+scrub(project.getAuthor(),100)+"')");
			for(ProjectSchema schema : project.getSchemas())
				stmt.executeUpdate("INSERT INTO project_schema(project_id,schema_id,model) VALUES("+projectID+","+schema.getId()+",'"+schema.getModel()+"')");
			stmt.close();
			connection.commit();
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			projectID = 0;
			System.out.println("(E) Database:addProject: "+e.getMessage());
		}
		return projectID;
	}

	/** Updates the specified project */
	public boolean updateProject(Project project)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("UPDATE project SET name='"+scrub(project.getName(),100)+"', description='"+scrub(project.getDescription(),4096)+"', author='"+scrub(project.getAuthor(),100)+"' WHERE id="+project.getId());
			stmt.executeUpdate("DELETE FROM project_schema WHERE project_id="+project.getId());
			for(ProjectSchema schema : project.getSchemas())
				stmt.executeUpdate("INSERT INTO project_schema(project_id,schema_id,model) VALUES("+project.getId()+","+schema.getId()+",'"+schema.getModel()+"')");
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:updateProject: "+e.getMessage());
		}
		return success;
	}

	/** Deletes the specified project */
	public boolean deleteProject(int projectID)
	{
		boolean success = false;
		try {
			// Delete all mappings associated with the project
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id FROM mapping WHERE project_id="+projectID);
			while(rs.next())
				deleteMapping(rs.getInt("id"));
			
			// Delete the project
			stmt.executeUpdate("DELETE FROM project_schema WHERE project_id="+projectID);
			stmt.executeUpdate("DELETE FROM project WHERE id="+projectID);
			stmt.close();

			// Commit the deletion of the project
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:deleteProject: "+e.getMessage());
		}
		return success;
	}

	//----------------------------------
	// Handles Mappings in the Database
	//----------------------------------

	/** Retrieves the list of mappings for the specified project ID */
	public ArrayList<Mapping> getMappings(Integer projectID)
	{
		ArrayList<Mapping> mappings = new ArrayList<Mapping>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id,source_id,target_id FROM mapping WHERE project_id="+projectID);
			while(rs.next())
				mappings.add(new Mapping(rs.getInt("id"),projectID,rs.getInt("source_id"),rs.getInt("target_id")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getMappings: "+e.getMessage()); }
		return mappings;		
	}

	/** Retrieves the specified mapping */
	public Mapping getMapping(Integer mappingID)
	{
		Mapping mapping = null;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT project_id,source_id,target_id FROM mapping WHERE id="+mappingID);
			if(rs.next())
				mapping = new Mapping(mappingID,rs.getInt("project_id"),rs.getInt("source_id"),rs.getInt("target_id"));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getMapping: "+e.getMessage()); }
		return mapping;
	}
	
	/** Adds the specified mapping */
	public Integer addMapping(Mapping mapping)
	{
		Integer mappingID = 0;
		try {
			mappingID = getUniversalIDs(1);
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("INSERT INTO mapping(id,project_id,source_id,target_id) VALUES("+mappingID+","+mapping.getProjectId()+","+mapping.getSourceId()+","+mapping.getTargetId()+")");
			stmt.close();
			connection.commit();
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			mappingID = 0;
			System.out.println("(E) Database:addMapping: "+e.getMessage());
		}
		return mappingID;
	}

	/** Deletes the specified mapping */
	public boolean deleteMapping(int mappingID)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("DELETE FROM proposed_mapping_cell WHERE mapping_id="+mappingID);
			stmt.executeUpdate("DELETE FROM mapping_input WHERE cell_id IN (SELECT id FROM validated_mapping_cell WHERE mapping_id="+mappingID+")");
			stmt.executeUpdate("DELETE FROM validated_mapping_cell WHERE mapping_id="+mappingID);
			stmt.executeUpdate("DELETE FROM mapping WHERE id="+mappingID);
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:deleteMapping: "+e.getMessage());
		}
		return success;
	}	
	
	//---------------------------------------
	// Handles Mapping Cells in the Database
	//---------------------------------------

	/** Retrieves the list of mapping cells in the repository for the specified mapping */
	public ArrayList<MappingCell> getMappingCells(Integer mappingID)
	{
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		try {
			Statement stmt = connection.getStatement();
            Statement stmt2 = connection.getStatement();

			// Retrieve proposed cells
			ResultSet rs = stmt.executeQuery("SELECT id,input_id,output_id,score,author,modification_date,notes FROM proposed_mapping_cell WHERE mapping_id="+mappingID);
			while(rs.next())
				mappingCells.add( MappingCell.createProposedMappingCell(rs.getInt("id"),mappingID,rs.getInt("input_id"),rs.getInt("output_id"),rs.getDouble("score"),rs.getString("author"),rs.getDate("modification_date"),rs.getString("notes")));
            rs.close();

            // Retrieve validated cells
            rs = stmt.executeQuery("SELECT validated_mapping_cell.id,output_id,author,modification_date,function_class,notes FROM validated_mapping_cell WHERE mapping_id="+mappingID);
            while(rs.next())
            {
                int cellID = rs.getInt("id");

                // Get the list of mapping inputs
                ArrayList<Integer> inputs = new ArrayList<Integer>();
                ResultSet rs2 = stmt2.executeQuery("SELECT input_id FROM mapping_input WHERE cell_id=" + cellID + " ORDER BY input_order" );
                while(rs2.next()) inputs.add(rs2.getInt("input_id"));
                rs2.close();

                // Store the mapping cell
                mappingCells.add(MappingCell.createValidatedMappingCell(cellID,mappingID,inputs.toArray(new Integer[0]),rs.getInt("output_id"),rs.getString("author"),rs.getDate("modification_date"),rs.getString("function_class"),rs.getString("notes")));
            }
            rs.close();

            // Close the statements
            stmt.close();
            stmt2.close();
		}
		catch(SQLException e) { System.out.println("(E) Database:getMappingCells: "+e.getMessage());}
		return mappingCells;
	}

	/** Adds the specified mapping cell */
	public Integer addMappingCell(MappingCell mappingCell)
		{ return addMappingCell(mappingCell, true); }

	/** Adds the specified mapping cell */
	private Integer addMappingCell(MappingCell mappingCell, boolean commit)
	{
		// Define various insert statements
        String validatedInsert = "INSERT INTO validated_mapping_cell (id, mapping_id, function_class ,output_id, author, modification_date, notes ) values (?,?,?,?,?,?,?)";
        String validatedInputInsert = "INSERT INTO mapping_input ( cell_id, input_id, input_order ) values (?,?,?)";
        String proposedInsert = "INSERT INTO proposed_mapping_cell (id, mapping_id, input_id, output_id, score, author, modification_date, notes ) values (?,?,?,?,?,?,?,?)";

        // Insert the mapping cell
        Integer mappingCellID = 0;
        try {
            mappingCellID = getUniversalIDs(1);
            Date date = new Date(mappingCell.getModificationDate().getTime());
            if( mappingCell.getValidated() )
            {
            	// Stores the validated mapping cell
            	PreparedStatement stmt = connection.getPreparedStatement(validatedInsert);
                int i = 1;
                stmt.setInt(i++, mappingCellID);
                stmt.setInt(i++, mappingCell.getMappingId());
                stmt.setString(i++, mappingCell.getFunctionClass());
                stmt.setInt(i++, mappingCell.getOutput());
                stmt.setString(i++, scrub(mappingCell.getAuthor(),100));
                stmt.setDate(i++, date);
                stmt.setString(i++, scrub(mappingCell.getNotes(),4096));
                stmt.executeUpdate();
                stmt.close();

                // Stores the validated mapping cell inputs
                stmt = connection.getPreparedStatement(validatedInputInsert);
                Integer[] inputs = mappingCell.getInput();
                i = 1;
                for(Integer input_id : inputs)
                {
                    stmt.setInt(1, mappingCellID);
                    stmt.setInt(2, input_id);
                    stmt.setInt(3, i++);
                    stmt.executeUpdate();
                }
                stmt.close();
            }
            
            // Store proposed mapping cells
            else
            {
                PreparedStatement stmt = connection.getPreparedStatement(proposedInsert);
                int i = 1;
                stmt.setInt(i++, mappingCellID);
                stmt.setInt(i++, mappingCell.getMappingId());
                stmt.setInt(i++, mappingCell.getInput()[0]);
                stmt.setInt(i++, mappingCell.getOutput());
                stmt.setDouble(i++, mappingCell.getScore());
                stmt.setString(i++, scrub(mappingCell.getAuthor(),100));
                stmt.setDate(i++, date);
                stmt.setString(i++, scrub(mappingCell.getNotes(),4096));
                stmt.executeUpdate();
                stmt.close();
            }

            // Commit the mapping cell to the database
            if(commit) connection.commit();
        }
        catch(SQLException e)
        {
            try { connection.rollback(); } catch(SQLException e2) {}
            mappingCellID = 0;
            System.out.println("(E) Database:addMappingCell: "+e.getMessage());
        }

		return mappingCellID;
	}

	/** Updates the specified mapping cell */
	public boolean updateMappingCell(MappingCell mappingCell)
	{
		boolean success = true;
		try {
            success = success && deleteMappingCell( mappingCell.getId(), false );
            success = success && (!addMappingCell( mappingCell, false ).equals(Integer.valueOf(0)));
			if (success) connection.commit();
            else { connection.rollback(); return false; }
        }
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:updateMappingCell: "+e.getMessage());
            return false;
		}
        return true;
	}

	/** Deletes the specified mapping cell */
	public boolean deleteMappingCell(int mappingCellID)
		{ return deleteMappingCell(mappingCellID, true); }

    /** Deletes the specified mapping cell */
	private boolean deleteMappingCell(int mappingCellID, boolean commit)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
            //delete it wherever it may be
            stmt.executeUpdate("DELETE FROM proposed_mapping_cell WHERE id="+mappingCellID);
            stmt.executeUpdate("DELETE FROM mapping_input WHERE cell_id="+mappingCellID);
			stmt.executeUpdate("DELETE FROM validated_mapping_cell WHERE id="+mappingCellID);
			stmt.close();
            if(commit) connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:deleteMappingCell: "+e.getMessage());
		}
		return success;
	}

	//-------------------------------------
	// Handles Annotations in the Database
	//-------------------------------------

	/** Sets the specified annotation in the database */
	public boolean setAnnotation(int elementID, String attribute, String value)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("DELETE FROM annotation WHERE element_id="+elementID+" AND attribute='"+attribute+"'");
			stmt.executeUpdate("INSERT INTO annotation(element_id,attribute,value) VALUES("+elementID+",'"+attribute+"','"+value+"')");
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:setAnnotation: "+e.getMessage());
		}
		return success;
	}

	/** Gets the specified annotation in the database */
	public String getAnnotation(int elementID, String attribute)
	{
		String value = null;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT value FROM annotation WHERE element_id="+elementID+" AND attribute='"+attribute+"'");
			if(rs.next()) value = rs.getString("value");
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getAnnotation: "+e.getMessage()); }
		return value;
	}
}