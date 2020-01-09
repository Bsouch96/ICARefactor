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
public class MAS1
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException
    {
        Portal portal = new Portal("External P1", "80.6.168.142", 8500);
        Portal portal2 = new Portal("External P2", "80.6.168.142", 8500);
        
        UserAgent agent = new UserAgent("External User", portal);
        UserAgent agent2 = new UserAgent("External User2", portal2);
        
        portal.addAgent(agent);
        portal2.addAgent(agent2);
        
        Thread.sleep(10000);
        
        for (int i = 0; i < 100; i++)
        {
            agent.SendMessage(new Message("Ben", "Hello", "External User", MessageType.USERMESSAGE));
            agent2.SendMessage(new Message("External User", "Hello", "External User2", MessageType.USERMESSAGE));
            agent2.SendMessage(new Message("Ben", "Hello", "Hello", MessageType.USERMESSAGE));
        }
    }
}