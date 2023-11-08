/**
 * Project 4 - Product.java
 * 
 * Class that represents an individual product available in a seller's store.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 8, 2023
 */
public class Product {

    private static Database db = new Database();
    private String productIdentificationNumber;
    private String name;
    private int availableQuantity;
    private double price;
    private String description;

    // creating a product for the very first time
    public Product(String name, int availableQuantity, double price, String description) {
        this.productIdentificationNumber = "PR" + String.valueOf(generateProductIdentificationNumber());
        this.name = name;
        this.availableQuantity = availableQuantity;
        this.price = price;
        this.description = description;
    }

    // Getters
    public String getProductIdentificationNumber() {
        return this.productIdentificationNumber;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getAvailableQuantity() {
        return this.availableQuantity;
    }

    public double getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return String.format("%s,%d,%.2f,%s", this.name, this.availableQuantity, this.price, this.description);
    }

    /**
     * Returns a unique 7-digit ID as long as the current ID is not already associated with an existing account in the products.csv database
     * 
     * @return A unique 7-digit ID
     */
    public int generateProductIdentificationNumber() {
        int currentID = 1000000; // the first store created will have this ID
        while (db.checkIDMatch(currentID, "products.csv")) {
            currentID += 1;
        }
        return currentID;
    }
}