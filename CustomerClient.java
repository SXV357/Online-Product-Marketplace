import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.SwingUtilities;
/**
 * Project 5 - CustomerClient.java
 * 
 * Class that handles communication with the server for a customer.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version November 29, 2023
 */
public class CustomerClient {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Customer customer;

    public CustomerClient(Socket socket, Customer customer) throws IOException{
        this.socket = socket;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
        this.customer = customer;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void homepage(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CustomerGUI(CustomerClient.this, customer.getEmail());
            }
        });
    }

    // Get all the products from the marketplace
    public String[] getAllProducts() {
        // action: GET_ALL_PRODUCTS
         // RETURN: ["ERROR", error message] or ["SUCCESS", products string]
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
    public String[] getProductInfo(int productSelection) {
        // action: GET_PRODUCT_INFO
         // RETURN: ["ERROR", error message] or ["SUCCESS", product info string]
        String[] result;
        try {
            oos.writeObject(new String[] {"GET_PRODUCT_INFO", String.valueOf(productSelection)});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Adds product from the marketplace using index to cart
    public String[] addToCart(int index, int quantity) {
        // action: ADD_TO_CART
         // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        String[] result;
        try {
            oos.writeObject(new String[] {"ADD_TO_CART", String.valueOf(index), String.valueOf(quantity)});
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
        // RETURN: ["ERROR", error message] or ["SUCCESS", shopping cart string]
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
        // RETURN: ["ERROR", error message] or ["SUCCESS", shopping history string]
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

    public String[] purchaseItems() {
        // action: PURCHASE_ITEMS
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        String[] result;
        try {
            oos.writeObject(new Object[] {"PURCHASE_ITEMS"});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // View Store Dashboard
    public Object[] customerGetStoresDashboard(int sortSelection, boolean ascending) {
        // action: EXPORT_PURCHASE_HISTORY
        // RETURN: ["ERROR", error message] or ["SUCCESS", arraylist of Stores]
        Object[] result;
        try {
            oos.writeObject(new Object[] {"EXPORT_PURCHASE_HISTORY", sortSelection, ascending});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    
    // View Personal Purchase Dashboard
    public Object[] customerGetPersonalPurchasesDashboard(int sortSelection, boolean ascending, String userID) {
        // action: PURCHASE_DASHBOARD
        // RETURN: ["ERROR", error message] or ["SUCCESS", arraylist of personal dashboard items]
        Object[] result;
        try {
            oos.writeObject(new Object[] {"PURCHASE_DASHBOARD", sortSelection, ascending, userID});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String[] editEmail(String newEmail) {
        // action: EDIT_EMAIL
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        String[] result;
        try {
            oos.writeObject(new String[] {"EDIT_EMAIL", newEmail});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String[] editPassword(String newPassword) {
        // action: EDIT_PASSWORD
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        String[] result;
        try {
            oos.writeObject(new String[] {"EDIT_PASSWORD", newPassword});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String[] deleteAccount() {
        // action: DELETE_ACCOUNT 
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        String[] result;
        try {
            oos.writeObject(new String[] {"DELETE_ACCOUNT"});
            oos.flush();
            result = (String[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }
}