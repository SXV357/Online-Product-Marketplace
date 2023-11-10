import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Project 4 - Customer.java
 *
 * Class to represent the permissions and details associated with a customer
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 *
 * @version November 2, 2023
 */

public class Customer extends User{

    private ArrayList<String> shoppingCart;
    private ArrayList<String> purchasehistory;
    private Database db = new Database();
    private String userID;

    public Customer(String email, String password, UserRole role) {
        super(email, password, role);
        shoppingCart = db.getMatchedEntries("shoppingCarts.csv", 0, getUserID());
        purchasehistory = db.getMatchedEntries("purchaseHistories.csv", 0, getUserID());
    }

    public Customer(String userID, String email, String password, UserRole role) {
        super(userID, email, password, UserRole.CUSTOMER);
        shoppingCart = db.getMatchedEntries("shoppingCarts.csv", 0, getUserID());
        purchasehistory = db.getMatchedEntries("purchaseHistories.csv", 0, getUserID());
    }

    /**
     * Converts arrays to list of strings
     * 
     * @return String of array's contents
    */
    public String arrToString(ArrayList<String> array) {
        StringBuilder output = new StringBuilder();
        String[] splitArr;
        for (int i = 0; i < array.size(); i++) {
            output.append(i).append(". ").append(array.get(i)).append(System.getProperty("line.separator"));
        }
        return output.toString();
    }

    /**
     * Returns the user's shopping history
     * 
     * @return The shopping history as a string
    */
    public String getShoppingHistory() {
        return arrToString(purchasehistory);
    }

    /**
     * Returns the user's shopping history
     * 
     * @return The shopping history as a string
    */
    public String getProductInfo(int index) {
        StringBuilder sb = new StringBuilder();
        String[] prodInfo = db.getDatabaseContents("products.csv").get(index).split(",");

        sb.append("Store Name: ").append(prodInfo[3]).append(System.getProperty("line.separator"));
        sb.append("Product Name: ").append(prodInfo[4]).append(System.getProperty("line.separator"));;
        sb.append("Available Quantity: ").append(prodInfo[5]).append(System.getProperty("line.separator"));;
        sb.append("Price: ").append(prodInfo[6]).append(System.getProperty("line.separator"));;
        sb.append("Description: ").append(prodInfo[7]);
        for (int i = 8; i < prodInfo.length; i++) {
            sb.append(prodInfo[i]);
        }

        return sb.toString();
    }

    /**
     * Returns the user's shopping cart
     * 
     * @return The shopping cart as a string
    */
    public String getCart() {
        return arrToString(shoppingCart);
    }

    /**
     * Removes a given item from the cart
     * 
     * @param index the index of the cart the remove
     */
    public boolean removeFromCart(int index) {
        try {
            db.removeFromDatabase("shoppingCarts.csv", shoppingCart.get(index));
            shoppingCart.remove(index);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        
    }

    /**
     * Adds a given item from the cart
     * 
     * @param productID the index of the product the add
     */
    public boolean addToCart(int index, int quantity) {
        try {
            ArrayList<String> products = db.getDatabaseContents("products.csv");
            String[] target = db.getMatchedEntries("products.csv", 2, products.get(index).split(",")[2]).get(0).split(",");
            if (Integer.parseInt(target[5]) < quantity) {
                return false;
            } else {
                target[5] = String.valueOf(Integer.parseInt(target[5]) - quantity);
                db.modifyDatabase("products.csv", db.getMatchedEntries("products.csv", 2, products.get(index).split(",")[2]).get(0), String.join(",", target));
            }
            
            StringBuilder output = new StringBuilder();
            int updatedQuant = quantity;
            output.append(getUserID()).append(",");
            for (String item : shoppingCart) {
                if (item.contains(target[2])) {    
                    updatedQuant = Integer.parseInt(item.split(",")[6]) + quantity;
                    db.removeFromDatabase("shoppingCarts.csv", item);
                    shoppingCart.remove(item);
                    break; 
                }
            }
            for (int i = 0; i < 5; i++) {
                output.append(target[i]).append(",");
            }
            output.append(updatedQuant).append(",");
            output.append(Double.parseDouble(target[6]) * updatedQuant);
    
            shoppingCart.add(output.toString());
            db.addToDatabase("shoppingCarts.csv", shoppingCart.get(shoppingCart.size()-1));
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        
    }

    /**
     * Purchases the items in the cart
     * 
     */
    public void purchaseItems() {
            StringBuilder output = new StringBuilder();
            String[] updatedEntry;
            output.append(getUserID()).append(",");
            for (String item : shoppingCart) {
                for (String entry : purchasehistory) {
                    if (item.split(",")[2].equals(entry.split(",")[2])) {  
                        updatedEntry = entry.split(",");
                        updatedEntry[6] = String.valueOf(Integer.parseInt(updatedEntry[6]) + Integer.parseInt(item.split(",")[6]));
                        updatedEntry[7] = String.valueOf(Double.parseDouble(updatedEntry[7]) + Double.parseDouble(item.split(",")[7]));
                        db.modifyDatabase("purchaseHistories.csv", entry, String.join(",", updatedEntry));
                    } else {
                        db.addToDatabase("purchaseHistories.csv", item);
                    }
                    db.removeFromDatabase("shoppingCarts.csv", item);
                }
            }
       
    }


    /**
     * Sorts based on user's choice of price or quantity
     * 
     * @return Returns the sorted string
     */
    public String sortStores(String choice) {
        ArrayList<String> sorted = db.getDatabaseContents("products.csv");
        int n = sorted.size();
        String temp = "";
        int searchIndex;
        if (choice.equals("price")) {
            searchIndex = 6;
        } else if (choice.equals("quantity")) {
            searchIndex = 5;
        } else {
            searchIndex = 6;
            sorted = purchasehistory;
        }
        
        if (sorted.size() > 1) {
            for (int i = 0; i < n; i++) {
                for (int j = 1; j < (n - i); j++) {
                    if (Double.parseDouble(sorted.get(j - 1).split(",")[searchIndex])
                           > Double.parseDouble(sorted.get(j).split(",")[searchIndex])) {
                       temp = sorted.get(j - 1);
                       sorted.set(j - 1, sorted.get(j));
                        sorted.set(j, temp);
                  }
               }
          }
        }
        
        return arrToString(sorted);
    }

    /**
     * Exports the user's purchase history
     * 
     * @return Returns true if the process is successful
     */
    public boolean exportPurchaseHistory() {
        try {
            if (!(purchasehistory.isEmpty())) {
                File targetDir = new File("exportedHistory");
                if (!targetDir.exists()) {
                    targetDir.mkdir();
                }
                File output = new File(targetDir, getEmail() + ".csv");
                output.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(output));
                String headers = db.getFileHeaders("purchaseHistories.csv");
                bw.write(headers + "\n");
                for (int i = 0; i < purchasehistory.size(); i++) {
                    bw.write(purchasehistory.get(i) + "\n");
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

    /**
     * Searches for all products containing a given query
     * 
     * @return Returns the products found
     */
    public String searchProducts(String query) {
        ArrayList<String> productsFound = new ArrayList<>();
        for (String product : db.getDatabaseContents("products.csv")) {
            if (product.contains(query)) {
                productsFound.add(product);
            }
        }
        if (productsFound.isEmpty()) {
            return "Query has returned no results!";
        } else {
            return arrToString(productsFound);
        }
        
    }
}
