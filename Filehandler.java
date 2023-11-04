/**
 * Project 4 - Filehandler.java
 *
 * Class handles all file read/write operations.
 * Other classes call this classes' methods to get csv file data as a String[][].
 *
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 *
 * @version November 2, 2023
 */
public class Filehandler {

    //all functions should use the final variables when calling Filehandler methods
    public static final String
    public static final String USERS = "users.csv";
    public static final String STORES = "stores.csv";
    public static final String PRODUCTS = "products.csv";
    public static final String PURCHASES = "purchases.csv";
    public static final String SHOPPINGCARTS = "shoppingcarts.csv";

    //returns a 2d array containing all the data contained in the csv under filepath
    public String[][] get(String filepath){
        //TODO: read from file, split line by line, return final array

    }

    //appends a single line to a csv
    public void add(String filepath, line){
        //TODO: read from file, split by line, return final array
    }


    //returns true if line sucessfuly modified, false if it didn't exist
    public boolean modify(String filepath, String id, String newLine){
        //TODO: read from file, check line by line for matching id
        //upon finding it, replace that line with newline
        //Must rewrite whole csv to remove that line (store whole csv in memory except that line, add newline to the end.)
    }

    //returns true if line sucessfuly removed, false if it didn't exist
    public boolean remove(String filepath, String removeLine){
        //TODO: read from file, check line by line for matching id
        //upon finding it, remove that line
        //rewrite whole csv without that line
    }

}