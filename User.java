import java.util.Random;
/**
 * Project 4 - User.java
 * 
 * Class that represents the characteristics associated with all users in the application.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 4, 2023
 */
public class User {
    //Fields
    private String email;  //Each user has a unique email
    private String password;  //Each user has a password (could be the same as another user)
    private int userID;  //Each user has a unique ID that is 6 digits long
    private UserRole role;  //Each user has a role (Seller or Customer);
    private Random rdmID = new Random();  //This is used to generate a random int from 0-999999 for IDs

    //Constructor for a CHECKING for a user
    //You already know the userID for this one
    public User(int userID, String email, String password, UserRole role) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    //Constructor for a CREATING a new user
    //You need to make a new userID for this one
    public User(String email, String password, UserRole role) {
        this.userID = generateID();
        this.email = email;
        this.password = password;
        this.role = role;
    }

    private int generateID () {
        int newID = rdmID.nextInt(999999);
        return newID;
    }
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

    //This compares the current user's email and password to another user's passed in as a parameter
    //Used to log user in
    @Override
    public boolean equals(Object o){
        if (o instanceof User) {
            User user = (User) o;
            if (this.email.equals(user.getEmail()) && this.password.equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }
}