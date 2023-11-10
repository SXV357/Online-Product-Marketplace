import java.util.ArrayList;

public class DashboardTest {
    /*
    private static final String usersDatabaseHeaders = "ID,Email,Password,Role";
    private static final String storesDatabaseHeaders = "Store ID,Seller ID,Store Name,Number of Products";
    private static final String productsDatabaseHeaders = "Seller ID,Store ID,Product ID,Store Name,Product Name,Available Quantity,Price,Description";
    private static final String purchaseHistoryDatabaseHeaders = "Customer ID,Seller ID,Store ID,Product ID,Store Name,Product Name,Purchase Quantity,Price";
    private static final String shoppingCartDatabaseHeaders = "Customer ID,Seller ID,Store ID,Product ID,Store Name,Product Name,Purchase Quantity,Price";
    */
    public static void main(String[] args) {
        CreateDatabaseData.createData();


        //testSellerGetCustomersDashboard();
        //testSellerGetProductsDashboard();
        testCustomerGetStoresDashboard();
        //testCustomerGetPersonalPurchasesDashboard();
        

    }

    private static void printResults(ArrayList<String> output){
        for(String line:output) {
            System.out.println(line);
        }
        if(output.size() == 0){
            System.out.println("output was empty");
        }
    }
    //Uses users.csv and purchasehistories.csv
    private static void testSellerGetCustomersDashboard(){
        System.out.println("Testing SellerGetCustomersDashboard\n _____________");
        System.out.println("Alphabetical Ascending:");
        printResults(Dashboard.sellerGetCustomersDashboard(0,false));

        System.out.println("Quantity Descending:");
        printResults(Dashboard.sellerGetCustomersDashboard(1,true));

        System.out.println("Spending Descending:");
        printResults(Dashboard.sellerGetCustomersDashboard(2,true));
    }

    private static void testSellerGetProductsDashboard(){
        System.out.println("Testing SellerGetProductsDashboard\n _____________");

        System.out.println("Alphabetical Ascending:");
        printResults(Dashboard.sellerGetProductsDashboard(0,false));

        System.out.println("Quantity Descending:");
        printResults(Dashboard.sellerGetProductsDashboard(1,true));

        System.out.println("Spending Descending:");
        printResults(Dashboard.sellerGetProductsDashboard(2,true));

    }

    private static void testCustomerGetStoresDashboard(){
        System.out.println("Testing CustomerGetStoresDashboard\n _____________");

        System.out.println("Alphabetical Ascending:");
        printResults(Dashboard.customerGetStoresDashboard(0, false));

        System.out.println("Quantity Descending:");
        printResults(Dashboard.customerGetStoresDashboard(1, true));

        System.out.println("Spending Descending:");
        printResults(Dashboard.customerGetStoresDashboard(2,true));
    }

    private static void testCustomerGetPersonalPurchasesDashboard(){
        System.out.println("Testing CustomerGetPersonalPurchasesDashboard\n _____________");

        System.out.println("Alphabetical Ascending for customer 1:");
        printResults(Dashboard.customerGetPersonalPurchasesDashboard(0, false, "C100001"));

        System.out.println("Quantity Descending for customer 2:");
        printResults(Dashboard.customerGetPersonalPurchasesDashboard(0, false, "C100002"));

        System.out.println("Spending Descending for customer 3:");
        printResults(Dashboard.customerGetPersonalPurchasesDashboard(0, false, "C100003"));

    }

    
}