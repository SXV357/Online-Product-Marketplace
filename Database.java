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
 * Project 4 - Database.java
 * 
 * Class that handles all database access and modification functionality related to the application.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 3, 2023
 */
public class Database {
    private static final String DATABASES_DIRECTORY = "databases/"; // the overarching folder containing all databases
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{6,15}$";
    private static final String[] stores = {"stores.csv", "products.csv"};
        // 1: directory where customers' shopping carts exist
        // 2: directory where customers' purchaseHistories exist
    // private static final String[] databaseSubDirs = {"shoppingCarts/", "purchaseHistories/"};
    private ArrayList<String> database;

    public Database() {
        this.database = new ArrayList<String>();
    }

    public ArrayList<String> getDatabase() {
        return this.database;
    }

    public boolean validateEmail(String email) {
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }

    public boolean validatePassword(String password) {
        Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        return passwordMatcher.matches();
    }

    // user string in form of ID,Email,Password,Role (Could have a toString method in the User class for this)
    public boolean addUser(String user) throws IOException {
        String email = user.split(",")[1];
        String password = user.split(",")[2];
        // can accordingly display an error message in the client class for this
        if (!validateEmail(email) || !validatePassword(password)) {
            return false;
        }
        this.database.add(user);
        updateUsersDatabase();
        return true;
    }

    // search for an entry in the database that matches the ID of the user that wants to delete their account
    public void removeUser(int userID) {
        for (int i = 0; i < this.database.size(); i++) {
            String[] userInformation = this.database.get(i).split(",");
            if (userID == Integer.parseInt(userInformation[0])) {
                this.database.remove(i);
            }
        }
        updateUsersDatabase();
    }

    // if the user specifies they would like to modify their account
        // for now, we will give them the option of modifying either their email or their password
    public boolean updateDatabase(User modifier, String attribute, String newValue) {
        boolean hasBeenModified = false;
        String userRepresentation = modifier.toString();
        // at this point the user is logged in and there's an entry in the database for which a match exists
        int matchedIndex = this.database.indexOf(userRepresentation);
        if (attribute.toLowerCase().equals("email")) {
            if (this.validateEmail(newValue)) {
                // update the entry in the database with the new email
                User modifiedUser = new User(modifier.getUserID(), modifier.getPassword(), newValue, modifier.getRole());
                this.database.set(matchedIndex, modifiedUser.toString());
                updateUsersDatabase();
                hasBeenModified = true;
            } else {
                hasBeenModified = false;
            }
        } else if (attribute.toLowerCase().equals("password")) {
            if (this.validatePassword(newValue)) {
                User modifiedUser = new User(modifier.getUserID(), newValue, modifier.getEmail(), modifier.getRole());
                this.database.set(matchedIndex, modifiedUser.toString());
                updateUsersDatabase();
                hasBeenModified = true;
            } else {
                hasBeenModified = false;
            }
        }
        return hasBeenModified;
    }

    // used when logging into the application to verify that there's a match
        // prevent the user from logging in if matchedUser equals null
    public boolean retrieveUser(String email, String password) {
        for (int i = 0; i < this.database.size(); i++) {
            String[] userEntry = this.database.get(i).split(",");
            if (email.equals(userEntry[1]) && password.equals(userEntry[2])) {
                return true;
            }
        }
        return false;
    }

    // when sellers elect to view sales by store, where theyll reference a customer's purchase history in which the customer's ID exists, so based on the ID retrieving customer info from the main database(whether we want to display customer email or not can be decided later)
    public String retrieveCustomerEmail(int customerID) {
        for (int k = 0; k < this.database.size(); k++) {
            String[] userEntry = this.database.get(k).split(",");
            if (customerID == Integer.parseInt(userEntry[0])) {
                return userEntry[1];
            }
        }
        return null; // error can be handled accordingly in the runner class when processing user input
    }

    public void updateUsersDatabase() {
        File dir = new File(DATABASES_DIRECTORY);
        try {
            if (!dir.exists()) {
                dir.mkdir();
            }
            File output = new File(dir, "users.csv");
            output.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(output));
            bw.write("ID,Email,Password,Role\n");
            for (int j = 0; j < this.database.size(); j++) {
                bw.write(this.database.get(j) + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // gets all the entries from the stores.csv, products.csv databases
    public ArrayList<ArrayList<String>> getDatabaseEntries() {
        ArrayList<ArrayList<String>> storeAndProductEntries = new ArrayList<>();
        for (String fileName: stores) {
            File currFile = new File(DATABASES_DIRECTORY, fileName);
            try {
                if (!currFile.exists()) {
                    currFile.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<String> databaseEntries = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(currFile))) {
                br.readLine(); // skip the header section
                String line;
                while ((line = br.readLine()) != null) {
                    databaseEntries.add(line);
                    line = br.readLine();
                }
                storeAndProductEntries.add(databaseEntries);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return storeAndProductEntries;
    }
}