package test.org.mitre.schemastore.model.mapfunctions;

import java.util.*;

import org.mitre.schemastore.model.mapfunctions.*;
import org.junit.*;
import static org.junit.Assert.*;

public class FunctionManagerTest {

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
    }

    @Test
    public void testParsing() {
        List<String> functions = FunctionManager.getAvailableFunctions();
        assertFalse( "No functions seem to be avaiable", functions.size() == 0 );
        assertTrue( "NumericAdd was not found", functions.contains( "Numeric Add" ));
    }


    @Test
    public void testRetrieval() throws Exception
    {
        AbstractMappingFunction f = FunctionManager.getFunction("Numeric Add");
        assertNotNull( f );
        assertTrue( f instanceof AbstractMappingFunction );
        // assertTrue( f instanceof org.mitre.schemastore.model.mapfunctions.NumericAdd );
    }

}

