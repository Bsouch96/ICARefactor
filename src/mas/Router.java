/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


/**
 *
 * @author V8178742
 */
public class Router extends MetaAgent
{
    public volatile TreeMap<String, MetaAgent> routerRouting = new TreeMap<>();
    private volatile TreeMap<String, Socket> networkPortals = new TreeMap<>(); //Used when local users are added.
    public volatile ArrayList<Portal> localPortals = new ArrayList<>();
    
    public Router(String userName)
    {
        super(userName, null);
    }

    public TreeMap getRouterRoutingTable()
    {
        return routerRouting;
    }
    
    @Override
    public void messageHandler(Message message) 
    {   //if we don't know about the new users portal we will add it to our portal list if it is local.
        if(message.getMessageType().equals(MessageType.ADDUSERMESSAGE) && message.getNewUser().portal.getRouter() != null && !localPortals.contains(message.getNewUser().portal))
            localPortals.add(message.getNewUser().portal);
        
        //if we have connected portals and we receive a system message then we loop though our portals and send them the message.
        if(message.getMessageType().equals(MessageType.ADDUSERMESSAGE) || message.getMessageType().equals(MessageType.DELETEUSERMESSAGE))
        {
            if(!localPortals.isEmpty())
            {
                localPortals.forEach((p) ->
                {
                    try
                    {
                        if(!p.equals(message.getNewUser().portal))
                            p.put(message);
                        else
                            p.updateLocalPortalTable(routerRouting);
                    }catch(InterruptedException ie)
                    {
                        System.out.println("Error!");
                    }
                });
            }
            
            /*if(!networkPortals.isEmpty()) //When sockets are implemented, serialise message and write to socket.
            {
                
                for(Map.Entry map : networkPortals.entrySet())
                {
                    Socket s = (Socket) map.getValue();
                }
            }*/
            
            //if the router doesn't have the new user stored along with the correct portal then we add it to our Tree Map.
            if(!routerRouting.containsKey(message.getNewUser().userName) && message.getNewUser().portal.getRouter() != null)
            {
                routerRouting.put(message.getNewUser().userName, message.getNewUser().portal);
            }
            /*else
                networkPortals.put(message.getNewUser().userName, message.getNewUser().portal.getSocket());*/
        }//if our message is for users, we search our Tree Map and forward it accordingly.
        else if(message.getMessageType().equals(MessageType.USERMESSAGE) && routerRouting.containsKey(message.getReceiver()))
        {
            try
            {
                System.out.println("Router " + this.userName + ": Passed though router");
                routerRouting.get(message.getReceiver()).put(message);
            }catch(InterruptedException ie)
            {
                System.out.println("Error!");
            }
        }//When sockets are implemented, the else will write the message to the connected socket. Add a seen signature to avoid infinite.
        else
            System.out.println("Router " + this.userName + ": Message receiver doesn't exist!");
    }
}