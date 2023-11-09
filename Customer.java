import java.io.IOException;
import java.util.ArrayList;

/**
 * Project 4 - Customer.java
 *
 * Class to represent the permissions and details associated with a customer
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 *
 * @version November 2, 2023
 */

public class Customer extends User{

    private ArrayList<String> shoppingCart;
    private ArrayList<String> purchasehistory;
    private Database db = new Database();
    private String userID;

    public Customer(String userID, String email, String password) throws IOException {
        super(userID, email, password, UserRole.CUSTOMER);
        shoppingCart = db.getMatchedEntries("shoppingCarts.csv", 0, userID);
        purchasehistory = db.getMatchedEntries("shoppingHistories.csv", 0, userID);

    }

    /**
     * Converts arrays to list of strings
     * 
     * @return String of array's contents
    */
    public String arrToString(ArrayList<String> array) {
        StringBuilder output = new StringBuilder();
        String[] splitArr;
        for (int i = 0; i < array.size(); i++) {
            splitArr = array.get(i).split(",");
            output.append(i).append(". ").append(array.get(i));
            for (int j = 0; j < splitArr.length; j++) {
                if (i == 0) {
                    output.append(splitArr[j]).append("  ");
                } else {
                    output.append("    ").append(splitArr[j]).append("    ");
                }
            }
            output.append(System.getProperty("line.separator"));
        }
        return output.toString();
    }

    /**
     * Returns the user's shopping history
     * 
     * @return The shopping history as an ArrayList
    */
    public ArrayList<String> getShoppingHistory() {
        return shoppingCart;
    }

    /**
     * Removes a given item from the cart
     * 
     * @param index the index of the cart the remove
     */
    public void removeFromCart(int index) throws IOException {
        db.removeFromDatabase("shoppingCarts", shoppingCart.get(index));
        shoppingCart.remove(index);
    }

    /**
     * Adds a given item from the cart
     * 
     * @param productID the index of the product the add
     */
    public void addToCart(int index) {
        ArrayList<String> products = db.getDatabaseContents("products.csv");
        shoppingCart.add(products.get(index));
        db.addToDatabase("shoppingCarts.csv", shoppingCart.get(shoppingCart.size()-1));
    }

    /**
     * Purchases the items in the cart
     * 
     */
    public void purchaseItems() throws IOException {
        db.updateDatabaseContents("purchaseHistories.csv", shoppingCart);
        for (String entry : shoppingCart) {
            db.removeFromDatabase("shoppingCarts.csv", entry);
            shoppingCart.add(entry);
        }
        shoppingCart.clear();
    }

    /**
     * Sorts stores by purchase history
     * 
     * @return Returns a sorted array of these stores
     */
    public ArrayList<String> getStoresByPurchased() {
        ArrayList<String> storeSorted = purchasehistory;
        ArrayList<String> sorted = new ArrayList<>();
        int n = sorted.size();
        String temp = "";
        int searchIndex = 4;
 
        //Bubble Sort
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < (n - i); j++) {
                if (Integer.parseInt(sorted.get(j - 1).split(",")[searchIndex])
                        > Integer.parseInt(sorted.get(j).split(",")[searchIndex])) {
                    temp = sorted.get(j - 1);
                    sorted.set(j - 1, sorted.get(j));
                    sorted.set(j, temp);
                }
            }
        }
        return sorted;
    }

    /**
     * Retreives the user's purchase history
     * 
     * @return Returns the user's purchase history
     */
    public ArrayList<String> getHistory() throws IOException {
        return db.getMatchedEntries("purchaseHistories.csv", 0, userID);
    }

    /**
     * Searches for all products containing a given query
     * 
     * @return Returns the products found
     */
    public ArrayList<String> searchProducts(String query) {
        ArrayList<String> productsFound = new ArrayList<>();
        for (String product : db.getDatabaseContents("products.csv")) {
            if (product.contains(query)) {
                productsFound.add(product);
            }
        }
        return productsFound;
    }
}
