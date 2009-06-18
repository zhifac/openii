package test.org.mitre.schemastore.mapfunctions;

import java.io.*;
import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.mitre.schemastore.mapfunctions.*;
import org.mitre.schemastore.model.*;
import org.mitre.schemastore.client.*;
import java.rmi.RemoteException;
import org.junit.*;
import static org.junit.Assert.*;

public class FunctionListParserTest {

    InputStream in = null;   //the input stream to read from
    /**
     * Sets up the test fixture. 
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        String fileContents = "<mappingfunctions>\n<class>org.mitre.schemastore.mappingfunctions.NumericAdd</class>"+
            "<class>\norg.mitre.schemastore.mappingfunctions.Concatenate\r\n</class>"+
            "\n</mappingfunctions>";
        in = new ByteArrayInputStream( fileContents.getBytes());
    }

    /**
     * Tears down the test fixture. 
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        in = null;
    }
    
    @Test
    public void testParse() throws Exception 
    {
        // Create SAX 2 parser...
        XMLReader xr = XMLReaderFactory.createXMLReader();
        // Set the ContentHandler...
        FunctionListParser parser = new FunctionListParser();

        //check that the list is empty
        List<String> list = parser.getFunctions();
        assertEquals( 0, list.size() );
        
        xr.setContentHandler(parser);
        // Parse the file...
        xr.parse(new InputSource(in));
        in.close();
        list = parser.getFunctions();
        assertEquals( 2, list.size() );
        assertEquals( "org.mitre.schemastore.mappingfunctions.NumericAdd", list.get(0));
        assertEquals( "org.mitre.schemastore.mappingfunctions.Concatenate", list.get(1));
    }

}

