/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author V8178742
 */
public class Router extends MetaAgent
{
    public volatile TreeMap<String, MetaAgent> routerRouting;
    public volatile TreeMap<String, Socket> networkPortals;
    public volatile ArrayList<Portal> localPortals;
    private Thread acceptThread;
    private ServerSocket serverSocket;
    private Thread socketThread;
    
    public Router(String userName)
    {
        super(userName);
        routerRouting = new TreeMap<>();
        networkPortals = new TreeMap<>();
        localPortals = new ArrayList<>();
        
        try
        {
            serverSocket = new ServerSocket(8500, 0, InetAddress.getLocalHost());
            
            System.out.println(InetAddress.getLocalHost());
            waitForConnection();
        }catch(UnknownHostException uh)
        {
            System.out.println("Host Unknown");
        }catch(IOException io)
        {
            System.out.println("IO Exception");
        }
    }

    public TreeMap getRouterRoutingTable()
    {
        return routerRouting;
    }
    
    public void waitForConnection()
    {
        acceptThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for(;;)
                {
                    try
                    {
                        Socket incoming = serverSocket.accept();
                        
                        OutputStream outputStream = incoming.getOutputStream();
                        //Create an ObjectOutputStream from the output stream so we can send Messages.
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                        InputStream inputStream = incoming.getInputStream();
                        //Create a ObjectInputStream so we can read Messages.
                        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                        int timeout = 0;
                        
                        while(inputStream.available() == 0)
                        {
                            //increase timeout to some point and close if times out
                            timeout++;
                            
                            if(timeout > 25000000)
                            {
                                incoming.close();
                                continue;
                            }
                         }
                        
                        Message extMessage = (Message)objectInputStream.readObject();
                        System.out.println("Received external connection from: " + extMessage.getSender());
                        if(extMessage.getMessageType().equals(MessageType.HELLO) && !networkPortals.containsKey(extMessage.getSender()))
                        {
                            //System.out.println();
                            readFromSocket();
                            networkPortals.put(extMessage.getSender(), incoming);
                            objectOutputStream.writeObject(new Message(Router.this.userName, "Hello Back!", MessageType.HELLOACK));
                            writeToSocket(new Message("", MessageType.SHAREROUTINGTABLE));
                        }
                        else
                            System.out.println("Already connected to this connection!");
                    }catch (IOException ex)
                    {
                        Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        });
        acceptThread.start();
    }
    
    @Override
    public synchronized void messageHandler(Message message) 
    {   //if we don't know about the new users portal we will add it to our portal list if it is local.
        if(message.getMessageType().equals(MessageType.ADDUSERMESSAGE) && message.getPortalConnection().getRouter() != null && !localPortals.contains(message.getPortalConnection()))
            localPortals.add(message.getPortalConnection());
        
        //if we have connected portals and we receive a system message then we loop though our portals and send them the message.
        if(message.getMessageType().equals(MessageType.ADDUSERMESSAGE) || message.getMessageType().equals(MessageType.DELETEUSERMESSAGE))
        {   //if the router doesn't have the new user stored along with the correct portal then we add it to our Tree Map.
            if(!routerRouting.containsKey(message.getUser()) && message.getPortalConnection().getRouter() != null && message.getMessageType().equals(MessageType.ADDUSERMESSAGE))
            {
                routerRouting.put(message.getUser(), message.getPortalConnection());
            }
            else if(routerRouting.containsKey(message.getUser()) && message.getPortalConnection().getRouter() != null && message.getMessageType().equals(MessageType.DELETEUSERMESSAGE))
            {
                routerRouting.remove(message.getUser());
            }
            
            if(!localPortals.isEmpty())
            {
                localPortals.forEach((p) ->
                {
                    try
                    {
                        if(!p.equals(message.getPortalConnection()))
                            p.put(message);
                        else// if(p.equals(message.getPortalConnection()) && )
                        {
                            StringBuilder routingHandles = new StringBuilder();
                            
                            for(Map.Entry routerMap : routerRouting.entrySet())
                            {
                                if(routerMap.getKey().equals(routerRouting.lastKey()))
                                    routingHandles.append(routerMap.getKey().toString());
                                else
                                    routingHandles.append(routerMap.getKey()).append("|");
                            }
                            
                            p.put(new Message(routingHandles.toString(), MessageType.SHAREROUTINGTABLE));
                        }
                            
                    }catch(InterruptedException ie)
                    {
                        System.out.println("Error!");
                    }
                });
            }
            if(!networkPortals.isEmpty()) //When sockets are implemented, serialise message and write to socket.
                writeToSocket(message);
            
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
        else if(message.getMessageType().equals(MessageType.USERMESSAGE) && networkPortals.containsKey(message.getReceiver()))
            writeToSocket(message);
        else
            System.out.println("Router " + this.userName + ": Message receiver doesn't exist!");
    }
    
    public TreeMap getRouterRouting()
    {
        return routerRouting;
    }
    
    public TreeMap getNetworkPortals()
    {
        return networkPortals;
    }
    
    public ArrayList<Portal> getLocalPortals()
    {
        return localPortals;
    }
    
    public synchronized void writeToSocket(Message message)
    {
        if(message == null)
            return;
        
        if(message.getMessageType().equals(MessageType.ADDUSERMESSAGE) || message.getMessageType().equals(MessageType.DELETEUSERMESSAGE) || message.getMessageType().equals(MessageType.SHAREROUTINGTABLE))
        {
            try
            {
                
                //Send message to all sockets but the socket that the message came from.
                for(Map.Entry map : networkPortals.entrySet())
                {
                    if(message.getPrevNodeSignature() != null && !message.getPrevNodeSignature().equals(map.getKey()))
                    {//OutputStream outputStream = incoming.getOutputStream();
                        message.setPrevNodeSignature(this.userName);
                        //OutputStream outputStream = ;
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream((OutputStream)map.getValue());
                        //ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                        objectOutputStream.writeObject(message);
                        objectOutputStream.flush();
                    }
                    else
                    {
                        StringBuilder routingHandles = new StringBuilder();
                        //Collect all socket handles to update Portal.
                        for(Map.Entry routerMap : routerRouting.entrySet())
                        {
                            if(routerMap.getKey().equals(routerRouting.lastKey()))
                                routingHandles.append(routerMap.getKey().toString());
                            else
                                routingHandles.append(routerMap.getKey()).append("|");
                        }
                        
                        message.setPrevNodeSignature(this.userName);
                        //OutputStream outputStream = networkPortals.get(message.getUser()).getOutputStream();
                        Socket socket = (Socket)map.getValue();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(new Message(routingHandles.toString(), MessageType.SHAREROUTINGTABLE));
                        objectOutputStream.flush();
                    }
                }
            }catch (IOException ex)
            {
                Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            try
            {
                message.setPrevNodeSignature(this.userName);
                OutputStream outputStream = networkPortals.get(message.getUser()).getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(message);
            }catch (IOException ex)
            {
                Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void readFromSocket()
    {
        socketThread = new Thread(new Runnable()
        {   @Override
            public void run()
            {
                try
                {
                    for(Map.Entry connectedSockets : networkPortals.entrySet())
                    {
                        Socket socket = (Socket) connectedSockets.getValue();
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        if(objectInputStream.available() > 0)
                        {
                            Message extMessage = (Message)objectInputStream.readObject();
                            if(extMessage.getMessageType().equals(MessageType.ADDUSERMESSAGE))
                            {
                                extMessage.setPrevNodeSignature(connectedSockets.getKey().toString());
                            }
                            messageHandler(extMessage);
                        }
                    }
                }catch (IOException ex)
                {
                    Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex)
                {
                    Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        socketThread.start();
    }
}