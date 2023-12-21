import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.SwingUtilities;

/**
 * Project 5 - SellerClient.java
 *
 * Class that handles a seller's connection and requests to the database server.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 *
 * @version December 7, 2023
 */
public class SellerClient {

    ObjectOutputStream oos;
    ObjectInputStream ois;

    /**
     * Constructs a SellerClient instance using the output and input streams passed in through Initial Client
     *
     * @param oos The object output stream to utilize to send data to the server
     * @param ois The object input stream to utilize to receive data from the server
     * @throws IOException
     */
    public SellerClient(ObjectOutputStream oos, ObjectInputStream ois) throws IOException {
        this.oos = oos;
        this.ois = ois;
    }

    /**
     * Launches the main menu GUI in response to a seller deleting their account or signing out.
     *
     * @throws IOException
     */
    public void handleAccountState() throws IOException {
        InitialClient initialClient = new InitialClient(oos, ois);
        initialClient.start();
    }

    /**
     * Launches the Seller GUI
     *
     * @param customerEmail The seller's email to display on their home page
     */
    public void homepage(String sellerEmail) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SellerClient sc;
                try {
                    sc = new SellerClient(oos, ois);
                    new SellerGUI(sc, sellerEmail);
                } catch (IOException e) {
                    new ErrorMessageGUI(e.getMessage());
                }
            }
        });
    }

    /**
     * Makes a call to the server to fetch all the customers that exist in the application and retrieves the result
     * of the operation to be sent back to the SellerGUI.
     *
     * @return An object array in the form of ["SUCCESS", Customers] or ["ERROR", Error Message]
     */
    public Object[] fetchAllCustomers() {
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

    /**
     * Makes a call to the server to fetch all the products that exist in the application and retrieves the result
     * of the operation to be sent back to the SellerGUI.
     *
     * @return An object array in the form of ["SUCCESS", Products] or ["ERROR", Error Message]
     */
    public Object[] fetchAllProducts() {
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

    /**
     * Makes a call to the server to fetch all the stores associated with the current seller and retrieves the
     * result of the operation to be sent back to the SellerGUI.
     *
     * @return An object array in the form of ["SUCCESS", Stores] or ["ERROR", Error Message]
     */
    public Object[] getStores() {
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

    /**
     * Makes a call to the server to fetch all the products associated with one of the current seller's stores and
     * retrieves the result of the operation to be sent back to the SellerGUI.
     *
     * @param storeName The store name to fetch the products from
     * @return An object array in the form of ["SUCCESS", Products] or ["ERROR", Error Message]
     */
    public Object[] getStoreProducts(String storeName) {
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

    /**
     *
     * Makes a call to the server to create a new store associated with the current seller and retrieves the result
     * of the operation to be sent back to the SellerGUI.
     *
     * @param storeName The name of the new store
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] createNewStore(String storeName) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"CREATE_NEW_STORE", storeName});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to modify an existing store associated with the current seller and retrieves the
     * result of the operation to be sent back to the SellerGUI.
     *
     * @param prevStoreName The name of the store to edit
     * @param newStoreName The new name of the store
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] modifyStoreName(String prevStoreName, String newStoreName) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"MODIFY_STORE_NAME", prevStoreName, newStoreName});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to delete an existing store associated with the current seller and retrieves the
     * result of the operation to be sent back to the SellerGUI.
     *
     * @param storeName The name of the store to delete
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] deleteStore(String storeName) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"DELETE_STORE", storeName});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to create a new product in an existing store associated with the current seller
     * and retrieves the result of the operation to be sent back to the SellerGUI.
     *
     * @param storeName The store to create the product in
     * @param productName The product's name
     * @param availableQuantity The quantity available for sale
     * @param price The product's price
     * @param description The product's description
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] createNewProduct(String storeName, String productName, String availableQuantity,
                                     String price, String description, String orderLimit) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"CREATE_NEW_PRODUCT", storeName, productName, availableQuantity,
                                         price, description, orderLimit});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to edit an existing product in an existing store associated with the current seller
     * and retrieves the result of the operation to be sent back to the SellerGUI.
     *
     * @param storeName The store to edit the product in
     * @param productName The name of the product to edit
     * @param editParam The parameter of the product to edit
     * @param newValue The new value of the specific parameter of the product
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] editProduct(String storeName, String productName, String editParam, String newValue) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"EDIT_PRODUCT", storeName, productName, editParam, newValue});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to delete an existing product in an existing store associated with the current
     * seller and retrieves the result of the operation to be sent back to the SellerGUI.
     *
     * @param storeName The store to delete the product in
     * @param productName The name of the product to delete
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] deleteProduct(String storeName, String productName) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"DELETE_PRODUCT", storeName, productName});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to import products into an existing store associated with the current seller and
     * retrieves the result of the operation to be sent back to the SellerGUI.
     *
     * @param filePath The file containing the products to import
     * @param storeName The name of the store to import the products into
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] importProducts(String filePath, String storeName) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"IMPORT_PRODUCTS", filePath, storeName});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to export products from an existing store associated with the current seller and
     * retrieves the result of the operation to be sent back to the SellerGUI.
     *
     * @param storeName The name of the store to export the products from
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] exportProducts(String storeName) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"EXPORT_PRODUCTS", storeName});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to fetch items in customers shopping carts associated with the current seller's
     * stores and retrieves the result of the operation to be sent back to the SellerGUI.
     *
     * @return An object array in the form of ["SUCCESS", Customer Shopping Carts] or ["ERROR", Error Message]
     */
    public Object[] viewCustomerShoppingCarts() {
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

    /**
     * Makes a call to the server to view sales by store for the current seller's stores and retrieves the result
     * of the operation to be sent back to the SellerGUI.
     *
     * @return An object array in the form of ["SUCCESS", Customer Shopping Carts] or ["ERROR", Error Message]
     */
    public Object[] viewSalesByStore() {
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

    public Object[] viewProductReviews(String storeName, String productName) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"VIEW_PRODUCT_REVIEWS", storeName, productName});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to fetch the customers dashboard and retrieves the result of the operation to be
     * sent back to the SellerGUI.
     *
     * @param sortSelection The parameter to sort the dashboard by
     * @param ascending The order to sort the dashboard in
     * @return An object array in the form of ["SUCCESS", Customers Dashboard] or ["ERROR", Error Message]
     */
    public Object[] sellerGetCustomersDashboard(int sortSelection, boolean ascending) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"SELLER_GET_CUSTOMERS_DASHBOARD", String.valueOf(sortSelection),
                    String.valueOf(ascending)});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to fetch the current seller's products dashboard and retrieves the result of the
     * operation to be sent back to the SellerGUI.
     *
     * @param sortSelection The parameter to sort the dashboard by
     * @param ascending The order to sort the dashboard in
     * @return An object array in the form of ["SUCCESS", Products Dashboard] or ["ERROR", Error Message]
     */
    public Object[] sellerGetProductsDashboard(int sortSelection, boolean ascending) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"SELLER_GET_PRODUCTS_DASHBOARD", String.valueOf(sortSelection),
                    String.valueOf(ascending)});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to edit the current seller's email and retrieves the result of the operation to
     * be sent back to the SellerGUI.
     *
     * @param newEmail The new email to replace the previous email with
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] editEmail(String newEmail) {
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

    /**
     * Makes a call to the server to edit the current seller's password and retrieves the result of the operation to
     * be sent back to the SellerGUI.
     *
     * @param newEmail The new password to replace the previous password with
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] editPassword(String newPassword) {
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

    /**
     * Makes a call to the server to delete the current seller's account and retrieves the result of the operation
     * to be sent back to the SellerGUI.
     *
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] deleteAccount() {
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

    /**
     * Makes a call to the server to log the current seller out and retrieves the result of the operation to be
     * sent back to the CustomerGUI.
     *
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] signOut() {
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
