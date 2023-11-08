/**
 * Project 4 - User.java
 * 
 * Class that represents the characteristics associated with all users in the application.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 6, 2023
 */
public class User {
    //Fields
    private static Database db = new Database();
    private String email;
    private String password;
    private UserRole role; // User's role in the application (Seller or Customer);
    private String userID;  //Each user has a unique ID

    //Blank Constructor for currentUser in runner
    public User() {
        this.userID = "";
        this.email = "";
        this.password = "";
        this.role = null;
    }

    //Constructor to initialize a new user when they create an account(Adds an identifier marker)
    public User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
        int generatedID = generateUserIdentificationNumber();
        switch (role) {
            case CUSTOMER: this.userID = "C" + String.valueOf(generatedID);
                                        break;
            case SELLER: this.userID = "S" + String.valueOf(generatedID);
                                        break;
            case UNDECIDED: this.userID = "";
                            break;
        }
    }

    //Constructor to re-initialize a user when they log back in with the correct credentials
    public User(String userID, String email, String password, UserRole role) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    //Getters
    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUserID() {
        return this.userID;
    }

    public UserRole getRole() {
        return this.role;
    }

    //Setters (when the user wants to modify their account)
    public void setEmail(String email) {
        this.email = email;
    }

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

    // this is what's being received by the addUsersToDatabase method in the database class
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s", this.userID, this.email, this.password, this.role);
    }
}