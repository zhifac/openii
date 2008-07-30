package org.openii.schemr.importer;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.mitre.flexidata.ygg.importers.Importer;
import org.mitre.flexidata.ygg.importers.ImporterException;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public abstract class LoadSimpleXML extends Importer {

	private int _schemaID;
	protected HashMap<String, Entity> _entities;
	protected HashMap<String, Attribute> _attributes;

	public LoadSimpleXML() {
		super();
	}

	@Override
	public Integer getURIType() {
		return FILE;
	}

	protected ArrayList<SchemaElement> generateSchemaElementList() {
		ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();

		for (Entity e : _entities.values())
			schemaElements.add(e);
		for (Attribute a : _attributes.values())
			schemaElements.add(a);
		return schemaElements;
	}

	@Override
	protected ArrayList<SchemaElement> getSchemaElements(Integer schemaID, URI uri)
			throws ImporterException {
		_schemaID = schemaID;
		_entities = new HashMap<String, Entity>();
		_attributes = new HashMap<String, Attribute>();
		parse(uri);
		return generateSchemaElementList();
	}

	private void parse(URI uri) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputStream is = uri.toURL().openStream();
			Document doc = db.parse(is);

			traverseTree(doc);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected abstract void traverseTree(Node n);

	protected Entity addEntity(String tblName) {
		Entity e = new Entity(nextId(), tblName, "", _schemaID);
		_entities.put(tblName, e);
		return e;
	}

	protected Attribute addAttribute(String name, String desc, int entityID, int domainID) {
		Attribute a = new Attribute(nextId(), name, desc, entityID, domainID, 1, 1, _schemaID);
		_attributes.put(a.getName(), a);
		return a;
	}
	
	
	private static HashMap<String, Integer> _domainIDMap = new HashMap<String, Integer>();
	static {
		_domainIDMap.put(INTEGER, -1);
		_domainIDMap.put(DOUBLE, -2);
		_domainIDMap.put(STRING, -3);
		_domainIDMap.put(DATETIME, -4);
		_domainIDMap.put(BOOLEAN, -5);
		_domainIDMap.put(ANY, -6);
	}
	
	protected int getDomainID(String typeVal) {
		Integer val = _domainIDMap.get(typeVal);
		if (val == null) val = _domainIDMap.get(ANY);
		return val;
	}

}