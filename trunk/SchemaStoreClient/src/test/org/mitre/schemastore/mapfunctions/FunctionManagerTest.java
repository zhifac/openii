package test.org.mitre.schemastore.mapfunctions;

import java.util.*;

import org.mitre.schemastore.mapfunctions.*;
import org.mitre.schemastore.client.*;
import java.rmi.RemoteException;
import org.junit.*;
import static org.junit.Assert.*;

public class FunctionManagerTest {

    //replace AbstractMappingFunction with the new fixture
    private FunctionManager fixture = null;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        try
        {
            SchemaStoreClient client = new SchemaStoreClient();
            //add test fixtures for the mapping function here
            fixture = new FunctionManager();
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
    public void testParsing() {
        List<String> functions = fixture.getAvailableFunctions();
        assertFalse( "No functions seem to be avaiable", functions.size() == 0 );
        assertTrue( "NumericAdd was not found", functions.contains( "Numeric Add" ));
    }


    @Test
    public void testRetrieval() throws Exception
    {
        AbstractMappingFunction f = fixture.getFunction("Numeric Add");
        assertNotNull( f );
        assertTrue( f instanceof AbstractMappingFunction );
        assertTrue( f instanceof org.mitre.schemastore.mapfunctions.NumericAdd );
    }

}

