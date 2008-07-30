package org.openii.schemr.importer;


import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;

import org.mitre.flexidata.ygg.importers.ImporterException;
import org.mitre.schemastore.model.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LoadSimpleXML_ICDE extends LoadSimpleXML {

	static String[] DIRS = new String[] { "inventory/schemas", "invsmall/schemas", "auto/schemas", "realestate/schemas" };
	static String ROOT = "data/icde05data";

	public static void main(String[] args) throws FileNotFoundException, ImporterException {
		

		FileFilter filter = new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().endsWith("xml");
			}
		};

		for (String dirname : DIRS) {
			File schemasDir = new File(ROOT + "/" + dirname);
			File[] schemaList = schemasDir.listFiles(filter);
			if (schemaList == null) {
				throw new FileNotFoundException(schemasDir.toString());
			}
			for (File f : schemaList) {
				LoadSimpleXML_ICDE a = new LoadSimpleXML_ICDE();
				a.importSchema(f.getName(), "", "", f.toURI());
			}
		}
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
		if (n == null) return;
		int type = n.getNodeType();
		
		if (type == Node.DOCUMENT_NODE) {
			_docTopNodeName = n.getFirstChild().getNodeName().trim();
			System.out.println("# found top node: "+_docTopNodeName);
			if ("domain".equals(_docTopNodeName)) {
				_tableTopNodeName = "search";
				_attributeNodeName = "element";
				_attributeName = "vname";
			} else 	if ("search".equals(_docTopNodeName)) {
				_tableTopNodeName = "form";
				_attributeNodeName = "element";
				_attributeName = "vname";
			} else 	if ("database".equals(_docTopNodeName)) {
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
		
		String elemName = (n.getNodeName() == null) ? "" : n.getNodeName().trim();
		if (!elemName.equals(_tableTopNodeName)) throw new IllegalStateException();

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
					tblName = c.getAttributes().getNamedItem("name").getNodeValue().trim();
				} else if ("element".equals(cname)) {
					elementsNode = c;
				}
			}
		}
		if (tblName == null || tblName.equals("") || elementsNode == null) throw new IllegalStateException();

		// create an entity for a table
		Entity tblEntity;
		if (!_entities.containsKey(tblName)) {
			tblEntity = addEntity(tblName);
		} else
			throw new IllegalStateException();
		
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
			System.err.println("WARNING: null attribute name for "+n);
			return; // bad
		}
		nameVal = nameNode.getNodeValue();
		
		if (textNode != null) commentVal = textNode.getNodeValue().trim();
		if (keyNode != null) keyVal = keyNode.getNodeValue().trim();
		if (nullNode != null) nullVal = nullNode.getNodeValue().trim();
		if (typeNode != null) {
			String raw = typeNode.getNodeValue().trim().toLowerCase();

			if (raw.startsWith("decimal") 
					|| raw.equals("real")
					|| raw.equals("float")
					|| raw.equals("money")) {
				typeVal = DOUBLE;
			} else if (raw.startsWith("int")
					|| raw.startsWith("varchar")
					|| raw.startsWith("char")) {
				typeVal = INTEGER;
			} else if (raw.startsWith("date") 
					|| raw.startsWith("time")) {
				typeVal = DATETIME;
			} else if (raw.equals("text") 
					|| raw.equals("string")
					|| raw.startsWith("int")
					|| raw.startsWith("varchar")
					|| raw.startsWith("char")) {
				typeVal = STRING;
			}
		}
		
		addAttribute(nameVal, commentVal, tblEntity.getId(), getDomainID(typeVal));
	}
}
