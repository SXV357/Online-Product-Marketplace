/**
 * Project 5 - UserTest.java
 * 
 * Class that handles all testing related to the functionalities implemented in
 * the user class.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version December 7, 2023
 */
// public class UserTest {
//     public static void main(String[] args) throws Exception {

//         ClearDatabaseData cl = new ClearDatabaseData();
//         cl.clearData();

//         /* CREATE NEW DATABASE INSTANCE FOR TESTING PURPOSES */
//         Database db = new Database();

//         /* USER CLASS TESTS */
//         System.out.println("Successful Email Modification Test Result: " + testModifyEmailSuccessful(db));
//         System.out.println("Unsuccessful Email Modification Test Result: " + testModifyEmailUnsuccessful(db));

//         System.out.println("Customer Account Deletion Test Result: " + deleteCustomerAccountSuccessful(db));
//         System.out.println("Seller Account Deletion Test Result: " + deleteSellerAccountSuccessful(db));
//     }

//     static String testModifyEmailSuccessful(Database db) throws Exception {
//         // Modifying an email to one that isn't already associated with an existing user
//         User customer = new Customer("customer1@gmail.com", "abc123", UserRole.CUSTOMER);
//         String previousCustomerRep = customer.toString();
//         db.addToDatabase("users.csv", previousCustomerRep);
//         User seller = new Seller("seller1@yahoo.com", "123456", UserRole.SELLER);
//         db.addToDatabase("users.csv", seller.toString());
//         customer.setEmail("hello123@hotmail.com");
//         db.modifyDatabase("users.csv", previousCustomerRep, customer.toString());
//         String resultUserContents = db.getDatabaseContents("users.csv").toString();
//         String expectedUserContents = "[C1000000,hello123@hotmail.com,abc123,CUSTOMER, S1000001," +
//                 "seller1@yahoo.com,123456,SELLER]";
//         return resultUserContents.equals(expectedUserContents) ? "Test Passed" : "Test Failed";
//     }

//     static String testModifyEmailUnsuccessful(Database db) throws Exception {
//         String[] firstUser = db.getDatabaseContents("users.csv").get(0).split(",");
//         User currentUser = new Customer(firstUser[0], firstUser[1], firstUser[2], UserRole.CUSTOMER);
//         String previousRepresentation = currentUser.toString();
//         currentUser.setEmail("seller1@yahoo.com");
//         db.modifyDatabase("users.csv", previousRepresentation, currentUser.toString());
//         String resultUserContents = db.getDatabaseContents("users.csv").toString();
//         String expectedUserContents = "[C1000000,hello123@hotmail.com,abc123,CUSTOMER, S1000001,seller1@yahoo.com," +
//                 "123456,SELLER]";
//         return resultUserContents.equals(expectedUserContents) ? "Test Passed" : "Test Failed";
//     }

//     static String deleteCustomerAccountSuccessful(Database db) throws Exception {
//         String[] firstUser = db.getDatabaseContents("users.csv").get(0).split(",");
//         User customer = new Customer(firstUser[0], firstUser[1], firstUser[2], UserRole.CUSTOMER);

//         String storeEntry = "ST1000000,S1000001,MetroFresh Market,1";
//         String productEntry = "S1000001,ST100000,PR1000000,MetroFresh Market,Bananas,5,2.75,Ripe Bananas";
//         String shoppingCartEntry = "C1000000,S1000001,ST1000000,PR1000000,MetroFresh Market,Bananas,2,5.50";
//         String purchaseHistoryEntry = "C1000000,S1000001,ST1000000,PR1000000,MetroFresh Market,Bananas,2,5.50";

//         db.addToDatabase("stores.csv", storeEntry);
//         db.addToDatabase("products.csv", productEntry);
//         db.addToDatabase("shoppingCarts.csv", shoppingCartEntry);
//         db.addToDatabase("purchaseHistories", purchaseHistoryEntry);

//         customer.deleteAccount();

//         String resultUserContents = db.getDatabaseContents("users.csv").toString();
//         String resultShoppingCartContents = db.getDatabaseContents("shoppingCarts.csv").toString();
//         String resultPurchaseHistoryContents = db.getDatabaseContents("purchaseHistories").toString();

//         String expectedUserContents = "[S1000001,seller1@yahoo.com,123456,SELLER]";
//         String expectedShoppingCartContents = "[]";
//         String expectedPurchaseHistoryContents = "[]";

//         return resultUserContents.equals(expectedUserContents)
//                 && resultShoppingCartContents.equals(expectedShoppingCartContents)
//                 && resultPurchaseHistoryContents.equals(expectedPurchaseHistoryContents) ? "Test Passed"
//                 : "Test Failed";
//     }

//     static String deleteSellerAccountSuccessful(Database db) throws Exception {
//         String[] firstUser = db.getDatabaseContents("users.csv").get(0).split(",");
//         User seller = new Seller(firstUser[0], firstUser[1], firstUser[2], UserRole.SELLER);

//         seller.deleteAccount();

//         String resultUserContents = db.getDatabaseContents("users.csv").toString();
//         String resultStoreContents = db.getDatabaseContents("stores.csv").toString();
//         String resultProductsContents = db.getDatabaseContents("products").toString();
//         String resultShoppingCartContents = db.getDatabaseContents("shoppingCarts.csv").toString();

//         String expectedUserContents = "[]";
//         String expectedStoreContents = "[]";
//         String expectedProductContents = "[]";
//         String expectedShoppingCartContents = "[]";

//         return resultUserContents.equals(expectedUserContents) && resultStoreContents.equals(expectedStoreContents)
//                 && resultProductsContents.equals(expectedProductContents)
//                 && resultShoppingCartContents.equals(expectedShoppingCartContents) ? "Test Passed" : "Test Failed";
//     }

// }