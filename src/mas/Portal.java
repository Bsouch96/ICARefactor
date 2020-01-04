/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author t7091808
 */
public class Portal extends MetaAgent
{
    protected volatile TreeMap<String, MetaAgent> routingTable;
    
    private Router portalRouter;
    
    //Single Portal communication
    public Portal(String userName)
    {
        super(userName, null);
        this.routingTable = new TreeMap<>();
        this.portalRouter = null;
    }
    
    //Portal to Portal communication.
    public Portal(String userName, Portal portal)
    {
        super(userName, portal);
        this.routingTable = new TreeMap<>();
        this.portalRouter = null;
        synchronise();
    }
    
    //Portal to Router communication
    public Portal(String userName, Router router)
    {
        super(userName, null);
        this.routingTable = new TreeMap<>();
        this.portalRouter = router;
        synchronise();
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
    
    public Router getRouter()
    {
        return portalRouter;
    }
    
    public void addAgent(MetaAgent agent)
    {
        routingTable.put(agent.userName, agent);
        System.out.println("add");
        
        if(portalRouter != null) //If the network has an existing Router, update it.
        {
            //Updates router
            portalRouter.routing.put(agent.userName, this);
            
            //Update all other Portals here...
            for(Portal pList : portalList)
            {
                if(pList.equals(this))
                    this.routingTable.put(agent.userName, agent);
                else
                    pList.routingTable.put(agent.userName, portalRouter);
            }
        }
        else if(this.portal != null) //If the network has no Router but a connected Portal.
        {
            //Updates connected Portal's routing table.
            portal.routingTable.put(agent.userName, this);
            System.out.println("Adding agent");
        }
        // if this.portal != null notify that portal
    }
    
    private void synchronise() //Only called when there is more than 1 Portal.
    {
        if(this.portal != null) //Null Pointer Exception occurs without this when adding a third Portal when a Router exists.
        {
            if(this.portal.portalRouter == null && this.portal.portal == null)
                this.portal.portal = this; //Connects original Portal to newly created Portal.
        }
        
        if(this.portalRouter != null) //Update newly added Portal with connection to router to find other UserAgents.
        {                             // Only works for Portals added without any current UserAgents.
            for(Map.Entry<String, MetaAgent> mapRouting : portalRouter.routing.entrySet())
            {
                this.routingTable.put(mapRouting.getKey(), portalRouter);
                /*pList.routingTable.put(name, this);
                portalRouter.routing.put(name, this);*/
            }
        }
        else if(this.portal != null) //Only update connected Portal
        {
            for(Map.Entry<String, MetaAgent> mapRouting : portal.routingTable.entrySet())
            {
                /*for(Portal pList : portalList)
                {
                    pList.routingTable.put(agent.userName, this);
                }*/

                if(!routingTable.containsKey(mapRouting.getKey()))
                    routingTable.put(mapRouting.getKey(), this.portal);
            }
        }
    }
    
    @Override
    public void messageHandler(Message message)
    {
        //System.out.println("Portal Contains Key: " + routingTable.containsKey(message.getReceiver()));
        System.out.println("Passed: " + this.userName);
        
        if(message.getReceiver().equals(this.userName))
        {
            System.out.println("Message direct to portal: " + message.toString());
        }
        else if(routingTable.containsKey(message.getReceiver()))
        {
            try
            {
                routingTable.get(message.getReceiver()).put(message);
            }catch (InterruptedException ex)
            {
                Logger.getLogger(Portal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
            System.out.println("Portal: " + this.userName + " - No idea who this is for!");
    }
}