import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Project 4 - Seller.java
 * 
 * Class to represent the permissions and details associated with a seller
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 8, 2023
 */
public class Seller extends User{

    private static Database db = new Database();
    // private ArrayList<Store> stores;

    // creating a seller for the very first time(They haven't created any stores yet so initializing arraylist to empty sequence)
    public Seller(String email, String password, UserRole role) {
        super(email, password, role);
    }

    // re-initializing a seller once they log into the application
    public Seller(String userID, String email, String password, UserRole role) {
        super(userID, email, password, role);
    }

    public ArrayList<String> getStores() {
        ArrayList<String> storeNames = new ArrayList<>();
        ArrayList<String> storeEntries = db.getDatabaseContents("stores.csv");
        for (int i = 0; i < storeEntries.size(); i++) {
            String[] storeEntry = storeEntries.get(i).split(",");
            storeNames.add(storeEntry[2]);
        }
        return storeNames;
    }
    
    public String retrieveStoreName(int index) {
        ArrayList<String> storeEntries = db.getDatabaseContents("stores.csv");
        if (index >= 0 && index <= storeEntries.size() - 1) {
            return storeEntries.get(index).split(",")[2];
        }
        return "";
    }

    // 1. Create a new store
    public boolean createNewStore(String newStoreName) {
        boolean storeCreated = false;
        ArrayList<String> matchedStoreEntries = db.getMatchedEntries("stores.csv", 2, newStoreName);
        // Checking to make sure there's not already a store with the same name
        if (newStoreName == null || newStoreName.isEmpty() || !matchedStoreEntries.isEmpty()) {
            storeCreated =  false;
        }
        if (matchedStoreEntries.isEmpty()) { // there are no stores that already exist with the name
            Store newStore = new Store(newStoreName);
            String newStoreEntry = String.format("%s,%s,%s,%d", newStore.getStoreIdentificationNumber(), super.getUserID(), newStore.getStoreName(), 0); // a new store will start with 0 products by default
            db.addToDatabase("stores.csv", newStoreEntry);
            storeCreated = true;
        }
        return storeCreated;
    }

    // 2. Delete an existing store(in its entirety)
    public boolean deleteStore(String storeName) {
        try {
            String matchedStore = db.getMatchedEntries("stores.csv", 2, storeName).get(0);
            ArrayList<String> matchedProducts = db.getMatchedEntries("products.csv", 3, storeName);
            db.removeFromDatabase("stores.csv", matchedStore); // remove the store first
            // then remove all products associated with that particular store
            for (int i = 0; i < matchedProducts.size(); i++) {
                db.removeFromDatabase("products.csv", matchedProducts.get(i));
            }
            return true;
        } catch (IndexOutOfBoundsException e) { // if the seller wants to delete a store that doesn't exist
            return false;
        }
    }

    // 3. Modify the name of an existing store
    public boolean modifyStoreName(String prevStoreName, String newStoreName) {
        if (prevStoreName == null || prevStoreName.isEmpty() || newStoreName == null || newStoreName.isEmpty()) {
            return false;
        }
        try {
            String matchedPrevStoreName = db.getMatchedEntries("stores.csv", 2, prevStoreName).get(0);
            ArrayList<String> matchedNewStoreName = db.getMatchedEntries("stores.csv", 2, newStoreName);
            if (matchedNewStoreName.isEmpty()) { // there are no already existing stores with the name newStoreName
                String[] newStoreRepresentation = matchedPrevStoreName.split(",");
                newStoreRepresentation[2] = newStoreName;
                db.modifyDatabase("stores.csv", matchedPrevStoreName, String.join(",", newStoreRepresentation));
                return true;
            } else {
                return false;
            }
        } catch (IndexOutOfBoundsException e) { // will handle if the store specified by prevStoreName is non-existent
            return false;
        }
    }

    // 4. Add a new product to any store of their choosing
    public boolean createNewProduct(String storeName, String productName, int availableQuantity, double price, String productDescription) {
        // update stores.csv database with updated number of products
        if (productName == null || productName.isEmpty() || availableQuantity < 0 || price < 0 || productDescription == null || productDescription.isEmpty()) {
            return false;
        }
        try {
            ArrayList<String> matchedProducts = db.getMatchedEntries("products.csv", 4, productName);
            if (!matchedProducts.isEmpty()) { // product already exists with the given name
                return false;
            } else {
                String matchedStoreEntry = db.getMatchedEntries("stores.csv", 2, storeName).get(0);

                Product newProduct = new Product(productName, availableQuantity, price, productDescription);

                String entry = String.format("%s,%s,%s,%s", super.getUserID(),matchedStoreEntry.split(",")[0],newProduct.getProductIdentificationNumber(), storeName) + "," + newProduct.toString();

                db.addToDatabase("products.csv", entry);

                // Since stores.csv contains count of number of individual products in a store, update that count when a new product has been created
                String[] newStoreRepresentation = matchedStoreEntry.split(",");
                int previousNumProducts = Integer.parseInt(newStoreRepresentation[3]);
                newStoreRepresentation[3] = String.valueOf(previousNumProducts + 1);
                db.modifyDatabase("stores.csv", matchedStoreEntry, String.join(",", newStoreRepresentation));

                return true;
            }
        } catch (IndexOutOfBoundsException e) { // if the storeName doesn't exist
            return false;
        } 
    }

    // 5. Edit an existing product in any store of their choosing
    public boolean editProduct(String productName, String editParam, String newValue) {
        // the seller can only edit name, price, and description of product and nothing else
        // quantity updates will take place within customer since it is attached to customer add to shopping cart and purchase functionalities
        if (!editParam.equals("name") && !editParam.equals("price") && !editParam.equals("description")) {
            return false;
        }
        try {
            String matchedProduct = db.getMatchedEntries("products.csv", 4, productName).get(0);
            String[] newProductRepresentation = matchedProduct.split(",");
            switch (editParam) {
                // if the editParam is name, need to check whether that newName is not already associated with an existing product
                case "name" -> {
                    ArrayList<String> matchedProductName = db.getMatchedEntries("products.csv", 4, newValue);
                    if (matchedProductName.isEmpty()) { // no existing products have same name as newValue
                        newProductRepresentation[4] = newValue;
                    } else {
                        return false;
                    }
                }
                case "price" -> newProductRepresentation[6] = newValue;
                case "description" -> newProductRepresentation[7] = newValue;
            }
            db.modifyDatabase("products.csv", matchedProduct, String.join(",", newProductRepresentation));
            return true;
        } catch (IndexOutOfBoundsException e) { // if the product specified by productName doesn't exist in the database
            return false;
        }
    }

    // 6. Delete an existing product in any store of their choosing
    public boolean deleteProduct(String storeName, String productName) {
        boolean productDeleted = false;
        try {
            // gets all products associated with the given storeName then searches for a match for the productname to be deleted
            String matchedStore = db.getMatchedEntries("stores.csv", 2, storeName).get(0);
            ArrayList<String> matchedProductsForGivenStore = db.getMatchedEntries("products.csv", 3, storeName);
            for (int i = 0; i < matchedProductsForGivenStore.size(); i++) {
                String[] productEntry = matchedProductsForGivenStore.get(i).split(",");
                if (productEntry[4].equals(productName)) {
                    db.removeFromDatabase("products.csv", matchedProductsForGivenStore.get(i));

                    // since stores.csv contains how many products the store has individually, modify that count after deleting a product
                    String[] newStoreRepresentation = matchedStore.split(",");
                    int previousNumProducts = Integer.parseInt(newStoreRepresentation[3]);
                    newStoreRepresentation[3] = String.valueOf(previousNumProducts - 1);
                    db.modifyDatabase("stores.csv", matchedStore, String.join(",", newStoreRepresentation));

                    productDeleted =  true;
                    break;
                }
            }
        } catch (Exception e) {
            productDeleted =  false;
        }
        return productDeleted;
    }

    // 7. View list of "SALES" by store(includes customer information and revenues from sale)
    public void viewStoreSales() {
        ArrayList<String> purchaseHistoryEntries = db.getMatchedEntries("purchaseHistories.csv", 1, super.getUserID());
        if (purchaseHistoryEntries.size() == 0) {
            System.out.println("Either no stores have been created with products for sale, or no sales have taken place in any of your existing stores.");
        } else {
            HashMap<String, ArrayList<String>> salesByStore = new HashMap<>();
            for (int i = 0; i < purchaseHistoryEntries.size(); i++) {
                String[] purchaseHistoryEntry = purchaseHistoryEntries.get(i).split(",");
                String storeIdentifier = String.format("Store %s-%s Sales\n", purchaseHistoryEntry[2], purchaseHistoryEntry[4]);
                String customer = purchaseHistoryEntry[0];
                int purchaseQuantity = Integer.parseInt(purchaseHistoryEntry[6]);
                double revenues = Double.parseDouble(purchaseHistoryEntry[7]);

                String salesInformation = String.format("Customer: %s, Product: %s-%s, Quantity Purchased: %d, Revenues: %.2f", customer, purchaseHistoryEntry[3], purchaseHistoryEntry[5], purchaseQuantity, revenues);

                if (!salesByStore.containsKey(storeIdentifier)) {
                    salesByStore.put(storeIdentifier, new ArrayList<String>());
                } 
                salesByStore.get(storeIdentifier).add(salesInformation);
            }
            for (String store: salesByStore.keySet()) {
                System.out.println(store);
                for (String saleInformation: salesByStore.get(store)) {
                    System.out.println("\t" + saleInformation);
                }
            }
        }
    }
    
    // 8. import products into any store of their choosing(would just create new entries in the products.csv file)
    public boolean importProducts(String filePath, String storeID) {
        // product file will be as such: Product name,AvailableQuantity,Price,Description
        try {
            String matchedStoreEntry = db.getMatchedEntries("stores.csv", 0, storeID).get(0);
            File productFile = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(productFile));
            br.readLine(); // skip the headers
            String line;
            while ((line = br.readLine()) != null) {
                String[] productLine = line.split(",");
                Product newProduct = new Product(productLine[0], Integer.parseInt(productLine[1]), Double.parseDouble(productLine[2]), productLine[3]);
                String entry = String.format("%s,%s,%s,%s", super.getUserID(),storeID,newProduct.getProductIdentificationNumber(), matchedStoreEntry.split(",")[2]) + "," + newProduct.toString();
                db.addToDatabase("products.csv", entry);
            }
            br.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    // 9. export the products associated with any of their existing stores
    public boolean exportProducts(String storeID) {
        ArrayList<String> matchedProducts = db.getMatchedEntries("products.csv", 1, storeID);
        try {
            if (!(matchedProducts.isEmpty())) {
                File targetDir = new File("exportedProducts");
                if (!targetDir.exists()) {
                    targetDir.mkdir();
                }
                File output = new File(targetDir, storeID + ".csv");
                output.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(output));
                String headers = db.getFileHeaders("products.csv");
                bw.write(headers + "\n");
                for (int i = 0; i < matchedProducts.size(); i++) {
                    bw.write(matchedProducts.get(i) + "\n");
                }
                bw.close();
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    // 10. View number of products currently in customers' shopping carts(includes store information and details associated with the products - would make use of the shoppingCarts.csv file for information retrieval)
    public void viewCustomerShoppingCarts() {
        // only for stores associated with this seller's ID
        ArrayList<String> matchedCarts = db.getMatchedEntries("shoppingCarts.csv", 1, super.getUserID());
        if (matchedCarts.isEmpty()) {
            System.out.println("Either no stores have been created with products for sale, or there are no customers who have products added to their shopping cart from any of your stores!");
        } else {
            HashMap<String, ArrayList<String>> cartsByStore = new HashMap<>();
            for (int i = 0; i < matchedCarts.size(); i++) {
                String[] purchaseHistoryEntry = matchedCarts.get(i).split(",");
                String storeIdentifier = String.format("Store %s-%s Sales\n", purchaseHistoryEntry[2], purchaseHistoryEntry[4]);
                String customer = purchaseHistoryEntry[0];
                int purchaseQuantity = Integer.parseInt(purchaseHistoryEntry[6]);
                double revenues = Double.parseDouble(purchaseHistoryEntry[7]);

                String salesInformation = String.format("Customer: %s, Product: %s-%s, Quantity: %d, Price: %.2f", customer, purchaseHistoryEntry[3], purchaseHistoryEntry[5], purchaseQuantity, revenues);

                if (!cartsByStore.containsKey(storeIdentifier)) {
                    cartsByStore.put(storeIdentifier, new ArrayList<String>());
                }
                cartsByStore.get(storeIdentifier).add(salesInformation);
            }
            for (String store: cartsByStore.keySet()) {
                System.out.println(store);
                for (String saleInformation: cartsByStore.get(store)) {
                    System.out.println("\t" + saleInformation);
                }
            }
        }
    }

}