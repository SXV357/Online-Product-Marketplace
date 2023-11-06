import java.util.ArrayList;

/**
 * Project 4 - Store.java
 *
 * Class that represents an individual store that belongs to a seller in the application.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 *
 * @version November 6, 2023
 */
public class Store {

    private static Database db = new Database();
    private String storeIdentificationNumber;
    private String storeName;
    private ArrayList<Product> products;

    public Store(String storeName) {
        this.storeIdentificationNumber = "ST" + String.valueOf(generateStoreIdentificationNumber());
        this.storeName = storeName;
        this.products = new ArrayList<Product>();
    }

    // Getters
    public String getStoreIdentificationNumber() {
        return this.storeIdentificationNumber;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    // Setters(If a seller wants to modify the name of one of their stores)
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    /**
     * Returns a unique 7-digit ID as long as the current ID is not already associated with an existing account in the stores.csv database
     * 
     * @return A unique 7-digit ID
     */
    public int generateStoreIdentificationNumber() {
        int currentID = 1000000; // the first store created will have this ID
        while (db.checkIDMatch(currentID, "stores.csv")) {
            currentID += 1;
        }
        return currentID;
    }
}