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
 * @version November 30, 2023
 */
public class InitialClient {

    private final String SERVER_ERROR_MSG = "Error occured when communicating with server";
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public InitialClient(Socket socket) throws IOException{
        this.socket = socket;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("localhost", Server.PORT_NUMBER);
        InitialClient initialClient = new InitialClient(socket);
        initialClient.start();
    }
    //First method to call: initializes entire program through login GUI
    public void start(){
        //constructor calls the GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserGUI(InitialClient.this);
            }
        });
    }
    
    public void loginPage(){
       SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGUI(InitialClient.this);
            }
        });
    }

    public void signUpPage(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SignUpGUI(InitialClient.this);
            }
        });
    }

    public void attemptLoginSeller(String email, String password){
            try {
            //Check credentials with the database and retrieve user object

            //Write null to indicate logging in or signing up
            //oos.writeObject(null);
            //TODO implement this request in server
            String[] serverRequest = {"SELLER_LOGIN",email,password};
            oos.writeObject(serverRequest);
            oos.flush();


            boolean loginSuccessful = true;

            loginSuccessful = (boolean) ois.readObject();
            
            //if invalid display error message and return to login page
            if (loginSuccessful){
                //Get seller object from server and move to new Seller Client
                Seller seller = null;
                
                seller = (Seller) ois.readObject();
                
                SellerClient sellerClient = new SellerClient(socket, seller);
                sellerClient.homepage();
            } else {
                //if invalid display error message and return to login page
                String errorMessage = "";
                try {
                    errorMessage = (String) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                new ErrorMessageGUI(errorMessage);
                new LoginGUI(this);
            }
        } catch (IOException e){
            new ErrorMessageGUI(SERVER_ERROR_MSG);
        } catch (Exception e){
            //Should never come here
            System.out.println("Internal Server Communication Error");
            e.printStackTrace();
        }

    }

    public void attemptLoginCustomer(String email, String password){
        try {
            //Check credentials with the database and retrieve user object
            //Write null to indicate logging in or signing up
            //oos.writeObject(null);
            //TODO implement this request in server
            String[] serverRequest = {"CUSTOMER_LOGIN",email,password};
            oos.writeObject(serverRequest);
            oos.flush();


            boolean loginSuccessful = true;
            try {
                loginSuccessful = (boolean) ois.readObject();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if(loginSuccessful){
                //get customer object from server
                Customer customer = null;
                try {
                    customer = (Customer) ois.readObject();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                CustomerClient customerClient = new CustomerClient(socket, customer);
                customerClient.homepage();  
            } else {
                //if invalid display error message and return to login page
                String errorMessage = "";
                try {
                    errorMessage = (String) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                new ErrorMessageGUI(errorMessage);
                new LoginGUI(this);
            }
        } catch (IOException e){
            new ErrorMessageGUI(SERVER_ERROR_MSG);
        } catch (Exception e){
            //Should never come here
            System.out.println("Internal Server Communication Error");
            e.printStackTrace();
        }

    }

    public void attemptCreateNewSellerAccount(String email, String password) {
        try{
            //Check credentials with the database and retrieve user object
            //Write null to indicate logging in or signing up
            //oos.writeObject(null);
            //TODO implement this request in server
            String[] serverRequest = {"CREATE_USER",email,password, "SELLER"};
            oos.writeObject(serverRequest);
            oos.flush();


            boolean userCreated = true;
            try {
                userCreated = (boolean) ois.readObject();
            } catch (ClassNotFoundException e) {
                //only come here if server mis outputted
                e.printStackTrace();
            }

            if(userCreated){
                //get Seller object from server
                Seller seller = null;
                try {
                    seller = (Seller) ois.readObject();
                } catch (ClassNotFoundException e) {
                    
                    e.printStackTrace();
                }
                //Display Customer homepage
                SellerClient sellerClient = new SellerClient(socket, seller);
                sellerClient.homepage();  
            } else{
                String errorMessage = "";
                try {
                    errorMessage = (String) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                new ErrorMessageGUI(errorMessage);
                new LoginGUI(this);
            }

        } catch (IOException e){
            new ErrorMessageGUI(SERVER_ERROR_MSG);
        } catch (Exception e){
            //Should never come here
            System.out.println("Internal Server Communication Error");
            e.printStackTrace();
        }

    }
    //TODO
    public void attemptCreateNewCustomerAccount(String email, String password){
        try {
            //Check credentials with the database and retrieve user object
            //Write null to indicate logging in or signing up
            //oos.writeObject(null);
            //TODO implement this request in server
            String[] serverRequest = {"CREATE_USER",email,password,"CUSTOMER"};
            oos.writeObject(serverRequest);
            oos.flush();

            boolean userCreated = true;

            userCreated = (boolean) ois.readObject();


            if(userCreated){
                //get customer object from server
                Customer customer = null;

                customer = (Customer) ois.readObject();

                CustomerClient customerClient = new CustomerClient(socket, customer);
                customerClient.homepage();  
            } else {
                //if invalid display error message and return to login page
                String errorMessage = "";

                errorMessage = (String) ois.readObject();
                
                new ErrorMessageGUI(errorMessage);
                new SignUpGUI(this);
            }
        } catch (IOException e){
            new ErrorMessageGUI(SERVER_ERROR_MSG);
        } catch (Exception e){
            //Should never come here
            System.out.println("Internal Server Communication Error");
            e.printStackTrace();
        }

    }
}