/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

import java.net.Socket;
import java.util.Map;

/**
 *
 * @author t7091808
 */
public class MAS
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException
    {
        
//        //Router router = new Router("Router1");
//        Portal portal1 = new Portal("Portal1",router);
//        Portal portal2 = new Portal("Portal2",router);
//        MetaAgent user1 = new UserAgent("User1",portal1);
//        MetaAgent user2 = new UserAgent("User2",portal1);
//        MetaAgent user3 = new UserAgent("User3",portal2);
//        MetaAgent user4 = new UserAgent("User4",portal2);
//        
//        portal1.updateTable(user1.userName, user1);
//        router.updateTable(user1.userName, portal2);
//        portal1.updateTable(user2.userName, user2);
//        router.updateTable(user2.userName, portal2);
//        portal1.updateTable(user3.userName, user3);
//        portal1.updateTable(user4.userName, user4);
//        
//        
//        portal2.updateTable(user3.userName, user3);
//        router.updateTable(user3.userName, portal1);
//        portal2.updateTable(user4.userName, user4);
//        router.updateTable(user4.userName, portal1);
//        portal2.updateTable(user1.userName, user1);
//        portal2.updateTable(user2.userName, user2);
//        
//        
//        System.out.println(router.routing.containsKey(user1.userName));
//        System.out.println(portal1.routingTable.containsKey(user1.userName));
//        
//        System.out.println(router.routing.containsKey(user2.userName));
//        System.out.println(portal1.routingTable.containsKey(user2.userName));
//        
//        System.out.println(router.routing.containsKey(user3.userName));
//        System.out.println(portal2.routingTable.containsKey(user3.userName));
//        
//        System.out.println(router.routing.containsKey(user4.userName));
//        System.out.println(portal2.routingTable.containsKey(user4.userName));
//        
//        System.out.println(portal1.routingTable.containsKey("User2"));
//        System.out.println(portal2.routingTable.containsKey("User2"));
//        
//        
//        System.out.println("Portal1 HashMap: " + portal1.routingTable.toString());
//        System.out.println("Portal2 HashMap: " + portal2.routingTable.toString());
        
        
//        Message msg = new Message("User3", "appropriate message", "User1");
//        Message msg1 = new Message("User4", "appropriate message", "User1");
//        Message msg2 = new Message("User3", "appropriate message", "User2");
//        Message msg3 = new Message("User4", "appropriate message", "User2");
//        Message msg4 = new Message("User2", "appropriate message", "User3");
//        Message msg5 = new Message("User2", "appropriate message", "User4");
//        Message msg6 = new Message("User1", "appropriate message", "User3");
//        Message msg7 = new Message("User1", "appropriate message", "User4");
//        user1.messageHandler(msg);
//        user1.messageHandler(msg1);
//        user2.messageHandler(msg2);
//        user2.messageHandler(msg3);
//      user3.messageHandler(msg4);
//      user4.messageHandler(msg5);
//      user3.messageHandler(msg6);
         //user4.messageHandler(msg7);
        
        //System.out.println(router.routing.toString());
        //user1.
        
        //Sending a message ebtween two agents on the same Portal.
//        Portal portal1 = new Portal("p1");
//        UserAgent agent1 = new UserAgent("a1", portal1);
//        portal1.addAgent(agent1);
//        
//        UserAgent agent2 = new UserAgent("a2", portal1);
//        portal1.addAgent(agent2);
//        
////        agent1.SendMessage(new Message("a2", "Hi!", "agent1"));
//        
//        //----------------------------------------------------------------------------------
//        //Sending a message between two agents on different portals.
//        
//        Portal portal2 = new Portal("p2", portal1);
//        UserAgent agent3 = new UserAgent("a3", portal2);
//        
//        portal2.addAgent(agent3);
//        
//        System.out.println("Sending from a2 to a3");
//        agent2.SendMessage(new Message("a3", "Hello agent3!", "a2", MessageType.USERMESSAGE));
//        
//        agent3.SendMessage(new Message("a2", "Hello agent2", "a3", MessageType.USERMESSAGE));
//        
//        
//        for(Map.Entry<String, MetaAgent> mapRouting : portal1.routingTable.entrySet())
//        {
//            System.out.println("Portal 1 Key: " + mapRouting.getKey());
//            System.out.println("Portal 1 Value: " + mapRouting.getValue().userName);
//        }
//        
//        for(Map.Entry<String, MetaAgent> mapRouting : portal2.routingTable.entrySet())
//        {
//            System.out.println("Portal 2 Key: " + mapRouting.getKey());
//            System.out.println("Portal 2 Value: " + mapRouting.getValue().userName);
//        }
//        
//        Router router1 = new Router("r1");
//        
//        router1.connectRouter();
//        
//        System.out.println("Listing p1 and p2 router connections...");
//        
//        for(Portal pList : router1.portalList)
//        {
//            System.out.println(pList.getRouter().userName);
//        }
//        
//        System.out.println("Listing the Router's Portal connections for each user agent");
//        System.out.println(router1.routing.isEmpty());
//        for(Map.Entry<String, MetaAgent> mapRouting : router1.routing.entrySet())
//        {
//            System.out.println("Router1's key: " + mapRouting.getKey() + " ----------- Value: " + mapRouting.getValue().userName);
//        }
//        System.out.println("Portal1's connected Portal: " + portal1.portal.userName);
//        System.out.println("Portal 1 routingTable should now point to r1 not p2");
//        for(Map.Entry<String, MetaAgent> mapRouting : portal1.routingTable.entrySet())
//        {
//            System.out.println("Portal 1 Key: " + mapRouting.getKey());
//            System.out.println("Portal 1 Value: " + mapRouting.getValue().userName);
//        }
//        
//        /*agent2.SendMessage(new Message("a3", "Hello from over the Router", "a2"));
//        agent3.SendMessage(new Message("a2", "Nice to hear from you over the Router", "a3"));*/
//        
//        //-----------------------------------------------------------------------------
//        Portal portal3 = new Portal("p3", router1);
//        
//        for(Map.Entry<String, MetaAgent> mapRouting : portal3.routingTable.entrySet())
//        {
//            System.out.println("Portal 3 Key: " + mapRouting.getKey() + " ----------- Value: " + mapRouting.getValue().userName);
//        }
//        
//        UserAgent agent4 = new UserAgent("a4", portal3);
//        
//        portal3.addAgent(agent4);
//        
//        //Test here for each Portal and Router having their routing table updated with new reference.
//        //Router 1: a4 = p3, Portal 1 and 2: a4 = r1, Portal 3: a4 = a4.
//        for(Map.Entry<String, MetaAgent> mapRouting : portal1.routingTable.entrySet())
//        {
//            System.out.println("Portal 1 Key: " + mapRouting.getKey() + " ----------- Value: " + mapRouting.getValue().userName);
//        }
//        
//        for(Map.Entry<String, MetaAgent> mapRouting : portal2.routingTable.entrySet())
//        {
//            System.out.println("Portal 2 Key: " + mapRouting.getKey() + " ----------- Value: " + mapRouting.getValue().userName);
//        }
//        
//        for(Map.Entry<String, MetaAgent> mapRouting : portal3.routingTable.entrySet())
//        {
//            System.out.println("Portal 3 Key: " + mapRouting.getKey() + " ----------- Value: " + mapRouting.getValue().userName);
//        }
//        
//        for(Map.Entry<String, MetaAgent> mapRouting : router1.routing.entrySet())
//        {
//            System.out.println("Router 1 Key: " + mapRouting.getKey() + " ----------- Value: " + mapRouting.getValue().userName);
//        }
        
        //------------------------------------------------------ ICA Refactor ---------------------------------------------------------------------------
        
        /*Router router = new Router("R1");
        
        Portal portal1 = new Portal("P1", router);
        
        Portal portal2 = new Portal("P2", router);
        
        UserAgent user1 = new UserAgent("A1", portal1);
        
        UserAgent user2 = new UserAgent("A2", portal1);
        
        UserAgent user3 = new UserAgent("A3", portal2);
        
        portal1.addAgent(user1);
        
        portal1.addAgent(user2);
        
        portal2.addAgent(user3);
        Thread.sleep(1000);
        for(Map.Entry<String, MetaAgent> mapRouting : portal1.routingTable.entrySet())
        {
            System.out.println("Portal 1 Key: " + mapRouting.getKey() + " ----------- Value: " + mapRouting.getValue().userName);
        }
        
        for(Map.Entry<String, MetaAgent> mapRouting : portal2.routingTable.entrySet())
        {
            System.out.println("Portal 2 Key: " + mapRouting.getKey() + " ----------- Value: " + mapRouting.getValue().userName);
        }
        
        for(Map.Entry<String, MetaAgent> mapRouting : router.routerRouting.entrySet())
        {
            System.out.println("Router 1 Key: " + mapRouting.getKey() + " ----------- Value: " + mapRouting.getValue().userName);
        }

        Thread.sleep(500);
        
        System.out.println(router.routerRouting.isEmpty());
        
        router.localPortals.forEach((p) ->
        {
            System.out.println("Portal name: " + p.userName + " Exists in Router local portals");
        }
        );
        
        for(Map.Entry<String, MetaAgent> mapRouting : router.routerRouting.entrySet())
        {
            System.out.println("Router 1 Key: " + mapRouting.getKey() + " ----------- Value: " + mapRouting.getValue().userName);
        }*/
        
        //user1.SendMessage(new Message("A2", "Hello!", "A1", MessageType.USERMESSAGE));
        //user2.SendMessage(new Message("A3", "Hello A3!", "A2", MessageType.USERMESSAGE));
        //user3.SendMessage(new Message("A1", "Hello A1!", "A3", MessageType.USERMESSAGE));
        
        /*System.out.println("\n---------------------------------- Deleting Users Tests -----------------------------\n");
        
        portal1.removeAgent(user3);
        portal2.removeAgent(user3);
        
        Thread.sleep(2000);
        
        for(Map.Entry<String, MetaAgent> mapRouting : portal1.routingTable.entrySet())
        {
            System.out.println("Portal 1 Key: " + mapRouting.getKey() + " ----------- Value: " + mapRouting.getValue().userName);
        }
        
        for(Map.Entry<String, MetaAgent> mapRouting : portal2.routingTable.entrySet())
        {
            System.out.println("Portal 2 Key: " + mapRouting.getKey() + " ----------- Value: " + mapRouting.getValue().userName);
        }
        
        for(Map.Entry<String, MetaAgent> mapRouting : router.routerRouting.entrySet())
        {
            System.out.println("Router 1 Key: " + mapRouting.getKey() + " ----------- Value: " + mapRouting.getValue().userName);
        }*/
        
        //Throws null pointer exception because user3's Portal has been deleted. User3 would be deleted if main() wasn't referencing it.
        //user3.SendMessage(new Message("A1", "Hello I should be deleted", "A3", MessageType.USERMESSAGE));
        
//        Router router = new Router("R1");
//        
//        Portal portal = new Portal("localPortal", router);
//        
//        UserAgent agent = new UserAgent("Ben", portal);
//        
//        portal.addAgent(agent);
//        
//        Thread.sleep(10000);
//        
//        System.out.println(router.networkPortals.isEmpty());
//        
//        for(Map.Entry<String, Socket> map : router.networkPortals.entrySet())
//        {
//            System.out.println("Map Key: " + map.getKey() + "---------------- Value: " + (Socket)map.getValue());
//        }
//        
//        for(Map.Entry<String, MetaAgent> map : portal.routingTable.entrySet())
//        {
//            System.out.println("Map Key: " + map.getKey() + "---------------- Value: " + map.getValue().userName);
//        }
//        
//        agent.SendMessage(new Message("Jacob", "Please work", "Ben", MessageType.USERMESSAGE));
        
        Portal portal = new Portal("smd123", "152.105.67.142", 8500);
        UserAgent agent = new UserAgent("Jacob", portal);
        Thread.sleep(500);
        portal.addAgent(agent);
        Thread.sleep(2000);
        Message message = new Message("Ben", "Space docking crew", "Jacob", MessageType.USERMESSAGE);
        agent.SendMessage(message);
    }
}