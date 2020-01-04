/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

/**
 *
 * @author t7091808
 */
public class UserAgent extends MetaAgent
{
    public UserAgent(String userName, Portal userPortal) 
    {
        super(userName, userPortal);
    }
    
    @Override
    public void messageHandler(Message message)
    {
        if(message.getReceiver().equals(this.userName))
            System.out.println(message.toString());
    }
    
    public void SendMessage(Message message)
    {
        portal.add(message);
    }
}