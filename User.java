
/**
 * Project 4 - User.java
 * 
 * Class that represents the characteristics associated with all users in the application.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 5, 2023
 */
public class User {
    //Fields
    private String email;  //Each user has a unique email
    private String password;  // Password
    private UserRole role;  //Each user has a role (Seller or Customer);
    private int userID;  //Each user has a unique ID

    //Constructor for when a user creates their account (userID must be created)
    public User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.userID = createID();
    }

    //Constructor for when you create a user with a KNOWN userID
    public User(int userID, String email, String password, UserRole role) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    //Blank Constructor for currentUser in runner
    public User() {
        this.userID = 0;
        this.email = "";
        this.password = "";
        this.role = null;
    }

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

    public void setRole(UserRole role) {
        this.role = role;
    }

    public int createID() {
        int newID = 100000;  //This would be the first user's ID

        //TODO implement a database method to check for existing userIDs
/*        while (*//* Check Database users to see if someone already has this ID*//*) {
            newID++;
        }*/
        return newID;
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