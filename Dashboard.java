import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Project 4 - Dashboard.java
 * 
 * Class that encompasses functionality for customers to view store and seller information, and for sellers to view statistics for each of their stores
 * Uses four methods, two for sellers and two for customers.
 * To create a full dashboard, call the two customer/seller methods, and display the resulting data. 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 2, 2023
 */
public class Dashboard {
    


    //Can sort product purchases by price and quantity
    //Returns the purchase history relevant to a single store
    //Each line is a purchase
    public String[] getSellerCustomersDashboard(Seller seller, Store store, String sortType, boolean sortAscending){
        
        
        //Format: "Seller ID,Store Name,Number of Products"
        Database database = new Database();

        //TODO: ensure this is how users is formatted
        ArrayList<String> allCustomers = database.getMatchedEntries("users.csv", 3, "Customer");
        ArrayList<ArrayList<String>> customerActivity = new ArrayList<>();

        for (String customer:allCustomers){
            String customerID = customer.split(",")[0];
            //get all purchases this customer has made
            ArrayList<String> customerPurchases = database.getMatchedEntries("purchaseHistories.csv", 0, customerID);
            customerActivity.add(customerPurchases);

        }

        ArrayList<String[]> splitCustomerData = new ArrayList<>();
        for (String line: allCustomers){
            splitCustomerData.add(line.split(","));
        }

        switch(sortType){
            case "Price":
                //Using a lambda function to compare only the price collumn after casting to double
                int priceIndex = 7; 
                Collections.sort(splitCustomerData, (a, b) -> Double.compare(Double.parseDouble(a[priceIndex]),Double.parseDouble(b[priceIndex])));
                if (!sortAscending) Collections.reverse(splitCustomerData);
                
                
                break;

            case "Alphabetical":
                break;

            case "Quantity":


        }

        return new String[0];
    }

    public String[] getSellerProductsDashboard(Customer customer, Store store, String sortType, boolean ascending){
        return new String[0];

    }

    public String[] getCustomerPersonalDashboard(Customer customer, Store store, String sortType, boolean ascending){
        return new String[0];

    }

    public String[] getCustomerOverallDashboard(Customer customer, Store store, String sortType, boolean ascending){
        return new String[0];

    }

}
