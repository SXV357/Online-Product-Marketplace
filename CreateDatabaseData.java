import java.util.ArrayList;

/**
 * Project 4 - CreateDatabaseData.java
 * 
 * Class that handles data generation for all the databases for purposes of
 * testing.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * 
 * @version November 11, 2023
 */
public class CreateDatabaseData {

    public static void clearData() {
        Database database = new Database();

        // Users
        ArrayList<String> testUsers = new ArrayList<>();
        database.updateDatabaseContents("users.csv", testUsers);

        // Stores
        ArrayList<String> testStores = new ArrayList<>();
        database.updateDatabaseContents("stores.csv", testStores);

        // Products
        ArrayList<String> testProducts = new ArrayList<>();
        database.updateDatabaseContents("products.csv", testProducts);

        // Purchase Histories
        ArrayList<String> testPurchases = new ArrayList<>();
        database.updateDatabaseContents("purchaseHistories.csv", testPurchases);

        // Shopping Carts
        ArrayList<String> testShoppingcarts = new ArrayList<>();
        database.updateDatabaseContents("shoppingCarts.csv", testShoppingcarts);
    }

    public static void createData() {
        Database database = new Database();

        // Users
        ArrayList<String> testUsers = new ArrayList<>();
        testUsers.add("C100001,cherry@gmail.com,12345,Customer");
        testUsers.add("C100002,banana@gmail.com,1234567,Customer");
        testUsers.add("C100003,apple@gmail.com,765543,Customer");
        testUsers.add("S100001,apricot@gmail.com,12313,Seller");
        testUsers.add("S100002,balogne@gmail.com,12315436,Seller");
        database.updateDatabaseContents("users.csv", testUsers);

        // Stores
        ArrayList<String> testStores = new ArrayList<>();
        testStores.add("ST100001,S100001,myStore,2");
        testStores.add("ST100002,S100002,otherStore,1");
        testStores.add("ST100003,S100002,thirdStore,1");
        database.updateDatabaseContents("stores.csv", testStores);

        // Products
        ArrayList<String> testProducts = new ArrayList<>();
        testProducts.add("S100001,ST100001,PR100001,myStore,myProduct,1000,25,Its a product!");
        testProducts.add("S100001,ST100001,PR100002,myStore,mySecondProduct,1000,1000,Its a product!");
        testProducts.add("S100002,ST100002,PR100003,otherStore,otherProduct,1000,1,Its a product!");
        testProducts.add("S100002,ST100003,PR100004,otherStore,productamondo,1000,5,Its a product!");
        database.updateDatabaseContents("products.csv", testProducts);

        // Purchases
        ArrayList<String> testPurchases = new ArrayList<>();
        // Add purchases to test DB
        // customer 1 purchases
        testPurchases.add("C100001,S100001,ST100001,PR100001,myStore,myProduct,10,250");
        testPurchases.add("C100001,S100001,ST100001,PR100002,myStore,mySecondProduct,1,1000");
        testPurchases.add("C100001,S100002,ST100002,PR100003,otherStore,otherProduct,1,1");
        // customer 2 purchases
        testPurchases.add("C100002,S100002,ST100002,PR100003,otherStore,otherProduct,500,500");
        testPurchases.add("C100002,S100002,ST100002,PR100004,otherStore,productamondo,10,25");

        // customer 3 makes no purchases

        // Write purchaseHistories.csv
        database.updateDatabaseContents("purchaseHistories.csv", testPurchases);

    }

    // SPECIFIC FOR TESTS RELATED TO THE DATABASE CLASS(DO NOT MODIFY)
    public static void createDataForDatabaseTests() {
        Database database = new Database();

        ArrayList<String> testUsers = new ArrayList<>();
        testUsers.add("C100001,cherry@gmail.com,12345,CUSTOMER");
        testUsers.add("S100002,balogne@gmail.com,12315436,SELLER");
        database.updateDatabaseContents("users.csv", testUsers);

        ArrayList<String> testStores = new ArrayList<>();
        testStores.add("ST100001,S100002,newStore1,1");
        testStores.add("ST100002,S100002,newStore2,1");
        database.updateDatabaseContents("stores.csv", testStores);

        ArrayList<String> testProducts = new ArrayList<>();
        testProducts.add("S100002,ST100001,PR100001,newStore1,myProduct,1000,25,Its a product!");
        testProducts.add("S100002,ST100002,PR100002,newStore2,myProduct2,1000,25,Its a new product!");
        database.updateDatabaseContents("products.csv", testProducts);
    }
}