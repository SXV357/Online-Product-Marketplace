/**
 * Project 4 - CredentialMismatchException.java
 * 
 * A custom exception to handle errors when logging into the application.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 2, 2023
 */

public class CredentialMismatchException extends Exception {
    public CredentialMismatchException(String message) {
        super(message);
    }
}