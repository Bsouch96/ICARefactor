/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author v8178742
 */
public class Connection
{
    private String handle;
    private final Socket SOCKET;
    private final OutputStream OUTPUTSTREAM;
    private final ObjectOutputStream OBJECTOUTPUTSTREAM;
    private final InputStream INPUTSTREAM;
    private final ObjectInputStream OBJECTINPUTSTREAM;
    
    Connection(Socket socket) throws IOException
    {
        this.SOCKET = socket;
        
        OUTPUTSTREAM = SOCKET.getOutputStream();
        OBJECTOUTPUTSTREAM = new ObjectOutputStream(OUTPUTSTREAM);
        INPUTSTREAM = SOCKET.getInputStream();
        OBJECTINPUTSTREAM = new ObjectInputStream(INPUTSTREAM);
        this.handle = null;
    }
    
    Connection(String handle, Socket socket) throws IOException
    {
        this.handle = handle;
        this.SOCKET = socket;
        
        OUTPUTSTREAM = SOCKET.getOutputStream();
        OBJECTOUTPUTSTREAM = new ObjectOutputStream(OUTPUTSTREAM);
        INPUTSTREAM = SOCKET.getInputStream();
        OBJECTINPUTSTREAM = new ObjectInputStream(INPUTSTREAM);
    }
    
    public String getHandle()
    {
        return this.handle;
    }
    
    public void setHandle(String handle)
    {
        this.handle = handle;
    }
    
    public Socket getSocket()
    {
        return SOCKET;
    }
    
    public void sendClientMessage(Message message) throws IOException
    {
        OBJECTOUTPUTSTREAM.writeObject(message);
        OBJECTOUTPUTSTREAM.flush();
    }
    
    public Message receiveClientMessage() throws IOException, ClassNotFoundException
    {
        return (Message) OBJECTINPUTSTREAM.readObject();
    }
    
    public boolean messageWaiting() throws IOException
    {
        return INPUTSTREAM.available() > 0;
    }
}
