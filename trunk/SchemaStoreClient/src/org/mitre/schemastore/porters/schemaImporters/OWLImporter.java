// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.schemaImporters;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.ImporterException.ImporterExceptionType;
import org.mitre.schemastore.porters.URIType;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.ontology.Profile;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFErrorHandler;
import com.hp.hpl.jena.rdf.model.RDFReader;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.DC;

/**
 * Imports an OWL into the Yggdrasil repository
 * 
 * Caveat: -- The converter doesn't handle multiple imported repository --
 * Convert object properties with multiple domains and ranges by connecting
 * whatever relationship available -- Does not handle restrictions
 * 
 * @author HAOLI
 * 
 */

public class OWLImporter extends SchemaImporter implements RDFErrorHandler {
	// Defines the additional owl domain types
	public static final String FLOAT = "Float";
	public static final String DATE = "Date";
	public static final String TIME = "Time";

	private OntModel _ontModel;
	private ArrayList<SchemaElement> _schemaElements = new ArrayList<SchemaElement>();
	private HashMap<String, Entity> _entityList = new HashMap<String, Entity>();
	private HashMap<String, Domain> _domainList = new HashMap<String, Domain>(); // <owl
																					// domain,
																					// ygg
																					// domain>
	/** testing main **/ 
	public static void main(String[] args) throws URISyntaxException, ImporterException{
		OWLImporter owlImporter = new OWLImporter();
		String basePath = "file:/Users/mgreer/Downloads/";
		String filePath = basePath + "SA-TT_Ontology_20130814.owl";
        Repository repository = null;
		try {
			repository = new Repository(Repository.DERBY,new URI("C:/Temp/"),"schemastore","postgres","postgres");
		} catch (URISyntaxException e2) {
			e2.printStackTrace();
		}		
		try {
			owlImporter.client = new SchemaStoreClient(repository);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
			
		// Initialize the importer
		owlImporter.uri = new URI(filePath);
		//xsdImporter.uri = new URI("C:/tempSchemas/niem-2.1/niem/domains/maritime/2.1/maritime.xsd");
		owlImporter.initialize();
		Collection<SchemaElement> elems = owlImporter.generateSchemaElements();
/*		for (SchemaElement elem : elems)
		{
			System.out.println(elem);
		}*/
		
	}
	
	/** Returns the importer name */
	public String getName() {
		return "OWL Importer";
	}

	/** Returns the importer description */
	public String getDescription() {
		return "This importer can be used to import schemas from an owl format";
	}

	/** Returns the importer URI type */
	public URIType getURIType() {
		return URIType.FILE;
	}

	/** Returns the importer URI file types */
	public ArrayList<String> getFileTypes() {
		ArrayList<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".owl");
		fileTypes.add(".rdf");
		fileTypes.add(".rdfs");
		return fileTypes;
	}

	/** Initializes the importer for the specified URI */
	protected void initialize() throws ImporterException {
		_schemaElements = new ArrayList<SchemaElement>();
		_entityList = new HashMap<String, Entity>();
		_domainList = new HashMap<String, Domain>();

		try {
			loadDomains();
			initializeOntModel(uri);

		} catch (Exception e) {
			throw new ImporterException(ImporterExceptionType.PARSE_FAILURE, e.getMessage());
		}
	}

	@Override
	/** Returns the schema elements from the specified URI */
	protected ArrayList<SchemaElement> generateSchemaElements() throws ImporterException
	{
		linearGen();
		return _schemaElements;
	}

	/** Handles the loading of the specified domain */
	private void loadDomain(String name, String description) {
		Domain domain = new Domain(nextId(), name, description, 0);
		_schemaElements.add(domain);
		_domainList.put(name, domain);
	}

	/** Handles the loading of all default domains */
	private void loadDomains() {
		loadDomain(ANY.toLowerCase(), "The Any wildcard domain");
		loadDomain(INTEGER.toLowerCase(), "The Integer domain");
		loadDomain(FLOAT.toLowerCase(), "The Float domain");
		loadDomain(STRING.toLowerCase(), "The String domain");
		loadDomain(BOOLEAN.toLowerCase(), "The Boolean domain");
		loadDomain(DATETIME.toLowerCase(), "The DateTime domain");
		loadDomain(DATE.toLowerCase(), "The Date domain");
		loadDomain(TIME.toLowerCase(), "The Time domain");
	}

	/** Initializes the ontology model */
	private void initializeOntModel(URI uri) throws MalformedURLException, IOException {
		// Determine what version of owl to use
		String language = "";
		if (uri.toString().endsWith(".owl")) language = "http://www.w3.org/2002/07/owl#";
		else if (uri.toString().endsWith(".rdf")) language = "http://www.w3.org/2000/01/rdf-schema#";
		else if (uri.toString().endsWith(".rdfs")) language = "http://www.w3.org/2000/01/rdf-schema#";

		// Create a stream to read from and a model to read into.
		InputStream ontologyStream = uri.toURL().openStream();
		_ontModel = ModelFactory.createOntologyModel(language);

		// Use Jena to read from the stream into the model.
		RDFReader reader = _ontModel.getReader();
		reader.setErrorHandler(this);
		reader.read(_ontModel, ontologyStream, uri.toString());
		ontologyStream.close();
	}

	private void linearGen() {
		convertClasses();
		convertDatatypeProperties();
		convertObjectProperties();
		convertIsARelationships();
	}

	private void convertIsARelationships() {
		// Iterate through all classes
		ExtendedIterator classes = _ontModel.listClasses();
		while (classes.hasNext()) {
			OntClass ontClass = (OntClass) classes.next();

			// stop if ontClass is null
			if (ontClass.getLocalName() == null) continue;

			// create a subType edge for for each direct parent class
			Entity entity = _entityList.get(ontClass.getLocalName());
			ExtendedIterator subClasses = ontClass.listSubClasses(true);
			while (subClasses.hasNext()) {
				OntClass child = (OntClass) subClasses.next();
				Entity childEntity = _entityList.get(child.getLocalName());
				if (childEntity == null) continue;
//				System.out.println(entity.getName() + ": " + childEntity.getName());
				Subtype subtype = new Subtype(nextId(), entity.getId(), childEntity.getId(), childEntity.getBase());
				_schemaElements.add(subtype);
			}
		}
	}

	// convert all datatypeProperties
	private void convertDatatypeProperties() {
		ExtendedIterator dataProperties = _ontModel.listDatatypeProperties();
		while (dataProperties.hasNext()) {
			DatatypeProperty dataProp = (DatatypeProperty) dataProperties.next();
	/*		if (dataProp.getLocalName().equalsIgnoreCase("hasproblemtype")||dataProp.getLocalName().equalsIgnoreCase("hasproblemcategory")||dataProp.getLocalName().equalsIgnoreCase("hassanotifyid")) {
				System.out.println(dataProp.getLocalName());
				System.out.println(dataProp.toString());
				System.out.println(dataProp.getDomain());
				System.out.println(dataProp.listRange());
			}*/
			Domain domain = convertRangeToDomain(dataProp);

			// create an attribute for each domain the data property belongs to
			// DEBUG jean doesn't seem to return a valid domain for multiple
			// domains
	//		ExtendedIterator containerClsItr = dataProp.listDeclaringClasses(true);
	//		if (!containerClsItr.hasNext()){
		ExtendedIterator		containerClsItr = dataProp.listDomain();
		//	}
			while (containerClsItr.hasNext()) {
				OntClass containerCls = (OntClass) containerClsItr.next();
				Entity entity = _entityList.get(containerCls.getLocalName());
				Statement description = dataProp.getProperty(DC.description);
				String comment = dataProp.getComment(null);
				
				if (description == null){
					if (comment==null) comment= "";
				}
				else{
					comment = description.getString();
				}

				if (entity != null) {
					Attribute attribute = new Attribute(nextId(), dataProp.getLocalName(), comment, entity.getId(), domain.getId(), null, null, false, 0);
					_schemaElements.add(attribute);
				}
			}
		}
	}

	// convert all objectProperties to relationships
	private void convertObjectProperties() {
		ExtendedIterator objProperties = _ontModel.listObjectProperties();
		while (objProperties.hasNext()) {
			ObjectProperty objProp = (ObjectProperty) objProperties.next();

			/*
			 * If an object property has multiple classes defined in its range,
			 * the range is taken as an conjunction of all the classes (as
			 * opposed to independent classes). Jena returns a null named class
			 * for the collection. Here we create a new class that's named by
			 * concatenating all classes names. DEBUG This may need to be
			 * changed to conjunctive relationships
			 */
			ExtendedIterator objPropDomainItr = objProp.listDomain();
			while (objPropDomainItr.hasNext()) {
				// LEFT entity
				OntClass leftOntClass = (OntClass) objPropDomainItr.next();
				if (leftOntClass == null || leftOntClass.getLocalName() == null) {
					System.err.println("\t &&&& null left entity for " + objProp.getLocalName());
					continue;
				}

				Entity leftEntity = _entityList.get(leftOntClass.getLocalName());
        		System.out.println("\tleft Entity for " + objProp.getLocalName() + " = " + leftOntClass.getLocalName());

				// RIGHT entity
				ExtendedIterator objPropRangeItr = objProp.listRange();
				while (objPropRangeItr.hasNext()) {
					OntResource rightOntResource = (OntResource) objPropRangeItr.next();
					if (rightOntResource == null || !rightOntResource.canAs(OntClass.class)) {
						System.out.println("\t" + objProp.getLocalName() + " is an objectProperty but its range is null :( ");
						continue;
					}
					OntClass rightCls = objProp.getRange().asClass();
					Entity rightEntity = _entityList.get(rightOntResource.getLocalName());
					System.out.println("\tright = " + rightCls.getLocalName() + "=>" + rightCls.getLocalName());
					System.out.println("cardinality = " + rightCls.getCardinality(objProp));

					Profile prof = objProp.getProfile();
					int relMin = objProp.getCardinality(prof.MIN_CARDINALITY());
					int relMax = objProp.getCardinality(prof.MAX_CARDINALITY());

					Integer leftClsMin = (relMin > 0) ? relMin : 0;
					Integer leftClsMax = null;
					Integer rightClsMin = null;
					Integer rightClsMax = (relMax > 0) ? relMax : 0;
					Statement statement = objProp.getProperty(DC.description);
					String comment = statement==null?"":statement.getString();
					Relationship rel = new Relationship(nextId(), objProp.getLocalName(), comment, leftEntity.getId(), leftClsMin, leftClsMax, rightEntity.getId(), rightClsMin, rightClsMax, 0);
					_schemaElements.add(rel);
				}
			}
		}
	}

	// convert all classes to entities
	private void convertClasses() {
		ExtendedIterator classes = _ontModel.listClasses();
		while (classes.hasNext()) {
			OntClass ontClass = (OntClass) classes.next();
			if (ontClass.getLocalName() == null) continue;
			String comment = ontClass.getComment(null);
			Statement desc = ontClass.getProperty(DC.description);
			if ( desc == null) {
				if (comment == null) {
					comment = "";
				}
			}
			else comment = desc.getString();

			Entity entity = new Entity(nextId(), ontClass.getLocalName(), comment, 0);
			_entityList.put(entity.getName(), entity);
			_schemaElements.add(entity);

		}
	}

	// converts a data property range to domain.
	private Domain convertRangeToDomain(OntProperty dataProp) {
		OntResource propType = dataProp.getRange();
		if (propType == null || propType.getLocalName() == null) {
			return _domainList.get(ANY.toLowerCase());
		}
		Domain domain = _domainList.get(propType.getLocalName().toLowerCase());
		if (domain == null) domain = _domainList.get(ANY.toLowerCase());
		return domain;
	}

	// Handles RDF Errors
	public void warning(Exception e) {}

	public void error(Exception e) {
		System.err.println(e.getMessage());
	}

	public void fatalError(Exception e) {
		System.err.println(e.getMessage());
	}
}
