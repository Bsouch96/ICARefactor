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
public class UserAgentTest {
    
    public UserAgentTest() {
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
     * Test of messageHandler method, of class UserAgent.
     */
    @Test
    public void testMessageHandler() {
        System.out.println("messageHandler");
        Message message = null;
        UserAgent instance = null;
        instance.messageHandler(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SendMessage method, of class UserAgent.
     */
    @Test
    public void testSendMessage() {
        System.out.println("SendMessage");
        Message message = null;
        UserAgent instance = null;
        instance.SendMessage(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
