/**
 * Project 4 - Seller.java
 * 
 * Class to represent the permissions and details associated with a seller
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 2, 2023
 */

//relies on static database class to read and write from csv files.
public class Seller extends User{
    private Store[] stores;

    public void addStore(String storeName){
        //Store newStore = new Store(storeName);

        //database.add should add a new line to the appropriate database

        String storeLine = String.join(",", this.UserID,storeName);
        //database.add(database.STORE,storeLine);

    }

    public void modifyStore(String oldName, String newName){
        //Store newStore = new Store(storeName);
        //database.add(database.STORE, String oldName, String newName);
    }

    public String[] getStores(){
        //pull from stores database to see which stores are owned by this UserID
        ArrayList<String> ownedStores = new ArrayList<String>();
        String[][] allStores;
        //String[] allStores = database.get(database.STORES);
        //assuming ownerID is stored in the first index
        int idIndex = 0;
        for(String store:allStores){
            //String[] storeData = line.split(",");
            if (store[idIndex].equals(this.UserID)){
                //store is owned by this user
                ownedStores.add(line);
            }
        }

        return ownedStores.toArray(new String[0]);
    }

    //Assuming format of each purchase will be: SellerID, StoreID, BuyerID, ...
    public String[] getSales(){
        //getting store data is unecessary if
        //String[][] ownedStores = this.getStores();
        //String[] ownedStoreIDs = new String[ownedStores.length];

        //get all purchases, filter for sellerID and storeID
        String[][] purchases;
        //purchases = database.get(database.PURCHASES);

        int sellerIDIndex = 0;
        int storeIDIndex = 1;
        int buyerIDIndex = 2;
        ArrayList<ArrayList<String>> relevantPurchases = new  ArrayList<ArrayList<String>>();
        for (String purchase:purchases){
            if(purchase[sellerIDIndex].equals(this.UserID)){

            }
        }
    }



}
