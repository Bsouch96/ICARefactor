/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

import java.net.Socket;
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
public class PortalTest {
    
    public PortalTest() {
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
     * Test of setPortal method, of class Portal.
     */
    @Test
    public void testSetPortal() {
        System.out.println("setPortal");
        Portal portal = null;
        Portal instance = null;
        boolean expResult = false;
        boolean result = instance.setPortal(portal);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRouter method, of class Portal.
     */
    @Test
    public void testSetRouter() {
        System.out.println("setRouter");
        Router router = null;
        Portal instance = null;
        boolean expResult = false;
        boolean result = instance.setRouter(router);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRouter method, of class Portal.
     */
    @Test
    public void testGetRouter() {
        System.out.println("getRouter");
        Portal instance = null;
        Router expResult = null;
        Router result = instance.getRouter();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSocket method, of class Portal.
     */
    @Test
    public void testGetSocket() {
        System.out.println("getSocket");
        Portal instance = null;
        Socket expResult = null;
        Socket result = instance.getSocket();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIpAddress method, of class Portal.
     */
    @Test
    public void testGetIpAddress() {
        System.out.println("getIpAddress");
        Portal instance = null;
        String expResult = "";
        String result = instance.getIpAddress();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPort method, of class Portal.
     */
    @Test
    public void testGetPort() {
        System.out.println("getPort");
        Portal instance = null;
        int expResult = 0;
        int result = instance.getPort();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPortalRoutingTable method, of class Portal.
     */
    @Test
    public void testGetPortalRoutingTable() {
        System.out.println("getPortalRoutingTable");
        Portal instance = null;
        TreeMap expResult = null;
        TreeMap result = instance.getPortalRoutingTable();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAgent method, of class Portal.
     */
    @Test
    public void testAddAgent() {
        System.out.println("addAgent");
        MetaAgent agent = null;
        Portal instance = null;
        instance.addAgent(agent);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of messageHandler method, of class Portal.
     */
    @Test
    public void testMessageHandler() {
        System.out.println("messageHandler");
        Message message = null;
        Portal instance = null;
        instance.messageHandler(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateLocalPortalTable method, of class Portal.
     */
    @Test
    public void testUpdateLocalPortalTable() {
        System.out.println("updateLocalPortalTable");
        Message message = null;
        Portal instance = null;
        instance.updateLocalPortalTable(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of connectTo method, of class Portal.
     */
    @Test
    public void testConnectTo() {
        System.out.println("connectTo");
        Portal instance = null;
        instance.connectTo();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
