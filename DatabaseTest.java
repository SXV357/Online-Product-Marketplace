import java.util.ArrayList;

/**
 * Project 4 - TestDatabase.java
 * 
 * Class that handles all testing related to the functionalities implemented in
 * the database class.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * 
 * @version November 13, 2023
 */
public class DatabaseTest {
    static final String nonExistentUserEntry = "100678,hello@yahoo.com,76ybgjh,SELLER";
    static final String nonExistentStoreEntry = "100912,Store100,90";
    static final String nonExistentProductEntry = "100912,Store100,Apple,10,2,This is an apple";
    static final String nonExistentShoppingCartEntry = "100678,100912,Store100,Apple,5,10";
    static final String nonExistentPurchaseHistoryEntry = "100678,100912,Store100,Apple,5,10";

    public static void main(String[] args) {

        CreateDatabaseData.clearData();
        CreateDatabaseData.createDataForDatabaseTests();

        /* INITIALIZE A NEW DATABASE INSTANCE FOR TESTING PURPOSES */
        Database db = new Database();

        /* DATABASE CLASS TESTS */

        System.out.println("Get File Headers Test Result: " + testGetFileHeaders(db));
        System.out.println("Database Column Bounds Test Result: " + testDatabaseColumnBounds(db));
        System.out.println("Add Initial Database Entries Test Result: " + testAddInitialDatabaseEntries(db));
        System.out.println("Add Duplicate Database Entries Test Result: " + testAddDuplicateDatabaseEntries(db));
        System.out.println("Modify Existing Database Entries Test Result: " + testSuccessfulDatabaseModification(db));
        System.out.println(
                "Modify Non-Existing Database Entries Test Result: " + testUnsuccessfulDatabaseModification(db));
        System.out.println("Remove Existing Database Entries Test Result: " + testSuccessfulDatabaseRemoval(db));
        System.out.println("Remove Non-Existing Database Entries Test Result: " + testUnsuccessfulDatabaseRemoval(db));
        System.out.println("Check ID Existence Test Result: " + testMatchedUserIDs(db));
        System.out.println(
                "Retrieve User From Database For Login Test Result: " + testUserRetrievalFromDatabaseForLogin(db));
        System.out.println("Database Entry in Existing File(s) Test Result: " + testFileEntryExists(db));
        System.out
                .println("Database Entry in Non-Existing File(s) Test Result: " + testUnsuccessfulFileEntryExists(db));
        System.out.println(
                "Get Database Entries in Existing Files Test Result: " + testSuccessfulGetDatabaseContents(db));
        System.out.println(
                "Get Database Entries in Non-Existing Files Test Result: " + testUnsuccessfulGetDatabaseContents(db));
        System.out.println("Get Matched Entries in Existing Files Test Result: " + testSuccessfulGetMatchedEntries(db));
        System.out.println("Get Matched Entries Unsuccessful Test Result: " + testUnsuccessfulGetMatchedEntries(db));
    }

    static String testGetFileHeaders(Database db) {
        String result = "";
        String[] fileNames = { "stores.csv", "purchaseHistories.csv", "users.csv", "shoppingCarts.csv",
                "products.csv" };
        for (int i = 0; i < fileNames.length; i++) {
            if (i != fileNames.length - 1) {
                result += db.getFileHeaders(fileNames[i]) + "\n";
            } else {
                result += db.getFileHeaders(fileNames[i]);
            }
        }
        String expected = "Store ID,Seller ID,Store Name,Number of Products\n" +
                "Customer ID,Seller ID,Store ID,Product ID,Store Name,Product Name,Purchase Quantity,Price\n" +
                "ID,Email,Password,Role\n" +
                "Customer ID,Seller ID,Store ID,Product ID,Store Name,Product Name,Purchase Quantity,Price\n" +
                "Seller ID,Store ID,Product ID,Store Name,Product Name,Available Quantity,Price,Description";
        return result.equals(expected) ? "Test Passed" : "Test Failed";
    }

    static String testDatabaseColumnBounds(Database db) {
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

        return (userColumnValid && !userColumnInvalid && storeColumnValid && !storeColumnInvalid
                && productColumnValid && !productColumnInvalid && shoppingCartColumnValid &&
                !shoppingCartColumnInvalid
                && purchaseHistoryColumnValid && !purchaseHistoryColumnInvalid) ? "Test Passed" : "Test Failed";
    }

    static String testAddInitialDatabaseEntries(Database db) {
        // Initially adding one entry to each of the databases(None of these entries
        // exist yet)
        String userEntry = "C100000,blabla@yahoo.com,abc123,CUSTOMER";
        String storeEntry = "ST100000,S1000000,Store1,5";
        String productEntry = "S1000000,ST1000000,PR1000000,Store1,Burrito,50,5.99,This is a breakfast burrito";
        String shoppingCartEntry = "C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9";
        String purchaseHistoryEntry = "C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9";
        // Adding each entry to its corresponding database
        db.addToDatabase("users.csv", userEntry);
        db.addToDatabase("stores.csv", storeEntry);
        db.addToDatabase("products.csv", productEntry);
        db.addToDatabase("shoppingCarts.csv", shoppingCartEntry);
        db.addToDatabase("purchaseHistories.csv", purchaseHistoryEntry);

        String resultUserContents = db.getDatabaseContents("users.csv").toString();
        String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
        String resultProductContents = db.getDatabaseContents("products.csv").toString();
        String resultShoppingCartContents = db.getDatabaseContents("shoppingCarts.csv").toString();
        String resultPurchaseHistoryContents = db.getDatabaseContents("purchaseHistories.csv").toString();

        String expectedUserContents = "[C100001,cherry@gmail.com,12345,CUSTOMER, S100002,balogne@gmail.com," +
                "12315436,SELLER, C100000,blabla@yahoo.com,abc123,CUSTOMER]";
        String expectedStoreContents = "[ST100001,S100002,newStore1,1, ST100002,S100002,newStore2,1, ST100000," +
                "S1000000,Store1,5]";
        String expectedProductContents = "[S100002,ST100001,PR100001,newStore1,myProduct,1000,25,Its a product!, " +
                "S100002,ST100002,PR100002,newStore2,myProduct2,1000,25,Its a new product!, S1000000,ST1000000," +
                "PR1000000,Store1,Burrito,50,5.99,This is a breakfast burrito]";
        String expectedShoppingCartContents = "[C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9]";
        String expectedPurchaseHistoryContents = "[C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9]";

        return resultUserContents.equals(expectedUserContents) && resultStoreContents.equals(expectedStoreContents)
                && resultProductContents.equals(expectedProductContents)
                && resultShoppingCartContents.equals(expectedShoppingCartContents)
                && resultPurchaseHistoryContents.equals(expectedPurchaseHistoryContents) ? "Test Passed"
                        : "Test Failed";
    }

    static String testAddDuplicateDatabaseEntries(Database db) {
        // Adding entries to each of the databasees(Ones that already exist)
        String currUserEntry = "C100000,blabla@yahoo.com,abc123,CUSTOMER";
        String currStoreEntry = "ST100000,S1000000,Store1,5";
        String currProductEntry = "S1000000,ST1000000,PR1000000,Store1,Burrito,50,5.99,This is a breakfast burrito";
        String currShoppingCartEntry = "C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9";
        String currPurchaseHistoryEntry = "C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9";
        // Trying to add the contents to the database
        db.addToDatabase("users.csv", currUserEntry);
        db.addToDatabase("stores.csv", currStoreEntry);
        db.addToDatabase("products.csv", currProductEntry);
        db.addToDatabase("shoppingCarts.csv", currShoppingCartEntry);
        db.addToDatabase("purchaseHistories.csv", currPurchaseHistoryEntry);

        String resultUserContents = db.getDatabaseContents("users.csv").toString();
        String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
        String resultProductContents = db.getDatabaseContents("products.csv").toString();
        String resultShoppingCartContents = db.getDatabaseContents("shoppingCarts.csv").toString();
        String resultPurchaseHistoryContents = db.getDatabaseContents("purchaseHistories.csv").toString();

        String expectedUserContents = "[C100001,cherry@gmail.com,12345,CUSTOMER, S100002,balogne@gmail.com,12315436," +
                "SELLER, C100000,blabla@yahoo.com,abc123,CUSTOMER]";
        String expectedStoreContents = "[ST100001,S100002,newStore1,1, ST100002,S100002,newStore2,1, ST100000," +
                "S1000000,Store1,5]";
        String expectedProductContents = "[S100002,ST100001,PR100001,newStore1,myProduct,1000,25,Its a product!, " +
                "S100002,ST100002,PR100002,newStore2,myProduct2,1000,25,Its a new product!, S1000000,ST1000000," +
                "PR1000000,Store1,Burrito,50,5.99,This is a breakfast burrito]";
        String expectedShoppingCartContents = "[C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9]";
        String expectedPurchaseHistoryContents = "[C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9]";

        return resultUserContents.equals(expectedUserContents) && resultStoreContents.equals(expectedStoreContents)
                && resultProductContents.equals(expectedProductContents)
                && resultShoppingCartContents.equals(expectedShoppingCartContents)
                && resultPurchaseHistoryContents.equals(expectedPurchaseHistoryContents) ? "Test Passed"
                        : "Test Failed";
    }

    static String testSuccessfulDatabaseModification(Database db) {
        // Modifying entries that already exist in all the databases
        String currUserEntry = "C100000,blabla@yahoo.com,abc123,CUSTOMER";
        String currStoreEntry = "ST100000,S1000000,Store1,5";
        String currProductEntry = "S1000000,ST1000000,PR1000000,Store1,Burrito,50,5.99,This is a breakfast burrito";
        String currShoppingCartEntry = "C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9";
        String currPurchaseHistoryEntry = "C1000000,S1000000,ST1000000,PR1000000,Store1,Burrito,10,59.9";

        boolean userEntryModified = db.modifyDatabase("users.csv", currUserEntry,
                "C100000,blabla123@gmail.com,abc123,CUSTOMER");
        boolean storEntryModified = db.modifyDatabase("stores.csv", currStoreEntry,
                "ST100000,S1000000,StoreSomething,10");
        boolean productEntryModified = db.modifyDatabase("products.csv", currProductEntry,
                "S1000000,ST1000000,PR1000000,Store1,Apple,10,5.99,Green apple");
        boolean shoppingCartEntryModified = db.modifyDatabase("shoppingCarts.csv", currShoppingCartEntry,
                "C1000000,S1000000,ST1000000,PR1000000,Store1,Apple,6,35.94");
        boolean purchaseHistoryEntryModified = db.modifyDatabase("purchaseHistories.csv", currPurchaseHistoryEntry,
                "C1000000,S1000000,ST1000000,PR1000000,Store1,Apple,6,35.94");

        return (userEntryModified && storEntryModified && productEntryModified && shoppingCartEntryModified
                && purchaseHistoryEntryModified) ? "Test Passed" : "Test Failed";
    }

    static String testUnsuccessfulDatabaseModification(Database db) {
        // Modifying entries that don't exist in any of the databases
        boolean userEntryModified = db.modifyDatabase("users.csv", nonExistentUserEntry,
                "100678,hello@yahoo.com,jhghgf,Seller");
        boolean storEntryModified = db.modifyDatabase("stores.csv", nonExistentStoreEntry, "100912,Store102,90");
        boolean productEntryModified = db.modifyDatabase("products.csv", nonExistentProductEntry,
                "100912,Store100,Apple,10,3,This is an apple");
        boolean shoppingCartEntryModified = db.modifyDatabase("shoppingCarts.csv", nonExistentShoppingCartEntry,
                "100678,100912,Store100,Apple,5,15");
        boolean purchaseHistoryEntryModified = db.modifyDatabase("purchaseHistories.csv",
                nonExistentPurchaseHistoryEntry,
                "100678,100912,Store100,Apple,5,15");

        return (!userEntryModified && !storEntryModified && !productEntryModified && !shoppingCartEntryModified
                && !purchaseHistoryEntryModified) ? "Test Passed" : "Test Failed";
    }

    static String testSuccessfulDatabaseRemoval(Database db) {
        // Removing entries that already exist in all the databases
        String currUserEntry = "C100000,blabla123@gmail.com,abc123,CUSTOMER";
        String currStoreEntry = "ST100000,S1000000,StoreSomething,10";
        String currProductEntry = "S1000000,ST1000000,PR1000000,Store1,Apple,10,5.99,Green apple";
        String currShoppingCartEntry = "C1000000,S1000000,ST1000000,PR1000000,Store1,Apple,6,35.94";
        String currPurchaseHistoryEntry = "C1000000,S1000000,ST1000000,PR1000000,Store1,Apple,6,35.94";
        db.removeFromDatabase("users.csv", currUserEntry);
        db.removeFromDatabase("stores.csv", currStoreEntry);
        db.removeFromDatabase("products.csv", currProductEntry);
        db.removeFromDatabase("shoppingCarts.csv", currShoppingCartEntry);
        db.removeFromDatabase("purchaseHistories.csv", currPurchaseHistoryEntry);

        String resultUserContents = db.getDatabaseContents("users.csv").toString();
        String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
        String resultProductContents = db.getDatabaseContents("products.csv").toString();
        String resultShoppingCartContents = db.getDatabaseContents("shoppingCarts.csv").toString();
        String resultPurchaseHistoryContents = db.getDatabaseContents("purchaseHistories.csv").toString();

        String expectedUserContents = "[C100001,cherry@gmail.com,12345,CUSTOMER, S100002,balogne@gmail.com,12315436," +
                "SELLER]";
        String expectedStoreContents = "[ST100001,S100002,newStore1,1, ST100002,S100002,newStore2,1]";
        String expectedProductContents = "[S100002,ST100001,PR100001,newStore1,myProduct,1000,25,Its a product!, " +
                "S100002,ST100002,PR100002,newStore2,myProduct2,1000,25,Its a new product!]";
        String expectedShoppingCartContents = "[]";
        String expectedPurchaseHistoryContents = "[]";

        return resultUserContents.equals(expectedUserContents) && resultStoreContents.equals(expectedStoreContents)
                && resultProductContents.equals(expectedProductContents)
                && resultShoppingCartContents.equals(expectedShoppingCartContents)
                && resultPurchaseHistoryContents.equals(expectedPurchaseHistoryContents) ? "Test Passed"
                        : "Test Failed";

    }

    static String testUnsuccessfulDatabaseRemoval(Database db) {
        // Removing entries that don't exist in any of the databases
        db.removeFromDatabase("users.csv", nonExistentUserEntry);
        db.removeFromDatabase("stores.csv", nonExistentStoreEntry);
        db.removeFromDatabase("products.csv", nonExistentProductEntry);
        db.removeFromDatabase("shoppingCarts.csv", nonExistentShoppingCartEntry);
        db.removeFromDatabase("purchaseHistories.csv", nonExistentPurchaseHistoryEntry);

        String resultUserContents = db.getDatabaseContents("users.csv").toString();
        String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
        String resultProductContents = db.getDatabaseContents("products.csv").toString();
        String resultShoppingCartContents = db.getDatabaseContents("shoppingCarts.csv").toString();
        String resultPurchaseHistoryContents = db.getDatabaseContents("purchaseHistories.csv").toString();

        String expectedUserContents = "[C100001,cherry@gmail.com,12345,CUSTOMER, S100002,balogne@gmail.com,12315436," +
                "SELLER]";
        String expectedStoreContents = "[ST100001,S100002,newStore1,1, ST100002,S100002,newStore2,1]";
        String expectedProductContents = "[S100002,ST100001,PR100001,newStore1,myProduct,1000,25,Its a product!, " +
                "S100002,ST100002,PR100002,newStore2,myProduct2,1000,25,Its a new product!]";
        String expectedShoppingCartContents = "[]";
        String expectedPurchaseHistoryContents = "[]";

        return resultUserContents.equals(expectedUserContents) && resultStoreContents.equals(expectedStoreContents)
                && resultProductContents.equals(expectedProductContents)
                && resultShoppingCartContents.equals(expectedShoppingCartContents)
                && resultPurchaseHistoryContents.equals(expectedPurchaseHistoryContents) ? "Test Passed"
                        : "Test Failed";
    }

    static String testMatchedUserIDs(Database db) {
        // for users.csv
        boolean userIDMatch = db.checkIDMatch(100001, "users.csv");
        boolean userIDNonMatch = db.checkIDMatch(123456, "users.csv");
        // for stores.csv
        boolean storeIDMatch = db.checkIDMatch(100001, "stores.csv");
        boolean storeIDNonMatch = db.checkIDMatch(123456, "stores.csv");
        // for products.csv
        boolean productIDMatch = db.checkIDMatch(100001, "products.csv");
        boolean productIDNonMatch = db.checkIDMatch(1234567, "products.csv");

        return (userIDMatch && !userIDNonMatch && storeIDMatch && !storeIDNonMatch && productIDMatch
                && !productIDNonMatch) ? "Test Passed" : "Test Failed";
    }

    static String testUserRetrievalFromDatabaseForLogin(Database db) {
        // Retrieve user with matched credentials
        String existentUserResult = db.retrieveUserMatchForLogin("cherry@gmail.com", "12345");
        String existentUserTwoResult = db.retrieveUserMatchForLogin("Cherry@Gmail.com", "12345");
        // Retrieve user with matched email and non-matched password
        String nonExistentUserOneResult = db.retrieveUserMatchForLogin("balogne@gmail.com", "hfiuwqy2u3");
        // Retrieve user with non-matched email but matched password
        String nonExistentUserTwoResult = db.retrieveUserMatchForLogin("hqudqg@yahoo.com", "12315436");
        // Retrieve user with non-matched email and non-matched password
        String nonExistentUserThreeResult = db.retrieveUserMatchForLogin("656ghhgf@hotmail.com", "abc123");

        String expectedExistentUserResult = "C100001,cherry@gmail.com,12345,CUSTOMER";
        return (existentUserResult.equals(expectedExistentUserResult)
                && existentUserTwoResult.equals(expectedExistentUserResult) && nonExistentUserOneResult == null
                && nonExistentUserTwoResult == null && nonExistentUserThreeResult == null) ? "Test Passed"
                        : "Test Failed";
    }

    static String testFileEntryExists(Database db) {
        // for users.csv
        boolean userEntryExists = db.checkEntryExists("users.csv", "S100002,balogne@gmail.com,12315436,SELLER");
        boolean userEntryNonExistence = db.checkEntryExists("users.csv", nonExistentUserEntry);

        // for stores.csv
        boolean storeEntryExists = db.checkEntryExists("stores.csv", "ST100001,S100002,newStore1,1");
        boolean storeEntryNonExistence = db.checkEntryExists("stores.csv", nonExistentStoreEntry);

        // for products.csv
        boolean productEntryExists = db.checkEntryExists("products.csv",
                "S100002,ST100002,PR100002,newStore2,myProduct2,1000,25,Its a new product!");
        boolean productEntryNonExistence = db.checkEntryExists("products.csv", nonExistentProductEntry);

        // for shoppingCarts.csv
        boolean shoppingEntryExists = db.checkEntryExists("shoppingCarts.csv",
                "C1000000,S1000000,ST1000000,PR1000000,Store1,Apple,6,35.94");
        boolean shoppingEntryNonExistence = db.checkEntryExists("shoppingCarts.csv", nonExistentShoppingCartEntry);

        // for purchaseHistories.csv
        boolean purchaseHistoryEntryExists = db.checkEntryExists("purchaseHistories.csv",
                "C1000000,S1000000,ST1000000,PR1000000,Store1,Apple,6,35.94");
        boolean purchaseHistoryEntryNonExistence = db.checkEntryExists("purchaseHistories.csv",
                nonExistentPurchaseHistoryEntry);

        return (userEntryExists && !userEntryNonExistence && storeEntryExists && !storeEntryNonExistence
                && productEntryExists && !productEntryNonExistence && !shoppingEntryExists &&
                !shoppingEntryNonExistence
                && !purchaseHistoryEntryExists && !purchaseHistoryEntryNonExistence) ? "Test Passed" : "Test Failed";
    }

    static String testUnsuccessfulFileEntryExists(Database db) {
        // Checking for entries in files that don't exist
        boolean result = false;
        String sampleEntry = "C100001,cherry@gmail.com,12345,CUSTOMER";
        String[] nonExistentFiles = { "random.csv", "blabla.csv", "hello.csv", "something.csv" };
        for (int i = 0; i < nonExistentFiles.length; i++) {
            result = db.checkEntryExists(nonExistentFiles[i], sampleEntry);
        }
        return !result ? "Test Passed" : "Test Failed";
    }

    static String testSuccessfulGetDatabaseContents(Database db) {
        String resultUsers = db.getDatabaseContents("users.csv").toString();
        String resultStores = db.getDatabaseContents("stores.csv").toString();
        String resultProducts = db.getDatabaseContents("products.csv").toString();
        String resultShoppingCarts = db.getDatabaseContents("shoppingCarts.csv").toString();
        String resultPurchaseHistories = db.getDatabaseContents("purchaseHistories.csv").toString();

        String expectedUsers = "[C100001,cherry@gmail.com,12345,CUSTOMER, S100002,balogne@gmail.com,12315436,SELLER]";
        String expectedStores = "[ST100001,S100002,newStore1,1, ST100002,S100002,newStore2,1]";
        String expectedProducts = "[S100002,ST100001,PR100001,newStore1,myProduct,1000,25,Its a product!, S100002," +
                "ST100002,PR100002,newStore2,myProduct2,1000,25,Its a new product!]";
        String expectedShoppingCarts = "[]";
        String expectedPurchaseHistories = "[]";

        return (resultUsers.equals(expectedUsers) && resultStores.equals(expectedStores)
                && resultProducts.equals(expectedProducts) && resultShoppingCarts.equals(expectedShoppingCarts)
                && resultPurchaseHistories.equals(expectedPurchaseHistories)) ? "Test Passed" : "Test Failed";
    }

    static String testUnsuccessfulGetDatabaseContents(Database db) {
        boolean result = false;
        // When the user tries retrieving contents from a non-existent file
        String[] nonExistentFiles = { "random.csv", "blabla.csv", "hello.csv", "something.csv" };
        for (String f : nonExistentFiles) {
            result = db.getDatabaseContents(f).isEmpty();
        }
        return result ? "Test Passed" : "Test Failed";
    }

    static String testSuccessfulGetMatchedEntries(Database db) {
        ArrayList<String> matchedEntries = db.getMatchedEntries("products.csv", 5, "1000");
        String resultContents = matchedEntries.toString();
        String expectedResultContents = "[S100002,ST100001,PR100001,newStore1,myProduct,1000,25,Its a product!, " +
                "S100002,ST100002,PR100002,newStore2,myProduct2,1000,25,Its a new product!]";
        return resultContents.equals(expectedResultContents) ? "Test Passed" : "Test Failed";
    }

    static String testUnsuccessfulGetMatchedEntries(Database db) {
        // The index is out of bounds
        ArrayList<String> matchedEntries = db.getMatchedEntries("users.csv", 4, "CUSTOMER");
        // The index is in bounds but the search parameter doesn't exist in the
        // specified colum
        ArrayList<String> matchedEntriesTwo = db.getMatchedEntries("users.csv", 3, "UNDECIDED");
        // // The provided file name to search for doesn't exist
        ArrayList<String> matchedEntriesThree = db.getMatchedEntries("random.csv", 0, "Something");
        return matchedEntries.isEmpty() && matchedEntriesTwo.isEmpty() && matchedEntriesThree.isEmpty() ? "Test Passed"
                : "Test Failed";
    }
}