package mas;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.Normalizer;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
* The Message class is used to create different types of messages.
* @author Ben Souch, Jacob Jardine, Teddy Teasdale, Michael Wasell
* @version #1.0
* @since 2019/11/06
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
    
    /**
     * Test of values method, of class UserAgentTest.
     * @throws InterruptedException 
     * @see InterruptedException
     */
    @Test
    public void testMessageHandler() throws InterruptedException
    {
        System.out.println("messageHandler");
        Router router = new Router("R1");
        Portal portal1 = new Portal("P1", router);
        Portal portal2 = new Portal("P2", router);
        UserAgent user1 = new UserAgent("A1", portal1);
        UserAgent user2 = new UserAgent("A2", portal1);
        UserAgent user3 = new UserAgent("A3", portal2);
        Message message = new Message("A3", "Hello A1!", "A1", MessageType.USERMESSAGE);
        portal1.addAgent(user1);
        portal1.addAgent(user2);
        portal2.addAgent(user3);
        String result = "";
        
        
        PrintStream originalOut = System.out;
        try {
        ByteArrayOutputStream os = new ByteArrayOutputStream(100);
        PrintStream capture = new PrintStream(os);
        // From this point on, everything printed to System.out will get captured
        
        System.setOut(capture);
        user3.SendMessage(message);
        Thread.sleep(500);
        capture.flush();
        result = os.toString();
        } 
        finally 
        {
        System.setOut(originalOut);
        }
        String expResult = "Message from: A1\nMessage: Hello A1!\nTo: A3";
        expResult = (Normalizer.normalize(expResult, Normalizer.Form.NFD));
        result = (Normalizer.normalize(result, Normalizer.Form.NFD));
        assertEquals(expResult, result);
        System.out.println(message.toString());
        System.out.println(result);
        
    }
}
