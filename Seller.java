/**
 * Project 4 - Seller.java
 * 
 * Class to represent the permissions and details associated with a seller
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 2, 2023
 */
public class Seller extends User{
    private Store[] stores;

    public void addStore(String storeName){
        //Store newStore = new Store(storeName);

        //database.add should add a new line to the appropriate database

        String storeLine = String.join(",", this.UserID,storeName);
        //database.add(STORE,storeLine);

    }

    public void modifyStore(String oldName, String newName){
        //Store newStore = new Store(storeName);
        //database.add(STORE, String oldName, String newName);
    }

    public Store[] getStores(){
        //pull from stores database to see which stores are owned by this UserID
        ArrayList<Stores>
        String[] allStores;
        //String[] allStores = database.get(STORES)
        //assuming ownerID is stored in the first index
        int idIndex = 0;
        for(String line:allStores){
            if
        }
    }



}
