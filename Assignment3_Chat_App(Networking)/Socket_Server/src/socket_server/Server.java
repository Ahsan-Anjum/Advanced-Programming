
package socket_server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

/**
 *
 * @author    Muhammad Ahsan Anjum Butt
 * @email     l134169@lhr.nu.edu.pk
 * @section   CS-B
 * @function     
 */
public class Server
{
    public static final int REGISTRATION_PORT = 9091;
    public static final int SERVER_PORT = 9092;
    private static InetAddress IP_Group;

    public static InetAddress getIP_Group()
    {
        if(IP_Group == null)
        {
            try
            {
    //            IP_Group = InetAddress.getByName("225.4.5.6");
                IP_Group = InetAddress.getByName("230.0.0.0");
            }
            catch(UnknownHostException e)
            {

            }
        }
        return IP_Group;
    }
        
    private static Vector<UserThread> clients = new Vector<>();
    
    public static void addClient(UserThread client)
    {
        clients.add(client);
    }
    
	
    public static void requestClientRemoval(UserThread toBeRemoved)
    {
        removeClientRequests.add(toBeRemoved);
    }
    private static void removeClientAndInformOthers(UserThread toBeRemoved)
    {
        clients.remove(toBeRemoved);
        sendMsgToAllClients("User " + toBeRemoved + " has left!");
    }
    
	
    public static void requestSendMsgToAll(String msg)
    {
        msgToAllClientsRequests.add(msg);
    }
    private static void sendMsgToAllClients(String msg)
    {    
        for(UserThread client:clients)
        {
            client.sendMsgToClient(msg);
        }
        if(last10Msgs.size() >= 10)
            last10Msgs.remove(0);
        last10Msgs.add(msg);

    }
    
	
    public static void requestSendMsgToOthers(String msg, UserThread sender)
    {
        msgToOtherClientsRequests.add(new Object[]{msg, sender});
    }
    private static void sendMsgToOtherClients(String msg, UserThread sender)
    {
        for(UserThread client: clients)
        {
            if(!client.equals(sender))
            {
                client.sendMsgToClient(msg);
            }
        }
        if(last10Msgs.size() >= 10)
            last10Msgs.remove(0);
        last10Msgs.add(msg);
    }
    
	
    public static String[] getLast10Msgs()
    {
        return last10Msgs.toArray(new String[last10Msgs.size()]);
    }
    
    
	private static final Vector<String> last10Msgs = new Vector<>(), msgToAllClientsRequests = new Vector<>();
    private static final Vector<UserThread> removeClientRequests = new Vector<>();
    private static final Vector<Object[]> msgToOtherClientsRequests = new Vector<>();
    
    public static void main(String[] args)
    {
        
        Registrar socketRegistrar = new SocketRegistrar();
        socketRegistrar.start();
        
        Admin admin = new Admin();
        admin.start();
        
        
        while(0 == 0)
        {
            if(!msgToAllClientsRequests.isEmpty())
            {
                String msg = msgToAllClientsRequests.remove(0);
                sendMsgToAllClients(msg);
            }
            
            if(!removeClientRequests.isEmpty())
            {
                UserThread toBeRemoved = removeClientRequests.remove(0);
                removeClientAndInformOthers(toBeRemoved);
            }
            
            if(!msgToOtherClientsRequests.isEmpty())
            {
                Object[] request = msgToOtherClientsRequests.remove(0);
                String msg = (String)request[0];
                UserThread sender = (UserThread)request[1];
                sendMsgToOtherClients(msg, sender);
            }
        }
    }
}
