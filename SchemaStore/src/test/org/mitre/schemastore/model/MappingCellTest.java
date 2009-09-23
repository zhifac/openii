package test.org.mitre.schemastore.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.mapfunctions.FunctionManager;

import test.org.mitre.schemastore.model.mapfunctions.TestFixtures;

public class MappingCellTest {

    MappingCell proposed = null;
    MappingCell validated = null;
    TestFixtures factory = new TestFixtures();
    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
                            //MappingCell(getId(),getMappingId(),getInput()[0],getOutput(),getScore(),getAuthor(),getModificationDate(),getNotes())
        proposed = MappingCell.createProposedMappingCell(new Integer(1),new Integer(13),new Integer(49),new Integer(72),new Double(0.5),"Author",new Date(),"notes");
                        //MappingCell(getId(),getMappingId(),getInput(),getOutput(),getAuthor(),getModificationDate(),getFunctionClass(),getNotes())
        validated = MappingCell.createValidatedMappingCell(new Integer(1),new Integer(13),
            Arrays.asList(new Integer(49), new Integer(48)).toArray(new Integer[0]),new Integer(72),"Author",new Date(),"org.mitre.schemastore.model.mapfunctions.NumericAdd","notes");
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
        assertEquals(49, proposed.getInput()[0].intValue());
        assertEquals(72, proposed.getOutput().intValue());
        assertEquals(new Double(0.5), proposed.getScore());
        assertEquals("Author", proposed.getAuthor());
        assertEquals("notes", proposed.getNotes());
        assertEquals("org.mitre.schemastore.model.mapfunctions.IdentityFunction", proposed.getFunctionClass());
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
        assertEquals(48, validated.getInput()[1].intValue());
        assertEquals(72, validated.getOutput().intValue());
        assertEquals(new Double(1.0), validated.getScore());
        assertEquals("Author", validated.getAuthor());
        assertEquals("notes", validated.getNotes());
        assertEquals("org.mitre.schemastore.model.mapfunctions.NumericAdd", validated.getFunctionClass());
        assertTrue( validated.getValidated() );
    }

    @Test
    public void testGetRelationalString() throws Exception
    {
        MappingCell cell = factory.getMappingCells().get("identity");
        assertEquals("\"Ht\"", FunctionManager.getFunction(cell.getFunctionClass()).getRelationalString(factory.getPrefixArray("", cell.getInput().length), cell, factory.getMappingDefinition()));
    }

    @Test
    public void testSetInputAndDomain()
    {
        factory.getMappingCells().get("identity");
        factory.getTargetSchema();
    }
}

