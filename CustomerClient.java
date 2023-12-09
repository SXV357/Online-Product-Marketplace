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
 * @version December 7, 2023
 */
public class CustomerClient {

    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    /**
     * Constructs a CustomerClient instance using the output and input streams passed in through Initial Client
     *
     * @param oos The object output stream to utilize to send data to the server
     * @param ois The object input stream to utilize to receive data from the server
     * @throws IOException
     */
    public CustomerClient(ObjectOutputStream oos, ObjectInputStream ois) throws IOException {
        this.oos = oos;
        this.ois = ois;
    }

    /**
     * Launches the main menu GUI in response to a customer deleting their account or signing out.
     *
     * @throws IOException
     */
    public void handleAccountState() throws IOException {
        InitialClient initialClient = new InitialClient(oos, ois);
        initialClient.start();
    }

    /**
     * Launches the Customer GUI
     *
     * @param customerEmail The customer's email to display on their home page
     */
    public void homepage(String customerEmail) {
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

    /**
     * Makes a call to the server to fetch all the stores that exist in the application and retrieves the result of
     * the operation to be sent back to the CustomerGUI.
     *
     * @return An object array in the form of ["SUCCESS", Stores] or ["ERROR", Error Message]
     */
    public Object[] fetchAllStores() {
        Object[] result;
        try {
            oos.writeObject(new String[]{"FETCH_ALL_STORES"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to fetch all the products that exist in the application and retrieves the result
     * of the operation to be sent back to the CustomerGUI.
     *
     * @return An object array in the form of ["SUCCESS", Products] or ["ERROR", Error Message]
     */
    public Object[] getAllProducts() {
        Object[] result;
        try {
            oos.writeObject(new String[]{"GET_ALL_PRODUCTS"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to fetch information for a product that a customer selects from the list of all
     * products and retrieves the result of the operation to be sent back to the CustomerGUI.
     *
     * @param productSelection The index of the customer's product selection from the products menu
     * @return An object array in the form of ["SUCCESS", Product Information] or ["ERROR", Error Message]
     */
    public Object[] getProductInfo(int productSelection) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"GET_PRODUCT_INFO", String.valueOf(productSelection)});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to add a product to a customer's cart and retrieves the result of the operation to
     * be sent back to the CustomerGUI.
     *
     * @param index The index of the customer's product selection
     * @param quantity The amount of the product the customer wants to add to cart
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] addToCart(int index, String quantity) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"ADD_TO_CART", String.valueOf(index), quantity});
            oos.flush();

            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to remove a product to a customer's cart and retrieves the result of the operation
     * to be sent back to the CustomerGUI.
     *
     * @param index The index of the customer's product selection
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] removeFromCart(int index) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"REMOVE_FROM_CART", String.valueOf(index)});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to fetch all items in a customer's shopping cart and retrieves the result of the
     * operation to be sent back to the CustomerGUI.
     *
     * @return An object array in the form of ["SUCCESS", Shopping Cart Items] or ["ERROR", Error Message]
     */
    public Object[] getCart() {
        Object[] result;
        try {
            oos.writeObject(new String[]{"GET_CART"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to fetch a customer's purchase history and retrieves the result of the operation to
     * be sent back to the CustomerGUI.
     *
     * @return An object array in the form of ["SUCCESS", Purchase History Items] or ["ERROR", Error Message]
     */
    public Object[] getShoppingHistory() {
        Object[] result;
        try {
            oos.writeObject(new String[]{"GET_SHOPPING_HISTORY"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to fetch products in the marketplace based on a query and retrieves the result of
     * the operation to be sent back to the CustomerGUI.
     *
     * @param query The parameter to use to search the marketplace for
     * @return An object array in the form of ["SUCCESS", Matched Products] or ["ERROR", Error Message]
     */
    public Object[] searchProducts(String query) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"SEARCH_PRODUCTS", query});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to fetch an upated product list based on the sort parameter and retrieves the
     * result of the operation to be sent back to the CustomerGUI.
     *
     * @param choice The parameter to use to sort the marketplace
     * @return An object array in the form of ["SUCCESS", Sorted Product List] or ["ERROR", Error Message]
     */
    public Object[] sortProducts(String choice) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"SORT_PRODUCTS", choice});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to export the current customer's purchase history and retrieves the result of the
     * operation to be sent back to the CustomerGUI.
     *
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] exportPurchaseHistory() {
        Object[] result;
        try {
            oos.writeObject(new String[]{"EXPORT_PURCHASE_HISTORY"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to purchase all the items in the current customer's shopping cart and retrieves the
     * result of the operation to be sent back to the CustomerGUI.
     *
     * @return An object array in the form of ["SUCCESS", Success Message] or ["ERROR", Error Message]
     */
    public Object[] purchaseItems() {
        Object[] result;
        try {
            oos.writeObject(new String[]{"PURCHASE_ITEMS"});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to fetch the stores dashboard and retrieves the result of the operation to be sent
     * back to the CustomerGUI.
     *
     * @param sortSelection The parameter to sort the dashboard by
     * @param ascending The order to sort the dashboard in
     * @return An object array in the form of ["SUCCESS", Stores Dashboard] or ["ERROR", Error Message]
     */
    public Object[] customerGetStoresDashboard(int sortSelection, boolean ascending) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"CUSTOMER_GET_STORES_DASHBOARD", String.valueOf(sortSelection),
                    String.valueOf(ascending)});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to fetch the current customers purchase history dashboard and retrieves the result
     * of the operation to be sent back to the CustomerGUI.
     *
     * @param sortSelection The parameter to sort the dashboard by
     * @param ascending The order to sort the dashboard in
     * @return An object array in the form of ["SUCCESS", Purchase History Dashboard] or ["ERROR", Error Message]
     */
    public Object[] customerGetPersonalPurchasesDashboard(int sortSelection, boolean ascending) {
        Object[] result;
        try {
            oos.writeObject(new String[]{"CUSTOMER_GET_PURCHASES_DASHBOARD", String.valueOf(sortSelection),
                    String.valueOf(ascending)});
            oos.flush();
            result = (Object[]) ois.readObject();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * Makes a call to the server to edit the current customer's email and retrieves the result of the operation to
     * be sent back to the CustomerGUI.
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
     * Makes a call to the server to edit the current customer's password and retrieves the result of the operation
     * to be sent back to the CustomerGUI.
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
     * Makes a call to the server to delete the current customer's account and retrieves the result of the operation
     * to be sent back to the CustomerGUI.
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
     * Makes a call to the server to log the current customer out and retrieves the result of the operation to be
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
