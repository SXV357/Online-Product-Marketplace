/**
 * Project 4 - TestDatabase.java
 * 
 * Class that handles all testing related to the functionalities implemented in the database class.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 6, 2023
 */
public class TestDatabase {
    public static void main(String[] args) {
        Database db = new Database();
        // test for getting file headers given the filename
        testGetFileHeaders(db);
        // test for database column index
        testDatabaseColumnBounds(db);
        // tests for adding entries to the databases
        testAddInitialDatabaseEntries(db);
        testAddAdditionalDatabaseEntries(db);
        // tests for updating entries in the databases
        testSuccessfulDatabaseModification(db);
        testUnsuccessfulDatabaseModification(db);
        // tests for removing entries in the databases
        testSuccessfulDatabaseRemoval(db);
        testUnsuccessfulDatabaseRemoval(db);
        // test for user ID match
        testMatchedUserIDs(db);
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
        assert result.equals("Seller ID,Store Name,Number of Products\n" +
                            "Customer ID,Seller ID,Store Name,Product Name,Purchase Quantity,Price\n" +
                            "ID,Email,Password,Role\n" +
                            "Customer ID,Seller ID,Store Name,Product Name,Purchase Quantity,Price\n" +
                            "Seller ID,Store Name,Product Name,Available Quantity,Price,Description");
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
        String userEntry = "100000,blabla@yahoo.com,abc123,Customer";
        String storeEntry = "100001,Store1,5";
        String productEntry = "100001,Store1,Burrito,50,5.99,This is a breakfast burrito";
        String shoppingCartEntry = "100000,100001,Store1,Burrito,10,59.9";
        String purchaseHistoryEntry = "100000,100001,Store1,Burrito,10,59.9";
        db.addToDatabase("users.csv", userEntry);
        db.addToDatabase("stores.csv", storeEntry);
        db.addToDatabase("products.csv", productEntry);
        db.addToDatabase("shoppingCarts.csv", shoppingCartEntry);
        db.addToDatabase("purchaseHistories.csv", purchaseHistoryEntry);
    }

    static void testAddAdditionalDatabaseEntries(Database db) {
        // adding an additional entry to each of the databases
        String newUserEntry = "100001,blabla@gmail.com,abc123,Customer";
        String newStoreEntry = "100002,Store2,10";
        String newProductEntry = "100002,Store2,Apple,10,2,This is an apple";
        String newShoppingCartEntry = "100001,100002,Store2,Apple,5,10";
        String newPurchaseHistoryEntry = "100001,100002,Store2,Apple,5,10";
        db.addToDatabase("users.csv", newUserEntry);
        db.addToDatabase("stores.csv", newStoreEntry);
        db.addToDatabase("products.csv", newProductEntry);
        db.addToDatabase("shoppingCarts.csv", newShoppingCartEntry);
        db.addToDatabase("purchaseHistories.csv", newPurchaseHistoryEntry);
    }

    static void testSuccessfulDatabaseModification(Database db) {
        // modifying entries that already exist within the database
        String currUserEntry = "100001,blabla@gmail.com,abc123,Customer";
        String currStoreEntry = "100002,Store2,10";
        String currProductEntry = "100002,Store2,Apple,10,2,This is an apple";
        String currShoppingCartEntry = "100001,100002,Store2,Apple,5,10";
        String currPurchaseHistoryEntry = "100001,100002,Store2,Apple,5,10";
        // if user for example chooses to modify their email
        boolean userModified = db.modifyDatabase("users.csv", currUserEntry, "100001,hello123@yahoo.com,abc123,Customer");
        // if for example, a seller chooses to add more products to one of their existing stores
        boolean storeModified = db.modifyDatabase("stores.csv", currStoreEntry, "100002,Store2,20");
        // if for example, a seller chooses to modify certain details associated with one of the products in their store
        boolean productModified = db.modifyDatabase("products.csv", currProductEntry, "100002,Store2,Apple,20,5,This is an apple");
        // if the customer wants to update the quantity of the item in their existing shopping cart
        boolean shoppingCartModified = db.modifyDatabase("shoppingCarts.csv", currShoppingCartEntry, "100001,100002,Store2,Apple,10,50");
        boolean purchaseHistoryModified = db.modifyDatabase("purchaseHistories.csv", currPurchaseHistoryEntry, "100001,100002,Store2,Apple,10,50");
        assert userModified && storeModified && productModified && shoppingCartModified && purchaseHistoryModified;
    }

    static void testUnsuccessfulDatabaseModification(Database db) {
        // modifying entries that don't exist in the database
        String nonExistentUserEntry = "100678,hello@yahoo.com,76ybgjh,Seller";
        String nonExistentStoreEntry = "100912,Store100,90";
        String nonExistentProductEntry = "100912,Store100,Apple,10,2,This is an apple";
        String nonExistentShoppingCartEntry = "100678,100912,Store100,Apple,5,10";
        String nonExistentPurchaseHistoryEntry = "100678,100912,Store100,Apple,5,10";
        // no changes should be reflected in any of the databases since none of the entries exist
        boolean userModified = db.modifyDatabase("users.csv", nonExistentUserEntry, "100678,hello@yahoo.com,jhghgf,Seller");
        boolean storeModified = db.modifyDatabase("stores.csv", nonExistentStoreEntry, "100912,Store102,90");
        boolean productModified = db.modifyDatabase("products.csv", nonExistentProductEntry, "100912,Store100,Apple,10,3,This is an apple");
        boolean shoppingCartModified = db.modifyDatabase("shoppingCarts.csv", nonExistentShoppingCartEntry, "100678,100912,Store100,Apple,5,15");
        boolean purchaseHistoryModified = db.modifyDatabase("purchaseHistories.csv", nonExistentPurchaseHistoryEntry, "100678,100912,Store100,Apple,5,15");
        assert !userModified && !storeModified && !productModified && !shoppingCartModified && !purchaseHistoryModified;
    }

    static void testSuccessfulDatabaseRemoval(Database db) {
        // removing entries that exist in the databases
        String currUserEntry = "100001,hello123@yahoo.com,abc123,Customer";
        String currStoreEntry = "100002,Store2,20";
        String currProductEntry = "100002,Store2,Apple,20,5,This is an apple";
        String currShoppingCartEntry = "100001,100002,Store2,Apple,10,50";
        String currPurchaseHistoryEntry = "100001,100002,Store2,Apple,10,50";
        db.removeFromDatabase("users.csv", currUserEntry); // OK
        db.removeFromDatabase("stores.csv", currStoreEntry); // OK
        db.removeFromDatabase("products.csv", currProductEntry); // OK
        db.removeFromDatabase("shoppingCarts.csv", currShoppingCartEntry); // OK
        db.removeFromDatabase("purchaseHistories.csv", currPurchaseHistoryEntry); // OK
    }

    static void testUnsuccessfulDatabaseRemoval(Database db) {
        // removing entries that don't exist in the database
        String nonExistentUserEntry = "100678,hello@yahoo.com,76ybgjh,Seller";
        String nonExistentStoreEntry = "100912,Store100,90";
        String nonExistentProductEntry = "100912,Store100,Apple,10,2,This is an apple";
        String nonExistentShoppingCartEntry = "100678,100912,Store100,Apple,5,10";
        String nonExistentPurchaseHistoryEntry = "100678,100912,Store100,Apple,5,10";
        db.removeFromDatabase("users.csv", nonExistentUserEntry); // OK
        db.removeFromDatabase("stores.csv", nonExistentStoreEntry); // OK
        db.removeFromDatabase("products.csv", nonExistentProductEntry); // OK
        db.removeFromDatabase("shoppingCarts.csv", nonExistentShoppingCartEntry); // OK
        db.removeFromDatabase("purchaseHistories.csv", nonExistentPurchaseHistoryEntry); // OK
    }

    static void testMatchedUserIDs(Database db) {
        // one for an ID that exists in the user's database and one for an ID that doesn't exist
        assert db.checkUserIDMatch(100000) && !db.checkUserIDMatch(123456);
        // one if an ID is passed in that is not 6-digits long
        assert !db.checkUserIDMatch(1234567);
    }

    static void testUserRetrievalFromDatabase(Database db) {
        
    }

}