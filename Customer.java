import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Project 5 - Customer.java
 *
 * Class to represent the permissions and details associated with a customer
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 *
 * @version December 9, 2023
 */
public class Customer extends User {

    private ArrayList<String> shoppingCart;
    private ArrayList<String> purchasehistory;
    private Database db = new Database();

    public Customer(String email, String password, UserRole role) throws Exception {
        super(email, password, role);
        shoppingCart = db.getMatchedEntries("shoppingCarts.csv", 0, getUserID());
        purchasehistory = db.getMatchedEntries("purchaseHistories.csv", 0, getUserID());
    }

    public Customer(String userID, String email, String password, UserRole role) throws Exception {
        super(userID, email, password, UserRole.CUSTOMER);
        shoppingCart = db.getMatchedEntries("shoppingCarts.csv", 0, getUserID());
        purchasehistory = db.getMatchedEntries("purchaseHistories.csv", 0, getUserID());
    }

    /**
     * Fetches all the stores that exist in the application and returns them in the form of an arraylist.
     * Utilized to determine customer dashboard behavior.
     *
     * @return An arraylist containing all the stores
     */
    public ArrayList<String> fetchAllStores() throws CustomerException {
        ArrayList<String> allStores = db.getDatabaseContents("stores.csv");
        if (allStores.isEmpty()) {
            throw new CustomerException("No stores have been created yet!");
        }
        return allStores;
    }

    /**
     * Converts arrays to list of strings
     *
     * @return String of array's contents
     */
    public String arrToString(ArrayList<String> array) {

        StringBuilder output = new StringBuilder();
        output.append("Product Name - Store Name - Quantity - Price\n");
        for (int i = 0; i < array.size(); i++) {
            output.append(i + 1).append(") ").append(array.get(i)).append("\n");
        }
        return output.toString();
    }

    /**
     * Returns the user's shopping history
     *
     * @return The shopping history as a string
     * @throws CustomerException
     */
    public String getShoppingHistory() throws CustomerException {
        String[] info;
        StringBuilder sb = new StringBuilder();
        ArrayList<String> output = new ArrayList<>();

        purchasehistory = db.getMatchedEntries("purchaseHistories.csv", 0, getUserID());

        if (purchasehistory.isEmpty()) {
            throw new CustomerException("Shopping History is Empty");
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
        return arrToString(output);
    }

    /**
     * Returns a specific products's info
     *
     * @return The product's info
     * @throws CustomerException
     */
    public String getProductInfo(int index) throws CustomerException {
        try {

            StringBuilder sb = new StringBuilder();
            String[] prodInfo = db.getDatabaseContents("products.csv").get(index).split(",");

            sb.append(prodInfo[3]).append(",");
            sb.append(prodInfo[4]).append(",");
            sb.append(prodInfo[5]).append(",");
            sb.append(prodInfo[6]).append(",");
            sb.append(prodInfo[7]);

            return sb.toString();
        } catch (IndexOutOfBoundsException e) {
            throw new CustomerException("Unable to retrieve information about this product. Please try again!");
        }

    }

    /**
     * Returns the all the products in the csv
     *
     * @return The products listed as a string
     * @throws CustomerException
     */
    public String getAllProducts() throws CustomerException {
        ArrayList<String> productList = db.getDatabaseContents("products.csv");
        StringBuilder sb = new StringBuilder();
        String[] info;
        ArrayList<String> output = new ArrayList<>();
        if (productList.isEmpty()) {
            throw new CustomerException("No sellers have added products to any of their stores yet");
        } else {
            for (String product : productList) {
                sb = new StringBuilder();
                info = product.split(",");
                sb.append(info[4]).append("     ");
                sb.append(info[3]).append("     ");
                sb.append(info[5]).append("     ");
                sb.append(info[6]).append("     ");
                output.add(sb.toString());
            }
        }
        return arrToString(output);

    }

    /**
     * Internal Supplementary formatting method
     *
     * @return The products formatted as a string
     * @throws CustomerException
     */
    public String formatProducts(ArrayList<String> productList) throws CustomerException {
        StringBuilder sb = new StringBuilder();
        String[] info;
        ArrayList<String> output = new ArrayList<>();

        if (productList.isEmpty()) {
            throw new CustomerException("No Products Available");
        } else {
            for (String product : productList) {
                sb = new StringBuilder();
                info = product.split(",");
                sb.append(info[4]).append("     ");
                sb.append(info[3]).append("     ");
                sb.append(info[5]).append("     ");
                sb.append(info[6]).append("     ");
                output.add(sb.toString());
            }
        }
        return arrToString(output);

    }

    /**
     * Returns the user's shopping cart
     *
     * @return The shopping cart as a string
     * @throws CustomerException
     */
    public String getCart() throws CustomerException {
        String[] info;
        StringBuilder sb = new StringBuilder();
        ArrayList<String> output = new ArrayList<>();
        shoppingCart = db.getMatchedEntries("shoppingCarts.csv", 0, getUserID());

        if (shoppingCart.isEmpty()) {
            throw new CustomerException("No Items in Shopping Cart");
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
        return arrToString(output);
    }

    /**
     * Removes a given item from the cart
     *
     * @param index the index of the cart the remove
     * @throws CustomerException
     */
    public void removeFromCart(int index) throws CustomerException {
        try {
            db.removeFromDatabase("shoppingCarts.csv", shoppingCart.get(index));
            shoppingCart.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new CustomerException("Unable to remove this item from cart. Please try again!");
        }

    }

    /**
     * Adds a given item from the cart
     *
     * @param productID the index of the product the add
     * @throws CustomerException
     */
    public void addToCart(int index, String desiredQuantity) throws CustomerException {
        try {
            ArrayList<String> products = db.getDatabaseContents("products.csv");
            String[] target = db.getMatchedEntries("products.csv", 2, products.get(index).split(",")[2]).get(0)
                    .split(",");

            int quantity;
            try {
                quantity = Integer.parseInt(desiredQuantity);
            } catch (NumberFormatException e) {
                throw new CustomerException("The quantity has to be an integer and cannot be a string");
            }
            if (quantity <= 0) {
                throw new CustomerException("The quantity selected must be greater than 0");
            } else if (Integer.parseInt(target[5]) < quantity) {
                throw new CustomerException("You cannot select more than " + Integer.parseInt(target[5]) + " items");
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
        } catch (Exception e) {
            throw new CustomerException(e.getMessage());
        }
    }

    /**
     * Purchases the items in the cart
     *
     * @throws CustomerException
     */
    public void purchaseItems() throws CustomerException {
        StringBuilder output = new StringBuilder();
        String[] updatedEntry;
        String[] target;
        int quantity;
        shoppingCart = db.getMatchedEntries("shoppingCarts.csv", 0, getUserID());
        purchasehistory = db.getMatchedEntries("purchaseHistories.csv", 0, getUserID());

        if (shoppingCart.isEmpty()) {
            throw new CustomerException("You don\'t have any items added to your cart yet!");
        }

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
                throw new CustomerException("Not Enough Product Stocked");
            } else {
                duplicate = false;
                for (int j = 0; j < purchasehistory.size(); j++) {
                    if (item.split(",")[3].equals(purchasehistory.get(i).split(",")[3])) {
                        updatedEntry = purchasehistory.get(i).split(",");
                        updatedEntry[6] = String
                                .valueOf(Integer.parseInt(updatedEntry[6]) + Integer.parseInt(item.split(",")
                                        [6]));
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

    }

    /**
     * Sorts based on user's choice of price or quantity
     *
     * @return Returns the sorted string
     * @throws CustomerException
     */
    public String sortProducts(String choice) throws CustomerException {
        ArrayList<String> sorted = db.getDatabaseContents("products.csv");
        int n = sorted.size();
        String temp = "";
        int searchIndex = -1;
        if (choice.equals("price")) {
            searchIndex = 6;
        } else if (choice.equals("quantity")) {
            searchIndex = 5;
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
            return formatProducts(sorted);
        } else {
            throw new CustomerException("You can only choose to sort the price or quantity");
        }

    }

    /**
     * Exports the user's purchase history
     *
     * @return Returns true if the process is successful
     * @throws CustomerException
     */
    public void exportPurchaseHistory() throws CustomerException {
        purchasehistory = db.getMatchedEntries("purchaseHistories.csv", 0, getUserID());
        try {
            if (!(purchasehistory.isEmpty())) {
                File targetDir = new File("exportedHistory");
                if (!targetDir.exists()) {
                    targetDir.mkdir();
                }
                File output = new File(targetDir, getEmail() + ".csv");
                if (!output.exists()) {
                    output.createNewFile();
                    writeToExportedHistory(output, purchasehistory);
                } else {
                    File existing = new File(targetDir, getEmail() + ".csv");
                    writeToExportedHistory(existing, purchasehistory);
                }
            } else {
                throw new CustomerException("Purchase History is Empty");
            }
        } catch (IOException e) {
            throw new CustomerException("An error occurred when exporting purchase history. Please try again");
        }
    }

    /**
     * Updates the contents of the customer's purchase history file with the new values
     *
     * @param output The customer's exported purchase history file to write to
     * @param matchedPurchaseHistoryEntries The purchase history entries associated with the customer
     * @throws CustomerException
     */
    public void writeToExportedHistory(File output, ArrayList<String> matchedPurchaseHistoryEntries)
            throws CustomerException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(output, false))) {
            String headers = db.getFileHeaders("purchaseHistories.csv");
            bw.write(headers + "\n");
            for (int i = 0; i < matchedPurchaseHistoryEntries.size(); i++) {
                bw.write(matchedPurchaseHistoryEntries.get(i) + "\n");
            }
        } catch (IOException e) {
            throw new CustomerException("An error occurred when exporting purchase history. Please try again");
        }
    }

    /**
     * Searches for all products containing a given query
     *
     * @return Returns the products found
     * @throws CustomerException
     */
    public String searchProducts(String query) throws CustomerException {
        if (query == null || query.isBlank() || query.isEmpty()) {
            throw new CustomerException("The query cannot be null, blank, or empty!");
        } else if (query.contains(",")) {
            throw new CustomerException("The query cannot contain any commas!");
        }
        ArrayList<String> productsFound = new ArrayList<>();
        for (String product : db.getDatabaseContents("products.csv")) {
            String[] productEntry = product.split(",");
            productEntry = Arrays.copyOfRange(productEntry, 3, productEntry.length);
            String queryLine = String.join(",", productEntry);
            if (queryLine.toLowerCase().contains(query.toLowerCase())) {
                productsFound.add(product);
            }
        }
        if (productsFound.isEmpty()) {
            throw new CustomerException("There are no products that match your query. Please try again!");
        } else {
            return formatProducts(productsFound);
        }

    }
}