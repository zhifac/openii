package org.mitre.schemastore.porters.projectExporters.matchmaker;



public class SchemaElementNode extends Node implements Comparable<SchemaElementNode> {
        public Integer elementId;
        public String elementName;
        public Integer schemaId;
        
        public SchemaElementNode(Integer schemaID, Integer elementId, String elementName) {
                super(elementId.toString());
                this.schemaId = schemaID;
                this.elementId = elementId;
                this.elementName = elementName;
        }

        public int compareTo(SchemaElementNode o) {
                return this.toString().compareToIgnoreCase(o.toString()); 
        }
        
        public String toString(){
                return schemaId + elementName + elementId; 
        }

} // End SchemaElementNode
