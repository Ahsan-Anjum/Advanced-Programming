package socket_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author   Muhammad Ahsan Anjum Butt
 * @email    l134169@lhr.nu.edu.pk
 * @section  CS-B
 * @function    
 */

public class SocketUserThread extends UserThread
{
    private Socket socket;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
    
    public SocketUserThread(InetAddress clientIP, int clientPort)
    {
        super(clientIP, clientPort);
		inputStream = null;
		outputStream = null;
    }
	
    @Override
    public void run()
    {
        connectToClient();
        if(connected)
        {
            listenToClient();
            Server.requestClientRemoval(this);
        }
    }
    
    @Override
    public void connectToClient()
    {
        try
        {
            socket = new Socket(clientIP, clientPort, null, Server.SERVER_PORT);
            connected = true;
			outputStream = new DataOutputStream(socket.getOutputStream());
			inputStream = new DataInputStream(socket.getInputStream());
            Server.addClient(this);
            String msg = "User " + this + " has been added!";
            Server.requestSendMsgToAll(msg);
        }
        catch(IOException e)
        {
			e.printStackTrace();
			try
			{
				socket = new Socket(clientIP, clientPort, InetAddress.getLoopbackAddress(), Server.SERVER_PORT);
				connected = true;
				outputStream = new DataOutputStream(socket.getOutputStream());
				inputStream = new DataInputStream(socket.getInputStream());
				Server.addClient(this);
				String msg = "User " + this + " has been added!";
				Server.requestSendMsgToAll(msg);
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
        }
    }
    
    @Override
    public void sendMsgToClient(String msg)
    {
        try
        {
            outputStream.writeUTF(msg);
        }
        catch(IOException e)
        {
            
        }
    }
    
    @Override
    public void listenToClient()
    {
        try
        {
            while(!clientWantsToDisconnect)
            {
                String clientSentMsg = inputStream.readUTF();
                if(clientSentMsg.equals(this + "disconnect"))
                    clientWantsToDisconnect = true;
                else
                    Server.requestSendMsgToOthers(clientSentMsg, this);
            }
        }
        catch(IOException e)
        {

        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(IOException e)
            {
                
            }
        }
    }
    
}
