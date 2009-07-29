/*
 * Copyright 2009 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package test.org.mitre.schemastore.mapfunctions;

import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.graph.*;
import java.util.*;


public class TestFixtures
{
    /*
     *  returns one statement
     *  "INSERT INTO targetEntity (BMI,Height-cm,peopleID,personName,Weight-kg)  SELECT DISTINCT 1,T0.Height-Inches,T0.personID,T0.lastName,T0.Weight-Pounds FROM sourceEntity AS T0;"
     */
	// public Object[] getFixtureOne() throws Exception
    // {

    //     Graph targetGraph = getTargetGraph();

    //     ArrayList<LogicalRelation> sourceLogRels = LogicalRelation.createLogicalRelations(sourceGraph);
    //     ArrayList<LogicalRelation> targetLogRels = LogicalRelation.createLogicalRelations(targetGraph);
    //     ArrayList<MappingCell> coveredCorrs = new ArrayList<MappingCell>();

    //     coveredCorrs.add( MappingCell.createValidatedMappingCell(1,new Integer(-1),Arrays.asList(s1.getId()).toArray(new Integer[0]),t1.getId(),"Author Q. Tester",new Date(),"org.mitre.schemastore.mapfunctions.IdentityFunction","notes"));
    //     coveredCorrs.add( MappingCell.createValidatedMappingCell(2,new Integer(-1),Arrays.asList(s3.getId()).toArray(new Integer[0]),t2.getId(),"Author Q. Tester",new Date(),"org.mitre.schemastore.mapfunctions.IdentityFunction","notes"));
    //     // coveredCorrs.add( MappingCell.createValidatedMappingCell(3,new Integer(-1),Arrays.asList(s4.getId(), s5.getId()).toArray(new Integer[0]),t3.getId(),"Author Q. Tester",new Date(),"org.mitre.schemastore.mapfunctions.NumericAdd","notes"));
    //     coveredCorrs.add( MappingCell.createValidatedMappingCell(4,new Integer(-1),Arrays.asList(s4.getId()).toArray(new Integer[0]),t3.getId(),"Author Q. Tester",new Date(),"org.mitre.schemastore.mapfunctions.IdentityFunction","notes"));
    //     coveredCorrs.add( MappingCell.createValidatedMappingCell(5,new Integer(-1),Arrays.asList(s5.getId()).toArray(new Integer[0]),t4.getId(),"Author Q. Tester",new Date(),"org.mitre.schemastore.mapfunctions.IdentityFunction","notes"));
    //     ArrayList<Dependency> dependsArrayList = Dependency.generateDependencies(sourceLogRels, targetLogRels, coveredCorrs);

    //     Dependency depend = dependsArrayList.get(0);
    //     Objec        Graph sourceGraph = getSourceGraph();t[] retVals=null;
    //     retVals = SQLGenerator.generateDependencySQLScript(depend, client);
    //     return retVals;
	// }

    public static Graph getSourceGraph()
    {
        ArrayList<SchemaElement> sourceElements = new ArrayList<SchemaElement>();
        Domain stringDom = new Domain(6,"String","",0);
        Domain integerDom = new Domain(7,"Integer","",0);

        Entity sourceEntity = new Entity(8,"sourceEntity","src entity",0);
        sourceElements.add(stringDom);
        sourceElements.add(integerDom);
        sourceElements.add(sourceEntity);
		SchemaElement s1 = new Attribute(9,"personID","",sourceEntity.getId(),stringDom.getId(),1,1,true,0);
		SchemaElement s2 = new Attribute(10,"firstName","",sourceEntity.getId(),stringDom.getId(),1,1,false,0);
		SchemaElement s3 = new Attribute(11,"last name","",sourceEntity.getId(),stringDom.getId(),1,1,false,0);
		SchemaElement s4 = new Attribute(12,"Height-Inches","",sourceEntity.getId(),integerDom.getId(),1,1,false,0);
		SchemaElement s5 = new Attribute(13,"Weight-Pounds","",sourceEntity.getId(),integerDom.getId(),1,1,false,0);        // SchemaElement s1 = client.getSchemaElement(50);  //ConsentStatus
        sourceElements.add(s1);
        sourceElements.add(s2);
        sourceElements.add(s3);
        sourceElements.add(s4);
        sourceElements.add(s5);

        Graph sourceGraph = new Graph(new Schema(14,"sourceSchema","doug","","","",false),sourceElements);
        return sourceGraph;
    }


    public static Graph getTargetGraph()
    {
        ArrayList<SchemaElement> targetElements = new ArrayList<SchemaElement>();
        Domain stringDom = new Domain(15,"String","",0);
        Domain integerDom = new Domain(16,"Integer","",0);

        Entity targetEntity = new Entity(17,"targetEntity","tgt entity",0);
        targetElements.add(stringDom);
        targetElements.add(integerDom);
        targetElements.add(targetEntity);
		SchemaElement t1 = new Attribute(18,"peopleID","",targetEntity.getId(),integerDom.getId(),1,1,true,0);
		SchemaElement t2 = new Attribute(19,"personName","",targetEntity.getId(),stringDom.getId(),1,1,false,0);
		SchemaElement t3 = new Attribute(20,"Height-cm","",targetEntity.getId(),integerDom.getId(),1,1,false,0);
		SchemaElement t4 = new Attribute(21,"Weight-kg","",targetEntity.getId(),integerDom.getId(),1,1,false,0);
		SchemaElement t5 = new Attribute(22,"BMI","",targetEntity.getId(),integerDom.getId(),1,1,false,0);        // SchemaElement t1 = client.getSchemaElement(55);  //Stop
        targetElements.add(t1);
        targetElements.add(t2);
        targetElements.add(t3);
        targetElements.add(t4);
        targetElements.add(t5);

        Graph targetGraph = new Graph(new Schema(23,"targetSchema","doug","","","",false),targetElements);
        return targetGraph;
    }


}
