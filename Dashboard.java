import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Project 4 - Dashboard.java
 * 
 * Class that encompasses functionality for customers to view store and seller information, and for sellers to view statistics for each of their stores
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 2, 2023
 */
public class Dashboard {

    //Can sort product purchases by price and quantity
    //Returns the purchase history relevant to a single store
    //Each line is a purchase
    public String[] getSellerDashboard(Seller seller, Store store, String sortType, boolean sortAscending){
        
        
        //Format: "Seller ID,Store Name,Number of Products"
        Database database = new Database();

        ArrayList<String> storeData = database.getDatabaseContents("stores.csv");

        ArrayList<String[]> splitStoreData = new ArrayList<>();
        for (String line: storeData){
            splitStoreData.add(line.split(","));
        }

        switch(sortType){
            case "Price":
                //Using a lambda function to compare only the price collumn after casting to double
                int priceIndex = 4; 
                Collections.sort(splitStoreData, (a, b) -> Double.compare(Double.parseDouble(a[priceIndex]),Double.parseDouble(b[priceIndex])));

                if (!sortAscending) {
                    Collections.reverse(splitStoreData);
                }
                break;

            case "Alphabetical":
                break;

            case "Quantity":


        }

        return new String[0];
    }

    public String[] getCustomerDashboard(Customer customer, Store store, String sortType, boolean ascending){
        return new String[0];

    }

}
