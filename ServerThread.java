import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

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

            Dashboard db = new Dashboard();

            String[] response;

            Object output = null;
            boolean exit = false;
            Database database = new Database();

            while (true) {
                User u = new User();
                String[] userInfo = (String[]) ois.readObject();
                switch (userInfo[0]) {
                    // Log In
                    case "LOG_IN" -> u = new User(database.retrieveUserMatchForLogin(userInfo[2], userInfo[3]),
                            userInfo[2], userInfo[3], UserRole.UNDECIDED);
                    // Sign up
                    case "SIGN UP" -> {
                        try {
                            u = new User(userInfo[2], userInfo[3], UserRole.UNDECIDED);
                        } catch (Exception e) {
                            oos.writeObject(e.getMessage());
                        }
                    }
                    default -> oos.writeObject("ERROR");
                }

                if (userInfo[1].equals("C") && !u.getUserID().isEmpty()) {
                    Customer c = new Customer(u.getUserID(), userInfo[2], userInfo[3], UserRole.CUSTOMER);
                    Server.activeUsers.add(c.getUserID());
                    oos.writeObject("Customer Connection to Server Established");
                    while (!exit) {
                        response = (String[]) ois.readObject();
                        output = null;
                        try {
                            switch (response[0]) {
                                // View All Products
                                case "GET_ALL_PRODUCTS" -> output = c.getAllProducts();
                                // Get Product Info
                                case "GET_PRODUCT_INFO" -> output = c.getProductInfo(Integer.parseInt(response[1]));
                                // Add Product to Cart
                                case "ADD_TO_CART" -> {
                                    c.addToCart(Integer.parseInt(response[1]), Integer.parseInt(response[2]));
                                    output = "Successfully added item to cart.";
                                }
                                // Remove Product from Cart
                                case "REMOVE_FROM_CART" -> {
                                    c.removeFromCart(Integer.parseInt(response[1]));
                                    output = "Successfully removed item from cart.";
                                }
                                // View Cart
                                case "GET_CART" -> output = c.getCart();
                                // View Shopping History
                                case "GET_SHOPPING_HISTORY" -> output = c.getShoppingHistory();
                                // Search Products
                                case "SEARCH_PRODUCTS" -> output = c.searchProducts(response[1]);
                                // Sort Products
                                case "SORT_PRODUCTS" -> output = c.sortProducts(response[1]);
                                // Export Shopping History
                                case "EXPORT_PURCHASE_HISTORY" -> {
                                    c.exportPurchaseHistory();
                                    output = "Successfully exported purchsae history.";
                                }
                                // View Store Dashboard
                                case "STORE_DASHBOARD" ->
                                    output = db.customerGetStoresDashboard(Integer.parseInt(response[1]),
                                            response[2].equals("true")).toString();
                                // View Purchases Dashboard
                                case "PURCHASE_DASHBOARD" ->
                                    output = db.customerGetPersonalPurchasesDashboard(Integer.parseInt(response[1]),
                                            response[2].equals("true"), c.getUserID()).toString();
                                // Modify Email
                                case "1" -> c.setEmail(response[1]);
                                // Modify Password
                                case "2" -> c.setPassword(response[1]);
                                // Delete Account (add reload program)
                                case "3" -> {
                                    c.deleteAccount();
                                    exit = true;
                                }
                                default -> output = null;

                            }
                            oos.writeObject(new Object[] { "SUCCESS", output });

                        } catch (CustomerException e) {
                            oos.writeObject(new Object[] { "ERROR", e.getMessage() });

                        }

                    }
                } else if (userInfo[1].equals("S") && !u.getUserID().isEmpty()) {
                    Seller s = new Seller(u.getUserID(), userInfo[2], userInfo[3], UserRole.SELLER);
                    Server.activeUsers.add(s.getUserID());
                    oos.writeObject("Seller Connection to Server Established");
                    while (!exit) {
                        response = (String[]) ois.readObject();
                        output = null;
                        try {
                            switch (response[0]) {
                                // Get Stores
                                case "GET_ALL_STORES" -> output = s.getStores();
                                // Get Products
                                case "GET_ALL_PRODUCTS" -> output = s.getProducts(response[1]);
                                // Create Product
                                case "CREATE_NEW_PRODUCT" -> {
                                    s.createNewProduct(response[1], response[2], response[3], response[4], response[5]);
                                    output = "Successfully created new product.";
                                }
                                // Edit Product
                                case "EDIT_PRODUCT" -> {
                                    s.editProduct(response[1], response[2], response[3], response[4]);
                                    output = "Successfully edited producted.";
                                }
                                // Delete Product
                                case "DELETE_PRODUCT" -> {
                                    s.deleteProduct(response[1], response[2]);
                                    output = "Successfully deleted producted.";
                                }
                                // Create Store
                                case "CREATE_NEW_STORE" -> {
                                    s.createNewStore(response[1]);
                                    output = "Successfully created new store.";
                                }
                                // Delete Store
                                case "DELETE_STORE" -> {
                                    s.deleteStore(response[1]);
                                    output = "Successfully deleted store.";
                                }
                                // Modify Store
                                case "MODIFY_STORE_NAME" -> {
                                    s.modifyStoreName(response[1], response[2]);
                                    output = "Successfully modifed store.";
                                }
                                // Export Products
                                case "EXPORT_PRODUCTS" -> {
                                    s.exportProducts(response[1]);
                                    output = "Successfully exported products.";
                                }
                                // Import Products
                                case "IMPORT_PRODUCTS" -> {
                                    s.importProducts(response[1], response[2]);
                                    output = "Successfully imported products.";
                                }
                                // View Customer Shopping Cart
                                case "VIEW_CUSTOMER_SHOPPING_CARTS" -> output = s.viewCustomerShoppingCarts();
                                // View Sales by Store
                                case "VIEW_SALES_BY_STORE" -> output = s.viewStoreSales();
                                // Sort Customer Dashboard
                                case "CUSTOMERS_DASHBOARD" ->
                                    output = db.sellerGetCustomersDashboard(Integer.parseInt(response[1]),
                                            response[2].equals("true")).toString();
                                // Sort Products Dashboard
                                case "PRODUCTS_DASHBOARD" ->
                                    output = db.sellerGetProductsDashboard(Integer.parseInt(response[1]),
                                            response[2].equals("true")).toString();
                                // Modify Email
                                case "1" -> s.setEmail(response[1]);
                                // Modify Password
                                case "2" -> s.setPassword(response[1]);
                                // Delete Account
                                case "3" -> {
                                    s.deleteAccount();
                                    exit = true;
                                }
                                default -> output = null;
                            }
                            oos.writeObject(new Object[] { "SUCCESS", output });
                        } catch (SellerException e) {
                            oos.writeObject(new Object[] { "ERROR", e.getMessage() });
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
