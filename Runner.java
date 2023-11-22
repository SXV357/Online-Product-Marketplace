import java.util.ArrayList;
import java.util.Scanner;

/**
 * Project 5 - Runner.java
 * 
 * Driver class for the application
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version November 21, 2023
 */

public class Runner {
    private static final Database DB = new Database(); // Makes a Database object
    private static boolean userLoggedIn = false; // This boolean is used to check if the user has logged in successfully
    private static User curUser = new User(); // This field stores the user object for the current User of the program
    private static final String LINE_BREAK = "\n-------------------------------------------------";
    private static final String SWITCH_IO_ERROR_MESSAGE = "Input Error:\nPlease enter your choice's " +
            "corresponding Integer" + LINE_BREAK;

    // Welcomes the user and asks them whether they would like to: Log-in, Create
    // Account, or Quit
    // Takes only the Scanner as a Parameter
    public static int welcomeUser(Scanner scan) {
        System.out.println("What would you like to do?");
        do {
            System.out.println("1) Log-in\n2) Create Account\n3) Quit" + LINE_BREAK);
            try {
                int response = Integer.parseInt(scan.nextLine());
                if (response > 0 && response < 4) {
                    return response;
                } else {
                    System.out.println(SWITCH_IO_ERROR_MESSAGE);
                }
            } catch (Exception e) {
                System.out.println(SWITCH_IO_ERROR_MESSAGE);
            }
        } while (true);
    }

    // Prompts the user to enter email and then password, if they match with an
    // existing user, they log in. If they don't
    // match the user is told that either the email or password was incorrect, and
    // they are prompted again.
    // Takes scanner as a parameter
    public static void loginUser(Scanner scan) {
        System.out.println("Enter your E-mail: ");
        String uEmail = scan.nextLine();
        System.out.println("Enter your password: ");
        String uPassword = scan.nextLine();
        if (DB.retrieveUserMatchForLogin(uEmail, uPassword) != null) {
            System.out.println("Login successful!");
            String userAsString = DB.retrieveUserMatchForLogin(uEmail, uPassword);
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

    // This method creates a new user object,
    public static void createUser(Scanner scan) {
        System.out.println("Enter your E-mail: ");
        String uEmail = scan.nextLine();
        System.out.println("Enter your password: ");
        String uPassword = scan.nextLine();
        if (uEmail.isEmpty() || uPassword.isEmpty()) {
            System.out.println("Fields cannot be blank" + LINE_BREAK);
        } else if (DB.retrieveUserMatchForSignUp(uEmail) != null) { //see if there is a user with the email already
            System.out.println("User already exists");
        } else {
            UserRole uRole = UserRole.UNDECIDED; // New user is defaulted to undecided until they set it
            while (uRole.equals(UserRole.UNDECIDED)) {
                System.out.println("Which would you like to be? \n1) Seller\n2) Customer" + LINE_BREAK);
                try {
                    int input = Integer.parseInt(scan.nextLine());
                    if (input == 1) {
                        uRole = UserRole.SELLER;
                    } else if (input == 2) {
                        uRole = UserRole.CUSTOMER;
                    } else {
                        System.out.println(SWITCH_IO_ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    System.out.println(SWITCH_IO_ERROR_MESSAGE);
                }
            }
            curUser = new User(uEmail, uPassword, uRole);
            DB.addToDatabase("users.csv", curUser.toString()); // based on latest modification of database
            userLoggedIn = true;
        }
    }

    public static int sellerPrompt(Scanner scan) {
        int sellerChoice;
        do {
            System.out.println("1) Stores\n2) Dashboard\n3) Customer Shopping Carts\n4) Edit Account\n5) Sign Out"
                    + LINE_BREAK);
            try {
                sellerChoice = Integer.parseInt(scan.nextLine());
                if (sellerChoice > 0 && sellerChoice < 6) {
                    return sellerChoice;
                } else {
                    System.out.println(SWITCH_IO_ERROR_MESSAGE);
                }
            } catch (Exception e) {
                System.out.println(SWITCH_IO_ERROR_MESSAGE);
            }
        } while (true);
    }

    public static int customerPrompt(Scanner scan) {
        int customerChoice;
        do {
            System.out.println("1) Market\n2) Purchase History\n3) Dashboard\n4) Shopping Cart\n5) Edit Account\n" +
                    "6) Sign Out" + LINE_BREAK);
            try {
                customerChoice = Integer.parseInt(scan.nextLine());
                if (customerChoice > 0 && customerChoice < 7) {
                    return customerChoice;
                } else {
                    System.out.println(SWITCH_IO_ERROR_MESSAGE);
                }
            } catch (Exception e) {
                System.out.println(SWITCH_IO_ERROR_MESSAGE);
            }
        } while (true);
    }

    public static void storeUI(Scanner scan, Seller curSeller) {
        while (true) { // loops until seller returns
            System.out.println("What would you like to do?");
            System.out.println("1) Select Store\n2) Create Store\n3) Go Back" + LINE_BREAK);
            ArrayList<Store> curSellerStores = curSeller.getStores();
            try {
                int storeUIChoice = Integer.parseInt(scan.nextLine());
                switch (storeUIChoice) {
                    case 1: // Select a Store
                        if (curSellerStores != null) {
                            while (true) { // loops until seller selects "Go Back"
                                System.out.println("Select a store or go back:");
                                for (int i = 0; i <= curSellerStores.size(); i++) {
                                    if (i < curSellerStores.size()) {
                                        System.out.println((i + 1) + ") " + curSellerStores.get(i).getStoreName());
                                    } else {
                                        System.out.println((i + 1) + ") " + "Go Back" + LINE_BREAK);
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
                                    System.out.println(SWITCH_IO_ERROR_MESSAGE);
                                }
                            }
                            break;
                        } else {
                            System.out.println("You do not currently have any stores!");
                            break;
                        }
                    case 2: // Create Store
                        System.out.println("What would you like to name the store?");
                        String newStoreName = scan.nextLine();
                        curSeller.createNewStore(newStoreName);
                        System.out.println(newStoreName + " has been created.");
                        break;
                    case 3: // sends seller back to initial SellerUI
                        return;
                    default:
                        System.out.println(SWITCH_IO_ERROR_MESSAGE);
                        break;
                }
            } catch (Exception e) {
                System.out.println(SWITCH_IO_ERROR_MESSAGE);
            }
        }
    }

    public static void manageStore(Scanner scan, Seller curSeller, Store curStore) {
        while (true) { // loops until seller goes back
            Product curProduct;
            System.out.println(curStore.getStoreName() + " Options:\n1) Create Product\n2) Edit Product\n" +
                    "3) Delete Product\n4) Import Products\n5) Export Products\n6) Go Back" + LINE_BREAK);
            try {
                switch (Integer.parseInt(scan.nextLine())) {
                    case 1: // Create Product
                        System.out.println("What would you like to name the product?");
                        String productName = scan.nextLine();
                        String availableQuantity;
                        while (true) {
                            System.out.println("How many are for sale?");
                            try {
                                availableQuantity = scan.nextLine();
                                break;
                            } catch (Exception e) {
                                System.out.println("Error: Please enter an integer");
                            }
                        }
                        String price;
                        while (true) {
                            System.out.println("What is the price per product in dollars?");
                            try {
                                price = scan.nextLine();
                                break;
                            } catch (Exception e) {
                                System.out.println("Error: Please enter a double");
                            }
                        }
                        System.out.println("Describe the product please");
                        String description = scan.nextLine();
                        curSeller.createNewProduct(curStore.getStoreName(), productName,
                                availableQuantity, price, description);
                        break;
                    case 2: // Edit Product
                        if (curStore.getProducts() == null) {
                            System.out.println("This store has no products");
                            break;
                        }
                        curProduct = pickProduct(scan, curStore);
                        if (curProduct == null) {
                            break;
                        }
                        String editParam;
                        while (true) {
                            System.out.println("Would you like to change the product's name, quantity, " +
                                    "price, or description?");
                            editParam = scan.nextLine();
                            if (editParam.equals("name") || editParam.equals("quantity") || editParam.equals("price")
                                    || editParam.equals("description")) {
                                break;
                            } else {
                                System.out.println("Please enter one of the three options.");
                            }
                        }
                        String newValue;
                        do { // loops until newValue is not null
                            System.out.println("What would you like the new value to be?");
                            newValue = scan.nextLine();
                        } while (newValue == null);
                        if (curSeller.editProduct(curStore.getStoreName(), curProduct.getName(), editParam,
                                newValue).equals("Product edited successfully")) {
                            System.out.println("Product successfully edited");
                        } else {
                            System.out.println("Error editing product"); // fix to be more specific
                        }
                        break;
                    case 3: // Delete Product
                        if (curStore.getProducts() == null) {
                            System.out.println("This store has no products");
                            break;
                        }
                        curProduct = pickProduct(scan, curStore);
                        curSeller.deleteProduct(curStore.getStoreName(),
                                curProduct.getName());
                        System.out.println("Product has been deleted");
                        break;
                    case 4: // Import Products
                        while (true) { // loop until import is successful
                            System.out.println("Please enter the file path for the import\n Enter \"Quit\" to exit"
                                    + LINE_BREAK);
                            String filePath = scan.nextLine();
                            if (filePath.equals("Quit") || filePath.equals("quit")) {
                                break;
                            }
                            String successfulImport = curSeller.importProducts(filePath,
                                    curStore.getStoreName());
                            if (successfulImport.equals("Products imported successfully")) {
                                break;
                            } else {
                                System.out.println("file path is incorrect");
                            }
                        }
                    case 5: // Export Products
                        if (curStore.getProducts() == null) {
                            System.out.println("This store has no products");
                            break;
                        }
                        curSeller.exportProducts(curStore.getStoreName());
                        System.out.println("Products have been exported to " + "exportedProducts/"
                                + curStore.getStoreName() + ".csv");
                        break;
                    case 6: // Go Back
                        return;
                    default: // integer is out of range
                        System.out.println(SWITCH_IO_ERROR_MESSAGE);
                        break;
                }
            } catch (Exception e) {
                System.out.println(SWITCH_IO_ERROR_MESSAGE);
            }

        }
    }

    public static Product pickProduct(Scanner scan, Store curStore) {
        ArrayList<Product> productList = curStore.getProducts();
        System.out.println("Which product would you like to adjust?");
        for (int i = 0; i <= productList.size(); i++) {
            if (i < productList.size()) {
                System.out.println((i + 1) + ") " + productList.get(i).getName());
            } else {
                System.out.println((i + 1) + ") " + "Go Back");
            }
        }
        int editProductChoice;
        Product chosenProduct = new Product("", 0, 0, "");
        try {
            editProductChoice = Integer.parseInt(scan.nextLine());
            if (editProductChoice == productList.size() + 1) {
                return null;
            } else {
                chosenProduct = productList.get(editProductChoice - 1);
            }
        } catch (Exception e) {
            System.out.println(SWITCH_IO_ERROR_MESSAGE);
        }
        return chosenProduct;
    }

    public static void marketUI(Scanner scan, Customer curCustomer) {
        while (true) { // loops until user selects to go back
            System.out.println("What would you like to do?\n1) View All Products\n2) Search for a Product\n3) Return"
                    + LINE_BREAK);
            int marketChoice = Integer.parseInt(scan.nextLine());
            switch (marketChoice) {
                case 1: // View Products
                    showAllProducts(scan, curCustomer, false);
                    break;
                case 2: // Search for product
                    showAllProducts(scan, curCustomer, true);
                    break;
                case 3: // Go Back
                    return;
                default: // marketChoice was out of range
            }
        }
    }

    public static void showAllProducts(Scanner scan, Customer curCustomer, Boolean searching) {
        if (searching) {
            System.out.println("What would you like to search for?");
            String query = scan.nextLine();
            if ((curCustomer.searchProducts(query)).equals("Query has returned no results!" + LINE_BREAK)) {
                System.out.println(curCustomer.searchProducts(query));
                return;
            }
            System.out.println("Select an Item");
            System.out.println(curCustomer.searchProducts(query));
        } else {
            System.out.println("Select an Item");
            System.out.println(curCustomer.getAllProducts());
        }
        System.out.println(LINE_BREAK);
        try {
            int productSelection = Integer.parseInt(scan.nextLine());
            System.out.println(curCustomer.getProductInfo(productSelection));
            if (curCustomer.getProductInfo(productSelection).equals("Invalid Product")) {
                return;
            }
            while (true) {
                System.out.println("Would you like to add this item to cart?\n1) Yes\n2) No" + LINE_BREAK);
                switch (Integer.parseInt(scan.nextLine())) {
                    case 1: // yes
                        System.out.println("How many would you like?");
                        int availableQuantity = Integer.parseInt(
                                curCustomer.getProductInfo(productSelection).substring(
                                        curCustomer.getProductInfo(productSelection).indexOf("Quantity: ") + 10,
                                        curCustomer.getProductInfo(productSelection).indexOf("\nPrice:")));
                        int quantity = Integer.parseInt(scan.nextLine());
                        if (quantity < 1) {
                            System.out.println("Please enter a positive integer for the number you wish to purchase");
                        } else if (quantity > availableQuantity) {
                            System.out.println("There are only " + availableQuantity + " available");
                        } else {
                            curCustomer.addToCart(productSelection, quantity);
                            System.out.println("Product has been added to cart");
                            return;
                        }
                        break;
                    case 2: // No
                        return;
                    default: // error
                        System.out.println(SWITCH_IO_ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            System.out.println(SWITCH_IO_ERROR_MESSAGE);
        }
    }

    private static void customerShoppingCart(Scanner scan, Customer curCustomer) {
        while (true) {
            if (curCustomer.getCart().equals("No Products Available")) {
                System.out.println("Shopping Cart is empty");
                return;
            }
            System.out.println(curCustomer.getCart());
            System.out.println("What would you like to do?\n1) Checkout\n2) Remove a Product\n3) Return" + LINE_BREAK);
            try {
                switch (Integer.parseInt(scan.nextLine())) {
                    case 1: // checkout
                        boolean purchased = curCustomer.purchaseItems();
                        if (!purchased) {
                            System.out.println("Error: not enough products in stock\n product has been removed" +
                                    "from cart");
                        }
                        break;
                    case 2: // remove an item
                        System.out.println("Which item would you like to remove?" + LINE_BREAK);
                        System.out.println(curCustomer.getCart());
                        int removeChoice = Integer.parseInt(scan.nextLine());
                        curCustomer.removeFromCart(removeChoice);
                        System.out.println("Item has been removed from cart");
                        break;
                    case 3: // return
                        return;
                    default: // error
                        System.out.println(SWITCH_IO_ERROR_MESSAGE);
                }

            } catch (Exception e) {
                System.out.println(SWITCH_IO_ERROR_MESSAGE);
            }
        }
    }

    private static void userIsSeller(Scanner scan, Seller curSeller) {
        while (true) { // loops until Seller signs out
            int sellerChoice = sellerPrompt(scan);
            switch (sellerChoice) {
                case 1: // Stores
                    storeUI(scan, curSeller);
                    break;
                case 2: // Dashboard
                    sellerDashboard(scan, curSeller);
                    break;
                case 3: // Customer Shopping Carts
                    System.out.println(curSeller.viewCustomerShoppingCarts());
                    break;
                case 4: // Edit user
                    editUser(scan);
                    if (!userLoggedIn) {
                        return;
                    }
                    break;
                case 5: // Sign Out
                    userLoggedIn = false;
                    System.out.println("Thank you!");
                    return; // ends the program
                default: // error
                    System.out.println(SWITCH_IO_ERROR_MESSAGE);
                    break;
            }
        }
    }

    private static void userIsCustomer(Scanner scan, Customer curCustomer) {
        while (true) { // loops until the user signs out
            int customerChoice = customerPrompt(scan);
            switch (customerChoice) {
                case 1: // Market
                    marketUI(scan, curCustomer);
                    break;
                case 2: // Purchase History
                    boolean historyExported = curCustomer.exportPurchaseHistory();
                    if (historyExported) {
                        System.out.println("Purchase history has been exported to the exportedHistory directory.");
                    } else {
                        System.out.println("Purchase History is empty");
                    }

                    break;
                case 3: // Dashboard
                    customerDashboard(scan, curCustomer);
                    break;
                case 4: // Shopping Cart
                    customerShoppingCart(scan, curCustomer);
                    break;
                case 5: // Edit Account
                    editUser(scan);
                    if (!userLoggedIn) {
                        return;
                    }
                    break;
                case 6: // Sign Out
                    userLoggedIn = false;
                    System.out.println("Thank you!");
                    return; // ends the program
                default: // error
                    System.out.println(SWITCH_IO_ERROR_MESSAGE);
                    break;
            }
        }
    }

    // This method doesn't check for input errors until all three inputs have been
    // inputted
    private static void customerDashboard(Scanner scan, Customer curCustomer) {
        System.out.println("Which type of dashboard would you like to see?\n1) Stores\n2) My Purchases" + LINE_BREAK);
        boolean inputting = true;
        String sortPrompt = "";
        while (inputting) { // until dashboard choice is fully imputed
            int customerChoice;
            try {
                customerChoice = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                customerChoice = -1;
            }

            // Customer chose which dashboard to see
            switch (customerChoice) {
                case 1: // Stores
                    sortPrompt = "Select how you would like to sort the dashboard.\n1) Store name\n2) Number of " +
                            "product sales\n3) Total revenue" + LINE_BREAK;
                    break;
                case 2: // My Purchases
                    sortPrompt = "Select how you would like to sort the dashboard.\n1) Product name\n2) Number of " +
                            "products bought\n3 )Total spent" + LINE_BREAK;
                    break;
                default: // error, will be printed at loop end.
                    break;
            }

            System.out.println(sortPrompt);
            int customerSortChoice;
            try {
                customerSortChoice = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                customerSortChoice = -1;
            }

            // Getting input
            System.out.println("Would you like to sort ascending or descending?\n1) Ascending\n2) Descending"
                    + LINE_BREAK);
            int customerAscChoice;
            boolean ascending = false;
            try {
                customerAscChoice = Integer.parseInt(scan.nextLine());
                if (customerAscChoice == 1) {
                    ascending = true;
                } else if (customerAscChoice == 2) {
                    ascending = false;
                } else {
                    customerAscChoice = -1;
                }
            } catch (Exception e) {
                customerAscChoice = -1;
            }

            // if all 3 inputs are valid, print database and break the loop
            if (!(customerChoice == -1) && !(customerSortChoice == -1) && !(customerAscChoice == -1)) {
                // depending on dashboard type
                switch (customerChoice) {
                    case 1:
                        Dashboard.printDashboardData("Stores",
                                Dashboard.customerGetStoresDashboard(customerSortChoice, ascending));
                        break;
                    case 2:
                        Dashboard.printDashboardData("PersonalPurchases",
                                Dashboard.customerGetPersonalPurchasesDashboard(customerSortChoice, ascending,
                                        curCustomer.getUserID()));
                    default:
                        break;
                }
                inputting = false;

            } else {
                System.out.println("Invalid input(s) Please try again.");
            }
        }
    }

    // This method doesn't check for input errors until all three inputs have been
    // inputted
    private static void sellerDashboard(Scanner scan, Seller curSeller) {
        System.out.println("Which type of dashboard would you like to see?\n1) Customers\n2) Products" + LINE_BREAK);
        boolean inputting = true;
        String sortPrompt = "";
        while (inputting) { // until dashboard choice is fully imputed
            int sellerChoice;
            try {
                sellerChoice = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                sellerChoice = -1;
            }

            // Customer chose which dashboard to see
            switch (sellerChoice) {
                case 1: // Stores
                    sortPrompt = "Select how you would like to sort the dashboard.\n1) Customer name\n" +
                            "2) Number of products bought\n3) Total spent" + LINE_BREAK;
                    break;
                case 2: // My Purchases
                    sortPrompt = "Select how you would like to sort the dashboard.\n1) Product name\n" +
                            "2) Number of products sold\n3) Total revenue" + LINE_BREAK;
                    break;
                default: // error, will be printed at loop end.
                    break;
            }

            System.out.println(sortPrompt);
            int sellerSortChoice;
            try {
                sellerSortChoice = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                sellerSortChoice = -1;
            }

            // Getting input
            System.out.println("Would you like to sort ascending or descending?\n1) Ascending\n2) Descending"
                    + LINE_BREAK);
            int sellerAscChoice;
            boolean ascending = false;
            try {
                sellerAscChoice = Integer.parseInt(scan.nextLine());
                if (sellerAscChoice == 1) {
                    ascending = true;
                } else if (sellerAscChoice == 2) {
                    ascending = false;
                } else {
                    sellerAscChoice = -1;
                }
            } catch (Exception e) {
                sellerAscChoice = -1;
            }

            // if all 3 inputs are valid, print database and break the loop
            if (!(sellerChoice == -1) && !(sellerSortChoice == -1) && !(sellerAscChoice == -1)) {
                // depending on dashboard type
                switch (sellerChoice) {
                    case 1:
                        Dashboard.printDashboardData("Customers",
                                Dashboard.sellerGetCustomersDashboard(sellerSortChoice, ascending));
                        break;
                    case 2:
                        Dashboard.printDashboardData("Products",
                                Dashboard.sellerGetProductsDashboard(sellerSortChoice, ascending));
                    default:
                        break;
                }
                inputting = false;

            } else {
                System.out.println("Invalid input(s) Please try again.");
            }
        }
    }

    private static void deleteUser(Scanner scan) {
        while (true) {
            System.out.println("Are you sure you want to delete your account?\n1) Yes\n2) No" + LINE_BREAK);
            try {
                switch (Integer.parseInt(scan.nextLine())) {
                    case 1:  //delete
                        curUser.deleteAccount();
                        userLoggedIn = false;
                        return;
                    case 2:  //Back out
                        return;
                    default:  //Error
                        System.out.println(SWITCH_IO_ERROR_MESSAGE);
                }
            } catch (Exception e) {
                System.out.println(SWITCH_IO_ERROR_MESSAGE);
            }
        }
    }

    private static void editUser(Scanner scan) {
        while (true) {
            String newUserString;
            String prevUserString;
            System.out.println("What would you like to do?\n1) Change Email\n2) Change Password\n3) Delete Account\n" +
                    "4) Exit" + LINE_BREAK);
            try {
                switch (Integer.parseInt(scan.nextLine())) {
                    case 1: // Change Email
                        prevUserString = curUser.toString();
                        System.out.println("What would you like your new Email to be?");
                        String newEmail = scan.nextLine();
                        curUser.setEmail(newEmail);
                        newUserString = curUser.toString();
                        DB.modifyDatabase("users.csv", prevUserString, newUserString);
                        break;
                    case 2: // Change Password
                        prevUserString = curUser.toString();
                        System.out.println("What would you like your new Password to be?");
                        String newPassword = scan.nextLine();
                        curUser.setPassword(newPassword);
                        newUserString = curUser.toString();
                        DB.modifyDatabase("users.csv", prevUserString, newUserString);
                        break;
                    case 3: // Delete Account
                        deleteUser(scan);
                        if (!userLoggedIn) {
                            return;
                        }
                        break;
                    case 4: // Return
                        return;
                    default: // error
                        System.out.println(SWITCH_IO_ERROR_MESSAGE);
                        break;
                }
            } catch (Exception e) {
                System.out.println(SWITCH_IO_ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome!");
        Scanner scan = new Scanner(System.in);
        while (true) {
            do {
                int welcomeResponse = welcomeUser(scan);
                switch (welcomeResponse) {
                    case 1: // Log user in
                        loginUser(scan);
                        break;
                    case 2: // Create new user
                        createUser(scan);
                        break;
                    case 3: // Quit Program
                        System.out.println("Goodbye");
                        scan.close();
                        return; // ends the program
                }
            } while (!userLoggedIn);
            if (curUser.getRole().equals(UserRole.SELLER)) { // code for if the current user is a seller
                Seller curSeller = new Seller(curUser.getUserID(), curUser.getEmail(),
                        curUser.getPassword(), curUser.getRole()); // makes a seller object with same ID as user
                System.out.println("Welcome Seller");
                userIsSeller(scan, curSeller);
            } else if (curUser.getRole().equals(UserRole.CUSTOMER)) { // code for if the current user is a customer
                Customer curCustomer = new Customer(curUser.getUserID(), curUser.getEmail(),
                        curUser.getPassword(), UserRole.CUSTOMER);
                System.out.println("Welcome Customer");
                userIsCustomer(scan, curCustomer);
            }
        }
    }
}