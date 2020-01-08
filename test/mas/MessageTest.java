package mas;

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
public class MessageTest {
    
    /**
     * MessageTest() empty constructor.
     */
    public MessageTest()
    {
        
    }

    @Rule public ExpectedException thrown = ExpectedException.none();
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new User Message and the receiver variable is empty.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testUserMsgConstArgsReceiver()
    {
        System.out.println("Testing UserMessage Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new User Message parameters.");
        Message message = new Message("", "Hello", "Ben", MessageType.USERMESSAGE);
    }
    
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new User Message and the receiver variable is null.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testUserMsgConstArgsReceiverNull()
    {
        System.out.println("Testing UserMessage Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new User Message parameters.");
        Message message = new Message(null, "Hello", "Ben", MessageType.USERMESSAGE);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new User Message and the messageBody variable is null.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testUserMsgConstArgsMessageBodyNull()
    {
        System.out.println("Testing UserMessage Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new User Message parameters.");
        Message message = new Message("Michael", null, "Ben", MessageType.USERMESSAGE);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new User Message and the sender variable is null.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testUserMsgConstArgsSenderNull()
    {
        System.out.println("Testing UserMessage Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new User Message parameters.");
        Message message = new Message("Michael", "Hello", null, MessageType.USERMESSAGE);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new User Message and the MessageType is incorrect.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testUserMsgConstArgsMessageTypeIncorrect()
    {
        System.out.println("Testing UserMessage Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new User Message parameters.");
        Message message = new Message("Michael", "Hello", "Ben", MessageType.DELETEUSERMESSAGE);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new User Message and the MessageType is null.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testUserMsgConstArgsMessageTypeNull()
    {
        System.out.println("Testing UserMessage Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new User Message parameters.");
        Message message = new Message("Michael", "Hello", "Ben", null);
    }
    

    
    /**
     * Testing the IllegalArgumentException thrown when creating a new add/delete user Message and the MessageType is incorrect.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testAddDelMsgConstArgsReceiverEmpty()
    {
        System.out.println("Testing Add/Delete User Message Constructor");
        Portal portal = new Portal("P1", null);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new Add/Delete user System Message parameters.");
        Message message = new Message("", "A1", portal, "P1", MessageType.ADDUSERMESSAGE);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new add/delete user Message and the MessageType is null.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testAddDelMsgConstArgsReceiverNull()
    {
        System.out.println("Testing Add/Delete User Message Constructor");
        Portal portal = new Portal("P1", null);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new Add/Delete user System Message parameters.");
        Message message = new Message(null, "A1", portal, "P1", MessageType.ADDUSERMESSAGE);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new add/delete user Message and the New User variable is empty.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testAddDelMsgConstArgsNewUserEmpty()
    {
        System.out.println("Testing Add/Delete User Message Constructor");
        Portal portal = new Portal("P1", null);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new Add/Delete user System Message parameters.");
        Message message = new Message("Ben", "", portal, "P1", MessageType.ADDUSERMESSAGE);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new add/delete user Message and the New User variable is null.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testAddDelMsgConstArgsNewUserNull()
    {
        System.out.println("Testing Add/Delete User Message Constructor");
        Portal portal = new Portal("P1", null);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new Add/Delete user System Message parameters.");
        Message message = new Message("Ben", null, portal, "P1", MessageType.ADDUSERMESSAGE);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new add/delete user Message and the Portal variable is null.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testAddDelMsgConstArgsPortalNull()
    {
        System.out.println("Testing Add/Delete User Message Constructor");
        Portal portal = new Portal("P1", null);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new Add/Delete user System Message parameters.");
        Message message = new Message("Ben", "A1", null, "P1", MessageType.ADDUSERMESSAGE);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new add/delete user Message and the prevNodeVariable variable is empty.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testAddDelMsgConstArgsPrevNodeHandleEmpty()
    {
        System.out.println("Testing Add/Delete User Message Constructor");
        Portal portal = new Portal("P1", null);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new Add/Delete user System Message parameters.");
        Message message = new Message("Ben", "A1", portal, "", MessageType.ADDUSERMESSAGE);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new add/delete user Message and the prevNodeVariable variable is null.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testAddDelMsgConstArgsPrevNodeHandleNull()
    {
        System.out.println("Testing Add/Delete User Message Constructor");
        Portal portal = new Portal("P1", null);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new Add/Delete user System Message parameters.");
        Message message = new Message("Ben", "A1", portal, null, MessageType.ADDUSERMESSAGE);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new add/delete user Message and the messageType variable is incorrect.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testAddDelMsgConstArgsMessgeTypeIncorrect()
    {
        System.out.println("Testing Add/Delete User Message Constructor");
        Portal portal = new Portal("P1", null);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new Add/Delete user System Message parameters.");
        Message message = new Message("Ben", "A1", portal, "P1", MessageType.SHAREROUTINGTABLE);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new add/delete user Message and the messageType variable is null.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testAddDelMsgConstArgsMessgeTypeNull()
    {
        System.out.println("Testing Add/Delete User Message Constructor");
        Portal portal = new Portal("P1", null);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new Add/Delete user System Message parameters.");
        Message message = new Message("Ben", "A1", portal, "P1", null);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new Share Routing Table Message and the routingUpdate variable is empty.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testShareRoutMsgConstArgsRoutEmpty()
    {
        System.out.println("Testing Add/Delete User Message Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new Share Router Message parameters.");
        Message message = new Message("", MessageType.SHAREROUTINGTABLE);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new Share Routing Table Message and the routingUpdate variable is null.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testShareRoutMsgConstArgsRoutNull()
    {
        System.out.println("Testing Add/Delete User Message Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new Share Router Message parameters.");
        Message message = new Message(null, MessageType.SHAREROUTINGTABLE);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new Share Routing Table Message and the messageType is incorrect.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testShareRoutMsgConstArgsMsgTypeIncorrect()
    {
        System.out.println("Testing Add/Delete User Message Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new Share Router Message parameters.");
        Message message = new Message("A1,A2,A3,A4,A5", MessageType.USERMESSAGE);
    }
    
    /**
     * Testing the IllegalArgumentException thrown when creating a new Share Routing Table Message and the messageType is null.
     * @since #1.0
     * @exception  IllegalArgumentException
     * @see IllegalArgumentException
     */
    @Test
    public void testShareRoutMsgConstArgsMsgTypeNull()
    {
        System.out.println("Testing Add/Delete User Message Constructor");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Please check your new Share Router Message parameters.");
        Message message = new Message("A1,A2,A3,A4,A5", null);
    }
    
    /**
     * Testing the toString() return when creating a new User Message.
     * @since #1.0
     */
    @Test
    public void testUserMessageToString()
    {
        System.out.println("Testing toString() of new User Message");
        Message message = new Message("Ben", "Hello", "Teddy", MessageType.USERMESSAGE);
        String expResult = "Message from: Teddy\nMessage: Hello\nTo: Ben";
        String actualResult = message.toString();
        assertEquals(expResult, actualResult);
    }
    
    /**
     * Testing the toString() return when creating a new Add User Message.
     * @since #1.0
     */
    @Test
    public void testAddUserMessageToString()
    {
        System.out.println("Testing toString() of new User Message");
        Portal portal = new Portal("P1", null);
        Message message = new Message("System", "Ben", portal, "P1", MessageType.ADDUSERMESSAGE);
        String expResult = "Message from: System\nMessage: Adding Ben\nTo: All connected Portals and Routers";
        String actualResult = message.toString();
        assertEquals(expResult, actualResult);
    }
    
    /**
     * Testing the toString() return when creating a new Delete User Message.
     * @since #1.0
     */
    @Test
    public void testDelUserMessageToString()
    {
        System.out.println("Testing toString() of new User Message");
        Portal portal = new Portal("P1", null);
        Message message = new Message("System", "Ben", portal, "P1", MessageType.DELETEUSERMESSAGE);
        String expResult = "Message from: System\nMessage: Deleting Ben\nTo: All connected Portals and Routers";
        String actualResult = message.toString();
        assertEquals(expResult, actualResult);
    }
    
    /**
     * Testing the toString() return when creating a new Delete User Message.
     * @since #1.0
     */
    @Test
    public void testShareRoutTableMessageToString()
    {
        System.out.println("Testing toString() of new User Message");
        Portal portal = new Portal("P1", null);
        Message message = new Message("A1|A2|A3|A4", MessageType.SHAREROUTINGTABLE);
        String expResult = "Message from: Router\nMessage: Add handles A1|A2|A3|A4 to your routing table\nTo: All connected Portals";
        String actualResult = message.toString();
        assertEquals(expResult, actualResult);
    }
}//"Message from: Router" + "\nMessage: Add handles " + ROUTINGUPDATE + " to your routing table" + "\nTo: All connected Portals";