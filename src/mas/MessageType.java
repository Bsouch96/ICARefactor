package mas;

import java.io.Serializable;

/**
 * MessageType implements the different types of messages.
 * @author Ben Souch, Jacob Jardine, Teddy Teasdale, Michael Wasell
 * @version #1.0
 * @since   2019/11/06
 * 
 */
public enum MessageType implements Serializable
{
    USERMESSAGE,
    DELETEUSERMESSAGE,
    ADDUSERMESSAGE,
    SHAREROUTINGTABLE,
    HELLO,
    HELLOACK
}
