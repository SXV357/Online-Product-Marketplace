import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Project 5 - ServerThread.java
 * 
 * Class that handles database interactions with client.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * 
 * @version November 15, 2023
 */

public class ServerThread extends Thread {

    Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            String[] response;
            
            String output = null;
            boolean success = true;
            boolean exit = false;

            while (true) {
            String[] userInfo = ois.readObject().toString().split(",");
            if (userInfo[0].equals("C")) {
                Customer c = new Customer(userInfo[1], userInfo[2], UserRole.CUSTOMER);
                Server.activeUsers.add(c.getUserID());
                oos.writeObject("Customer Connection to Server Established");
                while (!exit) {
                    response = ois.readObject().toString().split(",");
                    switch(Integer.parseInt(response[0])) {
                        //View All Products
                        case 1 -> output = c.getAllProducts();
                        //Add Product to Cart
                        case 2 -> success = c.addToCart(Integer.parseInt(response[1]), Integer.parseInt(response[2]));
                        //Remove Product from Cart
                        case 3 -> success = c.removeFromCart(Integer.parseInt(response[1]));
                        //View Cart
                        case 4 -> output = c.getCart();
                        //View Shopping History
                        case 5 -> output = c.getShoppingHistory();
                        //Search Products
                        case 6 -> output = c.searchProducts(response[1]);
                        //Sort Products
                        case 7 -> output = c.searchProducts(response[1]);
                        //Export Shopping History
                        case 8 -> c.exportPurchaseHistory();
                        //TODO FIX ARRAY TO STRING
                        //View Store Dashboard
                        case 9 -> output = Dashboard.customerGetStoresDashboard(Integer.parseInt(response[1]), response[2].equals("true")).toString();
                        //View Purchases Dashboard
                        case 10 -> output = Dashboard.customerGetPersonalPurchasesDashboard(Integer.parseInt(response[1]), response[2].equals("true"), c.getUserID()).toString();
                        //Modify Email
                        case 11 -> c.setEmail(response[1]);
                        //Modify Password
                        case 12 -> c.setPassword(response[1]);
                        //Delete Account (add reload program)
                        case 13 -> {
                            c.deleteAccount();
                            exit = true;
                        } 
                        default -> output = null;
                    }
                    if (success) {
                        oos.writeObject(output);
                    } else {
                        oos.writeObject("Invalid Input");
                    }
                    
                }
            } else if (userInfo[0].equals("S")) {
                Seller s = new Seller(userInfo[1], userInfo[2], UserRole.SELLER);
                Server.activeUsers.add(s.getUserID());
                oos.writeObject("Seller Connection to Server Established");
                while (!exit) {
                    response = ois.readObject().toString().split(",");
                    switch(Integer.parseInt(response[0])) {
                        //Create Product
                        //TODO fix description w/ commas
                        case 1 -> output = s.createNewProduct(response[1], response[2], response[3], response[4], response[5]);
                        //Edit Product
                        case 2 -> output = s.editProduct(response[1], response[2], response[3], response[4]);
                        //Delete Product
                        case 3 -> output = s.deleteProduct(response[1], response[2]);
                        //Import Products
                        case 4 -> output = s.importProducts(response[1], response[2]);
                        //Export Products
                        case 5 -> output = s.exportProducts(response[1]);
                        //Create Store
                        case 6 -> output = s.createNewStore(response[1]);
                        //TODO FIX THE ARRAY TO STRING
                        //Sort Customer Dashboard
                        case 7 -> output = Dashboard.sellerGetCustomersDashboard(Integer.parseInt(response[1]), response[2].equals("true")).toString();
                        //Sort Products Dashboard
                        case 8 -> Dashboard.sellerGetProductsDashboard(Integer.parseInt(response[1]), response[2].equals("true")).toString();
                        //TODO Implement view carts and sales by store
                        //Modify Email
                        case 9 -> s.setEmail(response[1]);
                        //Modify Password
                        case 10 -> s.setPassword(response[1]);
                        //Delete Account
                        case 11 -> {
                            s.deleteAccount();
                            exit = true;
                        }
                        default -> output = null;
                    }
                    if (success) {
                        oos.writeObject(output);
                    } else {
                        oos.writeObject("Invalid Input");
                    }
                }
            }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
