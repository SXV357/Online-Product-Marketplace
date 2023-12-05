import java.util.ArrayList;
import java.util.Collections;

/**
 * Project 5 - Dashboard.java
 * 
 * Class that encompasses functionality for customers to view store and seller
 * information, and for sellers to view statistics for each of their stores
 * Uses four methods, two for sellers and two for customers.
 * To create a full dashboard, call the two customer/seller methods, and display
 * the resulting data. Each Dashboard will return an arraylist with three
 * collumns: Name, Total
 * quantity, and Total revenue. Sort index is which collumn to sort by.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * 
 * @version December 5, 2023
 */
public class Dashboard {

    private Database database = new Database();

    // Seller's method to view all customers
    // Return line format: email,totalQuantity,totalSpent
    public ArrayList<String> sellerGetCustomersDashboard(int sortIndex, boolean sortAscending) throws SellerException {
        // Get all customer Users
        ArrayList<String> allCustomers = database.getMatchedEntries("users.csv", 3, "CUSTOMER");

        if (allCustomers.isEmpty()) {
            throw new SellerException("There are no existent customer accounts!");
        }

        // return array
        ArrayList<String> customerStatisticsStrings = new ArrayList<>();

        // for each customer, find their purchases
        for (String customer : allCustomers) {
            String[] customerDataList = customer.split(",");
            String customerID = customerDataList[0];

            // get all purchases this customer has made
            ArrayList<String> customerPurchases = database.getMatchedEntries("purchaseHistories.csv", 0, customerID);

            // Accumulate all this customer's purchase data into total purchases and total
            // spending
            double totalQuantity = 0;
            double totalSpent = 0;
            for (String purchase : customerPurchases) {
                String[] splitPurchase = purchase.split(",");

                double purchaseQuantity = Double.parseDouble(splitPurchase[6]);
                totalQuantity += purchaseQuantity;
                // price times quantity
                double purchaseCost = Double.parseDouble(splitPurchase[7]) * purchaseQuantity;
                totalSpent += purchaseCost;
            }

            String customerEmail = customerDataList[1];
            // Add customer's stats to the list
            // Format: email, totalQuantity, totalSpent
            String statsString = String.format("%s,%.2f,%.2f", customerEmail, totalQuantity, totalSpent);
            customerStatisticsStrings.add(statsString);
        }

        // Sorts the processed data
        // See here for allowed values of sortType
        if (customerStatisticsStrings.isEmpty()) {
            throw new SellerException("No customers have purchased products from any of your stores yet!");
        } else {
            return sortResults(sortIndex, sortAscending, customerStatisticsStrings);
        }
    }

    // Seller's method to view all products
    // Return line format: productName,TotalSales,Total Revenue
    public ArrayList<String> sellerGetProductsDashboard(int sortIndex, boolean sortAscending) throws SellerException {
        // Get all products:Seller ID,Store ID,Product ID,Store Name,Product
        // Name,Available Quantity,Price,Description
        ArrayList<String> allProducts = database.getDatabaseContents("products.csv");

        if (allProducts.isEmpty()) {
            throw new SellerException("No products have been added to any stores yet!");
        }

        // return array
        ArrayList<String> productStatisticsStrings = new ArrayList<>();

        // for each product, accumulate the total number of sales
        for (String product : allProducts) {
            String[] productDataList = product.split(",");
            // product id is the 3rd collumn of the products csv
            String productID = productDataList[2];

            // get all purchases this customer has made, productID on 3rd index of
            // purchasHistories
            // Customer ID,Seller ID,Store ID,Product ID,Store Name,Product Name,Purchase
            // Quantity,Price
            ArrayList<String> productPurchases = database.getMatchedEntries("purchaseHistories.csv", 3, productID);

            // Accumulate all this customer's purchase data into total purchases and total
            // spending

            double totalSold = 0;
            double totalRevenue = 0;
            for (String purchase : productPurchases) {
                String[] splitPurchase = purchase.split(",");

                double purchaseQuantity = Double.parseDouble(splitPurchase[6]);
                totalSold += purchaseQuantity;
                // price times quantity
                double saleRevenue = Double.parseDouble(splitPurchase[7]) * purchaseQuantity;
                totalRevenue += saleRevenue;
            }
            // Product's name is stored in the 4th collumn
            String productName = productDataList[4];
            // Add customer's stats to the list
            // Format: email, totalQuantity, totalSpent
            String statsString = String.format("%s,%.2f,%.2f", productName, totalSold, totalRevenue);
            productStatisticsStrings.add(statsString);
        }
        if (productStatisticsStrings.isEmpty()) {
            throw new SellerException("No products have been purchased from any of your stores yet!");
        } else {
            return sortResults(sortIndex, sortAscending, productStatisticsStrings);
        }
    }

    // Customer's method to view all stores and their products sold
    // Return line format: storeName, Products Sold, Total Revenue
    public ArrayList<String> customerGetStoresDashboard(int sortIndex, boolean sortAscending) throws CustomerException {
        // Get all stores:Store ID,Seller ID,Store Name,Number of Products
        ArrayList<String> allStores = database.getDatabaseContents("stores.csv");

        if (allStores.isEmpty()) {
            throw new CustomerException("No stores have been created yet!");
        }

        // return array
        ArrayList<String> storeStatisticsStrings = new ArrayList<>();

        // for each product, accumulate the total number of sales
        for (String store : allStores) {
            String[] storeDataList = store.split(",");
            // store id is the 1st collumn of the stores csv
            String storeID = storeDataList[0];

            // get all purchases made at this store
            // Customer ID,Seller ID,Store ID,Product ID,Store Name,Product Name,Purchase
            // Quantity,Price
            ArrayList<String> storeSales = database.getMatchedEntries("purchaseHistories.csv", 2, storeID);

            // Accumulate all this customer's purchase data into total purchases and total
            // spending

            double totalProductsSold = 0;
            double totalRevenue = 0;
            for (String purchase : storeSales) {
                String[] splitPurchase = purchase.split(",");

                double purchaseQuantity = Double.parseDouble(splitPurchase[6]);
                totalProductsSold += purchaseQuantity;
                // price times quantity
                double saleRevenue = Double.parseDouble(splitPurchase[7]) * purchaseQuantity;
                totalRevenue += saleRevenue;
            }
            // Store name is stored in the 3rd collumn
            String storeName = storeDataList[2];
            // Add customer's stats to the list
            // Format: email, totalQuantity, totalSpent
            String statsString = String.format("%s,%.2f,%.2f", storeName, totalProductsSold, totalRevenue);
            storeStatisticsStrings.add(statsString);
        }
        if (storeStatisticsStrings.isEmpty()) {
            throw new CustomerException("No products have been purchased from any of the stores yet!");
        } else {
            return sortResults(sortIndex, sortAscending, storeStatisticsStrings);
        }
    }

    // Personal Dashboard requires CustomerID
    // Returns all stores including summed information about purchases made by the
    // customer with customer ID at that store
    // Return line format: storeName, Products Bought, Total Spent
    public ArrayList<String> customerGetPersonalPurchasesDashboard(int sortIndex, boolean sortAscending,
            String customerID) throws CustomerException {
        // Get all stores:Store ID,Seller ID,Store Name,Number of Products
        ArrayList<String> allStores = database.getDatabaseContents("stores.csv");

        if (allStores.isEmpty()) {
            throw new CustomerException("No stores have been created yet!");
        }

        // return array
        ArrayList<String> storeStatisticsStrings = new ArrayList<>();

        // for each product, accumulate the total number of sales
        for (String store : allStores) {
            String[] storeDataList = store.split(",");
            // store id is the 1st collumn of the stores csv
            String storeID = storeDataList[0];

            // get all purchases made at this store
            // Customer ID,Seller ID,Store ID,Product ID,Store Name,Product Name,Purchase
            // Quantity,Price
            ArrayList<String> storeSales = database.getMatchedEntries("purchaseHistories.csv", 2, storeID);

            // Accumulate all this customer's purchase data into total purchases and total
            // spending

            double totalProductsBought = 0;
            double totalSpent = 0;
            for (String purchase : storeSales) {
                String[] splitPurchase = purchase.split(",");

                // If purchase wasn't made by this customer, continue
                if (!splitPurchase[0].equals(customerID)) {
                    continue;
                }

                double purchaseQuantity = Double.parseDouble(splitPurchase[6]);
                totalProductsBought += purchaseQuantity;
                // price times quantity
                double saleRevenue = Double.parseDouble(splitPurchase[7]) * purchaseQuantity;
                totalSpent += saleRevenue;
            }
            // Store name is stored in the 3th collumn
            String storeName = storeDataList[2];
            // Add customer's stats to the list
            // Format: email, totalQuantity, totalSpent
            String statsString = String.format("%s,%.2f,%.2f", storeName, totalProductsBought, totalSpent);
            storeStatisticsStrings.add(statsString);
        }
        if (storeStatisticsStrings.isEmpty()) {
            throw new CustomerException("You haven\'t purchased items from any of the stores yet!");
        } else {
             return sortResults(sortIndex, sortAscending, storeStatisticsStrings);
        }
    }

    // Assuming strings are in format: String, Double, Double
    public ArrayList<String> sortResults(int sortIndex, boolean sortAscending, ArrayList<String> arrayList) {
        switch (sortIndex) {
            case 0:
                Collections.sort(arrayList,
                        (a, b) -> a.split(",")[0].compareTo(b.split(",")[0]));

                // Alphabetically sorting works reverse to numerical sorting
                sortAscending = !sortAscending;
                break;
            case 1, 2:
                // Using a lambda function to compare only the price collumn after casting to
                // double
                Collections.sort(arrayList,
                        (a, b) -> Double.compare(Double.parseDouble(a.split(",")[sortIndex]),
                                Double.parseDouble(b.split(",")[sortIndex])));

                break;

        }

        if (!sortAscending)
            Collections.reverse(arrayList);

        return arrayList;
    }

}