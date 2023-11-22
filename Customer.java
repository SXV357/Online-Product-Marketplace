import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Project 5 - Customer.java
 * 
 * Class to represent the permissions and details associated with a customer
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version November 21, 2023
 */
public class Customer extends User {

    private ArrayList<String> shoppingCart;
    private ArrayList<String> purchasehistory;
    private Database db = new Database();

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
    public String arrToString(ArrayList<String> array, boolean quantity) {

        StringBuilder output = new StringBuilder();
        if (quantity) {
            output.append("Product Name - Store Name - Quantity - Price\n");
        } else {
            output.append("Product Name - Store Name - Price\n");
        }
        for (int i = 0; i < array.size(); i++) {
            output.append(i + 1).append(") ").append(array.get(i)).append("\n");
        }
        return output.toString();
    }

    /**
     * Returns the user's shopping history
     *
     * @return The shopping history as a string
     */
    public String getShoppingHistory() {
        String[] info;
        StringBuilder sb = new StringBuilder();
        ArrayList<String> output = new ArrayList<>();

        purchasehistory = db.getMatchedEntries("purchaseHistories.csv", 0, getUserID());

        if (purchasehistory.isEmpty()) {
            return "No Products Available";
        } else {
            for (String product : db.getMatchedEntries("purchaseHistories.csv", 0, getUserID())) {
                sb = new StringBuilder();
                info = product.split(",");
                sb.append(info[5]).append("     ");
                sb.append(info[4]).append("     ");
                sb.append(info[6]).append("     ");
                sb.append(info[7]).append("     ");
                output.add(sb.toString());
            }
        }
        return arrToString(output, true);
    }

    /**
     * Returns a specific products's info
     *
     * @return The product's info
     */
    public String getProductInfo(int index) {
        try {
            index -= 1;
            StringBuilder sb = new StringBuilder();
            String[] prodInfo = db.getDatabaseContents("products.csv").get(index).split(",");

            sb.append("Store Name: ").append(prodInfo[3]).append(System.getProperty("line.separator"));
            sb.append("Product Name: ").append(prodInfo[4]).append(System.getProperty("line.separator"));
            sb.append("Available Quantity: ").append(prodInfo[5]).append(System.getProperty("line.separator"));
            sb.append("Price: ").append(prodInfo[6]).append(System.getProperty("line.separator"));
            sb.append("Description: ").append(prodInfo[7]);
            for (int i = 8; i < prodInfo.length; i++) {
                sb.append(prodInfo[i]);
            }

            return sb.toString();
        } catch (IndexOutOfBoundsException e) {
            return "Invalid Product";
        }

    }

    /**
     * Returns the all the products in the csv
     *
     * @return The products listed as a string
     */
    public String getAllProducts() {
        ArrayList<String> productList = db.getDatabaseContents("products.csv");
        StringBuilder sb = new StringBuilder();
        String[] info;
        ArrayList<String> output = new ArrayList<>();
        if (productList.isEmpty()) {
            return "No Products Available";
        } else {
            for (String product : productList) {
                sb = new StringBuilder();
                info = product.split(",");
                sb.append(info[4]).append("     ");
                sb.append(info[3]).append("     ");
                sb.append(info[6]).append("     ");
                output.add(sb.toString());
            }
        }
        return arrToString(output, false);

    }

    /**
     * Internal Supplementary formatting method
     *
     * @return The products formatted as a string
     */
    public String formatProducts(ArrayList<String> productList, boolean quantity) {
        StringBuilder sb = new StringBuilder();
        String[] info;
        ArrayList<String> output = new ArrayList<>();

        if (productList.isEmpty()) {
            return "No Products Available";
        } else {
            for (String product : productList) {
                sb = new StringBuilder();
                info = product.split(",");
                sb.append(info[4]).append("     ");
                sb.append(info[3]).append("     ");
                if (quantity) {
                    sb.append(info[5]).append("     ");
                }
                sb.append(info[6]).append("     ");
                output.add(sb.toString());
            }
        }
        return arrToString(output, quantity);

    }

    /**
     * Returns the user's shopping cart
     *
     * @return The shopping cart as a string
     */
    public String getCart() {
        String[] info;
        StringBuilder sb = new StringBuilder();
        ArrayList<String> output = new ArrayList<>();
        shoppingCart = db.getMatchedEntries("shoppingCarts.csv", 0, getUserID());

        if (shoppingCart.isEmpty()) {
            return "No Products Available";
        } else {
            for (String product : shoppingCart) {
                sb = new StringBuilder();
                info = product.split(",");
                sb.append(info[5]).append("     ");
                sb.append(info[4]).append("     ");
                sb.append(info[6]).append("     ");
                sb.append(info[7]).append("     ");
                output.add(sb.toString());
            }
        }
        return arrToString(output, true);
    }

    /**
     * Removes a given item from the cart
     *
     * @param index the index of the cart the remove
     */
    public boolean removeFromCart(int index) {
        index -= 1;
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
        index -= 1;
        try {
            ArrayList<String> products = db.getDatabaseContents("products.csv");
            String[] target = db.getMatchedEntries("products.csv", 2, products.get(index).split(",")[2]).get(0)
                    .split(",");
            if (quantity <= 0 || Integer.parseInt(target[5]) < quantity) {
                return false;
            }

            shoppingCart = db.getMatchedEntries("shoppingCarts.csv", 0, getUserID());
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
            output.append(String.format("%.2f", Double.parseDouble(target[6]) * (double) updatedQuant));

            shoppingCart.add(output.toString());
            db.addToDatabase("shoppingCarts.csv", shoppingCart.get(shoppingCart.size() - 1));
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

    }

    /**
     * Purchases the items in the cart
     */
    public boolean purchaseItems() {
        StringBuilder output = new StringBuilder();
        String[] updatedEntry;
        String[] target;
        int quantity;
        shoppingCart = db.getMatchedEntries("shoppingCarts.csv", 0, getUserID());
        purchasehistory = db.getMatchedEntries("purchaseHistories.csv", 0, getUserID());

        output.append(getUserID()).append(",");
        String item;
        boolean duplicate = false;
        for (int i = 0; i < shoppingCart.size(); i++) {
            item = shoppingCart.get(i);
            target = db.getMatchedEntries("products.csv", 2, item.split(",")[3]).get(0).split(",");
            quantity = Integer.parseInt(item.split(",")[6]);
            if (quantity <= 0 || Integer.parseInt(target[5]) < quantity) {
                db.removeFromDatabase("shoppingCarts.csv", item);
                shoppingCart.remove(item);
                return false;
            } else {
                duplicate = false;
                for (int j = 0; j < purchasehistory.size(); j++) {
                    if (item.split(",")[3].equals(purchasehistory.get(i).split(",")[3])) {
                        updatedEntry = purchasehistory.get(i).split(",");
                        updatedEntry[6] = String
                                .valueOf(Integer.parseInt(updatedEntry[6]) + Integer.parseInt(item.split(",")[6]));
                        updatedEntry[7] = String
                                .valueOf(Double.parseDouble(updatedEntry[7]) + Double.parseDouble(item.split(",")[7]));
                        db.modifyDatabase("purchaseHistories.csv", purchasehistory.get(i),
                                String.join(",", updatedEntry));
                        duplicate = true;
                    }
                }
                if (!duplicate) {
                    db.addToDatabase("purchaseHistories.csv", item);
                }
                target = db.getMatchedEntries("products.csv", 2, item.split(",")[3]).get(0).split(",");
                target[5] = String.valueOf(Integer.parseInt(target[5]) - Integer.parseInt(item.split(",")[6]));
                db.modifyDatabase("products.csv",
                        db.getMatchedEntries("products.csv", 2, target[2]).get(0),
                        String.join(",", target));
            }
            db.removeFromDatabase("shoppingCarts.csv", item);
            shoppingCart.remove(item);
        }
        return true;

    }

    /**
     * Sorts based on user's choice of price or quantity
     *
     * @return Returns the sorted string
     */
    public String sortProducts(String choice) {
        ArrayList<String> sorted = db.getDatabaseContents("products.csv");
        int n = sorted.size();
        String temp = "";
        int searchIndex = -1;
        boolean quantity = false;
        if (choice.equals("price")) {
            searchIndex = 6;
        } else if (choice.equals("quantity")) {
            searchIndex = 5;
            quantity = true;
        }

        if (searchIndex != -1) {
            if (sorted.size() > 1) {
                for (int i = 0; i < n; i++) {
                    for (int j = 1; j < (n - i); j++) {
                        if (Double.parseDouble(sorted.get(j - 1).split(",")[searchIndex]) > Double
                                .parseDouble(sorted.get(j).split(",")[searchIndex])) {
                            temp = sorted.get(j - 1);
                            sorted.set(j - 1, sorted.get(j));
                            sorted.set(j, temp);
                        }
                    }
                }
            }
            return formatProducts(sorted, quantity);
        } else {
            return "Invalid search!";
        }

    }

    /**
     * Exports the user's purchase history
     *
     * @return Returns true if the process is successful
     */
    public boolean exportPurchaseHistory() {
        purchasehistory = db.getMatchedEntries("purchaseHistories.csv", 0, getUserID());
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
                bw.flush();
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
            return formatProducts(productsFound, false);
        }

    }
}
