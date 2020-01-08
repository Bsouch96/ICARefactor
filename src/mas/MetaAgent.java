package mas;
/**
* The MessageAgent class is an abstract class which is a parent to the userAgent class.
* @author Ben Souch, Jacob Jardine, Teddy Teasdale, Michael Wasell
* @version #1.0
* @since 2019/11/06
*/
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class MetaAgent extends ArrayBlockingQueue<Message> implements Runnable, Serializable
{

    protected String userName;
    protected Portal portal;
    
    private Thread t;
    private boolean exit;

    /**
     * The MetaAgent() makes use of the userName and portal to start a thread.
     * @param userName created to start the thread.
     * @param portal created to start the thread.
     * @throws IllegalArgumentException
     * @see IllegalArgumentException.
     */
    //UserAgents make use of this constructor
    public MetaAgent(String userName, Portal portal) 
    {
        super(100);
        
        if(userName == null || userName.isEmpty() || portal == null)
            throw new IllegalArgumentException("Please ensure your UserAgent username is not empty or null and your Portal is not null");
        
        this.userName = userName;
        this.portal = portal;
        
        this.exit = false;
        startThread();
    }
    
    /**
     * The MetaAgent() makes use of the userName to start a thread.
     * @param userName used as a unique id within the MAS.
     * @throws IllegalArgumentException
     * @see IllegalArgumentException.
     */
    //Portals and Routers make use of this constructor.
    public MetaAgent(String userName) 
    {
        super(100);
        
        if(userName == null || userName.isEmpty())
            throw new IllegalArgumentException("Please ensure your Portal/Router username is not null or empty");
        
        this.userName = userName;
        this.portal = portal;
        
        this.exit = false;
        startThread();
    }
    

    /**
     * startThread() to start the thread.
     */
    private void startThread() 
    {
        t = new Thread(this);
        t.start();
    }
    
    
    /**
     * run() to run and log thread.
     * @throws IllegalArgumentException ex.
     * @see IllegalArgumentException.
     */
    @Override
    public void run()
    {
        while(!exit)
        {
            try {
                messageHandler(this.take());
            } catch (InterruptedException ex) {
                Logger.getLogger(MetaAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     *  stop() to exit the thread.
     */
    public void stop()
    {
        exit = true;
    }
    
    
    /**
     * messageHandler() makes use of the message().
     * @param message system print out the message body. 
     */
    public void messageHandler(Message message)
    {
        System.out.println(message.getMessageBody());
    }
    
}