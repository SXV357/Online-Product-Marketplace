import java.util.ArrayList;

/**
 * Project 4 - TestDatabase.java
 * 
 * Class that handles all testing related to the functionalities implemented in the database class.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 7, 2023
 */
public class TestDatabase {
    static final String nonExistentUserEntry = "100678,hello@yahoo.com,76ybgjh,Seller";
    static final String nonExistentStoreEntry = "100912,Store100,90";
    static final String nonExistentProductEntry = "100912,Store100,Apple,10,2,This is an apple";
    static final String nonExistentShoppingCartEntry = "100678,100912,Store100,Apple,5,10";
    static final String nonExistentPurchaseHistoryEntry = "100678,100912,Store100,Apple,5,10";

    public static void main(String[] args) {
        Database db = new Database();
        // testGetFileHeaders(db); --> OK
        // testDatabaseColumnBounds(db); --> OK
        // testAddInitialDatabaseEntries(db); --> OK
        // testAddAdditionalDatabaseEntries(db); --> OK
        // testSuccessfulDatabaseModification(db); --> OK
        // testUnsuccessfulDatabaseModification(db); --> OK
        // testSuccessfulDatabaseRemoval(db); --> OK
        // testUnsuccessfulDatabaseRemoval(db); --> OK
        // testMatchedUserIDs(db); --> OK
        // testUserRetrievalFromDatabase(db); --> OK
        // testFileEntryExists(db); --> OK
        // testGetDatabaseContents(db); --> OK
    }

    static void testGetFileHeaders(Database db) {
        String result = "";
        String[] fileNames = {"stores.csv", "purchaseHistories.csv", "users.csv", "shoppingCarts.csv", "products.csv"};
        for (int i = 0; i < fileNames.length; i++) {
            if (i != fileNames.length - 1) {
                result += db.getFileHeaders(fileNames[i] + "\n");
            } else {
                result += db.getFileHeaders(fileNames[i]);
            }
        }
        assert result.equals("Store ID,Seller ID,Store Name,Number of Products\n" +
                            "Customer ID,Seller ID,Store ID,Store Name,Product ID,Product Name,Purchase Quantity,Price\n" +
                            "ID,Email,Password,Role\n" +
                            "Customer ID,Seller ID,Store ID,Store Name,Product ID,Product Name,Purchase Quantity,Price\n" +
                            "Store ID,Seller ID,Product ID,Store Name,Product Name,Available Quantity,Price,Description");
    }

    static void testDatabaseColumnBounds(Database db) {
        boolean userColumnValid = db.checkColumnBounds("users.csv", 2);
        boolean userColumnInvalid = db.checkColumnBounds("users.csv", 7);

        boolean storeColumnValid = db.checkColumnBounds("stores.csv", 1);
        boolean storeColumnInvalid = db.checkColumnBounds("stores.csv", 3);

        boolean productColumnValid = db.checkColumnBounds("products.csv", 3);
        boolean productColumnInvalid = db.checkColumnBounds("products.csv", 10);

        boolean shoppingCartColumnValid = db.checkColumnBounds("shoppingCarts.csv", 0);
        boolean shoppingCartColumnInvalid = db.checkColumnBounds("shoppingCarts.csv", -2);

        boolean purchaseHistoryColumnValid = db.checkColumnBounds("purchaseHistories.csv", 4);
        boolean purchaseHistoryColumnInvalid = db.checkColumnBounds("purchaseHistories.csv", 6);

        assert userColumnValid && !userColumnInvalid && storeColumnValid && !storeColumnInvalid && productColumnValid && !productColumnInvalid && shoppingCartColumnValid && !shoppingCartColumnInvalid && purchaseHistoryColumnValid && !purchaseHistoryColumnInvalid;
    }

    static void testAddInitialDatabaseEntries(Database db) {
        // initially adding one entry to each of the databases
        String userEntry = "C100000,blabla@yahoo.com,abc123,CUSTOMER";
        String storeEntry = "ST100000,S1000000,Store1,5";
        String productEntry = "S1000000,ST1000000,PR1000000,Store1,Burrito,50,5.99,This is a breakfast burrito";
        String shoppingCartEntry = "C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9";
        String purchaseHistoryEntry = "C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9";
        db.addToDatabase("users.csv", userEntry);
        db.addToDatabase("stores.csv", storeEntry);
        db.addToDatabase("products.csv", productEntry);
        db.addToDatabase("shoppingCarts.csv", shoppingCartEntry);
        db.addToDatabase("purchaseHistories.csv", purchaseHistoryEntry);
    }

    static void testAddAdditionalDatabaseEntries(Database db) {
        // adding an additional entry to each of the databases
        String newUserEntry = "C100001,blabla@gmail.com,abc123,CUSTOMER";
        String newStoreEntry = "ST100001,S1000001,Store2,10";
        String newProductEntry = "S1000001,ST1000001,PR1000001,Store2,Apple,10,5.99,This is an apple";
        String newShoppingCartEntry = "C1000001,S1000001,ST1000001,PR1000001,Store2,Apple,5,29.95";
        String newPurchaseHistoryEntry = "C1000001,S1000001,ST1000001,PR1000001,Store2,Apple,5,29.95";
        db.addToDatabase("users.csv", newUserEntry);
        db.addToDatabase("stores.csv", newStoreEntry);
        db.addToDatabase("products.csv", newProductEntry);
        db.addToDatabase("shoppingCarts.csv", newShoppingCartEntry);
        db.addToDatabase("purchaseHistories.csv", newPurchaseHistoryEntry);
    }

    static void testSuccessfulDatabaseModification(Database db) {
        // modifying entries that already exist within the database
        String currUserEntry = "C100001,blabla@gmail.com,abc123,CUSTOMER";
        String currStoreEntry = "ST100001,S1000001,Store2,10";
        String currProductEntry = "S1000001,ST1000001,PR1000001,Store2,Apple,10,5.99,This is an apple";
        String currShoppingCartEntry = "C1000001,S1000001,ST1000001,PR1000001,Store2,Apple,5,29.95";
        String currPurchaseHistoryEntry = "C1000001,S1000001,ST1000001,PR1000001,Store2,Apple,5,29.95";

        boolean userModified = db.modifyDatabase("users.csv", currUserEntry, "C100001,blabla123@gmail.com,abc123,CUSTOMER");
        boolean storeModified = db.modifyDatabase("stores.csv", currStoreEntry, "ST100001,S1000001,StoreSomething,10");
        boolean productModified = db.modifyDatabase("products.csv", currProductEntry, "S1000001,ST1000001,PR1000001,Store2,Apple,10,5.99,Green apple");
        boolean shoppingCartModified = db.modifyDatabase("shoppingCarts.csv", currShoppingCartEntry, "C1000001,S1000001,ST1000001,PR1000001,Store2,Apple,6,35.94");
        boolean purchaseHistoryModified = db.modifyDatabase("purchaseHistories.csv", currPurchaseHistoryEntry, "C1000001,S1000001,ST1000001,PR1000001,Store2,Apple,6,35.94");

        assert userModified && storeModified && productModified && shoppingCartModified && purchaseHistoryModified;
    }

    static void testUnsuccessfulDatabaseModification(Database db) {
        // modifying entries that don't exist in the database
        boolean userModified = db.modifyDatabase("users.csv", nonExistentUserEntry, "100678,hello@yahoo.com,jhghgf,Seller");
        boolean storeModified = db.modifyDatabase("stores.csv", nonExistentStoreEntry, "100912,Store102,90");
        boolean productModified = db.modifyDatabase("products.csv", nonExistentProductEntry, "100912,Store100,Apple,10,3,This is an apple");
        boolean shoppingCartModified = db.modifyDatabase("shoppingCarts.csv", nonExistentShoppingCartEntry, "100678,100912,Store100,Apple,5,15");
        boolean purchaseHistoryModified = db.modifyDatabase("purchaseHistories.csv", nonExistentPurchaseHistoryEntry, "100678,100912,Store100,Apple,5,15");
        assert !userModified && !storeModified && !productModified && !shoppingCartModified && !purchaseHistoryModified;
    }

    static void testSuccessfulDatabaseRemoval(Database db) {
        // removing entries that exist in the databases
        String currUserEntry = "C100001,blabla123@gmail.com,abc123,CUSTOMER";
        String currStoreEntry = "ST100001,S1000001,StoreSomething,10";
        String currProductEntry = "S1000001,ST1000001,PR1000001,Store2,Apple,10,5.99,Green apple";
        String currShoppingCartEntry = "C1000001,S1000001,ST1000001,PR1000001,Store2,Apple,6,35.94";
        String currPurchaseHistoryEntry = "C1000001,S1000001,ST1000001,PR1000001,Store2,Apple,6,35.94";
        db.removeFromDatabase("users.csv", currUserEntry);
        db.removeFromDatabase("stores.csv", currStoreEntry);
        db.removeFromDatabase("products.csv", currProductEntry);
        db.removeFromDatabase("shoppingCarts.csv", currShoppingCartEntry);
        db.removeFromDatabase("purchaseHistories.csv", currPurchaseHistoryEntry);
    }

    static void testUnsuccessfulDatabaseRemoval(Database db) {
        // removing entries that don't exist in the database
        db.removeFromDatabase("users.csv", nonExistentUserEntry);
        db.removeFromDatabase("stores.csv", nonExistentStoreEntry);
        db.removeFromDatabase("products.csv", nonExistentProductEntry);
        db.removeFromDatabase("shoppingCarts.csv", nonExistentShoppingCartEntry);
        db.removeFromDatabase("purchaseHistories.csv", nonExistentPurchaseHistoryEntry);
    }

    static void testMatchedUserIDs(Database db) {
        // for users.csv
        boolean userIDMatch = db.checkIDMatch(100000, "users.csv");
        boolean userIDNonMatch = db.checkIDMatch(123456, "users.csv");
        // for stores.csv
        boolean storeIDMatch = db.checkIDMatch(100000, "stores.csv");
        boolean storeIDNonMatch = db.checkIDMatch(123456, "stores.csv");
        // for products.csv
        boolean productIDMatch = db.checkIDMatch(1000000, "products.csv");
        boolean productIDNonMatch = db.checkIDMatch(1234567, "products.csv");
        assert (userIDMatch && storeIDMatch && productIDMatch) && (!userIDNonMatch && !storeIDNonMatch && !productIDNonMatch);
    }

    static void testUserRetrievalFromDatabase(Database db) {
        String matchedUser = db.retrieveUser("blabla@yahoo.com", "abc123");
        String noMatchedUser = db.retrieveUser("hello@gmail.com", "123456");
        assert matchedUser.equals("C100000,blabla@yahoo.com,abc123,CUSTOMER") && noMatchedUser == null;
    }

    static void testFileEntryExists(Database db) {
        // for users.csv
        assert db.checkEntryExists("users.csv", "C100000,blabla@yahoo.com,abc123,CUSTOMER");
        assert !(db.checkEntryExists("users.csv", nonExistentUserEntry));
        // for stores.csv
        assert db.checkEntryExists("stores.csv", "ST100000,S1000000,Store1,5");
        assert !(db.checkEntryExists("stores.csv", nonExistentStoreEntry));
        // for products.csv
        assert db.checkEntryExists("products.csv", "S1000000,ST1000000,PR1000000,Store1,Burrito,50,5.99,This is a breakfast burrito");
        assert !(db.checkEntryExists("products.csv", nonExistentProductEntry));
        // for shoppingCarts.csv
        assert db.checkEntryExists("shoppingCarts.csv", "C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9");
        assert !(db.checkEntryExists("shoppingCarts.csv", nonExistentShoppingCartEntry));
        // for purchaseHistories.csv
        assert db.checkEntryExists("purchaseHistories.csv", "C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9");
        assert !(db.checkEntryExists("purchaseHistories.csv", nonExistentPurchaseHistoryEntry));
    }

    static void testGetDatabaseContents(Database db) {
        ArrayList<String> users = db.getDatabaseContents("users.csv");
        ArrayList<String> stores = db.getDatabaseContents("stores.csv");
        ArrayList<String> products = db.getDatabaseContents("products.csv");
        ArrayList<String> shoppingCarts = db.getDatabaseContents("shoppingCarts.csv");
        ArrayList<String> purchaseHistories = db.getDatabaseContents("purchaseHistories.csv");
        System.out.println(users);
        System.out.println(stores);
        System.out.println(products);
        System.out.println(shoppingCarts);
        System.out.println(purchaseHistories);

    }

}