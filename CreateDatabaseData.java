import java.util.ArrayList;

public class CreateDatabaseData {

    public static void createData(){
        Database database = new Database();

        //Initialize databases for testing purchases

            /*
     * private static final String usersDatabaseHeaders = "ID,Email,Password,Role";
     * private static final String storesDatabaseHeaders =
     * "Store ID,Seller ID,Store Name,Number of Products";
     * private static final String productsDatabaseHeaders =
     * "Seller ID,Store ID,Product ID,Store Name,Product Name,Available Quantity,Price,Description"
     * ;
     * private static final String purchaseHistoryDatabaseHeaders =
     * "Customer ID,Seller ID,Store ID,Product ID,Store Name,Product Name,Purchase Quantity,Price"
     * ;
     * private static final String shoppingCartDatabaseHeaders =
     * "Customer ID,Seller ID,Store ID,Product ID,Store Name,Product Name,Purchase Quantity,Price"
     * ;
     */
        //Users
        {
        ArrayList<String> testUsers = new ArrayList<>();
        testUsers.add("C100001,cherry@gmail.com,12345,Customer");
        testUsers.add("C100002,banana@gmail.com,1234567,Customer");
        testUsers.add("C100003,apple@gmail.com,765543,Customer");
        testUsers.add("S100001,apricot@gmail.com,12313,Seller");
        testUsers.add("S100002,balogne@gmail.com,12315436,Seller");
        database.updateDatabaseContents("users.csv", testUsers);
        }

        //Stores
        {
        ArrayList<String> testStores = new ArrayList<>();
        testStores.add("ST100001,S100001,myStore,2");
        testStores.add("ST100002,S100002,otherStore,1");
        testStores.add("ST100003,S100002,thirdStore,1");
        database.updateDatabaseContents("stores.csv", testStores);
        }

        //Products
        {
        ArrayList<String> testProducts = new ArrayList<>();
        testProducts.add("S100001,ST100001,PR100001,myStore,myProduct,1000,25,Its a product!");
        testProducts.add("S100001,ST100001,PR100002,myStore,mySecondProduct,1000,1000,Its a product!");
        testProducts.add("S100002,ST100002,PR100003,otherStore,otherProduct,1000,1,Its a product!");
        testProducts.add("S100002,ST100003,PR100004,otherStore,productamondo,1000,5,Its a product!");
        database.updateDatabaseContents("products.csv", testProducts);
        }


        //Purchases
        {
        ArrayList<String> testPurchases = new ArrayList<>();
        //Add purchases to test DB
        //customer 1 purchases
        testPurchases.add("C100001,S100001,ST100001,PR100001,myStore,myProduct,10,25");
        testPurchases.add("C100001,S100001,ST100001,PR100002,myStore,mySecondProduct,1,1000");
        testPurchases.add("C100001,S100002,ST100002,PR100003,otherStore,otherProduct,1,1");
        //customer 2 purchases
        testPurchases.add("C100002,S100002,ST100002,PR100003,otherStore,otherProduct,500,1");
        testPurchases.add("C100002,S100002,ST100002,PR100004,otherStore,productamondo,10,5");

        //customer 3 makes no purchases

        //Write purchaseHistories.csv
        database.updateDatabaseContents("purchaseHistories.csv", testPurchases);
        }
    }
}
