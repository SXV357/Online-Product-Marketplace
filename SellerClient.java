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
 * @version November 27, 2023
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

    public void signOut() throws IOException{
        InitialClient initialClient = new InitialClient(socket);
        initialClient.start();
    }

    public void homepage(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SellerGUI(SellerClient.this, seller.getEmail());
            }
        });
    }

    // Get all the stores associated with the current seller
    public Object getStores(String action) {
        // action: GET_ALL_STORES
        Object result;
        try {
            oos.writeObject(new String[] {action});
            oos.flush();
            result = ois.readObject(); // Represents arraylist or string error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Get all the products associated with a certain store of the current seller
    public Object getProducts(String action, String storeName) {
        // action: GET_ALL_PRODUCTS
        Object result;
        try {
            oos.writeObject(new String[] {action, storeName});
            oos.flush();
            result = ois.readObject(); // Represents arraylist or string error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }


    public String createNewStore(String action, String storeName) {
        // action: CREATE_NEW_STORE
        String result;
        try {
            oos.writeObject(new String[] {action, storeName});
            oos.flush();
            result = (String) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String modifyStoreName(String action, String prevStoreName, String newStoreName) {
        // action: MODIFY_STORE_NAME
        String result;
        try {
            oos.writeObject(new String[] {prevStoreName, newStoreName});
            oos.flush();
            result = (String) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String deleteStore(String action, String storeName) {
        // action: DELETE_STORE
        String result;
        try {
            oos.writeObject(new String[] {action, storeName});
            oos.flush();
            result = (String) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String createNewProduct(String action, String storeName, String productName, String availableQuantity, String price, String description) {
        // action: CREATE_NEW_PRODUCT
        String result;
        try {
            oos.writeObject(new String[] {action, storeName, productName, availableQuantity, price, description});
            oos.flush();
            result = (String) ois.readObject(); 
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String editProduct(String action, String storeName, String productName, String editParam, String newValue) {
        // action: EDIT_PRODUCT
        String result;
        try {
            oos.writeObject(new String[] {action, storeName, productName, editParam, newValue});
            oos.flush();
            result = (String) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String deleteProduct(String action, String storeName, String productName) {
        // action: DELETE_PRODUCT
        String result;
        try {
            oos.writeObject(new String[] {action, storeName, productName});
            oos.flush();
            result = (String) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String importProducts(String action, String filePath, String storeName) {
        // action: IMPORT_PRODUCTS
        String result;
        try {
            oos.writeObject(new String[] {action, filePath, storeName});
            oos.flush();
            result = (String) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String exportProducts(String action, String storeName) {
        // action: EXPORT_PRODUCTS
        String result;
        try {  
            oos.writeObject(new String[] {action, storeName});
            oos.flush();
            result = (String) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String viewCustomerShoppingCarts(String action) {
        // action: VIEW_CUSTOMER_SHOPPING_CARTS
        String result;
        try {
            oos.writeObject(new String[] {action});
            oos.flush();
            result = (String) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public String viewSalesByStore(String action) {
        // action: VIEW_SALES_BY_STORE
        String result;
        try {
            oos.writeObject(new String[] {action});
            oos.flush();
            result = (String) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object sellerGetCustomersDashboard(String action, int sortSelection, boolean ascending) {
        // action: CUSTOMERS_DASHBOARD
        Object result;
        try {
            oos.writeObject(new Object[] {action, sortSelection, ascending});
            oos.flush();
            result = ois.readObject(); // Represents the arraylist or the string error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object sellerGetProductsDashboard(String action, int sortSelection, boolean ascending) {
        // action: PRODUCTS_DASHBOARD
        Object result;
        try {
            oos.writeObject(new Object[] {action, sortSelection, ascending});
            oos.flush();
            result = ois.readObject(); // Represents the arraylist or the string error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }
}