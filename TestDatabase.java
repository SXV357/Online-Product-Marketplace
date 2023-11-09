import java.util.ArrayList;

/**
 * Project 4 - TestDatabase.java
 * 
 * Class that handles all testing related to the functionalities implemented in the database class.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 9, 2023
 */
public class TestDatabase {
    static final String nonExistentUserEntry = "100678,hello@yahoo.com,76ybgjh,Seller";
    static final String nonExistentStoreEntry = "100912,Store100,90";
    static final String nonExistentProductEntry = "100912,Store100,Apple,10,2,This is an apple";
    static final String nonExistentShoppingCartEntry = "100678,100912,Store100,Apple,5,10";
    static final String nonExistentPurchaseHistoryEntry = "100678,100912,Store100,Apple,5,10";

    public static void main(String[] args) {
        Database db = new Database();
        // testGetFileHeaders(db); -> OK
        // testDatabaseColumnBounds(db); -> OK
        // testAddInitialDatabaseEntries(db); -> OK
        // testAddAdditionalDatabaseEntries(db); -> OK
        // testAddDuplicateDatabaseEntries(db); -> OK
        // testSuccessfulDatabaseModification(db); -> OK
        // testUnsuccessfulDatabaseModification(db); -> OK
        // testSuccessfulDatabaseRemoval(db); -> OK
        // testUnsuccessfulDatabaseRemoval(db); -> OK
        // testMatchedUserIDs(db); -> OK
        // testUserRetrievalFromDatabase(db); -> OK
        // testFileEntryExists(db); -> OK
        // testUnsuccessfulFileEntryExists(db); -> OK
        // testSuccessfulGetDatabaseContents(db); -> OK
        // testUnsuccessfulGetDatabaseContents(db); -> OK
        // testUnsuccessfulGetMatchedEntries(db); -> OK
    }

    static void testGetFileHeaders(Database db) {
        String result = "";
        String[] fileNames = {"stores.csv", "purchaseHistories.csv", "users.csv", "shoppingCarts.csv", "products.csv"};
        for (int i = 0; i < fileNames.length; i++) {
            if (i != fileNames.length - 1) {
                result += db.getFileHeaders(fileNames[i]) + "\n";
            } else {
                result += db.getFileHeaders(fileNames[i]);
            }
        }
        String expected =   "Store ID,Seller ID,Store Name,Number of Products\n" +
                            "Customer ID,Seller ID,Store ID,Product ID,Store Name,Product Name,Purchase Quantity,Price\n" +
                            "ID,Email,Password,Role\n" +
                            "Customer ID,Seller ID,Store ID,Product ID,Store Name,Product Name,Purchase Quantity,Price\n" +
                            "Seller ID,Store ID,Product ID,Store Name,Product Name,Available Quantity,Price,Description";
        System.out.println(result.equals(expected));
    }

    static void testDatabaseColumnBounds(Database db) {
        // 4 columns in users.csv
        boolean userColumnValid = db.checkColumnBounds("users.csv", 2);
        boolean userColumnInvalid = db.checkColumnBounds("users.csv", 7);

        // 4 columns in stores.csv
        boolean storeColumnValid = db.checkColumnBounds("stores.csv", 1);
        boolean storeColumnInvalid = db.checkColumnBounds("stores.csv", 4);

        // 8 columns in products.csv
        boolean productColumnValid = db.checkColumnBounds("products.csv", 3);
        boolean productColumnInvalid = db.checkColumnBounds("products.csv", 10);

        // 8 columns in shoppingCarts.csv
        boolean shoppingCartColumnValid = db.checkColumnBounds("shoppingCarts.csv", 0);
        boolean shoppingCartColumnInvalid = db.checkColumnBounds("shoppingCarts.csv", -2);

        // 8 columns in purchaseHistories.csv
        boolean purchaseHistoryColumnValid = db.checkColumnBounds("purchaseHistories.csv", 4);
        boolean purchaseHistoryColumnInvalid = db.checkColumnBounds("purchaseHistories.csv", 8);

        System.out.println(userColumnValid); // true
        System.out.println(!userColumnInvalid); // true

        System.out.println(storeColumnValid); // true
        System.out.println(!storeColumnInvalid); // true

        System.out.println(productColumnValid); // true
        System.out.println(!productColumnInvalid); // true

        System.out.println(shoppingCartColumnValid); // true
        System.out.println(!shoppingCartColumnInvalid); // true

        System.out.println(purchaseHistoryColumnValid); // true
        System.out.println(!purchaseHistoryColumnInvalid); // true
    }

    static void testAddInitialDatabaseEntries(Database db) {
        // Initially adding entries to each of the databases(Ones that don't exist yet)
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
        // Adding an additional entry to each of the databases(Ones that don't exist yet)
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

    static void testAddDuplicateDatabaseEntries(Database db) {
        // Adding entries to each of the databasees(Ones that already exist)
        String currUserEntry = "C100001,blabla@gmail.com,abc123,CUSTOMER";
        String currStoreEntry = "ST100001,S1000001,Store2,10";
        String currProductEntry = "S1000001,ST1000001,PR1000001,Store2,Apple,10,5.99,This is an apple";
        String currShoppingCartEntry = "C1000001,S1000001,ST1000001,PR1000001,Store2,Apple,5,29.95";
        String currPurchaseHistoryEntry = "C1000001,S1000001,ST1000001,PR1000001,Store2,Apple,5,29.95";
        db.addToDatabase("users.csv", currUserEntry);
        db.addToDatabase("stores.csv", currStoreEntry);
        db.addToDatabase("products.csv", currProductEntry);
        db.addToDatabase("shoppingCarts.csv", currShoppingCartEntry);
        db.addToDatabase("purchaseHistories.csv", currPurchaseHistoryEntry);
    }

    @SuppressWarnings("unused")
    static void testSuccessfulDatabaseModification(Database db) {
        // Modifying entries that already exist in all the databases
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
    }

    @SuppressWarnings("unused")
    static void testUnsuccessfulDatabaseModification(Database db) {
        // Modifying entries that don't exist in any of the databases
        boolean userModified = db.modifyDatabase("users.csv", nonExistentUserEntry, "100678,hello@yahoo.com,jhghgf,Seller");
        boolean storeModified = db.modifyDatabase("stores.csv", nonExistentStoreEntry, "100912,Store102,90");
        boolean productModified = db.modifyDatabase("products.csv", nonExistentProductEntry, "100912,Store100,Apple,10,3,This is an apple");
        boolean shoppingCartModified = db.modifyDatabase("shoppingCarts.csv", nonExistentShoppingCartEntry, "100678,100912,Store100,Apple,5,15");
        boolean purchaseHistoryModified = db.modifyDatabase("purchaseHistories.csv", nonExistentPurchaseHistoryEntry, "100678,100912,Store100,Apple,5,15");
    }

    static void testSuccessfulDatabaseRemoval(Database db) {
        // Removing entries that already exist in all the databases
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
        // Removing entries that don't exist in any of the databases
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
        
        System.out.println(userIDMatch); // true
        System.out.println(!userIDNonMatch); // true

        System.out.println(storeIDMatch); // true
        System.out.println(!storeIDNonMatch); // true

        System.out.println(productIDMatch); // true
        System.out.println(!productIDNonMatch); // true

    }

    static void testUserRetrievalFromDatabase(Database db) {
        // Try retrieving a user that exists in the database and a user that doesn't
        String matchedUser = db.retrieveUser("blabla@yahoo.com", "abc123");
        String noMatchedUser = db.retrieveUser("hello@gmail.com", "123456");
        System.out.println(matchedUser.equals("C100000,blabla@yahoo.com,abc123,CUSTOMER")); // true
        System.out.println(noMatchedUser == null); // true
    }

    static void testFileEntryExists(Database db) {
        // When checking for valid or invalid entries in existing files
        // for users.csv
        boolean userEntryExists =  db.checkEntryExists("users.csv", "C100000,blabla@yahoo.com,abc123,CUSTOMER");
        boolean userEntryNonExistence =  db.checkEntryExists("users.csv", nonExistentUserEntry);

        // for stores.csv
        boolean storeEntryExists =  db.checkEntryExists("stores.csv", "ST100000,S1000000,Store1,5");
        boolean storeEntryNonExistence =  db.checkEntryExists("stores.csv", nonExistentStoreEntry);

        // for products.csv
        boolean productEntryExists =  db.checkEntryExists("products.csv", "S1000000,ST1000000,PR1000000,Store1,Burrito,50,5.99,This is a breakfast burrito");
        boolean productEntryNonExistence =  db.checkEntryExists("products.csv", nonExistentProductEntry);

        // for shoppingCarts.csv
        boolean shoppingEntryExists =  db.checkEntryExists("shoppingCarts.csv", "C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9");
        boolean shoppingEntryNonExistence =  db.checkEntryExists("shoppingCarts.csv", nonExistentShoppingCartEntry);

        // for purchaseHistories.csv
        boolean purchaseHistoryEntryExists =  db.checkEntryExists("purchaseHistories.csv", "C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9");
        boolean purchaseHistoryEntryNonExistence =  db.checkEntryExists("purchaseHistories.csv", nonExistentPurchaseHistoryEntry);

        System.out.println(userEntryExists); // true
        System.out.println(!userEntryNonExistence); // true

        System.out.println(storeEntryExists); // true
        System.out.println(!storeEntryNonExistence); // true

        System.out.println(productEntryExists); // true
        System.out.println(!productEntryNonExistence); // true

        System.out.println(shoppingEntryExists); // true
        System.out.println(!shoppingEntryNonExistence); // true

        System.out.println(purchaseHistoryEntryExists); // true
        System.out.println(!purchaseHistoryEntryNonExistence); // true
    }

    static void testUnsuccessfulFileEntryExists(Database db) {
        // Checking for valid or invalid entries in files that don't exist
        String sampleEntry = "C100000,blabla@yahoo.com,abc123,CUSTOMER";
        String[] nonExistentFiles = {"random.csv", "blabla.csv", "hello.csv", "something.csv"};
        for (int i = 0; i < nonExistentFiles.length; i++) {
            boolean entryExists = db.checkEntryExists(nonExistentFiles[i], sampleEntry);
            System.out.println(!entryExists);
        }
    }

    static void testSuccessfulGetDatabaseContents(Database db) {
        ArrayList<String> users = db.getDatabaseContents("users.csv");
        ArrayList<String> stores = db.getDatabaseContents("stores.csv");
        ArrayList<String> products = db.getDatabaseContents("products.csv");
        ArrayList<String> shoppingCarts = db.getDatabaseContents("shoppingCarts.csv");
        ArrayList<String> purchaseHistories = db.getDatabaseContents("purchaseHistories.csv");
        System.out.println(users); // 1 item
        System.out.println(stores); // 1 item
        System.out.println(products); // 1 item
        System.out.println(shoppingCarts); // 1 item
        System.out.println(purchaseHistories); // 1 item
    }

    static void testUnsuccessfulGetDatabaseContents(Database db) {
        // When the user tries retrieving contents from a non-existent file
        String[] nonExistentFiles = {"random.csv", "blabla.csv", "hello.csv", "something.csv"};
        for (String f: nonExistentFiles) {
            ArrayList<String> contents = db.getDatabaseContents(f);
            System.out.println(contents.isEmpty());
        }
    }

    static void testUnsuccessfulGetMatchedEntries(Database db) {
        ArrayList<String> matchedEntries = db.getMatchedEntries("users.csv", 4, "CUSTOMER");
        System.out.println(matchedEntries.isEmpty()); // The index is out of bounds

        ArrayList<String> matchedEntriesTwo = db.getMatchedEntries("users.csv", 3, "UNDECIDED");
        System.out.println(matchedEntriesTwo.isEmpty()); // The index is in bounds but the search parameter doesn't exist in the specified column

        ArrayList<String> matchedEntriesThree = db.getMatchedEntries("random.csv", 0, "Something");
        System.out.println(matchedEntriesThree.isEmpty()); // The provided file name to search for doesn't exist

    }
}