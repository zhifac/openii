package test.org.mitre.schemastore.model;

import org.mitre.schemastore.model.*;
import org.mitre.schemastore.client.*;
import java.rmi.RemoteException;
import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;

public class MappingCellTest {

    MappingCell proposed = null;
    MappingCell validated = null;
    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
                            //MappingCell(getId(),getMappingId(),getInput()[0],getOutput(),getScore(),getAuthor(),getModificationDate(),getNotes())
        proposed = MappingCell.createProposedMappingCell(new Integer(1),new Integer(13),new Integer(5),new Integer(0),new Double(0.5),"Author",new Date(),"notes");
                        //MappingCell(getId(),getMappingId(),getInput(),getOutput(),getAuthor(),getModificationDate(),getFunctionClass(),getNotes())
        validated = MappingCell.createValidatedMappingCell(new Integer(1),new Integer(13),
            Arrays.asList(new Integer(2), new Integer(42)).toArray(new Integer[0]),new Integer(0),"Author",new Date(),"function class","notes");
    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        proposed = null;
    }

    @Test
    public void testProposed() {
        testProposed( proposed );
    }

    @Test
    public void testProposedCopy()
    {
        MappingCell p2 = proposed.copy();
        testProposed( p2 );
    }

    private void testProposed( MappingCell proposed )
    {
        assertEquals(1, proposed.getId().intValue());
        assertEquals(13, proposed.getMappingId().intValue());
        assertEquals(5, proposed.getInput()[0].intValue());
        assertEquals(0, proposed.getOutput().intValue());
        assertEquals(new Double(0.5), proposed.getScore());
        assertEquals("Author", proposed.getAuthor());
        assertEquals("notes", proposed.getNotes());
        assertEquals("org.mitre.schemastore.mapfunctions.NullFunction", proposed.getFunctionClass());
        assertFalse( proposed.getValidated() );
    }

    @Test
    public void testValidated() {
        testValidated( validated );
    }

    @Test
    public void testValidatedCopy()
    {
        MappingCell p2 = validated.copy();
        testValidated( p2 );
    }

    private void testValidated( MappingCell validated )
    {
        assertEquals(1, validated.getId().intValue());
        assertEquals(13, validated.getMappingId().intValue());
        assertEquals(42, validated.getInput()[1].intValue());
        assertEquals(0, validated.getOutput().intValue());
        assertEquals(new Double(1.0), validated.getScore());
        assertEquals("Author", validated.getAuthor());
        assertEquals("notes", validated.getNotes());
        assertEquals("function class", validated.getFunctionClass());
        assertTrue( validated.getValidated() );
    }
}

