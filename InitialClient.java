import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    //TODO
    public void start(){
        //LoginGUI.startScreen();
    }
    
    //TODO
    public void loginPage(){
        //loginGUI call
    }

    //TODO
    public void createAccountPage(){
        //loginGUI call
    }

    //TODO
    public void attemptLoginSeller(String email, String password) throws IOException{

        //Check credentials with the database

        //if valid, create new seller client and pass server connection to it
        SellerClient sellerClient = new SellerClient(socket);
        //Then display seller page 
        sellerClient.homepage();
        //-this client object becomes irrelevant after this is done 

        //if invalid display error message and return to login page


    }

    //TODO
    public void attemptLoginCustomer(String email, String password) throws IOException{
        //Check credentials with the database

        //if valid, create new Customer client and pass server connection to it
        CustomerClient customerClient = new CustomerClient(socket);

        //Then display Customer homepage
        customerClient.homepage(); 
        //-this client object becomes irrelevant after this is done 

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
