import java.util.ArrayList;

public class ClearDatabaseData {
    private Database database = new Database();
    public static void main(String[] args) {
        new ClearDatabaseData().clearData();
    }

    public void clearData() {
    // Users
    ArrayList<String> testUsers = new ArrayList<>();
    database.updateDatabaseContents("users.csv", testUsers);

    // Stores
    ArrayList<String> testStores = new ArrayList<>();
    database.updateDatabaseContents("stores.csv", testStores);

    // Products
    ArrayList<String> testProducts = new ArrayList<>();
    database.updateDatabaseContents("products.csv", testProducts);

    // Purchase Histories
    ArrayList<String> testPurchases = new ArrayList<>();
    database.updateDatabaseContents("purchaseHistories.csv", testPurchases);

    // Shopping Carts
    ArrayList<String> testShoppingcarts = new ArrayList<>();
    database.updateDatabaseContents("shoppingCarts.csv", testShoppingcarts);
}
}
