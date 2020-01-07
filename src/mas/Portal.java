package mas;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.TreeMap;

/**
* The Portal class implements a portal which communicates to a local router.
* @author Ben Souch, Jacob Jardine, Teddy Teasdale, Michael Wasell
* @version #1.0
* @since 2019/11/06
*/
public class Portal extends MetaAgent
{
    public volatile TreeMap<String, MetaAgent> routingTable;
    private Socket portalSocket; //Create Socket here for external connections to Routers.
    private Router portalRouter; //Local Router connection.
    private String ipAddress;
    private int port;
    private Thread connectionThread;
    private InetAddress address;
    
    /**
     * 
     * @param userName
     * @param router 
     */
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
    
    /**
     * 
     * @param userName
     * @param ipAddress
     * @param port 
     */
    //Portal to external Router communication.
    public Portal(String userName, String ipAddress, int port)
    {
        super(userName);
        
        if(ipAddress == null || !validateIpAddress(ipAddress) || port < 8000)
            throw new IllegalArgumentException("Please ensure that your IP Address is appropriate and your Port is not less than 8000");
        
        this.routingTable = new TreeMap<>();
        this.portalRouter = null;
        this.ipAddress = ipAddress;
        this.port = port;
        
        connectTo();
    }
    
    /**
     * 
     * @param portal
     * @return 
     */
    public boolean setPortal(Portal portal)
    {
        if(portal != null)
        {
            this.portal = portal;
            return true;
        }
        
        return false;
    }
    
    /**
     * 
     * @param router
     * @return 
     */
    public boolean setRouter(Router router)
    {
        if(router != null)
        {
            this.portalRouter = router;
            return true;
        }
        
        return false;
    }
    
    /**
     * 
     * @return 
     */
    protected Router getRouter()
    {
        return portalRouter;
    }
    
    /**
     * 
     * @return 
     */
    //Uncomment when sockets are implemented.
    public Socket getSocket()
    {
        return portalSocket;
    }
    
    /**
     * 
     * @return 
     */
    public String getIpAddress()
    {
        return ipAddress;
    }
    
    /**
     * 
     * @return 
     */
    public int getPort()
    {
        return port;
    }
    
    /**
     * 
     * @return 
     */
    public TreeMap getPortalRoutingTable()
    {
        return routingTable;
    }
    
    /**
     * 
     * @param agent 
     */
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
    
    /**
     * 
     * @param message 
     */
    @Override
    public void messageHandler(Message message)
    {
        //System.out.println("Portal Contains Key: " + routingTable.containsKey(message.getReceiver()));
        System.out.println("Passed: " + this.userName);
        
        if(message.getMessageType().equals(MessageType.ADDUSERMESSAGE))
        {
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
        else if(message.getMessageType().equals(MessageType.DELETEUSERMESSAGE))
        {
            if(routingTable.containsKey(message.getUser()))
            {
                routingTable.remove(message.getUser());
                try
                {
                    portalRouter.put(message);
                }catch(InterruptedException ie)
                {
                    System.out.println("Error!");
                }
            }
        }
    }
    
    /**
     * 
     * @param message 
     */
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
    
    /**
     * 
     * @param agent 
     */
    public void removeAgent(UserAgent agent)
    {//if our map contains the username and the user is local to this portal, commence delete.
        if(routingTable.containsKey(agent.userName) && routingTable.get(agent.userName).equals(agent))
        {
            messageHandler(new Message("System", agent.userName, this, this.userName, MessageType.DELETEUSERMESSAGE));
            agent.portal = null;
            System.out.println(this.userName + ": " + agent.userName + " deleted. Sending message...");
        }
        else
            System.out.println(this.userName + ": Cannot delete a user that is not local to me.");
    }
    
    /**
     * 
     */
    public void connectTo()
    {
        /*connectionThread = new Thread(new Runnable()
        {
            @Override
            public void Run() throws IOException
            {
                try
                {
                    address = InetAddress.getByName(ipAddress);
                    portalSocket = new Socket(address, port);
                    portalSocket.bind(address);
                }catch(UnknownHostException uh)
                {
                    

                }
                
            }
        });
        connectionThread.start();*/
    }
    
    /**
     * 
     * @param ipAddress
     * @return 
     */
    //Should match 0.0.0.0 - 255.255.255.255
    public boolean validateIpAddress(String ipAddress)
    {
        return ipAddress.matches("\\b(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.\n" +
                        "  (25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.\n" +
                        "  (25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.\n" +
                        "  (25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\b");
            
    }
}