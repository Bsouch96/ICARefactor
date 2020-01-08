package mas;

/**
* The UserAgent class extends MetaAgent to make us of the UserName and 
* UserPortal methods.
* @author Ben Souch, Jacob Jardine, Teddy Teasdale, Michael Wasell
* @version #1.0
* @since 2019/11/06
*/
public class UserAgent extends MetaAgent
{
    /**
     * The UserAgent() makes use of the userName() and userPortal().
     * @param userName - Method pulled from MetaAgent to create UserAgent 
     * @param userPortal - Method pulled from MetaAgent to create UserAgent
     */
    public UserAgent(String userName, Portal userPortal) 
    {
        super(userName, userPortal);
    }
    
    /**
     * The messageHandler() makes use of the message() by checking if message is null
     * and printing message to a string.
     * @param message can be either a user message or a user message to be added to the Queue.
     */
    @Override
    public void messageHandler(Message message)
    {
        if(message == null)
            return;
        else if(message.getReceiver().equals(this.userName))
            System.out.println(message.toString());
    }
    
    /**
     * SendMessage() sends a message from the portal. 
     * @param message of type message (USERMESSAGE, DELETEUSERMESSAGE, ADDUSERMESSAGE, SHAREROUTINGTABLE).
     * @throws IllegalArgumentException ie.
     * @see IllegalArgumentException
     */
    public void SendMessage(Message message)
    {
        if(message == null)
            return;
        else
        {
            try
            {
                portal.put(message);
            }catch(InterruptedException ie)
            {
                System.out.println("Error!");
            }
        }
    }

}