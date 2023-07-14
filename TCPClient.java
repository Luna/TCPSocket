package TCPSocket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * This program demonstrates a TCP client
 *
 * @author rm005287
 * @version 1.0
 * @since 2021-11-21
 */
public class TCPClient {

    private Socket tcpSocket;
    private InetAddress serverAddress;
    private int serverPort;
    private Scanner scanner;

    /**
     * @param serverAddress
     * @param serverPort
     * @throws Exception
     */
    private TCPClient(InetAddress serverAddress, int serverPort) throws Exception {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;

        //Initiated the connection with the server using Socket. 
        //Created a stream socket and connected it to the specified port number at the specified IP address.
        this.tcpSocket = new Socket(this.serverAddress, this.serverPort);

        this.scanner = new Scanner(System.in);
    }

    /**
     * The start method connect to the server and datagrams
     *
     * @throws IOException
     */
    private void start() throws IOException {
        String input;
        //PrintWriter from an existing OutputStream (i.e., tcpSocket). 
        //This convenience constructor creates the necessary intermediateOutputStreamWriter, which will convert characters into bytes using the default character encoding
        //Doesn't look so that client cannot keep sending datagrams to server

        while (true) {

            System.out.println("\r\nEnter your message");
            input = scanner.nextLine();
            PrintWriter output = new PrintWriter(this.tcpSocket.getOutputStream(), true);
            output.println(input);
            output.flush();
            System.out.println("\r\nMessage sent!");
            // break clause to end transmission between server and client
            System.out.println("\r\nDisconnected from the server!");
            break;
        }
    }

    public static void main(String[] args) throws Exception {
        // Sets the server address (IP) and port number

        InetAddress serverIP = InetAddress.getByName("127.0.0.1"); // Local loop back IP 
        int port = 7077;

        if (args.length > 0) {
            serverIP = InetAddress.getByName(args[0]);
            port = Integer.parseInt(args[1]);
        }

        // Calls the constructor and pass the IP and port
        TCPClient client = new TCPClient(serverIP, port);
        System.out.println("\r\nConnected to Server: " + client.tcpSocket.getInetAddress());
        client.start();
    }
}
