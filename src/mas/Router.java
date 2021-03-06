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
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


/**

* The Message class is used to create different types of messages.

* @author Ben Souch, Jacob Jardine, Teddy Teasdale, Michael Wasell

* @version #1.0

* @since 2019/11/06

*/
public class Router extends MetaAgent
{
    private volatile TreeMap<String, MetaAgent> routerRouting;
    private volatile TreeMap<String, Connection> networkPortals;
    private volatile ArrayList<Portal> localPortals;
    private Thread acceptThread;
    private ServerSocket serverSocket;
    private Thread socketThread;
    private ReentrantLock networkLock;
    
    public Router(String userName)
    {
        super(userName);
        routerRouting = new TreeMap<>();
        networkPortals = new TreeMap<>();
        localPortals = new ArrayList<>();
        networkLock = new ReentrantLock();
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
                        
                        Connection newConnection = new Connection(incoming);
                        
                        int timeout = 0;
                        
                        while(newConnection.messageWaiting())
                        {
                            //increase timeout to some point and close if times out
                            timeout++;
                            
                            if(timeout > 25000000)
                            {
                                incoming.close();
                                continue;
                            }
                         }
                        
                        Message extMessage = newConnection.receiveClientMessage();
                        
                        if(extMessage.getMessageType().equals(MessageType.HELLO) && !networkPortals.containsKey(extMessage.getSender()))
                        {
                            readFromSocket();
                            newConnection.setHandle(extMessage.getSender());
                            networkPortals.put(extMessage.getSender(), newConnection);
                            newConnection.sendClientMessage(new Message(Router.this.userName, "Hello Back!", MessageType.HELLOACK));
                            newConnection.sendClientMessage(new Message(getHandles(), MessageType.SHAREROUTINGTABLE));
                        }
                        else
                            System.out.println("Already connected to this connection!");
                    }catch (IOException ex)
                    {
                        Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex)
                    {
                        Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        acceptThread.start();
    }
    
    @Override
    public synchronized void messageHandler(Message message) 
    {   
        lock.lock();
        
        //if we don't know about the new users portal we will add it to our portal list if it is local.
        if(message.getMessageType().equals(MessageType.ADDUSERMESSAGE) && message.getPortalConnection() != null && message.getPortalConnection().getRouter() != null && !localPortals.contains(message.getPortalConnection()))
        {
            localPortals.add(message.getPortalConnection());
        }
        
        //if we have connected portals and we receive a system message then we loop though our portals and send them the message.
        if(message.getMessageType().equals(MessageType.ADDUSERMESSAGE) || message.getMessageType().equals(MessageType.DELETEUSERMESSAGE))
        {   //if the router doesn't have the new user stored along with the correct portal then we add it to our Tree Map.
            if(!routerRouting.containsKey(message.getUser()) && message.getPortalConnection() != null && message.getPortalConnection().getRouter() != null && message.getMessageType().equals(MessageType.ADDUSERMESSAGE))
            {
                //System.out.println(message.getPortalConnection().userName);
                routerRouting.put(message.getUser(), message.getPortalConnection());
            }
            else if(!networkPortals.containsKey(message.getUser()))
            {
                networkLock.lock();
                networkPortals.put(message.getUser(), networkPortals.get(message.getPrevNodeSignature()));
                networkLock.unlock();
                networkPortals.remove(message.getPrevNodeSignature());
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
                        {
                            p.put(message);
                        }
                        else
                        {
                            message.setPrevNodeSignature(userName);
                            p.put(new Message(getHandles(), MessageType.SHAREROUTINGTABLE));
                        }
                            
                    }catch(InterruptedException ie)
                    {
                        Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ie);
                    }
                });
            }
            if(!networkPortals.isEmpty())
            {
                networkLock.lock();
                String oldSender = message.getPrevNodeSignature();
                for(Map.Entry<String, Connection> map : networkPortals.entrySet())
                {
                    if(!oldSender.equals(map.getKey()))
                    {
                        try
                        {
                            message.setPrevNodeSignature(userName);
                            message.setPortalConnection(null);
                            networkPortals.get(map.getKey()).sendClientMessage(message);
                        } catch (IOException ex)
                        {
                            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else
                    {
                        try 
                        {
                            message.setPrevNodeSignature(userName);
                            networkPortals.get(map.getKey()).sendClientMessage(new Message(getHandles(), MessageType.SHAREROUTINGTABLE));
                        } catch (IOException ex)
                        {
                            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                networkLock.unlock();
                lock.unlock();
            }
        }//if our message is for users, we search our Tree Map and forward it accordingly.
        else if(message.getMessageType().equals(MessageType.USERMESSAGE) && routerRouting.containsKey(message.getReceiver()))
        {
            try
            {
                routerRouting.get(message.getReceiver()).put(message);
            }catch(InterruptedException ie)
            {
                Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ie);
            }
            finally
            {
                lock.unlock();
            }
        }
        else if(message.getMessageType().equals(MessageType.USERMESSAGE) && networkPortals.containsKey(message.getReceiver()))
        {
            try
            {
                networkPortals.get(message.getReceiver()).sendClientMessage(message);
            } catch (IOException ex)
            {
                Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally
            {
                lock.unlock();
            }
        }
        else
        {
            System.out.println("Router " + this.userName + ": Message receiver doesn't exist!");
            lock.unlock();
        }
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
    
    public void readFromSocket()
    {
        socketThread = new Thread(new Runnable()
        {   @Override
            public void run()
            {
                while(true)
                {
                    try
                    {
                        networkLock.lock();
                        ConcurrentHashMap<String, Connection> copy = new ConcurrentHashMap<>(networkPortals);
                        for(Map.Entry connectedSockets : copy.entrySet())
                        {
                            Connection socket = (Connection) connectedSockets.getValue();

                            if(socket.messageWaiting())
                            {
                                Message extMessage = socket.receiveClientMessage();
                                Router.this.put(extMessage);
                            }
                        }
                        networkLock.unlock();
                    }catch (IOException ex)
                    {
                        Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
                    }catch (ClassNotFoundException ex)
                    {
                        Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
                    }catch (InterruptedException ex)
                    {
                        Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        socketThread.start();
    }
    
    public String getHandles()
    {
        String routingHandles = "";            
        
        for(Map.Entry<String, MetaAgent> routerMap : routerRouting.entrySet())
        {
            if(routerMap.getKey().equals(routerRouting.lastKey()) && networkPortals.isEmpty())
            {
                routingHandles += routerMap.getKey();
            }
            else
            {
                routingHandles += routerMap.getKey();
                routingHandles += "|";
            }
        }
        for(Map.Entry<String, Connection> conMap : networkPortals.entrySet())
        {
            if(conMap.getKey().equals(networkPortals.lastKey()))
            {
                routingHandles += conMap.getKey();
            }
            else
            {
                routingHandles += conMap.getKey();
                routingHandles += "|";
            }
        }
        
        if(routingHandles.substring(routingHandles.length()).equals("\\|"))
        {
            routingHandles = routingHandles.substring(0, routingHandles.length()-1);
        }
        
        return routingHandles;
    }
}