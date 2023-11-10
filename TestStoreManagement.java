/**
 * Project 4 - TestStoreManagement.java
 *
 * Class is the test class for the Seller Store and Product.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 *
 * @version November 10, 2023
 */

//check the store.csv user.csv and product.csv before you running this class
public class TestStoreManagement {
    private static Database db = new Database();

    public static void main(String[] args) {

        // Create a test seller object
        Seller seller = new Seller("seller@example.com", "password", UserRole.SELLER);
        db.addToDatabase("users.csv", seller.toString());
        System.out.println(seller.toString());

        // Create a test store
        Store store1 = new Store("Store 1");

        System.out.println(store1.getStoreIdentificationNumber());

        // Test store creation
        boolean storeCreated = seller.createNewStore(store1.getStoreName());
        System.out.println("Store 1 created: " + storeCreated);

        // Create some test products
        Product product1 = new Product("Product 1", 10, 19.99, "Description 1");
        Product product2 = new Product("Product 2", 5, 29.99, "Description 2");

        db.addToDatabase("products.cvs", product1.toString());
        db.addToDatabase("products.cvs", product2.toString());



        // Test product creation
        boolean productAdded = seller.createNewProduct(store1.getStoreName(), product1.getName(), product1.getAvailableQuantity(), product1.getPrice(), product1.getDescription());
        System.out.println("Product 1 added to Store 1: " + productAdded);




        // Test product information modification
        boolean productModified = seller.editProduct(store1.getStoreName(), product1.getName(), "price", "24.99");
        System.out.println("Product 1 price modified: " + productModified);

        // Test product deletion

        // Test viewing sales information and shopping cart information
        System.out.println("Store 1 Sales:");
        seller.viewStoreSales();

        System.out.println("Shopping Carts in Store 2:");
        seller.viewCustomerShoppingCarts();

        // Test product import and export
        boolean productsImported = seller.importProducts("imported_products.csv", store1.getStoreName());
        System.out.println("Products imported to Store 1: " + productsImported);

        boolean productsExported = seller.exportProducts(store1.getStoreName());
        System.out.println("Products exported from Store 1: " + productsExported);
    }
}
