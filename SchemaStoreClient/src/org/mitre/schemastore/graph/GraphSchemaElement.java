// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.graph;

import org.mitre.schemastore.model.*;
import java.util.*;

public abstract interface GraphSchemaElement {

	abstract public ArrayList<SchemaElement> getParents(); 
	abstract public ArrayList<SchemaElement> getChildren(); 
	
}
