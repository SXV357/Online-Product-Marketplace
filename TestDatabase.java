import java.io.IOException;
/**
 * Project 4 - TestDatabase.java
 * 
 * Test cases for functionality in the Database class
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 5, 2023
 */
public class TestDatabase {

    public static void main(String[] args) throws IOException {
        Database db = new Database();
        // tests for adding user to database based on email and pw validation
        testAddUserToDatabaseWhenCredentialsAreValid(db);
        testAddUserToDatabaseWhenEmailIsInvalid(db);
        testAddUserToDatabaseWhenPasswordIsInvalid(db);
        testAddUserToDatabaseWhenEmailAndPasswordAreInvalid(db);
        testAddUserToDatabaseWhenCredentialsProvidedAreBlank(db);
        // tests for removing user from database
        testRemoveUserFromDatabaseSuccessful(db);
        testRemoveUserFromDatabaseFailure(db);
        // tests for updating user entry in database
        testSuccessfulEmailUpdateInDatabase(db);
        testSuccessfulPasswordUpdateInDatabase(db);
        testUnsuccessfulEmailUpdateInDatabase(db);
        testUnsuccessfulPasswordUpdateInDatabase(db);
        // test for user retrieval from the database
        testUserRetrievalFromDatabase(db);
        // test for retrieving a customer's email from database
        testCustomerEmailRetrievalFromDatabase(db);
    }
    
    static void testAddUserToDatabaseWhenCredentialsAreValid(Database db) {
        // both the email and the password are valid
        User validCreds = new User(141094, "random_email1@example.com", "v8s091lW23", UserRole.CUSTOMER);
        boolean userAdded = db.addUser(validCreds);
        assert userAdded;
    }

    static void testAddUserToDatabaseWhenEmailIsInvalid(Database db) {
        // email is invalid here(does not contain a domain name)
        User invalidEmail = new User(237912, "something@.com", "ed78CV1L", UserRole.SELLER);
        boolean userAdded = db.addUser(invalidEmail);
        assert !userAdded;
    }

    static void testAddUserToDatabaseWhenPasswordIsInvalid(Database db) {
        // password is invalid here(contains special characters which are not allowed)
        User invalidPw = new User(189764, "validemail@hotmail.com", "&*tyqer6", UserRole.CUSTOMER);
        boolean userAdded = db.addUser(invalidPw);
        assert !userAdded;
    }

    static void testAddUserToDatabaseWhenEmailAndPasswordAreInvalid(Database db) {
        User wrongCreds = new User(189764, "nonvalid.com", "abc12", UserRole.CUSTOMER);
        boolean userAdded = db.addUser(wrongCreds);
        assert !userAdded;
    }

    static void testAddUserToDatabaseWhenCredentialsProvidedAreBlank(Database db) {
        User missingCreds = new User(210347, "", "", UserRole.CUSTOMER);
        boolean userAdded = db.addUser(missingCreds);
        assert !userAdded;
    }

    static void testRemoveUserFromDatabaseSuccessful(Database db) throws IOException {
        // user exists in the database
        User targetUser = new User(141094, "random_email1@example.com", "v8s091lW23", UserRole.CUSTOMER);
        boolean userRemoved = db.removeUser(targetUser.getUserID());
        assert userRemoved;
    }

    static void testRemoveUserFromDatabaseFailure(Database db) throws IOException {
        // when the ID doesn't match an existing entry in the database
        boolean userRemoved = db.removeUser(263419);
        assert !userRemoved;
    }

    @SuppressWarnings("unused")
    static void testSuccessfulEmailUpdateInDatabase(Database db) {
        // when the user wants to modify their email and the new value they provided is a valid email
        User modifier = new User(879026, "user1@yahoo.com", "75ghUYX", UserRole.SELLER);
        boolean modifierAdded = db.addUser(modifier);
        boolean emailUpdated = db.updateDatabase(modifier, "email", "modifieduser1@yahoo.com");
        assert emailUpdated;
    }

    static void testSuccessfulPasswordUpdateInDatabase(Database db) {
        // when the user wants to modify their password and the new value they provided is a valid password
        User modifier = new User(879026, "modifieduser1@yahoo.com", "75ghUYX", UserRole.SELLER);
        boolean passwordUpdated = db.updateDatabase(modifier, "password", "abGO9651");
        assert passwordUpdated;
    }

    static void testUnsuccessfulEmailUpdateInDatabase(Database db) {
        // when the user wants to modify their email and the new value provided is not a valid email
        User modifier = new User(879026, "modifieduser1@yahoo.com", "abGO9651", UserRole.SELLER);
        // an email is given but it is an invalid email
        boolean emailUpdated = db.updateDatabase(modifier, "email", "hello@1.2com");
        // an email is not even given in the first place
        boolean emailUpdatedTwo = db.updateDatabase(modifier, "email", "248%$%GBJHG");
        assert !emailUpdated && !emailUpdatedTwo;
    }

    static void testUnsuccessfulPasswordUpdateInDatabase(Database db) {
        // when the user wants to modify their password and the new value provided is not a valid password
        User modifier = new User(879026, "modifieduser1@yahoo.com", "abGO9651", UserRole.SELLER);
        // a password is given but it is not a valid password
        boolean passwordUpdated = db.updateDatabase(modifier, "password", "&%1XOP79");
        // a password is not even given in the first place
        boolean passwordUpdatedTwo = db.updateDatabase(modifier, "password", "hello123@gmail.com");
        assert !passwordUpdated && !passwordUpdatedTwo;
    }

    static void testUserRetrievalFromDatabase(Database db) {
        // one user exists in the database and one doesn't
        boolean successfulUserRetrieval = db.retrieveUser("modifieduser1@yahoo.com", "abGO9651");
        boolean unsuccessfulUserRetrieval = db.retrieveUser("random_email1@example.com", "v8s091lW23");
        assert successfulUserRetrieval && !unsuccessfulUserRetrieval;
    }

    static void testCustomerEmailRetrievalFromDatabase(Database db) {
        String nonNullCustomerEmail = db.retrieveCustomerEmail(879026);
        String nullCustomerEmail = db.retrieveCustomerEmail(123456);
        System.out.println("Matched customer email: " + nonNullCustomerEmail);
        assert (nonNullCustomerEmail != null) && (nullCustomerEmail == null);
    }

}