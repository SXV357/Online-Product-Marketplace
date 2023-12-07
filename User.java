import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project 5 - User.java
 * 
 * Class that represents the characteristics associated with all users in the
 * application.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version December 7, 2023
 */
public class User {

    private Database db = new Database();
    private final String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,6}$";
    private String email;
    private String password;
    private UserRole role;
    private String userID;

    /**
     * Blank constructor to initialize a non-existent user until the user creates an
     * account or logs in
     */
    public User() {
        this.userID = "";
        this.email = "";
        this.password = "";
        this.role = null;
    }

    /**
     * Constructor to initialize a new user when they create an account. Generates a
     * unique ID for the user and adds a marker to it signifying whether the user is
     * a customer or a seller.
     *
     * @param email    The user's new email
     * @param password The user's new password
     * @param role     The user's role
     * @throws Exception
     */
    public User(String email, String password, UserRole role) throws Exception {
        if (email == null || email.isBlank() || email.isEmpty()) {
            throw new Exception("Invalid email. Email cannot be null, blank, or empty");
        } else if (password == null || password.isBlank() || password.isEmpty()) {
            throw new Exception("Invalid password. Password cannot be null, blank, or empty");
        }
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            boolean emailExists = checkDuplicateEmail(email);
            if (emailExists) {
                throw new Exception("Another user exists with the same email. Please choose a different" +
                        " one and try again!");
            } else {
                this.email = email;
            }
        } else {
            throw new Exception("Invalid email format. Please enter a valid one and try again!");
        }
        if (password.contains(",")) {
            throw new Exception("Password cannot contain commas");
        } else {
            this.password = password;
        }
        this.role = role;
        int generatedID = generateUserIdentificationNumber();
        switch (role) {
            case CUSTOMER -> this.userID = "C" + String.valueOf(generatedID);
            case SELLER -> this.userID = "S" + String.valueOf(generatedID);
        }
        db.addToDatabase("users.csv", this.toString());
    }

    /**
     * Customer to re-initialize a user when they log back into the application.
     * Sets the values of all fields to the ones passed to the constructor.
     *
     * @param userID   The user's existing ID
     * @param email    The user's existing email
     * @param password The user's existing password
     * @param role     The user's existing role
     */
    public User(String userID, String email, String password, UserRole role) throws Exception {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the current user's email
     *
     * @return The email associated with this user
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Gets the current user's password
     *
     * @return The password associated with this user
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Gets the current user's ID
     *
     * @return The ID associated with this user
     */
    public String getUserID() {
        return this.userID;
    }

    /**
     * Gets the current user's role
     *
     * @return The role associated with this user
     */
    public UserRole getRole() {
        return this.role;
    }

    /**
     * Sets the user's current email to the email passed into the method. The user's
     * email won't be modified if the new value they provide is associated with an
     * existing user's email.
     *
     * @param email The email to modify the current email to
     */
    public void setEmail(String email) throws Exception {
        if (email == null || email.isBlank() || email.isEmpty()) {
            throw new Exception("The email cannot be null, blank, or empty");
        }
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            boolean emailExists = checkDuplicateEmail(email);
            if (emailExists) {
                throw new Exception("Another user exists with the same email. Please choose a different one" +
                        " and try again!");
            } else {
                String prevUserString = this.toString();
                this.email = email;
                db.modifyDatabase("users.csv", prevUserString, this.toString());
            }
        } else {
            throw new Exception("Invalid email format. Please enter a valid one and try again!");
        }
    }

    /**
     * Sets the user's current email to the password passed into the method
     *
     * @param password The password to modify the current password to
     */
    public void setPassword(String password) throws Exception {
        if (password == null || password.isBlank() || password.isEmpty()) {
            throw new Exception("The new password cannot be null, blank or empty!");
        } else if (password.contains(",")) {
            throw new Exception("New password cannot contain commas.");
        }
        String prevUserString = this.toString();
        this.password = password;
        db.modifyDatabase("users.csv", prevUserString, this.toString());
    }

    /**
     * Takes in the user's email and returns whether it is associated with an existing user
     *
     * @param checkEmail The email to check the existence for in database
     * @return The existence of the email in the users.csv database
     */
    public boolean checkDuplicateEmail(String checkEmail) {
        return db.retrieveUserMatchForSignUp(checkEmail) != null;
    }

    /**
     * Returns a unique 7-digit ID as long as the current ID is not already
     * associated with an existing account in the users.csv database
     *
     * @return A unique 7-digit ID
     */
    public int generateUserIdentificationNumber() {
        int currentID = 1000000; // This would be the first user's ID
        while (db.checkIDMatch(currentID, "users.csv")) {
            currentID++;
        }
        return currentID;
    }

    /**
     * Deletes the user's account in associated databases based on the user's role.
     * If a seller deletes their account, the associated entries containing that
     * seller's ID in a customer's purchase history won't be deleted.
     *
     * @throws Exception
     */
    public void deleteAccount() throws Exception {
        try {
            db.removeFromDatabase("users.csv", this.toString());
            if (this.role == UserRole.CUSTOMER) {
                ArrayList<String> matchedShoppingCarts = db.getMatchedEntries("shoppingCarts.csv",
                        0, this.userID);
                ArrayList<String> matchedPurchaseHistories = db.getMatchedEntries("purchaseHistories.csv",
                        0,
                        this.userID);
                for (String shoppingCartEntry : matchedShoppingCarts) {
                    db.removeFromDatabase("shoppingCarts.csv", shoppingCartEntry);
                }
                for (String purchaseHistoryEntry : matchedPurchaseHistories) {
                    db.removeFromDatabase("purchaseHistories.csv", purchaseHistoryEntry);
                }
            } else if (this.role == UserRole.SELLER) {
                ArrayList<String> matchedStores = db.getMatchedEntries("stores.csv", 1, this.userID);
                ArrayList<String> matchedProducts = db.getMatchedEntries("products.csv", 0, this.userID);
                ArrayList<String> matchedShoppingCarts = db.getMatchedEntries("shoppingCarts.csv",
                        1, this.userID);
                for (String storeEntry : matchedStores) {
                    db.removeFromDatabase("stores.csv", storeEntry);
                }
                for (String productEntry : matchedProducts) {
                    db.removeFromDatabase("products.csv", productEntry);
                }
                for (String shoppingCartEntry : matchedShoppingCarts) {
                    db.removeFromDatabase("shoppingCarts.csv", shoppingCartEntry);
                }
            }
        } catch (Exception e) {
            throw new Exception("An error occurred when deleting this account. Please try again!");
        }
    }

    /**
     * Represents an entry associated with a specific user in the users.csv database
     */
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s", this.userID, this.email, this.password, this.role);
    }
}