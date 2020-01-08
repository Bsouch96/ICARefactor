/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

/**
 *
 * @author V8178742
 */
public class MAS
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException
    {
        Router router = new Router("R1");
        
        Portal portal = new Portal("localPortal", router);
        Portal portal2 = new Portal("localPortal2", router);
        
        UserAgent agent = new UserAgent("Ben", portal);
        UserAgent agent2 = new UserAgent("Agent2", portal);
        UserAgent agent3 = new UserAgent("Agent3", portal);
        
        portal.addAgent(agent);
        portal2.addAgent(agent2);
        portal2.addAgent(agent3);
        
        Thread.sleep(10000);
        
        agent.SendMessage(new Message("External User", "Please work", "Ben", MessageType.USERMESSAGE));
        
        for (int i = 0; i < 100; i++)
        {
            agent.SendMessage(new Message("External User", "Please work", "Ben", MessageType.USERMESSAGE));
        }
        
        for (int i = 0; i < 100; i++)
        {
            agent2.SendMessage(new Message("Ben", "NextMessage", "Agent2", MessageType.USERMESSAGE));//Local user agent2 to local user Ben
            agent3.SendMessage(new Message("Agent2", "AnotherMessage", "Agent3", MessageType.USERMESSAGE));//Local user agent3 to local user agent 2
            agent2.SendMessage(new Message("External User", "Please work", "Agent2", MessageType.USERMESSAGE));
            agent3.SendMessage(new Message("External User2", "Please work", "Agent3", MessageType.USERMESSAGE));
        }
    }
}