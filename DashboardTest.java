
import java.util.ArrayList;

public class DashboardTest {
    
    public static void main(String[] args) {
        CreateDatabaseData.createData();

        testSellerGetCustomersDashboard();
        //testSellerGetProductsDashboard();
        
        //testCustomerGetStoresDashboard();
        //testCustomerGetPersonalPurchasesDashboard();

    }

    private static void printResults(ArrayList<String> output) {
        for (String line : output) {
            System.out.println(line);
        }
        if (output.size() == 0) {
            System.out.println("output was empty");
            //Should never get here.
        }
    }

    // Uses users.csv and purchasehistories.csv
    private static void testSellerGetCustomersDashboard() {
        //System.out.println("Testing SellerGetCustomersDashboard\n _____________");

        //System.out.println("Alphabetical Descending:");
        //printResults(Dashboard.sellerGetCustomersDashboard(0, false));
        ArrayList<String> test1 = new ArrayList<>();
        test1.add("apple@gmail.com,0.00,0.00");
        test1.add("banana@gmail.com,510.00,250250.00");
        test1.add("cherry@gmail.com,12.00,3501.00");
        if(!test1.equals(Dashboard.sellerGetCustomersDashboard(0, false))){
            System.out.println("Test 1 failed");
        }

        ArrayList<String> test2 = new ArrayList<>();
        test2.add("apple@gmail.com,0.00,0.00");
        test2.add("banana@gmail.com,510.00,250250.00");
        test2.add("cherry@gmail.com,12.00,3501.00");
        if(!test2.equals(Dashboard.sellerGetCustomersDashboard(0, false))){
            System.out.println("Test 2 failed");
        }

        System.out.println("Quantity Ascending:");
        printResults(Dashboard.sellerGetCustomersDashboard(1, true));

        //System.out.println("Spending Descending:");
        //printResults(Dashboard.sellerGetCustomersDashboard(2, false));

        System.out.println("Testing completed without errors!");


    }

    private static void testSellerGetProductsDashboard() {
        System.out.println("Testing SellerGetProductsDashboard\n _____________");

        System.out.println("Alphabetical Descending:");
        printResults(Dashboard.sellerGetProductsDashboard(0, false));

        System.out.println("Quantity Ascending:");
        printResults(Dashboard.sellerGetProductsDashboard(1, true));

        System.out.println("Spending Ascending:");
        printResults(Dashboard.sellerGetProductsDashboard(2, true));

        System.out.println("Spending Descending:");
        printResults(Dashboard.sellerGetProductsDashboard(2, false));

    }

    private static void testCustomerGetStoresDashboard() {
        System.out.println("Testing CustomerGetStoresDashboard\n _____________");

        System.out.println("Alphabetical Descending:");
        printResults(Dashboard.customerGetStoresDashboard(0, false));

        System.out.println("Quantity Ascending:");
        printResults(Dashboard.customerGetStoresDashboard(1, true));

        System.out.println("Spending Ascending:");
        printResults(Dashboard.customerGetStoresDashboard(2, true));
    }

    private static void testCustomerGetPersonalPurchasesDashboard() {
        System.out.println("Testing CustomerGetPersonalPurchasesDashboard\n _____________");

        System.out.println("Alphabetical Ascending for customer C100001:");
        printResults(Dashboard.customerGetPersonalPurchasesDashboard(0, true, "C100001"));

        System.out.println("Quantity Descending for customer C100002:");
        printResults(Dashboard.customerGetPersonalPurchasesDashboard(1, false, "C100002"));

        System.out.println("Spending Descending for customer C100003:");
        printResults(Dashboard.customerGetPersonalPurchasesDashboard(2, false, "C100003"));

    }

}
