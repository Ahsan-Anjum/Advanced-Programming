package socket_server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 *
 * @author   Muhammad Ahsan Anjum Butt
 * @email    l134169@lhr.nu.edu.pk
 * @section  CS-B
 * @function    
 */
public class Admin extends Thread
{
    private static final InetAddress ADMIN_IP = InetAddress.getLoopbackAddress();
    private static final int ADMIN_PORT = 9090;
    
	
    @Override
    public void run()
    {
        try
        {
            //  [1]
            HttpServer server = HttpServer.create(new InetSocketAddress(ADMIN_IP, ADMIN_PORT), 0);
            server.createContext("/", new HttpHandler()
            {
                @Override                
                public void handle(HttpExchange httpExchange) throws IOException
                {
                    String[] last10Msgs = Server.getLast10Msgs();
                    StringBuilder response = new StringBuilder();
                    for(int i = 0; i < last10Msgs.length; i++)
                    {
                        response.append(last10Msgs[i]).append("\n\n\n");
                    }
                    httpExchange.sendResponseHeaders(200, response.length());
                    OutputStream os = httpExchange.getResponseBody();
                    os.write(response.toString().getBytes());
                    os.close();
                }
            });
            server.setExecutor(null);
            server.start();
        }
        catch(IOException e)
        {
            
        }
    }
	
}

//  REFERENCES
//  [1] https://stackoverflow.com/questions/3732109/simple-http-server-in-java-using-only-java-se-api