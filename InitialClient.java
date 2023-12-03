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
 * @version December 2, 2023
 */
public class InitialClient {

    private final String SERVER_ERROR_MSG = "Error occured when communicating with server";
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public InitialClient(Socket socket) throws IOException {
        this.socket = socket;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.oos.flush();
        this.ois = new ObjectInputStream(socket.getInputStream());
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("localhost", Server.PORT_NUMBER);
        InitialClient initialClient = new InitialClient(socket);
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

            boolean loginSuccessful = true;
            loginSuccessful = (boolean) ois.readObject();

            if (loginSuccessful) {
                //get user object from server
                Object incoming;
                Customer customer = null;
                Seller seller = null;
                try {
                    incoming = ois.readObject();
                    if (incoming instanceof Customer) {
                        customer = (Customer) incoming;
                        CustomerClient customerClient = new CustomerClient(socket, customer);
                        customerClient.homepage();  
                    } else {
                        seller = (Seller) incoming;
                        SellerClient sellerClient = new SellerClient(socket, seller);
                        sellerClient.homepage(); 
                    }
                    
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                new ErrorMessageGUI((String) ois.readObject());
            }
        } catch (IOException e) {
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

            boolean userCreated = true;
            userCreated = (boolean) ois.readObject();

            if (userCreated) {
                new LoginGUI(this); 
            } else {
                new ErrorMessageGUI((String) ois.readObject());
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

            boolean userCreated = true;
            userCreated = (boolean) ois.readObject();

            if (userCreated) {
                new LoginGUI(this);
            } else {
                new ErrorMessageGUI((String) ois.readObject());
            }
        } catch (IOException e) {
            new ErrorMessageGUI(SERVER_ERROR_MSG);
        } catch (Exception e) {
            System.out.println("Internal Server Communication Error");
            e.printStackTrace();
        }
    }
}