import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.SwingUtilities;
/**
 * Project 5 - SellerClient.java
 * 
 * Class that handles a seller's connection and requests to the database server.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * 
 * @version November 28, 2023
 */
public class SellerClient {
    
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Seller seller;

    public SellerClient(Socket socket, Seller seller) throws IOException{
        this.socket = socket;
        this.oos = new ObjectOutputStream(this.socket.getOutputStream());
        this.ois = new ObjectInputStream(this.socket.getInputStream());
        this.seller = seller;
    }

    // public void signOut() throws IOException{
    //     InitialClient initialClient = new InitialClient(socket);
    //     initialClient.start();
    // }

    public void homepage(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SellerGUI(SellerClient.this, seller.getEmail());
            }
        });
    }

    // Get all the stores associated with the current seller
    public Object getStores() {
        // action: GET_ALL_STORES
        // RETURN THE ARRAYLIST AS IS OR THE CUSTOM ERROR MESSAGE
        Object result;
        try {
            oos.writeObject(new String[] {"GET_ALL_STORES"});
            oos.flush();
            result = ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Get all the products associated with a certain store of the current seller
    public Object getProducts(String storeName) {
        // action: GET_ALL_PRODUCTS
        // RETURN A STRING CONFIRMATION OR THE CUSTOM ERROR MESSAGE FROM SERVER
        Object result;
        try {
            oos.writeObject(new String[] {"GET_ALL_PRODUCTS", storeName});
            oos.flush();
            result = ois.readObject(); // Represents arraylist or string error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }


    public String[] createNewStore(String storeName) {
        // action: CREATE_NEW_STORE
        // RETURN A STRING CONFIRMATION OR THE CUSTOM ERROR MESSAGE FROM SERVER
        String[] result;
        try {
            oos.writeObject(new String[] {"CREATE_NEW_STORE", storeName});
            oos.flush();
            result = (String[]) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String[] modifyStoreName(String prevStoreName, String newStoreName) {
        // action: MODIFY_STORE_NAME
        // RETURN A STRING CONFIRMATION OR THE CUSTOM ERROR MESSAGE FROM SERVER
        String[] result;
        try {
            oos.writeObject(new String[] {"MODIFY_STORE_NAME", prevStoreName, newStoreName});
            oos.flush();
            result = (String[]) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String[] deleteStore(String storeName) {
        // action: DELETE_STORE
        // RETURN A STRING CONFIRMATION OR THE CUSTOM ERROR MESSAGE FROM SERVER
        String[] result;
        try {
            oos.writeObject(new String[] {"DELETE_STORE", storeName});
            oos.flush();
            result = (String[]) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String[] createNewProduct(String storeName, String productName, String availableQuantity, String price, String description) {
        // action: CREATE_NEW_PRODUCT
        // RETURN A STRING CONFIRMATION OR THE CUSTOM ERROR MESSAGE FROM SERVER
        String[] result;
        try {
            oos.writeObject(new String[] {"CREATE_NEW_PRODUCT", storeName, productName, availableQuantity, price, description});
            oos.flush();
            result = (String[]) ois.readObject(); 
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String[] editProduct(String storeName, String productName, String editParam, String newValue) {
        // action: EDIT_PRODUCT
        // RETURN A STRING CONFIRMATION OR THE CUSTOM ERROR MESSAGE FROM SERVER
        String[] result;
        try {
            oos.writeObject(new String[] {"EDIT_PRODUCT", storeName, productName, editParam, newValue});
            oos.flush();
            result = (String[]) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String[] deleteProduct(String storeName, String productName) {
        // action: DELETE_PRODUCT
        // RETURN A STRING CONFIRMATION OR THE CUSTOM ERROR MESSAGE FROM SERVER
        String[] result;
        try {
            oos.writeObject(new String[] {"DELETE_PRODUCT", storeName, productName});
            oos.flush();
            result = (String[]) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String[] importProducts(String filePath, String storeName) {
        // action: IMPORT_PRODUCTS
        // RETURN A STRING CONFIRMATION OR THE CUSTOM ERROR MESSAGE FROM SERVER
        String[] result;
        try {
            oos.writeObject(new String[] {"IMPORT_PRODUCTS", filePath, storeName});
            oos.flush();
            result = (String[]) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String[] exportProducts(String storeName) {
        // action: EXPORT_PRODUCTS
        // RETURN A STRING CONFIRMATION OR THE CUSTOM ERROR MESSAGE FROM SERVER
        String[] result;
        try {  
            oos.writeObject(new String[] {"EXPORT_PRODUCTS", storeName});
            oos.flush();
            result = (String[]) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object viewCustomerShoppingCarts() {
        // action: VIEW_CUSTOMER_SHOPPING_CARTS
        // RETURN THE HASHMAP AS IS OR THE CUSTOM ERROR MESSAGE
        Object result;
        try {
            oos.writeObject(new String[] {"VIEW_CUSTOMER_SHOPPING_CARTS"});
            oos.flush();
            result = ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object viewSalesByStore() {
        // action: VIEW_SALES_BY_STORE
        // RETURN THE HASHMAP AS IS OR THE CUSTOM ERROR MESSAGE
        Object result;
        try {
            oos.writeObject(new String[] {"VIEW_SALES_BY_STORE"});
            oos.flush();
            result = ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object sellerGetCustomersDashboard(int sortSelection, boolean ascending) {
        // action: CUSTOMERS_DASHBOARD
        // RETURN THE ARRAYLIST AS IS OR THE CUSTOM ERROR MESSAGE
        Object result;
        try {
            oos.writeObject(new Object[] {"CUSTOMERS_DASHBOARD", sortSelection, ascending});
            oos.flush();
            result = ois.readObject(); // Represents the arraylist or the string error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object sellerGetProductsDashboard(int sortSelection, boolean ascending) {
        // action: PRODUCTS_DASHBOARD
        // RETURN THE ARRAYLIST AS IS OR THE CUSTOM ERROR MESSAGE
        Object result;
        try {
            oos.writeObject(new Object[] {"PRODUCTS_DASHBOARD", sortSelection, ascending});
            oos.flush();
            result = ois.readObject(); // Represents the arraylist or the string error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }
}