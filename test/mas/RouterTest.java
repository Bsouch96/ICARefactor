/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

import java.util.ArrayList;
import java.util.TreeMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Souchys
 */
public class RouterTest {
    
    public RouterTest() {
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
     * Test of getRouterRoutingTable method, of class Router.
     */
    @Test
    public void testGetRouterRoutingTable() {
        System.out.println("getRouterRoutingTable");
        Router instance = null;
        TreeMap expResult = null;
        TreeMap result = instance.getRouterRoutingTable();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of waitForConnection method, of class Router.
     */
    @Test
    public void testWaitForConnection() {
        System.out.println("waitForConnection");
        Router instance = null;
        instance.waitForConnection();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of messageHandler method, of class Router.
     */
    @Test
    public void testMessageHandler() {
        System.out.println("messageHandler");
        Message message = null;
        Router instance = null;
        instance.messageHandler(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRouterRouting method, of class Router.
     */
    @Test
    public void testGetRouterRouting() {
        System.out.println("getRouterRouting");
        Router instance = null;
        TreeMap expResult = null;
        TreeMap result = instance.getRouterRouting();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNetworkPortals method, of class Router.
     */
    @Test
    public void testGetNetworkPortals() {
        System.out.println("getNetworkPortals");
        Router instance = null;
        TreeMap expResult = null;
        TreeMap result = instance.getNetworkPortals();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLocalPortals method, of class Router.
     */
    @Test
    public void testGetLocalPortals() {
        System.out.println("getLocalPortals");
        Router instance = null;
        ArrayList<Portal> expResult = null;
        ArrayList<Portal> result = instance.getLocalPortals();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
