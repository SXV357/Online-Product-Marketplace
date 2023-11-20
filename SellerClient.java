import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Project 5 - SellerClient.java
 * 
 * Class that handles a seller's connection and requests to the database server.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * 
 * @version November 15, 2023
 */
    

public class SellerClient {
    
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    //instance variable for convenience
    //saves repeated server requests by always storing this data
    private Seller seller;
    public SellerClient(Socket socket, Seller seller) throws IOException{
        this.socket = socket;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());

        this.seller = seller;
    }

    public void signOut() throws IOException{
        InitialClient initialClient = new InitialClient(socket);
        initialClient.start();
    }

    //TODO
    public void homepage(){
        //call seller GUI
        //TODO sellerGUI constructor needs more parameters
        SellerGUI sellerGUI = new SellerGUI(seller.getEmail());
        //sellerGUI main is the seller homepage
        sellerGUI.main(null);
    }
}
