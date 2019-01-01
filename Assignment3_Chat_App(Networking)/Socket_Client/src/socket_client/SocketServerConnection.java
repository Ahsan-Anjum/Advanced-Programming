package socket_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author   Muhammad Ahsan Anjum Butt
 * @email    l134169@lhr.nu.edu.pk
 * @section  CS-B
 * @function    
 */
public class SocketServerConnection extends ServerConnection
{
    private final Client GUI;
    
    private ServerSocket serverSocket;
    private Socket connectionSocket;
    DataInputStream inputStream = null;
    DataOutputStream outputStream = null;
    private boolean wantToDisconnect;
    
    public static final int REGISTRATION_PORT = 9091;
    public static final int SERVER_PORT = 9092;
    private static InetAddress IP_Group;
    static 
    {
        try
        {
            IP_Group = InetAddress.getByName("230.0.0.0");
        }
        catch(UnknownHostException e)
        {
            
        }
    }
    
    public SocketServerConnection(Client GUI, int portNo)
    {
        this.GUI = GUI;
        wantToDisconnect = registered = false;
        clientPort = portNo;
    }
    
    @Override
    public void run()
    {
        register();
        if(registered)
            receive();
    }
    
    @Override
    protected void register()
    {
        try
        {            
            MulticastSocket groupSocket = new MulticastSocket(REGISTRATION_PORT);
			
            //  [1]
//            SocketAddress address = new InetSocketAddress(IP_Group, REGISTRATION_PORT);
//            NetworkInterface NI = NetworkInterface.getByName("wlp8s0");
//            groupSocket.joinGroup(address, NI);
            
            byte[] buffer = (toString()).getBytes();
            DatagramPacket dgram = new DatagramPacket(buffer, buffer.length, IP_Group, REGISTRATION_PORT);
            groupSocket.send(dgram);
//            groupSocket.leaveGroup(address, NI);
            groupSocket.close();

            serverSocket = new ServerSocket(clientPort);
            connectionSocket = serverSocket.accept();
            serverSocket.close();
            registered = connectionSocket.isConnected();
            outputStream = new DataOutputStream(connectionSocket.getOutputStream());
            inputStream = new DataInputStream(connectionSocket.getInputStream());
            
            GUI.setTitle("client @ port # " + clientPort);
            GUI.getConnectDisconnectButton().setEnabled(true);
            GUI.getConnectDisconnectButton().setText("Disconnect");
            GUI.enableMsgSendingControls(registered);
            
        }
        catch(IOException e)
        {
            GUI.getConnectDisconnectButton().setEnabled(true);
            GUI.getConnectDisconnectButton().setText("Connect");
        }
        
    }

    @Override
    public void send(String msg)
    {
        try
        {
            outputStream.writeUTF(msg);
        }
        catch (IOException ex)
        {
        
        }
    }

    @Override
    protected void receive()
    {
        try
        {
            while(!wantToDisconnect)
            {
                String msg = inputStream.readUTF();
                GUI.appendToMsgsTextArea(msg);
            }
        }
        catch(IOException e)
        {

        }
        finally
        {
            try
            {
                connectionSocket.close();
            }
            catch(IOException e)
            {
                
            }
            
//            try
//            {
//                serverSocket.close();
//            }
//            catch(IOException e)
//            {
//
//            }
        }
    }

    @Override
    public void disconnect()
    {
        wantToDisconnect = true;
        send(this + "disconnect");
        try
        {
            connectionSocket.close();
        }
        catch (IOException ex)
        {
            
        }

//        try
//        {
//            serverSocket.close();
//        }
//        catch(IOException e)
//        {
//
//        }
        registered = false;
        GUI.setTitle("");
        GUI.getConnectDisconnectButton().setText("Connect");
        GUI.enableMsgSendingControls(false);
    }
}

//  REFERENCES
//  [1] https://stackoverflow.com/questions/8471403/java-multicast-sending-data-not-receiving