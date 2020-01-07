/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Souchys
 */
public class PortalTest {
    
    public PortalTest()
    {
    
    }

    @Rule public ExpectedException thrown = ExpectedException.none();
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new Portal and the Portal userName variable is empty.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testLocalPortalConstArgsUserNameEmpty()
    {
        System.out.println("Testing Local Portal Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please ensure your Portal/Router username is not null or empty");
        Router router = new Router("R1");
        Portal portal = new Portal("", router);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new Portal and the Portal userName variable is null.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testLocalPortalConstArgsUserNameNull()
    {
        System.out.println("Testing Local Portal Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please ensure your Portal/Router username is not null or empty");
        Router router = new Router("R1");
        Portal portal = new Portal(null, router);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new Portal and the Router variable is null.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testLocalPortalConstArgsRouterNull()
    {
        System.out.println("Testing Local Portal Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please ensure your Portals local Router is not null");
        Portal portal = new Portal("P1", null);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new Portal and the Portal ipAddress is empty.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testExternalPortalConstArgsIpEmpty()
    {
        System.out.println("Testing External Portal Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please ensure that your IP Address is appropriate and your Port is not less than 8000");
        Portal portal = new Portal("P1", "", 8500);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new Portal and the Portal ipAddress is empty.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testExternalPortalConstArgsIpNull()
    {
        System.out.println("Testing External Portal Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please ensure that your IP Address is appropriate and your Port is not less than 8000");
        Portal portal = new Portal("P1", null, 8500);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new Portal and the Portal ipAddress is empty.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testExternalPortalConstArgsIPIncorrect()
    {
        System.out.println("Testing External Portal Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please ensure that your IP Address is appropriate and your Port is not less than 8000");
        Portal portal = new Portal("P1", "1789562", 8500);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new Portal and the Portal ipAddress is empty.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testExternalPortalConstArgsPortIncorrect()
    {
        System.out.println("Testing External Portal Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please ensure that your IP Address is appropriate and your Port is not less than 8000");
        Portal portal = new Portal("P1", "152.0.0.0", -1425);
    }
}
