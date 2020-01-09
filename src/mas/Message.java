/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

import java.io.Serializable;

/**

* The Message class is used to create different types of messages.

* @author Ben Souch, Jacob Jardine, Teddy Teasdale, Michael Wasell

* @version #1.0

* @since 2019/11/06

*/
public class Message implements Serializable
{
    private final static long serialVersionUID = 1L;
    private final String RECEIVER;
    private final String MESSAGEBODY;
    private final String SENDER;
    private final String USER;
    private final MessageType MESSAGETYPE;
    private String routingUpdate;
    private Portal portalConnection;
    private String prevNodeSignature;
    
    /**

     * Message() makes use of the receiver, messageBody, sender and messageType for peer to peer communication.

     * @param receiver The receiver of the message

     * @param messageBody Content of a message

     * @param sender The sender of a message

     * @param messageType USERMESSAGE, DELETEUSERMESSAGE, ADDUSERMESSAGE, SHAREROUTINGTABLE

     * @throws IllegalArgumentException

     * @see IllegalArgumentException

     */
    Message(String receiver, String messageBody, String sender, MessageType messageType)
    {
        if(messageType == null || !messageType.equals(messageType.USERMESSAGE) || receiver == null || receiver.isEmpty() || messageBody == null || sender == null)
            throw new IllegalArgumentException("Please check your new User Message parameters.");
        
        this.RECEIVER = receiver;
        this.MESSAGEBODY = messageBody;
        this.SENDER = sender;
        this.USER = null;
        this.MESSAGETYPE = messageType;
        this.routingUpdate = null;
        this.portalConnection = null;
        this.prevNodeSignature = null;
    }
    
    /**

     * Message() makes use of the receiver, user, portalConnection, prevNodeHandle and messageType to add

     * or delete a user constructor.

     * @param receiver The receiver of the message.

     * @param user Added or deleted.

     * @param portalConnection Connecting portal.

     * @param prevNodeHandle Signature of the previous node that has passed the message.

     * @param messageType USERMESSAGE, DELETEUSERMESSAGE, ADDUSERMESSAGE, SHAREROUTINGTABLE.

     * @throws IllegalArgumentException

     * @see IllegalArgumentException

     */
    Message(String receiver, String user, Portal portalConnection, String prevNodeHandle, MessageType messageType)
    {
        if(messageType == null || portalConnection == null || !messageType.equals(messageType.ADDUSERMESSAGE) && !messageType.equals(messageType.DELETEUSERMESSAGE) || prevNodeHandle == null || prevNodeHandle.isEmpty() || receiver == null || receiver.isEmpty() || user == null || user.isEmpty())
            throw new IllegalArgumentException("Please check your new Add/Delete user System Message parameters.");
        
        this.RECEIVER = receiver;
        this.MESSAGEBODY = null;
        this.SENDER = "System";
        this.USER = user;
        this.MESSAGETYPE = messageType;
        this.routingUpdate = null;
        this.portalConnection = portalConnection;
        this.prevNodeSignature = prevNodeHandle;
    }
     /**

     * Message() makes use of messageType, SHAREROUTINGTABLE and routingUpdate to share the routing table constructor.

     * or delete a user constructor.

     * @param routingUpdate Updating routing table.

     * @param messageType USERMESSAGE, DELETEUSERMESSAGE, ADDUSERMESSAGE, SHAREROUTINGTABLE.

     * @throws IllegalArgumentException

     * @see IllegalArgumentException

     */
    //Share Router routing table Constructor
    Message(String routingUpdate, MessageType messageType)
    {
        if(messageType == null || !messageType.equals(messageType.SHAREROUTINGTABLE) || routingUpdate == null)
            throw new IllegalArgumentException("Please check your new Share Router Message parameters.");
        
        this.RECEIVER = null;
        this.MESSAGEBODY = null;
        this.SENDER = "Router";
        this.USER = null;
        this.MESSAGETYPE = messageType;
        this.routingUpdate = routingUpdate;
        this.portalConnection = null;
        this.prevNodeSignature = null;
    }

    Message(String sender, String msgBody, MessageType messageType)
    {
        //if(messageType == null || !messageType.equals(MessageType.HELLO) && !messageType.equals(MessageType.HELLOACK) || sender == null || sender.isEmpty())
            //throw new IllegalArgumentException("Please ensure your Hello and Helloack message parameters are appropriate.");
        
        this.SENDER = sender;
        this.MESSAGEBODY = msgBody;
        this.MESSAGETYPE = messageType;
        this.RECEIVER = null;
        this.USER = null;
        this.routingUpdate = null;
        this.portalConnection = null;
        this.prevNodeSignature = null;
    }
    
    /**

     * getReceiver() to return the Receiver.

     * @return - RECEIVER

     */
    public String getReceiver()
    {
        return RECEIVER;
    }
/**

     * getMessageBody() to return the MessageBody.

     * @return - MESSAGEBODY

     */
    public String getMessageBody()
    {
        return MESSAGEBODY;
    }
    /**

     * getSender() to return the Sender.

     * @return - SENDER

     */
    public String getSender()
    {
        return SENDER;
    }
    /**

     * getSerialVersionUID() to return SerialVersionUID.

     * @return - serialVersionUID

     */
    public long getSerialVersionUID()
    {
        return this.serialVersionUID;
    }
    /**

     * getUser() to return User.

     * @return - USER

     */
    public String getUser()
    {
        return USER;
    }
    /**

     * getMessageType() to return MessageType.

     * @return - MESSAGETYPE

     */
    public MessageType getMessageType()
    {
        return MESSAGETYPE;
    }
    /**

     * getRoutingUpdate() to return RoutingUpdate.

     * @return RoutingUpdate

     */
    public String getRoutingUpdate()
    {
        return routingUpdate;
    }
    /**

     * getPrevNodeHandle() to return PrevNodeHandle.

     * @return prevNodeHandle

     */
    public Portal getPortalConnection()
    {
        return portalConnection;
    }
    /**

     * toString() to return the different message types.

     * @return System messages.

     */
    public String getPrevNodeSignature()
    {
        return prevNodeSignature;
    }
    
    public void setPrevNodeSignature(String prevNode)
    {
        if(prevNode == null || prevNode.isEmpty())
            return;
        
        this.prevNodeSignature = prevNode;
    }
    public void setPortalConnection(Portal portal)
    {
        portalConnection = portal;
    }
    
    public void setRoutingUpdate(String routingUpdate)
    {
        this.routingUpdate = routingUpdate;
    }
    
    @Override
    public String toString()
    {
        if(MESSAGETYPE.equals(MESSAGETYPE.USERMESSAGE))
            return "Message from: " + SENDER + "\nMessage: " + MESSAGEBODY + "\nTo: " + RECEIVER;
        else if(MESSAGETYPE.equals(MESSAGETYPE.ADDUSERMESSAGE))
            return "Message from: " + SENDER + "\nMessage: Adding " + USER + "\nTo: All connected Portals and Routers";
        else if(MESSAGETYPE.equals(MESSAGETYPE.DELETEUSERMESSAGE))
            return "Message from: " + SENDER + "\nMessage: Deleting " + USER + "\nTo: All connected Portals and Routers";
        else if(MESSAGETYPE.equals(MESSAGETYPE.HELLO))
            return "Message from: " + SENDER + "\nMessage: Hello " + "\nTo: Router";
        else
            return "Message from: " + SENDER + "\nMessage: " + MESSAGEBODY + "\nTo: Portal";
    }
}