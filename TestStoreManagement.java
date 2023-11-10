import java.util.ArrayList;

public class TestStoreManagement {
    public static void main(String[] args) {
        Seller s = new Seller("seller1@gmail.com", "ehjfewgfjh", UserRole.SELLER);
        // testGetStores(s); -> OK
        // createNewStore(s); -> OK
        // createDuplicateStore(s); -> OK

        // deleteNonExistentStore(s); -> OK
        // deleteExistingStore(s); -> OK

        // modifyStoreNameSuccess(s); -> OK
        // modifyStoreNameFail(s); -> OK

        // createNewProductSuccess(s); -> OK
        // createNewProductFail(s); -> OK

        // deleteProductFailure(s); -> OK
        // deleteProductSuccess(s); -> OK

        // editProductSuccess(s); -> OK
        // editProductFail(s); -> OK

        // testViewStoreSales(s); -> OK
        // testViewCustomerShoppingCarts(s); -> OK

        // testImportProductsSuccess(s); -> OK
        // testImportProductsFail(s); -> OK

        // testExportProductsSuccess(s); -> OK
        // testExportProductsFail(s); -> OK

    }

    static void testGetStores(Seller s) {
        // The seller initially hasn't created any stores
        ArrayList<Store> stores = s.getStores();
        System.out.println(stores); // should print null
    }

    static void createNewStore(Seller s) {
        // Create a store with a name that doesn't exist already
        String newStoreName = "FreshHarbor Groceries";
        boolean storeCreated = s.createNewStore(newStoreName);
        System.out.println(storeCreated); // true
    }

    static void createDuplicateStore(Seller s) {
        // Create a store with the same name as an existing store
        String currentStoreName = "FreshHarbor Groceries";
        boolean storeCreated = s.createNewStore(currentStoreName);
        System.out.println(storeCreated);  // false
    }

    static void deleteNonExistentStore(Seller s) {
        String nonExistentStoreName = "MetroFresh Market";
        boolean storeDeleted = s.deleteStore(nonExistentStoreName);
        System.out.println(storeDeleted); // false
    }

    static void deleteExistingStore(Seller s) {
        String currentStoreName = "FreshHarbor Groceries";
        boolean storeDeleted = s.deleteStore(currentStoreName);
        System.out.println(storeDeleted); // true
    }

    static void modifyStoreNameSuccess(Seller s) {
        // The previous store name exists and the new provided store name is not already associated with an existing store
        String previousStoreName = "FreshHarbor Groceries";
        String newStoreName = "MetroFresh Market";
        boolean storeNameModified = s.modifyStoreName(previousStoreName, newStoreName);
        System.out.println(storeNameModified);
    }

    static void modifyStoreNameFail(Seller s) {
        // Both parameters are null or empty or one or the other
        String n1 = "", n2 = "";
        String n3 = null, n4 = null;
        String n5 = "", n6 = null;
        String n7 = "", n8 = null;
        boolean mod1 = s.modifyStoreName(n1, n2); // false
        boolean mod2 = s.modifyStoreName(n3, n4); // false
        boolean mod3 = s.modifyStoreName(n5, n6); // false
        boolean mod4 = s.modifyStoreName(n7, n8); // false
        System.out.println(mod1 + " " + mod2 + " " + mod3 + " " + mod4);
        // The previousStoreName doesn't exist
        String nonExistentPrev = "GreenVista Supermarket";
        boolean mod5 = s.modifyStoreName(nonExistentPrev, "TownSquare Provisions");
        System.out.println(mod5); // false
        // Previous store name exists but the new store name is already associated with an existing store name
        // s.createNewStore("TownSquare Provisions");
        String existingName = "MetroFresh Market";
        String newName = "TownSquare Provisions";
        boolean mod6 = s.modifyStoreName(existingName, newName);
        System.out.println(mod6); // false
    }

    static void createNewProductSuccess(Seller s) {
        String currentStore = "MetroFresh Market";
        String productName = "Corn Dogs";
        int availableQuantity = 50;
        double price = 6.50;
        String description = "Juicy corn dogs fried to perfection";
        boolean productCreated = s.createNewProduct(currentStore, productName, availableQuantity, price, description);
        System.out.println(productCreated);
    }

    static void createNewProductFail(Seller s) {
        // If the storename itself is non-existent
        String nonExistentStore = "UrbanPantry Mart";
        String productName = "whfjwhfj";
        int qty = 54;
        double price = 6.0;
        String descrip = "qudtqvtwqutr";
        boolean productCreated = s.createNewProduct(nonExistentStore, productName, qty, price, descrip);
        System.out.println(productCreated); // false
        // If the storename is existent but the seller tries adding a product with the same name as an alr existing one
        String currStore = "MetroFresh Market";
        String prodName = "Corn Dogs";
        int qty2 = 54;
        double price2 = 6.0;
        String descrip2 = "qudtqvtwqutr";
        boolean productCreated2 = s.createNewProduct(currStore, prodName, qty2, price2, descrip2);
        System.out.println(productCreated2); // false
    }

    static void deleteProductFailure(Seller s) {
        // Removing an existing product from a non-existent store
        String nonExistentStore = "Food marketplace";
        String productName = "Corn Dogs";
        boolean productDeleted = s.deleteProduct(nonExistentStore, productName);
        System.out.println(productDeleted); // false
        // Removing a non-existing product from an existing store
        String existingStore = "MetroFresh Market";
        String nonExistentProduct = "Bananas";
        boolean productDeleted2 = s.deleteProduct(existingStore, nonExistentProduct);
        System.out.println(productDeleted2); // false
        // Trying to remove products from an existing store when the store doesn't have any products at all
        String existingStoreTwo = "TownSquare Provisions";
        String productToDelete = "something";
        boolean productDeleted3 = s.deleteProduct(existingStoreTwo, productToDelete);
        System.out.println(productDeleted3); // false
    }

    static void deleteProductSuccess(Seller s) {
        String currStore = "MetroFresh Market";
        String currProduct = "Corn Dogs";
        boolean productDeleted = s.deleteProduct(currStore, currProduct);
        System.out.println(productDeleted);
    }

    @SuppressWarnings("unused")
    static void editProductSuccess(Seller s) {
        String currentStore = "MetroFresh Market";
        String productName = "Corn Dogs";
        int availableQuantity = 50;
        double price = 6.50;
        String description = "Juicy corn dogs fried to perfection";
        boolean productCreated = s.createNewProduct(currentStore, productName, availableQuantity, price, description);
        boolean productModified = s.editProduct(currentStore, productName, "name", "Crispy Corn Dogs");
        System.out.println(productModified);
    }

    static void editProductFail(Seller s) {
        // When the seller wants to edit a product in a store that doesn't exist
        boolean productEditFailOne = s.editProduct("Metrotown store", "Crispy Corn Dogs", "description", "Crispy corn dogs");
        System.out.println(productEditFailOne);
        // When the seller wants to edit something other than name, description, price, or quantity
        boolean productEditFailTwo = s.editProduct("MetroFresh Market", "Crispy Corn Dogs", "reach", "jkdwjfi");
        System.out.println(productEditFailTwo);
        // The store exists but doesn't contain any products
        boolean productEditFailThree = s.editProduct("TownSquare Provisions", "qwduiwqd", "quantity", "5");
        System.out.println(productEditFailThree);
        // The store exists and contains products but a match for the given product name wasn't found
        boolean productEditFailFour = s.editProduct("MetroFresh Market", "Bananas", "description", "ripe bananas");
        System.out.println(productEditFailFour);
        // The store exists and the product exists in the store and the edit parameter is name but the new value provided for name already corresponds to an existing product within the same store
        // s.createNewProduct("MetroFresh Market", "Apples", 20, 5.99, "Green apples");
        boolean productEditFailFive = s.editProduct("MetroFreshMarket", "Crispy Corn Dogs", "name", "Apples");
        System.out.println(productEditFailFive);
    }

    static void testViewStoreSales(Seller s) {
        s.viewStoreSales();
    }

    static void testViewCustomerShoppingCarts(Seller s) {
        s.viewCustomerShoppingCarts();
    }

    static void testImportProductsSuccess(Seller s) {
        boolean productsImported = s.importProducts("import.csv", "TownSquare Provisions");
        System.out.println(productsImported);
    }

    static void testImportProductsFail(Seller s) {
        // the file specified by the file path doesn't exist
        boolean productImportFailOne = s.importProducts("stuff.csv", "TownSquare Provisions");
        System.out.println(productImportFailOne);
        // the file exists but the store specified by store name doesn't exist
        boolean productImportFailTwo = s.importProducts("import.csv", "Hello store");
        System.out.println(productImportFailTwo);
    }

    static void testExportProductsSuccess(Seller s) {
        boolean productsExported = s.exportProducts("TownSquare Provisions");
        System.out.println(productsExported);
    }

    static void testExportProductsFail(Seller s) {
        // the store specified by store name doesn't exist
        boolean productsExportedFailOne = s.exportProducts("Hello marketplace");
        System.out.println(productsExportedFailOne);
        // the store specified by store name doesn't have any products at all
        // s.createNewStore("Evergreen Basket");
        boolean productsExportedFailTwo = s.exportProducts("Evergreen Basket");
        System.out.println(productsExportedFailTwo);
    }
}