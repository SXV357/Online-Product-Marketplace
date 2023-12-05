import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.SwingUtilities;
/**
 * Project 5 - CustomerClient.java
 * 
 * Class that handles communication with the server for a customer.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version December 4, 2023
 */
public class CustomerClient {

    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public CustomerClient(ObjectOutputStream oos, ObjectInputStream ois) throws IOException {
        this.oos = oos;
        this.ois =  ois;
    }

    public void handleAccountState() throws IOException {
        InitialClient initialClient = new InitialClient(oos, ois);
        initialClient.start();
    }

    public void homepage(String customerEmail){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CustomerClient cc;
                try {
                    cc = new CustomerClient(oos, ois);
                    new CustomerGUI(cc, customerEmail);
                } catch (IOException e) {
                    e.printStackTrace();
                }    
            }
        });
    }

    // Get all the products from the marketplace
    public Object[] getAllProducts() {
        // action: GET_ALL_PRODUCTS
         // RETURN: ["ERROR", error message] or ["SUCCESS", products string]
        Object[] result;
        try {
            oos.writeObject(new String[] {"GET_ALL_PRODUCTS"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Get a given products info
    public Object[] getProductInfo(int productSelection) {
        // action: GET_PRODUCT_INFO
         // RETURN: ["ERROR", error message] or ["SUCCESS", product info string]
        Object[] result;
        try {
            oos.writeObject(new String[] {"GET_PRODUCT_INFO", String.valueOf(productSelection)});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Adds product from the marketplace using index to cart
    public Object[] addToCart(int index, int quantity) {
        // action: ADD_TO_CART
         // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        Object[] result;
        try {
            oos.writeObject(new String[] {"ADD_TO_CART", String.valueOf(index), String.valueOf(quantity)});
            oos.flush();
        
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Removes product using index from cart
    public Object[] removeFromCart(int index) {
        // action: REMOVE_FROM_CART
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        Object[] result;
        try {
            oos.writeObject(new String[] {"REMOVE_FROM_CART", String.valueOf(index)});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Returns customer's shopping cart
    public Object[] getCart() {
        // action: GET_CART
        // RETURN: ["ERROR", error message] or ["SUCCESS", shopping cart string]
        Object[] result;
        try {
            oos.writeObject(new String[] {"GET_CART"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Returns customer's shopping history
    public Object[] getShoppingHistory() {
        // action: GET_SHOPPING_HISTORY
        // RETURN: ["ERROR", error message] or ["SUCCESS", shopping history string]
        Object[] result;
        try {
            oos.writeObject(new String[] {"GET_SHOPPING_HISTORY"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Returns filtered product list by search
    public Object[] searchProducts(String query) {
        // action: SEARCH_PRODUCTS
        // RETURN: ["ERROR", error message] or ["SUCCESS", filtered product list]
        Object[] result;
        try {
            oos.writeObject(new String[] {"SEARCH_PRODUCTS", query});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Returns sorted products list by price or quantity
    public Object[] sortProducts(String choice) {
        // action: SORT_PRODUCTS
        // RETURN: ["ERROR", error message] or ["SUCCESS", sorted product list]
        Object[] result;
        try {
            oos.writeObject(new String[] {"SORT_PRODUCTS", choice});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Exports Purchase History
    public Object[] exportPurchaseHistory() {
        // action: EXPORT_PURCHASE_HISTORY
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        Object[] result;
        try {
            oos.writeObject(new String[] {"EXPORT_PURCHASE_HISTORY"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] purchaseItems() {
        // action: PURCHASE_ITEMS
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        Object[] result;
        try {
            oos.writeObject(new String[] {"PURCHASE_ITEMS"});
            oos.flush();
            result = (Object[]) ois.readObject();
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
            oos.writeObject(new String[] {"CUSTOMER_GET_STORES_DASHBOARD", String.valueOf(sortSelection), String.valueOf(ascending)});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    
    // View Personal Purchase Dashboard
    public Object[] customerGetPersonalPurchasesDashboard(int sortSelection, boolean ascending) {
        // action: PURCHASE_DASHBOARD
        // RETURN: ["ERROR", error message] or ["SUCCESS", arraylist of personal dashboard items]
        Object[] result;
        try {
            oos.writeObject(new String[] {"CUSTOMER_GET_PURCHASES_DASHBOARD", String.valueOf(sortSelection), String.valueOf(ascending)});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] editEmail(String newEmail) {
        // action: EDIT_EMAIL
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        Object[] result;
        try {
            oos.writeObject(new String[] {"EDIT_EMAIL", newEmail});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] editPassword(String newPassword) {
        // action: EDIT_PASSWORD
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        Object[] result;
        try {
            oos.writeObject(new String[] {"EDIT_PASSWORD", newPassword});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] deleteAccount() {
        // action: DELETE_ACCOUNT
        Object[] result; 
        try {
            oos.writeObject(new String[] {"DELETE_ACCOUNT"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] signOut() {
        // action: SIGN_OUT 
        Object[] result;
        try {
            oos.writeObject(new String[] {"SIGN_OUT"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }
}