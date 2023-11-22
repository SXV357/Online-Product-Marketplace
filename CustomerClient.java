import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * Project 5 - CustomerClient.java
 * 
 * Class that handles communication with the server for a customer.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version November 21, 2023
 */
public class CustomerClient {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    //instance variable for convenience
    //saves repeated server requests by always storing this data
    private Customer customer;

    public CustomerClient(Socket socket, Customer customer) throws IOException{
        this.socket = socket;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());

        this.customer = customer;
    }

    //TODO
    public void homepage(){
        //call customer GUI
        //TODO customerGUI constructor
        //CustomerGUI customerGUI = new CustomerGUI(customer.getEmail());
        //CustomerGUI main is the seller homepage (Can be changed later)
        CustomerGUI.main(null);
    }
}
