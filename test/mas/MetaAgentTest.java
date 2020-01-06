/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

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
public class MetaAgentTest {
    
    public MetaAgentTest() {
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
     * Test of run method, of class MetaAgent.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        MetaAgent instance = null;
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stop method, of class MetaAgent.
     */
    @Test
    public void testStop() {
        System.out.println("stop");
        MetaAgent instance = null;
        instance.stop();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of messageHandler method, of class MetaAgent.
     */
    @Test
    public void testMessageHandler() {
        System.out.println("messageHandler");
        Message message = null;
        MetaAgent instance = null;
        instance.messageHandler(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class MetaAgentImpl extends MetaAgent {

        public MetaAgentImpl() {
            super("", null);
        }
    }
    
}
