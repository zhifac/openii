package org.mitre.schemastore.porters.schemaImporters;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.avro.Protocol;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.apache.avro.compiler.idl.Idl;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.ImporterException.ImporterExceptionType;
import org.mitre.schemastore.porters.URIType;

public class AvroImporter extends SchemaImporter {

	// Stores the M3 schema elements (entities, attributes, domain, relationships, etc.) 
		private static Integer _autoInc = 10;
		
		private static Integer nextAutoInc(){
			return _autoInc++;
		}

		protected HashMap<Integer, SchemaElement> _schemaElementsAvro = new HashMap<Integer, SchemaElement>();
		protected HashMap<String, SchemaElement> _reusableSchemaElements = new HashMap<String, SchemaElement>();

		
		/** stores the list of domains seen (used to import elements) **/
		protected HashMap<String,Domain> _domainList = new HashMap<String,Domain>();
		protected List<org.apache.avro.Schema> avroSchemas = new ArrayList<org.apache.avro.Schema>();
		
		private Protocol protocol = null;
		/** testing main **/ 
		public static void main(String[] args) throws URISyntaxException, ImporterException{
			AvroImporter avroImporter = new AvroImporter();
			String basePath = "file:/Users/mgreer/Documents/NetBeansProjects/AvroTest/";
			String protoPath = basePath + "schemas/avrocadavro-leviathan-write-schemas/src/main/avro/LegConformanceSegments.avdl";
	        String avscPath = basePath + "schemas/caasd-avro-schemas-caasd-avro-schemas/src/main/avro/fisb/aerodrome/AerographicalRecord.avsc";
	        String avroPath = basePath + "schemas/twitter.avro";
			Repository repository = null;
			try {
				repository = new Repository(Repository.DERBY,new URI("C:/Temp/"),"schemastore","postgres","postgres");
			} catch (URISyntaxException e2) {
				e2.printStackTrace();
			}		
			try {
				avroImporter.client = new SchemaStoreClient(repository);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
				
			// Initialize the importer
			avroImporter.uri = new URI(protoPath);
			//xsdImporter.uri = new URI("C:/tempSchemas/niem-2.1/niem/domains/maritime/2.1/maritime.xsd");
			avroImporter.initialize();
			Collection<SchemaElement> elems = avroImporter.generateSchemaElements();
			for (SchemaElement elem : elems)
			{
				System.out.println(elem);
			}
			
		}
			
	/* returns the importer URI type */
	@Override
	public URIType getURIType() {

		return URIType.FILE;
	}
    /* returns the importer name */
	@Override
	public String getName() {
		
		return "Avro Schema Importer";
	}

	/* returns the importer description */
	@Override
	public String getDescription() {
		
		return "This importer can be used to import schemas from avro schema files";
	}
	/** Returns the importer URI file types */
	
	public ArrayList<String> getFileTypes() {
		ArrayList<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".avdl");
		fileTypes.add(".avsc");
		fileTypes.add(".avro");
		return fileTypes;
	}
	
	/** Initializes the importer for the specified URI 
	 * @throws ImporterException 
	 * @throws URISyntaxException */
	protected void initialize() throws ImporterException
	{	
		try {

			/** reset the Importer **/
			_schemaElementsAvro = new HashMap<Integer, SchemaElement>();
			_domainList = new HashMap<String, Domain>();
			avroSchemas = new ArrayList<org.apache.avro.Schema>();
			_reusableSchemaElements = new HashMap<String, SchemaElement>();
			
			/** Preset domains and then process this schema **/
			loadDomains();

			/** create DOM tree for main schema **/
			String fileString = uri.toString();
			if (fileString.endsWith(".avdl"))
			{
				Idl idl = new Idl(new File(uri));            
				protocol = idl.CompilationUnit();

				avroSchemas.addAll(protocol.getTypes());
			}
			else if (fileString.endsWith(".avsc"))
			{
				org.apache.avro.Schema.Parser parser = new org.apache.avro.Schema.Parser();
				avroSchemas.add(parser.parse(new File(uri)));
			}
			else if (fileString.endsWith(".avro"))
			{
				DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>();
				DataFileStream<GenericRecord> dataFileReader = new DataFileStream<GenericRecord>(new FileInputStream(new File(uri)),reader);
				avroSchemas.add(dataFileReader.getSchema());

			}
			else {
				throw new ImporterException(ImporterExceptionType.INVALID_URI, "Invalid file type.  Must be .avsc, .avro or .avdl");
			}



		}
		catch(Exception e) { 			
			e.printStackTrace();
			throw new ImporterException(ImporterExceptionType.PARSE_FAILURE,e.getMessage()); 
		}
	}
	/* returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> generateSchemaElements() throws ImporterException {
	    try {

	    	for (org.apache.avro.Schema avroSchema : avroSchemas)
	    	{
	    		if (avroSchema.getType()==Type.RECORD) {
	    		_processRecord(avroSchema, null, "["+ avroSchema.getName() + "]", null, false, false, null);
	    		}
	    		else if (avroSchema.getType()==Type.ENUM){
	    			_addEnum(avroSchema);
	    		}
	    			
	    	}
	    }catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    return new ArrayList<SchemaElement>(_schemaElementsAvro.values());
	    
	}

	protected void _processRecord(org.apache.avro.Schema recordSchema, SchemaElement parent, String containerName, String doc, 
			boolean allowNull, boolean noLimit, Set<String> aliases) throws ImporterException
	{
		SchemaElement entity = _reusableSchemaElements.get(recordSchema.getFullName());

		if (entity == null )
		{
			entity = new Entity(nextAutoInc(), recordSchema.getName(), recordSchema.getDoc(),0);
		
			if (recordSchema.getAliases()!= null)
				for (String alias : recordSchema.getAliases())
				{
					Alias aliasElem = new  Alias(nextAutoInc(), alias, entity.getId(), 0);
					_schemaElementsAvro.put(aliasElem.hashCode(), aliasElem);
				
				}
			_schemaElementsAvro.put(entity.hashCode(), entity);
			_reusableSchemaElements.put(recordSchema.getFullName(), entity);
		
			for (Field field : recordSchema.getFields()){
				_processField(entity, field, false, false);
			}
		}
		_createContainment(entity, parent, containerName, doc, allowNull, noLimit, aliases);
	}
	protected void _createContainment(SchemaElement child, SchemaElement parent, String name, String doc, boolean allowNull, boolean noLimit, Set<String> aliases)
	{
		Containment containment = new Containment(nextAutoInc(), name, doc, parent!=null?parent.getId():0, child.getId(), allowNull?0:1, noLimit?-1:1, 0);
		_schemaElementsAvro.put(containment.hashCode(), containment);
		if (aliases != null)
			for (String alias : aliases)
			{
				Alias aliasElem = new  Alias(nextAutoInc(), alias, containment.getId(), 0);
				_schemaElementsAvro.put(aliasElem.hashCode(), aliasElem);
			}
	}
	protected void _processField(SchemaElement parent, Field schemaField, boolean allowNull, boolean noLimit) throws ImporterException{
		String doc = _processDoc(schemaField);
		_processField(schemaField.schema(), parent, schemaField.name(), doc, allowNull, noLimit, schemaField.aliases());
	}
	protected void _processField(org.apache.avro.Schema schema, SchemaElement parent, String name, 
			String doc,  boolean allowNull, boolean noLimit, Set<String> aliases) throws ImporterException{
		switch(schema.getType()){
		case RECORD : _processRecord(schema, parent, name, doc, allowNull, noLimit, aliases);
		break;
		case UNION:  _processUnion(schema, parent, name, doc, allowNull, noLimit, aliases);
		            break;
		case FIXED:  _processFixed(schema, parent, name, doc, allowNull, noLimit, aliases);
		            break;
		case ENUM: _processEnum(schema, parent, name, doc, allowNull, noLimit, aliases);
		           break; 
		case MAP: _processMap(schema, parent, name, doc, aliases);
					break;
		case ARRAY:  _processArray(schema, parent, name, doc, aliases);
				    break;
		case INT:
		case FLOAT:
		case LONG:
		case DOUBLE:
		case BOOLEAN:
		case BYTES:
		case STRING: _processPrimitiveType(schema, parent, name, doc, allowNull, noLimit, aliases);
		}
	}
	
	protected String _processDoc(Field field)
	{
		return field.doc();
	}

	protected void _processPrimitiveType(org.apache.avro.Schema schema, SchemaElement parent, String name, String doc, boolean allowNull, boolean noLimit, Set<String> aliases) throws ImporterException {
		SchemaElement elem = _domainList.get(schema.getType().name());
		if (elem == null)
		{
			throw new ImporterException(ImporterExceptionType.IMPORT_FAILURE, "Domain value " + schema.getType().name() + " doesn't exist in domain");
		}
			_createContainment(elem, parent, name, doc, allowNull, noLimit, aliases);
	}
	protected void _processUnion(org.apache.avro.Schema schema, SchemaElement parent, String name, String doc, boolean allowNull, boolean noLimit, Set<String> aliases) throws ImporterException
	{
		boolean includesNull = allowNull;
		ArrayList<org.apache.avro.Schema> unionTypes = new ArrayList<org.apache.avro.Schema>();
		for (org.apache.avro.Schema subSchema : schema.getTypes()){
			if (subSchema.getType()== Type.NULL)
			{
				includesNull = true;
			}
			else
			{
				unionTypes.add(subSchema);
			}
		}
		if (unionTypes.size()==1)
		{
			_processField(unionTypes.get(0), parent, name, doc, includesNull, noLimit, aliases);
		}
		else
		{
			Entity unionParent = new Entity(nextAutoInc(), null, null, 0);
			_createContainment(unionParent, parent, name, doc, allowNull, noLimit, aliases);
			_schemaElementsAvro.put(unionParent.hashCode(), unionParent);
			for (org.apache.avro.Schema subSchema : unionTypes) {
				_processField(subSchema, unionParent, "[union]_" + name, doc, includesNull, false, null);
			}
		}
	}

	protected void _processArray(org.apache.avro.Schema schema, SchemaElement parent, String name, String doc, Set<String> aliases) throws ImporterException{
		switch (schema.getElementType().getType()) {
			case ARRAY:
			case MAP: Entity arrayParent = new Entity(nextAutoInc(), null, null, 0);
			        _createContainment(arrayParent, parent, name, doc, true, true, aliases);
			        _schemaElementsAvro.put(arrayParent.hashCode(), arrayParent);
			        _processField(schema.getElementType(), arrayParent, "[array]_" + name, doc, false, false, null);
				break;
			default: _processField(schema.getElementType(), parent, name, doc, true, true, aliases);
		}
	}

	protected void _processFixed(org.apache.avro.Schema schema, SchemaElement parent, String name, String doc, boolean allowNull, boolean noLimit, Set<String> aliases) throws ImporterException{
		String type = "[FIXED_"+ schema.getFixedSize() + "]";
		Domain elem = _domainList.get(type);
		if (elem== null)
		{
			elem = new Domain(nextAutoInc(), type, "The domain of fixed size fields of length " + schema.getFixedSize(), 0);
			_schemaElementsAvro.put(elem.hashCode(), elem);
			_domainList.put(type, elem);
		}
		_createContainment(elem, parent, name, doc, allowNull, noLimit, aliases);
	}

	protected void _processMap(org.apache.avro.Schema schema, SchemaElement parent, String name, String doc, Set<String> aliases) throws ImporterException{
		Entity mapParent = new Entity(nextAutoInc(), null, null, 0);
        _createContainment(mapParent, parent, name, doc, true, true, aliases);
        _schemaElementsAvro.put(mapParent.hashCode(), mapParent);
        _createContainment(_domainList.get(Type.STRING.name()), mapParent, "[map_key]_" + name, doc, false, false, null);
        
        _processField(schema.getValueType(), mapParent, "[map_value]_" + name, doc, false, false, null);
	}
	protected Domain _addEnum(org.apache.avro.Schema schema) {
		Domain dom = _domainList.get(schema.getFullName());
		if (dom == null)
		{
			dom = new Domain(nextAutoInc(), schema.getName(), schema.getDoc()==null || schema.getDoc().isEmpty()?schema.getName():schema.getDoc(), 0 );
			_schemaElementsAvro.put(dom.hashCode(), dom);
			_domainList.put(schema.getFullName(), dom);
			if (schema.getAliases()!= null)
				for (String alias : schema.getAliases())
				{
					Alias aliasElem = new  Alias(nextAutoInc(), alias, dom.getId(), 0);
					_schemaElementsAvro.put(aliasElem.hashCode(), aliasElem);
				
				}
			for (String value : schema.getEnumSymbols()){
				DomainValue val = new DomainValue(nextAutoInc(), value, null, dom.getId(),  0);
				_schemaElementsAvro.put(val.hashCode(), val);
			}
		}
		return dom;
	}
	protected void _processEnum(org.apache.avro.Schema schema, SchemaElement parent, String name, String doc, boolean allowNull, boolean noLimit, Set<String> aliases){
		Domain dom = _addEnum(schema);
		_createContainment(dom, parent, name, doc, allowNull, noLimit, aliases);
	}		
	
/**
 * Function for loading the preset domains into the Schema and into a list
 * for use during Attribute creation
 */
private void loadDomains() {

	Domain domain = new Domain(nextAutoInc(), INTEGER + " ","The integer domain", 0);
	_schemaElementsAvro.put(domain.hashCode(), domain);
	_domainList.put(Type.INT.name(), domain);
	
	domain = new Domain(nextAutoInc(), INTEGER + " ", "The long domain",0);
	_schemaElementsAvro.put(domain.hashCode(), domain);
	_domainList.put(Type.LONG.name(), domain);
	
	domain = new Domain(nextAutoInc(), REAL + " ","The float domain", 0);
	_schemaElementsAvro.put(domain.hashCode(), domain);
	_domainList.put(Type.FLOAT.name(), domain);
	
	domain = new Domain(nextAutoInc(), REAL + " ","The double domain", 0);
	_schemaElementsAvro.put(domain.hashCode(), domain);
	_domainList.put(Type.DOUBLE.name(), domain);
	
	
	domain = new Domain(nextAutoInc(), BOOLEAN + " ", "The boolean domain", 0);
	_schemaElementsAvro.put(domain.hashCode(), domain);
	_domainList.put(Type.BOOLEAN.name(), domain);
	
	domain = new Domain(nextAutoInc(), STRING + " ","The string domain", 0);
	_schemaElementsAvro.put(domain.hashCode(), domain);
	_domainList.put(Type.STRING.name(), domain);
	
	domain = new Domain(nextAutoInc(), "bytes" + " ","The bytes domain", 0);
	_schemaElementsAvro.put(domain.hashCode(), domain);
	_domainList.put(Type.BYTES.name(), domain);
	
	}
}



