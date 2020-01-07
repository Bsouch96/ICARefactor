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
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author V8178742
 */
public class Portal extends MetaAgent
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
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;
    private InputStream inputStream;
    private ObjectInputStream objectInputStream;
    
    
    //Portal to local Router communication.
    public Portal(String userName, Router router)
    {
        super(userName);
        
        if(router == null)
            throw new IllegalArgumentException("Please ensure your Portals local Router is not null");
        
        this.routingTable = new TreeMap<>();
        this.portalRouter = router;
        //socket = null;
    }
    
    //Portal to external Router communication.
    public Portal(String userName, String ipAddress, int port)
    {
        super(userName);
        
        if(ipAddress == null || !validateIpAddress(ipAddress) || port > 8000)
            throw new IllegalArgumentException("Please ensure that your IP Address is appropriate and your Port is not less than 8000");
        
        this.routingTable = new TreeMap<>();
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
        System.out.println("Passed: " + this.userName);
        
        switch (message.getMessageType())
        {
            case ADDUSERMESSAGE:
                if(!routingTable.containsKey(message.getUser()) && portalRouter != null)
                {
                    System.out.println(this.userName + ": adding " + message.getUser() + " to routingTable");
                    routingTable.put(message.getUser(), portalRouter);
                }
                else if(routingTable.containsKey(message.getUser()) && portalRouter != null)
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
                else if(externalTable.containsKey(message.getUser()) && portalRouter == null)
                {
                    writeToSocket(message);
                }
                else if(!externalTable.containsKey(message.getUser()) && portalRouter == null)
                {
                    System.out.println(this.userName + ": adding " + message.getUser() + " to routingTable");
                    externalTable.put(message.getUser(), portalSocket);
                }
                break;
            case USERMESSAGE:
                if(routingTable.containsKey(message.getReceiver()) && routingTable.get(message.getReceiver()).equals(portalRouter))
                {
                    try
                    {
                        routingTable.get(message.getReceiver()).put(message);
                    }catch(InterruptedException ie)
                    {
                        System.out.println("Error!");
                    }
                }
                else if(externalTable.containsKey(message.getReceiver()) && externalTable.get(message.getReceiver()).equals(portalSocket))
                    writeToSocket(message);
                else if(externalTable.containsKey(message.getReceiver()))
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
                break;
            case DELETEUSERMESSAGE:
                if(routingTable.containsKey(message.getUser()))
                {
                    if(portalRouter != null)
                    {
                        try
                        {
                            portalRouter.put(message);
                        }catch(InterruptedException ie)
                        {
                            System.out.println("Error!");
                        }
                    }
                    else if(portalSocket != null)
                        writeToSocket(message);
                    
                    routingTable.remove(message.getUser());
                }
                break;
            case SHAREROUTINGTABLE:
                updateLocalPortalTable(message);
                break;
            default:
                break;
        }
    }
    
    public synchronized void writeToSocket(Message message)
    {
        if(message == null)
            return;
        
        try
        {
            message.setPrevNodeSignature(this.userName);
            outputStream = portalSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        }catch (IOException ex)
        {
            Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public synchronized void updateLocalPortalTable(Message message)
    {
        if(message == null || message.getRoutingUpdate() == null || message.getRoutingUpdate().equals(""))
            return;

        String[] newHandles = message.getRoutingUpdate().split("\\|");
        
        for (int i = 0; i < newHandles.length-1; i++)
        {
            if(!routingTable.containsKey(newHandles[i]) && portalRouter != null)
                routingTable.put(newHandles[i], portalRouter);
            else
                externalTable.put(newHandles[i], portalSocket);
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
                    address = InetAddress.getByName(ipAddress);
                    portalSocket = new Socket(address, port);
                    OutputStream outputStream = portalSocket.getOutputStream();
                    // create an object output stream from the output stream so we can send an object through it
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    InputStream inputStream = portalSocket.getInputStream();
                    // create a DataInputStream so we can read data from it.
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    
                    objectOutputStream.writeObject(new Message(Portal.this.userName, "Hello!", MessageType.HELLO));
                    
                    int timeout = 0;
                    
                    while(inputStream.available() == 0)
                    {
                        //increase timeout to some point and close if times out
                        timeout++;

                        if(timeout > 10000000)
                        {
                            portalSocket.close();
                            continue;
                        }
                    }
                    
                    Message extMessage = (Message)objectInputStream.readObject();
                    if(extMessage.getMessageType().equals(MessageType.HELLOACK))
                        readFromSocket();
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
                try
                {
                    inputStream = portalSocket.getInputStream();
                    objectInputStream = new ObjectInputStream(inputStream);
                    Message extMessage = (Message) objectInputStream.readObject();
                    messageHandler(extMessage);
                }catch (IOException ex)
                {
                    Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex)
                {
                    Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        readFromSocketThread.start();
    }
}