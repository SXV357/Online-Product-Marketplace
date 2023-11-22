package archive;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * Project 5 - Database.java
 * 
 * Class that handles all database access and modification functionality related to the application.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 5, 2023
 */
public class PrevDatabase {
    private static final String DATABASES_DIRECTORY = "databases/"; // the overarching folder containing all databases
    private static final String[] databaseSubDirs = {"products/", "shoppingCarts/", "purchaseHistories/"};
    // 1. products/: where each csv file is in the form sellerID-storeName.csv and contains product information
    // 2. shoppingCarts/: where each csv file is in the form customerID-shoppingCart.csv
    // 3. purchaseHistories/: where each csv file is in the form customerID-purchaseHistory.csv
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{6,15}$";
    private ArrayList<String> database;

    public PrevDatabase() {
        this.database = new ArrayList<String>();
    }

    public ArrayList<String> getDatabase() {
        return this.database;
    }
}

    // logic for validating the user's email and password when they try creating an account
    // public boolean validateEmail(String email) {
    //     Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
    //     Matcher emailMatcher = emailPattern.matcher(email);
    //     return emailMatcher.matches();
    // }

    // public boolean validatePassword(String password) {
    //     Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
    //     Matcher passwordMatcher = passwordPattern.matcher(password);
    //     return passwordMatcher.matches();

    // user string in form of ID,Email,Password,Role (Could have a toString method in the User class for this)
    // public boolean addUser(User user) {
    //     boolean addUserResult = false;
    //     String userRepresentation = user.toString();
    //     // can accordingly display an error message in the client class for this
    //     if (!validateEmail(user.getEmail()) || !validatePassword(user.getPassword())) {
    //         addUserResult = false;
    //     } else {
    //         if (!(retrieveUser(user.getEmail(), user.getPassword()))) { // there's no existence already
    //             this.database.add(userRepresentation);
    //             updateUsersDatabase();
    //             instantiateUserEntries(String.valueOf(user.getUserID()), user.getRole());
    //             addUserResult =  true;
    //         }
    //     }
    //     return addUserResult;
    // }

    // when a customer creates an account, this will create two empty files associated with that user in the databases directory: one corresponding to their shopping cart and one corresponding to their purchase history
    // public void instantiateUserEntries(String userID, UserRole userRole) {
    //     try {
    //         if (userRole == UserRole.CUSTOMER) {
    //             String[] relatedSubDirs = {databaseSubDirs[1], databaseSubDirs[2]};
    //             for (String subDir: relatedSubDirs) {
    //                 File sub = new File(DATABASES_DIRECTORY, subDir);
    //                 if (!sub.exists()) {
    //                     sub.mkdir();
    //                 }
    //                 File output = subDir.equals("shoppingCarts/") ? new File(sub, userID + "-" + "shoppingCart.csv") : new File(sub, userID + "-" + "purchaseHistory.csv");
    //                 output.createNewFile();
    //             }
    //             System.out.println(userID + "\'s shopping cart and purchase history files were created successfully!");
    //         }
    //     } catch (IOException e) {
    //         System.out.println("There was an error when creating " + userID + "\'s shopping cart and purchase history files");
    //     }
    // }

    // search for an entry in the database that matches the ID of the user that wants to delete their account
    // public boolean removeUser(int userID) throws IOException {
    //     // if the user is a customer then remove the associated files from shoppingcart and purchase history
    //     // if the user is a seller then remove the associated file from the products directory
    //     for (int i = 0; i < this.database.size(); i++) {
    //         String[] userInformation = this.database.get(i).split(",");
    //         if (userID == Integer.parseInt(userInformation[0])) {
    //             this.database.remove(i);
    //             updateUsersDatabase();
    //             if (userInformation[3].equals(UserRole.CUSTOMER.toString())) {
    //                 File shoppingCartMatch = extractMatchedFile(DATABASES_DIRECTORY + databaseSubDirs[1], String.valueOf(userID));
    //                 File purchaseHistMatch = extractMatchedFile(DATABASES_DIRECTORY + databaseSubDirs[2], String.valueOf(userID));
    //                 if (shoppingCartMatch != null && purchaseHistMatch != null && shoppingCartMatch.exists() && purchaseHistMatch.exists()) {
    //                     shoppingCartMatch.delete();
    //                     purchaseHistMatch.delete();
    //                 }
    //             } else if (userInformation[3].equals(UserRole.SELLER.toString())) { // they're a seller
    //                 File storeMatch = extractMatchedFile(DATABASES_DIRECTORY + databaseSubDirs[0], String.valueOf(userID));
    //                 if (storeMatch != null && storeMatch.exists()) {
    //                     storeMatch.delete();
    //                 }
    //             }
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // if the user specifies they would like to modify their account
        // for now, we will give them the option of modifying either their email or their password
    // public boolean updateUsersDatabase(User modifier, String attribute, String newValue) {
    //     boolean hasBeenModified = false;
    //     String userRepresentation = modifier.toString();
    //     // at this point the user is logged in and there's an entry in the database for which a match exists
    //     int matchedIndex = this.database.indexOf(userRepresentation);
    //     if (matchedIndex != -1) {
    //         if (attribute.toLowerCase().equals("email")) {
    //             if (this.validateEmail(newValue)) {
    //                 // update the entry in the database with the new email
    //                 User modifiedUser = new User(newValue, modifier.getPassword(), modifier.getRole());
    //                 this.database.set(matchedIndex, modifiedUser.toString());
    //                 updateUsersDatabase();
    //                 hasBeenModified = true;
    //             } else {
    //                 hasBeenModified = false;
    //             }
    //         } else if (attribute.toLowerCase().equals("password")) {
    //             if (this.validatePassword(newValue)) {
    //                 User modifiedUser = new User(modifier.getEmail(), newValue, modifier.getRole());
    //                 this.database.set(matchedIndex, modifiedUser.toString());
    //                 updateUsersDatabase();
    //                 hasBeenModified = true;
    //             } else {
    //                 hasBeenModified = false;
    //             }
    //         }
    //     }
    //     return hasBeenModified;
    // }

    // used when logging into the application to verify that there's a match
        // prevent the user from logging in if matchedUser equals null
    // if user's email and password is valid then this method is used to check whether there's not already an account in use with those credentials before re-directing them to the log in page
    // public boolean retrieveUser(String email, String password) {
    //     for (int i = 0; i < this.database.size(); i++) {
    //         String[] userEntry = this.database.get(i).split(",");
    //         if (email.equals(userEntry[1]) && password.equals(userEntry[2])) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // when sellers elect to view sales by store, where theyll reference a customer's purchase history in which the customer's ID exists, so based on the ID retrieving customer info from the main database(whether we want to display customer email or not can be decided later)
    // public String retrieveCustomerEmail(int customerID) {
    //     for (int k = 0; k < this.database.size(); k++) {
    //         String[] userEntry = this.database.get(k).split(",");
    //         if (customerID == Integer.parseInt(userEntry[0])) {
    //             return userEntry[1];
    //         }
    //     }
    //     return null; // error can be handled accordingly in the runner class when processing user input
    // }

    // retrieves all the entries from the file in the databases directory containing information about stores that sellers create and returns the contents in the form of an arraylist
    // public ArrayList<String> retrieveSellerStoreEntries() {
    //     ArrayList<String> stores = new ArrayList<>();
    //     try {
    //         File output = new File(DATABASES_DIRECTORY + "sellerStores.csv");
    //         output.createNewFile();
    //         BufferedReader br = new BufferedReader(new FileReader(output));
    //         br.readLine(); // skip the header
    //         String line;
    //         while ((line = br.readLine()) != null) {
    //             stores.add(line);
    //             line = br.readLine();
    //         }
    //         br.close();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return stores;
    // }

    // updates the main users.csv file with updated information about all users in response to a new user being created, a user choosing to delete their account, or a user choosing to modify their account
    // public void updateUsersDatabase() {
    //     File dir = new File(DATABASES_DIRECTORY);
    //     try {
    //         if (!dir.exists()) {
    //             dir.mkdir();
    //         }
    //         File output = new File(dir, "users.csv");
    //         output.createNewFile();
    //         BufferedWriter bw = new BufferedWriter(new FileWriter(output));
    //         bw.write("ID,Email,Password,Role\n");
    //         for (int j = 0; j < this.database.size(); j++) {
    //             bw.write(this.database.get(j) + "\n");
    //         }
    //         bw.close();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
    
    // given the sub-directory in the databases directory and the user ID, the function searches the directory for a file match, then returns the file
    // THIS IS ONLY APPLICABLE TO FILES LOCATED IN THE PRODUCTS, SHOPPINGCARTS, AND PURCHASEHISTORIES SUBDIRECTORIES
    // public File extractMatchedFile(String targetDirectory, String userID) throws IOException {
    //     // (for products, shopping cart, and purchase hist); expected input in form: databases/targetdirectory/
    //     File match = null;
    //     File dir = new File(targetDirectory);
    //     if (dir.isDirectory() && dir.exists()) {
    //         File[] files = dir.listFiles();
    //         for (File f: files) {
    //             if (f.getName().startsWith(userID)) { // we have that associated file
    //                 match = f;
    //                 break;
    //             }
    //         }
    //     }
    //     return match;
    // }

    // extracts the contents of the file in the directory targetDirectory and its name starting with userID and returns its contents in the form of an arraylist
    // THIS IS ONLY APPLICABLE TO FILES LOCATED IN THE PRODUCTS, SHOPPINGCARTS, AND PURCHASEHISTORIES SUBDIRECTORIES
    // public ArrayList<String> extractProdShopCartHistoryEntries(String targetDirectory, String userID) throws IOException {
    //     ArrayList<String> databaseContents = new ArrayList<>();
    //     File matchedFile = extractMatchedFile(targetDirectory, userID);
    //     try(BufferedReader br = new BufferedReader(new FileReader(matchedFile))) {
    //         br.readLine(); // skip the first line(header of csv)
    //         String line;
    //         while ((line = br.readLine()) != null) {
    //             databaseContents.add(line);
    //             line = br.readLine();
    //         }
    //         return databaseContents;
    //     } catch (IOException e) {
    //         return new ArrayList<String>();
    //     }
    // }

    // extracts the // extracts the contents of the file in the directory targetDirectory and its name starting with userID and then overwrites its contents with the contents of the arraylist updatedContents
    // THIS IS ONLY APPLICABLE TO FILES LOCATED IN THE PRODUCTS, SHOPPINGCARTS, AND PURCHASEHISTORIES SUBDIRECTORIES
    // public void updateDatabase(String targetDirectory, String userID, ArrayList<String> updatedContents) throws IOException {
    //     // headers of csv will vary depending on taretDirectory passed in
    //     // for products sub-dir: Product Name, Quantity, Price, Description
    //     // for purchase history sub-dir: CustomerID, StoreName, Item, Quantity, Expenses
    //     // for shopping cart sub-dir: CustomerID, StoreName, Product Name, Quantity, Cost, Description
    //     String dirCheck = targetDirectory.split("/")[1];
    //     String csvHeader = "";
    //     switch (dirCheck) {
    //         case "products" -> csvHeader = "ProductName,Quantity,Price,Description";
    //         case "shoppingCarts" -> csvHeader = "CustomerID,StoreName,Item,Quantity,Expenses";
    //         case "purchaseHistories" -> csvHeader = "CustomerID,StoreName,ProductName, Quantity,Cost,Description";
    //     }
    //     File output = extractMatchedFile(targetDirectory, userID);
    //     try {
    //         output.createNewFile();
    //         BufferedWriter bw = new BufferedWriter(new FileWriter(output));
    //         bw.write(csvHeader);
    //         for (int j = 0; j < updatedContents.size(); j++) {
    //             bw.write(updatedContents.get(j) + "\n");
    //         }
    //         bw.close();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }