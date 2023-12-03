import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * Project 5 - ServerThread.java
 * 
 * Class that handles database interactions with client.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * 
 * @version December 2, 2023
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
        
                String[] userInfo = (String[]) ois.readObject();
                switch (userInfo[0]) {
                    // Log In
                    case "LOGIN" -> {
                        try {
                            String userMatch = database.retrieveUserMatchForLogin(userInfo[1], userInfo[2]);
                            if (userMatch == null) {
                                throw new Exception("The email or password you entered is non-existent. Please try again");
                            } else {
                                if (userMatch.split(",")[3].equals("CUSTOMER")) {
                                u = new Customer(userMatch.split(",")[0], userInfo[1], userInfo[2], UserRole.CUSTOMER);
                            } else if (userMatch.split(",")[3].equals("SELLER")){
                                u = new Seller(userMatch.split(",")[0], userInfo[1], userInfo[2], UserRole.SELLER);
                            }   
                            System.out.println("user login");
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
                if (u instanceof Customer) {
                    //Server.activeUsers.add(c.getUserID());
                    Customer c = (Customer) u;
                    System.out.println("Hello");
                    oos.writeObject("Customer Connection to Server Established");
                    oos.flush();
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
                                case "EDIT_EMAIL" -> c.setEmail(response[1]);
                                // Modify Password
                                case "EDIT_PASSWORD" -> c.setPassword(response[1]);
                                // Delete Account
                                case "DELETE_ACCOUNT" -> {
                                    c.deleteAccount();
                                    exit = true;
                                }
                                case "SIGN_OUT" -> {
                                    exit = true;
                                }
                                default -> output = null;

                            }
                            if (!exit) {
                                oos.writeObject(new Object[] { "SUCCESS", output });
                                oos.flush();
                            }
                            

                        } catch (CustomerException e) {
                            oos.writeObject(new Object[] { "ERROR", e.getMessage() });
                            oos.flush();
                        }
                    }
                } else if (u instanceof Seller) {
                    //Server.activeUsers.add(s.getUserID());
                    Seller s = (Seller) u;
                    oos.writeObject("Seller Connection to Server Established");
                    oos.flush();
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
                                case "EDIT_EMAIL" -> s.setEmail(response[1]);
                                // Modify Password
                                case "EDIT_PASSWORD" -> s.setPassword(response[1]);
                                // Delete Account
                                case "DELETE_ACCOUNT" -> {
                                    s.deleteAccount();
                                    exit = true;
                                }

                                case "SIGN_OUT" -> {
                                    exit = true;
                                }
                                default -> output = null;
                            }
                            if (!exit) {
                                oos.writeObject(new Object[] { "SUCCESS", output });
                                oos.flush();
                            }
                            
                        } catch (SellerException e) {
                            oos.writeObject(new Object[] { "ERROR", e.getMessage() });
                            oos.flush();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}