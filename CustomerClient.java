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

    // Get all the products from the marketplace
    public String[] getAllProducts() {
        // action: GET_ALL_PRODUCTS
        // RETURN: ["ERROR", error message] or ["SUCCESS", products arraylist]
        String[] result;
        try {
            oos.writeObject(new String[] {"GET_ALL_PRODUCTS"});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Get a given products info
    public String[] getProductInfo() {
        // action: GET_PRODUCT_INFO
        // RETURN: ["ERROR", error message] or ["SUCCESS", product info]
        String[] result;
        try {
            oos.writeObject(new String[] {"GET_PRODUCT_INFO"});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Adds product from the marketplace using index to cart
    public String[] addToCart(int index) {
        // action: ADD_TO_CART
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        String[] result;
        try {
            oos.writeObject(new String[] {"ADD_TO_CART", String.valueOf(index)});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Removes product using index from cart
    public String[] removeFromCart(int index) {
        // action: REMOVE_FROM_CART
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        String[] result;
        try {
            oos.writeObject(new String[] {"REMOVE_FROM_CART", String.valueOf(index)});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Returns customer's shopping cart
    public String[] getCart() {
        // action: GET_CART
        // RETURN: ["ERROR", error message] or ["SUCCESS", shopping cart]
        String[] result;
        try {
            oos.writeObject(new String[] {"GET_CART"});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Returns customer's shopping history
    public String[] getShoppingHistory() {
        // action: GET_SHOPPING_HISTORY
        // RETURN: ["ERROR", error message] or ["SUCCESS", shopping history]
        String[] result;
        try {
            oos.writeObject(new String[] {"GET_SHOPPING_HISTORY"});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Returns filtered product list by search
    public String[] searchProducts(String query) {
        // action: SEARCH_PRODUCTS
        // RETURN: ["ERROR", error message] or ["SUCCESS", filtered product list]
        String[] result;
        try {
            oos.writeObject(new String[] {"SEARCH_PRODUCTS", query});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Returns sorted products list by price or quantity
    public String[] sortProducts(String choice) {
        // action: SORT_PRODUCTS
        // RETURN: ["ERROR", error message] or ["SUCCESS", sorted product list]
        String[] result;
        try {
            oos.writeObject(new String[] {"SORT_PRODUCTS", choice});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Exports Purchase History
    public String[] exportPurchaseHistory() {
        // action: EXPORT_PURCHASE_HISTORY
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        String[] result;
        try {
            oos.writeObject(new String[] {"EXPORT_PURCHASE_HISTORY"});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // View Store Dashboard
    public String[] customerGetStoresDashboard() {
        // action: EXPORT_PURCHASE_HISTORY
        // RETURN: ["ERROR", error message] or ["SUCCESS", arraylist of Stores]
        String[] result;
        try {
            oos.writeObject(new String[] {"EXPORT_PURCHASE_HISTORY"});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    
    // View Personal Purchase Dashboard
    public String[] customerGetPersonalPurchasesDashboard() {
        // action: PURCHASE_DASHBOARD
        // RETURN: ["ERROR", error message] or ["SUCCESS", arraylist of personal dashboard items]
        String[] result;
        try {
            oos.writeObject(new String[] {"PURCHASE_DASHBOARD"});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }
}
