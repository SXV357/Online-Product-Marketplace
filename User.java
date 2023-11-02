/**
 * Project 4 - User.java
 * 
 * Class that represents the characteristics associated with all users in the application.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 2, 2023
 */
public class User {
//Fields
    private String email;  //Each user has a unique email
    private String password;  //Each user has a password (could be the same as another user)
    private int userID;  //Each user has a unique ID that is 6 digits long
    private String role;  //Each user has a role (Seller or Customer);

//Constructor for a new user
    public User(String email, String password, int userID, String role) {
        this.email = email;
        this.password = password;
        this.userID = userID;
        this.role = role;
    }

//Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getUserID() {
        return userID;
    }

    public String getRole() {
        return role;
    }

    //Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
