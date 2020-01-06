/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

import java.io.Serializable;

/**
 * 
 * @author V8178742
 */
public class Message
{
    private final static long serialVersionUID = 1L;
    private final String RECEIVER; //Will be UserAgent with GUI
    private final String MESSAGEBODY;
    private final String SENDER;
    private final String NEWUSER;
    private final MessageType MESSAGETYPE;
    private final String ROUTINGUPDATE;
    private final Portal PORTALCONNECTION;
    private String prevNodeHandle;
    
    //UserMessage Constructor
    Message(String receiver, String messageBody, String sender, MessageType messageType)
    {
        if(!messageType.equals(messageType.USERMESSAGE) || receiver.equals("") || receiver == null || messageBody == null || sender == null)
            throw new IllegalArgumentException();
        
        this.RECEIVER = receiver;
        this.MESSAGEBODY = messageBody;
        this.SENDER = sender;
        this.NEWUSER = null;
        this.MESSAGETYPE = messageType;
        this.ROUTINGUPDATE = null;
        this.PORTALCONNECTION = null;
        this.prevNodeHandle = null;
    }
    
    //Addition or deletion of user Constructor
    Message(String receiver, String newUser, Portal portalConnection, MessageType messageType)
    {
        if(!messageType.equals(messageType.ADDUSERMESSAGE) && !messageType.equals(messageType.DELETEUSERMESSAGE) || receiver.equals("") || receiver == null)
            throw new IllegalArgumentException();
        
        this.RECEIVER = receiver;
        this.MESSAGEBODY = null;
        this.SENDER = null;
        this.NEWUSER = newUser;
        this.MESSAGETYPE = messageType;
        this.ROUTINGUPDATE = null;
        this.PORTALCONNECTION = portalConnection;
        this.prevNodeHandle = prevNodeHandle;
    }
    
    //Share Router routing table Constructor
    Message(String routingUpdate, MessageType messageType)
    {
        if(!messageType.equals(messageType.SHAREROUTINGTABLE) || routingUpdate.equals("") || routingUpdate == null)
            throw new IllegalArgumentException();
        
        this.RECEIVER = null;
        this.MESSAGEBODY = null;
        this.SENDER = "Router";
        this.NEWUSER = null;
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
    
    public String getNewUser()
    {
        return NEWUSER;
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
            return "Message from: " + SENDER + "\nMessage: Adding " + NEWUSER + "\nTo: All connected Portals and Routers (RoutingTables should be updated)";
        else if(MESSAGETYPE.equals(MESSAGETYPE.DELETEUSERMESSAGE))
            return "Message from: " + SENDER + "\nMessage: Deleting " + NEWUSER + "\nTo: All connected Portals and Routers (RoutingTables should be updated)";
        else
            return "Message from: Router" + "\nMessage: Add handles " + ROUTINGUPDATE + " to your routing table" + "\nTo: all connected Portals";
    }
}