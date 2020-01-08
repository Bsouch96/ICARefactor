package mas;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
* The Message class is used to create different types of messages.
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
     * @param socket
     * @throws IOException 
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
     * #
     * @param handle
     * @param socket
     * @throws IOException 
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
     * 
     * @return 
     */
    public String getHandle()
    {
        return this.handle;
    }
    
    /**
     * 
     * @param handle 
     */
    public void setHandle(String handle)
    {
        this.handle = handle;
    }
    
    /**
     * 
     * @return 
     */
    public Socket getSocket()
    {
        return SOCKET;
    }
    
    /**
     * 
     * @param message
     * @throws IOException 
     */
    public void sendClientMessage(Message message) throws IOException
    {
        OBJECTOUTPUTSTREAM.writeObject(message);
        OBJECTOUTPUTSTREAM.flush();
    }
    
    /**
     * 
     * @return
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public Message receiveClientMessage() throws IOException, ClassNotFoundException
    {
        return (Message) OBJECTINPUTSTREAM.readObject();
    }
    
    /**
     * 
     * @return
     * @throws IOException 
     */
    public boolean messageWaiting() throws IOException
    {
        return OBJECTINPUTSTREAM.available() > 0;
    }
}
