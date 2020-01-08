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
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author V8178742
 */
public class Portal extends MetaAgent implements Serializable
{
    public volatile TreeMap<String, MetaAgent> routingTable;
    public volatile TreeMap<String, Socket> externalTable;
    private Socket portalSocket; //Create Socket here for external connections to Routers.
    private Router portalRouter; //Local Router connection.
    private String ipAddress;
    private int port;
    private Thread connectionThread;
    private Thread readFromSocketThread;
    private InetAddress address;
    Connection connectedRouter;
    
    
    
    //Portal to local Router communication.
    public Portal(String userName, Router router)
    {
        super(userName);
        
        if(router == null)
            throw new IllegalArgumentException("Please ensure your Portals local Router is not null");
        
        this.routingTable = new TreeMap<>();
        this.externalTable = new TreeMap<>();
        this.portalRouter = router;
        
        
        //socket = null;
    }
    
    //Portal to external Router communication.
    public Portal(String userName, String ipAddress, int port)
    {
        super(userName);
        
        if(ipAddress == null  || port < 8000)
            throw new IllegalArgumentException("Please ensure that your IP Address is appropriate and your Port is not less than 8000");
        
        this.routingTable = new TreeMap<>();
        this.externalTable = new TreeMap<>();
        this.portalRouter = null;
        this.ipAddress = ipAddress;
        this.port = port;
        
        
        
        connectTo();
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
        return portalSocket;
    }
    
    public String getIpAddress()
    {
        return ipAddress;
    }
    
    public int getPort()
    {
        return port;
    }
    
    public TreeMap getPortalRoutingTable()
    {
        return routingTable;
    }
    
    public synchronized void addAgent(MetaAgent agent)
    {
        if(agent == null)
            return;
        
        routingTable.put(agent.userName, agent);
        System.out.println("add");
        
        //Create new system message to broadcast to network.
        messageHandler(new Message("System", agent.userName, this, this.userName, MessageType.ADDUSERMESSAGE));
    }
    
    @Override
    public synchronized void messageHandler(Message message)
    {
        //System.out.println("Portal Contains Key: " + routingTable.containsKey(message.getReceiver()));
        //System.out.println("Passed: " + this.userName);
        //System.out.println("PORTAL: MESSAGE TYPE THAT WAS PASSED: " + message.getMessageType());
        lock.lock();
        
        //System.out.println(message.getPrevNodeSignature());
        switch (message.getMessageType())
        {
            case ADDUSERMESSAGE://Local Portal addition to routingTable.
                if(!routingTable.containsKey(message.getUser()) && portalRouter != null)
                {
                    System.out.println(this.userName + ": adding " + message.getUser() + " to routingTable");
                    routingTable.put(message.getUser(), portalRouter);
                    lock.unlock();
                }//Local Portal broadcasting its new agent.
                else if(routingTable.containsKey(message.getUser()) && portalRouter != null)
                {
                    try
                    {
                        if(!message.getPrevNodeSignature().equals(portalRouter.userName))
                        {
                            message.setPrevNodeSignature(Portal.this.userName);
                            portalRouter.put(message);
                        }
                    }catch(InterruptedException ie)
                    {
                        System.out.println("Error!");
                    }
                    finally
                    {
                        lock.unlock();
                    }
                }
                //External Portal broadcasting its new agent.
                else if(routingTable.containsKey(message.getUser()) && portalSocket != null)
                {
                    message.setPortalConnection(null);
                    if(!message.getPrevNodeSignature().equals(connectedRouter.getHandle()))
                    {
                        message.setPrevNodeSignature(Portal.this.userName);
                        writeToSocket(message);
                    }
                    lock.unlock();
                }//External Portal addition to externalTable from Router.
                else if(!externalTable.containsKey(message.getUser()) && portalRouter == null)
                {
                    message.setPrevNodeSignature(Portal.this.userName);
                    externalTable.put(message.getUser(), portalSocket);
                    lock.unlock();
                }
                break;
            case USERMESSAGE:
                //System.out.println(externalTable.get(message.getReceiver()));
                if(routingTable.containsKey(message.getReceiver()))
                {
                    try
                    {
                        message.setPrevNodeSignature(Portal.this.userName);
                        routingTable.get(message.getReceiver()).put(message);
                    }catch(InterruptedException ie)
                    {
                        System.out.println("Error!");
                    }
                    finally
                    {
                        lock.unlock();
                    }
                }
                else if(externalTable.containsKey(message.getReceiver()) && externalTable.get(message.getReceiver()).equals(portalSocket))
                {//System.out.println(Portal.this.externalTable + ": PASSING TO SOCKET");
                    message.setPrevNodeSignature(Portal.this.userName);
                    writeToSocket(message);
                    lock.unlock();
                }
                else if(externalTable.containsKey(message.getReceiver()))
                {
                    message.setPrevNodeSignature(Portal.this.userName);
                    writeToSocket(message);
                    lock.unlock();
                }
                else
                {
                    System.out.println(this.userName + ": " + "\"" + message.getReceiver() + "\"" + " could not be found.");
                    lock.unlock();
                }
                break;
            case DELETEUSERMESSAGE:
                if(routingTable.containsKey(message.getUser()))
                {
                    if(portalRouter != null)
                    {
                        try
                        {
                            message.setPrevNodeSignature(Portal.this.userName);
                            portalRouter.put(message);
                        }catch(InterruptedException ie)
                        {
                            System.out.println("Error!");
                        }
                    }
                    else if(portalSocket != null)
                    {
                        message.setPrevNodeSignature(Portal.this.userName);
                        writeToSocket(message);
                    }
                    
                    routingTable.remove(message.getUser());
                    lock.unlock();
                }
                break;
            case SHAREROUTINGTABLE:
                message.setPrevNodeSignature(Portal.this.userName);
                updatePortalTable(message);
                lock.unlock();
                break;
            default:
                lock.unlock();
                break;
        }
    }
    
    public synchronized void writeToSocket(Message message)
    {
        lock.lock();
        if(message == null)
            return;
        
        try
        {
            message.setPrevNodeSignature(this.userName);
            connectedRouter.sendClientMessage(message);
            //System.out.println(Portal.this.userName + ": WRITING TO MY SOCKET");
            //objectOutputStream.flush();
            //System.out.println(Portal.this.userName + ": FLUSHED");
        }catch (IOException ex)
        {
            Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            lock.unlock();
        }
    }
    
    public synchronized void updatePortalTable(Message message)
    {
        //System.out.println("Hit updatePortalTable method.");

        //System.out.println(message.getRoutingUpdate());
        
        String[] newHandles = message.getRoutingUpdate().split("\\|");
        
        for (int i = 0; i < newHandles.length-1; i++)
        {
            if(!routingTable.containsKey(newHandles[i]) && portalRouter != null)
            {
                //System.out.println(this.userName + ": adding: " + newHandles[i]);
                routingTable.put(newHandles[i], portalRouter);
            }
            else if(!externalTable.containsKey(newHandles[i]) && portalSocket != null)
            {
                //System.out.println(this.userName + ": adding: " + newHandles[i]);
                externalTable.put(newHandles[i], portalSocket);
            }
        }
    }
    
    public synchronized void removeAgent(UserAgent agent)
    {//if our map contains the username and the user is local to this portal, commence delete.
        if(routingTable.containsKey(agent.userName) && routingTable.get(agent.userName).equals(agent))
        {
            System.out.println(this.userName + ": " + agent.userName + " deleted. Sending message...");
            messageHandler(new Message("System", agent.userName, this, this.userName, MessageType.DELETEUSERMESSAGE));
            agent.portal = null;
            
        }
        else
            System.out.println(this.userName + ": Cannot delete a user that is not local to me.");
    }
    
    public void connectTo()
    {
        connectionThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    lock.lock();
                    address = InetAddress.getByName(ipAddress);
                    portalSocket = new Socket(address, port);
                    connectedRouter = new Connection(portalSocket);
                    
                    connectedRouter.sendClientMessage(new Message(Portal.this.userName, "Hello!", MessageType.HELLO));
                    
                    int timeout = 0;
                    
                    while(!connectedRouter.messageWaiting())
                    {
                        //increase timeout to some point and close if times out
                        timeout++;

                        if(timeout > 10000000)
                        {
                            portalSocket.close();
                            continue;
                        }
                    }
                    
                    Message extMessage = connectedRouter.receiveClientMessage();
                    if(extMessage.getMessageType().equals(MessageType.HELLOACK))
                    {
                        lock.unlock();
                        connectedRouter.setHandle(extMessage.getSender());
                        readFromSocket();
                    }
                    else
                        lock.unlock();
                }catch(UnknownHostException uh)
                {
                    Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, uh);
                }catch(IOException io)
                {
                    Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, io);
                } catch (ClassNotFoundException ex)
                {
                    Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        connectionThread.start();
    }
    
    //Should match 0.0.0.0 - 255.255.255.255
    public boolean validateIpAddress(String ipAddress)
    {
        return ipAddress.matches("\\b(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.\n" +
                        "  (25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.\n" +
                        "  (25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.\n" +
                        "  (25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\b");
            
    }
    
    public void readFromSocket()
    {
        readFromSocketThread = new Thread(new Runnable()
        {   @Override
            public void run()
            {
                while(true)
                {
                    try
                    {
                        if(connectedRouter.messageWaiting())
                        {
                            Message extMessage = connectedRouter.receiveClientMessage();
                            Portal.this.put(extMessage);
                        }
                    }catch (IOException ex)
                    {
                        Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex)
                    {
                        Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        readFromSocketThread.start();
    }
}