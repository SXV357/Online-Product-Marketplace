import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Database {
    private static final String DATABASES_DIRECTORY = "databases/";
    private static final String usersDatabaseHeaders = "ID,Email,Password,Role";
    private static final String storesDatabaseHeaders = "Seller ID,Store Name,Number of Products";
    private static final String productsDatabaseHeaders = "Seller ID,Store Name,Product Name,Available Quantity,Price,Description";
    private static final String purchaseHistoryDatabaseHeaders = "Customer ID,Seller ID,Store Name,Product Name,Purchase Quantity,Price";
    private static final String shoppingCartDatabaseHeaders = "Customer ID,Seller ID,Store Name,Product Name,Purchase Quantity,Price";

    private ArrayList<String> userEntries;
    private ArrayList<String> storeEntries;
    private ArrayList<String> productEntries;
    private ArrayList<String> shoppingCartEntries;
    private ArrayList<String> purchaseHistoryEntries;

    public Database() {
        this.userEntries = new ArrayList<>();
        this.storeEntries = new ArrayList<>();
        this.productEntries = new ArrayList<>();
        this.shoppingCartEntries = new ArrayList<>();
        this.purchaseHistoryEntries = new ArrayList<>();
    }

    /**
     * Takes in the name of the file as input and returns a string containing all the headers associated with that file.
     * 
     * @param fileName The name of the file that the user wants to update
     * @return The headers that are associated with that specific CSV file.
     */
    public String getFileHeaders(String fileName) {
        String fileHeaders = "";
        switch (fileName) {
            case "users.csv" -> fileHeaders = usersDatabaseHeaders;
            case "stores.csv" -> fileHeaders = storesDatabaseHeaders;
            case "products.csv" -> fileHeaders = productsDatabaseHeaders;
            case "shoppingCarts.csv" -> fileHeaders = shoppingCartDatabaseHeaders;
            case "purchaseHistories.csv" -> fileHeaders = purchaseHistoryDatabaseHeaders;
        }
        return fileHeaders;
    }

    /**
     * Takes in an ID, then compares it with all entries in the users.csv database to check if there's a match
     * 
     * @param id The ID to compare with an existing entry in the users.csv database
     * @return Returns whether the ID already exists in the users.csv database
     */
    public boolean checkUserIDMatch(int id) {
        for (int i = 0; i < this.userEntries.size(); i++) {
            String[] userRepresentation = this.userEntries.get(i).split(",");
            if (id == Integer.parseInt(userRepresentation[0])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes in the user's email and password and retrieves a match for the credentials in the users.csv database.
     * 
     * @param email The user's email to compare with an existing entry in the users.csv database
     * @param password The user's password to compare with an existing entry in the users.csv database
     * @return A comma-separated string containing the matched user's information in the users.csv database
     */
    public String retrieveUser(String email, String password) {
        for (int j = 0; j < this.userEntries.size(); j++) {
            String[] userRepresentation = this.userEntries.get(j).split(",");
            if (email.equals(userRepresentation[1]) && password.equals(userRepresentation[2])) {
                return this.userEntries.get(j);
            }
        }
        return "";
    }

    /**
     * Takes in a comma-separated string as an entry and appends it to the file specified by the file name
     * 
     * @param fileName The name of the file to append the entry to
     * @param entry The entry to be appended to the file
     */
    public void addToDatabase(String fileName, String entry) {
        switch (fileName) {
            case "users.csv": 
                            this.userEntries.add(entry);
                            updateDatabaseContents(fileName, this.userEntries);
                            break;
            case "stores.csv":
                            this.storeEntries.add(entry);
                            updateDatabaseContents(fileName, storeEntries);
                            break;
            case "products.csv":
                            this.productEntries.add(entry);
                            updateDatabaseContents(fileName, productEntries);
                            break;
            case "shoppingCarts.csv":
                            this.shoppingCartEntries.add(entry);
                            updateDatabaseContents(fileName, shoppingCartEntries);
                            break;
            case "purchaseHistories.csv":
                            this.purchaseHistoryEntries.add(entry);
                            updateDatabaseContents(fileName, purchaseHistoryEntries);
                            break;
        }
    }

    /**
     * Takes in a comma-separated string as an entry and removes it from the file specified by the file name
     * 
     * @param fileName The name of the file to remove the entry from
     * @param entry The entry to be removed from the file
     */
    public void removeFromDatabase(String fileName, String entry) {
        switch (fileName) {
            case "users.csv": 
                            this.userEntries.remove(entry);
                            updateDatabaseContents(fileName, this.userEntries);
                            break;
            case "stores.csv":
                            this.storeEntries.remove(entry);
                            updateDatabaseContents(fileName, storeEntries);
                            break;
            case "products.csv":
                            this.productEntries.remove(entry);
                            updateDatabaseContents(fileName, productEntries);
                            break;
            case "shoppingCarts.csv":
                            this.shoppingCartEntries.remove(entry);
                            updateDatabaseContents(fileName, shoppingCartEntries);
                            break;
            case "purchaseHistories.csv":
                            this.purchaseHistoryEntries.remove(entry);
                            updateDatabaseContents(fileName, purchaseHistoryEntries);
                            break;
        }
    }

    /**
     * Takes in two comma separated strings, one representing the previous entry in the file specified and one representing the modified entry and updates that specified entry in the file with the modified entry
     * 
     * @param fileName The name of the file to be updated with the modified entry
     * @param prevEntry The entry that already exists in the file
     * @param newEntry The entry that will replace the previous entry in the specified file
     */
    public void modifyDatabase(String fileName, String prevEntry, String newEntry) {
        switch (fileName) {
            case "users.csv": 
                            int prevUserIdx = this.userEntries.indexOf(prevEntry);
                            this.userEntries.set(prevUserIdx, newEntry);
                            updateDatabaseContents(fileName, this.userEntries);
                            break;
            case "stores.csv":
                            int prevStoreIdx = this.storeEntries.indexOf(prevEntry);
                            this.storeEntries.set(prevStoreIdx, newEntry);;
                            updateDatabaseContents(fileName, storeEntries);
                            break;
            case "products.csv":
                            int prevProductIdx = this.productEntries.indexOf(prevEntry);
                            this.productEntries.set(prevProductIdx, newEntry);
                            updateDatabaseContents(fileName, productEntries);
                            break;
            case "shoppingCarts.csv":
                            int prevShoppingCartIdx = this.shoppingCartEntries.indexOf(prevEntry);
                            this.shoppingCartEntries.set(prevShoppingCartIdx, newEntry);
                            updateDatabaseContents(fileName, shoppingCartEntries);
                            break;
            case "purchaseHistories.csv":
                            int prevPurchaseHistoryIdx = this.purchaseHistoryEntries.indexOf(prevEntry);
                            this.purchaseHistoryEntries.set(prevPurchaseHistoryIdx, newEntry);
                            updateDatabaseContents(fileName, purchaseHistoryEntries);
                            break;
        }
    }

    /**
     * Takes in the name of the file as input and extracts all of its contents line-by-line
     * 
     * @param fileName The filename to extract the contents from
     * @return An arraylist containing all the entries in the specified file
     */
    public ArrayList<String> getDatabaseContents(String fileName) {
        ArrayList<String> fileContents = new ArrayList<String>();
        File output = new File(DATABASES_DIRECTORY + fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(output));
            br.readLine(); // skipping the headers
            String line;
            while ((line = br.readLine()) != null) {
                fileContents.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            return new ArrayList<String>();
        }
        return fileContents;
    }

    /**
     * Takes in the name of the file and overwrites it completely with the contents specified by the contents in the arraylist
     * 
     * @param fileName The name of the file whose contents need to be updated
     * @param contents An arraylist representing the modified contents to be written to the file
     */
    public void updateDatabaseContents(String fileName, ArrayList<String> contents) {
        File dir = new File(DATABASES_DIRECTORY);
        try {
            if (!dir.exists()) {
                dir.mkdir();
            }
            File output = new File(dir, fileName);
            String fileHeaders = getFileHeaders(fileName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(output));
            bw.write(fileHeaders + "\n");
            for (int i = 0; i < contents.size(); i++) {
                bw.write(contents.get(i) + "\n");
            }
            bw.close();
            System.out.println("The contents of " + fileName + " were updated successfully!");
        } catch (IOException e) {
            System.out.println("There was an error when updating the contents of " + fileName);
        }
    }
}