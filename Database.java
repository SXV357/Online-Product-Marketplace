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
 * @version November 2, 2023
 */
public class Database {
    private static final String DATABASES_DIRECTORY = "databases/";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{6,15}$";
    private static final String[] stores = {"stores.csv", "products.csv", "purchaseHistory.csv", "shoppingCart.csv"};
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
    public void addUser(String user) throws IOException {
        String email = user.split(",")[1];
        String password = user.split(",")[2];
        if (!validateEmail(email) || !validatePassword(password)) {
            System.out.println("Either your email is not in the right format, or your password is not between 6 and 15 characters long. Please try again");
        } else { // email and password match the required format specifications
            this.database.add(user);
            save();
        }
    }

    // maybe have user enter their password again to confirm they would like to delete their account
    public void removeUser(String verificationEmail, String verificationPassword) {
        // only proceed if the password they enter is associated with an existing account
            // catch: two users could have the same password so just can't check for password equality
        for (int i = 0; i < this.database.size(); i++) {
            String[] userInformation = this.database.get(i).split(",");
            if (verificationPassword.equals(userInformation[2]) && verificationEmail.equals(userInformation[1])) {
                this.database.remove(i);
            }
        }
        save();
    }

    public void updateDatabase(String attribute, String target, String modifiedValue) {
        boolean matched = false;
        if (attribute.toLowerCase().equals("email")) {
            // both values are emails
            if (validateEmail(target) && validateEmail(modifiedValue)) {
                matched = true;
            } else if ((validateEmail(target) && !validateEmail(modifiedValue)) || (!validateEmail(target) && validateEmail(modifiedValue)) || (!validateEmail(target) && !validateEmail(modifiedValue))) {
                System.out.println("You specified you wanted to modify your email, but the original and the modified values that you provided are not necessarily emails. Please check the format and try again!");
                matched = false;
            }
        } else if (attribute.toLowerCase().equals("password")) {
            if (validatePassword(modifiedValue)) {
                matched = true;
            } else {
                System.out.println("Please check the new password you provided and make sure that is it between 6-15 characters long.");
                matched = false;
            }
        }
        // only do this if target and modifiedValue are of the attribute type passed in
        // if (matched) {
        //     String matchedUser = retrieveUser(attribute, target);
        //     int matchedIndex = this.database.indexOf(matchedUser);
        //     String[] userInfo = matchedUser.split(",");
        //     if (attribute.toLowerCase().equals("email")) {
        //         userInfo[1] = modifiedValue;
        //     } else if (attribute.toLowerCase().equals("password")) {
        //         userInfo[2] = modifiedValue;
        //     }
        //     this.database.set(matchedIndex, String.join(",", userInfo));
        //     save();
        // }
    }

    // used when logging into the application to verify that there's a match
    public String retrieveUser(String email, String password) {
        String matchedUser = "";
        for (int i = 0; i < this.database.size(); i++) {
            String[] userEntry = this.database.get(i).split(",");
            if (email.equals(userEntry[1]) && password.equals(userEntry[2])) {
                matchedUser = this.database.get(i);
            }
        }
        return matchedUser;
    }

    public void save() {
        File output = new File(DATABASES_DIRECTORY + "users.csv");
        try {
            if (!output.exists()) {
                output.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(output))) {
            bw.write("ID,Email,Password,Role\n");
            for (int j = 0; j < this.database.size(); j++) {
                bw.write(this.database.get(j) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // gets all the enntries from the stores.csv, products.csv, shoppingcart.csv and purchasehistory.csv databases
    public ArrayList<ArrayList<String>> getDatabaseEntries() {
        ArrayList<ArrayList<String>> storeEntries = new ArrayList<>();
        for (String fileName: stores) {
            File currFile = new File(DATABASES_DIRECTORY + fileName);
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
                storeEntries.add(databaseEntries);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return storeEntries;
    }

    public static void main(String[] args) throws IOException {
        String user1 = "141094,random_email1@example.com,v8s091lW23,Customer";
        String user2 = "985390,user1234@gmail.com,Ak9X0iSdL7, Seller";
        String user3 = "739813,john.doe@yahoo.com,gD457zE7si,Customer";
        String user4 = "676277,my_email@domain.net,8i5XIFm4SV,Seller";
        String user5 = "456604,j.smith@hotmail.com,cxV0vk7kQ4,Customer";
        Database db = new Database();
        db.addUser(user1);
        db.addUser(user2);
        db.addUser(user3);
        db.addUser(user4);
        db.addUser(user5);
        db.removeUser("random_email1@example.com", "v8s091lW23");

    }
}
