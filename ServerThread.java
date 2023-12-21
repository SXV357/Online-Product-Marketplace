import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Project 5 - ServerThread.java
 * 
 * Class that handles database interactions with client.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version December 8, 2023
 */
public class ServerThread extends Thread {

    Socket socket;

    /**
     * Constructs a new ServerThread instance utilizing the socket that communication will be taking place over.
     * 
     * @param socket The communication channel between all clients and the server
     */
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * Process initial client, seller client, and customer client requests and send data back to all of them
     */
    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            oos.flush();

            Dashboard db = new Dashboard();

            String[] response;

            Object output = null;
            boolean exit = false;
            Database database = new Database();
            User u = null;

            while (socket.isConnected()) {
                exit = false;
                u = new User();

                // Handle communication with the initial client
                String[] userInfo = (String[]) ois.readObject();
                switch (userInfo[0]) {
                    // Log In
                    case "LOGIN" -> {
                        try {
                            String userMatch = database.retrieveUserMatchForLogin(userInfo[1], userInfo[2]);
                            if (userMatch.split(",")[3].equals("CUSTOMER")) {
                                u = new Customer(userMatch.split(",")[0], userInfo[1], userInfo[2],
                                        UserRole.CUSTOMER);
                            } else if (userMatch.split(",")[3].equals("SELLER")) {
                                u = new Seller(userMatch.split(",")[0], userInfo[1], userInfo[2],
                                        UserRole.SELLER);
                            }
                        } catch (Exception e) {
                            oos.writeObject(e.getMessage());
                            oos.flush();
                        }
                    }
                    // Customer Sign up
                    case "CREATE_CUSTOMER" -> {
                        try {
                            u = new Customer(userInfo[1], userInfo[2], UserRole.CUSTOMER);
                        } catch (Exception e) {
                            oos.writeObject(e.getMessage());
                            oos.flush();
                        }
                    }
                    // Seller Sign up
                    case "CREATE_SELLER" -> {
                        try {
                            u = new Seller(userInfo[1], userInfo[2], UserRole.SELLER);
                        } catch (Exception e) {
                            oos.writeObject(e.getMessage());
                            oos.flush();
                        }
                    }
                    default -> oos.writeObject("ERROR");
                }
                // Handle communication with the customer client
                if (u instanceof Customer) {
                    Customer c = (Customer) u;
                    oos.writeObject("Customer Connection to Server Established");
                    oos.flush();
                    oos.writeObject(u.getEmail());
                    oos.flush();
                    while (!exit) {
                        response = (String[]) ois.readObject();
                        output = null;
                        try {
                            switch (response[0]) {
                                // Get all stores
                                case "FETCH_ALL_STORES" -> output = c.fetchAllStores();
                                // View All Products
                                case "GET_ALL_PRODUCTS" -> output = c.getAllProducts();
                                // Get Product Info
                                case "GET_PRODUCT_INFO" -> output = c.getProductInfo(Integer.parseInt(response[1]));
                                // Add Product to Cart
                                case "ADD_TO_CART" -> {
                                    c.addToCart(Integer.parseInt(response[1]), response[2]);
                                    output = "Successfully added item to cart.";
                                }
                                // Remove Product from Cart
                                case "REMOVE_FROM_CART" -> {
                                    c.removeFromCart(Integer.parseInt(response[1]), response[2]);
                                    output = "Successfully removed item from cart.";
                                }
                                case "LEAVE_REVIEW" -> {
                                    c.leaveReview(Integer.parseInt(response[1]), response[2]);
                                    output = "Review uploaded successfully";
                                }
                                case "RETURN_ITEM" -> {
                                    c.returnItems(Integer.parseInt(response[1]));
                                    output = "Item returned successfully!";
                                }
                                // View Cart
                                case "GET_CART" -> output = c.getCart();
                                // View Shopping History
                                case "GET_SHOPPING_HISTORY" -> output = c.getShoppingHistory();
                                // Search Products
                                case "SEARCH_PRODUCTS" -> output = c.searchProducts(response[1]);
                                // Sort Products
                                case "SORT_PRODUCTS" -> output = c.sortProducts(response[1], response[2].equals("true"));
                                // Export Shopping History
                                case "EXPORT_PURCHASE_HISTORY" -> {
                                    c.exportPurchaseHistory();
                                    output = "Successfully exported purchase history.";
                                }
                                // Purchase Items
                                case "PURCHASE_ITEMS" -> {
                                    c.purchaseItems();
                                    output = "Items checked out successfully!";
                                }
                                // View Store Dashboard
                                case "CUSTOMER_GET_STORES_DASHBOARD" ->
                                        output = db.customerGetStoresDashboard(Integer.parseInt(response[1]),
                                                response[2].equals("true"));
                                // View Purchases Dashboard
                                case "CUSTOMER_GET_PURCHASES_DASHBOARD" ->
                                        output =
                                                db.customerGetPersonalPurchasesDashboard
                                                (Integer.parseInt(response[1]),
                                                response[2].equals("true"), c.getUserID());
                                // Modify Email
                                case "EDIT_EMAIL" -> {
                                    c.setEmail(response[1]);
                                    output = c.getEmail();
                                }
                                // Modify Password
                                case "EDIT_PASSWORD" -> {
                                    c.setPassword(response[1]);
                                    output = "Password edited successfully!";
                                }
                                // Delete Account
                                case "DELETE_ACCOUNT" -> {
                                    c.deleteAccount();
                                    oos.writeObject(new Object[]{"SUCCESS", "Account deleted successfully!"});
                                    oos.flush();
                                    exit = true;
                                }
                                case "SIGN_OUT" -> {
                                    oos.writeObject(new Object[]{"SUCCESS", "Signed out successfully!"});
                                    oos.flush();
                                    exit = true;
                                }
                                default -> output = null;

                            }
                            if (!exit) {
                                oos.writeObject(new Object[]{"SUCCESS", output});
                                oos.flush();
                            }


                        } catch (CustomerException e) {
                            oos.writeObject(new Object[]{"ERROR", e.getMessage()});
                            oos.flush();
                        } catch (Exception e) {
                            oos.writeObject(new Object[]{"ERROR", e.getMessage()});
                            oos.flush();
                        }
                    }
                // Handle communication with the seller client
                } else if (u instanceof Seller) {
                    Seller s = (Seller) u;
                    oos.writeObject("Seller Connection to Server Established");
                    oos.flush();
                    oos.writeObject(u.getEmail());
                    oos.flush();
                    while (!exit) {
                        response = (String[]) ois.readObject();
                        output = null;
                        try {
                            switch (response[0]) {
                                // Get all customers
                                case "FETCH_ALL_CUSTOMERS" -> output = s.getAllCustomers();
                                // Get all products
                                case "FETCH_ALL_PRODUCTS" -> output = s.getAllProducts();
                                // Get Stores
                                case "GET_ALL_STORES" -> output = s.getStores();
                                // Get Products
                                case "GET_STORE_PRODUCTS" -> output = s.getProducts(response[1]);
                                // Create Product
                                case "CREATE_NEW_PRODUCT" -> {
                                    s.createNewProduct(response[1], response[2], response[3], response[4],
                                            response[5], response[6], response[7], response[8]);
                                    output = "Successfully created new product.";
                                }
                                // Edit Product
                                case "EDIT_PRODUCT" -> {
                                    s.editProduct(response[1], response[2], response[3], response[4]);
                                    output = "Successfully edited product.";
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
                                // View product reviews
                                case "VIEW_PRODUCT_REVIEWS" -> output = s.viewProductReviews(response[1], response[2]);
                                // View Customer Shopping Cart
                                case "VIEW_CUSTOMER_SHOPPING_CARTS" -> output = s.viewCustomerShoppingCarts();
                                // View Sales by Store
                                case "VIEW_SALES_BY_STORE" -> output = s.viewStoreSales();
                                // Sort Customer Dashboard
                                case "SELLER_GET_CUSTOMERS_DASHBOARD" ->
                                        output = db.sellerGetCustomersDashboard(Integer.parseInt(response[1]),
                                                response[2].equals("true"));
                                // Sort Products Dashboard
                                case "SELLER_GET_PRODUCTS_DASHBOARD" ->
                                        output = db.sellerGetProductsDashboard(Integer.parseInt(response[1]),
                                                response[2].equals("true"));
                                // Modify Email
                                case "EDIT_EMAIL" -> {
                                    s.setEmail(response[1]);
                                    output = s.getEmail();
                                }
                                // Modify Password
                                case "EDIT_PASSWORD" -> {
                                    s.setPassword(response[1]);
                                    output = "Password edited successfully!";
                                }
                                // Delete Account
                                case "DELETE_ACCOUNT" -> {
                                    s.deleteAccount();
                                    oos.writeObject(new Object[]{"SUCCESS", "Account deleted successfully!"});
                                    oos.flush();
                                    exit = true;
                                }

                                case "SIGN_OUT" -> {
                                    oos.writeObject(new Object[]{"SUCCESS", "Signed out successfully!"});
                                    oos.flush();
                                    exit = true;
                                }
                                default -> output = null;
                            }
                            if (!exit) {
                                oos.writeObject(new Object[]{"SUCCESS", output});
                                oos.flush();
                            }

                        } catch (SellerException e) {
                            oos.writeObject(new Object[]{"ERROR", e.getMessage()});
                            oos.flush();
                        } catch (Exception e) {
                            oos.writeObject(new Object[]{"ERROR", e.getMessage()});
                            oos.flush();
                        }
                    }
                }
            }
        } catch (Exception e) {
            new ErrorMessageGUI("A client shutdown occurred. Relaunch the client if you would like to continue using the application.");
        }
    }
}