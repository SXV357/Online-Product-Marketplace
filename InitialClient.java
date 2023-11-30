import java.net.Socket;
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
 * @version November 15, 2023
 */

public class InitialClient {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public InitialClient(Socket socket) throws IOException{
        this.socket = socket;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
    }
    //First method to call: initializes entire program through login GUI

    public void start(){
        //constructor calls the GUI
        new UserGUI();

        //LoginGUI.startScreen();
    }
    
    public void loginPage(){
        //loginGUI call
        new LoginGUI();
    }

    public void createAccountPage(){
        //GUI call
        new SignUp();
    }

    //TODO
    public void attemptLoginSeller(String email, String password) throws IOException{

        //Check credentials with the database and retrieve user object
        //TODO replace this seller with a seller requested from the server
        Seller seller = new Seller(email, password, null);

        //if valid, create new seller client and pass server connection to it
        SellerClient sellerClient = new SellerClient(socket, seller);
        //Then display seller page 
        sellerClient.homepage();
        //-this client object becomes irrelevant after this is done 

        //if invalid display error message and return to login page


    }

    public void attemptLoginCustomer(String email, String password) throws IOException{
        //Check credentials with the database and retrieve user object
        boolean loginSuccessful = true;


        //TODO replace this seller with a seller requested from the server
        Customer customer = new Customer(email, password, null);
        //if valid, create new seller client and pass server connection to it

        CustomerClient customerClient = new CustomerClient(socket, customer);
        
        if(loginSuccessful){
            //Display Customer homepage
            customerClient.homepage();  
        } else{
            new ErrorMessageGUI("");
            new LoginGUI();
        }

        //this client object becomes irrelevant after this is done 

        //if invalid display error message and return to login page
    }

    //TODO
    public void attemptCreateNewSellerAccount(String email, String password){
        //Check credentials with the database, if valid, create a new seller object
        Seller newSeller = new Seller(email,password,UserRole.SELLER);
        //if valid, create new seller client and pass server connection to it
        //
        //Then display seller page 
        //-this client object becomes irrelevant after this is done 

        //if invalid display error message and return to login page
    }

    //TODO
    public void attemptCreateNewCustomerAccount(String email, String password){
        //Check credentials with the database, if valid, create a new customer object
        Customer newCustomer = new Customer(email,password,UserRole.CUSTOMER);

        //create new Customer client and pass server connection to it
        //Then display seller page 
        //-this client object becomes irrelevant after this is done 

        //if credentials invalid display error message and return to login page
    }
}
