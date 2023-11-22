import java.util.ArrayList;

/**
 * Project 4 - TestStore.java
 *
 * Class that handles all testing related to the functionalities implemented in
 * the store class.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version November 21, 2023
 */
public class StoreTest {
    public static void main(String[] args) {

        CreateDatabaseData data = new CreateDatabaseData();
        data.clearData();

        /* INITIALIZING A NEW STORE AND DATABASE FOR TESTING PURPOSES */
        Store newStore = new Store("Fruit Marketplace");
        Database db = new Database();

        /* STORE CLASS TESTS */
        System.out.println("Get Products From Store That Doesn\'t Contain Products Test Result: "
                + testGetProductsUnsuccessful(newStore));
        System.out.println("Get Products From Store That Contains Products Test Result: "
                + testGetProductsSuccessful(newStore, db));

    }

    static String testGetProductsSuccessful(Store st, Database db) {
        String productEntryOne = "S1000000,ST1000000,PR1000000,Fruit Marketplace,Apple,10,5.99,Kashmiri Apples";
        String productEntryTwo = "S1000000,ST1000000,PR1000001,Fruit Marketplace,Banana,5,3.20,Ripe Bananas";
        String productEntryThree = "S1000000,ST1000000,PR1000002,Fruit Marketplace,Kiwi,7,2.50,Fresh Kiwis";
        db.addToDatabase("products.csv", productEntryOne);
        db.addToDatabase("products.csv", productEntryTwo);
        db.addToDatabase("products.csv", productEntryThree);
        ArrayList<Product> products = st.getProducts();
        String resultProducts = "";
        for (int i = 0; i < products.size(); i++) {
            if (i == products.size() - 1) {
                resultProducts += products.get(i).toString();
            } else {
                resultProducts += products.get(i).toString() + "\n";
            }
        }
        String expectedProducts = "Apple,10,5.99,Kashmiri Apples\n" +
                "Banana,5,3.20,Ripe Bananas\n" +
                "Kiwi,7,2.50,Fresh Kiwis";
        return resultProducts.equals(expectedProducts) ? "Test Passed" : "Test Failed";
    }

    static String testGetProductsUnsuccessful(Store st) {
        ArrayList<Product> resultProducts = st.getProducts();
        return resultProducts == null ? "Test Passed" : "Test Failed";
    }
}