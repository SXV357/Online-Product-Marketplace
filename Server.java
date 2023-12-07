import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Project 5 - Server.java
 * 
 * Class that handles server host and port connection and thread allocation.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version December 7, 2023
 */
public class Server {

    public static final int PORT_NUMBER = 4242;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
            }
        }
    }
}