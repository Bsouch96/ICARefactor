package mas;
/**
* The Message class is used to create different types of messages.
* @author Ben Souch, Jacob Jardine, Teddy Teasdale, Michael Wasell
* @version #1.0
* @since 2019/11/06
*/
public class Message  
{
    private final static long serialVersionUID = 1L;
    private final String RECEIVER;
    private final String MESSAGEBODY;
    private final String SENDER;
    private final String USER;
    private final MessageType MESSAGETYPE;
    private final String ROUTINGUPDATE;
    private final Portal PORTALCONNECTION;
    private String prevNodeHandle;
    
    /**
     * Message() makes use of the receiver, messageBody, sender and messageType for peer to peer communication.
     * @param receiver The receiver of the message
     * @param messageBody Content of a message
     * @param sender The sender of a message
     * @param messageType USERMESSAGE, DELETEUSERMESSAGE, ADDUSERMESSAGE, SHAREROUTINGTABLE
     * @throws IllegalArgumentException
     * @see IllegalArgumentException
     */
    //UserMessage Constructor
    Message(String receiver, String messageBody, String sender, MessageType messageType)
    {
        if(messageType == null || !messageType.equals(messageType.USERMESSAGE) || receiver == null || receiver.isEmpty() || messageBody == null || sender == null)
            throw new IllegalArgumentException("Please check your new User Message parameters.");
        
        this.RECEIVER = receiver;
        this.MESSAGEBODY = messageBody;
        this.SENDER = sender;
        this.USER = null;
        this.MESSAGETYPE = messageType;
        this.ROUTINGUPDATE = null;
        this.PORTALCONNECTION = null;
        this.prevNodeHandle = null;
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
    //Addition or deletion of user Constructor
    Message(String receiver, String user, Portal portalConnection, String prevNodeHandle, MessageType messageType)
    {
        if(messageType == null || portalConnection == null || !messageType.equals(messageType.ADDUSERMESSAGE) && !messageType.equals(messageType.DELETEUSERMESSAGE) || prevNodeHandle == null || prevNodeHandle.isEmpty() || receiver == null || receiver.isEmpty() || user == null || user.isEmpty())
            throw new IllegalArgumentException("Please check your new Add/Delete user System Message parameters.");
        
        this.RECEIVER = receiver;
        this.MESSAGEBODY = null;
        this.SENDER = "System";
        this.USER = user;
        this.MESSAGETYPE = messageType;
        this.ROUTINGUPDATE = null;
        this.PORTALCONNECTION = portalConnection;
        this.prevNodeHandle = prevNodeHandle;
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
        if(messageType == null || !messageType.equals(messageType.SHAREROUTINGTABLE) || routingUpdate == null || routingUpdate.isEmpty())
            throw new IllegalArgumentException("Please check your new Share Router Message parameters.");
        
        this.RECEIVER = null;
        this.MESSAGEBODY = null;
        this.SENDER = "Router";
        this.USER = null;
        this.MESSAGETYPE = messageType;
        this.ROUTINGUPDATE = routingUpdate;
        this.PORTALCONNECTION = null;
        this.prevNodeHandle = null;
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
        return ROUTINGUPDATE;
    }

    /**
     * getPortalConnection() to return PortalConnection.
     * @return PortalConnection
     */
    public Portal getPortalConnection()
    {
        return PORTALCONNECTION;
    }
    
    /**
     * getPrevNodeHandle() to return PrevNodeHandle.
     * @return prevNodeHandle
     */
    public String getPrevNodeHandle()
    {
        return prevNodeHandle;
    }

    /**
     * toString() to return the different message types.
     * @return System messages.
     */
    @Override
    public String toString()   
    {
        if(MESSAGETYPE.equals(MESSAGETYPE.USERMESSAGE))
            return "Message from: " + SENDER + "\nMessage: " + MESSAGEBODY + "\nTo: " + RECEIVER;
        else if(MESSAGETYPE.equals(MESSAGETYPE.ADDUSERMESSAGE))
            return "Message from: " + SENDER + "\nMessage: Adding " + USER + "\nTo: All connected Portals and Routers";
        else if(MESSAGETYPE.equals(MESSAGETYPE.DELETEUSERMESSAGE))
            return "Message from: " + SENDER + "\nMessage: Deleting " + USER + "\nTo: All connected Portals and Routers";
        else
            return "Message from: Router" + "\nMessage: Add handles " + ROUTINGUPDATE + " to your routing table" + "\nTo: All connected Portals";
    }

}