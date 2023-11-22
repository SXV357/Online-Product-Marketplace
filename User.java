import java.util.ArrayList;

/**
 * Project 4 - User.java
 * 
 * Class that represents the characteristics associated with all users in the
 * application.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version November 21, 2023
 */
public class User {

    private Database db = new Database();
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
     */
    public User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
        int generatedID = generateUserIdentificationNumber();
        switch (role) {
            case CUSTOMER -> this.userID = "C" + String.valueOf(generatedID);
            case SELLER -> this.userID = "S" + String.valueOf(generatedID);
            case UNDECIDED -> this.userID = "";
        }
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
    public User(String userID, String email, String password, UserRole role) {
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
    public void setEmail(String email) {
        boolean modifyEmail = true;
        ArrayList<String> userEntries = db.getDatabaseContents("users.csv");
        for (String userEntry : userEntries) {
            String userEmail = userEntry.split(",")[1];
            if (userEmail.equals(email)) {
                modifyEmail = false;
                System.out.println(
                        "Another user exists with the same email. Please choose a different one and try again!");
                break;
            }
        }
        if (modifyEmail) {
            this.email = email;
        }
    }

    /**
     * Sets the user's current email to the password passed into the method
     *
     * @param password The password to modify the current password to
     */
    public void setPassword(String password) {
        this.password = password;
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
     */
    public void deleteAccount() {
        try {
            db.removeFromDatabase("users.csv", this.toString());
            if (this.role == UserRole.CUSTOMER) {
                ArrayList<String> matchedShoppingCarts = db.getMatchedEntries("shoppingCarts.csv", 0, this.userID);
                ArrayList<String> matchedPurchaseHistories = db.getMatchedEntries("purchaseHistories.csv", 0,
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
                ArrayList<String> matchedShoppingCarts = db.getMatchedEntries("shoppingCarts.csv", 1, this.userID);
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
            System.out.println("An error occurred when deleting this account. Please try again!");
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