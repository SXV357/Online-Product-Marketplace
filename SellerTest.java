import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Project 4 - TestStoreManagement.java
 * 
 * Class that handles all testing related to the functionalities implemented in
 * the seller class.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * 
 * @version November 12, 2023
 */
public class SellerTest {

    public static void main(String[] args) {

        CreateDatabaseData.clearData();

        /* CREATING A NEW SELLER AND DATABASE INSTANCE */
        Database db = new Database();
        Seller s = new Seller("seller1@gmail.com", "ehjfewgfjh", UserRole.SELLER);

        /* SELLER + PRODUCT + STORE CLASS TESTS */

        System.out.println("Get Stores Test Result: " + testGetStores(s));
        System.out.println("Create New Store Test Result: " + createNewStore(s, db));
        System.out.println("Create Duplicate Store Test Result: " + createDuplicateStore(s, db));

        System.out.println("Delete Non-Existing Store Test Result: " + deleteNonExistentStore(s, db));
        System.out.println("Delete Existing Store Test Result: " + deleteExistingStore(s, db));

        System.out.println("Successful Store Name Modification Test Result: " + modifyStoreNameSuccess(s, db));
        System.out.println("Unsuccessful Store Name Modification: " + modifyStoreNameFail(s, db));

        System.out.println("Successful Product Creation Test Result: " + createNewProductSuccess(s, db));
        System.out.println("Unsuccessful Product Creation Test Result: " + createNewProductFail(s, db));

        System.out.println("Unsuccessful Product Removal Test Result: " + deleteProductFailure(s, db));
        System.out.println("Successful Product Removal Test Result: " + deleteProductSuccess(s, db));

        System.out.println("Successful Product Detail Modification Test Result: " + editProductSuccess(s, db));
        System.out.println("Unsuccessful Product Detail Modification Test Result: " + editProductFail(s, db));

        System.out.println("Successful View Customer Shopping Carts Test Result: "
                + testViewCustomerShoppingCartsSuccessful(s, db));
        System.out.println("Unsuccessful View Customer Shopping Carts Test Result: "
                + testViewCustomerShoppingCartsUnsuccessful(s, db));

        System.out.println("Successful View Store Sales Test Result: " + testViewStoreSalesSuccessful(s, db));
        System.out.println("Unsuccessful View Store Sales Test Result: " + testViewStoreSalesUnsuccessful(s, db));

        System.out.println("Successful Product Import Test Result: " + testImportProductsSuccess(s, db));
        System.out.println("Unsuccessful Product Import Test Result: " + testImportProductsFail(s, db));

        System.out.println("Successful Product Export Test Result: " + testExportProductsSuccess(s));
        System.out.println("Unsuccessful Product Export Test Result: " + testExportProductsFail(s));

    }

    static String testGetStores(Seller s) {
        // The seller initially hasn't created any stores
        ArrayList<Store> stores = s.getStores();
        return stores == null ? "Test Passed" : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String createNewStore(Seller s, Database db) {
        // Create a store with a name that doesn't exist already
        String newStoreName = "FreshHarbor Groceries";
        boolean storeCreated = s.createNewStore(newStoreName);
        String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
        String expectedStoreContents = "[ST1000000,S1000000,FreshHarbor Groceries,0]";
        return resultStoreContents.equals(expectedStoreContents) ? "Test Passed" : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String createDuplicateStore(Seller s, Database db) {
        // Create a store with the same name as an existing store
        String currentStoreName = "FreshHarbor Groceries";
        boolean storeCreated = s.createNewStore(currentStoreName);
        String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
        String expectedStoreContents = "[ST1000000,S1000000,FreshHarbor Groceries,0]";
        return resultStoreContents.equals(expectedStoreContents) ? "Test Passed" : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String deleteNonExistentStore(Seller s, Database db) {
        // Delete a store that doesn't exist
        String nonExistentStoreName = "MetroFresh Market";
        boolean storeDeleted = s.deleteStore(nonExistentStoreName);
        String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
        String expectedStoreContents = "[ST1000000,S1000000,FreshHarbor Groceries,0]";
        return resultStoreContents.equals(expectedStoreContents) ? "Test Passed" : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String deleteExistingStore(Seller s, Database db) {
        // Delete a store that exists
        String currentStoreName = "FreshHarbor Groceries";
        boolean storeDeleted = s.deleteStore(currentStoreName);
        String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
        String expectedStoreContents = "[]";
        return resultStoreContents.equals(expectedStoreContents) ? "Test Passed" : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String modifyStoreNameSuccess(Seller s, Database db) {
        // The previous store name exists and the new provided store name is not already
        // associated with an existing store
        String previousStoreName = "FreshHarbor Groceries";
        boolean storeCreated = s.createNewStore(previousStoreName);
        String newStoreName = "MetroFresh Market";
        boolean storeNameModified = s.modifyStoreName(previousStoreName, newStoreName);
        String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
        String expectedStoreContents = "[ST1000000,S1000000,MetroFresh Market,0]";
        return resultStoreContents.equals(expectedStoreContents) ? "Test Passed" : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String modifyStoreNameFail(Seller s, Database db) {
        // Both parameters are null or empty or one or the other
        String n1 = "", n2 = "";
        String n3 = null, n4 = null;
        String n5 = "", n6 = null;
        String n7 = "", n8 = null;
        boolean mod1 = s.modifyStoreName(n1, n2);
        boolean mod2 = s.modifyStoreName(n3, n4);
        boolean mod3 = s.modifyStoreName(n5, n6);
        boolean mod4 = s.modifyStoreName(n7, n8);

        // The previousStoreName doesn't exist
        String nonExistentPrev = "GreenVista Supermarket";
        boolean mod5 = s.modifyStoreName(nonExistentPrev, "TownSquare Provisions");

        // Previous store name exists but the new store name is already associated with
        // an existing store name
        boolean newStoreCreated = s.createNewStore("TownSquare Provisions");
        String existingName = "MetroFresh Market";
        String newName = "TownSquare Provisions";
        boolean mod6 = s.modifyStoreName(existingName, newName);

        return !mod1 && !mod2 && !mod3 && !mod4 && !mod5 && !mod6 ? "Test Passed" : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String createNewProductSuccess(Seller s, Database db) {
        // Create a product in a store that exists and no other product within the same
        // store has the same name
        String currentStore = "MetroFresh Market";
        String productName = "Corn Dogs";
        int availableQuantity = 50;
        double price = 6.50;
        String description = "Juicy corn dogs fried to perfection";
        boolean productCreated = s.createNewProduct(currentStore, productName, availableQuantity, price, description);

        String resultProductContents = db.getDatabaseContents("products.csv").toString();
        String expectedProductContents = "[S1000000,ST1000000,PR1000000,MetroFresh Market,Corn Dogs,50,6.50,Juicy corn dogs fried to perfection]";

        String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
        String expectedStoreContents = "[ST1000000,S1000000,MetroFresh Market,1, ST1000001,S1000000,TownSquare Provisions,0]";

        return resultProductContents.equals(expectedProductContents)
                && resultStoreContents.equals(expectedStoreContents) ? "Test Passed" : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String createNewProductFail(Seller s, Database db) {
        // The store is existent, and the product's name is unique but the products'
        // provided attributes are invalid
        String existentStore = "TownSquare Provisions";
        String newProductName = "Bananas";
        int quantity = -5; // Negative quantity is not allowed
        double salePrice = 3.50;
        String productDescription = "Fresh Bananas";
        boolean createdProduct = s.createNewProduct(existentStore, newProductName, quantity, salePrice,
                productDescription);

        // The store is non-existent
        String nonExistentStore = "UrbanPantry Mart";
        String productName = "whfjwhfj";
        int qty = 54;
        double price = 6.0;
        String descrip = "qudtqvtwqutr";
        boolean productCreated = s.createNewProduct(nonExistentStore, productName, qty, price, descrip);

        // The store is existent but the seller tries adding a product with the same
        // name as an existing one in the same store
        String currStore = "MetroFresh Market";
        String prodName = "Corn Dogs";
        int qty2 = 54;
        double price2 = 6.0;
        String descrip2 = "qudtqvtwqutr";
        boolean productCreated2 = s.createNewProduct(currStore, prodName, qty2, price2, descrip2);

        String resultProductContents = db.getDatabaseContents("products.csv").toString();
        String expectedProductContents = "[S1000000,ST1000000,PR1000000,MetroFresh Market,Corn Dogs,50,6.50,Juicy corn dogs fried to perfection]";

        String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
        String expectedStoreContents = "[ST1000000,S1000000,MetroFresh Market,1, ST1000001,S1000000,TownSquare Provisions,0]";

        return resultProductContents.equals(expectedProductContents)
                && resultStoreContents.equals(expectedStoreContents) ? "Test Passed" : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String deleteProductFailure(Seller s, Database db) {
        // Removing an existing product from a non-existent store
        String nonExistentStore = "Food marketplace";
        String productName = "Corn Dogs";
        boolean productDeleted = s.deleteProduct(nonExistentStore, productName);

        // Removing a non-existing product from an existing store
        String existingStore = "MetroFresh Market";
        String nonExistentProduct = "Bananas";
        boolean productDeleted2 = s.deleteProduct(existingStore, nonExistentProduct);

        // Trying to remove a product that exists in a different store from a store that
        // doesn't have any products
        String existingStoreTwo = "TownSquare Provisions";
        String productToDelete = "Corn Dogs";
        boolean productDeleted3 = s.deleteProduct(existingStoreTwo, productToDelete);

        String resultProductContents = db.getDatabaseContents("products.csv").toString();
        String expectedProductContents = "[S1000000,ST1000000,PR1000000,MetroFresh Market,Corn Dogs,50,6.50,Juicy corn dogs fried to perfection]";

        String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
        String expectedStoreContents = "[ST1000000,S1000000,MetroFresh Market,1, ST1000001,S1000000,TownSquare Provisions,0]";

        return resultProductContents.equals(expectedProductContents)
                && resultStoreContents.equals(expectedStoreContents) ? "Test Passed" : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String deleteProductSuccess(Seller s, Database db) {
        // Delete an existing product in an existing store
        String currStore = "MetroFresh Market";
        String currProduct = "Corn Dogs";
        boolean productDeleted = s.deleteProduct(currStore, currProduct);

        String resultProductContents = db.getDatabaseContents("products.csv").toString();
        String expectedProductContents = "[]";

        String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
        String expectedStoreContents = "[ST1000000,S1000000,MetroFresh Market,0, ST1000001,S1000000,TownSquare Provisions,0]";

        return resultProductContents.equals(expectedProductContents)
                && resultStoreContents.equals(expectedStoreContents) ? "Test Passed" : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String editProductSuccess(Seller s, Database db) {
        // Edit an existing product in an existing store with a valid edit parameter and
        // valid new value
        String currentStore = "MetroFresh Market";
        String productName = "Corn Dogs";
        int availableQuantity = 50;
        double price = 6.50;
        String description = "Juicy corn dogs fried to perfection";
        boolean productCreated = s.createNewProduct(currentStore, productName, availableQuantity, price, description);
        boolean productModified = s.editProduct(currentStore, productName, "name", "Crispy Corn Dogs");

        String resultProductContents = db.getDatabaseContents("products.csv").toString();
        String expectedProductContents = "[S1000000,ST1000000,PR1000000,MetroFresh Market,Crispy Corn Dogs,50,6.50,Juicy corn dogs fried to perfection]";

        String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
        String expectedStoreContents = "[ST1000000,S1000000,MetroFresh Market,1, ST1000001,S1000000,TownSquare Provisions,0]";

        return resultProductContents.equals(expectedProductContents)
                && resultStoreContents.equals(expectedStoreContents) ? "Test Passed" : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String editProductFail(Seller s, Database db) {
        // When the seller wants to edit a product in a store that doesn't exist
        boolean productEditFailOne = s.editProduct("Metrotown store", "Crispy Corn Dogs", "description",
                "Crispy corn dogs");

        // When the seller wants to edit something other than name, description, price,
        // or quantity
        boolean productEditFailTwo = s.editProduct("MetroFresh Market", "Crispy Corn Dogs", "reach", "jkdwjfi");

        // The store exists but doesn't contain any products
        boolean productEditFailThree = s.editProduct("TownSquare Provisions", "qwduiwqd", "quantity", "5");

        // The store exists and contains products but a match for the given product name
        // wasn't found
        boolean productEditFailFour = s.editProduct("MetroFresh Market", "Bananas", "description", "ripe bananas");

        // The store exists and the product exists in the store and the edit parameter
        // is name but the new value provided for name already corresponds to an
        // existing product within the same store
        s.createNewProduct("MetroFresh Market", "Apples", 20, 5.99, "Green apples");
        boolean productEditFailFive = s.editProduct("MetroFresh Market", "Crispy Corn Dogs", "name", "Apples");

        String resultProductContents = db.getDatabaseContents("products.csv").toString();
        String expectedProductContents = "[S1000000,ST1000000,PR1000000,MetroFresh Market,Crispy Corn Dogs,50,6.50,Juicy corn dogs fried to perfection, S1000000,ST1000000,PR1000001,MetroFresh Market,Apples,20,5.99,Green apples]";

        String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
        String expectedStoreContents = "[ST1000000,S1000000,MetroFresh Market,2, ST1000001,S1000000,TownSquare Provisions,0]";

        return resultProductContents.equals(expectedProductContents)
                && resultStoreContents.equals(expectedStoreContents) ? "Test Passed" : "Test Failed";
    }

    static String testViewCustomerShoppingCartsSuccessful(Seller s, Database db) {
        String shoppingCartEntry = String.format("%s,%s,%s,%s,%s,%s,%s,%s", "C1000000", "S1000000", "ST1000000",
                "PR1000000", "MetroFresh Market", "Crispy Corn Dogs", "10", "65");
        db.addToDatabase("shoppingCarts.csv", shoppingCartEntry);
        String resultShoppingCarts = s.viewCustomerShoppingCarts();
        String expectedShoppingCarts = "Store ST1000000-MetroFresh Market Shopping Carts\n\tCustomer: C1000000, Product: PR1000000-Crispy Corn Dogs, Quantity: 10, Estimated Costs: 65.00";
        return resultShoppingCarts.equals(expectedShoppingCarts) ? "Test Passed" : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String testViewCustomerShoppingCartsUnsuccessful(Seller s, Database db) {
        // No stores associated with the seller
        ArrayList<String> storeEntries = db.getDatabaseContents("stores.csv");
        ArrayList<String> productEntries = db.getDatabaseContents("products.csv");
        ArrayList<String> shoppingCartEntries = db.getDatabaseContents("shoppingCarts.csv");
        for (String store : storeEntries) {
            db.removeFromDatabase("stores.csv", store);
        }
        for (String product : productEntries) {
            db.removeFromDatabase("products.csv", product);
        }
        for (String shoppingCart : shoppingCartEntries) {
            db.removeFromDatabase("shoppingCarts.csv", shoppingCart);
        }
        String resultShoppingCartsOne = s.viewCustomerShoppingCarts();
        String expectedShoppingCartsOne = "You haven't created any stores yet!";

        // No products associated with any of the seller's stores
        boolean storeCreated = s.createNewStore("Inorbit Market");
        String resultShoppingCartsTwo = s.viewCustomerShoppingCarts();
        String expectedShoppingCartsTwo = "No products have been added to any of your stores!";

        // No customers have added products to shopping cart from any stores
        boolean productCreated = s.createNewProduct("Inorbit Market", "Crispy Corn Dogs", 50, 6.50,
                "Juicy corn dogs fried to perfection");
        String resultShoppingCartsThree = s.viewCustomerShoppingCarts();
        String expectedShoppingCartsThree = "No customers have added products to their shopping cart from any of your stores!";

        return resultShoppingCartsOne.equals(expectedShoppingCartsOne)
                && resultShoppingCartsTwo.equals(expectedShoppingCartsTwo)
                && resultShoppingCartsThree.equals(expectedShoppingCartsThree) ? "Test Passed" : "Test Failed";
    }

    static String testViewStoreSalesSuccessful(Seller s, Database db) {
        String purchaseHistoryEntry = "C1000000,S1000000,ST1000000,PR1000000,Inorbit Market,Crispy Corn Dogs,10,65";
        db.addToDatabase("purchaseHistories.csv", purchaseHistoryEntry);
        String resultStoreSales = s.viewStoreSales();
        String expectedStoreSales = "Store ST1000000-Inorbit Market Sales\n\tCustomer: C1000000, Product: PR1000000-Crispy Corn Dogs, Quantity Purchased: 10, Revenues: 65.00";
        return resultStoreSales.equals(expectedStoreSales) ? "Test Passed" : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String testViewStoreSalesUnsuccessful(Seller s, Database db) {
        // No stores associated with the seller
        ArrayList<String> storeEntries = db.getDatabaseContents("stores.csv");
        ArrayList<String> productEntries = db.getDatabaseContents("products.csv");
        ArrayList<String> purchaseHistoryEntries = db.getDatabaseContents("purchaseHistories.csv");
        for (String store : storeEntries) {
            db.removeFromDatabase("stores.csv", store);
        }
        for (String product : productEntries) {
            db.removeFromDatabase("products.csv", product);
        }
        for (String purchaseHistory : purchaseHistoryEntries) {
            db.removeFromDatabase("purchaseHistories.csv", purchaseHistory);
        }
        String resultPurchaseHistoriesOne = s.viewStoreSales();
        String expectedPurchaseHistoriesOne = "You haven't created any stores yet!";

        // No products associated with any of the seller's stores
        boolean storeCreated = s.createNewStore("Inorbit Market");
        String resultPurchaseHistoriesTwo = s.viewStoreSales();
        String expectedPurchaseHistoriesTwo = "No products have been added to any of your stores!";

        // No customers have added products to shopping cart from any stores
        boolean productCreated = s.createNewProduct("Inorbit Market", "Crispy Corn Dogs", 50, 6.50,
                "Juicy corn dogs fried to perfection");
        String resultPurchaseHistoriesThree = s.viewStoreSales();
        String expectedPurchaseHistoriesThree = "Customers have not purchased items from any of your stores!";

        return resultPurchaseHistoriesOne.equals(expectedPurchaseHistoriesOne)
                && resultPurchaseHistoriesTwo.equals(expectedPurchaseHistoriesTwo)
                && resultPurchaseHistoriesThree.equals(expectedPurchaseHistoriesThree) ? "Test Passed" : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String testImportProductsSuccess(Seller s, Database db) {
        boolean productsImported = s.importProducts("ProductsToImport.csv", "Inorbit Market");
        String resultStoreEntries = db.getDatabaseContents("stores.csv").toString();
        String resultProductEntries = db.getDatabaseContents("products.csv").toString();
        String expectedStoreEntries = "[ST1000000,S1000000,Inorbit Market,4]";
        String expectedProductEntries = "[S1000000,ST1000000,PR1000000,Inorbit Market,Crispy Corn Dogs,50,6.50,Juicy corn dogs fried to perfection, S1000000,ST1000000,PR1000001,Inorbit Market,Spicy Chicken Wings,50,12.99,Deliciously seasoned and crispy chicken wings with a spicy kick, S1000000,ST1000000,PR1000002,Inorbit Market,Margherita Pizza,30,9.99,Classic pizza topped with fresh tomatoes mozzarella and basil, S1000000,ST1000000,PR1000003,Inorbit Market,Pasta,10,5.70,Napoli pasta.]";
        return resultStoreEntries.equals(expectedStoreEntries) && resultProductEntries.equals(expectedProductEntries)
                ? "Test Passed"
                : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String testImportProductsFail(Seller s, Database db) {
        // the file specified by the file path doesn't exist
        boolean productImportFailOne = s.importProducts("stuff.csv", "Inorbit Market");

        // the file exists but the store specified by store name doesn't exist
        boolean productImportFailTwo = s.importProducts("import.csv", "TownSquare Provisions");

        String resultStoreEntries = db.getDatabaseContents("stores.csv").toString();
        String resultProductEntries = db.getDatabaseContents("products.csv").toString();
        String expectedStoreEntries = "[ST1000000,S1000000,Inorbit Market,4]";
        String expectedProductEntries = "[S1000000,ST1000000,PR1000000,Inorbit Market,Crispy Corn Dogs,50,6.50,Juicy corn dogs fried to perfection, S1000000,ST1000000,PR1000001,Inorbit Market,Spicy Chicken Wings,50,12.99,Deliciously seasoned and crispy chicken wings with a spicy kick, S1000000,ST1000000,PR1000002,Inorbit Market,Margherita Pizza,30,9.99,Classic pizza topped with fresh tomatoes mozzarella and basil, S1000000,ST1000000,PR1000003,Inorbit Market,Pasta,10,5.70,Napoli pasta.]";
        return resultStoreEntries.equals(expectedStoreEntries) && resultProductEntries.equals(expectedProductEntries)
                ? "Test Passed"
                : "Test Failed";
    }

    @SuppressWarnings("unused")
    static String testExportProductsSuccess(Seller s) {
        boolean productsExported = s.exportProducts("Inorbit Market");
        ArrayList<String> contents = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("exportedProducts/Inorbit Market.csv")));
            br.readLine(); // skip headers
            String line;
            while ((line = br.readLine()) != null) {
                contents.add(line);
            }
            br.close();
            String result = contents.toString();
            String expected = "[S1000000,ST1000000,PR1000000,Crispy Corn Dogs,50,6.50,Juicy corn dogs fried to perfection, S1000000,ST1000000,PR1000001,Spicy Chicken Wings,50,12.99,Deliciously seasoned and crispy chicken wings with a spicy kick, S1000000,ST1000000,PR1000002,Margherita Pizza,30,9.99,Classic pizza topped with fresh tomatoes mozzarella and basil, S1000000,ST1000000,PR1000003,Pasta,10,5.70,Napoli pasta.]";
            return result.equals(expected) ? "Test Passed" : "Test Failed";
        } catch (IOException e) {
            return "There was an error when exporting the products.";
        }
    }

    static String testExportProductsFail(Seller s) {
        // the store specified by store name doesn't exist
        boolean productsExportedFailOne = s.exportProducts("Hello marketplace");

        // the store specified by store name doesn't have any products at all
        s.createNewStore("Evergreen Basket");
        boolean productsExportedFailTwo = s.exportProducts("Evergreen Basket");

        return !productsExportedFailOne && !productsExportedFailTwo ? "Test Passed" : "Test Failed";
    }
}