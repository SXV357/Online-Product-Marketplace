/**
 * Project 5 - Product.java
 * <p>
 * Class that represents an individual product available in a seller's store.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * @version December 6, 2023
 */
public class Product {

    private Database db = new Database();
    private String productIdentificationNumber;
    private String name;
    private int availableQuantity;
    private double price;
    private String description;

    /**
     * Creating a new product for the first time. Generates a unique ID and adds a
     * signifier to it indicating that it is a product.
     *
     * @param name              The new product's name
     * @param availableQuantity The new product's quantity
     * @param price             The new product's price
     * @param description       The new product's description
     */
    public Product(String name, int availableQuantity, double price, String description) {
        this.productIdentificationNumber = "PR" + String.valueOf(generateProductIdentificationNumber());
        this.name = name;
        this.availableQuantity = availableQuantity;
        this.price = price;
        this.description = description;
    }

    /**
     * Re-initializing an already existing product.
     *
     * @param productIdentificationNumber The existing product's ID
     * @param name                        The existing product's name
     * @param availableQuantity           The existing product's quantity
     * @param price                       The existing product's price
     * @param description                 The existing product's description
     */
    public Product(String productIdentificationNumber, String name, int availableQuantity, double price,
                   String description) {
        this.productIdentificationNumber = productIdentificationNumber;
        this.name = name;
        this.availableQuantity = availableQuantity;
        this.price = price;
        this.description = description;
    }

    /**
     * Gets the current product's ID
     *
     * @return The ID associated with this product
     */
    public String getProductIdentificationNumber() {
        return this.productIdentificationNumber;
    }

    /**
     * Gets the current product's name
     *
     * @return The name associated with this product
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the current product's description
     *
     * @return The description associated with this product
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the current product's quantity
     *
     * @return The quantity associated with this product
     */
    public int getAvailableQuantity() {
        return this.availableQuantity;
    }

    /**
     * Gets the current product's price
     *
     * @return The price associated with this product
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Returns a string representation of the current product. Utilized in the
     * stores.csv database.
     */
    @Override
    public String toString() {
        return String.format("%s,%d,%.2f,%s", this.name, this.availableQuantity, this.price, this.description);
    }

    /**
     * Returns a unique 7-digit ID as long as the current ID is not already
     * associated with an existing account in the products.csv database
     *
     * @return A unique 7-digit ID
     */
    public int generateProductIdentificationNumber() {
        int currentID = 1000000; // The first store created will have this ID
        while (db.checkIDMatch(currentID, "products.csv")) {
            currentID += 1;
        }
        return currentID;
    }
}