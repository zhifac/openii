// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import org.mitre.schemastore.data.Groups;
import org.mitre.schemastore.data.SchemaRelationships;
import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
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
	static private DBConnection connection = new DBConnection();
	
	/** Class for returning extensions */
	static public class Extension
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

	/** Class for returning a schema group */
	static public class SchemaGroup
	{
		/** Stores the schema ID */
		private Integer schemaID;
		
		/** Stores the group ID */
		private Integer groupID;
		
		/** Constructs the schema group */
		private SchemaGroup(Integer schemaID, Integer groupID)
			{ this.schemaID = schemaID;  this.groupID = groupID; }

		/** Returns the schema ID */
		public Integer getSchemaID() { return schemaID; }
		
		/** Returns the group ID */
		public Integer getGroupID() { return groupID; }
	}
	
	/** Retrieves a universal id */
	static private Integer getUniversalID() throws SQLException
	{
		Statement stmt = connection.getStatement();
		stmt.executeUpdate("LOCK TABLE universal_id IN exclusive MODE");
		stmt.executeUpdate("UPDATE universal_id SET id = id+1");
		ResultSet rs = stmt.executeQuery("SELECT id FROM universal_id");
		rs.next();
		Integer universalID = rs.getInt("id");
		stmt.close();
		connection.commit();
		return universalID;
	}
	
	/** Scrub strings to avoid database errors */
	static private String scrub(String word, int length)
	{
		if(word!=null)
		{
			word = word.replace("'","''");
			if(word.length()>length) word = word.substring(0,length);
		}
		return word;
	}
	
	//---------------------------------
	// Handles Schemas in the Database 
	//---------------------------------
	
	/** Retrieves the list of schemas in the repository */
	static public ArrayList<Schema> getSchemas()
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
	static public Schema getSchema(Integer schemaID)
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
	static public Schema extendSchema(Schema schema)
	{
		Schema extendedSchema = null;
		try {
			int schemaID = getUniversalID();
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("INSERT INTO \"schema\"(id,name,author,source,\"type\",description,locked) VALUES("+schemaID+",'"+scrub(schema.getName(),50)+" Extension','"+scrub(schema.getAuthor(),50)+"','"+scrub(schema.getSource(),200)+"','"+scrub(schema.getType(),50)+"','Extension of "+scrub(schema.getName(),487)+"','f')");
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
	static public Integer addSchema(Schema schema)
	{
		Integer schemaID = 0;
		try {
			schemaID = getUniversalID();
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("INSERT INTO \"schema\"(id,name,author,source,\"type\",description,locked) VALUES("+schemaID+",'"+scrub(schema.getName(),50)+"','"+scrub(schema.getAuthor(),50)+"','"+scrub(schema.getSource(),200)+"','"+scrub(schema.getType(),50)+"','"+scrub(schema.getDescription(),500)+"','"+(schema.getLocked()?"t":"f")+"')");
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
	static public boolean updateSchema(Schema schema)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("UPDATE \"schema\" SET name='"+scrub(schema.getName(),50)+"', author='"+scrub(schema.getAuthor(),50)+"', source='"+scrub(schema.getSource(),200)+"', \"type\"='"+scrub(schema.getType(),50)+"', description='"+scrub(schema.getDescription(),500)+"' WHERE id="+schema.getId());
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
	static public ArrayList<Integer> getDeletableSchemas()
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
	static public boolean deleteSchema(int schemaID)
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
			stmt.executeUpdate("DELETE FROM schema_group WHERE schema_id="+schemaID);
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

	/** Returns list of all(element_id, word, syn_id) in the database for the specified schema and its ancestors. */
	static public ArrayList<String> getSynonyms(Integer schemaID)
	{
		ArrayList<String> synonyms = new ArrayList<String>();

		// Cycle through all schemas
		ArrayList<Integer> schemaIDs = SchemaRelationships.getAncestors(schemaID); schemaIDs.add(schemaID);
		for(Integer currSchemaID : schemaIDs)
		{
			// Retrieve all synonyms for the current schema 
			try {
				Statement stmt = connection.getStatement();
				ResultSet rs = stmt.executeQuery("SELECT words.element_id, words.word, syn_id " +
						"FROM schema_elements join (words LEFT OUTER JOIN syns ON words.word = syns.word) " +
						"on schema_elements.id = words.element_id where schema_id=" + currSchemaID);	
				while(rs.next())
					synonyms.add(rs.getInt("element_id") + "," + rs.getString("word") + "," + rs.getInt("syn_id"));
				stmt.close();	
			}
			catch (SQLException e) { System.out.println("(E) Database:getSynonyms: "+e.getMessage()); }
		}
			
		return synonyms;
	}

	/** Locks the specified schema */
	static public boolean lockSchema(int schemaID, boolean locked)
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
	// Handles Schema Groups in the Database 
	//---------------------------------------

	/** Retrieves the schema groups validation number */
	static public Integer getSchemaGroupValidationNumber()
	{
		Integer validationNumber = 0;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT mod(sum((schema_id+1)*(group_id+1)),10000000) AS validation_number FROM schema_group");
			if(rs.next())
				validationNumber = rs.getInt("validation_number");
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getSchemaGroupValidationNumber: "+e.getMessage()); }
		return validationNumber;
	}
	
	/** Retrieves the list of groups */
	static public ArrayList<Group> getGroups()
	{
		ArrayList<Group> groups = new ArrayList<Group>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id,name,parent_id FROM groups");
			while(rs.next())
				groups.add(new Group(rs.getInt("id"),rs.getString("name"),rs.getInt("parent_id")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getGroups: "+e.getMessage()); }
		return groups;
	}

	/** Retrieves the specified group from the repository */
	static public Group getGroup(Integer groupID)
	{
		Group group = null;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id,name,parent_id FROM groups");
			if(rs.next())
				group = new Group(rs.getInt("id"),rs.getString("name"),rs.getInt("parent_id"));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getGroup: "+e.getMessage()); }
		return group;
	}
	
	/** Retrieves the list of subgroups for the specified group */
	static public ArrayList<Group> getSubgroups(Integer groupID)
	{
		ArrayList<Group> groups = new ArrayList<Group>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id,name,parent_id FROM groups WHERE parent_id " + (groupID==null ? " IS NULL" : "= "+groupID));
			while(rs.next())
				groups.add(new Group(rs.getInt("id"),rs.getString("name"),rs.getInt("parent_id")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getGroups: "+e.getMessage()); }
		return groups;
	}
	
	/** Adds the specified group */
	static public Integer addGroup(Group group)
	{
		Integer groupID = 0;
		try {
			groupID = getUniversalID();
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("INSERT INTO groups(id,name,parent_id) VALUES("+groupID+",'"+scrub(group.getName(),50)+"',"+group.getParentId()+")");
			stmt.close();
			connection.commit();
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			groupID = 0;
			System.out.println("(E) Database:addGroup: "+e.getMessage());
		}
		return groupID;
	}
	
	/** Updates the specified group */
	static public Boolean updateGroup(Group group)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("UPDATE groups SET name='"+scrub(group.getName(),50)+"' WHERE id="+group.getId());
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:updateGroup: "+e.getMessage());
		}
		return success;
	}
	
	/** Deletes the specified group */
	static public boolean deleteGroup(int groupID)
	{	
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("DELETE FROM schema_group WHERE group_id="+groupID);
			stmt.executeUpdate("DELETE FROM groups WHERE id="+groupID);
			stmt.close();
			connection.commit();
			success = true;			
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:deleteGroup: "+e.getMessage());
		}
		return success;
	}
	
	/** Retrieve the list of schema groups */
	static public ArrayList<SchemaGroup> getSchemaGroups()
	{
		ArrayList<SchemaGroup> schemaGroups = new ArrayList<SchemaGroup>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT schema_id, group_id FROM schema_group");
			while(rs.next())
				schemaGroups.add(new SchemaGroup(rs.getInt("schema_id"),rs.getInt("group_id")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getSchemaGroups: "+e.getMessage()); }		
		return schemaGroups;
	}
	
	/** Add the group to the specified schema */
	static public Boolean addGroupToSchema(Integer schemaID, Integer groupID)
	{
		Boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT count(*) AS count FROM schema_group WHERE schema_id="+schemaID+" AND group_id="+groupID);
			rs.next();
			if(rs.getInt("count")==0)
				stmt.executeUpdate("INSERT INTO schema_group(schema_id,group_id) VALUES ("+schemaID+","+groupID+")");
			stmt.close();
			connection.commit();
			success = true;			
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:addGroupToSchema: "+e.getMessage());
		}
		return success;
	}
	
	/** Remove the group to the specified schema */
	static public Boolean removeGroupFromSchema(Integer schemaID, Integer groupID)
	{
		Boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("DELETE FROM schema_group WHERE schema_id="+schemaID+" AND group_id="+groupID);
			stmt.close();
			connection.commit();
			success = true;			
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:removeGroupFromSchema: "+e.getMessage());
		}
		return success;
	}

	//----------------------------------------------
	// Handles Schema Relationships in the Database 
	//----------------------------------------------

	/** Retrieves the schema extensions validation number */
	static public Integer getSchemaExtensionsValidationNumber()
	{
		Integer validationNumber = 0;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT mod(sum((schema_id+1)*(base_id+1)),10000000) AS validation_number FROM extensions");
			if(rs.next())
				validationNumber = rs.getInt("validation_number");
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getSchemaExtensionsValidationNumber: "+e.getMessage()); }
		return validationNumber;
	}
	
	/** Retrieves the list of schema extensions */
	static public ArrayList<Extension> getSchemaExtensions()
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
	static public boolean setSchemaParents(Integer schemaID, ArrayList<Integer> parentIDs)
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
	static public ArrayList<Domain> getDefaultDomains()
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
	static public Integer getDefaultDomainCount()
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
	static public ArrayList<SchemaElement> getBaseElements(Integer schemaID)
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

	/** Retrieves the base element count from the repository */
	static public Integer getBaseElementCount(Integer schemaID)
	{
		Integer elementCount = 0;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT count(*) AS count FROM schema_elements WHERE schema_id="+schemaID);
			if(rs.next()) elementCount = rs.getInt("count");
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getBaseElementCount: "+e.getMessage()); }
		return elementCount;
	}
	
	/** Retrieves the requested schema element from the repository */
	static public SchemaElement getSchemaElement(Integer schemaElementID)
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
	static public Integer addSchemaElement(SchemaElement schemaElement)
	{
		Integer schemaElementID = 0;
		try {
			Statement stmt = connection.getStatement();
			schemaElementID = getUniversalID();

			// Retrieve the schema element name, description, and base ID
			String name = scrub(schemaElement.getName(),50);
			String description = scrub(schemaElement.getDescription(),500);
			Integer baseID = schemaElement.getBase();

			// Inserts an entity
			if(schemaElement instanceof Entity)
				stmt.executeUpdate("INSERT INTO entity(id,name,description,schema_id) VALUES("+schemaElementID+",'"+name+"','"+description+"',"+baseID+")");

			// Inserts an attribute
			if(schemaElement instanceof Attribute)
			{
				Attribute attribute = (Attribute)schemaElement;
				stmt.executeUpdate("INSERT INTO attribute(id,name,description,entity_id,domain_id,\"min\",\"max\",\"key\",schema_id) VALUES("+schemaElementID+",'"+name+"','"+description+"',"+attribute.getEntityID()+","+attribute.getDomainID()+","+attribute.getMin()+","+attribute.getMax()+",'"+(attribute.isKey()?"t":"f")+"',"+baseID+")");
			}

			// Inserts a domain
			if(schemaElement instanceof Domain)
				stmt.executeUpdate("INSERT INTO \"domain\"(id,name,description,schema_id) VALUES("+schemaElementID+",'"+name+"','"+description+"',"+baseID+")");

			// Inserts a domain value
			if(schemaElement instanceof DomainValue)
			{
				DomainValue domainValue = (DomainValue)schemaElement;
				stmt.executeUpdate("INSERT INTO domainvalue(id,value,description,domain_id,schema_id) VALUES("+schemaElementID+",'"+name+"','"+description+"',"+domainValue.getDomainID()+","+baseID+")");
			}
			
			// Inserts a relationship
			if(schemaElement instanceof Relationship)
			{
				Relationship relationship = (Relationship)schemaElement;
				stmt.executeUpdate("INSERT INTO relationship(id,name,left_id,left_min,left_max,right_id,right_min,right_max,schema_id) VALUES("+schemaElementID+",'"+name+"',"+relationship.getLeftID()+","+relationship.getLeftMin()+","+relationship.getLeftMax()+","+relationship.getRightID()+","+relationship.getRightMin()+","+relationship.getRightMax()+","+baseID+")");
			}
			
			// Inserts a containment relationship
			if(schemaElement instanceof Containment)
			{
				Containment containment = (Containment)schemaElement;
				stmt.executeUpdate("INSERT INTO containment(id,name,description,parent_id,child_id,\"min\",\"max\",schema_id) VALUES("+schemaElementID+",'"+name+"','"+description+"',"+containment.getParentID()+","+containment.getChildID()+","+containment.getMin()+","+containment.getMax()+","+baseID+")");
			}
			
			// Inserts a subset relationship
			if(schemaElement instanceof Subtype)
			{
				Subtype subtype = (Subtype)schemaElement;
				stmt.executeUpdate("INSERT INTO subtype(id,parent_id,child_id,schema_id) VALUES("+schemaElementID+","+subtype.getParentID()+","+subtype.getChildID()+","+baseID+")");
			}
			
			// Inserts an alias
			if(schemaElement instanceof Alias)
			{
				Alias alias = (Alias)schemaElement;
				stmt.executeUpdate("INSERT INTO alias(id,name,element_id,schema_id) VALUES("+schemaElementID+",'"+name+"',"+alias.getElementID()+","+baseID+")");
			}

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
	
	/** Updates the specified schema element */
	static public boolean updateSchemaElement(SchemaElement schemaElement)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();

			// Retrieve the schema element name, description, and base ID
			String name = scrub(schemaElement.getName(),50);
			String description = scrub(schemaElement.getDescription(),500);
			
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
	static public boolean deleteSchemaElement(int schemaElementID)
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
			stmt.executeUpdate("DELETE FROM "+type+" WHERE element_id="+schemaElementID);
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
	static public ArrayList<SchemaElement> getSchemaElementsForKeyword(String keyword, ArrayList<Integer> groupIDs)
	{
		ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>();
		try {
			Statement stmt = connection.getStatement();
			keyword = new String(' ' + keyword + ' ').replaceAll("^ \\*","").replaceAll("\\* $","").replaceAll("\\*","%");
			String nameFilter = "lower(' '||name||' ') LIKE '%"+keyword.toLowerCase()+"%'";
			String descFilter = "lower(' '||description||' ') LIKE '%"+keyword.toLowerCase()+"%'";

			// Generate the base filter
			String baseFilter;
			if(groupIDs.size()>0)
			{
				baseFilter = "schema_id IN (";
				HashSet<Integer> schemaIDs = new HashSet<Integer>();
				for(Integer groupID : groupIDs)
					schemaIDs.addAll(Groups.getGroupSchemas(groupID));		
				for(Integer schemaID : schemaIDs)
					baseFilter += schemaID + ",";
				baseFilter = baseFilter.substring(0, baseFilter.length()-1) + ")";
			}
			else baseFilter = "1=1";
				
			// Gets the schema entities
			ResultSet rs = stmt.executeQuery("SELECT id,name,description,schema_id FROM entity WHERE "+baseFilter+" AND " + nameFilter + " AND " + descFilter);
			while(rs.next())
				elements.add(new Entity(rs.getInt("id"),rs.getString("name"),rs.getString("description"),rs.getInt("schema_id")));
					
			// Gets the schema attributes
			rs = stmt.executeQuery("SELECT id,name,description,entity_id,domain_id,\"min\",\"max\",\"key\",schema_id FROM attribute WHERE "+baseFilter+" AND " + nameFilter + " AND " + descFilter);
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
			rs = stmt.executeQuery("SELECT id,name,description,schema_id FROM \"domain\" WHERE "+baseFilter+" AND " + nameFilter + " AND " + descFilter);
			while(rs.next())
				elements.add(new Domain(rs.getInt("id"),rs.getString("name"),rs.getString("description"),rs.getInt("schema_id")));

			// Gets the schema domain values
			rs = stmt.executeQuery("SELECT id,value,description,domain_id,schema_id FROM domainvalue WHERE "+baseFilter+" AND " + descFilter);
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
			rs = stmt.executeQuery("SELECT id,name,description,parent_id,child_id,\"min\",\"max\",schema_id FROM containment WHERE "+baseFilter+" AND " + nameFilter + " AND " + descFilter);
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
	static public ArrayList<DataSource> getDataSources(Integer schemaID)
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
	static public DataSource getDataSource(Integer dataSourceID)
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
	static public DataSource getDataSourceByURL(String url)
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
	static public Integer addDataSource(DataSource dataSource)
	{
		Integer dataSourceID = 0;
		try {
			dataSourceID = getUniversalID();
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("INSERT INTO data_source(id,name,url,schema_id) VALUES("+dataSourceID+",'"+scrub(dataSource.getName(),50)+"','"+scrub(dataSource.getUrl(),200)+"',"+dataSource.getSchemaID()+")");
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
	static public boolean updateDataSource(DataSource dataSource)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("UPDATE data_source SET name='"+scrub(dataSource.getName(),50)+"', url='"+scrub(dataSource.getUrl(),200)+"' WHERE id="+dataSource.getId());
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
	static public boolean deleteDataSource(int dataSourceID)
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
	// Handles Mappings in the Database 
	//----------------------------------
	
	/** Retrieves the specified mapping from the repository */
	static public Mapping getMapping(Integer mappingID)
	{
		Mapping mapping = null;
		try {
			Statement stmt = connection.getStatement();

			// Get the schemas associated with mapping
			ArrayList<Integer> schemas = new ArrayList<Integer>();
			ResultSet rs = stmt.executeQuery("SELECT schema_id FROM mapping_schema WHERE mapping_id="+mappingID);
			while(rs.next()) schemas.add(rs.getInt("schema_id"));

			// Get the mapping information
			ResultSet rs2 = stmt.executeQuery("SELECT name,description,author FROM mapping WHERE id="+mappingID);
			if(rs2.next())
				mapping = new Mapping(mappingID,rs2.getString("name"),rs2.getString("description"),rs2.getString("author"),schemas.toArray(new Integer[0]));

			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getMapping: "+e.getMessage()); }
		return mapping;
	}
	
	/** Retrieves the list of mappings in the repository */
	static public ArrayList<Mapping> getMappings()
	{
		ArrayList<Mapping> mappings = new ArrayList<Mapping>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id FROM mapping");
			while(rs.next())
				mappings.add(getMapping(rs.getInt("id")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getMappings: "+e.getMessage()); }
		return mappings;
	}
	
	/** Retrieves the list of mappings associated with the specified schema in the repository */
	static public ArrayList<Integer> getSchemaMappingIDs(Integer schemaID)
	{
		ArrayList<Integer> mappingIDs = new ArrayList<Integer>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT mapping_id FROM mapping_schema WHERE schema_id="+schemaID);
			while(rs.next())
				mappingIDs.add(rs.getInt("mapping_id"));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getSchemaMappings: "+e.getMessage()); }
		return mappingIDs;
	}
	
	/** Adds the specified mapping */
	static public Integer addMapping(Mapping mapping)
	{
		Integer mappingID = 0;
		try {
			mappingID = getUniversalID();
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("INSERT INTO mapping(id,name,description,author) VALUES("+mappingID+",'"+scrub(mapping.getName(),50)+"','"+scrub(mapping.getDescription(),500)+"','"+scrub(mapping.getAuthor(),50)+"')");
			for(Integer schema : mapping.getSchemas())
				stmt.executeUpdate("INSERT INTO mapping_schema(mapping_id,schema_id) VALUES("+mappingID+","+schema+")");
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

	/** Updates the specified mapping */
	static public boolean updateMapping(Mapping mapping)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("UPDATE mapping SET name='"+scrub(mapping.getName(),50)+"', description='"+scrub(mapping.getDescription(),500)+"', author='"+scrub(mapping.getAuthor(),50)+"' WHERE id="+mapping.getId());
			stmt.executeUpdate("DELETE FROM mapping_schema WHERE mapping_id="+mapping.getId());
			for(Integer schema : mapping.getSchemas())
				stmt.executeUpdate("INSERT INTO mapping_schema(mapping_id,schema_id) VALUES("+mapping.getId()+","+schema+")");			
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:updateMapping: "+e.getMessage());
		}
		return success;
	}
	
	/** Deletes the specified mapping */
	static public boolean deleteMapping(int mappingID)
	{	
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("DELETE FROM mapping_cell WHERE mapping_id="+mappingID);
			stmt.executeUpdate("DELETE FROM mapping_schema WHERE mapping_id="+mappingID);
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
	
	/** Retrieves the list of mapping cells in the repository for the specified mapping */
	static public ArrayList<MappingCell> getMappingCells(Integer mappingID)
	{
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT id,element1_id,element2_id,score,scorer,validated FROM mapping_cell WHERE mapping_id="+mappingID);
			while(rs.next())
				mappingCells.add(new MappingCell(rs.getInt("id"),mappingID,rs.getInt("element1_id"),rs.getInt("element2_id"),rs.getDouble("score"),rs.getString("scorer"),rs.getString("validated").equals("t")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getMappingCells: "+e.getMessage()); }
		return mappingCells;
	}

	/** Adds the specified mapping cell */
	static public Integer addMappingCell(MappingCell mappingCell)
	{
		Integer mappingCellID = 0;
		try {
			mappingCellID = getUniversalID();
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("INSERT INTO mapping_cell(id,mapping_id,element1_id,element2_id,score,scorer,validated) VALUES("+mappingCellID+","+mappingCell.getMappingId()+","+mappingCell.getElement1()+","+mappingCell.getElement2()+","+mappingCell.getScore()+",'"+mappingCell.getScorer()+"','"+(mappingCell.getValidated()?"t":"f")+"')");
			stmt.close();
			connection.commit();
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
	static public boolean updateMappingCell(MappingCell mappingCell)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("UPDATE mapping_cell SET score="+mappingCell.getScore()+", scorer='"+mappingCell.getScorer()+"', validated='"+(mappingCell.getValidated()?"t":"f")+"' WHERE id="+mappingCell.getId());
			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:updateMappingCell: "+e.getMessage());
		}
		return success;
	}
	
	/** Deletes the specified mapping cell */
	static public boolean deleteMappingCell(int mappingCellID)
	{	
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			stmt.executeUpdate("DELETE FROM mapping_cell WHERE id="+mappingCellID);
			stmt.close();
			connection.commit();
			success = true;			
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:deleteMappingCell: "+e.getMessage());
		}
		return success;
	}
}