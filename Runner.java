import java.util.Scanner;

/**
 * Project 4 - Runner.java
 * 
 * Driver class for the application.
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version October 31, 2023
 */

public class Runner {
    private static boolean running = true;  //This boolean is used to see if the user wishes to quit
    private static User currentUser;  //This field stores the user object for the current User of the program


    //Welcomes the user and asks them whether they would like to: Log-in, Create Account, or Quit
    //Takes only the Scanner as a Parameter
    public static int welcomeUser(Scanner scan) {
        System.out.println("Welcome!\nWhat would you like to do?");
        do {
            System.out.println("1) Log-in\n2) Create Account\n3) Quit");
            try {
                int response = Integer.parseInt(scan.nextLine());
                if (response > 0 && response < 4) {
                    return response;
                } else {
                    System.out.println("Please enter your choice's corresponding Integer");
                }
            } catch (Exception e) {
                System.out.println("Please enter your choice's corresponding Integer");
            }
        } while (true);
    }

    //Prompts the user to enter email and then password, if they match with an existing user, they log in. If they don't
    //match the user is told that either the email or password was incorrect, and they are prompted again.
    //Takes scanner as a parameter
    public static void loginUser (Scanner scan) {
        //TODO
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int welcomeResponse = welcomeUser(scan);
        switch (welcomeResponse) {
            case 1:
                //TODO Login user
                break;
            case 2:
                //TODO create user
                break;
            case 3:
                System.out.println("Thank you!");
                running = false;
                break;
        }

    }
}