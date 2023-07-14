package TCPSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This program demonstrates a TCP server
 *
 * @author rm005287
 * @version 1.0
 * @since 2021-11-21
 */
public class TCPServer {

    private ServerSocket server;

    /**
     * The TCPServer constructor initiate the socket
     *
     * @param ipAddress
     * @param port
     * @throws Exception
     */
    public TCPServer(String ipAddress, int port) throws Exception {
        if (ipAddress != null && !ipAddress.isEmpty()) {
            this.server = new ServerSocket(port, 1, InetAddress.getByName(ipAddress));
        } else {
            this.server = new ServerSocket(0, 1, InetAddress.getLocalHost());
        }
    }

    /**
     * The listen method listen to incoming client's datagrams and requests
     *
     * @throws Exception
     */
    private void listen() throws Exception {
        // Listens to incoming client's requests via the ServerSocket

        String data = null;
        Socket client = this.server.accept();
        String clientAddress = client.getInetAddress().getHostAddress();
        System.out.println("\r\nClient connected! " + "from the address " + clientAddress);

        // Print received datagrams from client
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        // if statement used because we only want one input rather than multiple to end transmission between client and server
        if ((data = in.readLine()) != null) {
            System.out.println("\r\nMessage received from " + clientAddress + ": " + data);
            client.sendUrgentData(0);
            System.out.println("\r\nEnd of transmission from client");
        }

    }

    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }

    public int getPort() {
        return this.server.getLocalPort();
    }

    public static void main(String[] args) throws Exception {
        // Set the server address (IP) and port number

        String serverIP = "127.0.0.1"; // Local loop back IP 
        int port = 7077;

        if (args.length > 0) {
            serverIP = args[0];
            port = Integer.parseInt(args[1]);
        }
        // Calls the constructor and passes the IP and port

        TCPServer server = new TCPServer(serverIP, port);
        System.out.println("\r\nRunning Server: "
                + "Host=" + server.getSocketAddress().getHostAddress()
                + " Port=" + server.getPort());
        server.listen();
    }

}
