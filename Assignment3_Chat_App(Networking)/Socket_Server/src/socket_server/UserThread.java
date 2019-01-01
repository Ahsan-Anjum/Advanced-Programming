package socket_server;

import java.net.InetAddress;

/**
 *
 * @author   Muhammad Ahsan Anjum Butt
 * @email    l134169@lhr.nu.edu.pk
 * @section  CS-B
 * @function    
 */
public abstract class UserThread extends Thread
{
    protected InetAddress clientIP;
    protected int clientPort;
    protected boolean connected, clientWantsToDisconnect;

    public InetAddress getClientIP()
    {
        return clientIP;
    }

    public int getPort()
    {
        return clientPort;
    }
    
    public UserThread(InetAddress clientIP, int clientPort)
    {
        this.clientIP = clientIP;
        this.clientPort = clientPort;
		setName(this.toString());
        clientWantsToDisconnect = connected = false;
    }
    
    public abstract void connectToClient();
    
    public abstract void listenToClient();
    
    public abstract void sendMsgToClient(String msg);
    
    public String toString()
    {
        return clientIP.getHostAddress() + "@" + clientPort;
    }
    
//    public boolean equals(UserThread other)
//    {
//        return (clientIP.getHostAddress().equals(other.clientIP.getHostAddress()) && clientPort == other.clientPort);
//    }
}
