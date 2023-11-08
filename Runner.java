import java.util.ArrayList;
import java.util.Scanner;

/**
 * Project 4 - Runner.java
 * <p>
 * Driver class for the application.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
  * @version November 5, 2023
 */

public class Runner {
    private static final Database db = new Database();  //Makes a Database object
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
    public static void loginUser(Scanner scan) {
        System.out.println("Enter your E-mail: ");
        String uEmail = scan.nextLine();
        System.out.println("Enter your password: ");
        String uPassword = scan.nextLine();
        if (db.retrieveUser(uEmail, uPassword) != null) {
            System.out.println("Login successful!");
            String userAsString = db.retrieveUser(uEmail, uPassword);
            String[] userFromDB = userAsString.split(",");
            String uID = userFromDB[0];
            UserRole uRole = UserRole.UNDECIDED;
            if (userFromDB[3].equals("SELLER")) {
                uRole = UserRole.SELLER;
            } else if (userFromDB[3].equals("CUSTOMER")) {
                uRole = UserRole.CUSTOMER;
            }
            curUser = new User(uID, uEmail, uPassword, uRole);
                        userLoggedIn = true;
        } else {
            System.out.println("Username or password is incorrect");
        }
    }

    //This method creates a new user object,
    public static void createUser(Scanner scan) {
        System.out.println("Enter your E-mail: ");
        String uEmail = scan.nextLine();
        System.out.println("Enter your password: ");
        String uPassword = scan.nextLine();
        if (db.retrieveUser(uEmail, uPassword) != null) {  //Check to see if there is a user with the email already
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
            curUser = new User(uEmail, uPassword, uRole);
                        db.addToDatabase("users.csv", curUser.toString()); // based on latest modification of database
            userLoggedIn = true;
        }
    }

    
    public static int sellerPrompt(Scanner scan) {
        System.out.println("Welcome Seller");
        int sellerChoice;
        do {
            System.out.println("1) Stores\n2) Dashboard\n3) Customer Shopping Carts\n4) Sign Out");
            try {
                sellerChoice = Integer.parseInt(scan.nextLine());
                if (sellerChoice > 0 && sellerChoice < 5) {
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
        int customerChoice;
        do {
            System.out.println("1) Market\n2) Purchase History\n3) Dashboard\n4) Shopping Cart\n5) Sign Out");
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

    public static void storeUI(Scanner scan, Seller curSeller) {
        while (true) {  //loops until seller returns
            System.out.println("What would you like to do?");
            System.out.println("1) Select Store\n2) Create Store\n3) Go Back");
            ArrayList<Store> curSellerStores = curSeller.getStores();
            try {
                int storeUIChoice = Integer.parseInt(scan.nextLine());
                switch (storeUIChoice) {
                    case 1:  //Select a Store
                        while (true) {  //loops until seller selects "Go Back"
                            System.out.println("Select a store or go back:");
                            for (int i = 0; i <= curSellerStores.size(); i++) {
                                if (i < curSellerStores.size()) {
                                    System.out.println((i + 1) + ") " + curSellerStores.get(i).getStoreName());
                                } else {
                                    System.out.println((i + 1) + ") " + "Go Back");
                                }
                            }
                            try {
                                int storeChoice = Integer.parseInt(scan.nextLine());
                                if (storeChoice == curSellerStores.size() + 1) {
                                    break;
                                } else {
                                    Store curStore = curSellerStores.get(storeChoice - 1);
                                    manageStore(scan, curSeller, curStore);
                                }
                            } catch (Exception e) {
                                System.out.println("Please enter your choice's corresponding Integer");
                            }
                        }
                        break;
                    case 2:  //Create Store
                        System.out.println("What would you like to name the store?");
                        //TODO implement a way to check unique store name
                        String newStoreName = scan.nextLine();
                        curSeller.createNewStore(newStoreName);
                        System.out.println(newStoreName + " has been created.");
                        break;
                    case 3:  //sends seller back to initial SellerUI
                        return;
                    default:
                        System.out.println("Please enter your choice's corresponding Integer");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Please enter your choice's corresponding Integer");
            }
        }
    }

    public static void manageStore(Scanner scan, Seller curSeller, Store curStore) {
        while (true) {  //loops until seller goes back
            System.out.println(curStore.getStoreName() + "Options:\n1) Create Product\n2) Edit Product\n" +
                    "3) Delete Product\n4) Import Products\n5) Export Products\n6) Go Back");
            try {
                switch (Integer.parseInt(scan.nextLine())) {
                    case 1:  //Create Product
                        System.out.println("What would you like to name the product?");
                        String productName = scan.nextLine();
                        int availableQuantity;
                        while (true) {
                            System.out.println("How many are for sale?");
                            try {
                                availableQuantity = Integer.parseInt(scan.nextLine());
                                break;
                            } catch (Exception e) {
                                System.out.println("Error: Please enter an integer");
                            }
                        }
                        double price;
                        while (true) {
                            System.out.println("What is the price per product in dollars?");
                            try {
                                price = Double.parseDouble(scan.nextLine());
                                break;
                            } catch (Exception e) {
                                System.out.println("Error: Please enter an double");
                            }
                        }
                        System.out.println("Describe the product please");
                        String description = scan.nextLine();
                        curSeller.createNewProduct(curStore.getStoreIdentificationNumber(), productName,
                                availableQuantity, price, description);
                        break;
                    case 2:  //Edit Product

                        break;
                    case 3:  //Delete Product

                        break;
                    case 4:  //Import Products

                        break;
                    case 5:  //Export Products

                        break;
                    case 6:  //Go Back
                        return;
                    default:  //integer is out of range
                        System.out.println("Please enter your choice's corresponding Integer");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Please enter your choice's corresponding Integer");
            }

        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome!");
        Scanner scan = new Scanner(System.in);
        do {
            int welcomeResponse = welcomeUser(scan);
            switch (welcomeResponse) {
                case 1:  //Log user in
                    loginUser(scan);
                    break;
                case 2:  //Create new user
                    createUser(scan);
                    break;
                case 3:  //Quit Program
                    System.out.println("Thank you!");
                    return;  //ends the program
            }
        } while (!userLoggedIn);
        if (curUser.getRole().equals(UserRole.SELLER)) {  //code for if the current user is a seller
Seller curSeller = new Seller(curUser.getUserID(), curUser.getEmail(),
                    curUser.getPassword(), curUser.getRole()); //makes a seller object with same ID as user
            while (true) {  //loops until Seller signs out
            int sellerChoice = sellerPrompt(scan);
            switch (sellerChoice) {
case 1:  //Stores
                        storeUI(scan, curSeller);
                        break;
                    case 2:  //Dashboard
                        //TODO add Dashboard UI
                        break;
                    case 3:  //Customer Shopping Carts
                        //TODO add Customer Shopping Cart UI
                        break;
                    case 4:  //Sign Out
                        System.out.println("Thank you!");
                        return;  //ends the program
                }
            }
        } else if (curUser.getRole().equals(UserRole.CUSTOMER)) {  //code for if the current user is a customer
/* add when customer has a constructor
            Customer curCustomer = new Seller(curUser.getUserID(), curUser.getEmail(),
                    curUser.getPassword(), curUser.getRole()); //makes a customer object with same ID as user
            */
            int customerChoice = customerPrompt(scan);
            switch (customerChoice) {
                case 1:  //Market
                    //TODO add market UI
                    break;
                case 2:  //Purchase History
                    //TODO add Purchase History UI
                    break;
                case 3:  //Dashboard
                    //TODO add Dashboard UI
                    break;
                case 4:  //Shopping Cart
                    //TODO add Shopping Cart UI
                    break;
                case 5:  //Sign Out
                    System.out.println("Thank you!");
                    return;  //ends the program
            }
                }
    }
}