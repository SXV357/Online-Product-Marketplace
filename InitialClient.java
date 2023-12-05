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
 *         Miller, Oliver Long
 * 
 * @version December 3, 2023
 */
public class InitialClient {

    private final String SERVER_ERROR_MSG = "Error occured when communicating with server";
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

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

    //First method to call: initializes entire program through login GUI
    public void start() {
        //constructor calls the GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserGUI(InitialClient.this);
            }
        });
    }

        public void attemptLogin(String email, String password){
        try {
            String[] serverRequest = {"LOGIN", email, password};
            oos.writeObject(serverRequest);
            oos.flush();

            String userEmail = (String) ois.readObject();

            String response = (String) ois.readObject();
            if (response.equals("Customer Connection to Server Established")) {
                new CustomerClient(oos, ois).homepage(userEmail);
            } else if (response.equals("Seller Connection to Server Established")) {
                new SellerClient(oos, ois).homepage(userEmail);
            } else {
                new ErrorMessageGUI(response);
                new LoginGUI(this);
            }
    
        } catch (IOException e) {
            e.printStackTrace();
            new ErrorMessageGUI(SERVER_ERROR_MSG);
        } catch (Exception e) {
            System.out.println("Internal Server Communication Error");
            e.printStackTrace();
        }
    }

    public void attemptCreateNewSellerAccount(String email, String password) {
        try {
            String[] serverRequest = {"CREATE_SELLER", email, password};
            oos.writeObject(serverRequest);
            oos.flush();

            String userEmail = (String) ois.readObject();

            String response = (String) ois.readObject();
            if (response.equals("Seller Connection to Server Established")) {
                new SellerClient(oos, ois).homepage(userEmail);
            } else {
                new ErrorMessageGUI(response);
                new SignUpGUI(this);
            }

        } catch (IOException e) {
            new ErrorMessageGUI(SERVER_ERROR_MSG);
        } catch (Exception e) {
            System.out.println("Internal Server Communication Error");
            e.printStackTrace();
        }
    }

    public void attemptCreateNewCustomerAccount(String email, String password){
        try {
            String[] serverRequest = {"CREATE_CUSTOMER", email, password};
            oos.writeObject(serverRequest);
            oos.flush();

            String userEmail = (String) ois.readObject();

            String response = (String) ois.readObject();
            if (response.equals("Customer Connection to Server Established")) {
                new CustomerClient(oos, ois).homepage(userEmail);
            } else {
                new ErrorMessageGUI(response);
                new SignUpGUI(this);
            }
        } catch (IOException e) {
            new ErrorMessageGUI(SERVER_ERROR_MSG);
        } catch (Exception e) {
            System.out.println("Internal Server Communication Error");
            e.printStackTrace();
        }
    }
}