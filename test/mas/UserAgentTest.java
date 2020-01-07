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
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Souchys
 */
public class UserAgentTest {
    
    public UserAgentTest()
    {
        
    }
    
    @Rule public ExpectedException thrown = ExpectedException.none();
    
    //testUserMsgConstArgsReceiver
    
    /**
     * Testing the IllegalArgumentException thrown when creating a UserAgent with an empty userName.
     * @since #1.0
     * @exception IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testUserAgentConstArgsUserNameEmpty()
    {
        System.out.println("Testing UserAgent Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please ensure your UserAgent username is not empty or null and your Portal is not null");
        Router router = new Router("R1");
        Portal portal = new Portal("P1", router);
        UserAgent agent1 = new UserAgent("", portal);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a UserAgent with a null userName.
     * @since #1.0
     * @exception IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testUserAgentConstArgsUserNameNull()
    {
        System.out.println("Testing UserAgent Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please ensure your UserAgent username is not empty or null and your Portal is not null");
        Router router = new Router("R1");
        Portal portal = new Portal("P1", router);
        UserAgent agent1 = new UserAgent(null, portal);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a UserAgent with a null Portal.
     * @since #1.0
     * @exception IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testUserAgentConstArgsPortalNull()
    {
        System.out.println("Testing UserAgent Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please ensure your UserAgent username is not empty or null and your Portal is not null");
        Router router = new Router("R1");
        Portal portal = new Portal("P1", router);
        UserAgent agent1 = new UserAgent("Ben", null);
    }
}
