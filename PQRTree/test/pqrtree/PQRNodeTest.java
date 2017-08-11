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
public class PQRNodeTest {
    
    public PQRNodeTest() {
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
     * Test of getType method, of class PQRNode.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        PQRNode instance = null;
        PQRType expResult = null;
        PQRType result = instance.getType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setType method, of class PQRNode.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        PQRType type = null;
        PQRNode instance = null;
        instance.setType(type);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isDeleted method, of class PQRNode.
     */
    @Test
    public void testIsDeleted() {
        System.out.println("isDeleted");
        PQRNode instance = null;
        boolean expResult = false;
        boolean result = instance.isDeleted();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChildCount method, of class PQRNode.
     */
    @Test
    public void testGetChildCount() {
        System.out.println("getChildCount");
        PQRNode instance = null;
        int expResult = 0;
        int result = instance.getChildCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRepresentativeChild method, of class PQRNode.
     */
    @Test
    public void testGetRepresentativeChild() {
        System.out.println("getRepresentativeChild");
        PQRNode instance = null;
        Node expResult = null;
        Node result = instance.getRepresentativeChild();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRepresentativeChild method, of class PQRNode.
     */
    @Test
    public void testSetRepresentativeChild() {
        System.out.println("setRepresentativeChild");
        Node v = null;
        PQRNode instance = null;
        instance.setRepresentativeChild(v);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addGrayChild method, of class PQRNode.
     */
    @Test
    public void testAddGrayChild() {
        System.out.println("addGrayChild");
        Node node = null;
        PQRNode instance = null;
        instance.addGrayChild(node);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addBlackChild method, of class PQRNode.
     */
    @Test
    public void testAddBlackChild() {
        System.out.println("addBlackChild");
        Node node = null;
        PQRNode instance = null;
        instance.addBlackChild(node);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGrayChild method, of class PQRNode.
     */
    @Test
    public void testGetGrayChild() {
        System.out.println("getGrayChild");
        PQRNode instance = null;
        PQRNode expResult = null;
        PQRNode result = instance.getGrayChild();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertChild method, of class PQRNode.
     */
    @Test
    public void testInsertChild() {
        System.out.println("insertChild");
        Node v = null;
        PQRNode instance = null;
        instance.insertChild(v);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertBeginning method, of class PQRNode.
     */
    @Test
    public void testInsertBeginning() {
        System.out.println("insertBeginning");
        Node v = null;
        PQRNode instance = null;
        instance.insertBeginning(v);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertEnd method, of class PQRNode.
     */
    @Test
    public void testInsertEnd() {
        System.out.println("insertEnd");
        Node v = null;
        PQRNode instance = null;
        instance.insertEnd(v);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertBetween method, of class PQRNode.
     */
    @Test
    public void testInsertBetween() {
        System.out.println("insertBetween");
        Node v = null;
        Node i = null;
        Node j = null;
        PQRNode instance = null;
        instance.insertBetween(v, i, j);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeChild method, of class PQRNode.
     */
    @Test
    public void testRemoveChild() {
        System.out.println("removeChild");
        Node v = null;
        PQRNode instance = null;
        instance.removeChild(v);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of joinBlackChildren method, of class PQRNode.
     */
    @Test
    public void testJoinBlackChildren() {
        System.out.println("joinBlackChildren");
        PQRNode instance = null;
        instance.joinBlackChildren();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transformPIntoQ method, of class PQRNode.
     */
    @Test
    public void testTransformPIntoQ() {
        System.out.println("transformPIntoQ");
        PQRNode r = null;
        PQRNode instance = null;
        PQRNode expResult = null;
        PQRNode result = instance.transformPIntoQ(r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveAwayFromLCA method, of class PQRNode.
     */
    @Test
    public void testMoveAwayFromLCA() {
        System.out.println("moveAwayFromLCA");
        PQRNode v = null;
        PQRNode instance = null;
        instance.moveAwayFromLCA(v);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mergeIntoLCA method, of class PQRNode.
     */
    @Test
    public void testMergeIntoLCA() {
        System.out.println("mergeIntoLCA");
        PQRNode instance = null;
        instance.mergeIntoLCA();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mergePNode method, of class PQRNode.
     */
    @Test
    public void testMergePNode() {
        System.out.println("mergePNode");
        PQRNode instance = null;
        instance.mergePNode();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of adjust method, of class PQRNode.
     */
    @Test
    public void testAdjust() {
        System.out.println("adjust");
        PQRNode instance = null;
        instance.adjust();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cleanUp method, of class PQRNode.
     */
    @Test
    public void testCleanUp() {
        System.out.println("cleanUp");
        PQRNode instance = null;
        instance.cleanUp();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class PQRNode.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        PQRNode instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of areAllChildrenBlack method, of class PQRNode.
     */
    @Test
    public void testAreAllChildrenBlack() {
        System.out.println("areAllChildrenBlack");
        PQRNode instance = null;
        boolean expResult = false;
        boolean result = instance.areAllChildrenBlack();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
