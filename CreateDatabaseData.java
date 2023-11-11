import java.util.ArrayList;

public class CreateDatabaseData {

    public static void main(String[] args) {
        clearData();
        createData();
    }

    public static void clearData() {
        Database database = new Database();
        // Users
        {
            ArrayList<String> testUsers = new ArrayList<>();
            database.updateDatabaseContents("users.csv", testUsers);
        }

        // Stores
        {
            ArrayList<String> testStores = new ArrayList<>();
            database.updateDatabaseContents("stores.csv", testStores);
        }

        // Products
        {
            ArrayList<String> testProducts = new ArrayList<>();
            database.updateDatabaseContents("products.csv", testProducts);
        }

        // Purchase histories
        {
            ArrayList<String> testPurchases = new ArrayList<>();
            database.updateDatabaseContents("purchaseHistories.csv", testPurchases);
        }
        // Shopping carts
        {
            ArrayList<String> testShoppingcarts = new ArrayList<>();
            database.updateDatabaseContents("shoppingCarts.csv", testShoppingcarts);
        }
    }

    public static void createData() {
        // Initialize databases for testing purchases
        Database database = new Database();
        // Users
        {
            ArrayList<String> testUsers = new ArrayList<>();
            testUsers.add("C100001,cherry@gmail.com,12345,CUSTOMER");
            testUsers.add("S100002,balogne@gmail.com,12315436,SELLER");
            database.updateDatabaseContents("users.csv", testUsers);
        }

        // Stores
        {
            ArrayList<String> testStores = new ArrayList<>();
            testStores.add("ST100001,S100002,newStore1,1");
            testStores.add("ST100002,S100002,newStore2,1");
            database.updateDatabaseContents("stores.csv", testStores);
        }

        // Products
        {
            ArrayList<String> testProducts = new ArrayList<>();
            testProducts.add("S100002,ST100001,PR100001,newStore1,myProduct,1000,25,Its a product!");
            testProducts.add("S100002,ST100002,PR100002,newStore2,myProduct2,1000,25,Its a new product!");
            database.updateDatabaseContents("products.csv", testProducts);
        }
    }
}