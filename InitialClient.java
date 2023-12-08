import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.SwingUtilities;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Project 5 - InitialClient.java
 * 
 * Class that handles initial database interactions with user login.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version December 7, 2023
 */
public class InitialClient {

    private final String serverErrorMsg = "Error occured when communicating with server";
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    /**
     * Constructs an InitialClient instance using the output and input streams initialized via the socket
     * 
     * @param oos The object output stream to utilize to send data to the server
     * @param ois The object input stream to utilize to send data to the server
     * @throws IOException
     */
    public InitialClient(ObjectOutputStream oos, ObjectInputStream ois) throws IOException {
        this.oos = oos;
        this.ois = ois;
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("localhost", Server.PORT_NUMBER);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        oos.flush();
        InitialClient initialClient = new InitialClient(oos, ois);
        initialClient.start();
    }

    /**
     * Ground-zero of the application from where execution begins.
     */
    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserGUI(InitialClient.this);
            }
        });
    }

    /**
     * Handles communication with the server for a logging an existing user back into the application
     * 
     * @param email The email the user logged in with
     * @param password The password the user logged in with
     */
    public void attemptLogin(String email, String password) {
        try {
            String[] serverRequest = {"LOGIN", email, password};
            oos.writeObject(serverRequest);
            oos.flush();

            String response = (String) ois.readObject();
            if (response.equals("Customer Connection to Server Established")) {
                String userEmail = (String) ois.readObject();
                new CustomerClient(oos, ois).homepage(userEmail);
            } else if (response.equals("Seller Connection to Server Established")) {
                String userEmail = (String) ois.readObject();
                new SellerClient(oos, ois).homepage(userEmail);
            } else {
                new ErrorMessageGUI(response);
                new LoginGUI(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
            new ErrorMessageGUI(serverErrorMsg);
        } catch (Exception e) {
            System.out.println("Internal Server Communication Error");
            e.printStackTrace();
        }
    }

    /**
     * Handles communication with the server for creating a new customer account
     * 
     * @param email The email the user provided
     * @param password The password the user provided
     */
    public void attemptCreateNewSellerAccount(String email, String password) {
        try {
            String[] serverRequest = {"CREATE_SELLER", email, password};
            oos.writeObject(serverRequest);
            oos.flush();

            String response = (String) ois.readObject();
            if (response.equals("Seller Connection to Server Established")) {
                new SellerClient(oos, ois).homepage((String) ois.readObject());
            } else {
                new ErrorMessageGUI(response);
                new SignUpGUI(this);
            }

        } catch (IOException e) {
            new ErrorMessageGUI(serverErrorMsg);
        } catch (Exception e) {
            System.out.println("Internal Server Communication Error");
            e.printStackTrace();
        }
    }

    /**
     * Handles communication with the server for creating a new seller account
     * 
     * @param email The email the user provided
     * @param password The password the user provided
     */
    public void attemptCreateNewCustomerAccount(String email, String password) {
        try {
            String[] serverRequest = {"CREATE_CUSTOMER", email, password};
            oos.writeObject(serverRequest);
            oos.flush();

            String response = (String) ois.readObject();
            if (response.equals("Customer Connection to Server Established")) {
                new CustomerClient(oos, ois).homepage((String) ois.readObject());
            } else {
                new ErrorMessageGUI(response);
                new SignUpGUI(this);
            }
        } catch (IOException e) {
            new ErrorMessageGUI(serverErrorMsg);
        } catch (Exception e) {
            System.out.println("Internal Server Communication Error");
            e.printStackTrace();
        }
    }
}