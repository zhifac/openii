package test.org.mitre.schemastore.mapfunctions;

import org.mitre.schemastore.mapfunctions.*;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.client.*;
import java.rmi.RemoteException;
import org.junit.*;
import static org.junit.Assert.*;

public class TestTemplate {

    //replace AbstractMappingFunction with the new fixture
    private AbstractMappingFunction fixture = null;

    /**
     * Sets up the test fixture. 
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        try
        {
            SchemaStoreClient client = new SchemaStoreClient( System.getProperty("SchemaStoreJar" ) );
            //add test fixtures for the mapping function here
        }
        catch (RemoteException e)
        {
            System.out.println( "Failed to set up the test SchemaStoreClient" );
        }
    }

    /**
     * Tears down the test fixture. 
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        fixture = null;
    }
    
    @Test
    public void testSomeBehavior() {
        //assertEquals("Empty list should have 0 elements", 0, emptyList.size());
    }

}

