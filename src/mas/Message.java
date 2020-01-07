/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

/**
 * 
 * @author V8178742
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

    public String getReceiver()
    {
        return RECEIVER;
    }

    public String getMessageBody()
    {
        return MESSAGEBODY;
    }

    public String getSender()
    {
        return SENDER;
    }
    
    public long getSerialVersionUID()
    {
        return this.serialVersionUID;
    }
    
    public String getUser()
    {
        return USER;
    }
    
    public MessageType getMessageType()
    {
        return MESSAGETYPE;
    }
    
    public String getRoutingUpdate()
    {
        return ROUTINGUPDATE;
    }
    
    public Portal getPortalConnection()
    {
        return PORTALCONNECTION;
    }
    
    public String getPrevNodeHandle()
    {
        return prevNodeHandle;
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
        else
            return "Message from: Router" + "\nMessage: Add handles " + ROUTINGUPDATE + " to your routing table" + "\nTo: All connected Portals";
    }
}