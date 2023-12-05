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
 * @version December 5, 2023
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
            throw new CustomerException("Invalid Index");
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
            throw new CustomerException("No Products available");
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
    public String formatProducts(ArrayList<String> productList) throws CustomerException{
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
            throw new CustomerException("Invalid Index");
        }

    }

    /**
     * Adds a given item from the cart
     *
     * @param productID the index of the product the add
     * @throws CustomerException
     */
    public void addToCart(int index, int quantity) throws CustomerException {
        try {
            ArrayList<String> products = db.getDatabaseContents("products.csv");
            String[] target = db.getMatchedEntries("products.csv", 2, products.get(index).split(",")[2]).get(0)
                    .split(",");
            if (quantity <= 0 || Integer.parseInt(target[5]) < quantity) {
                throw new CustomerException("Invalid Quantity");
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
        } catch (IndexOutOfBoundsException e) {
            throw new CustomerException("Invalid Index");
        }
    }

    /**
     * Purchases the items in the cart
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
            throw new CustomerException("Invalid Choice");
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
                output.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(output));
                String headers = db.getFileHeaders("purchaseHistories.csv");
                bw.write(headers + "\n");
                for (int i = 0; i < purchasehistory.size(); i++) {
                    bw.write(purchasehistory.get(i) + "\n");
                }
                bw.flush();
                bw.close();
            } else {
                throw new CustomerException("Purchase History is Empty");
            }
        } catch (IOException e) {
            throw new CustomerException("File Path DNE");
        }
    }

    /**
     * Searches for all products containing a given query
     *
     * @return Returns the products found
     * @throws CustomerException
     */
    public String searchProducts(String query) throws CustomerException {
        ArrayList<String> productsFound = new ArrayList<>();
        for (String product : db.getDatabaseContents("products.csv")) {
            if (product.contains(query)) {
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