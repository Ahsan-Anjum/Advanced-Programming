package socket_client;

import java.io.File;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author   Muhammad Ahsan Anjum Butt
 * @email    l134169@lhr.nu.edu.pk
 * @section  CS-B
 * @function    
 */
public abstract class ServerConnection extends Thread
{
    //  [1]
    public static final String CURRENT_WORKING_DIRECTORY = ( new File("").getAbsolutePath() ) + "/";
    
    protected int clientPort;
    protected boolean registered;
    protected static InetAddress clientIP;
    static
    {
        //	[2]
        try(final DatagramSocket socket = new DatagramSocket())
        {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            clientIP = socket.getLocalAddress();
            socket.disconnect();
        }
        catch (UnknownHostException ex)
        {
            
        }
        catch (SocketException ex)
        {
            
        }
    }
    
    protected abstract void register();
            
    public abstract void send(String msg);
    
    protected abstract void receive();

    public abstract void disconnect();
    
    @Override
    public String toString()
    {
        return clientIP.getHostAddress() + "@" + clientPort;
    }
    
//    protected Integer getNextPortNo()
//    {
//        try
//        {
//            File file = new File(CURRENT_WORKING_DIRECTORY + "clientPort.txt");
//            FileReader reader = new FileReader(file);
//            BufferedReader buffReader = new BufferedReader(reader);
//            clientPort = Integer.parseInt(buffReader.readLine());
//            buffReader.close();
//            
//            FileWriter writer = new FileWriter(file);
//            writer.write("" + (clientPort + 1));
//            writer.close();
//        }
//        catch (FileNotFoundException ex)
//        {
//            
//        }
//        catch (IOException ex)
//        {
//            
//        }
//        return clientPort;
//    }

}

//  REFERENCES
//  [1] https://stackoverflow.com/questions/3153337/get-current-working-directory-in-java
//  [2]	https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java