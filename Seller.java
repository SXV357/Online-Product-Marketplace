import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project 5 - Seller.java
 * 
 * Class to represent the permissions and details associated with a seller
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version December 8, 2023
 */
public class Seller extends User {

    private Database db = new Database();

    /**
     * Constructor to initialize a new seller when they create an account. Generates
     * a unique ID for the seller and adds a marker to it signifying that the user
     * is a seller.
     *
     * @param email    The seller's new email
     * @param password The seller's new password
     * @param role     The seller's role
     */
    public Seller(String email, String password, UserRole role) throws Exception {
        super(email, password, role);
    }

    /**
     * Customer to re-initialize a seller when they log back into the application.
     * Sets the values of all fields to the ones passed to the constructor.
     *
     * @param userID   The seller's existing ID
     * @param email    The seller's existing email
     * @param password The seller's existing password
     * @param role     The seller's existing role
     * @throws Exception
     */
    public Seller(String userID, String email, String password, UserRole role) throws Exception {
        super(userID, email, password, role);
    }

    /**
     * Fetches all the customers that exist in the application and returns them in the form of an arraylist.
     * Utilized to determine seller dashboard behavior.
     *
     * @return An arraylist containing all the customers
     */
    public ArrayList<String> getAllCustomers() throws SellerException {
        ArrayList<String> allCustomers = db.getMatchedEntries("users.csv", 3, "CUSTOMER");
        if (allCustomers.isEmpty()) {
            throw new SellerException("There are no existent customer accounts!");
        }
        return allCustomers;
    }

    /**
     * Fetches all the products that exist in the application and returns them in the form of an arraylist.
     * Utilized to determine seller dashboard behavior.
     *
     * @return An arraylist containing all the products
     */
    public ArrayList<String> getAllProducts() throws SellerException {
        ArrayList<String> allProducts = db.getDatabaseContents("products.csv");
        if (allProducts.isEmpty()) {
            throw new SellerException("No products have been added to any stores yet!");
        }
        return allProducts;
    }

    /**
     * Retrieves all the stores associated with this seller and returns the entries
     * in the form of an arraylist of store objects
     *
     * @return An arraylist of store objects corresponding to this seller
     * @throws SellerException
     */
    public ArrayList<String> getStores() throws SellerException {
        ArrayList<Store> stores = new ArrayList<>();
        ArrayList<String> storeEntries = db.getMatchedEntries("stores.csv", 1, getUserID());
        if (storeEntries.isEmpty()) {
            throw new SellerException("You haven\'t created any stores yet!");
        }
        for (int i = 0; i < storeEntries.size(); i++) {
            String[] storeEntry = storeEntries.get(i).split(",");
            stores.add(new Store(storeEntry[0], storeEntry[2]));
        }

        ArrayList<String> storeNames = new ArrayList<>();
        for (Store st : stores) {
            storeNames.add(st.getStoreName());
        }
        return storeNames;
    }

    /**
     * Queries the database for product entries associated with the specified store
     * then
     * bundles all the entries into product objects and returns them in the form of
     * an arraylist.
     *
     * @param storeName The name of the store to retrieve the products from
     * @return An arraylist of all the products associated with this store
     * @throws SellerException
     */
    public ArrayList<String> getProducts(String storeName) throws SellerException {
        storeName = storeName.replace(",", "");
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<String> productEntries = db.getMatchedEntries("products.csv", 3, storeName);
        if (productEntries.isEmpty()) {
            throw new SellerException(storeName + " doesn\'t have any products!");
        }
        for (int i = 0; i < productEntries.size(); i++) {
            String[] productEntry = productEntries.get(i).split(",");
            Product product = new Product(productEntry[2], productEntry[4], Integer.parseInt(productEntry[5]),
                    Double.parseDouble(productEntry[6]), productEntry[7]);
            products.add(product);
        }
        ArrayList<String> productNames = new ArrayList<>();
        for (Product prod : products) {
            productNames.add(prod.getName());
        }
        return productNames;
    }

    /**
     * Creates a new store associated with this seller. If another store exists with
     * the same name, the seller is prevented from creating the store.
     *
     * @param newStoreName The name of the new store
     * @return An indication of whether or not the store was created successfully.
     * @throws SellerException
     */
    public void createNewStore(String newStoreName) throws SellerException {
        ArrayList<String> matchedStoreEntries = db.getMatchedEntries("stores.csv", 2, newStoreName);
        if (newStoreName == null || newStoreName.isEmpty() || newStoreName.isBlank()) {
            throw new SellerException("Unable to create a new store. The name cannot be null, blank, or empty!");
        } else if (newStoreName.contains(",")) {
            throw new SellerException("The store name cannot contain any commas");
        }
        if (!matchedStoreEntries.isEmpty()) {
            throw new SellerException(
                    "Unable to create a new store. There is already a store that exists with the same name!");
        } else {
            Store newStore = new Store(newStoreName);
            String newStoreEntry = String.format("%s,%s,%s,%d", newStore.getStoreIdentificationNumber(),
                    super.getUserID(), newStore.getStoreName(), 0);
            db.addToDatabase("stores.csv", newStoreEntry);
        }
    }

    /**
     * Deletes an existing store associated with this seller, including all of the
     * store's associated products. In an event where the seller chooses to delete a
     * store that doesn't exist, the deletion doesn't take place.
     *
     * @param storeName The name of the store to remove
     * @return An indication of whether or not the store, including all associated
     * products were deleted successfully.
     * @throws SellerException
     */
    public void deleteStore(String storeName) throws SellerException {
        try {
            String matchedStore = db.getMatchedEntries("stores.csv", 2, storeName).get(0);
            ArrayList<String> matchedProducts = db.getMatchedEntries("products.csv", 1,
                    matchedStore.split(",")[0]);
            db.removeFromDatabase("stores.csv", matchedStore);
            for (int i = 0; i < matchedProducts.size(); i++) {
                db.removeFromDatabase("products.csv", matchedProducts.get(i));
            }
            ArrayList<String> matchedShoppingCartEntries = db.getMatchedEntries("shoppingCarts.csv", 4, storeName);
            for (int j = 0; j < matchedShoppingCartEntries.size(); j++) {
                db.removeFromDatabase("shoppingCarts.csv", matchedShoppingCartEntries.get(j));
            }
        } catch (IndexOutOfBoundsException e) {
            throw new SellerException("Unable to delete store. The given store name is non-existent!");
        }
    }

    /**
     * Modifies the name of an existing store associated with this seller. If the
     * seller attempts to modify the name of a store that doesn't exist or attempts
     * to modify the name of an existing store to a name that either corresponds to
     * one of this sellers' other stores or one that corresponds to another seller's
     * stores, they are prevented from doing so.
     *
     * @param prevStoreName The name of the seller's existing store whose name they
     *                      choose to modify.
     * @param newStoreName  The store's new name.
     * @return An indication of whether the store name modification took place
     * successfully.
     * @throws SellerException
     */
    public void modifyStoreName(String prevStoreName, String newStoreName) throws SellerException {
        try {
            if (newStoreName == null || newStoreName.isEmpty() || newStoreName.isBlank()) {
                throw new SellerException(
                        "Unable to modify store name. The new store name cannot be null, blank, or empty");
            } else if (newStoreName.contains(",")) {
                throw new SellerException("The new store name cannot contain any commas!");
            }
            String matchedPrevStoreName = db.getMatchedEntries("stores.csv", 2, prevStoreName).get(0);
            ArrayList<String> matchedNewStoreName = db.getMatchedEntries("stores.csv", 2, newStoreName);
            if (matchedNewStoreName.isEmpty()) {
                String[] newStoreRepresentation = matchedPrevStoreName.split(",");
                newStoreRepresentation[2] = newStoreName;
                db.modifyDatabase("stores.csv", matchedPrevStoreName, String.join(",",
                        newStoreRepresentation));

                ArrayList<String> matchedProductEntries = db.getMatchedEntries("products.csv",
                        3, prevStoreName);
                ArrayList<String> matchedShoppingCartEntries = db.getMatchedEntries("shoppingCarts.csv",
                        4, prevStoreName);
                ArrayList<String> matchedPurchaseHistoryEntries = db.getMatchedEntries("purchaseHistories.csv",
                        4, prevStoreName);

                for (String prodEntry : matchedProductEntries) {
                    String[] prodRep = prodEntry.split(",");
                    prodRep[3] = newStoreName;
                    db.modifyDatabase("products.csv", prodEntry, String.join(",", prodRep));
                }
                for (String shoppingCartEntry : matchedShoppingCartEntries) {
                    String[] shoppingCartRep = shoppingCartEntry.split(",");
                    shoppingCartRep[4] = newStoreName;
                    db.modifyDatabase("shoppingCarts.csv", shoppingCartEntry, String.join(",",
                            shoppingCartRep));
                }
                for (String purchaseHistoryEntry : matchedPurchaseHistoryEntries) {
                    String[] purchaseHistoryRep = purchaseHistoryEntry.split(",");
                    purchaseHistoryRep[4] = newStoreName;
                    db.modifyDatabase("purchaseHistories.csv", purchaseHistoryEntry, String.join(",",
                            purchaseHistoryRep));
                }
            } else {
                throw new SellerException(
                        "Unable to modify store name. The new name provided is already associated with" +
                                " an existing store!");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new SellerException("Unable to modify store name. The previous store name provided is non-existent!");
        }
    }

    /**
     * Creates a new product in any of the store's associated with this seller. If
     * the seller tries adding a product to a store that doesn't exist or adds a
     * product to an existing store that already contains the same product, the
     * seller is prevented from doing so.
     *
     * @param storeName          The name of the store to add the product to
     * @param productName        The new product's name.
     * @param availableQuantity  The number of products going on sale
     * @param price              The price of the product
     * @param productDescription A description associated with the product
     * @return An indication of whether the product was succcessfully added to the
     * selected store or not.
     * @throws SellerException
     */
    public void createNewProduct(String storeName, String productName, String availableQuantity, String price,
                                 String productDescription) throws SellerException {
        try {
            if (productName == null || productName.isEmpty() || productName.isBlank()) {
                throw new SellerException("Unable to add product. The product name cannot be null, blank, or empty");
            } else if (productName.contains(",")) {
                throw new SellerException("The product name cannot contain any commas!");
            }
            if (availableQuantity == null || availableQuantity.isEmpty() || availableQuantity.isBlank()) {
                throw new SellerException("Unable to add product. The quantity cannot be null, blank, or empty");
            } else if (availableQuantity.contains(",")) {
                throw new SellerException("The quantity cannot contain any commas!");
            }
            if (price == null || price.isEmpty() || price.isBlank()) {
                throw new SellerException("Unable to add product. The price cannot be null, blank, or empty");
            } else if (price.contains(",")) {
                throw new SellerException("The price cannot contain any commas!");
            }
            if (productDescription == null || productDescription.isEmpty() || productDescription.isBlank()) {
                throw new SellerException("Unable to add product. The description cannot be null, blank, or empty");
            } else if (productDescription.contains(",")) {
                throw new SellerException("The description cannot contain any commas!");
            }
            int quantity;
            try {
                quantity =  Integer.parseInt(availableQuantity);
            } catch (NumberFormatException e) {
                throw new SellerException("The quantity has to be an integer and cannot be a string");
            }
            double productPrice;
            try {
                productPrice = Double.parseDouble(price);
            } catch (Exception e) {
                throw new SellerException("price quantity has to be an integer and cannot be a string");
            }
            if (quantity < 0) {
                throw new SellerException("Unable to add product. The quantity cannot be negative");
            } else if (productPrice < 0) {
                throw new SellerException("Unable to add product. The price cannot be negative");
            }
            String matchedStoreEntry = db.getMatchedEntries("stores.csv", 2, storeName).get(0);

            // To ensure that the seller doesn't try adding a product with the same name in
            // one of their existing stores
            ArrayList<String> matchedProducts = db.getMatchedEntries("products.csv", 3, storeName);
            for (int i = 0; i < matchedProducts.size(); i++) {
                String[] productEntry = matchedProducts.get(i).split(",");
                if (productEntry[4].equals(productName)) {
                    throw new SellerException(
                            "Unable to add product. The name of this product is already associated with an existing" +
                                    " product in the given store");
                }
            }

            Product newProduct = new Product(productName, quantity, productPrice,
                    productDescription.replace(",", ""));

            String entry = String.format("%s,%s,%s,%s", super.getUserID(), matchedStoreEntry.split(",")[0],
                    newProduct.getProductIdentificationNumber(), storeName) + "," + newProduct.toString();

            db.addToDatabase("products.csv", entry);

            String[] newStoreRepresentation = matchedStoreEntry.split(",");
            int previousNumProducts = Integer.parseInt(newStoreRepresentation[3]);
            newStoreRepresentation[3] = String.valueOf(previousNumProducts + 1);
            db.modifyDatabase("stores.csv", matchedStoreEntry, String.join(",",
                    newStoreRepresentation));

        } catch (Exception e) {
            throw new SellerException(e.getMessage());
        }
    }

    /**
     * Edit a product's properties in any of the seller's stores. If the seller
     * tries editing a product in a STORE that doesn't exist, or the seller tries
     * editing a product that doesn't exist in a store that exists, the seller is
     * prevented from doing so.
     *
     * @param storeName   The name of the store in which the seller wants to edit a
     *                    product
     * @param productName The name of the product in the given store to edit
     * @param editParam   Represents which of the products' properties the seller
     *                    wants to edit(Name, Price, Quantity, Description)
     * @param newValue    The new value of the property to modify
     * @return An indication of whether the product in the given store was modified
     * successfully
     * @throws SellerException
     */
    @SuppressWarnings("unused")
    public void editProduct(String storeName, String productName, String editParam, String newValue)
            throws SellerException {
        try {
            if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                throw new SellerException("Unable to edit product. The new value cannot be null, blank, or empty");
            } else if (newValue.contains(",")) {
                throw new SellerException("The new value cannot contain any commas");
            }
            String matchedStore = db.getMatchedEntries("stores.csv", 2, storeName).get(0);
            ArrayList<String> productMatches = db.getMatchedEntries("products.csv", 3, storeName);
            if (productMatches.isEmpty()) { // there are no products associated with this given store
                throw new SellerException("Unable to edit product. The given store doesn\'t contain any products");
            } else {
                for (int i = 0; i < productMatches.size(); i++) {
                    String[] productRep = productMatches.get(i).split(",");
                    if (productRep[4].equals(productName)) { // found a match
                        switch (editParam) {
                            case "name" -> {
                                // check if any products associated with that given store have same name as
                                // newValue
                                for (int j = 0; j < productMatches.size(); j++) {
                                    if (productMatches.get(j).split(",")[4].equals(newValue)) {
                                        throw new SellerException(
                                                "Unable to edit product. The new name provided is already" +
                                                        " associated with an existing product in the given store");
                                    }
                                }
                                productRep[4] = newValue;
                            }
                            case "price" -> {
                                double newPrice;
                                try {
                                    newPrice = Double.parseDouble(newValue);
                                } catch (NumberFormatException e) {
                                    throw new SellerException("The new price has to be an integer");
                                }
                                if (newPrice <= 0) {
                                    throw new SellerException("The new price has to be greater than 0");
                                }
                                productRep[6] = String.valueOf(newPrice);
                            }
                            case "description" -> productRep[7] = newValue;
                            case "quantity" -> {
                                int prevQuantity = Integer.parseInt(productRep[5]);
                                int newQuantity;
                                try {
                                    newQuantity = Integer.parseInt(newValue);
                                } catch (NumberFormatException e) {
                                    throw new SellerException("The new quantity has to be an integer");
                                }
                                // Prevent from modifying to a negative quantity
                                if (newQuantity < 0) {
                                    throw new SellerException(
                                            "Unable to edit product. The new quantity cannot be negative.");
                                }
                                productRep[5] = String.valueOf(newQuantity);
                            }
                        }
                        db.modifyDatabase("products.csv", productMatches.get(i), String.join(",",
                                productRep));
                    }
                }

            }
        } catch (IndexOutOfBoundsException e) {
            throw new SellerException("Unable to edit product. The given store name is non-existent.");
        }
    }

    /**
     * Deletes the product associated with the given store. In an event when the
     * seller tries removing a product from a non-existent store or a non-existent
     * product from a given store, the seller is prevented from doing so.
     *
     * @param storeName   The name of the store to delete the product from
     * @param productName The name of the product to delete from the store
     * @return An indication whether the specified product was deleted from the
     * specified store successfully
     * @throws SellerException
     */
    public void deleteProduct(String storeName, String productName) throws SellerException {
        try {
            String matchedStore = db.getMatchedEntries("stores.csv", 2, storeName).get(0);
            ArrayList<String> matchedProductsForGivenStore = db.getMatchedEntries("products.csv", 3,
                    storeName);
            // If the store exists but it doesn't contain any products
            if (matchedProductsForGivenStore.isEmpty()) {
                throw new SellerException("Unable to delete product. This store doesn\'t contain any products.");
            }
            for (int i = 0; i < matchedProductsForGivenStore.size(); i++) {
                String[] productEntry = matchedProductsForGivenStore.get(i).split(",");
                if (productEntry[4].equals(productName)) {

                    db.removeFromDatabase("products.csv", matchedProductsForGivenStore.get(i));

                    String[] newStoreRepresentation = matchedStore.split(",");
                    int previousNumProducts = Integer.parseInt(newStoreRepresentation[3]);
                    newStoreRepresentation[3] = String.valueOf(previousNumProducts - 1);
                    db.modifyDatabase("stores.csv", matchedStore, String.join(",",
                            newStoreRepresentation));
                    break;
                }
            }
        } catch (Exception e) {
            throw new SellerException("Unable to delete product. Please try again!");
        }
    }

    /**
     * View a list of sales by store, where the seller can view customer
     * information, product details, how many items the customer purchased, and the
     * revenues per sale.
     *
     * @return A string containing store sale information
     * @throws SellerException
     */
    public HashMap<String, ArrayList<String>> viewStoreSales() throws SellerException {
        HashMap<String, ArrayList<String>> salesByStore = new HashMap<>();
        ArrayList<String> matchedStores = db.getMatchedEntries("stores.csv", 1, super.getUserID());
        if (matchedStores.isEmpty()) {
            throw new SellerException("You haven't created any stores yet!");
        } else {
            // The seller has stores. Check if there are products associated with any of the
            // stores
            ArrayList<String> matchedProducts = db.getMatchedEntries("products.csv", 0,
                    super.getUserID());
            if (matchedProducts.isEmpty()) {
                throw new SellerException("No products have been added to any of your stores!");
            } else { // Products are associated with the seller's store(s)
                ArrayList<String> matchedPurchaseHistories = db.getMatchedEntries("purchaseHistories.csv",
                        1, super.getUserID());
                if (matchedPurchaseHistories.isEmpty()) {
                    throw new SellerException("Customers have not purchased items from any of your stores!");
                } else {
                    for (int i = 0; i < matchedPurchaseHistories.size(); i++) {
                        String[] purchaseHistoryEntry = matchedPurchaseHistories.get(i).split(",");
                        String storeIdentifier = String.format("%s Sales\n", purchaseHistoryEntry[4]);
                        String customer = db.retrieveUserEmail(purchaseHistoryEntry[0]);
                        int purchaseQuantity = Integer.parseInt(purchaseHistoryEntry[6]);
                        double revenues = Double.parseDouble(purchaseHistoryEntry[7]);

                        String salesInformation = String.format(
                                "\tCustomer: %s, Product: %s, Quantity Purchased: %d, Revenues: %.2f",
                                customer, purchaseHistoryEntry[5], purchaseQuantity, revenues);

                        if (!salesByStore.containsKey(storeIdentifier)) {
                            salesByStore.put(storeIdentifier, new ArrayList<String>());
                        }
                        salesByStore.get(storeIdentifier).add(salesInformation);
                    }
                }
            }
        }
        return salesByStore;
    }

    /**
     * Allows the seller to import a CSV file containing product entries in the form
     * Product Name, Available Quantity, Price, Description into any store of their
     * choice.
     *
     * @param filePath  The file containing the products to be imported into the
     *                  store
     * @param storeName The name of the store to add the products to
     * @return An indication whether the product import took place successfully or
     * not.
     * @throws SellerException
     */
    public void importProducts(String filePath, String storeName) throws SellerException {
        try {
            if (filePath == null || filePath.isEmpty() || filePath.isBlank()) {
                throw new SellerException("Unable to import products. The file path cannot be null, blank, or empty");
            }
            String matchedStoreEntry = db.getMatchedEntries("stores.csv", 2, storeName).get(0);
            File productFile = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(productFile));
            br.readLine(); // skip the headers
            String line;
            int numProducts = 0;
            while ((line = br.readLine()) != null) {
                String[] productLine = line.split(",");
                this.createNewProduct(storeName, productLine[0], productLine[1], productLine[2], productLine[3]);
                numProducts += 1;
            }
            String[] matchedStore = matchedStoreEntry.split(",");
            int prevNumProducts = Integer.parseInt(matchedStore[3]);
            if (!(numProducts == 0)) {
                matchedStore[3] = String.valueOf(prevNumProducts + numProducts);
                db.modifyDatabase("stores.csv", matchedStoreEntry, String.join(",", matchedStore));
            }
            br.close();
        } catch (Exception e) {
            throw new SellerException("Unable to import products. Please try again!");
        }
    }

    /**
     * Allows a seller to export the products associated with any of their stores to
     * a separate CSV file.
     *
     * @param storeName The name of the store whose products the seller wants to
     *                  export
     * @return An indication whether the export took place successfully.
     * @throws SellerException
     */
    @SuppressWarnings("unused")
    public void exportProducts(String storeName) throws SellerException {
        try {
            String matchedStore = db.getMatchedEntries("stores.csv", 2, storeName).get(0);
            ArrayList<String> matchedProducts = db.getMatchedEntries("products.csv", 3, storeName);
            if (!(matchedProducts.isEmpty())) {
                File targetDir = new File("exportedProducts");
                if (!targetDir.exists()) {
                    targetDir.mkdir();
                }
                File output = new File(targetDir, storeName + ".csv");
                output.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(output));
                String headers = "Seller ID,Store ID,Product ID,Product Name,Available Quantity,Price,Description";
                bw.write(headers + "\n");
                for (int i = 0; i < matchedProducts.size(); i++) {
                    String[] productEntry = matchedProducts.get(i).split(",");
                    String sellerID = productEntry[0];
                    String storeID = productEntry[1];
                    String productID = productEntry[2];
                    String productName = productEntry[4];
                    int availableQuantity = Integer.parseInt(productEntry[5]);
                    double price = Double.parseDouble(productEntry[6]);
                    String description = productEntry[7];
                    String formattedProduct = String.format("%s,%s,%s,%s,%d,%.2f,%s", sellerID, storeID, productID,
                            productName, availableQuantity, price, description);
                    bw.write(formattedProduct + "\n");
                }
                bw.flush();
                bw.close();
            } else {
                throw new SellerException("Unable to export products. This store doesn\'t contain any products");
            }
        } catch (Exception e) {
            throw new SellerException("Unable to export products. Please try again");
        }
    }

    /**
     * View customer's shopping carts associated with this sellers' stores, where
     * the seller can view customer information, product details, quantity added,
     * and price the customer will have to incur should they purchase it.
     *
     * @return A string containing customer shopping cart information
     * @throws SellerException
     */
    public HashMap<String, ArrayList<String>> viewCustomerShoppingCarts() throws SellerException {
        HashMap<String, ArrayList<String>> cartsByStore = new HashMap<>();
        ArrayList<String> matchedStores = db.getMatchedEntries("stores.csv", 1, super.getUserID());
        if (matchedStores.isEmpty()) {
            throw new SellerException("You haven't created any stores yet!");
        } else {
            // The seller has stores. Check if there are products associated with any of the
            // stores
            ArrayList<String> matchedProducts = db.getMatchedEntries("products.csv", 0,
                    super.getUserID());
            if (matchedProducts.isEmpty()) {
                throw new SellerException("No products have been added to any of your stores!");
            } else { // The seller has products associated with their stores
                ArrayList<String> matchedCarts = db.getMatchedEntries("shoppingCarts.csv", 1,
                        super.getUserID());
                if (matchedCarts.isEmpty()) {
                    throw new SellerException(
                            "No customers have added products to their shopping cart from any of your stores!");
                } else {
                    for (int i = 0; i < matchedCarts.size(); i++) {
                        String[] shoppingCartEntry = matchedCarts.get(i).split(",");
                        String storeIdentifier = String.format("%s Shopping Carts\n", shoppingCartEntry[4]);
                        String customer = db.retrieveUserEmail(shoppingCartEntry[0]);
                        int addedQuantity = Integer.parseInt(shoppingCartEntry[6]);
                        double price = Double.parseDouble(shoppingCartEntry[7]);

                        String shoppingCartInformation = String.format(
                                "\tCustomer: %s, Product: %s, Quantity: %d, Estimated Costs: %.2f",
                                customer, shoppingCartEntry[5], addedQuantity, price);

                        if (!cartsByStore.containsKey(storeIdentifier)) {
                            cartsByStore.put(storeIdentifier, new ArrayList<String>());
                        }
                        cartsByStore.get(storeIdentifier).add(shoppingCartInformation);
                    }
                }
            }
        }
        return cartsByStore;
    }
}