/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pqrtree;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joao
 */
public class LeafTest {
    
    public LeafTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class Leaf.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Leaf instance = new Leaf(3);
        String expResult = "3";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of areAllChildrenBlack method, of class Leaf.
     */
    @Test
    public void testAreAllChildrenBlack() {
        System.out.println("areAllChildrenBlack");
        Leaf instance = new Leaf(0);
        boolean expResult = true;
        boolean result = instance.areAllChildrenBlack();
        assertEquals(expResult, result);
    }
    
}
