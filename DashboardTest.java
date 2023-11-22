import java.util.ArrayList;

/**
 * Project 4 - TestStoreManagement.java
 * 
 * Class that handles all testing related to the functionalities implemented in
 * the Dashboard class.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version November 21, 2023
 */
public class DashboardTest {
    public static void main(String[] args) {
        CreateDatabaseData data = new CreateDatabaseData();
        data.createData();

        Dashboard dashboard = new Dashboard();

        testSellerGetCustomersDashboard(dashboard);
        testSellerGetProductsDashboard(dashboard);

        testCustomerGetStoresDashboard(dashboard);
        testCustomerGetPersonalPurchasesDashboard(dashboard);

    }

    public static void printResults(ArrayList<String> output) {
        for (String line : output) {
            System.out.println(line);
        }
        if (output.size() == 0) {
            System.out.println("output was empty");
            // Should never get here.
        }
    }

    // Uses users.csv and purchasehistories.csv
    public static void testSellerGetCustomersDashboard(Dashboard dashboard) {
        System.out.println("Testing SellerGetCustomersDashboard\n _____________");

        // System.out.println("Alphabetical Descending:");
        // printResults(Dashboard.sellerGetCustomersDashboard(0, false));
        ArrayList<String> test1 = new ArrayList<>();
        test1.add("apple@gmail.com,0.00,0.00");
        test1.add("banana@gmail.com,510.00,250250.00");
        test1.add("cherry@gmail.com,12.00,3501.00");
        if (!test1.equals(dashboard.sellerGetCustomersDashboard(0, false))) {
            System.out.println("Test 1 failed");
            return;
        }

        // System.out.println("Quantity Ascending:");
        // printResults(Dashboard.sellerGetCustomersDashboard(1, true));
        ArrayList<String> test2 = new ArrayList<>();
        test2.add("apple@gmail.com,0.00,0.00");
        test2.add("cherry@gmail.com,12.00,3501.00");
        test2.add("banana@gmail.com,510.00,250250.00");
        if (!test2.equals(dashboard.sellerGetCustomersDashboard(1, true))) {
            System.out.println("Test 2 failed");
            return;
        }

        // System.out.println("Spending Descending:");
        // printResults(Dashboard.sellerGetCustomersDashboard(2, false));
        ArrayList<String> test3 = new ArrayList<>();
        test3.add("banana@gmail.com,510.00,250250.00");
        test3.add("cherry@gmail.com,12.00,3501.00");
        test3.add("apple@gmail.com,0.00,0.00");
        if (!test3.equals(dashboard.sellerGetCustomersDashboard(2, false))) {
            System.out.println("Test 3 failed");
            return;
        }
        System.out.println("sellerGetCustomersDashboard() Testing completed without errors!");

    }

    public static void testSellerGetProductsDashboard(Dashboard dashboard) {
        System.out.println("Testing SellerGetProductsDashboard\n _____________");

        // System.out.println("Alphabetical Descending:");
        // printResults(Dashboard.sellerGetProductsDashboard(0, false));
        ArrayList<String> test1 = new ArrayList<>();
        test1.add("myProduct,10.00,2500.00");
        test1.add("mySecondProduct,1.00,1000.00");
        test1.add("otherProduct,501.00,250001.00");
        test1.add("productamondo,10.00,250.00");
        if (!test1.equals(dashboard.sellerGetProductsDashboard(0, false))) {
            System.out.println("Test 1 failed");
            return;
        }
        // System.out.println("Quantity Ascending:");
        // printResults(Dashboard.sellerGetProductsDashboard(1, true));
        ArrayList<String> test2 = new ArrayList<>();
        test2.add("mySecondProduct,1.00,1000.00");
        test2.add("myProduct,10.00,2500.00");
        test2.add("productamondo,10.00,250.00");
        test2.add("otherProduct,501.00,250001.00");
        if (!test2.equals(dashboard.sellerGetProductsDashboard(1, true))) {
            System.out.println("Test 2 failed");
            return;
        }
        // System.out.println("Spending Ascending:");
        // printResults(Dashboard.sellerGetProductsDashboard(2, true));
        ArrayList<String> test3 = new ArrayList<>();
        test3.add("productamondo,10.00,250.00");
        test3.add("mySecondProduct,1.00,1000.00");
        test3.add("myProduct,10.00,2500.00");
        test3.add("otherProduct,501.00,250001.00");

        if (!test3.equals(dashboard.sellerGetProductsDashboard(2, true))) {
            System.out.println("Test 3 failed");
            return;
        }
        // System.out.println("Spending Descending:");
        // printResults(Dashboard.sellerGetProductsDashboard(2, false));
        ArrayList<String> test4 = new ArrayList<>();
        test4.add("otherProduct,501.00,250001.00");
        test4.add("myProduct,10.00,2500.00");
        test4.add("mySecondProduct,1.00,1000.00");
        test4.add("productamondo,10.00,250.00");
        if (!test4.equals(dashboard.sellerGetProductsDashboard(2, false))) {
            System.out.println("Test 4 failed");
            return;
        }

        System.out.println("sellerGetProductsDashboard() Testing completed without errors!");

    }

    public static void testCustomerGetStoresDashboard(Dashboard dashboard) {
        System.out.println("Testing CustomerGetStoresDashboard\n _____________");

        // System.out.println("Alphabetical Descending:");
        // printResults(Dashboard.customerGetStoresDashboard(0, false));
        ArrayList<String> test1 = new ArrayList<>();
        test1.add("myStore,11.00,3500.00");
        test1.add("otherStore,511.00,250251.00");
        test1.add("thirdStore,0.00,0.00");
        if (!test1.equals(dashboard.customerGetStoresDashboard(0, false))) {
            System.out.println("Test 1 failed");
            return;
        }
        // System.out.println("Quantity Ascending:");
        // printResults(Dashboard.customerGetStoresDashboard(1, true));
        ArrayList<String> test2 = new ArrayList<>();
        test2.add("thirdStore,0.00,0.00");
        test2.add("myStore,11.00,3500.00");
        test2.add("otherStore,511.00,250251.00");
        if (!test2.equals(dashboard.customerGetStoresDashboard(1, true))) {
            System.out.println("Test 2 failed");
            return;
        }
        // System.out.println("Spending Ascending:");
        // printResults(Dashboard.customerGetStoresDashboard(2, true));
        ArrayList<String> test3 = new ArrayList<>();
        test3.add("thirdStore,0.00,0.00");
        test3.add("myStore,11.00,3500.00");
        test3.add("otherStore,511.00,250251.00");
        if (!test3.equals(dashboard.customerGetStoresDashboard(2, true))) {
            System.out.println("Test 3 failed");
            return;
        }

        System.out.println("customerGetStoresDashboard() Testing completed without errors!");

    }

    public static void testCustomerGetPersonalPurchasesDashboard(Dashboard dashboard) {
        System.out.println("Testing CustomerGetPersonalPurchasesDashboard\n _____________");

        // System.out.println("Alphabetical Ascending for customer C100001:");
        // printResults(Dashboard.customerGetPersonalPurchasesDashboard(0, true,
        // "C100001"));
        ArrayList<String> test1 = new ArrayList<>();
        test1.add("thirdStore,0.00,0.00");
        test1.add("otherStore,1.00,1.00");
        test1.add("myStore,11.00,3500.00");
        if (!test1.equals(dashboard.customerGetPersonalPurchasesDashboard(0, true, "C100001"))) {
            System.out.println("Test 1 failed");
            return;
        }
        // System.out.println("Quantity Descending for customer C100002:");
        // printResults(Dashboard.customerGetPersonalPurchasesDashboard(1, false,
        // "C100002"));
        ArrayList<String> test2 = new ArrayList<>();
        test2.add("otherStore,510.00,250250.00");
        test2.add("thirdStore,0.00,0.00");
        test2.add("myStore,0.00,0.00");
        if (!test2.equals(dashboard.customerGetPersonalPurchasesDashboard(1, false, "C100002"))) {
            System.out.println("Test 2 failed");
            return;
        }
        // System.out.println("Spending Descending for customer C100003:");
        // printResults(Dashboard.customerGetPersonalPurchasesDashboard(2, false,
        // "C100003"));
        ArrayList<String> test3 = new ArrayList<>();
        test3.add("thirdStore,0.00,0.00");
        test3.add("otherStore,0.00,0.00");
        test3.add("myStore,0.00,0.00");
        if (!test3.equals(dashboard.customerGetPersonalPurchasesDashboard(2, false, "C100003"))) {
            System.out.println("Test 3 failed");
            return;
        }

        System.out.println("customerGetStoresDashboard() Testing completed without errors!");
    }
}