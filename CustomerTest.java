import static org.junit.Assert.assertEquals;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;

/**
 * Project 4 - CustomerTest.java
 *
 * Class that handles all testing related to the functionalities implemented in
 * the Customer class.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 *
 * @version November 13, 2023
 */
public class CustomerTest {
        public static void main(String[] args) {
                // Instatiated a new customer and database for testing
                Customer c = new Customer("C100001", "customer@gmail.com", "customer", UserRole.CUSTOMER);
                Customer c2 = new Customer("C100003", "empty@gmail.com", "customer", UserRole.CUSTOMER);

                c2.exportPurchaseHistory();
                Database db = new Database();

                // Creates testing data
                CreateDatabaseData.clearData();
                testGetAllProductsWhenEmpty(c);
                CreateDatabaseData.createData();

                // List of all tests
                testGetProductInfo(c);
                testAddToCart(c, db);
                testGetCart(c, c2);
                testRemoveFromCart(c, db);
                testGetShoppingHistory(c, c2);
                testPurchaseItems(c, db);
                testSortProducts(c);
                testSearchProducts(c);
                testExportPurchaseHistory(c, c2);

        }

        @Test(timeout = 1000)
        public static void testGetProductInfo(Customer c) {
                // Verifies Out of Bounds Edgecases
                assertEquals("Error in Out of Bounds Detection", "Invalid Product", c.getProductInfo(0).trim());
                assertEquals("Error in Out of Bounds Detection", "Invalid Product", c.getProductInfo(-1).trim());
                assertEquals("Error in Out of Bounds Detection", "Invalid Product", c.getProductInfo(100).trim());

                // Verifies File Reading and Formatting
                assertEquals("Incorrect Product Info Gathered", "Store Name: myStore\n" +
                                "Product Name: myProduct\n" +
                                "Available Quantity: 1000\n" +
                                "Price: 25\n" +
                                "Description: Its a product!", c.getProductInfo(1).replace("\r\n", "\n").trim());

                assertEquals("Incorrect Product Info Gathered", "Store Name: myStore\n" +
                                "Product Name: mySecondProduct\n" +
                                "Available Quantity: 1000\n" +
                                "Price: 1000\n" +
                                "Description: Its a product!", c.getProductInfo(2).replace("\r\n", "\n").trim());

                assertEquals("Incorrect Product Info Gathered", "Store Name: otherStore\n" +
                                "Product Name: otherProduct\n" +
                                "Available Quantity: 1000\n" +
                                "Price: 1\n" +
                                "Description: Its a product!", c.getProductInfo(3).replace("\r\n", "\n").trim());
                System.out.println("getProductInfo ... OK");

        }

        @Test(timeout = 1000)
        public static void testAddToCart(Customer c, Database db) {
                // Verifies Out of Bounds Edgecases
                assertEquals("Quantity Below Bounds Detection Error", false, c.addToCart(1, -1));
                assertEquals("Quantity Above Bounds Detection Error", false, c.addToCart(1, 10000));
                assertEquals("Index Below Bounds Detection Error", false, c.addToCart(-1, 1));
                assertEquals("Index Above Bounds Detection Error", false, c.addToCart(100, 1));

                // Verifies Adding Product and Formatting
                c.addToCart(1, 10);
                assertEquals("Add To Cart Error", "C100001,S100001,ST100001,PR100001,myStore,myProduct,10,250.0",
                                db.getMatchedEntries("shoppingCarts.csv", 0, c.getUserID()).get(0).trim());

                c.addToCart(1, 20);
                assertEquals("Add To Cart Error", "C100001,S100001,ST100001,PR100001,myStore,myProduct,30,750.0",
                                db.getMatchedEntries("shoppingCarts.csv", 0, c.getUserID()).get(0).trim());

                c.addToCart(2, 100);
                assertEquals("Add To Cart Error",
                                "C100001,S100001,ST100001,PR100002,myStore,mySecondProduct,100,100000.0",
                                db.getMatchedEntries("shoppingCarts.csv", 0, c.getUserID()).get(1).trim());

                System.out.println("addToCart ... OK");
        }

        @Test(timeout = 1000)
        public static void testGetCart(Customer c, Customer c2) {
                // Verifies the Formatting of Getting Products in the Cart
                assertEquals("Error Retrieving Cart", "Product Name - Store Name - Quantity - Price\n" +
                                "1) myProduct     myStore     30     750.0     \n" +
                                "2) mySecondProduct     myStore     100     100000.0",
                                c.getCart().replace("\r\n", "\n").trim());

                // Verifies the condition when the shopping cart is empty
                assertEquals("Error with Empty Cart", "No Products Available", c2.getCart());
                System.out.println("getCart ... OK");
        }

        @Test(timeout = 1000)
        public static void testRemoveFromCart(Customer c, Database db) {
                // Verifies Out of Bounds Edgecases
                assertEquals("Quantity Below Bounds Detection Error", false, c.removeFromCart(-1));
                assertEquals("Quantity Above Bounds Detection Error", false, c.removeFromCart(100));

                // Verifies Item Removal From Cart
                c.removeFromCart(1);
                assertEquals("Remove From Cart Error", true,
                                db.getMatchedEntries("shoppingCarts.csv", 3, "PR100001").isEmpty());

                c.removeFromCart(1);
                assertEquals("Remove From Cart Error", true,
                                db.getMatchedEntries("shoppingCarts.csv", 3, "PR100002").isEmpty());

                System.out.println("removeFromCart ... OK");
        }

        @Test(timeout = 1000)
        public static void testGetShoppingHistory(Customer c, Customer c2) {
                // Verifies the Formatting of Getting Products from History
                assertEquals("Product Name - Store Name - Quantity - Price\n" +
                                "1) myProduct     myStore     10     250     \n" +
                                "2) mySecondProduct     myStore     1     1000     \n" +
                                "3) otherProduct     otherStore     1     1",
                                c.getShoppingHistory().replace("\r\n", "\n").trim());

                // Verifies the condition when the shopping history is empty
                assertEquals("Error with Empty Shopping History", "No Products Available", c2.getShoppingHistory());
                System.out.println("getShoppingHistory ... OK");
        }

        @Test(timeout = 1000)
        public static void testGetAllProducts(Customer c) {
                // Verifies the Formatting of Getting All Products from Marketplace
                assertEquals("Product Name - Store Name - Price\n" +
                                "1) myProduct     myStore     25     \n" +
                                "2) mySecondProduct     myStore     1000     \n" +
                                "3) otherProduct     otherStore     1     \n" +
                                "4) productamondo     otherStore     5",
                                c.getAllProducts().replace("\r\n", "\n").trim());

                System.out.println("getAllProducts ... OK");
        }

        @Test(timeout = 1000)
        public static void testGetAllProductsWhenEmpty(Customer c) {
                // Verifies the condition when the shopping history is empty
                assertEquals("Error with Empty Shopping History", "No Products Available", c.getAllProducts());
                System.out.println("testGetAllProductsWhenEmpty ... OK");
        }

        @Test(timeout = 1000)
        public static void testPurchaseItems(Customer c, Database db) {
                // Adds 10 of the first product in the marketplace to cart
                c.addToCart(1, 10);
                // Purchases the 10 items
                c.purchaseItems();

                // Verifies the shopping cart is empty
                assertEquals("Remove From Cart Error", true,
                                db.getMatchedEntries("shoppingCarts.csv", 0, "C100001").isEmpty());

                // Verifies the updated purchase history
                assertEquals("Update Purchase History Error",
                                "C100001,S100001,ST100001,PR100001,myStore,myProduct,20,500.0",
                                db.getMatchedEntries("purchaseHistories.csv", 0, "C100001").get(0).trim());

                // Verifies the update product's quantity in the marketplace
                assertEquals("Update Product Quantity Error",
                                "S100001,ST100001,PR100001,myStore,myProduct,990,25,Its a product!",
                                db.getMatchedEntries("products.csv", 2, "PR100001").get(0).trim());

                System.out.println("purchaseItems ... OK");
        }

        @Test(timeout = 1000)
        public static void testSortProducts(Customer c) {

                // Verifies the invalid input
                assertEquals("Search Catch Error", "Invalid search!",
                                c.sortProducts("random unsortable entry"));

                // Verifies the sorting by price
                assertEquals("Invalid Sort Format - Price", "Product Name - Store Name - Price\n" +
                                "1) otherProduct     otherStore     1     \n" +
                                "2) productamondo     otherStore     5     \n" +
                                "3) myProduct     myStore     25     \n" +
                                "4) mySecondProduct     myStore     1000",
                                c.sortProducts("price").replace("\r\n", "\n").trim());

                // Verifies the sorting by quantity
                assertEquals("Invalid Sort Format - Quantity", "Product Name - Store Name - Quantity - Price\n" +
                                "1) myProduct     myStore     990     25     \n" +
                                "2) mySecondProduct     myStore     1000     1000     \n" +
                                "3) otherProduct     otherStore     1000     1     \n" +
                                "4) productamondo     otherStore     1000     5",
                                c.sortProducts("quantity").replace("\r\n", "\n").trim());

                System.out.println("sortStores ... OK");
        }

        @Test(timeout = 1000)
        public static void testSearchProducts(Customer c) {

                // Verifies the invalid input
                assertEquals("Search Catch Error", "Query has returned no results!",
                                c.searchProducts("random invalid search"));

                // Verifies the searching by store name
                assertEquals("Invalid Search Result", "Product Name - Store Name - Price\n" +
                                "1) otherProduct     otherStore     1     \n" +
                                "2) productamondo     otherStore     5",
                                c.searchProducts("otherStore").replace("\r\n", "\n").trim());

                // Verifies the searching by product name
                assertEquals("Invalid Search Result", "Product Name - Store Name - Price\n" +
                                "1) otherProduct     otherStore     1",
                                c.searchProducts("otherProduct").replace("\r\n", "\n").trim());

                // Verifies the searching by description
                assertEquals("Invalid Search Format - Quantity", "Product Name - Store Name - Price\n" +
                                "1) myProduct     myStore     25     \n" +
                                "2) mySecondProduct     myStore     1000     \n" +
                                "3) otherProduct     otherStore     1     \n" +
                                "4) productamondo     otherStore     5",
                                c.searchProducts("Its a product!").replace("\r\n", "\n").trim());

                System.out.println("searchProcuts ... OK");
        }

        @Test(timeout = 1000)
        public static void testExportPurchaseHistory(Customer c, Customer c2) {
                // Tests Returns true -> file created / false -> no file created
                assertEquals("Export History Boolean Return Error", true, c.exportPurchaseHistory());
                assertEquals("Export History Boolean Return Error", false, c2.exportPurchaseHistory());

                File f = new File("exportedHistory/empty@gmail.com.csv");

                assertEquals("File Should Not Exist Error", false, f.exists());

                // Tests the Customer's Purchase History has been exported properly
                StringBuilder sb = new StringBuilder();
                try (BufferedReader br = new BufferedReader(
                                new FileReader(new File("exportedHistory/customer@gmail.com.csv")))) {
                        br.readLine(); // skip headers
                        String line;
                        while ((line = br.readLine()) != null) {
                                sb.append(line).append("\n");
                        }
                        assertEquals("Export Purchase History Error",
                                        "C100001,S100001,ST100001,PR100001,myStore,myProduct,10,250\n" +
                                                        "C100001,S100001,ST100001,PR100002,myStore," +
                                                "mySecondProduct,1,1000\n"
                                                        +
                                                        "C100001,S100002,ST100002,PR100003,otherStore," +
                                                "otherProduct,1,1",
                                        sb.toString().trim());

                        System.out.println("exportPurchaseHistory ... OK");
                } catch (IOException e) {
                        System.out.println("Exported Purchase History File Directory Does Not Exist");
                        e.printStackTrace();
                }
        }

}
