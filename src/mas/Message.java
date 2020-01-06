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
    private final MetaAgent NEWUSER;
    private final MessageType MESSAGETYPE;
    private final String ROUTINGUPDATE;
    
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
    }
    
    Message(String receiver, MetaAgent newUser, MessageType messageType)
    {
        if(!messageType.equals(messageType.ADDUSERMESSAGE) && !messageType.equals(messageType.DELETEUSERMESSAGE) || receiver.equals("") || receiver == null)
            throw new IllegalArgumentException();
        
        this.RECEIVER = receiver;
        this.MESSAGEBODY = null;
        this.SENDER = newUser.portal.userName;
        this.NEWUSER = newUser;
        this.MESSAGETYPE = messageType;
        this.ROUTINGUPDATE = null;
    }
    
    Message(String receiver, String routingUpdate, MessageType messageType)
    {
        if(!messageType.equals(messageType.SHAREROUTINGTABLE) || receiver.equals("") || receiver == null)
            throw new IllegalArgumentException();
        
        
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
    
    public MetaAgent getNewUser()
    {
        return NEWUSER;
    }
    
    public MessageType getMessageType()
    {
        return MESSAGETYPE;
    }
    
    @Override
    public String toString()
    {
        if(MESSAGETYPE.equals(MESSAGETYPE.USERMESSAGE))
            return "Message from: " + SENDER + "\nMessage: " + MESSAGEBODY + "\nTo: " + RECEIVER;
        else if(MESSAGETYPE.equals(MESSAGETYPE.ADDUSERMESSAGE))
            return "Message from: " + SENDER + "\nMessage: Adding " + NEWUSER.userName + " to Portal " + NEWUSER.portal.userName + "\nTo: All connected Portals and Routers (RoutingTables should be updated)";
        else
            return "Message from: " + SENDER + "\nMessage: Deleting " + NEWUSER.userName + " to Portal " + NEWUSER.portal.userName + "\nTo: All connected Portals and Routers (RoutingTables should be updated)";
    }
}