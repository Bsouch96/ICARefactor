/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author V8178742
 */
public abstract class MetaAgent extends ArrayBlockingQueue<Message> implements Runnable, Serializable
{
    protected String userName;
    protected Portal portal;
    
    private Thread t;
    private boolean exit;

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
    

    private void startThread() 
    {
        t = new Thread(this);
        t.start();
    }
    
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
    
    public void stop()
    {
        exit = true;
    }
    
    public void messageHandler(Message message)
    {
        System.out.println(message.getMessageBody());
    }
    
}