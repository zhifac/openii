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

import org.mitre.schemastore.mapfunctions.*;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.client.*;
import org.mitre.schemastore.model.graph.*;
import backend.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

public class TestFixturesTest
{

    /*
     *  returns one statement
     *  "INSERT INTO targetEntity (BMI,Height-cm,peopleID,personName,Weight-kg)  SELECT DISTINCT 1,T0.Height-Inches,T0.personID,T0.lastName,T0.Weight-Pounds FROM sourceEntity AS T0;"
     */
	public Object[] getFixtureOne(){
		// create source / target graphs
		ArrayList<SchemaElement> sourceElements = new ArrayList<SchemaElement>();
		ArrayList<SchemaElement> targetElements = new ArrayList<SchemaElement>();

		Domain stringDom = new Domain(LogicalRelation.getNextID(),"String","",0);
		Domain integerDom = new Domain(LogicalRelation.getNextID(),"Integer","",0);
		Entity sourceEntity = new Entity(LogicalRelation.getNextID(),"sourceEntity","src entity",0);

		sourceElements.add(stringDom);
		sourceElements.add(integerDom);
		sourceElements.add(sourceEntity);
		SchemaElement s1 = new Attribute(LogicalRelation.getNextID(),"personID","",sourceEntity.getId(),stringDom.getId(),1,1,true,0);
		SchemaElement s2 = new Attribute(LogicalRelation.getNextID(),"firstName","",sourceEntity.getId(),stringDom.getId(),1,1,false,0);
		SchemaElement s3 = new Attribute(LogicalRelation.getNextID(),"lastName","",sourceEntity.getId(),stringDom.getId(),1,1,false,0);
		SchemaElement s4 = new Attribute(LogicalRelation.getNextID(),"Height-Inches","",sourceEntity.getId(),integerDom.getId(),1,1,false,0);
		SchemaElement s5 = new Attribute(LogicalRelation.getNextID(),"Weight-Pounds","",sourceEntity.getId(),integerDom.getId(),1,1,false,0);
		sourceElements.add(s1);
		sourceElements.add(s2);
		sourceElements.add(s3);
		sourceElements.add(s4);
		sourceElements.add(s5);

		Entity targetEntity = new Entity(LogicalRelation.getNextID(),"targetEntity","tgt entity",0);
		targetElements.add(stringDom);
		targetElements.add(integerDom);
		targetElements.add(targetEntity);
		SchemaElement t1 = new Attribute(LogicalRelation.getNextID(),"peopleID","",targetEntity.getId(),integerDom.getId(),1,1,true,0);
		SchemaElement t2 = new Attribute(LogicalRelation.getNextID(),"personName","",targetEntity.getId(),stringDom.getId(),1,1,false,0);
		SchemaElement t3 = new Attribute(LogicalRelation.getNextID(),"Height-cm","",targetEntity.getId(),integerDom.getId(),1,1,false,0);
		SchemaElement t4 = new Attribute(LogicalRelation.getNextID(),"Weight-kg","",targetEntity.getId(),integerDom.getId(),1,1,false,0);
		SchemaElement t5 = new Attribute(LogicalRelation.getNextID(),"BMI","",targetEntity.getId(),integerDom.getId(),1,1,false,0);

		targetElements.add(t1);
		targetElements.add(t2);
		targetElements.add(t3);
		targetElements.add(t4);
		targetElements.add(t5);

		Graph sourceGraph = new Graph(new Schema(LogicalRelation.getNextID(),"sourceSchema","doug","","","",false),sourceElements);
		Graph targetGraph = new Graph(new Schema(LogicalRelation.getNextID(),"targetSchema","doug","","","",false),targetElements);

		ArrayList<LogicalRelation> sourceLogRels = LogicalRelation.createLogicalRelations(sourceGraph);
		ArrayList<LogicalRelation> targetLogRels = LogicalRelation.createLogicalRelations(targetGraph);
		ArrayList<MappingCell> coveredCorrs = new ArrayList<MappingCell>();
		coveredCorrs.add(new MappingCell(LogicalRelation.getNextID(),new Integer(-1),s1.getId(),t1.getId(),new Double(1.0),"",new Date(),"","",new Boolean(true)));
		coveredCorrs.add(new MappingCell(LogicalRelation.getNextID(),new Integer(-1),s3.getId(),t2.getId(),new Double(1.0),"",new Date(),"","",new Boolean(true)));
		coveredCorrs.add(new MappingCell(LogicalRelation.getNextID(),new Integer(-1),s4.getId(),t3.getId(),new Double(1.0),"",new Date(),"","",new Boolean(true)));
		coveredCorrs.add(new MappingCell(LogicalRelation.getNextID(),new Integer(-1),s5.getId(),t4.getId(),new Double(1.0),"",new Date(),"","",new Boolean(true)));
		ArrayList<Dependency> dependsArrayList = Dependency.generateDependencies(sourceLogRels, targetLogRels, coveredCorrs);

		Dependency depend = dependsArrayList.get(0);
		Object[] retVals = SQLGenerator.generateDependencySQLScript(depend);

        return retVals;
	}


    @Test
    public void textFixtureOne()
    {
        String statement = "INSERT INTO targetEntity (BMI,Height-cm,peopleID,personName,Weight-kg)  SELECT DISTINCT 1,T0.Height-Inches,T0.personID,T0.lastName,T0.Weight-Pounds FROM sourceEntity AS T0;";
        Object[] retVals = getFixtureOne();
        // retVal[0] -- <ArrayList of CREATE skolem table statements>
		ArrayList<String> skTables = (ArrayList<String>)retVals[0];
        assertEquals( 0, skTables.size() );

		// retVal[1] -- HashMap of <Entity-ID, SELECT statements>
		HashMap<Integer,String> statements = (HashMap<Integer,String>)retVals[1];
        assertEquals( 1, statements.size() );
		for (String str : statements.values()){
            assertEquals( statement, str);
		}

		// retVal[2] -- ArrayList of cleanup statements (DROP Skolem tables)
		ArrayList<String> cleanup = (ArrayList<String>)retVals[2];
        assertEquals( 0, cleanup.size() );
    }

}
