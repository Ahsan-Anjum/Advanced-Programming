package socket_server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;

/**
 *
 * @author   Muhammad Ahsan Anjum Butt
 * @email    l134169@lhr.nu.edu.pk
 * @section  CS-B
 * @function    
 */
public class SocketRegistrar extends Registrar
{
    private boolean joined;
    private MulticastSocket groupSocket;
	private NetworkInterface networkCard;
    
    public SocketRegistrar()
    {
        joined = false;
		setName("SocketRegistrar");
    }
    
    @Override
    public void run()
    {
        joinMulticastGroup();
        if(joined)
        {
            registerClients();
            leaveMulticastGroup();
        }
    }
	
    private void joinMulticastGroup()
    {
        try
        {
			groupSocket = new MulticastSocket(Server.REGISTRATION_PORT);
			
			//	[1]
			SocketAddress address = new InetSocketAddress(Server.getIP_Group(), Server.REGISTRATION_PORT);
            networkCard = NetworkInterface.getByName("wlp8s0");
			groupSocket.joinGroup(address, networkCard);
            joined = true;
        }
        catch(IOException e)
        {
            
        }
    }
	
    @Override
    public void registerClients()
    {
		while (0 == 0)
		{
            try
            {
		        byte[] buffer = new byte[1024];
                DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);
                groupSocket.receive(datagram);
                String client_IP_Port_Str = new String(datagram.getData(), 0, datagram.getLength());
				String[] splitRes = client_IP_Port_Str.split("@");
				String clientIP_Str = splitRes[0];
				int clientPort = Integer.parseInt(splitRes[1]);
				
                InetAddress clientIP = InetAddress.getByName(clientIP_Str);
                
				SocketUserThread newClient = new SocketUserThread(clientIP, clientPort);
                newClient.start();
            }
            catch(IOException e)
            {
                
            }
        }
    }
    
    private void leaveMulticastGroup()
    {
        try
        {
            groupSocket.leaveGroup(Server.getIP_Group());
            groupSocket.close();
        }
        catch (IOException ex)
        {
            
        }
    }
}

//	REFERENCES
//	[1]	https://stackoverflow.com/questions/8471403/java-multicast-sending-data-not-receiving