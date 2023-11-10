/**
 * Project 4 - User.java
 * 
 * Class that represents the characteristics associated with all users in the application.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 9, 2023
 */
public class User {
    
    private static Database db = new Database();

    private String email;
    private String password;
    private UserRole role;
    private String userID;

    /**
     * Blank constructor to initialize a non-existent user until the user creates an account or logs in
     */
    public User() {
        this.userID = "";
        this.email = "";
        this.password = "";
        this.role = null;
    }

    /**
     * Constructor to initialize a new user when they create an account. Generates a unique ID for the user and adds a marker to it signifying whether the user is a customer or a seller.
     * 
     * @param email The user's new email
     * @param password The user's new password
     * @param role The user's role
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
     * Customer to re-initialize a user when they log back into the application. Sets the values of all fields to the ones passed to the constructor.
     * 
     * @param userID The user's existing ID
     * @param email The user's existing email
     * @param password The user's existing password
     * @param role The user's existing role
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
     * Sets the user's current email to the email passed into the method
     * 
     * @param email The email to modify the current email to
     */
    public void setEmail(String email) {
        this.email = email;
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
     * Returns a unique 7-digit ID as long as the current ID is not already associated with an existing account in the users.csv database
     * 
     * @return A unique 7-digit ID
     */
    public int generateUserIdentificationNumber() {
        int currentID = 1000000;  //This would be the first user's ID
        while (db.checkIDMatch(currentID, "users.csv")) {
            currentID++;
        }
        return currentID;
    }

    /**
     * Represents an entry associated with a specific user in the users.csv database
     */
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s", this.userID, this.email, this.password, this.role);
    }
}