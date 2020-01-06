/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author V8178742
 */
public class Portal extends MetaAgent
{
    public volatile TreeMap<String, MetaAgent> routingTable;
    private Socket socket; //Create Socket here for external connections to Routers.
    private Router portalRouter; //Local Router connection.
    
    //Portal to local Router communication.
    public Portal(String userName, Router router)
    {
        super(userName, null);
        this.routingTable = new TreeMap<>();
        this.portalRouter = router;
        //socket = null;
    }
    
    //Portal to external Router communication.
    public Portal(String userName, String ipAddress, int port)
    {
        super(userName, null);
        this.routingTable = new TreeMap<>();
        this.portalRouter = null;
        
        try
        {
            socket = new Socket(ipAddress, port);
        }catch(UnknownHostException uh)
        {
            System.out.println("Host Unknown");
        }catch(IOException io)
        {
            System.out.println("IO Exception");
        }
    }
    
    public boolean setPortal(Portal portal)
    {
        if(portal != null)
        {
            this.portal = portal;
            return true;
        }
        
        return false;
    }
    
    public boolean setRouter(Router router)
    {
        if(router != null)
        {
            this.portalRouter = router;
            return true;
        }
        
        return false;
    }
    
    protected Router getRouter()
    {
        return portalRouter;
    }
    
    //Uncomment when sockets are implemented.
    public Socket getSocket()
    {
        return socket;
    }
    
    public TreeMap getPortalRoutingTable()
    {
        return routingTable;
    }
    
    public void addAgent(MetaAgent agent)
    {
        if(agent == null)
            return;
        
        routingTable.put(agent.userName, agent);
        System.out.println("add");
        //Create new system message to broadcast to network.
        Message systemMessage = new Message("System", agent.userName, this, this.userName, MessageType.ADDUSERMESSAGE);
        
        //if(this.portalRouter == null) //Portal is connected to an external Router.
                //Put onto Socket connection's queue. Change the following command when Sockets are implemented.
        messageHandler(systemMessage);
    }
    
    @Override
    public void messageHandler(Message message)
    {
        //System.out.println("Portal Contains Key: " + routingTable.containsKey(message.getReceiver()));
        System.out.println("Passed: " + this.userName);
        
        if(message.getMessageType().equals(MessageType.ADDUSERMESSAGE))
        {
            if(!routingTable.containsKey(message.getNewUser()) && portalRouter != null)
            {
                System.out.println(this.userName + ": adding " + message.getNewUser() + " to routingTable");
                routingTable.put(message.getNewUser(), portalRouter);
            }
                
            else if(routingTable.containsKey(message.getNewUser()) && portalRouter != null)
            {
                try
                {
                    portalRouter.put(message);
                }catch(InterruptedException ie)
                {
                    System.out.println("Error!");
                }
            }
            //Implement else when Sockets are implemented.
            /*else
                routingTable.put(message.getNewUser().userName, socket);*/
        }
        else if(message.getMessageType().equals(MessageType.USERMESSAGE))
        {
            if(message.getReceiver().equals(this.userName))
                System.out.println("Message direct to portal: " + message.toString());
            else if(routingTable.containsKey(message.getReceiver()))
            {   
                try
                {
                    routingTable.get(message.getReceiver()).put(message);
                }catch(InterruptedException ie)
                {
                    System.out.println("Error!");
                }
            }
            else
                System.out.println(this.userName + ": " + "\"" + message.getReceiver() + "\"" + " could not be found.");
        }
    }
    
    public void updateLocalPortalTable(Message message)
    {
        if(message == null || message.getRoutingUpdate() == null || message.getRoutingUpdate().equals(""))
            return;

        String[] newHandles = message.getRoutingUpdate().split("\\|");
        
        for (int i = 0; i < newHandles.length-1; i++)
        {
            if(!routingTable.containsKey(newHandles[i]))
                routingTable.put(newHandles[i], portalRouter);
        }
    }
}