// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.importers;

import java.io.IOException;
import java.net.URI;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import java.util.ArrayList;


public interface Loader
{

    /**
     *  Loads the schema from the URI listed.  This file should end in either "xsd" for an XML schema, or 'sql' for a relational schema.
     */
    public Schema loadSchema(URI schemaLoc) throws IOException;

    /**
     *  Returns the Schema object, suitable for visualization or submission to Yggdrasil, the schema store.
     */
    public Schema getSchema();

    /**
     *  Returns the objects that make up the Schema, suitable for visualization or submission to Yggdrasil, the schema store.
     */
    public ArrayList<SchemaElement> getSchemaElements();


}
