package org.mitre.schemastore.porters.schemaExporters;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.porters.schemaExporters.sql.DomainTable;
import org.mitre.schemastore.porters.schemaExporters.sql.ForeignKey;
import org.mitre.schemastore.porters.schemaExporters.sql.NoRelationFoundException;
import org.mitre.schemastore.porters.schemaExporters.sql.Rdb;
import org.mitre.schemastore.porters.schemaExporters.sql.RdbAttribute;
import org.mitre.schemastore.porters.schemaExporters.sql.RdbValueType;
import org.mitre.schemastore.porters.schemaExporters.sql.SQLWriter;
import org.mitre.schemastore.porters.schemaExporters.sql.Table;
import org.mitre.schemastore.porters.schemaExporters.sql.View;

/**
 * Caveats: 1. all string fields are mapped to TEXT in the database
 * 
 * @author HAOLI
 * 
 */

public class SQLExporter extends SchemaExporter {

	private HashMap<Integer, Entity> _entities = new HashMap<Integer, Entity>();
	private HashMap<Integer, Attribute> _attributes = new HashMap<Integer, Attribute>();
	private HashMap<Integer, Relationship> _relationships = new HashMap<Integer, Relationship>();
	private HashMap<Integer, Domain> _domains = new HashMap<Integer, Domain>();
	private HashMap<Integer, Subtype> _subtypes = new HashMap<Integer, Subtype>();
	private HashMap<Integer, Containment> _containments = new HashMap<Integer, Containment>();
	private HashMap<Integer, DomainValue> _domainValues = new HashMap<Integer, DomainValue>();
	private Rdb _rdb;

	private String standardizeName(SchemaElement se){
		String name = se.getId() + "-" + se.getName();
		if (se.getName().length() == 0)
			name = se.getId() + "-" + "ENTITY";
		return name;
	}
	
	
	
	/**
	 * maps relationships . create foreign key for 1-to-many relationships. create bridge tables for
	 * many-to-many relationships
	 */
	private void mapRelationships() {
		for (Relationship rel : _relationships.values()) {

			Entity leftEntity = _entities.get(rel.getLeftID());
			Entity rightEntity = _entities.get(rel.getRightID());

			try {
				Table leftTable = _rdb.getRelation(standardizeName(leftEntity));
				Table rightTable = _rdb.getRelation(standardizeName(rightEntity));
				String leftName = leftTable.getName();
				String rightName = rightTable.getName();

				String relName = rel.getName();
				Integer lmin = rel.getLeftMin();
				Integer lmax = rel.getLeftMax();
				Integer rmin = rel.getRightMin();
				Integer rmax = rel.getRightMax();

				System.err.println(rel.getName() + " " + rel.getLeftMin() + ": " + rel.getLeftMax()
						+ " || " + rel.getRightMin() + " : " + rel.getRightMax());

				if (lmax == null || lmax.equals(-1) || lmax > 1) { // L*
					if (rmax == null || rmax.equals(-1) || rmax > 1) {
						// L*->R* create bridge table
						String bridgeTblName = leftName + "_" + relName + "_" + rightName;

						Table bridge = _rdb.createTable(bridgeTblName, false);
						ForeignKey leftKey = _rdb.addForeignKey(bridge, "FROM_" + leftName, leftTable, RdbValueType.FOREIGN_KEY); 
						
						ForeignKey rightKey = _rdb.addForeignKey(bridge, "TO" + leftName, rightTable, RdbValueType.FOREIGN_KEY);
						
						_rdb.addAttribute(bridge, leftKey);
						_rdb.addAttribute(bridge, rightKey);
						bridge.setComment(rel.getDescription());
						
					} else {
						// L*->R1 create Fk in L table ref R.
						ForeignKey fk = _rdb.addForeignKey(leftTable, relName, rightTable, RdbValueType.FOREIGN_KEY);
						leftTable.addAttribute(fk);
						fk.setComment(rel.getDescription());
						fk.setIsRequired(rmin.equals(1));
					}
				} else { // L1
					if (rmax == null || rmax.equals(-1) || rmax > 1) {
						// L1->R* create rFk in Rtable ref Lpk
						ForeignKey fk = _rdb.addForeignKey(rightTable, relName, leftTable, RdbValueType.FOREIGN_KEY); 
						rightTable.addAttribute(fk);
						fk.setComment(rel.getDescription());
						fk.setIsRequired(lmin.equals(1));
					} else { // L1->R1 
						System.out.println("Create L1->R1 relationship");
						ForeignKey fk = _rdb.addForeignKey(leftTable, relName, rightTable, RdbValueType.FOREIGN_KEY);
						leftTable.addAttribute(fk);
						fk.setComment(rel.getDescription());
						fk.setIsRequired(rmin.equals(1));
					}
				}
			} catch (NoRelationFoundException e) {
				System.err.println("(E) while mapping relationship " + rel.getName()
						+ ": Can't find table for entity (" + leftEntity.getName() + " : "
						+ rightEntity.getName() + " ) ");
			//	e.printStackTrace();
			}
		}
	}

	/**
	 * maps containments which include entity to entity containment, entity to domain containments,
	 * and schema to entity containments
	 */
	private void mapContainments() {
		for (Containment c : _containments.values()) {
			Entity parentEntity = _entities.get(c.getParentID());
			Entity childEntity = _entities.get(c.getChildID());
			Domain domain = _domains.get(c.getChildID());

			if (parentEntity != null && childEntity != null)
				mapEntityEntityContainment(c, parentEntity, childEntity);
			 else if (parentEntity != null && domain != null) 
				mapEntityDomainContainment(c, parentEntity, domain);
		}
	}


	/**
	 * Maps containments relationship between an entity and a domain
	 * 
	 * @param c
	 *            containment
	 * @param parent
	 *            containment parent entity
	 * @param domain
	 */
	private void mapEntityDomainContainment(Containment c, Entity parent, Domain child) {
		String attributeName = c.getName();
		if (attributeName.length() == 0){
			attributeName = "Any";
		}
		RdbValueType dbType;
		try {
			dbType = toRdbValueType(child);
			if (c.getMax() == 1) { // 1-1 attribute
				Table relation;
				relation = _rdb.getRelation(standardizeName(parent));
				RdbAttribute att = _rdb.addAttribute(relation, attributeName, dbType, false);
				att.setComment(c.getDescription());
			} else if (c.getMax() == -1) { // 1-many
				String bridgeName = c.getName() + "." + parent.getName();
				Table pTable = _rdb.getRelation(standardizeName(parent));
				Table bridge = _rdb.createTable(bridgeName, false);
				ForeignKey pId = new ForeignKey(_rdb, bridge, "pID", pTable,
						pTable.getPrimaryKey().getName(), RdbValueType.INTEGER);
				RdbAttribute att = _rdb.addAttribute(bridge, attributeName, dbType, false);
				att.setComment(c.getDescription());

				bridge.addAttribute(pId);
				bridge.addAttribute(att);
				bridge.setPrimaryKey(pId);
				bridge.setPrimaryKey(att);
				pId.setIsRequired(true);
				att.setIsRequired(true);
			}
		} catch (NoRelationFoundException e) {
			System.err.println("[E] No relation found for entity: " + parent.getName());
			//e.printStackTrace();

		} catch (Exception e2) {
			System.err.println("[E] No relation found for entity: " + parent.getName());
			//e2.printStackTrace();
		}

	}

	/**
	 * Map all E->E containment to foreign keys in children entities referencing parents regardless
	 * of 1-to-many or 0-to-many or 1-to-1. The foreign key is named after the containment
	 * 
	 */
	private void mapEntityEntityContainment(Containment c, Entity parent, Entity child) {
		
		Table childTable = null, parentTable = null;
		try {
			childTable = _rdb.getRelation(standardizeName(child));
		} catch (NoRelationFoundException e) {
			System.err.println("(E) while mapping containment " + c.getName()+ ", relation for child " + child.getId() + " with name " + childTable + " cannot be found: ");
			return;
		}

		try {
			parentTable = _rdb.getRelation(standardizeName(parent));
		} catch (NoRelationFoundException e) {
			System.err.println("(E) while mapping containment " + c.getName()+ ", relation for parent " + parent.getId() + " with name " + parent.getName() +" cannot be found: ");
			return;
		}

		String fkName = c.getName() + "." + parentTable.getName();
		ForeignKey fk = _rdb.addForeignKey(childTable, fkName, parentTable, RdbValueType.INTEGER);
		fk.setIsRequired(false);
		fk.setComment(c.getDescription());

	}

	/**
	 * create tables for simple types/domains. Domain values are entries to the table
	 */
	private void mapDomains() {
		for (Domain domain : _domains.values()) {
			
			String dName = domain.getName();
			if (!(dName.equalsIgnoreCase("Integer") || dName.equalsIgnoreCase("int")
					|| dName.equalsIgnoreCase("float") || dName.equalsIgnoreCase("Double")
					|| dName.equalsIgnoreCase("decimal") //|| dName.equalsIgnoreCase("Datetime") || dName.equalsIgnoreCase("ID")
					|| dName.equalsIgnoreCase("String") || dName.equalsIgnoreCase("Timestamp")
					|| dName.equalsIgnoreCase("Boolean")  || dName.equalsIgnoreCase("IDREF"))) {

				// importer imports both Any and ANY
				if (dName.equalsIgnoreCase("Any")) dName = "ANY";

				// create a new table for complex domain. Add domain values to table as tuples.
				DomainTable domainTable = _rdb.createDomainTable(domain, true);
				for (DomainValue dv : _domainValues.values()) {
					if (dv.getDomainID().equals(domain.getId())) {
						domainTable.addDomainValue(dv.getName());
					}
				}
			}
		}
	}

	/**
	 * map simple domain to a known DDL type.
	 */
	private RdbValueType toRdbValueType(Domain domain) {
		if (domain.getName().equalsIgnoreCase("Integer")
				|| domain.getName().equalsIgnoreCase("int")) return RdbValueType.INTEGER;
		else if (domain.getName().equalsIgnoreCase("Double")
				|| domain.getName().equalsIgnoreCase("float")
				|| domain.getName().equalsIgnoreCase("decimal")) return RdbValueType.NUMERIC;
	//	else if (domain.getName().equalsIgnoreCase("DateTime")) return RdbValueType.DATETIME;
		else if (domain.getName().equalsIgnoreCase("String")) return RdbValueType.VARCHAR255;
		else if (domain.getName().equalsIgnoreCase("TimeStamp")) return RdbValueType.TIMESTAMP;
		else if (domain.getName().equalsIgnoreCase("Boolean")) return RdbValueType.BOOLEAN;
	//	else if (domain.getName().equalsIgnoreCase("ID")) return RdbValueType.ID;
		else if (domain.getName().equalsIgnoreCase("IDREF")) return RdbValueType.FOREIGN_KEY;
		else {
		// DBURIDCK: For mentioned types that translate to non-base Domains in M3, we have already used
		//		Create Domain to create a domain, so use that type
			return new RdbValueType("\"" + domain.getName() + "\"", String.class);

		}

	}

	/**
	 * Access uses [] to delimit table names or attribute names with spaces or formats. Postgres
	 * uses double quotes. According to Chris, Oracle uses single quotes.
	 * 
	 * Override this function to desensitize the name, wrap it in the correct name delimiters, and
	 * correct length.
	 */

	protected String toDbDelimitedName(String name) {
		name = name.replaceAll("'", "\'");
		name = name.substring(32);
		return "\"" + name + "\"";
	}

	// create a view for every entity. with union of all subtypes
	private void mapSubtypes() {
		for (Entity e : _entities.values()) {
			View v = new View(_rdb, e.getName());
			ArrayList<Entity> children = getChildren(e);
			for (Entity c : children) {
				try {
					v.addToUnionRelation(_rdb.getRelation(standardizeName(c)));
				} catch (NoRelationFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * recursively get all children classes of an entity
	 */
	private ArrayList<Entity> getChildren(Entity e) {
		ArrayList<Entity> children = new ArrayList<Entity>();

		ArrayList<Entity> firstChild = getFirstChildren(e);
		if (firstChild.size() == 0) return children;

		children.addAll(firstChild);
		for (Entity t : firstChild) {
			children.addAll(getChildren(t));
		}
		return children;
	}

	/**
	 * returns first level children of an entity
	 */
	private ArrayList<Entity> getFirstChildren(Entity entity) {
		ArrayList<Entity> children = new ArrayList<Entity>();
		for (Subtype s : _subtypes.values()) {
			if (s.getParentID() == entity.getId()) {
				children.add(_entities.get(s.getChildID()));
			}
		}
		return children;
	}

	private StringBuffer exportDDL() throws IOException {
		SQLWriter ddlWriter = new SQLWriter(_rdb);
		return ddlWriter.serialize();
	}

	private void mapAttirbutes() throws Exception {
		Iterator<Attribute> aItr = _attributes.values().iterator();
		while (aItr.hasNext()) {
			Attribute attribute = aItr.next();

			try {
				Integer entityId = attribute.getEntityID();
				Integer domainId = attribute.getDomainID();
				mapAttribute(attribute, entityId, domainId);

			} catch (NoRelationFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void mapAttribute(Attribute attribute, Integer entityId, Integer domainId)
			throws NoRelationFoundException, Exception {
		// get container entity object from the entities hash by ID
		Entity entity = _entities.get(entityId);
		Domain domain = _domains.get(domainId);
		Table table = _rdb.getRelation(standardizeName(entity));
		

		// add attribute to the table for single cardinality
		// otherwise, create an association table
		if (attribute.getMax() != null && attribute.getMax() == 1) {
			RdbAttribute rdbAttribute = new RdbAttribute(_rdb, table, attribute.getName(), toRdbValueType(domain));
			_rdb.addAttribute(table, rdbAttribute);
			if (attribute.getMin() >= 1) rdbAttribute.setIsRequired(true);
			rdbAttribute.setComment(attribute.getDescription());
		} else {
			Table assocTable = _rdb.createTable(table.getName() + "." + attribute.getName(), false);
			ForeignKey attFk = _rdb.addForeignKey(assocTable, table.getName().substring(0) + "ID", table, RdbValueType.INTEGER);
			RdbAttribute rdbAttribute = new RdbAttribute(_rdb, assocTable, attribute.getName(),
					toRdbValueType(domain));
			_rdb.addAttribute(assocTable, rdbAttribute);
			rdbAttribute.setIsRequired(true);
			attFk.setIsRequired(true);
			assocTable.setPrimaryKey(attFk);
			assocTable.setPrimaryKey(rdbAttribute);

			assocTable.setComment("Association table for multiple attribute named "
					+ attribute.getName() + " contained in " + table.getName());
			attFk.setComment("Foreign key referencing " + table.getPrimaryKey());

		}
	}

	private void mapEntities() {
		for (Entity entity : _entities.values()){
			Table table = _rdb.createTable(standardizeName(entity), true);
			table.setComment(entity.getDescription());
		}
	}

	// cache all schema elements and bucket them into hash maps by their types
	private void initialize(Integer schemaID, ArrayList<SchemaElement> elements)
			throws RemoteException {
		_rdb = new Rdb("");
		_entities.clear(); _attributes.clear(); _containments.clear();
		_subtypes.clear(); _relationships.clear(); _domains.clear();
		_domainValues.clear();
		
		for (SchemaElement e : elements) {
			if (e instanceof Entity) _entities.put(new Integer(e.getId()), (Entity) e);
			else if (e instanceof Attribute) _attributes.put(new Integer(e.getId()), (Attribute) e);
			else if (e instanceof Containment) _containments.put(new Integer(e.getId()), (Containment) e);
			else if (e instanceof Subtype) _subtypes.put(new Integer(e.getId()), (Subtype) e);
			else if (e instanceof Relationship) _relationships.put(new Integer(e.getId()), (Relationship) e);
			else if (e instanceof Domain) _domains.put(new Integer(e.getId()), (Domain) e);
			else if (e instanceof DomainValue) _domainValues.put(new Integer(e.getId()), (DomainValue) e);
		}
	}

	public StringBuffer exportSchema(Integer schemaID, ArrayList<SchemaElement> schemaElements) {
		try {
					
			// load schema and schema elements into memory
			initialize(schemaID, schemaElements);

			// map complex domains to tables
			mapDomains();

			// entities to tables.
			mapEntities();

			// attributes to RdbAttributes
			mapAttirbutes();

			// containments to either RdbAttributes or Foreign Keys
			mapContainments();

			// map relationships
			mapRelationships();

			// a view for every entity (union of subTypes)
			mapSubtypes();

			// write DDL
			return exportDDL();
		} catch (Exception e) {
			e.printStackTrace();
			return new StringBuffer(e.getMessage());
		}
	}

	@Override
	public String getDescription() {
		return "Export schema in SQL format (for PostgreSQL databases).";
	}

	@Override
	public String getFileType() {
		return ".sql";
	}

	@Override
	public String getName() {
		return "SQL Exporter";
	}

}