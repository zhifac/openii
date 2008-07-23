// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.importers;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFErrorHandler;
import com.hp.hpl.jena.rdf.model.RDFReader;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * Imports an OWL into the Yggdrasil repository
 * 
 * Caveat:
 * -- The converter doesn't handle multiple imported repository
 * -- Convert object properties with multiple domains and ranges by connecting whatever relationship available
 * -- there are null classes in the ontology. :(  
 * -- Does not handle restrictions
 * 
 * @author HAOLI
 *
 */

public class OWLImporter extends Importer implements RDFErrorHandler
{
	// Defines the various owl domain types
	public static final String FLOAT = "Float";
	public static final String INTEGER = "Int";
	public static final String DATE = "Date";
	public static final String TIME = "Time";

	private OntModel _ontModel;
	private ArrayList<SchemaElement> _schemaElements = new ArrayList<SchemaElement>();

	private HashMap<String, Entity> _entityList = new HashMap<String, Entity>();
	private HashMap<String, Domain> _domainList = new HashMap<String, Domain>();

	/** Returns the importer name */
	public String getName()
		{ return "OWL Importer"; }
	
	/** Returns the importer description */
	public String getDescription()
		{ return "This importer can be used to import schemas from an owl format"; }
	
	/** Returns the importer URI type */
	public Integer getURIType()
		{ return FILE; }
	
	/** Returns the importer URI file types */
	public ArrayList<String> getFileTypes()
	{
		ArrayList<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".owl"); fileTypes.add(".rdf"); fileTypes.add(".rdfs");
		return fileTypes;
	}
	
	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> getSchemaElements(Integer schemaID, String uri) throws ImporterException
	{
		try {
			loadDomains();
			initializeOntModel(uri);
			linearGen();
			return _schemaElements;
		}
		catch(Exception e) { throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); }
	}

	/** Handles the loading of the specified domain */
	private void loadDomain(String name, String description)
	{
		Domain domain = new Domain(nextId(), name, description, 0);
		_schemaElements.add(domain);
		_domainList.put(ANY.toLowerCase(), domain);		
	}
	
	/** Handles the loading of all default domains */
	private void loadDomains()
	{
		loadDomain(ANY, "The Any wildcard domain");
		loadDomain(INTEGER, "The Integer domain");
		loadDomain(FLOAT, "The Float domain");
		loadDomain(STRING, "The String domain");
		loadDomain(BOOLEAN, "The Boolean domain");
		loadDomain(DATETIME, "The DateTime domain");
		loadDomain(DATE, "The Date domain");
		loadDomain(TIME, "The Time domain");
	}

	/** Initializes the ontology model */
	private void initializeOntModel(String uri) throws MalformedURLException, IOException
	{
		// Determine what version of owl to use
		String language = "";
		if(uri.endsWith(".owl")) language = "http://www.w3.org/2002/07/owl#";
		else if(uri.endsWith(".rdf")) language = "http://www.w3.org/2000/01/rdf-schema#";
		else if(uri.endsWith("rdfs")) language = "http://www.w3.org/2000/01/rdf-schema#";
		
		// Create a stream to read from and a model to read into.
		InputStream ontologyStream = new URL(uri).openStream();
		_ontModel = ModelFactory.createOntologyModel(language);

		// Use Jena to read from the stream into the model.
		RDFReader reader = _ontModel.getReader();
		reader.setErrorHandler(this);
		reader.read(_ontModel, ontologyStream, uri);
		ontologyStream.close();
	}
	
	private void linearGen() {
		convertAllClasses();
		convertAllDatatypeProperties();
		convertAllObjectProperties();
		convertAllIsARelationships();
	}

	private void convertAllIsARelationships() {
		// Iterate through all classes
		ExtendedIterator classes = _ontModel.listClasses();
		while (classes.hasNext()) {
			OntClass ontClass = (OntClass) classes.next();

			// stop if ontClass is null
			if (ontClass.getLocalName() == null)
				continue;

			Entity entity = _entityList.get(ontClass.getLocalName());

			// create a subType edge for for each direct parent class
			// DEBUG listSuperClasses returns returns "resource" 
			ExtendedIterator parentClasses = ontClass.listSuperClasses(true);
			while (parentClasses.hasNext()) {
				OntClass parent = (OntClass) parentClasses.next();
				Entity parentEntity = _entityList.get(parent.getLocalName());
				if (parentEntity == null)
					continue;

				System.out.println(ontClass.getLocalName() + " isA -->" + parent.getLocalName());
				Subtype subtype = new Subtype(nextId(), parentEntity.getId(), entity
						.getId(), entity.getBase());
				_schemaElements.add(subtype);
			}
		}
	}

	// convert all datatypeProperties
	private void convertAllDatatypeProperties() {
		ExtendedIterator dataProperties = _ontModel.listDatatypeProperties();
		while (dataProperties.hasNext()) {
			DatatypeProperty dataProp = (DatatypeProperty) dataProperties.next();
			Domain domain = convertRangeToDomain(dataProp);

			// create an attribute for each domain the data property belongs to
			// DEBUG jean doesn't seem to return a valid domain for multiple domains
			ExtendedIterator containerClsItr = dataProp.listDeclaringClasses(true);
			while (containerClsItr.hasNext()){
				OntClass containerCls = (OntClass) containerClsItr.next();
				Entity entity = _entityList.get(containerCls.getLocalName());
				String comment = dataProp.getComment(null);
				if (comment == null)
					comment = "";
				
				if (entity != null) {
					Attribute attribute = new Attribute(nextId(), dataProp.getLocalName(),
							comment, entity.getId(), domain.getId(), null,null,0);
					_schemaElements.add(attribute);
				}
				
				System.out.println( containerCls.getLocalName() + "***  has property --> " + dataProp.getLocalName() );
			}
		}
	}

	// convert all objectProperties
	private void convertAllObjectProperties() {
		ExtendedIterator objProperties = _ontModel.listObjectProperties();
		while (objProperties.hasNext()) {
			ObjectProperty objProp = (ObjectProperty) objProperties.next();
			System.out.println(objProp.getLocalName() + "==>");

			// Iterate through the objProperty's domain classes, create a
			// relationship for each of the range classes
			// DEBUG jena returns null for multiple domains
			ExtendedIterator objPropDomainItr = objProp.listDomain();
			while (objPropDomainItr.hasNext()) {
				// LEFT entity
				OntClass leftOntClass = (OntClass) objPropDomainItr.next();
				if (leftOntClass == null || leftOntClass.getLocalName() == null) {
					System.err.println("\t &&&& no left entity for " + objProp.getLocalName());
					System.err.println( leftOntClass.isClass() );
					continue;
				}
				
				Entity leftEntity = _entityList.get(leftOntClass.getLocalName());
				System.out.println("\tleft Entity for " + objProp.getLocalName() + " = " + leftOntClass.getLocalName());

				// RIGHT entity
				ExtendedIterator objPropRangeItr = objProp.listRange();
				while (objPropRangeItr.hasNext()) {
					OntResource rightOntResource = (OntResource) objPropRangeItr.next();
					if (rightOntResource == null || !rightOntResource.canAs(OntClass.class)) {
						System.out
								.println("\t"
										+ objProp.getLocalName()
										+ " is an objectProperty but its range is not a valid ontClass :( ");
						continue;
					}
					OntClass rightCls = objProp.getRange().asClass();
					Entity rightEntity = _entityList.get(rightOntResource.getLocalName());
					System.out.println("\tright = " + rightCls.getLocalName() + "=>"
							+ rightCls.getLocalName());
					System.out.println("cardinality = " + rightCls.getCardinality(objProp));

					// TODO get cardinality
					Integer leftClsMin = 1;
					Integer leftClsMax = 1;
					Integer rightClsMin = 0;
					Integer rightClsMax = objProp.isFunctionalProperty() ? 1 : 0;

					Relationship rel = new Relationship(nextId(), objProp
							.getLocalName(), leftEntity.getId(), leftClsMin, leftClsMax,
							rightEntity.getId(), rightClsMin, rightClsMax, 0);
					_schemaElements.add(rel);
				}
			}

			// add object properties as ANY data type properties to draw in GUI
			// Domain domain = convertDomain(objProp);
			// OntResource containerCls = objProp.getDomain();
			// Entity entity = _entityList.get(containerCls.getLocalName());
			// Attribute attribute = new Attribute(ImporterUtils.nextId(),
			// objProp.getLocalName(),
			// objProp.getComment(null), entity.getId(), domain.getId(), 0);
			// _schemaElements.add(attribute);
		}
	}

	// convert all classes to entities
	private void convertAllClasses() {
		ExtendedIterator classes = _ontModel.listClasses();
		while (classes.hasNext()) {
			OntClass ontClass = (OntClass) classes.next();

			// 
			if (ontClass.getLocalName() == null)
				continue;

			String comment = ontClass.getComment(null);
			if (comment == null)
				comment = "";

			Entity entity = new Entity(nextId(), ontClass.getLocalName(), comment, 0);
			_entityList.put(entity.getName(), entity);
			_schemaElements.add(entity);

		}
	}

	// converts a data property range to domain. 
	private Domain convertRangeToDomain(OntProperty dataProp) {
		OntResource propType = dataProp.getRange();
		Domain domain = _domainList.get(propType.getLocalName().toLowerCase());
		if (domain == null)
			domain = _domainList.get(ANY.toLowerCase());
		return domain;
	}

	// Handles RDF Errors 
	public void warning(Exception e) {}
	public void error(Exception e) { System.err.println(e.getMessage()); }
	public void fatalError(Exception e) { System.err.println(e.getMessage()); }
}
