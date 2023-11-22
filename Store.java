import java.util.ArrayList;

/**
 * Project 5 - Store.java
 * 
 * Class that represents an individual store that belongs to a seller in the
 * application.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version November 21, 2023
 */
public class Store {

    private Database db = new Database();
    private String storeIdentificationNumber;
    private String storeName;

    /**
     * Creating a new store for the first time. Generates a unique ID and adds a
     * signifier to it indicating that it is a store.
     *
     * @param storeName The new store's name
     */
    public Store(String storeName) {
        this.storeIdentificationNumber = "ST" + String.valueOf(generateStoreIdentificationNumber());
        this.storeName = storeName;
    }

    /**
     * Re-initialize an already-existing store
     *
     * @param storeIdentificationNumber The existing store's ID
     * @param storeName                 The existing store's name
     */
    public Store(String storeIdentificationNumber, String storeName) {
        this.storeIdentificationNumber = storeIdentificationNumber;
        this.storeName = storeName;
    }

    /**
     * Queries the database for product entries associated with this store then
     * bundles all the entries into product objects and returns them in the form of
     * an arraylist.
     *
     * @return An arraylist of all the products associated with this store
     */
    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<String> matchedProducts = db.getMatchedEntries("products.csv", 1, this.storeIdentificationNumber);
        if (matchedProducts.isEmpty()) {
            return null;
        }
        for (int i = 0; i < matchedProducts.size(); i++) {
            String[] productEntry = matchedProducts.get(i).split(",");
            Product product = new Product(productEntry[2], productEntry[4], Integer.parseInt(productEntry[5]),
                    Double.parseDouble(productEntry[6]), productEntry[7]);
            products.add(product);
        }
        return products;
    }

    /**
     * Gets the current store's ID
     *
     * @return The ID associated with this store
     */
    public String getStoreIdentificationNumber() {
        return this.storeIdentificationNumber;
    }

    /**
     * Gets the current store's name
     *
     * @return The name associated with this store
     */
    public String getStoreName() {
        return this.storeName;
    }

    /**
     * Returns a unique 7-digit ID as long as the current ID is not already
     * associated with an existing account in the stores.csv database
     *
     * @return A unique 7-digit ID
     */
    public int generateStoreIdentificationNumber() {
        int currentID = 1000000; // The first store created will have this ID
        while (db.checkIDMatch(currentID, "stores.csv")) {
            currentID += 1;
        }
        return currentID;
    }
}