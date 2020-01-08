package mas;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
* The Connection class is used to create the network connections.
* @author Ben Souch, Jacob Jardine, Teddy Teasdale, Michael Wasell
* @version #1.0
* @since 2019/11/06
*/
public class Connection
{
    private String handle;
    private final Socket SOCKET;
    private final OutputStream OUTPUTSTREAM;
    private final ObjectOutputStream OBJECTOUTPUTSTREAM;
    private final InputStream INPUTSTREAM;
    private final ObjectInputStream OBJECTINPUTSTREAM;
    
    /**
     * 
     * @param socket connection to handle network.
     * @throws IOException 
     * @see IOException.
     */
    Connection(Socket socket) throws IOException
    {
        this.SOCKET = socket;
        
        OUTPUTSTREAM = SOCKET.getOutputStream();
        OBJECTOUTPUTSTREAM = new ObjectOutputStream(OUTPUTSTREAM);
        INPUTSTREAM = SOCKET.getInputStream();
        OBJECTINPUTSTREAM = new ObjectInputStream(INPUTSTREAM);
        this.handle = null;
    }
    
    /**
     * 
     * @param handle produces the objects name.
     * @param socket connection to handle network
     * @throws IOException 
     * @see IOException.
     */
    Connection(String handle, Socket socket) throws IOException
    {
        this.handle = handle;
        this.SOCKET = socket;
        
        OUTPUTSTREAM = SOCKET.getOutputStream();
        OBJECTOUTPUTSTREAM = new ObjectOutputStream(OUTPUTSTREAM);
        INPUTSTREAM = SOCKET.getInputStream();
        OBJECTINPUTSTREAM = new ObjectInputStream(INPUTSTREAM);
    }
    
    /**
     * getHandle() to return the handle.
     * @return handle
     */
    public String getHandle()
    {
        return this.handle;
    }
    
    /**
     * setHandle() to return the handle.
     * @param handle produces the objects name.
     */
    public void setHandle(String handle)
    {
        this.handle = handle;
    }
    
    /**
     * getSocket() to return the handle.
     * @return getSocket()
     */
    public Socket getSocket()
    {
        return SOCKET;
    }
    
    /**
     * 
     * @param message passes message type USERMESSAGE, DELETEUSERMESSAGE, ADDUSERMESSAGE, SHAREROUTINGTABLE, HELLO, HELLOACK
     * @throws IOException 
     * @see IOException.
     */
    public void sendClientMessage(Message message) throws IOException
    {
        OBJECTOUTPUTSTREAM.writeObject(message);
        OBJECTOUTPUTSTREAM.flush();
    }
    
    /**
     * receiveClientMessage() to return OBJECTINPUTSTREAM.
     * @return 
     * @throws IOException
     * @see IOException.
     * @throws ClassNotFoundException
     * @see ClassNotFoundException.
     */
    public Message receiveClientMessage() throws IOException, ClassNotFoundException
    {
        return (Message) OBJECTINPUTSTREAM.readObject();
    }
    
    /**
     * messageWaiting() to return OBJECTINPUTSTREAM
     * @return OBJECTINPUTSTREAM
     * @throws IOException 
     * @see IOException.
     */
    public boolean messageWaiting() throws IOException
    {
        return OBJECTINPUTSTREAM.available() > 0;
    }
}
