import java.util.Scanner;

/**
 * Project 4 - Runner.java
 *
 * Driver class for the application.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 *
 * @version November 3, 2023
 */

public class Runner {
    private static Database db = new Database();  //Makes a Database object
    private static boolean userLoggedIn = false;  //This boolean is used to check if the user has logged in successfully
    private static User curUser = new User();  //This field stores the user object for the current User of the program


    //Welcomes the user and asks them whether they would like to: Log-in, Create Account, or Quit
    //Takes only the Scanner as a Parameter
    public static int welcomeUser(Scanner scan) {
        System.out.println("What would you like to do?");
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
        System.out.println("Enter your E-mail: ");
        String uEmail = scan.nextLine();
        System.out.println("Enter your password: ");
        String uPassword = scan.nextLine();
        if (db.retrieveUser(uEmail, uPassword)) {
            System.out.println("Login successful!");
            //TODO set curUser to the one that just logged in
            userLoggedIn = true;
        } else {
            System.out.println("Username or password is incorrect");
        }
    }

    //This method creates a new user object,
    public static void createUser(Scanner scan){
        System.out.println("Enter your E-mail: ");
        String uEmail = scan.nextLine();
        System.out.println("Enter your password: ");
        String uPassword = scan.nextLine();
        if (db.retrieveUser(uEmail, uPassword)) {  //Check to see if there is a user with the email already
            System.out.println("User already exists");
        } else {
            UserRole uRole = UserRole.UNDECIDED;  //New user is defaulted to undecided until they set it
            while (uRole.equals(UserRole.UNDECIDED)) {
                System.out.println("Which would you like to be? \n1) Seller\n2) Customer");
                try {
                    int input = Integer.parseInt(scan.nextLine());
                    if (input == 1) {
                        uRole = UserRole.SELLER;
                    } else if (input == 2) {
                        uRole = UserRole.CUSTOMER;
                    } else {
                        System.out.println("Please enter your choice's corresponding Integer");
                    }
                } catch (Exception e) {
                    System.out.println("Please enter your choice's corresponding Integer");
                }
            }
            User newU = new User(uEmail, uPassword, uRole);
            curUser = newU;
            db.addUser(curUser);
            userLoggedIn = true;
        }
    }

    public static int sellerPrompt(Scanner scan) {
        System.out.println("Welcome Seller");
        int sellerChoice = 0;
        do {
            System.out.println("1) Market\n2) Purchase History\n3) Dashboard\n4) Shopping Cart\n 5)Sign Out");
            try {
                sellerChoice = Integer.parseInt(scan.nextLine());
                if (sellerChoice > 0 && sellerChoice < 6) {
                    return sellerChoice;
                } else {
                    System.out.println("Please enter your choice's corresponding Integer");
                }
            } catch (Exception e) {
                System.out.println("Please enter your choice's corresponding Integer");
            }
        } while (true);
    }

    public static int customerPrompt(Scanner scan) {
        System.out.println("Welcome Customer");
        int customerChoice = 0;
        do {
            System.out.println(""); //TODO
            try {
                customerChoice = Integer.parseInt(scan.nextLine());
                if (customerChoice > 0 && customerChoice < 6) {
                    return customerChoice;
                } else {
                    System.out.println("Please enter your choice's corresponding Integer");
                }
            } catch (Exception e) {
                System.out.println("Please enter your choice's corresponding Integer");
            }
        } while (true);
    }


    public static void main(String[] args) {
        System.out.println("Welcome!");
        Scanner scan = new Scanner(System.in);
        do {
            int welcomeResponse = welcomeUser(scan);
            switch (welcomeResponse) {
                case 1:
                    loginUser(scan);
                    break;
                case 2:
                    createUser(scan);
                    break;
                case 3:
                    System.out.println("Thank you!");
                    return;  //ends the program
            }
        } while (!userLoggedIn);
        if (curUser.getRole().equals(UserRole.SELLER)) {  //code for if the current user is a seller
            int sellerChoice = sellerPrompt(scan);
            switch (sellerChoice) {
                //TODO add all of the seller choices
                case 1:
            }
        } else if (curUser.getRole().equals(UserRole.CUSTOMER)) {  //code for if the current user is a customer
            int customerChoice = customerPrompt(scan);
            switch (customerChoice) {
                //TODO add all of the seller choices
                case 1:
            }
        }
    }
}