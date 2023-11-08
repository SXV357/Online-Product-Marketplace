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


    // creating a seller for the very first time(They haven't created any stores yet so initializing arraylist to empty sequence)
    public Seller(String email, String password, UserRole role) {
        super(email, password, role);
    }

    // re-initializing a seller once they log into the application
    public Seller(String userID, String email, String password, UserRole role) {
        super(userID, email, password, role);
    }

    public void setStores (ArrayList<Store> stores) {
        this.stores = stores;
    }

    // 1. Create a new store
    public boolean createNewStore(String storeName) {
        if (storeName == null || storeName.isEmpty()) {
            return false;
        }
        Store newStore = new Store(storeName);
        String newStoreEntry = String.format("%s,%s,%s,%d", newStore.getStoreIdentificationNumber(), super.getUserID(), newStore.getStoreName(), 0); // a new store will start with 0 products by default
        db.addToDatabase("stores.csv", newStoreEntry);
        return true;
    }

    // 2. Delete an existing store(in its entirety); Takes in ID because multiple stores could have the same name
    public boolean deleteStore(String storeID) {
        // There should only be one matched store since all IDs will be unique
        try {
            String matchedStore = db.getMatchedEntries("stores.csv", 0, storeID).get(0);
            db.removeFromDatabase("stores.csv", matchedStore);
            return true;
        } catch (IndexOutOfBoundsException e) { // if there are no entries
            return false;
        }
    }

    // 3. Modify the name of an existing store
    public boolean modifyStoreName(String storeID, String newStoreName) {
        if (newStoreName == null || newStoreName.isEmpty()) {
            return false;
        }
        try {
            String matchedStore = db.getMatchedEntries("stores.csv", 0, storeID).get(0);
            String[] storeRepresentation = matchedStore.split(",");
            storeRepresentation[2] = newStoreName;
            db.modifyDatabase("users.csv", matchedStore, String.join(",", storeRepresentation));
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    // 4. Add a new product to any store of their choosing
    public boolean createNewProduct(String storeID, String productName, int availableQuantity, double price, String productDescription) {
        if (productName == null || productName.isEmpty() || availableQuantity < 0 || price < 0 || productDescription == null || productDescription.isEmpty()) {
            return false;
        }
        String matchedStoreEntry = db.getMatchedEntries("stores.csv", 0, storeID).get(0);
        try {
            // making sure the store exists before creating a product associated with it
            Product newProduct = new Product(productName, availableQuantity, price, productDescription);
            String entry = String.format("%s,%s,%s,%s", super.getUserID(),storeID,newProduct.getProductIdentificationNumber(), matchedStoreEntry.split(",")[2]) + "," + newProduct.toString();
            db.addToDatabase("products.csv", entry);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        } 
    }

    // 5. Edit an existing product in any store of their choosing
    public boolean editProduct(String productID, String editParam, String newValue) {
        // the seller can only edit name, price, and description of product and nothing else
        if (!editParam.equals("name") && !editParam.equals("price") && !editParam.equals("description")) {
            return false;
        }
        try {
            String matchedProduct = db.getMatchedEntries("products.csv", 2, productID).get(0);
            String[] productRepresentation = matchedProduct.split(",");
            switch (editParam) {
                case "name" -> productRepresentation[4] = newValue;
                case "price" -> productRepresentation[6] = newValue;
                case "description" -> productRepresentation[7] = newValue;
            }
            db.modifyDatabase("products.csv", matchedProduct, String.join(",", productRepresentation));
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    // 6. Delete an existing product in any store of their choosing
    public boolean deleteProduct(String storeID, String productID) {
        boolean productDeleted = false;
        try {
            ArrayList<String> matchedStores = db.getMatchedEntries("products.csv", 0, storeID);
            for (int i = 0; i < matchedStores.size(); i++) {
                String[] productEntry = matchedStores.get(i).split(",");
                if (productEntry[2].equals(productID)) {
                    db.removeFromDatabase("products.csv", matchedStores.get(i));
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
                } else {
                    salesByStore.get(storeIdentifier).add(salesInformation);
                }
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
                } else {
                    cartsByStore.get(storeIdentifier).add(salesInformation);
                }
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