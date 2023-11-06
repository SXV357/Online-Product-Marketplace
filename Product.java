/**
 * Project 4 - Product.java
 * 
 * Class that represents an individual product available in a seller's store.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 6, 2023
 */
public class Product {

    private static Database db = new Database();
    private String productIdentificationNumber;
    private String name;
    private String description;
    private int availableQuantity;
    private double price;

    public Product(String name, String description, int availableQuantity, double price) {
        this.productIdentificationNumber = "PR" + String.valueOf(generateProductIdentificationNumber());
        this.name = name;
        this.description = description;
        this.availableQuantity = availableQuantity;
        this.price = price;
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

    // Setters(Used for modifying an existing product in a given store)
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public void setPrice(double price) {
        this.price = price;
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