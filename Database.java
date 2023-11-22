import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Project 5 - Database.java
 * 
 * Class that handles all database access and modification functionality related
 * to the application.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * 
 * @version November 21, 2023
 */
public class Database {
    private final String DATABASES_DIRECTORY = "databases/";
    private final String USERS_DATABASE_HEADERS = "ID,Email,Password,Role";
    private final String STORES_DATABASE_HEADERS = "Store ID,Seller ID,Store Name,Number of Products";
    private final String PRODUCTS_DATABASE_HEADERS = "Seller ID,Store ID,Product ID,Store Name,Product Name," +
            "Available Quantity,Price,Description";
    private final String PURCHASE_HISTORY_DATABASE_HEADERS = "Customer ID,Seller ID,Store ID,Product ID,Store "
            + "Name,Product Name,Purchase Quantity,Price";
    private final String SHOPPING_CART_DATABASE_HEADERS = "Customer ID,Seller ID,Store ID,Product ID,Store Name,"
            + "Product Name,Purchase Quantity,Price";
    private Object lock = new Object();

    /**
     * Takes in the name of the file as input and returns a string containing all
     * the headers associated with that file.
     *
     * @param fileName The name of the file that the user wants to update
     * @return The headers that are associated with that specific CSV file.
     */
    public String getFileHeaders(String fileName) {
        String fileHeaders = "";
        switch (fileName) {
            case "users.csv" -> fileHeaders = USERS_DATABASE_HEADERS;
            case "stores.csv" -> fileHeaders = STORES_DATABASE_HEADERS;
            case "products.csv" -> fileHeaders = PRODUCTS_DATABASE_HEADERS;
            case "shoppingCarts.csv" -> fileHeaders = SHOPPING_CART_DATABASE_HEADERS;
            case "purchaseHistories.csv" -> fileHeaders = PURCHASE_HISTORY_DATABASE_HEADERS;
        }
        return fileHeaders;
    }

    /**
     * Takes in the name of the file and a column index and returns whether that
     * index is within bounds of how many ever columns exist in the given file
     *
     * @param fileName The file using which the validity of the index will be
     *                 determined
     * @param index    The index of the specified column
     * @return If the column index is within bounds of the total number of columns
     *         in the file
     */
    public boolean checkColumnBounds(String fileName, int index) {
        String[] columns = getFileHeaders(fileName).split(",");
        return ((index >= 0) && (index <= columns.length - 1));
    }

    /**
     * Takes in an ID and a file name and checks whether that ID is already
     * associated with an entry
     *
     * @param idToCheck The ID to compare with the entries in the file
     * @param fileName  The file to check for the existence of the ID
     * @return The existence of the ID in the specified file
     */
    public boolean checkIDMatch(int idToCheck, String fileName) {
        synchronized (lock) {
            ArrayList<String> correspondingEntries = new ArrayList<>();
            int comparisonIndex = 0;
            int startIDSubstring = 0;
            switch (fileName) {
                case "users.csv":
                    correspondingEntries = getDatabaseContents("users.csv");
                    comparisonIndex = 0;
                    startIDSubstring = 1;
                    break;
                case "stores.csv":
                    correspondingEntries = getDatabaseContents("stores.csv");
                    comparisonIndex = 0;
                    startIDSubstring = 2;
                    break;
                case "products.csv":
                    correspondingEntries = getDatabaseContents("products.csv");
                    comparisonIndex = 2;
                    startIDSubstring = 2;
                    break;
            }
            if (correspondingEntries.isEmpty()) {
                return false;
            } else {
                for (int i = 0; i < correspondingEntries.size(); i++) {
                    String[] userRepresentation = correspondingEntries.get(i).split(",");
                    String matchedID = userRepresentation[comparisonIndex].substring(startIDSubstring);
                    if (idToCheck == Integer.parseInt(matchedID)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * Takes in the user's email and password and retrieves a match for the
     * credentials in the users.csv database. Used for when the user is logging into
     * the application. A null value signifies that no user with the given
     * credentials was found in the database.
     *
     * @param email    The user's email to compare with an existing entry in the
     *                 users.csv database
     * @param password The user's password to compare with an existing entry in the
     *                 users.csv database
     * @return A comma-separated string containing the matched user's information in
     *         the users.csv database
     */
    public String retrieveUserMatchForLogin(String email, String password) {
        synchronized (lock) {
            ArrayList<String> userEntries = getDatabaseContents("users.csv");
            // If the very first user in the application tries logging in instead of signing
            // up
            if (userEntries.isEmpty()) {
                return null;
            }
            for (int j = 0; j < userEntries.size(); j++) {
                String[] userRepresentation = userEntries.get(j).split(",");
                if (email.toLowerCase().equals(userRepresentation[1].toLowerCase())
                        && password.toLowerCase().equals(userRepresentation[2].toLowerCase())) {
                    return userEntries.get(j);
                }
            }
            return null;
        }
    }

    /**
     * Takes in the user's email and retrieves a match for the credential in the
     * users.csv database. Used for when the user is creating a new account. A null
     * value signifies that no user with the given email was found in the database.
     *
     * @param email The user's email to compare with an existing entry in the
     *              users.csv database
     * @return A comma-separated string containing the matched user's information in
     *         the users.csv database
     */
    public String retrieveUserMatchForSignUp(String email) {
        synchronized (lock) {
            ArrayList<String> userEntries = getDatabaseContents("users.csv");
            // If the very first user in the application tries creating an account, no
            // matches found yet
            if (userEntries.isEmpty()) {
                return null;
            }
            for (int j = 0; j < userEntries.size(); j++) {
                String[] userRepresentation = userEntries.get(j).split(",");
                if (email.toLowerCase().equals(userRepresentation[1].toLowerCase())) {
                    return userEntries.get(j);
                }
            }
            return null;
        }
    }

    /**
     * Takes in a comma-separated string as an entry and appends it to the file
     * specified by the file name
     *
     * @param fileName The name of the file to append the entry to
     * @param entry    The entry to be appended to the file
     */
    public void addToDatabase(String fileName, String entry) {
        synchronized (lock) {
            ArrayList<String> relevantContents = getDatabaseContents(fileName);
            switch (fileName) {
                case "users.csv":
                    if (!checkEntryExists(fileName, entry)) {
                        relevantContents.add(entry);
                        updateDatabaseContents(fileName, relevantContents);
                    }
                    break;
                case "stores.csv":
                    if (!checkEntryExists(fileName, entry)) {
                        relevantContents.add(entry);
                        updateDatabaseContents(fileName, relevantContents);
                    }
                    break;
                case "products.csv":
                    if (!checkEntryExists(fileName, entry)) {
                        relevantContents.add(entry);
                        updateDatabaseContents(fileName, relevantContents);
                    }
                    break;
                case "shoppingCarts.csv":
                    if (!checkEntryExists(fileName, entry)) {
                        relevantContents.add(entry);
                        updateDatabaseContents(fileName, relevantContents);
                    }
                    break;
                case "purchaseHistories.csv":
                    if (!checkEntryExists(fileName, entry)) {
                        relevantContents.add(entry);
                        updateDatabaseContents(fileName, relevantContents);
                    }
                    break;
            }
        }
    }

    /**
     * Takes in a comma-separated string as an entry and removes it from the file
     * specified by the file name
     *
     * @param fileName The name of the file to remove the entry from
     * @param entry    The entry to be removed from the file
     */
    public void removeFromDatabase(String fileName, String entry) {
        synchronized (lock) {
            ArrayList<String> relevantContents = getDatabaseContents(fileName);
            switch (fileName) {
                case "users.csv":
                    boolean userRemoved = relevantContents.remove(entry);
                    if (userRemoved) {
                        updateDatabaseContents(fileName, relevantContents);
                    }
                    break;
                case "stores.csv":
                    boolean storeRemoved = relevantContents.remove(entry);
                    if (storeRemoved) {
                        updateDatabaseContents(fileName, relevantContents);
                    }
                    break;
                case "products.csv":
                    boolean productRemoved = relevantContents.remove(entry);
                    if (productRemoved) {
                        updateDatabaseContents(fileName, relevantContents);
                    }
                    break;
                case "shoppingCarts.csv":
                    boolean shoppingCartRemoved = relevantContents.remove(entry);
                    if (shoppingCartRemoved) {
                        updateDatabaseContents(fileName, relevantContents);
                    }
                    break;
                case "purchaseHistories.csv":
                    boolean purchaseHistoryRemoved = relevantContents.remove(entry);
                    if (purchaseHistoryRemoved) {
                        updateDatabaseContents(fileName, relevantContents);
                    }
                    break;
            }
        }
    }

    /**
     * Takes in two comma separated strings, one representing the previous entry in
     * the file specified and one representing the modified entry and updates that
     * specified entry in the file with the modified entry
     *
     * @param fileName  The name of the file to be updated with the modified entry
     * @param prevEntry The entry that already exists in the file
     * @param newEntry  The entry that will replace the previous entry in the
     *                  specified file
     */
    public boolean modifyDatabase(String fileName, String prevEntry, String newEntry) {
        synchronized (lock) {
            ArrayList<String> relevantContents = getDatabaseContents(fileName);
            boolean databaseModified = false;
            switch (fileName) {
                case "users.csv":
                    int prevUserIdx = relevantContents.indexOf(prevEntry);
                    if (prevUserIdx != -1) {
                        relevantContents.set(prevUserIdx, newEntry);
                        updateDatabaseContents(fileName, relevantContents);
                        databaseModified = true;
                    }
                    break;
                case "stores.csv":
                    int prevStoreIdx = relevantContents.indexOf(prevEntry);
                    if (prevStoreIdx != -1) {
                        relevantContents.set(prevStoreIdx, newEntry);
                        updateDatabaseContents(fileName, relevantContents);
                        databaseModified = true;
                    }
                    break;
                case "products.csv":
                    int prevProductIdx = relevantContents.indexOf(prevEntry);
                    if (prevProductIdx != -1) {
                        relevantContents.set(prevProductIdx, newEntry);
                        updateDatabaseContents(fileName, relevantContents);
                        databaseModified = true;
                    }
                    break;
                case "shoppingCarts.csv":
                    int prevShoppingCartIdx = relevantContents.indexOf(prevEntry);
                    if (prevShoppingCartIdx != -1) {
                        relevantContents.set(prevShoppingCartIdx, newEntry);
                        updateDatabaseContents(fileName, relevantContents);
                        databaseModified = true;
                    }
                    break;
                case "purchaseHistories.csv":
                    int prevPurchaseHistoryIdx = relevantContents.indexOf(prevEntry);
                    if (prevPurchaseHistoryIdx != -1) {
                        relevantContents.set(prevPurchaseHistoryIdx, newEntry);
                        updateDatabaseContents(fileName, relevantContents);
                        databaseModified = true;
                    }
                    break;
            }
            return databaseModified;
        }
    }

    /**
     * Searches for the entry parameter in the file specified by filename and
     * returns whether it exists or not
     *
     * @param fileName The name of the file to within which to look for
     * @param entry    A comma-separated string representing a possible entry in the
     *                 file
     * @return The existence of the entry in the specified file
     */
    public boolean checkEntryExists(String fileName, String entry) {
        synchronized (lock) {
            File target = new File(DATABASES_DIRECTORY + fileName);
            try (BufferedReader br = new BufferedReader(new FileReader(target))) {
                br.readLine(); // skip the header
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.equals(entry)) {
                        return true;
                    }
                }
                return false;
            } catch (IOException e) {
                return false;
            }
        }
    }

    /**
     * Searches the column specified by the index in the specified file and returns
     * all rows where the value in that column matches the value of the search
     * parameter
     *
     * @param fileName    The file to search for
     * @param index       The index of the column to search for
     * @param searchParam The parameter to compare a value in the specified column
     *                    to
     * @return An arraylist of the matched rows
     */
    public ArrayList<String> getMatchedEntries(String fileName, int index, String searchParam) {
        synchronized (lock) {
            ArrayList<String> matchedEntries = new ArrayList<>();
            try {
                if (checkColumnBounds(fileName, index)) {
                    File target = new File(DATABASES_DIRECTORY + fileName);
                    BufferedReader br = new BufferedReader(new FileReader(target));
                    br.readLine(); // skip the header
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] contents = line.split(",");
                        if (contents[index].equals(searchParam)) {
                            matchedEntries.add(line);
                        }
                    }
                    br.close();
                }
            } catch (Exception e) {
                return new ArrayList<String>();
            }
            return matchedEntries;
        }
    }

    /**
     * Takes in the name of the file as input and extracts all of its contents
     * line-by-line
     *
     * @param fileName The filename to extract the contents from
     * @return An arraylist containing all the entries in the specified file
     */
    public ArrayList<String> getDatabaseContents(String fileName) {
        synchronized (lock) {
            ArrayList<String> fileContents = new ArrayList<String>();
            File output = new File(DATABASES_DIRECTORY + fileName);
            try {
                BufferedReader br = new BufferedReader(new FileReader(output));
                br.readLine(); // skipping the headers
                String line;
                while ((line = br.readLine()) != null) {
                    fileContents.add(line);
                }
                br.close();
            } catch (IOException e) {
                return new ArrayList<String>();
            }
            return fileContents;
        }
    }

    /**
     * Takes in the name of the file and overwrites it completely with the contents
     * specified by the contents in the arraylist
     *
     * @param fileName The name of the file whose contents need to be updated
     * @param contents An arraylist representing the modified contents to be written
     *                 to the file
     */
    public void updateDatabaseContents(String fileName, ArrayList<String> contents) {
        synchronized (lock) {
            File dir = new File(DATABASES_DIRECTORY);
            try {
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File output = new File(dir, fileName);
                String fileHeaders = getFileHeaders(fileName);
                PrintWriter bw = new PrintWriter(new FileWriter(output));
                bw.println(fileHeaders);
                for (int i = 0; i < contents.size(); i++) {
                    bw.println(contents.get(i));
                }
                bw.flush();
                bw.close();
            } catch (IOException e) {
                System.out.println("There was an error when updating the contents of " +
                        fileName);
            }
        }
    }
}