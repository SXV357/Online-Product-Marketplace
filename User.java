/**
 * Project 4 - User.java
 * 
 * Class that represents the characteristics associated with all users in the application.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 3, 2023
 */
public class User {
//Fields
    private String email;  //Each user has a unique email
    private String password;  //Each user has a password (could be the same as another user)
    private int userID;  //Each user has a unique ID that is 6 digits long
    private UserRole role;  //Each user has a role (Seller or Customer);

    //Constructor for a new user
    public User(int userID, String email, String password, UserRole role) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // TO DO: Method to generate a random 6-digit ID(Assign it to userID when instantiating a user)
    // TO DO: Implement equals method to compare two User objects

    //Getters
    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public int getUserID() {
        return this.userID;
    }

    public UserRole getRole() {
        return this.role;
    }

    //Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // this is what's being received by the addUsersToDatabase method in the database class
    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s", this.userID, this.email, this.password, this.role);
    }

}