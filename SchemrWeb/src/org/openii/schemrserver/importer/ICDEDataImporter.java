package org.openii.schemrserver.importer;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;
import org.openii.schemrserver.SchemaUtility;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ICDEDataImporter extends SchemaImporter {

	static String[] DIRS = new String[] { "inventory/schemas",
			"invsmall/schemas", "auto/schemas", "realestate/schemas" };
	static String ROOT = "data/icde05data";

	private HashMap<String, Entity> _entities;
	private HashMap<String, Attribute> _attributes;
	protected ArrayList<SchemaElement> schemaElements;
	
	private static HashMap<String, Domain> domainList = new HashMap<String, Domain>();
	static {
		Domain domain = new Domain(SchemaImporter.nextId(), INTEGER, "The Integer domain", 0);
		domainList.put(INTEGER, domain);
		domain = new Domain(SchemaImporter.nextId(), REAL, "The Real domain", 0);
		domainList.put(REAL, domain);
		domain = new Domain(SchemaImporter.nextId(), STRING, "The String domain", 0);
		domainList.put(STRING, domain);
		domain = new Domain(SchemaImporter.nextId(), DATETIME, "The DateTime domain", 0);
		domainList.put(DATETIME, domain);
		domain = new Domain(SchemaImporter.nextId(), BOOLEAN, "The Boolean domain", 0);
		domainList.put(BOOLEAN, domain);
		domain = new Domain(SchemaImporter.nextId(), ANY, "Generic domain", 0);
		domainList.put(ANY, domain);
	}
	
//	private static HashMap<String, Integer> _domainIDMap = new HashMap<String, Integer>();
//	static {
//		_domainIDMap.put(INTEGER, -1);
//		_domainIDMap.put(DOUBLE, -2);
//		_domainIDMap.put(STRING, -3);
//		_domainIDMap.put(DATETIME, -4);
//		_domainIDMap.put(BOOLEAN, -5);
//		_domainIDMap.put(ANY, -6);
//	}
//	
//	protected int getDomainID(String typeVal) {
//		Integer val = _domainIDMap.get(typeVal);
//		if (val == null) val = _domainIDMap.get(ANY);
//		return val;
//	}
	public static void main(String[] args) {

		FileFilter filter = new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().endsWith("xml");
			}
		};

		for (String dirname : DIRS) {
			File schemasDir = new File(ROOT + "/" + dirname);
			File[] schemaList = schemasDir.listFiles(filter);
			if (schemaList == null) {
				System.exit(-1);
			}
			for (File f : schemaList) {
				ICDEDataImporter importer = new ICDEDataImporter();
				try {
					importer.setClient(SchemaUtility.getCLIENT());
					importer.importSchema(f.getName(), "Various authors", "Schema matching demo data", f.toURI());
				} catch (ImporterException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ICDEDataImporter() {
		super();
	}
	
	@Override
	public String getDescription() {
		return "Imports ICDE 2005 schema matching paper dataset";
	}

	@Override
	public String getName() {
		return "ICDEDatasetImporter";
	}

	private String _tableTopNodeName = null;
	private String _docTopNodeName = null;
	private String _attributeNodeName = null;
	private String _attributeName = null;

	protected void traverseTree(Node n) {
		if (n == null)
			return;
		int type = n.getNodeType();

		if (type == Node.DOCUMENT_NODE) {
			_docTopNodeName = n.getFirstChild().getNodeName().trim();
			if ("domain".equals(_docTopNodeName)) {
				_tableTopNodeName = "search";
				_attributeNodeName = "element";
				_attributeName = "vname";
			} else if ("search".equals(_docTopNodeName)) {
				_tableTopNodeName = "form";
				_attributeNodeName = "element";
				_attributeName = "vname";
			} else if ("database".equals(_docTopNodeName)) {
				_tableTopNodeName = "table";
				_attributeNodeName = "column";
				_attributeName = "name";
			}
		}

		if (type == Node.ELEMENT_NODE) {
			String elemName = (n.getNodeName() == null) ? "" : n.getNodeName().trim();
			if (elemName.equals(_tableTopNodeName)) {
				parseTable(n);
			}
		}
		NodeList nl = n.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			traverseTree(nl.item(i));
		}
	}

	protected void parseTable(Node n) {

		String elemName = (n.getNodeName() == null) ? "" : n.getNodeName()
				.trim();
		if (!elemName.equals(_tableTopNodeName))
			throw new IllegalStateException();

		String tblName = null;
		Node elementsNode = null;
		// look for a sub element "source" with the table name
		NodeList nl = n.getChildNodes();
		if (elemName.equals("table")) {
			NamedNodeMap nnm = n.getAttributes();
			Node nameNode = nnm.getNamedItem("name");
			tblName = nameNode.getNodeValue();
			elementsNode = n;
		} else if (elemName.equals("search") || elemName.equals("form")) {
			for (int i = 0; i < nl.getLength(); i++) {
				Node c = nl.item(i);
				String cname = c.getNodeName();
				if ("source".equals(cname)) {
					tblName = c.getAttributes().getNamedItem("name")
							.getNodeValue().trim();
				} else if ("element".equals(cname)) {
					elementsNode = c;
				}
			}
		}
		if (tblName == null || tblName.equals("") || elementsNode == null)
			throw new IllegalStateException();

		// create an entity for a table
		Entity tblEntity;
		if (!_entities.containsKey(tblName)) {
			tblEntity = new Entity(nextId(), tblName, "", 0);
			_entities.put(tblName, tblEntity);
		} else throw new IllegalStateException();

		NodeList elementsList = elementsNode.getChildNodes();
		for (int i = 0; i < elementsList.getLength(); i++) {
			Node c = elementsList.item(i);
			String cname = c.getNodeName().trim();
			boolean isAttribute = cname.equals(_attributeNodeName);
			if (c.getNodeType() == Node.ELEMENT_NODE && isAttribute) {
				parseAttribute(c, tblEntity);
			}
		}
	}

	private void parseAttribute(Node n, Entity tblEntity) {
		NamedNodeMap nnm = n.getAttributes();
		String nameVal = "";
		String commentVal = "";
		String typeVal = ANY;
		String keyVal = "";
		String nullVal = "NULL";

		Node nameNode = nnm.getNamedItem(_attributeName);
		Node textNode = nnm.getNamedItem("text");
		Node keyNode = nnm.getNamedItem("key");
		Node nullNode = nnm.getNamedItem("null");
		Node typeNode = nnm.getNamedItem("type");

		if (nameNode == null) {
			System.err.println("WARNING: null attribute name for " + n);
			return; // bad
		}
		nameVal = nameNode.getNodeValue();

		if (textNode != null)
			commentVal = textNode.getNodeValue().trim();
		if (keyNode != null)
			keyVal = keyNode.getNodeValue().trim();
		if (nullNode != null)
			nullVal = nullNode.getNodeValue().trim();
		if (typeNode != null) {
			String raw = typeNode.getNodeValue().trim().toLowerCase();

			if (raw.startsWith("decimal") || raw.equals("real")
					|| raw.equals("float") || raw.equals("money")) {
				typeVal = REAL;
			} else if (raw.startsWith("int") || raw.startsWith("varchar")
					|| raw.startsWith("char")) {
				typeVal = INTEGER;
			} else if (raw.startsWith("date") || raw.startsWith("time")) {
				typeVal = DATETIME;
			} else if (raw.equals("text") || raw.equals("string")
					|| raw.startsWith("int") || raw.startsWith("varchar")
					|| raw.startsWith("char")) {
				typeVal = STRING;
			}
		}
		
		int domId = domainList.get(typeVal) == null ? domainList.get(ANY).getId() : domainList.get(typeVal).getId();
		Attribute attribute = new Attribute(nextId(), nameVal, commentVal,
				tblEntity.getId(), domId, null, null, false, 0);
		_attributes.put(nameVal, attribute);
		
	}

	@Override
	protected ArrayList<Integer> generateExtendedSchemaIDs()
			throws ImporterException {
		return new ArrayList<Integer>();
	}

	@Override
	protected ArrayList<SchemaElement> generateSchemaElements()
			throws ImporterException {

		for (Entity e : _entities.values())
			schemaElements.add(e);

		for (Attribute a : _attributes.values())
			schemaElements.add(a);

		return schemaElements;
	}

	@Override
	public Integer getURIType() {
		return FILE;
	}

	@Override
	protected void initializeSchemaStructures() throws ImporterException {
		_entities = new HashMap<String, Entity>();
		_attributes = new HashMap<String, Attribute>();
		schemaElements = new ArrayList<SchemaElement>();
		for (Domain d : domainList.values()) {
			schemaElements.add(d);
		}
		
		this.parse(this.uri);
	}
	
	private void parse(URI uri) {
		System.out.println(this.getClass().getSimpleName() + " parsing " + uri.getPath());

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
	
}
