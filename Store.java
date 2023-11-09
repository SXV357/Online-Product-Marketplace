import java.util.ArrayList;
/**
 * Project 4 - Store.java
 *
 * Class that represents an individual store that belongs to a seller in the application.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 *
 * @version November 8, 2023
 */
public class Store {

    private static Database db = new Database();
    private String storeIdentificationNumber;
    private String storeName;

    // creating a store for the very first time
    public Store(String storeName) {
        this.storeIdentificationNumber = "ST" + String.valueOf(generateStoreIdentificationNumber());
        this.storeName = storeName;
    }

    // re-initialize an already existing store
    public Store(String storeIdentificationNumber, String storeName) {
        this.storeIdentificationNumber = storeIdentificationNumber;
        this.storeName = storeName;
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<String> matchedProducts = db.getMatchedEntries("products.csv", 1, this.storeIdentificationNumber);
        if (matchedProducts.isEmpty()) {
            return null;
        }
        for (int i = 0; i < matchedProducts.size(); i++) {
            String[] productEntry = matchedProducts.get(i).split(",");
            Product product = new Product(productEntry[2], productEntry[4], Integer.parseInt(productEntry[5]), Double.parseDouble(productEntry[6]), productEntry[7]);
            products.add(product);
        }
        return products;
    }

    // Getters
    public String getStoreIdentificationNumber() {
        return this.storeIdentificationNumber;
    }

    public String getStoreName() {
        return this.storeName;
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