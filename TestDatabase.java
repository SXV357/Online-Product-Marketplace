public class TestDatabase {
    public static void main(String[] args) {
        Database db = new Database();
        testGetFileHeaders(db);
        testAddInitialDatabaseEntries(db);
        testAddAdditionalDatabaseEntries(db);
        testSuccessfulDatabaseModification(db);
        testUnsuccessfulDatabaseModification(db);
    }

    static void testGetFileHeaders(Database db) {
        String[] fileNames = {"stores.csv", "purchaseHistories.csv", "users.csv", "shoppingCarts.csv", "products.csv"};
        for (int i = 0; i < fileNames.length; i++) {
            System.out.printf("File headers associated with %s: %s%n", fileNames[i], db.getFileHeaders(fileNames[i]));
        }
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
}
