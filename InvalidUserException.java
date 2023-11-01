/**
 * Project 4 - InvalidUserException.java
 * 
 * A custom exception to handle errors when logging into the application.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version October 31, 2023
 */

public class InvalidUserException extends Exception {
    public InvalidUserException(String message) {
        super(message);
    }
}