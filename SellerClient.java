import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.SwingUtilities;

/**
 * Project 5 - SellerClient.java
 * <p>
 * Class that handles a seller's connection and requests to the database server.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * @version December 6, 2023
 */
public class SellerClient {

    ObjectOutputStream oos;
    ObjectInputStream ois;

    public SellerClient(ObjectOutputStream oos, ObjectInputStream ois) throws IOException {
        this.oos = oos;
        this.ois = ois;
    }

    public void handleAccountState() throws IOException {
        InitialClient initialClient = new InitialClient(oos, ois);
        initialClient.start();
    }

    public void homepage(String sellerEmail) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SellerClient sc;
                try {
                    sc = new SellerClient(oos, ois);
                    new SellerGUI(sc, sellerEmail);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Object[] fetchAllCustomers() {
        // action: FETCH_ALL_CUSTOMERS
        // RETURN: ["ERROR", error message] or ["SUCCESS", customers arraylist]
        Object[] result;
        try {
            oos.writeObject(new String[]{"FETCH_ALL_CUSTOMERS"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] fetchAllProducts() {
        // action: FETCH_ALL_PRODUCTS
        // RETURN: ["ERROR", error message] or ["SUCCESS", products arraylist]
        Object[] result;
        try {
            oos.writeObject(new String[]{"FETCH_ALL_PRODUCTS"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Get all the stores associated with the current seller
    public Object[] getStores() {
        // action: GET_ALL_STORES
        // RETURN: ["ERROR", error message] or ["SUCCESS", stores arraylist]
        Object[] result;
        try {
            oos.writeObject(new String[]{"GET_ALL_STORES"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // Get all the products associated with a certain store of the current seller
    public Object[] getStoreProducts(String storeName) {
        // action: GET_ALL_PRODUCTS
        // RETURN: ["ERROR", error message] or ["SUCCESS", products arraylist]
        Object[] result;
        try {
            oos.writeObject(new String[]{"GET_STORE_PRODUCTS", storeName});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] createNewStore(String storeName) {
        // action: CREATE_NEW_STORE
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        Object[] result;
        try {
            oos.writeObject(new String[]{"CREATE_NEW_STORE", storeName});
            oos.flush();
            result = (Object[]) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] modifyStoreName(String prevStoreName, String newStoreName) {
        // action: MODIFY_STORE_NAME
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        Object[] result;
        try {
            oos.writeObject(new String[]{"MODIFY_STORE_NAME", prevStoreName, newStoreName});
            oos.flush();
            result = (Object[]) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] deleteStore(String storeName) {
        // action: DELETE_STORE
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        Object[] result;
        try {
            oos.writeObject(new String[]{"DELETE_STORE", storeName});
            oos.flush();
            result = (Object[]) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] createNewProduct(String storeName, String productName, String availableQuantity,
                                     String price, String description) {
        // action: CREATE_NEW_PRODUCT
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        Object[] result;
        try {
            oos.writeObject(new String[]{"CREATE_NEW_PRODUCT", storeName, productName, availableQuantity,
                    price, description});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] editProduct(String storeName, String productName, String editParam, String newValue) {
        // action: EDIT_PRODUCT
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        Object[] result;
        try {
            oos.writeObject(new String[]{"EDIT_PRODUCT", storeName, productName, editParam, newValue});
            oos.flush();
            result = (Object[]) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] deleteProduct(String storeName, String productName) {
        // action: DELETE_PRODUCT
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        Object[] result;
        try {
            oos.writeObject(new String[]{"DELETE_PRODUCT", storeName, productName});
            oos.flush();
            result = (Object[]) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] importProducts(String filePath, String storeName) {
        // action: IMPORT_PRODUCTS
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        Object[] result;
        try {
            oos.writeObject(new String[]{"IMPORT_PRODUCTS", filePath, storeName});
            oos.flush();
            result = (Object[]) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] exportProducts(String storeName) {
        // action: EXPORT_PRODUCTS
        // RETURN: ["ERROR", error message] or ["SUCCESS", success message]
        Object[] result;
        try {
            oos.writeObject(new String[]{"EXPORT_PRODUCTS", storeName});
            oos.flush();
            result = (Object[]) ois.readObject(); // Represents the string confirmation or error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] viewCustomerShoppingCarts() {
        // action: VIEW_CUSTOMER_SHOPPING_CARTS
        // RETURN: ["ERROR", error message] or ["SUCCESS", shopping carts hashmap]
        Object[] result;
        try {
            oos.writeObject(new String[]{"VIEW_CUSTOMER_SHOPPING_CARTS"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] viewSalesByStore() {
        // action: VIEW_SALES_BY_STORE
        // RETURN: ["ERROR", error message] or ["SUCCESS", sales by store hashmap]
        Object[] result;
        try {
            oos.writeObject(new String[]{"VIEW_SALES_BY_STORE"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] sellerGetCustomersDashboard(int sortSelection, boolean ascending) {
        // action: CUSTOMERS_DASHBOARD
        // RETURN: ["ERROR", error message] or ["SUCCESS", dashboard arraylist]
        Object[] result;
        try {
            oos.writeObject(new String[]{"SELLER_GET_CUSTOMERS_DASHBOARD", String.valueOf(sortSelection),
                    String.valueOf(ascending)});
            oos.flush();
            result = (Object[]) ois.readObject(); // Represents the arraylist or the string error message
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public Object[] sellerGetProductsDashboard(int sortSelection, boolean ascending) {
        // action: PRODUCTS_DASHBOARD
        // RETURN: ["ERROR", error message] or ["SUCCESS", dashboard arraylist]
        Object[] result;
        try {
            oos.writeObject(new String[]{"SELLER_GET_PRODUCTS_DASHBOARD", String.valueOf(sortSelection),
                    String.valueOf(ascending)});
            oos.flush();
            result = (Object[]) ois.readObject(); // Represents the arraylist or the string error message
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
            oos.writeObject(new String[]{"EDIT_EMAIL", newEmail});
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
            oos.writeObject(new String[]{"EDIT_PASSWORD", newPassword});
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
            oos.writeObject(new String[]{"DELETE_ACCOUNT"});
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
            oos.writeObject(new String[]{"SIGN_OUT"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }
}