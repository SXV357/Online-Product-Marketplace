import javax.xml.crypto.Data;

import java.io.IOException;
import java.lang.reflect.Array;
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
    public Customer(String userID, String email, String password) throws IOException {
        super(Integer.parseInt(userID), email, password, UserRole.CUSTOMER);
        shoppingCart = db.extractProdShopCartHistoryEntries("shoppingCarts", userID);
        purchasehistory = db.extractProdShopCartHistoryEntries("shoppingHistories", userID);

    }


    public String arrToString(ArrayList<String> array) {
        StringBuilder output = new StringBuilder();
        String[] splitArr;
        for (int i = 0; i < array.size(); i++) {
            splitArr = array.get(i).split(",");
            output.append(i).append(". ").append(array.get(i));
            for (int j = 0; j < splitArr.length; j++) {
                if (i == 0) {
                    output.append(splitArr[j]).append("  ");
                } else {
                    output.append("    ").append(splitArr[j]).append("    ");
                }
            }
            output.append(System.getProperty("line.separator"));
        }
        return output.toString();
    }

    public String getShoppingHistory() {
        return arrToString(shoppingCart);
    }

    public void removeFromCart(int index) throws IOException {
        shoppingCart.remove(index);
        //db.updateProdShopCartHistoryEntries("shoppingCarts", userID, shoppingCart);
    }

    public void addToCart(int sellerID) {
       // db.updateShoppingCart("shoppingCarts", userID, shoppingCart.add());
    }

    public void purchaseItems() throws IOException {
        db.updateProdShopCartHistoryEntries("purchaseHistories", userID, shoppingCart);
        shoppingCart.clear();
        db.updateProdShopCartHistoryEntries("shoppingCarts", userID, shoppingCart);
    }

    public String getStoresByPurchased() {
        ArrayList<String> storeSorted = purchasehistory;
        ArrayList<String> sorted = new ArrayList<>();
        int n = sorted.size();
        String temp = "";
        int searchIndex = 4;
        //sorted = db.extractProdShopCartHistoryEntries("", temp);
 
        //Bubble Sort
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < (n - i); j++) {
                if (Integer.parseInt(sorted.get(j - 1).split(",")[searchIndex])
                        > Integer.parseInt(sorted.get(j).split(",")[searchIndex])) {
                    temp = sorted.get(j - 1);
                    sorted.set(j - 1, sorted.get(j));
                    sorted.set(j, temp);
                }
            }
        }
        return arrToString(sorted);
    }

    public String getHistory() throws IOException {
        return arrToString(db.extractProdShopCartHistoryEntries("shoppingCarts", userID));
    }

    public String searchProducts(String query) {
        StringBuilder productsFound = new StringBuilder();
        for (String product : new String[2]) {
            if (product.contains(query)) {
                productsFound.append(product).append(System.getProperty("line.separator"));
            }
        }
        return productsFound.toString();
    }
}